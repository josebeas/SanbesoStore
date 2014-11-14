<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Bootstrap Form With Spring Mvc Example</title>
	<link type="text/css" href="resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<fieldset>
				<legend>Employee Details</legend>
				    <table class="table table-striped">
					    <tr>
					    	<td>First Name</td>
					    	<td>Last Name</td>
					    	<td>Email</td>
					    </tr>
					    <tr>
				    	<td>${employee.firstName}</td>
				    	<td>${employee.lastName}</td>
				    	<td>${employee.email}</td>
				    </tr>    		
    				</table>
				</fieldset>
			</div>
		</div>
	</div>	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="resources/js/bootstrap.js" type="text/javascript"></script>	
</body>
</html>