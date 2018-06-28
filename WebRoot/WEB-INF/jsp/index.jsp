<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>GLSERVER测试</title>

    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }

        a, img {
            border: 0;
            text-decoration: none;
        }

        body {
            font: 14px/180% Arial, Helvetica, sans-serif, "新宋体";
        }

        #options {
            z-index: 7000;
            position: absolute;
            padding-bottom: 10px;
            padding-left: 10px;
            width: 120px;
            padding-right: 10px;
            background: #e7e7e7;
            color: #999;
            top: 0px;
            right: 0px;
            padding-top: 10px
        }

        #options p {
            height: 24px;
            line-height: 24px;
        }

        #options a {
            color: #999;
            text-decoration: none
        }

        #options a:hover {
            background: #666666;
            color: #fff
        }

        #settings {
            z-index: 8000;
            position: absolute;
            text-indent: -99999px;
            width: 43px;
            display: block;
            background: url(/glserver/static/pic/setting.gif) no-repeat 0px 0px;
            height: 43px;
            overflow: hidden;
            top: 0px;
            cursor: pointer;
            right: 0px
        }

        #settings:hover {
            background: url(/glserver/static/pic/setting.gif) no-repeat 0px -86px
        }

        .cerrar {
            background: url(/glserver/static/pic/setting.gif) no-repeat 0px -43px !important;
        }
    </style>

    <script type="text/javascript" src="/glserver/static/js/jquery-1.3.2.js"></script>
    <script src="/glserver/static/js/three.js"></script>
    <script src="/glserver/static/js/jquery-2.2.4.min.js"></script>
    <script src="/glserver/static/js/stats.min.js"></script>
    <script src="/glserver/static/js/OrbitControls.js"></script>
    <script src="/glserver/static/js/ThreeBSP.js"></script>
    <script src="/glserver/static/js/curves/NURBSCurve.js"></script>
    <script src="/glserver/static/js/curves/NURBSSurface.js"></script>
    <script src="/glserver/static/js/curves/NURBSUtils.js"></script>
    <script src="/glserver/static/js/Tween.min.js"></script>
    <script src="/glserver/static/js/CSS3DRenderer.js"></script>
    <script src="/glserver/static/js/common.js"></script>
    <script src="/glserver/static/js/d3d.js"></script>
    <script type="text/javascript">
        // 操作菜单
    	$(document).ready(function () {
            $('#options').hide();
            $('#settings').click(function () {
                $('#options').slideToggle();
                $(this).toggleClass("cerrar");
            });
            
	         // 初始化
	         let ins = new d3d("#canvas-frame", 0xCCC);
	         ins.start();
           
        });
        
    </script>

</head>
<body>
<div id="canvas-frame"></div>
<div id="settings">设置</div>
<div id="options" style="display: none;">
    <p><a href="javascript:void(0)">Cube</a></p>
    <p><a href="javascript:void(0)">Sphere</a></p>
    <p><a href="javascript:void(0)">Label</a></p>
</div>
</body>
</html>