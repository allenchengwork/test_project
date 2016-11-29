package com.maplexbox.filter;

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
		String uri = request.getRequestURI();
		System.out.println("uri = "+uri);
		String[] exclude = new String[] {
			"/app/", "/app_", "/resources/", "/node_modules/"	
		};
		
		for (String ex : exclude) {
			if (uri.startsWith(ex)) {
				return false;
			}
		}
		
		if (uri.startsWith("/index")) {
			return false;
		}
		
		if (uri.startsWith("/systemjs.config.js")) {
			return false;
		}
		
		return true;
	}

}
