<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<h2><fmt:message key="order.details.title"/>&nbsp;<fmt:formatNumber pattern="######" value="${requestScope.idOrder}"/></h2>
<P align="left"><font color="red"><c:out value="${pageScope.message}" /></font></P>

<TABLE border="0">
	<TR>
		<TD align="center"><fmt:message key="product.title"/></TD>
		<TD align="center"><fmt:message key="product.total.items"/></TD>
	</TR>
<c:forEach items="${requestScope.KEY_LIST_ITEMS}" var="item">
	<c:url var="showProductDetailsURL" value="/products/showProductDetails.do">
		<c:param name="idProduct" value="${item.productDTO.idProduct}"/>
	</c:url>
	<TR>
		<TD align="center">
			<B>${item.productDTO.name}</B><BR>
			<a href="${showProductDetailsURL}"><img height="60%" width="60%" src="${pageContext.request.contextPath}/img/products/${item.productDTO.code}.JPG" /></a>
		</TD>
		<td>${item.numberItems}</td>
	</TR>
</c:forEach>
</TABLE>
<c:if test="${empty requestScope.KEY_LIST_ITEMS}">
<B><fmt:message key="order.details.empty"/> [${requestScope.idOrder}]</B>
</c:if>


<P><a href="${sessionScope.landingPageURL}"><fmt:message key="button.back"/></a></P>