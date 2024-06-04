<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Add new or edit time series suppliers: <br>
<form:form modelAttribute="timeSeriesSupplier" method="post">
    <form:hidden path="id" />
    Email: <form:input path="email" /><br>
    Column of identifier in supplier's files: <form:input path="fileColumnIndexIdentifierCode" /><br>
    Column of price date in supplier's files: <form:input path="fileColumnIndexPriceDate" /><br>
    Column of price value in supplier's files: <form:input path="fileColumnIndexPriceValue" /><br>
    <input type="submit">
</form:form>
</body>
</html>
