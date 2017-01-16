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
    <title>玩家胜率</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<%=basePath%>/bin/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
        <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
        <!-- Morris -->
        <link href="<%=basePath%>/bin/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
        <!-- Gritter -->
        <link href="<%=basePath%>/bin/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
        <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
        <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">

    <script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
    <script src="<%=basePath%>/bin/js/jquery.min.js?v=2.1.4" type="text/javascript" ></script>
    <script src="<%=basePath%>/bin/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.js"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="<%=basePath%>/bin/js/plugins/flot/jquery.flot.pie.js"></script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        setJRFG();
        var list = getDataList();
        var lrxxList = getLrxxList();
        setFlotData(list);
        setLrxxData(lrxxList);
        $(".collapse-link").click(function() {
            var o = $(this).closest("div.ibox"),
                e = $(this).find("i"),
                i = o.find("div.ibox-content");
            i.slideToggle(200), e.toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"), o.toggleClass("").toggleClass("border-bottom"), setTimeout(function() {
                o.resize(), o.find("[id^=map-]").resize()
            }, 50)
        })
    });
    function setFlotData(list) {
        var i = 0;
        var ticks = [];
        var data1 = [];
        var data2 = [];
        $.each(list, function(k, v) {
            var temp = [i+1, v.YHXX_YHMC];
            var temp1 = [i, v.SLXX_SCSL];
            var temp2 = [i+1, v.SLXX_ZTSL];
            i += 3;
            ticks.push(temp);
            data1.push(temp1);
            data2.push(temp2);
		});
        var dataset = [
                       { label: "上次胜率", data: data1, color: "#5482FF" },
                       { label: "本次胜率", data: data2, color: "red" }
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
    function setLrxxData(list) {
        var lrslList = getLrslList();
        var sp = $("#whole");

        $.each(list, function(k, v) {
                var html = "";
                html += "<div class='row'><div class='col-sm-6' style='width:100%; height:80%'><div class='ibox float-e-margins' style='width:100%'><div class='ibox-title' style='width:100%'><h5>";
           		html += v.LRXX_LRMC+"胜率<small>(百分比)</small></h5>";
           		html += "<div class='ibox-tools'><a class='collapse-link'><i class='fa fa-chevron-up'></i></a></div></div>";
           		html += "<div class='ibox-content'><div class='flot-chart'><div class='flot-chart-content' id='flot_" + v.LRXX_LRID + "'></div>";
          		html += "</div></div></div></div></div>";
                if (v.LRXX_LRID == "01") {
                    var i = 0;
                    var ticks = [];
                    var data1 = [];
                    $.each(lrslList, function(k, v1) {
                        var temp = [i+1, v1.YHMC];
                        var temp1 = [i+1, v1.LRSL];
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
                         sp.after(html);
                         $.plot($("#flot_"+v.LRXX_LRID), dataset, e);
                         $("#flot_"+v.LRXX_LRID).UseTooltip();
                }
                if (v.LRXX_LRID == "02") {
                    var i = 0;
                    var ticks = [];
                    var data1 = [];
                    $.each(lrslList, function(k, v1) {
                        var temp = [i+1, v1.YHMC];
                        var temp1 = [i+1, v1.LQSL];
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
                         sp.after(html);
                         $.plot($("#flot_"+v.LRXX_LRID), dataset, e);
                         $("#flot_"+v.LRXX_LRID).UseTooltip();
                }
                if (v.LRXX_LRID == "03") {
                    var i = 0;
                    var ticks = [];
                    var data1 = [];
                    $.each(lrslList, function(k, v1) {
                        var temp = [i+1, v1.YHMC];
                        var temp1 = [i+1, v1.NWSL];
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
                         sp.after(html);
                         $.plot($("#flot_"+v.LRXX_LRID), dataset, e);
                         $("#flot_"+v.LRXX_LRID).UseTooltip();
                }
                if (v.LRXX_LRID == "04") {
                    var i = 0;
                    var ticks = [];
                    var data1 = [];
                    $.each(lrslList, function(k, v1) {
                        var temp = [i+1, v1.YHMC];
                        var temp1 = [i+1, v1.XZSL];
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
                         sp.after(html);
                         $.plot($("#flot_"+v.LRXX_LRID), dataset, e);
                         $("#flot_"+v.LRXX_LRID).UseTooltip();
                }
                if (v.LRXX_LRID == "05") {
                    var i = 0;
                    var ticks = [];
                    var data1 = [];
                    $.each(lrslList, function(k, v1) {
                        var temp = [i+1, v1.YHMC];
                        var temp1 = [i+1, v1.PMSL];
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
                         sp.after(html);
                         $.plot($("#flot_"+v.LRXX_LRID), dataset, e);
                         $("#flot_"+v.LRXX_LRID).UseTooltip();
                }
		})

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
    function setJRFG(){
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040130",
		data: {
			CMD    : "<%=Servlet1040130.CMD_SELECT_JRFG%>"
		},
		complete :function(response){},
		success: function(response){
		    var strResult = response[0];
			if (strResult == "SUCCESS") {
				var resultRecord = response[1];
				$("#txtJRFG").text("今日法官："+resultRecord.JRFG_FGID);
				$("#txtBYFG").text("备用法官："+resultRecord.JRFG_BYID);
			} else {
				layer.msg('友情提示：获取法官出错！', 1, 0);
			}
		}
		});
	}

    function getDataList(){
        var list = [];
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040130",
		data: {
			CMD    : "<%=Servlet1040130.CMD_SELECT%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}
    function getLrxxList(){
        var list = [];
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040130",
		data: {
			CMD    : "<%=Servlet1040130.CMD_SELECT_LRXX%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}
    function getLrslList(){
        var list = [];
		$.ajax({
		async     : false,
		type      : "post",
		dataType  : "json",
		url: "<%=basePath%>/Servlet1040130",
		data: {
			CMD    : "<%=Servlet1040130.CMD_SELECT_LRSL%>"
		},
		complete :function(response){},
		success: function(response){
			list = response[0];
		}
		});
		return list;
	}
    </script>
  </head>
  <body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight" style="height:700px; overflow:auto;">
      <div class="row" id="whole">
        <div class="col-sm-6" style="width:100%; height:80%">
          <div class="ibox float-e-margins" style="width:100%">
            <div class="ibox-title" style="width:100%">
              <h5>总胜率 <small>(百分比)</small><span style="margin-left:80px" id="txtJRFG"></span><span style="margin-left:80px" id="txtBYFG"></span></h5>
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