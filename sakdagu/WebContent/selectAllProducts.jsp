<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, casestudy.business.domain.Product"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 리스트</title>
    <link rel="stylesheet" href="<c:url value="/css/body.css"/>">
</head>
<body>

    <jsp:useBean id="productList" type="casestudy.business.domain.Product[]" scope="request"/>

    <div class="tableContainer">
        <div class="tableRow">
            <c:import url="top-bar.jsp"/>
            </div><div class="tableRow">
			<!-- START of main content-->
            <div class="main">
                <h4>[상품 리스트 정보]</h4>
                <table id="allproductlist">
                    <tr>
                        <th class="productno">번호</th>
                        <th class="productname">제품명</th>
                        <th class="productprice">판매가격</th>
                        <th class="productimage">제품사진</th>
                        <th class="productcompany">제조사</th>
                    </tr>

                    <c:forEach items="${productList}" var="product" varStatus="loopStatus" >
                    <tr>
                        <td class="productno">${loopStatus.count }</td>
                        <td class="productname">
                            <a href="product?action=select&productID=${product.productID}">${product.productID}</a>
                        </td>
                        <td class="productprice">${product.price2}</td>
                        <td class="productimage">
                            <a href="product?action=select&productID=${product.productID}">
                                <img src="images/${product.photoDir}">
                            </a>
                        </td>
                        <td class="productcompany">${product.company}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            <!-- END of main content-->
        </div>
    </div>        
</body>
</html>