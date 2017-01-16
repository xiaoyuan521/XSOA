<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1040000.Servlet1040140"%>
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
    <title>玩家信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <script src="<%=basePath%>/bin/js/jquery.min.js?v=2.1.4" type="text/javascript" ></script>
    <script src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="<%=basePath%>/bin/js/content.min.js?v=1.0.0"></script>
    <script>
        $(document).ready(function(){
           	getPlayerInfo();
           	$(".contact-box").each(function(){
                animationHover(this,"pulse")
            });
        });
        function getPlayerInfo() {
            var list = [];
    		$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1040140",
    		data: {
    			CMD    : "<%=Servlet1040140.CMD_SELECT%>"
    		},
    		complete :function(response){},
    		success: function(response){
    		    var sp = $("#info");
    		    var html = "";
    		    sp.empty();
    			list = response[0];
    			$.each(list, function(k, v) {
    			    var grzp = "<%=basePath%>/bin/img/photo/a1.jpg"
    			    if (v.YHXX_GRZP != "") {
    			        grzp = "<%=basePath%>/bin/upload/"+v.YHXX_GRZP;
    			    }
    				html += "<div class='col-sm-4'><div class='contact-box'><div class='col-sm-4'><div class='text-center'>";
    				html += "<img alt='image' style='border-radius: 50%;display: block;width: 70px;height: 75px' class='m-t-xs' src='"+ grzp +"'>";
    				html += "<div class='m-t-xs font-bold'>" + v.YHXX_GRCH + "</div></div></div>";
    				html += "<div class='col-sm-8'>";
    				html += "<h3><strong>" + v.YHXX_YHMC + "</strong></h3>";
    				html += "<p><i class='fa fa-map-marker'></i>" + v.BMXX_BMMC + "</p>";
    				html += "<address>E-mail:" + v.YHXX_EMAIL + "<br>";
    				html += "<abbr title='Phone'>Tel:</abbr>" + v.YHXX_TEL;
    				html += "</address></div><div class='clearfix'></div></div></div>";
    			});
    			sp.append(html);
    		}
    		});
        }
    </script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
	</head>
	<body class="gray-bg" style="overflow-y: auto">
    <div class="wrapper wrapper-content animated fadeInRight" style="height:700px; overflow:auto;">
        <div class="row" id="info">
        </div>
    </div>
</body>
</html>