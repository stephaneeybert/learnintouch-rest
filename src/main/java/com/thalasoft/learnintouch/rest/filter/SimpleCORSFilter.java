package com.thalasoft.learnintouch.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

	private static final String ORIGIN = "Origin";
	private static final String OPTIONS = "OPTIONS";
	private static final String OK = "OK";
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		if (httpServletRequest.getHeader(ORIGIN) != null) {
			String origin = httpServletRequest.getHeader(ORIGIN);
			httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
			httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
			httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
			httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
		}
		
		if (httpServletRequest.getMethod().equals(OPTIONS)) {
			try {
				httpServletResponse.getWriter().print(OK);
				httpServletResponse.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}		
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
	
}
