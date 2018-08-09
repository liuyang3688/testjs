<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/21
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String cpath = request.getContextPath(); %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Babylon Template</title>
    <style>
        html, body {
            overflow: hidden;
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        #renderCanvas {
            width: 100%;
            height: 100%;
            touch-action: none;
        }
    </style>
    <script src="<%=cpath%>/js/babylon.js"></script>
    <script src="<%=cpath%>/js/babylonjs.loaders.js"></script>
    <script src="<%=cpath%>/js/pep.js"></script>
    <script src="<%=cpath%>/js/hand.js"></script>
</head>
<body>
<canvas id="renderCanvas" touch-action="none"></canvas>

<script>
    var size = 20;
    // X坐标轴
    var xAxisPoints =[
        new BABYLON.Vector3(-1*size, 0, 0),
        new BABYLON.Vector3(size, 0, 0),
        new BABYLON.Vector3(0.95*size, 0.05*size, 0),
        new BABYLON.Vector3(size, 0, 0),
        new BABYLON.Vector3(0.95*size, -0.05*size, 0)
    ];
    // Y坐标轴
    var yAxisPoints =[
        new BABYLON.Vector3(0, -1*size, 0),
        new BABYLON.Vector3(0, size, 0),
        new BABYLON.Vector3(0, 0.95*size, 0.05*size),
        new BABYLON.Vector3(0, size, 0),
        new BABYLON.Vector3(0, 0.95*size, -0.05*size),
    ];
    // z坐标轴
    var zAxisPoints =[
        new BABYLON.Vector3(0, 0, -1*size),
        new BABYLON.Vector3(0, 0, size),
        new BABYLON.Vector3(0.05*size, 0, 0.95*size),
        new BABYLON.Vector3(0, 0, size),
        new BABYLON.Vector3(-0.05*size, 0, 0.95*size),
    ];
    var canvas = document.getElementById("renderCanvas");
    var engine = new BABYLON.Engine(canvas, true);

    var createScene = function () {

        var scene = new BABYLON.Scene(engine);
        // //Parameters: name, position, scene
        //var camera = new BABYLON.UniversalCamera("UniversalCamera", new BABYLON.Vector3(0, 0, -20), scene);
        // //Parameters: alpha, beta, radius, target position, scene
        var camera = new BABYLON.ArcRotateCamera("ArcRCamera", Math.PI / 2, Math.PI / 2, 2, new BABYLON.Vector3(-10, 10, 10), scene);
        // camera.inertia = 1;
        // camera.speed = 1;
        camera.setTarget(BABYLON.Vector3.Zero());
        camera.attachControl(canvas, true);
        var light1 = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(1, 1, 0), scene);
        //var light2 = new BABYLON.PointLight("light2", new BABYLON.Vector3(0, 1, -1), scene);
        var xAxis = BABYLON.MeshBuilder.CreateLines("lines", {points: xAxisPoints}, scene);
        var yAxis = BABYLON.MeshBuilder.CreateLines("lines", {points: yAxisPoints}, scene);
        var zAxis = BABYLON.MeshBuilder.CreateLines("lines", {points: zAxisPoints}, scene);
        xAxis.color = new BABYLON.Color3(1,0,0);
        yAxis.color = new BABYLON.Color3(0,1,0);
        zAxis.color = new BABYLON.Color3(0,0,1);

        var floor = BABYLON.MeshBuilder.CreatePlane("floor", {width: 10, height: 10,sideOrientation: BABYLON.Mesh.DOUBLESIDE}, scene);
        var myBox = BABYLON.MeshBuilder.CreateBox("myBox", {height: 5, width: 2, depth: 0.5}, scene);
        floor.position.set(0,0,0);
        floor.rotation.set(Math.PI/2,0,0);
        return scene;
    };

    var scene = createScene();

    engine.runRenderLoop(function () {
        scene.render();
    });


    window.addEventListener("resize", function () { // Watch for browser/canvas resize events
        engine.resize();
    });
</script>

</body>

</html>