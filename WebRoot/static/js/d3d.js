var D3DOBJ = null;
var GCONFIG = {
	pic_path : '/glserver/static/pic/',
	bg_img: 'bg.jpg'
};
function d3d(fId, clearColor)
{
	this.fId = fId;
	this.clearColor = clearColor;
	this.width = window.innerWidth;
	this.height = window.innerHeight;
	this.objects = [];
	D3DOBJ = this;
};
d3d.prototype.start = function()
{
	this.initScene();
	this.initCamera();
	this.initThree();
	this.initAxisHelper();
	this.initLight();
	this.initBgImg();
	this.bindEventResize();
	this.initMouseCtrl();
	this.updateFromDB();
//	//this.addObjects();
	this.animation();
};
//Bind Resize Event
d3d.prototype.bindEventResize = function(){
	window.addEventListener('resize', this.onWindowResize, false);
};
d3d.prototype.onWindowResize = function () {
    var width = window['innerWidth'];
    var height = window['innerHeight'];
    D3DOBJ.camera.aspect = width / height;
    D3DOBJ.camera.updateProjectionMatrix();
    D3DOBJ.renderer.setSize(width, height);
};
//Init Scene
d3d.prototype.initScene = function(){
	this.scene = new THREE.Scene();
};
//Init Camera
d3d.prototype.initCamera = function() {
    this.camera = new THREE.PerspectiveCamera(45, this.width / this.height, 1, 100000);
    this.camera.name = 'mainCamera';
    this.camera.position.x = 0;
    this.camera.position.y = 800;
    this.camera.position.z = 1800;
    this.camera.lookAt(0,0,0);
};
// Init WebGL Renderer
d3d.prototype.initThree = function()
{
	this.renderer = new THREE.WebGLRenderer({alpha: true,antialias:true});
	this.renderer.setSize(this.width, this.height);
	$(this.fId).append( this.renderer.domElement );
    this.renderer.setClearColor(0xAAAAAA, 1.0);
    this.renderer.shadowMap.enabled = true;
    this.renderer.showMapSoft = true;
    this.renderer.domElement.addEventListener('mousedown', this.onDocumentMouseDown, false);
    this.renderer.domElement.addEventListener('mousemove', this.onDocumentMouseMove ,false);
};
d3d.prototype.onDocumentMouseDown = function(){
	//console.log("onDocumentMouseDown");
};
d3d.prototype.onDocumentMouseMove = function(){
	//console.log("onDocumentMouseMove");
};

// Init Axis Helper
d3d.prototype.initAxisHelper = function(){
	this.axisHelper = new THREE.AxesHelper( 2000 );
	this.scene.add(this.axisHelper);
	var geometry = new THREE.BoxGeometry( 1, 1, 1 );
	var material = new THREE.MeshBasicMaterial( {color: 0x00ff00} );
	var cube = new THREE.Mesh( geometry, material );
	this.scene.add( cube );
};
d3d.prototype.initLight = function() {
    var ambientLight = new THREE.AmbientLight(0xcccccc);
    ambientLight['position']['set'](0, 0, 0);
    this.scene.add(ambientLight);
    var pointLight1 = new THREE.PointLight(0xcccccc);
    pointLight1['shadow']['camera']['near'] = 1;
    pointLight1['shadow']['camera']['far'] = 5000;
    pointLight1['position']['set'](0, 350, 0);
    pointLight1['castShadow'] = false;
    this.scene.add(pointLight1);
};
d3d.prototype.initBgImg = function() {
    var skyBoxGeometry = new THREE.BoxGeometry( 5000, 5000, 5000 );
    var texture = new THREE.TextureLoader().load(GCONFIG['pic_path'] + GCONFIG['bg_img']);
    var skyBoxMaterial = new THREE.MeshBasicMaterial( { map:texture, side: THREE.DoubleSide } );
    var skyBox = new THREE.Mesh( skyBoxGeometry, skyBoxMaterial );
    this.scene.add(skyBox);
};
d3d.prototype.initMouseCtrl = function () {
    this.controls = new THREE.OrbitControls(this.camera);
    this.controls.maxPolarAngle = Math.PI * 0.42;
    this.controls.minDistance=50;
    this.controls.maxDistance = 5000;
    this.controls.addEventListener('change', this.updateControls);
};
d3d.prototype.updateControls = function () {
    //log('D3D.prototype.updateControls begin ...');
    //log('D3D.prototype.updateControls end ...');
};
d3d.prototype.addObjects = function() {
	for(var i=0; i<d3dobj.objects.length; ++i){
		this.scene.add(d3dobj.objects[i]);
	}
};
d3d.prototype.animation = function() {
	if (TWEEN != null && typeof(TWEEN) !== 'undefined') {
        TWEEN.update();
    }
	D3DOBJ.onUpdateScene();
	requestAnimationFrame(D3DOBJ['animation']);
	D3DOBJ.renderer.render(D3DOBJ.scene, D3DOBJ.camera);
};
var isFirst = true;
d3d.prototype.onUpdateScene = function() {
	// 更新场景
	var date = new Date();
	if( date.getSeconds()%10 == 0){
		if(isFirst){
			isFirst = false;
			D3DOBJ.updateFromDB();
		}
	}
	else{
		isFirst = true;
	}
};
d3d.prototype.updateFromDB = function() {
	// 判断修改标志
	// 从数据库读取Geometry 和Material
	// 创建出对象加载到scene
	$.ajax({
		url:'/glserver/mesh/get_all_mesh',
		dataType:'json',
		data: null,
		success:function(data){
			D3DOBJ.parseMeshData(data);
			$.ajax({
				url:'/glserver/clone/get_all_clone',
				dataType:'json',
				data: null,
				success:function(data){
					D3DOBJ.parseCloneData(data);
					$.ajax({
						url:'/glserver/label/get_all_label',
						dataType:'json',
						data: null,
						success:function(data){
							D3DOBJ.parseLabelData(data);
						},
						error: function(data) {
							console.log("update label from db error");
						}
					});
				},
				error: function(data) {
					console.log("update clone from db error");
				}
			});
		},
		error: function(data) {
			console.log("update mesh from db  error");
		}
	});
};
d3d.prototype.addObject = function(object){
	if ( arguments.length > 1 ) {
		var parentName = arguments[1];
		var parentMesh = D3DOBJ.scene.getObjectByName(parentName);
		if(exist(parentName) && parentName !== '' && parentMesh != undefined){
			parentMesh.add(object);
		}
		else{
			D3DOBJ.scene.add(object);
		}
	}
	else{
		D3DOBJ.scene.add(object);
	}
};
d3d.prototype.parseLabelData = function(datas){
	for(var i=0; i<datas.length; ++i){
		var data = datas[i];
		
		// 首先在scene中查看
		
		var label = D3DOBJ.scene.getObjectByName(data['name']);
		if( label != undefined){
			label.parent.remove(label);
		}
		label = D3DOBJ.createLabel(data);
		//D3DOBJ.addObject(label, data['parent']);
		
		//修改isDirty标志
		$.ajax({
			type:'post',
			url:'/glserver/label/update_isdirty',
			dataType:'json',
			data: { uuid: data['uuid'] },
			success:function(data){
				//
			},
			error: function(data) {
				console.log("update label isdirty error uuid:"+data['uuid']);
			}
		});
	}
};
d3d.prototype.createLabel = function(data){
	var label = null;
	if(exist(data['pic']) && data['pic'] !== ''){
		var image = new Image();
		image.onload = function(){
			//data['width'] = image.width;
			//data['height'] = image.height;
			data['imgobj'] = this;
			label = D3DOBJ.drawLabel(data);
			D3DOBJ.addObject(label, data['parent']);
		};
		image.src = GCONFIG['pic_path'] + data['pic'];
	} else {
		label = D3DOBJ.drawLabel(data);
		D3DOBJ.addObject(label, data['parent']);
	}
};
d3d.prototype.drawLabel = function(data){
	var drawingCanvas = document.createElement('canvas');
	var drawingContext = drawingCanvas.getContext('2d');
	drawingCanvas.width = data['width'] || 50;
    drawingCanvas.height = data['height'] || 50;
    if(exist(data['pic']) && data['pic'] !== ''){
    	drawingContext.drawImage(data['imgobj'], 0, 0, drawingCanvas.width, drawingCanvas.height);
    	drawingContext.fillStyle = 'rgba(33,136,104,1)';
    } else {
    	drawingContext.fillStyle = "rgba(43,76,143,1)";
        drawingContext.fillRect(0, 0, drawingCanvas.width, drawingCanvas.height);
        drawingContext.fillStyle = 'rgba(255,255,255,1)';
    }
    drawingContext.textAlign = 'center';
    drawingContext.textBaseline = 'middle';
    if(exist(data.texts)){
    	for (var i = 0; i < data.texts.length; i++) {
            drawingContext.font = data.texts[i].font;
            drawingContext.fillText(data.texts[i].text, data.texts[i].offX, data.texts[i].offY);
        }
    }
    
    var material = new THREE.MeshBasicMaterial();
    var texture = new THREE.Texture(drawingCanvas);
    texture.needsUpdate = true;
    material.map = texture;
    //material.opacity=.5;
    //material.transparent=true;

    var mesh = new THREE.Mesh(new THREE.BoxGeometry(drawingCanvas.width, drawingCanvas.height, 1), material);
    mesh.name = data['name'];
    mesh.uuid = data['uuid'];
    mesh.position.y = data["y"];
    mesh.position.x = data["x"];
    mesh.position.z = data["z"];
    var scl_x = data['scl_x'] || 1;
    var scl_y = data['scl_y'] || 1;
    var scl_z = data['scl_z'] || 1;
    mesh.scale.set( scl_x, scl_y, scl_z);
    if(exist(data['rot_x']) && data['rot_x'] !== 0 ){
    	mesh['rotateX'](data['rot_x']*Math['PI']/180);
    }
    if(exist(data['rot_y']) && data['rot_y'] !== 0 ){
    	mesh['rotateY'](data['rot_y']*Math['PI']/180);
    }
    if(exist(data['rot_z']) && data['rot_z'] !== 0 ){
        mesh['rotateZ'](data['rot_z']*Math['PI']/180);
    }
    return mesh;
};
d3d.prototype.parseCloneData = function(data){
	for(var i=0; i<data.length; ++i){
		var cloneData = data[i];
		var mesh = D3DOBJ.scene.getObjectByName(cloneData['name']);
		if( mesh != undefined)
			D3DOBJ.scene.remove(mesh);
		
		mesh = D3DOBJ.cloneObj(cloneData);
		D3DOBJ.addObject(mesh, cloneData['parent']);
		
		//修改isDirty标志
		$.ajax({
			type:'post',
			url:'/glserver/clone/update_isdirty',
			dataType:'json',
			data: { uuid: cloneData['uuid'] },
			success:function(data){
				//
			},
			error: function(data) {
				console.log("update clone isdirty error uuid:"+cloneData['uuid']);
			}
		});
	}
};
d3d.prototype.cloneObj = function(cloneData){
	var sourceName = cloneData['copyfrom'];
	var sourceObj = null;
	if(exist(sourceName) && sourceName !== ''){
		sourceObj = D3DOBJ.scene.getObjectByName(sourceName);
	} else {
		console.log('clone obj, source name is null');
		return;
	}
	if( sourceObj == undefined ) {
		console.log('scene has not find source name: ' + sourceName);
		return;
	}
	var cloneObj = sourceObj.clone();
	cloneObj['name'] = cloneData['name'];
    cloneObj['uuid'] = cloneData['uuid'];
    if (cloneObj['children'] != null && cloneObj['children']['length'] > 1) {
        $['each'](cloneObj['children'], function (i, child) {
            child['name'] = child['name'] + '-' + cloneData['name'];
        });
    }
    cloneObj['position']['x'] = cloneData['x'] || 0;
    cloneObj['position']['y'] = cloneData['y'] || 0;
    cloneObj['position']['z'] = cloneData['z'] || 0;
    cloneObj['scale']['x'] = cloneData['scl_x'] || 1;
    cloneObj['scale']['y'] = cloneData['scl_y'] || 1;
    cloneObj['scale']['z'] = cloneData['scl_z'] || 1;
    
    if( exist(cloneData['rot_x']) && cloneData['rot_x'] !== 0 ){
    	cloneObj['rotateX'](cloneData['rot_x']*Math['PI']/180);
    }
    if( exist(cloneData['rot_y']) && cloneData['rot_y'] !== 0 ){
    	cloneObj['rotateY'](cloneData['rot_y']*Math['PI']/180);
    }
    if( exist(cloneData['rot_z']) && cloneData['rot_z'] !== 0 ){
    	cloneObj['rotateZ'](cloneData['rot_z']*Math['PI']/180);
    }
    return cloneObj;
};
d3d.prototype.parseMeshData = function(data){
	for(var i=0; i<data.length; ++i){
		var meshData = data[i];
		var mesh = D3DOBJ.scene.getObjectByName(meshData['name']);
		if( mesh != undefined)
			D3DOBJ.scene.remove(mesh);
		switch(meshData['type'])
		{
		case 'floor':
			mesh = D3DOBJ.createFloor(meshData);
			break;
		case 'wall':
			mesh = D3DOBJ.createWall(meshData);
			break;
		case 'hole': // 非场景的直接children
			mesh = D3DOBJ.createHole(meshData);
			break;
		case 'cube':
			mesh = D3DOBJ.createBox(meshData);
			break;
		case 'glass':
			mesh = D3DOBJ.createGlass(meshData);
			break;
		case 'plane':
			mesh = D3DOBJ.createPlane(meshData);
			break;
		}
		//debug(meshData['name'], 'cabinet11-doorFront');
		if(exist(meshData['parent'])===false || meshData['parent']==='' ){
			mesh['scale']['x'] = meshData['scl_x'] || 1;
		    mesh['scale']['y'] = meshData['scl_y'] || 1;
		    mesh['scale']['z'] = meshData['scl_z'] || 1;
		    if( exist(meshData['rot_x']) && meshData['rot_x'] !== 0 ){
		    	mesh['rotateX'](meshData['rot_x']*Math['PI']/180);
		    }
		    if( exist(meshData['rot_y']) && meshData['rot_y'] !== 0 ){
		    	mesh['rotateY'](meshData['rot_y']*Math['PI']/180);
		    }
		    if( exist(meshData['rot_z']) && meshData['rot_z'] !== 0 ){
		    	mesh['rotateZ'](meshData['rot_z']*Math['PI']/180);
		    }
		}
		if(meshData['type'] !== 'hole'){
			D3DOBJ.addObject(mesh, meshData['parent']);
		}
		
		//修改isDirty标志
		$.ajax({
			type:'post',
			url:'/glserver/mesh/update_isdirty',
			dataType:'json',
			data: { uuid: meshData['uuid'] },
			success:function(data){
				//
			},
			error: function(data) {
				console.log("update mesh isdirty error uuid:"+meshData['uuid']);
			}
		});
	}
};

d3d.prototype.createGlass = function(meshData){
    var side_config = THREE['DoubleSide'];
    return D3DOBJ.createPlane(meshData, side_config);
};
d3d.prototype.createPlane = function(meshData, side_config){
	var textureLoader = new THREE.TextureLoader();
    var texture = textureLoader['load'](GCONFIG['pic_path']+meshData['imgurl'], function () {}, undefined, function () {});
    if(exist(side_config) == false || side_config === ''){
    	side_config = THREE['FrontSide'];
    }
    var config = {
        map: texture,
        overdraw: true,
        side: side_config,
        transparent: meshData['transparent'],
        opacity: meshData['opacity']
    };
    if (meshData['blending']) {
        config['blending'] = THREE['AdditiveBlending'];
    }
    var mesh = new THREE.Mesh(new THREE.PlaneGeometry(meshData['width'], meshData['height'], 1, 1), new THREE.MeshBasicMaterial(config));
    mesh['position']['x'] = meshData['x'];
    mesh['position']['y'] = meshData['y'];
    mesh['position']['z'] = meshData['z'];
    return mesh;
};
d3d.prototype.createHole = function(meshData){
	var hole = D3DOBJ.createBox(meshData);
	var parentName = meshData['parent'];
	var opcode = meshData['opcode'];
	var parentMesh = D3DOBJ.scene.getObjectByName(parentName);
	D3DOBJ.scene.remove(parentMesh);
	if(exist(parentName) && parentName !== '' && parentMesh != undefined){
		parentMesh = D3DOBJ.mergeModel(opcode, parentMesh, hole);
		D3DOBJ.scene.add(parentMesh);
	}
};
d3d.prototype.mergeModel = function (op, fromObj, toObj) {
    var _dg3dObj = this;
    var bspFrom = new ThreeBSP(fromObj);
    var bspTo = new ThreeBSP(toObj);
    var subObj = null;
    if (op === '-') {
        subObj = bspFrom['subtract'](bspTo);
    } else if (op === '+') {
        toObj['updateMatrix']();
        fromObj['geometry']['merge'](toObj['geometry'], toObj['matrix']);
        return fromObj;
    } else if (op === '&') {
        subObj = bspFrom['intersect'](bspTo);
    } else {
        _dg3dObj['addObject'](toObj);
        return fromObj;
    }
    var materials = [];
    for (var i = 0; i < 1; i++) {
        materials['push'](new THREE.MeshLambertMaterial({
            vertexColors: THREE['FaceColors']
        }));
    }
    var subMesh = subObj['toMesh'](fromObj.material);
    subMesh['material']['shading'] = THREE['FlatShading'];
    subMesh['geometry']['computeFaceNormals']();
    subMesh['geometry']['computeVertexNormals']();
	subMesh['uuid'] = fromObj['uuid'];
	subMesh['name'] = fromObj['name'];
    subMesh['material']['needsUpdate'] = true;
    subMesh['geometry']['buffersNeedUpdate'] = true;
    subMesh['geometry']['uvsNeedUpdate'] = true;
    var fromCopyObj = null;
    for (var i = 0; i < subMesh['geometry']['faces']['length']; i++) {
        var isCopyFaces = false;
        for (var faceI = 0; faceI < fromObj['geometry']['faces']['length']; faceI++) {
            if (subMesh['geometry']['faces'][i]['vertexNormals'][0]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['x'] && subMesh['geometry']['faces'][i]['vertexNormals'][0]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['y'] && subMesh['geometry']['faces'][i]['vertexNormals'][0]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['z'] && subMesh['geometry']['faces'][i]['vertexNormals'][1]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['x'] && subMesh['geometry']['faces'][i]['vertexNormals'][1]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['y'] && subMesh['geometry']['faces'][i]['vertexNormals'][1]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['z'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['x'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['y'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['z']) {
                subMesh['geometry']['faces'][i]['color']['setHex'](fromObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 + fromObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 + fromObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff);
                fromCopyObj = fromObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 + fromObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 + fromObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff;
                isCopyFaces = true;
            }
        }
        if (isCopyFaces === false) {
            for (var faceI = 0; faceI < toObj['geometry']['faces']['length']; faceI++) {
                if (subMesh['geometry']['faces'][i]['vertexNormals'][0]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['x'] && subMesh['geometry']['faces'][i]['vertexNormals'][0]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['y'] && subMesh['geometry']['faces'][i]['vertexNormals'][0]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['z'] && subMesh['geometry']['faces'][i]['vertexNormals'][1]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['x'] && subMesh['geometry']['faces'][i]['vertexNormals'][1]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['y'] && subMesh['geometry']['faces'][i]['vertexNormals'][1]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['z'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['x'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['y'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['z'] && subMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['z']) {
                    subMesh['geometry']['faces'][i]['color']['setHex'](toObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 + toObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 + toObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff);
                    fromCopyObj = toObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 + toObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 + toObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff;
                    isCopyFaces = true;
                }
            }
        }
        if (isCopyFaces === false) {
            subMesh['geometry']['faces'][i]['color']['setHex'](fromCopyObj);
        }
    }
    subMesh['castShadow'] = true;
    subMesh['receiveShadow'] = true;
    return subMesh;
};
d3d.prototype.createWall = function(meshData){
	console.log('create wall');
	return D3DOBJ.createBox(meshData);	
};
d3d.prototype.createFloor = function(mesh){
	console.log('create floor');
	return D3DOBJ.createBox(mesh);	
};
d3d.prototype.createBox = function(mesh){
	var length = mesh['length'] || 1000;
	var width = mesh['width'] || length;
	var height = mesh['height'] || 10;
	var x = mesh['x'] || 0;
	var y = mesh['y'] || 0;
	var z = mesh['z'] || 0;
	var skinColor = mesh['skinColor'] || 0x00b0e2;
	var boxGeo = new THREE.BoxGeometry( length, height, width, 0, 0, 1);
	// 初始化全局
	for (var i = 0; i < boxGeo['faces']['length']; i += 2) {
		boxGeo['faces'][i]['color']['setHex'](skinColor);
		boxGeo['faces'][i + 1]['color']['setHex'](skinColor);
    }
	var vertexColors = { vertexColors: THREE['FaceColors']};
    var textFore = vertexColors;
    var textBack = vertexColors;
    var textUp = vertexColors;
    var textDown = vertexColors;
    var textRight = vertexColors;
    var textLeft = vertexColors;
        
    if(exist(mesh["skin_fore"])){
    	mesh["skin_fore"]['isRepeat'] = mesh['isRepeat'];
    	textFore = D3DOBJ.createTexture(length, width, mesh["skin_fore"], boxGeo, 0);
    }
    if(exist(mesh["skin_back"])){
    	mesh["skin_back"]['isRepeat'] = mesh['isRepeat'];
    	textBack = D3DOBJ.createTexture(length, width, mesh["skin_back"], boxGeo, 2);
    }
    if(exist(mesh["skin_up"])) {
    	mesh["skin_up"]['isRepeat'] = mesh['isRepeat'];
    	textUp = D3DOBJ.createTexture(length, width, mesh["skin_up"], boxGeo, 4);
    }
    if(exist(mesh["skin_down"])){
    	mesh["skin_down"]['isRepeat'] = mesh['isRepeat'];
    	textDown = D3DOBJ.createTexture(length, width, mesh["skin_down"], boxGeo, 6);
    }
    if(exist(mesh["skin_right"])){
    	mesh["skin_right"]['isRepeat'] = mesh['isRepeat'];
    	textRight = D3DOBJ.createTexture(length, width, mesh["skin_right"], boxGeo, 8);
    }
    if(exist(mesh["skin_left"])){
    	mesh["skin_left"]['isRepeat'] = mesh['isRepeat'];
    	textLeft = D3DOBJ.createTexture(length, width, mesh["skin_left"], boxGeo, 10);
    }
    
    var materials = [];
    materials['push'](new THREE.MeshLambertMaterial(textFore));
    materials['push'](new THREE.MeshLambertMaterial(textBack));
    materials['push'](new THREE.MeshLambertMaterial(textUp));
    materials['push'](new THREE.MeshLambertMaterial(textDown));
    materials['push'](new THREE.MeshLambertMaterial(textRight));
    materials['push'](new THREE.MeshLambertMaterial(textLeft));
    var box = new THREE.Mesh(boxGeo, materials);
    box['castShadow'] = true;
    box['receiveShadow'] = true;
    box['uuid'] = mesh['uuid'];
    box['name'] = mesh['name'];
    box['position']['set'](x, y, z);

    return box;  
};
d3d.prototype.createTexture = function (length, width, skin, boxGeo, faceIndex) {
	if (exist(skin['imgurl']) && skin['imgurl'] !== '') {
        retObj = {
            map: D3DOBJ['createSkin'](length, width, skin),
            transparent: false
        };
    } else {
        if (exist(skin['skinColor']) && skin['skinColor'] !== '') {
        	boxGeo['faces'][faceIndex]['color']['setHex'](skin['skinColor']);
        	boxGeo['faces'][faceIndex + 1]['color']['setHex'](skin['skinColor']);
        }
        retObj = {
            vertexColors: THREE['FaceColors']
        };
    }
    return retObj;
};
d3d.prototype.createSkin = function(length, width, skin){
    var img_width = 64;
    var img_height= 64;
    if (exist(skin['width'])) {
        img_width = skin['width'];
    }
    if (exist(skin['height'])) {
    	img_height = skin['height'];
    }
    var texture = new THREE.TextureLoader()['load'](GCONFIG['pic_path']+skin['imgurl']);
    if(exist(skin['isRepeat']) && skin['isRepeat']>0){
    	texture['wrapS'] = THREE['RepeatWrapping'];
	    texture['wrapT'] = THREE['RepeatWrapping'];
	    texture['repeat']['set'](length / img_width, width / img_height);
    }
   
    return texture;
};