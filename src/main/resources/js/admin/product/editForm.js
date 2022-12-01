function getFileName(filePath) {
    const begin = filePath.search("products/")
    return filePath.slice(begin + 9, filePath.length-36) + ".jpg"
}

const imageInput = $('input[type=file]')
for (const imageInputElement of imageInput) {
    const _this = $(imageInputElement)
    console.log(getFileName(_this.prev().attr("path")))
    loadURLToInputField(_this[0], _this.prev().prop('src'), getFileName(_this.prev().attr("path")))
}

// loadURLToInputField("http://localhost:8080/noithatctvv/images/products/ban-an-roma-1.jpg");

function loadURLToInputField(input, url, fileName) {
    getImgURL(url, (imgBlob) => {

        let file = new File([imgBlob], fileName, {type: "image/jpeg", lastModified: new Date().getTime()}, 'utf-8');
        let container = new DataTransfer();
        container.items.add(file);
        input.files = container.files;
        // document.querySelector('#status').files = container.files;

    })
}

// xml blob res
function getImgURL(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        callback(xhr.response);
    };
    xhr.open('GET', url);
    xhr.responseType = 'blob';
    xhr.send();
}
