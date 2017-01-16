<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020120"%> 
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
		<title>签到记录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<!--插件脚本End  -->
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'所在部门', name:'BMXX_BMMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'员工', name:'YGXM' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'签到日期', name:'QDJL_QDRQ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'签到时间', name:'QDJL_QDSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'类型', name:'QDLX' ,width:80, sortable:true, align:'center',lockDisplay: true  }
				];
				//计算表格高度
				intheight = document.documentElement.clientHeight - $('#selectRegion').height() - 80; 
				if (intheight < 100) {
					intheight = 100;
				}
				//初始化表格
	   			mmg = $('.mmg').mmGrid({
	   				height: intheight,
	   				cols: cols,
					url: '<%=basePath%>/Servlet1020120',
					method: 'post',
					params:{CMD : "<%=Servlet1020120.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'QDJL_QDID',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: false,
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
			    //初始化下拉列表
			    var dataBean = getBaseData();
			    if (dataBean.ZWDJ == '0') {//普通员工
			    	$('#selectBmmc').append('<option value="' + dataBean.BMXX_BMID + '">' + dataBean.BMXX_BMMC + '</option>');
			    	$('#selectBmmc option[value="000"]').remove();
			    	$('#selectBmmc').attr('disabled', 'disabled');
			    	$('#selectYgxm').append('<option value="' + dataBean.YGID + '">' + dataBean.YGXM + '</option>');
			    	$('#selectYgxm option[value="000"]').remove();
			    	$('#selectYgxm').attr('disabled', 'disabled');
			    	//页面初始化加载数据
					loadGridByBean();
			    } else {//管理人员
			    	loadSearchSelect($('#selectBmmc'), 'TYPE_YGPJ_BMMC-' + dataBean.YGID, '所在部门');
			    	$('#selectBmmc').change(function() {
						loadSearchSelect($('#selectYgxm'), 'TYPE_YGPJ_BMYG-' + $('#selectBmmc').val() + '-' + dataBean.YGID, '员工');
						$('#selectYgxm').append('<option value="' + dataBean.YGID + '">' + dataBean.YGXM + '</option>');
				    });
			    }
			});
			//获取登陆用户基础数据
			function getBaseData() {
				var baseData;
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020120",
					data: {
						CMD    : "<%=Servlet1020120.CMD_GET_BASE%>"
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							baseData = response[1];
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：加载数据异常，请联系系统管理员！', 1, 0);
						}
					}
				});
				return baseData;
			}
			//定义查询bean
			function makeBeanIn(strBMXX_BMID, strQDJL_YGID,
					strKSSJ, strJSSJ, strQDJL_QDLX) {
				this.BMXX_BMID = strBMXX_BMID;
				this.QDJL_YGID = strQDJL_YGID;
				this.KSSJ = strKSSJ;
				this.JSSJ = strJSSJ;
				this.QDJL_QDLX = strQDJL_QDLX;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					$('#selectBmmc').val(),
					$('#selectYgxm').val(),
					$('#txtSelectKssj').val(),
					$('#txtSelectJssj').val(),
					$('#selectQdlx').val()
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
					<th style="width:100px">部门</th>
					<td><select id="selectBmmc" style="width: 100px"></select></td>
					<th style="width:100px">员工</th>
					<td>
						<select id="selectYgxm" style="width: 100px">
							<option value="000">所有</option>
						</select>
					</td>
					<th style="width:100px">签到日期</th>
					<td>
						<input id="txtSelectKssj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
					</td>
					<th style="width:50px">～</th>
					<td>
						<input id="txtSelectJssj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
					</td>
					<th style="width:100px">类型</th>
					<td>
						<select id="selectQdlx">
							<option value="000">请选择</option>
							<option value="0">签到</option>
							<option value="1">签退</option>
						</select>
					</td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
	</body>
</html>