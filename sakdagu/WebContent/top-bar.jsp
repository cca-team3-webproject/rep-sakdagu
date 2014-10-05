<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="js/script.js"></script>
<div class="topbar">
	<%-- 1. session scope에 설정된 "loginMember" 속성이 없으면 로그인 페이지(login.jsp)를,

        있으면 로그아웃 페이지(logout.jsp)를 포함시킨다.(include 액션 사용) --%>
	<c:if test="${empty sessionScope.loginMember}">
		<c:import url="/login.jsp" />
	</c:if>
	<c:if test="${not empty sessionScope.loginMember}">
		<c:import url="/logout.jsp" />
	</c:if>
	<br>
	<br>
	<br> <a href="<c:url value="/"/>"> <img id="logoP"
		src="<c:url value="/images/logo.png"/>"></a><br>

	<form name="searchForm" action="<c:url value="/board/list"/>"
		method="GET">
		<table id="categoryPosition">
			<tr>

				<td><select name="category">
						<option value="전체"
							<c:if test="${param.category eq '베스트'}"> selected="selected"</c:if>>전체</option>
						<option value="여성의류"
							<c:if test="${param.category eq '여성의류'}"> selected="selected"</c:if>>여성의류</option>
						<option value="남성의류"
							<c:if test="${param.category eq '남성의류'}"> selected="selected"</c:if>>남성의류</option>
						<option value="패션잡화"
							<c:if test="${param.category eq '패션잡화'}"> selected="selected"</c:if>>패션잡화</option>
						<option value="뷰티"
							<c:if test="${param.category eq '뷰티'}"> selected="selected"</c:if>>뷰티</option>
						<option value="스포츠패션"
							<c:if test="${param.category eq '스포츠패션'}"> selected="selected"</c:if>>스포츠패션</option>
						<option value="해외배송"
							<c:if test="${param.category eq '해외배송'}"> selected="selected"</c:if>>해외배송</option>
				</select></td>
				<td><input id="searchinput" type="text" name="searchText"
					value="${param.searchText}" onclick="select();"> <input
					type="submit" value="검색"></td>
			</tr>
		</table>
	</form>
	<br>
	<br>

<table id="sidebartable">
		<tr>
			<td class="category"><a
				href="<c:url value="/board/list?category=베스트"/>"><button
						class="categoryButton">베스트</button></a></td>
			<td class="category"><a
				href="<c:url value="/board/list?category=여성의류"/>"><button
						class="categoryButton">여성의류</button></a></td>
			<td class="category"><a
				href="<c:url value="/board/list?category=남성의류"/>"><button
						class="categoryButton">남성의류</button></a></td>
			<td class="category"><a
				href="<c:url value="/board/list?category=패션잡화"/>"><button
						class="categoryButton">패션잡화</button></a></td>
			<td class="category"><a
				href="<c:url value="/board/list?category=뷰티"/>"><button
						class="categoryButton">뷰티</button></a></td>
			<td class="category"><a
				href="<c:url value="/board/list?category=스포츠패션"/>"><button
						class="categoryButton">스포츠패션</button></a></td>
			<td class="category"><a
				href="<c:url value="/board/list?category=해외배송"/>"><button
						class="categoryButton">해외배송</button></a></td>
		</tr>
	</table>
</div>
