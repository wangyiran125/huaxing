var logisticsUserinfo = null;
//自动刷新的时间间隔
INTERVAL = 30000;
dictData = {};
mapNameSpace = {};
timerNameSpace = {};
modalNameSpace = {};

//弹出对话框的对象 START
//使用的时候，用new即可，如truck_aidercenter.html里面的： new TheModal('truck-list-box', 'delete-plan-btn');
//第一个参数是触发打开对话框的按钮的父元素的ID，第二个参数是按钮的data-role属性值,第三个属性为提示信息文本
function TheModal(boxId, triggerBtnRole, text){//config存放的是触发modal框的盒子以及里面的btn
  var _this = this;
  var randomId = 'modal-box'+new Date().getTime();

  $('\
    <div id="'+randomId+'">\
      <div class="box_mark"></div>\
      <div class="box_logoIn waybill_LogoIn" class="box_logoIn">\
        <div class="close" data-role="modal-cancel-btn"><img src="images/pic_14.png" /></div>\
        <div class="mark_title" style="margin-top:30px;">'+text+'</div>\
        <div class="mywar_delete" data-role="modal-confirm-btn" style="margin-left:35px;"><a href="javascript:;">是</a></div>\
        <div class="mywar_submit" data-role="modal-cancel-btn"><a href="javascript:;">否</a></div>\
      </div>\
    </div>\
    ').appendTo('body');

  this.modalBox = $('#'+randomId);

  $('#'+boxId).delegate('[data-role='+triggerBtnRole+']', 'click', function(){
    if(modalNameSpace.noShowModal){
      modalNameSpace.noShowModal = false;
      return;
    }
    _this.showModal();
  });

  this.modalBox.find('[data-role=modal-cancel-btn]').click(function(){
    _this.cancelHandler();
  });

  this.modalBox.find('[data-role=modal-confirm-btn]').click(function(){
    _this.confirmHandler();
  });
}

TheModal.prototype.cancelHandler = function(){
  this.modalBox.find('.box_mark').hide();
  this.modalBox.find('.box_logoIn').hide();
};

TheModal.prototype.confirmHandler = function(){
  this.realConfirm();
};

TheModal.prototype.showModal = function(){
  var $boxMark = this.modalBox.find('.box_mark'),
      $boxLogoIn = this.modalBox.find('.box_logoIn');
  $boxMark.show();
  $boxLogoIn.show();
  $boxMark.css({'width':document.documentElement.clientWidth, 'height':document.documentElement.clientHeight});
  var left = (document.documentElement.clientWidth - $boxLogoIn[0].offsetWidth)/2,
      top = (document.documentElement.clientHeight - $boxLogoIn[0].offsetHeight)/2 - 25;
  $boxLogoIn.css({'left': left, 'top': top});
};

//弹出对话框的对象 END

function verifyUserLogin() {
    var requestUrl = "../api/users/userinfo?t=" + new Date().getTime();

    return $.ajax({
        url: requestUrl,
        type: "GET",
        dataType: "json", // expected format for response
        contentType: "application/json", // send as JSON
        timeout: 30000
    });
}

function viewUserCenter(){
    var username = logisticsUserinfo.username;
    var level = logisticsUserinfo.vipLevel;
    if(level == 1){
    	$("#user_logininfo").html("<img src='images/pic_11.png' style='margin-right: 3px; margin-top: 5px;'/><span>金卡用户：" + username + "</span>");
    }else if(level == 2){
    	$("#user_logininfo").html("<img src='images/pic_12.png' style='margin-right: 3px; margin-top: 5px;'/><span>银卡用户：" + username + "</span>");
    }
}

function verifyAndDisplayUserInfo()
{
    $.when(verifyUserLogin())
        .done( function(userData)
        {
           if(userData.code == 0 && userData.payload.username){
                logisticsUserinfo = userData.payload;
                viewUserCenter();
           }else { 
        	   logisticsAlert(userData.description);
               location.href = "login.html";
           }
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            logisticsAlert("无法获取用户身份信息。请确认网络或服务器端运行状态正常后，再次刷新本页面。");
            
            location.href = "login.html";
        });
}

//获取cookie
function getCookie (name) {
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)")
	if(arr=document.cookie.match(reg)){
		return unescape(arr[2]);
	}else{
		return null;
	}
}

//记住用户名
function setCookie (name,value) {
	var rname_checkbox=$("#rname_checkbox");//获取记住用户名多选框
	//判断用户是否选中记住用户名多选框
    if(rname_checkbox[0].checked){//设置cookie
    var days=30;
    var exp=new Date();
    exp.setTime(exp.getTime()+days*24*60*60*1000);
        document.cookie=name+"="+escape(value)+";expires="+exp.toGMTString();
    }else{//删除cookie
    var exp=new Date();
    exp.setTime(exp.getTime()-1);
    var cval=getCookie(name);
    if(cval!=null){
    	document.cookie=name+"="+cval+";expires="+exp.toGMTString();
    }
	}
}

function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	var vcode = $("#vcode").val();
	if((username == "" || typeof username != "string") || username == ""){
		logisticsError("请输入用户名");
		return;
	}
	if((password == "" || typeof password != "string") || password == ""){
		logisticsError("请输入密码");
		return;
	}
	if((vcode == "" || typeof vcode != "string") || vcode == ""){
		logisticsError("请输入验证码");
		return;
	}
	$.ajax({
        type:'post',
        url:'../api/loginforweb',
        data:JSON.stringify({username:username,password:$.md5(password),vcode:vcode}),
        dataType:'json',
        contentType:'application/json',
        success:function(json){
           if(json.code == 0){
        	   getDictData();
        	   sessionStorage.zgwzdict = JSON.stringify(dictData);
        	   setCookie("uname",username);//把用户名存放到cookie中
               location.href="index.html";
           }else{
           	logisticsError(json.description);
        	   $(".verificationcode img").attr("src","../api/ajaxcontrol/verifycode?t="+new Date().getTime());
        	   $("#password").val('');
        	   $("#vcode").val('');
           }
        },
        error: function(jqXHR, textStatus, errorThrown){
            ajaxError(jqXHR, textStatus, errorThrown);
        }
    }) 
}

$(function(){
    $("#username").keydown(function(e){
       if(e.keyCode == 13){
    	   $(".regist_login").click();
       }
   })
   $("#password").keydown(function(e){
       if(e.keyCode == 13){
    	   $(".regist_login").click();
       }
   })
   $("#vcode").keydown(function(e){
       if(e.keyCode == 13){
    	   $(".regist_login").click();
       }
   })
   
   $(".verificationcode img").click(function(){
		$(this).attr("src","../api/ajaxcontrol/verifycode?t="+new Date().getTime());
   })
   
   $(".verificationcode a").click(function(){
		$(".verificationcode img").attr("src","../api/ajaxcontrol/verifycode?t="+new Date().getTime());
   })
})

//获取字典表数据
function getDictData(){
	$.when($.ajax({
		 url: "../api/dicts?t="+ new Date().getTime(),
         type: "GET",
         async:false,
         dataType: "json", // expected format for response
         contentType: "application/json"// send as JSON
		})
	).done(function(dict){
		if(dict.code == 0 && dict.payload){
			dict = dict.payload;
			dictData = dict;
            sessionStorage.zgwzdict = JSON.stringify(dictData);
		} else {
           logisticsError(dictData.description);
        }
	}).fail(function(jqXHR, textStatus, errorThrown){
            ajaxError(jqXHR, textStatus, errorThrown);
        })
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

//生成随机字符串
function newGuid(){
	/*return Math.random().toString(36).substr(2);*/
	 var guid = "";
     for (var i = 1; i <= 32; i++){
       var n = Math.floor(Math.random()*16.0).toString(16);
       guid +=n;
     }
     guid += new Date().getTime();
     return guid.toUpperCase();    
}

//获取url参数
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

//验证表单提示特效
function flash(obj,time,wh,fx)
{ 
	$(function(){
	var $panel = $(obj);
	var offset = $panel.offset()-$panel.width();
	var x= offset.left;
	var y= offset.top;
	for(var i=1; i<=time; i++){
		if(i%2==0)
		{
			$panel.animate({left:'+'+wh+'px'},fx);
		}else
		{
			$panel.animate({left:'-'+wh+'px'},fx);
		}
			
	}
	$panel.animate({left:0},fx);
	$panel.offset({ top: y, left: x });
		
	})
}
//添加货物
function addGoods(goodsId){
	  var url = '../api/goods',
	  	  type = 'POST';
	  if(goodsId != null){
		  url = url + "/"+goodsId;
		  type = 'PUT';
	  }
	  if($("#departureprovince").val() == -1){
		  $("#departureprovince").addClass('promt');
		  flash('#departureprovince',3,8,50);
		  return;
	  }
	  if($("#destinationprovince").val() == -1){
		  $("#destinationprovince").addClass('promt');
		  flash('#destinationprovince',3,8,50);
		  return;
	  }
	  //验证
	  var shippingPrice=validateShippingPrice();
	  var quantity=validateIsFullNum("quantity","数量");
	  var weight=validateIsNumDecmal("weight","重量");
	  var volume=validateIsFullNum("volume","体积");
	  
	  if($("#departureTime").val() == '选择日期'){
		  $("#departureTime").addClass('promt');
		  flash('#departureTime',3,8,50);
		  return;
	  }

    var validity=false;
    if ($("#islongterm").is(':checked')) {
      $("#validity").removeClass('promt');
      validity=true;
    } else{
      if ($("#validity").val()=='选择日期'||$("#validity").val()=="") {
        $("#validity").addClass('promt');
        flash('#validity',3,8,50);
        return;
      }else{
        validity=true;
      }
    };

	  // 位置必填
    if($("#signarea_positionDescription").val()==""||$("#signarea_positionDescription").val()==null) {
      $("#signarea_positionDescription").addClass('promt');
      return;
    } 

	  var contactName=validaterealName('contactName','联系姓名',true);
	  //验证手机号
	  var contactMobile=validateMobile("contactMobile",true);
	  //验证电话
	  var phone=validateTel();

	  var pass=shippingPrice&&quantity&&weight&&volume&&contactMobile&&phone&&validity;
	  if (pass) {
	  	var goodsInfo = {
          	departureProvinceCode : $("#departureprovince").val(),
          	departureCityCode : $("#departurecity").val(),
              departure : $("#departure").val(),
              destinationProvinceCode : $("#destinationprovince").val(),
              destinationCityCode : $("#destinationcity").val(),
              destination : $("#destination").val() ,
              goodsName : $("#goodsName").val(),
              picture : pictureUrl,
              goodsType : $("#goodsType").val(),
              shippingType : $("#shippingType").val(),
              quantity : $("#quantity").val(),
              weight : $("#weight").val(),
              volume : $("#volume").val(),
              shippingPrice : function(){
              	if($("#mianyi").is(":checked")){
              		return '-1';
              	}else{
              		return $("#shippingPrice").val();
              	}
              }(),
              notice : function(){
                  var noticeStr = '';
                  var noticeEle = $("input[name='notice']:checked");
                  if(noticeEle){
                      noticeEle.each(function(){
                          noticeStr += ','+$(this).val();
                      });
                      noticeStr = noticeStr.substring(1); 
                  }
                  return noticeStr;
              }(),
              departureTime:$("#departureTime").val() == '选择日期'? null:$("#departureTime").val(),
              validity : function(){
              		if($("#islongterm").is(":checked")){
              			return '-1';
              		}
              		return $("#validity").val() == '选择日期'?null:$("#validity").val();
              	}(),
              contactName : $("#contactName").val(),
              contactMobile : $("#contactMobile").val(),
              phone : $("#phone").val(),
              remark : $("#input_comment").val(),
              latitude: mapNameSpace.latitude,
              longitude: mapNameSpace.longitude,
              goodsAddress: mapNameSpace.address
          };

          $.ajax({
              type:type,
              url:url,
              data:JSON.stringify(goodsInfo),
              dataType:'json',
              contentType:'application/json',
              success:function(json){
                 if(json.code == 0){
                 	location.href="goods_list.html"
                 }else{
                 	logisticsError(json.description);
                 }
              },
              error: function(jqXHR, textStatus, errorThrown){
                  ajaxError(jqXHR, textStatus, errorThrown);
              }
          })
	  } else{
	  	return;
	  };
	  
}
function getRole(roleCode){
	if(roleCode == 2){
		return '仓库主';
	}else if(roleCode == 3){
		return '货主'
	}else if (roleCode == 4) {
		return '车主'
	}else if (roleCode == 5) {
        return '司机'
    }
}
//查看个人中心个人信息
function getUserinfo(){
	// $("#username").val(logisticsUserinfo.username);
	$("#role").text(getRole(logisticsUserinfo.role));
	$("#name").val(logisticsUserinfo.name);
	$("#idCard").val(logisticsUserinfo.idCard);
	$("#mobile").val(logisticsUserinfo.mobile);
	$("#phone").val(logisticsUserinfo.phone);
	$("#qq").val(logisticsUserinfo.qq);
	$("#email").val(logisticsUserinfo.email);
  $("#licensePlateNumber").html(decodeURI(logisticsUserinfo.licensePlateNumber));

}

//修改个人信息
function updateUserInfo(){
	//var username = validateUsername(logisticsUserinfo.username);
	var idCard=validateIdCard($("#idCard").val());
	var mobile=validateMobile("mobile",false);
	var phone=validateTel();
	var qq=validateQQ();
	var email=validateEmail();
  var name=validaterealName('name','姓名',false);
	var pass=idCard&&mobile&&phone&&qq&&email&&name;
	if(pass){
		$.ajax({
			type:'put',
			url:'../api/users',
			data:JSON.stringify({
				//username:$("#username").val(),
				name:$("#name").val(),
				idCard:$("#idCard").val(),
				mobile:$("#mobile").val(),
				phone:$("#phone").val(),
				qq:$("#qq").val(),
				email:$("#email").val()}),
			contentType:'application/json',
			success:function(json){
				if(json.code == 0){
					logisticsAlert("您的信息已经修改成功");
					location.reload();
					//location.replace('goods_aidercenter.html?tar=center');
				}else{
					logisticsError(json.description);
				}
			},
            error: function(jqXHR, textStatus, errorThrown){
                ajaxError(jqXHR, textStatus, errorThrown);
            }
			
		})
	}else{
		return;
	}
}
//修改密码
function updatePwd(){
	if(validateOldPwd() && validatePwd() && validatePwdAgain()){
		$.ajax({
			type:'put',
			url:'../api/users/password',
			data:JSON.stringify({"oldPassword":$.md5($("#oldPassword").val()),"newPassword":$.md5($("#password").val())}),
			contentType:'application/json',
			success:function(json){
				if(json.code == 0){
					logisticsAlert("修改成功");
					$("#oldPassword").val('');
					$("#password").val('');
					$("#passwordAgain").val('');
				}else{
					logisticsError(json.description);
				}
			},
            error: function(jqXHR, textStatus, errorThrown){
                ajaxError(jqXHR, textStatus, errorThrown);
            }
		})
	}else{
		return;
	}
}
//查看地址列表
function loadAddress(){
	$("#address-box").empty();
	$.ajax({
		type:'get',
		url:'../api/address?t='+new Date().getTime(),
		success:function(json){
			if(json.code == 0 && json.payload){
				 var list = json.payload;
				    for (var i = 0; i < list.length; i++) {
			        	  var aid = newGuid();
			        	  sessionStorage.setItem(aid, JSON.stringify(list[i]));
			        	  $('\
			        		   <li>\
			                      <div class="address_left">\
			                        <h2> <a href="###"><img src="images/pic_3.png" /></a>&nbsp;&nbsp;'+parseToDistrictName(list[i].provinceCode)+'&nbsp;'+parseToDistrictName(list[i].cityCode)+'&nbsp;'+list[i].address+' &nbsp;&nbsp;</h2>\
			                        <div class="send_check" style="margin-top:20px;">\
			                          <input class="departure" '+function(){if(list[i].flag==1){return 'checked';}else{return '';}}()+' onclick="setDefaultAddress('+list[i].id+',1)" name="departure" type="radio" />&nbsp;&nbsp;设为默认出发地<br />\
			                          <input class="destination" '+function(){if(list[i].flag==2){return 'checked';}else{return '';}}()+'  onclick="setDefaultAddress('+list[i].id+',2)" name="destination" type="radio" />&nbsp;&nbsp;设为默认到达地\
			                        </div>\
			                      </div>\
			                      <div class="address_right">\
			                        <div class="mycar_xiangqing"><a href="goods_update_address.html?aid='+aid+'">修改</a></div>\
			                        <div onclick="conf.editId='+list[i].id+';" class="mycar_lost" data-role="delateAdress-btn"><a href="javascript:void(0)">删除</a></div>\
			                      </div>\
			                    </li>\
			                ').appendTo('#address-box');
			          };
			}
		}
	})
}
//默认出发地设置
function setDefaultAddress(aid,flag){
	$.ajax({
		type:'put',
		url:'../api/address/'+aid+'/'+flag,
		contentType:'application/json',
		success:function(json){
			if(json.code == 0){
				/*if(flag == 1){
					alert("该地址已设为默认出发地");
				}else{
					alert("该地址已设为默认到达地");
				}*/
				//成功
			}else{
				logisticsError(json.description);
			}
		},
        error: function(jqXHR, textStatus, errorThrown){
            ajaxError(jqXHR, textStatus, errorThrown);
        }
	})
}

//删除地址
function deleteAddress(){
	//if(confirm('确定要删除吗？')){
		$.ajax({
			type:'delete',
			url:'../api/address/'+conf.editId,
			success:function(json){
				if(json.code == 0){
          modalNameSpace.deleteAdressModal.cancelHandler();
					loadAddress();
				}
			},
            error: function(jqXHR, textStatus, errorThrown){
                ajaxError(jqXHR, textStatus, errorThrown);
            }
		})
	//}else{
		//return;
	//}
}

//加入收藏
function addFavorite (a,url,title) {
	try{//IE
		window.external.addFavorite(url,title);
	}catch(e){
	//Firefox
	if (navigator.userAgent.indexOf('Firefox')>=0) {
		a.rel="sidebar";
		a.href=url;
		return true;
	};
	//Opera
	if (navigator.userAgent.indexOf('Opera') >= 0) {
		a.rel="sidebar";
		a.href=url;
		return true;
	};
	logisticsAlert("您的浏览器不支持自动加入收藏，请使用Ctrl+D进行添加！");
	}
	return false;
}
var COOKIE_NAME_LOGISTICS_ALERTING = "LogisticsAlerting";

function removeLogisticsAlerting() {
    document.cookie = COOKIE_NAME_LOGISTICS_ALERTING + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function saveLogisticsAlerting(msg) {
    var exp=new Date();
    exp.setTime(exp.getTime()+8000);//50秒过期
    document.cookie=COOKIE_NAME_LOGISTICS_ALERTING+"="+escape(msg)+";expires="+exp.toGMTString();
}

function resumeLogisticsAlert() {
    if (document.cookie.length>0) {
        c_start=document.cookie.indexOf(COOKIE_NAME_LOGISTICS_ALERTING + "=")
        if (c_start!=-1) {
            c_start=c_start + COOKIE_NAME_LOGISTICS_ALERTING.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            var msg = unescape(document.cookie.substring(c_start,c_end))
            removeLogisticsAlerting();
            logisticsAlert(msg);
        }
    }
    return ""
}
function logisticsAlert(msg) {
    if($('#modal_info')) {
        $('#modal_info').html("<P>" + msg + "</P>");
        $('#modal_info').trigger('openModal');
        saveLogisticsAlerting(msg);
        setTimeout("closeLogisticsAlert()", 3000);
     } else {
        alert(msg);
    }
}

function closeLogisticsAlert() {
    $("#modal_info").trigger('closeModal');
    removeLogisticsAlerting();
}

function logisticsError(msg) {
    //msg = "服务器返回错误，请稍后重试或联系系统管理员!<br>" + msg;
    if ($('#modal_error')) {
        $('#modal_error').html("<P>" + msg + "</P>");
        $('#modal_error').trigger('openModal');
        setTimeout("closeLogisticsError()", 8000);
    } else {
        alert(msg);
    }
}

function closeLogisticsError() {
    $("#modal_error").trigger('closeModal');
}

function ajaxError(jqXHR, textStatus, errorThrown) {
    logisticsError("服务器暂时无法访问，请您稍后再试！" );
}

function formatterDate(date){
    
	var dt = new Date(date);
	return (dt.getFullYear()+"-"
		   +checkNum(dt.getMonth()+1)+"-"
		   +checkNum(dt.getDate())+" "
		   +checkNum(dt.getHours())+":"
		   +checkNum(dt.getMinutes())+":"
		   +checkNum(dt.getSeconds()));
	
}

function checkNum(num){
	if(num < 10){
		return "0"+num;
	}
	return num;
}

var pictureUrl;
//上传照片
$(function(){
	//上传图片
	$("#picture").change(function(){
		$("#f1").ajaxSubmit({
			dataType:'json',
			success:function(json){
				if(json.code == 0){
					if(json.payload != null){
						pictureUrl = json.payload;
					}else{
						alert(json.description);
					}
					//$("#viewavatar").html("<img width='50px' src='"+getImgUrl+"'/>");
				}
			}
		})
	 });
})