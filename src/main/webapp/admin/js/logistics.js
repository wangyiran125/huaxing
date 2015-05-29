var logisticsUserinfo = null;
function verifyUserLogin() {
    var requestUrl = "../api/users/userinfo";

    return $.ajax({
        url: requestUrl,
        type: "GET",
        dataType: "json", // expected format for response
        contentType: "application/json", // send as JSON
        timeout: 30000
    });
}

function viewAdminCenter(){
    var username = logisticsUserinfo.name || logisticsUserinfo.username;
    $("#usermsg #uname").html(username);
}

function verifyAndDisplayAdminInfo()
{
    $.when(verifyUserLogin())
        .done( function(userData)
        {
           if(userData.code == 0 && userData.payload.username && userData.payload.role == 1){
                logisticsUserinfo = userData.payload;
                viewAdminCenter();
           }else {
        	   alert(userData.description);
               location.href = "login.html";
           }
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            alert("登录信息获取失败。请确认网络或服务器端运行状态是否正常后，再次刷新本页面。\n相关信息:status=" + textStatus.toString() + ", error="+errorThrown);
            
            location.href = "login.html";
        });
}

function checkUserRole(){
	if(!logisticsUserinfo || logisticsUserinfo.role != 1){
		parent.href = "login.html";
	}
}

function logout(){
    $.when( $.ajax({
            url: "../api/logout",
            type: "GET",
            dataType: "json", // expected format for response
            contentType: "application/json", // send as JSON
            timeout: 30000
        })
    ).done(function(data){
            //alert("注销done，返回结果=" + data.code);
        })
        .fail(function(){
            //alert("注销fail");
            //TODO: 这里//alert("注销fail");
            //TODO: 这里有个问题，如果Logout时网络断了，下次在获取用户信息时，实际上还会是Login的状态
            location.href = "login.html";
        })
        .always(function() {
            //不过登录成功与否，都进入login.html页面
            location.href = "login.html";
        });
}
//获取URL参数
function getUrlParam(url,paras)  
{  
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
    var paraObj = {} 
    for (i=0; j=paraString[i]; i++){ 
    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    } 
    var returnValue = paraObj[paras.toLowerCase()]; 
    if(typeof(returnValue)=="undefined"){ 
    return ""; 
    }else{ 
    return returnValue; 
    } 
}
//获取url参数
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

//禁用操作
function matterOperator(value, row, rowIndex) {
	var more = "";
	switch(row.isDeleted){
		case 0:
			more = "<div class='isactivate forbidden' onclick='isactivate("+value+")' title='禁用该用户'>禁用</div>";
			break;
		case -1:
			more = "<div class='isactivate activate' onclick='isactivate("+value+")' title='激活该用户'>激活</div>";
			break;
	}
	
	return more;
}

//用户状态显示（正常、已禁用）
function matterStatus(value,row){
	var more = "";
	switch(value){
		case 0:
			more = "正常";
			break;
		case -1:
			more = "已禁用"
			break;
	}
	return more;
}
//禁用或激活用户
function isactivate(userId){
	$.ajax({
		type:'delete',
		url:'../api/users/'+userId,
		success:function(json){
			if(json.code == 0){
				$('#goods').datagrid();
			}else{
				alert(json.description);
			}
		},
		error:function(){
			alert("服务器异常，请稍后操作");
		}
	})
}
