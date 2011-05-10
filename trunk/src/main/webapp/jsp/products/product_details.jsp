<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<c:set var="messageStock" value="<font color='GREEN'><fmt:message key='product.in.stock'/></font>" scope="page" />
<c:if test="${requestScope.KEY_SINGLE_ITEM.quantity==0}">
	<c:set var="messageStock" value="<font color='RED'><fmt:message key='product.out.stock'/></font>" scope="page" />
</c:if>

<c:url var="addItemToCartURL" value="/shoppingCart/addItem.do">
	<c:param name="numberOfItems" value="1"/>
	<c:param name="idProduct" value="${requestScope.KEY_SINGLE_ITEM.idProduct}"/>
	<c:param name="price" value="${requestScope.KEY_SINGLE_ITEM.price}"/>
	<c:param name="name" value="${requestScope.KEY_SINGLE_ITEM.name}"/>
</c:url>

<SCRIPT>
	function addItemToCart(){
		<c:if test="${requestScope.KEY_SINGLE_ITEM.quantity==0}">
			alert('<fmt:message key="product.out.stock"/>');
			return;
		</c:if>
		location.href='${addItemToCartURL}';
	}
</SCRIPT>

<P align="left"><font color="red"><c:out value="${pageScope.message}" /></font></P>
<BR>
<TABLE border="1">
	<TR>
		<td colspan="4"><B>${requestScope.KEY_SINGLE_ITEM.name}</B></td>
	</TR>
	<TR>
		<td><img src="${pageContext.request.contextPath}/img/products/${requestScope.KEY_SINGLE_ITEM.code}.JPG" /></td>
		<TD><fmt:message key="product.price"/>: <fmt:formatNumber type="currency" pattern="$ ###,###.##" value="${requestScope.KEY_SINGLE_ITEM.price}"/></TD>
		<TD>${pageScope.messageStock}</TD>
		<TD><a href="JavaScript:addItemToCart();"><fmt:message key="shopping.cart.add.item"/></a></TD>
	</TR>
	<TR>
		<td colspan="4" align="justify">${requestScope.KEY_SINGLE_ITEM.description}</td>
	</TR>	
</TABLE>

<P><a href="${sessionScope.landingPageURL}"><fmt:message key="button.back"/></a></P>