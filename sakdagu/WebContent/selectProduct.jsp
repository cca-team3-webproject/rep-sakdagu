<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 정보</title>
<link rel="stylesheet" href="css/dukeshop.css">
</head>
<body>

	<jsp:useBean id="selectedProduct"
		class="casestudy.business.domain.Product" scope="request" />

	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="top-bar.jsp"/>
			<!-- START of main content-->
			<div class="main">
				<h4>[상품 상세 정보]</h4>
				<table id="productdetails">
					<tr>
						<td rowspan="5"><img
							src="images/${ selectedProduct.photoDir }"></td>
						<td id="producttitle" colspan="2">${ selectedProduct.productName }</td>
					</tr>
					<tr>
						<td>제조사</td>
						<td>${ selectedProduct.company }</td>
					</tr>
					<tr>
						<td>일반가격</td>
						<td id="price1">${ selectedProduct.price1 }원</td>
					</tr>
					<tr>
						<td>판매가격</td>
						<td id="price2">⇒&nbsp;${ selectedProduct.price2 }원</td>
					</tr>
					<tr>
						<td>카드할부여부</td>
						<td><c:choose>
								<c:when test="${ selectedProduct.installment eq '1'}">
									<c:out value="가능" />
								</c:when>
								<c:otherwise>

									<c:out value="">
                            불가능
                            </c:out>

								</c:otherwise>
							</c:choose>
							</td>
					</tr>
					<tr>
						<td colspan="3" id="details">
							<table>
								<tr>
									<td>제품설명</td>
								</tr>
								<tr>
									<td><textarea rows="4" cols="40">${ selectedProduct.detail }</textarea></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<!-- END of main content-->
		</div>
	</div>
</body>
</html>
