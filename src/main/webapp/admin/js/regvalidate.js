var validateRegExp = {
    decmal: "^([+-]?)\\d*\\.\\d+$",
    // 浮点数
    decmal1: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",
    // 正浮点数
    decmal2: "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$",
    // 负浮点数
    decmal3: "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$",
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
    num1: "^[1-9]\\d*|0$",
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
    idcard: "^[1-9]([0-9]{14}|[0-9]{17})$",
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
    username: "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{4,20}$",
    // 户名
    deptname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
    // 单位名
    zipcode: "^\\d{6}$",
    // 邮编
    realname: "^[A-Za-z\\u4e00-\\u9fa5]+$",
    // 真实姓名
    companyname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
    companyaddr: "^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$",
    companysite: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&#=]*)?$"
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
    }
};

var validatePrompt = {
    regName: {
        //onFocus: "4-20位字符，支持中英文、数字及\"-\"、\"_\"组合",
        onFocus:"4-20位字符,支持汉字、字母、数字及\"-\"、\"_\"组合",
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
    	onFocus:"请输入真实姓名",
        succeed: "",
        isNull: "请输入真实姓名",
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
    	onFocus: "请输入身份证号",
        succeed: "",
        isNull: "请输入身份证号",
        error: "格式不正确"
    },
    phone: {
        onFocus: "请输入手机号码",
        succeed: "",
        isNull: "请输入手机号码",
        error: ""
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

function validateUsername(){
	var pass = false; 
	var username = $("#username").val();
	if(validateRules.isNull(username) || username == ""){
		$("#username_promt").html(validatePrompt.regName.isNull);
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
             error: function(){
            	 alert("服务器异常，请稍后注册");
             }
        }) 
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
    pass = username && password && passwordAgain;
    if(pass){
    	var user = {
				 username : $("#username").val(),
		 		 password : $("#password").val(),
		 		 name : $("#name").val(),
		 		 idCard : $("#idCard").val(),
		 		 mobile : $("#mobile").val(),
		 		 phone : $("#phone").val(),
		 		 email : $("#email").val(),
		 		 qq : $("#qq").val(),
		 		 role : $("#role").val(),
		 		 avatar : getImgUrl
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
               	location.href="login.html"
               }else{
               	alert(json.description);
               }
            },
            error: function(){
                alert("服务器异常，请稍后尝试！")
            }
        }) 
    }else{
        return;
    }
}