<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<h2><fmt:message key="search.products.title"/></h2>
<P align="left"><font color="red"><c:out value="${pageScope.message}" /></font></P>

<form:form modelAttribute="CategoryDTO" name="productSearchForm" action="searchProducts.do" autocomplete="true" method="POST">
<TABLE>
	<TR>
		<TD><label for="idCategory"><fmt:message key="search.products.available.categories"/></label></TD>
		<TD><form:select path="idCategory" itemValue="idCategory" itemLabel="name" items="${sessionScope.KEY_LIST_ITEMS}" /></TD>
	</TR>
	<TR>
		<TD><input type="submit" value="<fmt:message key='button.search'/>" title="<fmt:message key='search.products.msg'/>"/></TD>		
	</TR>
	<input type="hidden" name="submitted" id="submitted" value="true"/>
</TABLE>	
</form:form>
<c:if test="${!empty requestScope.formWasSubmitted}">
	<TABLE border="0">
	<c:forEach items="${requestScope.KEY_LIST_ITEMS}" var="item">
		<c:url var="showProductDetailsURL" value="/products/showProductDetails.do">
			<c:param name="idProduct" value="${item.idProduct}"/>
		</c:url>
		<TR>
			<TD align="center">
				<B>${item.name}</B><BR>
				<a href="${showProductDetailsURL}"><img src="${pageContext.request.contextPath}/img/products/${item.code}.JPG" /></a>
			</TD>
		</TR>
	</c:forEach>
	</TABLE>
	<c:if test="${empty requestScope.KEY_LIST_ITEMS}">
	<B><fmt:message key="search.products.empty.msg"/></B>
	</c:if>
</c:if>

<SCRIPT>
	function setSelectedValue(campoSelect,text){
	   	var objSelect = campoSelect;
		if(objSelect.type=="select-one"||objSelect.type=="select-multiple"){	
			for(var j=0;j<objSelect.options.length;j++){
				if(objSelect.options[j].value==text){
            	   	objSelect.selectedIndex=j;
				}
			}
		}
	}	
	
	setSelectedValue(productSearchForm.idCategory, '${requestScope.CategoryDTO.idCategory}');
</SCRIPT>
<P><a href="${sessionScope.landingPageURL}"><fmt:message key="button.back"/></a></P>