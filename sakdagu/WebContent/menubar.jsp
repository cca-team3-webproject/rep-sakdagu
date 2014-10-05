<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="styles.css">
<script src="http://code.jquery.com/jquery-latest.min.js"
	type="text/javascript"></script>
<script src="script.js"></script>
<%-- request scope 속성에 에러메시지가 있으면 출력한다. --%>

<div id='cssmenu'>
	<ul>
		<li><a href="<c:url value="/board/list?category=베스트"/>"><span>베스트</span></a></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=여성의류"/>"><span>여성의류</span></a>
			<ul>
				<li class='has-sub'><a href='#'><span>Product 1</span></a></li>
				<li class='has-sub'><a href='#'><span>Product 2</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=남성의류"/>"><span>남성의류</span></a>
			<ul>
				<li class='has-sub'><a href='#'><span>Product 1</span></a></li>
				<li class='has-sub'><a href='#'><span>Product 2</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=패션잡화"/>"><span>패션잡화</span></a>
			<ul>
				<li class='has-sub'><a href='#'><span>Product 1</span></a></li>
				<li class='has-sub'><a href='#'><span>Product 2</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=뷰티"/>"><span>뷰티</span></a>
			<ul>
				<li class='has-sub'><a href='#'><span>Product 1</span></a></li>
				<li class='has-sub'><a href='#'><span>Product 2</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=스포츠패션"/>"><span>스포츠패션</span></a>
			<ul>
				<li class='has-sub'><a href='#'><span>Product 1</span></a></li>
				<li class='has-sub'><a href='#'><span>Product 2</span></a></li>
			</ul></li>
		<li class='last'><a href="<c:url value="/board/list?category=해외배송"/>"><span>해외배송</span></a></li>
	</ul>
</div>
