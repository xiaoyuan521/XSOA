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
    <link href="<%=basePath%>/bin/css/style.css?r=<%=radom %>" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>/bin/css/layout.css?r=<%=radom %>" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>/bin/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/content.min.js?v=1.0.0"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plugins/summernote/summernote.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plugins/summernote/summernote-zh-CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        $(".summernote").summernote({lang:"zh-CN"});
        var ztid = $.getUrlParam('ztid');
        var bkid = $.getUrlParam('bkid');
        var yhid = $.getUrlParam('yhid');
        var bzid = $.getUrlParam('bzid');
        var dqid = <%=user.getYHXX_YHID()%>;
        getZtInfo(ztid);
        getHfInfo(ztid);
        if (yhid == "nouser") {
            if (bzid == dqid) {
				$(".del").show();
				$("#goZd").show();
            } else {
                $(".del").hide();
                $("#goZd").hide();
            }
        } else {
            $(".del").hide();
            $("#goZd").hide();
        }
        $("#btnSave").on('click', function(){
            saveData(ztid,bkid,yhid,bzid);
        });
        $("#goBack").on('click', function(){
            window.location.href="<%=basePath%>/bin/jsp/1100000/1100121.jsp?bkid="+bkid+"&yhid="+yhid+"&bzid="+bzid;
        });
        $("#goHf").on('click', function(){
        	document.getElementById("scroll").scrollIntoView();
        });
        $(".hf").on('click', function(){
        	document.getElementById("scroll").scrollIntoView();
        	var hfr = $(this).parent().find("input").val();
        	$("#kshf").text("回复："+hfr);
        });
        $(".sc").on('click', function(){
        	var hfid = $(this).parent().find("input").val();
        	deleteHf(hfid,ztid,bkid,yhid,bzid);
        });
        $("#goZd").on('click', function(){
        	var txtZd = $("#txtZD").text();
        	var flag;
        	if (txtZd == "置顶") {
        	    $("#txtZD").text("取消置顶");
        	    flag = 1;
        	} else {
        	    $("#txtZD").text("置顶");
        	    flag = 0;
        	}
        	updateZTLB(ztid,flag);
        });
    });
    var edit=function(){$("#eg").addClass("no-padding");
    $(".click2edit").summernote({lang:"zh-CN",focus:true})};
    $.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	};
	function makeBeanIn(strBKZT_ZTID) {
		this.BKZT_ZTID = strBKZT_ZTID;
	}
	function getZtInfo(strZtid){
	    var beanIn = new makeBeanIn(strZtid);
		$.ajax({
			async     : false,
			type      : "post",
			dataType  : "json",
			url: "<%=basePath%>/Servlet1100120",
			data: {
				CMD    : "<%=Servlet1100120.CMD_SELECT_ZT_INFO%>",
			    BeanIn : JSON.stringify(beanIn)
			},
			complete :function(response){},
			success: function(response) {
				var strResult = response[0];
				if (strResult == "SUCCESS") {
					var resultRecord = response[1];
					if (resultRecord.BKZT_ZTLB == "0") {
					    $("#txtZD").text("置顶");
					} else {
					    $("#txtZD").text("取消置顶");
					}
					$("#txtZTMC").text(resultRecord.BKZT_ZTMC);
					$("#txtFBR").val(resultRecord.BKZT_FBR);
					$("#txtBZMC").text(resultRecord.ZZMC);
					$("#imgBZZP").attr('src','<%=basePath%>/bin/upload/'+resultRecord.GRZP);
					$("#txtFBSJ").text(resultRecord.BKZT_FBSJ);
					$("#txtZTNR").html(resultRecord.ZTNR);
					$("#bbsMC").html(resultRecord.JBMC);
					$("#bbsDJ").html(resultRecord.JBID);
					$("#bbsDJ").css("background","url(<%=basePath%>"+resultRecord.JBTP+") no-repeat left top");
					$("#zt_title").attr("title", "本吧头衔"+resultRecord.JBID+"级,经验值："+resultRecord.SJJY);
				} else if (strResult == "EXCEPTION") {
					layer.msg('友情提示：帖子信息出错！', 1, 0);
					blnRet = false;
				}
			}
  		});
	}
	function getHfInfo(strZtid){
	    var list = [];
  		$.ajax({
  		async     : false,
  		type      : "post",
  		dataType  : "json",
  		url: "<%=basePath%>/Servlet1100120",
  		data: {
  			CMD    : "<%=Servlet1100120.CMD_SELECT_HF%>",
  			ZTID   : strZtid
  		},
  		complete :function(response){},
  		success: function(response){
  		    var sp = $("#info");
  		    sp.empty();
		    var html = "";
			list = response[0];
			$.each(list, function(k, v) {
			    var num = Number(k+1);
			    var grzp = "<%=basePath%>/bin/upload/"+v.GRZP;
				html += "<div class='browse_body'><div class='left'><div class='xiantiao2'></div><nobr><div class='pic' style='text-align:center;'>";
				html += "<img src='"+ grzp +"' height='120px' width='120px'/></div></nobr>";
				html += "<div class='wenzi'><span>"+ v.ZZMC +"</span>";
				html += "<br><a title='本吧头衔"+ v.JBID +"级，经验值："+ v.SJJY +"'><div class='d_badge_title'>"+ v.JBMC +"</div><div class='d_badge_lv' style='background:url(<%=basePath%>"+ v.JBTP +") no-repeat left top'>"+ v.JBID +"</div></a>";
				html += "</div></div><div class='right'><div class='xiaodaohang'>";
				html += "<b class='timest'>"+ v.ZTHF_HFSJ +"</b><b> "+ num +"楼 ：</b></div><div class='liuyan'>";
				html += "<div id='kkk'>"+ v.HFNR +"</div></div><div class='gongneng'>";
				html += "<b class='del'><img src='<%=basePath%>/bin/imageshjl/b_tools_4.gif'/></b><span class='del'><a href='javascript:void(0)' class='sc'>删除<input type='hidden' value='"+v.ZTHF_HFID +"'/></a></span>";
				html += "<b><img src='<%=basePath%>/bin/imageshjl/b_tools_3.gif'/></b><span><a href='javascript:void(0)' class='hf'>回复<input type='hidden' value='"+ num +"楼 "+v.ZZMC +"'/></a></span></div>";
				html += "</div><div class='div_kongge'>&nbsp;</div></div>";
			});
			sp.append(html);
  		}
  		});
	}
	function makeBean(strZTHF_ZTID,strHFNR,strFBR) {
        this.ZTHF_ZTID = strZTHF_ZTID;
    	this.HFNR = strHFNR;
    	this.FBR = strFBR;
    }
	function checkText(){
    	//是否输入内容
    	if($.trim($(".summernote").code()) == ""){
			layer.alert('回复内容不能为空！', 0, '友情提示',function() {
				$(".summernote").focus();
				layer.close(layer.index);
			});
			return false;
    	}
    }
	//回复
	function saveData(ztid,bkid,yhid,bzid) {
        var aHTML=$(".summernote").code();
        //检查用户输入值
    	if(checkText()==false){
    		return;
    	}
        if($("#kshf").text() != "快速回复"){
            aHTML = '<p><span style="color:gray;">'+ $("#kshf").text() +'</span></p><br>'+aHTML;
        }
    	var flag = true;
    	var beanIn = new makeBean();
    	beanIn.ZTHF_ZTID = ztid;
    	beanIn.HFNR = aHTML;
    	beanIn.FBR = $("#txtFBR").val();

    	$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1100120",
    		data: {
    			CMD    : "<%=Servlet1100120.CMD_SAVE_HF%>",
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
  	        layer.msg('恭喜：回复成功！ 经验+2', 1, 9,function(){backZt(ztid,bkid,yhid,bzid);});
  	    } else {
  	        layer.msg('对不起：回复失败！', 1, 8);
  	    }
    }
	function makeBeanZd(strBKZT_ZTID,strFLAG) {
        this.BKZT_ZTID = strBKZT_ZTID;
    	this.BKZT_ZTLB = strFLAG;
    }
	//置顶
	function updateZTLB(strZtid,strFlag) {
    	var beanIn = new makeBeanZd();
    	beanIn.BKZT_ZTID = strZtid;
    	beanIn.BKZT_ZTLB = strFlag;

    	$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1100120",
    		data: {
    			CMD    : "<%=Servlet1100120.CMD_UPDATE_ZD%>",
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
  	        layer.msg('恭喜：操作成功！', 1, 9);
  	    } else {
  	        layer.msg('对不起：操作失败！', 1, 8);
  	    }
    }
	/* 跳转主题页 */
    function backZt(ztid,bkid,yhid,bzid){
        window.location.href="<%=basePath%>/bin/jsp/1100000/1100124.jsp?ztid="+ztid+"&bkid="+bkid+"&yhid="+yhid+"&bzid="+bzid;
    }
    function makeBeanDel(strZTHF_HFID) {
        this.ZTHF_HFID = strZTHF_HFID;
    }
	//删除回复
	function deleteHf(strHfid,strZtid,strBkid,strYhid,strBzid) {
    	var beanIn = new makeBeanDel();
    	beanIn.ZTHF_HFID = strHfid;

    	$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1100120",
    		data: {
    			CMD    : "<%=Servlet1100120.CMD_DELETE_HF%>",
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
  	        layer.msg('恭喜：删除成功！', 1, 9,function(){backHf(strZtid,strBkid,strYhid,strBzid);});
  	    } else {
  	        layer.msg('对不起：删除失败！', 1, 8);
  	    }
    }
	/* 跳转回复页 */
    function backHf(ztid,bkid,yhid,bzid){
        window.location.href="<%=basePath%>/bin/jsp/1100000/1100124.jsp?ztid="+ztid+"&bkid="+bkid+"&yhid="+yhid+"&bzid="+bzid;
    }
	</script>
	</head>
<body class="gray-bg">
<div id="body_div" style="height:700px; overflow:auto;">
    <input type="hidden" id="jinghuashu" value="">
    <input type="hidden" name="cuanidreply" value="">
    <input type="hidden" name="theme.idtheme" value="">
    <input type="hidden" id="idplate" name="plate.idplate" value="">
    <div class="top_title">
      <a id="goBack" href="javascript:void(0)">
        <div class="botton">返回</div>
      </a>
      <a id="goHf" href="javascript:void(0)">
        <div class="botton">我要回复</div>
      </a>
      <a id="goZd" href="javascript:void(0)">
        <div class="botton"><font id="txtZD" color=#375612></font></div>
      </a>
    </div>
    <div class="nav">
      <div class="browsebbs"> 主题：<span id="txtZTMC"></span>
        <div class="wenzhi">帖子阅读</div>
      </div>
    </div>
    <!-- 楼主begin -->
    <div class="browse_body">
      <div class="left">
        <div class="xiantiao2"></div>
        <nobr>
          <div class="pic" style="text-align:center;">
            <img id="imgBZZP" src="" height="120px" width="120px" />
          </div>
        </nobr>
        <div class="wenzi">
        <input id="txtFBR" type="hidden" />
        <span id="txtBZMC">
        </span>
        <br>
        <a id="zt_title">
          <div id="bbsMC" class="d_badge_title">
          </div>
          <div id="bbsDJ" class="d_badge_lv">
          </div>
        </a>
        </div>
      </div>
      <div class="right">
        <div class="xiaodaohang">
          <span id="txtZTMC"></span>
          <span class="timest"></span>
          <b><font color="red">楼主 ：</font><span id="txtFBSJ"></span></b>
        </div>
        <div id="txtZTNR" class="liuyan">
        </div>
      </div>
      <div class="div_kongge">&nbsp;</div>
    </div>
    <!-- 楼主end -->
    <!-- 1楼begin -->
    <div id="info">
    </div>
    <!-- 1楼end -->
    <br />
    <div class="nav">
      <div id="kshf" class="browsebbs2">快速回复</div>
    </div>
    <div class="nav">
      <table width="100%" align="left" border="0" cellpadding="0"
        cellspacing="1" bgcolor="#D6D6D8">
        <tr>
          <td height="120" bgcolor="#FFFFFF" class="wo7" style="text-align:left;">
            <div id="scroll"></div>
            <div class="summernote">
            </div>
          </td>
        </tr>
        <tr align="center">
          <td height="60" colspan="2" bgcolor="#FFFFFF"><table
              width="300" border="0" align="center" cellpadding="0"
              cellspacing="0">
              <tr>
                <td align="center">
                  <input id="btnSave" class="in5" type="button" value="回复" />
                </td>
                <td align="center"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>
</div>
</body>
</html>