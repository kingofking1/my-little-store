<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:url value="/shoppingCart/updateItem.do" var="updateItemURL" scope="page"/>
<c:url value="/checkOut/reviewOrder.do" var="reviewOrderURL" scope="page"/>

<SCRIPT>
	function updateItems(idProduct){
		var numberOfItems = document.getElementById('numberOfItems_'+idProduct);
		location.href='${updateItemURL}?idProduct='+idProduct+"&numberOfItems="+numberOfItems.value;
	}
</SCRIPT>

<h2><fmt:message key="shopping.cart.title"/></h2>

<TABLE border="1">
	<TR>
		<TD align="center"><B><fmt:message key="product.title"/></B></TD>
		<TD align="center"><B><fmt:message key="product.description"/></B></TD>
		<TD align="center"><B><fmt:message key="product.items"/></B></TD>
		<TD align="center"><B><fmt:message key="product.unit.price"/></B></TD>
		<TD align="center"><B><fmt:message key="product.total"/></B></TD>
		<TD align="center"><B><fmt:message key="button.edit"/></B></TD>
	</TR>
<c:if test="${empty requestScope.KEY_LIST_ITEMS}">
	<TR>
		<TD colspan="6" align="center"><B><fmt:message key="shopping.cart.empty"/></B></TD>
	</TR>
</c:if>	
<c:forEach items="${requestScope.KEY_LIST_ITEMS}" var="item" varStatus="status">
	<TR>
		<TD align="left">${item.name}</TD>
		<TD align="justify" title="${item.description}">${fn:substring(item.description, 0, 50)}...</TD>
		<TD align="center"><input type="text" size="3" maxlength="5" name="numberOfItems_${item.idProduct}" id="numberOfItems_${item.idProduct}" value="${item.quantity}"/></TD>
		<TD align="center"><fmt:formatNumber type="currency" pattern="$ ###,###.00" value="${item.price}"/></TD>
		<TD align="right"><fmt:formatNumber type="currency" pattern="$ ###,###.00" value="${item.quantity * item.price}"/></TD>
		<TD align="center"><a href="JavaScript:updateItems(${item.idProduct});">Update</a></TD>
	</TR>
</c:forEach>
	<TR>
		<td colspan="2" align="right"><fmt:message key="product.total.items"/>:</td>
		<td align="center">${requestScope.totalItemsCart}</td>
		<td align="right"><fmt:message key="shopping.cart.total"/>:</td>
		<TD align="right"><B><fmt:formatNumber pattern="$ ###,###.##" value="${requestScope.totalCart}"/></B></TD>
		<TD>&nbsp;</TD>
	</TR>
<c:if test="${!empty requestScope.KEY_LIST_ITEMS}">
	<TR><TD colspan="6">&nbsp;</TD></TR>
	<TR>
		<TD align="right"><a href="${pageScope.reviewOrderURL}"><fmt:message key="shopping.cart.checkout"/></a></TD>
	</TR>
</c:if>	
</TABLE>
<BR>
<P><fmt:message key="label.back.shopping"/>&nbsp;<a href="${sessionScope.landingPageURL}"><fmt:message key="button.back"/></a></P>