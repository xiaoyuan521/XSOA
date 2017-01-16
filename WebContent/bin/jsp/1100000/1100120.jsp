<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1100000.Servlet1100120"%>
<%@ page import="com.xsoa.pojo.basetable.Pojo_YHXX" %>
<%@ page import="com.framework.session.SessionAttribute"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	Pojo_YHXX user = (Pojo_YHXX)session.getAttribute(SessionAttribute.LOGIN_USER);
	long radom = System.currentTimeMillis();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    <title>论坛版块</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <link href="<%=basePath%>/bin/css/bbsstyle.css?r=<%=radom %>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
    <!--插件脚本Start  -->
    <script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
    <!--插件脚本End  -->
    <script type="text/javascript">
    $(document).ready(function(){
        getBkInfo();
        $("a").click(function(){    //td点击事件；
            var bkid = $(this).parent().parent().find("input#bk").val();
            var bzid = $(this).parent().parent().find("input#bz").val();
            window.location.href="<%=basePath%>/bin/jsp/1100000/1100121.jsp?bkid="+bkid+"&bzid="+bzid+"&yhid=nouser";
        })
        $("#mypost").click(function(){
            window.location.href="<%=basePath%>/bin/jsp/1100000/1100121.jsp?yhid="+<%=user.getYHXX_YHID()%>;
        })
    });
	function getBkInfo(){
	    var list = [];
  		$.ajax({
  		async     : false,
  		type      : "post",
  		dataType  : "json",
  		url: "<%=basePath%>/Servlet1100120",
  		data: {
  			CMD    : "<%=Servlet1100120.CMD_SELECT%>"
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
			    var ztmc = v.ZTMC;
			    if (v.ZTMC.length > 7) {
			        ztmc = v.ZTMC.substr(0, 7)+"...";
			    }
				html += "<div class='box'><table border='0' height='85' width='100%'><tr>";
				html += "<input type='hidden' id='bk' value='"+ v.LTBK_BKID +"'/><input type='hidden' id='bz' value='"+ v.LTBK_BZID +"'/>";
				html += "<td width='10%'><a><img src='"+ tplj +"' width='73' height='73'></a></td>";
				html += "<td width='35%' style='text-align:left; padding-left:10px;'><a href='javascript:void(0)'>"+ v.LTBK_BKMC +"</a></td>";
				html += "<td width='20%'>"+ v.ZTZS +"</td>";
				html += "<td width='20%' style='text-align:left; paddi ng-left:10px;'>";
				html += "<p><span>┏ 主题：</span>"+ ztmc +"</p><p><span>┠ 作者：</span>"+ v.ZZMC +"</p><p><span>┗ 时间：</span>"+ v.FBSJ +"</p></td>";
				html += "<td width='15%'>"+ v.BZMC +"</td>";
				html += " </tr></table></div>";
			});
			sp.append(html);
  		}
  		});
	}
	</script>
	</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
<div class="top_title">
    <a href="javascript:void(0)" id="mypost"><div class="botton">我的帖子</div></a>
</div>
<div class="nav">
  <div class="nav1">版块</div>
  <div class="nav2">主题</div>
  <div class="nav4">最后发表</div>
  <div class="nav5">版主</div>
</div>
<!-- 循环此处begin -->
<div id="info">
  <div class="box">
  <table border="0" height="85" width="100%">
    <tr>
      <td width="10%">
       <a href=""/> <img src="" width="73" height="73"></a>
      </td>
      <td width="35%" style="text-align:left; padding-left:10px;"><a href=""/></a></td>
      <td width="20%">56</td>
      <td width="20%" style="text-align:left; paddi ng-left:10px;">
      <p><span>┏ 主题：</span>zt</p>
      <p><span>┠ 作者：</span>zz</p>
      <p><span>┗ 时间：</span>sj</p>
      </td>
      <td width="15%">chengzilong</td>
    </tr>
  </table>
  </div>
</div>
</div>
</body>
</html>