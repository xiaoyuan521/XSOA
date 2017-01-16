<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<title>交易客户-详情二级页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
		<!--表格样式End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
		<!--插件脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/cityselect/jquery.cityselect.js"></script>
		<!--插件脚本End  -->
		<script type="text/javascript">
			var obj = new Object();
			$(document).ready(function() {
				//获取父页面参数
				obj = window.parent.obj;
					$('#txtEditKhmc').val(obj.KHXX_KHMC);
					$('#selectEditSfCz').val(obj.KHXX_PROV + "-" + obj.KHXX_CITY);
					$('#selectEditHy').val(obj.HYMC);
					$('#selectEditGsgm').val(obj.GSGM);
					$('#selectEditXqdj').val(obj.XQDJ);
					$('#selectEditLy').val(obj.LYMC);
					$('#txtEditKhjj').val(obj.KHXX_KHJJ);
					$('#txtEditGddh').val(obj.KHXX_GDDH);
					$('#txtEditGscz').val(obj.KHXX_GSCZ);
					$('#txtEditGsdz').val(obj.KHXX_GSDZ);
					$('#selectEditGxdj').val(obj.GXDJ);
					$('#selectEditXssc').val(obj.XSSC);
					$('#selectEditXydj').val(obj.XYDJ);
					$('#selectEditZycd').val(obj.ZYCD);
					$('#txtEditLxrxm').val(obj.KHXX_LXRXM);
					$('#txtEditSjhm').val(obj.KHXX_LXRSJHM);
					$('#txtEditLxrqq').val(obj.KHXX_LXRQQ);
					$('#txtEditLxremail').val(obj.KHXX_LXREMAIL);
			});
		</script>
		<style>
		*{font-weight:100;}
		.eTable6 tr td input{width:140px;height:16px;}
		.eTable6 tr td select{width:85px;height:20px;}
		.titlefortop{background:#f5f5f5;font-size:16px;border-top:1px solid #ddd;}
		.bot{background:#999;color:#fff;padding:10px 30px;border:0;float:right;margin-right:10px;}
		</style>
	</head>
	<body style="line-height:35px;">
		<div id = "editRegion">
			<p class="titlefortop">基本信息</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">客户名称</th>
					<td><input id="txtEditKhmc" type="text" readonly="readonly"/></td>
					<th style="width:100px">所属区域</th>
					<td>
						<input id="selectEditSfCz" type="text" readonly="readonly"/>
					</td>
					<th style="width:100px">行业</th>
					<td>
						<input id="selectEditHy" type="text" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<th style="width:100px">公司规模</th>
					<td>
						<input id="selectEditGsgm" type="text" readonly="readonly"/>
					</td>
					<th style="width:100px">需求等级</th>
					<td>
						<input id="selectEditXqdj" type="text" readonly="readonly"/>
					</td>
					<th style="width:100px">来源</th>
					<td>
						<input id="selectEditLy" type="text" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<th style="width:100px">客户简介</th>
					<td colspan="7">
						<textarea id="txtEditKhjj" rows="3" cols="90%" style="outline:none;resize:none;" readonly="readonly"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id = "editRegion">
			<p class="titlefortop">联系方式</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">固定电话</th>
					<td><input id="txtEditGddh" type="text" readonly="readonly"/></td>
					<th style="width:100px">公司传真</th>
					<td><input id="txtEditGscz" type="text" readonly="readonly"/></td>
					<th style="width:100px">公司地址</th>
					<td colspan="3"><input id="txtEditGsdz" type="text" readonly="readonly"/></td>
				</tr>
			</table>
		</div>
		<div id = "editRegion">
			<p class="titlefortop">其他</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:80px">关系等级</th>
					<td>
						<input id="selectEditGxdj" type="text" readonly="readonly" style="width:100px"/>
					</td>
					<th style="width:80px">销售市场</th>
					<td>
						<input id="selectEditXssc" type="text" readonly="readonly" style="width:100px"/>
					</td>
					<th style="width:80px">信用等级</th>
					<td>
						<input id="selectEditXydj" type="text" readonly="readonly" style="width:100px"/>
					</td>
					<th style="width:80px">重要程度</th>
					<td>
						<input id="selectEditZycd" type="text" readonly="readonly" style="width:100px"/>
					</td>
				</tr>
			</table>
		</div>
		<div id = "editRegion">
			<p class="titlefortop">联系人信息</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">联系人姓名</th>
					<td><input id="txtEditLxrxm" name="联系人姓名" maxlength="10" readonly="readonly" /></td>
					<th style="width:100px">手机号码</th>
					<td><input id="txtEditSjhm" name="手机号码" maxlength="10" readonly="readonly" /></td>
				</tr>
				<tr>
					<th style="width:100px">联系人QQ</th>
					<td><input id="txtEditLxrqq" name="联系人QQ" maxlength="10" readonly="readonly" /></td>
					<th style="width:100px">联系人E-Mail</th>
					<td><input id="txtEditLxremail" name="联系人E-Mail" maxlength="10" readonly="readonly" /></td>
				</tr>
			</table>
		</div>
	</body>
</html>