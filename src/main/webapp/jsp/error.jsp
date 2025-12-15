<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="ui.errorPage"/></title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="header.jsp"/>
<section id="error" class="section">
    <div class="container">
        <div class="text-content">
            <br><br>
            <p>
                <fmt:message key="ui.error.code"/> <c:out value="${errorCode}"/>
                <c:if test="${errorCode == null}">
                    <fmt:message key="ui.errorCode500"/>
                </c:if>
            </p>
            <p>
                <c:out value="${errorMessage}"/>
                <c:if test="${errorMessage == null}">
                    <fmt:message key="ui.errorMessage500"/><br>
                    <fmt:message key="ui.tryAgain"/>
                </c:if>
            </p>
        </div>
    </div>
</section>
<jsp:include page="footer.jsp"/>
</body>
</html>
