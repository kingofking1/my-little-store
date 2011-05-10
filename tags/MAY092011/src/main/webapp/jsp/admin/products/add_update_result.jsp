<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Redirect to /products/listAllProducts.htm -->
<c:url value="/products/listAllProducts.htm" scope="page" var="listAllProductsURL">
	<c:param name="maxResults">10</c:param>
</c:url>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<P align="justify">
	<font color="red"><c:out value="${pageScope.message}" /></font>
	<c:if test="${!empty requestScope.KEY_FATAL}">
	<BR><B><fmt:message key="error.persists"/></B>
	</c:if>
</P>

<a href="${listAllProductsURL}"><fmt:message key='button.click.here'/></a>

<SCRIPT>
<c:if test="${!empty requestScope.KEY_INFO}">
	alert("${requestScope.KEY_INFO} <fmt:message key='product.success.add.update'/>");
	location.href='${listAllProductsURL}';
</c:if>
</SCRIPT>