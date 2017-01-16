<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>员工信息—新增修改二级页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style>
			.editTab{position:relative; display:none; width:800px; height:600px; border:1px solid #ccc;}
			.editTabmove{position:absolute; width:800px; height:30px; top:0; left:0;}
			.editTabtit{ display:block; height:30px; border-bottom:1px solid #ccc; background-color:#eee;}
			.editTabtit span{position:relative; float:left; width:120px; height:30px; line-height:30px; text-align:center; cursor:pointer;}
			.editTabtit span.tabnow{left:-1px; _top:1px; height:31px; border-left:1px solid #ccc; border-right:1px solid #ccc; background-color:#fff; z-index:10;}
			.editContianer{line-height:24px; clear:both;}
			.editContianer ul{display:none;}
			.editContianer ul.partOneInfo{display:block;}
			.editContianer ul li{display:block;height: 40px;line-height: 40px;width: 360px;float: left;}
			.editContianer ul li div{width: 100px;display: inline-block; text-align: right;padding-right: 10px;}
			.tabsave{position:absolute; right:50px; top:7px; cursor:pointer;}
			.tabcancel{position:absolute; right:10px; top:7px; cursor:pointer;}
			.input_style input{border: 1px solid #ddd;width: 153px;height: 26px;}
			.input_style select{border: 1px solid #ddd;width: 157px;height: 32px;position: relative;left: -5px;}
			.radiobtn input{width: 30px;position: relative;top: 8px;}
			.color{color:red;}
		</style>
	</head>
	<body>
		<form id="studentForm">
			<div class="editTab">
				<span class="editTabmove"></span>
				<div class="editTabtit">
			    	<span id="editJbxx" class="tabnow">基本信息</span>
			        <span>岗位描述</span>
			        <span>联系方式</span>
			        <span>其他信息</span>
			    </div>
			    <div class="editContianer">
			    	<ul class="partOneInfo input_style">
			        	<li><div>员工编号:</div><input id="txtEditZh" name="员工编号" type="text" maxlength="10"/><span class="color">&nbsp;*</span></li>
			        	<li><div>姓名:</div><input id="txtEditXm" type="text"><span class="color">&nbsp;*</span></li>
			        	<li><div>出生日期:</div><input id="txtEditCsrq" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></li>
			        	<li><div>年龄:</div><input id="txtEditNl" type="text"></li>
			        	<li class="radiobtn"><div>性别:</div>
			        		<input id="nan" name="selectEditXb" type="radio" value="0">男
							<input id="nv" name="selectEditXb" type="radio" value="1">女
							<span class="color">&nbsp;*</span>
			        	</li>
			        	<li><div>血型:</div>
			        		<select id="selectEditXx">
			        			<option value="000">请选择</option>
			        			<option value="A型">A型</option>
			        			<option value="B型">B型</option>
			        			<option value="O型">O型</option>
			        			<option value="AB型">AB型</option>
			        		</select>
			        	</li>
			        	<li><div>婚姻状况:</div>
			        		<select id="selectEditHyzk">
			        			<option value="000">请选择</option>
			        			<option value="未婚">未婚</option>
			        			<option value="已婚">已婚</option>
			        		</select>
			        	</li>
			        	<li><div>民族:</div><input id="txtEditMz" type="text"></li>
			        	<li><div>身份证号码:</div><input id="txtEditSfzhm" type="text"><span class="color">&nbsp;*</span></li>
			        	<li><div>户口类型:</div>
			        		<select id="selectEditHklx">
			        			<option value="000">请选择</option>
			        			<option value="城镇">城镇</option>
			        			<option value="农村">农村</option>
			        		</select>
			        	</li>
			        	<li><div>户口所在地:</div><input id="txtEditHkszd" type="text"></li>
			        	<li><div>政治面貌:</div>
			        		<select id="selectEditZzmm">
			        			<option value="000">请选择</option>
			        			<option value="共青团员">共青团员</option>
			        			<option value="党员">党员</option>
			        			<option value="群众">群众</option>
			        		</select>
			        	</li>
			        	<li><div>毕业学校:</div><input id="txtEditByxx" type="text"></li>
			        	<li><div>所学专业:</div><input id="txtEditSszy" type="text"></li>
			        	<li><div>最高学历:</div><input id="txtEditZgxl" type="text"></li>
			        	<li><div>毕业时间:</div><input id="txtEditBysj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></li>
			        	<li><div>考勤机卡号:</div><input id="txtEditKqjkh" type="text"></li>
			        </ul>
			    	<ul class="partTwoInfo input_style">
			        	<li><div>部门:</div>
			        		<select id="selectEditSzbm"></select><span class="color">&nbsp;*</span>
			        	</li>
			        	<li><div>角色:</div>
			        		<select id="selectEditJs"></select><span class="color">&nbsp;*</span>
						</li>
			        	<li><div>职务:</div>
			        		<select id="selectEditZwmc"></select><span class="color">&nbsp;*</span>
			        	</li>
			        	<li><div>职务等级:</div>
			        		<select id="selectEditZwdj" style="width:</div> 100px">
								<option value="000">请选择</option>
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
							</select>
			        	</li>
			        	<li><div>职务类型:</div>
			        		<select id="selectEditZwlx">
			        			<option value="1" selected="selected">专职</option>
			        		</select>
			        	</li>
			        	<li><div>绩效级别:</div>
			        		<select id="selectEditJxjb">
								<option value="000">请选择</option>
								<option value="1级">1级</option>
								<option value="2级">2级</option>
								<option value="3级">3级</option>
								<option value="4级">4级</option>
								<option value="5级">5级</option>
								<option value="6级">6级</option>
								<option value="7级">7级</option>
								<option value="8级">8级</option>
								<option value="9级">9级</option>
							</select>
			        	</li>
			        	<li>
			        		<div>当前员工状态:</div>
			        		<select id="selectEditYgzt">
			        			<option value="000">请选择</option>
			        			<option value="0">试用</option>
			        			<option value="1">正式</option>
			        			<option value="2">离职</option>
			        		</select>
			        	</li>
			        	<li><div>入职时间:</div><input id="txtEditRzsj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()"><span class="color">&nbsp;*</span></li>
			        	<li><div>转正时间:</div><input id="txtEditZzsj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></li>
			        	<li><div>离职时间:</div><input id="txtEditLzsj" type="text" readonly="readonly" class="laydate-icon-default" onclick="laydate()"></li>
			        	<li><div>转正说明:</div><textarea id="txtEditZzsm" rows="3" cols="20"></textarea></li>
			        	<li><div>离职说明:</div><textarea id="txtEditLzsm" rows="3" cols="20"></textarea></li>
			        </ul>
			    	<ul class="partThreeInfo input_style">
			        	<li><div>办公电话:</div><input id="txtEditBgdh" type="text"></li>
			        	<li><div>移动电话:</div><input id="txtEditYddh" type="text"><span class="color">&nbsp;*</span></li>
			        	<li><div>公司邮件:</div><input id="txtEditGsyj" type="text"></li>
			        	<li><div>私人邮件:</div><input id="txtEditSryj" type="text"></li>
			        	<li><div>住址:</div><input id="txtEditZz" type="text"></li>
			        	<li><div>备注:</div><input id="txtEditBz" type="text"></li>
			        	<li><div>紧急联系人:</div><input id="txtEditJjlxr" type="text"></li>	
			        	<li><div>紧急联系电话:</div><input id="txtEditJjlxdh" type="text"></li>
			        </ul>
			    	<ul class="partFourInfo input_style">
			        	<li><div>外语类型:</div><input id="txtEditWylx" type="text"></li>
			        	<li><div>外语级别:</div><input id="txtEditWyjb" type="text"></li>
			        </ul>
			    </div>
			    <span class="tabsave" title="保存">保存</span>
			    <span class="tabcancel" title="取消">取消</span>
			</div>
		</form>
	</body>
</html>