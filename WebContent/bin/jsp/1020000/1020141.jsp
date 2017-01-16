<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020140"%>
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
		<title>加班申请</title>
		<link rel="stylesheet" href="1020141.css?r=<%=radom %>" type="text/css">
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
		<script type="text/javascript">
			var obj = new Object();
			var optionFlag;//定义操作标识
			$(document).ready(function() {
				//初始化下拉列表
				loadEditSelect($('#selectEditXysp'), 'SPLC_KQJB', '审批人');
				//获取父页面传递参数
				obj = window.parent.obj;
				optionFlag = obj.optionFlag;
				initData();
				if (optionFlag == "Upd") {
					$('#txtEditJbxss').val(obj.JBSQ_JBXSS);
					$('#txtEditKssj').val(obj.JBSQ_KSSJ);
					$('#txtEditJssj').val(obj.JBSQ_JSSJ);
					$('input[name="selectEditJblx"][value=' + obj.JBSQ_JBLX + ']').attr('checked',true);
					$('#txtEditJbyy').val(obj.JBSQ_JBYY);
					$('#selectEditXysp').val(obj.JBSQ_XYSP);
				}
				//定义申请按钮点击事件
				$('#btnSave').on('click', function() {
					if (optionFlag == "Add") {
						if (funEditCheck() == false) return;
						layer.confirm('是否申请加班信息？', function() {
							layer.close(layer.index);
							if (insertData() == true) {
								iframeLayerClose();
							}
						});
					} else if (optionFlag == "Upd") {
			    		if (funEditCheck() == false) return;
			    		layer.confirm('是否重新申请信息？', function() {
			    			layer.close(layer.index);
			    			if (updData(obj.JBSQ_SQID) == true) {
			    				iframeLayerClose();
							}
			    		});
					}
				});
				//定义取消按钮点击事件
				$('#btnCancel').on('click', function() {
					iframeLayerClose();
				});
				/* 日期控件设置Start */
				var start = {
					elem: '#txtEditKssj',
					format: 'YYYY-MM-DD hh:mm:ss',
					istime: true,
					istoday: true,
					choose: function(datas){
						end.min = datas; //开始日选好后，重置结束日的最小日期
						end.start = datas //将结束日的初始值设定为开始日
						if ($('#txtEditJssj').val() != "") {
							hourDiffer();
						} else {
							$('#txtEditJbxss').val("");
						}
					}
				};
				var end = {
					elem: '#txtEditJssj',
					format: 'YYYY-MM-DD hh:mm:ss',
					istime: true,
					istoday: true,
					choose: function(datas){
						start.max = datas; //结束日选好后，重置开始日的最大日期
						if ($('#txtEditKssj').val() != "") {
							hourDiffer();
						} else {
							$('#txtEditJbxss').val("");
						}
					}
				};
				laydate(start);
				laydate(end);
				laydate.skin("default");
				/* 日期控件设置End */
			});
			/* 初始化基础信息（申请人，部门，申请时间） */
			function initData(){
				var blnRet = false;
				$.ajax({
					async : false,
					type : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020140",
					data: {
						CMD    : "<%=Servlet1020140.CMD_INIT_INFO%>"
					},
					complete :function(response){},
					success: function(response) {
						var result = response[0];
						if (result == "SUCCESS") {
							var initBean = response[1];
							$('#txtEditSqr').val(initBean.JBSQ_SQR);
							$('#txtEditSqrxm').val(initBean.SQRXM);
							$('#txtEditBmid').val(initBean.BMID);
							$('#txtEditBmmc').val(initBean.BMMC);
							$('#txtEditSqsj').val(initBean.JBSQ_SQSJ);
							blnRet = true;
						} else if (result == "FAILURE") {
							layer.msg('对不起：加班申请初始化数据失败！', 1, 8);
							blnRet = false;
						} else if (result == "EXCEPTION") {
							layer.msg('友情提示：加班申请初始化数据出错！', 1, 0);
							blnRet = false;
						}
					}
				});
			}
			//定义bean
			function makeBeanIn(strJBSQ_SQID, strJBSQ_JBLX, strJBSQ_SQR,
					strJBSQ_SQSJ, strJBSQ_KSSJ, strJBSQ_JSSJ, strJBSQ_JBXSS,
					strJBSQ_XYSP, strJBSQ_JBYY) {
				this.JBSQ_SQID = strJBSQ_SQID;
				this.JBSQ_JBLX = strJBSQ_JBLX;
				this.JBSQ_SQR = strJBSQ_SQR;
				this.JBSQ_SQSJ = strJBSQ_SQSJ;
				this.JBSQ_KSSJ = strJBSQ_KSSJ;
				this.JBSQ_JSSJ = strJBSQ_JSSJ;
				this.JBSQ_JBXSS = strJBSQ_JBXSS;
				this.JBSQ_XYSP = strJBSQ_XYSP;
				this.JBSQ_JBYY = strJBSQ_JBYY;
			}
			//计算两个日期相差的小时数
			function hourDiffer() {
				var beanIn = new makeBeanIn(
					"","","","",
					$('#txtEditKssj').val(),
					$('#txtEditJssj').val(),
					"","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020140",
					data: {
						CMD    : "<%=Servlet1020140.CMD_HOUR_DIFFER%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							var hourDiffer = response[1];
							$('#txtEditJbxss').val(hourDiffer);
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：计算加班小时数失败！', 1, 8);
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：计算加班小时数出错！', 1, 0);
						}
					}
				});
			}
			//新增方法
			function insertData() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					"",
					$('input[name="selectEditJblx"]:checked').val(),
					$('#txtEditSqr').val(),
					$('#txtEditSqsj').val(),
					$('#txtEditKssj').val(),
					$('#txtEditJssj').val(),
					$('#txtEditJbxss').val(),
					$('#selectEditXysp').val(),
					$('#txtEditJbyy').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020140",
					data: {
						CMD    : "<%=Servlet1020140.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增加班申请信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增加班申请信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增加班申请信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改方法
			function updData(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					dataId,
					$('input[name="selectEditJblx"]:checked').val(),
					$('#txtEditSqr').val(),
					$('#txtEditSqsj').val(),
					$('#txtEditKssj').val(),
					$('#txtEditJssj').val(),
					$('#txtEditJbxss').val(),
					$('#selectEditXysp').val(),
					$('#txtEditJbyy').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020140",
					data: {
						CMD    : "<%=Servlet1020140.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改加班申请信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改加班申请信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改加班申请信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#txtEditKssj').val() == "") {
						layer.alert('请输入加班时间！', 0, '友情提示', function() {
							$('#txtEditKssj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditJssj').val() == "") {
						layer.alert('请输入加班时间！', 0, '友情提示', function() {
							$('#txtEditJssj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('input:radio[name="selectEditJblx"]:checked').val() == null) {
						layer.alert('请选择加班类型！', 0, '友情提示', function() {
							$('input[name="selectEditJblx"]').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditJbyy').val() == "") {
						layer.alert('请输入加班内容！', 0, '友情提示', function() {
							$('#txtEditJbyy').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditXysp').val() == "000") {
						layer.alert('请选择审批人！', 0, '友情提示', function() {
							$('#selectEditXysp').focus();
							layer.close(layer.index);
						});
						return false;
					}
				}
				return true;
			}
		</script>
	</head>
	<body>
		<div class="table_reset" id="editRegion">
			<table id="detailCanvas" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4" class="title_ border_left_none">
						<span>加 班 申 请</span>
					</td>
				</tr>
				<tr>
					<th>申请人：</th>
					<td>
						<input type="hidden" id="txtEditSqr" />
						<input type="text" id="txtEditSqrxm" onfocus=this.blur() readonly />
					</td>
					<th>部门：</th>
					<td class="border_right">
						<input type="hidden" id="txtEditBmid" />
						<input type="text" id="txtEditBmmc" onfocus=this.blur() readonly />
					</td>
				</tr>
	 			<tr>
	 				<th>申请日期：</th>
					<td>
						<input type="text" id="txtEditSqsj"  readonly="readonly" class="laydate-icon-default">
					</td>
					<th>加班数(小时)：</th>
					<td class="border_right">
						<input type="text" id="txtEditJbxss" onfocus=this.blur() readonly />
					</td>
				</tr>
				<tr>
					<th>加班时间：</th>
					<td>
						<input type="text" id="txtEditKssj"  readonly="readonly" class="laydate-icon-default">
					</td>
					<th style="font-size: 24px;">～</th>
					<td class="border_right">
						<input type="text" id="txtEditJssj" readonly="readonly" class="laydate-icon-default">
					</td>
				</tr>
				<tr>
					<th>加班类型：</th>
					<td colspan="3" class="checkboxstyle border_right">
						<label><input name="selectEditJblx" type="radio" value="0" /><span>工作日</span></label>
						<label><input name="selectEditJblx" type="radio" value="1" /><span>周末</span></label>
						<label><input name="selectEditJblx" type="radio" value="2" /><span>节假日</span></label>
					</td>
				</tr>
				<tr>
					<th>加班内容：</th>
					<td valign="middle" colspan="3" class="border_right">
						<textarea id="txtEditJbyy" style="width: 100%; border: 0"></textarea>
					</td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" class="table_2 border_none_exbottom">
				<tr>
					<td colspan="4" class="border_bottom_none">
						<div class="leftstyle">
							<span>审批人:</span>
							<select id="selectEditXysp">
								<option value="000" selected>选择处理人</option>
							</select>
						</div>
						<div class="rightstyle">
							<a class="botton" id="btnSave" name="btnSave">申请</a>
							<a class="botton" id="btnCancel" name="btnCancel">关闭</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>