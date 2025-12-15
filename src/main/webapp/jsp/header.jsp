<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<header style="display:flex;justify-content:flex-end;align-items:center;background-color:#002244;padding:10px 20px;">
    <div style="margin-right:auto;">
        <a href="/controller?command=home" style="color:white;text-decoration:none;font-weight:bold;">АртТелеком</a>
    </div>
    <ul style="display:flex;list-style:none;margin:0;padding:0;gap:20px;align-items:center;">
    <c:if test="${sessionScope.username == null}">
        <li>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="login"/>
                <input type="submit" value='<fmt:message key="ui.login"/>'/>
            </form>
        </li>
        <li>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="check_reg_status"/>
                <input type="submit" value='<fmt:message key="ui.registration"/>'/>
            </form>
        </li>
    </c:if>
    <a href="#" style="color:white;margin-left:20px;text-decoration:none;font-weight:bold;">RU</a> |
    <a href="#" style="color:white;margin-left:20px;text-decoration:none;font-weight:bold;">BY</a> |
    <a href="#" style="color:white;text-decoration:none;font-weight:bold;">EN</a>
<%--        <li>--%>
<%--            <form action="controller" method="get">--%>
<%--                <input type="hidden" name="command" value="language"/>--%>
<%--                <input type="hidden" name="language" value='<fmt:message key="ui.language"/>'/>--%>
<%--                <input type="submit" value="EN/RU"/>--%>
<%--            </form>--%>
<%--        </li>--%>
    </ul>
</header>