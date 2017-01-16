<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1010000.Servlet1010140"%> 
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
		<title>员工评价</title>
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
		<!--插件脚本End  -->
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'所在部门', name:'BMXX_BMMC' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'员工', name:'BPJRXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'评价内容', name:'YGPJ_PJNR' ,width:200, sortable:true, align:'center',lockDisplay: true  },
					{ title:'评价人', name:'PJRXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'评价时间', name:'YGPJ_PJSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'评价等级', name:'PJDJ' ,width:80, sortable:true, align:'center',lockDisplay: true  }
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
					url: '<%=basePath%>/Servlet1010140',
					method: 'post',
					params:{CMD : "<%=Servlet1010140.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'YGPJ_PJID',
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
							$('#selectEditBmmc').val(arrList[0].BMXX_BMID);
							loadEditSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + arrList[0].BMXX_BMID, '员工');
							$('#selectEditYgxm').val(arrList[0].YGPJ_BPJR);
							$('#selectEditPjlx').val(arrList[0].YGPJ_PJDJ);
							$('#txtEditPjnr').val(arrList[0].YGPJ_PJNR);
							setButtonStatus('4');
						}
					} else {
						$('#selectEditBmmc').val("000");
						$('#selectEditYgxm').val("000");
						$('#selectEditPjlx').val("000");
						$('#txtEditPjnr').val("");
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
						$('#selectEditBmmc').val(arrList[0].BMXX_BMID);
						loadEditSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + arrList[0].BMXX_BMID, '员工');
						$('#selectEditYgxm').val(arrList[0].YGPJ_BPJR);
						$('#selectEditPjlx').val(arrList[0].YGPJ_PJDJ);
						$('#txtEditPjnr').val(arrList[0].YGPJ_PJNR);
						setButtonStatus('4');
					} else {
						$('#selectEditBmmc').val("000");
						$('#selectEditYgxm').val("000");
						$('#selectEditPjlx').val("000");
						$('#txtEditPjnr').val("");
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
						layer.confirm('是否增加评价信息？', function() {
							layer.close(layer.index);
							if (insertData() == true) {
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
			    		layer.confirm('是否修改评价信息？', function() {
			    			layer.close(layer.index);
			    			if (updateData(arrList[0].YGPJ_PJID) == true) {
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
					layer.confirm('是否删除评价信息？', function() {
						layer.close(layer.index);
						if (deleteData(arrList[0].YGPJ_PJID) == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('2');
							mmg.deselect('all');
						}
					});
				});
			    //初始化下拉列表
				var dataBean = getBaseData();
				if (dataBean.SFZJL == 'true') {
					loadSearchSelect($('#selectBmmc'), 'TYPE_BMMC', '所在部门');
				    $('#selectBmmc').change(function() {
						loadSearchSelect($('#selectYgxm'), 'TYPE_BMYG-' + $('#selectBmmc').val(), '员工');
				    });
					loadEditSelect($('#selectEditBmmc'), 'TYPE_BMMC', '所在部门');
					$('#selectEditBmmc').change(function() {
						loadEditSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + $('#selectEditBmmc').val(), '员工');
				    });
				} else {
					loadSearchSelect($('#selectBmmc'), 'TYPE_YGPJ_BMMC-' + dataBean.YGID, '所在部门');
					loadEditSelect($('#selectEditBmmc'), 'TYPE_YGPJ_BMMC-' + dataBean.YGID, '所在部门');
					if (dataBean.GLBMSL == '1') {
						$('#selectBmmc option[value="000"]').remove();
						loadSearchSelect($('#selectYgxm'), 'TYPE_YGPJ_BMYG-' + $('#selectBmmc').val() + '-' + dataBean.YGID, '员工');
						$('#selectEditBmmc option[value="000"]').remove();
						loadEditSelect($('#selectEditYgxm'), 'TYPE_YGPJ_BMYG-' + $('#selectEditBmmc').val() + '-' + dataBean.YGID, '员工');
					} else if (dataBean.GLBMSL > '1') {
						$('#selectBmmc').change(function() {
							loadSearchSelect($('#selectYgxm'), 'TYPE_YGPJ_BMYG-' + $('#selectBmmc').val() + '-' + dataBean.YGID, '员工');
					    });
						$('#selectEditBmmc').change(function() {
					    	loadEditSelect($('#selectEditYgxm'), 'TYPE_YGPJ_BMYG-' + $('#selectEditBmmc').val() + '-' + dataBean.YGID, '员工');
					    });
					}
				}
				loadSearchSelect($('#selectPjlx'), 'TYPE_PJLX', '评价类型');
				loadEditSelect($('#selectEditPjlx'), 'TYPE_PJLX', '评价类型');
				//页面初始化加载数据
				loadGridByBean();
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
			function makeBeanIn(strYGPJ_PJID, strBMXX_BMID,
					strYGPJ_BPJR, strYGPJ_PJDJ, strYGPJ_PJNR) {
				this.YGPJ_PJID = strYGPJ_PJID;
				this.BMXX_BMID = strBMXX_BMID;
				this.YGPJ_BPJR = strYGPJ_BPJR;
			    this.YGPJ_PJDJ = strYGPJ_PJDJ;
			    this.YGPJ_PJNR = strYGPJ_PJNR;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					"",
					$('#selectBmmc').val(),
					$('#selectYgxm').val(),
					$('#selectPjlx').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//新增评价信息
			function insertData() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					"","",
					$('#selectEditYgxm').val(),
					$('#selectEditPjlx').val(),
					$('#txtEditPjnr').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010140",
					data: {
						CMD    : "<%=Servlet1010140.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增评价信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增评价信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增评价信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改评价信息
			function updateData(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					dataId,"",
					$('#selectEditYgxm').val(),
					$('#selectEditPjlx').val(),
					$('#txtEditPjnr').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010140",
					data: {
						CMD    : "<%=Servlet1010140.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改评价信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改评价信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改评价信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//删除评价信息
			function deleteData(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					dataId,"","","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010140",
					data: {
						CMD    : "<%=Servlet1010140.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除评价信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除评价信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除评价信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//获取登陆用户基础数据
			function getBaseData() {
				var baseData;
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010140",
					data: {
						CMD    : "<%=Servlet1010140.CMD_GET_BASE%>"
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							baseData = response[1];
						} else if (strResult == "FAILURE") {
							layer.msg('友情提示：此管理者尚未分配职务，无法对下级员工进行评价！', 2, 0);
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：加载数据异常，请联系系统管理员！', 1, 0);
						}
					}
				});
				return baseData;
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
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditPjlx').attr("disabled","disabled");
					$('#txtEditPjnr').attr("disabled","disabled");
					$('#selectEditBmmc').val("000");
					$('#selectEditYgxm').val("000");
					$('#selectEditPjlx').val("000");
					$('#txtEditPjnr').val("");
				} else if (strFlag.substring(0, 1) == "3") {
					//新增/修改
					$('#btnAdd').attr("disabled","disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').removeAttr("disabled");
					$('#btnCancel').removeAttr("disabled");
					if (strFlag == "31") {//新增
						$('#selectEditBmmc').removeAttr("disabled");
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditPjlx').removeAttr("disabled");
						$('#txtEditPjnr').removeAttr("disabled");
						$('#selectEditBmmc').val("000");
						$('#selectEditYgxm').val("000");
						$('#selectEditPjlx').val("000");
						$('#txtEditPjnr').val("");
						$('#selectEditBmmc').focus();
					} else if (strFlag == "32") {//修改
						$('#selectEditBmmc').removeAttr("disabled");
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditPjlx').removeAttr("disabled");
						$('#txtEditPjnr').removeAttr("disabled");
						$('#selectEditBmmc').focus();
					} else if (strFlag == "32") {//删除
						$('#selectEditBmmc').attr("disabled","disabled");
						$('#selectEditYgxm').attr("disabled","disabled");
						$('#selectEditPjlx').attr("disabled","disabled");
						$('#txtEditPjnr').attr("disabled","disabled");
					}
				} else if (strFlag == "4") {//选中行
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditPjlx').attr("disabled","disabled");
					$('#txtEditPjnr').attr("disabled","disabled");
				}
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#selectEditBmmc').val() == "000") {
						layer.alert('请选择所在部门！', 0, '友情提示', function() {
							$('#selectEditBmmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditYgxm').val() == "000") {
						layer.alert('请选择员工！', 0, '友情提示', function() {
							$('#selectEditYgxm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditPjlx').val() == "000") {
						layer.alert('请选择评价类型！', 0, '友情提示', function() {
							$('#selectEditPjlx').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditPjnr').val() == "") {
						layer.alert('请输入评价内容！', 0, '友情提示', function() {
							$('#txtEditPjnr').focus();
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
					<th style="width:100px">评价类型</th>
					<td><select id="selectPjlx" style="width: 100px"></select></td>
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
					<th style="width:100px">评价类型</th>
					<td><select id="selectEditPjlx" style="width: 100px"></select></td>
					<th style="width:100px">评价内容</th>
					<td><input id="txtEditPjnr" name="评价内容" style="width: 300px;" /></td>
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