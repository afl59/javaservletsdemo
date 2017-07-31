package com.luo.webapp.quickstart;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class ResultSetToArrayServlet extends HttpServlet {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	static final String DB_URL = "jdbc:derby://localhost:1527/seconddb;create=true";

	// Database credentials
	static final String USER = null;
	static final String PASS = null;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html><html><body><p>");
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.print("JDBC_DRIVER registered...");
			
			// Open connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// Execute query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM task";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Conversions
			ArrayList<Hashtable<String, Object>> list = toList(rs);
			Gson gson = new Gson();
			out.print(gson.toJson(list) + "</p></body></html>");
			
			// Clean-up
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// Close resources
			System.out.println("Closing resources...");
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
		out.println("</ul></body></html>");
		out.flush();
		out.close();
	}

	// Method to convert JDBC ResultSet to ArrayList of Hashtable
	public static ArrayList<Hashtable<String, Object>> toList(ResultSet rs) throws Exception {

		// Create ArrayList object to store data
		ArrayList<Hashtable<String, Object>> toReturn = new ArrayList<Hashtable<String, Object>>();

		// Get meta data about ResultSet
		ResultSetMetaData metaData = rs.getMetaData();

		// Get # of columns of ResultSet from MetaData
		int numOfColumns = metaData.getColumnCount();

		// Loop through each row to load data into ArrayList
		while (rs.next()) {

			// Create a Hashtable to store one row,
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			// Loop through each column of the row,
			for (int i = 1; i <= numOfColumns; i++) {
				// Put one <name, value> pair into Hashtable for each column
				// Name is column label, value is from ResultSet
				table.put(metaData.getColumnLabel(i), rs.getObject(i));
			}

			// Add Hashtable object into ArrayList
			toReturn.add(table);
		}

		// Return ArrayList
		return toReturn;
	}

}