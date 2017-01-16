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
<link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
<link href="<%=basePath%>/bin/css/login.min.css?v=4.0.0" rel="stylesheet">

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
                var listFriend = strJsonToJsonByFriend();
                var listGroup = strJsonToJsonByGroup();
                setFriendFile(listFriend);
                setGroupFile(listGroup);
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

function setFriendFile(list) {
    $.ajax({
        async:false,
        type: "post",
        dataType: "json",
        url: "<%=basePath%>/ServletLogin",
        data: {
            CMD         : "<%=ServletLogin.CMD_SET_FRIEND %>",
            FLAG      : "friend",
            LIST        : list
        },
        complete :function(){},
        success: function(response){
            //var CMD = response[0];
        }
    });
}

function setGroupFile(list) {
    $.ajax({
        async:false,
        type: "post",
        dataType: "json",
        url: "<%=basePath%>/ServletLogin",
        data: {
            CMD         : "<%=ServletLogin.CMD_SET_GROUP %>",
            FLAG      : "group",
            LIST        : list
        },
        complete :function(){},
        success: function(response){
            //var CMD = response[0];
        }
    });
}

function strJsonToJsonByFriend() {
    var list = [];
    var listLr=[];
    var listGl=[];
    var groupLr={};
    var groupGl={};
    var groupList=[];
    var json={};
    var obj;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletLogin",
		data: {
			CMD    : "<%=ServletLogin.CMD_SELECT%>"
		},
		complete :function(){},
		success: function(response){
  	    	list = response[0];
  	    	obj = response[1];
        	$.each(list, function(k, v) {
      	        if ($('#username').val() != v.YHXX_YHID) {
      	            var dataLr={};
                      var face = "<%=basePath%>/bin/upload/"+ v.YHXX_GRZP;
                      dataLr['id'] = v.YHXX_YHID;
                      dataLr['name'] = v.YHXX_YHMC;
                      dataLr['face'] =  face;
                      listLr.push(dataLr);
      	        }
        	});
        	var dataGl={};
        	var faceGl = "<%=basePath%>/bin/upload/"+obj.YHXX_GRZP;
        	dataGl['id'] = obj.YHXX_YHID;
        	dataGl['name'] = obj.YHXX_YHMC;
        	dataGl['face'] = faceGl;
        	listGl.push(dataGl);

        	groupLr['name'] = '狼友';
        	groupLr['nums'] = listLr.length;
        	groupLr['id'] = 1;
        	groupLr['item'] = listLr;

        	groupGl['name'] = '系统管理员';
        	groupGl['nums'] = 1;
        	groupGl['id'] = 2;
        	groupGl['item'] = listGl;
		}

	});
	groupList.push(groupLr);
	groupList.push(groupGl);

	json['data'] = groupList;
	json['status'] = 1;
	json['msg'] = 'ok';

	var aa = JSON.stringify(json);
    return aa;
}
function strJsonToJsonByGroup() {
    var list = [];
    var listLr=[];
    var groupList=[];
    var json={};
    var obj;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ServletLogin",
		data: {
			CMD    : "<%=ServletLogin.CMD_SELECT%>"
		},
		complete :function(){},
		success: function(response){
  	    	list = response[0];
  	    	obj = response[1];
        	$.each(list, function(k, v) {
      	        var dataLr={};
                var face = "<%=basePath%>/bin/upload/"+ v.YHXX_GRZP;
                dataLr['id'] = v.YHXX_YHID;
                dataLr['name'] = v.YHXX_YHMC;
                dataLr['face'] =  face;
                listLr.push(dataLr);
        	});
		}

	});
	json['data'] = listLr;
	json['status'] = 1;
	json['msg'] = 'ok';

	var aa = JSON.stringify(json);
    return aa;
}
</script>
</head>
<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>[ 狼 人 杀 ]</h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>狼人杀(WereWolf)系统</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 狼人杀介绍</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 战绩统计</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 玩家信息</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 交流论坛</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 排位系统</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 狼人瞬间</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> web聊天</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 待开发...</li>
                    </ul>
                    <strong>还没有账号？ <a href="#">立即注册&raquo;</a></strong>
                </div>
            </div>
            <div class="col-sm-5">
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md">登录到狼人杀系统</p>
                    <input id="username" type="text" class="form-control uname" placeholder="用户名" />
                    <input id="password" type="password" class="form-control pword m-b" placeholder="密码" />
                    <a href="">忘记密码了？</a>
                    <button onclick="login_click()" class="btn btn-success btn-block">登录</button>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2016 Microad
            </div>
        </div>
    </div>
</body>
</html>