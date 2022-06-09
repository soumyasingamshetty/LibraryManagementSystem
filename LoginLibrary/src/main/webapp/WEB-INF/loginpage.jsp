<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page errorPage = "displayerrorpage.jsp" %>
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

<div>

	<div> <H1>Welcome to Our Library</H1></div>
	<br><br><br>
	
	
	
	<form action="/loginService/loginpage" method="post">
	<c:if test="${not empty successMessage }">
		<div style="color: red;"><h3>${successMessage}</h3></div>
	</c:if>
	<c:if test="${not empty statusFlagError }">
		<div style="color: red;"><h3>${statusFlagError}</h3></div>
	</c:if>
	<c:if test="${not empty loginPageError }">
		<div style="color: red;"><h3>${loginPageError}</h3></div>
	</c:if>
	<c:if test="${not empty loginerror }">
		<div style="color: red;"><h3>${loginerror}</h3></div>
	</c:if>
		<div>Email : <input type="text" name="userName" value="" required="required"></div>
		<br>
		<div>Password : <input type="password" name="password" value="" required="required"></div>
		<br>
		<div><input type="submit" value="Login" style="background: yellow">
		<input type="button" value="Register"  onclick="goToRegister()" style="background: yellow" >
		</div>
	
	</form>
</div>
</div>
</html>
<script type="text/javascript">
	function goToRegister(){
		alert("Welcome To our Registration Page")
		window.location.href="/loginService/displayregistrationform";
	}
</script>