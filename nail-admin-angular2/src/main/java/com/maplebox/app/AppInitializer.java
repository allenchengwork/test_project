package com.maplebox.app;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.base.Charsets;
import com.maplebox.filter.AppFilter;
import com.maplebox.util.PropertyUtil;

public class AppInitializer implements WebApplicationInitializer {
	private static final String APP_MODE = "app.mode";
	private static final String APP_DEVELOPER = "app.developer";
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	Properties appConfig = configProperties();
    	
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(new RequestContextListener());
        
        servletContext.setAttribute("isLog4jAutoInitializationDisabled", false);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        dispatcher.setAsyncSupported(true);
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
        
        AppFilter indexFilter = new AppFilter();
        filterRegistration = servletContext.addFilter("indexFilter", indexFilter);
        filterRegistration.addMappingForServletNames(null, true, "dispatcherServlet");
        filterRegistration.setInitParameter("exclude", appConfig.getProperty("url.exclude"));
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.maplebox.config");
        return context;
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
