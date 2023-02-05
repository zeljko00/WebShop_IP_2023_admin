<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="etf.ip.model.CategoryDTO"%>
<%@ page import="etf.ip.model.SpecificAttributeDTO"%>
<jsp:useBean id="categories" type="java.util.List<CategoryDTO>"
	scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
				<td class="colored">Kategorija</td>
				<td class="colored">Atributi</td>
			</tr>
			<%
			int count = 1;
			for (CategoryDTO cat : categories) {
			%>
				<tr>
					<td class="pos colored"><%=count%></td>
					<td><a href="admin?action=category&category=<%=cat.getId()%>"><%=cat.getName()%></a></td>
					<td><ul>
					<%
					for (SpecificAttributeDTO a : cat.getSpecificAttributes()) {
					%>
						<li><%= a.getName()%></li>
					<%
					}
					%>
				</ul>
					</td>
				</tr>
				<%
				count++;
			
			}
			%>

		</table>
		
		<form action="admin?action=addCategory" method="post" id="new-category-form">
			
			Naziv nove kategorije: <input type="text" name="category">
			<input type="submit" value="Sacuvaj">
		</form>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>