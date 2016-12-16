package com.maplebox.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.base.Charsets;
import com.maplebox.encrypt.EncryptPropertyPlaceholderConfigurer;
import com.maplebox.util.PropertyUtil;

import net.bull.javamelody.MonitoringFilter;
import net.bull.javamelody.SessionListener;
import net.sf.ehcache.constructs.web.ShutdownListener;

public class AppInitializer implements WebApplicationInitializer {
	private static final String APP_MODE = "app.mode";
	private static final String APP_DEVELOPER = "app.developer";
	
	private static final String KEY = "Maplebox123Key45";
	private static final String IV = "RandomInitVector";
	
	private static EncryptPropertyPlaceholderConfigurer placeholderConfigurer;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	Properties appConfig = configProperties();
    	
        WebApplicationContext context = getContext();
        servletContext.addListener(ShutdownListener.class);
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(SessionListener.class);
        servletContext.addListener(Log4jServletContextListener.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        dispatcher.setAsyncSupported(true);
        //dispatcher.setMultipartConfig(new MultipartConfigElement(null, 1024*1024, 1024*1024*5, 1024*1024*5*5));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(Charsets.UTF_8.name());
        characterEncodingFilter.setForceEncoding(true);
        
        FilterRegistration.Dynamic filterRegistration = null;
        filterRegistration = servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");
        
        ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
        filterRegistration = servletContext.addFilter("shallowEtagHeaderFilter", shallowEtagHeaderFilter);
        filterRegistration.addMappingForServletNames(null, true, "dispatcherServlet");

        MonitoringFilter monitoringFilter = new MonitoringFilter();
        filterRegistration = servletContext.addFilter("javamelody", monitoringFilter);
        if (filterRegistration == null) {
        	filterRegistration = (Dynamic) servletContext.getFilterRegistration("javamelody");
        }
        filterRegistration.setAsyncSupported(true);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("disabled", appConfig.getProperty("javamelody.disabled"));
        initParameters.put("quartz-default-listener-disabled", "true");
        initParameters.put("authorized-users", appConfig.getProperty("javamelody.authorized-users"));
        initParameters.put("monitoring-path", appConfig.getProperty("javamelody.monitoring-path"));
        initParameters.put("url-exclude-pattern", "(/images/.*|/js/.*|/styles/.*)");
        
        filterRegistration.setInitParameters(initParameters);
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.maplebox.config");
        return context;
    }
    
    public static synchronized PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
    	if (placeholderConfigurer == null) {
	    	Properties prop = AppInitializer.configProperties();
	    	String encryptPropNames = prop.getProperty("prop.encrypt", "");
	    	
			placeholderConfigurer = new EncryptPropertyPlaceholderConfigurer(KEY, IV);
			placeholderConfigurer.setEncryptPropNames(encryptPropNames);
			placeholderConfigurer.setLocation(new ClassPathResource(getXmlConfigPath()));
    	}
		
		return placeholderConfigurer;
    }

    public static Properties configProperties() {
    	String configPath = getXmlConfigPath();
		Properties prop = null;
		try {
			prop = PropertyUtil.loadFromXMLByClassPath(configPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return prop;
	}
    
    public static String getXmlConfigPath() {
    	String mode = System.getProperty(APP_MODE, "dev");
    	String developer = System.getProperty(APP_DEVELOPER, "");
    	return "config/web-config-"+mode+developer+".xml";
    }
}
