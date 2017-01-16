<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1050000.Servlet1050110"%> 
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
<title>日报申请</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>

<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<style type="text/css">
.ribao_title{height:50px;background:#fff;text-align:center;font-family:'宋体';font-size:30px;}
.ribao_jiben{font-size:12px;line-height:24px;background:#000;color:#fff;}
.ribao_textarea_title{font-size:12px;line-height:24px;}
.ribao_textarea{width:95%;height:80px;outline:none;resize:none;border:#ddd 1px solid;}
.ribao_select{text-align:left;font-size:12px;padding:10px 0 10px 10px;}
.ribao_zhuangtai{line-height:24px;font-size:12px;background:#226ea5;color:#fff;}
.ribao_liucheng{vertical-align:top;background:#fff;}
.ribao_liucheng span{float:left;line-height:24px;font-size:12px;padding-left:10px}
.ribao_button{background:#fff;height:35px;}
.ribao_button table tr td{padding-left:10px}
.ribao_button table tr td input{background:#52627c;color:#fff;border:0;padding:5px 20px;cursor: pointer;border-radius: 4px;}
/* .ribao_button table tr td input{background:#fc0920;color:#fff;border:0;padding:5px 20px;} */
</style>
<script type="text/javascript">
$(document).ready(function(){
	//获的当天日期
	var myDate = new Date();   
	var year = "";
	var month = "";
	var day = "";
	var timeStr = "";
	year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
	month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
	if(month<10)
		{
		month = "0"+month;
		}
	day = myDate.getDate();        //获取当前日(1-31)
	if(day < 10) {
		day = "0"+day;
	}
	timeStr = year+"-"+month+"-"+day;
	//初始化下拉列表
	setButtonStatus("1");
    loadEditSelect($('#selectEditJHSPR'), 'SPLC_RBGL', '计划审批人');  
    loadEditSelect($('#selectEditZJSPR'), 'SPLC_RBGL', '总结审批人');  
	
	//获取父页面参数
	obj = window.parent.obj;
	if(obj.optionFlag == "Send"){
		var strRbzt = parseInt(getData("",timeStr))-0;
		if(0 <= strRbzt && strRbzt< 3 && strRbzt != 1){
			obj.optionFlag = "sqUpd";
		}else if(strRbzt == 1){
			setButtonStatus("3");
		}else if(0 > strRbzt){
			obj.optionFlag = "sqAdd";
		}else if(strRbzt == 3){
			obj.optionFlag = "zjAdd";
		}else if(3 < strRbzt && strRbzt < 7 && strRbzt != 5){
			obj.optionFlag = "zjUpd";
		}else if(strRbzt == 5){
			setButtonStatus("3");
		}else if(strRbzt == 7){
			obj.optionFlag = "End";
			setButtonStatus("3");
		}
		loadXMandBM(timeStr);
			//$('#txtEditKhmc').val(obj.KHXX_KHMC);
			//$('#selectEditHy').val(obj.KHXX_HY);
	}else{
		getData(obj.rbid,timeStr);
		$("#sqr").append(obj.sqr);
		$("#sqbm").append(obj.sqbm);
		$("#sqrq").append(obj.sqrq);
		setButtonStatus("3");
	}
	
	//$('#selectEditSPBM').attr("disabled", "disabled");
 	//$('#selectEditSPR').attr("disabled", "disabled");
    $('#btnSave').on('click', function(){
			if (obj.optionFlag == "sqAdd") {
				if (funEditCheck("sqAdd") == false) return;
				layer.confirm('是否增加日报申请信息？', function() {
					layer.close(layer.index);
					if (insertDayReport("sqAdd") == true) {
						iframeLayerClose();
					}
				});
			}else if (obj.optionFlag == "zjAdd") {
				if (funEditCheck("zjAdd") == false) return;
				layer.confirm('是否增加日报总结信息？', function() {
					layer.close(layer.index);
					if (insertDayReport("zjAdd") == true) {
						iframeLayerClose();
					}
				});
			}else if (obj.optionFlag == "sqUpd") {
			    if (funEditCheck("sqUpd") == false) return;
			    layer.confirm('是否修改日报申请信息？', function() {
			    	layer.close(layer.index);
			    	if (updateDayReport("sqUpd") == true) {
			    		iframeLayerClose();
					}
			    });
			}else if (obj.optionFlag == "zjUpd") {
			    if (funEditCheck("zjUpd") == false) return;
			    layer.confirm('是否修改日报总结信息？', function() {
			    	layer.close(layer.index);
			    	if (updateDayReport("zjUpd") == true) {
			    		iframeLayerClose();
					}
			    });
			}
    });
    $('#btnCancel').on('click', function(){
			iframeLayerClose();
    }); 
});
function makeBeanIn(strRBID,strSQRID,strSQRQ,strJHNR,strSPRID,strZJNR,strZJSPR,strREG){
	this.RBGL_RBID = strRBID;
    this.RBGL_SQR = strSQRID;  
    this.RBGL_SQRQ = strSQRQ;  
    this.RBGL_JHNR = strJHNR;
    this.RBGL_SPR = strSPRID;
    this.RBGL_ZJNR = strZJNR;
    this.RBGL_ZJSPR = strZJSPR;
    this.RBGL_REG = strREG;
}
//新增日报方法
function insertDayReport(strREG) {
	var blnRet = false;
	var beanIn = new makeBeanIn(
		$('#rbid').val(),
		$('#sqrid').val(),
		$('#sqrq').text(),
		$('#txtJHNR').val(),
		$('#selectEditJHSPR').val(),
		$('#txtZJNR').val(),
		$('#selectEditZJSPR').val(),
		strREG
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "<%=Servlet1050110.CMD_INSERT%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：新增日报信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：新增日报信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：新增日报信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//修改日报方法
function updateDayReport(strREG) {
	var blnRet = false;
	var beanIn = new makeBeanIn(
		$('#rbid').val(),
		"",
		"",
		$('#txtJHNR').val(),
		$('#selectEditJHSPR').val(),
		$('#txtZJNR').val(),
		$('#selectEditZJSPR').val(),
		strREG
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "<%=Servlet1050110.CMD_UPDATE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：修改日报信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：修改日报信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：修改日报信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
function getData(rbid,nowdate){
	var blnRet;
	var DatebeanIn = new makeBeanIn(
			rbid,
			"",
			nowdate,
			"",
			"",
			"",
			"",
			""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "<%=Servlet1050110.CMD_INFO%>",
		    BeanIn : JSON.stringify(DatebeanIn)
		},
		complete :function(response){},
		success: function(response){
			var sp = $("#sp");
			var strResault = response[0];
			var dataBean = response[1];	
			var list = response[2];
			var thisData = response[3];
			var nextData = response[4];
			var rbztData = response[5];
			var thatData = response[6];
			sp.empty();
			$.each(list, function(k, v) {
				sp.append("<img width='20' height='20' title='流程信息' src='<%=basePath%>/bin/img/common/downflow.png'></img>");
				sp.append("【"+v.RBMX_CZRXM+"】 "+v.RBMX_CZSJ+" 时发出【"+v.RBMX_CZFS+"】操作: "+v.RBMX_CZNR+"<br>");
			});
			if(strResault=="SUCCESS"){	
				blnRet = dataBean.RBGL_ZTID;
				if(dataBean.RBGL_ZTID == "2" || dataBean.RBGL_ZTID == "3" || dataBean.RBGL_ZTID == "6"){
					sp.append("<img width='20' height='20' title='流程信息' src='<%=basePath%>/bin/img/common/downflow.png'></img>");
					sp.append("【"+dataBean.SQR+"】 等待操作...");
				}else if(dataBean.RBGL_ZTID == "7"){
					
				}else{
					sp.append("<img width='20' height='20' title='流程信息' src='<%=basePath%>/bin/img/common/downflow.png'></img>");
					sp.append("【"+nextData.RBMX_CZRXM+"】 等待操作...");
				}
				
				$("#rbid").val(dataBean.RBGL_RBID);
				$("#selectEditJHSPR  option[value='"+thisData.RBMX_XYCZR+"'] ").attr("selected",true);
				if(thatData!=null){
					$("#selectEditZJSPR  option[value='"+thatData.RBMX_XYCZR+"'] ").attr("selected",true);
				}
				
				if(dataBean.RBGL_ZTID == 3){
					setButtonStatus("2");
					$("#jhzt").append(dataBean.RBZT);
				}else if(dataBean.RBGL_ZTID < 3){
					$("#jhzt").append(dataBean.RBZT);
				}else if(dataBean.RBGL_ZTID > 3){
					setButtonStatus("2");
					$("#jhzt").append(rbztData.RBZT);
					$("#zjzt").append(dataBean.RBZT);
				}
				
				$("#txtJHNR").append(dataBean.RBGL_JHNR);
				$("#txtZJNR").append(dataBean.RBGL_ZJNR);
			}else if(strResault=="DATA_NULL"){
				blnRet = -1;
			}else{
				layer.msg('友情提示：获取个人信息出错！', 1, 0);
			}
		}
	});
	return blnRet;
}
function loadXMandBM(strSQRQ){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1050110",
		data: {
			CMD    : "CMD_DATA"
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				$("#sqrid").val(dataBean.RBGL_SQR);
				$("#sqr").append(dataBean.SQR);
				$("#sqbm").append(dataBean.SQBM);
				$("#sqrq").append(strSQRQ);
				blnRet = true;
			} else if (strResault == "DATA_NULL") {
				blnRet = false;
			}else{
				layer.msg('友情提示：获取个人信息出错！', 1, 0);
			}
		}
	});
}
//设置编辑状态
function setButtonStatus(strFlag) {
	if (strFlag == "1") {//初始状态-申请可用，总结不可用
		$('#txtJHNR').focus();
		$('#selectEditZJSPR').attr("disabled","disabled");
		$('#txtZJNR').attr('disabled','true');
	} else if (strFlag == "2") {//批准状态-申请不可用，总结可用	
		$('#selectEditZJSPR').removeAttr("disabled");
		$('#txtZJNR').removeAttr("disabled");
		$('#txtZJNR').focus();
		$('#selectEditJHSPR').attr("disabled","disabled");
		//$('#txtJHNR').attr("disabled","disabled");
		$('#txtJHNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtJHNR').css("color","gray");
	} else if (strFlag == "3") {//结束状态-都不可用，确定按钮不可用
		$('#txtJHNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtZJNR').attr("readonly","readonly");//input元素设置readonly
		$('#txtJHNR').css("color","gray");
		$('#txtZJNR').css("color","gray");
		$('#selectEditJHSPR').attr("disabled","disabled");
		$('#selectEditZJSPR').attr("disabled","disabled");
		$('#btnSave').attr("disabled","disabled");
		$('#btnSave').css("background","#959494");
	} else if (strFlag == "4") {//选中行
		
	}
}
//验证编辑输入数据
function funEditCheck(strREG) {
	if(strREG=="sqAdd" || strREG=="sqUpd"){
		if($('#txtJHNR').val() == ""){
			layer.alert('请输入日工作计划！', 0, '友情提示', function() {
				$('#txtJHNR').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditJHSPR').val() == "000") {
			layer.alert('请选择计划审批人！', 0, '友情提示', function() {
				$('#selectEditJHSPR').focus();
				layer.close(layer.index);
			});
			return false;
		}	
	}else if(strREG=="zjAdd" || strREG=="zjUpd"){
		if($('#txtZJNR').val() == ""){
			layer.alert('请输入日工作总结！', 0, '友情提示', function() {
				$('#txtZJNR').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditZJSPR').val() == "000") {
			layer.alert('请选择总结审批人！', 0, '友情提示', function() {
				$('#selectEditZJSPR').focus();
				layer.close(layer.index);
			});
			return false;
		}	
	}
	return true;
}
function changeCZ(strCZ){
 	if(strCZ == 2){
 		//$("#xyclr").show();  
 		$('#selectEditSPBM').removeAttr("disabled");
	    $('#selectEditSPR').removeAttr("disabled");
 	}else{
 		//$("#xyclr").hide();  
 		$('#selectEditSPBM').val("000");
 		$('#selectEditSPR').val("000");
 		$('#selectEditSPBM').attr("disabled", "disabled");
 		$('#selectEditSPR').attr("disabled", "disabled");
 	}
}
function makeInfoBeanIn(strKPID){
    this.KPGL_KPID = strKPID; 
}

</script>
</head>
<body>
	<div class="Yy"></div>
	<div class="NewMain">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#999">
			<tr>
				<td width="40%" class="ribao_title">日报申请</td>
				<td width="60%" class="ribao_title">日报流程</td>
			</tr>
			<tr>
				<td align="center"  bgcolor="white">
					<input type="hidden" id="sqrid" /><input type="hidden" id="rbid" />
					<div class="ribao_jiben">
						申请人:<span id="sqr"></span>&nbsp;&nbsp;
						部门:<span id="sqbm"></span>&nbsp;&nbsp;
						申请日期:<span id="sqrq"></span>
					</div>
					<div class="ribao_textarea_title">日工作计划</div>
					<textarea  name="gzjh"  id="txtJHNR"  class="ribao_textarea" style="overflow-y:auto"></textarea>
					<div class="ribao_select">
						<span>计划审批人：</span><select id="selectEditJHSPR"></select>
					</div>
					<div class="ribao_zhuangtai">
						<span>当前状态：</span><span id="jhzt"></span>
					</div>
					<div class="ribao_textarea_title">日工作总结</div>
					<textarea  name="gzzj" id="txtZJNR"  class="ribao_textarea" style="overflow-y:auto"></textarea>
					<div class="ribao_select">
						<span>总结审批人：</span><select id="selectEditZJSPR" ></select>
					</div>
					<div class="ribao_zhuangtai">
						<span>当前状态：</span><span id="zjzt"></span>
					</div>
				</td>
				<td class="ribao_liucheng">
					<span id="sp"></span>
				</td>
			</tr>
			<tr>
				<td class="ribao_button" colspan="2">
					<table width="300"  align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td><input type="button" name="Submit2" value="确定"  id="btnSave" /></td>
							<td><input type="button" name="Submit2" value="关闭"  id="btnCancel" /></td>
						</tr>
					</table>
		  	    </td>
			</tr>
		</table>
	</div>	
</body>
</html>