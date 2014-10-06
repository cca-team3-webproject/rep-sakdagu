<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageContext.exception.message}</title>
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link rel="stylesheet" href="<c:url value="/css/menubar.css"/>">
<script src="../js/board.js"></script>
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="/side-bar.jsp" />
			<div class="main">
				<p class="exceptionname">${pageContext.exception.message}</p>
				<p>
					${pageContext.exception}<br> <br> This is the request
					URI: <br>
					<code>${requestScope["javax.servlet.error.request_uri"]}</code>
					<br>
				</p>
			</div>
		</div>
	</div>
</body>
</html>
