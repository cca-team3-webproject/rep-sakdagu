<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<link rel="stylesheet" href="../css/board.css">
<link rel="stylesheet" href="../css/dukeshop.css">
<script src="../js/board.js"></script>
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="/top-bar.jsp" />
		</div>
		<div class="tableRow">
			<div class="boardpage">
				<table id="listtable" class="maintable">
					<thead>
						<tr>
							<th class="location" colspan="2"><a
								href="<c:url value="/board/list?category=${param.category}"/>">
									${param.category}</a> &gt;<br> <a
								href="<c:url value="/board/list?category=${param.category}"/>">
									<c:choose>
										<c:when test="${empty param.subCategory}">
											<b>전체</b>
										</c:when>
										<c:otherwise>
								전체
								</c:otherwise>
									</c:choose>
							</a> <c:forEach items="${subCategoryList}" var="subCategory">
									<a
										href="<c:url value="/board/list?category=${param.category}&subCategory=${subCategory}"/>">
										<c:choose>
											<c:when test="${param.subCategory eq subCategory}">
												<b>${subCategory}</b>
											</c:when>
											<c:otherwise>
											${subCategory}
								</c:otherwise>
										</c:choose>

									</a>
								</c:forEach></th>
						</tr>
					</thead>
					<tbody>

						<tr>
							<c:choose>
								<c:when test="${empty boardList }">
									<td colspan="5">등록된 게시물이 없습니다.</td>
								</c:when>
								<c:otherwise>

									<c:forEach items="${boardList}" var="board"
										varStatus="loopState">
										<c:if test="${loopState.count % 2 eq 1}">
											<tr>
										</c:if>
										<td>
											<table>

												<tr>
													<td class="image" rowspan="6"><a
														href="read?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&category=${param.category}&subCategory=${param.subCategory}">
															<img src="<c:url value="/images/${board.photoDir}"/>">
													</a></td>
												</tr>
												<tr>
													<%-- <td class="num">${board.num}</td> --%>

													<td class="title"><a
														href="read?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&category=${param.category}&subCategory=${param.subCategory}">${board.title}</a>
													</td>
												</tr>
												<tr>
													<td class="writer">${board.writer}</td>
												</tr>
												<tr>
													<td class="regdate">${board.regDate}</td>
												</tr>
												<tr>
													<td class="readcount">${board.readCount}</td>
												</tr>
												<tr>
													<td class="category"><a
														href="<c:url value="/board/list?category=${board.category}"/>">
															${board.category}</a> <c:if
															test="${not empty board.subCategory}">
															&gt;<br>
															<a
																href="<c:url value="/board/list?category=${board.category}&subCategory=${board.subCategory}"/>">
																${board.subCategory}</a>
														</c:if></td>
												</tr>
											</table>
										</td>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tr>
					</tbody>

					<tfoot>
						<tr>
							<td id="pagenavigator" colspan="5"><c:if
									test="${currentPageNumber > 1}">
									<a
										href="list?pageNumber=${startPageNumber-1}&searchType=${param.searchType}&searchText=${param.searchText}&category=${param.category}&subCategory=${param.subCategory}">이전</a>
								</c:if> <c:forEach begin="${startPageNumber}" end="${endPageNumber}"
									var="pageNumber">
									<c:choose>
										<c:when test="${pageNumber eq currentPageNumber}">
											<a class="pagenumber currpage">${pageNumber}</a>
										</c:when>
										<c:otherwise>
											<a class="pagenumber"
												href="list?pageNumber=${pageNumber}&searchType=${param.searchType}&searchText=${param.searchText}&category=${param.category}&subCategory=${param.subCategory}">${pageNumber}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach> <c:if test="${endPageNumber < totalPageCount}">
									<a
										href="list?pageNumber=${endPageNumber+1}&searchType=${param.searchType}&searchText=${param.searchText}&category=${param.category}&subCategory=${param.subCategory}">다음</a>
								</c:if></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
</body>
</html>