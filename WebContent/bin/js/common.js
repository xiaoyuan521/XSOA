 
var CDOT = "CDOT";				//There is a decimal point.
var CNUMONLY = "CNUMONLY";		//There is not a decimal point.


/** 
 * @Function_Name: filterKeyForNumber 
 * @Description: 限制Input控件输入数字，onkeypress内调用
 * @param obj--限制输入对象
 * @param strFlg 
 *            CDOT:带小数点数字
 *            CNUMONLY:纯数字
 * @author misty
 * @date 2014年4月8日 下午10:09:11 
 */
function filterKeyForNumber(obj, strFlg) {
	switch (strFlg) {
		case CDOT:
			if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode != 46
					|| /[^\d\.]/.test(obj.value)) {
				event.returnValue = false;
			}
			break;
		case CNUMONLY:
			if (event.keyCode < 48 || event.keyCode > 57) {
				event.returnValue = false;
			}
			break;
	}
}

/**	
 * @FunctionName: placeHolder	
 * @Description: 跨浏览器placeHolder,对于不支持原生placeHolder的浏览器，通过value或插入span元素两种方案模拟	
 * @param obj{Object} 要应用placeHolder的表单元素对象
 * @param span{Boolean}是否采用悬浮的span元素方式来模拟placeHolder，默认值false,默认使用value方式模拟
 * @return void	
 * @author ljg	
 * @date 2015年2月2日 下午2:42:25	
 */	
function placeHolder(obj, span) {
    if (!obj.getAttribute('placeholder')) return;
    var imitateMode = span === true ? true: false;
    var supportPlaceholder = 'placeholder' in document.createElement('input');
    if (!supportPlaceholder) {
        var defaultValue = obj.getAttribute('placeholder');
        if (!imitateMode) {
            obj.onfocus = function() { (obj.value == defaultValue) && (obj.value = '');
                obj.style.color = '';
            }
            obj.onblur = function() {
                if (obj.value == defaultValue) {
                    obj.style.color = '';
                } else if (obj.value == '') {
                    obj.value = defaultValue;
                    obj.style.color = '#ACA899';
                }
            }
            obj.onblur();
        } else {
            var placeHolderCont = document.createTextNode(defaultValue);
            var oWrapper = document.createElement('span');
            oWrapper.style.cssText = 'position:absolute; color:#ACA899; display:inline-block; overflow:hidden;';
            oWrapper.className = 'wrap-placeholder';
            oWrapper.style.fontFamily = getStyle(obj, 'fontFamily');
            oWrapper.style.fontSize = getStyle(obj, 'fontSize');
            oWrapper.style.marginLeft = parseInt(getStyle(obj, 'marginLeft')) ? parseInt(getStyle(obj, 'marginLeft')) + 3 + 'px': 3 + 'px';
            oWrapper.style.marginTop = parseInt(getStyle(obj, 'marginTop')) ? getStyle(obj, 'marginTop') : 1 + 'px';
            oWrapper.style.paddingLeft = getStyle(obj, 'paddingLeft');
            oWrapper.style.width = obj.offsetWidth - parseInt(getStyle(obj, 'marginLeft')) + 'px';
            oWrapper.style.height = obj.offsetHeight + 'px';
            oWrapper.style.lineHeight = obj.nodeName.toLowerCase() == 'textarea' ? '': obj.offsetHeight + 'px';
            oWrapper.appendChild(placeHolderCont);
            obj.parentNode.insertBefore(oWrapper, obj);
            oWrapper.onclick = function() {
                obj.focus();
            }
            
            //删除字符时
            obj.onkeyup = function(){
           		oWrapper.style.display = this.value != '' ? 'none': 'inline-block';
            }
            
            obj.onblur = changeHandler;
            
            //绑定input或onpropertychange事件
            if (typeof(obj.oninput) == 'object') {
                obj.addEventListener("input", changeHandler, false);
            } else {
                obj.onpropertychange = changeHandler;
            }
            function changeHandler() {
                oWrapper.style.display = obj.value != '' ? 'none': 'inline-block';
            }
            /**
                 * @name getStyle
                 * @class 获取样式
                 * @param {Object} obj 要获取样式的对象
                 * @param {String} styleName 要获取的样式名
                 */
            function getStyle(obj, styleName) {
                var oStyle = null;
                if (obj.currentStyle) oStyle = obj.currentStyle[styleName];
                else if (window.getComputedStyle) oStyle = window.getComputedStyle(obj, null)[styleName];
                return oStyle;
            }
        }
    }
}


/** 
 * @Function_Name: filterKeybordAZaz09 
 * @Description: 限制输入A~Z，a~z,0~9 
 * @param obj 返回值说明
 * @author misty
 * @date 2014年4月8日 下午10:15:50 
 */
function filterKeybordAZaz09(obj){
	if((event.keyCode<48 || event.keyCode>57)
		&& (event.keyCode<65 || event.keyCode>90)
		&& (event.keyCode<97 || event.keyCode>122)){
		event.returnValue=false;
	}
}

/** 
 * @Function_Name: filterKeybordAZaz09 
 * @Description: 限制输入大小写字母 
 * @param obj 返回值说明
 * @author misty
 * @date 2014年4月8日 下午10:17:07 
 */
function filterKeybordAZaz(obj){
	if((event.keyCode<65 || event.keyCode>90)
		&& (event.keyCode<97 || event.keyCode>122)){
		event.returnValue=false;
	}
}

/* 左补0 */  
function pad(num, n) {  
    var len = num.toString().length;  
    while(len < n) {  
        num = "0" + num;  
        len++;  
    }  
    return num;  
}

function nullSafe(obj){
	if (obj == undefined || obj == null || obj == "null") {
		return "";
	}
	return obj;
}

/*根据名称取得当前页面的参数值*/
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/*
 * 计算两个日期的间隔天数
 * BeginDate:起始日期的文本框，格式為：2012-01-01
 * EndDate:結束日期的文本框，格式為：2012-01-02
 * 返回兩個日期所差的天數
 * 調用方法：
 * alert("相差"+Computation("date1","date2")+"天");
 */            
 function GetDateRegion(BeginDate,EndDate){
     var aDate, oDate1, oDate2, iDays;
     aDate = BeginDate.split("-");
     oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);   //转换为12/13/2008格式
     aDate = EndDate.split("-");
     oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
     //iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24)+1;   //把相差的毫秒数转换为天数
     var i=(oDate2 - oDate1) / 1000 / 60 / 60 /24;
     if(i<0){
         i-=1;
     }else{
         i+=1;
     }
     iDays = i;   //把相差的毫秒数转换为天数
     //alert(iDays);
     return iDays;
 }
 
 function getCurrDate(){
	 var result,year,mon,date,today = new Date();;
	 year= today.getFullYear();
	 mon = today.getMonth() + 1;
	 mon = (mon<10) ? "0"+mon : mon;
	 date= today.getDate();
	 date = (date<10) ? "0"+date : date;
	 result = year +"-"+mon+"-"+date;
	 return result;
 }
 
 /**	
 * @FunctionName: getTax	
 * @Description: 根据工资计算出所得税
 * @param salary
 * @returns {Number}
 * @return Number-所得税
 * @author ljg	
 * @date 2015年3月30日 下午5:06:27	
 */	
function getTax(salary){
	    var tax = 0;
	    var salaryPre = salary - 3500;
	    if( salaryPre < 0 ){
	        tax = 0;
	    }else if(salaryPre<=1500 && salaryPre > 0){
	        tax = salaryPre*0.03;
	    }else if(salaryPre<=4500){
	        tax = salaryPre*0.1 - 105;
	    }else if(salaryPre<=9000){
	        tax = salaryPre*0.2 - 555;
	    }else if(salaryPre<=35000){
	        tax = salaryPre*0.25 - 1005;
	    }else if(salaryPre<=55000){
	        tax = salaryPre*0.30 - 2755;
	    }else if(salaryPre<=80000){
	        tax = salaryPre*0.35 - 5505;
	    }else if(salaryPre > 80000){
	        tax = salaryPre*0.45 - 13505;
	    }
	    return tax;
	}