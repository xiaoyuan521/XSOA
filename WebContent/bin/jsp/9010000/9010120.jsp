<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet9010000.Servlet9010120"%> 
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
		<title>角色管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="<%=basePath%>/bin/css/control/buttonStyle.css?r=<%=radom %>" type="text/css">
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmGrid.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmPaginator.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/tablesize.css?r=<%=radom %>" type="text/css">
		<!--表格样式End  -->
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
		<!--插件脚本End  -->
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'角色编号', name:'YHJS_JSID' ,width:50, sortable:true, align:'center',lockDisplay: true },
					{ title:'角色名称', name:'YHJS_JSMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'所属部门', name:'BMXX_BMMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'角色描述', name:'YHJS_JSMS' ,width:150, sortable:true, align:'center',lockDisplay: true  }
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
					url: '<%=basePath%>/Servlet9010120',
					method: 'post',
					params:{CMD : "<%=Servlet9010120.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'YHJS_JSID',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: true,
					indexCol: true,
					indexColWidth: 35,
					fullWidthRows: true,
					autoLoad: false,
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
							$('#txtEditCode').val(arrList[0].YHJS_JSID);
							$('#txtEditName').val(arrList[0].YHJS_JSMC);
							$('#selectEditBmmc').val(arrList[0].YHJS_BMID);
							$('#txtEditMS').val(arrList[0].YHJS_JSMS);
							setButtonStatus('4');   
						}
					} else {
						$('#txtEditCode').val("");
						$('#txtEditName').val("");
						$('#selectEditBmmc').val("000");
						$('#txtEditMS').val("");
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
						$('#txtEditCode').val(arrList[0].YHJS_JSID);
						$('#txtEditName').val(arrList[0].YHJS_JSMC);
						$('#selectEditBmmc').val(arrList[0].YHJS_BMID);
						$('#txtEditMS').val(arrList[0].YHJS_JSMS);
						setButtonStatus('4');   
					} else {
						$('#txtEditCode').val("");
						$('#txtEditName').val("");
						$('#selectEditBmmc').val("000");
						$('#txtEditMS').val("");
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
						layer.confirm('是否增加角色信息？', function() {
							layer.close(layer.index);
							if (insertRole() == true) {
								//重新查询数据
								loadGridByBean();
								setButtonStatus('2');
								mmg.deselect('all');
							}
						});
					}else if (optionFlag == "Upd") {
			    		var arrList = mmg.selectedRows();
			    		if (arrList.length <= 0) {
			    			layer.alert('请选择要修改的数据行！', 0, '友情提示');
			    			return;
			    		}
			    		if (funEditCheck() == false) return;
			    		layer.confirm('是否修改角色信息？', function() {
			    			layer.close(layer.index);
			    			if (updateRole() == true) {
								//重新查询数据
								loadGridByBean();
								setButtonStatus('2');
								mmg.deselect('all');
							}
			    		});
					}
				});
				//定义取消按钮点击事件
				$('#btnCancel').on('click', function() {
					setButtonStatus('2');
					mmg.deselect('all');
				});
				//定义删除按钮点击事件
				$('#btnDel').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要删除的数据行！', 0, '友情提示');
						return;
					}
					layer.confirm('是否删除角色信息？', function() {
						layer.close(layer.index);
						if (deleteRole() == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('2');
							mmg.deselect('all');
						}
					});
				});
				//页面初始化加载数据
				loadGridByBean();
			    //初始化下拉列表
				loadSearchSelect($('#selectBmmc'), 'TYPE_BMMC', '所属部门');
				loadEditSelect($('#selectEditBmmc'), 'TYPE_BMMC', '所属部门');
			});
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
			//定义bean
			function makeBeanIn(strYHJS_JSID, strYHJS_JSMC, strYHJS_BMID, strYHJS_JSMS) {
				this.YHJS_JSID = strYHJS_JSID;
				this.YHJS_JSMC = strYHJS_JSMC;
			    this.YHJS_BMID = strYHJS_BMID;
			    this.YHJS_JSMS = strYHJS_JSMS;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					$('#txtSelectCode').val(),
					$('#txtSelectName').val(),
					$('#selectBmmc').val(),
					""
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//新增用户方法
			function insertRole() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					$('#txtEditCode').val(),
					$('#txtEditName').val(),
					$('#selectEditBmmc').val(),
					$('#txtEditMS').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010120",
					data: {
						CMD    : "<%=Servlet9010120.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增角色信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增角色信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增角色信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改用户方法
			function updateRole() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					$('#txtEditCode').val(),
					$('#txtEditName').val(),
					$('#selectEditBmmc').val(),
					$('#txtEditMS').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010120",
					data: {
						CMD    : "<%=Servlet9010120.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改角色信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改角色信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改角色信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//删除用户方法
			function deleteRole() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					$('#txtEditCode').val(),
					"","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010120",
					data: {
						CMD    : "<%=Servlet9010120.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除角色信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除角色信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除角色信息出错！', 1, 0);
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
					$('#txtEditCode').attr("disabled","disabled");
					$('#txtEditName').attr("disabled","disabled");
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#txtEditMS').attr("disabled","disabled");
					$('#txtEditCode').val("");
					$('#txtEditName').val("");
					$('#selectEditBmmc').val("000");
					$('#txtEditMS').val("");
				} else if (strFlag.substring(0, 1) == "3") {
					//新增/修改
					$('#btnAdd').attr("disabled","disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').removeAttr("disabled");
					$('#btnCancel').removeAttr("disabled");
					if (strFlag == "31") {//新增
						$('#txtEditCode').removeAttr("disabled");
						$('#txtEditName').removeAttr("disabled");
						$('#selectEditBmmc').removeAttr("disabled");
						$('#txtEditMS').removeAttr("disabled");
						$('#txtEditCode').val("");
						$('#txtEditName').val("");
						$('#selectEditBmmc').val("000");
						$('#txtEditMS').val("");
						$('#txtEditCode').focus();
					} else if (strFlag == "32") {//修改
						$('#txtEditCode').attr("disabled", "disabled");
						$('#txtEditName').removeAttr("disabled");
						$('#selectEditBmmc').removeAttr("disabled");
						$('#txtEditMS').removeAttr("disabled");
						$('#txtEditName').focus();
					} else if (strFlag == "32") {//删除
						$('#txtEditCode').attr("disabled","disabled");
						$('#txtEditName').attr("disabled","disabled");
						$('#selectEditBmmc').attr("disabled","disabled");
						$('#txtEditMS').attr("disabled","disabled");
					}
				} else if (strFlag == "4") {//选中行
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
					$('#txtEditCode').attr("disabled","disabled");
					$('#txtEditName').attr("disabled","disabled");
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#txtEditMS').attr("disabled","disabled");
				}
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#txtEditCode').val() == "") {
						layer.alert('请输入角色编号！', 0, '友情提示', function() {
							$('#txtEditCode').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditName').val() == "") {
						layer.alert('请输入角色名称！', 0, '友情提示', function() {
							$('#txtEditName').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditBmmc').val() == "000") {
						layer.alert('请选择所属部门！', 0, '友情提示', function() {
							$('#selectEditBmmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if (optionFlag == "Add") {//新增：判断是否角色编号已存在
						if (checkUserExist() == true) {
							layer.alert('角色编号已存在，不能新增！', 0, '友情提示');
							return false;
						}
					} else if (optionFlag == "Upd") {//修改：判断是否角色编号已存在
						if (checkUserExist() == false) {
							layer.alert('角色编号不存在，不能修改！', 0, '友情提示');
							return false;
						}
					}
				}
				return true;
			}
			//验证重复方法
			function checkUserExist() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					$('#txtEditCode').val(),
					"","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010120",
					data: {
						CMD    : "<%=Servlet9010120.CMD_CHK_EXIST%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {
					},
					success : function(response) {
						var strResault = response[0];
						if (strResault == "DATA_EXIST") {
							blnRet = true;
						} else{
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">角色编号</th>
					<td><input id="txtSelectCode" name="角色编号" maxlength="4" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
					<th style="width:100px">角色名称</th>
					<td><input id="txtSelectName" name="角色名称" maxlength="10" /></td>
					<th style="width:100px">所属部门</th>
					<td><select id="selectBmmc" style="width: 100px"></select></td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch"  class="btn btn-primary btn-sm" /></th>
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
					<th style="width:100px">角色编号</th>
					<td><input id="txtEditCode" name="角色编号" maxlength="4" onkeypress="filterKeyForNumber(this,'CNUMONLY');"/></td>
					<th style="width:100px">角色名称</th>
					<td><input id="txtEditName" name="角色名称" maxlength="10" /></td>
					<th style="width:100px">角色描述</th>
					<td><input id="txtEditMS" name="角色描述" maxlength="20" /></td>
					<th style="width:100px">所属部门</th>
					<td><select id="selectEditBmmc" style="width: 100px"></select></td>
				</tr>
			</table>
			<div id="buttonCanvas" class="gToolbar gTbrCenter ">
				<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" class="btn btn-primary btn-sm" />
				<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" class="btn btn-primary btn-sm" /> 
				<input type="button" value="删除" id="btnDel" name="btnDel"  class="btn btn-primary btn-sm" /> 
				<input type="button" value="保存" id="btnSave" name="btnSave"  class="btn btn-primary btn-sm" /> 
				<input type="button" value="取消" id="btnCancel" name="btnCancel"  class="btn btn-primary btn-sm" /> 
			</div>
		</fieldset>
	</body>
</html>