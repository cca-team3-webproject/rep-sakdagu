<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<link rel="stylesheet" href="<c:url value="/css/cart.css"/>">

<script src="../js/board.js"></script>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script>
var buy =function(){
	alert('감사');
	location.href="<c:url value="/"/>";
}
</script>
</head>
<body>
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
						<tr>
							<th>곽재혁팜</th>
							<th>10000원</th>
							<th>무료</th>
							<th></th>
						</tr>
						<tr>
							<th>곽재혁팜</th>
							<th>10000원</th>
							<th>무료</th>
							<th></th>
						</tr>
							<h1></h1>
					</tbody>
				</table>
				
				<h1>총 주문금액 = </h1>
			</div>
			<div>
				<button id="shopping" onclick="history.back();">계속 쇼핑하기</button>
				
				<button id="get" onclick="buy();">바로 구매하기 </button>
			</div>
		</div>
	</div>
</body>
</html>
