var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');


$('#singleUploadForm').submit(function(event) {
    var formElement = this;
    var formData = new FormData(formElement);
    var fileName=$('#singleFileUploadInput')[0].files[0].name.replace('.ai','.svg');
    $('#progress').show();
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/convert",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
        	$('#progress').hide();
            console.log(data);
            const url = window.URL.createObjectURL(new Blob([data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            
        },
        error: function (error) {
            console.log(error.responseJSON);
            $('#progress').text(error.responseJSON.status+' : '+error.responseJSON.message);
            $('#progress').addClass('error');
        }
    });

    event.preventDefault();
});
