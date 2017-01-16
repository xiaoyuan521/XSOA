<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020180"%> 
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
		<title>加班审批</title>
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
			var obj;//定义参数对象
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'加班类型', name:'JBLX' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'申请人', name:'SQRXM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请时间', name:'JBSQ_SQSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'加班内容', name:'JBSQ_JBYY' ,width:140, sortable:true, align:'center',lockDisplay: true  },
					{ title:'开始时间', name:'JBSQ_KSSJ' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'结束时间', name:'JBSQ_JSSJ' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'加班小时数', name:'JBSQ_JBXSS' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'下一审批人', name:'XYSPXM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'状态', name:'JBZT' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'是否完结', name:'SFWJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:80, sortable:true, align:'center',lockDisplay: false, 
						renderer: function(val){
				           return '<button class="btn btn-success btn-xs">审批</button>&nbsp;<button class="btn btn-info btn-xs">详情</button>';
						}
					}
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
					url: '<%=basePath%>/Servlet1020180',
					method: 'post',
					params:{CMD : "<%=Servlet1020180.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'JBSQ_SQID',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: false,
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
				mmg.on('cellSelected', function(e, item, rowIndex, colIndex) {
					if (item.JBSQ_SFWJ == '1') {
						layer.alert('此申请单已经审批结束！', 0, '友情提示');
		    			return;
					}
					if (item.JBSQ_JBZT == '4') {
						layer.alert('此申请单已驳回，等待申请人重新提交！', 0, '友情提示');
		    			return;
					}
					if ($('#selectCzlx').val() == '1' && (item.JBSQ_JBZT == '0' || item.JBSQ_JBZT == '2')) {
						layer.alert('您暂时不具备此申请单的审批权限！', 0, '友情提示');
		    			return;
					}
					//配置节点
					if($(e.target).is('.btn-success')){
						e.stopPropagation();  //阻止事件冒泡
						iframeLayerOpen("<%=basePath%>/bin/jsp/1020000/1020181.jsp?dataId=" + item.JBSQ_SQID, "700px", "500px");
					}
					if($(e.target).is('.btn-info')){
						e.stopPropagation();  //阻止事件冒泡
						btn_Info(item.JBSQ_SQID,"SPLC_KQJB");
					}
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				//页面初始化加载数据
				loadGridByBean();
			    //初始化下拉列表
			    loadSearchSelect($('#selectJblx'), 'TYPE_JBLX', '加班类型');
				loadSearchSelect($('#selectSpzt'), 'TYPE_SPZT', '审批状态');
			});
			function btn_Info(strSQID,strREG) {
				obj = new Object();
			    obj.optionFlag = strREG;
			    obj.SQID = strSQID;
				$.layer({
			        type: 2,
			        title: false,
			        maxmin: false,
			        shadeClose: false, //开启点击遮罩关闭层
			        area : ["500px" , "500px"],
			        offset : ['20px', ''],
			        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
			        },
			        iframe: {src: "<%=basePath%>/bin/jsp/common/ProcessLogs.jsp"}
			    });
			}
			//定义查询bean
			function makeBeanIn(strJBSQ_SQID, strJBSQ_JBLX, strJBSQ_SQR,
					strJBSQ_KSSJ, strJBSQ_JSSJ, strJBSQ_JBZT, strCZLX) {
				this.JBSQ_SQID = strJBSQ_SQID;
				this.JBSQ_JBLX = strJBSQ_JBLX;
				this.JBSQ_SQR = strJBSQ_SQR;
				this.JBSQ_KSSJ = strJBSQ_KSSJ;
				this.JBSQ_JSSJ = strJBSQ_JSSJ;
				this.JBSQ_JBZT = strJBSQ_JBZT;
				this.CZLX = strCZLX;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					"",
					$('#selectJblx').val(),
					"",
					$('#txtSelectKssj').val(),
					$('#txtSelectJssj').val(),
					$('#selectSpzt').val(),
					$('#selectCzlx').val()
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
					<th style="width:100px">操作类型</th>
					<td>
						<select id="selectCzlx" style="width: 100px">
							<option value="0">待审批</option>
							<option value="1">已审批</option>
						</select>
					</td>
					<th style="width:100px">加班类型</th>
					<td><select id="selectJblx" style="width: 100px"></select></td>
					<th style="width:100px">状态</th>
					<td><select id="selectSpzt" style="width: 100px"></select></td>
					<th style="width:100px">开始时间</th>
					<td>
						<input id="txtSelectKssj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
					</td>
					<th style="width:100px">结束时间</th>
					<td>
						<input id="txtSelectJssj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
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