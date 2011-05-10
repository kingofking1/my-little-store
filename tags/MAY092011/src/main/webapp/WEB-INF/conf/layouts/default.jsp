<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html> 		
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />		
		<spring:url value="/images/favicon.ico" var="favicon" />
		<link rel="SHORTCUT ICON" href="${favicon}" />
		<spring:message code="site.title" var="app_name"/>
		<title><spring:message code="site.title" arguments="${app_name}" /></title>

		<link href="${pageContext.request.contextPath}/css/displaytag.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/css/standard.css" rel="stylesheet" type="text/css">
		
		<script src="${pageContext.request.contextPath}/js/overlib/overlib.js"></script>
	</head>
  	<body class="tundra spring">
  		<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
  	
   		<div id="wrapper">
		    <tiles:insertAttribute name="header" ignore="true" />
		    <tiles:insertAttribute name="menu" ignore="true" />
		    <div id="main">
	    		<tiles:insertAttribute name="body"/> 
		    	<tiles:insertAttribute name="footer" ignore="true"/>
		    </div>
		</div>
	</body>
</html>
