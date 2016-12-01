package com.maplebox.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class IndexFilter implements Filter {
	private static final String APP_PATH_REGEX = "^/app/v[\\d]+\\.[\\d]+\\.[\\d]+/";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			
			if (checkForwardIndex(httpRequest.getServletPath())) {
				request.getRequestDispatcher("/index").forward(request, response);
				return;
			}
			
			if (checkForwardApp(httpRequest.getServletPath())) {
				String newAppPath = getConvertAppPath(httpRequest.getServletPath());
				request.getRequestDispatcher(newAppPath).forward(request, response);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean checkForwardIndex(String path) {
		String[] exclude = new String[] {
			"/app/", "/resources/", "/plugins/", "/node_modules/",
			"/index", "/systemjs.config.js", "/favicon.ico"
		};
		
		for (String ex : exclude) {
			if (path.startsWith(ex)) {
				return false;
			}
		}
		
		return true;
	}

	private boolean checkForwardApp(String path) {
		Pattern pattern = Pattern.compile(APP_PATH_REGEX);
		
		Matcher matcher = pattern.matcher(path);
		
		if (matcher.find()) {
			return true;
		}
		
		return false;
	}
	
	private String getConvertAppPath(String path) {
		return path.replaceAll(APP_PATH_REGEX, "/app/");
	}
}
