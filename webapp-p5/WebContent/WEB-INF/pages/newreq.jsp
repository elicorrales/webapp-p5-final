<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="common.jsp"%>

<title>Request Calculation</title>

</head>
<body>
	<c:choose>
		<c:when test="${ not empty error }">
			<div class="alert alert-danger">
				<strong>${error }</strong>
			</div>
		</c:when>
		<c:when test="${ not empty success }">
			<div class="alert alert-success">
				<strong>${success }</strong>
			</div>

		</c:when>
	</c:choose>

	<div class="container">
		<form:form action="newreq" method="post" commandName="calcRequestForm">
			<table style="width: 650px" class="table table-striped">
				<tr>
					<td>Shape:</td>
					<td><form:select path="shapeName" items="${shapeList}" /></td>
				</tr>
				<tr>
					<td>Type:</td>
					<td><form:select path="calcType" items="${calcTypeList}" /></td>
				</tr>
				<tr>
					<td>Dim:</td>
					<td><form:input path="dimension" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Request" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>