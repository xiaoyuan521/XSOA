<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xsoa.servlet.servlet1020000.Servlet1020120"%>
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
		<title>签到记录</title>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <meta name="author" content="Benjamin Joffe">
        <meta name="Content-Script-Type" content="text/javascript">
        <meta name="Content-Style-Type" content="text/css">
        <STYLE>
        <!--
        @import "<%=basePath%>/bin/js/tetris/css/main4.css";
        -->
        <!--
        @import "<%=basePath%>/bin/js/tetris/css/all.css";
        -->
        #container {
          margin:0 auto;
          text-align:center;}
        .cyr {
          text-align:center;}
        </STYLE>
		<script type="text/javascript" src="<%=basePath%>/bin/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/common.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/core.js?r=<%=radom %>"></script>
		<script type="text/javascript" src="<%=basePath%>/bin/js/tetris/js/main.js"></script>
        <script type="text/javascript" src="<%=basePath%>/bin/js/tetris/js/logic_min2.js"></script>
        <script type="text/javascript" src="<%=basePath%>/bin/js/tetris/js/ga.js" ></script>

		<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		try {
		    var pageTracker = _gat._getTracker("UA-7116912-1");
		    pageTracker._trackPageview();
		}catch(err) {

		}
		</script>
	</head>
	<body>
  <DIV id="benjoffe">
  <DIV id="content">
  <DIV id="loading" style="display: none; "><img src="<%=basePath%>/bin/js/tetris/js/loading.gif" alt="" style="width:16px;height:16px;vertical-align:top;"> Loading game...</DIV>
    <DIV id="container" style="visibility: visible; ">
      <DIV id="menu" style="display:none; ">
        <DIV id="menu_area">
          <DIV id="screen0">
            <DIV id="helpBox">
              <P><B>说明</B><BR>
                使用箭头键来引导掉块,空格键和向上箭头键可以旋转块，旋转方向相反。
                或者利用A，W，S和D键达到相同目的。<BR>
                <BR>
                当一个完整的15个块的水平线被填满时，该行将消除，并获得100点。
                如果多行被清除，你将获得额外的积分。</P></DIV>
            <DIV id="copy">&#169;2016 <A href="http://www.html5china.com/">Microad</A></DIV>
            <DIV id="but_main0" class="but"></DIV>
          </DIV>
          <DIV id="screen1">
            <DIV id="go1" class="but"></DIV>
            <DIV id="go2" class="nonstick"></DIV>
            <DIV id="go3" class="nonstick"></DIV>
            <DIV id="but_main1" class="but"></DIV>
          </DIV>
          <DIV id="screen2">
            <DIV id="quote">"A <B>circle</B> may be small, yet it may be as mathematically beautiful and perfect as a large one."<BR>
              <SPAN>- Isaac Disraeli</SPAN></DIV>
            <DIV id="but_play" class="but"></DIV>
            <DIV id="but_settings" class="but"></DIV>
            <DIV id="but_high" class="but"></DIV>
            <DIV id="but_help" class="but"></DIV>
          </DIV>
          <DIV id="screen3">
            <SELECT id="bestType">
              <OPTION>传统模式</OPTION>
              <OPTION>Time Attack</OPTION>
              <OPTION>Garbage</OPTION>
            </SELECT>
            <DIV id="best1"><B>Traditional</B><BR>
              1. Empty (0)<BR>
              2. Empty (0)<BR>
              3. Empty (0)</DIV>
            <DIV id="best2"><B>Time Attack</B><BR>
              1. Empty (0)<BR>
              2. Empty (0)<BR>
              3. Empty (0)</DIV>
            <DIV id="best3"><B>Garbage</B><BR>
              1. Empty (59:59)<BR>
              2. Empty (59:59)<BR>
              3. Empty (59:59)</DIV>
            <DIV id="but_main2" class="but"></DIV>
          </DIV>
          <DIV id="screen4">
            <DIV id="div_base">
              <LABEL>Skin:
                <SELECT id="set_base">
                  <OPTION>Glass</OPTION>
                </SELECT>
              </LABEL>
            </DIV>
            <DIV id="div_ghost">
              <LABEL>
                <INPUT id="set_ghost" type="checkbox">
                Show ghost</LABEL>
            </DIV>
            <DIV id="but_main3" class="but"></DIV>
          </DIV>
        </DIV>
      </DIV>
      <DIV id="out"></DIV>
      <DIV id="playing" style="background-image: url(<%=basePath%>/bin/js/tetris/img/base0.png); ">
        <CANVAS id="canvas" width="200" height="400" style="opacity: 0; "></CANVAS>
        <DIV id="paused" style="opacity: 1; display: block; ">
          <DIV id="but_resume" class="but"></DIV>
          <DIV id="but_restart" class="but"></DIV>
          <DIV id="but_quit" class="but"></DIV>
        </DIV>
        <DIV id="panel" style="display: none; ">
          <DIV id="title1" style="display: block; "></DIV>
          <DIV id="title2" style="display: none; "></DIV>
          <DIV id="title3" style="display: none; "></DIV>
          <DIV id="score">0</DIV>
          <DIV id="time">0:00</DIV>
          <DIV id="next" style="background-position: -240px 0px; "></DIV>
          <DIV id="but_pause" class="but"></DIV>
        </DIV>
        <DIV id="gameover" style="display: block; ">
          <DIV id="winner" style="display: block; ">
            <FORM id="high_form">
              You have achieved a high score, please enter your name:<BR>
              <INPUT type="text" id="high_name" maxlength="20">
              <INPUT type="submit" value="OK">
            </FORM>
          </DIV>
          <DIV id="newgame" style="display: none; ">
            <DIV id="sorryText"></DIV>
            <DIV id="skull"></DIV>
            <DIV id="but_restart2" class="but"></DIV>
            <DIV id="but_main4" class="but"></DIV>
          </DIV>
        </DIV>
      </DIV>
      <DIV id="close" class="nonstick" style="left: 435px; top: 185px; "></DIV>
    </DIV>
  </DIV>
  <DIV id="foot">
      <script src="http://s15.cnzz.com/stat.php?id=2297124&amp;web_id=2297124" language="JavaScript"></script>
    </div>
  </DIV>
</DIV>
</body>
</html>