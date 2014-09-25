<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="js/script.js">
	
</script>
<div class="topbar">
	<%-- 1. session scope에 설정된 "loginMember" 속성이 없으면 로그인 페이지(login.jsp)를,
        있으면 로그아웃 페이지(logout.jsp)를 포함시킨다.(include 액션 사용) --%>
	<table>
		<tr>
			<td><c:if test="${empty sessionScope.loginMember}">
					<c:import url="/login.jsp" />
				</c:if> <c:if test="${not empty sessionScope.loginMember}">
					<c:import url="/logout.jsp" />
				</c:if></td>
		</tr>
		<tr>
			<td><table id="sidebartable">
					<tr>
						<td class="category"><a
							href="<c:url value="/board/list?category=best"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>베스트</a></td>
						<td class="category"><a
							href="<c:url value="/board/list?category=women"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>여성의류</a></td>
						<td class="category"><a
							href="<c:url value="/board/list?category=best"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>남성의류</a></td>
						<td class="category"><a
							href="<c:url value="/board/list?category=best"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>패션잡화</a></td>

						<td class="category"><a
							href="<c:url value="/board/list?category=best"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>뷰티</a></td>
						<td class="category"><a
							href="<c:url value="/board/list?category=best"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>스포츠패션</a></td>
						<td class="category"><a
							href="<c:url value="/board/list?category=best"/>"><img
								src="<c:url value="/images/best.jpg"/>"><br>해외배송</a></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td><table id="sidebartable">
					<tr>
						<td id="menulabel">Menu</td>
						<td><a href='<c:url value="/product?action=select-all"/>'>상품보기</a></td>

						<c:if test="${sessionScope.loginMember.memberID == 'duke'}">
							<td><a href='<c:url value="/registerProduct.jsp"/>'>상품등록</a></td>
							<td><a href='<c:url value="/registerProduct2.jsp"/>'>상품등록2</a></td>


						</c:if>
						<c:if test="${empty sessionScope.loginMember}">
							<td><a href='<c:url value="/registerMember.jsp"/>'>회원가입</a></td>
						</c:if>

						<c:if test="${not empty sessionScope.loginMember}">
							<td><a href='<c:url value="/member?action=select"/>'>회원정보변경</a></td>
							<td><a
								onclick="removeCheck('<c:url value='/member?action=remove'/>');">회원탈퇴</a></td>
						</c:if>
						<td><a href="<c:url value='/board/list'></c:url>">게시판보기</a></td>
					</tr>

				</table></td>
		</tr>
	</table>
</div>

