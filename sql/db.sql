/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : db

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2018-07-19 12:31:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for button
-- ----------------------------
DROP TABLE IF EXISTS `button`;
CREATE TABLE `button` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `isShow` tinyint(4) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `callback` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of button
-- ----------------------------
INSERT INTO `button` VALUES ('1', '1', 'btn_reset', '场景还原', './menus/reset.png', 'function(){ D3DOBJ.reset(); }');
INSERT INTO `button` VALUES ('2', '1', 'btn_aeroview', '空中俯瞰', './menus/aeroview.png', 'function(){D3DOBJ.aeroview();}');
INSERT INTO `button` VALUES ('3', '1', 'btn_roam', '场景巡视', './menus/person.png', 'function(){D3DOBJ.roam();}');
INSERT INTO `button` VALUES ('4', '1', 'btn_connection', '配线管理', './menus/connection.png', 'function(){D3DOBJ.connection();}');
INSERT INTO `button` VALUES ('5', '1', 'btn_temp', '温度监控', './menus/temperature.png', 'function(){D3DOBJ.temp();}');
INSERT INTO `button` VALUES ('6', '1', 'btn_air', '空调风向', './menus/air.png', 'function(){D3DOBJ.air();}');
INSERT INTO `button` VALUES ('7', '1', 'btn_smoke', '烟雾检测', './menus/smoke.png', 'function(){D3DOBJ.smoke();}');

-- ----------------------------
-- Table structure for cabinet
-- ----------------------------
DROP TABLE IF EXISTS `cabinet`;
CREATE TABLE `cabinet` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `station` int(11) DEFAULT NULL,
  `person` varchar(255) DEFAULT NULL,
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cabinet
-- ----------------------------
INSERT INTO `cabinet` VALUES ('1', '1C2', 'C2配自网络机柜Ⅰ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('2', '1C3', 'C3配自网络机柜Ⅱ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('3', '1C4', 'C4配自安全接入Ⅰ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('4', '1C5', 'C5配自安全接入Ⅱ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('5', '1C6', 'C6配自工作站柜', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('6', '1C7', 'C7配自安全防护', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('7', '1C8', 'C8配自服务器柜Ⅰ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('8', '1C9', 'C9配自服务器柜Ⅱ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('9', '1D2', 'D2配自服务器柜Ⅰ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('10', '1D3', 'D3配自服务器柜Ⅱ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('11', '1D4', 'D4配自安全接入Ⅰ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('12', '1D5', 'D5配自安全接入Ⅱ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('13', '1D6', 'D6配自工作站柜', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('14', '1E3', 'E3监控安全防护Ⅰ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('15', '1E2', 'E2新能源数据采集', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('16', '1E4', 'E4监控安全防护Ⅱ', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('17', '1E8', 'E81#UPS配电柜', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('18', '1E9', 'E92#UPS配电柜', '1', '孙长春', '');
INSERT INTO `cabinet` VALUES ('19', '1F9', 'F9市电配电柜', '1', '孙长春', '');

-- ----------------------------
-- Table structure for clone
-- ----------------------------
DROP TABLE IF EXISTS `clone`;
CREATE TABLE `clone` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `isShow` tinyint(4) DEFAULT NULL,
  `copyfrom` varchar(255) DEFAULT NULL,
  `pos_x` double DEFAULT '0',
  `pos_y` double DEFAULT '0',
  `pos_z` double DEFAULT '0',
  `rot_x` double DEFAULT '0',
  `rot_y` double DEFAULT '0',
  `rot_z` double DEFAULT '0',
  `scl_x` double DEFAULT '1',
  `scl_y` double DEFAULT '1',
  `scl_z` double DEFAULT '1',
  `parent` varchar(255) DEFAULT '',
  `isDirty` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clone
-- ----------------------------
INSERT INTO `clone` VALUES ('1', 'aircondition2', '1', 'aircondition', '450', '110', '-595', '0', '-90', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('3', 'firedeivce2', '1', 'firedevice', '-650', '110', '600', '0', '90', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('4', 'firedevice3', '1', 'firedevice', '980', '110', '330', '0', '90', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('5', 'firedevice4', '1', 'firedevice', '950', '110', '-595', '0', '-90', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('6', 'cab_F2', '1', 'cabinet', '680', '113', '-329', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('7', 'cab_F3', '1', 'cabinet', '680', '113', '-258', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('8', 'cab_F4', '1', 'cabinet', '680', '113', '-187', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('9', 'cab_F5', '1', 'cabinet', '680', '113', '-116', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('10', 'cab_F6', '1', 'cabinet', '680', '113', '-45', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('11', 'cab_F7', '1', 'cabinet', '680', '113', '26', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('12', 'cab_F8', '1', 'cabinet', '680', '113', '97', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('13', 'cab_F9', '1', 'cabinet', '680', '113', '168', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('14', 'cab_E1', '1', 'cabinet', '400', '113', '-400', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('15', 'cab_E2', '1', 'cabinet', '400', '113', '-329', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('16', 'cab_E3', '1', 'cabinet', '400', '113', '-258', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('17', 'cab_E4', '1', 'cabinet', '400', '113', '-187', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('18', 'cab_E5', '1', 'cabinet', '400', '113', '-116', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('19', 'cab_E6', '1', 'cabinet', '400', '113', '-45', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('20', 'cab_E7', '1', 'cabinet', '400', '113', '26', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('21', 'cab_E8', '1', 'cabinet', '400', '113', '97', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('22', 'cab_E9', '1', 'cabinet', '400', '113', '168', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('23', 'cab_D1', '1', 'cabinet', '120', '113', '-400', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('24', 'cab_D2', '1', 'cabinet', '120', '113', '-329', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('25', 'cab_D3', '1', 'cabinet', '120', '113', '-258', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('26', 'cab_D4', '1', 'cabinet', '120', '113', '-187', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('27', 'cab_D5', '1', 'cabinet', '120', '113', '-116', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('28', 'cab_D6', '1', 'cabinet', '120', '113', '-45', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('29', 'cab_D7', '1', 'cabinet', '120', '113', '26', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('30', 'cab_D8', '1', 'cabinet', '120', '113', '97', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('31', 'cab_D9', '1', 'cabinet', '120', '113', '168', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('32', 'cab_C1', '1', 'cabinet', '-160', '113', '-400', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('33', 'cab_C2', '1', 'cabinet', '-160', '113', '-329', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('34', 'cab_C3', '1', 'cabinet', '-160', '113', '-258', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('35', 'cab_C4', '1', 'cabinet', '-160', '113', '-187', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('36', 'cab_C5', '1', 'cabinet', '-160', '113', '-116', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('37', 'cab_C6', '1', 'cabinet', '-160', '113', '-45', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('38', 'cab_C7', '1', 'cabinet', '-160', '113', '26', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('39', 'cab_C8', '1', 'cabinet', '-160', '113', '97', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('40', 'cab_C9', '1', 'cabinet', '-160', '113', '168', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('41', 'cab_B1', '1', 'cabinet', '-440', '113', '-400', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('42', 'cab_B2', '1', 'cabinet', '-440', '113', '-329', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('43', 'cab_B3', '1', 'cabinet', '-440', '113', '-258', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('44', 'cab_B4', '1', 'cabinet', '-440', '113', '-187', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('45', 'cab_B5', '1', 'cabinet', '-440', '113', '-116', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('46', 'cab_B6', '1', 'cabinet', '-440', '113', '-45', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('47', 'cab_B7', '1', 'cabinet', '-440', '113', '26', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('48', 'cab_B8', '1', 'cabinet', '-440', '113', '97', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('49', 'cab_B9', '1', 'cabinet', '-440', '113', '168', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('50', 'cab_A1', '1', 'cabinet', '-720', '113', '-400', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('51', 'cab_A2', '1', 'cabinet', '-720', '113', '-329', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('52', 'cab_A3', '1', 'cabinet', '-720', '113', '-258', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('53', 'cab_A4', '1', 'cabinet', '-720', '113', '-187', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('54', 'cab_A5', '1', 'cabinet', '-720', '113', '-116', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('55', 'cab_A6', '1', 'cabinet', '-720', '113', '-45', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('56', 'cab_A7', '1', 'cabinet', '-720', '113', '26', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('57', 'cab_A8', '1', 'cabinet', '-720', '113', '97', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('58', 'cab_A9', '1', 'cabinet', '-720', '113', '168', '0', '0', '0', '1', '1', '1', null, '0');
INSERT INTO `clone` VALUES ('59', 'firedevice1', '1', 'firedevice', '-650', '110', '-595', '0', '-90', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('60', 'aircondition1', '1', 'aircondition', '-450', '110', '-595', '0', '-90', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('61', 'cab_F1', '1', 'cabinet', '680', '113', '-400', '0', '0', '0', '1', '1', '1', '', '0');
INSERT INTO `clone` VALUES ('62', 'dataserver11', '1', 'dataserver', '0', '50', '0', '0', '0', '0', '1', '1', '1', 'cab_F1', '0');

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `typeId` int(4) DEFAULT '1',
  `typeName` varchar(255) DEFAULT NULL,
  `cabinet` int(11) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `person` varchar(255) DEFAULT NULL,
  `record` varchar(255) DEFAULT NULL,
  `ethRowCount` tinyint(4) DEFAULT '1',
  `ethColCount` tinyint(4) DEFAULT '10',
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '1C21401', '一区KVM系统', '3', '1UKVM', '1', '', '', '孙长春', '', '2', '10', '设备型号：武汉达梦KVM DKX3-832\n切换方式：远程切换\n输入路数：32\n输出路数：32\n设备U数：1\n安装地点：C2\n起始U位：14U');
INSERT INTO `device` VALUES ('7', '1C23001', '主干网交换机A', '4', '1U交换机', '1', '', '', '孙长春', '', '2', '10', '设备型号：华为S5720-56C-HI\n交换速率：48个10/100/1000Base-T以太网端口，\n4个万兆SFP+，两个扩展槽位\n光口数：4\n电口数：48\n设备U数：1\n安装地点：C2\n起始U位 ：30');
INSERT INTO `device` VALUES ('9', '1C31401', '县公司防火墙', '1', '1U防火墙', '2', '', '', '孙长春', '', '2', '10', '设备型号：天融信TG-51030\n是否光纤接入：N\n吞吐量：6G\n电源数：2\n设备U数：1\n安装地点：C3\n起始U位：14U');
INSERT INTO `device` VALUES ('10', '1C31602', '配电加密认证装置B', '8', '2U加密管理装置', '2', '', '', '孙长春', '', '2', '10', '设备型号：北京智芯SJJ1545\n设备U数：2\n安装地点：C3\n起始U位 ：16');
INSERT INTO `device` VALUES ('11', '1C31902', '反向安全隔离装置B(一安区)', '12', '2U反向隔离', '2', '', '', '孙长春', '', '2', '10', '设备型号：南瑞信通Syskeeper-2000E\n传输方向：反向\n设备U数：2\n安装地点：C3\n起始U位 ：19');
INSERT INTO `device` VALUES ('12', '1C32201', '正向安全隔离装置B(一安区)', '2', '1U正向隔离', '2', '', '', '孙长春', '', '2', '10', '设备型号：珠海鸿瑞WALL-85M-II-SQ\n传输方向：正向\n设备U数：1\n安装地点：C3\n起始U位 ：22');
INSERT INTO `device` VALUES ('13', '1C32402', '反向安全隔离装置B(一三区)', '12', '2U反向隔离', '2', '', '', '孙长春', '', '2', '10', '设备型号：南瑞信通Syskeeper-2000E\n传输方向：反向\n设备U数：2\n安装地点：C3\n起始U位 ：24');
INSERT INTO `device` VALUES ('14', '1C32701', '正向安全隔离装置B(一三区)', '2', '1U正向隔离', '2', '', '', '孙长春', '', '2', '10', '设备型号：珠海鸿瑞WALL-85M-II-SQ\n传输方向：正向\n设备U数：1\n安装地点：C3\n起始U位 ：27');
INSERT INTO `device` VALUES ('15', '1C33001', '主干网交换机B', '4', '1U交换机', '2', '', '', '孙长春', '', '2', '10', '设备型号：华为S5720-56C-HI\n交换速率：48个10/100/1000Base-T以太网端口，\n4个万兆SFP+，两个扩展槽位\n光口数：4\n电口数：48\n设备U数：1\n安装地点：C3\n起始U位 ：30');
INSERT INTO `device` VALUES ('16', '1C41301', '采集交换机A', '1', null, '3', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，\n4个复用的千兆Combo SFP，\n两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：C4\n起始U位 ：13');
INSERT INTO `device` VALUES ('17', '1C41501', '安全区交换机A', '1', null, '3', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，\n4个复用的千兆Combo SFP，\n两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：C4\n起始U位 ：15');
INSERT INTO `device` VALUES ('18', '1C41802', '配电安全接入网关A', '1', null, '3', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('19', '1C42102', '采集服务器A', '1', null, '3', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C4安全接入柜Ⅰ\n起始U位 ：21\nHspdbfe1. a 198.120.100.141   Hspdbfe1. b 198.121.100.141\nHspdbfe1. c 198.122.100.141   Hspdbfe1. d 198.123.100.141');
INSERT INTO `device` VALUES ('20', '1C51301', '采集交换机B', '1', null, '4', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，\n4个复用的千兆Combo SFP，\n两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：C5\n起始U位 ：13');
INSERT INTO `device` VALUES ('21', '1C51501', '安全区交换机B', '1', null, '4', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，\n4个复用的千兆Combo SFP，\n两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：C5\n起始U位 ：15');
INSERT INTO `device` VALUES ('22', '1C51802', '配电安全接入网关B', '1', null, '4', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('23', '1C52102', '采集服务器B', '1', null, '4', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C5安全接入柜Ⅱ\n起始U位 ：21\nHspdbfe2. a 198.120.100.142   Hspdbfe2. b 198.121.100.142\nHspdbfe2. c 198.122.100.142   Hspdbfe2. d 198.123.100.142');
INSERT INTO `device` VALUES ('24', '1C70903', '磁盘阵列扩展', '1', null, '6', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('25', '1C71303', '磁盘阵列', '1', null, '6', '', '', '孙长春', '', '2', '10', '设备型号：曙光DS600-G20\nRAID方式：RAID5\n磁盘容量：1.2T*13\n硬盘转速：10000转\n高速缓存容量：64GB\n外接主机通道：FC-SAN存储\n单击磁盘个数：13\n设备U数：6\n安装地点：C7\n起始U位 ：13');
INSERT INTO `device` VALUES ('26', '1C71701', '入侵检测装置', '1', null, '6', '', '', '孙长春', '', '2', '10', '设备型号：天融信TS-21104\n是否光纤接入：N\n电源数：2\n电口数：4（千兆）\n设备U数：1\n安装地点：C7\n起始U位：17U');
INSERT INTO `device` VALUES ('27', '1C71902', '内网审计系统', '1', null, '6', '', '', '孙长春', '', '2', '10', '设备型号：天融信TA-11314-NET\n是否光纤接入：N\n吞吐量：6G\n电源数：2\n设备U数：1\n安装地点：C7\n起始U位：19U');
INSERT INTO `device` VALUES ('28', '1C7222', 'EMS反向安全隔离装置', '1', null, '6', '', '', '孙长春', '', '2', '10', '设备型号：南瑞信通Syskeeper-2000E\n传输方向：反向\n设备U数：2\n安装地点：C7\n起始U位 ：22');
INSERT INTO `device` VALUES ('29', '1C7251', 'EMS正向安全隔离装置', '1', null, '6', '', '', '孙长春', '', '2', '10', '设备型号：珠海鸿瑞WALL-85M-II-SQ\n传输方向：正向\n设备U数：1\n安装地点：C7\n起始U位 ：25');
INSERT INTO `device` VALUES ('30', '1C7284', '内网安全监测服务器', '1', null, '6', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.8GHz\nCPU数   ：4颗AMD6320每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C7安全防护柜\n起始U位 ：28\nHspdajc1. a 198.120.100.116   Hspdajc1. b 198.121.100.116\nHspdajc1. c 198.122.100.116   Hspdajc1. d 198.123.100.116');
INSERT INTO `device` VALUES ('32', '1C8204', '数据库服务器A', '1', null, '7', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.8GHz\nCPU数   ：4颗AMD6320每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C8服务器机柜Ⅰ\n起始U位 ：20\nHspdadb1.a 198.120.100.105   Hspdadb1.b 198.121.100.105\nHspdadb1.c 198.122.100.105   Hspdadb1.d 198.123.100.105');
INSERT INTO `device` VALUES ('33', '1C8254', 'SCADA/应用服务器A', '1', null, '7', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.6GHz\nCPU数   ：4颗AMD6344每颗12核\n内存大小：64GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C8服务器机柜Ⅰ\n起始U位 ：25\nHspdasd1. a 198.120.100.101   Hspdasd1. b 198.121.100.101\nHspdasd1. c 198.120.100.101   Hspdasd1. d 198.123.100.101\n');
INSERT INTO `device` VALUES ('34', '1C8302', '前置服务器A', '1', null, '7', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C8服务器机柜Ⅰ\n起始U位 ：30\nHspdafe1. a 198.120.100.101   Hspdafe1. b 198.121.100.103\nHspdafe1. c 198.122.100.103   Hspdafe1. d 198.123.100.103');
INSERT INTO `device` VALUES ('35', '1C8332', '图模调试服务器A', '1', null, '7', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C8服务器机柜Ⅰ\n起始U位 ：33\nHspdatm1. a 198.120.100.107   Hspdatm1. b 198.121.100.107\nHspdatm1. c 198.122.100.107   Hspdatm1. d 198.123.100.107');
INSERT INTO `device` VALUES ('36', '1C8362', '标准化接口服务器A', '1', null, '7', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：300 GB*2  SAS硬盘\n安装地点：C8服务器机柜Ⅰ\n起始U位 ：36\nHspdajk1. a 198.120.100.109   Hspdajk1. b 198.121.100.109\nHspdajk1. c 198.122.100.109   Hspdajk1. d 198.123.100.109');
INSERT INTO `device` VALUES ('38', '1C9204', '数据库服务器B', '1', null, '8', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.8GHz\nCPU数   ：4颗AMD6320每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C9服务器机柜Ⅱ\n起始U位 ：20\nHspdadb2.a 198.120.100.106   Hspdadb2.a 198.121.100.106\nHspdadb2.c 198.122.100.106   Hspdadb2.a 198.123.100.106');
INSERT INTO `device` VALUES ('39', '1C9254', 'SCADA/应用服务器B', '1', null, '8', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.6GHz\nCPU数   ：4颗AMD6344每颗12核\n内存大小：64GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C9服务器机柜Ⅱ\n起始U位 ：25\nHspdasd2. a 198.120.100.102  Hspdasd2. b 198.121.100.102\nHspdasd2. c 198.122.100.102  Hspdasd2. d 198.123.100.102');
INSERT INTO `device` VALUES ('40', '1C9302', '前置服务器B', '1', null, '8', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C9服务器机柜Ⅱ\n起始U位 ：30\nHspdafe2. a 198.120.100.104   Hspdafe2. b 198.121.100.104\nHspdafe2. c 198.122.100.104   Hspdafe2. d 198.123.100.104');
INSERT INTO `device` VALUES ('41', '1C9332', '图模调试服务器B', '1', null, '8', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：C9服务器机柜Ⅱ\n起始U位 ：33\nHspdatm2. a 198.120.100.108   Hspdatm2. b 198.121.100.108\nHspdatm2. c 198.122.100.108   Hspdatm2. d 198.123.100.108');
INSERT INTO `device` VALUES ('42', '1D2234', 'SCADA/应用服务器A', '1', null, '9', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.6GHz\nCPU数   ：4颗AMD6344每颗12核\n内存大小：64GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：D2服务器机柜Ⅰ\n起始U位 ：23\nHspdcap1. a 198.120.100.154   Hspdcap1. b 198.121.100.154\nHspdcap1. c 198.122.100.154   Hspdcap1. d 198.123.100.154');
INSERT INTO `device` VALUES ('43', '1D2284', '信息发布服务器A', '1', null, '9', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.8GHz\nCPU数   ：4颗AMD6320每颗8核\n内存大小：64GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：D2服务器机柜Ⅰ\n起始U位 ：28\nHspdcfb1. a 198.120.100.158   Hspdcfb1. b 198.121.100.158\nHspdcfb1. c 198.122.100.158   Hspdcfb1. d 198.123.100.158');
INSERT INTO `device` VALUES ('44', '1D2332', '信息交换总线服务器A', '1', null, '9', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：300 GB*2  SAS硬盘\n安装地点：D2服务器机柜Ⅰ\n起始U位 ：33\nHspdczx1. a 198.120.100.156   Hspdczx1. b 198.121.100.156\nHspdczx1. c 198.122.100.156   Hspdczx1. d 198.123.100.156');
INSERT INTO `device` VALUES ('45', '1D2362', '前置服务器A', '1', null, '9', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：D2服务器机柜Ⅰ\n起始U位 ：36\nHspdcfe1. a 198.120.100.152   Hspdcfe1. b 198.121.100.152\nHspdcfe1. c 198.122.100.152   Hspdcfe1. d 198.123.100.152');
INSERT INTO `device` VALUES ('46', '1D3234', 'SCADA/应用服务器B', '1', null, '10', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.6GHz\nCPU数   ：4颗AMD6344每颗12核\n内存大小：64GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：D3服务器机柜Ⅱ\n起始U位 ：23\nHspdcap2. a 198.120.100.155   Hspdcap2. b 198.121.100.155\nHspdcap2. c 198.122.100.155   Hspdcap2. d 198.123.100.155');
INSERT INTO `device` VALUES ('47', '1D3284', '信息发布服务器B', '1', null, '10', '', '', '孙长春', '', '2', '10', '设备型号：曙光A840-G10\nCPU主频：2.8GHz\nCPU数   ：4颗AMD6320每颗8核\n内存大小：64GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：D3服务器机柜Ⅱ\n起始U位 ：28\nHspdcfb2. a 198.120.100.159   Hspdcfb2. b 198.121.100.159\nHspdcfb2. c 198.122.100.159   Hspdcfb2. d 198.123.100.159');
INSERT INTO `device` VALUES ('48', '1D3332', '信息交换总线服务器B', '1', null, '10', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：300 GB*2  SAS硬盘\n安装地点：D3服务器机柜Ⅱ\n起始U位 ：33\nHspdczx2. a 198.120.100.157   Hspdczx2. b 198.121.100.157\nHspdczx2. c 198.122.100.157   Hspdczx2. d 198.123.100.157');
INSERT INTO `device` VALUES ('49', '1D3362', '前置服务器B', '1', null, '10', '', '', '孙长春', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：600 GB*2  SAS硬盘\n安装地点：D3服务器机柜Ⅱ\n起始U位 ：36\nHspdcfe2. a 198.120.100.153   Hspdcfe2. b 198.121.100.153\nHspdcfe2. c 198.122.100.153   Hspdcfe2. d 198.123.100.153');
INSERT INTO `device` VALUES ('50', '1D41601', '县公司防火墙', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：天融信TG-51030\n是否光纤接入：N\n吞吐量：6G\n电源数：2\n设备U数：1\n安装地点：D4\n起始U位：16U');
INSERT INTO `device` VALUES ('51', '1D42002', '数据隔离组件A', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：中电普华CEIT DSG 3000\n设备U数：2\n安装地点：D4\n起始U位 ：20');
INSERT INTO `device` VALUES ('52', '1D42301', '无线数采交换机A', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，4个复用的千兆Combo SFP，两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：D4\n起始U位 ：23');
INSERT INTO `device` VALUES ('53', '1D42601', '总线交换机A', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，4个复用的千兆Combo SFP，两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：D4\n起始U位 ：26');
INSERT INTO `device` VALUES ('54', '1D42802', '配电加密认证装置A', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：北京智芯SJJ1545\n设备U数：2\n安装地点：D4\n起始U位 ：28');
INSERT INTO `device` VALUES ('55', '1D43101', '三区主干网交换机A', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：华为S5720-56C-HI\n交换速率：48个10/100/1000Base-T以太网端口，4个万兆SFP+，两个扩展槽位\n光口数：4\n电口数：48\n设备U数：1\n安装地点：D4\n起始U位 ：31');
INSERT INTO `device` VALUES ('56', '1D43301', '无线采集防火墙', '1', null, '11', '', '', '孙长春', '', '2', '10', '设备型号：天融信TG-51030\n是否光纤接入：N\n吞吐量：6G\n电源数：2\n设备U数：1\n安装地点：D4\n起始U位：33U');
INSERT INTO `device` VALUES ('57', '1D5131', '三区KVM系统', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：武汉达梦KVM DKX3-832\n切换方式：远程切换\n输入路数：32\n输出路数：32\n设备U数：1\n安装地点：D5\n起始U位：13U');
INSERT INTO `device` VALUES ('58', '1D5161', '接口防火墙', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：天融信TG-51030\n是否光纤接入：N\n吞吐量：6G\n电源数：2\n设备U数：1\n安装地点：D5\n起始U位：16U');
INSERT INTO `device` VALUES ('59', '1D5202', '数据隔离组件B', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：中电普华CEIT DSG 3000\n设备U数：2\n安装地点：D5\n起始U位 ：20');
INSERT INTO `device` VALUES ('60', '1D5231', '无线数采交换机B', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，4个复用的千兆Combo SFP，两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：D5\n起始U位 ：23');
INSERT INTO `device` VALUES ('61', '1D5261', '总线交换机B', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：华为S5710-28C-EI\n交换速率：24个10/100/1000Base-T以太网端口，4个复用的千兆Combo SFP，两个扩展槽位；4个万兆SFP+\n光口数：8\n电口数：24\n设备U数：1\n安装地点：D5\n起始U位 ：26');
INSERT INTO `device` VALUES ('62', '1D5282', '配电加密认证装置B', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：北京智芯SJJ1545\n设备U数：2\n安装地点：D5\n起始U位 ：28');
INSERT INTO `device` VALUES ('63', '1D5311', '三区主干网交换机B', '1', null, '12', '', '', '孙长春', '', '2', '10', '设备型号：华为S5720-56C-HI\n交换速率：48个10/100/1000Base-T以太网端口，4个万兆SFP+，两个扩展槽位\n光口数：4\n电口数：48\n设备U数：1\n安装地点：D5\n起始U位 ：31');
INSERT INTO `device` VALUES ('64', '1D5344', '天文时钟', '1', null, '12', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('65', '1D6174', '数据库服务器B', '1', null, '13', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('66', '1D6224', '数据库服务器A', '1', null, '13', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('67', '1D6273', '磁盘阵列扩展', '1', null, '13', '', '', '孙长春', '', '2', '10', '');
INSERT INTO `device` VALUES ('68', '1D6313', '磁盘阵列', '1', null, '13', '', '', '孙长春', '', '2', '10', '设备型号：曙光DS600-G20\nRAID方式：RAID5\n磁盘容量：1.2T*16\n硬盘转速：10000转\n高速缓存容量：64GB\n外接主机通道：FC-SAN存储\n单击磁盘个数：16\n设备U数：6\n安装地点：D6\n起始U位 ：31');
INSERT INTO `device` VALUES ('69', '1D63501', 'SAN交换机B', '1', null, '13', '', '', '孙长春', '', '2', '10', '设备型号：SUN\n光口数：24\n设备U数：1\n安装地点：D6\n起始U位 ：35');
INSERT INTO `device` VALUES ('70', '1D63701', 'SAN交换机A', '1', null, '13', '', '', '孙长春', '', '2', '10', '设备型号：SUN\n光口数：24\n设备U数：1\n安装地点：D6\n起始U位 ：37');
INSERT INTO `device` VALUES ('71', '1C9362', '标准化接口服务器B', '1', null, '8', '', '', '', '', '2', '10', '设备型号：曙光A420r-G\nCPU主频：3.1GHz\nCPU数   ：2颗AMD4386每颗8核\n内存大小：32GB\n磁盘容量：300 GB*2  SAS硬盘\n安装地点：C9服务器机柜Ⅱ\n起始U位 ：36\nHspdajk2. a 198.120.100.110   Hspdajk2. b 198.121.100.110\nHspdajk2. c 198.122.100.110   Hspdajk2. a 198.123.100.110');
INSERT INTO `device` VALUES ('72', '1C8181', 'SAN交换机A', '1', null, '7', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('73', '1C9181', 'SAN交换机B', '1', null, '8', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('74', '1D4371', '入侵检测装置', '1', null, '11', '', '', '', '', '2', '10', '设备型号：天融信TS-21104\n是否光纤接入：N\n电源数：2\n电口数：4（千兆）\n设备U数：1\n安装地点：D4\n起始U位：37U');
INSERT INTO `device` VALUES ('75', '1E3124', '监视告警与核查服务器', '1', null, '14', 'root', 'rocky', '', '', '2', '10', '192.168.12.104');
INSERT INTO `device` VALUES ('76', '1E3174', '闲置设备', '1', null, '14', 'root', 'rocky', '', '', '2', '10', '192.168.12.102');
INSERT INTO `device` VALUES ('77', '1E3224', '内网监控平台工作站', '1', null, '14', 'root', 'rocky', '', '', '2', '10', '192.168.12.103');
INSERT INTO `device` VALUES ('78', '1E4124', '服务网关机', '1', null, '16', 'root', 'rocky', '', '', '2', '10', 'IP1：192.168.12.105\nIP2：34.19.30.10');
INSERT INTO `device` VALUES ('79', '1E4174', '数据库服务器', '1', null, '16', 'root', 'rocky', '', '', '2', '10', '192.168.12.102');
INSERT INTO `device` VALUES ('80', '1E4222', '二区网络安全检测装置', '1', null, '16', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('81', '1E4242', '一区网络安全检测装置', '1', null, '16', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('82', '1E4301', '华为交换机', '1', null, '16', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('83', '1E4281', '网络卫士防火墙系统', '1', null, '16', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('84', '1E2141', '安全二区交换机', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('85', '1E2161', '安全一区交换机', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('86', '1E2191', '双电源切换器', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('87', '1E2221', '安全二区反向隔离', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('88', '1E2241', '安全一区反向隔离', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('89', '1E2271', 'KVM显示器', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('90', '1E2302', '安全二区服务器', '1', null, '15', '', '', '', '', '2', '10', '');
INSERT INTO `device` VALUES ('91', '1E2332', '安全一区服务器', '1', null, '15', '', '', '', '', '2', '10', '');

-- ----------------------------
-- Table structure for device_type
-- ----------------------------
DROP TABLE IF EXISTS `device_type`;
CREATE TABLE `device_type` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `tpl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_type
-- ----------------------------
INSERT INTO `device_type` VALUES ('1', '1U防火墙', '1u_firewall');
INSERT INTO `device_type` VALUES ('2', '1U正向隔离', '1u_fwdiso');
INSERT INTO `device_type` VALUES ('3', '1UKVM', '1u_kvm');
INSERT INTO `device_type` VALUES ('4', '1U交换机', '1u_switch');
INSERT INTO `device_type` VALUES ('5', '2U检测装置', '2u_detector');
INSERT INTO `device_type` VALUES ('6', '2U配网架', '2u_dframe');
INSERT INTO `device_type` VALUES ('7', '2U加密设备', '2u_enc');
INSERT INTO `device_type` VALUES ('8', '2U加密管理装置', '2u_encmgr');
INSERT INTO `device_type` VALUES ('9', '2U防火墙', '2u_firewall');
INSERT INTO `device_type` VALUES ('10', '2U立卓服务器', '2u_lizhuo');
INSERT INTO `device_type` VALUES ('11', '2U路由电源装置', '2u_routepwr');
INSERT INTO `device_type` VALUES ('12', '2U反向隔离', '2u_rvsiso');
INSERT INTO `device_type` VALUES ('13', '2U服务器', '2u_server');
INSERT INTO `device_type` VALUES ('14', '3U磁盘阵列', '3u_raid');
INSERT INTO `device_type` VALUES ('15', '3U服务器', '3u_server');
INSERT INTO `device_type` VALUES ('16', '4U天文时钟', '4u_clock');
INSERT INTO `device_type` VALUES ('17', '4U服务器', '4u_server');
INSERT INTO `device_type` VALUES ('18', '4U工作站', '4u_worksta');
INSERT INTO `device_type` VALUES ('19', '14U路由器', '14u_router');
INSERT INTO `device_type` VALUES ('20', '21U路由器(H3C)', '21u_router_h3c');
INSERT INTO `device_type` VALUES ('21', '21U路由器(中兴)', '21u_router_zx');

-- ----------------------------
-- Table structure for eth
-- ----------------------------
DROP TABLE IF EXISTS `eth`;
CREATE TABLE `eth` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `device` int(11) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `peerId` varchar(255) DEFAULT NULL,
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eth
-- ----------------------------
INSERT INTO `eth` VALUES ('8', '1C230010101', '0101', '7', '', '1D426010101', '');
INSERT INTO `eth` VALUES ('11', '1C230010102', '0102', '7', '', '1D426010102', '');
INSERT INTO `eth` VALUES ('12', '1C230010103', '0103', '7', '', '1D426010103', '');
INSERT INTO `eth` VALUES ('13', '1C230010201', '0201', '7', '', '1D426010201', '');
INSERT INTO `eth` VALUES ('14', '1C230010202', '0202', '7', '', '1D426010202', '');
INSERT INTO `eth` VALUES ('15', '1C230010203', '0203', '7', '', '1D426010203', '');
INSERT INTO `eth` VALUES ('16', '1D426010101', '0101', '53', '', '1C230010101', '');
INSERT INTO `eth` VALUES ('17', '1D426010102', '0102', '53', '', '1C230010102', '');
INSERT INTO `eth` VALUES ('18', '1D426010103', '0103', '53', '', '1C230010103', '');
INSERT INTO `eth` VALUES ('19', '1D426010201', '0201', '53', '', '1C230010201', '');
INSERT INTO `eth` VALUES ('20', '1D426010202', '0202', '53', '', '1C230010202', '');
INSERT INTO `eth` VALUES ('21', '1D426010203', '0203', '53', '', '1C230010203', '');

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `isShow` tinyint(4) DEFAULT '1',
  `type` varchar(255) DEFAULT NULL,
  `match_func` varchar(255) DEFAULT NULL,
  `deal_func` varchar(20000) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES ('1', 'card_event', '1', 'rclick', 'function(name){ return  name.indexOf(\'cab_\')>=0;}', 'function(obj){/* if(obj.pick) { obj.position.y -=200; obj.pick=0; } else {var box = new THREE.BoxHelper( obj,0xffff00 );D3DOBJ.scene.add(box); obj.pick=1;}*/}');
INSERT INTO `event` VALUES ('2', 'open_doorFront', '1', 'dbclick', 'function(name){ return  name.indexOf(\'doorFront\')>=0;}', 'function(obj){ if(obj.isOpen){  obj.rotation.y+=1.57; obj.position.x+=35; obj.position.z+=33; obj.isOpen=false;}else{obj.rotation.y-=1.57; obj.position.x-=35; obj.position.z-=33; obj.isOpen=true;}}');
INSERT INTO `event` VALUES ('3', 'open_doorBack', '1', 'dbclick', 'function(name){ return  name.indexOf(\'doorBack\')>=0;}', 'function(obj){ if(obj.isOpen){  obj.rotation.y-=1.57; obj.position.x-=35; obj.position.z-=30; obj.isOpen=false;}else{obj.rotation.y+=1.57; obj.position.x+=35; obj.position.z+=30; obj.isOpen=true;}}');
INSERT INTO `event` VALUES ('4', 'open_doorLeft', '1', 'dbclick', 'function(name){ return  name.indexOf(\'doorRight\')>=0;}', 'function(obj){ if(obj.isOpen){  obj.rotation.y+=1.57; obj.position.x+=52; obj.position.z+=52; obj.isOpen=false;}else{obj.rotation.y-=1.57; obj.position.x-=52; obj.position.z-=52; obj.isOpen=true;}}');
INSERT INTO `event` VALUES ('5', 'open_doorRight', '1', 'dbclick', 'function(name){ return  name.indexOf(\'doorLeft\')>=0;}', 'function(obj){ if(obj.isOpen){  obj.rotation.y-=1.57; obj.position.x-=52; obj.position.z+=52; obj.isOpen=false;}else{obj.rotation.y+=1.57; obj.position.x+=52; obj.position.z-=52; obj.isOpen=true;}}');
INSERT INTO `event` VALUES ('6', 'pull_device', '1', 'dbclick', 'function(name){ return  name.indexOf(\'dataserver\')>=0;}', 'function(obj){ D3DOBJ.pullDevice(obj);}');
INSERT INTO `event` VALUES ('7', 'hide_cabinet', '1', 'rclick', 'function(name){ return  name.indexOf(\'col_\')>=0;}', 'function(obj){ D3DOBJ.hideCabinet(obj); }');
INSERT INTO `event` VALUES ('8', 'hide_cable', '1', 'rclick', 'function(name){ return name.indexOf(\'cable_\')===0;}', 'function(obj){D3DOBJ.hidePath(obj);}');

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `isShow` tinyint(4) DEFAULT NULL,
  `length` double DEFAULT NULL,
  `width` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `pos_x` double DEFAULT NULL,
  `pos_y` double DEFAULT NULL,
  `pos_z` double DEFAULT NULL,
  `rot_x` double DEFAULT '0',
  `rot_y` double DEFAULT '0',
  `rot_z` double DEFAULT '0',
  `scl_x` double DEFAULT '1',
  `scl_y` double DEFAULT '1',
  `scl_z` double DEFAULT '1',
  `fillColor` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `isDirty` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES ('1', 'col_F1', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F1', '0');
INSERT INTO `label` VALUES ('2', 'col_F2', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F2', '0');
INSERT INTO `label` VALUES ('3', 'col_F3', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F3', '0');
INSERT INTO `label` VALUES ('4', 'col_F4', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F4', '0');
INSERT INTO `label` VALUES ('5', 'col_F5', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F5', '0');
INSERT INTO `label` VALUES ('6', 'col_F6', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', '', 'cab_F6', '0');
INSERT INTO `label` VALUES ('7', 'col_F7', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F7', '0');
INSERT INTO `label` VALUES ('8', 'col_F8', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F8', '0');
INSERT INTO `label` VALUES ('9', 'col_F9', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F9', '0');
INSERT INTO `label` VALUES ('10', 'row_FL', '1', '0', '64', '64', '0', '57.5', '-36', '0', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F1', '0');
INSERT INTO `label` VALUES ('11', 'row_FR', '1', '0', '64', '64', '0', '57.5', '36', '0', '180', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_F9', '0');
INSERT INTO `label` VALUES ('12', 'card_F1', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F1_doorF', '0');
INSERT INTO `label` VALUES ('13', 'col_E1', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E1', '0');
INSERT INTO `label` VALUES ('14', 'col_E2', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E2', '0');
INSERT INTO `label` VALUES ('15', 'col_E3', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E3', '0');
INSERT INTO `label` VALUES ('16', 'col_E4', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E4', '0');
INSERT INTO `label` VALUES ('17', 'col_E5', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E5', '0');
INSERT INTO `label` VALUES ('18', 'col_E6', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E6', '0');
INSERT INTO `label` VALUES ('19', 'col_E7', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E7', '0');
INSERT INTO `label` VALUES ('20', 'col_E8', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E8', '0');
INSERT INTO `label` VALUES ('21', 'col_E9', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E9', '0');
INSERT INTO `label` VALUES ('22', 'col_D1', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D1', '0');
INSERT INTO `label` VALUES ('23', 'col_D2', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D2', '0');
INSERT INTO `label` VALUES ('24', 'col_D3', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D3', '0');
INSERT INTO `label` VALUES ('25', 'col_D4', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D4', '0');
INSERT INTO `label` VALUES ('26', 'col_D5', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D5', '0');
INSERT INTO `label` VALUES ('27', 'col_D6', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D6', '0');
INSERT INTO `label` VALUES ('28', 'col_D7', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D7', '0');
INSERT INTO `label` VALUES ('29', 'col_D8', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D8', '0');
INSERT INTO `label` VALUES ('30', 'col_D9', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D9', '0');
INSERT INTO `label` VALUES ('31', 'col_C1', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C1', '0');
INSERT INTO `label` VALUES ('32', 'col_C2', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C2', '0');
INSERT INTO `label` VALUES ('33', 'col_C3', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C3', '0');
INSERT INTO `label` VALUES ('34', 'col_C4', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C4', '0');
INSERT INTO `label` VALUES ('35', 'col_C5', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C5', '0');
INSERT INTO `label` VALUES ('36', 'col_C6', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C6', '0');
INSERT INTO `label` VALUES ('37', 'col_C7', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C7', '0');
INSERT INTO `label` VALUES ('38', 'col_C8', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C8', '0');
INSERT INTO `label` VALUES ('39', 'col_C9', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C9', '0');
INSERT INTO `label` VALUES ('40', 'col_B1', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B1', '0');
INSERT INTO `label` VALUES ('41', 'col_B2', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B2', '0');
INSERT INTO `label` VALUES ('42', 'col_B3', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B3', '0');
INSERT INTO `label` VALUES ('43', 'col_B4', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B4', '0');
INSERT INTO `label` VALUES ('44', 'col_B5', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B5', '0');
INSERT INTO `label` VALUES ('45', 'col_B6', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B6', '0');
INSERT INTO `label` VALUES ('46', 'col_B7', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B7', '0');
INSERT INTO `label` VALUES ('47', 'col_B8', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B8', '0');
INSERT INTO `label` VALUES ('48', 'col_B9', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B9', '0');
INSERT INTO `label` VALUES ('49', 'col_A1', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A1', '0');
INSERT INTO `label` VALUES ('50', 'col_A2', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A2', '0');
INSERT INTO `label` VALUES ('51', 'col_A3', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A3', '0');
INSERT INTO `label` VALUES ('52', 'col_A4', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A4', '0');
INSERT INTO `label` VALUES ('53', 'col_A5', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A5', '0');
INSERT INTO `label` VALUES ('54', 'col_A6', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A6', '0');
INSERT INTO `label` VALUES ('55', 'col_A7', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A7', '0');
INSERT INTO `label` VALUES ('56', 'col_A8', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A8', '0');
INSERT INTO `label` VALUES ('57', 'col_A9', '1', '0', '64', '64', '0', '107.5', '0', '-90', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A9', '0');
INSERT INTO `label` VALUES ('58', 'row_EL', '1', '0', '64', '64', '0', '57.5', '-36', '0', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E1', '0');
INSERT INTO `label` VALUES ('59', 'row_ER', '1', '0', '64', '64', '0', '57.5', '36', '0', '180', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_E9', '0');
INSERT INTO `label` VALUES ('61', 'row_DL', '1', '0', '64', '64', '0', '57.5', '-36', '0', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D1', '0');
INSERT INTO `label` VALUES ('62', 'row_DR', '1', '0', '64', '64', '0', '57.5', '36', '0', '180', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_D9', '0');
INSERT INTO `label` VALUES ('63', 'row_CL', '1', '0', '64', '64', '0', '57.5', '-36', '0', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C1', '0');
INSERT INTO `label` VALUES ('64', 'row_CR', '1', '0', '64', '64', '0', '57.5', '36', '0', '180', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_C9', '0');
INSERT INTO `label` VALUES ('65', 'row_BL', '1', '0', '64', '64', '0', '57.5', '-36', '0', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B1', '0');
INSERT INTO `label` VALUES ('66', 'row_BR', '1', '0', '64', '64', '0', '57.5', '36', '0', '180', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_B9', '0');
INSERT INTO `label` VALUES ('67', 'row_AL', '1', '0', '64', '64', '0', '57.5', '-36', '0', '0', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A1', '0');
INSERT INTO `label` VALUES ('68', 'row_AR', '1', '0', '64', '64', '0', '57.5', '36', '0', '180', '0', '0.6', '0.6', '1', '#2B4C8F', null, 'cab_A9', '0');
INSERT INTO `label` VALUES ('69', 'card_F2', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F2_doorF', '0');
INSERT INTO `label` VALUES ('70', 'card_F3', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F3_doorF', '0');
INSERT INTO `label` VALUES ('71', 'card_F4', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F4_doorF', '0');
INSERT INTO `label` VALUES ('72', 'card_F5', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F5_doorF', '0');
INSERT INTO `label` VALUES ('73', 'card_F6', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F6_doorF', '0');
INSERT INTO `label` VALUES ('74', 'card_F7', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F7_doorF', '0');
INSERT INTO `label` VALUES ('75', 'card_F8', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F8_doorF', '0');
INSERT INTO `label` VALUES ('76', 'card_F9', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_F9_doorF', '0');
INSERT INTO `label` VALUES ('77', 'card_E1', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E1_doorF', '0');
INSERT INTO `label` VALUES ('78', 'card_E2', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E2_doorF', '0');
INSERT INTO `label` VALUES ('79', 'card_E3', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E3_doorF', '0');
INSERT INTO `label` VALUES ('80', 'card_E4', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E4_doorF', '0');
INSERT INTO `label` VALUES ('81', 'card_E5', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E5_doorF', '0');
INSERT INTO `label` VALUES ('82', 'card_E6', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E6_doorF', '0');
INSERT INTO `label` VALUES ('83', 'card_E7', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E7_doorF', '0');
INSERT INTO `label` VALUES ('84', 'card_E8', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E8_doorF', '0');
INSERT INTO `label` VALUES ('85', 'card_E9', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_E9_doorF', '0');
INSERT INTO `label` VALUES ('86', 'card_D1', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D1_doorF', '0');
INSERT INTO `label` VALUES ('87', 'card_D2', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D2_doorF', '0');
INSERT INTO `label` VALUES ('88', 'card_D3', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D3_doorF', '0');
INSERT INTO `label` VALUES ('89', 'card_D4', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D4_doorF', '0');
INSERT INTO `label` VALUES ('90', 'card_D5', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D5_doorF', '0');
INSERT INTO `label` VALUES ('91', 'card_D6', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D6_doorF', '0');
INSERT INTO `label` VALUES ('92', 'card_D7', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D7_doorF', '0');
INSERT INTO `label` VALUES ('93', 'card_D8', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D8_doorF', '0');
INSERT INTO `label` VALUES ('94', 'card_D9', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_D9_doorF', '0');
INSERT INTO `label` VALUES ('95', 'card_C1', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C1_doorF', '0');
INSERT INTO `label` VALUES ('96', 'card_C2', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C2_doorF', '0');
INSERT INTO `label` VALUES ('97', 'card_C3', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C3_doorF', '0');
INSERT INTO `label` VALUES ('98', 'card_C4', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C4_doorF', '0');
INSERT INTO `label` VALUES ('99', 'card_C5', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C5_doorF', '0');
INSERT INTO `label` VALUES ('100', 'card_C6', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C6_doorF', '0');
INSERT INTO `label` VALUES ('101', 'card_C7', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C7_doorF', '0');
INSERT INTO `label` VALUES ('102', 'card_C8', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C8_doorF', '0');
INSERT INTO `label` VALUES ('103', 'card_C9', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_C9_doorF', '0');
INSERT INTO `label` VALUES ('104', 'card_B1', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B1_doorF', '0');
INSERT INTO `label` VALUES ('105', 'card_B2', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B2_doorF', '0');
INSERT INTO `label` VALUES ('106', 'card_B3', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B3_doorF', '0');
INSERT INTO `label` VALUES ('107', 'card_B4', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B4_doorF', '0');
INSERT INTO `label` VALUES ('108', 'card_B5', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B5_doorF', '0');
INSERT INTO `label` VALUES ('109', 'card_B6', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B6_doorF', '0');
INSERT INTO `label` VALUES ('110', 'card_B7', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B7_doorF', '0');
INSERT INTO `label` VALUES ('111', 'card_B8', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B8_doorF', '0');
INSERT INTO `label` VALUES ('112', 'card_B9', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_B9_doorF', '0');
INSERT INTO `label` VALUES ('113', 'card_A1', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A1_doorF', '0');
INSERT INTO `label` VALUES ('114', 'card_A2', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A2_doorF', '0');
INSERT INTO `label` VALUES ('115', 'card_A3', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A3_doorF', '0');
INSERT INTO `label` VALUES ('116', 'card_A4', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A4_doorF', '0');
INSERT INTO `label` VALUES ('117', 'card_A5', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A5_doorF', '0');
INSERT INTO `label` VALUES ('118', 'card_A6', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A6_doorF', '0');
INSERT INTO `label` VALUES ('119', 'card_A7', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A7_doorF', '0');
INSERT INTO `label` VALUES ('120', 'card_A8', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A8_doorF', '0');
INSERT INTO `label` VALUES ('121', 'card_A9', '1', '0', '256', '256', '-1', '70', '0', '0', '90', '0', '0.1', '0.1', '1', null, 'card.png', 'cab_A9_doorF', '0');
INSERT INTO `label` VALUES ('122', 'area_Prod', '1', '0', '512', '64', '-450', '12', '310', '90', '0', '0', '1', '1', '1', '#cccccc', null, null, '1');
INSERT INTO `label` VALUES ('123', 'area_Ctrl1', '1', '0', '512', '64', '250', '12', '310', '90', '0', '0', '1', '1', '1', '#cccccc', null, null, '1');
INSERT INTO `label` VALUES ('124', 'area_Ctrl2', '1', '0', '420', '64', '750', '12', '310', '90', '0', '0', '1', '1', '1', '#cccccc', null, null, '1');

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `skinColor` varchar(255) DEFAULT '',
  `imgurl` varchar(255) DEFAULT '',
  `isRepeat` tinyint(4) DEFAULT '0',
  `skin_up.skinColor` varchar(255) DEFAULT NULL,
  `skin_up.imgUrl` varchar(255) DEFAULT NULL,
  `skin_down.skinColor` varchar(255) DEFAULT NULL,
  `skin_down.imgUrl` varchar(255) DEFAULT NULL,
  `skin_left.skinColor` varchar(255) DEFAULT NULL,
  `skin_left.imgUrl` varchar(255) DEFAULT NULL,
  `skin_right.skinColor` varchar(255) DEFAULT NULL,
  `skin_right.imgUrl` varchar(255) DEFAULT NULL,
  `skin_fore.skinColor` varchar(255) DEFAULT NULL,
  `skin_fore.imgUrl` varchar(255) DEFAULT NULL,
  `skin_back.skinColor` varchar(255) DEFAULT NULL,
  `skin_back.imgUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('1', 'floor', '0x00b0e2', '', '1', '0x00B2EE', 'floor.jpg', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('2', null, '0x6699ff', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('3', null, '0x9933cc', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('4', null, '0xFF7F00', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('5', null, '0xFF7F00', '', '1', '0x00B2EE', 'grid_floor.png', '0x00B2EE', 'grid_floor.png', null, null, null, null, '0x00B2EE', 'grid_floor.png', null, null);
INSERT INTO `material` VALUES ('6', null, '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `material` VALUES ('7', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('8', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('9', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('10', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('11', null, '', '', '0', '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null);
INSERT INTO `material` VALUES ('12', null, '', '', '0', '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null);
INSERT INTO `material` VALUES ('13', null, '', '', '0', '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null);
INSERT INTO `material` VALUES ('14', null, '', '', '0', '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null, '0x00B2EE', null);
INSERT INTO `material` VALUES ('15', 'wall2', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `material` VALUES ('16', 'wall2-hole', '0xffdddd', '', '0', null, null, '0xdddddd', null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('17', 'wall2-doorL', '', '', '0', null, '', null, '', null, 'doorone_in.png', null, 'doorone_in_right.png', null, '', null, '');
INSERT INTO `material` VALUES ('18', 'wall2-doorR', '0x51443e', '', '0', null, null, null, null, null, 'doorone_in_right.png', null, 'doorone_in.png', null, null, null, null);
INSERT INTO `material` VALUES ('19', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('20', null, '0x00B2EE', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('21', 'wall3', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `material` VALUES ('22', 'wall3-doorHole', '0xffdddd', '', '0', null, null, '0xdddddd', null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('23', 'wall3-doorL', '0x51443e', '', '0', null, null, null, null, null, '', null, '', null, 'door_left.jpg', null, 'door_right.jpg');
INSERT INTO `material` VALUES ('24', 'wall3-doorRight', '0x51443e', '', '0', null, null, null, null, null, '', null, '', null, 'door_right.jpg', null, 'door_left.jpg');
INSERT INTO `material` VALUES ('25', null, '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `material` VALUES ('26', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('27', null, '0x00B2EE', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('28', null, '0x00B2EE', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('29', null, '0x00B2EE', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('30', null, '0x00B2EE', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('31', null, '0x51443e', '', '0', null, null, null, null, null, 'doorone_in.png', null, 'doorone_in.png', null, '', null, '');
INSERT INTO `material` VALUES ('32', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('33', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('34', null, '0xffdddd', '', '0', null, null, '0xdddddd', null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('35', null, '0x51443e', '', '0', null, null, null, null, null, 'doorone_in_right.png', null, 'doorone_in_right.png', null, '', null, '');
INSERT INTO `material` VALUES ('36', null, '0x51443e', '', '0', null, null, null, null, null, 'doorone_in.png', null, 'doorone_in.png', null, null, null, null);
INSERT INTO `material` VALUES ('37', null, '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `material` VALUES ('38', null, '', 'glass.png', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('39', null, '', 'glass.png', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('40', null, '', 'glass.png', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('41', null, '', 'glass.png', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('42', null, '', 'glass.png', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('43', null, '', 'glass.png', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('44', null, '0xfefefe', '', '0', null, '/aircondition_side.png', null, null, null, 'aircondition_side.png', null, 'aircondition_side.png', null, 'aircondition_front.png', null, null);
INSERT INTO `material` VALUES ('45', null, '0xfefefe', '', '0', null, null, null, null, null, null, null, null, null, 'aircondition.jpg', null, null);
INSERT INTO `material` VALUES ('46', null, '0xfefefe', '', '0', null, null, null, null, null, 'firedevice_side.png', null, 'firedevice_side.png', null, 'firedevice.png', null, null);
INSERT INTO `material` VALUES ('47', null, '', 'message.jpg', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('48', null, '0x8ac9e2', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('49', null, '0x8ac9e2', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('50', null, '0x8ac9e2', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('51', null, '0x8ac9e2', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('52', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('53', 'cabinet', '0x333333', '', '0', null, 'rack_door_back.jpg', null, 'rack_door_back.jpg', null, 'cabinet_side.png', null, 'cabinet_side.png', null, 'rack_door_back.jpg', null, 'rack_door_back.jpg');
INSERT INTO `material` VALUES ('54', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('55', 'cabinet-doorF', '0x333333', '', '0', null, null, null, null, null, null, null, null, null, 'rack_door_back.jpg', null, 'cabinet_door2.png');
INSERT INTO `material` VALUES ('56', 'cabinet-doorB', '0x333333', '', '0', null, null, null, null, null, null, null, null, null, 'rack_door_back.jpg', null, 'rack_door_back.jpg');
INSERT INTO `material` VALUES ('57', '1u_firewall', '0x92630b', '', '0', '', 'rack_inside.jpg', null, 'rack_inside.jpg', null, '', null, '', null, './device/1u_firewall.jpg', null, '');
INSERT INTO `material` VALUES ('58', '1u_fwdiso', '', '', '0', null, null, null, null, null, null, null, null, null, './device/1u_fwdiso.jpg', null, null);
INSERT INTO `material` VALUES ('59', '1u_kvm', '', '', '0', null, null, null, null, null, null, null, null, null, './device/1u_kvm.jpg', null, null);
INSERT INTO `material` VALUES ('60', '1u_switch', '', '', '0', null, null, null, null, null, null, null, null, null, './device/1u_switch.jpg', null, null);
INSERT INTO `material` VALUES ('61', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('62', '2u_detector', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_detector.jpg', null, null);
INSERT INTO `material` VALUES ('63', '2u_dframe', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_dframe.jpg', null, null);
INSERT INTO `material` VALUES ('64', '2u_enc', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_enc.jpg', null, null);
INSERT INTO `material` VALUES ('65', '2u_encmgr', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_encmgr.jpg', null, null);
INSERT INTO `material` VALUES ('66', '2u_firewall', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_firewall.jpg', null, null);
INSERT INTO `material` VALUES ('67', '2u_lizhuo', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_lizhuo.jpg', null, null);
INSERT INTO `material` VALUES ('68', '2u_routepwr', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_routepwr.jpg', null, null);
INSERT INTO `material` VALUES ('69', '2u_rvsiso', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_rvsiso.jpg', null, null);
INSERT INTO `material` VALUES ('70', '2u_server', '', '', '0', null, null, null, null, null, null, null, null, null, './device/2u_server.jpg', null, null);
INSERT INTO `material` VALUES ('71', '3u_raid', '', '', '0', null, null, null, null, null, null, null, null, null, './device/3u_raid.jpg', null, null);
INSERT INTO `material` VALUES ('72', '3u_server', '', '', '0', null, null, null, null, null, null, null, null, null, './device/3u_server.jpg', null, null);
INSERT INTO `material` VALUES ('73', '4u_clock', '', '', '0', null, null, null, null, null, null, null, null, null, './device/4u_clock.jpg', null, null);
INSERT INTO `material` VALUES ('74', '4u_server', '', '', '0', null, null, null, null, null, null, null, null, null, './device/4u_server.jpg', null, null);
INSERT INTO `material` VALUES ('75', '4u_worksta', '', '', '0', null, null, null, null, null, null, null, null, null, './device/4u_worksta.jpg', null, null);
INSERT INTO `material` VALUES ('76', '21u_router_h3c', '', '', '0', null, null, null, null, null, null, null, null, null, './device/21u_router_h3c.jpg', null, null);
INSERT INTO `material` VALUES ('77', '21u_router_zx', '', '', '0', null, null, null, null, null, null, null, null, null, './device/21u_router_zx.jpg', null, null);
INSERT INTO `material` VALUES ('78', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `material` VALUES ('79', null, '', '', '0', null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for mesh
-- ----------------------------
DROP TABLE IF EXISTS `mesh`;
CREATE TABLE `mesh` (
  `uuid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `isShow` tinyint(4) DEFAULT NULL,
  `length` double DEFAULT '0',
  `width` double DEFAULT '0',
  `height` double DEFAULT '0',
  `pos_x` double DEFAULT '0',
  `pos_y` double DEFAULT '0',
  `pos_z` double DEFAULT '0',
  `rot_x` double DEFAULT '0',
  `rot_y` double DEFAULT '0',
  `rot_z` double DEFAULT '0',
  `scl_x` double DEFAULT '1',
  `scl_y` double DEFAULT '1',
  `scl_z` double DEFAULT '1',
  `transparent` tinyint(4) DEFAULT '0',
  `opacity` double DEFAULT '1',
  `material` int(11) DEFAULT '0',
  `parent` varchar(255) DEFAULT '',
  `opcode` varchar(255) DEFAULT '',
  `isDirty` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mesh
-- ----------------------------
INSERT INTO `mesh` VALUES ('1', 'floor', 'floor', '1', '2080', '1300', '10', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '', '', '0');
INSERT INTO `mesh` VALUES ('2', 'area1', 'floor', '1', '860', '750', '0.2', '-430', '5.5', '-110', '0', '0', '0', '0', '0', '0', '0', '1', '2', '', '', '0');
INSERT INTO `mesh` VALUES ('3', 'area2', 'floor', '1', '540', '750', '0.2', '270', '5.5', '-110', '0', '0', '0', '0', '0', '0', '0', '1', '3', '', '', '0');
INSERT INTO `mesh` VALUES ('4', 'area3', 'floor', '1', '300', '750', '0.2', '690', '5.5', '-110', '0', '0', '0', '0', '0', '0', '0', '1', '4', '', '', '0');
INSERT INTO `mesh` VALUES ('5', 'area_de', 'floor', '1', '210', '656', '0.2', '262', '6.5', '-108', '0', '0', '0', '0', '0', '0', '0', '1', '5', '', '', '0');
INSERT INTO `mesh` VALUES ('6', 'wall1', 'wall', '1', '2060', '20', '240', '0', '120', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '6', '', '', '0');
INSERT INTO `mesh` VALUES ('7', 'windowHole1', 'hole', '1', '250', '20', '160', '-693', '130', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '7', 'wall1', '-', '0');
INSERT INTO `mesh` VALUES ('8', 'windowHole2', 'hole', '1', '250', '20', '160', '-231', '130', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '8', 'wall1', '-', '0');
INSERT INTO `mesh` VALUES ('9', 'windowHole3', 'hole', '1', '250', '20', '160', '231', '130', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '9', 'wall1', '-', '0');
INSERT INTO `mesh` VALUES ('10', 'windowHole4', 'hole', '1', '250', '20', '160', '693', '130', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '10', 'wall1', '-', '0');
INSERT INTO `mesh` VALUES ('11', 'windowCaseBottom1', 'cube', '1', '250', '30', '10', '-693', '50', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '11', '', '', '0');
INSERT INTO `mesh` VALUES ('12', 'windowCaseBottom2', 'cube', '1', '250', '30', '10', '-231', '50', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '12', '', '', '0');
INSERT INTO `mesh` VALUES ('13', 'windowCaseBottom3', 'cube', '1', '250', '30', '10', '231', '50', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '13', '', '', '0');
INSERT INTO `mesh` VALUES ('14', 'windowCaseBottom4', 'cube', '1', '250', '30', '10', '693', '50', '-640', '0', '0', '0', '0', '0', '0', '0', '1', '14', '', '', '0');
INSERT INTO `mesh` VALUES ('15', 'wall2', 'wall', '1', '2060', '20', '240', '0', '120', '640', '0', '0', '0', '0', '0', '0', '0', '1', '15', '', '', '0');
INSERT INTO `mesh` VALUES ('16', 'wall2-doorhole1', 'hole', '1', '208', '20', '220', '565', '110', '640', '0', '0', '0', '0', '0', '0', '0', '1', '16', 'wall2', '-', '0');
INSERT INTO `mesh` VALUES ('17', 'wall2-doorLeft', 'cube', '1', '104', '4', '210', '617', '112', '640', '0', '0', '0', '0', '0', '0', '0', '1', '17', '', '', '0');
INSERT INTO `mesh` VALUES ('18', 'wall2-doorRight', 'cube', '1', '104', '4', '210', '513', '112', '640', '0', '0', '0', '0', '0', '0', '0', '1', '18', '', '', '0');
INSERT INTO `mesh` VALUES ('19', 'wall2-windowHole1', 'hole', '1', '250', '20', '160', '-231', '130', '640', '0', '0', '0', '0', '0', '0', '0', '1', '19', 'wall2', '-', '0');
INSERT INTO `mesh` VALUES ('20', 'wall2-windowCaseBottom1', 'cube', '1', '250', '30', '10', '-231', '50', '640', '0', '0', '0', '0', '0', '0', '0', '1', '20', '', '', '0');
INSERT INTO `mesh` VALUES ('21', 'wall3', 'wall', '1', '20', '1300', '240', '1030', '120', '0', '0', '0', '0', '0', '0', '0', '0', '1', '21', '', '', '0');
INSERT INTO `mesh` VALUES ('22', 'wall3-doorhole', 'hole', '1', '20', '208', '220', '1030', '120', '486', '0', '0', '0', '0', '0', '0', '0', '1', '22', 'wall3', '-', '0');
INSERT INTO `mesh` VALUES ('23', 'wall3-doorL', 'cube', '1', '4', '104', '216', '1032', '120', '538', '0', '0', '0', '0', '0', '0', '0', '0.1', '23', '', '', '0');
INSERT INTO `mesh` VALUES ('24', 'wall3-doorRight', 'cube', '1', '4', '104', '216', '1030', '120', '434', '0', '0', '0', '0', '0', '0', '0', '1', '24', '', '', '0');
INSERT INTO `mesh` VALUES ('25', 'wall4', 'wall', '1', '20', '1300', '240', '-1030', '120', '0', '0', '0', '0', '0', '0', '0', '0', '1', '25', '', '', '0');
INSERT INTO `mesh` VALUES ('26', 'wall4-doorhole1', 'hole', '1', '20', '104', '220', '-1030', '110', '518', '0', '0', '0', '0', '0', '0', '0', '1', '26', 'wall4', '-', '0');
INSERT INTO `mesh` VALUES ('27', 'wall4-doorCaseRight', 'cube', '1', '20', '5', '220', '-1030', '110', '570', '0', '0', '0', '0', '0', '0', '0', '1', '27', '', '', '0');
INSERT INTO `mesh` VALUES ('28', 'wall4-doorCaseLeft', 'cube', '1', '20', '5', '220', '-1030', '110', '466', '0', '0', '0', '0', '0', '0', '0', '1', '28', '', '', '0');
INSERT INTO `mesh` VALUES ('29', 'wall4-doorCaseTop', 'cube', '1', '20', '108', '5', '-1030', '220', '518', '0', '0', '0', '0', '0', '0', '0', '1', '29', '', '', '0');
INSERT INTO `mesh` VALUES ('30', 'wall4-doorCaseBottom', 'cube', '1', '20', '175', '5', '-1030', '5', '485', '0', '0', '0', '0', '0', '0', '0', '1', '30', '', '', '0');
INSERT INTO `mesh` VALUES ('31', 'wall4-doorRight', 'cube', '1', '100', '4', '210', '-1032', '112', '518', '0', '90', '0', '0', '0', '0', '0', '1', '31', '', '', '0');
INSERT INTO `mesh` VALUES ('32', 'wall4-windowHole', 'hole', '1', '20', '1095', '200', '-1030', '120', '-82.5', '0', '0', '0', '0', '0', '0', '0', '1', '32', 'wall4', '-', '0');
INSERT INTO `mesh` VALUES ('33', 'wall5', 'wall', '1', '620', '20', '240', '720', '120', '370', '0', '0', '0', '0', '0', '0', '0', '1', '33', '', '', '0');
INSERT INTO `mesh` VALUES ('34', 'wall5-doorhole', 'hole', '1', '210', '20', '220', '565', '110', '370', '0', '0', '0', '0', '0', '0', '0', '1', '34', 'wall5', '-', '0');
INSERT INTO `mesh` VALUES ('35', 'wall5-doorRight', 'cube', '1', '104', '4', '210', '-205', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '35', 'wall5', '', '0');
INSERT INTO `mesh` VALUES ('36', 'wall5-doorLeft', 'cube', '1', '104', '4', '210', '-101', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '36', 'wall5', '', '0');
INSERT INTO `mesh` VALUES ('37', 'wall6', 'wall', '1', '20', '290', '240', '400', '120', '505', '0', '0', '0', '0', '0', '0', '0', '1', '37', '', '', '0');
INSERT INTO `mesh` VALUES ('38', 'wall2-glass1', 'glass', '1', '0', '250', '160', '-693', '130', '-640', '0', '0', '0', '0', '0', '0', '1', '1', '38', '', '', '0');
INSERT INTO `mesh` VALUES ('39', 'wall2-glass2', 'glass', '1', '0', '250', '160', '-231', '130', '-640', '0', '0', '0', '0', '0', '0', '1', '1', '39', '', '', '0');
INSERT INTO `mesh` VALUES ('40', 'wall2-glass3', 'glass', '1', '0', '250', '160', '231', '130', '-640', '0', '0', '0', '0', '0', '0', '1', '1', '40', '', '', '0');
INSERT INTO `mesh` VALUES ('41', 'wall2-glass4', 'glass', '1', '0', '250', '160', '693', '130', '-640', '0', '0', '0', '0', '0', '0', '1', '1', '41', '', '', '0');
INSERT INTO `mesh` VALUES ('42', 'wall1-glass1', 'glass', '1', '0', '250', '160', '-231', '130', '640', '0', '0', '0', '0', '0', '0', '1', '1', '42', '', '', '0');
INSERT INTO `mesh` VALUES ('43', 'wall4-glass1', 'glass', '1', '0', '1095', '200', '-1030', '120', '-93', '0', '90', '0', '0', '0', '0', '1', '1', '43', '', '', '0');
INSERT INTO `mesh` VALUES ('44', 'aircondition1', 'cube', '0', '60', '150', '220', '-450', '110', '-595', '0', '-90', '0', '0', '0', '0', '0', '1', '44', '', '', '1');
INSERT INTO `mesh` VALUES ('45', 'aircondition3', 'cube', '1', '60', '60', '220', '-880', '110', '-595', '0', '-90', '0', '0', '0', '0', '0', '1', '45', '', '', '0');
INSERT INTO `mesh` VALUES ('46', 'firedevice1', 'cube', '0', '60', '80', '220', '-650', '110', '-595', '0', '-90', '0', '0', '0', '0', '0', '1', '46', '', '', '1');
INSERT INTO `mesh` VALUES ('47', 'messagePanel', 'plane', '1', '0', '100', '160', '388', '150', '495', '0', '-90', '0', '0', '1', '0', '1', '1', '47', '', '', '0');
INSERT INTO `mesh` VALUES ('48', 'cylinder21', 'cube', '1', '100', '50', '242', '0', '120', '656', '0', '0', '0', '0', '0', '0', '0', '1', '48', '', '', '0');
INSERT INTO `mesh` VALUES ('49', 'cylinder22', 'cube', '1', '100', '50', '242', '-950', '120', '656', '0', '0', '0', '0', '0', '0', '0', '1', '49', '', '', '0');
INSERT INTO `mesh` VALUES ('50', 'cylinder11', 'cube', '1', '50', '50', '242', '0', '120', '-624', '0', '0', '0', '0', '0', '0', '0', '1', '50', '', '', '0');
INSERT INTO `mesh` VALUES ('51', 'cylinder12', 'cube', '1', '50', '50', '242', '-950', '120', '-624', '0', '0', '0', '0', '0', '0', '0', '1', '51', '', '', '0');
INSERT INTO `mesh` VALUES ('52', 'cylinder13', 'cube', '1', '50', '50', '242', '1014', '120', '-624', '0', '0', '0', '0', '0', '0', '0', '1', '52', '', '', '0');
INSERT INTO `mesh` VALUES ('53', 'cabinet11', 'cube', '0', '80', '70', '215', '680', '113', '-400', '0', '0', '0', '0', '0', '0', '0', '1', '53', '', '', '1');
INSERT INTO `mesh` VALUES ('54', 'hole', 'hole', '0', '80', '66', '211', '680', '113', '-400', '0', '0', '0', '0', '0', '0', '0', '1', '54', 'cab_F1', '-', '1');
INSERT INTO `mesh` VALUES ('55', 'doorFront', 'cube', '0', '2', '66', '211', '-40', '0', '0', '0', '0', '0', '1', '1', '1', '0', '1', '55', 'cab_F1', '', '1');
INSERT INTO `mesh` VALUES ('56', 'doorBack', 'cube', '0', '2', '66', '211', '40', '0', '0', '0', '0', '0', '1', '1', '1', '0', '1', '56', 'cab_F1', '', '1');

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES ('1', '1', '黄山市调度主站', '');

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `isShow` tinyint(4) DEFAULT '0',
  `length` double DEFAULT NULL,
  `width` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `pos_x` double DEFAULT NULL,
  `pos_y` double DEFAULT NULL,
  `pos_z` double DEFAULT NULL,
  `rot_x` double DEFAULT NULL,
  `rot_y` double DEFAULT NULL,
  `rot_z` double DEFAULT NULL,
  `scl_x` double DEFAULT NULL,
  `scl_y` double DEFAULT NULL,
  `scl_z` double DEFAULT NULL,
  `transparent` double DEFAULT NULL,
  `opacity` double DEFAULT NULL,
  `material` int(11) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `opcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('1', 'cabinet', 'cube', '1', '80', '70', '215', null, null, null, null, null, null, null, null, null, '0', '1', '53', null, null);
INSERT INTO `template` VALUES ('2', 'hole', 'hole', '1', '80', '66', '211', null, null, null, null, null, null, null, null, null, '0', '1', '54', 'cabinet', '-');
INSERT INTO `template` VALUES ('3', 'doorF', 'cube', '1', '2', '66', '211', '-40', null, null, null, null, null, null, null, null, '0', '1', '55', 'cabinet', null);
INSERT INTO `template` VALUES ('4', 'doorB', 'cube', '1', '2', '66', '211', '40', null, null, null, null, null, null, null, null, '0', '1', '56', 'cabinet', null);
INSERT INTO `template` VALUES ('5', 'firedevice', 'cube', '1', '60', '80', '220', null, null, null, null, null, null, null, null, null, '0', '1', '46', null, null);
INSERT INTO `template` VALUES ('6', 'aircondition', 'cube', '1', '60', '150', '220', null, null, null, null, null, null, null, null, null, '0', '1', '44', null, null);
INSERT INTO `template` VALUES ('7', '1u_firewall', 'cube', '1', '60', '65', '4.5', '0', '0', '0', null, null, null, null, null, null, '0', '1', '57', null, null);
INSERT INTO `template` VALUES ('8', '1u_fwdiso', 'cube', '1', '60', '65', '4.5', null, null, null, null, null, null, null, null, null, null, null, '58', null, null);
INSERT INTO `template` VALUES ('9', '1u_kvm', 'cube', '1', '60', '65', '4.5', null, null, null, null, null, null, null, null, null, null, null, '59', null, null);
INSERT INTO `template` VALUES ('10', '1u_switch', 'cube', '1', '60', '65', '4.5', null, null, null, null, null, null, null, null, null, null, null, '60', null, null);
INSERT INTO `template` VALUES ('12', '2u_detector', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '62', null, null);
INSERT INTO `template` VALUES ('13', '2u_dframe', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '63', null, null);
INSERT INTO `template` VALUES ('14', '2u_enc', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '64', null, null);
INSERT INTO `template` VALUES ('15', '2u_encmgr', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '65', null, null);
INSERT INTO `template` VALUES ('16', '2u_firewall', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '66', null, null);
INSERT INTO `template` VALUES ('17', '2u_lizhuo', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '67', null, null);
INSERT INTO `template` VALUES ('18', '2u_routerpwr', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '68', null, null);
INSERT INTO `template` VALUES ('19', '2u_rvsiso', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '69', null, null);
INSERT INTO `template` VALUES ('20', '2u_server', 'cube', '1', '60', '65', '9', null, null, null, null, null, null, null, null, null, null, null, '70', null, null);
INSERT INTO `template` VALUES ('21', '3u_raid', 'cube', '1', '60', '65', '13.5', null, null, null, null, null, null, null, null, null, null, null, '71', null, null);
INSERT INTO `template` VALUES ('22', '3u_server', 'cube', '1', '60', '65', '13.5', null, null, null, null, null, null, null, null, null, null, null, '72', null, null);
INSERT INTO `template` VALUES ('23', '4u_clock', 'cube', '1', '60', '65', '18', null, null, null, null, null, null, null, null, null, null, null, '73', null, null);
INSERT INTO `template` VALUES ('24', '4u_server', 'cube', '1', '60', '65', '18', null, null, null, null, null, null, null, null, null, null, null, '74', null, null);
INSERT INTO `template` VALUES ('25', '4u_worksta', 'cube', '1', '60', '65', '18', null, null, null, null, null, null, null, null, null, null, null, '75', null, null);
INSERT INTO `template` VALUES ('26', '14u_router', 'cube', '1', '60', '65', '63', null, null, null, null, null, null, null, null, null, null, null, '76', null, null);
INSERT INTO `template` VALUES ('27', '21u_router_h3c', 'cube', '1', '60', '65', '94.5', null, null, null, null, null, null, null, null, null, null, null, '77', null, null);
INSERT INTO `template` VALUES ('28', '21u_router_zx', 'cube', '1', '60', '65', '94.5', null, null, null, null, null, null, null, null, null, null, null, '78', null, null);
INSERT INTO `template` VALUES ('29', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '79', null, null);

-- ----------------------------
-- Table structure for text
-- ----------------------------
DROP TABLE IF EXISTS `text`;
CREATE TABLE `text` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `isShow` tinyint(4) DEFAULT NULL,
  `font` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT '\0\0\0',
  `text` varchar(255) DEFAULT NULL,
  `labelIds` varchar(255) DEFAULT '',
  `offX` double DEFAULT NULL,
  `offY` double DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of text
-- ----------------------------
INSERT INTO `text` VALUES ('1', 'F1', '1', 'Bold 48px serif', '#FFFFFF', 'F1', '1', '32', '32');
INSERT INTO `text` VALUES ('2', 'F2', '1', 'Bold 48px serif', '#FFFFFF', 'F2', '2', '32', '32');
INSERT INTO `text` VALUES ('3', 'F3', '1', 'Bold 48px serif', '#FFFFFF', 'F3', '3', '32', '32');
INSERT INTO `text` VALUES ('4', 'F4', '1', 'Bold 48px serif', '#FFFFFF', 'F4', '4', '32', '32');
INSERT INTO `text` VALUES ('5', 'F5', '1', 'Bold 48px serif', '#FFFFFF', 'F5', '5', '32', '32');
INSERT INTO `text` VALUES ('6', 'F6', '1', 'Bold 48px serif', '#FFFFFF', 'F6', '6', '32', '32');
INSERT INTO `text` VALUES ('7', 'F7', '1', 'Bold 48px serif', '#FFFFFF', 'F7', '7', '32', '32');
INSERT INTO `text` VALUES ('8', 'F8', '1', 'Bold 48px serif', '#FFFFFF', 'F8', '8', '32', '32');
INSERT INTO `text` VALUES ('9', 'F9', '1', 'Bold 48px serif', '#FFFFFF', 'F9', '9', '32', '32');
INSERT INTO `text` VALUES ('10', 'F', '1', 'Bold 48px serif', '#FFFFFF', 'F', '10,11', '32', '32');
INSERT INTO `text` VALUES ('11', 'F-1', '1', '24px serif', '#143A3F', 'F-1', '12', '128', '90');
INSERT INTO `text` VALUES ('12', '配网安全接入I', '1', 'Bold 26px serif', '#143A3F', '配网安全接入I', '69-121', '128', '130');
INSERT INTO `text` VALUES ('13', '生产控制大区', '1', '24px serif', '#143A3F', '生产控制大区', '69-121', '128', '170');
INSERT INTO `text` VALUES ('14', 'E1', '1', 'Bold 48px serif', '#FFFFFF', 'E1', '13', '32', '32');
INSERT INTO `text` VALUES ('15', 'E2', '1', 'Bold 48px serif', '#FFFFFF', 'E2', '14', '32', '32');
INSERT INTO `text` VALUES ('16', 'E3', '1', 'Bold 48px serif', '#FFFFFF', 'E3', '15', '32', '32');
INSERT INTO `text` VALUES ('17', 'E4', '1', 'Bold 48px serif', '#FFFFFF', 'E4', '16', '32', '32');
INSERT INTO `text` VALUES ('18', 'E5', '1', 'Bold 48px serif', '#FFFFFF', 'E5', '17', '32', '32');
INSERT INTO `text` VALUES ('19', 'E6', '1', 'Bold 48px serif', '#FFFFFF', 'E6', '18', '32', '32');
INSERT INTO `text` VALUES ('20', 'E7', '1', 'Bold 48px serif', '#FFFFFF', 'E7', '19', '32', '32');
INSERT INTO `text` VALUES ('21', 'E8', '1', 'Bold 48px serif', '#FFFFFF', 'E8', '20', '32', '32');
INSERT INTO `text` VALUES ('22', 'E9', '1', 'Bold 48px serif', '#FFFFFF', 'E9', '21', '32', '32');
INSERT INTO `text` VALUES ('23', 'D1', '1', 'Bold 48px serif', '#FFFFFF', 'D1', '22', '32', '32');
INSERT INTO `text` VALUES ('24', 'D2', '1', 'Bold 48px serif', '#FFFFFF', 'D2', '23', '32', '32');
INSERT INTO `text` VALUES ('25', 'D3', '1', 'Bold 48px serif', '#FFFFFF', 'D3', '24', '32', '32');
INSERT INTO `text` VALUES ('26', 'D4', '1', 'Bold 48px serif', '#FFFFFF', 'D4', '25', '32', '32');
INSERT INTO `text` VALUES ('27', 'D5', '1', 'Bold 48px serif', '#FFFFFF', 'D5', '26', '32', '32');
INSERT INTO `text` VALUES ('28', 'D6', '1', 'Bold 48px serif', '#FFFFFF', 'D6', '27', '32', '32');
INSERT INTO `text` VALUES ('29', 'D7', '1', 'Bold 48px serif', '#FFFFFF', 'D7', '28', '32', '32');
INSERT INTO `text` VALUES ('30', 'D8', '1', 'Bold 48px serif', '#FFFFFF', 'D8', '29', '32', '32');
INSERT INTO `text` VALUES ('31', 'D9', '1', 'Bold 48px serif', '#FFFFFF', 'D9', '30', '32', '32');
INSERT INTO `text` VALUES ('32', 'C1', '1', 'Bold 48px serif', '#FFFFFF', 'C1', '31', '32', '32');
INSERT INTO `text` VALUES ('33', 'C2', '1', 'Bold 48px serif', '#FFFFFF', 'C2', '32', '32', '32');
INSERT INTO `text` VALUES ('34', 'C3', '1', 'Bold 48px serif', '#FFFFFF', 'C3', '33', '32', '32');
INSERT INTO `text` VALUES ('35', 'C4', '1', 'Bold 48px serif', '#FFFFFF', 'C4', '34', '32', '32');
INSERT INTO `text` VALUES ('36', 'C5', '1', 'Bold 48px serif', '#FFFFFF', 'C5', '35', '32', '32');
INSERT INTO `text` VALUES ('37', 'C6', '1', 'Bold 48px serif', '#FFFFFF', 'C6', '36', '32', '32');
INSERT INTO `text` VALUES ('38', 'C7', '1', 'Bold 48px serif', '#FFFFFF', 'C7', '37', '32', '32');
INSERT INTO `text` VALUES ('39', 'C8', '1', 'Bold 48px serif', '#FFFFFF', 'C8', '38', '32', '32');
INSERT INTO `text` VALUES ('40', 'C9', '1', 'Bold 48px serif', '#FFFFFF', 'C9', '39', '32', '32');
INSERT INTO `text` VALUES ('41', 'B1', '1', 'Bold 48px serif', '#FFFFFF', 'B1', '40', '32', '32');
INSERT INTO `text` VALUES ('42', 'B2', '1', 'Bold 48px serif', '#FFFFFF', 'B2', '41', '32', '32');
INSERT INTO `text` VALUES ('43', 'B3', '1', 'Bold 48px serif', '#FFFFFF', 'B3', '42', '32', '32');
INSERT INTO `text` VALUES ('44', 'B4', '1', 'Bold 48px serif', '#FFFFFF', 'B4', '43', '32', '32');
INSERT INTO `text` VALUES ('45', 'B5', '1', 'Bold 48px serif', '#FFFFFF', 'B5', '44', '32', '32');
INSERT INTO `text` VALUES ('46', 'B6', '1', 'Bold 48px serif', '#FFFFFF', 'B6', '45', '32', '32');
INSERT INTO `text` VALUES ('47', 'B7', '1', 'Bold 48px serif', '#FFFFFF', 'B7', '46', '32', '32');
INSERT INTO `text` VALUES ('48', 'B8', '1', 'Bold 48px serif', '#FFFFFF', 'B8', '47', '32', '32');
INSERT INTO `text` VALUES ('49', 'B9', '1', 'Bold 48px serif', '#FFFFFF', 'B9', '48', '32', '32');
INSERT INTO `text` VALUES ('50', 'A1', '1', 'Bold 48px serif', '#FFFFFF', 'A1', '49', '32', '32');
INSERT INTO `text` VALUES ('51', 'A2', '1', 'Bold 48px serif', '#FFFFFF', 'A2', '50', '32', '32');
INSERT INTO `text` VALUES ('52', 'A3', '1', 'Bold 48px serif', '#FFFFFF', 'A3', '51', '32', '32');
INSERT INTO `text` VALUES ('53', 'A4', '1', 'Bold 48px serif', '#FFFFFF', 'A4', '52', '32', '32');
INSERT INTO `text` VALUES ('54', 'A5', '1', 'Bold 48px serif', '#FFFFFF', 'A5', '53', '32', '32');
INSERT INTO `text` VALUES ('55', 'A6', '1', 'Bold 48px serif', '#FFFFFF', 'A6', '54', '32', '32');
INSERT INTO `text` VALUES ('56', 'A7', '1', 'Bold 48px serif', '#FFFFFF', 'A7', '55', '32', '32');
INSERT INTO `text` VALUES ('57', 'A8', '1', 'Bold 48px serif', '#FFFFFF', 'A8', '56', '32', '32');
INSERT INTO `text` VALUES ('58', 'A9', '1', 'Bold 48px serif', '#FFFFFF', 'A9', '57', '32', '32');
INSERT INTO `text` VALUES ('59', 'E', '1', 'Bold 48px serif', '#FFFFFF', 'E', '58,59', '32', '32');
INSERT INTO `text` VALUES ('60', 'D', '1', 'Bold 48px serif', '#FFFFFF', 'D', '61,62', '32', '32');
INSERT INTO `text` VALUES ('61', 'C', '1', 'Bold 48px serif', '#FFFFFF', 'C', '63,64', '32', '32');
INSERT INTO `text` VALUES ('62', 'B', '1', 'Bold 48px serif', '#FFFFFF', 'B', '65,66', '32', '32');
INSERT INTO `text` VALUES ('63', 'A', '1', 'Bold 48px serif', '#FFFFFF', 'A', '67,68', '32', '32');
INSERT INTO `text` VALUES ('64', 'F-2', '1', '24px serif', '#143A3F', 'F-2', '69', '128', '90');
INSERT INTO `text` VALUES ('65', 'F-3', '1', '24px serif', '#143A3F', 'F-3', '70', '128', '90');
INSERT INTO `text` VALUES ('66', 'F-4', '1', '24px serif', '#143A3F', 'F-4', '71', '128', '90');
INSERT INTO `text` VALUES ('67', 'F-5', '1', '24px serif', '#143A3F', 'F-5', '72', '128', '90');
INSERT INTO `text` VALUES ('68', 'F-6', '1', '24px serif', '#143A3F', 'F-6', '73', '128', '90');
INSERT INTO `text` VALUES ('69', 'F-7', '1', '24px serif', '#143A3F', 'F-7', '74', '128', '90');
INSERT INTO `text` VALUES ('70', 'F-8', '1', '24px serif', '#143A3F', 'F-8', '75', '128', '90');
INSERT INTO `text` VALUES ('71', 'F-9', '1', '24px serif', '#143A3F', 'F-9', '76', '128', '90');
INSERT INTO `text` VALUES ('72', 'E-1', '1', '24px serif', '#143A3F', 'E-1', '77', '128', '90');
INSERT INTO `text` VALUES ('73', 'E-2', '1', '24px serif', '#143A3F', 'E-2', '78', '128', '90');
INSERT INTO `text` VALUES ('74', 'E-3', '1', '24px serif', '#143A3F', 'E-3', '79', '128', '90');
INSERT INTO `text` VALUES ('75', 'E-4', '1', '24px serif', '#143A3F', 'E-4', '80', '128', '90');
INSERT INTO `text` VALUES ('76', 'E-5', '1', '24px serif', '#143A3F', 'E-5', '81', '128', '90');
INSERT INTO `text` VALUES ('77', 'E-6', '1', '24px serif', '#143A3F', 'E-6', '82', '128', '90');
INSERT INTO `text` VALUES ('78', 'E-7', '1', '24px serif', '#143A3F', 'E-7', '83', '128', '90');
INSERT INTO `text` VALUES ('79', 'E-8', '1', '24px serif', '#143A3F', 'E-8', '84', '128', '90');
INSERT INTO `text` VALUES ('80', 'E-9', '1', '24px serif', '#143A3F', 'E-9', '85', '128', '90');
INSERT INTO `text` VALUES ('81', 'D-1', '1', '24px serif', '#143A3F', 'D-1', '86', '128', '90');
INSERT INTO `text` VALUES ('82', 'D-2', '1', '24px serif', '#143A3F', 'D-2', '87', '128', '90');
INSERT INTO `text` VALUES ('83', 'D-3', '1', '24px serif', '#143A3F', 'D-3', '88', '128', '90');
INSERT INTO `text` VALUES ('84', 'D-4', '1', '24px serif', '#143A3F', 'D-4', '89', '128', '90');
INSERT INTO `text` VALUES ('85', 'D-5', '1', '24px serif', '#143A3F', 'D-5', '90', '128', '90');
INSERT INTO `text` VALUES ('86', 'D-6', '1', '24px serif', '#143A3F', 'D-6', '91', '128', '90');
INSERT INTO `text` VALUES ('87', 'D-7', '1', '24px serif', '#143A3F', 'D-7', '92', '128', '90');
INSERT INTO `text` VALUES ('88', 'D-8', '1', '24px serif', '#143A3F', 'D-8', '93', '128', '90');
INSERT INTO `text` VALUES ('89', 'D-9', '1', '24px serif', '#143A3F', 'D-9', '94', '128', '90');
INSERT INTO `text` VALUES ('90', 'C-1', '1', '24px serif', '#143A3F', 'C-1', '95', '128', '90');
INSERT INTO `text` VALUES ('91', 'C-2', '1', '24px serif', '#143A3F', 'C-2', '96', '128', '90');
INSERT INTO `text` VALUES ('92', 'C-3', '1', '24px serif', '#143A3F', 'C-3', '97', '128', '90');
INSERT INTO `text` VALUES ('93', 'C-4', '1', '24px serif', '#143A3F', 'C-4', '98', '128', '90');
INSERT INTO `text` VALUES ('94', 'C-5', '1', '24px serif', '#143A3F', 'C-5', '99', '128', '90');
INSERT INTO `text` VALUES ('95', 'C-6', '1', '24px serif', '#143A3F', 'C-6', '100', '128', '90');
INSERT INTO `text` VALUES ('96', 'C-7', '1', '24px serif', '#143A3F', 'C-7', '101', '128', '90');
INSERT INTO `text` VALUES ('97', 'C-8', '1', '24px serif', '#143A3F', 'C-8', '102', '128', '90');
INSERT INTO `text` VALUES ('98', 'C-9', '1', '24px serif', '#143A3F', 'C-9', '103', '128', '90');
INSERT INTO `text` VALUES ('99', 'B-1', '1', '24px serif', '#143A3F', 'B-1', '104', '128', '90');
INSERT INTO `text` VALUES ('100', 'B-2', '1', '24px serif', '#143A3F', 'B-2', '105', '128', '90');
INSERT INTO `text` VALUES ('101', 'B-3', '1', '24px serif', '#143A3F', 'B-3', '106', '128', '90');
INSERT INTO `text` VALUES ('102', 'B-4', '1', '24px serif', '#143A3F', 'B-4', '107', '128', '90');
INSERT INTO `text` VALUES ('103', 'B-5', '1', '24px serif', '#143A3F', 'B-5', '108', '128', '90');
INSERT INTO `text` VALUES ('104', 'B-6', '1', '24px serif', '#143A3F', 'B-6', '109', '128', '90');
INSERT INTO `text` VALUES ('105', 'B-7', '1', '24px serif', '#143A3F', 'B-7', '110', '128', '90');
INSERT INTO `text` VALUES ('106', 'B-8', '1', '24px serif', '#143A3F', 'B-8', '111', '128', '90');
INSERT INTO `text` VALUES ('107', 'B-9', '1', '24px serif', '#143A3F', 'B-9', '112', '128', '90');
INSERT INTO `text` VALUES ('108', 'A-1', '1', '24px serif', '#143A3F', 'A-1', '113', '128', '90');
INSERT INTO `text` VALUES ('109', 'A-2', '1', '24px serif', '#143A3F', 'A-2', '114', '128', '90');
INSERT INTO `text` VALUES ('110', 'A-3', '1', '24px serif', '#143A3F', 'A-3', '115', '128', '90');
INSERT INTO `text` VALUES ('111', 'A-4', '1', '24px serif', '#143A3F', 'A-4', '116', '128', '90');
INSERT INTO `text` VALUES ('112', 'A-5', '1', '24px serif', '#143A3F', 'A-5', '117', '128', '90');
INSERT INTO `text` VALUES ('113', 'A-6', '1', '24px serif', '#143A3F', 'A-6', '118', '128', '90');
INSERT INTO `text` VALUES ('114', 'A-7', '1', '24px serif', '#143A3F', 'A-7', '119', '128', '90');
INSERT INTO `text` VALUES ('115', 'A-8', '1', '24px serif', '#143A3F', 'A-8', '120', '128', '90');
INSERT INTO `text` VALUES ('116', 'A-9', '1', '24px serif', '#143A3F', 'A-9', '121', '128', '90');
INSERT INTO `text` VALUES ('117', 'XXX机柜', '1', 'Bold 26px serif', '#000000', 'XXX机柜', '69,70,71,72', '128', '130');
INSERT INTO `text` VALUES ('118', 'XXXX大区', '1', '24px serif', '#000000', 'XXXX大区', '69,70,71,72', '128', '170');
INSERT INTO `text` VALUES ('119', 'prodArea', '1', 'Bold 60px serif', '#000000', '生产控制大区', '122', '256', '32');
INSERT INTO `text` VALUES ('120', 'ctrlArea1', '1', 'Bold 60px serif', '#000000', '控制大区一', '123', '256', '32');
INSERT INTO `text` VALUES ('121', 'ctrlArea2', '1', 'Bold 60px serif', '#000000', '控制大区二', '124', '256', '32');
