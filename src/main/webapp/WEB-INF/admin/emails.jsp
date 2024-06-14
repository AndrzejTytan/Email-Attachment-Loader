<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/static-jsp/head-css-js.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/static-jsp/nav-bar.jsp"%>
Emails - data sources: <br>
<table id="data-table">
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
