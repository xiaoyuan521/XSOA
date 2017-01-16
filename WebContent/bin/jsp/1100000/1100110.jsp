<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1100000.Servlet1100110"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	long radom = System.currentTimeMillis();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    <title>论坛管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <link rel="stylesheet" href="<%=basePath%>/bin/css/bbs.css?r=<%=radom %>" type="text/css">
    <script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
    <!--插件脚本Start  -->
    <script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
    <!--插件脚本End  -->
    <script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
    <script type="text/javascript">
    var list = [];
    $(document).ready(function(){
        getBkInfo();
        $('#btnAdd').on('click', function() {
			addBk();
		});
    });
    function addBk(){
        $.layer({
	        type: 2,
	        title: false,
	        maxmin: false,
	        shadeClose: false, //开启点击遮罩关闭层
	        area : ["700px" , "500px"],
	        offset : ['20px', ''],
	        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
	        },
	        end: function(){//关闭按钮
	            getBkInfo();
	        },
	        iframe: {src: "<%=basePath%>/bin/jsp/1100000/1100111.jsp"}
	    });
    }
	function getBkInfo(){
	    var list = [];
  		$.ajax({
  		async     : false,
  		type      : "post",
  		dataType  : "json",
  		url: "<%=basePath%>/Servlet1100110",
  		data: {
  			CMD    : "<%=Servlet1100110.CMD_SELECT%>"
  		},
  		complete :function(response){},
  		success: function(response){
  		    var sp = $("#info");
  		    sp.empty();
		    var html = "";
			list = response[0];
			$.each(list, function(k, v) {
			    var tplj = "<%=basePath%>/bin/img/photo/a2.jpg"
			    if (v.LTBK_TPLJ != "") {
			        tplj = "<%=basePath%>/bin/upload/"+v.LTBK_TPLJ;
			    }
				html += "<div class='box'><table border='0' height='85' width='100%'><tr><td width='10%'>";
				html += "<img src='"+ tplj +"' width='73' height='73'/></td>";
				html += "<td width='15%' style='text-align:left; padding-left:10px;'><a href='#'>"+ v.LTBK_BKMC +"</a></td>";
				html += "<td width='10%' >"+ v.BZMC +"</td>";
				html += "<td width='20%' >"+ v.KJMC +"</td>";
				html += "<td width='35%' >"+ v.LTBK_BKSM +"</td>";
				html += "<td width='10%'>";
			  	html += "<a onclick='del()'><img title='删除' src='<%=basePath%>/bin/img/common/delete.gif'></img></a>";
				html += "<a onclick='upd()'><img title='修改' src='<%=basePath%>/bin/img/common/delete.gif'></img></a>";
			    html += "<a onclick='info()'><img title='查看详细' src='<%=basePath%>/bin/img/common/delete.gif'></img></a></td>";
			  	html += "</tr></table></div>";
			});
			sp.append(html);
  		}
  		});
	}
		</script>
	</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
<input type="hidden" id="mes"">
  <div class="top_title">
    <a id="btnAdd"><div class="botton">添加版块</div></a>
  </div>
  <div class="nav_2">
    <div class="nav1">版块名称</div>
    <div class="nav2">版主</div>
    <div class="nav4">版块可见部门</div>
    <div class="nav5">说明</div>
    <div class="nav2">操作</div>
  </div>
  <div id="info">
  </div>
</div>
</body>
</html>