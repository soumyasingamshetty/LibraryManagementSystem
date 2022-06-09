<html>
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
		<center><h1>Books Usage </h1></center>
		<c:if test="${not empty BorrowedSuccessMessage }">
		<div style="color: red;"><h3>${BorrowedSuccessMessage}</h3></div>
	</c:if>
	<c:if test="${not empty noBorrowedBooksError }">
		<div style="color: red;"><h3>${noBorrowedBooksError}</h3></div>
	</c:if>
	
<div class=center>
	<table style="width:100%">	
	<tr>
	<th>Book Id</th>
	<th>Borrower Id</th>
	<th>Borrower Name</th>
	<th>TransactionId</th>
	<th>Return Items</th>
	<c:forEach var="book" items="${borrowedbooks}">
		<div>
			<tr>
			<td>${book.bookId}</td>
			<td>${book.borrowerId}</td>
			<td>${book.borrower}</td>
			<td>${book.transactionID}</td>
			<td><a href="/loginService/returnBook/${book.borrowId}">return</a></td>
			</tr>
		</div>
	</c:forEach>
	</table>
</div>

</body>
</html>
</html>