<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

Name: <c:out value="${item.get().name}"/><br>
Identifier: <c:out value="${item.get().identifierCode}"/><br>
Currency: <c:out value="${item.get().pricingCurrencyIso}"/><br>

<br>

<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${timeSeries}" var="ts">
        <tr>
            <td><c:out value="${ts.priceDate}"/></td>
            <td><c:out value="${ts.priceValue}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
