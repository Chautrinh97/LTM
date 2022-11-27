<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="rand" scope="request"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang chủ</title>
    <link rel="shortcut icon" href="${context}/favicon.ico"/>

    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <%-- APP--%>
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/common.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/home.css?rd=${rand}">

    <%--    SCRIPT--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col col-2-4">
                <c:if test="${search==true}">
                    <div class="search__filter">
                        <div class="search__filter__heading">
                            <span class="search__filter__heading-icon"><i class="las la-filter"></i></span>
                            <span class="search__filter__heading-text">BỘ LỌC TÌM KIẾM</span>
                        </div>
                        <div class="search__filter__group">
                            <div class="search__filter__group__text-descripton">Khoảng giá</div>
                            <div class="search__filter__group__content search__filter__price-range">
                                <input type="text" maxlength="13"
                                       class="search__filter__price-range__input search__filter__price-range__input--min-price"
                                       placeholder="₫ TỪ" value="${param.minPrice}">
                                <div class="search__filter__price-range__line"><i class="las la-minus"></i></div>
                                <input type="text" maxlength="13"
                                       class="search__filter__price-range__input search__filter__price-range__input--max-price"
                                       placeholder="₫ ĐẾN" value="${param.maxPrice}">
                            </div>
                        </div>
                        <button class="btn search__filter__apply-btn btn-primary">
                            ÁP DỤNG
                        </button>

                    </div>
                </c:if>
                <c:if test="${search!=true}">
                    <div class="category-wrapper">
                        <div class="category__header">
                            <i class="las la-bars category__header__menu-icon"></i>
                            <span class="category__header__text">
Danh mục</span>
                        </div>
                        <div class="category-list">
                            <a href="${context}/" class="category-item ${empty param?'active':''}">Tất cả</a>
                            <c:forEach items="${categoryList}" var="category">
                                <a href="${context}/products?category=${category.categoryName}" class="category-item
                            ${category.categoryName==param.category?'active':''}">
                                        ${category.categoryName}
                                </a>
                            </c:forEach>


                        </div>
                    </div>

                </c:if>
            </div>

            <div class="col col-9-6">
                <div class="products-wrapper">
                    <div class="products__sort-selection">
                        <div class="products__sort-selection__heading-text">
                            Sắp xếp theo
                        </div>
                        <div class="products__sort-selection__item order-bar__option ${param.sortBy=='default'|| empty param.sortBy?'active':''}"
                             data-sort="default">
                            Mới nhất
                        </div>
                        <div class="products__sort-selection__price products__sort-selection__item order-bar__option
                        ${param.sortBy=='price'?'active':''}" data-sort="price">
                            <div class="products__sort-selection__price__selected">
                                <span class="products__sort-selection__price__selected__text">
                                    <c:choose>
                                        <c:when test="${param.sortBy=='price' && param.order== 'ASC'}">
                                            Giá: Thấp đến cao
                                        </c:when>
                                        <c:when test="${param.sortBy=='price' && param.order== 'DESC'}">
                                            Giá: Cao đến thấp
                                        </c:when>
                                        <c:otherwise>Giá</c:otherwise>
                                    </c:choose>
                                </span>
                                <i class="las la-angle-down products__sort-selection__price__selected__icon">
                                </i>
                            </div>
                            <div class="products__sort-selection__price__popover">
                                <span class="products__sort-selection__price-item" data-order="ASC">
                                    Giá: Thấp đến cao
                                </span>
                                <span class="products__sort-selection__price-item" data-order="DESC">
                                    Giá: Cao đến thấp
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="products">
                        <div class="container-fluid">
                            <div class="row">
                                <c:choose>
                                    <c:when test="${not empty productList}">
                                        <c:forEach items="${productList}" var="product">
                                            <c:set var="currentProduct" scope="request" value="${product}"/>

                                            <div class="col col-3">

                                                <a href="${context}/products/${product.uri}" class="product">
                                                    <div class="product__image" style="background-image:
                                                            url('${context}/${product.imagePathList[0].path}');">
                                                    </div>
                                                    <div class="product__name">
                                                            ${product.name}
                                                    </div>
                                                    <div class="product__price">
                                                        <fmt:formatNumber maxFractionDigits="0"
                                                                          value="${product.price}" type="number"/>

                                                    </div>
                                                </a>
                                            </div>
                                        </c:forEach>

                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-products-found">
                                            RẤT TIẾC, KHÔNG CÓ SẢN PHẨM NÀO
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../../common/pagination.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- SURROGATE FORM -->
<form action="${context}/products${search?'/search':''}" id="surrogateForm">
    <c:if test="${search}">
        <input type="hidden" name="keyword"
               value="${param.keyword}" ${empty param.keyword?'disabled':''}>
    </c:if>
    <c:if test="${!search}">
        <input type="hidden" name="category"
               value="${param.category}" ${empty param.category?'disabled':''}>
    </c:if>

    <input type="hidden" name="page" value="${not empty param.page? param.page: 1}">
    <input type="hidden" name="minPrice" value="${param.minPrice}"
    ${empty param.minPrice?'disabled':''}>
    <input type="hidden" name="maxPrice"
           value="${param.maxPrice}"
    ${empty param.maxPrice?'disabled':''}>

    <input type="hidden" name="sortBy"
           value="${param.sortBy}" ${empty param.sortBy?'disabled':''}>
    <input type="hidden" name="order" value="${param.order}" ${empty param.order?'disabled':''}>
</form>

<script src="${context}/js/customer/search.js?rd=${rand}"></script>

<jsp:include page="../../common/footer.jsp"/>

</body>
</html>
