package com.stetsenko.webajaxexample.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*")
public class AuthFilter implements Filter {

	public static final String REDIRECTED_FROM = "redirected-from";
	public static final String CURRENT_USER = "currentUser";
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		logger.debug("Filtering: {}", request.getRequestURI());

		if ("/login".equals(request.getServletPath())) {
			chain.doFilter(request, response);
		}
		else {
			Object user = request.getSession().getAttribute(CURRENT_USER);
			if (user == null) {
				// remember where from we was redirected to return back after login
				request.getSession().setAttribute(REDIRECTED_FROM, request.getRequestURI());
				response.sendRedirect(request.getContextPath()+ "/login");
			}
			else {
				chain.doFilter(req, res);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
