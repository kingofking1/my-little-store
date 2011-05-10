<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:url value="/checkOut/addPaymentInformation.do" var="paymentInfoURL" scope="page"/>

<h2><fmt:message key="order.details.title.only"/></h2>

<TABLE border="1">
	<TR>
		<TD align="center"><B><fmt:message key="product.title"/></B></TD>
		<TD align="center"><B><fmt:message key="product.description"/></B></TD>
		<TD align="center"><B><fmt:message key="product.items"/></B></TD>
		<TD align="center"><B><fmt:message key="product.unit.price"/></B></TD>
		<TD align="center"><B><fmt:message key="product.total"/></B></TD>
	</TR>
<c:if test="${empty requestScope.KEY_LIST_ITEMS}">
	<TR>
		<TD colspan="5" align="center"><B><fmt:message key="order.empty.add.items"/></B></TD>
	</TR>
</c:if>	
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
		<td colspan="2" align="right"><fmt:message key="product.total.items"/>:</td>
		<td align="center">${requestScope.totalItemsCart}</td>
		<td align="right"><fmt:message key="shopping.cart.total"/>:</td>
		<TD align="right"><BIG><B><fmt:formatNumber pattern="$ ###,###.##" value="${requestScope.totalCart}"/></B></BIG></TD>
	</TR>
<c:if test="${!empty requestScope.KEY_LIST_ITEMS}">
	<TR><TD colspan="5">&nbsp;</TD></TR>
	<TR>
		<TD colspan="5" align="right"><a href="${pageScope.paymentInfoURL}"><fmt:message key="label.payment.add.info"/></a></TD>
	</TR>
</c:if>	
</TABLE>
<BR>
<P><a href="${sessionScope.landingPageURL}"><fmt:message key="button.back"/></a></P>