<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1010000.Servlet1010130"%> 
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
		<title>调动查询</title>
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
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'员工', name:'YGXM' ,width:150, sortable:true, align:'center',lockDisplay: true },
					{ title:'原部门', name:'YBMMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'原职务', name:'YZWMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'原职务等级', name:'ZWDD_YZWDJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'新部门', name:'XBMMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'新职务', name:'XZWMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'新职务等级', name:'ZWDD_XZWDJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'备注信息', name:'ZWDD_BZXX' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作人', name:'CJRXM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作时间', name:'ZWDD_CJSJ' ,width:150, sortable:true, align:'center',lockDisplay: true  }
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
					url: '<%=basePath%>/Servlet1010130',
					method: 'post',
					params:{CMD : "<%=Servlet1010130.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'ZWDD_UUID',
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
			    //初始化下拉列表
				loadSearchSelect($('#selectBmmc'), 'TYPE_BMMC', '所在部门');
				loadSearchSelect($('#selectYgxm'), 'TYPE_YGXM', '员工姓名');
			    $('#selectBmmc').change(function() {
			    	if ($('#selectBmmc').val() == '000') {
			    		loadSearchSelect($('#selectYgxm'), 'TYPE_YGXM', '员工姓名');
			    	} else {
						loadSearchSelect($('#selectYgxm'), 'TYPE_BMYG-' + $('#selectBmmc').val(), '员工姓名');
			    	}
			    });
			  	//页面初始化加载数据
				loadGridByBean();
			});
			//定义查询bean
			function makeBeanIn(strBMID, strZWDD_YGID) {
				this.BMID = strBMID;
				this.ZWDD_YGID = strZWDD_YGID;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					$('#selectBmmc').val(),
					$('#selectYgxm').val()
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
					<th style="width:100px">所在部门</th>
					<td><select id="selectBmmc" style="width: 100px"></select></td>
					<th style="width:100px">员工姓名</th>
					<td>
						<select id="selectYgxm" style="width: 100px">
							<option value="000">所有</option>
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
	</body>
</html>