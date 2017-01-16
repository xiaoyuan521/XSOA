<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet9010000.Servlet9010130"%>
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
		<title>权限管理</title>
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
			div.zTreeDemoBackground {width:200px;height:100%;text-align:left;}

			ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:200px;height:430px;overflow-y:scroll;overflow-x:auto;}
			ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
			ul.log.small {height:45px;}
			ul.log li {color: #666666;list-style: none;padding-left: 10px;}
			ul.log li.dark {background-color: #E3E3E3;}

			/* ruler */
			div.ruler {height:20px; width:220px; background-color:#f0f6e4;border: 1px solid #333; margin-bottom: 5px; cursor: pointer}
			div.ruler div.cursor {height:20px; width:30px; background-color:#3C6E31; color:white; text-align: right; padding-right: 5px; cursor: pointer}
			.q_button{background:#52627c;color:#fff;border:0;padding:5px 20px;cursor: pointer;border-radius: 4px;}

			#tree_container {
			 float: left;
			 height: 100%;
			 width: 211px;
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
						url:"<%=basePath%>/Servlet9010130",
						autoParam:["id","type"],
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
					url: "<%=basePath%>/Servlet9010130",
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
			if(currNodeType=="CMD_JS"){
				parentNode = treeNode.getParentNode();
				parentNodeName = parentNode.name;
				currNodeId = treeNode.id;
				currNodeName = treeNode.name;
				$("#txtJSMC").html(parentNodeName+"-"+currNodeName);
				$("#txtJSID").val(currNodeId);

				$("input[name='checkcd_f']").prop("checked",false);//清除选中
				$("input[name='checkcd_z']").prop("checked",false);//清除选中
				getMenuList();
				setMenuList(treeNode.id);
				setButtonStatus("2");
			}else{
				parentNodeName = "";
				parentNode = "";
				currNodeId = "";
				currNodeName = "";
				$("#txtJSMC").html("");
				$("#txtJSID").val("");
				setButtonStatus("1");
				$("input[name='checkcd_f']").prop("checked",false);//清除选中
				$("input[name='checkcd_z']").prop("checked",false);//清除选中
			}

		}
			var obj;//参数对象
			$(document).ready(function() {
				getMenuList();//获得菜单列表
				setButtonStatus("1");
				//加载组织树
				$.fn.zTree.init($("#JSTree"), setting);
				    $('#btnSave').on('click', function(){
						layer.confirm('是否保存设置？', function() {
							layer.close(layer.index);
							if (saveJSQX() == true) {
								window.location.href='<%=basePath%>/bin/jsp/9010000/9010130.jsp';
							}
						});
    				});
    				 $('#btnReset').on('click', function(){
						layer.confirm('是否重置？', function() {
							layer.close(layer.index);
							window.location.href='<%=basePath%>/bin/jsp/9010000/9010130.jsp';
						});
    				});
			});
			function saveJSQX(){
				var blnRet = false;
				var strJSID = $("#txtJSID").val();
			 	var strMENUS = "";
			 	$("input[name='checkcd_f']:checked").each(function(){
			   		 strMENUS+=$(this).val()+',';
			    });
				$("input[name='checkcd_z']:checked").each(function(){
			   		 strMENUS+=$(this).val()+',';
			    });
			    if(strMENUS==""){
					strMENUS = ",";
				}
			    $.ajax({
			      async     : false,
			      type      : "post",
			      dataType  : "json",
			      url: "<%=basePath%>/Servlet9010130",
			      data: {
			         type      : "<%=Servlet9010130.CMD_RELATION%>",
			         JSID      : strJSID,
			         MENUS : strMENUS
			      },
			      complete :function(response){},
			      success: function(response){
			          var strResault = response[0];
			          if(strResault=="CMD_OK"){
			          	 layer.msg('恭喜：设置角色权限成功！', 1, 9);
			             blnRet = true;
			          }else{
			          	 layer.msg('对不起：设置角色权限失败！', 1, 8);
			             blnRet = false;
			          }
			      }
			   });
			   return blnRet;
			}
			function setMenuList(strJSID){
					$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet9010130",
					data: {
						type    : "<%=Servlet9010130.CMD_JSMENU_LIST%>",
					    JSID     :strJSID
					},
					complete :function(response){},
					success: function(response){
						var list = response[0];
						$.each(list, function(k, v) {
							$("input[name='checkcd_f']").each(function () {
			                    if ($(this).val() == v.JSQX_CDID) {
			                        $(this).attr("checked",true);
			                    }
			                });
							$("input[name='checkcd_z']").each(function () {
			                    if ($(this).val() == v.JSQX_CDID) {
			                        $(this).attr("checked",true);
			                    }
			                });
						});
					}
				});
			}
			function getMenuList(){
				$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet9010130",
				data: {
					type    : "<%=Servlet9010130.CMD_MENU_LIST%>"
				},
				complete :function(response){},
				success: function(response){
					var list = response[0];
					var listHtml = "";
					$.each(list, function(k, v) {
						if(v.MENU_CDCJ == "0"){
							listHtml+="<br><input type='checkbox' style='margin:0 10px 10px 0; width: 19px;height: 25px;' name='checkcd_f' id='"+v.MENU_CDID+"' value='"+v.MENU_CDID+"' onclick='getCheck("+v.MENU_CDID+")'/><font style='font-size:18px;font-weight:bold'>"+v.MENU_CDMC+"</font><br>";
						}else if(v.MENU_CDCJ == "1"){
							listHtml+="<input type='checkbox' style='margin:0 10px 10px 20px' name='checkcd_z' id='"+v.MENU_CDID+"' value='"+v.MENU_CDID+"'/>"+v.MENU_CDMC+"";
						}

						//if((k!=0)&&((k+1)%7==0)){
						//listHtml+="<br>";
						//}
					});
					//listHtml +="<br><input type='button' id='btnSave' class='q_button' value='保存'>&nbsp;<input type='button' id='btnReset' class='q_button' value='重置'>";
					$("#sp").html(listHtml);
				}
				});
			}
			function getCheck(str){
				var strcdid = str+"";
				if(document.getElementById(str).checked){
					$("[id^='"+strcdid.substr(0,3)+"']").removeAttr("disabled");
				}else{
					$("[id^='"+strcdid.substr(0,3)+"']").attr("disabled","disabled");
					$("[id^='"+strcdid.substr(0,3)+"']").prop("checked",false);//清除选中
       				$("#"+str).removeAttr("disabled");
				}
			}
			//设置按钮状态
			function setButtonStatus(strFlag) {
				if (strFlag == "1") {//初始状态-所有checkbox不可用
					$("input[name='checkcd_f']").prop("disabled", true);
					$("input[name='checkcd_z']").prop("disabled", true);
					$('#btnSave').attr("disabled","disabled");
					$('#btnSave').css("background","#959494");
				} else if (strFlag == "2") {//查询后/返回
					$("input[name='checkcd_f']").prop("disabled", false);
					$("input[name='checkcd_z']").prop("disabled", false);
					$("input[name='checkcd_f']").each(function(){
	    				 //if($(this).attr("checked")==true){
	    				 if(!$(this).is(':checked')) {
       						$("[id^='"+$(this).val().substr(0,3)+"']").attr("disabled","disabled");
       						$(this).removeAttr("disabled");
    					 }
    				});
					$('#btnSave').removeAttr("disabled");
					$('#btnSave').css("background","#52627c");
				} else if (strFlag == "3") {

				}
			}
		</script>
	</head>
	<body>
		<div id="tree_container" class="zTreeDemoBackground left">
			<ul id="JSTree" class="ztree"></ul>
			<label>当前角色:</label>
			<input type="hidden" id="txtJSID"/>
			<label id = "txtJSMC"></label><br/>
		</div>
		<div style="overflow:auto;height:500px;" class="right"  id="sp">

		</div>
		<table>
			<tr>
			<td>
			</td>
			<td>
				<input type='button' id='btnSave' class='q_button' value='保存'>&nbsp;<input type='button' id='btnReset' class='q_button' value='重置'>
			</td>
			</tr>
		</table>
	</body>
</html>