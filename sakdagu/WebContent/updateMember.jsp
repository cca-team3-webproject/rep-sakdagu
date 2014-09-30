<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="css/dukeshop.css">
</head>
<body>

    <%-- <jsp:useBean id="loginMember" class="casestudy.business.domain.Member" scope="request" />
 --%>
    <div class="tableContainer">
        <div class="tableRow">
           <c:import url="top-bar.jsp"/> 
            <!-- START of main content-->
            <div class="main">
                <h4>[회원 정보 수정]</h4>
                <form action="member?action=update" method="POST">
                    <table class="registertable">
                        <tr>
                            <td class="label">회원ID :</td>
                            <td>${ loginMember.memberID }
                            <input type="hidden" name="memberID" value="${ loginMember.memberID }"></td>
                        </tr>
                        <tr>
                            <td class="label">비밀번호 :</td>
                            <td><input type="password" name="password" size="20" maxlength="10" value="${ loginMember.password }"></td>
                        </tr>
                        <tr>
                            <td class="label">이름 :</td>
                            <td><input type="text" name="name" size="20" maxlength="20" value="${ loginMember.name }"></td>
                        </tr>
                        <tr>
                            <td class="label">이메일 :</td>
                            <td><input type="text" name="email" size="30" maxlength="60" value="${ loginMember.email }"></td>
                        </tr>
                        <tr>
                            <td class="label">전화번호 :</td>
                            <td><input type="text" name="tel" size="20" maxlength="20" value="${ loginMember.tel }"></td>
                        </tr>
                        <tr>
                            <td class="label">우편번호 :</td>
                            <td><input type="text" name="zipcode" size="15" maxlength="7" value="${ loginMember.zipcode }"></td>
                        </tr>
                        <tr>
                            <td class="label">주소 :</td>
                            <td><input type="text" name="address" size="50" maxlength="70" value="${ loginMember.address }"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="정보수정"> <input type="reset" value="취소"></td>
                        </tr>
                    </table>
                </form>    
            </div>
            <!-- END of main content-->
        </div>
    </div>                
</body>
</html>
