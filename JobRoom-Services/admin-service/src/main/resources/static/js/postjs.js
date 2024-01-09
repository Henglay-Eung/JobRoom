

$('document').ready(function(){
    // delete funtion
    $('.table .fa-trash-alt').on('click',function(event){
        event.preventDefault();
        var href = $(this).attr('href');
        console.log("delete function " , href)
        $('#delete-modal #delRef').attr('href', href);
        $('#delete-modal').modal();
    });

    // display post
    $('.table .fa-eye').on('click', function(event){
        event.preventDefault();
        console.log("Heello from display post")
        var href = $(this).attr('href');
        console.log(href + " href")
        $.get(href, function(post){
            console.log(post)
            $('#postUsername').text(post.data.userResponse.username);
            $('#postCaption').text(post.data.caption);
            $('#postCreatedDate').text(post.data.createdDate.substr(0, 10));
            $('#postImage').attr('src', post.data.images[0]);
        });

        $('#display-post-modal').modal();
    });

    //search
    $('#search_post_button').on('click', function () {
        var search=$('#search_post').val();
        // console.log('search box ' + search);
        // alert(search);
        // $.get('/find_post/name/'+search, function (data){
        // })
        window.location.href='/find_post/name/'+search
    })

});

function disable(data,id) {
    console.log(id)
    console.log(data)
    var value =$('#'+data)
    $('#set-status-modal').modal('show')
    console.log(value[0].checked)
    if(value[0].checked){
        $('#btn-disable-enable').text("Disable")
        $('#modal-text').text("Do you want to disable this post?")
    }else{
        $('#modal-text').text("Do you want to enable this post?")
        $('#btn-disable-enable').text("Enable")
    }


    $('#set-status-modal').find('#btn-disable-enable').click(function () {
        if(value[0].checked==true){

            $.get( '/set_post_true/' + id, function( data ) {
                location.reload()
            });
        }else{
            $.get('/set_post_false/' + id, function( data ) {
                location.reload()
            });
        }
    })

}
