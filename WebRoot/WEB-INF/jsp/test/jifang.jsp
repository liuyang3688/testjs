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
    <script src="<%=cpath%>/js/pep.js"></script>
</head>
<body>
<canvas id="renderCanvas" touch-action="none"></canvas>

<script>
    var canvas = document.getElementById("renderCanvas");
    var engine = new BABYLON.Engine(canvas, true);

    var createScene = function () {

        var scene = new BABYLON.Scene(engine);
        var camera = new BABYLON.ArcRotateCamera("Camera", Math.PI / 2, Math.PI / 2, 2, BABYLON.Vector3.Zero(), scene);
        camera.attachControl(canvas, true);

        var light1 = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(1, 1, 0), scene);
        var light2 = new BABYLON.PointLight("light2", new BABYLON.Vector3(0, 1, -1), scene);

        var body = BABYLON.MeshBuilder.CreateCylinder("body", { height: 0.75, diameterTop: 0.2, diameterBottom: 0.5, tessellation: 6, subdivisions: 1 }, scene);
        var arm = BABYLON.MeshBuilder.CreateBox("arm", { height: 0.75, width: 0.3, depth: 0.1875 }, scene);
        arm.position.x = 0.125;
        var pilot = BABYLON.Mesh.MergeMeshes([body, arm], true);
        var localOrigin = localAxes(2);
        localOrigin.parent = pilot;
        /*************End Pilot****************************************/

//#####################BABYLON 101 DEMO CODE POSITION###################

        pilot.position = new BABYLON.Vector3(2, 3, 4);
        pilot.rotation.x = Math.PI/2;
        pilot.rotation.y = Math.PI/2;
        pilot.rotation.z = Math.PI/2;
        /////////////////////////////////////
        var showAxis = function (size) {
            var makeTextPlane = function (text, color, size) {
                var dynamicTexture = new BABYLON.DynamicTexture("DynamicTexture", 50, scene, true);
                dynamicTexture.hasAlpha = true;
                dynamicTexture.drawText(text, 5, 40, "bold 36px Arial", color, "transparent", true);
                var plane = new BABYLON.Mesh.CreatePlane("TextPlane", size, scene, true);
                plane.material = new BABYLON.StandardMaterial("TextPlaneMaterial", scene);
                plane.material.backFaceCulling = false;
                plane.material.specularColor = new BABYLON.Color3(0, 0, 0);
                plane.material.diffuseTexture = dynamicTexture;
                return plane;
            };

            var axisX = BABYLON.Mesh.CreateLines("axisX", [
                new BABYLON.Vector3.Zero(), new BABYLON.Vector3(size, 0, 0), new BABYLON.Vector3(size * 0.95, 0.05 * size, 0),
                new BABYLON.Vector3(size, 0, 0), new BABYLON.Vector3(size * 0.95, -0.05 * size, 0)
            ], scene);
            axisX.color = new BABYLON.Color3(1, 0, 0);
            var xChar = makeTextPlane("X", "red", size / 10);
            xChar.position = new BABYLON.Vector3(0.9 * size, -0.05 * size, 0);
            var axisY = BABYLON.Mesh.CreateLines("axisY", [
                new BABYLON.Vector3.Zero(), new BABYLON.Vector3(0, size, 0), new BABYLON.Vector3(-0.05 * size, size * 0.95, 0),
                new BABYLON.Vector3(0, size, 0), new BABYLON.Vector3(0.05 * size, size * 0.95, 0)
            ], scene);
            axisY.color = new BABYLON.Color3(0, 1, 0);
            var yChar = makeTextPlane("Y", "green", size / 10);
            yChar.position = new BABYLON.Vector3(0, 0.9 * size, -0.05 * size);
            var axisZ = BABYLON.Mesh.CreateLines("axisZ", [
                new BABYLON.Vector3.Zero(), new BABYLON.Vector3(0, 0, size), new BABYLON.Vector3(0, -0.05 * size, size * 0.95),
                new BABYLON.Vector3(0, 0, size), new BABYLON.Vector3(0, 0.05 * size, size * 0.95)
            ], scene);
            axisZ.color = new BABYLON.Color3(0, 0, 1);
            var zChar = makeTextPlane("Z", "blue", size / 10);
            zChar.position = new BABYLON.Vector3(0, 0.05 * size, 0.9 * size);
        };
        function localAxes(size) {
            var pilot_local_axisX = BABYLON.Mesh.CreateLines("pilot_local_axisX", [
                new BABYLON.Vector3.Zero(), new BABYLON.Vector3(size, 0, 0), new BABYLON.Vector3(size * 0.95, 0.05 * size, 0),
                new BABYLON.Vector3(size, 0, 0), new BABYLON.Vector3(size * 0.95, -0.05 * size, 0)
            ], scene);
            pilot_local_axisX.color = new BABYLON.Color3(1, 0, 0);

            pilot_local_axisY = BABYLON.Mesh.CreateLines("pilot_local_axisY", [
                new BABYLON.Vector3.Zero(), new BABYLON.Vector3(0, size, 0), new BABYLON.Vector3(-0.05 * size, size * 0.95, 0),
                new BABYLON.Vector3(0, size, 0), new BABYLON.Vector3(0.05 * size, size * 0.95, 0)
            ], scene);
            pilot_local_axisY.color = new BABYLON.Color3(0, 1, 0);

            var pilot_local_axisZ = BABYLON.Mesh.CreateLines("pilot_local_axisZ", [
                new BABYLON.Vector3.Zero(), new BABYLON.Vector3(0, 0, size), new BABYLON.Vector3(0, -0.05 * size, size * 0.95),
                new BABYLON.Vector3(0, 0, size), new BABYLON.Vector3(0, 0.05 * size, size * 0.95)
            ], scene);
            pilot_local_axisZ.color = new BABYLON.Color3(0, 0, 1);

            var local_origin = BABYLON.MeshBuilder.CreateBox("local_origin", { size: 1 }, scene);
            local_origin.isVisible = false;

            pilot_local_axisX.parent = local_origin;
            pilot_local_axisY.parent = local_origin;
            pilot_local_axisZ.parent = local_origin;

            return local_origin;

        }
        /***************************End World Axes***************************/

        showAxis(8);
        /////////////////////////////////////
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