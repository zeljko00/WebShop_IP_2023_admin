<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="etf.ip.model.CategoryDTO" %>
<%@ page import="etf.ip.model.SpecificAttributeDTO" %>

<jsp:useBean id="category" type="etf.ip.model.CategoryDTO" scope="request" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Kategorija</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="header.jsp" />
<jsp:include page="admin-header.jsp" />
<jsp:include page="menu.jsp" />
<br>
<br>
	<div id="stat-container">
		<h2 class="colored-font">Kategorija - <%=category.getName() %></h2>
		<h3 class="colored-font">Atributi:</h3>
		<ul>
			<% for(SpecificAttributeDTO a: category.getSpecificAttributes()){ %>
				<li><%=a.getName() %> | <a href="admin?action=deleteAttribute&category=<%=category.getId() %>&attribute=<%=a.getId() %>"> ukloni</a></li>
			<%} %>
		</ul>
		<h3 class="colored-font">Izmjena naziva kategorija</h3>
		<form action="admin?action=updateCategory&category=<%=category.getId()%>" method="post">
			Novi naziv: <input type="text" name="name">
			<input type="submit" value="Sacuvaj">
		</form>
		<h3 class="colored-font">Dodavanje novog atributa</h3>
		<form action="admin?action=addAttribute&category=<%=category.getId()%>"  method="post">
			Naziv atributa: <input type="text" name="name">
			<input type="submit" value="Sacuvaj">
		</form>
		
	</div>
<jsp:include page="footer.jsp" />
</body>
</html>