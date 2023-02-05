<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="etf.ip.dao.UserDAO"%>
<%@ page import="etf.ip.model.User"%>
<jsp:useBean id="user" class="etf.ip.model.User" scope="session"/>
<jsp:useBean id="userDAO" class="etf.ip.dao.UserDAO" scope="application"/>
<jsp:setProperty name="user" property="username" param="username" />
<jsp:setProperty name="user" property="password" param="password" />

<%boolean flag=false; 
if(user.getUsername()!=null && user.getUsername().equals("")==false && user.getPassword()!=null && user.getPassword().equals("")==false){
    User temp=userDAO.login(user.getUsername(),user.getPassword());
    if(temp!=null){
        user.setLastname(temp.getLastname());
        user.setFirstname(temp.getFirstname());
        user.setPassword(temp.getPassword());
        user.setUsername(temp.getUsername());
        response.sendRedirect("support.jsp");
    }
    else
        flag=true;
}
%>
<!DOCTYPE html>
<html>
<head>
    <title>Stranica za prijavu</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />
<jsp:include page="WEB-INF/support-header.jsp" />
<div id="space">
<form action="index.jsp" method="post" class="custom-form">
    <label>Korisničko ime:</label><input type="text" name="username"><br>
    <label>Lozinka:</label><input type="password" name="password"><br>
    <input type="submit" value="Prijavi se" class="custom-input">
</form>
<% if(flag) {%>
<p class="invalid-cred">Nevalidni kredencijali!</p>
<%} %>
</div>

<jsp:include page="WEB-INF/footer.jsp" />
</body>
</html>