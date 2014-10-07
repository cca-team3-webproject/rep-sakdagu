<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link rel="stylesheet" href="<c:url value="/css/menubar.css"/>">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="<c:url value="/js/product.js"/>"></script>
<script type="text/javascript">
	var buy = function() {
		alert($('#select_box option:selected').text());
	};

	var selectProduct = function(obj) {
		var radios = obj.value;

		jQuery.ajax({
			type : "GET",
			url : 'selectProduct',
			data : {
				"boardNum" : '${param.num}',
				"productID" : radios
			},
			dataType : 'text',
			success : function(text) {
				var options = JSON.parse(text);
				for (var i = 0; i < options.length; i++) {
					var opt = options[i];
					var t = '<option value='+opt.optionID+'> 선택' + (i + 1)
							+ ' - ' + opt.optionTitle + '   \\ ' + opt.price2
							+ '</option>';
					$('select[name=selectedOption]').append(t);
				}
				;
				/* 
							[{"optionID":1,"optionTitle":"선택1","quantity":100,"price2":6900,"price1":9900},{"optionID":2,"optionTitle":"선택2","quantity":100,"price2":8900,"price1":10900},{"optionID":3,"optionTitle":"선택3","quantity":100,"price2":6900,"price1":9900},{"optionID":4,"optionTitle":"선택4","quantity":100,"price2":6900,"price1":9900},{"optionID":5,"optionTitle":"선택5","quantity":100,"price2":6900,"price1":9900}] */
			}
		});

	};

	function selectOption(obj) {
		var t = obj.options[obj.selectedIndex].text;
		var x = '<tr><td>' + t + '</td>';
		var y = '<td>x <select name="quantity" onchange="selectQuantity(this);"'
				+ +'id="select_box">'
				+ '<c:forEach begin="1" end="11" varStatus="v">'
				+ '<option value="${v.count-1}"'
				+ '<c:if test="${param.quantity eq v.count-1}"> selected="selected"</c:if>>${v.count-1}</option></c:forEach>'
				+ '</select>개 =' + '</td></tr>';
		$('#calc').append(x + y);
	};

	function selectQuantity(obj) {
		var radios = obj.value;
		location.href = '<c:url value="/board/read?num='
		+${param.num} +'&productID=' + 1 +'&optionID='
		+ 1 +'&quantity=' + radios + '"/>';
	};
</script>

<script>
	// 문서가 로드되면 호출. $(document).ready(function(){});과 동일    
	$(function() {
		// 최초 문서 객체들을 추가
		$('.bag').css({
			top : '60%',
			left : '66%',
			position : 'fixed',
			width : '35%'
		});
		var documentHeight = $(document).height();

		// 스크롤 이벤트를 처리할 핸들러 등록
		$(window).scroll(function() {
			var scrollHeight = $(window).scrollTop() + $(window).height();
			// 문서 아래까지 스크롤되면 문서 객체 추가
			if (scrollHeight * 2 > documentHeight) {
				moveBag();
			} else {
				reBag();
			}
			;
		});
	});
</script>
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="/top-bar.jsp" />
		</div>
		<div class="tableRow">
			<div class="boardpage">
				<table id="readtable" class="maintable">
					<%-- 					<caption class="location">
						<a href="<c:url value="/board/list?category=${board.category}"/>">
							${board.category}</a> &gt;<br> <a
							href="<c:url value="/board/list?category=${board.category}&subCategory=${board.subCategory}"/>">
							<b>${board.subCategory}</b>
						</a>
					</caption> --%>
					<thead>
						<tr>
							<td class="contents">${board.contents}</td>
						</tr>
					</thead>
					<tbody>
						<c:import url="readProducts.jsp" />
					</tbody>

					<tfoot>
						<tr>
							<td>
								<div class="buttonbar">
									<input type="button" value="목록"
										onclick="goUrl('list?pageNumber=${currentPageNumber}&searchType=${param.searchType}&searchText=${param.searchText}');">
									<input type="button" value="답글"
										onclick="goUrl('replyForm?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
									<input type="button" value="수정"
										onclick="goUrl('updateForm?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
									<input type="button" value="삭제"
										onclick="deleteCheck('remove?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
								</div>
							</td>
						</tr>

					</tfoot>
				</table>

			</div>
		</div>
	</div>
</body>
</html>
