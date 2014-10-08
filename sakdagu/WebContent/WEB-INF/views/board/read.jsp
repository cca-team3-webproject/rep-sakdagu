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
<script src="<c:url value="/js/board.js"/>"></script>
<script type="text/javascript">
	var buy = function(obj) {
		/* var str = $("form").serialize(); */
		var sum = $('#sum').text();
		if (confirm('총 금액 ' + sum + ' 구매 하시겠습니까?')) {
			alert('=======================\n구매가 완료되었습니다.');
			return false;
		} else {
			location.href = '/sakdagu/cart.jsp?sum=' + sum
			/* + '&' + str */;
		}
	};

	var sum = function() {
		var s = 0;
		for (var i = 0; i < $('textarea').length; i++) {
			s = s + Number($('textarea')[i].value);
		}
		$('#sum').html(s + '원');
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
			}
		});

	};

	var selectOption = function(obj) {
		var productIndex = $('select[name=selectedProduct]').val();
		var optionIndex = obj.selectedIndex;

		if (optionIndex > 0) {
			jQuery
					.ajax({
						type : "GET",
						url : 'selectOption',
						data : {
							"boardNum" : '${param.num}',
							"productID" : productIndex,
							"optionID" : optionIndex
						},
						dataType : 'text',
						success : function(text) {
							var opt = JSON.parse(text);
							var x = '<tr><td> 선택 '
									+ optionIndex
									+ ' - '
									+ opt.optionTitle
									+ '<br>\\ <span name="price'+optionIndex+'">'
									+ opt.price2 + '</span>';
							var y = 'x <select name="Quantity'
									+ optionIndex
									+ '" onchange="selectQuantity(this);"'
									+ 'id="select_box">'
									+ '<c:forEach begin="1" end="10" varStatus="v">'
									+ '<option value="${v.count}">${v.count}</option></c:forEach>'
									+ '</select>개 = </td><td><textarea row="1" readonly="readonly" id="optionQuantity'+optionIndex+'">'
									+ opt.price2 + '</textarea>원	</td></tr>';
							$('#calc').prepend(x + y);
							sum();
						}
					});
		}
	};

	function selectQuantity(obj) {
		var radios = obj.value;
		var price = $(obj).prev().text();
		var name = $(obj).attr('name');

		$('#option' + name).html(radios * price);
		sum();
	};
</script>

<script>
	// 문서가 로드되면 호출. $(document).ready(function(){});과 동일    
	$(function() {
		// 최초 문서 객체들을 추가
		var moveBag = function() {
			$('.bag').css({
				top : $('body > div > div:nth-child(1)').height(),
				left : $('.maintable').width() * 1.25,
				position : 'fixed'
			});
		};
		moveBag();
		// 스크롤 이벤트를 처리할 핸들러 등록
		$(window).scroll(function() {
			// 문서 아래까지 스크롤되면 문서 객체 추가
			moveBag();
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
										onclick="goUrl('list?pageNumber=${currentPageNumber}&category=${param.category}&searchText=${param.searchText}');">

									<c:if test="${isAdmin}">

										<%-- <input type="button" value="답글"
											onclick="goUrl('replyForm?pageNumber=${currentPageNumber}&num=${board.num}&category=${param.category}&searchText=${param.searchText}');">
										 --%>
										<input type="button" value="수정"
											onclick="goUrl('updateForm?pageNumber=${currentPageNumber}&num=${board.num}&category=${param.category}&searchText=${param.searchText}');">
										<input type="button" value="삭제"
											onclick="deleteCheck('remove?pageNumber=${currentPageNumber}&num=${board.num}&category=${param.category}&searchText=${param.searchText}');">

									</c:if>

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
