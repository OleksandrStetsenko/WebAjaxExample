package com.stetsenko.webajaxexample.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

@WebServlet("/messages")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
    	    Connection conn = ConnectionCreator.getConnection(request);
	        ResultSet res = conn.createStatement().executeQuery("select * from messages order by posted");
	        List<Map<String, String>> rows = new ArrayList<Map<String,String>>();
	        List<String> columns = new ArrayList<String>();
	        try {
		        ResultSetMetaData md = res.getMetaData();
	            for (int i = 1; i <= md.getColumnCount(); i++)
	                columns.add(md.getColumnLabel(i));
	            while (res.next()) {
	                Map<String, String> row = new HashMap<String, String>();
	                for (int i = 1; i <= md.getColumnCount(); i++)
	                    row.put(md.getColumnLabel(i).toLowerCase(), res.getString(i));
	                rows.add(row);
	            }
	        }
	        finally {
	        	res.close();
	        }
	        response.setContentType("text/json");
	        PrintWriter out = response.getWriter();
	        out.print("[");
	        int messages = 0;
	        for (Map<String, String> message: rows) {
	        	if (messages++ > 0)
	        		out.print(", ");
	        	out.print("{");
	        	int fields = 0;
	        	for (String key: message.keySet()) {
		        	if (fields++ > 0)
		        		out.print(", ");
	        		out.printf("\"%s\": \"%s\"", key, message.get(key));
	        	}
	        	out.print("}");
	        }
	        out.print("]");
	    }
	    catch (Exception e) {
	        throw new ServletException(e);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
    	    Connection conn = ConnectionCreator.getConnection(request);
	        PreparedStatement ps = conn.prepareStatement("insert into messages values (?, sysdate, ?)");
	        try {
	        	ps.setString(1, request.getParameter("username"));
	        	ps.setString(2, request.getParameter("message"));
	        	ps.execute();
	        }
	        finally {
	        	ps.close();
	        }
	    }
	    catch (Exception e) {
	        throw new ServletException(e);
	    }
	}
}
