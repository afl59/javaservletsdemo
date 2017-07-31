package com.luo.webapp.quickstart;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

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
   If you use form, you can specify the method type for the request, check out the index.jsp file

   3. To retrieve the parameters from client through browser
   You can use query string to pass parameters through URL. http://localhost:8080/hello/hello?query=fei 
   You can use form input field to pass parameters too. You need specify the URL in the action attribute of the form. 
   You can use form method attribute for type of request, GET, POST, PUT, or DELETE.
   On the server side, you can use request.getparameter method to retrieve them. It always return as String value.

   com.lei.webapp.quickstart.HelloServlet
   */

public class MVCServlet extends HttpServlet {
	
	
	// Hanlde HTTP GET request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// MVC pattern (MODEL for data, VIEW for UI, CONTROLLER for business
		// logics)

		// This Servlet method is your CONTROLLER code
		// Get input from client through request object
		// Here is how to retrieve from URL query string, the part start with ?,
		// then name of the parameter=value of parameter
		// It can have multiple parameters such as ?p1=v1&p2=v2&p3=v3 (using &
		// to join them)
		String query = request.getParameter("query");

		// Validate input, such as preventing SQL injection

		// Call MODEL code (JDBC code) to get or update data

		// Store data in page or session context
		String appName = "Demo";
		request.setAttribute("appName", appName);

		// Forward to jsp to render the VIEW object (HTML page)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);

		// Or directly output HTML code here
		// PrintWriter out = response.getWriter();
		// out.println( "Hello World! from HelloServlet: " + query );
		// out.flush();
		// out.close();

	}

	// Hanlde HTTP POST request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Here is how to retrieve from form input field, the name of parameter
		// is the name attribute of the input field
		String name = request.getParameter("name");

		PrintWriter out = response.getWriter();
		out.println(name + " has been added.");
		out.flush();
		out.close();
	}

}
