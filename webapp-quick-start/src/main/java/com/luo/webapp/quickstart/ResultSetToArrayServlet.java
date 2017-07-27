package com.luo.webapp.quickstart;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ResultSetToArrayServlet extends HttpServlet {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	static final String DB_URL = "jdbc:derby://localhost:1527/seconddb;create=true";

	// Database credentials
	static final String USER = null;
	static final String PASS = null;

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
			for (int i = 0; i < numOfColumns; i++) {
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