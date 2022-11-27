$(function () {
    // INPUT SPINNER
    var config = {
        groupClass: "input-spinner-wrapper",
        // the template of the input
        template:
            '<span class="${groupClass}">' +
            '<button class="btn-decrement btn-spinner" type="button">${decrementButton}</button>' +
            '<input type="number" inputmode="decimal"/>' +
            '<button class="btn-increment  btn-spinner" type="button">${incrementButton}</button>' +
            "</span>"
    };
    $("input[type='number']").inputSpinner(config);

    // set from input
    const fromInput = $('input[name=from]')
    fromInput.val(window.location.href)

    const addToCartForm = $('#add-to-cart-form')
    const buyNowCheckbox = $('#buy-now-check-box')
    const buyNowBtn = $('#buy-now-btn')
    buyNowBtn.click(function (e){
        e.preventDefault();
        buyNowCheckbox.prop('checked', true)
        addToCartForm.submit()
    })
});