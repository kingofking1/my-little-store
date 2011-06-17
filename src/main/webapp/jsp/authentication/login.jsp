<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>
<c:if test="${!empty requestScope.KEY_FATAL}">
	<c:set var="message" scope="page" value="${requestScope.KEY_FATAL}"/>
</c:if>

<P align="justify">
	<font color="red"><c:out value="${pageScope.message}" /></font>
	<c:if test="${!empty requestScope.KEY_FATAL}">
	<BR><B><fmt:message key="error.persists"/></B>
	</c:if>
</P>
<BR>

<h2><fmt:message key="login.title"/></h2>

<form:form modelAttribute="CustomerDTO" name="loginForm" action="doLogin.do" autocomplete="true">
<TABLE>
	<TR>
		<TD><label for="email" title="Please provide your user Id"><fmt:message key="login.username"/></label></TD>
		<TD><form:input path="email" maxlength="120" size="35" /></TD>
	</TR>
	<TR>
		<TD><label for="password" title="Enter your password"><fmt:message key="login.password"/></label></TD>
		<TD><form:password path="password" maxlength="45" size="20" autocomplete="false" /></TD>
	</TR>
	<TR>
		<TD><label for="rememberMe"><fmt:message key="login.rememberMe"/></label></TD>
		<TD><input type="checkbox" name="rememberMe" id="rememberMe" value="true"/></TD>
	</TR>		
	<TR>
		<TD colspan="2"><input type="button" value="Sign In" onclick="JavaScript:doSubmit(this.form);"/>	</TD>
	</TR>
</TABLE>	
	<form:hidden path="idCustomer"/>
	<form:hidden path="firstName"/>
	<form:hidden path="middleName"/>
	<form:hidden path="lastName"/>
</form:form>
<c:url var="signUpURL" value="/authentication/registerCustomer.htm" scope="page"></c:url>
<P><fmt:message key="login.signMeUp"/>&nbsp;<a href="${pageScope.signUpURL}"><fmt:message key="login.signMeUp.now"/></a></P>

<SCRIPT>
	function doSubmit(form){
		if(trim(form.email.value).length==0){
			alert('Please enter your email');
			form.email.focus();
			return false;
		}
		if(trim(form.password.value).length==0){
			alert('Please enter your password');
			form.password.focus();
			return false;		
		}
		form.submit();
	}
	
	function trim(str) {
		var resultStr = "";
		resultStr = lTrim(str);
		resultStr = rTrim(resultStr);
		return resultStr;
	}

	function lTrim(String) {
		var i = 0;
		var j = String.length - 1;
		if (String == null) {
			return (false);
		}
		for (i = 0; i < String.length; i++) {
			if (String.substr(i, 1) != " " && String.substr(i, 1) != "\t") {
				break;
			}
		}
		if (i <= j) {
			return (String.substr(i, (j + 1) - i));
		} else {
			return ("");
		}
	}

	function rTrim(String) {
		var i = 0;
		var j = String.length - 1;
		if (String == null) {
			return (false);
		}
		for (j = String.length - 1; j >= 0; j--) {
			if (String.substr(j, 1) != " " && String.substr(j, 1) != "\t") {
				break;
			}
		}
		if (i <= j) {
			return (String.substr(i, (j + 1) - i));
		} else {
			return ("");
		}
	}	
</SCRIPT>