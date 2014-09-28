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
			<c:import url="/top-bar.jsp" />
			<!-- START of main content-->
		</div>
		<div class="tableRow">
			<div class="boardpage">

				<table class="maintable">
					<caption>게시글 입력</caption>
					<thead>
						<tr>
							<th>카테고리</th>
							<td>
								<form name="categoryForm" action="writeForm" method="GET">
									<select name="category" onchange="submit();">
										<option value="여성의류"
											<c:if test="${param.category eq '여성의류'}"> selected="selected"</c:if>>여성의류</option>
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
									</select>
								</form>
							</td>
						</tr>
					</thead>
				</table>
				<form name="writeForm" action="write" method="POST"
					onsubmit="return boardWriteCheck(writeForm);"
					enctype="multipart/form-data">
					<input type="hidden" name="category" value="${param.category}">
					<table id="writetable" class="maintable">
						<thead>
							<c:if test="${param.category!='해외배송'}">
								<tr>
									<th>세부 카테고리</th>
									<td><select name="subCategory">
											<c:forEach items="${subCategoryList}" var="subCategoryitem">
												<option value="${subCategoryitem}">${subCategoryitem}</option>
											</c:forEach>
									</select></td>
								</tr>
							</c:if>
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
							<tr>
								<th class="label">사 진</th>
								<td><input type="file" name="photoDir"></td>
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