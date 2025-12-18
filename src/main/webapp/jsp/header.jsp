<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<header style="display:flex;justify-content:flex-end;align-items:center;background-color:#002244;padding:10px 20px;">
    <div style="margin-right:auto;">
        <a href="/controller?command=home" style="color:white;text-decoration:none;font-weight:bold;"><fmt:message
            key="ui.artTelecom"/></a>
    </div>
    <ul style="display:flex;list-style:none;margin:0;padding:0;gap:20px;align-items:center;">
        <c:if test="${sessionScope.username == null}">
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="login"/>
                    <input type="submit" class="header-button" value='<fmt:message key="ui.login"/>'/>
                </form>
            </li>
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="check_reg_status"/>
                    <input type="submit" class="header-button" value='<fmt:message key="ui.registration"/>'/>
                </form>
            </li>
        </c:if>
        <li>
            <a href="controller?command=change_language&language=ru"
               style="color:${sessionScope.language == 'ru' ? '#FFD700' : 'white'};
                       font-weight:${sessionScope.language == 'ru' ? 'bold' : 'normal'};
                       text-decoration:none;">RU
            </a>
        </li>
        <li>
            <a href="controller?command=change_language&language=by"
               style="color:${sessionScope.language == 'by' ? '#FFD700' : 'white'};
                       font-weight:${sessionScope.language == 'by' ? 'bold' : 'normal'};
                       text-decoration:none;">BY
            </a>
        </li>
        <li>
            <a href="controller?command=change_language&language=en"
               style="color:${sessionScope.language == 'en' ? '#FFD700' : 'white'};
                       font-weight:${sessionScope.language == 'en' ? 'bold' : 'normal'};
                       text-decoration:none;">EN
            </a>
        </li>
    </ul>
</header>