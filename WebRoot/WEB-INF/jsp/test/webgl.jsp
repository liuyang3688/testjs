<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/18
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String cpath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*" />
    <title>3D机房建模</title>
    <script src="<%=cpath%>/js/three.js"></script>
    <script src="<%=cpath%>/js/THREEx.KeyboardState.js"></script>
    <script src="<%=cpath%>/js/THREEx.FullScreen.js"></script>
    <script src="<%=cpath%>/js/THREEx.WindowResize.js"></script>
    <script src="<%=cpath%>/js/OrbitControls.js"></script>
    <script src="<%=cpath%>/js/GLTFLoader.js"></script>
    <script src="<%=cpath%>/js/ColladaLoader.js"></script>
    <script src="<%=cpath%>/js/Detector.js"></script>
    <script src="<%=cpath%>/js/Stats.js"></script>
    <%--<style>--%>
        <%--body {--%>
            <%--margin:0;--%>
        <%--}--%>
        <%--div#ThreeJS {--%>
            <%--border: none;--%>
            <%--cursor: pointer;--%>
            <%--width: 100%;--%>
            <%--height: 650px;--%>
            <%--background-color: #39609B;--%>
        <%--}--%>
    <%--</style>--%>

<%--</head>--%>
<%--<body>--%>
<%--<div id="ThreeJS"></div>--%>
<%--<script>--%>
    <%--var container;--%>
    <%--var scene;--%>
    <%--var camera;--%>
    <%--var renderer;--%>
    <%--var controls;--%>
    <%--var stats;--%>
    <%--var targetList = [];--%>
    <%--var mouse={x:0,y:0};--%>
    <%--var keyboard = new THREEx.KeyboardState();--%>
    <%--var dummy= new THREE.Object3D();//仿制品--%>
    <%--init();--%>
    <%--animate();--%>

    <%--function init()--%>
    <%--{--%>
        <%--// SCENE--%>
        <%--scene = new THREE.Scene();--%>

        <%--// CAMERA--%>
        <%--var SCREEN_WIDTH = window.innerWidth;--%>
        <%--var SCREEN_HEIGHT = window.innerHeight;--%>
        <%--var VIEW_ANGLE = 45;--%>
        <%--var ASPECT = SCREEN_WIDTH / SCREEN_HEIGHT;--%>
        <%--var NEAR = 0.1;--%>
        <%--var FAR = 20000;--%>
        <%--camera = new THREE.PerspectiveCamera(VIEW_ANGLE, ASPECT, NEAR, FAR);--%>
        <%--camera.position.set(0,20,50);--%>
        <%--console.log("scene.position:"+scene.position);--%>
        <%--camera.lookAt(scene.position); //设置相机的朝向--%>

        <%--scene.add(camera);--%>
        <%--// RENDERER--%>
        <%--renderer = new THREE.WebGLRenderer({antialias:true});--%>
        <%--renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);--%>
        <%--container = document.getElementById('ThreeJS');--%>
        <%--container.appendChild(renderer.domElement);--%>
        <%--// EVENTS--%>
        <%--THREEx.WindowResize(renderer, camera);--%>
        <%--THREEx.FullScreen.bindKey({ charCode : 'm'.charCodeAt(0) });--%>
        <%--// CONTROLS--%>
        <%--controls = new THREE.OrbitControls(camera, renderer.domElement);--%>
        <%--// STATS--%>
        <%--stats = new Stats();--%>
        <%--stats.domElement.style.position = 'absolute';--%>
        <%--stats.domElement.style.bottom = '0px';--%>
        <%--stats.domElement.style.zIndex = 100;--%>
        <%--container.appendChild(stats.domElement);--%>
        <%--//LIGHT--%>
        <%--//var light = new THREE.PointLight(0xAAAAAA);--%>
        <%--var light = new THREE.AmbientLight( 0xBBBBBB );--%>
        <%--light.position.set(0,250,0);--%>
        <%--scene.add(light);--%>
        <%--//AXIS--%>
        <%--var axisHelper =new THREE.AxisHelper(600);--%>
        <%--scene.add(axisHelper);--%>


        <%--//FLOOR--%>
        <%--// var skyBoxGeometry = new THREE.CubeGeometry( 10000, 10000, 10000 );--%>
        <%--// var skyBoxMaterial = new THREE.MeshBasicMaterial( { color: 0x9999ff, side: THREE.BackSide } );--%>
        <%--// var skyBox = new THREE.Mesh( skyBoxGeometry, skyBoxMaterial );--%>
        <%--// scene.add(skyBox);--%>

        <%--// var faceColorMaterial = new THREE.MeshBasicMaterial( { color: 0xffffff, vertexColors: THREE.FaceColors } );--%>
        <%--// var sphereGeometry = new THREE.SphereGeometry( 80, 32, 16 );--%>
        <%--// for ( var i = 0; i < sphereGeometry.faces.length; i++ )--%>
        <%--// {--%>
        <%--// 	face = sphereGeometry.faces[ i ];--%>
        <%--// 	face.color.setRGB( 0, 0, 0.8 * Math.random() + 0.2 );--%>
        <%--// }--%>
        <%--// var sphere = new THREE.Mesh( sphereGeometry, faceColorMaterial );--%>
        <%--// sphere.position.set(0, 50, 0);--%>
        <%--// scene.add(sphere);--%>
        <%--var loader = new THREE.ColladaLoader();--%>
        <%--loader.load( 'statics/model/jifang-su8.dae', function ( gltf ) {--%>
                <%--//gltf.scene.position.x += 100;--%>
                <%--//gltf.scene.scale.set(40,40,40);--%>
                <%--gltf.scene.traverse( function ( child ) {--%>
                    <%--if ( child.name == 'Cube' )--%>
                    <%--{--%>
                        <%--targetList.push(child);--%>
                    <%--}--%>
                <%--});--%>
                <%--scene.add( gltf.scene );--%>
            <%--},--%>
            <%--// called when loading is in progresses--%>
            <%--function ( xhr ) {--%>
                <%--console.log( ( xhr.loaded / xhr.total * 100 ) + '% loaded' );--%>
            <%--},--%>
            <%--// called when loading has errors--%>
            <%--function ( error ) {--%>
                <%--console.log( 'An error happened' );--%>
            <%--}--%>
        <%--);--%>

        <%--//targetList.push(sphere);--%>
        <%--// when the mouse moves, call the given function--%>
        <%--document.addEventListener( 'mousedown', onDocumentMouseDown, false );--%>
    <%--}--%>
    <%--function onDocumentMouseDown( event )--%>
    <%--{--%>
        <%--console.log("Click.");--%>

        <%--// update the mouse variable--%>
        <%--mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;--%>
        <%--mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;--%>


        <%--var vector = new THREE.Vector3( mouse.x, mouse.y, 1 );--%>
        <%--//projector.unprojectVector( vector, camera );--%>
        <%--vector.unproject(camera);--%>
        <%--var ray = new THREE.Raycaster( camera.position, vector.sub( camera.position ).normalize() );--%>

        <%--var intersects = ray.intersectObjects( targetList );--%>

        <%--if ( intersects.length > 0 )--%>
        <%--{--%>
            <%--console.log("Hit @ " + toString( intersects[0].point ) );--%>
            <%--// change the color of the closest face.--%>
            <%--//intersects[ 0 ].face.color.setRGB( 0.8 * Math.random() + 0.2, 0, 0 );--%>
            <%--//intersects[ 0 ].object.geometry.colorsNeedUpdate = true;--%>
            <%--var point = intersects[0].point;--%>
            <%--var obj = intersects[0].object;--%>
            <%--obj.rotation.y+=0.5*Math.PI;--%>
        <%--}--%>

    <%--}--%>
    <%--function animate()--%>
    <%--{--%>
        <%--requestAnimationFrame( animate );--%>
        <%--render();--%>
        <%--update();--%>
    <%--}--%>
    <%--function update()--%>
    <%--{--%>
        <%--if ( keyboard.pressed("z") )--%>
        <%--{--%>
            <%--// do something--%>
        <%--}--%>

        <%--controls.update();--%>
        <%--stats.update();--%>
    <%--}--%>
    <%--function toString(v) { return "[ " + v.x + ", " + v.y + ", " + v.z + " ]"; }--%>
    <%--function render()--%>
    <%--{--%>
        <%--renderer.render( scene, camera );--%>
    <%--}--%>
    <style>
    body {
        margin:0;
    }
    div#canvas-frame {
        border: none;
        cursor: pointer;
        width: 100%;
        height: 800px;
        background-color: #39609B;
    }
    </style>
    <script>
        var renderer;
        function initThree() {
            width = document.getElementById('canvas-frame').clientWidth;
            height = document.getElementById('canvas-frame').clientHeight;
            renderer = new THREE.WebGLRenderer({alpha:true, antialias:true});
            renderer.setSize(width, height);
            document.getElementById("canvas-frame").appendChild(renderer.domElement);
            //renderer.setClearColor(0x39609B, 1.0);
            renderer.setClearAlpha(0);
            renderer.setClearColor(0xFFFFFF, 0.0);
        }
        var camera;
        function initCamera() {
            camera = new THREE.PerspectiveCamera(45, width/height, 1, 1000);
            camera.position.x = -100;
            camera.position.y = 100;
            camera.position.z = 100;
            camera.lookAt(200,0,0);
        }
        var scene;
        function initScene(){
            scene = new THREE.Scene();
        }
        var light;
        function initLight() {
            // light = new THREE.DirectionalLight(0x888888, 1.0, 0);
            // light.position.set(100,100,200);
            // scene.add(light);
            // var spotLight = new THREE.SpotLight( 0xffffff );
            // spotLight.position.set( 100, 1000, 100 );
            // spotLight.castShadow = true;
            // spotLight.shadow.mapSize.width = 1024;
            // spotLight.shadow.mapSize.height = 1024;
            // spotLight.shadow.camera.near = 500;
            // spotLight.shadow.camera.far = 4000;
            // spotLight.shadow.camera.fov = 30;
            // scene.add( spotLight );
            light = new THREE.AmbientLight( 0xAAAAAA ); // soft white light
            scene.add( light );
        }
        function drawAxis()
        {

        }
        var targetList = [];
        function initObject()
        {
            var loader = new THREE.ColladaLoader();
            loader.load( "<%=cpath%>/model/jifang3.dae", function ( collada ) {
                    rouletteScene = collada.scene;
                    rouletteScene.position.set(0,10,0);
                    rouletteScene.scale.set(0.15,0.15,0.15);
                    //rouletteScene.position.set(0,5,5);
                    scene.add(rouletteScene);
                },function( xhr) {
                    console.log((xhr.loaded/xhr.total * 100)+"% loaded");
                }
            );
            document.addEventListener( 'mousedown', onDocumentMouseDown, false );
        }

        function onDocumentMouseDown( event )
        {
            console.log("Click.");

            // update the mouse variable
            mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
            mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;

            // find intersections

            // create a Ray with origin at the mouse position
            //   and direction into the scene (camera direction)
            var vector = new THREE.Vector3( mouse.x, mouse.y, 1 );
            projector.unprojectVector( vector, camera );
            var ray = new THREE.Raycaster( camera.position, vector.sub( camera.position ).normalize() );

            // create an array containing all objects in the scene with which the ray intersects
            var intersects = ray.intersectObjects( targetList );

            // if there is one (or more) intersections
            if ( intersects.length > 0 )
            {
                console.log("Hit @ " + toString( intersects[0].point ) );
                // change the color of the closest face.
                intersects[ 0 ].face.color.setRGB( 0.8 * Math.random() + 0.2, 0, 0 );
                intersects[ 0 ].object.geometry.colorsNeedUpdate = true;
            }

        }

        var controls;
        function initControls() {

            controls = new THREE.OrbitControls( camera, renderer.domElement );

            // 如果使用animate方法时，将此函数删除
            //controls.addEventListener( 'change', render );
            // 使动画循环使用时阻尼或自转 意思是否有惯性
            controls.enableDamping = true;
            //动态阻尼系数 就是鼠标拖拽旋转灵敏度
            controls.dampingFactor = 0.8;
            //是否可以缩放
            controls.enableZoom = true;
            //是否自动旋转
            //controls.autoRotate = true;
            //设置相机距离原点的最远距离
            controls.minDistance = 50;
            //设置相机距离原点的最远距离
            controls.maxDistance = 1000;
            //是否开启右键拖拽
            controls.enablePan = true;
        }
        function render()
        {
            controls.update();
            renderer.render(scene, camera);
            requestAnimationFrame(render);
        }
        function threeStart()
        {
            initThree();
            initCamera();
            initScene();
            initLight();
            initObject();
            initControls();
            render();
        }
        function onWebGL()
        {

        }
    </script>
</head>
<body  onload="threeStart();">
<div id="canvas-frame"></div>
<div id="toolbar" style="display: block; position: absolute; left: 10px; top: 75px; width: 32px; height: 160px; background: rgba(255, 255, 255, 0.75); border-radius: 5px;">
    <a href="index"><img src="<%=cpath%>/image/ico5.png" title="返回首页" style="position: absolute; left: 4px; top: 16px; pointer-events: auto; cursor: pointer; width: 24px; height: 24px;"></a>
    <a href="webgl"><img src="<%=cpath%>/image/ico3.png" title="3D模型" style="position: absolute; left: 4px; top: 60px; pointer-events: auto; cursor: pointer; width: 24px; height: 24px;" onclick="window.location('webgl')"></a>
    <a href="rongjilv"><img src="<%=cpath%>/image/ico2.png" title="容积率" style="position: absolute; left: 4px; top: 104px; pointer-events: auto; cursor: pointer; width: 24px; height: 24px;" onclick="window.location('rongjilv')"></a>
</div>
</body>
</html>
