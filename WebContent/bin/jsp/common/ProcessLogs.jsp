<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.common.ProcessLogsServlet"%> 
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批流程</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<style type="text/css">
.sp_title{height:40px;background:#fff;text-align:center;font-family:'宋体';font-size:30px;}
.sp_liucheng{vertical-align:top;background:#fff;height:400px}
.sp_liucheng span{float:left;line-height:24px;font-size:12px;padding-left:10px}
.sp_button{background:#fff;height:35px;}
.sp_button table tr td{padding-left:10px}
.sp_button table tr td input{background:#52627c;color:#fff;border:0;padding:5px 20px;cursor: pointer;border-radius: 4px;}
/* .sp_button table tr td input{background:#fc0920;color:#fff;border:0;padding:5px 20px;}*/
</style>
<script type="text/javascript">
$(document).ready(function(){
	//获取父页面参数
	obj = window.parent.obj;
	var sqid = obj.SQID;
	if(obj.optionFlag == "SPLC_KQCC"){//考勤出差
		$("#mc").append("出差");
		getData(sqid,"SPLC_KQCC");
	}else if(obj.optionFlag == "SPLC_KQJB"){//考勤加班
		$("#mc").append("加班");
		getData(sqid,"SPLC_KQJB");
	}else if(obj.optionFlag == "SPLC_KQXJ"){//考勤休假
		$("#mc").append("休假");
		getData(sqid,"SPLC_KQXJ");
	}
    $('#btnCancel').on('click', function(){
			iframeLayerClose();
    }); 
});
function makeBeanIn(strSQID,strLOGTYPE){
	this.SQID = strSQID;
    this.LOGTYPE = strLOGTYPE;  
}
function getData(strSQID,strLOGTYPE){
	var blnRet;
	var DatebeanIn = new makeBeanIn(
			strSQID,
			strLOGTYPE
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/ProcessLogsServlet",
		data: {
			CMD    : "<%=ProcessLogsServlet.CMD_INFO%>",
		    BeanIn : JSON.stringify(DatebeanIn)
		},
		complete :function(response){},
		success: function(response){
			var lc = $("#lc");
			var strResault = response[0];
			var list = response[1];
			lc.empty();
			$.each(list, function(k, v) {
				lc.append("<img width='20' height='20' title='流程信息' src='<%=basePath%>/bin/img/common/downflow.png'></img>");
				lc.append("【"+v.CZR+"】 "+v.CZSJ+" 发出【"+v.CZLX+"】操作: "+v.CZNR+"<br>");
			});
			if(strResault=="SUCCESS"){	

			}else if(strResault=="DATA_NULL"){
				lc.append("【无审批流程】");
			}else{
				layer.msg('友情提示：获取流程信息出错！', 1, 0);
			}
		}
	});
	return blnRet;
}
</script>
</head>
<body>
	<div class="Yy"></div>
	<div class="NewMain">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#999">
			<tr>
				<td width="60%" class="sp_title"><span id="mc"></span>审批流程</td>
			</tr>
			<tr>
				<td class="sp_liucheng">
				<div  style="overflow:auto">
					<span id="lc"></span>
				</div>
				</td>
			</tr>
			<tr>
				<td class="sp_button" colspan="1">
					<table width="100%"  cellpadding="0" cellspacing="0">
						<tr>			
							<td align="center"><input type="button" name="Submit2" value="关闭"  id="btnCancel" /></td>
						</tr>
					</table>
		  	    </td>
			</tr>
		</table>
	</div>	
</body>
</html>