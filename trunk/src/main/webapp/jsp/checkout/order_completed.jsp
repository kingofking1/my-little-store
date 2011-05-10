<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>
<c:if test="${!empty requestScope.KEY_FATAL}">
	<c:set var="message" scope="page" value="${requestScope.KEY_FATAL}"/>
</c:if>

<h2><fmt:message key="order.processed"/></h2>

<c:if test="${!empty requestScope.KEY_ERROR || !empty requestScope.KEY_FATAL}">
<P align="left"><B><fmt:message key="order.processed.msg"/><BR><font color="red"><c:out value="${pageScope.message}" /></font></B></P><BR>
</c:if>

<c:if test="${empty requestScope.KEY_FATAL}">
<P><fmt:message key="order.processed.success.msg"/>:</P>
<P><fmt:message key="order.number"/>: <b><BIG>${requestScope.ORDER_NUMBER}</BIG></b></P>
<P><fmt:message key="tx.number"/>: <b><BIG>${requestScope.CONFIRMATION_NUMBER}</BIG></b></P><BR>
<P><fmt:message key="thank.4.your.business"/></P><br>
</c:if>
<P title="Click here"><a href="${sessionScope.landingPageURL}"><fmt:message key="index.title"/></a></P>