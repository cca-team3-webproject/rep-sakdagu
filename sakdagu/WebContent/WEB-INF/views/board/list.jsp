<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link rel="stylesheet" href="<c:url value="/css/menubar.css"/>">
<script src="../js/board.js"></script>
</head>
<body>
	<div class="tableContainer">

		<div class="tableRow">
			<div class="boardpage">
				<table id="listtable" class="maintable">
					<caption>게시글 목록</caption>
					<thead>
						<tr>
							<th class="num">글번호</th>
							<th class="title">제 목</th>
							<th class="writer">글쓴이</th>
							<th class="regdate">작성일</th>
							<th class="readcount">조회</th>
							<th class="category">카테고리</th>
							<th class="subCategory">세부 카테고리</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty boardList }">
								<tr>
									<td colspan="7">등록된 게시물이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>

								<c:forEach items="${boardList}" var="board"
									varStatus="loopState">

									<tr>
										<td class="num">${board.num}</td>

										<td class="title"><c:forEach begin="1"
												end="${board.replyStep}">
												<c:out value="&nbsp;" escapeXml="false" />
											</c:forEach> <c:choose>
												<c:when test="${not empty sessionScope.loginMember}">
													<a
														href="read?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}">${board.title}</a>
												</c:when>
												<c:otherwise>
													<c:out value="${board.title}" />
												</c:otherwise>
											</c:choose></td>

										<td class="writer">${board.writer}</td>
										<td class="regdate">${board.regDate}</td>
										<td class="readcount">${board.readCount}</td>
										<td class="category">${board.category}</td>
										<td class="subCategory">${board.subCategory}</td>
									</tr>
								</c:forEach>

							</c:otherwise>
						</c:choose>

					</tbody>
					<tfoot>
						<tr>
							<td id="pagenavigator" colspan="7"><c:if
									test="${currentPageNumber > 1}">
									<a
										href="list?pageNumber=${startPageNumber-1}&searchType=${param.searchType}&searchText=${param.searchText}">이전</a>
								</c:if> <c:forEach begin="${startPageNumber}" end="${endPageNumber}"
									var="pageNumber">
									<c:choose>
										<c:when test="${pageNumber eq currentPageNumber}">
											<a class="pagenumber currpage">${pageNumber}</a>
										</c:when>
										<c:otherwise>
											<a class="pagenumber"
												href="list?pageNumber=${pageNumber}&searchType=${param.searchType}&searchText=${param.searchText}">${pageNumber}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach> <c:if test="${endPageNumber < totalPageCount}">
									<a
										href="list?pageNumber=${endPageNumber+1}&searchType=${param.searchType}&searchText=${param.searchText}">다음</a>
								</c:if></td>
						</tr>
					</tfoot>
				</table>
				<div class="buttonbar">
					<form name="searchForm" action="list" method="GET">
						<input type="hidden" name="category" value="${param.category}">
						<select name="searchType">
							<option value="all"
								<c:if test="${empty param.searchType}"> selected="selected"</c:if>>전체검색</option>
							<option value="title"
								<c:if test="${param.searchType eq 'title'}"> selected="selected"</c:if>>제목</option>
							<option value="writer"
								<c:if test="${param.searchType eq 'writer'}"> selected="selected"</c:if>>글쓴이</option>
							<option value="contents"
								<c:if test="${param.searchType eq 'contents'}"> selected="selected"</c:if>>내용</option>
						</select> <input id="searchinput" type="text" name="searchText"
							value="${param.searchText}" onclick="select();"> <input
							type="submit" value="검색"> <input type="button" value="목록"
							onclick="goUrl('list');">

					</form>
					<c:if test="${not empty sessionScope.loginMember}">
						<form name="writeForm" action="<c:url value="/board/writeForm"/>" method="GET">
							<select name="category">
								<option value="여성의류"
									<c:if test="${param.category eq '여성의류' || param.category eq '베스트'}"> selected="selected"</c:if>>여성의류</option>
								<option value="남성의류"
									<c:if test="${param.category eq '남성의류'}"> selected="selected"</c:if>>남성의류</option>
								<option value="패션잡화"
									<c:if test="${param.category eq '패션잡화'}"> selected="selected"</c:if>>패션잡화</option>
								<option value="뷰티"
									<c:if test="${param.category eq '뷰티'}"> selected="selected"</c:if>>뷰티</option>
								<option value="스포츠패션"
									<c:if test="${param.category eq '스포츠패션'}"> selected="selected"</c:if>>스포츠패션</option>
								<option value="해외배송"
									<c:if test="${param.category eq '해외배송'}"> selected="selected"</c:if>>해외배송</option>
							</select><input type="submit" value="글쓰기">
						</form>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>