<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="js/script.js">
	
</script>
<div class="sidebar">
	<%-- 1. session scope에 설정된 "loginMember" 속성이 없으면 로그인 페이지(login.jsp)를,
        있으면 로그아웃 페이지(logout.jsp)를 포함시킨다.(include 액션 사용) --%>

	<c:if test="${empty sessionScope.loginMember}">
		<c:import url="/login.jsp" />
	</c:if>
	<c:if test="${not empty sessionScope.loginMember}">
		<c:import url="/logout.jsp" />
	</c:if>

	<table id="sidebartable">
		<tr>
			<td id="menulabel">Menu</td>
		</tr>
		<tr>
			<td><hr></td>
		</tr>
		<tr>
			<td><a href='<c:url value="/product?action=select-all"/>'>상품보기</a></td>
		</tr>

		<c:if test="${sessionScope.loginMember.memberID == 'duke'}">
			<tr>
				<td><a href='<c:url value="/registerProduct.jsp"/>'>상품등록</a></td>
			</tr>
			<tr>
				<td><a href='<c:url value="/registerProduct2.jsp"/>'>상품등록2</a></td>
			</tr>
		</c:if>

		<c:if test="${empty sessionScope.loginMember}">
			<tr>
				<td><a href='<c:url value="/registerMember.jsp"/>'>회원가입</a></td>
			</tr>
		</c:if>
		<c:if test="${not empty sessionScope.loginMember}">
			<tr>
				<td><a href='<c:url value="/member?action=select"/>'>회원정보변경</a></td>
			</tr>
			<tr>
				<td><a
					onclick="removeCheck('<c:url value='/member?action=remove'/>');">회원탈퇴</a></td>
			</tr>
		</c:if>
		<tr>
			<td><a href="<c:url value='/board/list'></c:url>">게시판보기</a></td>

		</tr>
	</table>
</div>

