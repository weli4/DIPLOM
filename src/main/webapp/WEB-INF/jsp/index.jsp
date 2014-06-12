<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<a href="workspace" accesskey=""> Workspace </a>
<br/>
<a href="saati" >Saati</a>
<form action="login" method="POST">
    username<input type="text" name="username"/>
    password<input type="password" name="passwor"/>
    <button type="submit">login</button>
</form>



<%@ include file="/WEB-INF/jsp/footer.jsp" %>