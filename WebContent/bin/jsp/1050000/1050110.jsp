<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1050000.Servlet1050110"%> 
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
		<title>日报申请</title>
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
			//获的当天日期
			var myDate = new Date();   
			var year = "";
			var month = "";
			var day = "";
			var timeStr = "";
			year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
			month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
			if(month<10)
				{
				month = "0"+month;
				}
			day = myDate.getDate();        //获取当前日(1-31)
			if(day < 10) {
				day = "0"+day;
			}
			timeStr = year+"-"+month+"-"+day;
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
					{ title:'审批人', name:'SPR' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'审批部门', name:'SPBM' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'日报状态', name:'RBZT' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:110, sortable:true, align:'center',lockDisplay: false, renderer: function(val,item){
       					if(item.RBGL_ZTID=="0" && item.RBGL_SQRQ == timeStr){
       						return '<button class="btn btn-info btn-xs">详情</button>&nbsp;<button class="btn btn-success btn-xs">编辑</button>&nbsp;<button class="btn btn-warning btn-xs">删除</button>';
       					}else if(item.RBGL_ZTID=="4" && item.RBGL_SQRQ == timeStr){
       						return '<button class="btn btn-info btn-xs">详情</button>&nbsp;<button class="btn btn-success btn-xs">编辑</button>';
       					}else{
       						return '&nbsp;<button class="btn btn-info btn-xs">详情</button>';
       					}
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
					url: '<%=basePath%>/Servlet1050110',
					method: 'post',
					params:{CMD : "<%=Servlet1050110.CMD_SELECT%>"},
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
					if ($(e.target).is('.btn-info')) {
						e.stopPropagation();  //阻止事件冒泡
			   		 	btn_Info(item.RBGL_RBID,item.SQR,item.SQBM,item.RBGL_SQRQ);
					}
					if ($(e.target).is('.btn-success')) {
						e.stopPropagation();  //阻止事件冒泡
						btn_Add();
						//obj = new Object();
						//obj.optionFlag = "Send";
						//iframeLayerOpen("<%=basePath%>/bin/jsp/1050000/1050111.jsp");
					}
					if ($(e.target).is('.btn-warning')) {
						e.stopPropagation();  //阻止事件冒泡
			    		layer.confirm('是否删除日报信息？', function() {
							//layer.close(layer.index);
							if (deleteDayReport(item.RBGL_RBID) == true) {
								//重新查询数据
								loadGridByBean();
								mmg.deselect('all');
							}
						});
					}
					
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				//页面初始化加载数据
				loadGridByBean();
				//初始化下拉列表
				loadSearchSelect($('#selectRbzt'), 'TYPE_RBZT', '日报状态');
			});
				//详情按钮点击方法
			function btn_Info(RBID,SQR,SQBM,SQRQ) {
				obj = new Object();
			    obj.optionFlag = "Info";
			    obj.rbid = RBID;
			    obj.sqr = SQR;
			    obj.sqbm = SQBM;
			    obj.sqrq = SQRQ;
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
			        iframe: {src: "<%=basePath%>/bin/jsp/1050000/1050111.jsp"}
			    });
			}
			//新增按钮点击方法
			function btn_Add() {
				obj = new Object();
				obj.optionFlag = "Send";
				//iframeLayerOpen("<%=basePath%>/bin/jsp/1050000/1050111.jsp");
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
			        iframe: {src: "<%=basePath%>/bin/jsp/1050000/1050111.jsp"}
			    });
			}
			//定义bean
			function makeBeanIn(strRBGL_RBID,strRBGL_SQRQ, strRBGL_ZTID) {
				this.RBGL_RBID = strRBGL_RBID;
				this.RBGL_SQRQ = strRBGL_SQRQ;
				this.RBGL_ZTID = strRBGL_ZTID;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					"",
					$('#txtSelectSqrq').val(),
					$('#selectRbzt').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//删除日报方法
			function deleteDayReport(strRBID) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					strRBID,
					"",
					""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1050110",
					data: {
						CMD    : "<%=Servlet1050110.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除日报信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除日报信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除日报信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">上报日期</th>
					<td><input id="txtSelectSqrq" type="text" style="width: 100px;height: 16px;font-size:14px;line-height:14px;" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></td>
					<th style="width:100px">日报状态</th>
					<td>
						<select id="selectRbzt" style="width: 100px">	
						</select>
					</td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch"  class="btn btn-primary btn-sm" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion">
			<legend>编辑</legend>
			<div id="buttonCanvas" class="gToolbar gTbrCenter ">
				<input type="button" value="我的日报" onclick="btn_Add()" class="btn btn-primary"/>
			</div>
		</fieldset>
	</body>
</html>