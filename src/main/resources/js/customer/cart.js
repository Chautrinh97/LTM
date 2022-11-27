$(function () {
    const quantitySpinner = $('input[name=quantity]')
    const checkoutForm = $('#checkout-form')
    const checkBoxes = $('input[name=item]')
    const numOfItems = $('#numOfItems')
    const totalAmount = $('#totalAmount')
    const allItem = $('input[name=all-item]')


    allItem.change(function () {
        if (allItem.prop('checked')) {
            for (const checkbox of checkBoxes) {
                $(checkbox).prop('checked', true)
                $(checkbox).change()
            }
        } else {
            for (const checkbox of checkBoxes) {
                $(checkbox).prop('checked', false)
                $(checkbox).change()
            }
        }
    })
    quantitySpinner.change(
        function () {
            const checkBtnInput = $(this).parent().prevAll('.cart__check-btn').first().find('input')
            if (checkBtnInput.prop('checked')) {
                totalAmount.text(accounting.formatMoney(countTotalAmount(checkBoxes), '', 0, '.', '.'))
            }
            const updateForm = $(this).parent().parent().find('form[name=update-form]')
            updateForm.find("input[name=newQuantity]").val($(this).val())
            updateForm.submit()
        }
    )
    checkBoxes.change(function () {


        if (isAllBoxChecked(checkBoxes)) {
            allItem.prop('checked', true)
        } else {
            allItem.prop('checked', false)

        }
        numOfItems.text(countCheckboxes(checkBoxes))
        totalAmount.text(accounting.formatMoney(countTotalAmount(checkBoxes), '', 0, '.', '.'))
    })
    // Buy now
    const firstCheckBoxItem =     checkBoxes.eq(0)
    if (firstCheckBoxItem.prop('checked')){
        firstCheckBoxItem.change();
    }
    const checkoutBtn = $('#checkout-btn')
    checkoutBtn.click(function (e) {

        for (const checkbox of checkBoxes) {
            if (!$(checkbox).prop('checked')) {
                const cartItem = $(checkbox).parent().parent()
                const idInput = cartItem.prev()
                const quantityInput = cartItem.find('input[name=quantity]')
                console.log(checkBoxes.index($(checkbox)))
                console.log(idInput)
                console.log(quantityInput)
                idInput.attr('disabled', true)
                quantityInput.attr('disabled', true)
            }
        }
    })

    function countCheckboxes(checkBoxes) {
        let count = 0;
        for (const checkBox of checkBoxes) {
            if (checkBox.checked) {
                count++;
            }
        }
        return count;
    }

    function countTotalAmount(checkBoxes) {
        let totalAmount = 0
        for (const checkBox of checkBoxes) {
            if (checkBox.checked) {
                const item = $(checkBox).parents('.cart-item').eq(0)
                const quantity = item.find('input[name=quantity]').val()

                const price = item.find('.cart-header__price-column').text().replaceAll('.', '')
                totalAmount += quantity * price

            }
        }
        return totalAmount;
    }

    function isAllBoxChecked(checkBoxes) {
        for (const checkBox of checkBoxes) {
            if (!checkBox.checked) return false;
        }
        return true;
    }
});