<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url value="/checkOut/submitOrderAndPayment.do" var="submitOrderAndPaymentURL" scope="page"/>
<c:url value="/checkOut/submitOrderThenPayment.do" var="submitOrderThenPaymentURL" scope="page"/>

<h2><fmt:message key="order.confirmation.title"/></h2>
<P><fmt:message key="order.confirmation.msg"/></P>
<BR>
<TABLE border="1">
	<TR><TD colspan="5"><B><fmt:message key="order.details.title"/></B></TD></TR>
	<TR>
		<TD align="center"><B><fmt:message key="product.title"/></B></TD>
		<TD align="center"><B><fmt:message key="product.description"/></B></TD>
		<TD align="center"><B><fmt:message key="product.items"/></B></TD>
		<TD align="center"><B><fmt:message key="product.unit.price"/></B></TD>
		<TD align="center"><B><fmt:message key="product.total"/></B></TD>
	</TR>
<c:forEach items="${requestScope.KEY_LIST_ITEMS}" var="item" varStatus="status">
	<TR>
		<TD align="left">${item.name}</TD>
		<TD align="justify" title="${item.description}">${fn:substring(item.description, 0, 50)}...</TD>
		<TD align="center">${item.quantity}</TD>
		<TD align="center"><fmt:formatNumber type="currency" pattern="$ ###,###.00" value="${item.price}"/></TD>
		<TD align="right"><fmt:formatNumber type="currency" pattern="$ ###,###.00" value="${item.quantity * item.price}"/></TD>
	</TR>
</c:forEach>
	<TR>
		<td colspan="3"></td>
		<td align="right"><fmt:message key="label.tax"/></td>
		<TD align="right"><B><fmt:formatNumber pattern="$ ###,###.##" value="${requestScope.totalCart*0}"/></B></TD>
	</TR>
	<TR>
		<td colspan="2" align="right"><fmt:message key="product.total.items"/></td>
		<td align="center">${requestScope.totalItemsCart}</td>
		<td align="right" bgcolor="yellow"><fmt:message key="order.total.sale"/></td>
		<TD align="right" bgcolor="yellow"><BIG><B><fmt:formatNumber pattern="$ ###,###.##" value="${requestScope.totalCart}"/></B></BIG></TD>
	</TR>
</TABLE>
<br>	
<TABLE border="1">
	<TR><TD colspan="5"><B><fmt:message key="payment.info.title.alternative"/></B></TD></TR>
	<TR>
		<TD><label for="number"><fmt:message key="payment.form.number"/></label></TD>
		<TD><fmt:message key="payment.form.number.ending.in"/> ${fn:substring(CreditCardDTO.number, fn:length(CreditCardDTO.number)-4, fn:length(CreditCardDTO.number))}</TD>
	</TR>
	<TR>
		<TD><label for="type"><fmt:message key="payment.form.card.type"/></label></TD>
		<TD>${CreditCardDTO.type}</TD>
	</TR>
	<TR>
		<TD><label for="expirationDate"><fmt:message key="payment.form.card.exp.date"/></label></TD>
		<TD>${CreditCardDTO.expirationDate}</TD>
	</TR>	
</TABLE>
<br>
<TABLE border="1">
	<TR><TD colspan="5"><B><fmt:message key="customer.information.title"/></B></TD></TR>
	<TR>
		<TD><label for="fullName"><fmt:message key="customer.full.name"/></label></TD>
		<TD>${CustomerDTO.firstName}&nbsp;${CustomerDTO.middleName}&nbsp;${CustomerDTO.lastName}</TD>
	</TR>
	<TR>
		<TD><label for="email"><fmt:message key="login.username"/></label></TD>
		<TD>${CustomerDTO.email}</TD>
	</TR>
</TABLE>
<BR>
<form:form name="orderForm" action="submitOrderAndPayment.do" method="POST">
	<input type="hidden" name="idCreditCard" id="idCreditCard" value="${CreditCardDTO.idCreditCard}" />
	<P>${requestScope.reCaptchaError}</P>
	<P>${requestScope.reCaptcha}</P>	
</form:form>
<TABLE>
	<TR>
		<td title="You still can cancel the process..." align="left"><a href="${sessionScope.landingPageURL}"><fmt:message key="button.cancel"/></a></td>
		<td width="25px">&nbsp;</td>
		<td><a href="JavaScript:submitOrder('${submitOrderAndPaymentURL}');"><fmt:message key="button.submit.order"/></a></td>
	</TR>
</TABLE>
 
<P align="right"><a href="JavaScript:submitOrder('${submitOrderThenPaymentURL}');"><fmt:message key="button.submit.order.2tx"/></a></P><br><br>


<SCRIPT>
	function submitOrder(url){
		orderForm.action = url;
		orderForm.submit();
	}
</SCRIPT>