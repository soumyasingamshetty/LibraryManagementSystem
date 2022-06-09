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
		<center><h1>Books Available</h1></center>
<c:if test="${not empty votestatus}">
	<div style="color : red "><h3>${votestatus}</h3></div>
</c:if>
<div class=center>
<div>
	<table style="width:100%">	
	<tr>
	<th>Book Id</th>
	<th>Book Name</th>
	<th>ISBN</th>
	<th>Author</th>
	<th>Renew</th>
	<th>Vote</th></tr>
	<c:forEach var="book" items="${userRequestedBookList}">
		<div>
			<tr>
			<td>${book.bookId}</td>
			<td>${book.bookName}</td>
			<td>${book.isbn}</td>
			<td>${book.author}</td>
			<td><a href="<c:url value="/loginService/borrow/${book.bookId}" />">Borrow</a></td>
			<td><a href="<c:url value="/loginService/vote/${book.bookId}" />">Vote for popularity</a></td>
			</tr>
		</div>
	</c:forEach>
	</table>
	</div>
</div>

</body>
</html>