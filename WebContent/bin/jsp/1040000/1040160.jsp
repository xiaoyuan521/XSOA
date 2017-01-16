<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1040000.Servlet1040160"%>
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
    <title>排位赛</title>
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
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.barnumbers.js"></script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        var list = getDataList();
        setDWXX(list);
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
		url: "<%=basePath%>/Servlet1040160",
		data: {
			CMD    : "<%=Servlet1040160.CMD_SELECT%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}

    function getDwList(){
        var list = [];
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040160",
		data: {
			CMD    : "<%=Servlet1040160.CMD_SELECT_DW%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}

    //段位信息
    function setDWXX(dataList) {
        var yticks = [];
        var dwList = getDwList();
        $.each(dwList, function(k, v) {
            var temp = [v.DWDJ_MAX, v.DWDJ_DWMC];
            yticks.push(temp);
        });

        var i = 0;
        var dwList = [];
        var xticks = [];
        var data1 = [];
        $.each(dataList, function(k, v) {
            dwList.push({
                id : v.DWXX_GRJF,
                name : v.DWXX_DWMC
            });
            var temp = [i+1, v.YHXX_YHMC];
            var temp1 = [i+1, v.DWXX_GRJF];

            i += 2;
            xticks.push(temp);
            data1.push(temp1);
        });
        var dataset = [
            { label: "积分", data: data1, color: "#666666" }
        ];
        var e = {
                 series: {
                 bars: {
                 show: true,
                 barWidth: .2,
                 fill: !0,
                 fillColor: {
                   colors: [{
                       opacity: .9
                   }, {
                   opacity: .9
                   }]
                 }
                 }
                 },
                 bars: {
                     align: "left",
                     barWidth: .9
                 },
                 xaxis: {
                     axisLabel: "",
                     axisLabelUseCanvas: true,
                     axisLabelFontSizePixels: 5,
                     axisLabelFontFamily: 'Verdana, Arial',
                     axisLabelPadding: 5,
                     ticks: xticks
                 },
                 yaxis: {
                     show: true,
                     ticks: yticks,
                     tickSize: 2,
                     axisLabelUseCanvas: true,
                     axisLabelFontSizePixels: 5,
                     axisLabelFontFamily: 'Verdana, Arial',
                     axisLabelPadding: 5

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
        var somePlot = $.plot($("#flot-bar-chart"), dataset, e);
        var ctx = somePlot.getCanvas().getContext("2d");
        var data = somePlot.getData()[0].data;
        var xaxis = somePlot.getXAxes()[0];
        var yaxis = somePlot.getYAxes()[0];
        var offset = somePlot.getPlotOffset();
        ctx.font = "12px 'Segoe UI'";
        ctx.fillStyle = "#A42D00";
        for (var i = 0; i < data.length; i++){
            var text = data[i][1] + '';
            var text1;
            $.each(dwList, function(index, item) {
                if (Number(text) == item.id) {
					text1 = item.name;
                }
            });
            var metrics = ctx.measureText(text);
            var xPos = xaxis.p2c(data[i][0]) + offset.left;
            var yPos = yaxis.p2c(data[i][1]) + offset.top + metrics.width - 16;
            ctx.save();
            ctx.translate(xPos, yPos);
            //ctx.rotate(-Math.PI/2);
            ctx.fillText(text1, 1, 1);
            ctx.restore();
        }
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
        $('<div id="tooltip">' + contents + '</div>').css({
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
        <div class="col-sm-6" style="width:100%; height:500px">
          <div class="ibox float-e-margins" style="width:100%; height:100%">
            <div class="ibox-title" style="width:100%">
              <h5>团队排位</h5>
              <div class="ibox-tools">
                <a class="collapse-link">
                  <i class="fa fa-chevron-up"></i>
                </a>
              </div>
            </div>
            <div class="ibox-content" style="height:100%">
              <div class="flot-chart" style="height:90%">
                <div class="flot-chart-content" id="flot-bar-chart"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6" style="width:100%; height:80%">
          <div class="ibox float-e-margins" style="width:100%">
            <div class="ibox-title" style="width:100%">
              <h5>排位规则</h5>
              <div>
              <br>0~15 小羊羔：胜+7分 负-4分<br>
              16~30 小绵羊：胜+6分 负-4分<br>
              31~45 老山羊：胜+5分 负-3分<br>
              46~60 小狼崽：胜+4分 负-3分<br>
              61~75 大灰狼：胜+3分 负-2分<br>
              76~90 金刚狼：胜+2分 负-2分<br>
              91+ 最强狼王：胜+1分 负-3分
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>