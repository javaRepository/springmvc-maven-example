<%@ page isErrorPage="true" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN""http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Freemarker Error</title>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">

		<style type="text/css">
			*{margin: 0px;padding: 0px;}
			body{font-family: Arial;font-size: small;color: black;background: #f5f5f5;}
			a{text-decoration: none;color: #0059b4;}
		</style>
	</head>
	<body>
		<%
			String userInfo = request.getParameter("userInfo");
			if (null != userInfo) {
		%>
				Network unavailable, please <a href="home?userInfo=${userInfo}">retry</a> or <a href="login?userInfo=${userInfo}">re-login</a>.
		<%
			} else {
		%>
				Network unavailable, please <a href="/">retry</a> or <a href="login">re-login</a>.
		<%
			}
		%>

		<div style="display:none;">
			<%=exception%>
		</div>
	</body>
</html>
