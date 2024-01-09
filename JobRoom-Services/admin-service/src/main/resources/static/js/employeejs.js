$('document').ready(function(){
    // display post
    $('.table .fa-eye').on('click', function(event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function(employee){
            var img;
            if(employee.data.profilePicture==null){
                console.log("profile pic is empty")
                img="https://cel.ac/wp-content/uploads/2016/02/placeholder-img-1.jpg"
            }else{
                img=employee.data.profilePicture
            }
            console.log(employee)
            $('#employeeName').text(employee.data.fullName);
            $('#employeeCity').text(employee.data.city);
            $('#employeeEmail').text(employee.data.email);
            $('#employeeTelephone').text(employee.data.telephone);
            $('#employeeProfilePic').attr('src', img);
        });

        $('#display-employee-modal').modal();
    });

    //search
    $('#search_employee_button').on('click', function () {
        var search=$('#search_employee').val();
        console.log('search box ' + search);
        window.location.href='/find_employee/name/'+search
    })
});

//change the status with modal
// function disable(data,id) {
//     console.log(id)
//     console.log(data)
//     var value =$('#'+data)
//     $('#set-status-modal').modal('show')
//     console.log(value[0].checked)
//     if(value[0].checked){
//         $('#btn-disable-enable').text("Deactivate")
//         $('#modal-text').text("Do you want to deactivate this user's account?")
//     }else{
//         $('#modal-text').text("Do you want to activate this user's account?")
//         $('#btn-disable-enable').text("Activate")
//     }
//
//
//     $('#set-status-modal').find('#btn-disable-enable').click(function () {
//         if(value[0].checked==true){
//
//             $.get( '/set_employee_true/' + id, function( data ) {
//                 console.log(data)
//                 location.reload()
//             });
//         }else{
//             $.get('/set_employee_false/' + id, function( data ) {
//                 console.log(data)
//                 location.reload()
//             });
//         }
//     })
//
//     // location.reload()
// }

//change the status with modal
function disable(data,id) {
    console.log(id)
    console.log(data)
    var value =$('#'+data)
    $('#set-status-modal').modal('show')
    console.log(value[0].checked)
    if(value[0].checked){
        $('#btn-disable-enable').text("Deactivate")
        $('#modal-text').text("Do you want to deactivate this user?")
    }else{
        $('#modal-text').text("Do you want to activate this user?")
        $('#btn-disable-enable').text("Activate")
    }


    $('#btn-disable-enable').click(function () {
        if(value[0].checked==true){
            console.log("Inside second funtion")
            $.get( '/set_employee_true/' + id, location.reload());
            location.reload()
        }else{
            $.get('/set_employee_false/' + id, function( data ) {
                alert(data)
                location.reload()
            });
            location.reload()
        }
        location.reload()
    })

}
