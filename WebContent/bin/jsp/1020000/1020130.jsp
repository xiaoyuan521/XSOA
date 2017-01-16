<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020130"%>
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
		<title>部门信息</title>
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
					{ title:'休假类型', name:'XJSQ_XJMC' ,width:50, sortable:true, align:'center',lockDisplay: true },
					{ title:'申请人', name:'XJSQ_SQRM' ,width:60, sortable:true, align:'center',lockDisplay: true  },
					{ title:'申请日期', name:'XJSQ_SQSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'请假原因', name:'XJSQ_XJYY' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'开始时间', name:'XJSQ_KSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'结束时间', name:'XJSQ_JSSJ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'请假天数', name:'XJSQ_XJTS' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'状态', name:'XJSQ_ZTMC' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'是否完结', name:'XJSQ_SFWJ' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'下一审批人', name:'XJSQ_SPMC' ,width:60, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:40, sortable:true, align:'center',lockDisplay: false, renderer: function(val,item){
						return '<img class="img-info" title="查看" src="<%=basePath%>/bin/img/common/detail.gif"></img>';
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
					url: '<%=basePath%>/Servlet1020130',
					method: 'post',
					params:{CMD : "<%=Servlet1020130.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'XJSQ_SQSJ',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: true,
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
				 //定义单元格点击事件
				mmg.on('cellSelected', function(e, item, rowIndex, colIndex) {
					if ($(e.target).is('.img-info')) {
						e.stopPropagation();  //阻止事件冒泡
			   		 	btn_Info(item.XJSQ_SQID,"SPLC_KQXJ");
					}
				});
				//定义表格触发事件
				mmg.on('loadSuccess',function(e, data) {
					//设置按钮状态
					setButtonStatus('1');
				}).on('click', ':checkbox', function() {//选择checkbox
					if (this.checked == true) {
						var arrList = mmg.selectedRows();
						if (arrList.length > 0) {
							setButtonStatus('2');
						}
					} else {
						setButtonStatus('1');
					}
				}).on('click', 'tr', function(e) { //点击行;
					if (mmg.rowsLength() <= 0) return; //无数据,不进行操作
					var rowIndex = e.target.parentNode.rowIndex;
					if (typeof(rowIndex) == "undefined") {
						rowIndex = e.target.parentNode.parentNode.rowIndex;
					}
					var arrList = mmg.selectedRows();
					if (arrList.length > 0) {
						setButtonStatus('2');
					} else {
						setButtonStatus('1');
					}
				});

				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});

				//定义新增按钮点击事件
				$('#btnAdd').on('click', function() {
					openDetail();
				});

				//定义修改按钮点击事件
				$('#btnUpd').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要修改的数据行！', 0, '友情提示');
						return;
					}
					if(arrList[0].XJSQ_SFWJ=="1"){
						layer.alert('当前休假申请已完结，不能修改！', 0, '友情提示');
						return;
					}

					if(arrList[0].XJSQ_XJZT=="0" || arrList[0].XJSQ_XJZT=="4"){//申请和驳回可以修改
					}else{
						layer.alert('当前休假申请正在处理中，不能修改！', 0, '友情提示');
						return;
					}

					openDetail(arrList[0].XJSQ_SQID);
				});


				//定义删除按钮点击事件
				$('#btnDel').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要删除的数据行！', 0, '友情提示');
						return;
					}
					if(arrList[0].XJSQ_SFWJ=="1"){
						layer.alert('当前休假申请已完结，不能删除！', 0, '友情提示');
						return;
					}

					if(arrList[0].XJSQ_XJZT=="0" || arrList[0].XJSQ_XJZT=="4"){//申请和驳回可以修改
					}else{
						layer.alert('当前休假申请正在处理中，不能删除！', 0, '友情提示');
						return;
					}
					layer.confirm('是否删除休假信息？', function() {
						layer.close(layer.index);
						if (deleteXJSQ(arrList[0].XJSQ_SQID) == true) {
							//重新查询数据
							loadGridByBean();
							setButtonStatus('1');
							mmg.deselect('all');
						}
					});
				});

				loadSearchSelect($('#selectXJLX'), 'TYPE_XJLX', '休假类型');
				loadSearchSelect($('#selectXJZT'), 'TYPE_SPZT', '审批状态');

				//页面初始化加载数据
				loadGridByBean();

				setButtonStatus("1");
			});
			function btn_Info(strSQID,strREG) {
			    alert("1");
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
			function openDetail(){
				var strXJSQ_SQID="";
				if(typeof(arguments[0])!="undefined"){
					strXJSQ_SQID=arguments[0];
				}
				$.layer({
			        type: 2,
			        title: false,
			        maxmin: false,
			        shadeClose: false, //开启点击遮罩关闭层
			        area : ["700px" , "500px"],
			        offset : ['20px', ''],
			        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
			        },
			        end: function(){//关闭按钮
			        	loadGridByBean();
			        },
			        iframe: {src: "<%=basePath%>/bin/jsp/1020000/1020131.jsp?XJSQ_SQID="+strXJSQ_SQID}
			    });
			}

			function makeBeanIn(strXJSQ_SQID,strXJSQ_XJLX,strXJSQ_SQSJ,strXJSQ_KSSJ,strXJSQ_JSSJ,strXJSQ_XJZT) {
				this.XJSQ_SQID = strXJSQ_SQID;
				this.XJSQ_XJLX = strXJSQ_XJLX;
				this.XJSQ_SQSJ = strXJSQ_SQSJ;
				this.XJSQ_KSSJ = strXJSQ_KSSJ;
				this.XJSQ_JSSJ = strXJSQ_JSSJ;
				this.XJSQ_XJZT = strXJSQ_XJZT;
			}

			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanIn(
					"",
					$('#selectXJLX').val(),
					$('#txtJSQ_SQSJ').val(),
					$('#txtXJSQ_KSSJ').val(),
					$('#txtXJSQ_JSSJ').val(),
					$('#selectXJZT').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}

			//删除休假方法
			function deleteXJSQ(strSQID) {
				var blnRet = false;
				var beanIn = new makeBeanIn(strSQID,"","","","","");
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1020130",
					data: {
						CMD    : "<%=Servlet1020130.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除休假申请信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除休假申请信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除休假申请信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//设置按钮状态
			function setButtonStatus(strFlag) {
				if (strFlag == "1") {//初始
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').attr("disabled", "disabled");
					$('#btnDel').attr("disabled", "disabled");
				}else if (strFlag == "2") {//可操作状态
					$('#btnAdd').removeAttr("disabled");
					$('#btnUpd').removeAttr("disabled");
					$('#btnDel').removeAttr("disabled");
				}
			}

		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">休假类型</th>
					<td><select id="selectXJLX" style="width: 100px"></select></td>
					<th style="width:100px">状态</th>
					<td><select id="selectXJZT" style="width: 100px"></select></td>
					<th style="width:100px">申请日期</th>
					<td>
						<input id="txtJSQ_SQSJ" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
					</td>
					<th style="width:100px">休假期限</th>
					<td>
						<input id="txtXJSQ_KSSJ" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
					</td>
					<th style="width:50px">～</th>
					<td>
						<input id="txtXJSQ_JSSJ" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
					</td>
					<th style="width:100px"><input type="button" value="查询" id="btnSearch"  class="btn btn-primary btn-sm"/></th>
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
				<input type="button" value="申请" id="btnAdd" name="btnAdd" class="btn btn-primary btn-sm"/>
				<input type="button" value="修改" id="btnUpd" name="btnUpd" class="btn btn-primary btn-sm"/>
				<input type="button" value="删除" id="btnDel" name="btnDel" class="btn btn-primary btn-sm"/>
			</div>
		</fieldset>
	</body>
</html>