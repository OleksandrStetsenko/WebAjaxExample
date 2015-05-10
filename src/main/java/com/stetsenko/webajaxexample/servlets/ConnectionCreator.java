package com.stetsenko.webajaxexample.servlets;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ConnectionCreator implements ServletRequestListener {

	public static final String CONNECTION = ConnectionCreator.class.getName()+ ".connection";

	public static Connection getConnection(ServletRequest req) {
	    return (Connection) req.getAttribute(CONNECTION);
	}
	
	@Resource(lookup = "java:comp/env/dataSource")
	private DataSource dataSource;
	
	public void requestDestroyed(ServletRequestEvent req) {
		Connection conn = getConnection(req.getServletRequest());
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				req.getServletContext().log("Failed to close connection", e);
			}
		}
	}

	public void requestInitialized(ServletRequestEvent req) {
		try {
//		    DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/dataSource");
		    req.getServletRequest().setAttribute(CONNECTION, dataSource.getConnection());
		    req.getServletContext().log("Request connection initialized");
		}
		catch (Exception e) {
			req.getServletContext().log("Failed to create connection", e);
		}
	}

}
