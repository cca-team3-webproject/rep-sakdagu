<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- request scope 속성에 에러메시지가 있으면 출력한다. --%>
<c:if test=" ${not empty requestScope.loginErrorMsg}">
	<ul id="loginerrormsg">
		<li>${requestScope.loginErrorMsg }</li>
	</ul>
</c:if>

<form action='<c:url value="/member?action=login"/>' method="POST">
	<table id="logintable">
		<tr>
			<td class="label">ID</td>
			<td><input type="text" name="memberID" size="10"></td>
			<!--             </tr>
            <tr> -->
			<td class="label">PW</td>
			<td><input type="password" name="password" size="10"></td>
			<!--             </tr>
            <tr> -->
			<td></td>
			<td><input type="submit" name="login" value="로그인"></td>
			<td><a href='<c:url value="/registerMember.jsp"/>'>회원 가입</a></td>
		</tr>

	</table>
</form>

