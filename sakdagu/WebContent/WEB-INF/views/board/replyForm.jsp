<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 등록</title>
<link rel="stylesheet" href="<c:url value="/css/topbar.css"/>">
<link rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link rel="stylesheet" href="<c:url value="/css/menubar.css"/>">
<script src="../ckeditor/ckeditor.js"></script>
<script src="../js/board.js"></script>
   <link rel="stylesheet" href="../css/body.css">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="/top-bar.jsp"/>
	<div class="boardpage">
		<form name="writeForm"
			action="reply?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&masterNum=${board.masterNum}&replyOrder=${board.replyOrder}&replyStep=${board.replyStep}"
			method="POST" onsubmit="return boardWriteCheck(writeForm);">
			<table id="updatetable" class="maintable">
				<caption>답글 등록</caption>
				<thead>
					<tr>
						<th>제 목</th>
						<td><input class="titleinput" type="text" name="title"
							maxlength="100" value="Re: ${board.title}"></td>
					</tr>
					<tr>
						<th>글쓴이</th>
						<td>${sessionScope.loginMember.name}
						<input class="writerinput" type="hidden" name="writer" value="${sessionScope.loginMember.name}"
							maxlength="20"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2"><textarea id="editor" class="contentsinput"
								name="contents"><p></p><br><hr><div>${board.contents}</div></textarea>
							<script>
								// Replace the <textarea id="editor1"> with a CKEditor
								// instance, using default configuration.
								CKEDITOR.replace('editor');
							</script>
							</td>
					</tr>
				</tbody>
			</table>
			<div class="buttonbar">
				<input type="submit" value="답글 등록"> <input type="button"
					value="취소"
					onclick="goUrl('read?pageNumber=${currentPageNumber}&num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}');">
			</div>
		</form>
	</div>
	</div>
	</div>
	
</body>
</html>