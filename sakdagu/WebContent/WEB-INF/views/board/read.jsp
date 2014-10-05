<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
<link rel="stylesheet" href="../css/topbar.css">
<link rel="stylesheet" href="../css/body.css">
<script src="<c:url value="/js/product.js"/>"></script>
<script type="text/javascript">
function selectProduct(obj) {
	var radios = obj.value;

	location.href = '<c:url value="/board/read?num=' + ${param.num} +'&productID=' + radios + '"/>';
	/* 
	 var opt = '';

	 $('.optionArea').html('');
	 $(opt).appendTo('.optionArea'); */

};
function selectOption(obj) {
	var radios = obj.value;

	location.href = '<c:url value="/board/read?num=' + ${param.num} +'&productID=' + ${param.productID} +'&optionID=' + radios + '"/>';

};

function selectQuantity(obj) {
	var radios = obj.value;
	location.href = '<c:url value="/board/read?num=' + ${param.num}+'&productID=' + ${param.productID}+'&optionID=' + ${param.optionID}+'&quantity=' + radios + '"/>';
};

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
					<caption class="location">
						<a href="<c:url value="/board/list?category=${board.category}"/>">
							${board.category}</a> &gt;<br> <a
							href="<c:url value="/board/list?category=${board.category}&subCategory=${board.subCategory}"/>">
							<b>${board.subCategory}</b>
						</a>
					</caption>
					<thead>
						<tr>
							<td class="contents" colspan="4">${board.contents}</td>
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
