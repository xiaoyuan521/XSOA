<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	long radom = System.currentTimeMillis();
    System.out.print(basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>layui WebIM 1.0beta Demo</title>
    <link href="<%=basePath%>/bin/css/layim.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/bin/js/lay/lib.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/lay/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bin/js/lay/layim.js"></script>
</head>
<body>
</body>
</html>