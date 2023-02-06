<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="etf.ip.model.UserDTO" %>
<%@ page import="java.util.List" %>

<jsp:useBean  type="java.util.List<UserDTO>" id="users" scope="request"/>
<jsp:useBean  type="java.lang.String" id="note" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Korisnici</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="header.jsp" />
<jsp:include page="admin-header.jsp" />
<jsp:include page="menu.jsp" />
<br>
	<br>
	<div id="stat-container">
	<table>
		<tr>
			<td class="pos colored">Br.</td>
			<td class="colored">Ime</td>
			<td class="colored">Prezime</td>
			<td class="colored">Korisnicko ime</td>
			<td class="colored">Email</td>
			<td class="colored">Status</td>
			<td class="colored">&nbsp</td>
		</tr>
		<%
		int count = 1;
		for (UserDTO user : users) {
			
		%>
		<tr>
			<td class="pos colored"><%=count%></td>
			<td><%=user.getFirstname() %></td>
			<td><%=user.getLastname() %></td>
			<td><a href="admin?action=user&user=<%= user.getId()%>"><%=user.getUsername() %></a></td>
			<td><%=user.getEmail() %></td>
			<td><%=user.getStatus() %></td>
			<td><a href="admin?action=deleteUser&user=<%=user.getId()%>">ukloni</a></td>
		</tr>
		<%
		count++;
		}
		%>
	</table>
	<h2 class="colored-font">Dodavanje novog korisnika</h2>
	<form action="admin?action=addUser" method="post" id="new-category-form">
		<label>Ime:</label> <input type="text" name="firstname"><br>
		<label>Prezime:</label> <input type="text" name="lastname"><br>
		<label>Korisnicko ime:</label> <input type="text" name="username"><br>
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