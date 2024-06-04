<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
List of all items: <br>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Identifier</th>
        <th>Currency</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${items}" var="item">
        <tr>
            <td><c:out value="${item.name}"/></td>
            <td><c:out value="${item.identifierCode}"/></td>
            <td><c:out value="${item.pricingCurrencyIso}"/></td>
            <td>
                <a href="/items/time-series/${item.id}">Details</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
