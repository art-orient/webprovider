<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<footer>
    <div>
        &copy; 2025 <fmt:message key="ui.artFooter"/>
    </div>
    <div>
        <fmt:message key="ui.contacts"/>:
        <a href="mailto:info@arttelecom.by" style="color:gold;text-decoration:none;">info @arttelecom.by</a>
        | <fmt:message key="ui.tel"/>: +375 (29) 123-45-67
    </div>
</footer>