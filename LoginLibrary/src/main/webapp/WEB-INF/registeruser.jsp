
<style>

form{
	background-color: lightpink;
	width: 500px;
	height: 500px;
	border-style: solid;
	border-color: purple;
	padding: 10px;
	
}
.center{
 display:flex;
  justify-content: center;
}
</style>

<div class=center>
<div>

	<div> <H1>Welcome to Our Library</H1></div>
	<div> <H1>Registration Form</H1></div>
	<br><br><br>
	<form action="/loginService/registeruser" method="post">
		<div>Firstname : <input type="text" name="firstname" value="" required="required"></div>
		<br>
		<div>Lastname : <input type="text" name="lastname" value="" required="required"></div>
		<br>
		<div>Email : <input type="text" name="email" value="" required="required"></div>
		<br>
		<div>Gender : <input type="radio" name="gender" value="M" required="required"> Male
		 <input type="radio" name="gender" value="F" required="required"> Female </div>
		<br>
		<div>User: <select name="usertype" id="utype">
    		<option value="">--Please choose an option--</option>
    		<option value="libraryuser" selected>Library User</option>
    		<option value="admin">Admin</option>
		</select></div>	<br>
		<div>Admin id: <input type="text" name="adminid" value=""></div><br>
		<div>Password : <input type="password" name="password" value="" required="required"></div>
		<br>
		<div>Confirm Password : <input type="password" name="confirmpassword" value="" required="required"></div>
		<br>
		<div><input type="submit" value="Register" style="background: yellow">
		
		<c:if test="${not empty responseString }">
		<div style="color: green;"><h3>${responseString}</h3></div>
	    </c:if>
	    <c:if test="${not empty adminError }">
		<div style="color: red;"><h3>${adminError}</h3></div>
	    </c:if>
	    <c:if test="${not empty userTypeError }">
		<div style="color: red;"><h3>${userTypeError}</h3></div>
	    </c:if>
		</div>
	
	</form>
</div>
</div>