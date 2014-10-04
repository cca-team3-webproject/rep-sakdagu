<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tr>
	<td class="image" rowspan="8" colspan="2"><img
		src="<c:url value="/board/img?boardNum=${board.num}"/>"></td>
	<td class="writer">${board.writer}</td>
</tr>
<tr>
	<th colspan="2" class="title">${board.title}</th>
</tr>
<tr>
	<td class="readcount">${board.readCount}</td>
	<td class="regdate">${board.regDate}</td>
</tr>
<tr>
	<th colspan="2"><select name="selectedProduct">
			<c:forEach items="${products}" var="product">
				<option value="${product.productID}">상품
					${product.productID}-${product.productTitle}</option>
			</c:forEach>
	</select></th>
</tr>
<tr>
	<th colspan="2"><select name="selectedOption">
			<c:forEach items="${products}" var="product">
				<c:forEach items="${product.option}" var="option">
					<option value="${option.optionID}">선택
						${option.optionID}-${option.optionTitle}</option>
				</c:forEach>
			</c:forEach>
	</select></th>
</tr>
<tr>
	<td colspan="2"><hr></td>
</tr>
<tr>
	<td colspan="2" background="#ff55ff">${products[0].productTitle}-${products[0].option[1].optionTitle}
		x <select><c:forEach begin="1" end="10" varStatus="v">
				<option value="${v.count}">${v.count}</option>
			</c:forEach></select>개 =
	</td>
</tr>
<tr>
	<td colspan="2" align="right">${products[0].option[0].price1}원</td>
</tr>

<c:forEach items="${products}" var="product">
	<c:forEach items="${product.option}" var="option">
		<tr>
			<td colspan="6"><hr></td>
		</tr>
		<tr>
			<td class="images" colspan="6">상품${option.productID}-옵션${option.optionID}[${product.productTitle}-${option.optionTitle}]
				<br> <img
				src="<c:url value="/board/img?boardNum=${board.num}&productID=${option.productID}&optionID=${option.optionID}"/>">
			</td>
		</tr>

	</c:forEach>
</c:forEach>
