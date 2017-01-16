<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet9020000.Servlet9020110"%>
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
		<title>登录日志</title>
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
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<!--插件脚本End  -->
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'用户名', name:'DLRZ_YHMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
					{ title:'用户ID', name:'DLRZ_YHID' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'登录时间', name:'DLRZ_DLSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'登录IP', name:'DLRZ_DLIP' ,width:200, sortable:true, align:'center',lockDisplay: true  }
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
					url: '<%=basePath%>/Servlet9020110',
					method: 'post',
					params:{CMD : "<%=Servlet9020110.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'DLRZ_DLID',
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
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				loadSearchSelect($('#selectYGMC'), 'YHXX_WJMC', '玩家');
 				//$('#selectBMMC').change(function() {
					//loadSearchSelect($('#selectYGMC'), 'YHXX_WJMC-' + $('#selectBMMC').val(), '员工姓名');
			    //});
				//页面初始化加载数据
				loadGridByBean();
			});
			//定义bean
			function makeBeanIn(strYHID, strKSSJ,strJSSJ) {
				this.DLRZ_YHID = strYHID;
				this.DLRZ_KSSJ = strKSSJ;
				this.DLRZ_JSSJ = strJSSJ;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					$('#selectYGMC').val(),
					$('#txtEditKSSJ').val(),
					$('#txtEditJSSJ').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">人员</th>
					<td><select id="selectYGMC" style="width: 100px"></select></td>
					<th style="width:100px">登录时间</th>
					<td><input id="txtEditKSSJ" type="text" style="width: 120px;height: 16px;" readonly="readonly" class="laydate-icon-default">--<input id="txtEditJSSJ" type="text" style="width: 120px;height: 16px;" readonly="readonly" class="laydate-icon-default"></td>

					<th  style="width:100px"><input type="button" value="查询" id="btnSearch"  class="btn btn-primary btn-sm" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
	</body>
</html>