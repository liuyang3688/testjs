<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>GLSERVER测试</title>
    <link rel="shortcut icon" href="/glserver/favicon.ico"/>
    <link rel="bookmark" href="/glserver/favicon.ico"/>
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
        #title{
            height: 19px;
            border-top: #85ABE4 1px solid;
            border-right: #222 1px solid;
            border-left: #85ABE4 1px solid;
            border-bottom: none;
            background: #5B8BD9;
            color:#FFFFFF;
            text-align: center;
            font-size:14px
        }
        #contents{
            border: #85ABE4 1px solid;
            border-top: none;
            padding: 2px 5px;
            font-size: 13px;
            float:left;
        }
        #contents{
            background: url(/glserver/static/pic/sh.jpg) no-repeat;
        }

        #img{
            /*background: url(/glserver/static/pic/s.jpg) no-repeat;*/
            float: right;
            width: 49px;
            height: 19px;
            line-height: 19px;
        }
        #img:hover{
            /*background: url(/glserver/static/pic/sh.jpg) no-repeat;*/
            background-color: #0000ff;
            cursor: pointer;
        }
        #ten{float: left;width: 200px;}
        .none{display: none;}
        .show{display: block;}
        #contents .content {
            clear: both;
            text-align:center;
        }
        #contents .key{
            width:80px;
            text-align:center;
            float:left;
            color:#0000ff;
        }
        #contents .value{
            width:200px;
            text-align:center;
            float:right;
            padding-left:10px;
        }
    </style>
    <link rel="stylesheet" href="/glserver/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/glserver/static/css/bootstrapValidator.min.css" />
    <link rel="stylesheet" href="/glserver/static/css/customValidator.css" />
    <%--<script type="text/javascript" src="/glserver/static/js/jquery-1.3.2.js"></script>--%>

</head>
<body>
<div id="canvas-frame"></div>
</body>
<script src="/glserver/static/js/three.js"></script>
<script src="/glserver/static/js/jquery-3.0.0.min.js"></script>
<script src="/glserver/static/js/stats.min.js"></script>
<script src="/glserver/static/js/OrbitControls.js"></script>
<script src="/glserver/static/js/ThreeBSP.js"></script>
<script src="/glserver/static/js/curves/NURBSCurve.js"></script>
<script src="/glserver/static/js/curves/NURBSSurface.js"></script>
<script src="/glserver/static/js/curves/NURBSUtils.js"></script>
<script src="/glserver/static/js/Tween.min.js"></script>
<script src="/glserver/static/js/CSS3DRenderer.js"></script>
<script src="/glserver/static/js/common.js"></script>
<script src="/glserver/static/js/popper.min.js"></script>
<script src="/glserver/static/js/bootstrap.min.js"></script>
<script src="/glserver/static/js/bootstrapValidator.min.js"></script>
<script src="/glserver/static/js/d3d.js"></script>
<script type="text/javascript">
    // 操作菜单
    $(document).ready(function () {
        // 初始化
        let ins = new D3DLib("#canvas-frame", 0xCCC);
        ins.start();

    });
</script>
</html>