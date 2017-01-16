<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1010000.Servlet1010150"%> 
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
		<title>员工事件</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmGrid.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmPaginator.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/tablesize.css?r=<%=radom %>" type="text/css">
		<!--表格样式End  -->
		<!--按钮样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/css/control/buttonStyle.css?r=<%=radom %>" type="text/css">
		<!--按钮样式End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
		<!--表格脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmGrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmPaginator.js"></script>
		<!--表格脚本End  -->
		<!--插件脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<!--插件脚本End  -->
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'所在部门', name:'BMXX_BMMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'员工', name:'YGXM' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'事件类型', name:'SJLX' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'事件描述', name:'YGSJ_SJMS' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'事件时间', name:'YGSJ_FSSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'奖惩金额', name:'YGSJ_JCJE' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'记录人', name:'JLRXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'记录时间', name:'YGSJ_CJSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  }
				];
				//计算表格高度
				intheight = document.documentElement.clientHeight - $('#editRegion').height() - $('#selectRegion').height() - 80; 
				if (intheight < 100) {
					intheight = 100;
				}
				//初始化表格
	   			mmg = $('.mmg').mmGrid({
	   				height: intheight,
	   				cols: cols,
					url: '<%=basePath%>/Servlet1010150',
					method: 'post',
					params:{CMD : "<%=Servlet1010150.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'YGSJ_SJID',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: true,
					indexCol: true,
					indexColWidth: 35,
					fullWidthRows: true,
					autoLoad: false,
					nowrap: true,
					plugins: [
						$('#pg').mmPaginator({
							limit:30
						})
					]
				});
				//定义表格触发事件
				mmg.on('loadSuccess',function(e, data) {
					//设置按钮状态
					setButtonStatus('2');
				}).on('click', ':checkbox', function() {//选择checkbox
					if (this.checked == true) {
						var arrList = mmg.selectedRows();
						if (arrList.length > 0) {
							$('#selectEditYgxm').val(arrList[0].YGSJ_YGID);
							$('#selectEditSjlx').val(arrList[0].YGSJ_SJLX);
							optionDom();
							$('#txtEditSjms').val(arrList[0].YGSJ_SJMS);
							$('#txtEditSjfssj').val(arrList[0].YGSJ_FSSJ);
							$('#txtEditJcje').val(arrList[0].YGSJ_JCJE);
							setButtonStatus('4');
						}
					} else {
						$('#selectEditYgxm').val("000");
						$('#selectEditSjlx').val("000");
						optionDom();
						$('#txtEditSjms').val("");
						$('#txtEditSjfssj').val("");
						$('#txtEditJcje').val("");
						setButtonStatus('2');//未选中行,则只有新增按钮可用
					}
				}).on('click', 'tr', function(e) { //点击行;
					if (mmg.rowsLength() <= 0) return; //无数据,不进行操作
					var rowIndex = e.target.parentNode.rowIndex;
					if (typeof(rowIndex) == "undefined") {
						rowIndex = e.target.parentNode.parentNode.rowIndex;
					}
					var arrList = mmg.selectedRows();
					if (arrList.length > 0) {
						$('#selectEditYgxm').val(arrList[0].YGSJ_YGID);
						$('#selectEditSjlx').val(arrList[0].YGSJ_SJLX);
						optionDom();
						$('#txtEditSjms').val(arrList[0].YGSJ_SJMS);
						$('#txtEditSjfssj').val(arrList[0].YGSJ_FSSJ);
						$('#txtEditJcje').val(arrList[0].YGSJ_JCJE);
						setButtonStatus('4');
					} else {
						$('#selectEditYgxm').val("000");
						$('#selectEditSjlx').val("000");
						optionDom();
						$('#txtEditSjms').val("");
						$('#txtEditSjfssj').val("");
						$('#txtEditJcje').val("");
						setButtonStatus('2');//未选中行,则只有新增按钮可用
					}
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				//定义保存按钮点击事件
				$('#btnSave').on('click', function() {
					if (optionFlag == "Add") {
						if (funEditCheck() == false) return;
						layer.confirm('是否增加事件信息？', function() {
							layer.close(layer.index);
							if (insertData() == true) {
								//重新查询数据
								loadGridByBean();
								setButtonStatus('2');
								mmg.deselect('all');
								optionDom();
							}
						});
					}else if (optionFlag == "Upd") {
			    		var arrList = mmg.selectedRows();
			    		if (arrList.length <= 0) {
			    			layer.alert('请选择要修改的数据行！', 0, '友情提示');
			    			return;
			    		}
			    		if (funEditCheck() == false) return;
			    		layer.confirm('是否修改事件信息？', function() {
			    			layer.close(layer.index);
			    			if (updateData(arrList[0].YGSJ_SJID) == true) {
								//重新查询数据
								loadGridByBean();
								setButtonStatus('2');
								mmg.deselect('all');
								optionDom();
							}
			    		});
					}
				});
				//定义取消按钮点击事件
				$('#btnCancel').on('click', function() {
					setButtonStatus('2');
					mmg.deselect('all');
					optionDom();
				});
				//定义删除按钮点击事件
				$('#btnDel').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要删除的数据行！', 0, '友情提示');
						return;
					}
					layer.confirm('是否删除事件信息？', function() {
						layer.close(layer.index);
						if (deleteData(arrList[0].YGSJ_SJID) == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('2');
							mmg.deselect('all');
							optionDom();
						}
					});
				});
			    //初始化下拉列表
				loadSearchSelect($('#selectBmmc'), 'TYPE_BMMC', '部门名称');
			    $('#selectBmmc').change(function() {
					loadSearchSelect($('#selectYgxm'), 'TYPE_BMYG-' + $('#selectBmmc').val(), '员工姓名');
			    });
			    loadEditSelect($('#selectEditBmmc'), 'TYPE_BMMC', '部门名称');
			    $('#selectEditBmmc').change(function() {
					loadSearchSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + $('#selectEditBmmc').val(), '员工姓名');
			    });
				//初始化标签属性及方法
				$('#txtEditJcjeTitl').hide();
				$('#txtEditJcje').hide();
				$('#selectEditSjlx').on('change', function() {
					optionDom();
				});
				if (getBaseData() == true) {
					$('#editRegion').show();
				} else {
					$('#editRegion').hide();
				}
				//页面初始化加载数据
				loadGridByBean();
			});
			//获取登陆用户基础数据
			function getBaseData() {
				var blnRet = false;
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010150",
					data: {
						CMD    : "<%=Servlet1010150.CMD_GET_BASE%>"
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							blnRet = response[1];
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：加载数据异常，请联系系统管理员！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//控制标签显示/隐藏
			function optionDom() {
				if ($('#selectEditSjlx').val() == '0' || $('#selectEditSjlx').val() == '1') {
					$('#txtEditJcjeTitl').show();
					$('#txtEditJcje').show();
				} else {
					$('#txtEditJcjeTitl').hide();
					$('#txtEditJcje').hide();
				}
			}
			//新增按钮点击方法
			function btn_Add() {
				setButtonStatus('31');
				optionFlag = "Add";
			}
			//修改按钮点击方法
			function btn_Upd() {
				setButtonStatus('32');
				optionFlag = "Upd";
			}
			//定义编辑bean
			function makeBeanInSearch(strBMXX_BMID, strYGSJ_YGID, strYGSJ_SJLX) {
				this.BMXX_BMID = strBMXX_BMID;
				this.YGSJ_YGID = strYGSJ_YGID;
			    this.YGSJ_SJLX = strYGSJ_SJLX;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanInSearch(
					$('#selectBmmc').val(),
					$('#selectYgxm').val(),
					$('#selectSjlx').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//定义编辑bean
			function makeBeanInEdit(strYGSJ_SJID, strYGSJ_YGID, strYGSJ_SJLX,
					strYGSJ_SJMS, strYGSJ_FSSJ, strYGSJ_JCJE) {
				this.YGSJ_SJID = strYGSJ_SJID;
				this.YGSJ_YGID = strYGSJ_YGID;
			    this.YGSJ_SJLX = strYGSJ_SJLX;
			    this.YGSJ_SJMS = strYGSJ_SJMS;
			    this.YGSJ_FSSJ = strYGSJ_FSSJ;
			    this.YGSJ_JCJE = strYGSJ_JCJE;
			}
			//新增数据
			function insertData() {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					"",
					$('#selectEditYgxm').val(),
					$('#selectEditSjlx').val(),
					$('#txtEditSjms').val(),
					$('#txtEditSjfssj').val(),
					$('#txtEditJcje').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010150",
					data: {
						CMD    : "<%=Servlet1010150.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增事件信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增事件信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增事件信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改数据
			function updateData(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					dataId,
					$('#selectEditYgxm').val(),
					$('#selectEditSjlx').val(),
					$('#txtEditSjms').val(),
					$('#txtEditSjfssj').val(),
					$('#txtEditJcje').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010150",
					data: {
						CMD    : "<%=Servlet1010150.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改事件信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改事件信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改事件信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//删除数据
			function deleteData(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					dataId,"","","","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010150",
					data: {
						CMD    : "<%=Servlet1010150.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除事件信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除事件信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除事件信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//设置按钮状态
			function setButtonStatus(strFlag) {
				if (strFlag == "1") {//初始状态
					$('#btnAdd').attr("disabled", "disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
				} else if (strFlag == "2") {//查询后/返回
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditSjlx').attr("disabled","disabled");
					$('#txtEditSjms').attr("disabled","disabled");
					$('#txtEditSjfssj').attr("disabled","disabled");
					$('#txtEditJcje').attr("disabled","disabled");
					$('#selectEditYgxm').val("000");
					$('#selectEditSjlx').val("000");
					$('#txtEditSjms').val("");
					$('#txtEditSjfssj').val("");
					$('#txtEditJcje').val("");
				} else if (strFlag.substring(0, 1) == "3") {
					//新增/修改
					$('#btnAdd').attr("disabled","disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').removeAttr("disabled");
					$('#btnCancel').removeAttr("disabled");
					if (strFlag == "31") {//新增
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditSjlx').removeAttr("disabled");
						$('#txtEditSjms').removeAttr("disabled");
						$('#txtEditSjfssj').removeAttr("disabled");
						$('#txtEditJcje').removeAttr("disabled");
						$('#selectEditYgxm').val("000");
						$('#selectEditSjlx').val("000");
						$('#txtEditSjms').val("");
						$('#txtEditSjfssj').val("");
						$('#txtEditJcje').val("");
						$('#selectEditYgxm').focus();
					} else if (strFlag == "32") {//修改
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditSjlx').removeAttr("disabled");
						$('#txtEditSjms').removeAttr("disabled");
						$('#txtEditSjfssj').removeAttr("disabled");
						$('#txtEditJcje').removeAttr("disabled");
						$('#selectEditYgxm').focus();
					} else if (strFlag == "32") {//删除
						$('#selectEditYgxm').attr("disabled","disabled");
						$('#selectEditSjlx').attr("disabled","disabled");
						$('#txtEditSjms').attr("disabled","disabled");
						$('#txtEditSjfssj').attr("disabled","disabled");
						$('#txtEditJcje').attr("disabled","disabled");
					}
				} else if (strFlag == "4") {//选中行
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditSjlx').attr("disabled","disabled");
					$('#txtEditSjms').attr("disabled","disabled");
					$('#txtEditSjfssj').attr("disabled","disabled");
					$('#txtEditJcje').attr("disabled","disabled");
				}
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#selectEditYgxm').val() == "000") {
						layer.alert('请选择员工！', 0, '友情提示', function() {
							$('#selectEditYgxm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditSjlx').val() == "000") {
						layer.alert('请选择事件类型！', 0, '友情提示', function() {
							$('#selectEditSjlx').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditSjms').val() == "") {
						layer.alert('请输入事件描述！', 0, '友情提示', function() {
							$('#txtEditSjms').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditSjfssj').val() == "") {
						layer.alert('请输入事件发生时间！', 0, '友情提示', function() {
							$('#txtEditSjfssj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditSjlx').val() == "0" || $('#selectEditSjlx').val() == "1") {
						if ($('#txtEditJcje').val() == "") {
							layer.alert('请输入奖惩金额！', 0, '友情提示', function() {
								$('#txtEditJcje').focus();
								layer.close(layer.index);
							});
							return false;
						}
					}
				}
				return true;
			}
		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">所在部门</th>
					<td><select id="selectBmmc" style="width: 100px"></select></td>
					<th style="width:100px">员工</th>
					<td>
						<select id="selectYgxm" style="width: 100px">
							<option value="000">所有</option>
						</select>
					</td>
					<th style="width:100px">事件类型</th>
					<td>
						<select id="selectSjlx" style="width: 100px">
							<option value="000">所有</option>
							<option value="0">奖励</option>
							<option value="1">惩罚</option>
							<option value="2">加班</option>
							<option value="3">请假</option>
						</select>
					</td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch" class="btn btn-primary btn-sm" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion">
			<legend>编辑</legend>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">所在部门</th>
					<td><select id="selectEditBmmc" style="width: 100px"></select></td>
					<th style="width:100px">员工</th>
					<td>
						<select id="selectEditYgxm" style="width: 100px">
							<option value="000">请选择</option>
						</select>
					</td>
					<th style="width:100px">事件类型</th>
					<td>
						<select id="selectEditSjlx" style="width: 100px">
							<option value="000">请选择</option>
							<option value="0">奖励</option>
							<option value="1">惩罚</option>
							<option value="2">加班</option>
							<option value="3">请假</option>
						</select>
					</td>
					<th style="width:100px">发生时间</th>
					<td><input id="txtEditSjfssj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></td>
					<th id="txtEditJcjeTitl" style="width:100px">奖惩金额</th>
					<td><input id="txtEditJcje" name="奖惩金额" maxlength="100" /></td>
					<th style="width:100px">事件描述</th>
					<td><input id="txtEditSjms" name="事件描述" maxlength="100" /></td>
				</tr>
			</table>
			<div id="buttonCanvas" class="gToolbar gTbrCenter ">
				<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" class="btn btn-primary btn-sm" />
				<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" class="btn btn-primary btn-sm" /> 
				<input type="button" value="删除" id="btnDel" name="btnDel" class="btn btn-primary btn-sm" /> 
				<input type="button" value="保存" id="btnSave" name="btnSave" class="btn btn-primary btn-sm" /> 
				<input type="button" value="取消" id="btnCancel" name="btnCancel" class="btn btn-primary btn-sm" /> 
			</div>
		</fieldset>
	</body>
</html>