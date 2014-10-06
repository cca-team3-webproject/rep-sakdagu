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

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script>
	    // 문서 객체 추가 함수
	    var moveBag = function () {
	            // 요소를 생성하여 삽입
	            $('.bag').css({
	            	top: '60%', left: '75%', position: 'fixed', width: '35%'
	            });
	    };
	    var reBag = function () {
            // 요소를 생성하여 삽입
            $('.bag').css({	top: '45%', left: '50%'}
	            );
    };
    
        // 문서가 로드되면 호출. $(document).ready(function(){});과 동일    
        $(function () {
            // 최초 문서 객체들을 추가
                var documentHeight = $(document).height();
            
            // 스크롤 이벤트를 처리할 핸들러 등록
            $(window).scroll(function () {
                var scrollHeight = $(window).scrollTop() + $(window).height();
                // 문서 아래까지 스크롤되면 문서 객체 추가
                if (scrollHeight*2 > documentHeight) {
                    moveBag();
                }
                else
                {
                	reBag();
                };
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
					<caption class="location">
						<a href="<c:url value="/board/list?category=${board.category}"/>">
							${board.category}</a> &gt;<br> <a
							href="<c:url value="/board/list?category=${board.category}&subCategory=${board.subCategory}"/>">
							<b>${board.subCategory}</b>
						</a>
					</caption>
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
