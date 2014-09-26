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

				<table id="writetable" class="maintable">
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
						<c:if test="${param.category!='해외배송'}">
							<tr>
								<th>세부 카테고리</th>
								<td><select name="subCategory">
										<c:choose>
											<c:when
												test="${param.category=='여성의류' || empty param.category}">
												<option value="상의">상의</option>
												<option value="하의">하의</option>
												<option value="원피스/세트">원피스/세트</option>
												<option value="아우터">아우터</option>
												<option value="언더웨어">언더웨어</option>
											</c:when>
											<c:when test="${param.category=='남성의류'}">
												<option value="상의">상의</option>
												<option value="하의">하의</option>
												<option value="정장/세트">정장/세트</option>
												<option value="아우터">아우터</option>
												<option value="언더웨어">언더웨어</option>
											</c:when>
											<c:when test="${param.category=='패션잡화'}">
												<option value="여성신발">여성신발</option>
												<option value="남성신발">남성신발</option>
												<option value="운동화/캐쥬얼화">운동화/캐쥬얼화</option>
												<option value="가방/지갑">가방/지갑</option>
												<option value="소품/선글라스">소품/선글라스</option>
												<option value="시계/쥬얼리">시계/쥬얼리</option>
											</c:when>
											<c:when test="${param.category=='뷰티'}">
												<option value="">기초/기능성케어</option>
												<option value="">메이크업/클렌징</option>
												<option value="">헤어/바디</option>
												<option value="">향수/미용</option>
												<option value="">남성화장품</option>
												<option value="">네일케어</option>
											</c:when>
											<c:when test="${param.category=='스포츠패션'}">
												<option value="스포츠의류">스포츠의류</option>
												<option value="스포츠슈즈">스포츠슈즈</option>
											</c:when>
										</c:choose>
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
				<form name="writeForm" action="write" method="POST"
					onsubmit="return boardWriteCheck(writeForm);">
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