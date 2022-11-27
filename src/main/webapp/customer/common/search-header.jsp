<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--CONTENT--%>
<div class="header">
    <a href="${context}/" class="header__logo"></a>
    <c:if test="${not empty action}">
        <div class="header__action">
                ${action}
        </div>
    </c:if>
    <c:if test="${empty action}">
        <div class="search">
            <form action="${context}/products/search" class="search-form">
                <input type="search" class="search-input" name="keyword" value="${param.keyword}">
                <button class="btn btn-primary btn-search"><i class="las la-search"></i></button>
            </form>
        </div>
        <div class="cart">
            <a href="${context}/user/cart" class="cart-icon">
                <i class="las la-shopping-cart"></i>
                <div class="cart-icon__badge">
                        ${cart.size()}
                </div>
            </a>
            <div class="cart-preview">
                <div class="cart-preview__heading">
                    Sản phẩm mới thêm
                </div>
                <c:if test="${empty cart}">
                    <div class="cart-preview__empty">Giỏ hàng rỗng</div>
                </c:if>
                <c:if test="${not empty cart}">
                    <div class="cart-preview__cart-list">

                        <c:forEach items="${cart}" var="cartItem" varStatus="loop">
                            <c:if test="${loop.count<=5}">
                                <a href="" class="cart-preview__cart-item">
                                    <div class="cart-preview__cart-item__img"
                                         style="background-image: url('${context}/${cartItem.product.imagePathList[0].path}')">
                                    </div>
                                    <div class="cart-preview__cart-item__name">
                                            ${cartItem.product.name}
                                    </div>
                                    <div class="cart-preview__cart-item__price">
                                        <fmt:formatNumber value="${cartItem.product.price}"
                                                          maxFractionDigits="0" type="number"/>
                                    </div>

                                </a>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="cart-preview__footer">
                        <span class="cart-preview__items-sum">${cart.size()} thêm vào giỏ hàng</span>
                        <a href="${context}/user/cart" class="cart-preview__view-action btn btn-primary btn-lg">Xem giỏ
                            hàng</a>
                    </div>
                </c:if>
            </div>
        </div>
    </c:if>
</div>