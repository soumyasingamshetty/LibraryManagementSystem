<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<style>


.center{
 display:flex;
  justify-content: center;
}
</style>


<html>
<body style="background-color: aqua">	
<div class=center>
<div>

    <div> <H1>Welcome ${currentuser}</H1><a href ="/loginService/logout">Logout</a></div>
	<br><br><br>
	<form action="/loginService/allBooks" method="post">
	<div><H3>Select your category </H3></div>
	<div><input type="radio" name="popularity" value="popular" checked > Popular
		 <input type="radio" name="popularity" value="all" > All Books </div>
		 <br>
		 <div>
		 <select name="interest" id="interest">
		 <option value="all" selected>All</option>
		 <c:forEach var="subject" items="${listofsubjects}">
		 	<option value="${subject}" >${subject}</option>
		 </c:forEach>
		</select></div>
        <br><br>
    <div><input type="submit" value="List Books"  style="background: yellow"></div>
    
    <br><br>
    <div> Check Your status: 
    <input type="button" value="Borrowed Books" onClick="getBorrowedBooks()" style="background: yellow">
    
    <br>
   </div>
   
   
   </form>
</div>
</div>
</html>
<script type="text/javascript">
	function getBorrowedBooks(){
		window.location.href="/loginService/borrowedBooks"
	}
</script>