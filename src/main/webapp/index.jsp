<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<!DOCTYPE html>
<html>
<head>
    <title>ArtTelecom - Greetings</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="jsp/header.jsp"/>
<div class="welcome">
    <h1>🎆 <fmt:message key="ui.welcome"/> 🎆</h1>
    <h1>🎄 <fmt:message key="ui.newYear"/> 🎄</h1>
    <p><fmt:message key="ui.sales"/></p>
</div>

<div class="tariffs">
    <div class="tariff-card">
        <h3>Базовый</h3>
        <p>Простой и доступный тариф</p>
        <a href="jsp\tariff-basic.jsp">Подробнее</a>
    </div>
    <div class="tariff-card">
        <h3>Премиум</h3>
        <p>Максимум возможностей</p>
        <a href="jsp\tariff-premium.jsp">Подробнее</a>
    </div>
    <div class="tariff-card">
        <h3>Семейный</h3>
        <p>Для всей семьи</p>
        <a href="jsp\tariff-family.jsp">Подробнее</a>
    </div>
</div>
<jsp:include page="jsp/footer.jsp"/>
</body>
</html>
