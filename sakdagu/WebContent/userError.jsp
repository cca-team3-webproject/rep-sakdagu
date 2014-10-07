<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오류 발생</title>
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="top-bar.jsp" />
			</div><div class="tableRow">
			<!-- START of main content-->
			<div class="main">
				<h4 id="error">입력 정보 오류</h4>
				<p>아래와 같은 문제가 있습니다.</p>
				<ul>

					<c:forEach var="message" items="${errorMsgs }">
						<li>${message}...</li>

					</c:forEach>

				</ul>
				<p>다시 시도해 주세요.</p>
			</div>
			<!-- END of main content-->
		</div>
	</div>
</body>
</html>
