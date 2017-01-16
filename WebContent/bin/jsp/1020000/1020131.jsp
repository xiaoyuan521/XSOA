<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020130"%> 	
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
<link rel="stylesheet" href="1020131.css?r=<%=radom %>" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/control/buttonStyle.css?r=<%=radom %>" type="text/css">
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("input[name='chkXJSQ_XJLX']").click(function(){//点击事件:只能选中一个
		 if($(this).is(':checked')){
			 var currValue=$(this).val();
			 $("input[name='chkXJSQ_XJLX']:checked").each(function(){ 
				 if($(this).val()!=currValue){
					 $(this).removeAttr("checked");
				 } 
			 });
		 }
	});
	
	loadSearchSelect($('#selectXJSQ_XYSP'), 'SPLC_KQXJ', '审批人');
	
	//取得休假ID
	paraXJSQ_SQID = getQueryString("XJSQ_SQID");
	$("#txtXJSQ_SQSJ").val(getCurrDate());
	if(paraXJSQ_SQID==null || paraXJSQ_SQID==""){
		paraXJSQ_SQID="";
	}
	//初始化页面信息
	getXJXXByID(paraXJSQ_SQID);	
	
})

/* 计算休假天数 */
function setXJSQ_XJTS(){
	if($("#txtXJSQ_KSSJ").val()=="" || $("#txtXJSQ_JSSJ").val()==""){
		$("#txtXJSQ_XJTS").val("");
	}else{
		var strXJTS = GetDateRegion($("#txtXJSQ_KSSJ").val(),$("#txtXJSQ_JSSJ").val());
		$("#txtXJSQ_XJTS").val(strXJTS);
	}
}
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
		url: "<%=basePath%>/Servlet1020130",
		data: {
			CMD    : "<%=Servlet1020130.CMD_GETXJXX%>",
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
					$("input[name='chkXJSQ_XJLX']:checkbox[value='"+resultRecord.XJSQ_XJLX+"']").attr('checked','true');
					$("#txtXJSQ_XJYY").val(resultRecord.XJSQ_XJYY);
					$("#selectXJSQ_XYSP").val(resultRecord.XJSQ_XYSP);					
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


function checkSelect(){
	//是否选择申请日期
	if($("#txtXJSQ_SQSJ").val()==""){
		layer.alert('请选择申请日期！', 0, '友情提示',function() {
			$("#txtXJSQ_SQSJ").focus();
			layer.close(layer.index);
		});
		return false;
	}
	
	//是否选择休假期限开始
	if($("#txtXJSQ_KSSJ").val()==""){
		layer.alert('请选择休假期限！', 0, '友情提示',function() {
			$("#txtXJSQ_KSSJ").focus();
			layer.close(layer.index);
		});
		return false;
	}
	
	//是否选择休假期限结束
	if($("#txtXJSQ_JSSJ").val()==""){
		layer.alert('请选择休假期限！', 0, '友情提示',function() {
			$("#txtXJSQ_JSSJ").focus();
			layer.close(layer.index);
		});
		return false;
	}
	
	if($("#txtXJSQ_XJTS").val()<=0){
		layer.alert('请选择正确休假期限！', 0, '友情提示',function() {
			$("#txtXJSQ_JSSJ").focus();
			layer.close(layer.index);
		});
		return false;
	}
	
	//判断是否选择休假类型
	if($("input[name='chkXJSQ_XJLX']:checked").length <= 0){
		layer.alert('请选择休假类型！', 0, '友情提示');
		return false;
	}
	
	//是否输入休假原因
	if($("#txtXJSQ_XJYY").val()==""){
		layer.alert('请输入休假原因！', 0, '友情提示',function() {
			$("#txtXJSQ_XJYY").focus();
			layer.close(layer.index);
		});
		return false;
	}
	
	//是否选择审批人
	if($("#selectXJSQ_XYSP").val()=="000"){
		layer.alert('请选择审批人！', 0, '友情提示',function() {
			$("#selectXJSQ_XYSP").focus();
			layer.close(layer.index);
		});
		return false;
	}
	return true;
}

function makeBeanSaveIn(strXJSQ_SQID,strXJSQ_XJLX,strXJSQ_SQR,strXJSQ_SQSJ,strXJSQ_KSSJ
		,strXJSQ_JSSJ,strXJSQ_XJTS,strXJSQ_XJZT,strXJSQ_SFWJ,strXJSQ_XYSP,strXJSQ_XJYY) {
	this.XJSQ_SQID = strXJSQ_SQID;
	this.XJSQ_XJLX = strXJSQ_XJLX;
	this.XJSQ_SQR = strXJSQ_SQR;
	this.XJSQ_SQSJ = strXJSQ_SQSJ;
	this.XJSQ_KSSJ = strXJSQ_KSSJ;
	this.XJSQ_JSSJ = strXJSQ_JSSJ;
	this.XJSQ_XJTS = strXJSQ_XJTS;
	this.XJSQ_XJZT = strXJSQ_XJZT;
	this.XJSQ_SFWJ = strXJSQ_SFWJ;
	this.XJSQ_XYSP = strXJSQ_XYSP;
	this.XJSQ_XJYY = strXJSQ_XJYY;
}

function saveData(){
	//检查用户输入值
	if(checkSelect()==false){
		return;
	}
	
	var beanIn = new makeBeanSaveIn();
	beanIn.XJSQ_SQID = $("#hidXJSQ_SQID").val();
	beanIn.XJSQ_XJLX = $("input[name='chkXJSQ_XJLX']:checked").val();
	beanIn.XJSQ_SQR  = $("#hidXJSQ_SQR").val();
	beanIn.XJSQ_SQSJ = $("#txtXJSQ_SQSJ").val();
	beanIn.XJSQ_KSSJ = $("#txtXJSQ_KSSJ").val();
	beanIn.XJSQ_JSSJ = $("#txtXJSQ_JSSJ").val();
	beanIn.XJSQ_XJTS = $("#txtXJSQ_XJTS").val();
	beanIn.XJSQ_XYSP = $("#selectXJSQ_XYSP").val();
	beanIn.XJSQ_XJYY = $("#txtXJSQ_XJYY").val();
	
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1020130",
		data: {
			CMD    : "<%=Servlet1020130.CMD_SAVE%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：休假信息保存成功！', 1, 9,function(){closeW();});
				blnRet = true;
			} else if (strResult == "DATA_EXIST") {
				layer.msg('对不起：休假期限已存在,请重新输入！', 1, 8);
				blnRet = false;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：休假信息保存失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：休假信息保存出错！', 1, 0);
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
					<span>休 假 申 请</span>
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
				<td onclick="laydate()">
					<input type="text" id="txtXJSQ_SQSJ"  readonly="readonly" class="laydate-icon-default">
				</td>
				<th>请假天数：</th>
				<td class="border_right">
					<input type="text" id="txtXJSQ_XJTS" onfocus=this.blur() readonly />
				</td>
			</tr>
			<tr>
				<th>请假期限</th>
				<td onclick="laydate({choose: function(datas){setXJSQ_XJTS();}})">
					<input type="text" id="txtXJSQ_KSSJ"  onchange="setXJSQ_XJTS()" readonly="readonly" class="laydate-icon-default">
				</td>
				<th style="font-size: 24px;">～</th>
				<td onclick="laydate({choose: function(datas){setXJSQ_XJTS();}})" class="border_right">
					<input type="text" id="txtXJSQ_JSSJ" onchange="setXJSQ_XJTS()" readonly="readonly" class="laydate-icon-default">
				</td>
			</tr>
			<tr>
				<th>请假类型：</th>
				<td colspan="5" class="checkboxstyle border_right">
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="0" /><span>事假</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="1" /><span>病假</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="2" /><span>婚假</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="3" /><span>丧假</span></label><br>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="4" /><span>产假</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="5" /><span>陪产假</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="6" /><span>工伤假</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="7" /><span>带薪年假</span></label><br>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="8" /><span>调休</span></label>
					<label><input name="chkXJSQ_XJLX" type="checkbox" value="9" /><span>其它</span></label>
				</td>
			</tr>
			<tr>
				<th>申请原因：</th>
				<td valign="middle" colspan="5" class="border_right">
					<textarea style="width: 100%; border: 0" id="txtXJSQ_XJYY"></textarea>
				</td>
			</tr>
		</table>

		<table border="0" cellpadding="0" cellspacing="0" class="table_2 border_none_exbottom">
			<tr>
				<td colspan="6" class="border_bottom_none">
					<div class="leftstyle">
						<span>审批人:</span>
						<select id="selectXJSQ_XYSP">
							<option value="000" selected>选择处理人</option>
						</select>
					</div>
					<div class="rightstyle">
						<a class="botton" id="btnSave" name="btnSave" onclick="saveData()">保存</a>
						<a class="botton" onclick="closeW()">关闭</a>
					</div>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>