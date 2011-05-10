<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Redirect to /authentication/logout.do -->
<c:url value="/authentication/logout.do" scope="page" var="logoutURL"/>

<div id="headerTitle"><fmt:message key="logout.title"/></div>

<SCRIPT>
	alert("${requestScope.KEY_INFO}\n<fmt:message key='logout.msg.success'/>");	
	location.href='${logoutURL}';
</SCRIPT>