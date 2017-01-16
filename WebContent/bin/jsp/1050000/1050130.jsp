<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1050000.Servlet1050130"%> 
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
		<title>日报查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmGrid.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmPaginator.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/tablesize.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/ztree/zTreeStyle.css" type="text/css">
		<!--表格样式End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js"></script>
		<!--表格脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmGrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmPaginator.js"></script>
		<!--表格脚本End  -->
		<!--插件脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<!--插件脚本End  -->
		<style>
			html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {
				margin: 0;padding: 0;border: 0;outline: 0;font-weight: inherit;font-style: inherit;font-size: 100%;font-family: inherit;vertical-align: baseline;}
			body {color: #2f332a;font: 15px/21px Arial, Helvetica, simsun, sans-serif;background: #f0f6e4 \9;}
			h1, h2, h3, h4, h5, h6 {color: #2f332a;font-weight: bold;font-family: Helvetica, Arial, sans-serif;padding-bottom: 5px;}
			h1 {font-size: 24px;line-height: 34px;text-align: center;}
			h2 {font-size: 14px;line-height: 24px;padding-top: 5px;}
			h6 {font-weight: normal;font-size: 12px;letter-spacing: 1px;line-height: 24px;text-align: center;}
			a {color:#3C6E31;text-decoration: underline;}
			a:hover {background-color:#3C6E31;color:white;}
			input.radio {margin: 0 2px 0 8px;}
			input.radio.first {margin-left:0;}
			input.empty {color: lightgray;}
			code {color: #2f332a;}
			.highlight_red {color:#A60000;}
			.highlight_green {color:#A7F43D;}
			li {list-style: circle;font-size: 12px;}
			li.title {list-style: none;}
			ul.list {margin-left: 17px;}
			
			div.content_wrap {width: 600px;height:380px;}
			div.content_wrap div.left{float: left;width: 250px;}
			div.content_wrap div.right{float: right;width: 340px;}
			div.zTreeDemoBackground {width:200px;height:362px;text-align:left;}
			
			ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:420px;overflow-y:scroll;overflow-x:auto;}
			ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
			ul.log.small {height:45px;}
			ul.log li {color: #666666;list-style: none;padding-left: 10px;}
			ul.log li.dark {background-color: #E3E3E3;}
			
			/* ruler */
			div.ruler {height:20px; width:220px; background-color:#f0f6e4;border: 1px solid #333; margin-bottom: 5px; cursor: pointer}
			div.ruler div.cursor {height:20px; width:30px; background-color:#3C6E31; color:white; text-align: right; padding-right: 5px; cursor: pointer}
			
			#tree_container {
			 float: left;
			 height: 560;
			 width: 180px;
			 border: 1px solid #999999;
			}
			
			#grid_container {
			 float: right;
			 height: 570px;
			 width: 560px;
			 border: 1px solid #999999;
			}
		</style>
		<script type="text/javascript">
			//组织树结构定义
			var setting = {
					view: {
						selectedMulti: false
					},
					async: {
						enable: true,
						url:"<%=basePath%>/PersonStructServlet",
						autoParam:["id","pId","type"],
						dataFilter: filter
					},
					callback: {
						onClick: zTreeOnClick,
						onClick: onClick
					}
			};
			/* 用于对 Ajax 返回数据进行预处理的函数
		 * treeId:对应 zTree 的 treeId，便于用户操控 
		 * parentNode:进行异步加载的父节点 JSON 数据对象
		 * childNodes:异步加载获取到的数据转换后的 Array(JSON) / JSON / String 数据对象
		 * 返回值是 zTree 支持的JSON 数据结构即可。
		 */
		function filter(treeId, parentNode, childNodes) {
		   if (!childNodes) return null;
		   
		   for (var i=0, l=childNodes.length; i<l; i++)
			    //判断childNodes是否为空，如果空，则返回
			    if(nullSafe(childNodes[i])==""){
			    	return;
			    }else{
			    	childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			    }
		   return childNodes;
		}
		
		
		/* 点击树节点函数:用于捕获节点被点击的事件回调函数
		 * event:标准的 js event 对象
		 * treeId:对应 zTree 的 treeId，便于用户操控
		 * treeNode:被点击的节点 JSON 数据对象
		 * clickFlag:节点被点击后的选中操作类型
		 */
		function zTreeOnClick(event, treeId, treeNode) {
			var id = treeNode.id;
			var cmd = treeNode.cmd;
			alert("id="+id);
			if(id!=undefined){
				$.ajax({
					async:false,
					type: "post",
					dataType: "json",
					url: "<%=basePath%>/PersonStructServlet",
						data : {
							id : id,
							type : type
						},
						complete : function(response, textStatus) {
							
						}
				});
			}
		}
		function onClick(event, treeId, treeNode, clickFlag){
		
			currNodeType = treeNode.type;
			if(currNodeType=="CMD_YG"){
				parentNode = treeNode.getParentNode();
				parentNodeName = parentNode.name;
				currNodeId = treeNode.id;
				currNodeName = treeNode.name;
				$("#lblLCXX_SPMC").html(parentNodeName+"-"+currNodeName);
			}else{
				parentNodeName = "";
				parentNode = "";
				currNodeId = "";
				currNodeName = "";
				$("#lblLCXX_SPMC").html("");
			}
			
		}
			
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var obj;//参数对象
			$(document).ready(function() {
				//加载组织树
				$.fn.zTree.init($("#YGTree"), setting);
				//定义表格列值
				var cols = [
				    { title:'申请人', name:'SQR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请部门', name:'SQBM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请日期', name:'RBGL_SQRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请内容', name:'RBGL_JHNR' ,width:200, sortable:true, align:'center',lockDisplay: true  },
					{ title:'总结内容', name:'RBGL_ZJNR' ,width:200, sortable:true, align:'center',lockDisplay: true  },
					{ title:'审批人', name:'SPR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'审批部门', name:'SPBM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'日报状态', name:'RBZT' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:40, sortable:true, align:'center',lockDisplay: false, renderer: function(val,item){			
       					return '<img class="img-approve" title="审批" src="<%=basePath%>/bin/img/common/approval.gif"></img>';					
      			 	}}
				];
				//计算表格高度
				intheight = document.documentElement.clientHeight - 40; 
				if (intheight < 100) {
					intheight = 100;
				}
				//初始化表格
	   			mmg = $('.mmg').mmGrid({
	   				height: intheight,
	   				cols: cols,
					url: '<%=basePath%>/Servlet1050130',
					method: 'post',
					params:{CMD : "<%=Servlet1050130.CMD_SELECT%>"},
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
					if ($(e.target).is('.img-approve')) {
						e.stopPropagation();  //阻止事件冒泡
						obj = new Object();
			    		obj = item;
						search(item.RBGL_RBID,item.SQR,item.SQBM,item.RBGL_SQRQ,item.RBGL_SPR,item.RBGL_JHNR,item.RBGL_ZJNR,item.RBZT,item.RBGL_ZTID);
					}
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				//页面初始化加载数据
				loadGridByBean();
			});
			//查询方法
			function search(strRBID,strSQR,strSQBM,strSQRQ,strSPR,strJHNR,strZJNR,strRBZT,strZTID) {
				obj = new Object();
				obj.rbid = strRBID;
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
			        area : ["800px" , "500px"],
			        offset : ['20px', ''],
			        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
			        },
			        end: function(){//关闭按钮
			        	loadGridByBean();
			        },
			        iframe: {src: "<%=basePath%>/bin/jsp/1050000/1050131.jsp"}
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
		<div id="tree_container" class="zTreeDemoBackground left">
			<ul>选择审批人</ul>
			<ul id="YGTree" class="ztree"></ul>
			<label>审批人:</label>
			<label id = "lblLCXX_SPMC"></label><br/>
			<select id="selectSPJB" style="width: 100px">
				<option value ="1">第1审批人</option>
				<option value ="2">第2审批人</option>
				<option value ="3">第3审批人</option>
				<option value ="4">第4审批人</option>
				<option value ="5">第5审批人</option>
				<option value ="6">第6审批人</option>
				<option value ="7">第7审批人</option>
				<option value ="8">第8审批人</option>
				<option value ="9">第9审批人</option>
			</select>
			<input type="button" value="添加" id="btnAdd"/><br/>
			<input type="checkbox" id="chkKFZJ" />可否终结<br/>
			<div style="height:35px;"></div>
			
			<label>申请者:</label>
			<label id = "lblLCXX_SQMC"></label><br/>
		</div>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
	</body>
</html>