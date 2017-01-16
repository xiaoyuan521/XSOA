<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020160"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>月度出勤</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--表格样式Start  -->
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmGrid.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/mmPaginator.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/normalize.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/bin/plugin/mmgrid/css/tablesize.css" type="text/css">
<!--表格样式End  -->
<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/js/core.js"></script>
<!--表格脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/mmgrid/mmPaginator.js"></script>
<!--表格脚本End  -->
<!--插件脚本Start  -->
<script type="text/javascript" src="<%=basePath%>/bin/plugin/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/bin/plugin/laydate/laydate.js"></script>
<!--插件脚本End  -->
<script type="text/javascript">	
	var mmg;
	var intheight;
	//初始化表格
	$(document).ready(function(){
	   var cols = [
	       { title:'考勤日期', name:'KQRQ' ,width:100, sortable:true, align:'center',lockDisplay: false },
	       { title:'部门名称', name:'BMMC' ,width:100, sortable:true, align:'center',lockDisplay: false  },
	       { title:'员工姓名', name:'YGXM' ,width:80,sortable:true, align:'center',lockDisplay: false },
	       { title:'01日', name:'BCRC_DAY01' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'02日', name:'BCRC_DAY02' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'03日', name:'BCRC_DAY03' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'04日', name:'BCRC_DAY04' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'05日', name:'BCRC_DAY05' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'06日', name:'BCRC_DAY06' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'07日', name:'BCRC_DAY07' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'08日', name:'BCRC_DAY08' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'09日', name:'BCRC_DAY09' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'10日', name:'BCRC_DAY10' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'11日', name:'BCRC_DAY11' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'12日', name:'BCRC_DAY12' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'13日', name:'BCRC_DAY13' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'14日', name:'BCRC_DAY14' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'15日', name:'BCRC_DAY15' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'16日', name:'BCRC_DAY16' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'17日', name:'BCRC_DAY17' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'18日', name:'BCRC_DAY18' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'19日', name:'BCRC_DAY19' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'20日', name:'BCRC_DAY20' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'21日', name:'BCRC_DAY21' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'22日', name:'BCRC_DAY22' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'23日', name:'BCRC_DAY23' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'24日', name:'BCRC_DAY24' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'25日', name:'BCRC_DAY25' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'26日', name:'BCRC_DAY26' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'27日', name:'BCRC_DAY27' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'28日', name:'BCRC_DAY28' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'29日', name:'BCRC_DAY29' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'30日', name:'BCRC_DAY30' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'31日', name:'BCRC_DAY31' ,width:35, sortable:true, align:'center',lockDisplay: true  },
	       { title:'操作', name:'' ,width:40, sortable:true, align:'center',lockDisplay: true, renderer: function(val){
				return '<button class="btn btn-info">详情</button>';
			}},
	   ];
	
	   intheight = document.documentElement.clientHeight -$('#editRegion').height()-$('#selectRegion').height()-80; 
	   if(intheight<100){
		   intheight = 100;
	   }
	
	   mmg = $('.mmg').mmGrid({
	         height: intheight
	        ,cols: cols
	        ,url: '<%=basePath%>/Servlet1020160'
	        ,method: 'post'
	        ,params:{CMD : "<%=Servlet1020160.CMD_SELECT%>"}
	        ,remoteSort:true
	        ,sortName: 'BCRC_BCRCID'
	        ,sortStatus: 'ASC'
	        ,root: 'items'
	        ,multiSelect: false
	        ,checkCol: false
	        ,indexCol: true
	        ,indexColWidth: 35
	        ,fullWidthRows: true
	        ,autoLoad: false
	        ,plugins: [
	            $('#pg').mmPaginator({
	              limit:30
	            })
	        ]
	      });
	   
	   mmg.on('cellSelected', function(e, item, rowIndex, colIndex){
			//查看
			if($(e.target).is('.btn-info')){
				e.stopPropagation();  //阻止事件冒泡
				obj = new Object();
				obj.BCRC_BCRCID=item.BCRC_BCRCID;
				obj.BCRC_BCID=$("#txtSelBCMC").val();
				obj.BCRC_NY=item.BCRC_NY;			
				obj.BCRC_BCMC=item.BCMC;		
				obj.BCRC_BCSD=item.BCSD;
				obj.BCRC_SDID=item.SDID;
				obj.BCRC_SDMC=item.SDMC;
				iframeLayerOpenown('<%=basePath%>/bin/jsp/1020000/1020161.jsp');
			}
	   });
	   	   
	   $('#btnSearch').on('click', function(){
	   	   if($("#txtSelectKqrq").val()==""){
	    	   layer.alert('请选择考勤日期！', 0, '友情提示');
			   return;
		   }
		   loadGridByBean();
	   });
	   loadSearchSelect($("#txtSelectBmmc"),"TYPE_BMMC","部门名称");
	});
		//自定义弹出层方法
	function iframeLayerOpenown(url) {
		$.layer({
	        type: 2,
	        title: false,
	        maxmin: false,
	        shadeClose: false, //开启点击遮罩关闭层
	        area : ['720px' , '520px'],
	        offset : ['20px', ''],
	        iframe: {src: url},
	        end : function(){//弹出层彻底关闭执行的回调函数
	        	loadGridByBean();
	        }
	    });
	}
	function makeBeanIn(strKQRQ,strBMID,strYGXM){	
		this.KQRQ = strKQRQ;
	    this.BMID = strBMID;
	    this.YGXM = strYGXM;
	}
	function loadGridByBean(){
		var beanIn = new makeBeanIn(
			$('#txtSelectKqrq').val(),//考勤日期
			$('#txtSelectBmmc').val(),//部门名称		
			$('#txtSelectYgxm').val()//员工姓名
		);
		//重新查询数据
		mmg.load({
	    	beanLoad  :  JSON.stringify(beanIn)
		});
	}
</script>
</head>
<body>
	<fieldset id = "selectRegion">
		<legend>查询条件</legend>
		<table>
			<tr>
				<th style="width:100px">考勤日期</th>
					<td><input id="txtSelectKqrq" type="text" style="width: 100px;height: 16px;font-size:14px;line-height:14px;" readonly="readonly" class="laydate-icon-default" onclick="laydate({format:'YYYY-MM'})"></td>
				<th style="width:100px">部门名称</th>
				<td>
					<select id="txtSelectBmmc"> 
						<option value="000" selected>请选择</option> 
					</select> 		
				</td>
				<th style="width:100px">员工姓名</th>
				<td><input id="txtSelectYgxm" name="员工姓名"  style="width:80px"/></td>
				<th  style="width:100px"><input type="button" value="查询" id="btnSearch" /></th>
			</tr>
		</table>
	</fieldset>
	<div id="gridCanvas">
		<table id="mmg" class="mmg"></table>
		<div id="pg" style="text-align: right;"></div>
	</div>
</body>
</html>