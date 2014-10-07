<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<tr>
	<td class="image"><img
		src="<c:url value="/board/img?boardNum=${board.num}"/>"></td>
	<td>
		<div class="bag">
			<table>
				<tr>
					<th colspan="2" class="title">${board.title}</th>
				</tr>
				<tr>
					<td class="readcount">${board.readCount}</td>
					<td class="regdate">${board.regDate}</td>
				</tr>
				<tr>
					<th colspan="2"><select name="selectedProduct"
						onchange="selectProduct(this);">
							<option value="0">상품을 선택하세요</option>
							<c:forEach items="${products}" var="product">
								<option value="${product.productID}"
									<c:if test="${param.productID eq product.productID}"> selected="selected"</c:if>>상품
									${product.productID}-${product.productTitle}</option>
							</c:forEach>
					</select></th>
				</tr>
				<tr>
					<th colspan="2" class="optionArea"><c:forEach
							items="${products}" var="product">
							<c:if test="${product.productID eq param.productID}">
								<select name="selectedOption" onchange="selectOption(this);">
									<option value="0">사이즈/색깔/날짜 등을 선택하세요.</option>
									<c:forEach items="${product.option}" var="option">
										<option value="${option.optionID}"
											<c:if test="${param.optionID eq option.optionID}"> selected="selected"</c:if>>선택${option.optionID}-${option.optionTitle}
											\ ${option.price2}</option>
									</c:forEach>
								</select>
							</c:if>
						</c:forEach></th>
				</tr>
				<tr>
					<td colspan="2"><hr></td>
				</tr>
				<tr>
					<td colspan="2" style=""><c:if
							test="${not empty param.optionID}">
							<c:forEach items="${products}" var="product">
								<c:if test="${product.productID eq param.productID}">
									<c:forEach items="${product.option}" var="option">
										<c:if test="${option.optionID eq param.optionID}">
											${product.productTitle}-${option.optionTitle}
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach> x <select name="quantity" onchange="selectQuantity(this);"
								id="select_box">
								<c:forEach begin="1" end="11" varStatus="v">
									<option value="${v.count-1}"
										<c:if test="${param.quantity eq v.count-1}"> selected="selected"</c:if>>${v.count-1}</option>
								</c:forEach>
							</select>개 =
</c:if></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<form action="buyForm">
							<c:forEach items="${products}" var="product">
								<c:if test="${product.productID eq param.productID}">
									<c:forEach items="${product.option}" var="option">
										<c:if test="${option.optionID eq param.optionID}">
											<s> ${option.price1*param.quantity}</s>
											<span>${option.price2*param.quantity} <script type="text/javascript">
											$('#select_box option:selected').text(); </script></span>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
							원
						</form>
						<div id="order_area" class="btnB">
							<input id="instantOrderButton" type="button" value="구매하기"
								class="btnBg btn_buy"
								onclick="alert($('#select_box option:selected').text());">
							<button id="orderButton" data-cclick="PDP,TOP_CART,BUY,3"
								type="button" value="장바구니" class="cartButton"
								data-gaclick="{&quot;hitType&quot;:&quot;event&quot;, &quot;eventCategory&quot;:&quot;PDP&quot;, &quot;eventAction&quot;:&quot;click&quot;, &quot;eventLabel&quot;:&quot;/click_pdp_cart&quot;, &quot;eventValue&quot;:71615516}">장바구니</button>
						</div>
					</td>
				</tr>

			</table>
		</div>
	</td>
</tr>

<c:forEach items="${products}" var="product">
	<c:forEach items="${product.option}" var="option">
		<tr>
			<td><hr></td>
		</tr>
		<tr>
			<td class="images">상품${option.productID}-옵션${option.optionID}[${product.productTitle}-${option.optionTitle}]
				<br> <img
				src="<c:url value="/board/img?boardNum=${board.num}&productID=${option.productID}&optionID=${option.optionID}"/>">
			</td>
		</tr>
	</c:forEach>
</c:forEach>
