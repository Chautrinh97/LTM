<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <%--FONT AWESOME--%>
    <!-- FONT    -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/product/common.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/product/editForm.css?rd=${rand}">


    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <%-- SCRIPT--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content">

    <form action="${context}/admin/products" enctype="multipart/form-data" method="post" class="product__edit-form">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="productId" value="${product.productId}"/>
        <div class="form-group">
            <span class="form-group__label">Tên sản phẩm</span>

            <input type="text" name="productName" id="productName" value="${product.name}">

        </div>
        <div class="form-group">
            <span class="form-group__label">Ảnh</span>

            <div class="input-images-wrapper">
                <div id="coba">
                    <c:if test="${product.imagePathList!=null}">
                        <c:forEach items="${product.imagePathList}" var="imagePath" varStatus="loop">
                            <div class="col-md-3 col-sm-3 col-xs-6 spartan_item_wrapper"
                                 data-spartanindexrow="${loop.index}"
                                 style="margin-bottom : 20px; ">
                                <div style="position: relative;">
                                    <div class="spartan_item_loader" data-spartanindexloader="${loop.index}"
                                         style=" position: absolute; width: 100%; height: 120px; background: rgba(255,255,255, 0.7); z-index: 22; text-align: center; align-items: center; margin: auto; justify-content: center; flex-direction: column; display : none; font-size : 1.7em; color: #CECECE">
                                        <i class="fas fa-sync fa-spin"></i>
                                    </div>
                                    <label class="file_upload"
                                           style="width: 100%; height: 120px; border: 2px dashed #ddd; border-radius: 3px; cursor: pointer; text-align: center; overflow: hidden; padding: 5px; margin-top: 5px; margin-bottom : 5px; position : relative; display: flex; align-items: center; margin: auto; justify-content: center; flex-direction: column;">
                                        <a
                                                href="javascript:void(0)" data-spartanindexremove="${loop.index}"
                                                style="right: 3px; top: 3px; background: rgb(237, 60, 32); border-radius: 3px; width: 30px; height: 30px; line-height: 30px; text-align: center; text-decoration: none; color: rgb(255, 255, 255); position: absolute !important;"
                                                class="spartan_remove_row"><i class="fas fa-times"></i>
                                        </a>
                                        <img
                                                style="width: 64px; margin: 0px auto; vertical-align: middle; display: none;"
                                                data-spartanindexi="${loop.index}"
                                                src=""
                                                class="spartan_image_placeholder">
                                        <p data-spartanlbldropfile="${loop.index}" style="color : #5FAAE1; display:
                                        none;
                                        width :
                                         auto; ">
                                            Thả ảnh vào
                                            đây</p><img style="width: 100%; vertical-align: middle;" class="img_"
                                                        data-spartanindeximage="${loop.index}"
                                                        src="${context}/${imagePath.path}"
                                                        path="${imagePath.path}"><input
                                            class="form-control spartan_image_input" accept="image/*"
                                            data-spartanindexinput="${loop.index}"
                                            style="display : none" name="images" type="file"></label></div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="form-group">
            <span class="form-group__label">Mô tả sản phẩm</span>

            <textarea name="description" id="" cols="60" rows="8">${product.description}</textarea>
        </div>
        <div class="form-group">
            <span class="form-group__label">Kích thước</span>
            <input type="text" name="dimension" value="${product.dimension}">
        </div>
        <div class="form-group">
                             <span class="form-group__label">
                                Chất liệu
                             </span>

            <input type="text" name="material" id="material" value="${product.material}"
            >
        </div>
        <div class="form-group">
            <span class="form-group__label">Đơn giá</span>

            <input type="text" name="price" value="${product.price}">

        </div>

        <div class="form-group">
            <span class="form-group__label">Thời gian bảo hành</span>
            <input type="number" name="warrantyPeriod" value="${product.warrantyPeriod}">
            <span class="warranty-period-unit">tháng</span>
        </div>
        <div class="form-group">
            <span class="form-group__label">Danh mục</span>
            <select class="form-select category-select" name="categoryId">
                <option value="" ${product.category==null?'selected':''}>Chưa phân loại</option>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.categoryId}"
                        ${category.categoryId==product.category.categoryId?'selected':''}>${category.categoryName}</option>
                </c:forEach>

            </select>
        </div>
        <button class="btn btn-primary save-btn">
            Lưu sản phẩm
        </button>

    </form>
</div>
<script src="${context}/js/admin/product/image-picker.js?rd=${rand}"></script>
<script src="${context}/js/admin/product/common.js?rd=${rand}"></script>
<script src="${context}/js/admin/product/editForm.js?rd=${rand}"></script>
<%--<jsp:include page="../../common/footer.jsp"/>--%>
</body>
</html>
