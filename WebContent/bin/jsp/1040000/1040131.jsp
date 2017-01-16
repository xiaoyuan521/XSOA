<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1040000.Servlet1040130"%> 
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
		<title>潜在客户-新增/修改二级页面</title>
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
				//初始化所属区域
				$('#city_3').citySelect({
					prov	:	"辽宁",
					city	:	"沈阳"
				});
				//获取父页面参数
				obj = window.parent.obj;
				if (obj.optionFlag == "Upd") {
					$('#txtEditKhmc').val(obj.KHXX_KHMC);
					$('#city_3').citySelect({
						prov	:	obj.KHXX_PROV,
						city	:	obj.KHXX_CITY
					});
					$('#selectEditHy').val(obj.KHXX_HY);
					$('#selectEditGsgm').val(obj.KHXX_GSGM);
					$('#selectEditXqdj').val(obj.KHXX_XQDJ);
					$('#selectEditLy').val(obj.KHXX_LY);
					$('#txtEditKhjj').val(obj.KHXX_KHJJ);
					$('#txtEditGddh').val(obj.KHXX_GDDH);
					$('#txtEditGscz').val(obj.KHXX_GSCZ);
					$('#txtEditGsdz').val(obj.KHXX_GSDZ);
					$('#selectEditGxdj').val(obj.KHXX_GXDJ);
					$('#selectEditXssc').val(obj.KHXX_XSSC);
					$('#selectEditXydj').val(obj.KHXX_XYDJ);
					$('#selectEditZycd').val(obj.KHXX_ZYCD);
					$('#txtEditLxrxm').val(obj.KHXX_LXRXM);
					$('#txtEditSjhm').val(obj.KHXX_LXRSJHM);
					$('#txtEditLxrqq').val(obj.KHXX_LXRQQ);
					$('#txtEditLxremail').val(obj.KHXX_LXREMAIL);
				}
				//定义保存按钮点击事件
				$('#btnSave').on('click', function() {
					if (obj.optionFlag == "Add") {
						if (funEditCheck() == false) return;
						layer.confirm('是否增加客户信息？', function() {
							layer.close(layer.index);
							if (insertRole() == true) {
								iframeLayerClose();
							}
						});
					}else if (obj.optionFlag == "Upd") {
			    		if (funEditCheck() == false) return;
			    		layer.confirm('是否修改客户信息？', function() {
			    			layer.close(layer.index);
			    			if (updateRole(obj.KHXX_KHID) == true) {
			    				iframeLayerClose();
							}
			    		});
					}
				});
				//定义取消按钮点击事件
				$('#btnCancel').on('click', function() {
					iframeLayerClose();
				});
			});
			//定义bean
			function makeBeanIn(strKHXX_KHID, strKHXX_KHMC, strKHXX_PROV, strKHXX_CITY,
					strKHXX_HY, strKHXX_GSGM, strKHXX_LX, strKHXX_XQDJ, strKHXX_LY, strKHXX_KHJJ,
					strKHXX_GDDH, strKHXX_GSCZ, strKHXX_GSDZ, strKHXX_GXDJ, strKHXX_XSSC,
					strKHXX_XYDJ, strKHXX_ZYCD, strKHXX_LXRXM, strKHXX_LXRSJHM, strKHXX_LXRQQ,
					strKHXX_LXREMAIL) {
				this.KHXX_KHID = strKHXX_KHID;
			    this.KHXX_KHMC = strKHXX_KHMC;
			    this.KHXX_PROV = strKHXX_PROV;
			    this.KHXX_CITY = strKHXX_CITY;
			    this.KHXX_HY = strKHXX_HY;
			    this.KHXX_GSGM = strKHXX_GSGM;
			    this.KHXX_LX = strKHXX_LX;
			    this.KHXX_XQDJ = strKHXX_XQDJ;
			    this.KHXX_LY = strKHXX_LY;
			    this.KHXX_KHJJ = strKHXX_KHJJ;
			    this.KHXX_GDDH = strKHXX_GDDH;
			    this.KHXX_GSCZ = strKHXX_GSCZ;
			    this.KHXX_GSDZ = strKHXX_GSDZ;
			    this.KHXX_GXDJ = strKHXX_GXDJ;
			    this.KHXX_XSSC = strKHXX_XSSC;
			    this.KHXX_XYDJ = strKHXX_XYDJ;
			    this.KHXX_ZYCD = strKHXX_ZYCD;
			    this.KHXX_LXRXM = strKHXX_LXRXM;
			    this.KHXX_LXRSJHM = strKHXX_LXRSJHM;
			    this.KHXX_LXRQQ = strKHXX_LXRQQ;
			    this.KHXX_LXREMAIL = strKHXX_LXREMAIL;
			}
			//新增用户方法
			function insertRole() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					"",
					$('#txtEditKhmc').val(),
					$('#selectEditSf').val(),
					$('#selectEditCz').val(),
					$('#selectEditHy').val(),
					$('#selectEditGsgm').val(),
					"",
					$('#selectEditXqdj').val(),
					$('#selectEditLy').val(),
					$('#txtEditKhjj').val(),
					$('#txtEditGddh').val(),
					$('#txtEditGscz').val(),
					$('#txtEditGsdz').val(),
					$('#selectEditGxdj').val(),
					$('#selectEditXssc').val(),
					$('#selectEditXydj').val(),
					$('#selectEditZycd').val(),
					$('#txtEditLxrxm').val(),
					$('#txtEditSjhm').val(),
					$('#txtEditLxrqq').val(),
					$('#txtEditLxremail').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1040130",
					data: {
						CMD    : "<%=Servlet1040130.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增客户信息成功！', 2, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增客户信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增客户信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改用户方法
			function updateRole(dataId) {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					dataId,
					$('#txtEditKhmc').val(),
					$('#selectEditSf').val(),
					$('#selectEditCz').val(),
					$('#selectEditHy').val(),
					$('#selectEditGsgm').val(),
					"",
					$('#selectEditXqdj').val(),
					$('#selectEditLy').val(),
					$('#txtEditKhjj').val(),
					$('#txtEditGddh').val(),
					$('#txtEditGscz').val(),
					$('#txtEditGsdz').val(),
					$('#selectEditGxdj').val(),
					$('#selectEditXssc').val(),
					$('#selectEditXydj').val(),
					$('#selectEditZycd').val(),
					$('#txtEditLxrxm').val(),
					$('#txtEditSjhm').val(),
					$('#txtEditLxrqq').val(),
					$('#txtEditLxremail').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1040130",
					data: {
						CMD    : "<%=Servlet1040130.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改客户信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改客户信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改客户信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (obj.optionFlag == "Add" || obj.optionFlag == "Upd") {
					if ($('#txtEditKhmc').val() == "") {
						layer.alert('请输入客户名称！', 0, '友情提示', function() {
							$('#txtEditKhmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditHy').val() == "000") {
						layer.alert('请选择行业！', 0, '友情提示', function() {
							$('#selectEditHy').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditGsgm').val() == "000") {
						layer.alert('请选择公司规模！', 0, '友情提示', function() {
							$('#selectEditGsgm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditXqdj').val() == "000") {
						layer.alert('请选择需求等级！', 0, '友情提示', function() {
							$('#selectEditXqdj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditGsdz').val() == "") {
						layer.alert('请输入公司地址！', 0, '友情提示', function() {
							$('#txtEditGsdz').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditGxdj').val() == "000") {
						layer.alert('请选择关系等级！', 0, '友情提示', function() {
							$('#selectEditGxdj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditZycd').val() == "000") {
						layer.alert('请选择重要程度！', 0, '友情提示', function() {
							$('#selectEditZycd').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditLxrxm').val() == "") {
						layer.alert('请输入联系人姓名！', 0, '友情提示', function() {
							$('#txtEditLxrxm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditSjhm').val() == "") {
						layer.alert('请输入手机号码！', 0, '友情提示', function() {
							$('#txtEditSjhm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if (obj.optionFlag == "Add") {//新增：判断是否此客户信息已存在
						if (checkUserExist() == true) {
							layer.alert('此客户信息已存在，不能新增！', 0, '友情提示');
							return false;
						}
					}
				}
				return true;
			}
			//验证重复方法
			function checkUserExist() {
				var blnRet = false;
				var beanIn = new makeBeanIn(
					"",
					$('#txtEditKhmc').val(),
					"","","","",2,"",
					"","","","","","",
					"","","","","",""
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1040130",
					data: {
						CMD    : "<%=Servlet1040130.CMD_CHK_EXIST%>",
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
		</script>
		<style>
		*{font-weight:100;}
		.eTable6 tr td input{width:140px;height:16px;}
		.eTable6 tr td select{width:85px;height:20px;}
		.titlefortop{background:#f5f5f5;font-size:16px;border-top:1px solid #ddd;}
		.bot{background:#999;color:#fff;padding:0 20px;float:right;margin:20px 10px;cursor: pointer;border-radius: 4px;} 
		</style>
	</head>
	<body style="line-height:35px;">
		<div id = "editRegion">
			<p class="titlefortop">基本信息</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px;">客户名称</th>
					<td><input id="txtEditKhmc" name="客户名称"/></td>
					<th style="width:100px">所属区域</th>
					<td>
						<div id="city_3">
							<nobr>
							<select id="selectEditSf" class="prov"></select>
							<select id="selectEditCz" class="city" disabled="disabled"></select>
							</nobr>
						</div>
					</td>
					<th style="width:100px">行业</th>
					<td>
						<select id="selectEditHy" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">电脑及通讯</option>
							<option value="1">办公用品</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:100px">公司规模</th>
					<td>
						<select id="selectEditGsgm" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">20人以下</option>
							<option value="1">20-50人</option>
						</select>
					</td>
					<th style="width:100px">需求等级</th>
					<td>
						<select id="selectEditXqdj" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">一星</option>
							<option value="1">二星</option>
							<option value="2">三星</option>
						</select>
					</td>
					<th style="width:100px">来源</th>
					<td>
						<select id="selectEditLy" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">互联网</option>
							<option value="1">朋友介绍</option>
							<option value="2">公司分配</option>
							<option value="3">主动开拓</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:100px">客户简介</th>
					<td colspan="7">
						<textarea id="txtEditKhjj" rows="3" cols="90%" style="outline:none;resize:none;"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id = "editRegion">
			<p class="titlefortop">联系方式</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">固定电话</th>
					<td><input id="txtEditGddh" name="固定电话" maxlength="10" /></td>
					<th style="width:100px">公司传真</th>
					<td><input id="txtEditGscz" name="公司传真" maxlength="10" /></td>
					<th style="width:100px">公司地址</th>
					<td colspan="3"><input id="txtEditGsdz" name="公司地址" maxlength="10" /></td>
				</tr>
			</table>
		</div>
		<div id = "editRegion">
			<p class="titlefortop">其他</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:80px">关系等级</th>
					<td>
						<select id="selectEditGxdj" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">密切</option>
							<option value="1">较好</option>
							<option value="2">一般</option>
							<option value="3">较差</option>
						</select>
					</td>
					<th style="width:80px">销售市场</th>
					<td>
						<select id="selectEditXssc" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">出口</option>
							<option value="1">内销</option>
							<option value="2">混合</option>
						</select>
					</td>
					<th style="width:80px">信用等级</th>
					<td>
						<select id="selectEditXydj" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">优秀</option>
							<option value="1">良好</option>
							<option value="2">较差</option>
						</select>
					</td>
					<th style="width:80px">重要程度</th>
					<td>
						<select id="selectEditZycd" style="width: 100px;">
							<option value="000">请选择</option>
							<option value="0">一般</option>
							<option value="1">重要</option>
							<option value="2">VIP</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div id = "editRegion">
			<p class="titlefortop">联系人信息</p>
			<table id="detailCanvas" class="eTable6">
				<tr>
					<th style="width:100px">联系人姓名</th>
					<td><input id="txtEditLxrxm" name="联系人姓名" maxlength="10" /></td>
					<th style="width:100px">手机号码</th>
					<td><input id="txtEditSjhm" name="手机号码" /></td>
				</tr>
				<tr>
					<th style="width:100px">联系人QQ</th>
					<td><input id="txtEditLxrqq" name="联系人QQ" maxlength="10" /></td>
					<th style="width:100px">联系人E-Mail</th>
					<td><input id="txtEditLxremail" name="联系人E-Mail" /></td>
				</tr>
			</table>
		</div>
		<div id="buttonCanvas" class="gToolbar gTbrCenter ">
			<input type="button" value="取消" id="btnCancel" name="btnCancel" class="bot" style="height:30px;"/>
			<input type="button" value="保存" id="btnSave" name="btnSave" class="bot" style="background:#52627c;height:30px"/>  
		</div>
	</body>
</html>