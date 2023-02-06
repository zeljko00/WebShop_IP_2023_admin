<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.model.UserDTO"%>
    <jsp:useBean id="appUser" type="etf.ip.model.UserDTO" scope="request"/>
    <jsp:useBean id="note" type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Korisnik</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="header.jsp" />
<jsp:include page="admin-header.jsp" />
<jsp:include page="menu.jsp" />
<br>
<br>
	<div id="stat-container">
		<h2 class="colored-font">Korisnik - <%=appUser.getFirstname()+" "+appUser.getLastname() %></h2>
		<h3 class="colored-font">Informacije:</h3>
		<p>
		Korisnicko ime: <%=appUser.getUsername() %><br>
		Email: <%=appUser.getEmail() %><br>
		Lokacija: <%=appUser.getCity() %><br>
		Status: <%=appUser.getStatus() %>
		</p>
		<h3 class="colored-font">Izmjena informacija</h3>
		<form action="admin?action=updateUser&user=<%=appUser.getId()%>" method="post">

		<label>Ime:</label> <input type="text" name="firstname"><br>
		<label>Prezime:</label> <input type="text" name="lastname"><br>
		<label>Lozinka:</label> <input type="password" name="password"><br>
		<label>Lokacija:</label> <input type="text" name="city"><br>
		<label>Email:</label> <input type="text" name="email"><br>
		<input type="submit" value="Sacuvaj">

		</form>
			<% System.out.println("NOTE="+note);if("-".equals(note)==false){ %>
		<p class="note-p"><%= note %></p>
	<%} %>
		
	</div>
<jsp:include page="footer.jsp" />
</body>
</html>