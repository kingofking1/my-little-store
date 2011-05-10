<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<P align="left"><font color="red"><c:out value="${pageScope.message}" /></font></P>

<BR>

<h2 style="margin:0 0 20px 0"><Strong><fmt:message key="payment.info.title"/></Strong></h2>
<P><fmt:message key="payment.info.msg"/></P>
<select name="cbxCards" id="cbxCards" onchange="JavaScript:selectCard(this.value);">
	<option value=""><fmt:message key="label.select"/></option>
	<c:if test="${empty requestScope.KEY_LIST_ITEMS}">
		<option value="add"><fmt:message key="payment.add.credit.card"/></option>
	</c:if>
	<c:forEach items="${requestScope.KEY_LIST_ITEMS}" var="item">
		<option value="${item.idCreditCard}">**** **** **** ${fn:substring(item.number, fn:length(item.number)-4, fn:length(item.number))}</option>
	</c:forEach>
</select>

<form:form modelAttribute="CreditCardDTO" name="paymentMethodForm" action="addPaymentInformation.do" autocomplete="true" method="POST">
<TABLE>
	<TR>
		<TD><label for="number"><fmt:message key="payment.form.number"/></label></TD>
		<TD><form:input path="number" maxlength="18" size="18"/></TD>
	</TR>
	<TR>
		<TD><label for="securityCode" title="<fmt:message key='payment.form.security.code.msg'/>"><fmt:message key="payment.form.security.code"/></label></TD>
		<TD><form:input path="securityCode" maxlength="4" size="4" autocomplete="false" /></TD>
	</TR>
	<TR>
		<TD><label for="type" title="<fmt:message key='payment.form.card.type.msg'/>"><fmt:message key="payment.form.card.type"/></label></TD>
		<TD><form:select path="type" id="type" items="${requestScope.KEY_MAP_TYPE_CARDS}"/></TD>
	</TR>
	<TR>
		<TD><label for="expirationDate" title="<fmt:message key='payment.form.card.exp.date.msg'/>"><fmt:message key="payment.form.card.exp.date"/></label></TD>
		<TD><form:input path="expirationDate" maxlength="7" size="10" autocomplete="false" /></TD>
	</TR>
	<TR>
		<TD><input type="submit" value="<fmt:message key='payment.form.add.use.card'/>" title="<fmt:message key='payment.form.add.use.card.msg'/>" onclick="JavaScript:paymentMethodForm.idCreditCard.value='';"/></TD>
		<TD><input type="submit" value="<fmt:message key='payment.form.use.card'/>" title="<fmt:message key='payment.form.use.card.msg'/>" /></TD>		
	</TR>
</TABLE>	
	<form:hidden path="idCreditCard"/>
</form:form>

<c:url value="/checkOut/addPaymentInformation.do" var="paymentInfoURL" scope="page"/>

<SCRIPT>
	function selectCard(idCreditCard){
		if(idCreditCard!='' && idCreditCard!="add"){
			location.href='${paymentInfoURL}?idCreditCard='+idCreditCard;
		}
	}
		
	function setSelectedText(campoSelect,text){
	   	var objSelect = campoSelect;
		if(objSelect.type=="select-one"||objSelect.type=="select-multiple"){	
			for(var j=0;j<objSelect.options.length;j++){
				if(objSelect.options[j].text==text){
            	   	objSelect.selectedIndex=j;
				}
			}
		}
	}	
	
	setSelectedText(paymentMethodForm.type, '${requestScope.CreditCardDTO.type}');
</SCRIPT>