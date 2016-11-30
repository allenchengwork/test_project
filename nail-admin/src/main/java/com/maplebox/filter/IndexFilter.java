package com.maplebox.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class IndexFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			if (checkURI(httpRequest)) {
				request.getRequestDispatcher("/index").forward(request, response);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean checkURI(HttpServletRequest request) {
		String path = request.getServletPath();
		String[] exclude = new String[] {
			"/app/", "/app_", "/resources/", "/node_modules/",
			"/index", "/systemjs.config.js", "/favicon.ico"
		};
		
		for (String ex : exclude) {
			if (path.startsWith(ex)) {
				return false;
			}
		}
		
		return true;
	}

}
