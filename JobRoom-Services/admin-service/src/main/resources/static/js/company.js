
var baseAPi="http://35.197.132.204:30005/api/v1/"

$('document').ready(function () {
    $('#btnSearch').click(function () {
        var search=$('#cp-input-search').val()
        window.location.href='/admin/companies/?name='+search+"&page=0"
    })


})
function viewDetail(id) {
    $.get( baseAPi+"companies/admin/"+id, function( data ) {
        var img;
        if(data.data.logo=='string'|| data.data.logo==null){
            img="https://cel.ac/wp-content/uploads/2016/02/placeholder-img-1.jpg"
        }else{
            img=data.data.logo
        }
        $('#cpLogo').attr("src", img)
        $('#companyName').text(data.data.name)
        $('#primaryEmail').text(data.data.primaryEmail)
        $('#seondaryEmail').text(data.data.secondaryEmail)
        $('#website').text(data.data.website)
        $('#address').text(data.data.street+" ,"+data.data.commune+", "+data.data.district+" ,"+data.data.city)
        $('#phoneNumber').text(data.data.telephone)
        console.log(data.data)
    });
}
function disable(data,id) {
    console.log(id)
    console.log(data)
    var value =$('#'+data)
    $('#delete-modal').modal('show')
    console.log(value[0].checked)
    if(value[0].checked){
        $('#btn-disable-enable').text("Disable")
        $('#modal-text').text("Do you want to Disable this HR?")
    }else{
        $('#modal-text').text("Do you want to Enable this HR?")
        $('#btn-disable-enable').text("Enable")
    }


    $('#delete-modal').find('#btn-disable-enable').click(function () {
        if(value[0].checked==true){

            $.post( baseAPi+"companies/unban/"+id, function( data ) {
                console.log(data)
                location.reload()
            });
        }else{
            $.post( baseAPi+"companies/ban/"+id, function( data ) {
                console.log(data)
                location.reload()
            });
        }
    })

    // location.reload()
}
function cancel() {
    location.reload()
}