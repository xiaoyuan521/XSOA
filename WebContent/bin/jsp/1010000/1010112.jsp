<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>员工信息—查看二级页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style>
			.viewTab{position:relative; display:none; width:800px; height:600px; border:1px solid #ccc;}
			.viewTabmove{position:absolute; width:800px; height:30px; top:0; left:0;}
			.viewTabtit{ display:block; height:30px; border-bottom:1px solid #ccc; background-color:#eee;}
			.viewTabtit span{position:relative; float:left; width:120px; height:30px; line-height:30px; text-align:center; cursor:pointer;}
			.viewTabtit span.tabnow{left:-1px; _top:1px; height:31px; border-left:1px solid #ccc; border-right:1px solid #ccc; background-color:#fff; z-index:10;}
			.viewContianer{line-height:24px; clear:both;}
			.viewContianer ul{display:none;}
			.viewContianer ul.partOneInfo{display:block;}
			.viewContianer ul li{display:block;height: 40px;line-height: 40px;width: 360px;float: left;}
			.viewContianer ul li div{width: 100px;display: inline-block; text-align: right;padding-right: 10px;}
			.tabclose{position:absolute; right:10px; top:7px; cursor:pointer;}
			.input_style input{border: 1px solid #ddd;width: 153px;height: 26px;}
			.input_style select{border: 1px solid #ddd;width: 157px;height: 32px;position: relative;left: -5px;}
			.radiobtn input{width: 30px;position: relative;top: 8px;}
		</style>
	</head>
	<body>
		<form id="studentForm">
			<div class="viewTab">
				<span class="viewTabmove"></span>
				<div class="viewTabtit">
			    	<span id="viewJbxx" class="tabnow">基本信息</span>
			        <span>岗位描述</span>
			        <span>联系方式</span>
			        <span>其他信息</span>
			    </div>
			    <div class="viewContianer">
			    	<ul class="partOneInfo input_style">
			        	<li><div>员工编号:</div><input id="txtInfoZh" name="员工编号" type="text" maxlength="10" readonly="readonly"/></li>
<!-- 			        	<li><div>工号:</div><input id="txtInfoGh" type="text" readonly="readonly"></li> -->
			        	<li><div>姓名:</div><input id="txtInfoXm" type="text" readonly="readonly"></li>
			        	<li><div>出生日期:</div><input id="txtInfoCsrq" type="text" readonly="readonly"></li>
			        	<li><div>年龄:</div><input id="txtInfoNl" type="text" readonly="readonly"></li>
			        	<li><div>性别:</div><input id="txtInfoXb" type="text" readonly="readonly"></li>
			        	<li><div>血型:</div><input id="selectInfoXx" type="text" readonly="readonly"></li>
			        	<li><div>婚姻状况:</div><input id="selectInfoHyzk" type="text" readonly="readonly"></li>
			        	<li><div>民族:</div><input id="txtInfoMz" type="text" readonly="readonly"></li>
			        	<li><div>身份证号码:</div><input id="txtInfoSfzhm" type="text" readonly="readonly">
			        	<li><div>户口类型:</div><input id="selectInfoHklx" type="text" readonly="readonly">
			        	<li><div>户口所在地:</div><input id="txtInfoHkszd" type="text" readonly="readonly"></li>
			        	<li><div>政治面貌:</div><input id="selectInfoZzmm" type="text" readonly="readonly"></li>
			        	<li><div>毕业学校:</div><input id="txtInfoByxx" type="text" readonly="readonly"></li>
			        	<li><div>所学专业:</div><input id="txtInfoSszy" type="text" readonly="readonly"></li>
			        	<li><div>最高学历:</div><input id="txtInfoZgxl" type="text" readonly="readonly"></li>
			        	<li><div>毕业时间:</div><input id="txtInfoBysj" type="text" readonly="readonly"></li>
			        	<li><div>考勤机卡号:</div><input id="txtInfoKqjkh" type="text" readonly="readonly"></li>
			        </ul>
			    	<ul class="partTwoInfo input_style">
			        	<li><div>部门:</div><input id="selectInfoSzbm" type="text" readonly="readonly"></li>
			        	<li><div>角色:</div><input id="selectInfoJs" type="text" readonly="readonly"></li>
			        	<li><div>职务:</div><input id="selectInfoZwmc" type="text" readonly="readonly"></li>
			        	<li><div>职务等级:</div><input id="selectInfoZwdj" type="text" readonly="readonly"></li>
			        	<li><div>职务类型:</div><input id="selectInfoZwlx" type="text" readonly="readonly"></li>
			        	<li><div>绩效级别:</div><input id="selectInfoJxjb" type="text" readonly="readonly"></li>
			        	<li><div>入职时间:</div><input id="txtInfoRzsj" type="text" readonly="readonly"></li>
			        	<li><div>转正时间:</div><input id="txtInfoZzsj" type="text" readonly="readonly"></li>
			        	<li><div>离职时间:</div><input id="txtInfoLzsj" type="text" readonly="readonly"></li>
			        	<li><div>当前员工状态:</div><input id="selectInfoYgzt" type="text" readonly="readonly"></li>
			        	<li><div>转正说明:</div><textarea id="txtInfoZzsm" rows="3" cols="20" readonly="readonly"></textarea></li>
			        	<li><div>离职说明:</div><textarea id="txtInfoLzsm" rows="3" cols="20" readonly="readonly"></textarea></li>
			        </ul>
			    	<ul class="partThreeInfo input_style">
			        	<li><div>办公电话:</div><input id="txtInfoBgdh" type="text" readonly="readonly"></li>
			        	<li><div>移动电话:</div><input id="txtInfoYddh" type="text" readonly="readonly"></li>
			        	<li><div>公司邮件:</div><input id="txtInfoGsyj" type="text" readonly="readonly"></li>
			        	<li><div>私人邮件:</div><input id="txtInfoSryj" type="text" readonly="readonly"></li>
			        	<li><div>住址:</div><input id="txtInfoZz" type="text" readonly="readonly"></li>
			        	<li><div>备注:</div><input id="txtInfoBz" type="text" readonly="readonly"></li>
			        	<li><div>紧急联系人:</div><input id="txtInfoJjlxr" type="text" readonly="readonly"></li>	
			        	<li><div>紧急联系电话:</div><input id="txtInfoJjlxdh" type="text" readonly="readonly"></li>
			        </ul>
			    	<ul class="partFourInfo input_style">
			        	<li><div>外语类型:</div><input id="txtInfoWylx" type="text" readonly="readonly"></li>
			        	<li><div>外语级别:</div><input id="txtInfoWyjb" type="text" readonly="readonly"></li>
			        </ul>
			    </div>
			    <span class="tabclose" title="关闭">关闭</span>
			</div>
		</form>
	</body>
</html>