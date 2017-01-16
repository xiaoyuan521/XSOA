<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.common.PersonStructServlet"%>	
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
<title>员工信息结构</title>
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
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

div.content_wrap {width: 400px;height:350px;}
div.content_wrap div.left{float: left;width: 250px;}
div.content_wrap div.right{float: right;width: 340px;}
div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}

ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:350px;overflow-y:scroll;overflow-x:auto;}
ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
ul.log.small {height:45px;}
ul.log li {color: #666666;list-style: none;padding-left: 10px;}
ul.log li.dark {background-color: #E3E3E3;}

/* ruler */
div.ruler {height:20px; width:220px; background-color:#f0f6e4;border: 1px solid #333; margin-bottom: 5px; cursor: pointer}
div.ruler div.cursor {height:20px; width:30px; background-color:#3C6E31; color:white; text-align: right; padding-right: 5px; cursor: pointer}
</style>
<script type="text/javascript">
var currNodeId;
var currNodeType;
var currNodeName;
var parentNodeName;
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

$(document).ready(function() {
	//加载组织树
	$.fn.zTree.init($("#YGTree"), setting);
})



/* 点击树节点函数:用于捕获节点被点击的事件回调函数
 * event:标准的 js event 对象
 * treeId:对应 zTree 的 treeId，便于用户操控
 * treeNode:被点击的节点 JSON 数据对象
 * clickFlag:节点被点击后的选中操作类型
 */
function zTreeOnClick(event, treeId, treeNode) {
	var id = treeNode.id;
	var cmd = treeNode.cmd;
	
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
	currNodeId = treeNode.id;
	currNodeType = treeNode.type;
	currNodeName = treeNode.name;
	
	if(currNodeType=="CMD_YG"){
		parentNode = treeNode.getParentNode();
		parentNodeName=parentNode.name;
	}else{
		parentNodeName = "";
	}
}
function returnValue(){
	if(!currNodeId){
		layer.alert('请选择组织或个人！', 0, '友情提示');
		//alert("请选择组织或个人！");
		return false;
	}
	window.parent.currNodeId = currNodeId;
	window.parent.currNodeType = currNodeType;
	window.parent.currNodeName = currNodeName;
	window.parent.parentNodeName = parentNodeName;
	closeW();
}

function closeW(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

</script>
</head>
<body>
	<div id="tree_container" class="zTreeDemoBackground left">
		<ul id="YGTree" class="ztree"></ul>
	</div>
	<div>
		<input type="button" value="确定" id="btnOK" onclick="returnValue()" /> 
		<input type="button" value="取消" id="btnCancel" onclick="closeW()" /> 
	</div>

</body>
</html>