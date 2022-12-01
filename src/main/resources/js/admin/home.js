$(function () {


    // HANDLE SEARCH, SORT, PAGE FORM
    const surrogateForm = $('#surrogateForm')
    const pageInput = surrogateForm.find('input[name=page]')
    const sortByInput = surrogateForm.find('input[name=sortBy]')
    const orderInput = surrogateForm.find('input[name=order]')
    const pageBtn = $('.page-link')
    pageBtn.click(function () {
        if (pageInput.attr('disabled')) {
            pageInput.removeAttr('disabled');
        }
        pageInput.val($(this).attr('data-page'))
        surrogateForm.submit()
    })


    const sortOptions = $('.order-bar__option')
    sortOptions.click(function () {
        if (sortByInput.attr('disabled')) {
            sortByInput.removeAttr('disabled');
        }
        if (orderInput.attr('disabled')) {
            orderInput.removeAttr('disabled');
        }
        sortByInput.val($(this).attr('data-sort'))
        orderInput.val($(this).attr('data-order'))
        surrogateForm.submit()
    })


});