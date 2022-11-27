<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<%-- FONT ICON--%>
<link rel="stylesheet"
      href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
<%--CONTENT--%>
<div class="user-header">
    <div class="user-header__contact">
        <div class="user-header__phone">
            <i class="las la-phone-volume"></i>
            <a href="tel:0395397398" class="user-header__phone__text">0395397398</a>
        </div>
        <div class="user-header__social">
            <span class="user-header__social__text">Hỗ trợ:</span>
            <span class="user-header__facebook-icon"><i class="lab la-facebook"></i></span>
            <span class="user-header__insta-icon">
                <i class="lab la-instagram"></i>
            </span>
        </div>

    </div>
    <c:choose>
        <c:when test="${customer!=null}">
            <div class="user__information">
                <span class="user__information__icon">
                    <i class="lar la-user-circle"></i>
                </span>
                <span class="user__information__name">
                        ${customer.fullName}
                </span>
                <div class="user__information__popover">
                    <a href="${context}/user/account" class="user__information__popover-item">Tài khoản của tôi</a>
                    <a href="${context}/logout" class="user__information__popover-item">Đăng xuất</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="user-header__not-logged-in">
                <a href="${context}/register" class="user-header__not-logged-in__item">Đăng ký</a>
                <a href="${context}/login?from=${uri}"
                   class="user-header__not-logged-in__item">Đăng
                    nhập</a>
            </div>
        </c:otherwise>
    </c:choose>


</div>