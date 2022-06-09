<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

form{
	background-color: lightpink;
	width: 300px;
	height: 300px;
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
<div class=center>
<div></div>
<div></div>
<div></div>
<form action="/loginService/updateBook" method="post">

<c:if test="${not empty Updated}">
		<div style="color: green;"><h3>${Updated}</h3></div>
	</c:if>
	
	<div>ISBN: <input type="text" name="ISBN"  required=required ></div>
	<br>
	<div>Quantity : <input type="number" name="quantity" required=required></div>
	<br>
	<div><input type="Submit" value="Save" style="background-color:green" ></div>
	<br>
	
</form>
</div>

</html>

