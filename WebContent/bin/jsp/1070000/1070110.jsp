<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xswy.servlet.servlet1010000.Servlet1010120"%> 
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
		<title>楼宇信息</title>
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
					{ title:'所属项目', name:'XMMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'楼宇编号', name:'LYXX_LYBH' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'楼宇名称', name:'LYXX_LYMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'楼宇地址', name:'LYXX_LYDZ' ,width:120, sortable:true, align:'center',lockDisplay: true  },
					{ title:'楼宇类型', name:'LYXX_LYLX' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'楼宇结构', name:'LYXX_LYJG' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'楼宇朝向', name:'LYXX_LYCX' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'备注', name:'LYXX_BZXX' ,width:120, sortable:true, align:'center',lockDisplay: true  }
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
					sortName: 'LYXX_LYID',
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
				});
				//定义表格触发事件
				mmg.on('loadSuccess',function(e, data) {
					//设置按钮状态
					setButtonStatus('1');
				}).on('click', ':checkbox', function() {//选择checkbox
					if (this.checked == true) {
						var arrList = mmg.selectedRows();
						if (arrList.length > 0) {
							setButtonStatus('2');
						}
					} else {
						setButtonStatus('1');
					}
				}).on('click', 'tr', function(e) { //点击行;
					if (mmg.rowsLength() <= 0) return; //无数据,不进行操作
					var rowIndex = e.target.parentNode.rowIndex;
					if (typeof(rowIndex) == "undefined") {
						rowIndex = e.target.parentNode.parentNode.rowIndex;
					}
					var arrList = mmg.selectedRows();
					if (arrList.length > 0) {
						setButtonStatus('2');
					} else {
						setButtonStatus('1');
					}
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				//定义新增按钮点击事件
				$('#btnAdd').on('click', function() {
					openDetail();
				});
				//定义修改按钮点击事件
				$('#btnUpd').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要修改的数据行！', 0, '友情提示');
						return;
					}						
					openDetail(arrList[0].LYXX_LYID);
				});
				//定义删除按钮点击事件
				$('#btnDel').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要删除的数据行！', 0, '友情提示');
						return;
					}
					layer.confirm('是否删除楼宇信息？', function() {
						layer.close(layer.index);
						if (deleteData(arrList[0]) == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('1');
							mmg.deselect('all');
							
						}
					});
				});
			    //初始化下拉列表
			    loadSearchSelect($('#selectXmmc'), 'TYPE_XMMC', '项目名称');
				//页面初始化加载数据
				loadGridByBean();
			});
			function openImport(){
				$.layer({
			        type: 2,
			        title: false,
			        maxmin: false,
			        shadeClose: false, //开启点击遮罩关闭层
			        area : ["350px" , "100px"],
			        offset: ["350px", "200px"],
			        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
			        },
			        end: function(){//关闭按钮
			        	loadGridByBean();
			        }, 
				    iframe: {src: "<%=basePath%>/bin/jsp/1010000/1010122.jsp"}
				});			
			}
			function openDetail(){
				var strLYXX_LYID="";
				if(typeof(arguments[0])!="undefined"){
					strLYXX_LYID=arguments[0];
				}
				$.layer({
			        type: 2,
			        title: false,
			        maxmin: false,
			        shadeClose: false, //开启点击遮罩关闭层
			        area : ["700px" , "500px"],
			        offset : ['20px', ''],
			        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
			        },
			        end: function(){//关闭按钮
			        	loadGridByBean();
			        },
			        iframe: {src: "<%=basePath%>/bin/jsp/1010000/1010121.jsp?LYXX_LYID="+strLYXX_LYID}
			    });				
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
			//定义bean
			function makeBeanIn(strLYID,strXMMC, strGJZ) {
				this.LYXX_LYID = strLYID;
				this.LYXX_XMID = strXMMC;
				this.GJZ = strGJZ;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					"",
					$('#selectXmmc').val(),
					$('#txtSelectGjz').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//删除楼宇信息
			function deleteData(dataBean) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
						dataBean.LYXX_LYID,
						"",
						""
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
							layer.msg('恭喜：删除楼宇信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除楼宇信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除楼宇信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//设置按钮状态
			function setButtonStatus(strFlag) {
				if (strFlag == "1") {//初始
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
				}else if (strFlag == "2") {//可操作状态
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
				} 
			}
		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">所属项目</th>
					<td><select id="selectXmmc" style="width: 100px"></select></td>
					<th style="width:100px">关键字</th>
					<td><input id="txtSelectGjz" name="关键字" /></td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch" class="btn btn-primary btn-sm" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion">
			<legend>操作</legend>
			<div id="buttonCanvas" class="gToolbar gTbrCenter ">
				<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" class="btn btn-primary btn-sm" />
				<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" class="btn btn-primary btn-sm" /> 
				<input type="button" value="删除" id="btnDel" name="btnDel" class="btn btn-primary btn-sm" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	
				<input type="button" value="数据导入" id="btnImp" name="btnImp"  class="btn btn-primary btn-sm" onclick="openImport()" />
				<input type="button" value="数据导出" id="btnExp" name="btnExp"  class="btn btn-primary btn-sm" onclick="btn_Exp()"  />  
			</div>
			
		</fieldset>
	</body>
</html>