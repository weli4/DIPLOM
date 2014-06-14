<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="row fill">


    <c:forEach items="${project.stages}" var="stage">
        <div class="stage worked">
            <div class="info">
                <span>${stage.name}</span>             

                <div class="stage_process">
                    <c:forEach items="${stage.process}" var="process">

                        <div class="process" style="<c:if test="${process.t}">background-color:green</c:if>
                             <c:if test="${not process.t}">background-color:red</c:if>">
                            ${process.name}
                            <div class="description hide">
                                ${process.outputs}
                            </div>
                        </div>
                    </c:forEach>
                    <div class="outs">
                        <c:forEach items="${stage.process}" var="process">
                            ${process.name}-${process.outputs} <br/>
                            <c:forEach items="${process.outputs}" var="out">
                                ${out} <br/>
                            </c:forEach>
                        </c:forEach>
                    </div>


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
        </div>
    </c:forEach>

    <script>
        $.widget("ui.tooltip", $.ui.tooltip, {
            options: {
                content: function() {
                    return $(this).prop('title');
                }
            }
        });

        $(function() {
            $('.stage_process').attr('title', $('.outs').remove().html())
            $(document).tooltip();
        });
    </script>
    <style>
        label {
            display: inline-block;
            width: 5em;
        }
    </style>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>