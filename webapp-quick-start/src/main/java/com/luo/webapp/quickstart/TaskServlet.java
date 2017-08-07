package com.luo.webapp.quickstart;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

// 1. Be careful with URL, the url you type in the browser window should be http://localhost:8080/hello/hello
// 1st hello is the name your web application (war file name)
// 2nd hello is the path you specified in the web.xml to point to this servlet class 
// If you only have the 1st hello without any additional path, be default, it will take index.jsp or index.html under your webapp directory
// To add any other files such as CSS or JavaScript files into your project, simply place them inside webbapp folder or the subfolders.
// You can use /my.css, or /myfolder/my.css in your HTML file to acces them.
// Make sure you update index.jsp file to include CSS file, using 
//<head>
//  <link rel="stylesheet" href="css/my.css">
//</head>

// 2. Note the method name, one is to handle GET request, another is  for POST request
// If you simply type url in browser address bar and hit enter, it will send GET request
// If you use form, you can specify the method type for the request, check out the index.jsp file

// 3. To retrieve the parameters from client through browser
// You can use query string to pass parameters through URL. http://localhost:8080/hello/hello?query=fei 
// You can use form input field to pass parameters too. You need specify the URL in the action attribute of the form. 
// You can use form method attribute for type of request, GET, POST, PUT, or DELETE.
// On the server side, you can use request.getparameter method to retrieve them. It always return as String value.

// com.lei.webapp.quickstart.HelloServlet
public class TaskServlet extends HttpServlet {

	// Set JDBC variables
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	static final String DB_URL = "jdbc:derby://localhost:1527/seconddb;create=true";

	// Set JDBC credentials
	static final String USER = null;
	static final String PASS = null;

	// Handles HTTP Get Request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		// MVC pattern (MODEL for data, VIEW for UI, CONTROLLER for business
		// logics)

		// This Servlet method is your CONTROLLER code
		// Get input from client through request object
		// Here is how to retrieve from URL query string, the part start with ?,
		// then name of the parameter=value of parameter
		// It can have multiple parameters such as ?p1=v1&p2=v2&p3=v3 (using &
		// to join them)

		Connection conn = null;
		Statement stmt = null;

		try {

			// Register the JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("JDBC_DRIVER registered...");

			// Open a connection to the database
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected to database...");

			// Retrieve data from database and update the data
			stmt = conn.createStatement();
			ResultSet tempData = stmt.executeQuery("SELECT * FROM task");
			System.out.println("SELECT successful...");

			// Convert the ResultSet to a Java object
			ArrayList<Hashtable<String, Object>> data = toList(tempData);

			// Convert Java Object to JSON string
			// Create Google Gson object
			Gson gson = new Gson();
			String json = gson.toJson(data);

			// Send JSON string back to browser, jsp will handle HTML code
			out.println(json);
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
			}
		}
		System.out.println("Goodbye!");
		out.flush();
		out.close();
	}

	// Handle HTTP POST request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		int taskId;

		// Retrieve information from form input
		String userTaskName = request.getParameter("task-name");
		String userFirstName = request.getParameter("first-name");
		String userLastName = request.getParameter("last-name");

		Connection conn = null;
		Statement stmt = null;

		// Insert values into database
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("JDBC_DRIVER registered...");

			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Set up statement
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			ResultSet rs;

			// Generate a random ID based on time
			taskId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
			System.out.println("ID set...");

			// Prepare the INSERT query
			sql = "INSERT INTO task (id, taskName, firstName, lastName) values (" + taskId + ", '" + userTaskName
					+ "', '" + userFirstName + "', '" + userLastName + "')";

			// Update table
			stmt.executeUpdate(sql);
			System.out.println("INSERT successful...");

			// Retrieve data
			ResultSet tempData = stmt.executeQuery("SELECT * FROM task");

			// Convert ResultSet
			ArrayList<Hashtable<String, Object>> data = toList(tempData);

			// Convert Java Object to JSON string
			Gson gson = new Gson();
			String json = gson.toJson(data);

			// Send JSON back to jsp, it will handle HTML
			out.println(json);
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
			}
		}
		System.out.println("Goodbye!");
		out.flush();
		out.close();
	}

	// Handle HTTP delete request
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// For JSON printing
		PrintWriter out = response.getWriter();

		// Get ID of task to delete
		String id = "";
		id = request.getParameter("id");
		System.out.println("Selected ID: " + id + " to delete");

		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("JDBC_DRIVER registered...");

			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Create statement
			stmt = conn.createStatement();
			String sql = "DELETE FROM task WHERE id=" + id;

			// Execute update
			stmt.executeUpdate(sql);
			System.out.println("DELETE successful...");

			// Retrieve data
			ResultSet tempData = stmt.executeQuery("SELECT * FROM task");

			// Convert ResultSet
			ArrayList<Hashtable<String, Object>> data = toList(tempData);

			// Convert Java Object to JSON string
			// Create Google Gson object and convert to JSON
			Gson gson = new Gson();
			String json = gson.toJson(data);

			// Print json out, jsp will handle HTML
			out.println(json);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
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
			}
		}
		out.flush();
		out.close();
	}

	// This method is used to convert a ResultSet into an ArrayList so it can be
	// then converted into JSON
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
