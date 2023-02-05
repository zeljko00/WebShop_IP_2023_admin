<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.lang.String"%>
<jsp:useBean id="statistics" type="java.util.List<String>"
	scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Statistika</title>
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
			<td class="colored">zahtjev/odgovor</td>
		</tr>
		<%
		int count = 1;
		for (String line : statistics) {
			if (line.contains("log Response")==false) {
		%>
		<tr>
			<td class="pos colored"><%=count%></td>
			<td><%=line %></td>
		</tr>
		<%
		count++;
		}
		}
		%>
	</table>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>