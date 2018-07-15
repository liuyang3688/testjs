let D3DOBJ = null;
let GCONFIG = {
	pic_path : '/glserver/static/pic/',
	bg_img: 'bg.jpg',
    cab_inner_height:210,
    roam:false,
    conn:false,
    temp:false,
    air:false,
    smoke:false
};
function D3DLib(fId, clearColor)
{
	this.fId = fId;
	this.clearColor = clearColor;
	this.width = window.innerWidth;
	this.height = window.innerHeight;
	this.objects = [];
	this.cabList = [];
	this.tplList = new Map();
	this.events = null;
	this.buttons = null;
	this.raycaster = new THREE.Raycaster();
	this['mousePos'] = new THREE.Vector2();
	D3DOBJ = this;
};
D3DLib.prototype.start = function()
{
	this.initScene();
	this.initCamera();
	this.initMouseCtrl();
	this.initThree();
	this.initAxisHelper();
	this.initStats();
	this.initLight();
	this.initBgImg();
	this.bindEventResize();
	this.updateFromDB();
	this.loadEvtFromDB();
	this.loadBtnFromDB();
//	//this.addObjects();
	this.animation();
};
D3DLib.prototype.reset = function(){
    D3DOBJ.cabList = [];
    D3DOBJ.scene.children = [];
    D3DOBJ.initLight();
    D3DOBJ.initBgImg();
//    var promise_update_mesh_isdirty = new Promise(function(resolve, reject) {
//        $.ajax({
//            type:'post',
//            url:'/glserver/mesh/update_isdirty',
//            dataType:'json',
//            data: null,
//            success:function(data){
//                resolve();
//            },
//            error: function(data) {
//                console.log("update mesh isdirty all error");
//            }
//        });
//    });
//    var promise_update_clone_isdirty = new Promise(function(resolve, reject) {
//        $.ajax({
//            type:'post',
//            url:'/glserver/clone/update_isdirty',
//            dataType:'json',
//            data: null,
//            success:function(data){
//                resolve();
//            },
//            error: function(data) {
//                console.log("update clone isdirty all error");
//            }
//        });
//    });
//    var promise_update_label_isdirty = new Promise(function(resolve, reject) {
//        $.ajax({
//            type:'post',
//            url:'/glserver/label/update_isdirty',
//            dataType:'json',
//            data: null,
//            success:function(data){
//                resolve();
//            },
//            error: function(data) {
//                console.log("update label isdirty all error");
//            }
//        });
//    });
//    promise_update_mesh_isdirty.then(function(){
//        return promise_update_clone_isdirty;
//    }).then(function(){
//        return promise_update_label_isdirty;
//    }).then(function(){
//        D3DOBJ.updateFromDB();
//    });
};
//Load Event From DB
D3DLib.prototype.loadEvtFromDB = function () {
	$.ajax({
		url:'/glserver/event/get_all_event',
		dataType:'json',
		data: null,
		success:function(data){
			
			D3DOBJ.eventList = data;
		},
		error: function(data) {
			console.log("load event from db error");
		}
	});
};
//Load Button From DB
D3DLib.prototype.loadBtnFromDB = function(){
    $.ajax({
        url:'/glserver/button/get_all_btn',
        dataType:'json',
        data: null,
        success:function(data){
            D3DOBJ.parseBtnData(data);
        },
        error: function(data) {
            console.log("load button from db error");
        }
    });
};
D3DLib.prototype.parseBtnData = function(data) {
    if (data != null && data.length > 0) {
        let container = $(this.fId);
        let div = document['createElement']('div');
        div['style']['position'] = 'absolute';
        div['style']['top'] = '10px';
        div['style']['right'] = '20px';
        div['style']['width'] = '70%';
        div['style']['textAlign'] = 'right';
        div['style']['zIndex'] = 100;
        div['id'] = 'menutoolbar';
        container['append'](div);
        $['each'](data, function (i, obj) {
            $('#menutoolbar')['append'](' <img style="width: 60px;" src="' + GCONFIG['pic_path'] + obj['img'] + '" title="' + obj['title'] + '" id="' + obj['name'] + '" />');
            let callback = eval("(" + obj['callback'] + ")");
            if('function' === typeof(callback)){
                $('#' + obj['name']).on('click', callback);
            }
        })
    }
};
//Bind Resize Event
D3DLib.prototype.bindEventResize = function(){
	window.addEventListener('resize', this.onWindowResize, false);
};
D3DLib.prototype.onWindowResize = function () {
    D3DOBJ.width = window['innerWidth'];
    D3DOBJ.height = window['innerHeight'];
    
    D3DOBJ.camera.aspect = D3DOBJ.width / D3DOBJ.height;
    D3DOBJ.camera.updateProjectionMatrix();
    D3DOBJ.renderer.setSize(D3DOBJ.width, D3DOBJ.height);
    
};
//Init Scene
D3DLib.prototype.initScene = function(){
	this.scene = new THREE.Scene();
};
//Init Camera
D3DLib.prototype.initCamera = function() {
    this.camera = new THREE.PerspectiveCamera(45, this.width / this.height, 1, 100000);
    this.camera.name = 'mainCamera';
    this.camera.position.x = 0;
    this.camera.position.y = 800;
    this.camera.position.z = 1000;
    this.camera.lookAt(0,0,0);
//    var helper = new THREE.CameraHelper( this.camera );
//    D3DOBJ.scene.add( helper );
};
// Init WebGL Renderer
D3DLib.prototype.initThree = function()
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
let dbclick = 0;
let evtname = '';
D3DLib.prototype.onDocumentMouseDown = function(event){
	if(D3DOBJ.controls.autoRotate === true){
		D3DOBJ.controls.autoRotate = false;
	}
	
	D3DOBJ['mousePos']['x'] = (event['clientX'] / D3DOBJ['width']) * 2 - 1;
	D3DOBJ['mousePos']['y'] = -(event['clientY'] / D3DOBJ['height']) * 2 + 1;
	D3DOBJ['raycaster']['setFromCamera'](D3DOBJ['mousePos'], D3DOBJ['camera']);
	dbclick++;
	
	setTimeout(function () {
		if( event['target']['nodeName'] === 'CANVAS'){
			if(event.button === 2){
				evtname = 'rclick';
			} else {
				if (dbclick >= 2) {
					evtname = 'dbclick'
				} else {
					evtname = 'click'
				}
			}
		}
		dbclick = 0;
		D3DOBJ['onEventDeal'](evtname, event)
	}, 200);
	event['preventDefault']();
};
D3DLib.prototype.onEventDeal = function(evtname, event){
	//console.log('pos1 1event:'+evtname);
	let eventObj = event;
	if (evtname === '') {
	    return;
	}
	//D3DOBJ['raycaster']['setFromCamera'](_dg3dObj['mousePos'], _dg3dObj['camera']);
    let intersectObjects = D3DOBJ['raycaster']['intersectObjects'](D3DOBJ.scene.children, true);
	if (intersectObjects['length'] > 0) {
		//console.log('pos2 1event:'+evtname);
		D3DOBJ['controls']['enabled'] = false;
		D3DOBJ['SELECTED'] = intersectObjects[0]['object'];
		
		if (D3DOBJ['eventList'] != null && D3DOBJ['eventList'][evtname] != null && 
				D3DOBJ['eventList'][evtname]['length'] > 0) {
			//console.log('pos4 1event:'+evtname);
			$['each'](D3DOBJ['eventList'][evtname], function (i, obj) {
				//console.log('pos5 1event:'+evtname+",obj-name: "+D3DOBJ['SELECTED']['name']);
                let match_func = eval("(" + obj['match_func'] + ")");
				if ('function' === typeof(match_func)) {
                	if (match_func(D3DOBJ['SELECTED']['name'])) {
                		//console.log('pos6 1event:'+evtname);
                        let deal_func = eval("(" + obj['deal_func'] + ")");
                		if('function' === typeof(deal_func))
                			deal_func(D3DOBJ['SELECTED']);
                    }
                }
            })
		}
		
	    D3DOBJ['controls']['enabled'] = true;
	}
	
};
D3DLib.prototype.onDocumentMouseMove = function(event){
	event['preventDefault']();
};
D3DLib.prototype.initMouseCtrl = function () {
    this.controls = new THREE.OrbitControls(this.camera);
    this.controls.maxPolarAngle = Math.PI * 0.48;
    this.controls.minDistance=200;
    this.controls.maxDistance = 3000;
    this.controls.zoomSpeed = 2;
    this.controls.addEventListener('change', this.updateControls);
};
//////////////////////////////////////////////////
D3DLib.prototype.aeroview = function(){
	if (!D3DOBJ.controls.autoRotate){
        D3DOBJ.camera.position.set(1000,800,1000);
        D3DOBJ.camera.lookAt(0,0,0);
    }
    D3DOBJ.controls.autoRotate = !D3DOBJ.controls.autoRotate;
};
D3DLib.prototype.roam = function(){
	let pathName = 'dynamicPath001';
    if (!GCONFIG['roam']) {
        let pathConfig = {
            name: pathName,
            type: 'beeline',
            visible: true,
            radiu: 1.0,
            imgurl: GCONFIG['pic_path'] + 'roughness_map.jpg',
            scene: true
        };
        let pointArr = [{
            x: -550,
            y: 120,
            z: -480
        },
        {
            x: -550,
            y: 120,
            z: 310
        },
        {
            x: 220,
            y: 120,
            z: 310
        },
        {
            x: 220,
            y: 120,
            z: -480
        }, {
            x: -550,
            y: 120,
            z: -480
        }];
        D3DOBJ['dynamicPath'](pathConfig, pointArr)
    } else {
        D3DOBJ['stopDynamicPath'](pathName)
    }
    GCONFIG['roam'] = !GCONFIG['roam']
};
D3DLib.prototype.connection = function(){
    let pathName = 'line0001';
    let spriteConn1Name = 'spriteConn1';
    let spriteConn2Name = 'spriteConn2';
    if (!GCONFIG['conn']) {
        let pathConfig = {
            name: pathName,
            type: 'beeline',
            visible: true,
            //imgurl: GCONFIG['pic_path'] + 'UV_Grid_Sm.jpg',
            color: 'green',
            radiu: 0.8,
            scene: true
        };
        let pathPoints = [
            {x: -150,y: 100,z: 180},
            {x: -100,y: 100,z: 180},
            {x: -100,y: 240,z: 180},
            {x: -130,y: 240,z: 180},
            {x: -130,y: 240,z: 250},
            {x: 70,y: 240,z: 250},
            {x: 70,y: 240,z: 20},
            {x: 70,y: 210,z: 20},
            {x: 100,y: 210,z: 20},
            {x: 100,y: 160,z: 20}];
        D3DOBJ.addTunnel(pathConfig, pathPoints);

        let spriteConn1Config = {
            name: spriteConn1Name,
            position: {x: -70,y: 260,z: 200},
            size: {x: 256,y: 128,z: 60},
            color: {r: 244,g: 120,b: 32,a: 0.4},
            imgurl: 'res/textmark.jpg',
            rows: [{
                name: 'item1',
                fontface: 'Arial',
                fontsize: 20,
                borderThickness: 32,
                textColor: {
                    r: 255,
                    g: 255,
                    b: 255,
                    a: 1.0
                },
                text: '\u5F53\u524D\u7F51\u7EDC\u6D41\u91CF',
                size: {
                    x: 20,
                    y: 20
                },
                position: {
                    x: 10,
                    y: 30,
                    z: 0
                }
            },
                {
                    name: 'item2',
                    fontface: 'Arial',
                    fontsize: 60,
                    borderThickness: 14,
                    textColor: {
                        r: 255,
                        g: 255,
                        b: 255,
                        a: 1.0
                    },
                    text: '170.8',
                    size: {
                        x: 20,
                        y: 20
                    },
                    position: {
                        x: 10,
                        y: 80,
                        z: 0
                    }
                },
                {
                    name: 'item3',
                    fontface: 'Arial',
                    fontsize: 24,
                    borderThickness: 14,
                    textColor: {
                        r: 255,
                        g: 255,
                        b: 255,
                        a: 1.0
                    },
                    text: 'Mb/s',
                    size: {
                        x: 20,
                        y: 20
                    },
                    position: {
                        x: 190,
                        y: 90,
                        z: 0
                    }
                }]
        };
        let spriteConn2Config = {
            name: spriteConn2Name,
            position: {
                x: 110,
                y: 260,
                z: 20
            },
            size: {
                x: 256,
                y: 128,
                z: 80
            },
            color: {
                r: 34,
                g: 76,
                b: 143,
                a: 0.8
            },
            imgurl: '',
            rows: [{
                name: 'item1',
                fontface: 'Arial',
                fontsize: 20,
                borderThickness: 32,
                textColor: {
                    r: 255,
                    g: 255,
                    b: 255,
                    a: 1.0
                },
                text: '\u5F53\u524D\u4E0A\u884C\u5E26\u5BBD',
                size: {
                    x: 20,
                    y: 20
                },
                position: {
                    x: 10,
                    y: 30,
                    z: 0
                }
            },
                {
                    name: 'item2',
                    fontface: 'Arial',
                    fontsize: 60,
                    borderThickness: 14,
                    textColor: {
                        r: 255,
                        g: 255,
                        b: 255,
                        a: 1.0
                    },
                    text: '345.8',
                    size: {
                        x: 20,
                        y: 20
                    },
                    position: {
                        x: 10,
                        y: 80,
                        z: 0
                    }
                },
                {
                    name: 'item3',
                    fontface: 'Arial',
                    fontsize: 24,
                    borderThickness: 14,
                    textColor: {
                        r: 255,
                        g: 255,
                        b: 255,
                        a: 1.0
                    },
                    text: 'Mb/s',
                    size: {
                        x: 20,
                        y: 20
                    },
                    position: {
                        x: 190,
                        y: 90,
                        z: 0
                    }
                }]
        };
        
        D3DOBJ.makeDynamicTextSprite('equipment_card_52', spriteConn1Config);
        D3DOBJ.makeDynamicTextSprite('equipment_card_23', spriteConn2Config);
    } else {
        D3DOBJ.delObject(pathName);
        D3DOBJ.delObject(spriteConn1Name);
        D3DOBJ.delObject(spriteConn2Name);
    };
    GCONFIG['conn'] = !GCONFIG['conn']
};
D3DLib.prototype.temp = function(){
	let objName = 'temp2001';
    if (!GCONFIG['temp']) {
        let planeConfig = {
            name: objName,
            width: 2040,
            height: 220,
            imgurl: 'temp1.jpg',
            transparent: true,
            opacity: 1,
            blending: false,
            color: {r: 255,g: 255,b: 255,a: 1.0},
            position: {x: 0, y: 120, z: -500},
            rotation: {x: Math.PI/2.0,y: 0.0,z: 0.0} 
        };
        let sideConfig = THREE.DoubleSide;
        D3DOBJ.addPlane(planeConfig, sideConfig)
    } else {
        D3DOBJ.delObject(objName);
    }
    GCONFIG['temp'] = !GCONFIG['temp']
};
D3DLib.prototype.air = function(){
	let nurbsName = 'nurbs1001';
	if(!GCONFIG['air']){
        let nurbsConfig = {
			name: nurbsName,
			type: 'nurbs',
			position:{x:-820, y:100, z:-280},
			size: {x:300,y:260},
			color:{r:255,g:255,b:255, a:1.0},
			imgurl: GCONFIG['pic_path'] + 'arrow.png'
		};
		D3DOBJ.addNurbs(nurbsConfig);
	} else {
		D3DOBJ.delObject(nurbsName);
	}
	GCONFIG['air'] = !GCONFIG['air'];
};
D3DLib.prototype.smoke = function(){
    let sprite1Name = 'sprite1001';
    let sprite2Name = 'sprite1002';
    let textName = 'smokingtext';
    if (!GCONFIG['smoke']) {
        let sprite1Config = {
            name: sprite1Name,
            position: {
                x: -150,
                y: 210,
                z: -280,
                w: 3
            },
            size: {
                x: 150,
                y: 300,
                z: 150
            },
            number: 350,
            color: 0xd71345,
            imgurl: GCONFIG['pic_path'] + 'smoking.png'
        };
        D3DOBJ['addSprite'](sprite1Config);
        let sprite2Config = {
            name: sprite2Name,
            position: {
                x: 120,
                y: 210,
                z: 0,
                w: 5
            },
            size: {
                x: 150,
                y: 300,
                z: 150
            },
            number: 350,
            color: 0xf47920,
            imgurl: GCONFIG['pic_path'] + 'smoking.png'
        };
        D3DOBJ['addSprite'](sprite2Config);

        let text1Config = {
            name: textName,
            width:64,
            height:26,
            position: {
                x: 0,
                y: 30,
                z: 0
            },
            size: {
                x: 256,
                y: 128,
                z: 80
            },
            color: {
                r: 34,
                g: 76,
                b: 143,
                a: 0.8
            },
            imgurl: '',
            fontsize: 14,
            message: '烟雾报警'
        };
        D3DOBJ.makeTextSprite(sprite1Name, text1Config);
        let text2Config = {
            name: textName,
            width:64,
            height:26,
            position: {
                x: 0,
                y: 30,
                z: 0
            },
            size: {
                x: 256,
                y: 128,
                z: 80
            },
            color: {
                r: 34,
                g: 76,
                b: 143,
                a: 0.8
            },
            imgurl: '',
            fontsize: 14,
            message: '高温报警'
        };
        D3DOBJ.makeTextSprite(sprite2Name, text2Config);
    } else {
        D3DOBJ['delObject'](sprite1Name);
        D3DOBJ['delObject'](sprite2Name);
        D3DOBJ['delObject'](textName);
    }
    GCONFIG['smoke'] = !GCONFIG['smoke']
};
D3DLib.prototype.capacity = function() {
    //遍历每个机柜
        //获取出当前机柜总容量
        //已使用U
        //剩余U
        //最大连续U数
        //剩余容量
}
//////////////////////////////////////////////////
D3DLib.prototype.updateControls = function () {
    let width = $(D3DOBJ.fId).width();
    let height = $(D3DOBJ.fId).height();
	//console.log("canvas width:"+width+",height:"+height);
};
// Init Axis Helper
D3DLib.prototype.initAxisHelper = function(){
	this.axisHelper = new THREE.AxisHelper( 2000 );
	this.scene.add(this.axisHelper);
	let geometry = new THREE.BoxGeometry( 1, 1, 1 );
	let material = new THREE.MeshBasicMaterial( {color: 0x00ff00} );
	let cube = new THREE.Mesh( geometry, material );
	this.scene.add( cube );
};
D3DLib.prototype.initStats = function(){
    D3DOBJ.statUi = new Stats();
    D3DOBJ.statUi.setMode(0); // 0: fps, 1: ms
    D3DOBJ.statUi.domElement.style.position = 'absolute';
    D3DOBJ.statUi.domElement.style.left = '0px';
    D3DOBJ.statUi.domElement.style.top = '0px';

    document.body.appendChild(D3DOBJ.statUi.domElement);
}
D3DLib.prototype.initLight = function() {
    let ambientLight = new THREE.AmbientLight(0xcccccc);
    ambientLight['position']['set'](0, 0, 0);
    this.scene.add(ambientLight);
    let pointLight1 = new THREE.PointLight(0x666666);
    pointLight1['shadow']['camera']['near'] = 1;
    pointLight1['shadow']['camera']['far'] = 5000;
    pointLight1['position']['set'](0, 350, 0);
    pointLight1['castShadow'] = false;
    this.scene.add(pointLight1);
};
D3DLib.prototype.initBgImg = function() {
    let skyBoxGeometry = new THREE.BoxGeometry( 10000, 10000, 10000 );
    let texture = new THREE.TextureLoader().load(GCONFIG['pic_path'] + GCONFIG['bg_img']);
    let skyBoxMaterial = new THREE.MeshBasicMaterial( { map:texture, side: THREE.DoubleSide } );
    let skyBox = new THREE.Mesh( skyBoxGeometry, skyBoxMaterial );
    this.scene.add(skyBox);
};

D3DLib.prototype.addObjects = function() {
	for(let i=0; i<D3DOBJ.objects.length; ++i){
		this.scene.add(D3DOBJ.objects[i]);
	}
};
D3DLib.prototype.animation = function() {
	if (TWEEN != null && typeof(TWEEN) !== 'undefined') {
        TWEEN.update();
    }
	D3DOBJ.onUpdateScene();
	D3DOBJ.statUi.update();
	requestAnimationFrame(D3DOBJ['animation']);
	//D3DOBJ.controls.update();
	D3DOBJ.renderer.render(D3DOBJ.scene, D3DOBJ.camera);
};
let isFirst = true;
D3DLib.prototype.onUpdateScene = function() {
	// 更新场景
//	let date = new Date();
//	if( date.getSeconds()%60 == 0){
//		if(isFirst){
//			isFirst = false;
//			//D3DOBJ.updateFromDB();
//		}
//	}
//	else{
//		isFirst = true;
//	}
	if(D3DOBJ.controls.autoRotate === true){
		D3DOBJ.controls.update();
	}
//    if (_dg3dObj['vcmaterial']['length']>0 ) {
//        for(let i=0;i<_dg3dObj['vcmaterial'].length;i++){
//            _dg3dObj['vcmaterial'][i]['map']['offset']['x'] -= 0.001;
//            step = _dg3dObj['vcmaterial'][i]['map']['offset']['x']
//        }
//
//    }
//
    if (D3DOBJ['nurbsmaterial'] != null && typeof(D3DOBJ['nurbsmaterial']) !== 'undefined') {
        D3DOBJ['nurbsmaterial']['map']['offset']['y'] -= 0.1;
    }
    if (D3DOBJ['pathTubeGeometry'] != null && typeof(D3DOBJ['pathTubeGeometry']) !== 'undefined') {
		let scalar = 1.0;
        let yStep = 20;
        let xStep = 10;
        let camera = D3DOBJ['camera'];
        let pathTubeGeo = D3DOBJ['pathTubeGeometry'];
        let pointIndex = (D3DOBJ['dynamicPathTimer'] % 1);
        let point1 = new THREE.Vector3();
        let point2 = new THREE.Vector3();
        let pathPoint = pathTubeGeo['parameters']['path']['getPointAt'](pointIndex);
        pathPoint['y'] += yStep;

        pathPoint['multiplyScalar'](scalar);
        camera['position']['copy'](pathPoint);

        let pointIndex2 = (pointIndex + 30 / pathTubeGeo['parameters']['path']['getLength']()) % 1;
        let pathPoint2 = pathTubeGeo['parameters']['path']['getPointAt'](pointIndex2)['multiplyScalar'](scalar);
        pathPoint2['y'] += yStep;
        camera['matrix']['lookAt'](camera['position'], pathPoint2, point2);
        camera['rotation']['setFromRotationMatrix'](camera['matrix'], camera['rotation']['order']);
        D3DOBJ['dynamicPathTimer'] += 0.0005;
        if (D3DOBJ['dynamicPathTimer'] > 1.0) {
            D3DOBJ['dynamicPathTimer'] = 0.0
        }
    }
};
D3DLib.prototype.updateFromDB = function() {
	//////////////////////////////////////////
	let promise_get_all_tpl = function(){
		return new Promise(function (resolve, reject) {
	        $.ajax({
	            url: '/glserver/mesh/get_all_tpl',
	            dataType: 'json',
	            data: null,
	            success: function (data) {
	            	console.log('promise 1');
	                D3DOBJ.parseTplData(data);
	                resolve();
	            },
	            error: function (data) {
	                console.log("update tpl from db  error")
	            },
	        });
		});
	};
    let promise_get_all_mesh = function() {
    	return new Promise(function (resolve, reject) {
	        $.ajax({
	            url: '/glserver/mesh/get_all_mesh',
	            dataType: 'json',
	            data: null,
	            success: function (data) {
	                console.log('promise 2');
	                D3DOBJ.parseMeshData(data);
	                resolve();
	            },
	            error: function (data) {
	                console.log("update mesh from db  error");
	            },
	        });
	    });
    };
    let promise_get_all_clone = function() {
    	return new Promise(function(resolve, reject){
		    $.ajax({
		        url:'/glserver/clone/get_all_clone',
		        dataType:'json',
		        data: null,
		        success:function(data) {
		            console.log('promise 3');
		            D3DOBJ.parseCloneData(data);
		            resolve();
		        },
		        error: function(data){
		            console.log("update clone from db  error");
		        },
		    });
		});
    };
    let promise_get_all_label = function(){
    	return new Promise(function(resolve, reject){
	        $.ajax({
	            url: '/glserver/label/get_all_label',
	            dataType: 'json',
	            data: null,
	            success: function (data) {
	                console.log('promise 4');
	                D3DOBJ.parseLabelData(data);
	                //D3DOBJ.controls.autoRotate = true;
	            },
	            error: function (data) {
	                console.log("update label from db error");
	            }
	        });
	    });
    };
    let promise_get_all_device = function() {
    	return new Promise(function(resolve, reject) {
	        $.ajax({
	            url: '/glserver/clone/get_all_device',
	            dataType: 'json',
	            data: null,
	            success: function (data) {
	                console.log('promise 5');
	                D3DOBJ.parseDeviceData(data);
	                resolve();
	            },
	            error: function (data) {
	                console.log("update device from db error");
	            }
	        });
	    });
    };
    promise_get_all_tpl().then(function(){
    	return promise_get_all_mesh();
    }).then(function(){
    	return promise_get_all_clone();
    }).then(function(){
    	return promise_get_all_device();
    }).then(function(){
    	return promise_get_all_label();
    });
};
D3DLib.prototype.parseDeviceData = function(datas){
    for(let i=0; i<datas.length; ++i){
        let data = datas[i];

        // 首先在scene中查看
        let devName = data['code'];
        if (devName==='' || devName.length !== 7){
            console.log('device name is not qualified. devName:'+devName);
            continue;
        }
        let device = D3DOBJ.scene.getObjectByName(devName);
        if( device !== undefined){
            device.parent.remove(device);
        }

        // 根据Code解析出机柜，起始U
        // 根据template确定clone对象
        let staId = devName.substr(0,1);
        let cabId = devName.substr(1,2);
        let startU = parseInt(devName.substr(3,2));
        let useU = parseInt(devName.substr(5));
		data['name'] = 'dev_' + devName;
        data['parent'] = 'cab-' + cabId;
        data['x'] = 0;
        data['y'] = 5*startU - GCONFIG['cab_inner_height']/2;
        data['z'] = 0;
        data['rot_y'] = 180;

        let mesh = D3DOBJ.cloneObj(data);
        D3DOBJ.addObject(mesh, data['parent']);

        //修改isDirty标志
        // $.ajax({
        //     type:'post',
        //     url:'/glserver/label/update_isdirty',
        //     dataType:'json',
        //     data: { uuid: data['uuid'] },
        //     success:function(data){
        //         //
        //     },
        //     error: function(data) {
        //         console.log("update label isdirty error uuid:"+data['uuid']);
        //     }
        // });
    }
};
D3DLib.prototype.addObject = function(object){
    if (object === undefined || object === null) {
        return;
    }
    if (!object.hasOwnProperty('name')) {
        console.log(object);
        return;
    }
	if(object.name !== undefined && object.name !== null && object.name.indexOf('cab-') === 0){
	    D3DOBJ.cabList.push(object);
    }
    if ( arguments.length > 1 ) {
		let parentName = arguments[1];
		let parentMesh = D3DOBJ.scene.getObjectByName(parentName);
		if(exist(parentName) && parentName !== '' && parentMesh !== undefined){
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
D3DLib.prototype.delObject = function(name, parent){
    if (name === undefined || name === null) {
        return;
    }
    if(!(parent instanceof THREE.Mesh)){
    	parent = D3DOBJ.scene;
    }
    let mesh = null;
    while((mesh = parent.getObjectByName(name)) !== undefined) {
        parent.remove(mesh);
    }
};
D3DLib.prototype.findObject = function(name){
    return D3DOBJ.scene.getObjectByName(name);
};
D3DLib.prototype.parseLabelData = function(datas){
	for(let i=0; i<datas.length; ++i){
		let data = datas[i];
		
		// 首先在scene中查看
		
		let label = D3DOBJ.scene.getObjectByName(data['name']);
		if( label !== undefined){
			label.parent.remove(label);
		}
		label = D3DOBJ.createLabel(data);
		//D3DOBJ.addObject(label, data['parent']);
		
		//修改isDirty标志
//		$.ajax({
//			type:'post',
//			url:'/glserver/label/update_isdirty',
//			dataType:'json',
//			data: { uuid: data['uuid'] },
//			success:function(data){
//				//
//			},
//			error: function(data) {
//				console.log("update label isdirty error uuid:"+data['uuid']);
//			}
//		});
	}
};
D3DLib.prototype.createLabel = function(data){
	let label = null;
	if(exist(data['pic']) && data['pic'] !== ''){
		let image = new Image();
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
D3DLib.prototype.drawLabel = function(data){
	let drawingCanvas = document.createElement('canvas');
	let drawingContext = drawingCanvas.getContext('2d');
	drawingCanvas.width = data['width'] || 50;
    drawingCanvas.height = data['height'] || 50;

    if (data.hasOwnProperty("fillColor") && data['fillColor'] !== ''){
        drawingContext.fillStyle = data['fillColor'];
    }
    if(exist(data['pic']) && data['pic'] !== ''){
    	drawingContext.drawImage(data['imgobj'], 0, 0, drawingCanvas.width, drawingCanvas.height);
    } else {
        drawingContext.fillRect(0, 0, drawingCanvas.width, drawingCanvas.height);
    }
    drawingContext.textAlign = 'center';
    drawingContext.textBaseline = 'middle';
    //drawingContext.fillStyle = '#FFFFFF';
    if(exist(data['texts'])){
    	for (let i = 0; i < data['texts'].length; i++) {
    	    let text = data['texts'][i];
            drawingContext.font = text.font;
    	    if (text.hasOwnProperty('color') && text['color'] !== '') {
                drawingContext.fillStyle = text['color'];
            }
            drawingContext.fillText(text.text, text['offX'], text['offY']);
        }
    }
    
    let material = new THREE.MeshBasicMaterial();
    let texture = new THREE.Texture(drawingCanvas);
    texture.needsUpdate = true;
    material.map = texture;
    //material.opacity=.5;
    material.transparent=true;

    let mesh = new THREE.Mesh(new THREE.BoxGeometry(drawingCanvas.width, drawingCanvas.height, 1), material);
    mesh.name = data['name'];
    mesh.uuid = data['uuid'];
    mesh.position.y = data["y"];
    mesh.position.x = data["x"];
    mesh.position.z = data["z"];
    let scl_x = data['scl_x'] || 1;
    let scl_y = data['scl_y'] || 1;
    let scl_z = data['scl_z'] || 1;
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
D3DLib.prototype.parseCloneData = function(data){
	"use strict";
	for(let i=0; i<data.length; ++i){
		let cloneData = data[i];
		let mesh = D3DOBJ.scene.getObjectByName(cloneData['name']);
		if( mesh !== undefined)
			D3DOBJ.scene.remove(mesh);
		
		mesh = D3DOBJ.cloneObj(cloneData);
		D3DOBJ.addObject(mesh, cloneData['parent']);
		
		//修改isDirty标志
//		$.ajax({
//			type:'post',
//			url:'/glserver/clone/update_isdirty',
//			dataType:'json',
//			data: { uuid: cloneData['uuid'] },
//			success:function(data){
//				//
//			},
//			error: function(data) {
//				console.log("update clone isdirty error uuid:"+cloneData['uuid']);
//			}
//		});
	}
};
D3DLib.prototype.cloneObj = function(cloneData){
	let sourceName = cloneData['copyfrom'];
	let sourceObj = null;
	if(exist(sourceName) && sourceName !== ''){
		sourceObj = D3DOBJ.tplList.get(sourceName);
	} else {
		console.log('clone obj, source name is null');
		return;
	}
	if( sourceObj === undefined ) {
		console.log('scene has not find source name: ' + sourceName);
		return;
	}
	let cloneObj = sourceObj.clone();
	cloneObj['name'] = cloneData['name'];
    cloneObj['uuid'] = cloneData['uuid'];
    if (cloneObj['children'] != null && cloneObj['children']['length'] > 1) {
        $['each'](cloneObj['children'], function (i, child) {
            child['name'] = cloneData['name'] + '-' + child['name'];
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
D3DLib.prototype.parseTplData = function(data) {
	let sad;
	for(let i=0; i<data.length; ++i){
		let tplData = data[i];
		let tpl = D3DOBJ.tplList.has(tplData['name']);
		if( tpl !== undefined)
			D3DOBJ.tplList.delete(tpl);
		switch(tplData['type'])
		{
		case 'hole': // 非场景的直接children
			tpl = D3DOBJ.createHole(tplData, '1');
			break;
		case 'cube':
			tpl = D3DOBJ.createBox(tplData);
			break;
		}
		if(tplData['type'] !== 'hole'){
			if(exist(tplData['parent']) && tplData['parent'] !== ''){
				D3DOBJ.tplList.get(tplData['parent']).add(tpl);
			}
			D3DOBJ.tplList.set(tplData['name'], tpl);
		}
	}
};
D3DLib.prototype.parseMeshData = function(data){
	for(let i=0; i<data.length; ++i){
		let meshData = data[i];
		let mesh = D3DOBJ.scene.getObjectByName(meshData['name']);
		if( mesh !== undefined)
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
//		$.ajax({
//			type:'post',
//			url:'/glserver/mesh/update_isdirty',
//			dataType:'json',
//			data: { uuid: meshData['uuid'] },
//			success:function(data){
//				//
//			},
//			error: function(data) {
//				console.log("update mesh isdirty error uuid:"+meshData['uuid']);
//			}
//		});
	}
};

D3DLib.prototype.createGlass = function(meshData){
    let side_config = THREE['DoubleSide'];
    return D3DOBJ.createPlane(meshData, side_config);
};
D3DLib.prototype.createPlane = function(meshData, side_config){
	let textureLoader = new THREE.TextureLoader();
    let texture = textureLoader['load'](GCONFIG['pic_path']+meshData['imgurl'], function () {}, undefined, function () {});
    if(exist(side_config) === false || side_config === ''){
    	side_config = THREE['FrontSide'];
    }
    let config = {
        map: texture,
        overdraw: true,
        side: side_config,
        transparent: meshData['transparent'],
        opacity: meshData['opacity']
    };
    if (meshData['blending']) {
        config['blending'] = THREE['AdditiveBlending'];
    }
    let mesh = new THREE.Mesh(new THREE.PlaneGeometry(meshData['width'], meshData['height'], 1, 1), new THREE.MeshBasicMaterial(config));
    mesh['name'] = meshData['name'];
    mesh['position'].set(meshData['x'],meshData['y'],meshData['z']);
    return mesh;
};
// mode: 0 表示与场景中对象进行BSP操作
// mode: 1 表示与模板中对象进行BSP操作
D3DLib.prototype.createHole = function(meshData, mode){
	if(mode === undefined || mode === ''){
		mode = '0';
	}
	let hole = D3DOBJ.createBox(meshData);
	let parentName = meshData['parent'];
	let opcode = meshData['opcode'];
	let parentMesh = null;
	if(mode === '0'){
		parentMesh = D3DOBJ.scene.getObjectByName(parentName);
		D3DOBJ.scene.remove(parentMesh);
	} else {
		parentMesh = D3DOBJ.tplList.get(parentName);
		D3DOBJ.tplList.delete(parentMesh);
	}
	
	if(exist(parentName) && parentName !== '' && parentMesh !== undefined){
		parentMesh = D3DOBJ.mergeModel(opcode, parentMesh, hole);
		if(mode === '0'){
			D3DOBJ.scene.add(parentMesh);
		} else {
			D3DOBJ.tplList.set(parentName, parentMesh);
		}
	}
};
// D3DLib.prototype.mergeModel = function(op, fromObj, toObj){
//     let fromBsp = new ThreeBSP(fromObj);
//     let toBsp = new ThreeBSP(toObj);
//     let resBsp = null;
//     switch (op) {
//         case '-':
//             resBsp = fromBsp.subtract(toBsp);
//             break;
//         case '+':
//             resBsp = fromBsp.union(toBsp);
//             break;
//         case '&':
//             resBsp = fromBsp.intersect(toBsp);
//             break;
//         default:
//             console.log('unsupported opcode ' + opcode);
//     }
//     let resMesh = resBsp.toMesh(fromBsp.material);
//     resMesh['material']['shading'] = THREE['FlatShading'];
//     resMesh['geometry']['computeFaceNormals']();
//     resMesh['geometry']['computeVertexNormals']();
//     resMesh['uuid'] = fromObj['uuid'];
//     resMesh['name'] = fromObj['name'];
//     resMesh['material']['needsUpdate'] = true;
//     resMesh['geometry']['buffersNeedUpdate'] = true;
//     resMesh['geometry']['uvsNeedUpdate'] = true;
//     let fromCopyObj = null;
//     for (let i = 0; i < resMesh['geometry']['faces']['length']; i++) {
//         let isCopyFaces = false;
//         for (let faceI = 0; faceI < fromObj['geometry']['faces']['length']; faceI++) {
//             if (resMesh['geometry']['faces'][i]['vertexNormals'][0]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['x'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][0]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['y'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][0]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['z'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][1]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['x'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][1]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['y'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][1]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['z'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][2]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['x'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][2]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['y'] &&
//                 resMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['z']) {
//                 resMesh['geometry']['faces'][i]['color']['setHex'](
//                     fromObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
//                     fromObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
//                     fromObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff);
//                 fromCopyObj = fromObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
//                     fromObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
//                     fromObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff;
//                 isCopyFaces = true;
//             }
//         }
//         if (isCopyFaces === false) {
//             for (let faceI = 0; faceI < toObj['geometry']['faces']['length']; faceI++) {
//                 if (resMesh['geometry']['faces'][i]['vertexNormals'][0]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['x'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][0]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['y'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][0]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['z'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][1]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['x'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][1]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['y'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][1]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['z'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][2]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['x'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][2]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['y'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['z'] &&
//                     resMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['z']) {
//                     resMesh['geometry']['faces'][i]['color']['setHex'](toObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
//                         toObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
//                         toObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff);
//                     fromCopyObj = toObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
//                         toObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
//                         toObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff;
//                     isCopyFaces = true;
//                 }
//             }
//         }
//         if (isCopyFaces === false) {
//             resMesh['geometry']['faces'][i]['color']['setHex'](fromCopyObj);
//         }
//     }
//     resMesh.castShadow = true;
//     resMesh.receiveShadow = true;
//     return resMesh;
// }

D3DLib.prototype.mergeModel = function (op, fromObj, toObj) {
    //let _dg3dObj = this;
    let bspFrom = new ThreeBSP(fromObj);
    let bspTo = new ThreeBSP(toObj);
    let fromMaterial = fromObj.material;
    let subObj = null;
    if (op === '-') {
        subObj = bspFrom['subtract'](bspTo);
    } else if (op === '+') {
        toObj['updateMatrix']();
        fromObj['geometry']['merge'](toObj['geometry'], toObj['matrix']);
        return fromObj;
    } else if (op === '&') {
        subObj = bspFrom['intersect'](bspTo);
    } else {
    }

    let subMesh = subObj['toMesh'](fromMaterial);
    subMesh['material']['shading'] = THREE['FlatShading'];
    subMesh['geometry']['computeFaceNormals']();
    subMesh['geometry']['computeVertexNormals']();
    subMesh['uuid'] = fromObj['uuid'];
    subMesh['name'] = fromObj['name'];
    subMesh['material']['needsUpdate'] = true;
    subMesh['geometry']['buffersNeedUpdate'] = true;
    subMesh['geometry']['uvsNeedUpdate'] = true;

    let fromCopyObj = null;
    for (let i = 0; i < subMesh['geometry']['faces']['length']; i++) {
        let isCopyFaces = false;
        for (let faceI = 0; faceI < fromObj['geometry']['faces']['length']; faceI++) {
            if (subMesh['geometry']['faces'][i]['vertexNormals'][0]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['x'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][0]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['y'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][0]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][0]['z'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][1]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['x'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][1]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['y'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][1]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][1]['z'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][2]['x'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['x'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][2]['y'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['y'] &&
                subMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === fromObj['geometry']['faces'][faceI]['vertexNormals'][2]['z'])
            {
                subMesh['geometry']['faces'][i]['color']['setHex'](
                    fromObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
                    fromObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
                    fromObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff
                );
                fromCopyObj = fromObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
                    fromObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
                    fromObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff;
                isCopyFaces = true;
            }
        }
        if (isCopyFaces === false) {
            for (let faceI = 0; faceI < toObj['geometry']['faces']['length']; faceI++) {
                if (subMesh['geometry']['faces'][i]['vertexNormals'][0]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['x'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][0]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['y'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][0]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][0]['z'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][1]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['x'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][1]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['y'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][1]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][1]['z'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][2]['x'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['x'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][2]['y'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['y'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['z'] &&
                    subMesh['geometry']['faces'][i]['vertexNormals'][2]['z'] === toObj['geometry']['faces'][faceI]['vertexNormals'][2]['z']) {
                    subMesh['geometry']['faces'][i]['color']['setHex'](
                        toObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
                        toObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
                        toObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff
                    );
                    fromCopyObj = toObj['geometry']['faces'][faceI]['color']['r'] * 0xff0000 +
                        toObj['geometry']['faces'][faceI]['color']['g'] * 0x00ff00 +
                        toObj['geometry']['faces'][faceI]['color']['b'] * 0x0000ff;
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
D3DLib.prototype.createWall = function(meshData){
	return D3DOBJ.createBox(meshData);	
};
D3DLib.prototype.createFloor = function(mesh){
	return D3DOBJ.createBox(mesh);	
};
D3DLib.prototype.createBox = function(mesh){
	let length = mesh['length'] || 1000;
	let width = mesh['width'] || length;
	let height = mesh['height'] || 10;
	let x = mesh['x'] || 0;
	let y = mesh['y'] || 0;
	let z = mesh['z'] || 0;
	let skinColor = mesh['skinColor'] || 0x00b0e2;
	let boxGeo = new THREE.BoxGeometry( length, height, width, 0, 0, 1);
	// 初始化全局
	for (let i = 0; i < boxGeo['faces']['length']; i += 2) {
		boxGeo['faces'][i]['color']['setHex'](skinColor);
		boxGeo['faces'][i + 1]['color']['setHex'](skinColor);
    }
	let vertexColors = { vertexColors: THREE['FaceColors']};
    let textFore = vertexColors;
    let textBack = vertexColors;
    let textUp = vertexColors;
    let textDown = vertexColors;
    let textRight = vertexColors;
    let textLeft = vertexColors;
        
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
    
    let materials = [];
    materials['push'](new THREE.MeshLambertMaterial(textFore));
    materials['push'](new THREE.MeshLambertMaterial(textBack));
    materials['push'](new THREE.MeshLambertMaterial(textUp));
    materials['push'](new THREE.MeshLambertMaterial(textDown));
    materials['push'](new THREE.MeshLambertMaterial(textRight));
    materials['push'](new THREE.MeshLambertMaterial(textLeft));
    let box = new THREE.Mesh(boxGeo, materials);
    box['castShadow'] = true;
    box['receiveShadow'] = true;
    box['transparent'] = true;
    box['uuid'] = mesh['uuid'];
    box['name'] = mesh['name'];
    box['position']['set'](x, y, z);

    return box;  
};
// test git
D3DLib.prototype.createTexture = function (length, width, skin, boxGeo, faceIndex) {
	let retObj = null;
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
D3DLib.prototype.createSkin = function(length, width, skin){
    let img_width = 64;
    let img_height= 64;
    if (exist(skin['width'])) {
        img_width = skin['width'];
    }
    if (exist(skin['height'])) {
    	img_height = skin['height'];
    }
    let texture = new THREE.TextureLoader()['load'](GCONFIG['pic_path']+skin['imgurl']);
    if(exist(skin['isRepeat']) && skin['isRepeat']>0){
    	texture['wrapS'] = THREE['RepeatWrapping'];
	    texture['wrapT'] = THREE['RepeatWrapping'];
	    texture['repeat']['set'](length / img_width, width / img_height);
    }
    return texture;
};
D3DLib.prototype.dynamicPath = function(pathConfig, pointArr){
	let step = D3DOBJ['addTunnel'](pathConfig, pointArr);
    D3DOBJ['pathTubeGeometry'] = step['geometry'];
    D3DOBJ['dynamicPathTimer'] = 0.0
};
D3DLib.prototype.stopDynamicPath = function (name) {
    D3DOBJ['pathTubeGeometry'] = null;
    D3DOBJ['dynamicPathTimer'] = 0.0;
    D3DOBJ.delObject(name);
};
D3DLib.prototype.addTunnel = function (config, points) {
    let pointsArr = [];
    let roomCurve = null;
    if (points != null && points['length'] > 0) {
        for(let i=0; i<points.length; ++i){
        	let v3 = new THREE.Vector3(0,0,0);
        	v3.x = points[i].x;
        	v3.y = points[i].y;
        	v3.z = points[i].z;
        	pointsArr.push(v3);
        }
    };
    if (config['type'] == null || typeof(config['type']) === 'undefined' || config['type'] === 'curve') {
        roomCurve = new THREE.CatmullRomCurve3(pointsArr)
    } else if (config['type'] === 'beeline') {
        function CustomCurve(scale) {
            THREE['Curve']['call'](this);
            this['scale'] = (scale === undefined) ? 1 : scale
        }
        CustomCurve['prototype'] = Object['create'](THREE['Curve']['prototype']);
        CustomCurve['prototype']['constructor'] = CustomCurve;
        CustomCurve['prototype']['getPoint'] = function (pointIndex) {
            let nPoint = pointsArr['length'] - 1;
            let per = 1 / nPoint;
            let vindex = parseInt( pointIndex / per);
            if (1 === pointIndex) {
                console['log'](pointIndex + '/' + per + ' = ' + vindex + ' ');
                return pointsArr[vindex]['clone']()
            };
            let nVertor3 = new THREE.Vector3();
            nVertor3['subVectors'](pointsArr[vindex + 1], pointsArr[vindex]);
            nVertor3['multiplyScalar']((pointIndex - (per * vindex)) * nPoint);
            nVertor3['add'](pointsArr[vindex]);
            return nVertor3
        };
        roomCurve = new CustomCurve(10)
    }
    let material = null;
    if (config.hasOwnProperty('imgurl') && config['imgurl'] !== ''){
        let textureLoader = new THREE.TextureLoader()['load'](config['imgurl']);
        textureLoader['wrapS'] = textureLoader['wrapT'] = THREE['RepeatWrapping'];
        textureLoader['anisotropy'] = 16;
        material = new THREE.MeshLambertMaterial({
            map: textureLoader,
            side: THREE['DoubleSide']
        });
    } else {
        let color = config['color'] || 'green';
        material = new THREE.MeshBasicMaterial({color:color});
    }
//    _dg3dObj['vcmaterial']['push'](material);
    let geometry = new THREE.TubeGeometry(roomCurve, 750, config['radiu'], 8, false);
    let mesh = new THREE.Mesh(geometry, material);
    mesh['position']['set'](0, 0, 0);
    mesh['name'] = config['name'];
    mesh['visible'] = config['visible'];
    if (true === config['scene']) {
        D3DOBJ.scene.add(mesh);
    };
    return mesh
};
D3DLib.prototype.makeDynamicTextSprite = function(name, config){
	
};
D3DLib.prototype.makeTextSprite = function(name, textConfig){
    if (textConfig === undefined || textConfig === null) {
        textConfig = {};
    }

    let fontface = textConfig['hasOwnProperty']('fontface') ? textConfig['fontface'] : 'Arial';
    let fontsize = textConfig['hasOwnProperty']('fontsize') ? textConfig['fontsize'] : 18;
    let borderThickness = textConfig['hasOwnProperty']('borderThickness') ? textConfig['borderThickness'] : 4;
    let textColor = textConfig['hasOwnProperty']('textColor') ? textConfig['textColor'] : {r: 255,g: 255,b: 255,a: 1.0};
    let message = textConfig['hasOwnProperty']('message') ? textConfig['message'] : '';
    let x = textConfig['hasOwnProperty']('position') ? textConfig['position']['x'] : 10;
    let y = textConfig['hasOwnProperty']('position') ? textConfig['position']['y'] : 80;
    let z = textConfig['hasOwnProperty']('position') ? textConfig['position']['z'] : 0;

    let canvas = document['createElement']('canvas');
    let width = textConfig['hasOwnProperty']('width') ? textConfig['width'] : 128;
    let height = textConfig['hasOwnProperty']('height') ? textConfig['height'] : width / 2;
    canvas['width'] = width;
    canvas['height'] = height;
    let context = canvas['getContext']('2d');
    //vcanvas = canvas;
    //vcontext = context;
    context['fillStyle'] = 'rgb(255,127,39)';
    context['fillRect'](0, 0, width, height);
    context['fillStyle'] = 'black';
    context['textAlign'] = 'center';
    context['textBaseline'] = 'middle';
    context['font'] = 'bold ' + fontsize + 'px ' + fontface;
    context['lineWidth'] = borderThickness;
    context['fillStyle'] = 'rgba(' + textColor['r'] + ', ' + textColor['g'] + ', ' + textColor['b'] + ', 1.0)';
    context['fillText'](message, width/2, borderThickness+height/5);
    // let martrix = context['measureText'](message);
    // let temWidth = martrix['width'];
    let texture = new THREE.Texture(canvas);
    texture['needsUpdate'] = true;
    let spriteMaterial = new THREE.SpriteMaterial({
        map: texture,
        useScreenCoordinates: false
    });
    let sprite = new THREE.Sprite(spriteMaterial);
    let fromObj = D3DOBJ.findObject(name);
    sprite['position']['x'] = fromObj['position']['x'] + x;
    sprite['position']['y'] = fromObj['position']['y'] + y;
    sprite['position']['z'] = fromObj['position']['z'] + z;
    sprite['name'] = textConfig['name'];
    sprite['scale']['set'](5 * fontsize, 3.0 * fontsize, 1.0);
    D3DOBJ['addObject'](sprite)
};
D3DLib.prototype.addNurbs = function(nurbsConfig) {
	let degree1 = 2;
	let degree2 = 3;
	let knots1 = [0,0,0, 1,1,1];
	let knots2 = [0,0,0, 0,1,1, 1,1];
	let stepPoints = [];
	let initX=0, initY=0, initZ=0;
	let stepX=800, stepY=40, stepZ=50;
	for(let i=0; i<4; ++i){
		let step = [
			new THREE.Vector4(initX + (i * stepX), initY - 5 * stepY, initZ + 3.5 * stepZ, 1),
			new THREE.Vector4(initX + (i * stepX), initY - 2.8 * stepY, initZ + 2.0 * stepZ, 1),
			new THREE.Vector4(initX + (i * stepX), initY - 2.5 * stepY, initZ, 1),
			new THREE.Vector4(initX + (i * stepX), initY, initZ - 0.9 * stepZ, 1),
		];
		stepPoints.push(step);
	}
	let nurbsFace = new THREE.NURBSSurface(degree1, degree2, knots1, knots2, stepPoints);
	let textureLoader = new THREE.TextureLoader().load(nurbsConfig['imgurl']);
	textureLoader.wrapS = textureLoader.wrapT = THREE['RepeatWrapping'];
	textureLoader.anisotropy = 16;
	textureLoader.repeat.set(16,6);
	textureLoader.needsUpdate = true;
	
	function paramFunc(param1, param2, point){
		let point1 = nurbsFace['getPoint'](param1, param2);
		point.x = point1.x;
		point.y = point1.y;
		point.z = point1.z;
	}
	
	let geometry = new THREE.ParametricBufferGeometry(paramFunc, 20, 20);
	geometry.computeVertexNormals();
	geometry.normalizeNormals();
	/////////
	let colors = [];
    let custColors = [
    	{r: 0,g: 0.5,b: 0.7},
    	{r: 0.6,g: 1.0,b: 0.3},
    	{r: 0.1,g: 0.9,b: 0.1}];
    for (let i = 0; i < geometry['attributes']['normal']['array']['length']; i++) {
        let tempColor = custColors[i % 3];
        if (tempColor !== undefined) {
            colors[3 * i] = tempColor['r'];
            colors[3 * i + 1] = tempColor['g'];
            colors[3 * i + 2] = tempColor['b']
        }
    };
    geometry['addAttribute']('color', new THREE.BufferAttribute(new Float32Array(colors), 3));
	/////////
	let material = new THREE.MeshLambertMaterial({
        map: textureLoader,
        side: THREE['DoubleSide'],
        opacity: 0.8,
        overdraw: true,
        transparent: true,
        vertexColors: THREE['VertexColors']
    });
    
    let mesh = new THREE.Mesh(geometry, material);
    mesh['position']['set'](nurbsConfig['position']['x'], nurbsConfig['position']['y'], nurbsConfig['position']['z']);
    mesh['rotation']['x'] =3* Math.PI/4;
    mesh['name'] = nurbsConfig['name'];
    mesh['scale']['multiplyScalar'](1.0);
    D3DOBJ['nurbsmaterial'] = material;
    D3DOBJ['addObject'](mesh)
};
D3DLib.prototype.addPlane = function(planeConfig, sideConfig) {
	planeConfig.x = planeConfig.position.x;
	planeConfig.y = planeConfig.position.y;
	planeConfig.z = planeConfig.position.z;
	let plane = D3DOBJ.createPlane(planeConfig, sideConfig);
	if(exist(planeConfig['parent'])===false || planeConfig['parent']==='' ){
		plane['scale']['x'] = planeConfig['scl_x'] || 1;
	    plane['scale']['y'] = planeConfig['scl_y'] || 1;
	    plane['scale']['z'] = planeConfig['scl_z'] || 1;
	    if( exist(planeConfig['rot_x']) && planeConfig['rot_x'] !== 0 ){
	    	plane['rotateX'](planeConfig['rot_x']*Math['PI']/180);
	    }
	    if( exist(planeConfig['rot_y']) && planeConfig['rot_y'] !== 0 ){
	    	plane['rotateY'](planeConfig['rot_y']*Math['PI']/180);
	    }
	    if( exist(planeConfig['rot_z']) && planeConfig['rot_z'] !== 0 ){
	    	plane['rotateZ'](planeConfig['rot_z']*Math['PI']/180);
	    }
	}
	D3DOBJ.addObject(plane, planeConfig['parent']);
};
D3DLib.prototype.addSprite = function(config){
    let scalar = config['position']['w'];
    let textureLoader = new THREE.TextureLoader()['load'](config['imgurl']);
    let material = new THREE.SpriteMaterial({
        map: textureLoader,
        color: config['color'],
        blending: THREE['AdditiveBlending']
    });
    let obj;
    for (let i = 0; i < config['number']; i++) {
        obj = new THREE.Sprite(material);
        D3DOBJ['initParticle'](config, obj, i * 10, scalar);
        obj['name'] = config['name'];
        D3DOBJ['addObject'](obj);
    }
};
D3DLib.prototype.initParticle = function(config, obj, delay, scale){
	if(obj == null || typeof(obj) === 'undefined'){
		return;
	}
    obj['position']['set'](config['position']['x'], config['position']['y'], config['position']['z']);
    obj['scale']['x'] = obj['scale']['y'] = Math['random']() * scale + scale * 2;
    let x = config['position']['x'];
    let positonX = x + config['size']['x'];
    let y = config['position']['y'];
    let positionY = y + config['size']['y'];
    let z = config['position']['z'];
    let positionZ = z + config['size']['z'];
    new TWEEN.Tween(obj)['delay'](delay)['to']({}, 1000)['onComplete'](function () {
        D3DOBJ['initParticle'](config, obj, delay, scale)
    })['start']();
    new TWEEN.Tween(obj['position'])['delay'](delay)['to']({
        x: (Math['random']() * (positonX - x + 1) + x),
        y: (Math['random']() * (positionY - y + 1) + y),
        z: (Math['random']() * (positionZ - z + 1) + z)
    }, 10000)['start']();
    new TWEEN.Tween(obj['scale'])['delay'](delay)['to']({
        x: 0.01,
        y: 0.01
    }, 10000)['start']()
    
};

