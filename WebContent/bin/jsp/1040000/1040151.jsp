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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息录入</title>
<link rel="stylesheet" href="1040151.css?r=<%=radom %>" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/css/control/buttonStyle.css?r=<%=radom %>" type="text/css">
<link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/bin/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<script src="<%=basePath%>/bin/js/jquery.min.js?v=2.1.4" type="text/javascript" ></script>
<script src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
<script src="<%=basePath%>/bin/js/content.min.js?v=1.0.0"></script>
<script type="text/javascript">
var wjxxList = [];
var wjidList = [];
var saveData = [];
$(document).ready(function() {
	loadEditSelect($('#selectYXXX_WJMC'), 'YHXX_WJMC', '玩家');
	//取得玩家信息
	getWJXX();

	//取得游戏ID
	paraYXXX_YXID = getQueryString("YXXX_YXID");
	$("#txtYXXX_YXRQ").val(getCurrDate());
	if(paraYXXX_YXID==null || paraYXXX_YXID==""){
		paraYXXX_YXID="";
	}
	//初始化页面信息
	getYXXXByID(paraYXXX_YXID);

	//取得狼人信息
	getLRXX();
	//设置单选
	setCheckBox();

})
function delXX(yhid){
    var saveTemp = [];
    $('tr[id='+yhid+']').remove();
    for (var i=0; i<wjidList.length; i++) {
        if (wjidList[i] == yhid) {
            wjidList.splice(i, 1);
        }
    }
    $.each(saveData, function(index, item) {
        if (item.yhid == yhid) {
            saveData.splice(index,1);
        }
    });
    var ss = saveData;
}
function getWJXX(){
    var names = document.getElementById("selectYXXX_WJMC").children;
    for(var i=0; i<names.length; i++){
        if (names[i].value != "000") {
            wjxxList.push({
                id : names[i].value,
                name : names[i].text
            });
        }
    }
}

function makeBeanIn(strYXXX_YXID) {
	this.YXXX_YXID = strYXXX_YXID;
}

/* 根据游戏ID取得游戏信息 */
function getYXXXByID(strYXID){
	var blnRet = false;
	var beanIn = new makeBeanIn(strYXID);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040150",
		data: {
			CMD    : "<%=Servlet1040150.CMD_GETYXXX%>",
		    BeanIn : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response) {
			var strResult = response[0];
			if (strResult == "SUCCESS") {
				var resultRecord = response[1];
				$("#hidXJSQ_SQR").val(resultRecord.XJSQ_SQR);
				$("#txtXJSQ_SQRM").val(resultRecord.XJSQ_SQRM);
				if(strYXID!=""){
					$("#hidYXXX_YXID").val(resultRecord.XJSQ_SQID);
					$("#txtYXXX_YXRQ").val(resultRecord.YXXX_YXRQ);
					$("#txtXJSQ_XJTS").val(resultRecord.XJSQ_XJTS);
					$("input[name='chkYXXX_JSLX']:checkbox[value='"+resultRecord.YXXX_LRID+"']").attr('checked','true');
					$("#txtYXXX_BZXX").val(resultRecord.YXXX_BZXX);
					$("#selectYXXX_WJMC").val(resultRecord.YXXX_YHID);
				}
				blnRet = true;
			} else if (strResult == "EXCEPTION") {
				layer.msg('友情提示：游戏信息出错！', 1, 0);
				blnRet = false;
			}
		}
	});
}
/* 取得狼人信息 */
function getLRXX(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040150",
		data: {
			CMD    : "<%=Servlet1040150.CMD_GETLRXX%>"
		},
		complete :function(response){},
		success: function(response) {
		    var sp = $("#radioWjjs");
		    var count = 0;
		    var html = "";
		    sp.empty();
			list = response[0];
			$.each(list, function(k, v) {
			    count += 1;
			    if (count%5 == 0) {
			        html += "<br>";
			    }
				html += "<label><input name='chkYXXX_JSLX' type='checkbox' value='" + v.LRXX_LRID + "'/><span>" + v.LRXX_LRMC +"</span></label>";
			});
			sp.append(html);
		}
	});
}
function setCheckBox() {
    $("input[name='chkYXXX_JSLX']").click(function(){//点击事件:只能选中一个
		 if($(this).is(':checked')){
			 var currValue=$(this).val();
			 $("input[name='chkYXXX_JSLX']:checked").each(function(){
				 if($(this).val()!=currValue){
					 $(this).removeAttr("checked");
				 }
			 });
		 }
	});
	$("input[name='chkYXXX_SYZT']").click(function(){//点击事件:只能选中一个
		 if($(this).is(':checked')){
			 var currValue=$(this).val();
			 $("input[name='chkYXXX_SYZT']:checked").each(function(){
				 if($(this).val()!=currValue){
					 $(this).removeAttr("checked");
				 }
			 });
		 }
	});
}
/* 关闭窗口 */
function closeW(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}


function checkSelect(){
	//是否选择玩家
	if($("#selectYXXX_WJMC").val()=="000"){
		layer.alert('请选择玩家！', 0, '友情提示',function() {
			$("#selectYXXX_WJMC").focus();
			layer.close(layer.index);
		});
		return false;
	}
	//是否选择申请日期
	if($("#txtYXXX_YXRQ").val()==""){
		layer.alert('请选择游戏日期！', 0, '友情提示',function() {
			$("#txtYXXX_YXRQ").focus();
			layer.close(layer.index);
		});
		return false;
	}
	//判断是否选择输赢
	if($("input[name='chkYXXX_SYZT']:checked").length <= 0){
		layer.alert('请选择输赢！', 0, '友情提示');
		return false;
	}
	//判断是否选择角色
	if($("input[name='chkYXXX_JSLX']:checked").length <= 0){
		layer.alert('请选择角色类型！', 0, '友情提示');
		return false;
	}
	return true;
}

function makeBeanSaveIn(strYXXX_YXID,strYXXX_YHID,strYXXX_LRID,strYXXX_YXRQ,strYXXX_SYZT,strYXXX_BZXX) {
	this.YXXX_YXID = strYXXX_YXID;
	this.YXXX_YHID = strYXXX_YHID;
	this.YXXX_LRID = strYXXX_LRID;
	this.YXXX_YXRQ = strYXXX_YXRQ;
	this.YXXX_SYZT = strYXXX_SYZT;
	this.YXXX_BZXX = strYXXX_BZXX;
}
function confirmData() {
    //检查用户输入值
	if(checkSelect()==false){
		return;
	}
    var strWJID = $("#selectYXXX_WJMC").val();
    var strWJMC = $("#selectYXXX_WJMC").find("option:selected").text();
    var strJSID = $("input[name='chkYXXX_JSLX']:checked").val();
    var strJSMC = $("input[name='chkYXXX_JSLX']:checked").siblings().text();
    var strSYID = $("input[name='chkYXXX_SYZT']:checked").val();
    var strSYZT = $("input[name='chkYXXX_SYZT']:checked").siblings().text();
    var strYXRQ = $("#txtYXXX_YXRQ").val();
    var strBZXX = $("#txtYXXX_BZXX").val();

    var yxlb = $("#yxlb");
    if (wjidList.indexOf(strWJID) == -1) {
        wjidList.push(strWJID);
        yxlb.after("<tr id='"+ strWJID +"''><td>"+ strWJMC +"</td><td>"+ strJSMC +"</td><td>"+ strSYZT +"</td><td>"+ strBZXX +"</td><td><img id='img-del' onclick='delXX("+ strWJID +")' title='删除' src='<%=basePath%>/bin/img/common/delete.gif'></img></tr>");
        saveData.push({
            yhid : strWJID,
            lrid : strJSID,
            yxrq : strYXRQ,
            syzt : strSYID,
            bzxx : strBZXX
        });
    } else {
        layer.alert('该玩家已录入信息！', 0, '友情提示');
        return;
    }
}
function saveYXXX(){
	var flag = true;
	var wlidList = "";
    $.each(wjxxList, function(index, item) {
        if (wjidList.indexOf(item.id) == -1) {
            flag = false;
            wlidList += "[" + item.name + "]";
        }
    });
    if (!flag) {
        layer.confirm('玩家' + wlidList + '信息未录入是否继续?', function() {
			layer.close(layer.index);
			insertData();
		});
    } else {
        insertData();
    }
}

function insertData(){
    var flag = true;
    $.each(saveData, function(index, item) {
        var beanIn = new makeBeanSaveIn();
    	beanIn.YXXX_YXID = $("#hidYXXX_YXID").val();
    	beanIn.YXXX_YHID = item.yhid;
    	beanIn.YXXX_LRID = item.lrid;
    	beanIn.YXXX_YXRQ = item.yxrq;
    	beanIn.YXXX_SYZT = item.syzt;
    	beanIn.YXXX_BZXX = item.bzxx;
    	$.ajax({
    		async     : false,
    		type      : "post",
    		dataType  : "json",
    		url: "<%=basePath%>/Servlet1040150",
    		data: {
    			CMD    : "<%=Servlet1040150.CMD_SAVE%>",
    		    BeanIn : JSON.stringify(beanIn)
    		},
    		complete :function(response){},
    		success: function(response) {
    			var strResult = response[0];
    			if (strResult == "SUCCESS") {
    			    flag = true;
    			} else if (strResult == "FAILURE") {
    			    flag = false;
    			} else if (strResult == "EXCEPTION") {
    				flag = false;
    			}
    		}
    	});
    });
    if (flag) {
        layer.msg('恭喜：保存成功！', 1, 9,function(){closeW();});
    } else {
        layer.msg('对不起：保存失败！', 1, 8);
    }
}

</script>
</head>
<body>
	<div class="table_reset" id="editRegion">
		<table id="detailCanvas" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="6" class="title_ border_left_none">
					<span>信 息 录 入 </span>
				</td>
			</tr>
			<tr>
				<th>玩家：</th>
				<td align="left">
					<input type="hidden" id="hidYXXX_YXID" />
					<select id="selectYXXX_WJMC" style="width:80%" name="aa">
							<option value="000" selected>选择玩家</option>
				    </select>
				</td>
				<th>部门：</th>
				<td class="border_right">
					<input type="hidden" id="hidXJSQ_BMID" />
					<input type="text" id="txtXJSQ_BMMC" onfocus=this.blur() readonly />
				</td>
			</tr>
 			<tr>
 				<th>游戏日期：</th>
				<td onclick="laydate()">
					<input type="text" id="txtYXXX_YXRQ"  readonly="readonly" class="laydate-icon-default">
				</td>
				<th>输赢状态：</th>
                <td class="sycheckboxstyle border_right" >
                <label><input name="chkYXXX_SYZT" type="checkbox" value="0" /><span>赢</span></label>
                <label><input name="chkYXXX_SYZT" type="checkbox" value="1" /><span>输</span></label>
				</td>
			</tr>
			<tr>
				<th>玩家角色：</th>
				<td colspan="5" class="checkboxstyle border_right" id="radioWjjs">

				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td valign="middle" colspan="5" class="border_right">
					<textarea style="width: 100%; border: 0" id="txtYXXX_BZXX"></textarea>
				</td>
			</tr>
		</table>

		<table border="0" cellpadding="0" cellspacing="0" class="table_2 border_none_exbottom">

            <tr id="yxlb">
              <td style="width:20%">玩家</td>
              <td style="width:20%">角色</td>
              <td style="width:20%">输赢状态</td>
              <td style="width:30%">备注</td>
              <td style="width:10%">操作</td>
            </tr>
			<tr>
				<td colspan="6" class="border_bottom_none">
					<div class="rightstyle">
						<a class="botton" id="btnConfirm" name="btnConfirm" onclick="confirmData()">录入</a>
                        <a class="botton" id="btnSave" name="btnSave" onclick="saveYXXX()">保存</a>
					</div>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>