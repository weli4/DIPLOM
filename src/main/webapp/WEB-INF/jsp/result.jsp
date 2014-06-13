<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="row fill">


    <c:forEach items="${project.stages}" var="stage">
        <div class="stage worked">
            <div class="info conception">
                <span>${stage.name}</span>
                <i class="fa fa-times-circle pull-right"></i>
                <c:forEach items="${stage.processes}" var="process">
                <div class="stage_process">
                    Процессы:
                </div>
                </c:forEach>
                <div class="results">
                    <span class="show">Результаты</span>
                    <div class="data">
                        <p>Результат1</p>
                        <p>Результат2</p>
                        <p>Результат3</p>
                        <p>Результат4</p>
                    </div>
                </div>
            </div>

        </div>
    </c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>