<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="de.fau.amos.*"%> 
 <% User.init(getServletConfig()); %>

<!DOCTYPE html>
<html>

<head>
<title>AMOS-Cockpit</title>
<link rel="stylesheet" href="../styles/style.css" type="text/css" />
</head>

<body>

	<header>
		<h1>AMOS PROJECT</h1>
 		<h2>Green Energy Cockpit</h2>
	</header>
	<div id="loginStateBox">
	Logged in as "
	
	<% 
	out.print(session.getAttribute(Const.SessionAttributs.LOGGED_IN_USERNAME)); 
	%>
	"
	</div>
	    <nav id="menue">
        <ul>
            <a href="#"><li>Funktion #1</li></a>
            <a href="#"><li>Funktion #2</li></a>
            <a href="#"><li>Funktion #3</li></a>
            <a href="#"><li>Funktion #4</li></a>
            <a href="../login/logout"><li>Logout</li></a>
        </ul>
    </nav>   

	<div id="completeContentBox">
		<div id="content">
			<br><br>
			Hier wird sp�ter einmal der Inhalt stehen.
			<br><br><br>		
		</div>
	</div>
</body>
</html>