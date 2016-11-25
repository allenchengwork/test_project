package com.maplebox.nail.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.base.Charsets;

import net.bull.javamelody.MonitoringFilter;
import net.bull.javamelody.SessionListener;
import net.sf.ehcache.constructs.web.ShutdownListener;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(ShutdownListener.class);
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(SessionListener.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        dispatcher.setAsyncSupported(true);
        dispatcher.setMultipartConfig(new MultipartConfigElement(null, 1024*1024, 1024*1024*5, 1024*1024*5*5));
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
        initParameters.put("disabled", "false");
        initParameters.put("quartz-default-listener-disabled", "true");
        initParameters.put("authorized-users", "root:12345");
        initParameters.put("monitoring-path", "/monitoring");
        initParameters.put("url-exclude-pattern", "(/images/.*|/js/.*|/styles/.*)");
        
        filterRegistration.setInitParameters(initParameters);
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.maplebox.nail.config");
        return context;
    }

}
