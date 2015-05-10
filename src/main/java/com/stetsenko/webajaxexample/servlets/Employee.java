package com.stetsenko.webajaxexample.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/emp")
public class Employee extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(Employee.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
    	    Connection conn = ConnectionCreator.getConnection(request);
	        ResultSet res = conn.createStatement().executeQuery("select * from emp");
	        List<Map<String, String>> rows = new ArrayList<Map<String,String>>();
	        List<String> columns = new ArrayList<String>();
	        try {
		        ResultSetMetaData md = res.getMetaData();
	            for (int i = 1; i <= md.getColumnCount(); i++)
	                columns.add(md.getColumnLabel(i));
	            while (res.next()) {
	                Map<String, String> row = new HashMap<String, String>();
	                for (int i = 1; i <= md.getColumnCount(); i++)
	                    row.put(md.getColumnLabel(i), res.getString(i));
	                rows.add(row);
	            }
	        }
	        finally {
                logger.debug("Connection was closed.");
	        	res.close();
	        }
	        request.setAttribute("title", "Employee");
	        request.setAttribute("columns", columns);
	        request.setAttribute("rows", rows);
	        getServletContext().getRequestDispatcher("/WEB-INF/table.jsp").forward(request, response);
	    }
	    catch (Exception e) {
	        throw new ServletException(e);
	    }
	}

}
