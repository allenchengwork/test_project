package com.maplebox.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Splitter;

public class AppFilter implements Filter {
	private List<String> listExclude;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String exclude = filterConfig.getInitParameter("exclude");
		listExclude = Splitter.on(',')
	    		.trimResults()
	    		.omitEmptyStrings()
	    		.splitToList(exclude);
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
		for (String ex : listExclude) {
			if (path.startsWith(ex)) {
				return false;
			}
		}
		
		return true;
	}
}
