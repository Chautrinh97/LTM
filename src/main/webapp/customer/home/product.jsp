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
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%--    LINE-AWESOME--%>
    <link rel="stylesheet"
          href="https://maxst.icons8.com/vue-static/landings/line-awesome/font-awesome-line-awesome/css/all.min.css">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/common.css?rd=${rand}">

    <link rel="stylesheet" href="${context}/css/customer/product.css?rd=${rand}">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <c:if test="${not empty cartSuccessMessage}">
            <div class="success-message">${cartSuccessMessage}</div>
            <c:remove var="cartSuccessMessage" scope="session"/>
        </c:if>
        <div class="product__header">
            <div class="container-fluid">
                <div class="row">
                    <div class="col col-4">
                        <div class="d-flex align-items-center position-relative h-100">
                            <div class="product__image">
                                <div id="product-image-slide" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner">
                                        <c:forEach items="${product.imagePathList}" var="path" varStatus="loop">
                                            <div class="carousel-item ${loop.count==1?'active':''}">
                                                <img src="${context}/${path.path}"
                                                     class="d-block w-100">
                                            </div>
                                        </c:forEach>

                                    </div>
                                    <button class="carousel-control-prev" type="button"
                                            data-bs-target="#product-image-slide"
                                            data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button"
                                            data-bs-target="#product-image-slide"
                                            data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>
                                </div>
                            </div>
                            <div class="product__category">
                                <span class="product__category__text-heading">
                                   Danh mục
                                </span>
                                <c:choose>
                                    <c:when test="${empty product.category}">
                                        <span class="product__category__no-category">
                                            Chưa phân loại
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${context}/products?category=${product.category.categoryName}"
                                           class="product__category_text-description">
                                                ${product.category.categoryName}
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col col-8">
                        <div class="product__description-and-action">
                            <div class="product__name">
                                ${product.name}
                            </div>
                            <div class="product__price">
                                <fmt:formatNumber type="number" maxFractionDigits="0"
                                                  value="${product.price}"/>
                            </div>
                            <div class="product__info">
                                <span class="product__info__text-heading">
                                    Kích thước
                                </span>
                                <span>${product.dimension}</span>
                            </div>
                            <div class="product__info">
<span class="product__info__text-heading">
                                    Vật liệu
                                </span>
                                <span>${product.material}</span>
                            </div>
                            <div class="product__info">
<span class="product__info__text-heading">
                                    Bảo hành
                                </span>
                                <span class="product__info__text-content">${product.warrantyPeriod} tháng</span>
                            </div>
                            <div class="product-quantity">
                                <form action="${context}/user/cart" id="add-to-cart-form" method="POST">
                                    <input type="hidden" name="from">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="id" value="${product.productId}">
                                    <input type="number" value="1" min="1" step="1" name="quantity"/>
                                    <input type="checkbox" hidden id="buy-now-check-box" name="buy-now"
                                           />
                                </form>
                            </div>
                            <div class="product__action">
                                <button type="submit" class="product__action__add-to-cart" form="add-to-cart-form"><i
                                        class="las la-shopping-cart product__action__add-to-cart__icon"></i>Thêm
                                    vào giỏ hàng
                                </button>


                                <button type="submit" class="product__action__buy" form="add-to-cart-form"
                                        id="buy-now-btn">
                                    Mua ngay
                                </button>

                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="product__meta-info">
            <div class="product__meta-info__tabs">
                <span class="product__meta-info__tab-item">Mô tả</span>
            </div>
            <div class="product__meta-info__content">
                <div class="product__meta-info__content-of-item product__meta-info__content__description">
                    ${product.description}
                </div>
            </div>
        </div>
        <c:if test="${not empty similarProducts}">
            <div class="product__similar-product">
                <div class="product__similar-product__text-heading">
                    SẢN PHẨM TƯƠNG TỰ
                </div>
                <div class="product__similar-product__product-list">
                    <div class="container-fluid">
                        <div class="row">

                            <c:forEach items="${similarProducts}" var="product">
                                <div class="col col-2">
                                    <a href="${context}/products/${product.uri}"
                                       class="product__similar-product__product-list__product">
                                        <div class="product__similar-product__product-list__product__product__image"
                                             style="background-image:
                                                     url('${context}/${product.imagePathList[0].path}');">
                                        </div>
                                        <div class="product__similar-product__product-list__product__name">
                                                ${product.name}
                                        </div>
                                        <div class="product__similar-product__product-list__product__price">
                                            <fmt:formatNumber value="${product.price}"
                                                              type="number" maxFractionDigits="0"/>
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </div>
        </c:if>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://shaack.com/projekte/bootstrap-input-spinner/src/bootstrap-input-spinner.js"></script>
<script src="${context}/js/customer/product.js?rd=${rand}"></script>
<jsp:include page="../../common/footer.jsp"/>


</body>
</html>
