<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020150"%> 	
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
		<title>出差申请</title>
		<link rel="stylesheet" href="1020151.css?r=<%=radom %>" type="text/css">
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
				loadEditSelect($('#selectEditXysp'), 'SPLC_KQCC', '审批人');
				//获取父页面传递参数
				obj = window.parent.obj;
				optionFlag = obj.optionFlag;
				initData();
				if (optionFlag == "Upd") {
					$('#txtEditCcts').val(obj.CCSQ_CCTS);
					$('#txtEditKssj').val(obj.CCSQ_KSSJ);
					$('#txtEditJssj').val(obj.CCSQ_JSSJ);
					$('input[name="selectEditJtgj"][value=' + obj.CCSQ_JTGJ + ']').attr('checked',true); 
					$('#txtEditCcmdd').val(obj.CCSQ_CCMDD);
					$('#txtEditCcyy').val(obj.CCSQ_CCYY);
					$('#selectEditXysp').val(obj.CCSQ_XYSP);
				}
				//定义申请按钮点击事件
				$('#btnSave').on('click', function() {
					if (optionFlag == "Add") {
						if (funEditCheck() == false) return;
						layer.confirm('是否申请出差信息？', function() {
							layer.close(layer.index);
							if (insertData() == true) {
								iframeLayerClose();
							}
						});
					} else if (optionFlag == "Upd") {
			    		if (funEditCheck() == false) return;
			    		layer.confirm('是否重新申请信息？', function() {
			    			layer.close(layer.index);
			    			if (updData(obj.CCSQ_SQID) == true) {
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
					format: 'YYYY-MM-DD',
					istime: true,
					istoday: true,
					choose: function(datas){
						end.min = datas; //开始日选好后，重置结束日的最小日期
						end.start = datas //将结束日的初始值设定为开始日
						if ($('#txtEditJssj').val() != "") {
							dayDiffer();
						} else {
							$('#txtEditCcts').val("");
						}
					}
				};
				var end = {
					elem: '#txtEditJssj',
					format: 'YYYY-MM-DD',
					istime: true,
					istoday: true,
					choose: function(datas){
						start.max = datas; //结束日选好后，重置开始日的最大日期
						if ($('#txtEditKssj').val() != "") {
							dayDiffer();
						} else {
							$('#txtEditCcts').val("");
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
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020150",
					data: {
						CMD    : "<%=Servlet1020150.CMD_INIT_INFO%>"
					},
					complete :function(response){},
					success: function(response) {
						var result = response[0];
						if (result == "SUCCESS") {
							var initBean = response[1];
							$('#txtEditSqr').val(initBean.CCSQ_SQR);
							$('#txtEditSqrxm').val(initBean.SQRXM);
							$('#txtEditBmid').val(initBean.BMID);
							$('#txtEditBmmc').val(initBean.BMMC);
							$('#txtEditSqsj').val(initBean.CCSQ_SQSJ);
							blnRet = true;
						} else if (result == "FAILURE") {
							layer.msg('对不起：出差申请初始化数据失败！', 1, 8);
							blnRet = false;
						} else if (result == "EXCEPTION") {
							layer.msg('友情提示：出差申请初始化数据出错！', 1, 0);
							blnRet = false;
						}
					}
				});
			}
			//定义bean
			function makeBeanIn(strCCSQ_SQID, strCCSQ_JTGJ, strCCSQ_SQR,
					strCCSQ_SQSJ, strCCSQ_KSSJ, strCCSQ_JSSJ, strCCSQ_CCTS,
					strCCSQ_XYSP, strCCSQ_CCMDD, strCCSQ_CCYY) {
				this.CCSQ_SQID = strCCSQ_SQID;
				this.CCSQ_JTGJ = strCCSQ_JTGJ;
				this.CCSQ_SQR = strCCSQ_SQR;
				this.CCSQ_SQSJ = strCCSQ_SQSJ;
				this.CCSQ_KSSJ = strCCSQ_KSSJ;
				this.CCSQ_JSSJ = strCCSQ_JSSJ;
				this.CCSQ_CCTS = strCCSQ_CCTS;
				this.CCSQ_XYSP = strCCSQ_XYSP;
				this.CCSQ_CCMDD = strCCSQ_CCMDD;
				this.CCSQ_CCYY = strCCSQ_CCYY;
			}
			//计算两个日期相差的天数
			function dayDiffer() {
				var beanIn = new makeBeanIn(
					"","","","",
					$('#txtEditKssj').val(),
					$('#txtEditJssj').val(),
					"","","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020150",
					data: {
						CMD    : "<%=Servlet1020150.CMD_DAY_DIFFER%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							var dayDiffer = response[1];
							$('#txtEditCcts').val(dayDiffer);
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：计算出差天数失败！', 1, 8);
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：计算出差天数出错！', 1, 0);
						}
					}
				});
			}
			//新增方法
			function insertData() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					"",
					$('input[name="selectEditJtgj"]:checked').val(),
					$('#txtEditSqr').val(),
					$('#txtEditSqsj').val(),
					$('#txtEditKssj').val(),
					$('#txtEditJssj').val(),
					$('#txtEditCcts').val(),
					$('#selectEditXysp').val(),
					$('#txtEditCcmdd').val(),
					$('#txtEditCcyy').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020150",
					data: {
						CMD    : "<%=Servlet1020150.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增出差申请信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增出差申请信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增出差申请信息出错！', 1, 0);
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
					$('input[name="selectEditJtgj"]:checked').val(),
					$('#txtEditSqr').val(),
					$('#txtEditSqsj').val(),
					$('#txtEditKssj').val(),
					$('#txtEditJssj').val(),
					$('#txtEditCcts').val(),
					$('#selectEditXysp').val(),
					$('#txtEditCcmdd').val(),
					$('#txtEditCcyy').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020150",
					data: {
						CMD    : "<%=Servlet1020150.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改出差申请信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改出差申请信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改出差申请信息出错！', 1, 0);
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
						layer.alert('请输入出差时间！', 0, '友情提示', function() {
							$('#txtEditKssj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditJssj').val() == "") {
						layer.alert('请输入出差时间！', 0, '友情提示', function() {
							$('#txtEditJssj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('input:radio[name="selectEditJtgj"]:checked').val() == null) {
						layer.alert('请选择交通工具！', 0, '友情提示', function() {
							$('input[name="selectEditJtgj"]').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditCcmdd').val() == "") {
						layer.alert('请输入出差目的地！', 0, '友情提示', function() {
							$('#txtEditCcmdd').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditCcyy').val() == "") {
						layer.alert('请输入出差内容！', 0, '友情提示', function() {
							$('#txtEditCcyy').focus();
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
						<span>出 差 申 请</span>
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
					<th>出差天数：</th>
					<td class="border_right">
						<input type="text" id="txtEditCcts" onfocus=this.blur() readonly />
					</td>
				</tr>
				<tr>
					<th>出差时间：</th>
					<td>
						<input type="text" id="txtEditKssj"  readonly="readonly" class="laydate-icon-default">
					</td>
					<th style="font-size: 24px;">～</th>
					<td class="border_right">
						<input type="text" id="txtEditJssj" readonly="readonly" class="laydate-icon-default">
					</td>
				</tr>
				<tr>
					<th>交通工具：</th>
					<td colspan="3" class="checkboxstyle border_right">
						<label><input name="selectEditJtgj" type="radio" value="0" /><span>汽车</span></label>
						<label><input name="selectEditJtgj" type="radio" value="1" /><span>火车</span></label>
						<label><input name="selectEditJtgj" type="radio" value="2" /><span>飞机</span></label>
						<label><input name="selectEditJtgj" type="radio" value="3" /><span>其它</span></label>
					</td>
				</tr>
				<tr>
					<th>出差目的地：</th>
					<td colspan="3" class="checkboxstyle border_right">
						<input type="text" id="txtEditCcmdd" />
					</td>
				</tr>
				<tr>
					<th>出差内容：</th>
					<td valign="middle" colspan="3" class="border_right">
						<textarea id="txtEditCcyy" style="width: 100%; border: 0"></textarea>
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