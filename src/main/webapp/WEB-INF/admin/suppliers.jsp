<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/static-jsp/head-css-js.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/static-jsp/nav-bar.jsp"%>
List of all time series suppliers: <br>
<a href="/admin/suppliers/form">Create New</a><br>
<table id="data-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>File column index for identifier code</th>
        <th>File column index for price date</th>
        <th>File column index for price value</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${suppliers}" var="supplier">
        <tr>
            <td><c:out value="${supplier.id}"/></td>
            <td><c:out value="${supplier.email}"/></td>
            <td><c:out value="${supplier.fileColumnIndexIdentifierCode}"/></td>
            <td><c:out value="${supplier.fileColumnIndexPriceDate}"/></td>
            <td><c:out value="${supplier.fileColumnIndexPriceValue}"/></td>
            <td>
                <a href="/admin/suppliers/delete/${supplier.id}">Delete</a>
                <a href="/admin/suppliers/form/${supplier.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
