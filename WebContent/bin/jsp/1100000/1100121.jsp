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
    <title>主题</title>
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
    var list = [];
    $(document).ready(function(){
        var bkid = $.getUrlParam('bkid');
        var yhid = $.getUrlParam('yhid');
        var bzid = $.getUrlParam('bzid');
        var dqid = <%=user.getYHXX_YHID()%>;
        getZtInfo(yhid,bkid);
        if (yhid != "nouser") {
            $("#addpost").hide();
            $(".del").hide();
        } else {
            if (bzid == dqid) {
				$(".del").show();
            } else {
                $(".del").hide();
            }
        }
        $(".ztInfo").click(function(){
            var ztid = $(this).parent().find("input").val();
            window.location.href="<%=basePath%>/bin/jsp/1100000/1100124.jsp?ztid="+ztid+"&bkid="+bkid+"&yhid="+yhid+"&bzid="+bzid;
        })
        $(".del").click(function(){
            var ztid = $(this).parent().find("input").val();
            deleteZt(ztid,bkid,bzid,yhid);
        })
    });
    $.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	};
	function addnew(){
	    var bkid = $.getUrlParam('bkid');
	    var bzid = $.getUrlParam('bzid');
	    var yhid = $.getUrlParam('yhid');
	    window.location.href="<%=basePath%>/bin/jsp/1100000/1100122.jsp?bkid="+bkid+"&bzid="+bzid+"&yhid="+yhid;
	}
	function getZtInfo(strYHID,strBKID){
	    var list = [];
  		$.ajax({
  		async     : false,
  		type      : "post",
  		dataType  : "json",
  		url: "<%=basePath%>/Servlet1100120",
  		data: {
  			CMD    : "<%=Servlet1100120.CMD_SELECT_ZT%>",
  			YHID   : strYHID,
  			BKID   : strBKID
  		},
  		complete :function(response){},
  		success: function(response){
  		    var sp = $("#info");
  		    sp.empty();
		    var html = "";
			list = response[0];
			$.each(list, function(k, v) {
			    var grzp = "<%=basePath%>/bin/upload/"+v.GRZP;
			    if (v.ZHHF != "") {
			        var zhzp = "<%=basePath%>/bin/upload/"+v.ZHZP;
			    }
			    var zttp;
			    if (v.BKZT_ZTLB == "1") {
			        zttp = "<%=basePath%>/bin/img/bbs/jinghua.gif";
			    } else {
			        zttp = "<%=basePath%>/bin/img/bbs/forum_comm.gif";
			    }
				html += "<div class='box_list'><div class='b1'><div class='b1_pic'>";
				html += "<img src='"+ zttp +"'/></div>";
				if (v.BKZT_ZTLB == "1") {
				    html += "<div class='b1_tt'><a class='ztInfo' href='javascript:void(0)'><strong ><font color='red'>"+ v.BKZT_ZTMC +"</font></strong></a><input type='hidden' id='zt' value='"+ v.BKZT_ZTID +"'/></div>";
				} else {
				    html += "<div class='b1_tt'><a class='ztInfo' href='javascript:void(0)'>"+ v.BKZT_ZTMC +"</a><input type='hidden' id='zt' value='"+ v.BKZT_ZTID +"'/></div>";
				}
				html += "</div><div class='b2'><p style='float:left;padding-top:10px'>";
				html += "<img style='border-radius: 50%;display: block' src='"+ grzp +"' class='m-t-xs' width='20' height='20'/></p><p style='float:left;padding-top:15px'>"+ v.ZZMC +"</p>";
				html += "</div><div class='b2'><p style='padding-top:10px'>"+ v.HFGS +"</p></div>";
				html += "<div class='b2'>";
				if (v.ZHHF != "") {
				    html += "<p style='float:left;padding-top:10px'>";
				    html += "<img style='border-radius: 50%;display: block' src='"+ zhzp +"' class='m-t-xs' width='20' height='20'/></p>";
				}
				html += "<p style='float:left;padding-top:15px'>"+ v.ZHHF +"</p></div>";
				html += "<div class='b2_3'><img class='del' title='删除主题' src='<%=basePath%>/bin/img/bbs/delete_post.png'/><img class='up' title='收藏' src='<%=basePath%>/bin/img/bbs/store_up.png'/><input type='hidden' id='zt' value='"+ v.BKZT_ZTID +"'/></div></div>";
			});
			sp.append(html);
  		}
  		});
	}
	function makeBeanDel(strBKZT_ZTID) {
        this.BKZT_ZTID = strBKZT_ZTID;
    }
	//删除帖子
	function deleteZt(strZtid,strBkid,strBzid,strYhid) {
    	var beanIn = new makeBeanDel();
    	beanIn.BKZT_ZTID = strZtid;

    	$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1100120",
    		data: {
    			CMD    : "<%=Servlet1100120.CMD_DELETE_ZT%>",
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
  	        layer.msg('恭喜：删除成功！', 1, 9,function(){backZt(strBkid,strBzid,strYhid);});
  	    } else {
  	        layer.msg('对不起：删除失败！', 1, 8);
  	    }
    }
	/* 跳转主题页 */
    function backZt(bkid,bzid,yhid){
        window.location.href="<%=basePath%>/bin/jsp/1100000/1100121.jsp?bkid="+bkid+"&bzid="+bzid+"&yhid="+yhid;
    }
	</script>
	</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="height:700px; overflow:auto;">
<div class="top_title">
    <a id="addpost" onclick="addnew()"><div class="botton">发新帖</div></a>
</div>
<div class="nav">
  <div class="nav6">主题</div>
  <div class="nav7">作者</div>
  <div class="nav8">回复</div>
  <div class="nav9">最后回复</div>
  <s:if test="banzhu==true">
  <div class="nav10">操作</div>
  </s:if>
</div>
<div id="info">
</div>
<tfoot colspan="9" align="right" bgcolor="#FFFFFF">
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="80%" align="center" >
                <p:paging url="bbs_getthemebyid1" pageCount="${pageCount}" currentPage="${currentPage}"  sort="&column=${column}&order=${order}&flagName=${flagName}&plate.idplate=${plate.idplate}&plate.platename=${plate.platename}"/>
                </td>
                <td width="20%"></td>
              </tr>
            </table>
            </tfoot>
<!-- 页码end -->
</div>
</body>
</html>