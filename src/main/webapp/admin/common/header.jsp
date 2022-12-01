<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %></c:set>

<%-- FONT ICON--%>
<link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
<%--CONTENT--%>
<div class="header">
    <a href="${context}/admin" class="header__logo"></a>
    <c:if test="${headerAction!=null}">

        <div class="header__action">${headerAction}</div>
    </c:if>
    <c:if test="${admin!=null}">
        <div class="information">
                <span class="information__icon">
                   <i class="lar la-user-circle"></i>
                </span>
            <span class="information__name">
                    ${admin.fullName}
            </span>
            <div class="information__popover">
                <a href="${context}/admin/update" class="information__popover-item">Thay đổi thông tin</a>
                <a href="${context}/admin/logout" class="information__popover-item">Đăng xuất</a>
            </div>
        </div>
    </c:if>
</div>