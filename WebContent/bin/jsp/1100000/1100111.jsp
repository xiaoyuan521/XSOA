<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1100000.Servlet1100110"%>
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
<title>添加版块</title>
<style type="text/css">
.teacheredit_input_src{position:relative;top:-490px;right:-480px;width:200px;}
.teacheredit_input_src table tr td img{border:1px solid #D8D8D8;width:192px;height:164px;}
.teacher_edit_input{height:24px;width:128px;border:1px solid #D8D8D8;float:left; line-height:24px;color:gray;font-size:12px}
.teacher_edit_input2{height:24px;width:35px;border:1px solid #D8D8D8;margin-left:-5px;background:#f5f5f5;text-align:center;float:left;line-height:24px;font-size:12px}
.teacher_edit_input2:hover{background:#ddd;}
.teacher_edit_input2 span:active{position:relative;top:1px;left:1px;}
</style>
<link rel="stylesheet" href="1100111.css?r=<%=radom %>" type="text/css">
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
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.gears.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.silverlight.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.flash.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.browserplus.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.html4.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/plupload/js/plupload.html5.js"></script>
<script src="<%=basePath%>/bin/js/jquery.min.js?v=2.1.4" type="text/javascript" ></script>
<script src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
<script src="<%=basePath%>/bin/js/content.min.js?v=1.0.0"></script>
<script type="text/javascript">
var wjxxList = [];
var wjidList = [];
var saveData = [];
$(document).ready(function() {
	loadEditSelect($('#selectLTBK_BZMC'), 'YHXX_WJMC', '版主');
	//取得部门信息
	getBMXX();
})
/* 取得部门信息 */
function getBMXX(){
	$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1100110",
		data: {
			CMD    : "<%=Servlet1100110.CMD_GETBMXX%>"
		},
		complete :function(response){},
		success: function(response) {
		    var sp = $("#radioKjbm");
		    var count = 0;
		    var html = "";
		    sp.empty();
			list = response[0];
			$.each(list, function(k, v) {
			    count += 1;
			    if (count%5 == 0) {
			        html += "<br>";
			    }
				html += "<label><input name='chkBMXX_BMID' type='checkbox' value='" + v.BMXX_BMID + "'/><span>" + v.BMXX_BMMC +"</span></label>";
			});
			sp.append(html);
		}
	});
}
function makeBeanIn(strYXXX_YXID) {
	this.YXXX_YXID = strYXXX_YXID;
}
/* 关闭窗口 */
function closeW(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}


function checkSelect(){
    //是否填写版块名称
	if($("#txtBkmc").val()==""){
		layer.alert('请填写版块名称！', 0, '友情提示',function() {
			$("#txtBkmc").focus();
			layer.close(layer.index);
		});
		return false;
	}
	//是否选择版主
	if($("#selectLTBK_BZMC").val()=="000"){
		layer.alert('请选择版主！', 0, '友情提示',function() {
			$("#selectLTBK_BZMC").focus();
			layer.close(layer.index);
		});
		return false;
	}
	//判断是否选择可见部门
	if($("input[name='chkBMXX_BMID']:checked").length <= 0){
		layer.alert('请选择可见部门！', 0, '友情提示');
		return false;
	}
	return true;
}

function makeBeanSaveIn(strLTBK_BKMC,strLTBK_BZID,strLTBK_KJID,strLTBK_TPLJ,strLTBK_BKSM) {
	this.LTBK_BKMC = strLTBK_BKMC;
	this.LTBK_BZID = strLTBK_BZID;
	this.LTBK_KJID = strLTBK_KJID;
	this.LTBK_TPLJ = strLTBK_TPLJ;
	this.LTBK_BKSM = strLTBK_BKSM;
}

function saveLTBK(){
    //检查用户输入值
	if(checkSelect()==false){
		return;
	}
	var strBMID = "";
	$("input[name='chkBMXX_BMID']:checked").each(function(){
	    strBMID+=$(this).val()+",";
    });
    var beanIn = new makeBeanSaveIn();
    beanIn.LTBK_BKMC = $('#txtBkmc').val();
    beanIn.LTBK_BZID = $('#selectLTBK_BZMC').val();
  	beanIn.LTBK_KJID = strBMID;
  	beanIn.LTBK_TPLJ = $('#txname').val();
  	beanIn.LTBK_BKSM = $('#txtLTBK_BKSM').val();

  	$.ajax({
  		async     : false,
  		type      : "post",
  		dataType  : "json",
  		url: "<%=basePath%>/Servlet1100110",
  		data: {
  			CMD    : "<%=Servlet1100110.CMD_SAVE%>",
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
					<span>添 加 版 块 </span>
				</td>
			</tr>
			<tr>
				<th>版块名称：</th>
				<td colspan="5" align="left" class="border_right">
					<input type="text" id="txtBkmc" style="width: 70%"/>
				</td>
			</tr>
 			<tr>
 				<th>版主：</th>
				<td align="left" style="width: 70px">
					<select id="selectLTBK_BZMC" style="width:90%" name="aa">
							<option value="000" selected>选择玩家</option>
				    </select>
				</td>
				<th>上传图片：</th>
                <td class="sycheckboxstyle border_right" >
                    <input type="hidden" id="txname" />
					<input type="text" id="tpname" class="teacher_edit_input"  onfocus="this.blur()" readonly />
					<div id="container"  type="button"   class="teacher_edit_input2"><span>上传</span></div>
				    <input type="file" id="upload0" style="display:none;">
				</td>
			</tr>
			<tr>
				<th>可见部门：</th>
				<td colspan="5" class="checkboxstyle border_right" id="radioKjbm">

				</td>
			</tr>
			<tr>
				<th>说明：</th>
				<td valign="middle" colspan="5" class="border_right">
					<textarea style="width: 100%; border: 0" id="txtLTBK_BKSM"></textarea>
				</td>
			</tr>
		</table>

		<table border="0" cellpadding="0" cellspacing="0" class="table_2 border_none_exbottom">
			<tr>
				<td colspan="6" class="border_bottom_none">
					<div class="rightstyle">
                        <a class="botton" id="btnSave" name="btnSave" onclick="saveLTBK()">保存</a>
					</div>
				</td>
			</tr>
		</table>
	</div>

</body>
<script type="text/javascript">
  //上传身份证
  var uploader = new plupload.Uploader({
      runtimes : 'flash',
      multi_selection: false,
      browse_button : 'pickfiles',
      container: 'container',
      max_file_size : '100kb',
      url : '<%=basePath%>/ImgUploadServlet?name=bkzp',
      resize : {width : 100, height : 100, quality : 90},
      flash_swf_url : '<%=basePath%>/bin/js/plupload/js/plupload.flash.swf',
      filters : [
         {title : "Image files", extensions : "jpg,png,gif"}
      ],
      init: {
          FilesAdded: function(up, files) {
              uploader.start();
          }
      }
   });
   uploader.init();

   uploader.bind('Error', function(up, err) {
       if(err.code=="-600"){
         layer.alert('照片文件大小应小于100K！', 0, '友情提示');
       }
    });
   uploader.bind('FilesAdded',function(uploader,files){
     for(var i = 0, len = files.length; i<len; i++){
       var file_name = files[i].name; //文件名
       //构造html来更新UI
       $("#tpname").val(file_name);
    }
   });
   uploader.bind('FileUploaded', function(up, file, info) {
     if(file.status=="5"){
         //上传成功，返回文件名称.
         var fileSrc = info.response;
         $("#txname").val(fileSrc);
         //$("input[id='upload0']").parents(".uploader").find(".filename").val(fileSrc);
         layer.msg('恭喜：上传图片成功！', 1, 9);
    }
  });
</script>
</html>