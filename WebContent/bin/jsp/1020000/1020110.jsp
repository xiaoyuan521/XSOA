<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020110"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日程设定</title>
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/layer/layer.min.js"></script>
<style type="text/css">
.table_Head{font-family: 宋体;font-size:16pt;border-width:0 0 0 0;border-style:solid;}

.table_detail1{font-family: 宋体;font-size:12pt;border-width:1 1 1 1;border-style:solid;border-color:#666666;border-collapse:collapse;}

.table_cell{font-family: 宋体;font-size:12pt;border-width:0 0 1 0;border-style:solid;border-bottom-style:dotted;border-color:#666666;border-collapse:collapse;}

.table_cell_detail{font-family: 宋体;font-size:12pt;cursor:hand;border-style:solid;border-top-style:dotted;border-color:#666666;border-width:1 0 0 1;}

.table_tr{font-family: 宋体;font-size:12pt;border-bottom:1;border-style:solid;border-color:#666666;border-collapse:collapse;}
</style>
<script type="text/javascript">
var intWidth="100px"; //单元格宽度
var intHeight="58px";//行高
var intHeightLx="38px";//行高
var txtYear = '';
var txtMonth = '';
var weeks = new Array("日","一","二","三","四","五","六");
var dataDay = new Array(32);
var dataLxmc = new Array(32);

var bgColor1="#e5e5e5";//#e5e5e5",fd0000
var bgColor2="#E5E5E5";//#dd4b39
var bgColor3="#FFFFFF";
var bgColor4="#ffffff";
var bgColor5="#eeeeff";//edfeea

var fontColor="fd0000";

var strGRRC_GRRCID="";
var strBCRC_BCID="";
var strBCRC_NY="";
var strBCRC_BCMC="";
var strBCRC_BCSD="";
var strBCRC_SDID="";
var strBCRC_SDMC="";

var mes ="";
var reg = "true";
var qsday ="";
var jsday = "";
//初始化表格
$(document).ready(function(){
	var inputObj = window.parent.obj;
	strGRRC_GRRCID=inputObj.BCRC_BCRCID;
	strBCRC_BCID=inputObj.BCRC_BCID;
	strBCRC_NY=inputObj.BCRC_NY;
	strBCRC_BCMC=inputObj.BCRC_BCMC;
	strBCRC_BCSD=inputObj.BCRC_BCSD;
	strBCRC_SDID=inputObj.BCRC_SDID;
	strBCRC_SDMC=inputObj.BCRC_SDMC;
	txtYear = strBCRC_NY.split("-")[0];
	txtMonth = strBCRC_NY.split("-")[1];
	var strNY=strBCRC_NY.replace("-","");
	var strQS=strBCRC_BCSD.substr(0,10).replace(/[&\|\\\*^%$#@\-]/g,"");
	var strJS= strBCRC_BCSD.substr(11,10).replace(/[&\|\\\*^%$#@\-]/g,"");
	qsday = strBCRC_BCSD.substr(8,2).replace(/[&\|\\\*^%$#@\-]/g,"");
	jsday = strBCRC_BCSD.substr(19,2).replace(/[&\|\\\*^%$#@\-]/g,"");
	if(strQS.substr(0,6) == strNY&&strJS.substr(0,6) == strNY){
			mes = "请选择  "+strBCRC_BCSD.substr(0,10)+"~"+strBCRC_BCSD.substr(11,10)+"的日期！";
			reg = "false0";
	}else if(strQS.substr(0,6) == strNY){
		  	mes = "请选择  "+strBCRC_BCSD.substr(0,10)+"~月末的日期！";
		  	reg = "false1";
	}else if(strJS.substr(0,6) == strNY){
		  	mes = "请选择  月初~"+strBCRC_BCSD.substr(11,10)+"的日期！";
		  	reg = "false2";
	}else{

	}
	$('#btnSave').on('click', function(){
		saveData();
		iframeLayerClosenoRefresh();
	});

	$('#btnClose').on('click', function(){

		iframeLayerClosenoRefresh();
	});
});

function getDBData(){
	var beanIn = new makeBeanIngetDate(
		strGRRC_GRRCID,//班次日程ID
		strBCRC_BCID,//班次ID
		strBCRC_NY//年月
	);
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1020110",
		data: {
			CMD    : "<%=Servlet1020110.CMD_MUTI_SELECT%>",
			beanLoad : JSON.stringify(beanIn)
		},
		complete :function(response){},
		success: function(response){
			var strResult = response[0];
			if(strResult=="SUCCESS"){
				var resultList = response[1];
				if(resultList!=null){
					for(var i=1;i<=31;i++){
					var filed = eval('resultList.BCRC_DAY'+pad(i,2));
					var filedname = eval('resultList.SDJC'+pad(i,2));
						if(filed!=""){
							dataDay[i]=filed;
							dataLxmc[i]=filedname;
						}
					}
				}
				blnRet = true;
			}else{
				layer.alert('保存工作排班信息出错！', 0, '友情提示');
				blnRet = false;
			}
		}
	});
}

function saveData(){
	var beanIn = new makeBeanIn(
		strGRRC_GRRCID,//班次日程ID
		strBCRC_BCID,//班次ID
		strBCRC_NY//年月
	);

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1020110",
		data: {
			CMD    : "<%=Servlet1020110.CMD_MUTI_UPDATE%>",
		    BeanIn : JSON.stringify(beanIn),
		    dataDays : JSON.stringify(dataDay)
		},
		complete :function(response){},
		success: function(response){
			var strResault = response[0];
			if(strResault=="SUCCESS"){
				blnRet = true;
			}else{
				layer.alert('保存日程设定信息出错！', 0, '友情提示');
				blnRet = false;
			}
		}
	});
}

function setDBData(){
	for(var i=1;i<=42;i++){//所有单元格
		var iDay = document.getElementById('tdrq'+i).innerHTML;
		if(iDay!="&nbsp;"){
			if(typeof(dataDay[iDay])!='undefined' && dataDay[iDay] !=null){
				document.getElementById("tdrq"+i).style.backgroundColor = bgColor1;
				document.getElementById("tdlx"+i).innerHTML=dataLxmc[iDay];
			}
		}
	}
}
function makeBeanIngetDate(strBCRCID,strBCID,strYN){
	this.BCRC_BCRCID = strBCRCID;//班次日程ID
	this.BCRC_BCID = strBCID;//班次ID
	this.BCRC_NY = strYN;//年月
}
function makeBeanIn(strBCRCID,strBCID,strYN){
	this.BCRC_BCRCID = strBCRCID;//班次日程ID
	this.BCRC_BCID = strBCID;//班次ID
	this.BCRC_NY = strYN;//年月
}

function doInit(){
	tableLoad_Department(tableCalindaSelect1,txtYear,txtMonth);
	setData();
	getDBData();
	setDBData();
}

function tableLoad_Department(tbControl1,Year,Month){
	while (tbControl1.rows != null && tbControl1.rows.length != 0){
		tbControl1.deleteRow(0);
	}
	var tbody_1 = document.createElement("TBODY");
	var cellAll = document.createElement("TD");
	cellAll.style.textAlign="center";
	cellAll.innerHTML = "("+strBCRC_BCMC + ")日程安排---" + Year+"年"+Month+"月";
	cellAll.colSpan = 7;
	cellAll.style.borderBottom="1px";
	cellAll.className="table_Head";
	cellAll.style.backgroundColor=bgColor5;

	//行を追加
	var rowAll = document.createElement("TR");
	rowAll.appendChild(cellAll);
	rowAll.style.height="28px";
	tbody_1.appendChild(rowAll);

	var cellAll1 = document.createElement("TD");
	var cellAll2 = document.createElement("TD");
	var cellAll3 = document.createElement("TD");
	var cellAll4 = document.createElement("TD");
	var cellAll5 = document.createElement("TD");
	var cellAll6 = document.createElement("TD");
	var cellAll7 = document.createElement("TD");

	cellAll1.innerHTML = "&nbsp;"+"一";
	cellAll2.innerHTML = "&nbsp;"+"二";
	cellAll3.innerHTML = "&nbsp;"+"三";
	cellAll4.innerHTML = "&nbsp;"+"四";
	cellAll5.innerHTML = "&nbsp;"+"五";
	cellAll6.innerHTML = "&nbsp;"+"六";
	cellAll7.innerHTML = "&nbsp;"+"日";

	cellAll1.style.backgroundColor=bgColor5;
	cellAll2.style.backgroundColor=bgColor5;
	cellAll3.style.backgroundColor=bgColor5;
	cellAll4.style.backgroundColor=bgColor5;
	cellAll5.style.backgroundColor=bgColor5;
	cellAll6.style.backgroundColor=bgColor5;
	cellAll7.style.backgroundColor=bgColor5;


	cellAll1.className="table_cell";
	cellAll2.className="table_cell";
	cellAll3.className="table_cell";
	cellAll4.className="table_cell";
	cellAll5.className="table_cell";
	cellAll6.className="table_cell";
	cellAll7.className="table_cell";

	cellAll1.style.textAlign="center";
	cellAll2.style.textAlign="center";
	cellAll3.style.textAlign="center";
	cellAll4.style.textAlign="center";
	cellAll5.style.textAlign="center";
	cellAll6.style.textAlign="center";
	cellAll7.style.textAlign="center";

	cellAll1.style.width=intWidth;
	cellAll2.style.width=intWidth;
	cellAll3.style.width=intWidth;
	cellAll4.style.width=intWidth;
	cellAll5.style.width=intWidth;
	cellAll6.style.width=intWidth;
	cellAll7.style.width=intWidth;

	//行を追加
	var rowAll_1 = document.createElement("TR");
	rowAll_1.style.height="28px";

	rowAll_1.appendChild(cellAll1);
	rowAll_1.appendChild(cellAll2);
	rowAll_1.appendChild(cellAll3);
	rowAll_1.appendChild(cellAll4);
	rowAll_1.appendChild(cellAll5);
	rowAll_1.appendChild(cellAll6);
	rowAll_1.appendChild(cellAll7);
	//bodyを追加
	tbody_1.appendChild(rowAll_1);

	for(var iRow = 1; iRow <=6; iRow++){
		var row_RQ = document.createElement("TR");
		var row_LX = document.createElement("TR");
		for(var iCell = 1; iCell <=7; iCell++){
			var tdID;
			var cell_RQ = document.createElement("TD");
			cell_RQ.innerHTML = "&nbsp;";
			cell_RQ.style.textAlign="center";
			cell_RQ.style.width=intWidth;
			cell_RQ.className="table_cell";
			tdID="tdrq"+((iRow-1)*7+iCell+"");
			cell_RQ.setAttribute("id",tdID);
			cell_RQ.onclick = tdOnClick;
			//增加日期行
			row_RQ.appendChild(cell_RQ);
			row_RQ.style.height=intHeight;
			var cell_LX = document.createElement("TD");
			cell_LX.innerHTML = "&nbsp;";
			cell_LX.style.textAlign="center";
			cell_LX.style.width=intWidth;
			cell_LX.className="table_cell_detail";
			tdID="tdlx"+((iRow-1)*7+iCell+"");
			cell_LX.setAttribute("id",tdID);
			cell_LX.onclick = tdOnClick;
			//增加日期行
			row_LX.appendChild(cell_LX);
			row_LX.style.height=intHeightLx;

		}
		//bodyを追加
		tbody_1.appendChild(row_RQ);
		tbody_1.appendChild(row_LX);
	}
	//表を追加
	tbControl1.appendChild(tbody_1);
}

//单元格点击事件
function tdOnClick(){
	var strTD = this.id.substr(0,4);
	var selectIndex = this.id.split(strTD)[1];
	var strYear = txtYear;
	var strVALUE = document.getElementById("tdrq"+selectIndex).innerHTML;
	if(reg == "false0"){
	    if(parseFloat(strVALUE)<parseFloat(qsday)||parseFloat(strVALUE)>parseFloat(jsday)){
			layer.alert(mes, 0, '友情提示');
			return;
	    }
	}else if(reg == "false1"){
	    if(parseFloat(strVALUE)<parseFloat(qsday)){
			layer.alert(mes, 0, '友情提示');
			return;
	    }
    }else if(reg == "false2"){
        if(parseFloat(strVALUE)>parseFloat(jsday)){
			layer.alert(mes, 0, '友情提示');
			return;
	    }
    }

		if (document.getElementById("tdrq"+selectIndex).innerHTML!="&nbsp;"){
		if (document.getElementById(strTD+selectIndex).style.backgroundColor == bgColor1 || document.getElementById(strTD+selectIndex).style.backgroundColor == bgColor2){

			document.getElementById(strTD+selectIndex).style.backgroundColor = bgColor3;


					document.getElementById("tdlx"+selectIndex).innerHTML="&nbsp;";
					document.getElementById("tdrq"+selectIndex).style.backgroundColor = bgColor3;
					var iDay = document.getElementById('tdrq'+selectIndex).innerHTML;
					dataDay[iDay] = "";
					dataLxmc[iDay] = "";
				}else
				if(document.getElementById("tdlx"+selectIndex).innerHTML!="&nbsp;"){
					document.getElementById("tdlx"+selectIndex).innerHTML="&nbsp;";
					document.getElementById("tdrq"+selectIndex).style.backgroundColor = bgColor3;
					var iDay = document.getElementById('tdrq'+selectIndex).innerHTML;
					dataDay[iDay] = "";
					dataLxmc[iDay] = "";
				}else{
					document.getElementById("tdlx"+selectIndex).innerHTML=strBCRC_SDMC;
					document.getElementById("tdrq"+selectIndex).style.backgroundColor = bgColor1;
					var iDay = document.getElementById('tdrq'+selectIndex).innerHTML;
					dataDay[iDay] = strBCRC_SDID;
					dataLxmc[iDay] = strBCRC_SDMC;
				}



	}else{
		document.getElementById(strTD+selectIndex).style.backgroundColor = bgColor3;
	}
}

//根据年月取得最大天数
function getDay(year,month){
	var iMaxDay = 31;
	if (month==4 || month==6 || month==9 || month==11) iMaxDay = 30;
	if (month==2){
		if (year % 4 > 0) iMaxDay = 28;
		else if (year % 100 == 0 && year % 400 > 0) iMaxDay = 28;
		else iMaxDay = 29;
	}
	return iMaxDay;
}

//根据年月取得第一天是周几
function getWeekByFirstDate(strYear,iMonth){
	var strYYYYMMDD=strYear+"/"+(iMonth+"")+"/01";
	var dt = new Date(strYYYYMMDD);
	var day = weeks[dt.getDay()];
	var intWeek=0;
	switch (day+""){
		case "一":
			intWeek=1;
			break;
		case "二":
			intWeek=2;
			break;
		case "三":
			intWeek=3;
			break;
		case "四":
			intWeek=4;
			break;
		case "五":
			intWeek=5;
			break;
		case "六":
			intWeek=6;
			break;
		case "日":
			intWeek=7;
			break;
	}
	return intWeek;
}

function setData(){
    var intWeek = getWeekByFirstDate(txtYear,txtMonth);

    var intMaxDay = getDay(txtYear,txtMonth);

    for(var iDay=1;iDay<=intMaxDay;iDay++){
    	tdID = iDay + intWeek ;
    	document.getElementById("tdrq"+tdID).innerHTML= iDay;
    }
}
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}
</script>
</head>
<body onload="doInit();" onunload="">
<div style="padding-left: 10px;padding-top: 10px;width:700px;height:565px;">
<table>
    <tr>
		<td width="30px"></td>
		<td colspan="4">
			<table id="tableCalindaSelect1" class="table_detail1" ></table>
		</td>
    </tr>
    <tr><td style="height: 30px"></td></tr>
    <tr>
    	<td width="30px"></td>
		<td style="width:100px"></td>
		<td style="text-align:right;">
			<input type="button" value="保存" id="btnSave" name="btnSave" />
			<input type="button" value="关闭" id="btnClose" name="btnClose"/>
		</td>
    </tr>
</table>
</div>
<div id="buttonCanvas" class="gToolbar gTbrCenter ">
</div>
</body>
</html>