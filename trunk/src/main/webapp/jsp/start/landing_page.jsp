<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<P><fmt:message key="index.message"/></P>
<BR>
<h2><fmt:message key="products.suggested" /></h2>
<TABLE border="1">
<c:forEach items="${requestScope.KEY_LIST_ITEMS}" var="item">
	<c:url var="showProductDetails" value="/products/showProductDetails.do">
		<c:param name="idProduct" value="${item.idProduct}"/>
	</c:url>
	<TR>
		<TD align="center">
			<B>${item.name}</B><BR>
			<a href="${showProductDetails}"><img src="${pageContext.request.contextPath}/img/products/${item.code}.JPG" /></a>
		</TD>
	</TR>
</c:forEach>
</TABLE>