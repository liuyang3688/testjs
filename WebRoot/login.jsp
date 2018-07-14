<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!-- saved from url=(0078)http://www.17sucai.com/preview/2/2016-07-26/jQuery-VerificationCode/index.html -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>大唐新能源甘肃公司生产调度平台</title>
<meta name="author" content="DeathGhost">
<link rel="stylesheet" type="text/css" href="<%=path%>/static/plugins/login/style.css">
<style>
body {
	height: 100%;
	background: #228CC6;
	overflow: hidden;
}

canvas {
	z-index: -1;
	position: absolute;
}

.layout{
	text-decoration: underline;
}
</style>
 <!-- lay_ui -->
 <link href="<%=path %>/static/plugins/layui/css/layui.css" rel="stylesheet">
 <script type="text/javascript" src="<%=path %>/static/plugins/layui/layui.js"></script>
<script src="<%=path%>/static/plugins/login/jquery.js"></script>
<script src="<%=path%>/static/plugins/login/verificationNumbers.js"></script>
<script src="<%=path%>/static/plugins/login/Particleground.js"></script>
<script>
	$(document).ready(function() {
		
		$(".user_icon .login_txtbx").focus();
		if(top.location!=self.location){
			top.location = self.location;
		}
// 		top.location.href="<%=path%>/index.jsp";
		//粒子背景特效
		$('body').particleground({
			dotColor : '#5cbdaa',
			lineColor : '#5cbdaa'
		});
		//验证码
		//测试提交，对接程序删除即可
		$(".submit_btn").click(function() {
			submit();
		});
		
		//键盘监听
		 document.onkeydown=function(event){
	            var e = event || window.event || arguments.callee.caller.arguments[0];
	            if(e && e.keyCode==13){ // enter 键
	                 //要做的事情
	            	submit();
	            }
	      }; 
	});
	function submit(){
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		//if(userAgent.indexOf("Chrome") == -1){
		//	layui.use('layer', function(){
		//		var layer = layui.layer;
		//		layer.msg("请下载使用Chrome浏览器登录!");
		//	});
		//	return;
		//}
		var username = $(".user_icon input").val();
		var password = $(".pwd_icon input").val();
		if(username == null || typeof username == "undefined" || username == ""){
			layui.use('layer', function(){
				var layer = layui.layer;
				layer.msg("用户名不能为空!");
			});
			return;
		}
		if(password == null || typeof password == "undefined" || password == ""){
			layui.use('layer', function(){
				var layer = layui.layer;
				layer.msg("密码不能为空!");
			});
			return;
		}
		$.ajax({
			url: "<%=path%>/login/logon?name=" + username + "&pass=" + password + "&etc=" + new Date().getTime(),
			success: function(data){
				if(data == "OK"){
					window.location.href="<%=path%>/view.do?name=theme_dark/index";
				}else{
					layui.use('layer', function(){
						var layer = layui.layer;
						layer.msg(data);
					});
				}
			}
		});
	}
	function installChrome(){
		location.href = "<%=path%>/tool/ChromeSetup.exe";
	};
</script>

</head>
<body>
	<canvas class="pg-canvas" width="1366" height="612"></canvas>
	<dl class="admin_login">
		<dt>
			<strong>大唐新能源甘肃公司生产调度平台</strong> <em>Management System</em>
		</dt>
		<dd class="user_icon">
			<input type="text" placeholder="账号" class="login_txtbx">
		</dd>
		<dd class="pwd_icon">
			<input type="password" placeholder="密码" class="login_txtbx">
		</dd>
		<dd class="val_icon" style="display: none;">
			<div class="checkcode">
				<input type="text" id="J_codetext" placeholder="输入验证码" maxlength="4"
					class="login_txtbx">
				<canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
			</div>
			<input type="button" value="验证码核验" class="ver_btn"
				onclick="validate();">
		</dd>
		<dd>
			<input type="button" value=">>" class="submit_btn">
		</dd>
		<dt>
			<sub>请</sub>
			<sub onclick="installChrome()" class="layout" style="cursor:pointer">下载</sub>
			<sub>chrome浏览器</sub>
		</dt>
		<dd>
		</dd>
	</dl>
</body>
</html>