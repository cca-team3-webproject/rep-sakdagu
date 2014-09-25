<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
<link rel="stylesheet" href="../css/board.css">
<script src="../ckeditor/ckeditor.js"></script>
<script src="../js/board.js"></script>
<link rel="stylesheet" href="../css/dukeshop.css">
</head>
<body>
	<div class="tableContainer">
		<div class="tableRow">
			<c:import url="/side-bar.jsp" />
			<!-- START of main content-->
			<div class="boardpage">
				<form name="writeForm" action="write" method="POST"
					onsubmit="return boardWriteCheck(writeForm);">
					<table id="writetable" class="maintable">
						<caption>게시글 입력</caption>
						<thead>
							<tr>
								<th>카테고리</th>
								<td><select name="category">
										<option value="women">여성의류</option>
										<option value="man">남성의류</option>
										<option value="women">패션잡화</option>
										<option value="women">뷰티</option>
										<option value="women">스포츠패션</option>
										<option value="women">해외배송</option>
								</select></td>
							</tr>
							<tr>
								<th>제 목</th>
								<td><input class="titleinput" type="text" name="title"
									maxlength="100"></td>
							</tr>
							<tr>
								<th>판매자</th>
								<td><input class="writerinput" type="text" name="writer"
									value="${sessionScope.loginMember.name}" maxlength="20"></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="2"><textarea id="ckeditor"
										class="contentsinput" name="contents"></textarea> <script>
											CKEDITOR.replace('ckeditor');
										</script></td>
							</tr>
						</tbody>
					</table>
					<div class="buttonbar">
						<input type="submit" value="등록"> <input type="button"
							value="취소" onclick="goUrl('list');">
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
</html>