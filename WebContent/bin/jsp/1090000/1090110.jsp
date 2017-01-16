<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1090000.Servlet1090110"%>	
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
<title>流程配置</title>
<link rel="stylesheet" href="<%=basePath%>/bin/css/common.css?r=<%=radom %>" type="text/css">
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
<!--插件脚本End  -->
<script type="text/javascript">
var mmg;//定义表格对象
var intheight;//定义表格高度参数
var optionFlag;//定义操作参数
$(document).ready(function() {
	//定义表格列值
	var cols = [
		{ title:'流程名称', name:'LCXX_LCMC' ,width:100, sortable:true, align:'center',lockDisplay: true },
		{ title:'关联功能', name:'LCXX_GLMC' ,width:80, sortable:true, align:'center',lockDisplay: true  },
		{ title:'申请者', name:'LCXX_SQMC' ,width:80, sortable:true, align:'center',lockDisplay: true  },
		{ title:'初始审批人', name:'LCXX_CSSP' ,width:150, sortable:true, align:'center',lockDisplay: true  },
		{ title:'操作', name:'' ,width:80, sortable:true, align:'center',lockDisplay: false, 
			renderer: function(val){
	           return '<button class="btn btn-success btn-xs">配置节点</button>'
			}
		}
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
		url: '<%=basePath%>/Servlet1090110',
		method: 'post',
		params:{CMD : "<%=Servlet1090110.CMD_SELECT%>"},
		remoteSort:true,
		sortName: 'LCXX_GLGN,LCXX_SQLB',
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
	//定义表格触发事件
	mmg.on('loadSuccess',function(e, data) {
		//设置按钮状态
		setButtonStatus('2');
	}).on('click', ':checkbox', function() {//选择checkbox
		if (this.checked == true) {
			var arrList = mmg.selectedRows();
			if (arrList.length > 0) {
				$('#txtEditLCID').val(arrList[0].LCXX_LCID);
				$('#txtEditLCMC').val(arrList[0].LCXX_LCMC);
				$('#selectEditGLGN').val(arrList[0].LCXX_GLGN);
				$('#txtEditSQZ').val(arrList[0].LCXX_SQMC);
				$('#txtEditSQZID').val(arrList[0].LCXX_SQDM);
				setButtonStatus('4');
			}
		} else {
			$('#txtEditLCID').val("");
			$('#txtEditLCMC').val("");
			$('#selectEditGLGN').val("000");
			$('#txtEditSQZ').val("");
			$('#txtEditSQZID').val("");
			setButtonStatus('2');//未选中行,则只有新增按钮可用
		}
	}).on('click', 'tr', function(e) { //点击行;
		if (mmg.rowsLength() <= 0) return; //无数据,不进行操作
		var rowIndex = e.target.parentNode.rowIndex;
		if (typeof(rowIndex) == "undefined") {
			rowIndex = e.target.parentNode.parentNode.rowIndex;
		}
		var arrList = mmg.selectedRows();
		if (arrList.length > 0) {
			$('#txtEditLCID').val(arrList[0].LCXX_LCID);
			$('#txtEditLCMC').val(arrList[0].LCXX_LCMC);
			$('#selectEditGLGN').val(arrList[0].LCXX_GLGN);
			$('#txtEditSQZ').val(arrList[0].LCXX_SQMC);
			$('#txtEditSQZID').val(arrList[0].LCXX_SQDM);
			setButtonStatus('4');
		} else {
			$('#txtEditLCID').val("");
			$('#txtEditLCMC').val("");
			$('#selectEditGLGN').val("000");
			$('#txtEditSQZ').val("");
			$('#txtEditSQZID').val("");
			setButtonStatus('2');//未选中行,则只有新增按钮可用
		}
	}).on('cellSelected', function(e, item, rowIndex, colIndex){
		var arrList = mmg.row(rowIndex);
		
		//配置节点
		if($(e.target).is('.btn-success')){
			e.stopPropagation();  //阻止事件冒泡
			//alert("item.LCXX_LCID="+item.LCXX_LCID);
			/* alert("item.LCXX_LCMC="+item.LCXX_LCMC); */
			pLCXX_SQMC = item.LCXX_SQMC;
			$.layer({
		        type: 2,
		        title: false,
		        maxmin: false,
		        shadeClose: false, //开启点击遮罩关闭层
		        area : ["800px" , "580px"],
		        offset : ['20px', ''],
		        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
		        },
		        end: function(){//关闭按钮
		        	loadGridByBean();
		        },
		        iframe: {src: "<%=basePath%>/bin/jsp/1090000/1090111.jsp?LCXX_LCID="+item.LCXX_LCID}
		    });
			
		}
	});

	//定义查询按钮点击事件
	$('#btnSearch').on('click', function() {
		loadGridByBean();
	});
	//定义保存按钮点击事件
	$('#btnSave').on('click', function() {
		if (optionFlag == "Add") {
			if (funEditCheck() == false) return;
			layer.confirm('是否增加流程审批？', function() {
				layer.close(layer.index);
				if (insertLCXX() == true) {
					//重新查询数据
					loadGridByBean();
					setButtonStatus('2');
					mmg.deselect('all');
				}
			});
		}else if (optionFlag == "Upd") {
    		var arrList = mmg.selectedRows();
    		if (arrList.length <= 0) {
    			layer.alert('请选择要修改的数据行！', 0, '友情提示');
    			return;
    		}
    		if (funEditCheck() == false) return;
    		layer.confirm('是否修改流程审批？', function() {
    			layer.close(layer.index);
    			if (updateLCXX() == true) {
					//重新查询数据
					loadGridByBean();
					setButtonStatus('2');
					mmg.deselect('all');
				}
    		});
		}
	});
	//定义取消按钮点击事件
	$('#btnCancel').on('click', function() {
		setButtonStatus('2');
		mmg.deselect('all');
	});
	//定义删除按钮点击事件
	$('#btnDel').on('click', function() {
		var arrList = mmg.selectedRows();
		if (arrList.length <= 0) {
			layer.alert('请选择要删除的数据行！', 0, '友情提示');
			return;
		}
		layer.confirm('是否删除流程审批？', function() {
			layer.close(layer.index);
			if (deleteLCXX() == true) {
				//重新查询数据
				loadGridByBean();
				setButtonStatus('2');
				mmg.deselect('all');
			}
		});
	});

	loadSearchSelect($('#selectGLGN'), 'TYPE_GLGN', '关联功能');
	loadEditSelect($('#selectEditGLGN'), 'TYPE_GLGN', '关联功能');
		
	//页面初始化加载数据
	loadGridByBean();
});

//新增按钮点击方法
function btn_Add() {
	setButtonStatus('31');
	optionFlag = "Add";
}
//修改按钮点击方法
function btn_Upd() {
	setButtonStatus('32');
	optionFlag = "Upd";
}
//定义bean
function makeBeanIn(strLCXX_LCID, strLCXX_LCMC, strLCXX_LCMS, strLCXX_GLGN, strLCXX_SQLB, strLCXX_SQDM, strLCXX_SQZW) {
	this.LCXX_LCID = strLCXX_LCID;
	this.LCXX_LCMC = strLCXX_LCMC;
	this.LCXX_LCMS = strLCXX_LCMS;
	this.LCXX_GLGN = strLCXX_GLGN;
	this.LCXX_SQLB = strLCXX_SQLB;
	this.LCXX_SQDM = strLCXX_SQDM;
	this.LCXX_SQZW = strLCXX_SQZW;
}
//数据加载方法
function loadGridByBean() {
	var beanIn = new makeBeanIn(
		"",
		$('#txtSelectLCMC').val(),
		"",
		$('#selectGLGN').val(),
		$('#txtSelectSQLB').val(),
		$('#txtSelectSQDM').val(),
		""
	);
	//重新查询数据
	mmg.load({
		beanLoad  :  JSON.stringify(beanIn)
	});
}
//新增职务方法
function insertLCXX() {
	var blnRet = false;
	
	var beanIn = new makeBeanIn(
		$('#txtEditLCID').val(),
		$('#txtEditLCMC').val(),
		"",
		$('#selectEditGLGN').val(),
		$('#txtEditSQLB').val(),
		$('#txtEditSQDM').val(),
		$('#txtEditSQZW').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_INSERT%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：新增流程信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：新增流程信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：新增流程信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//修改职务方法
function updateLCXX() {
	var blnRet = false;
	var beanIn = new makeBeanIn(
		$('#txtEditLCID').val(),
		$('#txtEditLCMC').val(),
		"",
		$('#selectEditGLGN').val(),
		$('#txtEditSQLB').val(),
		$('#txtEditSQDM').val(),
		$('#txtEditSQZW').val()
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_UPDATE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：修改流程信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：修改流程信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：修改流程信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//删除部门方法
function deleteLCXX() {
	var blnRet = false;
	var beanIn = new makeBeanIn(
		$('#txtEditLCID').val(),
		"","","","","",""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_DELETE%>",
			BeanIn : JSON.stringify(beanIn)
		},
		complete : function(response) {},
		success : function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				layer.msg('恭喜：删除流程信息成功！', 1, 9);
				blnRet = true;
			} else if (strResult == "FAILURE") {
				layer.msg('对不起：删除流程信息失败！', 1, 8);
				blnRet = false;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：删除流程信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
	return blnRet;
}
//设置按钮状态
function setButtonStatus(strFlag) {
	if (strFlag == "1") {//初始状态
		$('#btnAdd').attr("disabled", "disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
	} else if (strFlag == "2") {//查询后/返回
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditLCID').val("");
		$('#txtEditLCMC').val("");
		$('#selectEditGLGN').val("000");
		$('#txtEditSQZ').val("");
		$('#txtEditSQLB').val("");
		$('#txtEditSQDM').val("");
		$('#txtEditSQZW').val("");
		$('#txtEditLCID').attr("disabled","disabled");
		$('#txtEditLCMC').attr("disabled","disabled");
		$('#selectEditGLGN').attr("disabled","disabled");
		$('#btnEditSQ').attr("disabled","disabled");
		$('#txtEditSQZ').attr("disabled","disabled");
		
		$('#txtEditSQZ').attr("disabled","disabled");
		//$('#txtSelectSQZ').attr("disabled","disabled");
	} else if (strFlag.substring(0, 1) == "3") {
		//新增/修改
		$('#btnAdd').attr("disabled","disabled");
		$('#btnUpd').attr("disabled", "disabled");
		$('#btnDel').attr("disabled", "disabled");
		$('#btnSave').removeAttr("disabled");
		$('#btnCancel').removeAttr("disabled");
		if (strFlag == "31") {//新增
			$('#txtEditLCMC').removeAttr("disabled");
			$('#selectEditGLGN').removeAttr("disabled");
			$('#btnEditSQ').removeAttr("disabled");
			$('#txtEditSQZ').removeAttr("disabled");
			$('#txtEditLCID').val("");
			$('#txtEditLCMC').val("");
			$('#selectEditGLGN').val("000");
			$('#txtEditSQLB').val("");
			$('#txtEditSQDM').val("");
			$('#txtEditSQZW').val("");
			$('#txtEditLCMC').focus();
		} else if (strFlag == "32") {//修改
			$('#txtEditLCMC').removeAttr("disabled");
			$('#selectEditGLGN').removeAttr("disabled");
			$('#btnEditSQ').removeAttr("disabled");
			$('#txtEditSQZ').removeAttr("disabled");
			$('#txtEditLCMC').focus();
		} else if (strFlag == "32") {//删除
			$('#txtEditLCMC').attr("disabled","disabled");
			$('#selectEditGLGN').attr("disabled","disabled");
			$('#btnEditSQ').attr("disabled","disabled");
			$('#txtEditSQZ').attr("disabled","disabled");
			
		}
	} else if (strFlag == "4") {//选中行
		$('#btnAdd').removeAttr("disabled");
		$('#btnUpd').removeAttr("disabled");
		$('#btnDel').removeAttr("disabled");
		$('#btnSave').attr("disabled", "disabled");
		$('#btnCancel').attr("disabled", "disabled");
		$('#txtEditLCMC').attr("disabled","disabled");
		$('#selectEditGLGN').attr("disabled","disabled");
		$('#btnEditSQ').attr("disabled","disabled");
		$('#txtEditSQZ').attr("disabled","disabled");
	}
}
//验证编辑输入数据
function funEditCheck() {
	if (optionFlag == "Add" || optionFlag == "Upd") {
		if ($('#txtEditLCMC').val() == "") {
			layer.alert('请输入流程名称！', 0, '友情提示', function() {
				$('#txtEditLCMC').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#selectEditGLGN').val() == "000") {
			layer.alert('请选择关联功能名称！', 0, '友情提示', function() {
				$('#selectEditGLGN').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if ($('#txtEditSQZ').val() == "") {
			layer.alert('请选择申请者！', 0, '友情提示', function() {
				$('#btnEditSQ').focus();
				layer.close(layer.index);
			});
			return false;
		}
		if (optionFlag == "Add") {//新增：判断是否审批流程已存在
			if (checkDataExist() == true) {
				layer.alert('审批流程已存在，不能新增！', 0, '友情提示');
				return false;
			}
		} else if (optionFlag == "Upd") {//修改：判断是否审批流程已存在
			if (checkDataExist() == true) {
				layer.alert('审批流程已存在，不能变更为指定的审批流程！', 0, '友情提示');
				return false;
			}
		}
	}
	return true;
}
//验证重复方法
function checkDataExist() {
	var blnRet = false;
	var beanIn = new makeBeanIn(
		"",
		"",
		"",
		$('#selectEditGLGN').val(),
		$('#txtEditSQLB').val(),
		$('#txtEditSQDM').val(),
		""
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1090110",
		data: {
			CMD    : "<%=Servlet1090110.CMD_CHK_EXIST%>",
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
function showTree(){
	currNodeId="";
	currNodeType="";
	currNodeName="";
	parentNodeName="";
	
	var flag=!arguments[0]?"1":arguments[0];
	
	var width=!arguments[1]?"250px":arguments[1];
	var height=!arguments[2]?"420px":arguments[2];
	$.layer({
        type: 2,
        title: false,
        maxmin: false,
        shadeClose: false, //开启点击遮罩关闭层
        area : [width , height],
        offset : ['20px', ''],
        close: function(index){//层右上角关闭按钮的点击事件触发回调函数，无返回值
        },
        end: function(){//关闭按钮，已设定返回值
        	if(flag=="1"){
        		if(!currNodeId || !currNodeType || !currNodeName){
            		$('#txtEditSQZ').val("");
            		$('#txtEditSQLB').val("");
            		$('#txtEditSQDM').val("");
            	}else{
            		if(!parentNodeName){
            			$('#txtEditSQZ').val(currNodeName);	
            		}else{
            			$('#txtEditSQZW').val(parentNodeName);
            			$('#txtEditSQZ').val(parentNodeName+"-"+currNodeName);
            		}
            		$('#txtEditSQLB').val(currNodeType);
            		$('#txtEditSQDM').val(currNodeId);        		
            	}	
        	}else if(flag=="2"){
        		if(!currNodeId || !currNodeType || !currNodeName){
            		$('#txtSelectSQZ').val("");
            		$('#txtSelectSQLB').val("");
            		$('#txtSelectSQDM').val("");
            	}else{
            		if(!parentNodeName){
            			$('#txtSelectSQZ').val(currNodeName);	
            		}else{
            			$('#txtSelectSQZ').val(parentNodeName+"-"+currNodeName);
            		}
            		$('#txtSelectSQLB').val(currNodeType);
            		$('#txtSelectSQDM').val(currNodeId);        		
            	}
        	}
        	
        },
        iframe: {src: "<%=basePath%>/bin/jsp/common/YGTree.jsp"}
    });
	
}

</script>

</head>
<body>
	<fieldset id="selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width: 100px">流程名称</th>
				<td><input id="txtSelectLCMC" name="流程名称" maxlength="20" /></td>
				<th style="width: 100px">关联功能</th>
				<td><select id="selectGLGN" style="width: 100px"></select></td>
				<th style="width: 100px">申请者</th>
				<td>
					<input id="txtSelectSQZ" name="申请者" maxlength="20" class="input-icon-style apply-icon" onclick="showTree('2');"/>
					<input id="txtSelectSQLB" style="display:none;" />
					<input id="txtSelectSQDM" style="display:none;" />
					<input type="hidden" value="选择" id="btnSelectSQ"  onclick="showTree('2')" class="btn btn-info btn-xs"/>
				</td>
				<th style="width: 100px"><input type="button" value="查询" id="btnSearch" class="btn btn-primary btn-sm"/></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
	<fieldset id="editRegion">
		<legend>编辑</legend>
		<table>
			<tr>
				<th style="width: 100px">流程名称</th>
				<td>
					<input id="txtEditLCMC" name="流程名称" maxlength="20" />
					<input id="txtEditLCID" style="display:none;" />
				</td>
				<th style="width: 100px">关联功能</th>
				<td><select id="selectEditGLGN" style="width: 100px"></select></td>
				<th style="width: 100px">申请者</th>
				<td>
					<input id="txtEditSQZ" name="申请者" maxlength="20" class="input-icon-style apply-icon" onclick="showTree('1')" />
					<input id="txtEditSQLB" style="display:none;" />
					<input id="txtEditSQDM" style="display:none;" />
					<input id="txtEditSQZW" style="display:none;" />
					<input type="hidden" value="选择" id="btnEditSQ" onclick="showTree('1')" class="btn btn-info btn-xs"/>
				</td>
			</tr>
		</table>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="新增" id="btnAdd" name="btnAdd" onclick="btn_Add()" class="btn btn-primary btn-sm"/> 
			<input type="button" value="修改" id="btnUpd" name="btnUpd" onclick="btn_Upd()" class="btn btn-primary btn-sm"/> 
			<input type="button" value="删除" id="btnDel" name="btnDel" class="btn btn-primary btn-sm"/>
			<input type="button" value="保存" id="btnSave" name="btnSave" class="btn btn-primary btn-sm"/> 
			<input type="button" value="取消" id="btnCancel" name="btnCancel" class="btn btn-primary btn-sm"/> 
		</div>
	</fieldset>
</body>
</html>