<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %></c:set>
<c:set var="from"><%= request.getQueryString()!=null?request.getQueryString().substring(5):""%></c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập</title>
    <link rel="shortcut icon" href="${context}/favicon.ico" />

    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <%-- APP--%>
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="content">

    <div class="login-box">
        <div class="form__heading-text">Đăng nhập</div>
        <c:if test="${loginMessage!=null}">
            <div class="login__fail-message error-message">${loginMessage}</div>
        </c:if>
        <form action="${context}/admin" method="post" class="login-form form" autocomplete="off">
            <input type="hidden" name="from" value="${from}"/>
            <div class="form-group form-floating">
                <input type="text" class="form-control" id="usernameOrEmail" name="usernameOrEmail" placeholder="Tên đăng nhập">
                <label for="usernameOrEmail" class="form-label">Email hoặc tên đăng nhập</label>
            </div>
            <div class="form-group form-floating">
                <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu">
                <label for="password" class="form-label">Mật khẩu</label>
            </div>
            <a href="${context}/admin/forgot-password" class="login__forget-password">Quên mật khẩu?</a>
            <button type="submit" class="btn submit-btn">Đăng nhập</button>
        </form>
    </div>
</div>
<jsp:include page="../../common/footer.jsp"/>



</body>
</html>
