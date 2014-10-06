<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty boardList }">
	<table id="listtable" class="maintable">
		<tbody>
			<tr>
				<c:forEach items="${boardList}" var="board" varStatus="loopState">
					<c:if test="${loopState.count % 3 eq 1}">
						<tr>
					</c:if>
					<td>
						<table>
							<tr>
								<td><a
									href="read?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&category=${param.category}&subCategory=${param.subCategory}">
										<em class="image"><img
											src="<c:url value="/board/img?boardNum=${board.num}"/>">
									</em> <br> <%-- <td class="num">${board.num}</td> --%> <br>
										<br> <em class="title">${board.title} </em>

								</a><br> <em class="category"><a
										href="<c:url value="/board/list?category=${board.category}"/>">
											${board.category}</a> <c:if test="${not empty board.subCategory}">
															&nbsp;&gt;&nbsp;
																<a
												href="<c:url value="/board/list?category=${board.category}&subCategory=${board.subCategory}"/>">
												${board.subCategory}</a>
										</c:if></em></td>
							</tr>
						</table>
					</td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</c:if>