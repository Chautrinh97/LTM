<%@ page import="com.ctv.model.bean.CartItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="shortcut icon" href="${context}/favicon.ico">
    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/common.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/cart.css?rd=${rand}">
</head>
<body>
<c:set var="action" value="Giỏ hàng" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="cart-header">
        <div class="cart__check-btn">
            <input type="checkbox" name="all-item"
            >
        </div>
        <div class="cart-header__product-column">
            Sản phẩm
        </div>
        <div class="cart-header__price-column">
            Đơn giá
        </div>
        <div class="cart-header__quantity-column">
            Số lượng
        </div>
        <div class="cart-header__action-column">
            Thao tác
        </div>
    </div>
    <div class="cart-list">
        <c:if test="${empty cart}">
            <div class="cart-list__empty">Giỏ hàng rỗng</div>
        </c:if>
        <c:if test="${not empty cart}">
            <form action="${context}/checkout" method="POST" id="checkout-form">

            </form>
            <form action="${context}/user/cart" method="POST" id="delete-multiple-item-form">
                <input type="hidden" name="action" value="delete" form="delete-multiple-item-form">
                <c:forEach items="${cart}" var="cartItem" varStatus="loop">
                    <input type="hidden" name="id" value="${cartItem.product.productId}"
                           form="delete-multiple-item-form">

                    <c:set var="errorMessage" scope="page">
                        <%= session.getAttribute("outOfStock" +
                                ((CartItem)(pageContext.getAttribute("cartItem"))).getProduct().getProductId()) %>
                    </c:set>
                    <c:if test="${errorMessage!='null'}">
                        <div class="out-of-stock-message">
                                ${errorMessage}
                                <% session.removeAttribute("outOfStock" +
                                        ((CartItem)(pageContext.getAttribute("cartItem"))).getProduct().getProductId());%>
                        </div>
                    </c:if>
                    <input type="hidden" name="id" value="${cartItem.product.productId}"
                           form="checkout-form">
                    <div class="cart-item">
                        <div class="cart__check-btn">
                            <input type="checkbox" name="item" form="delete-multiple-item-form"
                                ${loop.count==1&&(not empty isBuyNow)?'checked':''}
                            >
                            <c:if test="${not empty isBuyNow}">
                                <c:remove var="isBuyNow" scope="session" />
                            </c:if>
                        </div>
                        <div class="cart-header__product-column">
                            <a href="" class="cart-header__product-link">
                                <div class="cart-header__product-image"
                                     style="background-image: url('${context}/${cartItem.product.imagePathList[0].path}')"></div>
                                <div class="cart-header__product-name">${cartItem.product.name}
                                </div>
                            </a>

                        </div>
                        <div class="cart-header__price-column">
                            <fmt:formatNumber value="${cartItem.product.price}"
                                              maxFractionDigits="0" type="number"/>
                        </div>
                        <div class="cart-header__quantity-column">
                            <input type="number" value="${cartItem.quantity}" min="1" step="1" name="quantity"
                                   form="checkout-form">
                        </div>
                        <div class="cart-header__action-column">
                            <form action="${context}/user/cart" method="POST"
                                  id="delete-item-form-${cartItem.product.productId}">
                                <input type="hidden" name="action" value="delete"
                                       form="delete-item-form-${cartItem.product.productId}">
                                <input type="hidden" name="id" value="${cartItem.product.productId}"
                                       form="delete-item-form-${cartItem.product.productId}">
                                <button type="submit" class="btn btn-sm btn-delete"
                                        form="delete-item-form-${cartItem.product.productId}">Xóa
                                </button>
                            </form>

                        </div>
                        <form action="${context}/user/cart" method="POST"
                              id="update-item-form-${cartItem.product.productId}" name="update-form">
                            <input type="hidden" name="action" value="update"
                                   form="update-item-form-${cartItem.product.productId}">
                            <input type="hidden" name="id" value="${cartItem.product.productId}"
                                   form="update-item-form-${cartItem.product.productId}">
                            <input type="hidden" name="newQuantity"
                                   form="update-item-form-${cartItem.product.productId}">
                        </form>
                    </div>

                </c:forEach>
            </form>
        </c:if>
    </div>
</div>
<div class="cart__payment">
    <button type="submit" form="delete-multiple-item-form" class="cart__payment__delete"
    >Xóa
    </button>
    <div class="cart__payment__summary">
        <span class="cart__payment__summary-text">Tổng thanh toán (<span id="numOfItems">0</span> sản phẩm): </span>
        <span class="cart__payment__summary-price price" id="totalAmount">0</span>
    </div>
    <button class="cart__payment__checkout-btn btn btn-primary btn-lg" type="submit" form="checkout-form"
            id="checkout-btn">
        Mua hàng
    </button>
</div>

<%--<jsp:include page="../../common/footer.jsp"/>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://shaack.com/projekte/bootstrap-input-spinner/src/bootstrap-input-spinner.js"></script>
<script src="${context}/js/customer/spinnerHandler.js"></script>
<script src="${context}/js/lib/accounting.min.js"></script>
<script src="${context}/js/customer/cart.js?rd=${rand}"></script>
</body>
</html>
