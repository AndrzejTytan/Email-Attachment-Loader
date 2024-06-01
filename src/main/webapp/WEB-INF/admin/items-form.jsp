<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Add new or edit item: <br>
<form:form modelAttribute="itemDetail" method="post">
    <form:hidden path="id" />
    Identifier: <form:input path="identifierCode" /><br>
    Name: <form:input path="name" /><br>
    Currency: <form:input path="pricingCurrencyIso" /><br>
    <input type="submit">
</form:form>
</body>
</html>
