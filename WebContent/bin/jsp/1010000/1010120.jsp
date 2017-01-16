<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1010000.Servlet1010120"%> 
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
		<title>员工职务</title>
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
					{ title:'员工', name:'YGXM' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'所在部门', name:'BMXX_BMMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'角色名称', name:'YHJS_JSMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'职务名称', name:'ZWXX_ZWMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'职务类型', name:'ZWLX' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'职务等级', name:'YGZW_ZWDJ' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'绩效级别', name:'YGZW_JXJB' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'上级主管', name:'SJZGXM' ,width:100, sortable:true, align:'center',lockDisplay: true  }
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
					url: '<%=basePath%>/Servlet1010120',
					method: 'post',
					params:{CMD : "<%=Servlet1010120.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'YGZW_UUID',
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
							$('#txtEditUuid').val(arrList[0].YGZW_UUID);
							$('#selectEditYgxm').val(arrList[0].YGZW_YGID);
							$('#selectEditBmmc').val(arrList[0].BMXX_BMID);
							loadEditSelect($('#selectEditJsmc'), 'TYPE_BMJS-' + arrList[0].BMXX_BMID, '角色名称');
							$('#selectEditJsmc').val(arrList[0].YHJS_JSID);
							loadEditSelect($('#selectEditZwmc'), 'TYPE_JSZW-' + arrList[0].YHJS_JSID, '职务名称');
							$('#selectEditZwmc').val(arrList[0].YGZW_ZWID);
							$('#selectEditZwlx').val(arrList[0].YGZW_ZWLX);
							$('#selectEditZwdj').val(arrList[0].YGZW_ZWDJ);
							$('#selectEditJxjb').val(arrList[0].YGZW_JXJB);
							loadEditSelect($('#selectEditSjzgxm'), 'TYPE_SJZG-' + arrList[0].YGZW_YGID + '-' + arrList[0].BMXX_BMID + '-' + arrList[0].YGZW_ZWDJ, '上级主管姓名');
							$('#selectEditSjzgxm').val(arrList[0].YGZW_SJZG);
							setButtonStatus('4');
						}
					} else {
						$('#txtEditUuid').val("");
						$('#selectEditYgxm').val("000");
						$('#selectEditBmmc').val("000");
						$('#selectEditJsmc').val("000");
						$('#selectEditZwmc').val("000");
						$('#selectEditZwlx').val("000");
						$('#selectEditZwdj').val("000");
						$('#selectEditJxjb').val("000");
						$('#selectEditSjzgxm').val("000");
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
						$('#txtEditUuid').val(arrList[0].YGZW_UUID);
						$('#selectEditYgxm').val(arrList[0].YGZW_YGID);
						$('#selectEditBmmc').val(arrList[0].BMXX_BMID);
						loadEditSelect($('#selectEditJsmc'), 'TYPE_BMJS-' + arrList[0].BMXX_BMID, '角色名称');
						$('#selectEditJsmc').val(arrList[0].YHJS_JSID);
						loadEditSelect($('#selectEditZwmc'), 'TYPE_JSZW-' + arrList[0].YHJS_JSID, '职务名称');
						$('#selectEditZwmc').val(arrList[0].YGZW_ZWID);
						$('#selectEditZwlx').val(arrList[0].YGZW_ZWLX);
						$('#selectEditZwdj').val(arrList[0].YGZW_ZWDJ);
						$('#selectEditJxjb').val(arrList[0].YGZW_JXJB);
						loadEditSelect($('#selectEditSjzgxm'), 'TYPE_SJZG-' + arrList[0].YGZW_YGID + '-' + arrList[0].BMXX_BMID + '-' + arrList[0].YGZW_ZWDJ, '上级主管姓名');
						$('#selectEditSjzgxm').val(arrList[0].YGZW_SJZG);
						setButtonStatus('4');
					} else {
						$('#txtEditUuid').val("");
						$('#selectEditYgxm').val("000");
						$('#selectEditBmmc').val("000");
						$('#selectEditJsmc').val("000");
						$('#selectEditZwmc').val("000");
						$('#selectEditZwlx').val("000");
						$('#selectEditZwdj').val("000");
						$('#selectEditJxjb').val("000");
						$('#selectEditSjzgxm').val("000");
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
						layer.confirm('是否增加职务信息？', function() {
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
			    		layer.confirm('是否修改职务信息？', function() {
			    			layer.close(layer.index);
			    			if (updateData(arrList[0]) == true) {
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
					layer.confirm('是否删除职务信息？', function() {
						layer.close(layer.index);
						if (deleteData(arrList[0]) == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('2');
							mmg.deselect('all');
						}
					});
				});
			    //初始化下拉列表
			    loadSearchSelect($('#selectBmmc'), 'TYPE_BMMC', '部门名称');
			    loadSearchSelect($('#selectYgxm'), 'TYPE_YGXM', '员工姓名');
			    $('#selectBmmc').change(function() {
			    	if ($('#selectBmmc').val() == '000') {
			    		loadSearchSelect($('#selectYgxm'), 'TYPE_YGXM', '员工姓名');
			    	} else {
						loadSearchSelect($('#selectYgxm'), 'TYPE_BMYG-' + $('#selectBmmc').val(), '员工姓名');
			    	}
			    });
			    loadSearchSelect($('#selectZwmc'), 'TYPE_ZWMC', '职务名称');
				loadEditSelect($('#selectEditYgxm'), 'TYPE_YGXM', '员工姓名');
				loadEditSelect($('#selectEditBmmc'), 'TYPE_BMMC', '所属部门');
				$('#selectEditBmmc').change(function() {
					loadEditSelect($('#selectEditJsmc'), 'TYPE_BMJS-' + $('#selectEditBmmc').val(), '角色名称');
			    });
				$('#selectEditJsmc').change(function() {
					loadEditSelect($('#selectEditZwmc'), 'TYPE_JSZW-' + $('#selectEditJsmc').val(), '职务名称');
			    });
				$('#selectEditYgxm, #selectEditBmmc, #selectEditZwdj').change(function() {
					loadEditSelect($('#selectEditSjzgxm'), 'TYPE_SJZG-' + $('#selectEditYgxm').val() + '-' + $('#selectEditBmmc').val() + '-' + $('#selectEditZwdj').val(), '上级主管姓名');
				});
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
			//定义查询bean
			function makeBeanInSearch(strYGZW_UUID, strBMXX_BMID, strYGZW_YGID,
					strYGZW_ZWID, strYGZW_ZWLX, strYGZW_ZWDJ, strYGZW_JXJB) {
				this.YGZW_UUID = strYGZW_UUID;
				this.BMXX_BMID = strBMXX_BMID;
				this.YGZW_YGID = strYGZW_YGID;
			    this.YGZW_ZWID = strYGZW_ZWID;
			    this.YGZW_ZWLX = strYGZW_ZWLX;
			    this.YGZW_ZWDJ = strYGZW_ZWDJ;
			    this.YGZW_JXJB = strYGZW_JXJB;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanInSearch(
					"",
					$('#selectBmmc').val(),
					$('#selectYgxm').val(),
					$('#selectZwmc').val(),
					$('#selectZwlx').val(),
					$('#selectZwdj').val(),
					$('#selectJxjb').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//定义编辑bean
			function makeBeanInEdit(strYGZW_UUID, strYGZW_YGID, strYHJS_JSID, 
					strYGZW_ZWID, strYGZW_ZWLX, strYGZW_ZWDJ, strYGZW_JXJB,
					strYGZW_SJZG, strZWDD_YBMID, strZWDD_YZWID, strZWDD_YZWDJ) {
				this.YGZW_UUID = strYGZW_UUID;
				this.YGZW_YGID = strYGZW_YGID;
				this.YHJS_JSID = strYHJS_JSID;
			    this.YGZW_ZWID = strYGZW_ZWID;
			    this.YGZW_ZWLX = strYGZW_ZWLX;
			    this.YGZW_ZWDJ = strYGZW_ZWDJ;
			    this.YGZW_JXJB = strYGZW_JXJB;
			    this.YGZW_SJZG = strYGZW_SJZG;
			    this.ZWDD_YBMID = strZWDD_YBMID;
			    this.ZWDD_YZWID = strZWDD_YZWID;
			    this.ZWDD_YZWDJ = strZWDD_YZWDJ;
			}
			//新增用户方法
			function insertData() {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					"",
					$('#selectEditYgxm').val(),
					$('#selectEditJsmc').val(),
					$('#selectEditZwmc').val(),
					$('#selectEditZwlx').val(),
					$('#selectEditZwdj').val(),
					$('#selectEditJxjb').val(),
					$('#selectEditSjzgxm').val(),
					"","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010120",
					data: {
						CMD    : "<%=Servlet1010120.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增员工职务成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增员工职务失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增员工职务出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改用户方法
			function updateData(dataBean) {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					dataBean.YGZW_UUID,
					$('#selectEditYgxm').val(),
					$('#selectEditJsmc').val(),
					$('#selectEditZwmc').val(),
					$('#selectEditZwlx').val(),
					$('#selectEditZwdj').val(),
					$('#selectEditJxjb').val(),
					$('#selectEditSjzgxm').val(),
					dataBean.ZWXX_BMID,
					dataBean.YGZW_ZWID,
					dataBean.YGZW_ZWDJ
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010120",
					data: {
						CMD    : "<%=Servlet1010120.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改员工职务成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改员工职务失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改员工职务出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//删除用户方法
			function deleteData(dataBean) {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
						dataBean.YGZW_UUID,
						dataBean.YGZW_YGID,
						"","","","","","",
						dataBean.ZWXX_BMID,
						dataBean.YGZW_ZWID,
						dataBean.YGZW_ZWDJ
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010120",
					data: {
						CMD    : "<%=Servlet1010120.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除员工职务成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除员工职务失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除员工职务出错！', 1, 0);
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
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#selectEditJsmc').attr("disabled","disabled");
					$('#selectEditZwmc').attr("disabled","disabled");
					$('#selectEditZwlx').attr("disabled","disabled");
					$('#selectEditZwdj').attr("disabled","disabled");
					$('#selectEditJxjb').attr("disabled","disabled");
					$('#selectEditSjzgxm').attr("disabled","disabled");
					$('#selectEditYgxm').val("000");
					$('#selectEditBmmc').val("000");
					$('#selectEditJsmc').val("000");
					$('#selectEditZwmc').val("000");
					$('#selectEditZwlx').val("000");
					$('#selectEditZwdj').val("000");
					$('#selectEditJxjb').val("000");
					$('#selectEditSjzgxm').val("000");
				} else if (strFlag.substring(0, 1) == "3") {
					//新增/修改
					$('#btnAdd').attr("disabled","disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').removeAttr("disabled");
					$('#btnCancel').removeAttr("disabled");
					if (strFlag == "31") {//新增
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditBmmc').removeAttr("disabled");
						$('#selectEditJsmc').removeAttr("disabled");
						$('#selectEditZwmc').removeAttr("disabled");
						$('#selectEditZwlx').removeAttr("disabled");
						$('#selectEditZwdj').removeAttr("disabled");
						$('#selectEditJxjb').removeAttr("disabled");
						$('#selectEditSjzgxm').removeAttr("disabled");
						$('#selectEditYgxm').val("000");
						$('#selectEditBmmc').val("000");
						$('#selectEditJsmc').val("000");
						$('#selectEditZwmc').val("000");
						$('#selectEditZwlx').val("000");
						$('#selectEditZwdj').val("000");
						$('#selectEditJxjb').val("000");
						$('#selectEditSjzgxm').val("000");
						$('#selectEditYgxm').focus();
					} else if (strFlag == "32") {//修改
						$('#selectEditBmmc').removeAttr("disabled");
						$('#selectEditJsmc').removeAttr("disabled");
						$('#selectEditZwmc').removeAttr("disabled");
						$('#selectEditZwlx').removeAttr("disabled");
						$('#selectEditZwdj').removeAttr("disabled");
						$('#selectEditJxjb').removeAttr("disabled");
						$('#selectEditSjzgxm').removeAttr("disabled");
						$('#selectEditYgxm').focus();
					} else if (strFlag == "32") {//删除
						$('#selectEditYgxm').attr("disabled","disabled");
						$('#selectEditBmmc').attr("disabled","disabled");
						$('#selectEditJsmc').attr("disabled","disabled");
						$('#selectEditZwmc').attr("disabled","disabled");
						$('#selectEditZwlx').attr("disabled","disabled");
						$('#selectEditZwdj').attr("disabled","disabled");
						$('#selectEditJxjb').attr("disabled","disabled");
						$('#selectEditSjzgxm').attr("disabled","disabled");
					}
				} else if (strFlag == "4") {//选中行
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#selectEditJsmc').attr("disabled","disabled");
					$('#selectEditZwmc').attr("disabled","disabled");
					$('#selectEditZwlx').attr("disabled","disabled");
					$('#selectEditZwdj').attr("disabled","disabled");
					$('#selectEditJxjb').attr("disabled","disabled");
					$('#selectEditSjzgxm').attr("disabled","disabled");
				}
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#selectEditYgxm').val() == "000") {
						layer.alert('请选择员工姓名！', 0, '友情提示', function() {
							$('#selectEditYgxm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditBmmc').val() == "000") {
						layer.alert('请选择部门！', 0, '友情提示', function() {
							$('#selectEditBmmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditJsmc').val() == "000") {
						layer.alert('请选择角色名称！', 0, '友情提示', function() {
							$('#selectEditJsmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditZwmc').val() == "000") {
						layer.alert('请选择职务名称！', 0, '友情提示', function() {
							$('#selectEditZwmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditZwlx').val() == "000") {
						layer.alert('请选择职务类型！', 0, '友情提示', function() {
							$('#selectEditZwlx').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditZwdj').val() == "000") {
						layer.alert('请选择职务等级！', 0, '友情提示', function() {
							$('#selectEditZwdj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditJxjb').val() == "000") {
						layer.alert('请选择绩效级别！', 0, '友情提示', function() {
							$('#selectEditJxjb').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if (checkDataExist(optionFlag) == "1") {
						layer.alert('所选职务已存在，不能重复录入！', 0, '友情提示');
						return false;
					} else if (checkDataExist(optionFlag) == "2") {
						layer.alert('每位员工只允许有一个专职职务！', 0, '友情提示');
						return false;
					}
				}
				return true;
			}
			//验证重复方法
			function checkDataExist(optionFlag) {
				var blnRet = "0";
				var beanIn = new makeBeanInSearch(
					$('#txtEditUuid').val(),
					"",
					$('#selectEditYgxm').val(),
					$('#selectEditZwmc').val(),
					$('#selectEditZwlx').val(),
					"",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010120",
					data: {
						CMD    : "<%=Servlet1010120.CMD_CHK_EXIST%>",
						BeanIn : JSON.stringify(beanIn),
						FLAG : optionFlag
					},
					complete : function(response) {
					},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "DATA_NAME_EXIST") {
							blnRet = "1";
						} else if (strResult == "DATA_LX_EXIST") {
							blnRet = "2";
						} else {
							blnRet = "0";
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
					<th style="width:100px">所在部门</th>
					<td><select id="selectBmmc" style="width: 100px"></select></td>
					<th style="width:100px">员工姓名</th>
					<td>
						<select id="selectYgxm" style="width: 100px">
							<option value="000">所有</option>
						</select>
					</td>
					<th style="width:100px">职务名称</th>
					<td><select id="selectZwmc" style="width: 100px"></select></td>
					<th style="width:100px">职务类型</th>
					<td>
						<select id="selectZwlx" style="width: 100px">
							<option value="000">所有</option>
							<option value="1">专职</option>
			        		<option value="0">兼职</option>
						</select>
					</td>
					<th style="width:100px">职务等级</th>
					<td>
						<select id="selectZwdj" style="width: 100px">
							<option value="000">所有</option>
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
						</select>
					</td>
					<th style="width:100px">绩效级别</th>
					<td>
						<select id="selectJxjb" style="width: 100px">
							<option value="000">所有</option>
							<option value="1级">1级</option>
							<option value="2级">2级</option>
							<option value="3级">3级</option>
							<option value="4级">4级</option>
							<option value="5级">5级</option>
							<option value="6级">6级</option>
							<option value="7级">7级</option>
							<option value="8级">8级</option>
							<option value="9级">9级</option>
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
					<th style="width:100px">员工姓名</th>
					<td>
						<input id="txtEditUuid" type="hidden" />
						<select id="selectEditYgxm" style="width: 100px"></select>
					</td>
					<th style="width:100px">部门</th>
					<td><select id="selectEditBmmc" style="width: 100px"></select></td>
					<th style="width:100px">角色名称</th>
					<td>
						<select id="selectEditJsmc" style="width: 100px">
							<option value="000">请选择</option>
						</select>
					</td>
					<th style="width:100px">职务名称</th>
					<td>
						<select id="selectEditZwmc" style="width: 100px">
							<option value="000">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:100px">职务类型</th>
					<td>
						<select id="selectEditZwlx" style="width: 100px">
							<option value="000">请选择</option>
							<option value="1">专职</option>
			        		<option value="0">兼职</option>
						</select>
					</td>
					<th style="width:100px">职务等级</th>
					<td>
						<select id="selectEditZwdj" style="width: 100px">
							<option value="000">请选择</option>
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
						</select>
					</td>
					<th style="width:100px">绩效级别</th>
					<td>
						<select id="selectEditJxjb" style="width: 100px">
							<option value="000">请选择</option>
							<option value="1级">1级</option>
							<option value="2级">2级</option>
							<option value="3级">3级</option>
							<option value="4级">4级</option>
							<option value="5级">5级</option>
							<option value="6级">6级</option>
							<option value="7级">7级</option>
							<option value="8级">8级</option>
							<option value="9级">9级</option>
						</select>
					</td>
					<th style="width:100px">上级主管</th>
					<td>
						<select id="selectEditSjzgxm" style="width: 100px">
							<option value="000">请选择</option>
						</select>
					</td>
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