<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1050000.Servlet1050120"%> 
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
		<title>日报审批</title>
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
			var obj;//参数对象
			$(document).ready(function() {
				//定义表格列值
				var cols = [
				    { title:'申请人', name:'SQR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请部门', name:'SQBM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请日期', name:'RBGL_SQRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请内容', name:'RBGL_JHNR' ,width:200, sortable:true, align:'center',lockDisplay: true  },
					{ title:'总结内容', name:'RBGL_ZJNR' ,width:200, sortable:true, align:'center',lockDisplay: true  },
					{ title:'日报状态', name:'RBZT' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:20, sortable:true, align:'center',lockDisplay: false, renderer: function(val,item){			
       					return '<button class="btn btn-success btn-xs">审批</button>';					
      			 	}}
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
					url: '<%=basePath%>/Servlet1050120',
					method: 'post',
					params:{CMD : "<%=Servlet1050120.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'RBGL_RBID',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: true,
					indexCol: true,
					indexColWidth: 35,
					fullWidthRows: true,
					autoLoad: false,
					nowrap:true,
					plugins: [
						$('#pg').mmPaginator({
							limit:30
						})
					]
				});
	   			//定义单元格点击事件
				mmg.on('cellSelected', function(e, item, rowIndex, colIndex) {
					if ($(e.target).is('.btn-success')) {
						e.stopPropagation();  //阻止事件冒泡
						obj = new Object();
			    		obj = item;
						approve(item.RBGL_RBID,item.RBGL_SQR,item.SQR,item.SQBM,item.RBGL_SQRQ,item.RBGL_SPR,item.RBGL_JHNR,item.RBGL_ZJNR,item.RBZT,item.RBGL_ZTID);
					}
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				//页面初始化加载数据
				loadGridByBean();
			});
			//审批方法
			function approve(strRBID,strSQRID,strSQR,strSQBM,strSQRQ,strSPR,strJHNR,strZJNR,strRBZT,strZTID) {
			//item.RBGL_RBID,item.SQR,item.SQBM,item.RBGL_SQRQ,item.RBGL_JHNR,item.RBGL_ZJNR,item.RBZT
				obj = new Object();
				obj.rbid = strRBID;
				obj.sqrid = strSQRID;
				obj.sqr = strSQR;
				obj.sqbm = strSQBM;
				obj.sqrq = strSQRQ;
				obj.spr = strSPR;
				obj.jhnr = strJHNR;
				obj.zjnr = strZJNR;
				obj.rbzt = strRBZT;
				obj.ztid = strZTID;
				
				$.layer({
			        type: 2,
			        title: false,
			        maxmin: false,
			        shadeClose: false, //开启点击遮罩关闭层
			        area : ["800px" , "535px"],
			        offset : ['20px', ''],
			        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
			        },
			        end: function(){//关闭按钮
			        	loadGridByBean();
			        },
			        iframe: {src: "<%=basePath%>/bin/jsp/1050000/1050121.jsp"}
			    });
			}
			//定义bean
			function makeBeanIn(strRBGL_SQR, strRBGL_SQRQ) {
				this.RBGL_SQR = strRBGL_SQR;
				this.RBGL_SQRQ = strRBGL_SQRQ;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					$('#txtSelectSqr').val(),
					$('#txtSelectSqrq').val()		
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
					<th style="width:100px">申请人</th>
					<td><input id="txtSelectSqr" type="text" style="width: 100px;height: 14px;font-size:14px;line-height:14px;" /></td>
					<th style="width:100px">申请日期</th>
					<td><input id="txtSelectSqrq" type="text" style="width: 100px;height: 16px;font-size:14px;line-height:14px;" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></td>
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