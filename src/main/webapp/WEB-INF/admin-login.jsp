<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="invalid" type="java.lang.Boolean" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>Stranica za prijavu</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="header.jsp" />
<jsp:include page="admin-header.jsp" />
<div id="space">
<form action="admin?action=login" method="post" class="custom-form">
    <label>Korisničko ime:</label><input type="text" name="username"><br>
    <label>Lozinka:</label><input type="password" name="password"><br>
    <input type="submit" value="Prijavi se" class="custom-input">
</form>
<% if(invalid) {%>
<p class="invalid-cred">Nevalidni kredencijali!</p>
<%} %>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>