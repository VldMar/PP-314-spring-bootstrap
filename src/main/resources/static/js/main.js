$( document ).ready(function() {

});

function onEditClick(id) {
    $.ajax({
        type: "get",
        url: "/update/" + id,
        success: function (data) {
            console.log("SUCCESS : ", data);
            $("#update-modal").html(data);
            $("#editModal").modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}