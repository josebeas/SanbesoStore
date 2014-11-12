<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<legend>Spring's form tags example</legend>
		<form:form method="POST" commandName="customer" class="form-horizontal">
			<form:errors path="*" cssClass="errorblock" element="div"/>
			<table>
				<tr>
					<td>UserName : </td>
					<td><form:input path="userName" /></td>
					<td><form:errors path="userName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Address : </td>
					<td><form:textarea path="address" /></td>
					<td><form:errors path="address" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Password : </td>
					<td><form:password path="password" /></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Confirm Password : </td>
					<td><form:password path="confirmPassword" /></td>
					<td><form:errors path="confirmPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Subscribe to newsletter? : </td>
					<td><form:checkbox class="checkbox" path="receiveNewsletter" /></td>
					<td><form:errors path="receiveNewsletter" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Favourite Web Frameworks : </td>
					<td><form:checkboxes items="${webFrameworkList}" path="favFramework" /></td>
					<td><form:errors path="favFramework" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Sex : </td>
					<td>
						<form:radiobutton path="sex" value="M"/>Male 
						<form:radiobutton path="sex" value="F"/>Female 
					</td>
					<td><form:errors path="sex" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Choose a number : </td>
					<td><form:radiobuttons path="favNumber" items="${numberList}"  /></td>
					<td><form:errors path="favNumber" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Country : </td>
					<td>
						<form:select path="country">
							<form:option value="NONE" label="--- Select ---"/>
							<form:options items="${countryList}" />
						</form:select>
					</td>
					<td><form:errors path="country" cssClass="error" /></td>
				</tr>	 
				<tr>
					<td>Java Skills : </td>
					<td><form:select path="javaSkills" items="${javaSkillsList}" multiple="true" /></td>
					<td><form:errors path="javaSkills" cssClass="error" /></td>
				</tr>
				<form:hidden path="secretValue" />
				<tr>
					<td colspan="3"><input class="btn btn-primary" type="submit" /></td>
				</tr>
			</table>
		</form:form>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
	    <script src="resources/js/bootstrap.js" type="text/javascript"></script>
	</body>
</html>