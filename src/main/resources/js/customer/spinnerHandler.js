$(function () {
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
});