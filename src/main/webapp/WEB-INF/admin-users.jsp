<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="etf.ip.model.UserDTO" %>
<%@ page import="java.util.List" %>

<jsp:useBean  type="java.util.List<UserDTO>" id="users" scope="request"/>
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
		</tr>
		<%
		int count = 1;
		for (UserDTO user : users) {
			
		%>
		<tr>
			<td class="pos colored"><%=count%></td>
			<td><%=user.getFirstname() %></td>
			<td><%=user.getLastname() %></td>
			<td><%=user.getUsername() %></td>
			<td><%=user.getEmail() %></td>
			<td><%=user.getStatus() %></td>
		</tr>
		<%
		count++;
		}
		%>
	</table>
	</div>
<jsp:include page="footer.jsp" />
</body>
</html>