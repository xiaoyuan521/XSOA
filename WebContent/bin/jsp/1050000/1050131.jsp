<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1050000.Servlet1050120"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/css/layout.css" type="text/css">
<!--表格样式End  -->
<title>日报审批</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js"></script>

<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<style type="text/css">
	.wo6{ font-family:"微软雅黑"; font-size:28px; line-height:45px;}
	.wo7{ color: #32373B;line-height:30px;font-size:14px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
    loadEditSelect($('#selectEditXYSPR'), 'SPLC_RBGL', '下一审批人');  
	setButtonStatus(1);
	//获取父页面参数
	obj = window.parent.obj;

	$('#sqr').append(obj.sqr);
	$('#sqbm').append(obj.sqbm);
	$('#sqrq').append(obj.sqrq);
	$('#txtJHNR').val(obj.jhnr);
	$('#txtZJNR').val(obj.zjnr);
	$('#dqzt').append(obj.rbzt);
	getData(obj.rbid);
	
    $('#btnFlow').on('click', function(){
			if (funEditCheck("flow") == false) return;
		    var spzt;
		    if(obj.ztid == "0"||obj.ztid == "1"){
		    	spzt = "1";//计划流转
		    }else if(obj.ztid == "4"||obj.ztid == "5"){
		    	spzt = "5";//总结流转
		    }
			layer.confirm('是否流转？', function() {
				layer.close(layer.index);
				if (approveDayReport("flow",spzt) == true) {
					iframeLayerClose();
				}
			});
    });
    $('#btnAgree').on('click', function(){
			if (funEditCheck("agree") == false) return;
			var spzt;
		    if(obj.ztid == "0"||obj.ztid == "1"){
		    	spzt = "3";//计划批准
		    }else if(obj.ztid == "4"||obj.ztid == "5"){
		    	spzt = "7";//总结批准
		    }
			layer.confirm('是否批准？', function() {
				layer.close(layer.index);
				if (approveDayReport("agree",spzt) == true) {
					iframeLayerClose();
				}
			});
    });
    $('#btnBack').on('click', function(){
			if (funEditCheck("back") == false) return;
			var spzt;
		    if(obj.ztid == "0"||obj.ztid == "1"){
		    	spzt = "2";//计划驳回
		    }else if(obj.ztid == "4"||obj.ztid == "5"){
		    	spzt = "6";//总结驳回
		    }
			layer.confirm('是否驳回？', function() {
				layer.close(layer.index);
				if (approveDayReport("back",spzt) == true) {
					iframeLayerClose();
				}
			});
    });
    $('#btnCancel').on('click', function(){
			iframeLayerClose();
    }); 
});
function makeBeanIn(strRBID,strSPYJ,strXYSPR,strZTID){
	this.RBMX_RBID = strRBID;
    this.RBMX_CZNR = strSPYJ;  
    this.RBMX_XYCZR = strXYSPR;
    this.RBMX_ZTID = strZTID; 
}
//审批日报方法
function approveDayReport(strState,strRBZT) {
	var blnRet = false;
	if(strState == "flow"){
		var beanIn = new makeBeanIn(
			obj.rbid,
			$('#txtSPYJ').val(),
			$('#selectEditXYSPR').val(),
			strRBZT
		);	
	}else{
		var beanIn = new makeBeanIn(
			obj.rbid,
			$('#txtSPYJ').val(),
			obj.spr,
			strRBZT
		);
	}
	
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050120",
		data: {
			CMD    : "<%=Servlet1050120.CMD_APPROVE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				//layer.msg('恭喜：修改日报信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				//layer.msg('对不起：修改日报信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				//layer.msg('友情提示：修改日报信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function getData(rbid){
	var blnRet = false;
	var DatebeanIn = new makeBeanIn(
			rbid,
			"",
			"",
			""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050120",
		data: {
			CMD    : "<%=Servlet1050120.CMD_INFO%>",
		    BeanIn : JSON.stringify(DatebeanIn)
		},
		complete :function(response){},
		success: function(response){
			var sp = $("#sp");
			var strResault = response[0];
			var dataBean = response[1];	
			var list = response[2];
			var nextData = response[3];
			sp.empty();
			$.each(list, function(k, v) {
				sp.append("<img width='20' height='20' title='流程信息' src='<%=basePath%>/bin/img/common/downflow.png'></img>");
				sp.append("【"+v.RBMX_CZRXM+"】 "+v.RBMX_CZSJ+" 时发出【"+v.RBMX_CZFS+"】操作: "+v.RBMX_CZNR+"<br>");
			});
			if(strResault=="SUCCESS"){	
				blnRet = true;
				sp.append("<img width='20' height='20' title='流程信息' src='<%=basePath%>/bin/img/common/downflow.png'></img>");
				sp.append("【"+nextData.RBMX_CZRXM+"】 等待操作...");
				
			}else if(strResault=="DATA_NULL"){
			}else{
				layer.msg('友情提示：获取日报流程信息出错！', 1, 0);
			}
		}
	});
	return blnRet;
}
//设置编辑状态
function setButtonStatus(strFlag) {
	if (strFlag == "1") {//初始状态
		$('#txtSPYJ').focus();
		$('#txtJHNR').attr('disabled','true');
		$('#txtZJNR').attr('disabled','true');
	} else if (strFlag == "2") {//查询后/返回
		$('#txtJHNR').attr('disabled','true');
		$('#txtZJNR').attr('disabled','true');
	} else if (strFlag == "4") {//选中行
		$('#txtJHNR').attr('disabled','true');
		$('#txtZJNR').attr('disabled','true');
	}
}
//验证编辑输入数据
function funEditCheck(strState) {
	if($('#txtSPYJ').val() == ""){
		layer.alert('请输入审批意见！', 0, '友情提示', function() {
			$('#txtSPYJ').focus();
			layer.close(layer.index);
		});
		return false;
	}
	if(strState == "flow"){
		if ($('#selectEditXYSPR').val() == "000") {
			layer.alert('请选择下一审批人！', 0, '友情提示', function() {
				$('#selectEditXYSPR').focus();
				layer.close(layer.index);
			});
		return false;
		}	
	}
	return true;
}
</script>
</head>
<body >
		<div class="Yy"></div>
		<div class="NewMain">
				<table width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="black" >
					<tr>
						<td height="50" width="40%" colspan="1" bgcolor="white" class="wo6" align="center">
							&nbsp;日报审批
						</td>
						<td height="50"   width="60%" colspan="1" bgcolor="white" class="wo6" align="center">
							&nbsp;日报流程
						</td>
					</tr>
					<tr>
						
						<td   style="height:350px"  align="center"  bgcolor="white"  class="wo7">
							<input type="hidden" id="ztid" />
							<div style="margin-bottom:0; ">申请人:<span id="sqr"></span>&nbsp;&nbsp;部门:<span id="sqbm"></span>&nbsp;&nbsp;申请日期:<span id="sqrq"></span></div>
							<div>日工作计划</div>
							<textarea  id="txtJHNR"  style="width: 80%;height: 20%;overflow-y:hidden" readonly="readonly"></textarea>
							<br>
							<div>日工作总结</div>
							<textarea  id="txtZJNR"   style="width: 80%;height: 20%;overflow-y:hidden" readonly="readonly"></textarea>
							<div>审批意见</div>
							<textarea  id="txtSPYJ"   style="width: 80%;height: 20%;overflow-y:hidden"></textarea>
							<div style="border-left:10%">
								<span>下一审批人</span><select id="selectEditXYSPR" ></select>
								当前状态：<span id="dqzt"></span>
							</div>
						</td>
						<td   style="height:200px;vertical-align:top"  bgcolor="white"  class="wo7" ><span style="float:left; padding-left:5px ;line-height:27px;font-size:12px;padding-left:10px" id="sp"></span></td>
						
					</tr>
					<tr>
						<td height="80" colspan="4" bgcolor="white">
							<table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
											<td align="center" style="padding-left:10px">
												<input type="button" name="Submit2" value="流转"  id="btnFlow" />
											</td>	
											<td align="center" style="padding-left:10px">
												<input type="button" name="Submit2" value="批准"  id="btnAgree" />
											</td>
											<td align="center" style="padding-left:10px">
												<input type="button" name="Submit2" value="驳回"  id="btnBack" />
											</td>
											<td align="center" style="padding-left:10px">
												<input type="button" name="Submit2" value="关闭"  id="btnCancel" />
											</td>
										</tr>
							</table>
				  	    </td>
					</tr>
				</table>
		</div>	
	</body>
</html>