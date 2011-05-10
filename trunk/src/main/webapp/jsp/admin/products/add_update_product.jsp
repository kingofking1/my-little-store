<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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

<h2>
<c:choose>
	<c:when test="${requestScope.Operation == 'UPDATE'}"><fmt:message key="product.update.title"/></c:when>
	<c:when test="${requestScope.Operation == 'ADD'}"><fmt:message key="product.add.title"/></c:when>
	<c:otherwise><fmt:message key="label.unknown.option"/></c:otherwise>
</c:choose>
</h2>
<fmt:message key="product.name" var="productNameMsg" scope="page"/>
<fmt:message key="product.description" var="productDescMsg" scope="page"/>
<fmt:message key="product.code" var="productCodeMsg" scope="page"/>
<fmt:message key="product.price" var="productPriceMsg" scope="page"/>
<fmt:message key="product.quantity" var="productQuantityMsg" scope="page"/>
<fmt:message key="product.image" var="productImageMsg" scope="page"/>
<fmt:message key="product.brand.name" var="productBrandMsg" scope="page"/>
<fmt:message key="search.products.available.categories" var="productCategoryMsg" scope="page"/>

<form:form modelAttribute="ProductDTO" name="productForm" action="saveOrUpdateProduct.do" autocomplete="true" method="POST" enctype="multipart/form-data">
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
		<TD><label for="code">${pageScope.productCodeMsg}</label></TD>
		<TD><form:input path="code" maxlength="19" size="15" /></TD>
	</TR>
	<TR>
		<TD><label for="price">${pageScope.productPriceMsg}</label></TD>
		<TD><form:input path="price" maxlength="10" size="15" /></TD>
	</TR>
	<TR>
		<TD><label for="quantity">${pageScope.productQuantityMsg}</label></TD>
		<TD><form:input path="quantity" maxlength="10" size="10" /></TD>
	</TR>			
	<TR>
		<TD><label for="productImage">${pageScope.productImageMsg}</label></TD>
		<TD><input type="file" name="productImage" id="productImage" dir="rtl"/>
		<c:if test="${!empty requestScope.ProductDTO.code}">
		<br>
		<img src="${pageContext.request.contextPath}/img/products/${requestScope.ProductDTO.code}.JPG" height="45%" width="28%" />
		</c:if>
		</td>
	</TR>
	<TR>
		<TD><label for="brandDTO.idBrand">${pageScope.productBrandMsg}</label></TD>
		<TD>
			<select name="brand" id="brand">
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
			<select name="category" id="category">
				<option value=""><fmt:message key="label.select"/></option>
				<c:forEach items="${requestScope.KEY_LIST_CATEGORIES}" var="item">
					<option value="${item.idCategory}">${item.name}</option>
				</c:forEach>
			</select>
		</TD>
	</TR>				
	<TR>	
		<TD colspan="2">&nbsp;</TD>
	</TR>						
	<TR>
		<TD align="center"><input type="reset" value="<fmt:message key='button.reset'/>"/></TD>
		<TD align="center"><input type="button" onclick="JavaScript:doSubmit(this.form);" value="<fmt:message key='button.save'/>"/></TD>
	</TR>
</TABLE>	
<form:hidden path="idProduct"/>
</form:form>

<fmt:message key="field_required_msg" var="requiredFieldMsg"/>

<SCRIPT>
function doSubmit(form){
	
	if(trim(form.name.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productNameMsg}");
		form.name.focus();
		return false;
	}
	if(trim(form.description.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productDescMsg}");
		form.description.focus();
		return false;
	}
	if(trim(form.code.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productCodeMsg}");
		form.code.focus();
		return false;
	}
	if(trim(form.quantity.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productQuantityMsg}");
		form.quantity.focus();
		return false;
	}
	if(trim(form.productImage.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productImageMsg}");
		form.productImage.focus();
		return false;
	}
	if(trim(form.brand.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productBrandMsg}");
		form.brand.focus();
		return false;
	}
	if(trim(form.category.value).length==0){
		alert("${pageScope.requiredFieldMsg} ${pageScope.productCategoryMsg}");
		form.category.focus();
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

function setSelectedValue(campoSelect,value){
   	var objSelect = campoSelect;
	if(objSelect.type=="select-one"||objSelect.type=="select-multiple"){	
		for(var j=0;j<objSelect.options.length;j++){
			if(objSelect.options[j].value==value){
        	   	objSelect.selectedIndex=j;
			}
		}
	}
}	

setSelectedValue(productForm.brand, '${requestScope.ProductDTO.brandDTO.idBrand}');
setSelectedValue(productForm.category, '${requestScope.ProductDTO.categoryDTO.idCategory}');
</SCRIPT>