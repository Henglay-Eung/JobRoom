var baseAPi="http://35.197.132.204:30006/api/v1/"

$('document').ready(function () {
    $('img').on("error", function() {
        $(this).attr('src', 'https://cel.ac/wp-content/uploads/2016/02/placeholder-img-1.jpg');
    })

    // $('#type-announcement').change(function (id) {
    //    var value= $('#type-announcement').val()
    //    // window.location.href="/admin/companies/{id}/active-announcement"
    //     alert(id)
    // })
})

function search(id) {
    var search=$('#at-input-search').val()
    window.location.href='/admin/companies/'+id+'/announcements?position='+search+"&page=0"
}

function chooseAnnoun(id) {
    var value= $('#type-announcement').val()
    if(value=="open"){
        // window.location.href="/admin/companies/"+id+"/active-announcement"
        window.location.href="/admin/companies/"+id+"/announcements?open="+value
    }else{
        // window.location.href="/admin/companies/"+id+"/close-announcement"
        window.location.href="/admin/companies/"+id+"/announcements?close="+value
    }

}

function disable(data,id) {
    console.log(id)
    console.log(data)
    var value =$('#'+data)
    $('#delete-modal').modal('show')
    console.log(value[0].checked)
    if(value[0].checked){
        $('#btn-disable-enable').text("Disable")
        $('#modal-text').text("Do you want to Disable this Announcement?")
    }else{
        $('#modal-text').text("Do you want to Enable this Announcement?")
        $('#btn-disable-enable').text("Enable")
    }


    $('#delete-modal').find('#btn-disable-enable').click(function () {
        if(value[0].checked==true){

            $.post( baseAPi+"announcements/unban/"+id, function( data ) {
                console.log(data)
                location.reload()
            });
        }else{
            $.post( baseAPi+"announcements/ban/"+id, function( data ) {
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

function submitDate(id) {
    var search=$('#at-input-search').val()
    var start=$('#start-date').val()
    var end=$('#close-date').val()
    window.location.href='/admin/companies/'+id+'/announcements?startDate='+start+"&endDate="+end+"&page=0"+"&position="+search
}