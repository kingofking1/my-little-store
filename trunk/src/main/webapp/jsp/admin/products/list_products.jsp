<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el" %>

<c:set var="message" value="" scope="page" />
<c:if test="${!empty requestScope.KEY_ERROR}">
	<c:set var="message" scope="page" value="${requestScope.KEY_ERROR}"/>
</c:if>

<P align="justify">
	<font color="red"><c:out value="${pageScope.message}" /></font>
	<c:if test="${!empty requestScope.KEY_FATAL}">
	<BR><B><fmt:message key="error.persists"/></B>
	</c:if>
</P>

<fmt:message key="product.name" var="productNameMsg" scope="page"/>
<fmt:message key="product.description" var="productDescMsg" scope="page"/>
<fmt:message key="product.brand.name" var="productBrandMsg" scope="page"/>
<fmt:message key="search.products.available.categories" var="productCategoryMsg" scope="page"/>


<c:url var="addProductURL" value="/products/addProduct.htm" scope="page"/>

<h2><fmt:message key="products.list.title"/></h2>
<P align="left"><fmt:message key="products.list.msg"/></font></P>

<h3><fmt:message key="products.list.search.title"/></h3>
<form:form modelAttribute="ProductDTO" name="productForm" action="searchProductsByCriteria.do" autocomplete="true" method="POST">
<TABLE>
	<TR>
		<TD><label for="name">${pageScope.productNameMsg}</label></TD>
		<TD><form:input path="name" maxlength="99" size="35" /></TD>
	</TR>
	<TR>
		<TD><label for="description">${pageScope.productDescMsg}</label></TD>
		<TD><form:textarea path="description" maxlength="199" cols="40" rows="5" /></TD>
	</TR>
	<TR>
		<TD><label for="brandDTO.idBrand">${pageScope.productBrandMsg}</label></TD>
		<TD>
			<select name="idBrand" id="idBrand">
				<option value=""><fmt:message key="label.select"/></option>
				<c:forEach items="${requestScope.KEY_LIST_BRANDS}" var="item">
					<option value="${item.idBrand}">${item.name}</option>
				</c:forEach>
			</select>			
		</TD>
	</TR>	
	<TR>
		<TD><label for="categoryDTO.idCategory">${pageScope.productCategoryMsg}</label></TD>
		<TD>
			<select name="idCategory" id="idCategory">
				<option value=""><fmt:message key="label.select"/></option>
				<c:forEach items="${requestScope.KEY_LIST_CATEGORIES}" var="item">
					<option value="${item.idCategory}">${item.name}</option>
				</c:forEach>
			</select>			
		</TD>
	</TR>				
	<TR>
		<TD align="center"><input type="reset" value="<fmt:message key='button.reset'/>"/></TD>
		<TD align="center"><input type="button" onclick="JavaScript:doSubmit(this.form);" value="<fmt:message key='button.search'/>"/></TD>
	</TR>
</TABLE>	
</form:form>
<br>
<table width="80%" cellspacing="1" cellpadding="1" border="0" align="center">
<tr>
	<td align="right" valign="top"><a href="${addProductURL}"><img src="${pageContext.request.contextPath}/img/agregar.gif" /><fmt:message key="product.add.title"/></a></td>
</tr>
<tr>
	<td align="center">
	<display:table name="requestScope.KEY_LIST_ITEMS" pagesize="13" sort="list" export="true" requestURI="/products/listAllProducts.htm" id="row">
		<display:column titleKey="product.name" property="name" sortable="true" maxWords="7"/>
		<display:column titleKey="product.description" property="description" maxWords="15"/>
		<display:column titleKey="product.code" property="code"  sortable="true"/>
		<display:column titleKey="product.price" sortable="true"><fmt:formatNumber pattern="$ ###,###.##" value="${row.price}"/></display:column>
		<display:column titleKey="product.quantity" property="quantity" sortable="true"/>
		<display:column titleKey="search.products.available.categories" property="categoryDTO.name" sortable="true" />
		<display:column titleKey="product.brand.name" property="brandDTO.name" sortable="true"/>
		<display:column media="html" titleKey="button.edit" paramProperty="idProduct" paramId="idProduct" url="/products/showProductDetailsForUpdate.htm">
			<center><img src="${pageContext.request.contextPath}/img/editar.gif" /></center>
		</display:column>
	</display:table>
	</td>
</tr>
</table>

<fmt:message key="field_required_criteria_msg" var="requiredFieldMsg"/>

<SCRIPT>
function doSubmit(form){
	
	if(trim(form.name.value).length==0 && trim(form.description.value).length==0 &&
			trim(form.idBrand.value).length==0 && trim(form.idCategory.value).length==0){
		alert("${pageScope.requiredFieldMsg}");
		return false;
	}	
	form.submit();
}

function trim(str) {
	var resultStr = "";
	resultStr = lTrim(str);
	resultStr = rTrim(resultStr);
	return resultStr;
}

function lTrim(String) {
	var i = 0;
	var j = String.length - 1;
	if (String == null) {
		return (false);
	}
	for (i = 0; i < String.length; i++) {
		if (String.substr(i, 1) != " " && String.substr(i, 1) != "\t") {
			break;
		}
	}
	if (i <= j) {
		return (String.substr(i, (j + 1) - i));
	} else {
		return ("");
	}
}

function rTrim(String) {
	var i = 0;
	var j = String.length - 1;
	if (String == null) {
		return (false);
	}
	for (j = String.length - 1; j >= 0; j--) {
		if (String.substr(j, 1) != " " && String.substr(j, 1) != "\t") {
			break;
		}
	}
	if (i <= j) {
		return (String.substr(i, (j + 1) - i));
	} else {
		return ("");
	}
}
</SCRIPT>