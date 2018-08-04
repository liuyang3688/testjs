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

        #help {
            position: absolute;
            padding: 20px;
            top: 60px;
            right:20px;
            width:400px;
            background: #00B2E4;
            border-radius: 20px;
            color: white;
            font-size:14px;
            opacity: 0.9;
        }
        #help p {
            margin-bottom: 1px;
        }
    </style>
    <link rel="stylesheet" href="/glserver/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/glserver/static/css/bootstrapValidator.min.css" />
    <link rel="stylesheet" href="/glserver/static/css/customValidator.css" />
    <%--<script type="text/javascript" src="/glserver/static/js/jquery-1.3.2.js"></script>--%>

</head>
<body>
<div id="canvas-frame"></div>
<div id="help" style="display:none">
    <button type="button" class="close" aria-label="Close" onclick="javascript:D3DOBJ.help();">
        <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="mb-3">使用帮助</h4>
    <p><div>1. 如何选择某排机柜</div>选择当前排两侧机柜的行标签，右键单击即可。</p>
    <p><div>2. 如何选择单个机柜</div>选择当前机柜顶上的列标签，右键单击即可</p>
    <p><div>3. 如何查看机柜内设备</div>左键双击机柜的前门即可</p>
    <p><div>4. 如何查看单台设备信息</div>左键单击设备即可</p>
    <p><div>5. 如何查看网线连接</div>点击菜单-配线管理即可</p>
    <p><div>6. 如何查看供电情况</div>点击菜单-供电电缆即可</p>
    <p><div>7. 查看空调风向</div>点击菜单-空调风向，即可查看当前机房的空调风走向</p>
    <p><div>8. 查看机柜利用率</div>点击菜单-空间利用率，即可查看所有记过使用情况。</p>
    <p><div>9. 查看动环数据</div>点击菜单-动环数据，即可查看动环设备实时数据刷新情况。</p>
    <p><div>10. 选择推荐机柜</div>点击菜单-推荐机柜，即可按照对话框提示进行新加设备的机柜选择。</p>
</div>
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