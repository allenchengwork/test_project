package com.maplebox.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AppFilter implements Filter {
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
				request.getRequestDispatcher("/index.html").forward(request, response);
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
			"/index", "/favicon.ico",
			"/app/", "/assets/", "/plugins/",
			"/inline.", "/main.", "/scripts.", "/styles.", "/vendor."
		};
		
		for (String ex : exclude) {
			if (path.startsWith(ex)) {
				return false;
			}
		}
		
		return true;
	}
}
