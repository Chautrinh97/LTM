$(function () {
    // HANDLE SEARCH, FILTER , SORT, PAGE FORM
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
    sortOptions.click(function (e) {
        if (sortByInput.attr('disabled')) {
            sortByInput.removeAttr('disabled');
        }
        const sortByValue = $(this).attr('data-sort');
        sortByInput.val(sortByValue)

        // Price tăng hay giảm
        if (sortByValue === 'price') {
            orderInput.val($(e.target).attr('data-order'))
            if (orderInput.attr('disabled')) {
                orderInput.removeAttr('disabled');
            }
        }
        surrogateForm.submit()
    })
    const minPriceInput = $('.search__filter__price-range__input--min-price')
    const maxPriceInput = $('.search__filter__price-range__input--max-price')

    const minPriceSurrogateForm = surrogateForm.find('input[name=minPrice]')
    const maxPriceSurrogateForm = surrogateForm.find('input[name=maxPrice]')

    const applyFilterBtn = $('.search__filter__apply-btn')
    applyFilterBtn.click(function () {


        if (minPriceInput.val() !== "") {
            if (minPriceSurrogateForm.attr('disabled')) {
                minPriceSurrogateForm.removeAttr('disabled');
            }
            minPriceSurrogateForm.val(minPriceInput.val())
        } else {
            minPriceSurrogateForm.prop('disabled', true)
        }
        if (maxPriceInput.val() !== "") {
            if (maxPriceSurrogateForm.attr('disabled')) {
                maxPriceSurrogateForm.removeAttr('disabled');
            }
            maxPriceSurrogateForm.val(maxPriceInput.val())
        } else {
            maxPriceSurrogateForm.prop('disabled', true)
        }
        surrogateForm.submit()
    })
})
