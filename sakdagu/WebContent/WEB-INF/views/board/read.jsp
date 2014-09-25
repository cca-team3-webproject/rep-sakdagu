<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글 보기</title>
	<link rel="stylesheet" href="../css/board.css">
	<script src="../js/board.js"></script>
   <link rel="stylesheet" href="../css/dukeshop.css">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="/side-bar.jsp"/>
	<div class="boardpage">
		<table id="readtable" class="maintable">
			<caption>게시글 보기</caption>
			<thead>
				<tr>
					<th>제 목</th>
					<td class="title" colspan="5">${board.title}</td>
				</tr>
				<tr>
					<th>글쓴이</th>
					<td class="writer">${board.writer}</td>
					<th>조회</th>
					<td class="readcount">${board.readCount}</td>
					<th>작성일</th>
					<td class="regdate">${board.regDate}</td>
				</tr>
			</thead> 
			<tbody>
				<tr>
					<td class="contents" colspan="6">${board.contents}</td>
				</tr>
			</tbody>
		</table>
		<div class="buttonbar">
		

			<input type="button" value="목록" onclick="goUrl('list?pageNumber=${currentPageNumber}&searchType=${param.searchType}&searchText=${param.searchText}');">
			<input type="button" value="답글" onclick="goUrl('replyForm?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
			<input type="button" value="수정" onclick="goUrl('updateForm?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
			<input type="button" value="삭제" onclick="deleteCheck('remove?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
		</div>
	</div>
	</div>
	</div>
</body>
</html>