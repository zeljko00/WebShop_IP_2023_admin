<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="etf.ip.model.User"%>
<%@ page import="etf.ip.model.Message"%>
<%@ page import="etf.ip.model.MessageRepo"%>
<%@ page import="java.util.List"%>
<jsp:useBean id="user" class="etf.ip.model.User" scope="session" />
<jsp:useBean id="repo" class="etf.ip.model.MessageRepo" scope="request" />
<%
if (user == null || user.getUsername() == null)
	response.sendRedirect("index.jsp");
%>

<!DOCTYPE html>
<html>

<head>
<title>Webshop Korisnička podrška</title>
<link rel="stylesheet" href="style.css">
<script>
function hide(id){
	console.log("hide "+id)
	let request = new XMLHttpRequest();
  request.onreadystatechange = () => {
    if (request.readyState == 4 && request.status == 200) {
    }
  };
  request.open("put", "http://localhost:8081/WebShop/messages/"+id, true);
  request.setRequestHeader("Content-Type", "application/json");
  request.send();
	try{
	document.getElementById(id+"-button").className="header read";
	console.log(document.getElementById(id+"-button").innerHTML)
	console.log(document.getElementById(id+"-button").innerHTML.replace("NOVO - ",""));
	document.getElementById(id+"-button").innerHTML=document.getElementById(id+"-button").innerHTML.replace("NOVO - ","");
	}catch(e){console.log(e)}
	if(document.getElementById(id).style.display!=="none")
		document.getElementById(id).style.display="none";
	else document.getElementById(id).style.display="block"
}
function respond(id){
	console.log(id)
	console.log(document.getElementById(id+"-content").value);
	
	let request = new XMLHttpRequest();
	  request.onreadystatechange = () => {
	    if (request.readyState == 4 && request.status == 200) {
	    	document.getElementById(id+"-note").style.display="block"
	    }
	  };
	  request.open("post", "http://localhost:8081/WebShop/email?recepient="+id+"&content="+document.getElementById(id+"-content").value, true);
	  request.setRequestHeader("Content-Type", "application/json");
	  request.send();
}
function search(){
	document.location.replace("http://localhost:8081/Web")
}
</script>
</head>
<body>
	<%
	List<Message> list = null;
	if (request.getParameter("key") != null && "".equals(request.getParameter("key")) == false)
		list = repo.getMsgs(request.getParameter("key"));
	else
		list = repo.getMsgs();
	%>
	<jsp:include page="WEB-INF/header.jsp" />
	<div class="info"><%="Službenik za korisničku podršku: " + user.getFirstname() + " " + user.getLastname()%></div>
	<div class="content-div">
	<form action="support.jsp" method="GET" id="form">
			<input id="search-bar" name="key"> <input type="submit"
				id="search-btn" value="Pretraga">
			</button>
		</form>
		<%
		if (list.size() == 0) {
		%>
		
		<div class="note">Nema pristiglih poruka!</div>
		<%
		}
		%>
		<%
		for (Message m : list) {
		%>
		<div class="message">

			<button class="header unread" id="<%=m.getId()%>-button"
				onclick="hide(<%=m.getId()%>)">
				<strong>NOVO - </strong><%=m.getUser()%></button>
			<div class="hidden" style="display: none" id=<%=m.getId()%>>
				<p class="content"><%=m.getContent()%></p>
				<div>
					<textarea id="<%=m.getId()%>-content" name="cont"></textarea>
					<br />
					<button class="answer" onclick="respond(<%=m.getUserId()%>)">Odgovori</button>
					<p class="succ-note" id="<%=m.getId()%>-note" style="display: none">Poruka
						je poslata!</p>
				</div>
			</div>
		</div>
		<%
		}
		%>
	</div>
	<jsp:include page="WEB-INF/footer.jsp" />
</body>
</html>
