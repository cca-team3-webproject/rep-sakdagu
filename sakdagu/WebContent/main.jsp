<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <%
	response.sendRedirect("product?action=select");
/* 	response.sendRedirect("index.jsp"); */
%> --%>

<c:redirect url="product">
	<c:param name="action">select</c:param>
</c:redirect>
