<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/style.css" rel="stylesheet">
        <link href="resources/css/jquery-ui.custom.min.css" rel="stylesheet">
        <link href="resources/css/font-awesome.min.css" rel="stylesheet">
        <script src="resources/js/jquery.min.js"></script>
        <script src="resources/js/bootstrap.min.js"></script>
        <script src="resources/js/jquery-ui.custom.js"></script>
        <title></title>
    </head>
    <body>
        <div class="container">


            <form action="login.form" method="post">
                <table>
                    <tr><td>Username</td><td><input type="text" name="uname"    value="${uname}"></td></tr>
                    <tr><td>Password</td><td><input type="text" name="pwd"   value="${pwd}"></td></tr>
                    <tr><td colspan="2"> <input type="submit" value="Login"> </td></tr>
                </table>
            </form>



            <%@ include file="/WEB-INF/jsp/footer.jsp" %>