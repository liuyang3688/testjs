let D3DOBJ = null;
var colorArr = [ 'red', 'green', 'blue', 'yellow', 'purple' ];
let BJLX_AIR = 1,
    BJLX_FIRE = 2,
    BJLX_SERVER = 3,
    BJLX_UPS = 4;
var CsMap_AIR = {
    1:'空调参数1',
    2:'空调参数2',
    3:'空调参数3'
};
var CsMap_FIRE = {
    1:'消防参数1',
    2:'消防参数2',
    3:'消防参数3'
};
function getColorByRatio(ratio){
    // 绿 蓝 黄 红
    let color = 'grey';
    if (ratio<25) {
        color = colorArr[1];
    } else if (ratio < 50) {
        color = colorArr[2];
    } else if (ratio < 75) {
        color = colorArr[3];
    } else{
        color = colorArr[0];
    }
    return color;
}
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
};
let GCONFIG = {
	pic_path : '/glserver/static/pic/',
	bg_img: 'bg.jpg',
    cab_outer_height:230,
    side_cab: -440,
    cab_outer_width: 72,
    cab_inner_height:210,
    cab_inner_width: 70,
    cab_inner_length: 70,
    u_height: 5,
    u_count: 42,
    roam:false,
    conn:false,
    connList:[],
    temp:false,
    air:false,
    smoke:false,
    cab_usage:false,
    cab_sel:false,
    dyn_data:false,
    help: false,
    power: false,
    powerList:[],
    powerMatList:[],
    areaMap: new Map(),
    sysMap: new Map()
};
function D3DLib(fId, clearColor)
{
	this.fId = fId;
	this.clearColor = clearColor;
	this.width = window.innerWidth;
	this.height = window.innerHeight;
	this.objects = [];
	this.cabList = [];
	this.cabInfoMap = new Map();
	this.cabInfoSpriteList = [];
	this.dynDataSpriteMap = new Map();
	this.LAST_MESH = null;
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
    D3DOBJ.cabInfoSpriteList = [];
    D3DOBJ.cabInfoMap.clear();
    D3DOBJ.tplList.clear();
    D3DOBJ.scene.children = [];
    D3DOBJ.initLight();
    D3DOBJ.initBgImg();
    this.updateFromDB();
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
let toFalg = false;
let toId = 0;
D3DLib.prototype.onDocumentMouseDown = function(event){
	if(D3DOBJ.controls.autoRotate === true){
		D3DOBJ.controls.autoRotate = false;
	}
	
	D3DOBJ['mousePos']['x'] = event['clientX'];
    D3DOBJ['mousePos']['y'] = event['clientY'];
    let viewPt = new THREE.Vector2();
    viewPt.x = (event['clientX'] / D3DOBJ['width']) * 2 - 1;
	viewPt.y = -(event['clientY'] / D3DOBJ['height']) * 2 + 1;
	D3DOBJ['raycaster']['setFromCamera'](viewPt, D3DOBJ['camera']);
	dbclick++;
	if (!toFalg) {
        toId = setTimeout(function () {
            if( event['target']['nodeName'] === 'CANVAS'){
                if(event.button === 2){
                    evtname = 'rclick';
                } else {
                    if (dbclick >= 2) {
                        evtname = 'dbclick';
                    } else if(dbclick === 1) {
                        evtname = 'click';
                    }
                }
            }
            dbclick = 0;
            clearTimeout(toId);
            console.log(evtname);
            D3DOBJ['onEventDeal'](evtname, event)
        }, 200);
    }

	event['preventDefault']();
};
D3DLib.prototype.onEventDeal = function(evtname, event){
	//console.log('pos1 1event:'+evtname);
	let eventObj = event;
	if (evtname === '') {
	    return;
	}
	let intersectObjects = D3DOBJ['raycaster']['intersectObjects'](D3DOBJ.scene.children, true);
	if (intersectObjects['length'] > 0) {
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

		// 处理网线选中颜色
        if (evtname === 'click' || evtname ==='rclick') {
            // let SEL_MESH = D3DOBJ['SELECTED'];
            // if(SEL_MESH !== D3DOBJ.lastSelCable) {
            //
            // } else {
            //
            // }
            // if (SEL_MESH.hasOwnProperty('name') && SEL_MESH['name'].indexOf('cable_') === 0) {
            //     // 如果当前网线不是上次网线，那么更新上次网线 且上次网线颜色回复
            // } else {
            //
            // }
        }
		
	    D3DOBJ['controls']['enabled'] = true;
	}
	
};
D3DLib.prototype.onDocumentMouseMove = function(event){
    let viewPt = new THREE.Vector2();
    viewPt.x = (event['clientX'] / D3DOBJ['width']) * 2 - 1;
    viewPt.y = -(event['clientY'] / D3DOBJ['height']) * 2 + 1;
    D3DOBJ['raycaster']['setFromCamera'](viewPt, D3DOBJ['camera']);

    let intersectObjects = D3DOBJ['raycaster']['intersectObjects'](D3DOBJ.scene.children, true);
    if (intersectObjects['length'] > 0) {
        D3DOBJ['SELECTED'] = intersectObjects[0]['object'];
        let SEL_MESH = D3DOBJ['SELECTED'];
        if (SEL_MESH instanceof THREE.Mesh &&
            SEL_MESH.hasOwnProperty('name') &&
            SEL_MESH['name'] !== '' ){
            // 当前物体非网线和机柜
            if (SEL_MESH['name'].indexOf('cable_') !== 0 &&
                SEL_MESH['name'].indexOf('power_') !== 0 &&
                SEL_MESH['name'].indexOf('cab_') !== 0) {
                let bNeedCreate = true;
                if (D3DOBJ['boxHelper'] !== null &&
                    D3DOBJ['boxHelper'] !== undefined &&
                    D3DOBJ['boxHelper'].hasOwnProperty('object')) {//boxHelper已存在
                    if (D3DOBJ['boxHelper']['object'] !== D3DOBJ['SELECTED']) {//boxHelper辅助对象已变更
                        D3DOBJ.rmObject(D3DOBJ['boxHelper']);
                    }
                    else {
                        bNeedCreate = false;
                    }
                }

                if (bNeedCreate) {//boxHelper不存在
                    let boxHelper = new THREE.BoxHelper(D3DOBJ['SELECTED'], 0xffff00);
                    D3DOBJ['boxHelper'] = boxHelper;
                    D3DOBJ.addObject(boxHelper);
                }
            }

            // 上次是mesh 这次是mesh
            // 上次是mesh 这次是cable
            // 上次是cable 这次是mesh
            // 上次是cable 这次是cable

            if(SEL_MESH !== D3DOBJ.LAST_MESH){
                // 如果上次选中的是网线
                let LAST_MESH = D3DOBJ.LAST_MESH;
                if (LAST_MESH !== null && LAST_MESH.hasOwnProperty('name') && LAST_MESH['name'].indexOf('cable_')===0) {
                    [LAST_MESH['material']['color'], LAST_MESH['userData']['color']] = [LAST_MESH['userData']['color'],LAST_MESH['material']['color']];
                }
                if (SEL_MESH !== null && SEL_MESH.hasOwnProperty('name') && SEL_MESH['name'].indexOf('cable_')===0) {
                    [SEL_MESH['material']['color'], SEL_MESH['userData']['color']] = [SEL_MESH['userData']['color'],SEL_MESH['material']['color']];
                }

                if (LAST_MESH !== null && LAST_MESH.hasOwnProperty('name') && LAST_MESH['name'].indexOf('power_')===0) {
                    [LAST_MESH['material']['color'], LAST_MESH['userData']['color']] = [LAST_MESH['userData']['color'],LAST_MESH['material']['color']];
                }
                if (SEL_MESH !== null && SEL_MESH.hasOwnProperty('name') && SEL_MESH['name'].indexOf('power_')===0) {
                    [SEL_MESH['material']['color'], SEL_MESH['userData']['color']] = [SEL_MESH['userData']['color'],SEL_MESH['material']['color']];
                }
                D3DOBJ.LAST_MESH = SEL_MESH;
            }
        }
    }
    event['preventDefault']();
};
D3DLib.prototype.initMouseCtrl = function () {
    this.controls = new THREE.OrbitControls(this.camera);
    this.controls.maxPolarAngle = Math.PI * 0.48;
    this.controls.minDistance=100;
    this.controls.maxDistance = 3000;
    this.controls.zoomSpeed = 2;
    //this.controls.addEventListener('change', this.updateControls);
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
        let pointArr = [
            {x: -550, y: 120, z: -480},
            {x: -550, y: 120, z: 310},
            {x: 220, y: 120, z: 310},
            {x: 220,y: 120,z: -480},
            {x: -550,y: 120,z: -480}];
        D3DOBJ['dynamicPath'](pathConfig, pointArr)
    } else {
        D3DOBJ['stopDynamicPath'](pathName)
    }
    GCONFIG['roam'] = !GCONFIG['roam']
};
D3DLib.prototype.connection = function(){
    // 关闭电缆供应
    if (GCONFIG['power']) {
        D3DOBJ.power();
    }
    if (!GCONFIG['conn']) {
        $.ajax({
            url: '/glserver/eth/get_all_eth',
            dataType: 'json',
            data: null,
            success: function (datas) {
                GCONFIG['connList'] = [];
                let fmPt = new THREE.Vector3(0,0,0);
                let toPt = new THREE.Vector3(0,0,0);
                for(let i=0; i<datas.length; ++i){
                    let data = datas[i];
                    let fmEthName = data['code'];
                    let toEthName = data['peerCode'];

                    if (fmEthName.length !== 11 || toEthName.length !== 11){
                        console.log('fmEthName: ' + fmEthName + ', toEthName:' + toEthName);
                        continue;
                    }
                    let connName = null;
                    if(fmEthName < toEthName){
                        connName = 'cable_' + fmEthName + '_' + toEthName;
                    } else {
                        connName = 'cable_' + toEthName + '_' + fmEthName;
                    }
                    if(D3DOBJ.hasObject(connName)){
                        continue;
                    }

                    // 解析源点
                    let fmEthRowCount = data['fmEthRowCount'] || 1;
                    let fmEthColCount = data['fmEthColCount'] || 10;
                    let fmCabRow = fmEthName.substr(1,1);
                    let fmCabId = fmEthName.substr(1,2);
                    let fmDevCode = fmEthName.substr(0,7);
                    let fmStartU = parseInt(fmEthName.substr(3,2));
                    let fmUsedU  = parseInt(fmEthName.substr(5,2));
                    let fmEthRow = parseInt(fmEthName.substr(7,2));
                    let fmEthCol = parseInt(fmEthName.substr(9,2));
                    let fmCabName = 'cab_' + fmCabId;
                    let fmCabMesh = D3DOBJ.scene.getObjectByName(fmCabName);
                    if(fmCabMesh === null || fmCabMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    let fmDevName = 'dev_' + fmDevCode;
                    let fmDevMesh = D3DOBJ.scene.getObjectByName(fmDevName);
                    if(fmDevMesh === null || fmDevMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    // cab位置
                    fmPt.x = fmCabMesh.position.x + fmDevMesh.position.x;
                    fmPt.y = fmCabMesh.position.y + fmDevMesh.position.y;
                    fmPt.z = fmCabMesh.position.z + fmDevMesh.position.z;
                    // x轴移至后门处
                    fmPt.x += GCONFIG['cab_inner_length']/2 - 40;
                    // y轴坐标
                    let devHeight = fmUsedU * GCONFIG['u_height'];
                    fmPt.y -= devHeight / 2.0;
                    fmPt.y += devHeight * (fmEthRowCount+1-fmEthRow) /(fmEthRowCount+1);
                    // z轴坐标
                    let fmColLen =  GCONFIG['cab_inner_width'] * 1.0 / (fmEthColCount+1);
                    let fmZSpan = fmColLen * fmEthCol;
                    fmPt.z +=  fmZSpan - GCONFIG['cab_inner_width'] / 2 ;

                    // 解析目的点
                    let toEthRowCount = data['toEthRowCount'] || 1;
                    let toEthColCount = data['toEthColCount'] || 10;
                    let toCabRow = toEthName.substr(1,1);
                    let toCabId = toEthName.substr(1,2);
                    let toDevCode = toEthName.substr(0,7);
                    let toStartU = parseInt(toEthName.substr(3,2));
                    let toUsedU  = parseInt(toEthName.substr(5,2));
                    let toEthRow = parseInt(toEthName.substr(7,2));;
                    let toEthCol = parseInt(toEthName.substr(9,2));;
                    let toCabName = 'cab_' + toCabId;
                    let toCabMesh = D3DOBJ.scene.getObjectByName(toCabName);
                    if(toCabMesh === null || toCabMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    let toDevName = 'dev_' + toDevCode;
                    let toDevMesh = D3DOBJ.scene.getObjectByName(toDevName);
                    if(toDevMesh === null || toDevMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    // cab位置
                    toPt.x = toCabMesh.position.x + toDevMesh.position.x;
                    toPt.y = toCabMesh.position.y + toDevMesh.position.y;
                    toPt.z = toCabMesh.position.z + toDevMesh.position.z;
                    // x轴移至后门处
                    toPt.x += GCONFIG['cab_inner_length']/2 -40;
                    // y轴坐标
                    let toDevHeight = toUsedU * GCONFIG['u_height'];
                    toPt.y -= toDevHeight / 2.0;
                    toPt.y += toDevHeight * (toEthRowCount+1-toEthRow) /(toEthRowCount+1);
                    // z轴坐标
                    let toColLen =  GCONFIG['cab_inner_width'] * 1.0 / (toEthColCount+1);
                    let toZSpan = toColLen * toEthCol;
                    toPt.z += toZSpan - GCONFIG['cab_inner_width'] / 2 ;

                    // 根据fmPt和toPt确定路线点
                    // connType 1：不同行 2：同行不同柜 3：同柜不同设备 4：同一设备
                    let connType = 1;
                    if(fmCabRow !== toCabRow) {
                        connType = 1;
                    } else if (fmCabId !== toCabId){
                        connType = 2;
                    } else if (fmStartU !== toStartU) {
                        connType = 3;
                    } else{
                        connType = 4;
                    }
                    let params = {
                        'fmZSpan': fmZSpan,
                        'toZSpan': toZSpan,
                        'fmEthRow': fmEthRow,
                        'fmEthCol': fmEthCol,
                        'toEthRow': toEthRow,
                        'toEthCol': toEthCol,
                    }
                    D3DOBJ.createPath(connName, connType, fmPt, toPt, params);
                }
            },
            error: function (data) {
                console.log("update eth from db  error")
            },
        });
    } else {
        for (let i=0; i<GCONFIG['connList'].length; ++i){
            let pathName = GCONFIG['connList'][i];
            D3DOBJ.delObject(pathName);
        }
        //D3DOBJ.delObject(pathName);
    };
    GCONFIG['conn'] = !GCONFIG['conn']
};

D3DLib.prototype.createPath = function(pathName,pathType, fmPt, toPt, params) {
    let r=Math.floor(Math.random()*256);
    let g=Math.floor(Math.random()*256);
    let b=Math.floor(Math.random()*256);
    let pathConfig = {
        name: pathName,
        type: 'beeline',
        visible: true,
        color: colorArr[(params['fmEthRow'] + params['fmEthCol']-2)%5],
        radiu: 0.7,
        scene: true
    };
    let pathPointArr = [];
    pathPointArr.push(fmPt.clone());

    let fmRowSpan = (params['fmEthRow']-1)*2;
    let fmColSpan = params['fmEthCol'];
    let toRowSpan = (params['toEthRow']-1)*2;
    let toColSpan = params['toEthCol'];
    fmPt.x = fmPt.x + 60 + fmColSpan;
    console.log('pathname:'+pathName + 'fmPt.x' + fmPt.x);
    pathPointArr.push(fmPt.clone());
    params['fmZSpan'] -= fmRowSpan;
    switch(pathType){
        case 1://不同行
            //移至当前机柜右侧边
            fmPt.z -= params['fmZSpan'];
            pathPointArr.push(fmPt.clone());
            //移至当前机柜上侧边
            fmPt.y = GCONFIG['cab_outer_height'] + fmRowSpan;
            pathPointArr.push(fmPt.clone());
            //移至行机柜侧边
            fmPt.z = GCONFIG['side_cab'] + fmColSpan;
            pathPointArr.push(fmPt.clone());
            //移至目的行机柜
            fmPt.x = toPt.x + 60 + toColSpan;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z - params['toZSpan'] + toRowSpan;
            pathPointArr.push(fmPt.clone());
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());
            break;
        case 2://同行不同柜
            fmPt.z -= params['fmZSpan'];
            pathPointArr.push(fmPt.clone());
            fmPt.y = GCONFIG['cab_outer_height'] + fmRowSpan;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z - params['toZSpan'] - toRowSpan;
            pathPointArr.push(fmPt.clone());
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());
            break;
        case 3://同柜不同设备
            fmPt.z -= params['fmZSpan'];
            pathPointArr.push(fmPt.clone());
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());
            break;
        case 4://相同设备
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());

            break;
    }
    pathPointArr.push(toPt);
    let mesh = D3DOBJ.addTunnel(pathConfig, pathPointArr);
    mesh['userData']['color'] = new THREE.Color(1,1,1);
    if(mesh !== undefined && mesh !== null){
        GCONFIG['connList'].push(pathName);
    }
}
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
                y: 230,
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
                y: 230,
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
D3DLib.prototype.cab_usage = function() {
    if (!GCONFIG['cab_usage']){
        if (D3DOBJ.cabInfoMap.size === 0) {
            D3DOBJ.calcCabInfo();
        }
        // 遍历每个机柜
        // 根据cabInfo生成Sprite
        for(let i=0; i<D3DOBJ.cabList.length; ++i) {
            let cab = D3DOBJ.cabList[i];
            let pos = cab.position;
            if (!D3DOBJ.cabInfoMap.has(cab['userData']['refCode'])){
                continue;
            }
            let cabInfo = D3DOBJ.cabInfoMap.get(cab['userData']['refCode']);
            let config = {};
            config['name'] = 'sprite_'+cab['name'];
            config['font'] = '18px FangSong';
            config['background'] = getColorByRatio(cabInfo['usageRatio']);
            config['textColor'] = 'white';
            config['width'] = 240;
            config['height'] = 280;
            config['x'] = cab['position']['x'];
            config['y'] = GCONFIG['cab_inner_height'] + config['height'] / 8;
            config['z'] = cab['position']['z'];
            let msgArr = [];
            msgArr.push('机柜名:'+cab['name'].substr(4,2));
            msgArr.push('所属分区:'+ cabInfo['areaName']);
            msgArr.push('所属系统:'+ cabInfo['systemName']);
            msgArr.push('总U数:'+ cabInfo['totalU']);
            msgArr.push('已用U数:'+cabInfo['usedU']);
            msgArr.push('剩余U数:'+cabInfo['remainU']);
            msgArr.push('总容量:'+cabInfo['totalCap']);
            msgArr.push('已用容量:'+cabInfo['usedCap']);
            msgArr.push('剩余容量:'+cabInfo['remainCap']);
            msgArr.push('使用率:'+cabInfo['usageRatio']+"%");
            config['msgArr'] = msgArr;
            let sprite = D3DOBJ.makeTextSpriteEx(config);
            sprite['userData']['refCode'] = cab['userData']['refCode'];
            // 判断柜子是否visible
            sprite.visible = cab.visible;
            D3DOBJ.cabInfoSpriteList.push(sprite);
        }
    } else {
        for (let i=0; i<D3DOBJ['cabInfoSpriteList'].length; ++i){
            let sprite = D3DOBJ['cabInfoSpriteList'][i];
            D3DOBJ.rmObject(sprite);
        }
        D3DOBJ['cabInfoSpriteList']=[];
    }
    GCONFIG['cab_usage'] = !GCONFIG['cab_usage'];
};
D3DLib.prototype.calcCabInfo = function() {
    // function parseCabData(data) {
    //使用data生成Map
    //遍历每个机柜
    for(let i=0; i<D3DOBJ.cabList.length; ++i) {
        //获取出当前机柜总容量
        let cab = D3DOBJ.cabList[i];
        let cabInfo = {};
        cabInfo['totalCap'] = 2000;
        cabInfo['usedCap'] = 0;
        cabInfo['remainCap'] = 2000;
        cabInfo['totalU'] = 42;
        cabInfo['usedU'] = 0;
        cabInfo['remainU'] = 42;
        cabInfo['refCode'] = cab['userData']['refCode'];
        let used_u_count = 0;
        let used_cap_count = 0;
        let arrTmp = [];
        for (let j=0; j<cab.children.length; ++j) {
            let dev = cab.children[j];

            if (dev['name'].indexOf('dev_') === 0) {
                //截取编码
                let code = dev['name'].substr(4);
                let usedU = parseInt(code.substr(5,2));
                cabInfo['usedU'] += usedU;
                let devCap = 200;
                if(dev.hasOwnProperty('userData') && dev['userData'].hasOwnProperty('capacity')){
                    devCap = dev['userData']['capacity'];
                }
                cabInfo['usedCap'] += devCap;
                arrTmp.push(code);
            }
        }
        let max_cons_u = 0;
        if (arrTmp.length>0) {
            arrTmp.sort();
            // 筛选出最大连续U
            for(let k=0; k<arrTmp.length; ++k) {
                let last = "0100";
                if (k!==0) {
                    last = arrTmp[k-1].substr(3);
                }
                let curr = arrTmp[k].substr(3);
                let lastStartU = parseInt(last.substr(0,2));
                let lastUsedU = parseInt(last.substr(2,2));
                let currStartU = parseInt(curr.substr(0,2));
                if (lastStartU + lastUsedU > currStartU) {
                    console.log("lastStartU + lastUsedU >= currStartU error currDev:"+arrTmp[k]);
                } else {
                    let spanU = currStartU - (lastStartU + lastUsedU);
                    if(spanU > max_cons_u) {
                        max_cons_u = spanU;
                    }
                }
            }
        } else {
            max_cons_u = 42;
        }
        cabInfo['maxConsU'] = max_cons_u;
        cabInfo['remainCap'] = cabInfo['totalCap'] - cabInfo['usedCap'];
        cabInfo['remainU'] = cabInfo['totalU'] - cabInfo['usedU'];
        cabInfo['usageRatio'] = Math.round(cabInfo['usedU'] * 100.0 / cabInfo['totalU']);
        cabInfo['areaId'] = 0;
        cabInfo['systemId'] = 0;
        cabInfo['areaName'] = '所属分区未分配';
        cabInfo['systemName'] = '所属系统未分配';
        D3DOBJ.cabInfoMap.set(cab['userData']['refCode'], cabInfo);
    }
    // }
    let cabAffiMap = new Map();
    //获取机柜的信息
    $.ajax({
        url: '/glserver/clone/get_all_cab',
        dataType: 'json',
        data: null,
        async: false,
        success: function (datas) {
            for(let i=0; i<datas.length; ++i) {
                let data = datas[i];
                if (D3DOBJ.cabInfoMap.has(data['code'])) {
                    let cabInfo = D3DOBJ.cabInfoMap.get(data['code']);
                    cabInfo['areaId'] = data['areaId'];
                    cabInfo['systemId'] = data['systemId'];
                    cabInfo['areaName'] = data['areaName'];
                    cabInfo['systemName'] = data['systemName'];
                }
            }
        },
        error: function (data) {
            console.log("update cab from db  error")
        },
    });
};
D3DLib.prototype.cab_sel_box = function() {
    if (!GCONFIG['cab_sel'])
    {
        if (GCONFIG['areaMap'].size === 0) {
            // 请求area
            $.ajax({
                url:'/glserver/clone/get_all_area',
                dataType:'json',
                data: null,
                async: false,
                success:function(datas){
                    for (let i=0; i<datas.length; ++i) {
                        let data = datas[i];
                        GCONFIG['areaMap'].set(data['id'], data);
                    }
                },
                error: function(data) {
                    console.log("load area from db error");
                }
            });
        }
        if (GCONFIG['sysMap'].size === 0) {
            // 请求sys
            $.ajax({
                url:'/glserver/clone/get_all_sys',
                dataType:'json',
                data: null,
                async: false,
                success:function(datas){
                    for (let i=0; i<datas.length; ++i) {
                        let data = datas[i];
                        GCONFIG['sysMap'].set(data['id'], data);
                    }
                },
                error: function(data) {
                    console.log("load sys from db error");
                }
            });
        }
        //div框
        let eleMain = $("<form id='cab_sel_box'></form>");
        eleMain.css({
            'position':'absolute',
            'top':60,
            'right':20,
            'width':300,
            'padding':30,
            'background':'#5993d1',
            'border-radius':20,
            'opacity': 0.9
        });

        // 标题栏
        let eleTitle = $("<div class='form-group'><h4 class='text-center'>推荐机柜</h4></div>");
        eleMain.append(eleTitle);
        // 设备所属区域
        let eleArea = $('<div class="form-group"></div>');
        let eleAreaSel = $('<select id="cab_sel_box_area" class="custom-select" name="sel_area"></select>');
        eleAreaSel.append($('<option value="0">请选择设备所属区域</option>'));
        GCONFIG['areaMap'].forEach(function (val, key, map) {
            eleAreaSel.append($('<option value='+ val['id'] +'>' + val['name'] + '</option>'));
        });
        eleArea.append(eleAreaSel);
        eleMain.append(eleArea);
        // 设备所属系统
        let eleSys = $('<div class="form-group"></div>');
        let eleSysSel = $('<select id="cab_sel_box_sys" class="custom-select" name="sel_sys"></select>');
        eleSysSel.append($('<option value="0">请选择设备所属系统</option>'));
        eleSys.append(eleSysSel);
        eleMain.append(eleSys);
        // U数
        let eleU = $(
            '<div class="form-group"><div class="input-group">' +
                '<input type="text" id="cab_sel_box_u" class="form-control" placeholder="请输入占用U数" name="text_u">' +
                '<div class="input-group-append">' +
                    '<span class="input-group-text">U</span>' +
                '</div>' +
            '</div></div>');
        eleMain.append(eleU);
        // 容量
        let eleCap = $(
            '<div class="form-group"><div class="input-group">' +
                '<input type="text" id="cab_sel_box_cap" class="form-control" placeholder="请输入额定容量" name="text_cap">' +
                '<div class="input-group-append">' +
                    '<span class="input-group-text">W</span>' +
                '</div>' +
            '</div></div>');
        eleMain.append(eleCap);
        // 查找按键
        let eleBtnFind = $(
            '<div class="form-group">' +
                '<button type="button" id="cab_sel_box_find" class="btn btn-primary">查找机柜</button>&nbsp;&nbsp' +
                '<button type="button" class="btn btn-primary" onclick="D3DOBJ.cab_sel_box()">关闭对话框</button>' +
            '</div>'
        );
        eleMain.append(eleBtnFind);
        // 推荐机柜
        let eleTextArea = $(
            '<div id="cab_box_res_grp_res" class="form-group" style="display:none;">' +
                '<label for="basic-url">推荐上架机柜（仅前3）</label>' +
                '<textarea class="form-control" id="cab_sel_box_res" id="tarea1" rows="3"></textarea>' +
            '</div>'
        );
        eleMain.append(eleTextArea);
        // 关闭对话框
        // let eleBtnClose = $(
        //     '<div class="form-group">' +
        //         '' +
        //     '</div>'
        // );
        //eleMain.append(eleBtnClose);
        $('body').append(eleMain);

        // 添加验证事件
        $('#cab_sel_box').bootstrapValidator({
            message: 'This value is not valid',
            excluded : [':disabled'],
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                text_u: {
                    message: '设备占用U数 格式不正确',
                    validators: {
                        notEmpty: {
                            message: '设备占用U数不能为空'
                        },
                        digits: {
                            message: '请填写数字'
                        },
                        between:{
                            min: 1,
                            max: 42,
                            message:'占用U数应大于0小于42'
                        }
                    }
                },
                text_cap: {
                    message: '设备容量 格式不正确',
                    validators: {
                        notEmpty: {
                            message: '设备容量不能为空'
                        },
                        digits:{
                            message: '请填写数字'
                        },
                        between:{
                            min: 1,
                            max: 2000,
                            message:'容量大于1W小于2000W'
                        }
                    }
                },
                sel_area: {
                    message: '所属区域 格式不正确',
                    validators: {
                        notEmpty: {
                            message: '所属区域不能为空'
                        },
                        callback: {
                            message: '请选择所属区域',
                            callback: function(value, validator) {
                                if (value === "0") {
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                        }
                    }
                },
                sel_sys: {
                    message: '所属系统 格式不正确',
                    validators: {
                        notEmpty: {
                            message: '所属系统不能为空'
                        },
                        callback: {
                            message: '请选择所属系统',
                            callback: function(value, validator) {
                                if (value === "0") {
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                        }
                    }
                }
            }
        });
        D3DOBJ.controls.enabled = false;
    } else {
        D3DOBJ.closeDiv('cab_sel_box');
    }
    GCONFIG['cab_sel'] = !GCONFIG['cab_sel'];
};
D3DLib.prototype.help = function(){
    if (!GCONFIG['help']){
        $('#help').show();
    } else {
        $('#help').hide();
    }
    GCONFIG['help'] = !GCONFIG['help'];
};
D3DLib.prototype.power = function(){
    if (GCONFIG['conn']) {
        D3DOBJ.connection();
    }
    if (!GCONFIG['power']){
        $.ajax({
            url: '/glserver/clone/get_all_power',
            dataType: 'json',
            data: null,
            success: function (datas) {
                GCONFIG['powerMatList'] = [];
                let fmPt = new THREE.Vector3(0,0,0);
                let toPt = new THREE.Vector3(0,0,0);
                for(let i=0; i<datas.length; ++i){
                    let data = datas[i];
                    let fmPowerName = data['from'];
                    let toPowerName = data['to'];

                    if ((fmPowerName.length !== 11 && fmPowerName.length !== 12) ||
                        (toPowerName.length !== 11 && toPowerName.length !== 12)){
                        console.log('fmPowerName: ' + fmPowerName + ', toPowerName:' + toPowerName);
                        continue;
                    }
                    let connName = null;
                    //connName = 'power_' + fmPowerName + '_' + toPowerName;
                    connName = 'power_' + data['name'];
                    if(D3DOBJ.hasObject(connName)){
                        continue;
                    }
                    // 解析源点
                    let fmPowerRowCount = data['fmRowCount'] || 1;
                    let fmPowerColCount = data['fmColCount'] || 10;
                    let fmCabRow = fmPowerName.substr(1,1);
                    let fmCabId = fmPowerName.substr(1,2);
                    let fmDevCode = fmPowerName.substr(0,7);
                    let fmStartU = parseInt(fmPowerName.substr(3,2));
                    let fmUsedU  = parseInt(fmPowerName.substr(5,2));
                    let fmPowerRow = parseInt(fmPowerName.substr(7,2));
                    let fmPowerCol = parseInt(fmPowerName.substr(9,2));
                    let fmOri = 1;
                    if ( fmPowerName.length === 12 ) {
                        let ori = fmPowerName.substr(11,1);
                        if (ori === 'F'){
                            fmOri = -1;
                        }
                    }

                    let fmCabName = 'cab_' + fmCabId;
                    let fmCabMesh = D3DOBJ.scene.getObjectByName(fmCabName);
                    if(fmCabMesh === undefined || fmCabMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    let fmDevName = 'dev_' + fmDevCode;
                    let fmDevMesh = D3DOBJ.scene.getObjectByName(fmDevName);
                    if(fmDevMesh === undefined || fmDevMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    // dev位置
                    fmPt.x = fmCabMesh.position.x + fmDevMesh.position.x;
                    fmPt.y = fmCabMesh.position.y + fmDevMesh.position.y;
                    fmPt.z = fmCabMesh.position.z + fmDevMesh.position.z;
                    // x轴移至后门处
                    fmPt.x += GCONFIG['cab_inner_length']/2 - 40;
                    // y轴坐标
                    let devHeight = fmUsedU * GCONFIG['u_height'];
                    // y轴置于设备下边
                    fmPt.y -= devHeight / 2.0;
                    fmPt.y += devHeight * (fmPowerRowCount+1-fmPowerRow) /(fmPowerRowCount+1);
                    // z轴坐标 左加 右减
                    // 列宽
                    let fmPerColLen =  GCONFIG['cab_inner_width'] * 1.0 / (fmPowerColCount+1);
                    // 列宽*列数
                    let fmZSpan = fmPerColLen * fmPowerCol;
                    // Z轴归位机柜左边
                    fmPt.z +=  GCONFIG['cab_inner_width'] / 2;
                    // Z轴移到列位置
                    fmPt.z -= fmZSpan ;

                    // 解析目的点
                    let toPowerRowCount = data['toRowCount'] || 2;
                    let toPowerColCount = data['toColCount'] || 10;
                    let toCabRow = toPowerName.substr(1,1);
                    let toCabId = toPowerName.substr(1,2);
                    let toDevCode = toPowerName.substr(0,7);
                    let toStartU = parseInt(toPowerName.substr(3,2));
                    let toUsedU  = parseInt(toPowerName.substr(5,2));
                    let toPowerRow = parseInt(toPowerName.substr(7,2));
                    let toPowerCol = parseInt(toPowerName.substr(9,2));
                    let toOri = 1;
                    if ( toPowerName.length === 12 ) {
                        let ori = toPowerName.substr(11,1);
                        if (ori === 'F') {
                            toOri = -1;
                        }
                    }

                    let toCabName = 'cab_' + toCabId;
                    let toCabMesh = D3DOBJ.scene.getObjectByName(toCabName);
                    if(toCabMesh === undefined || toCabMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    let toDevName = 'dev_' + toDevCode;
                    let toDevMesh = D3DOBJ.scene.getObjectByName(toDevName);
                    if(toDevMesh === undefined || toDevMesh.hasOwnProperty('position')===false){
                        continue;
                    }
                    // cab位置
                    toPt.x = toCabMesh.position.x + toDevMesh.position.x;
                    toPt.y = toCabMesh.position.y + toDevMesh.position.y;
                    toPt.z = toCabMesh.position.z + toDevMesh.position.z;
                    // x轴移至后门处
                    toPt.x += GCONFIG['cab_inner_length']/2 -40;
                    // y轴坐标
                    let toDevHeight = toUsedU * GCONFIG['u_height'];
                    toPt.y -= toDevHeight / 2.0;
                    toPt.y += toDevHeight * (toPowerRowCount+1-toPowerRow) /(toPowerRowCount+1);
                    // z轴坐标
                    let toPerColLen =  GCONFIG['cab_inner_width'] * 1.0 / (toPowerColCount+1);
                    let toZSpan = toPerColLen * toPowerCol;
                    toPt.z += GCONFIG['cab_inner_width'] / 2 -toZSpan;

                    // 根据fmPt和toPt确定路线点
                    // connType 1：不同行 2：同行不同柜 3：同柜不同设备 4：同一设备
                    let connType = 1;
                    if(fmCabRow !== toCabRow) {
                        connType = 1;
                    } else if (fmCabId !== toCabId){
                        connType = 2;
                    } else if (fmStartU !== toStartU) {
                        connType = 3;
                    } else{
                        connType = 4;
                    }
                    let fmCabNum = 1;
                    switch (fmCabRow) {
                        case 'A': fmCabNum = 1; break;
                        case 'B': fmCabNum = 2; break;
                        case 'C': fmCabNum = 3; break;
                        case 'D': fmCabNum = 4; break;
                        case 'E': fmCabNum = 5; break;
                        case 'F': fmCabNum = 6; break;
                    }

                    let params = {
                        'fmZSpan': fmZSpan,
                        'toZSpan': toZSpan,
                        'fmPowerRow': fmPowerRow,
                        'fmPowerCol': fmPowerCol,
                        'toPowerRow': toPowerRow,
                        'toPowerCol': toPowerCol,
                        'fmOri': fmOri,
                        'toOri': toOri,
                        'radius': data['radius'],
                        'fmCabNum': fmCabNum
                    }
                    D3DOBJ.createPower(connName, connType, fmPt, toPt, params);
                }
            },
            error: function (data) {
                console.log("update power line from db  error")
            },
        });
    } else {
        for (let i=0; i<GCONFIG['powerList'].length; ++i){
            let power = GCONFIG['powerList'][i];
            D3DOBJ.delObject(power);
        }
    }
    GCONFIG['power'] = !GCONFIG['power'];
};
D3DLib.prototype.createPower = function(pathName, pathType, fmPt, toPt, params) {
    // let r=Math.floor(Math.random()*256);
    // let g=Math.floor(Math.random()*256);
    // let b=Math.floor(Math.random()*256);
    let imgurl = '/glserver/static/pic/UV_Red.png';
    if (pathName.indexOf('F9') >= 0 ) {
        imgurl = '/glserver/static/pic/UV_Green.png';
    }
    let pathConfig = {
        name: pathName,
        type: 'beeline',
        visible: true,
        //color: colorArr[(params['fmEthRow'] + params['fmEthCol']-2)%5],
        radiu: params['radius'],
        scene: true,
        imgurl: imgurl,
    };
    let pathPointArr = [];
    pathPointArr.push(fmPt.clone());

    //let fmRowSpan = (params['fmEthRow']-1)*2;
    let fmColSpan = params['fmPowerCol']*2;
    //let toRowSpan = (params['toEthRow']-1)*2;
    let toColSpan = params['toPowerCol']*2;
    //x外推
    fmPt.x = fmPt.x + params['fmOri']*70 + fmColSpan;
    pathPointArr.push(fmPt.clone());
    //params['fmZSpan'] -= fmRowSpan;
    switch(pathType){
        case 1://不同行
            // 移至机柜边
            fmPt.z += params['fmZSpan'];
            pathPointArr.push(fmPt.clone());
            // 升高
            fmPt.y = GCONFIG['cab_outer_height'] + 2*params['fmCabNum'];
            pathPointArr.push(fmPt.clone());
            // 移至行柜边
            fmPt.z = 210 + fmColSpan;
            pathPointArr.push(fmPt.clone());
            // 移至目的行柜
            fmPt.x = toPt.x + params['toOri']*(toColSpan+50);
            pathPointArr.push(fmPt.clone());
            // 移至机柜
            fmPt.z = toPt.z + params['toZSpan'] ;
            pathPointArr.push(fmPt.clone());
            // 降高
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            // 移到对应列
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());
            break;
        case 2://同行不同柜
            fmPt.z += params['fmZSpan'];
            pathPointArr.push(fmPt.clone());
            fmPt.y = GCONFIG['cab_outer_height'];
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z + params['toZSpan'];
            pathPointArr.push(fmPt.clone());
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());
            break;
        case 3://同柜不同设备
            fmPt.z += params['fmZSpan'];
            pathPointArr.push(fmPt.clone());
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());
            break;
        case 4://相同设备
            fmPt.y = toPt.y;
            pathPointArr.push(fmPt.clone());
            fmPt.z = toPt.z;
            pathPointArr.push(fmPt.clone());

            break;
    }
    pathPointArr.push(toPt);
    let mesh = D3DOBJ.addTunnel(pathConfig, pathPointArr);
    mesh['userData']['color'] = new THREE.Color(1,1,1);
    if(mesh !== undefined && mesh !== null){
        GCONFIG['powerList'].push(pathName);
    }
};
let dyn_data_int = null;
//width
//height
//background
//font
//textColor
//msgArr
//x-y-z
//parent
//name
D3DLib.prototype.parseDynData_Air = function(datas, config) {
    for (let key in datas) {
        config['parent'] = 'aircondition' + key;
        config['name'] = config['parent'] +'-dyn_data';
        config['msgArr'] = [];
        let csArr = datas[key];
        for (let i=0; i<csArr.length; ++i) {
            let bj = csArr[i];
            config['msgArr'].push(CsMap_AIR[bj['bjcs']] + ':' + bj['val'].toFixed(2));
        }
        let mapKey = pad(BJLX_AIR, 3) + pad(key, 3);
        if (D3DOBJ.dynDataSpriteMap.has(mapKey)){
            // 当前设备已生成过
            let sprite = D3DOBJ.dynDataSpriteMap.get(mapKey);
            D3DOBJ.rmObject(sprite, D3DOBJ.findObject(sprite['userData']['parent']));

            //let canvas = sprite.material.map.image;
            //D3DOBJ.drawOnCanvas(canvas, config);
        }
        //else {
            // 当前设备未生成过
            let sprite = D3DOBJ.makeTextSpriteEx(config);
            sprite['userData']['parent'] = config['parent'];
            D3DOBJ.dynDataSpriteMap.set(mapKey, sprite);
        //}
    }
};
D3DLib.prototype.parseDynData_Fire = function(datas, config) {
    for (let key in datas) {
        config['parent'] = 'firedevice' + key;
        config['name'] = config['parent'] +'-dyn_data';
        config['msgArr'] = [];
        let csArr = datas[key];
        for (let i=0; i<csArr.length; ++i) {
            let bj = csArr[i];
            config['msgArr'].push(CsMap_FIRE[bj['bjcs']] + ':' + bj['val'].toFixed(2));
        }
        let mapKey = pad(BJLX_FIRE, 3) + pad(key, 3);
        if (D3DOBJ.dynDataSpriteMap.has(mapKey)){
            // 当前设备已生成过
            let sprite = D3DOBJ.dynDataSpriteMap.get(mapKey);
            D3DOBJ.rmObject(sprite, D3DOBJ.findObject(sprite['userData']['parent']));

            //let canvas = sprite.material.map.image;
            //D3DOBJ.drawOnCanvas(canvas, config);
        }
        //else {
        // 当前设备未生成过
        let sprite = D3DOBJ.makeTextSpriteEx(config);
        sprite['userData']['parent'] = config['parent'];
        D3DOBJ.dynDataSpriteMap.set(mapKey, sprite);
        //}
    }
};
D3DLib.prototype.parseDynData_Server = function(arr) {

};
D3DLib.prototype.parseDynData_Ups = function(arr) {

};
D3DLib.prototype.dyn_data = function() {
    if (!GCONFIG['dyn_data']) {
        let config = {};
        config['font'] = '28px FangSong';
        config['background'] = 'orange';
        config['textColor'] = 'blue';
        config['width'] = 240;
        config['height'] = 280;
        config['x'] = 0;
        config['y'] = 130;
        config['z'] = 0;
        config['msgArr'] = [];
        dyn_data_int = setInterval(function(){
            $.ajax({
                url: '/glserver/rt/get_rt_data',
                dataType: 'json',
                data: null,
                success: function (datas) {
                    if (datas.hasOwnProperty(BJLX_AIR)) {// 解析空调
                        D3DOBJ.parseDynData_Air(datas[BJLX_AIR], config);
                    }
                    if (datas.hasOwnProperty(BJLX_FIRE)) {//解析灭火器
                        D3DOBJ.parseDynData_Fire(datas[BJLX_FIRE], config);
                    }
                    if (datas.hasOwnProperty(BJLX_SERVER)) {//解析服务器
                        D3DOBJ.parseDynData_Server(datas[BJLX_SERVER], config);
                    }
                    if (datas.hasOwnProperty(BJLX_UPS)) {//解析UPS
                        D3DOBJ.parseDynData_Ups(datas[BJLX_UPS], config);
                    }
                },
                error: function (data) {
                    console.log("update rt data from backend  error")
                },
            });
        }, 2000);
    } else {
        if (dyn_data_int !== null) {
            clearInterval(dyn_data_int);
            dyn_data_int = null;
            D3DOBJ.dynDataSpriteMap.forEach(function(val, key, map){
                D3DOBJ.rmObject(val, D3DOBJ.findObject(val['userData']['parent']));
            });
        }
    }
    GCONFIG['dyn_data'] = !GCONFIG['dyn_data'];
};
//////////////////////////////////////////////////
D3DLib.prototype.updateControls = function () {
};
// Init Axis Helper
D3DLib.prototype.initAxisHelper = function(){
	this.axisHelper = new THREE.AxesHelper( 2000 );
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
    for(let i=0; i<GCONFIG['powerMatList'].length; i++){
           let mat = GCONFIG['powerMatList'][i];
           mat['map']['offset']['x'] -= 0.005;
    }
    if (D3DOBJ['nurbsmaterial'] != null && typeof(D3DOBJ['nurbsmaterial']) !== 'undefined') {
        D3DOBJ['nurbsmaterial']['map']['offset']['y'] -= 0.1;
    }
    if (GCONFIG['roam']===true && D3DOBJ['pathTubeGeometry'] != null && typeof(D3DOBJ['pathTubeGeometry']) !== 'undefined') {
		let scalar = 1.0;
        let yStep = 50;
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
        pathPoint2['x'] += 10;
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
        let devCode = data['code'];
        if (devCode==='' || devCode.length !== 7){
            console.log('device name is not qualified. devName:'+devCode);
            continue;
        }
        let device = D3DOBJ.scene.getObjectByName(devCode);
        if( device !== undefined){
            device.parent.remove(device);
        }

        // 根据Code解析出机柜，起始U
        // 根据template确定clone对象
        let staId = devCode.substr(0,1);
        let cabId = devCode.substr(1,2);
        let startU = parseInt(devCode.substr(3,2));
        let useU = parseInt(devCode.substr(5));
		data['name'] = 'dev_' + devCode;
        data['parent'] = 'cab_' + cabId;
        data['x'] = 0;
        data['y'] = GCONFIG['u_height']*(startU-1 + (useU*1.0/2)) - GCONFIG['cab_inner_height']/2;
        data['z'] = 0;
        data['rot_y'] = 180;

        let mesh = D3DOBJ.cloneObj(data);
        D3DOBJ.addObject(mesh, data['parent']);

        // 生成设备的标签信息
        let devName = data['devName'];
        if(devName.indexOf('插排') > 0 || devName.indexOf('UPS')) {
            continue;
        }
        let devData = {};
        devData['width'] = 300;
        devData['height'] = 200;
        devData['fillColor'] = 'green';
        devData['pic'] = 'device/dev.png';
        devData['textAlign'] = 'left';
        devData['texts'] = [
            {text: data['devName'], color:'black', font: '20px bold',offX:10,offY:110}
        ];
        devData['name'] = 'dlabel_'+data['code'];
        devData['x'] = 23;
        devData['y'] = useU*2.5;
        devData['z'] = 15;
        devData['rot_x'] = 90;
        devData['rot_z'] = 90;
        devData['scl_x'] = 0.1;
        devData['scl_y'] = 0.1;
        devData['parent'] = data['name'];
        D3DOBJ.createLabel(devData);
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
	if(object.name !== undefined && object.name !== null && object.name.indexOf('cab_') === 0){
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
D3DLib.prototype.rmObject = function(obj, parent) {
    if (obj === undefined || obj === null) {
        return;
    }
    if(!(parent instanceof THREE.Mesh)){
        parent = D3DOBJ.scene;
    }
    parent.remove(obj);
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
D3DLib.prototype.hasObject = function(name, parent){
    if (name === undefined || name === null) {
        return;
    }
    if(!(parent instanceof THREE.Mesh)){
        parent = D3DOBJ.scene;
    }
    let ret = false;
    if(parent.getObjectByName(name) !== undefined) {
        ret = true;
    }
    return ret;
};
D3DLib.prototype.hideObject = function(name, parent){
    if (name === undefined || name === null) {
        return;
    }
    if(!(parent instanceof THREE.Mesh)){
        parent = D3DOBJ.scene;
    }
    let mesh = null;
    if((mesh = parent.getObjectByName(name)) !== undefined) {
        mesh.visible = !mesh.visible;
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
    drawingContext.textAlign = data['textAlign']||'center';
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
    cloneObj['userData']['refCode'] = cloneData['refCode'];
    cloneObj['userData']['capacity'] = cloneData['capacity'];
    if (cloneObj['children'] != null && cloneObj['children']['length'] > 1) {
        $['each'](cloneObj['children'], function (i, child) {
            child['name'] = child['name'] + '_' + cloneData['name'];
        });
    }
    cloneObj['position']['x'] += cloneData['x'] || 0;
    cloneObj['position']['y'] += cloneData['y'] || 0;
    cloneObj['position']['z'] += cloneData['z'] || 0;
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
		case 'cabinet':
			tpl = D3DOBJ.createCabinet(tplData);
			break;
		case 'pstrip':
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
D3DLib.prototype.createCabinet = function(data) {
    let length = data['length'] || 1000;
    let width = data['width'] || length;
    let height = data['height'] || 10;
    let x = data['x'] || 0;
    let y = data['y'] || 0;
    let z = data['z'] || 0;
    let boxGeo = new THREE.BoxGeometry( length, height, width, 0, 0, 1);
    // 初始化全局
    let material = new THREE.MeshLambertMaterial( {
        transparent:data['transparent']?true:false,
        opacity:data['opacity']
    } );
    let box = new THREE.Mesh( boxGeo, material );
    box['castShadow'] = true;
    box['receiveShadow'] = true;
    box['uuid'] = data['uuid'];
    box['name'] = data['name'];
    box['position']['set'](x, y, z);

    return box;
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
            // let nPoint = pointsArr['length'] - 1;
            // let per = 1 / nPoint;
            // let vindex = pointIndex*1.0 / per;
            // if (1 === pointIndex) {
            //     return pointsArr[vindex]['clone']()
            // };
            // let nVertor3 = new THREE.Vector3();
            // nVertor3['subVectors'](pointsArr[vindex + 1], pointsArr[vindex]);
            // nVertor3['multiplyScalar']((pointIndex - (per * vindex)) * nPoint);
            // nVertor3['add'](pointsArr[vindex]);
            // return nVertor3
            let nPoint = pointsArr['length'] - 1;
            let per = 1 / nPoint;
            let vindex = parseInt( pointIndex / per);
            if (1 === pointIndex) {
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
        if (config.hasOwnProperty('name') && config['name'].indexOf('power_')===0) {
            GCONFIG['powerMatList'].push(material);
        }
    } else {
        let color = config['color'] || 'green';
        material = new THREE.MeshBasicMaterial({color:color});
    }
    let geometry = new THREE.TubeGeometry(roomCurve, 128, config['radiu'], 4, false);
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
    // 创建canvas
    let canvas = document['createElement']('canvas');
    canvas['width'] = config.hasOwnProperty('width') ? config['width'] : 128;
    canvas['height'] = config.hasOwnProperty('height') ? config['height'] : canvas['width'] / 2;
    // 获取上下文 绘制canvas
    let context = canvas['getContext']('2d');
    // 绘制背景
    context['fillStyle'] = config.hasOwnProperty('background') ? config['background'] : 'rgb(128,128,128)';
    context['fillRect'](0, 0, canvas['width'], canvas['height']);
    // 绘制文本
    context['font'] = config.hasOwnProperty('font') ? config['font'] : 'bold 14px Arial';
    //context['lineWidth'] = borderThickness;
    context['fillStyle'] = config.hasOwnProperty('textColor') ? config['textColor'] : 'rgb(255,255,255)';
    let msgArr = config.hasOwnProperty('msgArr') ? (Array.isArray(config['msgArr'])? config['msgArr'] : [config['msgArr']]) : [];
    for (let i=0; i<msgArr.length; ++i) {
        let ratio = (i+1)*1.0 /(1 + msgArr.length);
        context['fillText'](msgArr[i], 10, ratio*canvas['height']);
    }

    let texture = new THREE.CanvasTexture(canvas);
    let spriteMaterial = new THREE.SpriteMaterial({
        map: texture
    });
    let sprite = new THREE.Sprite(spriteMaterial);
    sprite['name'] = config.hasOwnProperty('name') ? config['name'] : 'unknown';
    sprite['position']['x'] = config.hasOwnProperty('x') ? config['x'] : 0;
    sprite['position']['y'] = config.hasOwnProperty('y') ? config['y'] : 0;
    sprite['position']['z'] = config.hasOwnProperty('z') ? config['z'] : 0;
    sprite['scale']['set'](40, 40, 1.0);
    D3DOBJ.addObject(sprite);
    return sprite;
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
    context['textAlign'] = 'center';
    context['textBaseline'] = 'bottom';
    context['font'] = 'bold ' + fontsize + 'px ' + fontface;
    //context['lineWidth'] = borderThickness;
    context['fillStyle'] = 'rgba(' + textColor['r'] + ', ' + textColor['g'] + ', ' + textColor['b'] + ', 1.0)';
    context['fillText'](message, width/2, borderThickness+height/2);
    //context['fillText'](message, 0, 0);
    let texture = new THREE.Texture(canvas);
    texture['needsUpdate'] = true;
    let spriteMaterial = new THREE.SpriteMaterial({
        map: texture
    });
    let sprite = new THREE.Sprite(spriteMaterial);
    let fromObj = D3DOBJ.findObject(name);
    sprite['position']['x'] = fromObj['position']['x'] + x;
    sprite['position']['y'] = fromObj['position']['y'] + y;
    sprite['position']['z'] = fromObj['position']['z'] + z;
    sprite['name'] = textConfig['name'];
    sprite['scale']['set'](5 * fontsize, 2.5 * fontsize, 1.0);
    D3DOBJ['addObject'](sprite)
};
//config
//width
//height
//background
//font
//textColor
//msgArr
//x-y-z
//parent
//name
D3DLib.prototype.makeTextSpriteEx = function(config) {
    // 创建canvas
    let canvas = document['createElement']('canvas');
    canvas['width'] = config.hasOwnProperty('width') ? config['width'] : 128;
    canvas['height'] = config.hasOwnProperty('height') ? config['height'] : canvas['width'] / 2;
    D3DOBJ.drawOnCanvas(canvas, config);
    let texture = new THREE.Texture(canvas);
    texture.needsUpdate = true;
    let spriteMaterial = new THREE.SpriteMaterial({
        map: texture
    });
    let sprite = new THREE.Sprite(spriteMaterial);
    sprite['name'] = config.hasOwnProperty('name') ? config['name'] : 'unknown';
    sprite['position']['x'] = config.hasOwnProperty('x') ? config['x'] : 0;
    sprite['position']['y'] = config.hasOwnProperty('y') ? config['y'] : 0;
    sprite['position']['z'] = config.hasOwnProperty('z') ? config['z'] : 0;
    sprite['scale']['set'](40, 40, 1.0);
    if (config.hasOwnProperty('parent')) {
        D3DOBJ.addObject(sprite, config['parent']);
    } else {
        D3DOBJ.addObject(sprite);
    }
    return sprite;
};
D3DLib.prototype.drawOnCanvas = function(canvas, config) {
    // 获取上下文 绘制canvas
    let context = canvas['getContext']('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
    // 绘制背景
    context['fillStyle'] = config.hasOwnProperty('background') ? config['background'] : 'rgb(128,128,128)';
    context['fillRect'](0, 0, canvas['width'], canvas['height']);
    // 绘制文本
    context['font'] = config.hasOwnProperty('font') ? config['font'] : 'bold 14px Arial';
    //context['lineWidth'] = borderThickness;
    context['fillStyle'] = config.hasOwnProperty('textColor') ? config['textColor'] : 'rgb(255,255,255)';
    let msgArr = config.hasOwnProperty('msgArr') ? (Array.isArray(config['msgArr'])? config['msgArr'] : [config['msgArr']]) : [];
    for (let i=0; i<msgArr.length; ++i) {
        let ratio = (i+1)*1.0 /(1 + msgArr.length);
        context['fillText'](msgArr[i], 10, ratio*canvas['height']);
    }
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
////////////////////////////////////////////////////////Event
//隐藏网线
D3DLib.prototype.hideCable = function(obj) {
    DelBoxHelper();
    for(let i=0; i<GCONFIG['connList'].length; ++i) {
        if(GCONFIG['connList'][i] !== obj['name'] ){
            D3DOBJ.hideObject(GCONFIG['connList'][i]);
        }
    }
};
//隐藏电线
D3DLib.prototype.hidePower = function(obj) {
    DelBoxHelper();
    for(let i=0; i<GCONFIG['powerList'].length; ++i) {
        if(GCONFIG['powerList'][i] !== obj['name'] ){
            D3DOBJ.hideObject(GCONFIG['powerList'][i]);
        }
    }
};
//隐藏柜子
D3DLib.prototype.hideCabinet = function(obj) {
    DelBoxHelper();
    if(obj.isShowCabinet){
        obj.isShowCabinet=false;
    } else {
        obj.isShowCabinet=true;
    }
    for(let i=0; i<D3DOBJ.cabList.length; ++i){
        let cab = D3DOBJ.cabList[i];
        if(cab.name !== obj.parent.name) {
            cab.visible = !obj.isShowCabinet;
        }
    }
    for(let i=0; i<D3DOBJ.cabInfoSpriteList.length; ++i) {
        let sprite = D3DOBJ.cabInfoSpriteList[i];
        if (sprite['userData']['refCode'] !== obj.parent['userData']['refCode']) {
            sprite.visible = !obj.isShowCabinet;
        }
    }
};
//隐藏一排柜子
D3DLib.prototype.hideRowCabinet = function(obj) {
    DelBoxHelper();
    if(obj.isShowCabinet){
        obj.isShowCabinet=false;
    } else {
        obj.isShowCabinet=true;
    }
    let row_label_name = obj.name;
    let label_row = row_label_name.substr(4,1);
    // 柜子
    for(let i=0; i<D3DOBJ.cabList.length; ++i){
        let cab = D3DOBJ.cabList[i];
        let cabName = cab.name;
        let cab_row = cabName.substr(4,1);
        if(cab_row !== label_row) {
            cab.visible = !obj.isShowCabinet;
        }
    }
    for(let i=0; i<D3DOBJ.cabInfoSpriteList.length; ++i) {
        let sprite = D3DOBJ.cabInfoSpriteList[i];
        let cabCode = sprite['userData']['refCode'];
        let cab_row = cabCode.substr(1,1);
        if (label_row !== cab_row) {
            sprite.visible = !obj.isShowCabinet;
        }
    }
};
//推拉设备
D3DLib.prototype.pullDevice = function(obj){
    DelBoxHelper();
    if(obj.isOpen){
        obj.position.x+=35;
        obj.isOpen=false;
    }else{
        obj.position.x-=35;
        obj.isOpen=true;
    }
};
//开前门
D3DLib.prototype.openFrontDoor = function(obj){
    DelBoxHelper();
    let sign = 1;
    if(!obj.isOpen){
        sign = -1;
        obj.isOpen=true;
    } else{
        obj.isOpen=false;
    }
    obj.rotation.y += sign*1.9;
    obj.position.x += sign*32;
    obj.position.z += sign*45;
};
//开后门
D3DLib.prototype.openBackDoor = function(obj){
    DelBoxHelper();
    let sign = 1;
    if(obj.isOpen){
        sign = -1;
        obj.isOpen=false;
    }else{
        obj.isOpen=true;
    }

    obj.rotation.y += sign*1.3;
    obj.position.x += sign*32;
    obj.position.z += sign*43;
};
//开左门
D3DLib.prototype.openLeftDoor = function(obj){
    DelBoxHelper();
    let sign = 1;
    if(obj.isOpen){
        obj.isOpen=false;
    }else{
        sign = -1;
        obj.isOpen=true;
    }

    obj.rotation.y += sign*1.57;
    obj.position.x += sign*52;
    obj.position.z += sign*52;
};
//开右门
D3DLib.prototype.openRightDoor = function(obj){
    DelBoxHelper();
    let sign = 1;
    if(obj.isOpen){
        sign = -1;
        obj.isOpen=false;
    }else{
        obj.isOpen=true;
    }

    obj.rotation.y += sign*1.7;
    obj.position.x += sign*52;
    obj.position.z -= sign*52;
};
let CABLE_INFO_ID=0;
D3DLib.prototype.showCableInfo = function(obj) {
    if (!obj['userData']['show_cable_info']){
        if(obj === null || obj === undefined || !obj.hasOwnProperty('name')) {
            return;
        }
        //cable_1C230010102_1D426010102
        let cableName = obj['name'];
        let fmEthCode = cableName.substr(6, 11);
        let toEthCode = cableName.substr(18);

        let contents = new Map();

        $.ajax({
            type:'post',
            url:'/glserver/eth/get_cable_info',
            dataType:'json',
            data: {'fmEthCode':fmEthCode, 'toEthCode':toEthCode},
            success:function(data){
                // 起始机柜
                contents['起始机柜'] = fmEthCode.substr(1,2);
                // 起始设备名
                contents['起始设备'] = data['fmDeviceName'];
                // 起始网卡名
                contents['起始网卡'] = data['fmEthName'];
                // 对端机柜
                contents['对端机柜'] = toEthCode.substr(1,2);
                // 对端设备名
                contents['对端设备'] = data['toDeviceName'];
                // 对端网卡名
                contents['对端网卡'] = data['toEthName'];
                CABLE_INFO_ID ++ ;
                D3DOBJ.showDiv('cable-info-'+ obj['name'], contents);
                obj['userData']['show_cable_info'] = true;
            },
            error: function(data) {
                console.log("get cable info from db error");
            }
        });
    }

};
let DEV_INFO_ID = 0;
D3DLib.prototype.showDeviceInfo = function(obj) {
    if (!obj['userData']['show_dev_info']){
        if(obj === null || obj === undefined || !obj.hasOwnProperty('name')) {
            return;
        }
        //dev_1C31602
        //机柜名称
        //起始U
        //占用U
        let devName = obj['name'];

        let devCode = devName.substr(4);
        let startU = devName.substr(7,2);
        let usedU = devName.substr(9,2);
        let contents = new Map();

        $.ajax({
            type:'post',
            url:'/glserver/dev/get_device_info',
            dataType:'json',
            data: {'code':devCode},
            success:function(data){
                contents['设备名称'] = data['devName'];
                contents['设备型号'] = data['devModel'];
                contents['CPU信息'] = data['devCpu'];
                contents['内存信息'] = data['devMem'];
                contents['硬盘信息'] = data['devDisk'];
                contents['安装机柜'] = data['devCabName'];
                contents['位置信息'] = "起始U:" + startU + " 占用U:" +usedU;
                contents['barcode'] = data['devBarCode'];
                DEV_INFO_ID ++;
                D3DOBJ.showDiv('device-info-'+obj['name'], contents);
                obj['userData']['show_dev_info'] = true;
            },
            error: function(data) {
                console.log("get device info from db error");
            }
        });
    }
};
let POWER_INFO_ID=0;
D3DLib.prototype.showPowerInfo = function(obj) {
    if (!obj['userData']['show_power_info']){
        if(obj === null || obj === undefined || !obj.hasOwnProperty('name')) {
            return;
        }
        //power_1C230010102_1D426010102
        let powerName = obj['name'];
        let fmPowerName = powerName.substr(6, 11);
        let toPowerName = powerName.substr(18);

        let contents = new Map();
        // 起始机柜
        contents['电缆名称'] = powerName;
        // 起始设备名
        contents['供电A端'] = fmPowerName;
        // 起始网卡名
        contents['供电B端'] = toPowerName;
        POWER_INFO_ID ++ ;
        D3DOBJ.showDiv('power-info-'+ obj['name'], contents);
        obj['userData']['show_power_info'] = true;
    }
};
//显示信息框
D3DLib.prototype.showDiv = function(domId, contents){
    let eleMain = $('<div></div>');
    eleMain.attr('id', domId);
    eleMain.css('left', D3DOBJ['mousePos']['x'] + 5);
    eleMain.css('top', D3DOBJ['mousePos']['y'] + 5);
    eleMain.css('opacity', 0.8);
    eleMain.css('position', 'absolute');
        // 标题栏
        let eleTitle = $("<div id='title'>信息框</div>");
        let eleClose = $("<div id=\"img\" title=\"关闭\" onClick=\"D3DOBJ.closeDiv('" + domId + "')\">X</div>");
        eleTitle.append(eleClose);
        eleMain.append(eleTitle);
        // 内容
        let eleContents = $("<div id='contents'></div>");
        for(let key in contents){
            let eleContent = $("<div class='content'></div>");
            if (key === 'barcode') {
                // 二维码进行特殊处理
                let imgPath = GCONFIG['pic_path'] + 'barcode/' + contents['barcode'];
                let img = $("<img src='" + imgPath  + "' width='150' height='150'/>");
                let spanKey = $("<span class='key' style='margin-top: 20px;'>二维码</span>");
                let spanVal = $("<span class='value' style='padding: 10px;'></span>");
                spanVal.append(img);
                eleContent.append(spanKey);
                eleContent.append(spanVal);
            } else {
                let spanKey = $("<span class='key'>" + key + "</span>");
                let spanVal = "<span class='value'>" + contents[key] + "</span>";
                eleContent.append(spanKey);
                eleContent.append(spanVal);
            }
            eleContents.append(eleContent);
        }
        eleMain.append(eleContents);
    $('body').append(eleMain);
};
D3DLib.prototype.closeDiv = function(domId) {
    let element = document['getElementById'](domId);
    element.parentNode.removeChild(element);
    if (domId.indexOf('device-info-') === 0) {
        let objName = domId.substr(12);
        let obj = D3DOBJ.findObject(objName);
        if (obj !== undefined && obj !== null) {
            obj['userData']['show_dev_info'] = false;
        }
    } else if (domId.indexOf('cable-info-') === 0) {
        let objName = domId.substr(11);
        let obj = D3DOBJ.findObject(objName);
        if (obj !== undefined && obj !== null) {
            obj['userData']['show_cable_info'] = false;
        }
    } else if (domId.indexOf('power-info-') === 0) {
        let objName = domId.substr(11);
        let obj = D3DOBJ.findObject(objName);
        if (obj !== undefined && obj !== null) {
            obj['userData']['show_power_info'] = false;
        }
    }
};
function DelBoxHelper(){
    if(D3DOBJ['boxHelper'] !== null &&
        D3DOBJ['boxHelper'] !== undefined) {
        D3DOBJ.rmObject(D3DOBJ['boxHelper']);
    }
}
///////////////////////////////////////////Cab_sel_box
// 响应cab_sel_box事件
$(document).on('mouseover','#cab_sel_box', function(){
    D3DOBJ.controls.enabled = false;
});
$(document).on('mouseout','#cab_sel_box', function(){
    D3DOBJ.controls.enabled = true;
});
$(document).on('change', "#cab_sel_box_area", function(){
    $("#cab_sel_box_sys").empty();
    $("#cab_sel_box_sys").append($('<option>请选择设备所属系统</option>'));
    let selVal =$("#cab_sel_box_area").val();
    GCONFIG['sysMap'].forEach(function(val, key, map){
        if (val['areaId'] === parseInt(selVal)){
            $("#cab_sel_box_sys").append($('<option value='+ val['id'] +'>' + val['name'] + '</option>'));
        }
    });
});
$(document).on('submit', "#cab_sel_box", function(ev){
    ev.preventDefault();
});
$(document).on('click','#cab_sel_box_find', function(){
    let validator = $("#cab_sel_box").data('bootstrapValidator');
    validator.validate();//提交验证
    if (validator.isValid()) {//获取验证结果，如果成功，执行下面代码
        let areaId = parseInt($('#cab_sel_box_area').val());
        let systemId = parseInt($('#cab_sel_box_sys').val());
        let u = parseInt($('#cab_sel_box_u').val());
        let cap = parseInt($('#cab_sel_box_cap').val());
        let count = 0;
        if (D3DOBJ.cabInfoMap.size === 0) {
            D3DOBJ.calcCabInfo();
        }
        $('#cab_sel_box_res').empty();
        D3DOBJ.cabInfoMap.forEach(function(val, key, map){
            if (count<3){
                if (areaId === val['areaId'] &&
                    systemId === val['systemId'] &&
                    u <= val['maxConsU'] &&
                    cap <= val['remainCap']
                ) {
                    count += 1;
                    $('#cab_sel_box_res').append("机柜" + count + ':' + key);
                    if (count !== 3) {
                        $('#cab_sel_box_res').append('\n');
                    }
                }
            }
        });
        $('#cab_box_res_grp_res').css('display','block');
    }
});