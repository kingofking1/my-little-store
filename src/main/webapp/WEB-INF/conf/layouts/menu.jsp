<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Set some variables which will be used everywhere --%>
<c:url var="landingPageURL" value="/showLandingPage.do" scope="session"/>
<c:url var="logoutURL" value="/authentication/logout.do" scope="page"/>
<c:url var="viewShoppingCartContentURL" value="/shoppingCart/viewShoppingCart.do" scope="page"/>
<c:url var="searchProductsURL" value="/products/searchProducts.do" scope="page"/>
<c:url var="viewMyOrdersURL" value="/orders/viewMyOrders.htm" scope="page"/>


<div id="side-bar">
	<UL>
    <LI><a href="<c:url value="/showLandingPage.do"/>"><fmt:message key="index.title"/></a> 
	<!-- LI><a href="${logoutURL}"><fmt:message key="button.logout"/></a-->
	<LI><a href="${viewShoppingCartContentURL}"><fmt:message key="button.view.shopping.cart"/></a>
	<LI><a href="${searchProductsURL}"><fmt:message key="button.search.products"/></a>
	<LI><a href="${viewMyOrdersURL}"><fmt:message key="button.view.orders"/></a>
	</UL>	     
</div>