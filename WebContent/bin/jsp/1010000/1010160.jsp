<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1010000.Servlet1010160"%> 
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
		<title>员工合同</title>
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
					{ title:'合同编号', name:'YGHT_HTBH' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'部门', name:'BMMC' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'员工', name:'YGXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'生效日期', name:'YGHT_SXRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'失效日期', name:'YGHT_JZRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'合同期限', name:'YGHT_HTNX' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'状态', name:'SFYX' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'合同类型', name:'HTLX' ,width:120, sortable:true, align:'center',lockDisplay: true  },
					{ title:'备注信息', name:'YGHT_BZXX' ,width:150, sortable:true, align:'center',lockDisplay: true  }
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
					url: '<%=basePath%>/Servlet1010160',
					method: 'post',
					params:{CMD : "<%=Servlet1010160.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'YGHT_HTID',
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
							$('#txtEditUuid').val(arrList[0].YGHT_HTID);
							$('#txtEditHtbh').val(arrList[0].YGHT_HTBH);
							$('#selectEditBmmc').val(arrList[0].YGHT_BMID);
							loadSearchSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + $('#selectEditBmmc').val(), '员工姓名');
							$('#selectEditYgxm').val(arrList[0].YGHT_YGID);
							$('#selectEditHtzt').val(arrList[0].YGHT_SFYX);
							$('#txtEditSxrq').val(arrList[0].YGHT_SXRQ);
							$('#txtEditJzrq').val(arrList[0].YGHT_JZRQ);
							$('#selectEditHtlx').val(arrList[0].YGHT_HTLX);
							$('#selectEditQdpxxy').val(arrList[0].YGHT_QDPXXY);
							$('#selectEditSffj').val(arrList[0].YGHT_SFFJ);
							$('#txtEditBzxx').val(arrList[0].YGHT_BZXX);
							setButtonStatus('4');
						}
					} else {
						$('#txtEditHtbh').val("");
						$('#selectEditYgxm').val("000");
						$('#selectEditBmmc').val("000");
						$('#selectEditHtzt').val("000");
						$('#txtEditSxrq').val("");
						$('#txtEditJzrq').val("");
						$('#selectEditHtlx').val("000");
						$('#selectEditQdpxxy').val("000");
						$('#selectEditSffj').val("000");
						$('#txtEditBzxx').val("");
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
						$('#txtEditUuid').val(arrList[0].YGHT_HTID);
						$('#txtEditHtbh').val(arrList[0].YGHT_HTBH);		
						$('#selectEditBmmc').val(arrList[0].YGHT_BMID);
						loadSearchSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + $('#selectEditBmmc').val(), '员工姓名');
						$('#selectEditYgxm').val(arrList[0].YGHT_YGID);
						$('#selectEditHtzt').val(arrList[0].YGHT_SFYX);
						$('#txtEditSxrq').val(arrList[0].YGHT_SXRQ);
						$('#txtEditJzrq').val(arrList[0].YGHT_JZRQ);
						$('#selectEditHtlx').val(arrList[0].YGHT_HTLX);
						$('#selectEditQdpxxy').val(arrList[0].YGHT_QDPXXY);
						$('#selectEditSffj').val(arrList[0].YGHT_SFFJ);
						$('#txtEditBzxx').val(arrList[0].YGHT_BZXX);
						setButtonStatus('4');
					} else {
						$('#txtEditHtbh').val("");
						$('#selectEditYgxm').val("000");
						$('#selectEditBmmc').val("000");
						$('#selectEditHtzt').val("000");
						$('#txtEditSxrq').val("");
						$('#txtEditJzrq').val("");
						$('#selectEditHtlx').val("000");
						$('#selectEditQdpxxy').val("000");
						$('#selectEditSffj').val("000");
						$('#txtEditBzxx').val("");
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
						layer.confirm('是否增加合同信息？', function() {
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
			    		layer.confirm('是否修改合同信息？', function() {
			    			layer.close(layer.index);
			    			if (updateRole(arrList[0].YGHT_HTID) == true) {
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
					layer.confirm('是否删除合同信息？', function() {
						layer.close(layer.index);
						if (deleteRole(arrList[0].YGHT_HTID) == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('2');
							mmg.deselect('all');
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
				//loadSearchSelect($('#selectYgxm'), 'TYPE_YGXM', '员工姓名');
				//loadEditSelect($('#selectEditYgxm'), 'TYPE_YGXM', '员工姓名');
				/* 日期控件设置Start */
				var start = {
					elem: '#txtEditSxrq',
					format: 'YYYY-MM-DD',
					istime: false,
					istoday: true,
					choose: function(datas){
						end.min = datas; //开始日选好后，重置结束日的最小日期
						end.start = datas //将结束日的初始值设定为开始日
					}
				};
				var end = {
					elem: '#txtEditJzrq',
					format: 'YYYY-MM-DD',
					istime: false,
					istoday: true,
					choose: function(datas){
						start.max = datas; //结束日选好后，重置开始日的最大日期
					}
				};
				laydate(start);
				laydate(end);
				laydate.skin("default");
				/* 日期控件设置End */
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
			function makeBeanIn(strYGHT_HTID, strYGHT_HTBH, strYGHT_BMID, strYGHT_YGID, strYGHT_SXRQ,
					strYGHT_JZRQ, strYGHT_HTLX, strYGHT_QDPXXY, strYGHT_SFFJ, strYGHT_BZXX, strYGHT_SFYX) {
				this.YGHT_HTID = strYGHT_HTID;
				this.YGHT_HTBH = strYGHT_HTBH;
				this.YGHT_BMID = strYGHT_BMID;
			    this.YGHT_YGID = strYGHT_YGID;
			    this.YGHT_SXRQ = strYGHT_SXRQ;
			    this.YGHT_JZRQ = strYGHT_JZRQ;
			    this.YGHT_HTLX = strYGHT_HTLX;
			    this.YGHT_QDPXXY = strYGHT_QDPXXY;
			    this.YGHT_SFFJ = strYGHT_SFFJ;
			    this.YGHT_BZXX = strYGHT_BZXX;
			    this.YGHT_SFYX = strYGHT_SFYX;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
						"",
						$('#txtSelectHtbh').val(),
						$('#selectBmmc').val(),
						$('#selectYgxm').val(),
						"","",
						$('#selectHtlx').val(),
						"","","",
						$('#selectHtzt').val()
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
					"",
					$('#txtEditHtbh').val(),
					$('#selectEditBmmc').val(),
					$('#selectEditYgxm').val(),
					$('#txtEditSxrq').val(),
					$('#txtEditJzrq').val(),
					$('#selectEditHtlx').val(),
					"",
					"",
					$('#txtEditBzxx').val(),
					$('#selectEditHtzt').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010160",
					data: {
						CMD    : "<%=Servlet1010160.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增合同信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增合同信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增合同信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改用户方法
			function updateRole(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					dataId,
					$('#txtEditHtbh').val(),
					$('#selectEditBmmc').val(),
					$('#selectEditYgxm').val(),
					$('#txtEditSxrq').val(),
					$('#txtEditJzrq').val(),
					$('#selectEditHtlx').val(),
					"",
					"",
					$('#txtEditBzxx').val(),
					$('#selectEditHtzt').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010160",
					data: {
						CMD    : "<%=Servlet1010160.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改合同信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改合同信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改合同信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//删除用户方法
			function deleteRole(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					dataId,"","","","","","","","","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010160",
					data: {
						CMD    : "<%=Servlet1010160.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除合同信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除合同信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除合同信息出错！', 1, 0);
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
					$('#txtEditHtbh').attr("disabled","disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditHtzt').attr("disabled","disabled");
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#txtEditSxrq').attr("disabled","disabled");
					$('#txtEditJzrq').attr("disabled","disabled");
					$('#selectEditHtlx').attr("disabled","disabled");
					$('#selectEditQdpxxy').attr("disabled","disabled");
					$('#selectEditSffj').attr("disabled","disabled");
					$('#txtEditBzxx').attr("disabled","disabled");
					$('#txtEditHtbh').val("");
					$('#selectEditYgxm').val("000");
					$('#selectEditBmmc').val("000");
					$('#selectEditHtzt').val("000");
					$('#txtEditSxrq').val("");
					$('#txtEditJzrq').val("");
					$('#selectEditHtlx').val("000");
					$('#selectEditQdpxxy').val("000");
					$('#selectEditSffj').val("000");
					$('#txtEditBzxx').val("");
				} else if (strFlag.substring(0, 1) == "3") {
					//新增/修改
					$('#btnAdd').attr("disabled","disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
					$('#btnSave').removeAttr("disabled");
					$('#btnCancel').removeAttr("disabled");
					if (strFlag == "31") {//新增
						$('#txtEditHtbh').removeAttr("disabled");
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditBmmc').removeAttr("disabled");
						$('#selectEditHtzt').removeAttr("disabled");
						$('#txtEditSxrq').removeAttr("disabled");
						$('#txtEditJzrq').removeAttr("disabled");
						$('#selectEditHtlx').removeAttr("disabled");
						$('#selectEditQdpxxy').removeAttr("disabled");
						$('#selectEditSffj').removeAttr("disabled");
						$('#txtEditBzxx').removeAttr("disabled");
						$('#txtEditHtbh').val("");
						$('#selectEditBmmc').val("000");
						loadSearchSelect($('#selectEditYgxm'), 'TYPE_BMYG-' + $('#selectEditBmmc').val(), '员工姓名');
						$('#selectEditYgxm').val("000");	
						$('#selectEditHtzt').val("1");
						$('#txtEditSxrq').val("");
						$('#txtEditJzrq').val("");
						$('#selectEditHtlx').val("000");
						$('#selectEditQdpxxy').val("000");
						$('#selectEditSffj').val("000");
						$('#txtEditBzxx').val("");
						$('#txtEditHtbh').focus();
					} else if (strFlag == "32") {//修改
						$('#txtEditHtbh').removeAttr("disabled");
						$('#selectEditYgxm').removeAttr("disabled");
						$('#selectEditBmmc').removeAttr("disabled");
						$('#selectEditHtzt').removeAttr("disabled");
						$('#txtEditSxrq').removeAttr("disabled");
						$('#txtEditJzrq').removeAttr("disabled");
						$('#selectEditHtlx').removeAttr("disabled");
						$('#selectEditQdpxxy').removeAttr("disabled");
						$('#selectEditSffj').removeAttr("disabled");
						$('#txtEditBzxx').removeAttr("disabled");
						$('#txtEditHtbh').focus();
					} else if (strFlag == "32") {//删除
						$('#txtEditHtbh').attr("disabled","disabled");
						$('#selectEditYgxm').attr("disabled","disabled");
						$('#selectEditBmmc').attr("disabled","disabled");
						$('#selectEditHtzt').attr("disabled","disabled");
						$('#txtEditSxrq').attr("disabled","disabled");
						$('#txtEditJzrq').attr("disabled","disabled");
						$('#selectEditHtlx').attr("disabled","disabled");
						$('#selectEditQdpxxy').attr("disabled","disabled");
						$('#selectEditSffj').attr("disabled","disabled");
						$('#txtEditBzxx').attr("disabled","disabled");
					}
				} else if (strFlag == "4") {//选中行
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
					$('#btnSave').attr("disabled", "disabled");
					$('#btnCancel').attr("disabled", "disabled");
					$('#txtEditHtbh').attr("disabled","disabled");
					$('#selectEditYgxm').attr("disabled","disabled");
					$('#selectEditBmmc').attr("disabled","disabled");
					$('#selectEditHtzt').attr("disabled","disabled");
					$('#txtEditSxrq').attr("disabled","disabled");
					$('#txtEditJzrq').attr("disabled","disabled");
					$('#selectEditHtlx').attr("disabled","disabled");
					$('#selectEditQdpxxy').attr("disabled","disabled");
					$('#selectEditSffj').attr("disabled","disabled");
					$('#txtEditBzxx').attr("disabled","disabled");
				}
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#txtEditHtbh').val() == "") {
						layer.alert('请输入合同编号！', 0, '友情提示', function() {
							$('#txtEditHtbh').focus();
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
					if ($('#selectEditYgxm').val() == "000") {
						layer.alert('请选择员工！', 0, '友情提示', function() {
							$('#selectEditYgxm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditSxrq').val() == "") {
						layer.alert('请输入生效日期！', 0, '友情提示', function() {
							$('#txtEditSxrq').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditHtlx').val() == "000") {
						layer.alert('请选择合同类型！', 0, '友情提示', function() {
							$('#selectEditHtlx').focus();
							layer.close(layer.index);
						});
						return false;
					}
					//判断是否此员工合同已存在
					if (checkUserExist(optionFlag) == true) {
						layer.alert('此员工合同已存在，不能重复录入！', 0, '友情提示');
						return false;
					}
				}
				return true;
			}
			//验证重复方法
			function checkUserExist(optionFlag) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					$('#txtEditUuid').val(), $('#txtEditHtbh').val(),
					"","","","","","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010160",
					data: {
						CMD    : "<%=Servlet1010160.CMD_CHK_EXIST%>",
						BeanIn : JSON.stringify(beanIn),
						FLAG : optionFlag
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
					<th style="width:100px">合同编号</th>
					<td><input id="txtSelectHtbh" name="合同编号" maxlength="10" /></td>
					<th style="width:100px">部门</th>
					<td><select id="selectBmmc" style="width: 100px"></select></td>
					<th style="width:100px">员工</th>
					<td>
						<select id="selectYgxm" style="width: 100px">
							<option value="000">所有</option>
						</select>
					</td>
					<th style="width:100px">合同类型</th>
					<td>
						<select id="selectHtlx" style="width: 100px">
							<option value="000">请选择</option>
							<option value="0">临时合同</option>
							<option value="1">实习合同</option>
							<option value="2">正式合同</option>
							<option value="3">返聘合同</option>
							<option value="4">无固定期限劳动合同</option>
							<option value="5">固定期限劳动合同</option>
						</select>
					</td>
					<th style="width:100px">状态</th>
					<td>
						<select id="selectHtzt" style="width: 70px">
							<option value="000">所有</option>
							<option value="0">无效</option>
							<option value="1" selected="selected">有效</option>
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
					<th style="width:100px">合同编号</th>
					<td>
						<input id="txtEditUuid" type="hidden" />
						<input id="txtEditHtbh" name="合同编号" maxlength="10" />
					</td>
					<th style="width:100px">部门</th>
					<td><select id="selectEditBmmc" style="width: 120px"></select></td>
					<th style="width:100px">员工</th>
					<td>
						<select id="selectEditYgxm" style="width: 100px">
							<option value="000">请选择</option>
						</select>
					</td>
					<th style="width:100px">合同类型</th>
					<td>
						<select id="selectEditHtlx" style="width: 140px">
							<option value="000">请选择</option>
							<option value="0">临时合同</option>
							<option value="1">实习合同</option>
							<option value="2">正式合同</option>
							<option value="3">返聘合同</option>
							<option value="4">无固定期限劳动合同</option>
							<option value="5">固定期限劳动合同</option>
						</select>
					</td>
					<th style="width:100px">状态</th>
					<td>
						<select id="selectEditHtzt" style="width: 70px">
							<option value="000">请选择</option>
							<option value="0">无效</option>
							<option value="1" selected>有效</option>	
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:100px">生效日期</th>
					<td><input id="txtEditSxrq" type="text" style="width: 120px;height: 16px;" readonly="readonly" class="laydate-icon-default"></td>
					<th style="width:100px">失效日期</th>
					<td><input id="txtEditJzrq" type="text" style="width: 120px;height: 16px;" readonly="readonly" class="laydate-icon-default"></td>
					<th style="width:100px">备注信息</th>
					<td colspan="5"  ><input id="txtEditBzxx" name="备注信息" maxlength="100"  style="width:500px"/></td>
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