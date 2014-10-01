<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 등록</title>
    <link rel="stylesheet" href="<c:url value="/css/dukeshop.css"/>">
</head>
<body>
    <div class="tableContainer">
        <div class="tableRow">
            <c:import url="top-bar.jsp"/>
            </div><div class="tableRow">
			<!-- START of main content-->
            <div class="main">
                <h4>[상품 등록]</h4>
                <form action="product?action=register2" method="POST"  enctype="multipart/form-data">
                <input type="hidden" name="memberID" value="${ loginMember.memberID }">
                    <table class="registertable">
                        <tr>
                            <td class="label">상품ID :</td>
                            <td><input type="text" name="productID" size="20" maxlength="15"></td>
                        </tr>
                                                <tr>
                            <td class="label">제조사ID :</td>
                            <td><input type="text" name="mallID" size="20" maxlength="15"></td>
                        </tr>
  
                        <tr>
                            <td class="label">제품명 :</td>
                            <td><input type="text" name="productName" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">제조사명 :</td>
                            <td><input type="text" name="company" size="30" maxlength="60"></td>
                        </tr>
                        <tr>
                            <td class="label">일반가격 :</td>
                            <td><input type="text" name="price1" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">판매가격 :</td>
                            <td><input type="text" name="price2" size="15" maxlength="7"></td>
                        </tr>                        <tr>
                            <td class="label">카드할부여부 :</td>
                            <td><input type="radio" name="installment" value="1" checked >가능  <input type="radio" name="installment" value="0" >불가능</td>
                        </tr>
                        <tr>
                            <td class="label">키워드 :</td>
                            <td><input type="text" name="keyword" size="15" maxlength="7"></td>
                        </tr>
                        <tr>
                            <td class="label">설명 :</td>
                            <td><input type="text" name="detail" size="50" maxlength="70"></td>
                        </tr>                        <tr>
                            <td class="label">사진 :</td>
                            <td><input type="file" name="photoDir" ></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="상품등록"> <input type="reset" value="취소"></td>
                        </tr>
                    </table>
                </form>    
            </div>
            <!-- END of main content-->
        </div>
    </div>
</body>
</html>