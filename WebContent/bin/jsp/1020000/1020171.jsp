<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020170"%> 	
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
<title>休假申请</title>
<link rel="stylesheet" href="1020171.css?r=<%=radom %>" type="text/css">
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//取得休假ID
	paraXJSQ_SQID = getQueryString("XJSQ_SQID");

	//初始化页面信息
	getXJXXByID(paraXJSQ_SQID);

	//取得下一审批人
	var strSQR = $("#hidXJSQ_SQR").val();
	loadEditSelect($('#selectXJSQ_XYSP'), 'SPLC_KQXJ-'+strSQR, '下一审批人');
	if($('#selectXJSQ_XYSP option').length>1){//有下一审批人,继续判断是否可终结
		//取得当前用户是否可以终结
		if(getSPRKFZJ("SPLC_KQXJ",$("#hidXJSQ_SQR").val())=="0"){//不可终结
			btnFinish.className="bottonDisable";
			btnFinish.onclick="";
			btnReject.className="bottonDisable";
			btnReject.onclick="";
		}	
	}

})

function makeBeanIn(strXJSQ_SQID) {
	this.XJSQ_SQID = strXJSQ_SQID;
}

/* 根据休假ID取得休假信息 */ 
function getXJXXByID(strSQID){
	var blnRet = false;
	var beanIn = new makeBeanIn(strSQID);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1020170",
		data: {
			CMD    : "<%=Servlet1020170.CMD_GETXJXX%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				var resultRecord = response[1];
				$("#hidXJSQ_SQR").val(resultRecord.XJSQ_SQR);
				$("#txtXJSQ_SQRM").val(resultRecord.XJSQ_SQRM);
				$("#hidXJSQ_BMID").val(resultRecord.XJSQ_BMID);
				$("#txtXJSQ_BMMC").val(resultRecord.XJSQ_BMMC);
				if(strSQID!=""){
					$("#hidXJSQ_SQID").val(resultRecord.XJSQ_SQID);
					$("#txtXJSQ_SQSJ").val(resultRecord.XJSQ_SQSJ);
					$("#txtXJSQ_XJTS").val(resultRecord.XJSQ_XJTS);
					$("#txtXJSQ_KSSJ").val(resultRecord.XJSQ_KSSJ);
					$("#txtXJSQ_JSSJ").val(resultRecord.XJSQ_JSSJ);
					$("#txtXJSQ_XJLX").val(resultRecord.XJSQ_XJMC);
					$("#hidXJSQ_XJLX").val(resultRecord.XJSQ_XJLX);
					$("#txtXJSQ_XJYY").val(resultRecord.XJSQ_XJYY);
					$("#selectXJSQ_XYSP").val(resultRecord.XJSQ_XYSP);
					$("#txtXJSQ_XJZT").val(resultRecord.XJSQ_ZTMC);
				}
				
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：休假信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：休假信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
}

/* 关闭窗口 */
function closeW(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

function makeBeanSaveIn(strXJMX_SQID,strXJMX_CZNR,strXJMX_XYCZR,strXJMX_CZZT) {
	this.XJMX_SQID = strXJMX_SQID;
	this.XJMX_CZNR = strXJMX_CZNR;
	this.XJMX_XYCZR = strXJMX_XYCZR;
	this.XJMX_CZZT = strXJMX_CZZT;
}

function saveData(flag){
	//检查用户输入值
	if(flag=="2"){//流转
		//是否选择审批人
		if($("#selectXJSQ_XYSP").val()=="000"){
			layer.alert('请选择审批人！', 0, '友情提示',function() {
				$("#selectXJSQ_XYSP").focus();
				layer.close(layer.index);
			});
			return false;
		}
	}else if(flag=="4" || flag=="5"){//驳回重审与驳回完结
		//是否输入审批意见
		if($("#txtXJMX_CZNR").val()==""){
			layer.alert('请输入审批意见！', 0, '友情提示',function() {
				$("#txtXJMX_CZNR").focus();
				layer.close(layer.index);
			});
			return false;
		}
	}
	
	
	var beanIn = new makeBeanSaveIn();
	beanIn.XJMX_SQID = $("#hidXJSQ_SQID").val();
	beanIn.XJMX_CZNR = $("#txtXJMX_CZNR").val();
	if($("#selectXJSQ_XYSP").val()=="000"){
		beanIn.XJMX_XYCZR  = "";	
	}else{
		beanIn.XJMX_XYCZR  = $("#selectXJSQ_XYSP").val();	
	}
	beanIn.XJMX_CZZT = flag;
	
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1020170",
		data: {
			CMD    : "<%=Servlet1020170.CMD_SAVE%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				if(flag=="2"){//流转
					layer.msg('恭喜：批准流转成功！', 1, 9,function(){closeW();});	
				}else if(flag=="3"){//流转
					layer.msg('恭喜：批准完结成功！', 1, 9,function(){closeW();});
				}else if(flag=="4"){//驳回
					layer.msg('恭喜：驳回成功！', 1, 9,function(){closeW();});
				}
				blnRet = true;
			}  else if (strResult == "FAILURE") {
				layer.msg('对不起：审批失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：审批出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	
}

</script>
</head>
<body>
	<div class="table_reset" id="editRegion">
		<table id="detailCanvas" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="6" class="title_ border_left_none">
					<span>休 假 审 批</span>
				</td>
			</tr>
			<tr>
				<th>申请人：</th>
				<td>
					<input type="hidden" id="hidXJSQ_SQID" />
					<input type="hidden" id="hidXJSQ_SQR" />
					<input type="text" id="txtXJSQ_SQRM" onfocus=this.blur() readonly />
				</td>
				<th>部门：</th>
				<td class="border_right">
					<input type="hidden" id="hidXJSQ_BMID" />
					<input type="text" id="txtXJSQ_BMMC" onfocus=this.blur() readonly />
				</td>
			</tr>
 			<tr>
 				<th>申请日期：</th>
				<td>
					<input type="text" id="txtXJSQ_SQSJ" onfocus=this.blur() readonly="readonly">
				</td>
				<th>请假天数：</th>
				<td class="border_right">
					<input type="text" id="txtXJSQ_XJTS" onfocus=this.blur() readonly />
				</td>
			</tr>
			<tr>
				<th>请假期限</th>
				<td>
					<input type="text" id="txtXJSQ_KSSJ"  onfocus=this.blur() readonly="readonly">
				</td>
				<th style="font-size: 24px;">～</th>
				<td class="border_right">
					<input type="text" id="txtXJSQ_JSSJ" onfocus=this.blur() readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>请假类型：</th>
				<td>
					<input type="hidden" id="hidXJSQ_XJLX" />
					<input type="text" id="txtXJSQ_XJLX" onfocus=this.blur() readonly="readonly">
				</td>
				<th>状态</th>
				<td class="border_right">
					<input type="text" id="txtXJSQ_XJZT" onfocus=this.blur() readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>申请原因：</th>
				<td valign="middle" colspan="3" class="border_right">
					<textarea id="txtXJSQ_XJYY" style="width: 100%; border: 0;" onfocus=this.blur() ></textarea>
				</td>
			</tr>
			<tr>
				<th>审批意见：</th>
				<td valign="middle" colspan="5" class="border_right">
					<textarea style="width: 100%; border: 0" id="txtXJMX_CZNR"></textarea>
				</td>
			</tr>
			<tr>
				<th>下一处理人:</th>
				<td colspan="2" style="text-align:left;">
					<select id="selectXJSQ_XYSP">
						<option value="000" selected>选择处理人</option>
					</select>
				</td>
				<td class="border_right">
					<a class="bottonChange" id="btnChange" onclick="saveData(2)">批准流转</a>
				</td>
			</tr>
		</table>

		<table border="0" cellpadding="0" cellspacing="0" class="table_2 border_none_exbottom">
			<tr>
				<td colspan="6" class="border_bottom_none">
					<div class="rightstyle">
						
						<a class="botton" id="btnFinish" onclick="saveData(3)">批准完结</a>
						<a class="botton" id="btnReDo" onclick="saveData(4)">驳回重审</a>
						<a class="botton" id="btnReject" onclick="saveData(5)">驳回完结</a>
						<a class="botton" onclick="closeW()">关闭</a>
					</div>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>