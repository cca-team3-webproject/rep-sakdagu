<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sakdagu</title>
<link rel="stylesheet" href="<c:url value="/css/cart.css"/>">
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">

<script src='<c:url value="/js/board.js"/>'></script>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script>
	var buy = function() {
		alert('감사');
		location.href = "<c:url value="/"/>";
	}
</script>
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="top-bar.jsp" />
		</div>
		<div id="content" class="order">
			<h1 class="title">
				<span class="ir">장바구니</span> <span class="titlestep-count">(<em>0</em>개)
				</span>
			</h1>

			<div id="buySakdagu">
				<div class="orderTableWrap">
					<table class="orderTableDealList">
						<thead>
							<tr>
								<th class="lp20">상품정보</th>
								<th>상품금액</th>
								<th>배송비</th>
								<th>총 금액</th>
							</tr>
						</thead>
						<tbody id="item">
							<c:forEach begin="1" end="1">
								<tr>
									<th>----</th>
									<th>${param.sum}</th>
									<th>무료</th>
									<th>${param.sum}</th>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<h1>총 주문금액 =${param.sum}</h1>
				</div>
				<div>
					<input type="button" id="shopping" onclick="goUrl('board/list');"
						value="계속 쇼핑하기">
					<button id="get" onclick="buy();">바로 구매하기</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
