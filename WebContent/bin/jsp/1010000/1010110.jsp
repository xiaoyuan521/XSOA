<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1010000.Servlet1010110"%> 
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
		<title>员工信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!--表格样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmGrid.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmPaginator.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/tablesize.css?r=<%=radom %>" type="text/css">
		<!--表格样式End  -->
		<!--按钮样式Start  -->
		<link rel="stylesheet" href="<%=basePath%>/bin/css/control/buttonStyle.css?r=<%=radom %>" type="text/css">
		<!--按钮样式End  -->
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
		<!--表格脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmGrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmPaginator.js"></script>
		<!--表格脚本End  -->
		<!--插件脚本Start  -->
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
		<!--插件脚本End  -->
		<script type="text/javascript">
			var mmg;//定义表格对象
			var intheight;//定义表格高度参数
			var optionFlag;//定义操作参数
			$(document).ready(function() {
				//定义表格列值
				var cols = [
					{ title:'员工编号', name:'YHXX_YHID' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'姓名', name:'YGXX_XM' ,width:80, sortable:true, align:'center',lockDisplay: true },
					{ title:'部门', name:'BMMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'角色', name:'JSMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'职务', name:'ZWXX_ZWMC' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'性别', name:'XB' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'出生日期', name:'YGXX_CSRQ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'婚姻状况', name:'YGXX_HYZK' ,width:50, sortable:true, align:'center',lockDisplay: true  },
					{ title:'身份证号', name:'YGXX_SFZHM' ,width:150, sortable:true, align:'center',lockDisplay: true  },
					{ title:'毕业学校', name:'YGXX_BYXX' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'毕业时间', name:'YGXX_BYSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'学历', name:'YGXX_ZGXL' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'移动电话', name:'YGXX_YDDH' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'住址', name:'YGXX_ZZ' ,width:100, sortable:true, align:'center',lockDisplay: true  },
					{ title:'入职时间', name:'YGXX_RZSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'转正时间', name:'YGXX_ZZSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'离职时间', name:'YGXX_LZSJ' ,width:80, sortable:true, align:'center',lockDisplay: true  },
					{ title:'操作', name:'' ,width:30, sortable:true, align:'center',lockDisplay: true, renderer: function(val) {
						return '<img id="img-info" class="img-info" title="详情" src="<%=basePath%>/bin/img/common/detail.gif"></img>';
					}}
				];
				//计算表格高度
				intheight = document.documentElement.clientHeight - $('#editRegion').height() - $('#selectRegion').height() - 80; 
				if (intheight < 100) {
					intheight = 100;
				}
				//初始化表格
	   			mmg = $('.mmg').mmGrid({
	   				height: intheight,
	   				cols: cols,
					url: '<%=basePath%>/Servlet1010110',
					method: 'post',
					params:{CMD : "<%=Servlet1010110.CMD_SELECT%>"},
					remoteSort:true,
					sortName: 'YGXX_YGID',
					sortStatus: 'ASC',
					root: 'items',
					multiSelect: false,
					checkCol: true,
					indexCol: true,
					indexColWidth: 35,
					fullWidthRows: true,
					autoLoad: false,
					nowrap: true,
					plugins: [
						$('#pg').mmPaginator({
							limit:30
						})
					]
				});
	   			var viewTab;//定义viewTab层对象
				//定义单元格点击事件
				mmg.on('cellSelected', function(e, item, rowIndex, colIndex) {
					if ($(e.target).is('.img-info')) {
						e.stopPropagation();  //阻止事件冒泡
						//第一部分
						$('#txtInfoZh').val(item.YHXX_YHID);
						$('#txtInfoXm').val(item.YGXX_XM);
						$('#txtInfoCsrq').val(item.YGXX_CSRQ);
						$('#txtInfoNl').val(item.YGXX_NL);
						$('#txtInfoXb').val(item.XB);
						$('#selectInfoXx').val(item.YGXX_XX);
						$('#selectInfoHyzk').val(item.YGXX_HYZK);
						$('#txtInfoMz').val(item.YGXX_MZ);
						$('#txtInfoSfzhm').val(item.YGXX_SFZHM);
						$('#selectInfoHklx').val(item.YGXX_HKLX);
						$('#txtInfoHkszd').val(item.YGXX_HKSZD);
						$('#selectInfoZzmm').val(item.YGXX_ZZMM);
						$('#txtInfoByxx').val(item.YGXX_BYXX);
						$('#txtInfoSszy').val(item.YGXX_SSZY);
						$('#txtInfoZgxl').val(item.YGXX_ZGXL);
						$('#txtInfoBysj').val(item.YGXX_BYSJ);
						$('#txtInfoKqjkh').val(item.YGXX_KQJKH);
						//第二部分
						$('#selectInfoSzbm').val(item.BMMC);
						$('#selectInfoJs').val(item.JSMC);
						$('#selectInfoZwmc').val(item.ZWXX_ZWMC);
						$('#selectInfoZwdj').val(item.YGZW_ZWDJ);
						$('#selectInfoZwlx').val(item.ZWLX);
						$('#selectInfoJxjb').val(item.YGZW_JXJB);
						$('#txtInfoRzsj').val(item.YGXX_RZSJ);
						$('#txtInfoZzsj').val(item.YGXX_ZZSJ);
						$('#txtInfoLzsj').val(item.YGXX_LZSJ);
						$('#selectInfoYgzt').val(item.YGZT);
						$('#txtInfoZzsm').val(item.YGXX_ZZSM);
						$('#txtInfoLzsm').val(item.YGXX_LZSM);
						//第三部分
						$('#txtInfoBgdh').val(item.YGXX_BGDH);
						$('#txtInfoYddh').val(item.YGXX_YDDH);
						$('#txtInfoGsyj').val(item.YGXX_GSYJ);
						$('#txtInfoSryj').val(item.YGXX_SRYJ);
						$('#txtInfoZz').val(item.YGXX_ZZ);
						$('#txtInfoBz').val(item.YGXX_BZ);
						$('#txtInfoJjlxr').val(item.YGXX_JJLXR);
						$('#txtInfoJjlxdh').val(item.YGXX_JJLXDH);
						//第四部分
						$('#txtInfoWylx').val(item.YGXX_WYLX);
						$('#txtInfoWyjb').val(item.YGXX_WYJB);
						//初始化viewTab层参数
						viewTab = $.layer({
							type:1,
							border: [0],
							area: ['auto', 'auto'],
							title: false,
							shade : [0.7 , '#000' , true],
							move: '.viewTabmove',
							closeBtn: false,
							page: {
								dom: '.viewTab'
							}
						});
						//初始化显示Tab第一个标签
						var initViewDom = $('#viewJbxx'), initViewIndex = initViewDom.index(), viewMain = $('.viewContianer').children();
						initViewDom.addClass('tabnow').siblings().removeClass('tabnow');
						viewMain.eq(initViewIndex).show().siblings().hide();
					}
				});
				//定义切换viewTab事件
				var viewBtn = $('.viewTabtit').children(), viewMain = $('.viewContianer').children(), close = $('.tabclose');
				//定义标签点击事件
				viewBtn.on('click', function() {
					var othis = $(this), index = othis.index();
					othis.addClass('tabnow').siblings().removeClass('tabnow');
					viewMain.eq(index).show().siblings().hide();
				});
				//定义查询按钮点击事件
				$('#btnSearch').on('click', function() {
					loadGridByBean();
				});
				var editTab;//定义editTab层对象
				$('#btnAdd, #btnUpd').on('click', function(){
					if (optionFlag == "Upd") {
						var arrList = mmg.selectedRows();
			    		if (arrList.length <= 0) {
			    			layer.alert('请选择要修改的数据行！', 0, '友情提示');
			    			return;
			    		}
						//第一部分
						$('#txtEditZh').val(arrList[0].YHXX_YHID);
						$('#txtEditGh').val(arrList[0].YGXX_GH);
						$('#txtEditXm').val(arrList[0].YGXX_XM);
						$('#txtEditCsrq').val(arrList[0].YGXX_CSRQ);
						$('#txtEditNl').val(arrList[0].YGXX_NL);
						$('input[name="selectEditXb"][value=' + arrList[0].YGXX_XB + ']').attr('checked',true);
						$('#selectEditXx').val(arrList[0].YGXX_XX);
						$('#selectEditHyzk').val(arrList[0].YGXX_HYZK);
						$('#txtEditMz').val(arrList[0].YGXX_MZ);
						$('#txtEditSfzhm').val(arrList[0].YGXX_SFZHM);
						$('#selectEditHklx').val(arrList[0].YGXX_HKLX);
						$('#txtEditHkszd').val(arrList[0].YGXX_HKSZD);
						$('#selectEditZzmm').val(arrList[0].YGXX_ZZMM);
						$('#txtEditByxx').val(arrList[0].YGXX_BYXX);
						$('#txtEditSszy').val(arrList[0].YGXX_SSZY);
						$('#txtEditZgxl').val(arrList[0].YGXX_ZGXL);
						$('#txtEditBysj').val(arrList[0].YGXX_BYSJ);
						$('#txtEditKqjkh').val(arrList[0].YGXX_KQJKH);
						//第二部分
						loadEditSelect($('#selectEditSzbm'), 'TYPE_BMMC', '所属部门');
						$('#selectEditSzbm').val(arrList[0].BMID);
						loadEditSelect($('#selectEditJs'), 'TYPE_BMJS-' + arrList[0].BMID, '角色');
						$('#selectEditJs').val(arrList[0].YGXX_JSID);
						loadEditSelect($('#selectEditZwmc'), 'TYPE_JSZW-' + arrList[0].YGXX_JSID, '职务名称');
						$('#selectEditZwmc').val(arrList[0].ZWXX_ZWID);
						$('#selectEditZwdj').val(arrList[0].YGZW_ZWDJ);
						$('#selectEditZwlx').val(arrList[0].YGZW_ZWLX);
						$('#selectEditJxjb').val(arrList[0].YGZW_JXJB);
						$('#txtEditRzsj').val(arrList[0].YGXX_RZSJ);
						$('#txtEditZzsj').val(arrList[0].YGXX_ZZSJ);
						$('#txtEditLzsj').val(arrList[0].YGXX_LZSJ);
						$('#selectEditYgzt').val(arrList[0].YGXX_YGZT);
						$('#txtEditZzsm').val(arrList[0].YGXX_ZZSM);
						$('#txtEditLzsm').val(arrList[0].YGXX_LZSM);
						//第三部分
						$('#txtEditBgdh').val(arrList[0].YGXX_BGDH);
						$('#txtEditYddh').val(arrList[0].YGXX_YDDH);
						$('#txtEditGsyj').val(arrList[0].YGXX_GSYJ);
						$('#txtEditSryj').val(arrList[0].YGXX_SRYJ);
						$('#txtEditZz').val(arrList[0].YGXX_ZZ);
						$('#txtEditBz').val(arrList[0].YGXX_BZ);
						$('#txtEditJjlxr').val(arrList[0].YGXX_JJLXR);
						$('#txtEditJjlxdh').val(arrList[0].YGXX_JJLXDH);
						//第四部分
						$('#txtEditWylx').val(arrList[0].YGXX_WYLX);
						$('#txtEditWyjb').val(arrList[0].YGXX_WYJB);
					} else {
						//第一部分
						$('#txtEditZh').val("");
						$('#txtEditGh').val("");
						$('#txtEditXm').val("");
						$('#txtEditCsrq').val("");
						$('#txtEditNl').val("");
						$('input:radio[name="selectEditXb"]').attr('checked',false);
						$('#selectEditXx').val("");
						$('#selectEditHyzk').val("");
						$('#txtEditMz').val("");
						$('#txtEditSfzhm').val("");
						$('#selectEditHklx').val("");
						$('#txtEditHkszd').val("");
						$('#selectEditZzmm').val("");
						$('#txtEditByxx').val("");
						$('#txtEditSszy').val("");
						$('#txtEditZgxl').val("");
						$('#txtEditBysj').val("");
						$('#txtEditKqjkh').val("");
						//第二部分
						loadEditSelect($('#selectEditSzbm'), 'TYPE_BMMC', '所属部门');
						$('#selectEditSzbm').val("");
						loadEditSelect($('#selectEditJs'), 'TYPE_BMJS-000', '角色');
						$('#selectEditJs').val("");
						loadEditSelect($('#selectEditZwmc'), 'TYPE_JSZW-000', '职务名称');
						$('#selectEditZwmc').val("");
						$('#selectEditZwdj').val("");
						$('#selectEditZwlx').val("");
						$('#selectEditJxjb').val("");
						$('#txtEditRzsj').val("");
						$('#txtEditZzsj').val("");
						$('#txtEditLzsj').val("");
						$('#selectEditYgzt').val("");
						$('#txtEditZzsm').val("");
						$('#txtEditLzsm').val("");
						//第三部分
						$('#txtEditBgdh').val("");
						$('#txtEditYddh').val("");
						$('#txtEditGsyj').val("");
						$('#txtEditSryj').val("");
						$('#txtEditZz').val("");
						$('#txtEditBz').val("");
						$('#txtEditJjlxr').val("");
						$('#txtEditJjlxdh').val("");
						//第四部分
						$('#txtEditWylx').val("");
						$('#txtEditWyjb').val("");
					}
					$('#jbxx').addClass('tabnow').siblings().removeClass('tabnow');
					$('.editContianer').children().eq($('#jbxx').index()).show().siblings().hide();
					//初始化editTab层参数
					editTab = $.layer({
						type:1,
						border: [0],
						area: ['auto', 'auto'],
						title: false,
						shade : [0.7 , '#000' , true],
						move: '.editTabmove',
						closeBtn: false,
						page: {
							dom: '.editTab'
						}
					});
					//初始化显示Tab第一个标签
					var initEditDom = $('#editJbxx'), initEditIndex = initEditDom.index(), editMain = $('.editContianer').children();
					initEditDom.addClass('tabnow').siblings().removeClass('tabnow');
					editMain.eq(initEditIndex).show().siblings().hide();
				});
				//定义切换editTab事件
				var editBtn = $('.editTabtit').children(), editMain = $('.editContianer').children(), save = $('.tabsave'), cancel = $('.tabcancel');
				//定义标签点击事件
				editBtn.on('click', function() {
					var othis = $(this), index = othis.index();
					othis.addClass('tabnow').siblings().removeClass('tabnow');
					editMain.eq(index).show().siblings().hide();
				});
				//定义保存按钮点击事件
				save.on('click', function() {
					if (optionFlag == "Add") {
						if (funEditCheck() == false) return;
						layer.confirm('是否增加员工信息？', function() {
							layer.close(layer.index);
							if (insertYgInfo() == true) {
								layer.close(editTab);
								//重新查询数据
								loadGridByBean();
								mmg.deselect('all');
							}
						});
					} else if (optionFlag == "Upd") {
			    		if (funEditCheck() == false) return;
			    		layer.confirm('是否修改员工信息？', function() {
			    			layer.close(layer.index);
							var arrList = mmg.selectedRows();
			    			if (updateYgInfo(arrList[0].YGXX_YGID, arrList[0].YGZW_UUID) == true) {
			    				layer.close(editTab);
								//重新查询数据
								loadGridByBean();
								mmg.deselect('all');
							}
			    		});
					}
				});
				//定义取消按钮点击事件
				cancel.on('click', function() {
					layer.close(editTab);
				});
				//定义关闭按钮点击事件
				close.on('click', function() {
					layer.close(viewTab);
				});
				//定义删除按钮点击事件
				$('#btnDel').on('click', function() {
					var arrList = mmg.selectedRows();
					if (arrList.length <= 0) {
						layer.alert('请选择要删除的数据行！', 0, '友情提示');
						return;
					}
					layer.confirm('是否删除员工信息？', function() {
						layer.close(layer.index);
						if (deleteYgInfo(arrList[0].YGXX_YGID, arrList[0].YHXX_YHID) == true) {
							layer.close(editTab);
							//重新查询数据
							loadGridByBean();
							mmg.deselect('all');
						}
					});
				});
				//初始化下拉列表
				loadSearchSelect($('#selectSzbm'), 'TYPE_BMMC', '所属部门');
				$('#selectSzbm').change(function() {
					loadSearchSelect($('#selectJs'), 'TYPE_BMJS-' + $('#selectSzbm').val(), '角色');
			    });
				$('#selectJs').change(function() {
					loadSearchSelect($('#selectZwmc'), 'TYPE_JSZW-' + $('#selectJs').val(), '职务名称');
			    });
				loadEditSelect($('#selectEditSzbm'), 'TYPE_BMMC', '所属部门');
				$('#selectEditSzbm').change(function() {
					loadEditSelect($('#selectEditJs'), 'TYPE_BMJS-' + $('#selectEditSzbm').val(), '角色');
			    });
				$('#selectEditJs').change(function() {
					loadEditSelect($('#selectEditZwmc'), 'TYPE_JSZW-' + $('#selectEditJs').val(), '职务名称');
			    });
				//页面初始化加载数据
				loadGridByBean();
			});
			//新增按钮点击方法
			function btn_Add() {
				optionFlag = "Add";
			}
			//修改按钮点击方法
			function btn_Upd() {
				optionFlag = "Upd";
			}
			//定义查询bean
			function makeBeanInSearch(strYHXX_YHID, strYGXX_XM, strYGXX_SFZHM,
					strBMID, strYGXX_JSID, strZWXX_ZWID, strYGXX_YGZT) {
			    this.YHXX_YHID = strYHXX_YHID;
			    this.YGXX_XM = strYGXX_XM;
			    this.YGXX_SFZHM = strYGXX_SFZHM;
			    this.BMID = strBMID;
			    this.YGXX_JSID = strYGXX_JSID;
			    this.ZWXX_ZWID = strZWXX_ZWID;
			    this.YGXX_YGZT = strYGXX_YGZT;
			}
			//数据加载方法
			function loadGridByBean() {
				var beanIn = new makeBeanInSearch(
					$('#txtSelectZh').val(),
					$('#txtSelectXm').val(),
					$('#txtSelectSfzhm').val(),
					$('#selectSzbm').val(),
					$('#selectJs').val(),
					$('#selectZwmc').val(),
					$('#selectYgzt').val()
				);
				//重新查询数据
				mmg.load({
					beanLoad  :  JSON.stringify(beanIn)
				});
			}
			//定义编辑bean
			function makeBeanInEdit(strYGXX_YGID, strYGZW_UUID, strYHXX_YHID, strYGXX_GH, strYGXX_XM, strYGXX_CSRQ, strYGXX_NL, strYGXX_XB,
					strYGXX_XX, strYGXX_HYZK, strYGXX_MZ, strYGXX_SFZHM, strYGXX_HKLX, strYGXX_HKSZD, strYGXX_ZZMM, strYGXX_BYXX,
					strYGXX_SSZY, strYGXX_ZGXL, strYGXX_BYSJ, strYGXX_KQJKH, strBMID, strYGXX_JSID, strZWXX_ZWID, strYGZW_ZWDJ,
					strYGZW_ZWLX, strYGZW_JXJB, strYGXX_RZSJ, strYGXX_ZZSJ, strYGXX_LZSJ, strYGXX_YGZT, strYGXX_ZZSM, strYGXX_LZSM,
					strYGXX_BGDH, strYGXX_YDDH, strYGXX_GSYJ, strYGXX_SRYJ, strYGXX_ZZ, strYGXX_BZ, strYGXX_JJLXR, strYGXX_JJLXDH,
					strYGXX_WYLX, strYGXX_WYJB) {
				this.YGXX_YGID = strYGXX_YGID;
				this.YGZW_UUID = strYGZW_UUID;
				//第一部分
				this.YHXX_YHID = strYHXX_YHID;
				this.YGXX_GH = strYGXX_GH;
				this.YGXX_XM = strYGXX_XM;
				this.YGXX_CSRQ = strYGXX_CSRQ;
				this.YGXX_NL = strYGXX_NL;
				this.YGXX_XB = strYGXX_XB;
				this.YGXX_XX = strYGXX_XX;
				this.YGXX_HYZK = strYGXX_HYZK;
				this.YGXX_MZ = strYGXX_MZ;
				this.YGXX_SFZHM = strYGXX_SFZHM;
				this.YGXX_HKLX = strYGXX_HKLX;
				this.YGXX_HKSZD = strYGXX_HKSZD;
				this.YGXX_ZZMM = strYGXX_ZZMM;
				this.YGXX_BYXX = strYGXX_BYXX;
				this.YGXX_SSZY = strYGXX_SSZY;
				this.YGXX_ZGXL = strYGXX_ZGXL;
				this.YGXX_BYSJ = strYGXX_BYSJ;
				this.YGXX_KQJKH = strYGXX_KQJKH;
				//第二部分
				this.BMID = strBMID;
				this.YGXX_JSID = strYGXX_JSID;
				this.ZWXX_ZWID = strZWXX_ZWID;
				this.YGZW_ZWDJ = strYGZW_ZWDJ;
				this.YGZW_ZWLX = strYGZW_ZWLX;
				this.YGZW_JXJB = strYGZW_JXJB;
				this.YGXX_RZSJ = strYGXX_RZSJ;
				this.YGXX_ZZSJ = strYGXX_ZZSJ;
				this.YGXX_LZSJ = strYGXX_LZSJ;
				this.YGXX_YGZT = strYGXX_YGZT;
				this.YGXX_ZZSM = strYGXX_ZZSM;
				this.YGXX_LZSM = strYGXX_LZSM;
				//第三部分
				this.YGXX_BGDH = strYGXX_BGDH;
				this.YGXX_YDDH = strYGXX_YDDH;
				this.YGXX_GSYJ = strYGXX_GSYJ;
				this.YGXX_SRYJ = strYGXX_SRYJ;
				this.YGXX_ZZ = strYGXX_ZZ;
				this.YGXX_BZ = strYGXX_BZ;
				this.YGXX_JJLXR = strYGXX_JJLXR;
				this.YGXX_JJLXDH = strYGXX_JJLXDH;
				//第四部分
				this.YGXX_WYLX = strYGXX_WYLX;
				this.YGXX_WYJB = strYGXX_WYJB;
			}
			//新增用户方法
			function insertYgInfo() {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					"", "",
					//第一部分
					$('#txtEditZh').val(),
					$('#txtEditGh').val(),
					$('#txtEditXm').val(),
					$('#txtEditCsrq').val(),
					$('#txtEditNl').val(),
					$('input[name="selectEditXb"]:checked').val(),
					$('#selectEditXx').val(),
					$('#selectEditHyzk').val(),
					$('#txtEditMz').val(),
					$('#txtEditSfzhm').val(),
					$('#selectEditHklx').val(),
					$('#txtEditHkszd').val(),
					$('#selectEditZzmm').val(),
					$('#txtEditByxx').val(),
					$('#txtEditSszy').val(),
					$('#txtEditZgxl').val(),
					$('#txtEditBysj').val(),
					$('#txtEditKqjkh').val(),
					//第二部分
					$('#selectEditSzbm').val(),
					$('#selectEditJs').val(),
					$('#selectEditZwmc').val(),
					$('#selectEditZwdj').val(),
					$('#selectEditZwlx').val(),
					$('#selectEditJxjb').val(),
					$('#txtEditRzsj').val(),
					$('#txtEditZzsj').val(),
					$('#txtEditLzsj').val(),
					$('#selectEditYgzt').val(),
					$('#txtEditZzsm').val(),
					$('#txtEditLzsm').val(),
					//第三部分
					$('#txtEditBgdh').val(),
					$('#txtEditYddh').val(),
					$('#txtEditGsyj').val(),
					$('#txtEditSryj').val(),
					$('#txtEditZz').val(),
					$('#txtEditBz').val(),
					$('#txtEditJjlxr').val(),
					$('#txtEditJjlxdh').val(),
					//第四部分
					$('#txtEditWylx').val(),
					$('#txtEditWyjb').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010110",
					data: {
						CMD    : "<%=Servlet1010110.CMD_INSERT%>",
					    BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：新增员工信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：新增员工信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：新增员工信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//修改用户方法
			function updateYgInfo(ygid, ygzwid) {
				var blnRet = false;
				var beanIn = new makeBeanInEdit(
					ygid, ygzwid,
					//第一部分
					$('#txtEditZh').val(),
					$('#txtEditGh').val(),
					$('#txtEditXm').val(),
					$('#txtEditCsrq').val(),
					$('#txtEditNl').val(),
					$('input[name="selectEditXb"]:checked').val(),
					$('#selectEditXx').val(),
					$('#selectEditHyzk').val(),
					$('#txtEditMz').val(),
					$('#txtEditSfzhm').val(),
					$('#selectEditHklx').val(),
					$('#txtEditHkszd').val(),
					$('#selectEditZzmm').val(),
					$('#txtEditByxx').val(),
					$('#txtEditSszy').val(),
					$('#txtEditZgxl').val(),
					$('#txtEditBysj').val(),
					$('#txtEditKqjkh').val(),
					//第二部分
					$('#selectEditSzbm').val(),
					$('#selectEditJs').val(),
					$('#selectEditZwmc').val(),
					$('#selectEditZwdj').val(),
					$('#selectEditZwlx').val(),
					$('#selectEditJxjb').val(),
					$('#txtEditRzsj').val(),
					$('#txtEditZzsj').val(),
					$('#txtEditLzsj').val(),
					$('#selectEditYgzt').val(),
					$('#txtEditZzsm').val(),
					$('#txtEditLzsm').val(),
					//第三部分
					$('#txtEditBgdh').val(),
					$('#txtEditYddh').val(),
					$('#txtEditGsyj').val(),
					$('#txtEditSryj').val(),
					$('#txtEditZz').val(),
					$('#txtEditBz').val(),
					$('#txtEditJjlxr').val(),
					$('#txtEditJjlxdh').val(),
					//第四部分
					$('#txtEditWylx').val(),
					$('#txtEditWyjb').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010110",
					data: {
						CMD    : "<%=Servlet1010110.CMD_UPDATE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete :function(response){},
					success: function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：修改员工信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：修改员工信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：修改员工信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//定义删除bean
			function makeBeanInDel(strYGXX_YGID, strYGZW_UUID) {
				this.YGXX_YGID = strYGXX_YGID;
				this.YGZW_UUID = strYGZW_UUID;
			}
			//删除用户方法
			function deleteYgInfo(ygid, yhxxid) {
				var blnRet = false;
				var beanIn = new makeBeanInDel(
					ygid, yhxxid
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010110",
					data: {
						CMD    : "<%=Servlet1010110.CMD_DELETE%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "SUCCESS") {
							layer.msg('恭喜：删除员工信息成功！', 1, 9);
							blnRet = true;
						} else if (strResult == "FAILURE") {
							layer.msg('对不起：删除员工信息失败！', 1, 8);
							blnRet = false;
						} else if (strResult == "EXCEPTION") {
							layer.msg('友情提示：删除员工信息出错！', 1, 0);
							blnRet = false;
						}
					}
				});
				return blnRet;
			}
			//验证编辑输入数据
			function funEditCheck() {
				if (optionFlag == "Add" || optionFlag == "Upd") {
					if ($('#txtEditZh').val() == "") {
						layer.alert('请输入<基本信息-员工编号>！', 0, '友情提示', function() {
							$('#txtEditZh').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditXm').val() == "") {
						layer.alert('请输入<基本信息-姓名>！', 0, '友情提示', function() {
							$('#txtEditXm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('input:radio[name="selectEditXb"]:checked').val() == null) {
						layer.alert('请选择<基本信息-性别>！', 0, '友情提示', function() {
							$('input[name="selectEditXb"]').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditSfzhm').val() == "") {
						layer.alert('请输入<基本信息-身份证号码>！', 0, '友情提示', function() {
							$('#txtEditSfzhm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditSzbm').val() == "000") {
						layer.alert('请选择<岗位描述-部门>！', 0, '友情提示', function() {
							$('#selectEditSzbm').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditJs').val() == "000") {
						layer.alert('请选择<岗位描述-角色>！', 0, '友情提示', function() {
							$('#selectEditJs').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#selectEditZwmc').val() == "000") {
						layer.alert('请选择<岗位描述-职务>！', 0, '友情提示', function() {
							$('#selectEditZwmc').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditRzsj').val() == "") {
						layer.alert('请输入<岗位描述-入职时间>！', 0, '友情提示', function() {
							$('#txtEditRzsj').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if ($('#txtEditYddh').val() == "") {
						layer.alert('请输入<联系方式-移动电话>！', 0, '友情提示', function() {
							$('#txtEditYddh').focus();
							layer.close(layer.index);
						});
						return false;
					}
					if (optionFlag == "Add") {//新增：判断是否员工信息已存在
						if (checkUserExist() == "1") {
							layer.alert('员工信息已存在，不能新增！', 0, '友情提示');
							return false;
						} else if (checkUserExist() == "2") {
							layer.alert('员工所属员工编号已存在，不能新增！', 0, '友情提示');
							return false;
						} else if (checkUserExist() == "3") {
							layer.alert('员工职务已存在，不能新增！', 0, '友情提示');
							return false;
						}
					} else if (optionFlag == "Upd") {//修改：判断是否员工信息已存在
						if (checkUserExist() == "0") {
							layer.alert('员工信息不存在，不能修改！', 0, '友情提示');
							return false;
						}
					}
				}
				return true;
			}
			//定义验证重复bean
			function makeBeanInCheck(strYHXX_YHID, strYGXX_XM,
					strYGXX_SFZHM, strZWXX_ZWID) {
				this.YHXX_YHID = strYHXX_YHID;
				this.YGXX_XM = strYGXX_XM;
				this.YGXX_SFZHM = strYGXX_SFZHM;
				this.ZWXX_ZWID = strZWXX_ZWID;
			}
			//验证重复方法
			function checkUserExist() {
				var blnRet = "0";
				var beanIn = new makeBeanInCheck(
					$('#txtEditZh').val(),
					$('#txtEditXm').val(),
					$('#txtEditSfzhm').val(),
					$('#selectEditZwmc').val()
				);
				$.ajax({
					async     : false,
					type      : "post",
					dataType  : "json",
					url: "<%=basePath%>/Servlet1010110",
					data: {
						CMD    : "<%=Servlet1010110.CMD_CHK_EXIST%>",
						BeanIn : JSON.stringify(beanIn)
					},
					complete : function(response) {
					},
					success : function(response) {
						var strResult = response[0];
						if (strResult == "YGXX_EXIST") {
							blnRet = "1";
						} else if (strResult == "YHXX_EXIST") {
							blnRet = "2";
						} else if (strResult == "YGZW_EXIST") {
							blnRet = "3";
						} else {
							blnRet = "0";
						}
					}
				});
				return blnRet;
			}
		</script>
	</head>
	<body>
		<fieldset id = "selectRegion">
			<legend>查询条件</legend>
			<table>
				<tr>
					<th style="width:100px">员工编号</th>
					<td><input id="txtSelectZh" name="员工编号" /></td>
					<th style="width:100px">所在部门</th>
					<td><select id="selectSzbm" style="width: 100px"></select></td>
					<th style="width:100px">角色</th>
					<td>
						<select id="selectJs" style="width: 100px">
							<option value="000">所有</option>
						</select>
					</td>
					<th style="width:100px">职务</th>
					<td>
						<select id="selectZwmc" style="width: 100px">
							<option value="000">所有</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:100px">员工姓名</th>
					<td><input id="txtSelectXm" name="员工姓名" maxlength="10" /></td>
					<th style="width:100px">身份证号</th>
					<td colspan="3"><input id="txtSelectSfzhm" name="身份证号" style="width: 300px;" /></td>
					<th style="width:100px">状态</th>
					<td>
						<select id="selectYgzt" style="width: 100px">
							<option value="000">所有</option>
							<option value="0">试用</option>
							<option value="1">正式</option>
							<option value="2">离职</option>
						</select>
					</td>
					<th  style="width:100px"><input type="button" value="查询" id="btnSearch" class="btn btn-primary btn-sm" /></th>
				</tr>
			</table>
		</fieldset>
		<div id="gridCanvas">
			<table id="mmg" class="mmg"></table>
			<div id="pg" style="text-align: right;"></div>
		</div>
		<fieldset id = "editRegion">
			<legend>操作</legend>
			<div id="buttonCanvas" class="gToolbar gTbrCenter ">
				<input type="button" value="新增" id="btnAdd" onclick="btn_Add()" class="btn btn-primary btn-sm" />
				<input type="button" value="修改" id="btnUpd" onclick="btn_Upd()" class="btn btn-primary btn-sm" />
				<input type="button" value="删除" id="btnDel" class="btn btn-primary btn-sm" />
			</div>
		</fieldset>
		<jsp:include page="1010111.jsp" flush="false"></jsp:include>
		<jsp:include page="1010112.jsp" flush="false"></jsp:include>
	</body>
</html>