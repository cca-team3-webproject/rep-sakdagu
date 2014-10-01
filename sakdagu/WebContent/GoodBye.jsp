<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록(변경) 성공</title>
<link rel="stylesheet" href="<c:url value="/css/dukeshop.css"/>">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<%@ include file="top-bar.jsp"%>
			</div><div class="tableRow">
			<!-- START of main content-->
			<div class="main">
				<h4>[회원정보 등록(수정)결과]</h4>
				<%-- 				<%
                Member member = (Member) request.getAttribute("member");
                %> --%>
				<table>
					<tr>
						<td class="label">회원 ID :</td>
						<td>${loginMember.memberID }</td>
					</tr>
					<tr>
						<td class="label">회원이름 :</td>
						<td>${loginMember.name}님이등록(변경)되었습니다.</td>
					</tr>
				</table>
			</div>
			<!-- END of main content -->
		</div>
	</div>
</body>
</html>
