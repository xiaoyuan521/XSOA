<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.common.PersonStructServlet"%>
<%@ page import="com.xsoa.servlet.servlet1090000.Servlet1090111"%>		
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批设定</title>
<link rel="stylesheet" href="<%=basePath%>/bin/css/control/buttonStyle.css?r=<%=radom %>" type="text/css">
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/tablesize.css?r=<%=radom %>" type="text/css">

<link rel="stylesheet" href="<%=basePath%>/bin/plugin/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmPaginator.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
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
div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}

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
 height: 570px;
 width: 235px;
 border: 1px solid #999999;
}

#grid_container {
 float: right;
 height: 570px;
 width: 560px;
 border: 1px solid #999999;
 position: absolute;top: 0;right: 0;
}
</style>
<script type="text/javascript">
var mmg1;//定义表格对象
var currNodeId="";
var currNodeType="";
var currNodeName="";
var parentNodeName="";
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
function returnValue(){
	window.parent.currNodeId = currNodeId;
	window.parent.currNodeType = currNodeType;
	window.parent.currNodeName = currNodeName;
	window.parent.parentNodeName = parentNodeName;
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

var paraLCXX_LCID = "";
$(document).ready(function() {
	$("#lblLCXX_SQMC").html(parent.pLCXX_SQMC);
	paraLCXX_LCID = getQueryString("LCXX_LCID");
	//加载组织树
	$.fn.zTree.init($("#YGTree"), setting);
	//定义表格列值
	var cols = [
		{ title:'审批级别', name:'LCMX_SPJB' ,width:100, sortable:true, align:'center',lockDisplay: true },
		{ title:'审批部门', name:'BMXX_BMMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
		{ title:'审批人', name:'YGXX_XM' ,width:80, sortable:true, align:'center',lockDisplay: true  },
		{ title:'可否终结', name:'LCMX_KFZJ' ,width:50, sortable:true, align:'center',lockDisplay: true  },
		{ title:'操作', name:'' ,width:80, sortable:true, align:'center',lockDisplay: false, 
			renderer: function(val){
	           return '<button class="btn btn-success btn-xs">删除</button>'
			}
		}
	];
	
	//计算表格高度
	intheight = document.documentElement.clientHeight - 80; 
	if (intheight < 100) {
		intheight = 100;
	}
	
	//初始化表格
	mmg1 = $('.mmg1').mmGrid({
		height: intheight,
		cols: cols,
		url: '<%=basePath%>/Servlet1090111',
		method: 'post',
		params:{CMD : "<%=Servlet1090111.CMD_SELECT%>"},
		remoteSort:true,
		sortName: 'LCXX_GLGN,LCXX_SQLB',
		sortStatus: 'ASC',
		root: 'items',
		multiSelect: false,
		checkCol: false,
		indexCol: true,
		indexColWidth: 35,
		fullWidthRows: true,
		autoLoad: false,
		plugins: [
			$('#pg1').mmPaginator({
				limit:30
			})
		]
	});
	
	//定义表格触发事件
	mmg1.on('loadSuccess',function(e, data) {
	}).on('cellSelected', function(e, item, rowIndex, colIndex){
		var arrList = mmg1.row(rowIndex);
		//配置节点
		if($(e.target).is('.btn-success')){
			e.stopPropagation();  //阻止事件冒泡
			var strLCMX_JDID = item.LCMX_JDID;
			layer.confirm('是否删除审批人？', function() {
				layer.close(layer.index);
				if (deleteLCMX(strLCMX_JDID) == true) {
					//重新查询数据
					loadGridByBean();
					mmg.deselect('all');
				}
			});
		}
	});
	//定义保存按钮点击事件
	$('#btnAdd').on('click', function() {
		if (funEditCheck() == false) return;
		if (insertLCMX() == true) {
			//重新查询数据
			loadGridByBean();
			mmg.deselect('all');
		}
	});
	loadGridByBean();
})
//定义bean
function makeBeanIn(strLCMX_JDID, strLCMX_LCID, strLCMX_SPJB, strLCMX_SPID, strLCMX_KFZJ) {
	this.LCMX_JDID = strLCMX_JDID;	
	this.LCMX_LCID = strLCMX_LCID;	
	this.LCMX_SPJB = strLCMX_SPJB;
	this.LCMX_SPID = strLCMX_SPID;
	this.LCMX_KFZJ = strLCMX_KFZJ;
}

//数据加载方法
function loadGridByBean() {
	var beanIn = new makeBeanIn(
		"",
		paraLCXX_LCID,
		"",
		currNodeId,
		""
	);
	//重新查询数据
	mmg1.load({
		beanLoad  :  JSON.stringify(beanIn)
	});
}

//验证编辑输入数据
function funEditCheck() {
	if (!currNodeId == true) {
		layer.alert('请选择审批人！', 0, '友情提示', function() {
			layer.close(layer.index);
		});
		return false;
	}
	
	if (checkDataExist() == true) {
		layer.alert('审批人已存在，不能添加！', 0, '友情提示');
		return false;
	}
	return true;
}

//验证重复方法
function checkDataExist() {
	var blnRet = false;
	var beanIn = new makeBeanIn(
			"",
			paraLCXX_LCID,
			"",
			currNodeId,
			""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090111",
		data: {
			CMD    : "<%=Servlet1090111.CMD_CHK_EXIST%>",
			BeanIn : JSON.stringify(beanIn)
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

//新增职务方法
function insertLCMX() {
	var blnRet = false;
	var chkValue = "0";
	
	if($('#chkKFZJ').is(':checked')==true){
		chkValue = "1";
	}
	
	var beanIn = new makeBeanIn(
		"",
		paraLCXX_LCID,
		$('#selectSPJB').val(),
		currNodeId,
		chkValue
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090111",
		data: {
			CMD    : "<%=Servlet1090111.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：添加审批人成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：添加审批人失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：添加审批人出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}

//删除部门方法
function deleteLCMX(strLCMX_JDID) {
	var blnRet = false;
	
	var beanIn = new makeBeanIn(
		strLCMX_JDID,
		"",
		"",
		"",
		""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090111",
		data: {
			CMD    : "<%=Servlet1090111.CMD_DELETE%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除审批人成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：删除审批人失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：删除审批人出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}

</script>
</head>
<body>
	<div id="tree_container" class="zTreeDemoBackground left">
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
		<input type="button" value="添加" id="btnAdd" class="btn btn-primary btn-sm"/><br/>
		<input type="checkbox" id="chkKFZJ" />可否终结<br/>
		<div style="height:35px;"></div>
		
		<label>申请者:</label>
		<label id = "lblLCXX_SQMC"></label><br/>
	</div>
	<div id="grid_container">
		<table id="mmg1" class="mmg1"></table>
		<div id="pg1" style="text-align: right;"></div>
	</div>

</body>
</html>