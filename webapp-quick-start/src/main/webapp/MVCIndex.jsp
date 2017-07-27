<html>
<head>
<link rel="stylesheet" href="css/my.css">
</head>

<body>
	<h2>Hello World!</h2>
	<form action="/webapp-quick-start/hello" method="post">
		<input type="text" name="name" /> <input type="submit" />
	</form>

	<!--  There are a few implicit objects such as request, response, session you can use to retrieve data passed from Servlet -->
	<p>
		Query String:
		<%=request.getParameter("query")%></p>

	<!--  Use java data passed by Servlet using request.setAttribute  -->
	<!--  This is the recommended way  -->
	<p><%=request.getAttribute("appName")%></p>

	<!--  Loop -->
	<ul>
		<%
			for (int i = 0; i < 3; i++) {
		%>
		<li><%=i%></li>
		<%
			}
		%>
	</ul>

	<!--  You can use data in Java object like this -->
	<p>
		Today's date:
		<%=(new java.util.Date()).toLocaleString()%>
	</p>

	<!--  You can write normal Java code like this, but not recommended  -->
	<%
		out.println("<p>Your IP address is " + request.getRemoteAddr() + "</p>");
	%>

</body>
</html>
