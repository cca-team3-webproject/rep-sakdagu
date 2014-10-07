<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<script src="/sakdagu/js/script.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.js"></script>

<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="top-bar.jsp" />
		</div>
		<div class="tableRow">
			<!-- START of main content-->
			<div class="main">
				<h4>[회원 가입]</h4>
				<form action="member?action=register" method="POST">
					<table class="registertable">
						<tr>
							<td class="label">회원ID :</td>
							<td><input type="text" name="memberID" size="20"
								maxlength="15"></td>
						</tr>
						<tr>
							<td class="label">비밀번호 :</td>
							<td><input type="password" name="password1" size="20"
								maxlength="10"></td>
						</tr>
						<tr>
							<td class="label">비밀번호확인 :</td>
							<td><input type="password" name="password2" size="20"
								maxlength="10" onblur="passchk(this.form)"></td>
						</tr>
						<tr>
							<td class="label">이름 :</td>
							<td><input type="text" name="name" size="20" maxlength="20"></td>
						</tr>
						<tr>
							<td class="label">이메일 :</td>
							<td><input type="text" name="email" size="30" maxlength="60"></td>
						</tr>
						<tr>
							<td class="label">전화번호 :</td>
							<td><input type="text" name="tel" size="20" maxlength="20"></td>
						</tr>
						<tr>
							<td class="label">우편번호 :</td>
							<td><input id="zipcode1" type="text" name="zipcode" size="3"
								maxlength="7">-<input id="zipcode2" type="text" name="zipcode" size="3"
								maxlength="7"><input type="button" name="search" value="우편번호검색" onclick="openDaumPostcode()"></td>
						</tr>
						<tr>
							<td class="label">주소 :</td>
							<td><input id="addr" type="text" name="address" size="50"
								maxlength="70"></td>
						</tr>
						<tr>
							<td colspan="2">
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="회원가입"> <input
								type="reset" value="취소"></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- END of main content-->
		</div>
	</div>
</body>
</html>