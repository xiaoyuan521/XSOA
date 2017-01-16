<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1040000.Servlet1040120"%>
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
    <title>个人战绩</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <script src="<%=basePath%>/bin/js/jquery.min.js?v=2.1.4" type="text/javascript" ></script>
    <script src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.js"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.pie.js"></script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        var list = getDataList();
        var slList = getSlList();
        setNPL(list);
        setGRSL(slList);
        $(".collapse-link").click(function() {
            var o = $(this).closest("div.ibox"),
                e = $(this).find("i"),
                i = o.find("div.ibox-content");
            i.slideToggle(200), e.toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"), o.toggleClass("").toggleClass("border-bottom"), setTimeout(function() {
                o.resize(), o.find("[id^=map-]").resize()
            }, 50)
        })

    });
    function getDataList(){
        var list = [];
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040120",
		data: {
			CMD    : "<%=Servlet1040120.CMD_SELECT%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}
    function getSlList(){
        var list = [];
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040120",
		data: {
			CMD    : "<%=Servlet1040120.CMD_SELECT_SL%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}
    //拿牌率（饼状图）
    function setNPL(dataList) {
        var e = [];
        var sColor = ['#FF0000','#800080','#00FA9A','#1E90FF','#FAFAD2','#E0FFFF','#00FFFF']
        $.each(dataList, function(k, v) {
            e.push({
                label : v.LRJS,
                data : v.NPL,
                color : sColor[k]
            });
		});

        $.plot($("#flot-pie-chart"), e, {
            series: {
                pie: {
                    show: !0
                }
            },
            grid: {
                hoverable: !0
            },
            tooltip: !0,
            tooltipOpts: {
                content: "%p.0%, %s",
                shifts: {
                    x: 20,
                    y: 0
                },
                defaultTheme: !1
            }
        })
    }
    //个人胜率（柱状图）
    function setGRSL(dataList) {
        var i = 0;
        var ticks = [];
        var data1 = [];
        $.each(dataList, function(k, v) {
            var temp = [i+1, v.LRJS];
            var temp1 = [i+1, v.GRSL];
            i += 3;
            ticks.push(temp);
            data1.push(temp1);
 	    });
        var dataset = [
            { label: "胜率", data: data1, color: "red" }
        ];
        var e = {
                 series: {
                 bars: {
                 show: true,
                 barWidth: .2,
                 fill: !0,
                 fillColor: {
                   colors: [{
                       opacity: .8
                   }, {
                   opacity: .8
                   }]
                 }
                 }
                 },
                 bars: {
                     align: "left",
                     barWidth: .8
                 },
                 xaxis: {
                     axisLabel: "",
                     axisLabelUseCanvas: true,
                     axisLabelFontSizePixels: 5,
                     axisLabelFontFamily: 'Verdana, Arial',
                     axisLabelPadding: 5,
                     ticks: ticks
                 },
                 colors: ["#1ab394"],
                 grid: {
                     color: "#999999",
                     hoverable: true,
                     clickable: !0,
                     tickColor: "#D4D4D4",
                     borderWidth: 2
                 },
                 legend: {
                     noColumns: 0,
                     labelBoxBorderColor: "#000000",
                      position: "nw"
                 }
        }
        $.plot($("#flot-bar-chart"), dataset, e);
        $("#flot-bar-chart").UseTooltip();
    }
    var previousPoint = null, previousLabel = null;
    $.fn.UseTooltip = function () {
        $(this).bind("plothover", function (event, pos, item) {
            if (item) {
                if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                    previousPoint = item.dataIndex;
                    previousLabel = item.series.label;
                    $("#tooltip").remove();

                    var x = item.datapoint[0];
                    var y = item.datapoint[1];
                    var color = item.series.color;
                    showTooltip(item.pageX,
                            item.pageY,
                            color,
                            "<strong>" + item.series.label + "</strong> : <strong>" + y + "</strong> ");
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });
    };
    function showTooltip(x, y, color, contents) {
        $('<div id="tooltip">' + contents + '%</div>').css({
            position: 'absolute',
            display: 'none',
            top: y - 40,
            left: x - 20,
            border: '2px solid ' + color,
            padding: '3px',
            'font-size': '9px',
            'border-radius': '5px',
            'background-color': '#fff',
            'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
            opacity: 0.9
        }).appendTo("body").fadeIn(200);
    }
    </script>
  </head>
  <body class="gray-bg" style="overflow-y: auto">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-sm-6" style="width:100%; height:80%">
          <div class="ibox float-e-margins" style="width:100%">
            <div class="ibox-title" style="width:100%">
              <h5>拿牌率</h5>
              <div class="ibox-tools">
                <a class="collapse-link">
                  <i class="fa fa-chevron-up"></i>
                </a>
              </div>
            </div>
            <div class="ibox-content">
              <div class="flot-chart">
                <div class="flot-chart-content" id="flot-pie-chart"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6" style="width:100%; height:80%">
          <div class="ibox float-e-margins" style="width:100%">
            <div class="ibox-title" style="width:100%">
              <h5>个人胜率 <small>(百分比)</small></h5>
              <div class="ibox-tools">
                <a class="collapse-link">
                  <i class="fa fa-chevron-up"></i>
                </a>
              </div>
            </div>
            <div class="ibox-content">
              <div class="flot-chart">
                <div class="flot-chart-content" id="flot-bar-chart"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>