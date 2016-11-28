package com.maplebox.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.base.Charsets;
import com.maplebox.interceptor.JWTInterceptor;
import com.maplebox.jackson.StringTrimModule;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {
	"com.maplebox.controller",
	"com.maplebox.interceptor",
	"com.maplebox.listener"})
@PropertySource(
	value = {"classpath:config/web-config-${web.mode:dev}${web.developer:}.xml"},
	ignoreResourceNotFound = false
)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	static Logger log = LoggerFactory.getLogger(WebMvcConfig.class);
	
	@Autowired
	private JWTInterceptor jwtIterceptor;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	    stringConverter.setSupportedMediaTypes(Arrays.asList(
	    	new MediaType("text", "plain", Charsets.UTF_8),
	    	new MediaType("text", "html", Charsets.UTF_8)
	    ));
	    stringConverter.setWriteAcceptCharset(false);
	    converters.add(stringConverter);
	    
	    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		ObjectMapper objectMapper = builder
			.indentOutput(false).dateFormat(new SimpleDateFormat("yyyy-MM-dd")).build();
		objectMapper.registerModule(new StringTrimModule());
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
		
		ResourceHttpMessageConverter resourceConverter = new ResourceHttpMessageConverter();
		resourceConverter.setDefaultCharset(Charsets.UTF_8);
		converters.add(resourceConverter);
		
		ByteArrayHttpMessageConverter byteArrayConverter = new ByteArrayHttpMessageConverter();
		byteArrayConverter.setDefaultCharset(Charsets.UTF_8);
		converters.add(byteArrayConverter);
		
		FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
		formConverter.setCharset(Charsets.UTF_8);
		formConverter.setMultipartCharset(Charsets.UTF_8);
		converters.add(formConverter);
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[] {"classpath:i18n/messages"});
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		//StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		//return multipartResolver;
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxInMemorySize(10240);
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxUploadSizePerFile(524288);
		
		return multipartResolver;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtIterceptor).addPathPatterns("/admin/*");
    }
	
	@Bean
	public Producer captchaProducer() throws IOException {
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(new Config(kaptchaProperties()));
		
		return kaptcha;
	}
	
	public Properties kaptchaProperties() throws IOException {
		Properties prop = new Properties();
		try (InputStream in = IOUtils.buffer(new ClassPathResource("kaptcha.xml").getInputStream())) {
			prop.loadFromXML(in);
		}
		return prop;
	}
	
	/*@Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }*/
}
