<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="styles.css">
<link rel="stylesheet" href="<c:url value="/css/menubar.css"/>">
<script src="http://code.jquery.com/jquery-latest.min.js"
	type="text/javascript"></script>
<script src="script.js"></script>
<%-- request scope 속성에 에러메시지가 있으면 출력한다. --%>

<div id='cssmenu'>
	<ul>
		<li><a href="<c:url value="/board/list?category=베스트"/>"><span>베스트</span></a></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=여성의류"/>"><span>여성의류</span></a>
			<ul>
				<li class='has-sub'><a href="<c:url value="/board/list?category=여성의류&subCategory=전체"/>"><span>전체</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=여성의류&subCategory=상의"/>"><span>상의</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=여성의류&subCategory=하의"/>"><span>하의</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=여성의류&subCategory=원피스/세트"/>"><span>원피스/세트</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=여성의류&subCategory=아우터"/>"><span>아우터</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=여성의류&subCategory=언더웨어"/>"><span>언더웨어</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=남성의류"/>"><span>남성의류</span></a>
			<ul>
				<li class='has-sub'><a href="<c:url value="/board/list?category=남성의류&subCategory=전체"/>"><span>전체</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=남성의류&subCategory=상의"/>"><span>상의</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=남성의류&subCategory=하의"/>"><span>하의</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=남성의류&subCategory=정장/세트"/>"><span>정장/세트</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=남성의류&subCategory=아우터"/>"><span>아우터</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=남성의류&subCategory=언더웨어"/>"><span>언더웨어</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=패션잡화"/>"><span>패션잡화</span></a>
			<ul>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=전체"/>"><span>전체</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=여성신발"/>"><span>여성신발</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=남성신발"/>"><span>남성신발</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=운동화/캐주얼"/>"><span>운동화/캐주얼</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=가방/지갑"/>"><span>가방/지갑</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=소품/선글라스"/>"><span>소품/선글라스</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=패션잡화&subCategory=시계/쥬얼리"/>"><span>시계/쥬얼리</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=뷰티"/>"><span>뷰티</span></a>
			<ul>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=전체"/>"><span>전체</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=기초/기능성케어"/>"><span>기초/기능성케어</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=메이크업/클렌징"/>"><span>메이크업/클렌징</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=헤어/바디"/>"><span>헤어/바디</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=향수/미용"/>"><span>향수/미용</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=남성화장품"/>"><span>남성화장품</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=네일케어"/>"><span>네일케어</span></a></li>
			</ul></li>
		<li class='active has-sub'><a href="<c:url value="/board/list?category=스포츠패션"/>"><span>스포츠패션</span></a>
			<ul>
				<li class='has-sub'><a href="<c:url value="/board/list?category=스포츠패션&subCategory=전체"/>"><span>전체</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=스포츠의류"/>"><span>스포츠의류</span></a></li>
				<li class='has-sub'><a href="<c:url value="/board/list?category=뷰티&subCategory=스포츠슈즈"/>"><span>스포츠슈즈</span></a></li>
			</ul></li>
		<li class='last'><a href="<c:url value="/board/list?category=해외배송"/>"><span>해외배송</span></a></li>
	</ul>
</div>
