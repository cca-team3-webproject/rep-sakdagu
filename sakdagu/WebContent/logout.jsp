<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--     <%
    Member loginedMember = (Member) session.getAttribute("loginMember");
    %> --%>
<form action='<c:url value="/member?action=logout"/>' method="POST">
	<table id="logouttable">
		<tr>
			<td class="message">${ loginMember.name}님 <!--<br>  -->환영합니다.
			</td>
<!-- 		</tr>
		<tr> -->
			<td><input type="submit" name="logout" value="로그아웃"></td>
			<td><a href='<c:url value="/cart.jsp"/>'>장바구니</a></td>
		</tr>
	</table>
</form>