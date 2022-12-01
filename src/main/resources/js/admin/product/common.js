$(function () {
    const minusRowBtn = $('.form-group__minus-row-btn')
    const addRowBtn = $('.form-group__add-row-btn')
    const addForm = $(".products__add-form")
    addForm.click(function (e) {
        const minusRowBtn = $(e.target).parent('.form-group__minus-row-btn')
        if (minusRowBtn != null) {
            const count = $('.dimen-mater-price-group').length
            if (count > 1) {
                minusRowBtn.parent().remove()
            }
        }
    })

    const editForm = $(".product__edit-form")
     editForm.click(function (e) {
         const minusRowBtn = $(e.target).parent('.form-group__minus-row-btn')
         if (minusRowBtn != null) {
             const count = $('.dimen-mater-price-group').length
             if (count > 1) {
                 minusRowBtn.parent().remove()
             }
         }
     })
    addRowBtn.click(function () {
        const diMaPrGroup = $(this).prev()
        diMaPrGroup.after(diMaPrGroup.clone())
    })


    $("#coba").spartanMultiImagePicker({
        fieldName: 'images', maxCount: 5,
        rowHeight: '120px',
        groupClassName: 'col-md-3 col-sm-3 col-xs-6',
        dropFileLabel: 'Thả ảnh vào đây'
    })
})