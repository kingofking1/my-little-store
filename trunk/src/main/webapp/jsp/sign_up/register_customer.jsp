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

<c:url var="signUpURL" value="/authentication/registerCustomer.htm" scope="page"/>
<c:url var="captchaImage" value="/captcha/captcha.htm" scope="page"/>

<BR>

<fmt:message key="login.username" scope="page" var="userNameMsg"/>
<fmt:message key="login.password" scope="page" var="passwordMsg"/>
<fmt:message key="customer.form.re_password"  scope="page" var="rePasswordMsg"/>
<fmt:message key="customer.form.firstName"  scope="page" var="firstNameMsg"/>
<fmt:message key="customer.form.middleName"  scope="page" var="middleNameMsg"/>
<fmt:message key="customer.form.lastName"  scope="page" var="lastNameMsg"/>
<fmt:message key="customer.form.captcha"  scope="page" var="captchaMsg"/>

<h2><fmt:message key="signUp.title"/></h2>

<form:form modelAttribute="CustomerDTO" name="registerCustomerForm" action="registerCustomer.htm" autocomplete="true" method="POST">
<TABLE>
	<TR>
		<TD><label for="email" title="Please provide your user Id">${pageScope.userNameMsg}</label></TD>
		<TD><form:input path="email" maxlength="120" size="35" /></TD>
	</TR>
	<TR>
		<TD><label for="password" title="Enter your password">${pageScope.passwordMsg}</label></TD>
		<TD><form:password path="password" maxlength="45" size="20" autocomplete="false" /></TD>
	</TR>	
	<TR>
		<TD><label for="password" title="Enter again your password">${pageScope.rePasswordMsg}</label></TD>
		<TD><input type="password" name="re_password" id="re_password" maxlength="45" size="20" autocomplete="false" /></TD>
	</TR>
	<TR>
		<TD><label for="firstName" title="Please provide your First Name">${pageScope.firstNameMsg}</label></TD>
		<TD><form:input path="firstName" maxlength="50" size="25" /></TD>
	</TR>
	<TR>
		<TD><label for="middleName" title="Please provide your Middle Name or Initial">${pageScope.middleNameMsg}</label></TD>
		<TD><form:input path="middleName" maxlength="50" size="25" /></TD>
	</TR>
	<TR>
		<TD><label for="lastName" title="Please provide your Last Name">${pageScope.lastNameMsg}</label></TD>
		<TD><form:input path="lastName" maxlength="50" size="25" /></TD>
	</TR>
	<TR>
		<TD><label for="captcha" title="Please enter the text displayed on the image">${pageScope.captchaMsg}</label></TD>		
		<TD><input type="text" name="captchaText" id="captchaText" maxlength="15" size="25" /></TD>
	</TR>	
	<TR>	
		<TD colspan="2" align="center"><img src="${pageScope.captchaImage}"></TD>
	</TR>
	<TR>	
		<TD colspan="2">&nbsp;</TD>
	</TR>						
	<TR>
		<TD align="center"><input type="reset" onclick="JavaScript:location.href='${signUpURL}';" value="<fmt:message key='button.reset'/>"/></TD>
		<TD align="center"><input type="button" onclick="JavaScript:doSubmit(this.form);" value="<fmt:message key='button.save'/>"/></TD>
	</TR>
</TABLE>	
</form:form>
<c:url var="signInURL" value="/authentication/login.do" scope="page"/>
<P><fmt:message key="login.signIn"/>&nbsp;<a href="${pageScope.signInURL}"><fmt:message key="login.signIn.now"/></a></P>

<fmt:message key="field_required_msg" var="requiredFieldMsg"/>
<fmt:message key="fields_dont_match" var="fieldsDontMatchaMsg"/>
<fmt:message key="field_and" var="andMsg"/>

<SCRIPT>
function doSubmit(form){
	
	if(trim(form.email.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.userNameMsg}");
		form.email.focus();
		return false;
	}
	if(trim(form.password.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.passwordMsg}");
		form.password.focus();
		return false;		
	}else{
		if(trim(form.re_password.value).length==0){
			alert("${pageScope.requiredFieldMsg} ${pageScope.rePasswordMsg}");
			form.re_password.focus();
			return false;
		}else{
			if(form.password.value!=form.re_password.value){
				alert("${pageScope.passwordMsg} ${pageScope.andMsg} ${pageScope.rePasswordMsg} ${pageScope.fieldsDontMatchaMsg}");
				form.re_password.focus();
				return false;				
			}
		}
	}
	if(trim(form.firstName.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.firstNameMsg}");
		form.firstName.focus();
		return false;
	}
	if(trim(form.lastName.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.lastNameMsg}");
		form.lastName.focus();
		return false;
	}
	if(trim(form.captchaText.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.captchaMsg}");
		form.captchaText.focus();
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