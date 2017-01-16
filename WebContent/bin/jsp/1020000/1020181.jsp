<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020180"%> 	
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
		<title>加班审批</title>
		<link rel="stylesheet" href="1020181.css?r=<%=radom %>" type="text/css">
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				//获取父页面传递参数
				var dataId = getQueryString("dataId");
				initData(dataId);
				//初始化下拉列表
				var strSprId = $("#txtEditSqr").val();
				loadEditSelect($('#selectEditXysp'), 'SPLC_KQJB-' + strSprId, '下一审批人');
				if ($('#selectEditXysp option').length>1) {//有下一审批人,继续判断是否可终结
					//取得当前用户是否可以终结
					if (getSPRKFZJ("SPLC_KQJB", $("#txtEditSqr").val()) == "0") {//不可终结
						btnFinish.className="bottonDisable";
						btnFinish.onclick="";
						btnRejectFinish.className="bottonDisable";
						btnRejectFinish.onclick="";
					}
				}
				//定义取消按钮点击事件
				$('#btnCancel').on('click', function() {
					iframeLayerClose();
				});
			});
			/* 初始化基础信息（申请人，部门，申请时间） */
			function initData(dataId){
				var blnRet = false;
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020180",
					data: {
						CMD		: "<%=Servlet1020180.CMD_INIT_INFO%>",
						DATAID	: dataId
					},
					complete :function(response){},
					success: function(response) {
						var result = response[0];
						if (result == "SUCCESS") {
							var initBean = response[1];
							$('#txtEditSqid').val(initBean.JBSQ_SQID);
							$('#txtEditSqr').val(initBean.JBSQ_SQR);
							$('#txtEditSqrxm').val(initBean.SQRXM);
							$('#txtEditBmid').val(initBean.BMID);
							$('#txtEditBmmc').val(initBean.BMMC);
							$('#txtEditSqsj').val(initBean.JBSQ_SQSJ);
							$('#txtEditJbxss').val(initBean.JBSQ_JBXSS);
							$('#txtEditKssj').val(initBean.JBSQ_KSSJ);
							$('#txtEditJssj').val(initBean.JBSQ_JSSJ);
							$('#txtEditJblx').val(initBean.JBSQ_JBLX);
							$('#txtEditLxmc').val(initBean.JBLX);
							$('#txtEditSpzt').val(initBean.JBZT);
							$('#txtEditJbyy').val(initBean.JBSQ_JBYY);
							blnRet = true;
						} else if (result == "FAILURE") {
							layer.msg('对不起：获取加班申请详情失败！', 1, 8);
							blnRet = false;
						} else if (result == "EXCEPTION") {
							layer.msg('友情提示：获取加班申请详情出错！', 1, 0);
							blnRet = false;
						}
					}
				});
			}
			//定义bean
			function makeBeanIn(strJBSQ_SQID, strJBSQ_XYSP,
					strJBSQ_JBZT, strJBMX_CZNR) {
				this.JBSQ_SQID = strJBSQ_SQID;
				this.JBSQ_XYSP = strJBSQ_XYSP;
				this.JBSQ_JBZT = strJBSQ_JBZT;
				this.JBMX_CZNR = strJBMX_CZNR;
			}
			//保存审批
			function saveData(spzt) {
				if (spzt == "2") {//流转
					//是否选择审批人
					if ($("#selectEditXysp").val() == "000") {
						layer.alert('请选择审批人！', 0, '友情提示',function() {
							$("#selectEditXysp").focus();
							layer.close(layer.index);
						});
						return false;
					}
				} else if (spzt == "4" || spzt == "5") {//驳回重审与驳回完结
					//是否输入审批意见
					if ($("#txtEditSpyj").val() == "") {
						layer.alert('请输入审批意见！', 0, '友情提示',function() {
							$("#txtEditSpyj").focus();
							layer.close(layer.index);
						});
						return false;
					}
				}
				var beanIn = new makeBeanIn(
					$('#txtEditSqid').val(),
					$('#selectEditXysp').val(),
					spzt,
					$('#txtEditSpyj').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020180",
					data: {
						CMD    : "<%=Servlet1020180.CMD_SAVE%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							if (spzt=="2") {//流转
								layer.msg('恭喜：批准流转成功！', 1, 9, function(){
									iframeLayerClose();
								});
							} else if (spzt == "3") {//流转
								layer.msg('恭喜：批准完结成功！', 1, 9, function(){
									iframeLayerClose();
								});
							} else if (spzt=="4") {//驳回
								layer.msg('恭喜：驳回成功！', 1, 9, function(){
									iframeLayerClose();
								});
							}
						}  else if (strResult == "FAILURE") {
							layer.msg('对不起：审批失败！', 1, 8);
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：审批出错！', 1, 0);
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
						<span>加 班 审 批</span>
					</td>
				</tr>
				<tr>
					<th>申请人：</th>
					<td>
						<input type="hidden" id="txtEditSqid" />
						<input type="hidden" id="txtEditSqr" />
						<input type="text" id="txtEditSqrxm" onfocus=this.blur() readonly="readonly" />
					</td>
					<th>部门：</th>
					<td class="border_right">
						<input type="hidden" id="txtEditBmid" />
						<input type="text" id="txtEditBmmc" onfocus=this.blur() readonly="readonly" />
					</td>
				</tr>
	 			<tr>
	 				<th>申请日期：</th>
					<td>
						<input type="text" id="txtEditSqsj" onfocus=this.blur() readonly="readonly" />
					</td>
					<th>加班数(小时)：</th>
					<td class="border_right">
						<input type="text" id="txtEditJbxss" onfocus=this.blur() readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>加班时间：</th>
					<td>
						<input type="text" id="txtEditKssj" onfocus=this.blur() readonly="readonly" />
					</td>
					<th style="font-size: 24px;">～</th>
					<td class="border_right">
						<input type="text" id="txtEditJssj" onfocus=this.blur() readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>加班类型：</th>
					<td class="border_right">
						<input type="hidden" id="txtEditJblx" />
						<input type="text" id="txtEditLxmc" onfocus=this.blur() readonly="readonly" />
					</td>
					<th>状态：</th>
					<td class="border_right">
						<input type="text" id="txtEditSpzt" onfocus=this.blur() readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>加班内容：</th>
					<td valign="middle" colspan="5" class="border_right">
						<textarea id="txtEditJbyy" style="width: 100%; border: 0" onfocus=this.blur() readonly="readonly"></textarea>
					</td>
				</tr>
				<tr>
					<th>审批意见：</th>
					<td valign="middle" colspan="5" class="border_right">
						<textarea style="width: 100%; border: 0" id="txtEditSpyj"></textarea>
					</td>
				</tr>
				<tr>
					<th>下一审批人:</th>
					<td colspan="2" style="text-align:left;">
						<select id="selectEditXysp">
							<option value="000" selected>选择审批人</option>
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
							<a class="botton" id="btnReject" onclick="saveData(4)">驳回重审</a>
							<a class="botton" id="btnRejectFinish" onclick="saveData(5)">驳回完结</a>
							<a class="botton" id="btnCancel">取消</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>