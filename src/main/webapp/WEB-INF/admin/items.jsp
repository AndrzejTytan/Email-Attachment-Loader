<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
List of all time series suppliers: <br>
<a href="/admin/items/form">Create New</a><br>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Identifier</th>
        <th>Name</th>
        <th>Currency</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${items}" var="item">
        <tr>
            <td><c:out value="${item.id}"/></td>
            <td><c:out value="${item.identifierCode}"/></td>
            <td><c:out value="${item.name}"/></td>
            <td><c:out value="${item.pricingCurrencyIso}"/></td>
            <td>
                <a href="/admin/items/delete/${item.id}">Delete</a>
                <a href="/admin/items/form/${item.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
