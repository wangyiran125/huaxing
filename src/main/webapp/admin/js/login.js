function login(){
	$.ajax({
        type:'post',
        url:'../api/login',
        data:JSON.stringify({username:$("#username").val(),password:$.md5($("#password").val())}),
        dataType:'json',
        contentType:'application/json',
        success:function(json){
           if(json.code == 0){
               if(json.payload.role == 1){
                   location.href="admin_index.html";
               }else{
                   $("#fail_msg").html("用户名或密码错误。");
               }
           	
           }else{
        	   $("#fail_msg").html(json.description);
           }
        },
        error: function(){
            alert("服务器异常，请稍后尝试！")
        }
    }) 
}
