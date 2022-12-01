$(function () {
    $('.search__add-btn').click(function () {
        setTimeout(function () {
            $('#addModal').find("input[name='categoryName']").focus();
        }, 500)
    })
    $('.search__edit-btn').click(function () {
        setTimeout(function () {
            $('#editModal').find("input[name='categoryName']").focus();
        }, 500)
    })

    $(".toast").toast('show');


})