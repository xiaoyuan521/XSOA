<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1100000.Servlet1100120"%>
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
    <title>帖子</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/content.min.js?v=1.0.0"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plugins/summernote/summernote.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plugins/summernote/summernote-zh-CN.js"></script>
    <!--插件脚本Start  -->
    <script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
    <!--插件脚本End  -->
    <script type="text/javascript">
    $(document).ready(function(){
        $(".summernote").summernote({lang:"zh-CN"});
        var bkid = $.getUrlParam('bkid');
        var bzid = $.getUrlParam('bzid');
        var yhid = $.getUrlParam('yhid');
        $("#btnSave").on('click', function(){
            saveData(bkid,bzid,yhid);
        });
        $("#bk").on('click', function(){
        	window.parent.location.href = "<%=basePath%>/bin/jsp/home/home.jsp";
            var strSrc = "<%=basePath%>/bin/jsp/1100000/1100120.jsp";
            $("#main_frame").attr("src",strSrc);
        });
        $("#zt").on('click', function(){
            window.location.href="<%=basePath%>/bin/jsp/1100000/1100121.jsp?bkid="+bkid+"&bzid="+bzid+"&yhid="+yhid;
        })
    });
    var edit=function(){$("#eg").addClass("no-padding");
    $(".click2edit").summernote({lang:"zh-CN",focus:true})};
    $.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
    function saveData(bkid,bzid,yhid) {
        var aHTML=$(".summernote").code();
        //检查用户输入值
    	if(checkText()==false){
    		return;
    	}
    	var flag = true;
    	var beanIn = new makeBean();
    	beanIn.BKZT_BKID = bkid;
    	beanIn.BKZT_ZTMC = $("#txtZTMC").val();
    	beanIn.ZTNR = aHTML;

    	$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1100120",
    		data: {
    			CMD    : "<%=Servlet1100120.CMD_SAVE_ZT%>",
    		    BeanIn : JSON.stringify(beanIn)
    		},
    		complete :function(response){},
    		success: function(response) {
    			var strResult = response[0];
    			if (strResult == "SUCCESS") {
    			    flag = true;
    			} else if (strResult == "FAILURE") {
    			    flag = false;
    			} else if (strResult == "EXCEPTION") {
    				flag = false;
    			}
    		}
    	});
  	    if (flag) {
  	        layer.msg('恭喜：发布成功！ 经验+5', 1, 9,function(){backZt(bkid,bzid,yhid);});
  	    } else {
  	        layer.msg('对不起：发布失败！', 1, 8);
  	    }
    }
    function makeBean(strBKZT_BKID,strBKZT_ZTMC,strBKZT_ZTNR) {
        this.BKZT_BKID = strBKZT_BKID;
    	this.BKZT_ZTMC = strBKZT_ZTMC;
    	this.BKZT_ZTNR = strBKZT_ZTNR;
    }
    function checkText(){
    	//是否输入主题
    	if($("#txtZTMC").val() == ""){
			layer.alert('主题名称不能为空！', 0, '友情提示',function() {
				$("#txtZTMC").focus();
				layer.close(layer.index);
			});
			return false;
    	}
    	//是否输入内容
    	if($.trim($(".summernote").code()) == ""){
			layer.alert('主题内容不能为空！', 0, '友情提示',function() {
				$(".summernote").focus();
				layer.close(layer.index);
			});
			return false;
    	}
    }
    /* 跳转主题页 */
    function backZt(bkid,bzid,yhid){
        window.location.href="<%=basePath%>/bin/jsp/1100000/1100121.jsp?bkid="+bkid+"&bzid="+bzid+"&yhid="+yhid;
    }
    </script>
	</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="row">
<div class="col-sm-12">
  <div class="ibox float-e-margins">
    <div class="ibox-title">
        <ol class="breadcrumb">
                <li>
                    <a id="bk" href="javascript:void(0)">主页</a>
                </li>
                <li>
                    <a id="zt" href="javascript:void(0)">主题页</a>
                </li>
        </ol>
    </div>

    <div class="ibox-content">
      <div style="height:50px" class="form-group">
          <label style="float:left; width:60px" class="control-label">主题名称</label>
          <div class="col-sm-10" style="margin-left:20px">
              <input id="hidBKID" type="hidden">
              <input maxlength="50" id="txtZTMC" type="text" class="form-control">
          </div>
      </div>
      <div class="hr-line-dashed"></div>
      <div class="form-group">
          <label style="float:left; width:60px" class="control-label">主题内容</label>
          <div>
            <div class="summernote">
            </div>
          </div>
          <div style="float:right;margin-right:140px">
            <input id="btnSave" class="in5" type="button" value="发布" />
          </div>
      </div>
    </div>
  </div>
</div>
</div>
</div>
</body>
</html>