<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<BR>
<div align="right">
    <div>
        <fmt:message key="button.locale"/>:
            <c:url var="englishLocaleUrl" value="${urlBase}">
                <c:param name="locale" value="en" />
				<c:forEach items="${param}" var="pageParam">
					<c:if test="${pageParam.key!='locale'}" >
					<c:param name="${pageParam.key}" value="${pageParam.value}" />
					</c:if>                	
                </c:forEach>                
            </c:url>
            <c:url var="spanishLocaleUrl" value="${urlBase}">
                <c:param name="locale" value="es" />
				<c:forEach items="${param}" var="pageParam">
					<c:if test="${pageParam.key!='locale'}" >
					<c:param name="${pageParam.key}" value="${pageParam.value}" />
					</c:if>                	
                </c:forEach>                
            </c:url>
        
            <a href='<c:out value="${englishLocaleUrl}"/>'><fmt:message key="locale.english"/></a>
            <a href='<c:out value="${spanishLocaleUrl}"/>'><fmt:message key="locale.spanish"/></a>
    </div>
    
    <div>&nbsp;</div>
    <div><fmt:message key="site.footer"/></div>                
</div>