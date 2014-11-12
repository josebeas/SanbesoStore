<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<style>
			.error {
				color: #ff0000;
			}
			.errorblock{
				color: #000;
				background-color: #ffEEEE;
				border: 3px solid #ff0000;
				padding:8px;
				margin:16px;
			}

		</style>
		<!-- Bootstrap -->
		<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<h2>Spring's form tags example</h2>
		<table>
			<tr>
				<td>UserName :</td><td>${customer.userName}</td>
			</tr>
			<tr>
				<td>Address :</td><td>${customer.address}</td>
			</tr>
			<tr>
				<td>Password :</td><td>${customer.password}</td>
			</tr>
			<tr>
				<td>Confirm Password :</td><td>${customer.confirmPassword}</td>
			</tr>
			<tr>
				<td>Receive Newsletter :</td><td>${customer.receiveNewsletter}</td>
			</tr>
			<tr>
				<td>Favourite Web Frameworks :</td>
				<td>
				<c:forEach items="${customer.favFramework}" var="current">
				   [<c:out value="${current}" />]
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td>Sex :</td><td>${customer.sex}</td>
			</tr>
			<tr>
				<td>Favourite Number :</td><td>${customer.favNumber}</td>
			</tr>
			<tr>
				<td>Country :</td><td>${customer.country}</td>
			</tr>
			<tr>
				<td>Java Skills :</td><td>${customer.javaSkills}</td>
			</tr>
			<tr>
				<td>Hidden Value :</td><td>${customer.secretValue}</td>
			</tr>
		</table>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
	    <script src="resources/js/bootstrap.js" type="text/javascript"></script>
	</body>
</html>