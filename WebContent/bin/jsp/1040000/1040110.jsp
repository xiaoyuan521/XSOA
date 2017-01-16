<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1040000.Servlet1040110"%>
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
    <title>游戏介绍</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<%=basePath%>/bin/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
        <!-- Morris -->
        <link href="<%=basePath%>/bin/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
        <!-- Gritter -->
        <link href="<%=basePath%>/bin/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
        <link href="<%=basePath%>/bin/css/animate.min.css" rel="stylesheet">
        <link href="<%=basePath%>/bin/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">

    <script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
    <script type="text/javascript">
      $(document).ready(function() {

    });
    //新增按钮点击方法
      function btn_Add() {

    }
    </script>
  </head>
  <body class="gray-bg">
      <div class="wrapper wrapper-content" style="height:700px; overflow:auto;">
        <div class="row">
    <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>狼人杀（游戏）</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="index.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="index.html#">选项1</a>
                                </li>
                                <li><a href="index.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content ibox-heading">
                        <h3>狼人杀又名狼人，是一款多人参与的，以语言描述推动的、较量口才和分析判断能力的策略类桌面游戏。通常的版本需要8-18人参与。</h3>
                        <small><i class="fa fa-map-marker"></i> 桌面游戏</small>
                    </div>
                    <div class="ibox-content timeline">

                        <div class="timeline-item">
                            <div class="row">
                                <div>
                                   背景
                                </div>
                                <div class="col-xs-7 content no-top-border">
                                    <p>一. 有关狼人：</p>
                                    <p><span data-diameter="40" class="updating-chart">狼人起源于很久以前的欧洲，那时候由于瘟疫，村子里只活下来一个年轻人。他的
游戏道具
游戏道具(10张)
 后代有三个（不知道是和谁恋爱生的孩子，因为是“只活下来一个年轻人”？？？），一个被毒蝙蝠咬了，一个让毒狼咬了，只有一个比较正常。前两位一个是吸血鬼的祖先，一个是狼人祖先。
狼人可以活到100岁，肉食，月圆之夜会变成狼的形态，平时却和普通人一样。鉴别狼人的唯一方法是：狼人在人的状态时中指与无名指的长度相同，看到这儿，请大家互相检查，如有符合此条件的人，请悄悄报警。
狼人害怕银制品，液态的硝酸银可以使他们丧失战斗力！请善良的村民夜路自备。如果实在找不到硝酸银的话，一般的银器也可以使狼人感到巨大的灼痛。
被狼人咬伤不死的人会变成狼人，请记住，平常的日子里狼人看起来和我们一样善良，有很多就生活在我们身边！他们行为举止温文尔雅，甚至穿西装戴眼镜儿说话没有脏字儿……但也有很多狼人生活在郊外森林里。
狼人变身后是没有理智的，杀了人也不知道。但少数理智的善良的狼人为了控制自己不伤害人，晚上变身的时候会去一个荒无人烟的地方，避免伤害人类。所以，如果你身边有那种喜欢晚上去森林里，早晨回来，并周期性重复这个行为的人，有可能就是一个善良的狼人。</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="timeline-item">
                            <div class="row">
                                                                                        游戏目标
                                <div class="col-xs-7 content">
                                    <p class="m-b-xs"><strong>ddddddd</strong>
                                    </p>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
      </div>
      </div>
  </body>
</html>