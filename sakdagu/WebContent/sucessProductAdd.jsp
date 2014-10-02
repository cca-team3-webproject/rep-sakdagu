<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Product"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록(변경) 성공</title>
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="top-bar.jsp"/>
			</div><div class="tableRow">
			<!-- START of main content-->
			<div class="main">
				<h4>[상품정보 등록(수정)결과]</h4>
				<%-- 				<%
                product product = (product) request.getAttribute("product");
                %> --%>
				<table>
					<tr>
						<td class="label">상품 ID :</td>
						<td>${selectedProduct.productID }</td>
					</tr>
					<tr>
						<td class="label">상품이름 :</td>
						<td>${selectedProduct.productName}이 등록(변경)되었습니다.</td>
					</tr>
				</table>
			</div>
			<!-- END of main content -->
		</div>
	</div>
</body>
</html>
