package com.luo.webapp.quickstart;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Date;
/* 1. Be careful with URL, the url you type in the browser window should be http://localhost:8080/hello/hello
   1st hello is the name your web application (war file name)
   2nd hello is the path you specified in the web.xml to point to this servlet class 
   If you only have the 1st hello without any additional path, be default, it will take index.jsp or index.html under your webapp directory
   To add any other files such as CSS or JavaScript files into your project, simply place them inside webbapp folder or the subfolders.
   You can use /my.css, or /myfolder/my.css in your HTML file to acces them.
   Make sure you update index.jsp file to include CSS file, using 
  <head>
    <link rel="stylesheet" href="css/my.css">
  </head>

   2. Note the method name, one is to handle GET request, another is  for POST request
   If you simply type url in browser address bar and hit enter, it will send GET request
   If you use form, you can specify the method type for the request, check out the index.jsp fil  
 
   3. To retrieve the parameters from client through browser
   You can use query string to pass parameters through URL. http://localhost:8080/hello/hello?query=fei 
   You can use form input field to pass parameters too. You need specify the URL in the action attribute of the form. 
   You can use form method attribute for type of request, GET, POST, PUT, or DELETE.
   On the server side, you can use request.getparameter method to retrieve them. It always return as String value.

   com.lei.webapp.quickstart.HelloServlet
*/
public class SimpleServlet extends HttpServlet {
	
	// Set JDBC variables
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	static final String DB_URL = "jdbc:derby://localhost:1527/seconddb;create=true";

	// Set database credentials
	static final String USER = null;
	static final String PASS = null;

	// Handle HTTP GET request
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Important for making sure the returned page is of HTML type
		response.setContentType("text/html");
		
		// Retrieve query parameter
		String query = request.getParameter("query");

		PrintWriter out = response.getWriter();
		
		// DEBUG
		System.out.println("Hello World! from HelloServlet: " + query);

		// Establish HTML document for printing
		out.println("<!DOCTYPE html><html><body><ul>");
		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("JDBC_DRIVER registered");

			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, name FROM task";
			ResultSet rs = stmt.executeQuery(sql);

			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String name = rs.getString("name");

				// Display values
				out.print("<li>");
				out.print("ID: " + id);
				out.print(", Name: " + name);
				out.print("</li>");
			}
			// Clean-up environment
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
			}
		}
		System.out.println("Goodbye!");
		out.println("</ul></body></html>");
		out.flush();
		out.close();
	}

	// Handle HTTP POST request
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		int taskId; // For use later
		// Retrieving user values from page
		String userTaskName = request.getParameter("Task");
		String userFirstName = request.getParameter("First Name");
		String userLastName = request.getParameter("Last Name");

		PrintWriter out = response.getWriter();
		
		// Open HTML
		out.println("<!DOCTYPE html><html><body><ul>");

		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("JDBC_DRIVER registered...");

			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
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
			
			// For display
			sql = "SELECT * FROM task";
			rs = stmt.executeQuery(sql);

			System.out.println("SELECT successful...");

			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int fetchedId = rs.getInt("id");
				String fetchedTaskName = rs.getString("taskName");
				String fetchedFirstName = rs.getString("firstName");
				String fetchedLastName = rs.getString("lastName");

				// Display values
				out.print("<li>");
				out.print("ID: " + fetchedId);
				out.print("; Task: " + fetchedTaskName);
				out.print("; Name: " + fetchedLastName);
				out.print(", " + fetchedFirstName);
				out.print("</li>");
			}
			// Clean-up environment
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
			}
		}
		
		// Close html
		out.println("</ul></body></html>");
		out.flush();
		out.close();
	}

}