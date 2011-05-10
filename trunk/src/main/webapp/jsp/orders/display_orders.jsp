<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el" %>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<h2><fmt:message key="orders.placed"/></h2>
<P align="left"><font color="red"><c:out value="${pageScope.message}" /></font></P>

<table width="80%" cellspacing="1" cellpadding="1" border="0" align="center">
<tr>
	<td align="center">
	
	<display:table name="requestScope.KEY_LIST_ITEMS" pagesize="4" sort="list" export="true" requestURI="/orders/viewMyOrders.htm" id="row" defaultsort="1">
		<display:column titleKey="order.number" property="idOrder"/>
		<display:column property="creationTs" sortable="true" titleKey="order.creation.date"/>
		<display:column titleKey="order.total.sale"><fmt:formatNumber pattern="$ ###,###.##" value="${row.totalSale}"/></display:column>
		<display:column media="html" titleKey="label.order.details" paramProperty="idOrder" paramId="idOrder" url="/orders/showOrderDetails.do">
			<div onmouseover="return overlib('<P align=justify><fmt:message key="order.details.view.msg"/></P>',CAPTION,'<CENTER><fmt:message key="order.details.view"/> ${row.idOrder}</CENTER>',FGCOLOR,'#FFCC00',BGCOLOR,'#CC0000',HAUTO,VAUTO);" onmouseout="return nd();">		
			<center><img src="${pageContext.request.contextPath}/img/show.png" /></center>
			</div>
		</display:column>
	</display:table>
	<c:if test="${empty requestScope.KEY_LIST_ITEMS}">
		<B><fmt:message key="orders.recent.empty"/></B>
	</c:if>
	
	</td>
</tr>
</table>

<P><a href="${sessionScope.landingPageURL}"><fmt:message key="button.back"/></a></P>