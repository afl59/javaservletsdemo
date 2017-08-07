<html>
<head>
<link rel="stylesheet" href="css/my.css">
</head>

<body>
	<h2>Manage My Tasks</h2>

	<h3>Add New Task:</h3>
	<form action="/webapp-quick-start/task" method="post">
		Task:<input type="text" name="task-name" /> First Name:<input
			type="text" name="first-name"> Last Name: <input type="text"
			name="last-name" /> <input type="submit" onclick="update(event)"
			value="Add" />
	</form>

	<h3>Task List:</h3>
	<ul class="task-list"></ul>

	<!-- import jQuery library -->
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script>
		// Get task list from server async
		function get() {
			// Send async, ajax, request to server  
			// The call will immediately return without waiting for the response from server since it is async. 
			// Instead Browser will invoke getCallback function when received response later.
			$.ajax({
				type : "GET",
				url : "/webapp-quick-start/task",
				success : getCallback
			});
		}

		// Handle click event of add button
		function update(event) {
			event.preventDefault();
			// Send async, ajax, request to server  
			// The call will immediately return without waiting for the response from server since it is async. 
			// Instead Browser will invoke postCallback function when received response later.
			$.ajax({
				type : "POST",
				url : "/webapp-quick-start/task",
				success : postCallback,
				// IMPORTANT: Notice jQuery way to collect form input data and pass them to server
				data : $("form").serialize()
			});
		}

		// Browser invokes function after receiving response for async request above
		// Input parameter "data" here is the string value of the response, it can be other types depending on how jQuer.ajax call is configured above
		// In this case however, it will be JSON
		function getCallback(data) {
			// Use jQuery to parse JSON string value into JavaScript object for easy access
			var json = $.parseJSON(data);

			// Empty old data from HTML
			$(".task-list").html("");

			// Add tasks to the list
			for (var i = 0; i < json.length; i++) {
				$(".task-list").append(
						"<li><a onclick='del(this)' href='#' id='" + json[i].ID
								+ "'>Delete</a>" + "ID: " + json[i].ID
								+ " ; TASKNAME: " + json[i].TASKNAME
								+ "; OWNER: " + json[i].LASTNAME + ", "
								+ json[i].FIRSTNAME + "</li>");
			}
		}

		// Browser invokes function after receiving response for async request above
		// Input parameter "data" here is the string value of the response, it can be other types depending on how jQuer.ajax call is configured above
		function postCallback(data) {
			// Update the list as if you were simply getting the table
			getCallback(data);
		}

		// Delete implementation
		function del(data) {
			// Fetch id of task to delete through url
			var url = "/webapp-quick-start/task?id=" + data.id;
			alert(url);
			$.ajax({
				type : "DELETE",
				url : url,
				success : delCallback
			});
			event.preventDefault();
		}

		function delCallback(data) {
			getCallback(data);
		}

		// IMPORTANT: load initial task list
		get();
	</script>
</body>
</html>
