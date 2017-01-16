<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1050000.Servlet1050120"%> 
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
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/css/layout.css" type="text/css">
<!--表格样式End  -->
<title>日报审批</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>

<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<style type="text/css">
.ribao_title{height:50px;background:#fff;text-align:center;font-family:'宋体';font-size:30px;}
.ribao_jiben{font-size:12px;line-height:24px;background:#000;color:#fff;}
.ribao_textarea_title{font-size:12px;line-height:24px;}
.ribao_textarea{width:95%;height:74px;outline:none;resize:none;border:#ddd 1px solid;}
.ribao_select{text-align:left;font-size:12px;padding:10px 0 10px 10px;}
.ribao_zhuangtai{line-height:24px;font-size:12px;background:#226ea5;color:#fff;}
.ribao_liucheng{vertical-align:top;background:#fff;}
.ribao_liucheng span{float:left;line-height:24px;font-size:12px;padding-left:10px}
.ribao_button{background:#fff;height:35px;}
.ribao_button table tr td{padding-left:10px}
.ribao_button table tr td input{background:#52627c;color:#fff;border:0;padding:5px 20px;cursor: pointer;border-radius: 4px;}
/*.ribao_button table tr td input{background:#fc0920;color:#fff;border:0;padding:5px 20px;}*/
</style>
<script type="text/javascript">
$(document).ready(function(){
	//获取父页面参数
	obj = window.parent.obj;
	
	$('#sqr').append(obj.sqr);
	$('#sqbm').append(obj.sqbm);
	$('#sqrq').append(obj.sqrq);
	$('#txtJHNR').val(obj.jhnr);
	$('#txtZJNR').val(obj.zjnr);
	$('#dqzt').append(obj.rbzt);
	loadEditSelect($('#selectEditXYSPR'), 'SPLC_RBGL-'+obj.sqrid, '下一审批人');  
	if($('#selectEditXYSPR option').length>1){	
		//取得当前用户是否可以终结
		if(getSPRKFZJ("SPLC_RBGL",obj.sqrid)=="0"){//不可终结
			setButtonStatus(2);
		}else{
			setButtonStatus(1);
		}	
	}else{
		setButtonStatus(1);
	}
	//getSFZJ(obj.sqrid);
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
function getSFZJ(strSQRID){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050120",
		data: {
			CMD    : "CMD_SFZJ",
			SQR     :  strSQRID
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				if(dataBean.SFZJ==0){
					setButtonStatus(2);
					//$('#btnSave').attr("disabled","disabled");
				}else if(dataBean.SFZJ==1){
					setButtonStatus(1);
					
					//$('#btnSave').removeAttr("disabled");$('#btnSave').attr("disabled","disabled");
				}
			} else if (strResault == "DATA_NULL") {
				$('#btnSave').removeAttr("disabled");
			}else{
				layer.msg('友情提示：获取审批人权限出错！', 1, 0);
			}
		}
	});
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
		//$('#txtSPYJ').focus();
		$('#txtJHNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtZJNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtJHNR').css("color","gray");
		$('#txtZJNR').css("color","gray");
		//$('#txtJHNR').attr('disabled','true');
		//$('#txtZJNR').attr('disabled','true');
		$('#btnAgree').removeAttr("disabled");
	} else if (strFlag == "2") {//查询后/返回
		//$('#txtSPYJ').focus();
		$('#btnAgree').attr("disabled","disabled");
		$('#btnAgree').css("background","#959494");
		$('#txtJHNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtZJNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtJHNR').css("color","gray");
		$('#txtZJNR').css("color","gray");
		//$('#txtJHNR').attr('disabled','true');
		//$('#txtZJNR').attr('disabled','true');
	} else if (strFlag == "4") {//选中行
		$('#txtJHNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtZJNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtJHNR').css("color","gray");
		$('#txtZJNR').css("color","gray");
		//$('#txtJHNR').attr('disabled','true');
		//$('#txtZJNR').attr('disabled','true');
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
		<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#999">
			<tr>
				<td width="45%" class="ribao_title">日报审批</td>
				<td width="55%" class="ribao_title">日报流程</td>
			</tr>
			<tr>
				<td align="center" bgcolor="white">
					<input type="hidden" id="ztid" />
					<div class="ribao_jiben">
						申请人:<span id="sqr"></span>&nbsp;&nbsp;
						部门:<span id="sqbm"></span>&nbsp;&nbsp;
						申请日期:<span id="sqrq"></span>
					</div>
					<div class="ribao_textarea_title">日工作计划</div>
					<textarea id="txtJHNR" class="ribao_textarea" readonly="readonly"  style="overflow-y:auto;"></textarea>
					<div class="ribao_textarea_title">日工作总结</div>
					<textarea  id="txtZJNR" class="ribao_textarea" readonly="readonly"  style="overflow-y:auto"></textarea>
					<div class="ribao_textarea_title">审批意见</div>
					<textarea  id="txtSPYJ" class="ribao_textarea"  style="overflow-y:auto"></textarea>
					<div class="ribao_select">
						<span>下一审批人</span><select id="selectEditXYSPR" ></select>
					</div>
					<div  class="ribao_zhuangtai">
						<span>当前状态：</span><span id="dqzt"></span>
					</div>
				</td>
				<td class="ribao_liucheng">
					<span id="sp"></span>
				</td>
			</tr>
			<tr>
				<td  class="ribao_button" colspan="2">
					<table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center"><input type="button" name="Submit2" value="流转"  id="btnFlow" /></td>	
							<td align="center"><input type="button" name="Submit2" value="批准"  id="btnAgree" /></td>
							<td align="center"><input type="button" name="Submit2" value="驳回"  id="btnBack" /></td>
							<td align="center"><input type="button" name="Submit2" value="关闭"  id="btnCancel" /></td>
						</tr>
					</table>
		  	    </td>
			</tr>
		</table>
	</div>	
</body>
</html>