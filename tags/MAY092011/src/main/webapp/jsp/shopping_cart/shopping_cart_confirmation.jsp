<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<SCRIPT>
	alert("${requestScope.name} <fmt:message key='shopping.cart.success.add'/>");
	location.href='${sessionScope.landingPageURL}';
</SCRIPT>

<P><fmt:message key="label.not.redirect.msg"/><a href="${pageScope.landingPageURL}"><fmt:message key="button.click.here"/></a></P>