<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/theme_dark/common.css">
    <style type="text/css">
    	
    	.div404{
    		width: 400px;
    		height: 400px;
    		background-repeat: no-repeat;
    		background-size: 100% 100%;
    		margin-left: auto;
    		margin-right: auto;
    		color: #fff;
    	}
    	.error_text{
    		color:	#3475CB;
    		margin: 220px 150px;
    	}
    </style>
  </head>
  
  <body>
  	<div class="div404">
  		<p class='error_text'>数据暂未录入</p>
  	</div>
  </body>
</html>
