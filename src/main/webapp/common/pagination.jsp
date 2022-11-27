<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- PAGINATION -->
<nav>
    <ul class="pagination justify-content-center">
        <li class="page-item ${(empty param.page|| param.page=='1')?'disabled':''}"
        >
            <button class="page-link "
                    aria-label="Trước" ${(empty param.page|| param.page=='1')?'':'data-page='+='\"'+=param.page-1+='\"'}>
                <span aria-hidden="true"><</span>
            </button>
        </li>
        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <li class="page-item ${param.page==i||(empty param.page && i==1)?'active':''}">
                <button class="page-link" data-page="${i}"
                >${i}
                </button>
            </li>
        </c:forEach>
        <li class="page-item
         ${(param.page==numberOfPages||numberOfPages==1)?'disabled':''}"
        >
            <button class="page-link"
                    aria-label="Sau" ${(param.page==numberOfPages||numberOfPages==1)?'':'data-page='+='\"'+=param.page+1+='\"'}>
                <span aria-hidden="true">></span>
            </button>
        </li>
    </ul>
</nav>