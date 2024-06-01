<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
List of all time series suppliers: <br>
<a href="">Create New</a><br>
<table>
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
                <a href="">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
