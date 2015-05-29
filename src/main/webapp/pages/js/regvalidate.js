var validateRegExp = {
    decmal: "^([+-]?)\\d*\\.\\d+$",
    // 浮点数
    decmal1: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",
    // 正浮点数
    decmal2: "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$",
    // 负浮点数
    decmal3: "^d+(.d+)?$",
    // 浮点数
    decmal4: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$",
    // 非负浮点数（正浮点数 + 0）
    decmal5: "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",
    // 非正浮点数（负浮点数 +
    // 0）
    intege: "^-?[1-9]\\d*$",
    // 整数
    intege1: "^[1-9]\\d*$",
    // 正整数
    intege2: "^-[1-9]\\d*$",
    // 负整数
    num: "^([+-]?)\\d*\\.?\\d+$",
    // 数字
    num1: "^\d+(\.\d+)?$",
    // 正数（正整数 + 0）
    num2: "^-[1-9]\\d*|0$",
    // 负数（负整数 + 0）
    ascii: "^[\\x00-\\xFF]+$",
    // 仅ACSII字符
    chinese: "^[\\u4e00-\\u9fa5]+$",
    // 仅中文
    color: "^[a-fA-F0-9]{6}$",
    // 颜色
    date: "^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",
    // 日期
    email: "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",
    // 邮件
    idcard: "^[1-9]([0-9]{14}|[0-9]{16}[0-9xX])$",
    // 身份证
    ip4: "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",
    // ip地址
    letter: "^[A-Za-z]+$",
    // 字母
    letter_l: "^[a-z]+$",
    // 小写字母
    letter_u: "^[A-Z]+$",
    // 大写字母
    mobile: "^0?(13|15|18|14|17)[0-9]{9}$",
    // 手机
    notempty: "^\\S+$",
    // 非空
    password: "^[A-Za-z0-9\\w_-]{6,20}$",
    // 密码
    fullNumber: "^[0-9]+$",
    // 数字
    picture: "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",
    // 图片
    qq: "^[1-9]*[1-9][0-9]*$",
    // QQ号码
    rar: "(.*)\\.(rar|zip|7zip|tgz)$",
    // 压缩文件
    tel: "^[0-9\-()（）]{7,18}$",
    // 电话号码的函数(包括验证国内区号,国际区号,分机号)
    url: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",
    // url
    username: "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{2,20}$",
    // 户名
    deptname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
    // 单位名
    zipcode: "^\\d{6}$",
    // 邮编
    realname: "^[A-Za-z\\u4e00-\\u9fa5]+$",
    // 真实姓名
    companyname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
    companyaddr: "^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$",
    companysite: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&#=]*)?$",
    price:"^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$",
    //价格
    licensePlateNumber:"^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$"
    //车牌号
};

var getImgUrl;
$(function(){
	//上传图片
	$("#avatar").change(function(){
		$("#f1").ajaxSubmit({
			dataType:'json',
			success:function(json){
				if(json.payload != null){
					getImgUrl = json.payload;
					$("#viewavatar").html("<img width='50px' src='"+getImgUrl+"'/>");
				}
			}
		})
	 });
})

// 验证规则
var validateRules = {
    isNull: function(str) {
        return (str == "" || typeof str != "string");
    },
    betweenLength: function(str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isUid: function(str) {
        return new RegExp(validateRegExp.username).test(str);
    },
    fullNumberName: function(str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isPwd: function(str) {
        return /^.*([\W_a-zA-z0-9-])+.*$/i.test(str);
    },
    isPwdRepeat: function(str1, str2) {
        return (str1 == str2);
    },
    isEmail: function(str) {
        return new RegExp(validateRegExp.email).test(str);
    },
    isTel: function(str) {
        return new RegExp(validateRegExp.tel).test(str);
    },
    isMobile: function(str) {
        return new RegExp(validateRegExp.mobile).test(str);
    },
    isIdCard:function(str) {
        return new RegExp(validateRegExp.idcard).test(str);
    },
    isQQ:function  (str) {
        return new RegExp(validateRegExp.qq).test(str);
    },
    checkType: function(element) {
        return (element.attr("type") == "checkbox" || element.attr("type") == "radio" || element.attr("rel") == "select");
    },
    isRealName: function(str) {
        return new RegExp(validateRegExp.realname).test(str);
    },
    isCompanyname: function(str) {
        return new RegExp(validateRegExp.companyname).test(str);
    },
    isCompanyaddr: function(str) {
        return new RegExp(validateRegExp.companyaddr).test(str);
    },
    isCompanysite: function(str) {
        return new RegExp(validateRegExp.companysite).test(str);
    },
    simplePwd: function(str) {
        // var pin = $("#regName").val();
        // if (pin.length > 0) {
        // pin = strTrim(pin);
        // if (pin == str) {
        // return true;
        // }
        // }
        return pwdLevel(str) == 1;
    },
    weakPwd: function(str) {
        for (var i = 0; i < weakPwdArray.length; i++) {
            if (weakPwdArray[i] == str) {
                return true;
            }
        }
        return false;
    },
    isChinese:function (str) {//仅中文
        return new RegExp(validateRegExp.chinese).test(str);
    },
    isDecmal:function(str) {
        return new RegExp(validateRegExp.decmal).test(str);
    },
    isPrice:function (str) {
        return new RegExp(validateRegExp.price).test(str);
    },
    islicensePlateNumber:function (str) {//车牌号
        return new RegExp(validateRegExp.licensePlateNumber).test(str);
    }
};

var validatePrompt = {
    regName: {
        //onFocus: "4-20位字符，支持中英文、数字及\"-\"、\"_\"组合",
        onFocus:"2-20位字符,支持汉字、字母、数字及\"-\"、\"_\"组合",
        succeed: "",
        isNull: "请输入用户名",
        error: {
            beUsed: "该用户名已被使用，请重新输入",
            badLength: "用户名长度只能在4-20位字符之间",
            badFormat: "用户名只能由中文、英文、数字及\"-\"、\"_\"组成",
            fullNumberName: "用户名不能是纯数字，请重新输入"
        },
        onFocusExpand: function() {
            $("#morePinDiv").removeClass().addClass("intelligent-error hide");
        }
    },
    realName:{
    	//onFocus:"请输入真实姓名",
        succeed: "",
        isNull: "请输入真实姓名"
    },
    pwd: {
        onFocus: "<span>6-20位字符，建议由字母，数字和符号两种以上组合</span>",
        succeed: "",
        isNull: "请输入密码",
        error: {
            badLength: "密码长度只能在6-20位字符之间",
            badFormat: "密码只能由英文、数字及标点符号组成",
            simplePwd: "<span>该密码比较简单，有被盗风险，建议您更改为复杂密码，如字母+数字的组合</span>",
            weakPwd: "<span>该密码比较简单，有被盗风险，建议您更改为复杂密码</span>"
        },
        onFocusExpand: function() {
            $("#pwdstrength").hide();
        }
    },
    pwdRepeat: {
        onFocus: "请再次输入密码",
        succeed: "",
        isNull: "请确认密码",
        error: {
            badLength: "密码长度只能在6-20位字符之间",
            badFormat2: "两次输入密码不一致",
            badFormat1: "密码只能由英文、数字及标点符号组成"
        }
    },
    idCard:{
    	//onFocus: "请输入身份证号",
        succeed: "",
        isNull: "请输入身份证号",
        error: "格式不正确"
    },
    phone: {
        //onFocus: "请输入手机号码",
        succeed: "",
        isNull: "请输入手机号码",
        error: ""
    },
    trucklabel:{//公司简称
        onFocus:"2-4个汉字",
        isNull:"请输入公司简称",
        error:"格式不正确"
    },
    trucklength:{
        onFocus:"车长不能超过500米",
        isNull:"请输入车长",
        error:"格式不正确"
    },
    shippingPrice:{
        onFocus:"运价不能超过10万"
    },
    empty: {
        onFocus: "",
        succeed: "",
        isNull: "",
        error: ""
    }
};
function usernameFocus(){
	$("#username_promt").html(validatePrompt.regName.onFocus);
}
function pwdFocus(){
	$("#password_promt").html(validatePrompt.pwd.onFocus);
}
function pwdAgainFocus(){
	$("#passwordAgain_promt").html(validatePrompt.pwdRepeat.onFocus);
}
function nameFocus(){
	$("#name_promt").html(validatePrompt.realName.onFocus);
}
function idCardFocus(){
	$("#idCard_promt").html(validatePrompt.idCard.onFocus);
}
function mobileFocus(){
	$("#mobile_promt").html(validatePrompt.phone.onFocus);
} 
function trucklabelFocus () {
    $("#trucklabel_promt").html(validatePrompt.trucklabel.onFocus);
}
function trucklengthFocus() {
    $("#truckLength_promt").html(validatePrompt.trucklength.onFocus);
}
function shippingpriceFocus () {
    $("#shippingPrice_promt").html(validatePrompt.shippingPrice.onFocus);
}
function validateUsername(uname){
	var username = $("#username").val();
	var pass = false; 
	if(validateRules.isNull(username) || username == ""){
		$("#username_promt").html(validatePrompt.regName.isNull);
	}else{
		if(uname != null && uname == username){
			pass = true;
		}else{
			$.ajax({
	            type:'get',
	             url:'../api/users?username='+username,
	             dataType:'json',
	             async:false,
	             contentType:'application/json',
	             success:function(json){
	                if(json.code == 0){
	                	if(new RegExp(validateRegExp.username).test(username)){
	                		$("#username_promt").html(validatePrompt.regName.succeed);
	                		pass = true;
	                	}else{
	                		$("#username_promt").html("格式不正确");
	                		
	                	}
	                }else{
	                    $("#username_promt").html(json.description);
	                }
	             },
	             error: function(jqXHR, textStatus, errorThrown){
                     ajaxError(jqXHR, textStatus, errorThrown);
                 }
	        }) 
		}
	}
	return pass;
}
function validatePwd(){
	var pass = false;
	var pwd = $("#password").val();
	if(validateRules.isNull(pwd) || pwd == ""){
		$("#password_promt").html(validatePrompt.pwd.isNull);
	}else{
		if(new RegExp(validateRegExp.password).test(pwd)){
			$("#password_promt").html(validatePrompt.pwd.succeed);
			pass = true;
		}else{
			$("#password_promt").html("密码格式不正确");
		}
	}
	return pass;
}
function validateOldPwd(){
	var pass = false;
	var pwd = $("#oldPassword").val();
	if(validateRules.isNull(pwd) || pwd == ""){
		$("#oldPassword_promt").html("请输入原始密码");
	}else{
		if(new RegExp(validateRegExp.password).test(pwd)){
			$("#oldPassword_promt").html(validatePrompt.pwd.succeed);
			pass = true;
		}else{
			$("#oldPassword_promt").html("密码格式不正确");
		}
	}
	return pass;
}
function validatePwdAgain(){
	var pass = false;
	var pwdAgain = $("#passwordAgain").val();
	if(validateRules.isNull(pwdAgain) || pwdAgain == ""){
		$("#passwordAgain_promt").html(validatePrompt.pwdRepeat.isNull);
	}else{
		if(pwdAgain == $("#password").val()){
			$("#passwordAgain_promt").html(validatePrompt.pwdRepeat.succeed);
			pass = true;
		}else{
			$("#passwordAgain_promt").html(validatePrompt.pwdRepeat.error.badFormat2);
		}
	}
	return pass;
}
//注册流程
function reg(){
    var pass = false;
    var username = validateUsername(),
    	password = validatePwd(),
    	passwordAgain = validatePwdAgain();
    var name=validaterealName("name","姓名",true);
    var idCard=validateIdCard();
    var mobile=validateMobile("mobile",true);
    var phone=validateTel();
    var email=validateEmail();
    var qq=validateQQ();
    var trucklabel=true;
    var trucklabelobj=$("#trucklabel");
    if(trucklabelobj.length>0){
        trucklabel=validateTrunkLabel();
        pass = username && password && passwordAgain&&idCard&&mobile&&phone&&email&&qq&&trucklabel&&name;
    }else{
        pass = username && password && passwordAgain&&idCard&&mobile&&phone&&email&&qq&&name;
    }
    if(pass){
    	var user = {
				 username : $("#username").val(),
		 		 password : $.md5($("#password").val()),
		 		 name : $("#name").val(),
		 		 idCard : $("#idCard").val(),
		 		 mobile : $("#mobile").val(),
		 		 phone : $("#phone").val(),
		 		 email : $("#email").val(),
		 		 qq : $("#qq").val(),
		 		 role : $("#role").val(),
		 		 avatar : getImgUrl,
		 		 truckLabel : $("#trucklabel").val()
		 }
		 url = '../api/users/';
		 $.ajax({
            type:'post',
            url:url,
            data:JSON.stringify(user),
            dataType:'json',
            contentType:'application/json',
            success:function(json){
               if(json.code == 0){
                logisticsAlert("注册成功！");
               	location.href="login.html"
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

///////////////////////////////////////////////////////////////////////////////////////////////
//验证手机号码
function validateMobile (containerId,flag) {
    var contactMobile=$("#"+containerId+"").val();
    if (!validateRules.isNull(contactMobile)) {
        if (validateRules.isMobile(contactMobile)) {
            $("#"+containerId+"_promt").html("");
            return true;
        } else{
            $("#"+containerId+"_promt").html("请输入有效的手机号码");
            return false;
        };
    } else{
        if(flag){
            $("#"+containerId+"_promt").html("请输入手机号码");
            return false;
        }else{
            $("#"+containerId+"_promt").html("");
            return true;
        }
        
    };
}

//验证电话号码,可以为空
function validateTel () {
    var phone=$("#phone").val();
    if (!validateRules.isNull(phone)) {
        if (validateRules.isTel(phone)) {
            $("#phone_promt").html("");
            return true;
        } else{
            $("#phone_promt").html("请输入有效的电话号码");
            return false;
        };
    } else{
        $("#phone_promt").html("");
        return true;
    };
}

//验证身份证号
function validateIdCard () {
    var idCard=$("#idCard").val();
    if (!validateRules.isNull(idCard)) {
        if (validateRules.isIdCard(idCard)) {
            $("#idCard_promt").html("");
            return true;
        } else{
            $("#idCard_promt").html("请输入有效的身份证号");
            return false;
        };
    } else{
        $("#idCard_promt").html("");
        return true;
    };
}

//验证QQ
function validateQQ () {
    var qq=$("#qq").val();
    if (!validateRules.isNull(qq)) {
        if (validateRules.isQQ(qq)) {
            $("#qq_promt").html("");
            return true;
        } else{
            $("#qq_promt").html("请输入有效的QQ号");
            return false;
        };
    } else{
        $("#qq_promt").html("");
        return true;
    };
}

//验证邮箱
function validateEmail () {
    var email=$("#email").val();
    if (!validateRules.isNull(email)) {
        if (validateRules.isEmail(email)) {
            $("#email_promt").html("");
            return true;
        } else{
            $("#email_promt").html("请输入有效的邮箱帐号");
            return false;
        };
    } else{
        $("#email_promt").html("");
        return true;
    };
}
//验证非空
function validateIsNull (containerId,name) {
    var str="";
    if (containerId=="shippingPrice") {
        if($("#mianyi").is(":checked")){
                    $("#"+containerId+"_promt").html("");
                    return true;
                }else{
                    str=$("#shippingPrice").val();
                }
    } else{
        str=$("#"+containerId+"").val();
    };
    if (validateRules.isNull(str)||str=="") {
        $("#"+containerId+"_promt").html("请输入"+name+"");
        return false;
    } else{
        $("#"+containerId+"_promt").html("");
        return true;
    };
}

//验证公司简称（非空，2-4个汉字）
function validateTrunkLabel () {
    var trucklabel=$("#trucklabel").val();
    var trucklabel_promt=$("#trucklabel_promt");
    if(validateRules.isNull(trucklabel)||trucklabel=="")
    {
        trucklabel_promt.html(validatePrompt.trucklabel.isNull);
        return false;
    }else{
        if(validateRules.isChinese(trucklabel))
            {
                if(validateRules.betweenLength(trucklabel,2,4))
                    {
                        trucklabel_promt.html("");
                        return true;
                    }else{
                        trucklabel_promt.html(validatePrompt.trucklabel.error);
                        return false;
                    }
            }else{
                trucklabel_promt.html(validatePrompt.trucklabel.error);
                return false;
            }
    }
}

//整数验证
function validateIsFullNum (containerId,name) {
    var str=$("#"+containerId+"").val();
    if (!validateIsNull(containerId,name)) {
        $("#"+containerId+"_promt").html("请输入"+name+"");
        return false;
    } else{
        if(validateRules.fullNumberName(str))
        {
            $("#"+containerId+"_promt").html("");
            return true;
        }else{
            $("#"+containerId+"_promt").html("格式不正确");
            return false;
        }
    };
}

//小数验证
function validateIsDecmal (containerId) {
    var str=$("#"+containerId+"").val();
    if(validateRules.isNull(str)||str==""){
        $("#"+containerId+"_promt").html("请输入"+name+"");
        return false;
    }else{
        if (validateRules.isDecmal(str)) {
            $("#"+containerId+"_promt").html("");
            return true;
        } else{
            $("#"+containerId+"_promt").html("格式不正确");
            return false;
        };
    }
}

//判断数字，可以是小数
function validateIsNumDecmal (containerId,name) {
    var str=$("#"+containerId+"").val();
    if(validateRules.isNull(str)||str==""){
        $("#"+containerId+"_promt").html("请输入"+name+"");
        return false;
    }else{
        if (validateRules.isDecmal(str)||validateRules.fullNumberName(str)) {
            $("#"+containerId+"_promt").html("");
            return true;
        } else{
            $("#"+containerId+"_promt").html("格式不正确");
            return false;
        };
    }
}

//验证车长，长度不能超过500米
function validateTruckLength() {
    var trucklength=$("#truckLength").val();
    if (validateIsNumDecmal('truckLength','车长')) {
        var ftrucklength=parseFloat(trucklength);
        if (ftrucklength>0&&ftrucklength<=500) {
            $("#truckLength_promt").html("");
            return true;
        } else{
            $("#truckLength_promt").html("请输入合理的数字");
            return false;
        };
    } else{
        return false;
    };
}

//验证运价，不超过10万
function validateShippingPrice () {
    var shippingPrice="";
    if($("#mianyi").is(":checked")){
        $("#shippingPrice_promt").html("");
        return true;
    }else{
        shippingPrice=$("#shippingPrice").val();
    }

    if (validateIsFullNum('shippingPrice','运价')) {
        var IShippingPrice=parseFloat(shippingPrice);
        if (IShippingPrice>=0&&IShippingPrice<=99999) {
            $("#shippingPrice_promt").html("");
            return true;
        } else{
            $("#shippingPrice_promt").html("请输入合理的数字");
            return false;
        };
    } else{
        return false;
    };
}

//验证真实姓名
function validaterealName (containerId,name,flag) {
    var str=$("#"+containerId+"").val();
    if (flag) {
        if (validateIsNull(containerId,name)) {
            if (validateRules.isRealName(str)) {
                $("#"+containerId+"_promt").html("");
                return true;
            } else{
                $("#"+containerId+"_promt").html("格式不正确");
                return false;
            };
        } else{
            return false;
        };
    } else{
        if (validateRules.isNull(str)||str=="") {
            $("#"+containerId+"_promt").html("");
            return true;
        } else{
            if (validateRules.isRealName(str)) {
                $("#"+containerId+"_promt").html("");
                return true;
            } else{
                $("#"+containerId+"_promt").html("格式不正确");
                return false;
            };
        };
    };
    
}

//验证车牌号
function validateLicensePlateNumber () {
    var str=$("#licensePlateNumber").val();
    if (validateIsNull('licensePlateNumber','车牌号')) {
        if (validateRules.islicensePlateNumber(str)) {
            $("#licensePlateNumber_promt").html("");
            return true;
        } else{
            $("#licensePlateNumber_promt").html("请输入有效的车牌号");
            return false;
        };
    } else{
        return false;
    };
}