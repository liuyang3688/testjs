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
    <%--<script src="<%=cpath%>/js/babylon.objFileLoader.js"></script>--%>
    <script src="<%=cpath%>/js/pep.js"></script>
    <script src="<%=cpath%>/js/hand.js"></script>
</head>
<body>
    <canvas id="renderCanvas" touch-action="none"></canvas>

<script>
    var canvas = document.getElementById("renderCanvas");
    var engine = new BABYLON.Engine(canvas, true);

    var createScene = function () {

        var scene = new BABYLON.Scene(engine);
        var camera = new BABYLON.ArcRotateCamera("Camera", Math.PI / 2, Math.PI / 2, 2, BABYLON.Vector3(5,5,5), scene);
        // //camera.setTarget(0,0,0);
        camera.attachControl(canvas, true);
        // camera.inertia = 1;
        // camera.speed = 1;
        //var light1 = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(1, 1, 0), scene);
        var light2 = new BABYLON.HemisphericLight("light2", new BABYLON.Vector3(0, 10, -10), scene);
        light2.
        var open = 1;
        var loader = BABYLON.SceneLoader.Append("<%=cpath%>/model/", "jifang3.gltf", scene, function (scene) {
            //console.log("2");
            // var mesh = scene.getMeshByName("Grp_Box");
            // mesh.actionManager = new BABYLON.ActionManager(scene);
            // mesh.actionManager.registerAction(
            //     new BABYLON.ExecuteCodeAction(
            //         BABYLON.ActionManager.OnPickTrigger,
            //         function (e) {
            //             console.log('mouse clicked');
            //             //e.source.setPivotMatrix(BABYLON.Matrix.Translation(-1, -1, -1));
            //             window.open("https://www.baidu.com");
            //             e.source.rotate(BABYLON.Axis.Z, Math.PI/4);
            //         }
            //     )
            // );
            // ).then(
            //     new BABYLON.ExecuteCodeAction(
            //         BABYLON.ActionManager.OnPointerOverTrigger,
            //         function(){
            //             console.log("mouse hover the mesh");
            //         }
            //     )
            // );
        });

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