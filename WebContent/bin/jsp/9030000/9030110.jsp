<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet9030000.Servlet9030110"%>
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
	<link rel="stylesheet" href="9030110.css?r=<%=radom %>" type="text/css">
    <script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
	<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.gears.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.silverlight.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.flash.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.browserplus.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.html4.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.html5.js"></script>
<title>完善信息</title>
<style type="text/css">
.teacheredit_input_src{position:relative;top:-490px;right:-480px;width:200px;}
.teacheredit_input_src table tr td img{border:1px solid #D8D8D8;width:192px;height:164px;}
.teacher_edit_input{height:24px;width:128px;border:1px solid #D8D8D8;float:left; line-height:24px;color:gray;font-size:12px}
.teacher_edit_input2{height:24px;width:35px;border:1px solid #D8D8D8;margin-left:-5px;background:#f5f5f5;text-align:center;float:left;line-height:24px;font-size:12px}
.teacher_edit_input2:hover{background:#ddd;}
.teacher_edit_input2 span:active{position:relative;top:1px;left:1px;}
*{margin:0;padding:0;outline:none;}
ul li{list-style:none;}

</style>
<script type="text/javascript">
$(document).ready(function(){
	getInfo();
	$('#btnSave').on('click', function(){
			 if(funEditCheck()==false) return;
			layer.confirm('是否确定保存信息？', function() {
				if(updateYHXX()==true){
		           layer.msg('恭喜：保存成功！', 1, 9);
		        }else{
		           layer.msg('对不起：保存失败！', 1, 8);
		        }
	        });
    });

});
function getInfo(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet9030110",
		data: {
			CMD    : "CMD_DATA"
		},
		complete : function(response) {},
		success : function(response) {
			var strResault = response[0];
			var dataBean = response[1];
			if (strResault == "SUCCESS") {
				$('#txtYHID').val(dataBean.YHXX_YHID);
				$('#txtYHXM').val(dataBean.YHXX_YHMC);
				$('#txtBMMC').val(dataBean.BMMC);
				$('#txtJSMC').val(dataBean.JSMC);
				$('#txtLXFS').val(dataBean.YHXX_TEL);
				$('#txtEMAIL').val(dataBean.YHXX_EMAIL);
				$('#txname').val(dataBean.YHXX_GRZP);
				if (dataBean.YHXX_GRZP != "") {
				    $("#img0").attr("src",'<%=basePath%>/bin/upload/' + dataBean.YHXX_GRZP);
				}
			} else if (strResault == "DATA_NULL") {
				layer.alert('取得个人信息出错！', 0, '友情提示');
			}else{
				layer.alert('系统异常！', 0, '友情提示');
			}

		}
	});
}
function funEditCheck() {
		if ($('#txtLXFS').val() == "") {
			$('#yzLXFS').show();
			$('#txtLXFS').focus();
			return false;
		} else {
			$('#yzLXFS').hide();
		}
		if ($('#txtYHXM').val() == "") {
		    $('#yzXM').show();
			$('#yzXM').focus();
			return false;
		} else {
		    $('#yzXM').hide();
		}

	return true;
}
function makeBeanInedit(strYHID,strYHXM,strLXFS,strEMAIL,strGRZP){
    this.YHXX_YHID = strYHID;
    this.YHXX_YHMC = strYHXM;
    this.YHXX_TEL = strLXFS;
    this.YHXX_EMAIL = strEMAIL;
    this.YHXX_GRZP = strGRZP
}
function updateYHXX(){
	var blnRet = false;
	var beanIn = new makeBeanInedit(
		   $('#txtYHID').val(),
           $('#txtYHXM').val(),
           $('#txtLXFS').val(),
           $('#txtEMAIL').val(),
           $("#txname").val()
	);
    $.ajax({
      async     : false,
      type      : "post",
      dataType  : "json",
      url: "<%=basePath%>/Servlet9030110",
      data: {
         CMD    : "<%=Servlet9030110.CMD_UPDATE%>",
         BeanIn : JSON.stringify(beanIn)
      },
      complete :function(response){},
      success: function(response){
          var strResault = response[0];
          if(strResault=="SUCCESS"){
             blnRet = true;
          }else if(strResault=="ERROR"){
             blnRet = false;
          }
      }
   });
   return blnRet;
}
function yanzhengxm(){
	if ($('#txtYHXM').val() == "") {
			$('#yzXM').show();
		}else{
			$('#yzXM').hide();
		}
}
function yanzhengfs(){
		if ($('#txtLXFS').val() == "") {
				$('#yzLXFS').show();
			}else{
				$('#yzLXFS').hide();
			}
}
//验证身份证
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X
function IdCardValidate() {
	var idCard = $('#txtSFZH').val();
	    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
	    if (idCard.length == 15) {
	        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
	    } else if (idCard.length == 18) {
	        var a_idCard = idCard.split("");                // 得到身份证数组
	        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
	           	$("#yzSFZH").hide();
	            return true;
	        }else {
	        	$("#yzSFZH").show();
	            return false;
	        }
	    } else {
	    	$("#yzSFZH").show();
	        return false;
	    }
}
/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * @param a_idCard 身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0;                             // 声明加权求和变量
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
    }
    for ( var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和
    }
    valCodePosition = sum % 11;                // 得到验证码所位置
    if (a_idCard[17] == ValideCode[valCodePosition]) {
    	$("#yzSFZH").hide();
        return true;
    } else {
   	    $("#yzSFZH").show();
        return false;
    }
}
/**
  * 验证18位数身份证号码中的生日是否是有效生日
  * @param idCard 18位书身份证字符串
  * @return
  */
function isValidityBrithBy18IdCard(idCard18){
    var year =  idCard18.substring(6,10);
    var month = idCard18.substring(10,12);
    var day = idCard18.substring(12,14);
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题
    if(temp_date.getFullYear()!=parseFloat(year)
          ||temp_date.getMonth()!=parseFloat(month)-1
          ||temp_date.getDate()!=parseFloat(day)){
            $("#yzSFZH").show();
            return false;

    }else{
    	$("#yzSFZH").hide();
        return true;
    }
}
  /**
   * 验证15位数身份证号码中的生日是否是有效生日
   * @param idCard15 15位书身份证字符串
   * @return
   */
  function isValidityBrithBy15IdCard(idCard15){
      var year =  idCard15.substring(6,8);
      var month = idCard15.substring(8,10);
      var day = idCard15.substring(10,12);
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
      	if(temp_date.getYear()!=parseFloat(year)||temp_date.getMonth()!=parseFloat(month)-1||temp_date.getDate()!=parseFloat(day)){
                $("#yzSFZH").show();
                return false;
        }else{
       		$("#yzSFZH").hide();
            return true;
        }
  }
//去掉字符串头尾空格
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body style="background: none;">
<form id="teacherForm" style="height:700px">
		<div id="main-content"  style="height:700px; width:97.5%;  overflow:auto">
			<div class="content-box chuan_mar2" >
				<div class="content-box-header">
					<h3>完善个人信息</h3>

					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab" id="tab1">

							<fieldset>
								<p>
									<label>玩家ID：</label>
									<input class="text-input small-input inputxt" type="text" id="txtYHID" name="small-input"   onfocus="this.blur()" readonly disabled/>
								</p>
								<p>
									<label>玩家姓名：</label>
									<input class="text-input small-input inputxt" type="text" id="txtYHXM" name="small-input" onblur="yanzhengxm()"/><strong><span id="notXM" style="color:red;font-size:15px;"> *</span></strong><span id="yzXM"  style="display:none" class="input-notification error ">姓名不能为空</span>
								</p>
								<p>
									<label>部门名称：</label>
									<input class="text-input small-input inputxt" type="text" id="txtBMMC" name="small-input"   onfocus="this.blur()" readonly disabled/>
								</p>
								<p>
									<label>角色：</label>
									<input class="text-input small-input inputxt" type="text" id="txtJSMC" name="small-input"   onfocus="this.blur()" readonly disabled/>
								</p>
								<p>
									<label>联系方式：</label>
									<input class="text-input small-input inputxt" type="text" id="txtLXFS" name="medium-input" onblur="yanzhengfs()"/><strong><span id="notLXFS" style="color:red;font-size:15px;"> *</span></strong><span id="yzLXFS"  style="display:none" class="input-notification error ">联系方式不能为空</span>
								</p>
								<p>
									<label for='txtBYXX'>E-mail：</label>
									<input class="text-input small-input "  type="text"  id="txtEMAIL"  name="medium-input"  />
								</p>
								<p>
									<input class="button" type="button"  id="btnSave" value="确 定" />
								</p>

							</fieldset>
							<div class="clear"></div>
					</div>
				</div>
			</div>
            <div class="clear"></div>
              <!--  添加上传zhaopia -->
              <div class="teacheredit_input_src">
                <table style="z:index:-9999;border-bottom:2px solid #fff;">
                  <tr>
					<td width="192" align="center">
					<img id="img0"   src="" />
					</td>
				</tr>
				<tr>
					<td align="center"  class="uploader">
						<input type="hidden" id="txname" />
						<input type="text" class="filename teacher_edit_input"  onfocus="this.blur()" readonly />
						<div id="container"  type="button"   class="teacher_edit_input2"><span>浏览...</span></div>
						<div id="uploadsfz"   type="button"   class="teacher_edit_input2"><span>上传</span></div>
						<input type="file" id="upload0" style="display:none;">
					</td>
				</tr>
				<tr>
					<td align="center">
						<font id="font0"  style="color:gray">*图片格式为.jpg,.gif,.png</font>
					</td>
				</tr>

                </table>
              </div>

		</div>
</form>
</body>
<script type="text/javascript">
  //上传身份证
  var uploader = new plupload.Uploader({
      runtimes : 'flash',
      browse_button : 'pickfiles',
      container: 'container',
      max_file_size : '100kb',
      url : '<%=basePath%>/ImgUploadServlet',
      resize : {width : 100, height : 100, quality : 90},
      flash_swf_url : '<%=basePath%>/bin/js/plupload/js/plupload.flash.swf',
      filters : [
         {title : "Image files", extensions : "jpg,png,gif"}
      ]
   });
   uploader.init();

   uploader.bind('Error', function(up, err) {
       if(err.code=="-600"){
         layer.alert('照片文件大小应小于100K！', 0, '友情提示');
       }
    });
  //会在文件上传过程中不断触发，可以用此事件来显示上传进度
  uploader.bind('UploadProgress', function(up, files) {
     //$("#img0").attr("src",'<%=basePath%>/bin/img/jindu.jpg');

  });
  document.getElementById('uploadsfz').onclick = function(){
        uploader.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    };
   uploader.bind('FilesAdded',function(uploader,files){
     for(var i = 0, len = files.length; i<len; i++){
       var file_name = files[i].name; //文件名
       //构造html来更新UI
        $("input[id='upload0']").parents(".uploader").find(".filename").val(file_name);

    }
   });
   uploader.bind('FileUploaded', function(up, file, info) {
     if(file.status=="5"){
         //上传成功，返回文件名称.
         var fileSrc = info.response;
         //$("input[id='upload0']").parents(".uploader").find(".filename").val(fileSrc);
         $("#img0").attr("src",'<%=basePath%>/bin/upload/' + fileSrc);
         $("#txname").val(fileSrc);
         $("input[id='upload0']").parents(".uploader").find(".filename").val(fileSrc);
         layer.msg('恭喜：上传头像成功！', 1, 9);
    }
  });
</script>
</html>