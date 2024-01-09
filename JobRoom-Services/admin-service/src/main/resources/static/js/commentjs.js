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
        console.log("Hello from display comment")
        var href = $(this).attr('href');
        console.log(href + " href")
        $.get(href, function(comment){
            console.log(comment)
            var img;
            if(comment.data.userResponse.imageUrl==null){
                console.log("profile pic is empty")
                img="https://cel.ac/wp-content/uploads/2016/02/placeholder-img-1.jpg"
            }else{
                img=comment.data.userResponse.imageUrl
            }
            $('#comment-username').text(comment.data.userResponse.username);
            $('#comment-caption').text(comment.data.description);
            $('#comment-profile-pic').attr('src', img);
            if(comment.data.replyList.length != 0){
                console.log("yes replylist")
                jQuery.each(comment.data.replyList, function (i, reply){
                    var img;
                    if(reply.userResponse.imageUrl == null){
                        img="https://cel.ac/wp-content/uploads/2016/02/placeholder-img-1.jpg"
                    }else{
                        img = reply.userResponse.imageUrl;
                    }
                    $('#reply-list').append("<li>\n" +
                        "                                            <div class=\"row comments mb-2\">\n" +
                        "                                                <div class=\"col-md-2 offset-md-2 col-sm-2 offset-sm-2 col-3 offset-1 text-center user-img\">\n" +
                        "                                                    <img src="+img+" class=\"rounded-circle\" />\n" +
                        "                                                </div>\n" +
                        "                                                <div class=\"col-md-7 col-sm-7 col-8 comment rounded mb-2\">\n" +
                        "                                                    <h4 class=\"m-0\">"+reply.userResponse.username+"</h4>\n" +
                        "                                                    <p text='${reply.description}' class=\"mb-0 \">"+reply.description+"</p>\n" +
                        "                                                </div>\n" +
                        "                                            </div>\n" +
                        "                                        </li>")
                    console.log(reply.userResponse.username, i , "each reply")
                })
            }
        });
        $('#display-comment-modal').modal();
    });

    $('#close-display-comment').on('click', function (){
        location.reload();
    })

    //search
    $('#search_comment_button').on('click', function () {
        var search=$('#search_comment').val();
        console.log('search box ' + search);
        window.location.href='/find_comment/name/'+search
    })
});

//change status of user with no modal
// function disable(data, id) {
//     var value =$('#'+data)
//     console.log('#displayPost'+data)
//     $('#displayPost'+data).text(!value[0].checked)
//     if (value[0].checked){
//         $.get('/set_comment_false/' + id, function(data){
//             alert("Status set successfully")
//         });
//         console.log('call true method '+ '/set_comment_false/' + id)
//     }else{
//         $.get('/set_comment_true/' + id, function(data){
//             alert("Status set successfully")
//         });
//         console.log('call untrue method'+ id)
//     }
//     console.log(!value[0].checked)
// }


//change the status with modal
function disable(data,id) {
    console.log(id)
    console.log(data)
    var value =$('#'+data)
    $('#set-status-modal').modal('show')
    console.log(value[0].checked)
    if(value[0].checked){
        $('#btn-disable-enable').text("Disable")
        $('#modal-text').text("Do you want to disable this comment?")
    }else{
        $('#modal-text').text("Do you want to enable this comment?")
        $('#btn-disable-enable').text("Enable")
    }


    $('#set-status-modal').find('#btn-disable-enable').click(function () {
        if(value[0].checked==true){

            $.get( '/set_comment_true/' + id, function( data ) {
                location.reload()
            });
        }else{
            $.get('/set_comment_false/' + id, function( data ) {
                location.reload()
            });
        }
    })

    // location.reload()
}
