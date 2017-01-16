//js获取项目根路径，如： http://localhost:8080/HJLWL
function getRootPath(){
	var result = "";
    /*//获取当前网址，如： http://localhost:8080/HJLWL/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080/HJLWL
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);*/
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
	var localhostPath=curWwwPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	if(projectName == "/LRSXT"){
		result = localhostPath + projectName;
	}else{
		result = localhostPath + "/LRSXT";
	}
//	result = localhostPath + projectName;
    return(result);
}

/**
 * @Function_Name: getBrowseVersion
 * @Description: 取得浏览器版本
 * @returns {String} 返回浏览器版本
 * @author misty
 * @date 2014年4月8日 下午10:07:17
 */
function getBrowseVersion() {
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

	// 以下进行测试
	if (Sys.ie)
		return 'IE: ' + Sys.ie;
	if (Sys.firefox)
		return 'Firefox: ' + Sys.firefox;
	if (Sys.chrome)
		return 'Chrome: ' + Sys.chrome;
	if (Sys.opera)
		return 'Opera: ' + Sys.opera;
	if (Sys.safari)
		return 'Safari: ' + Sys.safari;
}


/**
 * @Function_Name: getObjPropert
 * @Description: 取得对象的所有属性
 * @param obj
 * @returns {String} 所有属性列表
 * @author misty
 * @date 2014年4月8日 下午10:43:26
 */
function getObjPropert(obj) {
	var props = "";
	// 开始遍历
	for ( var p in obj) { // 方法
		if (typeof (obj[p]) == "function") {
			obj[p]();
		} else {// p 为属性名称，obj[p]为对应属性的值
			props += p + " = " + obj[p] + "\t\n";
		}
	} // 最后显示所有的属性
	// $('#propert').val(props);
	return props;
}

/**
 * @Function_Name: loadSearchSelect
 * @Description: 设定下拉列表-用于查询
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午9:52:02
 */
function loadSearchSelect(obj,Type,Name){
	var blnRet = false;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "LOAD_SELECT",
			TYPE   : Type
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				var roleList = response[1];
				try{
					obj.empty();//清空
					var option = $("<option>").text("所有").val("000");//增加<所有>项
					obj.append(option);
					var len = roleList.length;
					for (var i=0;i<len;i++){
						var option = $("<option>").text(roleList[i].NAME).val(roleList[i].CODE);
						obj.append(option);//增加下拉列表值
					}
					obj[0].selectedIndex = 0;//选中第一项
				}catch(e){
					alert("取得"+Name+"下拉列表出错:"+e.message);
				}
				blnRet = true;
			} else if (strResault == "ERROR") {
				alert("取得"+Name+"下拉列表出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}
/**
 * @Function_Name: loadSearchSelectown
 * @Description: 设定下拉列表-用于查询 （第一个选项自定义）
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author czl
 * @date 2014年11月04日 上午9:52:02
 */
function loadSearchSelectown(obj,Type,Name){
	var blnRet = false;

	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "LOAD_SELECT",
			TYPE   : Type
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				var roleList = response[1];
				try{
					var len = roleList.length;
					for (var i=0;i<len;i++){
						var option = $("<option>").text(roleList[i].NAME).val(roleList[i].CODE);
						obj.append(option);//增加下拉列表值
					}
					obj[0].selectedIndex = 0;//选中第一项
				}catch(e){
					alert("取得"+Name+"下拉列表出错:"+e.message);
				}
				blnRet = true;
			} else if (strResault == "ERROR") {
				alert("取得"+Name+"下拉列表出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}
/**
 * @Function_Name: loadEditSelect
 * @Description: 设定下拉列表-用于编辑
 * @param obj-下拉列表对象
 * @param Type-取得下拉列表类别
 * @param Name-下拉列表应用名称
 * @returns {Boolean-是否成功} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function loadEditSelect(obj,Type,Name){
	var blnRet = false;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "LOAD_SELECT",
			TYPE   : Type
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			if (strResault == "SUCCESS") {
				var selectList = response[1];
				try{
					obj.empty();//清空
					var option = $("<option>").text("请选择").val("000");//增加<请选择>项
					obj.append(option);
					var len = selectList.length;
					for (var i=0;i<len;i++){
						var option = $("<option>").text(selectList[i].NAME).val(selectList[i].CODE);
						obj.append(option);//增加下拉列表值
					}
					obj[0].selectedIndex = 0;//选中第一项
				}catch(e){
					alert("取得"+Name+"下拉列表出错:"+e.message);
				}
				blnRet = true;
			} else if (strResault == "ERROR") {
				alert("取得"+Name+"下拉列表出错！");
				blnRet = false;
			}
		}
	});
	return blnRet;
}

/**
 * @Function_Name: getLoginUserBySession
 * @Description: 取得登录用户
 * @returns {String-用户名} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function getLoginUserBySession(){
	var blnRet = "";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "GET_USER_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
			blnRet = response[0];
		}
	});
	return blnRet;
}

/**
 * @Function_Name: getLoginUserIdBySession
 * @Description: 取得登录用户id
 * @returns {String-用户名} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function getLoginUserIdBySession(){
	var blnRet = "";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "GET_USERID_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
			blnRet = response[0];
		}
	});
	return blnRet;
}

/**
 * @FunctionName: getLoginYGIDBySession
 * @Description: 取得Session中的员工ID
 * @returns {String}
 * @return String
 * @author ljg
 * @date 2015年3月11日 上午9:58:05
 */
function getLoginYGIDBySession(){
	var blnRet = "";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "GET_YGID_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
			blnRet = response[0];
		}
	});
	return blnRet;
}

/**
 * @Function_Name: clearSession
 * @Description: 清除登录用户
 * @returns {String-用户名} 返回值说明
 * @author ljg
 * @date 2014年7月21日 下午10:09:16
 */
function clearSession(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "CLEAR_SESSION"
		},
		complete : function(response) {},
		success : function(response) {
		}
	});
}

/**
 * @FunctionName: loadcssfile
 * @Description: JS加载CSS文件(同名同目录)
 * @param filename
 * @return void
 * @author ljg
 * @date 2015年1月20日 上午9:45:59
 */
function loadcssfile(filename){
	var a = document.scripts,b,c = document,d = "getElementById",e = "getElementsByTagName",f=filename+"css";
	!c[d](f) && (function(){
		for(var i=0;i<a.length;i++){
			if(a[i].src.indexOf(filename + ".js")>-1){
				b = a[i].src;
				var p = b.substring(0, b.lastIndexOf("/") + 1) + filename + ".css";
				var g = document.createElement("link");
				g.type = "text/css",g.rel = "stylesheet",g.href = p,
				f && (g.id = f),c[e]("head")[0].appendChild(g);
			}
		}
	})();
}

function getUserMessCount(){
	var messcount=0;
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD    : "MESS_COUNT"
		},
		complete : function(response) {},
		success : function(response) {
			var result = response[0];
			if(result=="SUCCESS"){
				messcount=response[1];
			}
		}
	});
	return messcount;
}

/**
 * @FunctionName: createCheckCode
 * @Description: 取得验证码
 * @param checkid
 * @returns {Array}
 * @return Array
 * @author ljg
 * @date 2015年1月27日 下午12:42:48
 */
function createCheckCode(checkid){
	var code = new Array();
	var codeLength = 4;//验证码的长度
	var checkCode = document.getElementById(checkid);
	checkCode.value = "";

	var selectChar = new Array(1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

	for(var i=0;i<codeLength;i++) {
	   var charIndex = Math.floor(Math.random()*32);
	   code +=selectChar[charIndex];
	}
	if(code.length != codeLength){
	   createCode();
	}
	checkCode.value = code;
	return code;
}

/**
 * @FunctionName: iframeLayerOpen
 * @Description: iframe弹出层打开
 * @param url
 * @return void
 * @author ljg
 * @date 2015年1月27日 下午12:43:27
 */
function iframeLayerOpen(url) {
	var width=!arguments[1]?"800px":arguments[1];
	var height=!arguments[2]?"600px":arguments[2];
	$.layer({
        type: 2,
        title: false,
        maxmin: false,
        shadeClose: false, //开启点击遮罩关闭层
        area : [width , height],
        offset : ['20px', ''],
        iframe: {src: url}
    });
}
//iframe弹出层关闭
function iframeLayerClose() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.loadGridByBean();
	parent.layer.close(index);
}

function hasClass(obj, cls) {
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

function addClass(obj, cls) {
    if (!this.hasClass(obj, cls)) obj.className += " " + cls;
}

function removeClass(obj, cls) {
    if (hasClass(obj, cls)) {
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
        obj.className = obj.className.replace(reg, ' ');
    }
}

/**
 * @FunctionName: getSPRKFZJ
 * @Description: 通过关联功能和申请代码取得审批人是否可以终结
 * @param strGLGN
 * @param strSQDM
 * @returns {Number}
 * @return Number
 * @author ljg
 * @date 2015年3月7日 下午5:10:28
 */
function getSPRKFZJ(strGLGN,strSQDM){
	var result="0";
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: getRootPath()+"/CommonServlet",
		data: {
			CMD     : "SPLC_KFZJ",
			GLGN    : strGLGN,
			SQDM    : strSQDM
		},
		complete : function(response) {},
		success : function(response) {
			if(response[0]=="1"){
				result="1";
			}
		}
	});
	return result;
}