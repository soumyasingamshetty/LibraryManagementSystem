<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" import="java.io.*" import="java.lang.*"%>

<style>

.center{
 display:flex;
  justify-content: center;
}
table, th, td {
  border: 1px solid black;
}
</style>

<html>
	<head></head>
	<body style="background-color: aqua">

		<center><h1 style = "color: red">Service Unreachable.. Sorry for the inconvenience</h1></center><br>
		<h3>${exception }</h3>
		<h3>${exceptionmessage }</h3>

</body>
</html>