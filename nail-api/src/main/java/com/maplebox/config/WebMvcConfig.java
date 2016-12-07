package com.maplebox.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.base.Charsets;
import com.maplebox.app.AppInitializer;
import com.maplebox.interceptor.JWTInterceptor;
import com.maplebox.jackson.StringTrimModule;
import com.maplebox.util.PropertyUtil;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {
	"com.maplebox.controller",
	"com.maplebox.interceptor",
	"com.maplebox.listener"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	static Logger log = LoggerFactory.getLogger(WebMvcConfig.class);
	
	@Autowired
	private JWTInterceptor jwtIterceptor;
	
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Bean
    public static PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
		log.debug("WebMvcConfig.propertyConfigurer");
		return AppInitializer.propertyConfigurer();
    }
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charsets.UTF_8);
	    stringConverter.setSupportedMediaTypes(Arrays.asList(
	    	MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON
	    ));
	    stringConverter.setWriteAcceptCharset(false);
	    converters.add(stringConverter);
	    
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
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxInMemorySize(10240);
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxUploadSizePerFile(524288);
		
		return multipartResolver;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(jwtIterceptor).addPathPatterns("/admin/*");
    }
	
	@Bean
	public Producer captchaProducer() throws IOException {
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(new Config(kaptchaProperties()));
		
		return kaptcha;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		ObjectMapper objectMapper = builder
			.indentOutput(false).dateFormat(new SimpleDateFormat("yyyy-MM-dd")).build();
		objectMapper.registerModule(new StringTrimModule());
		return objectMapper;
	}
	
	@Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
	
	/*
	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.TAIWAN);
		return localeResolver;
	}
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.TAIWAN);
		return localeResolver;
	}
	*/
	
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.TAIWAN);
		return localeResolver;
	}
	
	public Properties kaptchaProperties() throws IOException {
		Properties prop = PropertyUtil.loadFromXMLByClassPath("kaptcha.xml");
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
