<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageContext.errorData.throwable.message}</title>
<link rel="stylesheet" href="<c:url value="/css/dukeshop.css"/>">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="top-bar.jsp" />
		</div>
		<div class="tableRow">
			<!-- START of main content-->
			<div class="main">
				<p class="exceptionname">${pageContext.exception}</p>
				<p>
					${pageContext.exception}<br> <br> This is the request
					URI: <br>
					<code>${pageContext.errorData.requestURI}</code>
					<br>
				</p>
			</div>
		</div>
	</div>
</body>
</html>