var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');


$('#singleUploadForm').submit(function(event) {
    var formElement = this;
    var formData = new FormData(formElement);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/convert",
        data: formData,
        processData: false,
        contentType: false,
        success: function (req) {
            console.log(req);
            var blob = req;
            var fileName = req.getResponseHeader("fileName") //if you have the fileName header available
            var link=document.createElement('a');
            link.href=window.URL.createObjectURL(blob);
            link.download=fileName;
            link.click();
        },
        error: function (error) {
            console.log(error);
            // process error
        }
    });

    event.preventDefault();
});
