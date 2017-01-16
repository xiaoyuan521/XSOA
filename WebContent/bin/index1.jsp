<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.login.ServletLogin"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Microad狼人杀系统</title>
<link rel="stylesheet" type="text/css"href="<%=basePath%>/bin/css/login.css" >
<link rel="icon" href="<%=basePath%>/bin/img/common/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/bin/img/common/favicon.ico" type="image/x-icon" />
<script language="JavaScript" src="<%=basePath%>/bin/js/common.js"></script>
<script language="JavaScript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script language="JavaScript">
document.onkeydown = index_onkeydown;
$(document).ready(function(){
	var w = window.screen.width;
	var h = window.screen.height;

	window.moveTo(0,0);
    window.resizeTo(w,h);
    //placeHolder(username,true);
    //placeHolder(password,true);
	$("#username").focus();
	refreshDateTime();

	$("#username").on("blur",function() {
		if($("#username").val()==""){
			$("#spname").show();
		}

	});

	$("#password").on("blur",function() {
		if($("#password").val()==""){
			$("#sppassword").show();
		}

	});
});

function cancel_usr(){
	$("#spname").hide();
}
function cancel_pwd(){
	$("#sppassword").hide();
}
function focus_usr(){
	$("#username").focus();
}
function focus_pwd(){
	$("#password").focus();
}

function index_onkeydown(e){
	e = e || event;
	var obj = e.srcElement ? e.srcElement : e.target;
   if(e.keyCode==13){
       if(obj.id=="username"){
           $("#password").focus();
       }else if(obj.id=="password"){
    	   login_click();
       }
   }
   return true;
}
function login_click(){
	//判断非空
	if($('#username').val() == "" || $('#password').val()==""){
		$('#password').focus();
		return false;
	}

	//通过用户代码取得名称
	$.ajax({
        async:false,
        type: "post",
        dataType: "json",
        url: "<%=basePath%>/ServletLogin",
        data: {
            CMD         : "<%=ServletLogin.CMD_USER_CHECK %>",
            USERID      : $('#username').val(),
            PASSWORD    : $('#password').val()
        },
        complete :function(){},
        success: function(response){
        	//alert("response[0]="+response[0]);
            var CMD = response[0];

            if(CMD=="CMD_OK"){
                /* 密钥校验成功，跳转主操作页面。 */
                location.href='<%=basePath%>/bin/jsp/home/home.jsp';
            }else if(CMD=="CMD_NOEXIST"){
               alert("用户不存在。");
               $('#username').focus();
            }else if(CMD=="CMD_PASS_ERR"){
               alert('用户名或密码不正确。');
               $('#password').focus();
            }else if(CMD=="CMD_MENU_NULL"){
	           alert('用户无权限。');
	           $('#password').focus();
            }else if(CMD=="CMD_EXCEPTION"){
            	alert('系统出现异常,请联系管理员。');
		       $('#password').focus();
            }else{
               alert('系统级问题,请联系管理员。');
               $('#password').focus();
            }
        }
    });
}
function refreshDateTime(){
    weeks = new Array("日","一","二","三","四","五","六");
    today = new Date();
    year= today.getFullYear();
    mon = today.getMonth() + 1;
    mon = (mon<10) ? "0"+mon : mon;
    date= today.getDate();
    date = (date<10) ? "0"+date : date;
    weekday = weeks[today.getDay()];

    curentDateTime = year +"年"+mon+"月"+date+"日 (周"+weekday+")";
    document.getElementById("dateShow").innerHTML= "北京时间："+curentDateTime;
}
</script>
</head>
<body bgcolor="#d7dfe1" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div class="main">
	<div class="header">
		<div class="back_top">
			<span id="dateShow"></span>
		</div>
	</div>
	<div class="background3"></div>
	<div class="background4"></div>
	<div class="background5"></div>
	<div class="inputContent">
		<div class="nameandpassword">
			<span>用户名：</span>
			<input id="username"  name="username" onkeydown="cancel_usr()" class="username">
			<span onclick="focus_usr()" id="spname" class="wenzimiaoshu">请输入用户名</span>
		</div>
		<div class="nameandpassword">
			<span>密码：</span>
			<input id="password" type="password" name="password" onkeydown="cancel_pwd()" class="password">
			<span onclick="focus_pwd()" id="sppassword" class="wenzimiaoshu" style="top:3px;">请输入登录密码</span>
		</div>
	</div>
	<div class="button">
		<div class="loginbottom" onclick="login_click();"><span>登 录</span><a href="#">忘记密码?</a></div>
	</div>
	<div class="background8"></div>
	<div class="footer"><img src="<%=basePath%>/bin/img/login/logo.png"></div>
</div>
</body>
</html>