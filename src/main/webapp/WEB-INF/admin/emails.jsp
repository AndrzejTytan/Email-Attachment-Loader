<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Emails - data sources: <br>
<table>
    <thead>
    <tr>
        <th>Email ID</th>
        <th>Email Address</th>
        <th>Received On</th>
        <th>Processed On</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${emails}" var="email">
        <tr>
            <td><c:out value="${email.id}"/></td>
            <td>
                <a href="/admin/suppliers/form/${email.timeSeriesSupplier.id}">
                    <c:out value="${email.timeSeriesSupplier.email}"/>
                </a>
            </td>
            <td><c:out value="${email.emailReceieved}"/></td>
            <td><c:out value="${email.emailProcessed}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
