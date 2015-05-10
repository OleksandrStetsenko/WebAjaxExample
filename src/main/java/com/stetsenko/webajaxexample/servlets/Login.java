package com.stetsenko.webajaxexample.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login servlet is configured through web.xml
 */
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String login = req.getParameter("login");
		String pass  = req.getParameter("password");
		
		String error = null;

        //get from web.xml
        //it is not good to save pass, but for example...
		String password = getServletConfig().getInitParameter(login);
		if (password == null) {
			error = "Unknown login";
		}
		else if (! password.equals(pass)) {
			error = "Wrong password";
		}
		
		if (error == null) {
			String redirect = (String) req.getSession().getAttribute(AuthFilter.REDIRECTED_FROM);
			if (redirect == null) {
                redirect = req.getContextPath();
            }
			req.getSession().setAttribute(AuthFilter.CURRENT_USER, login);

            if ( ! "".equals(redirect) ) {
                redirect = "messages.jsp";
            }

            resp.sendRedirect(redirect);
		}
		else {
			req.setAttribute("message", error);
			doGet(req, resp);
		}
	}
	
}
