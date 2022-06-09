<style>

form{
	background-color: lightpink;
	width: 400px;
	height: 400px;
	border-style: solid;
	border-color: purple;
	padding: 10px;
	
}
.center{
 display:flex;
  justify-content: center;
}
</style>

<html>
	<head></head>
	<body style="background-color: aqua">
		<center><h1>Welcome Admin ${currentuser} </h1></center>
	</body>
	
	

<div class=center>
<div>
	<form action="/loginService/addbook" method="post" name="addbookform">
	<c:if test="${not empty addbookissue }">
		<div style="color: red;"><h3>${addbookissue}</h3></div>
	</c:if>
	<c:if test="${not empty addedbook }">
		<div style="color: red;"><h3>${addedbook}</h3></div>
	</c:if>
	<c:if test="${not empty exceptionmessage }">
		<div style="color: red;"><h3>${exceptionmessage}</h3></div>
	</c:if>
		<div>Book Name : <input type="text" name="bookName" value="" required="required"></div>
		<br>
		<div>ISBN : <input type="text" name="isbn" value="" required="required"></div>
		<br>
		<div>Author : <input type="text" name="author" value="" required="required"></div>
		<br>
		<div>quantity : <input type="number" name="quantity" value="" required="required"></div>
		 <span id="errorname"></span>
		<br>
		<div>Subject : <input type="text" name="subject" value="" ></div>
		<br>
		<div><input type="submit" value="Add Book"  style="background: yellow">
		<input type="button" value="View Books" onclick="viewBooks()" style="background: yellow" >
		<input type="button" value="View Borrowed Books" onclick="allBorrowedBooks()" style="background: yellow" >
		<input type="button" value="Edit Books" onclick="editBooks()" style="background: yellow" >
		</div>
	</form>
</div>
</div>
</html>
<script type="text/javascript">
	function viewBooks(){
		window.location.href="/loginService/listbooks";
	}
	function allBorrowedBooks()
	{
		window.location.href="/loginService/userBorrowedBooks"
	}
	function editBooks(){
		window.location.href="/loginService/adminEditBook";
	}


</script>
