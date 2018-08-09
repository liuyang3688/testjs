<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/15
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String cpath = request.getContextPath(); %>
<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>导航页</title>
    <link href="<%=cpath%>/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="box">
    <div id="box-caption">黄山电网资产管理</div>
    <div id="box-contents">
        <div class="panel">
            <div id="link1" class="panel_img"></div>
            <div class="panel_desc"><a href="webgl">3D机房</a></div>
        </div>
        <div class="panel">
            <div id="link2" class="panel_img"></div>
            <div class="panel_desc"><a href="page_two">台账信息</a></div>
        </div>
        <div class="panel">
            <div id="link3" class="panel_img"></div>
            <div class="panel_desc"><a href="page_three">网络设备</a></div>
        </div>
    </div>
</div>
</body>
</html>

