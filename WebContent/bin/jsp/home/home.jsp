<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.pojo.basetable.Pojo_YHXX" %>
<%@ page import="com.framework.session.SessionAttribute"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	Pojo_YHXX user = (Pojo_YHXX)session.getAttribute(SessionAttribute.LOGIN_USER);
	String menuList = (String)session.getAttribute(SessionAttribute.LOGIN_MENU_JSON);
	String test = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Microad狼人杀系统</title>

<link rel="stylesheet" href="<%=basePath%>/bin/css/global.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/home.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/menu.css" type="text/css">

<link href="<%=basePath%>/bin/css/layim.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/menu.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<script type="text/javascript">
var basePath="<%=basePath%>";
window.onload=function(){
	var w = window.screen.width;
	var h = window.screen.height;

	window.moveTo(0,0);
    window.resizeTo(w,h);

    changeDivHeight();

   //$("#main_frame").attr("src",basePath + "/bin/img/logo.jpg");
   setInterval('refreshDateTime()',100);
   //openWindow();
};

//当浏览器窗口大小改变时，设置显示内容的高度
window.onresize=function(){
     changeDivHeight();
     var strSrc = $('#main_frame').attr("src");
   	 $("#main_frame").attr("src",strSrc);
};

function openWindow(){
    $.layer({
        type: 2,
        title: false,
        maxmin: false,
        shadeClose: true, //开启点击遮罩关闭层
        shade: 0.2,
        area : ["700px" , "500px"],
        offset : ['20px', ''],
        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
        },
        end: function(){//关闭按钮
            getBkInfo();
        },
        iframe: {src: "<%=basePath%>/bin/jsp/1100000/1100130.jsp"}
    });
}

function changeDivHeight(){
   //设定菜单高度
   var menuHeight = document.body.clientHeight - $('#header').height();
   $('#menu_container').height(menuHeight);

   //设定主区域高度和宽度
   var mainHeight = document.body.clientHeight - $('#header').height() ;
   var mainWidth = document.body.clientWidth - $('#leftFrameCanvas').width()-5 ;
   $('#main_frame').height(mainHeight);
   $('#main_frame').width(mainWidth);
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

    hour =today.getHours();hour = (hour<10) ? "0"+hour : hour;
    min=today.getMinutes();min = (min<10) ? "0"+min : min;
    sec=today.getSeconds();sec = (sec<10) ? "0"+sec : sec;

    curentDateTime = year +"/"+mon+"/"+date+" ("+weekday+")  "+hour+":"+min+":"+sec;
    document.getElementById("dateTimeShow").innerHTML= "系统时间："+curentDateTime;
}

function logout(){
	location.href='<%=basePath%>';
}

</script>
</head>
<body>
<script src="../../lay/lib.js"></script>
<script src="../../lay/layer/layer.min.js"></script>
<script src="../../lay/layim.js"></script>
<script src="../../lay/json2.js"></script>
<div id="header" class="navbar">
	<div class="navbar-inner">
        <div class="site">
            <ul class="site_links" style="padding-top:10px;">
                <li style="background:url(<%=basePath%>/bin/img/home/kuang.png) no-repeat;height:90px;width:250px;">&nbsp;
                	<IMG src="<%=basePath%>/bin/img/home/user.png">
                	<span style="line-height:50px;font-size:12px;">
                		当前用户：<b id="username" style="font-size:12px;"><%=user.getYHXX_YHMC() %></b>
                	</span>
                </li>
                <!-- 退出 -->
                <li style="line-height:46px;">
                	<a href="javascript:;" onclick="logout()">
                		<IMG src="<%=basePath%>/bin/img/home/close.png">
                	</a>
                </li>
            </ul>
        </div>
        <div class="site" style="margin-top: 52px;width:296px; color: #FFF;">
          <div>
          	<h1 id="dateTimeShow"></h1>
          </div>
        </div>
        <div class="logo"><IMG src="<%=basePath%>/bin/img/home/logo.png"></div>
    </div>
</div>

<div id="mainContent">
    <div id="leftFrameCanvas">
       <!-- 菜单项目区域 --><div id="menu_container" style="overflow-y:auto;"></div>
    </div>
    <iframe id="main_frame" name="main_frame" src="" marginwidth=0 marginheight=0 frameborder=0 scrolling="no">
    </iframe>
</div>
<script type="text/javascript">
//加载菜单
function loadMenu() {
	var menuContainer = document.getElementById("menu_container");
	makeMenu(menuContainer,<%=menuList%>);
}

loadMenu();
var yhInfo ={
	username : "<%=user.getYHXX_YHMC() %>",
    grzp : "<%=user.getYHXX_GRZP() %>"
}
layim('<%=basePath%>', yhInfo);
</script>
</body>
</html>