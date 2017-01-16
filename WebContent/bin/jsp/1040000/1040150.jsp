<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1040000.Servlet1040150"%>
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
		<title>信息录入</title>
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
        <script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<script type="text/javascript">
		var mmg;//定义表格对象
		var intheight;//定义表格高度参数
		var optionFlag;//定义操作参数
		$(document).ready(function() {
			//定义表格列值
			var cols = [
				{ title:'玩家', name:'YXXX_WJMC' ,width:80, sortable:true, align:'center',lockDisplay: true },
				{ title:'游戏日期', name:'YXXX_YXRQ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
				{ title:'玩家角色', name:'YXXX_WJJS' ,width:80, sortable:true, align:'center',lockDisplay: true  },
				{ title:'输赢状态', name:'SYZT' ,width:50, sortable:true, align:'center',lockDisplay: true  },
				{ title:'备注', name:'YXXX_BZXX' ,width:250, sortable:true, align:'center',lockDisplay: true  },
				{ title:'录入人', name:'YXXX_LRR' ,width:50, sortable:true, align:'center',lockDisplay: true  },
				{ title:'操作', name:'' ,width:40, sortable:true, align:'center',lockDisplay: false, renderer: function(val,item){
					return '<img class="img-del" title="删除" src="<%=basePath%>/bin/img/common/delete.gif"></img>';
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
				url: '<%=basePath%>/Servlet1040150',
				method: 'post',
				params:{CMD : "<%=Servlet1040150.CMD_SELECT%>"},
				remoteSort:true,
				sortName: 'YXXX_YXRQ',
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
				if ($(e.target).is('.img-del')) {
					e.stopPropagation();  //阻止事件冒泡
		   		 	btn_Del(item.YXXX_YXID, item.YXXX_YHID, item.YXXX_SYZT, item.YXXX_LRID);
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
				openUpdate(arrList[0].YXXX_YXID);
			});

			loadSearchSelect($('#selectWJMC'), 'YHXX_WJMC', '玩家');
			loadSearchSelect($('#selectWJJS'), 'YHXX_WJJS', '角色');

			//页面初始化加载数据
			loadGridByBean();

			setButtonStatus("1");
		});
		function btn_Del(strYXID, strYHID, strSYZT, strLRID) {
			layer.confirm('是否删除游戏信息？', function() {
				layer.close(layer.index);
				if (deleteYXXX(strYXID, strYHID, strSYZT, strLRID) == true) {
					//重新查询数据
					loadGridByBean();
					setButtonStatus('1');
					mmg.deselect('all');
				}
			});
		}
		function openDetail(){
			var strYXXX_YXID="";
			if(typeof(arguments[0])!="undefined"){
				strYXXX_YXID=arguments[0];
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
		        iframe: {src: "<%=basePath%>/bin/jsp/1040000/1040151.jsp?YXXX_YXID="+strYXXX_YXID}
		    });
		}

		function openUpdate(){
			var strYXXX_YXID="";
			if(typeof(arguments[0])!="undefined"){
				strYXXX_YXID=arguments[0];
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
		        iframe: {src: "<%=basePath%>/bin/jsp/1040000/1040152.jsp?YXXX_YXID="+strYXXX_YXID}
		    });
		}

		function makeBeanIn(strYXXX_YXID,strYXXX_YHID,strYXXX_LRID,strYXXX_SYZT,strYXXX_KSSJ,strYXXX_JSSJ) {
			this.YXXX_YXID = strYXXX_YXID;
			this.YXXX_YHID = strYXXX_YHID;
			this.YXXX_LRID = strYXXX_LRID
			this.YXXX_SYZT = strYXXX_SYZT;
			this.YXXX_KSSJ = strYXXX_KSSJ;
			this.YXXX_JSSJ = strYXXX_JSSJ;
		}

		function makeBeanInDel(strYXXX_YXID,strYXXX_YHID,strYXXX_SYZT,strYXXX_LRID) {
			this.YXXX_YXID = strYXXX_YXID;
			this.YXXX_YHID = strYXXX_YHID;
			this.YXXX_SYZT = strYXXX_SYZT;
			this.YXXX_LRID = strYXXX_LRID;
		}

		//数据加载方法
		function loadGridByBean() {
			var beanIn = new makeBeanIn(
				"",
				$('#selectWJMC').val(),
				$('#selectWJJS').val(),
				$('#selectSYZT').val(),
				$('#txtYXXX_KSSJ').val(),
				$('#txtYXXX_JSSJ').val()
			);
			//重新查询数据
			mmg.load({
				beanLoad  :  JSON.stringify(beanIn)
			});
		}

		//删除游戏信息
		function deleteYXXX(strYXID, strYHID, strSYZT, strLRID) {
			var blnRet = false;
			var beanIn = new makeBeanInDel(strYXID,strYHID,strSYZT,strLRID);
			$.ajax({
				async     : false,
				type      : "post",
				dataType  : "json",
				url: "<%=basePath%>/Servlet1040150",
				data: {
					CMD    : "<%=Servlet1040150.CMD_DELETE%>",
					BeanIn : JSON.stringify(beanIn)
				},
				complete : function(response) {},
				success : function(response) {
					var strResult = response[0];
					if (strResult == "SUCCESS") {
						layer.msg('恭喜：删除信息成功！', 1, 9);
						blnRet = true;
					} else if (strResult == "FAILURE") {
						layer.msg('对不起：删除信息失败！', 1, 8);
						blnRet = false;
					} else if (strResult == "EXCEPTION") {
						layer.msg('友情提示：删除信息出错！', 1, 0);
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
            <th style="width:100px">玩家</th>
            <td><select id="selectWJMC" style="width: 100px"></select></td>
            <th style="width:100px">角色</th>
            <td><select id="selectWJJS" style="width: 100px"></select></td>
            <th style="width:100px">输赢状态</th>
            <td>
              <select id="selectSYZT" style="width: 100px">
                <option value="000">所有</option>
                <option value="0">赢</option>
                <option value="1">输</option>
              </select>
            </td>
            <th style="width:100px">游戏日期</th>
            <td>
              <input id="txtYXXX_KSSJ" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
            </td>
            <th style="width:50px">～</th>
            <td>
              <input id="txtYXXX_JSSJ" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()">
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
          <input type="button" value="录入" id="btnAdd" name="btnAdd" class="btn btn-primary btn-sm"/>
          <input type="button" value="修改" id="btnUpd" name="btnUpd" class="btn btn-primary btn-sm"/>
        </div>
      </fieldset>
	</body>
</html>