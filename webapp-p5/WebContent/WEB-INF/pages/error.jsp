<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="common.jsp"%>

<title>An Error Occurred</title>

</head>
<body>

	<div class="container">

		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#error">Error</a></li>
		</ul>

		<div class="tab-content">
			<div id="error" class="tab-pane fade in active">
				<h3>Error</h3>


				
	<div class="container">

	<c:choose>
		<c:when test="${ not empty errmsg }">
			<div class="alert alert-danger">
       			<strong>We apologize there was an error:</strong><br/>
       			<strong>${errmsg}</strong><br/>
			</div>
		</c:when>
		<c:when test="${ not empty cause }">
			<div class="alert alert-danger">
       			<strong>The stated cause:</strong><br/>
				<strong>${cause }</strong>
			</div>

		</c:when>
	</c:choose>

	</div>
				
				
			</div>

		</div>

	</div>





</body>
</html>