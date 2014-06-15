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
                        <div class="row" style="font-size:70%">
                            <i style="font-size:130%; position:relative; right:20px; cursor:pointer" class="close fa fa-times-circle pull-right"></i>
                            <div class="col-md-6">
                                <c:forEach items="${stage.process}" var="process">
                                    <p style="<c:if test="${process.t}">color:green</c:if>
                                       <c:if test="${not process.t}">color:red</c:if>"><strong>${process.name}</strong></p>
                                            <c:forEach items="${process.outputList}" var="out">
                                        <div style="positinon:relative; right:20px;">-${out}</div>
                                    </c:forEach>
                                </c:forEach>
                            </div>
                            <div class="col-md-6">
                                <strong>Выходы стадии</strong>
                                <c:forEach items="${stage.outputs}" var="out">
                                    <div style="positinon:relative; right:20px;">-${out}</div>
                                </c:forEach>   
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    <br/>
    <c:forEach items="${project.stages}" var="stage">
        <div class="stage worked">
            <div class="info" style="z-index:1;">
                <span>${stage.name}</span>             


                <c:forEach items="${stage.process}" var="process">
                    <c:forEach items="${process.outputList}" var="out">

                        <p style="max-width:300px;">-${out}</p>

                    </c:forEach>             
                </c:forEach>                  
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
            var isTooltipOpened = false;
            $.widget("custom.tooltipX", $.ui.tooltip, {
                options: {
                    autoShow: true,
                    autoHide: true
                },
                _create: function() {
                    this._super();
                    if (!this.options.autoShow) {
                        this._off(this.element, "mouseover focusin");
                    }
                },
                _open: function(event, target, content) {
                    this._superApply(arguments);

                    if (!this.options.autoHide) {
                        this._off(target, "mouseleave focusout");
                    }
                }
            });

            $('.stage_process').each(function() {
                $(this).attr('title', $(this).find(".outs").remove().html());
            });
            $('.stage_process').tooltipX({
                autoHide: false,
                autoShow: false
            });
            tmp = true;
            $(".stage_process").click(function()
            {

                if (tmp)
                {
                    $(this).tooltipX("open");
                    $(".slider-assets").on("slidestart", function(event, ui) {
                        $(ui.handle).css("z-index", "1082");
                    });

                    $(".slider-assets").on("slidestop", function(event, ui) {
                        $(ui.handle).css("z-index", "1080");
                    });
                    tmp = false;
                }
                else {
                    $(this).tooltipX("close");
                    tmp = true;
                }


            });

        });
    </script>
    <style>
        label {
            display: inline-block;
            width: 5em;
        }
    </style>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>