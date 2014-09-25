<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
	<title>Duke's Store</title>
    <link rel="stylesheet" href="css/dukeshop.css">
</head>
<body>
    <div class="tableContainer">
        <div class="tableRow">
            <c:import url="top-bar.jsp"/>
        </div>
        <div class="tableRow">
            <!-- START of main content-->
            <c:import url="content.jsp"/>
        </div>
    </div>
</body>
</html>
