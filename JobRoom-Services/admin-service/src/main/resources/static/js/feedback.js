
var baseAPi="http://35.197.132.204:30006/api/v1/"

function deleteFeedback(id) {

    $('#delete-modal').modal('show')
    $('#delete-modal').find('#btn-disable-enable').click(function () {
        $.ajax({
            url: baseAPi+"feedback/"+id,
            type: 'DELETE',
            success: function(result) {
                location.reload()
            }
        });
    })
}
