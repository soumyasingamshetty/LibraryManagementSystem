<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
		<center><h1>All Books in Store </h1></center>
	<c:if test="${not empty bookerror }">
		<div style="color: red;"><h3>${bookerror}</h3></div>
	</c:if>
	<c:if test="${not empty deleted }">
		<div style="color: red;"><h3>${deleted}</h3></div>
	</c:if>
	<c:if test="${not empty errormessage}">
		<div style="color: red"><h3> ${errormessage} </h3></div>
	</c:if>
<div class=center>
<div>
	<table style="width:100%">	
	<tr>
	<th>Book Id</th>
	<th>Book Name</th>
	<th>ISBN</th>
	<th>Author</th>
	<th>Quantity</th>
	<th>Edit</th>
	<th>Delete</th></tr>
	<c:forEach var="book" items="${books}">
		<div>
			<tr>
			<td>${book.bookId}</td>
			<td>${book.bookName}</td>
			<td>${book.isbn}</td>
			<td>${book.author}</td>
			<td>${book.quantity}</td>
			<td><a href="<c:url value="/loginService/editBookbyisbn/${book.bookId}/${book.isbn}/${book.quantity}" />">Edit</a></td>
			<td><a href="/loginService/deletebook/${book.bookId}">Delete</a></td>
	
			</tr>
		</div>
	</c:forEach>
	</table>
	</div>
</div>

</body>
</html>