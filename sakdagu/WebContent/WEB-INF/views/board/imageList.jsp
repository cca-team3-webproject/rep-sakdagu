<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sakdagu</title>
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link rel="stylesheet" href="<c:url value="/css/menubar.css"/>">

<link rel="shortcut icon" href="<c:url value="/favicon.ico"/>">
<script src="../js/board.js"></script>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script>
	// 문서 객체 추가 함수
	var appendDocument = function(i) {
		// 요소를 생성하여 삽입
		$.ajax({
			url : "images",
			type : "get",
			data : {
				"pageNumber" : i,
				"category" : '${param.category}',
				"subCategory" : '${param.subCategory}',				
				"searchText" : '${param.searchText}',				
			},
			success : function(result) {
				$('#contents').append(result);

			}
		});
	};

	// 문서가 로드되면 호출된다. $(document).ready(function(){}); 과 동일
	$(function() {

		var i = 1;
		$('.maintable table').mouseenter(function() {
			$(this).addClass('hover');
		});
		$('.maintable table').mouseleave(function() {
			$(this).removeClass('hover');
		});

		appendDocument(i++);
		// 스크롤 이벤트를 처리할 핸들러 등록
		$(window).scroll(function() {
			var scrollHeight = $(window).scrollTop() + $(window).height();
			var documentHeight = $(document).height();
			// 문서 아래까지 스크롤되면 문서 객체 추가
			if (scrollHeight == documentHeight) {
				appendDocument(i++);
			}
		});
	});
</script>
</head>
<body>
	<div class="tableContainer">
			<c:import url="/top-bar.jsp" />
			
		<div class="tableRow">
			<div class="boardpage">
	
<%-- <c:import url=""></c:import>
 --%>
				<div>
					<table id="listtable" class="maintable">
						<tbody id="contents">


						</tbody>
						<tfoot>
						
						</tfoot>
					</table>
				</div>
				</div>
		</div>
	</div>
</body>
</html>
