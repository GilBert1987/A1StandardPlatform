/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.1.9-MariaDB : Database - shirotable
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shirotable` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `shirotable`;

/*Table structure for table `sc_commoncode` */

DROP TABLE IF EXISTS `sc_commoncode`;

CREATE TABLE `sc_commoncode` (
  `CC_ID` varchar(32) NOT NULL,
  `CC_Name` varchar(256) DEFAULT NULL,
  `CC_Index` int(11) DEFAULT NULL,
  `CC_ShortName` varchar(32) DEFAULT NULL,
  `CC_Remark` varchar(512) DEFAULT NULL,
  `CC_ParentID` varchar(32) DEFAULT NULL,
  `CCT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`CC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_commoncode` */

insert  into `sc_commoncode`(`CC_ID`,`CC_Name`,`CC_Index`,`CC_ShortName`,`CC_Remark`,`CC_ParentID`,`CCT_ID`) values ('CC_001','启用',1,NULL,'enabled',NULL,'CT_001'),('CC_002','停用',2,NULL,'disabled',NULL,'CT_001'),('CC_003','静态',1,NULL,'1',NULL,'CT_002'),('CC_004','动态',2,NULL,'2',NULL,'CT_002');

/*Table structure for table `sc_commoncodetype` */

DROP TABLE IF EXISTS `sc_commoncodetype`;

CREATE TABLE `sc_commoncodetype` (
  `CCT_ID` varchar(32) NOT NULL,
  `CCT_Code` varchar(32) DEFAULT NULL,
  `CCT_Name` varchar(64) DEFAULT NULL,
  `CCT_Index` int(11) DEFAULT NULL,
  `CCT_Remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`CCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_commoncodetype` */

insert  into `sc_commoncodetype`(`CCT_ID`,`CCT_Code`,`CCT_Name`,`CCT_Index`,`CCT_Remark`) values ('CT_001','user_state','状态',1,NULL),('CT_002','role_type','角色类型',2,NULL);

/*Table structure for table `sc_menu` */

DROP TABLE IF EXISTS `sc_menu`;

CREATE TABLE `sc_menu` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `code` varchar(256) DEFAULT NULL COMMENT '编码',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `hasroot` int(11) DEFAULT NULL,
  `rooturl` varchar(2048) DEFAULT NULL,
  `filedir` varchar(256) DEFAULT NULL COMMENT '文件路径',
  `titlepic` varchar(1024) DEFAULT NULL COMMENT '广告位图片',
  `remark` varchar(512) DEFAULT NULL COMMENT '标题图片',
  `level` int(11) DEFAULT NULL,
  `lastmodifiedtime` varchar(32) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_menu` */

insert  into `sc_menu`(`id`,`code`,`title`,`hasroot`,`rooturl`,`filedir`,`titlepic`,`remark`,`level`,`lastmodifiedtime`) values ('SM_201507090001','root','系统配置平台',1,'../form/SF_2017091720040001.form','page','',NULL,0,'1525683298318'),('SM_201507090002','SP_201501090004','业务平台',0,'','page','',NULL,1,'1525683128112'),('SM_201507090003','SP_201501220001','生产平台',0,'','page','',NULL,1,'1525683129970'),('SM_201507090004','SP_201504120001','教学平台',0,'','page','',NULL,1,'1525683131733'),('SM_201604190001','SP_201604190001','OA平台',0,'','page','',NULL,1,'1525683133636'),('SM_2016111813510001','SP_2016111813510001','员工餐厅',NULL,NULL,'page','',NULL,1,'1479448268283'),('SM_2018012917390001','SP_2018012917390001','酒店管理',1,'','page','',NULL,0,'1525683135655');

/*Table structure for table `sc_menu_shortcut` */

DROP TABLE IF EXISTS `sc_menu_shortcut`;

CREATE TABLE `sc_menu_shortcut` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '菜单ID',
  `code` varchar(256) DEFAULT NULL COMMENT '编码',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `pic` varchar(1024) DEFAULT NULL COMMENT '图标',
  `url` varchar(2048) DEFAULT NULL COMMENT 'URL地址',
  `showindex` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_menu_shortcut` */

insert  into `sc_menu_shortcut`(`id`,`menu_id`,`code`,`name`,`pic`,`url`,`showindex`) values ('SMS_2018050716520006','SM_201507090002','menu_root_shortcut1','首页','','/platform/menu/root.menu',1),('SMS_2018050716520007','SM_201507090003','menu_root_shortcut1','首页','','/platform/menu/root.menu',1),('SMS_2018050716520008','SM_201507090004','menu_course_shortcut1','首页','','/platform/menu/root.menu',1),('SMS_2018050716520009','SM_201507090004','menu_root_shortcut2','test','','',2),('SMS_2018050716540005','SM_2016111813580001','SP_2016111813580001_menu','员工餐厅平台','','/platform/menu/SP_2016111813580001.menu',1),('SMS_2018050716540006','SM_201507090001','menu_root_shortcut1','首页','','/platform/menu/root.menu',1),('SMS_2018050716540007','SM_201507090001','menu_course_shortcut1','教学平台','','/platform/menu/SP_201504120001.menu',2),('SMS_2018050716540008','SM_201507090001','SP_201604190001','OA平台','','/platform/menu/SP_201604190001.menu',3),('SMS_2018050716540009','SM_201507090001','SP_2016111813580001','员工餐厅','','/platform/menu/SP_2016111813580001.menu',4);

/*Table structure for table `sc_menu_submenu` */

DROP TABLE IF EXISTS `sc_menu_submenu`;

CREATE TABLE `sc_menu_submenu` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '主菜单ID',
  `code` varchar(256) DEFAULT NULL COMMENT '编码',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `pic` varchar(1024) DEFAULT NULL COMMENT '图标',
  `url` varchar(2048) DEFAULT NULL COMMENT 'URL地址',
  `isshow` int(11) DEFAULT NULL COMMENT '是否显示',
  `opentype` int(11) DEFAULT NULL COMMENT '打开类型',
  `parentcode` varchar(256) DEFAULT NULL COMMENT '父ID',
  `showindex` int(11) DEFAULT NULL COMMENT '排序',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_menu_submenu` */

insert  into `sc_menu_submenu`(`id`,`menu_id`,`code`,`name`,`pic`,`url`,`isshow`,`opentype`,`parentcode`,`showindex`,`level`) values ('SMB_2018050716520056','SM_201507090002','menu_root_submenus_1','首页','','',1,1,'-1',1,1),('SMB_2018050716520057','SM_201507090004','menu_course_submenus_1','教务系统','','',1,1,'-1',1,1),('SMB_2018050716520058','SM_201507090004','menu_course_submenus_1_1','教务管理','','',1,1,'menu_course_submenus_1',1,2),('SMB_2018050716520059','SM_201507090004','menu_course_submenus_1_1_1','教学班管理','','/platform/query/SQ_201504120001.query',1,1,'menu_course_submenus_1_1',1,3),('SMB_2018050716520060','SM_201507090004','menu_course_submenus_1_1_2','教师管理','','/platform/query/SQ_201504120002.query',1,1,'menu_course_submenus_1_1',2,3),('SMB_2018050716520061','SM_201507090004','menu_course_submenus_1_1_3','排课管理','','/platform/frame/SR_201504120002.frame',1,1,'menu_course_submenus_1_1',3,3),('SMB_2018050716520062','SM_201604190001','OA_Manager','OA管理','','',1,1,'-1',1,1),('SMB_2018050716520063','SM_201604190001','OA_Manager_Basic','基础信息','','',1,1,'OA_Manager',2,2),('SMB_2018050716520064','SM_201604190001','OA_Manager_Basic_Company','公司信息','','/platform/form/SF_201604180001.form?id=${sessionScope.user.tenantid}',1,1,'OA_Manager_Basic',1,3),('SMB_2018050716520065','SM_201604190001','OA_Manager_Basic_Org','组织机构','','/platform/tree/SE_201604190001.treelist',1,1,'OA_Manager_Basic',2,3),('SMB_2018050716520066','SM_201604190001','OA_Manager_Basic_Role','角色管理','','',1,1,'OA_Manager_Basic',4,3),('SMB_2018050716520067','SM_201604190001','OA_Manager_Basic_Post','职务管理','','',1,1,'OA_Manager_Basic',3,3),('SMB_2018050716520068','SM_201604190001','OA_Manager_Basic_User','人员管理','','',1,1,'OA_Manager_Basic',5,3),('SMB_2018050716520069','SM_201604190001','OA_Manager_Basic_Properties','基础属性','','',1,1,'OA_Manager_Basic',6,3),('SMB_2018050716520070','SM_201604190001','OA_Manager_Permissions','权限管理','','',1,1,'OA_Manager',3,2),('SMB_2018050716520071','SM_201604190001','OA_Manager_Permissions_Post','职务授权','','',1,1,'OA_Manager_Permissions',1,3),('SMB_2018050716520072','SM_201604190001','OA_Manager_Permissions_Role','角色授权','','',1,1,'OA_Manager_Permissions',2,3),('SMB_2018050716520073','SM_201604190001','OA_Manager_Permissions_OrgPost','部门职务授权','','',1,1,'OA_Manager_Permissions',3,3),('SMB_2018050716520074','SM_201604190001','OA_Manager_Person','个人信息管理','','',1,1,'OA_Manager',1,2),('SMB_2018050716520075','SM_201604190001','OA_Manager_Person_Self','个人信息','','',1,1,'OA_Manager_Person',1,3),('SMB_2018050716520076','SM_201604190001','OA_Manager_Form','表单管理','','',1,1,'OA_Manager',4,2),('SMB_2018050716520077','SM_201604190001','OA_Manager_Form_List','表单列表','','',1,1,'OA_Manager_Form',1,3),('SMB_2018050716520078','SM_201604190001','OA_Manager_WF','工作流配置','','',1,1,'OA_Manager',5,2),('SMB_2018050716520079','SM_201604190001','OA_Manager_WF_Design','流程配置','','',1,1,'OA_Manager_WF',1,3),('SMB_2018050716520080','SM_201604190001','OA_Manager_WF_Manager','流程管理','','',1,1,'OA_Manager_WF',2,3),('SMB_2018050716520081','SM_201604190001','OA_Manager_WF_MyFlow','我的流程','','',1,1,'OA_Manager',7,2),('SMB_2018050716520082','SM_201604190001','OA_Manager_WF_SendWF','发起流程','','',1,1,'OA_Manager_WF_MyFlow',1,3),('SMB_2018050716520083','SM_201604190001','OA_Manager_WF_Draft','流程草稿','','',1,1,'OA_Manager_WF_MyFlow',2,3),('SMB_2018050716520084','SM_201604190001','OA_Manager_WF_Run','运行流程','','',1,1,'OA_Manager_WF_MyFlow',3,3),('SMB_2018050716520085','SM_201604190001','OA_Manager_WF_Complete','办结流程','','',1,1,'OA_Manager_WF_MyFlow',5,3),('SMB_2018050716520086','SM_201604190001','OA_Manager_WF_Join','参与流程','','',1,1,'OA_Manager_WF_MyFlow',4,3),('SMB_2018050716520087','SM_2018012917390001','SMB_2018012918010001','测试','','../test',1,1,'-1',1,1),('SMB_2018050716520088','SM_2018012917390001','SMB_2018012918030001','测试test2','','',1,1,'SMB_2018012918010001',2,2),('SMB_2018050716520089','SM_2018012917390001','SMB_2018012918040004','测试test1','','',1,1,'SMB_2018012918010001',1,2),('SMB_2018050716520090','SM_2018012917390001','SMB_2018012918040015','测试test-3-1','','',1,1,'SMB_2018012918040004',1,3),('SMB_2018050716520091','SM_2018012917390001','SMB_2018012918040022','测试test-3-2','','',1,1,'SMB_2018012918040004',2,3),('SMB_2018050716520092','SM_2018012917390001','SMB_2018012918040030','测试test-3-3','','',1,1,'SMB_2018012918040004',3,3),('SMB_2018050716520093','SM_2018012917390001','SMB_2018012918040009','测试test3','','',1,1,'SMB_2018012918010001',3,2),('SMB_2018050716520094','SM_2018012917390001','SMB_2018012918010003','测试2','','',1,1,'-1',2,1),('SMB_2018050716540031','SM_2016111813580001','menuinfo201611250001','餐厅配置','','',1,1,'-1',1,1),('SMB_2018050716540032','SM_2016111813580001','menuinfo201611250002','员工卡管理','','',1,1,'menuinfo201611250001',1,2),('SMB_2018050716540033','SM_2016111813580001','menuinfo201611250003','餐厅部门配置','','/platform/tree/SE_2016112111180001.treelist',1,1,'menuinfo201611250002',2,3),('SMB_2018050716540034','SM_2016111813580001','SMB_2018011014540001','时间段列表','','/platform/query/SQ_2018011014490001.query',1,1,'menuinfo201611250002',1,3),('SMB_2018050716540035','SM_2016111813580001','SMB_2018031610090001','联营卡管理','','',1,1,'menuinfo201611250001',2,2),('SMB_2018050716540036','SM_2016111813580001','SMB_2018031610100025','卡类型管理','','/platform/changeurl?urlInfo=http%3A%2F%2Flocalhost%3A8087%2FWristStrap%2F2010_bracelet_type%2F00_standard.do',1,1,'SMB_2018031610090001',1,3),('SMB_2018050716540037','SM_2016111813580001','SMB_2018031916330014','卡信息管理','','/platform/changeurl?urlInfo=http%3A%2F%2Flocalhost%3A8087%2FWristStrap%2F2030_bracelet_info%2F2030_standard.do',1,1,'SMB_2018031610090001',2,3),('SMB_2018050716540038','SM_2016111813580001','SMB_2018031916350015','卡押金调整','','/platform/changeurl?urlInfo=http%3A%2F%2Flocalhost%3A8087%2FWristStrap%2F5020_consume_info%2F5020_standard.do',1,1,'SMB_2018031610090001',3,3),('SMB_2018050716540039','SM_2016111813580001','SMB_2018031916380001','POS机管理','','/platform/changeurl?urlInfo=http%3A%2F%2Flocalhost%3A8087%2FWristStrap%2F1020_tcounter%2F1020_standard.do',1,1,'SMB_2018031610090001',4,3),('SMB_2018050716540040','SM_2016111813580001','SMB_2018040314440001','组织扩展管理','','',1,1,'menuinfo201611250001',0,2),('SMB_2018050716540041','SM_2016111813580001','SMB_2018040314450018','组织扩展管理','','/platform/tree/SE_2018040214550001.treelist',1,1,'SMB_2018040314440001',3,3),('SMB_2018050716540042','SM_2016111813580001','SMB_2018041013240001','用户管理','','/platform/tool/ST_201503180001.tool',1,1,'SMB_2018040314440001',1,3),('SMB_2018050716540043','SM_2016111813580001','SMB_2018041017550001','组织机构管理','','/platform/tree/sc_organization.treelist',1,1,'SMB_2018040314440001',2,3),('SMB_2018050716540044','SM_2016111813580001','SMB_2017121123170001','员工报餐管理','','',1,1,'-1',2,1),('SMB_2018050716540045','SM_2016111813580001','SMB_2017121123170011','部门报餐申请','','/platform/tool/ST_2017121313120001.tool',1,1,'SMB_2017121123170001',1,2),('SMB_2018050716540046','SM_2016111813580001','menuinfo201611250004','餐厅报表','','',1,1,'-1',3,1),('SMB_2018050716540047','SM_2016111813580001','menuinfo201611250005','员工餐厅报表','','',1,1,'menuinfo201611250004',1,2),('SMB_2018050716540048','SM_2016111813580001','SMB_2018011114100001','当月就餐报表(员工)','','/platform/tool/ST_2018011114050001.tool',1,1,'menuinfo201611250005',4,3),('SMB_2018050716540049','SM_2016111813580001','SMB_2018011114110014','历史就餐报表(员工)','','/platform/tool/ST_2018011114050002.tool',1,1,'menuinfo201611250005',6,3),('SMB_2018050716540050','SM_2016111813580001','SMB_2018032317000001','用餐超次统计表','','/platform/query/SQ_2018032316140001.query',1,1,'menuinfo201611250005',3,3),('SMB_2018050716540051','SM_2016111813580001','SMB_2018040315370001','联营卡报表','','',1,1,'menuinfo201611250004',2,2),('SMB_2018050716540052','SM_2016111813580001','SMB_2018040315370021','当月就餐报表(联营)','','/platform/tool/ST_2018040315400001.tool',1,1,'SMB_2018040315370001',1,3),('SMB_2018050716540053','SM_2016111813580001','SMB_2018040315380021','历史就餐报表(联营)','','/platform/tool/ST_2018040315400002.tool',1,1,'SMB_2018040315370001',2,3),('SMB_2018050716540054','SM_2016111813580001','SMB_2018040811440001','联营卡充值表','','/platform/query/SQ_2018040318000001.query',1,1,'SMB_2018040315370001',3,3),('SMB_2018050716540055','SM_2016111813580001','SMB_2018040811490001','联营卡消费报表','','/platform/query/SQ_2018040318010001.query',1,1,'SMB_2018040315370001',4,3),('SMB_2018050716540056','SM_201507090001','menu_root_submenus_1_3','授权管理','','',1,0,'-1',3,1),('SMB_2018050716540057','SM_201507090001','menu_root_submenus_1_3_2','静态角色授权','','/platform/tree/SE_201501190001.treelist',1,1,'menu_root_submenus_1_3',2,2),('SMB_2018050716540058','SM_201507090001','menu_root_submenus_1_3_1','职务授权','','/platform/tree/SE_201501190002.treelist',1,0,'menu_root_submenus_1_3',1,2),('SMB_2018050716540059','SM_201507090001','menu_root_submenus_1_2','基础信息配置','','#',1,1,'-1',2,1),('SMB_2018050716540060','SM_201507090001','menu_root_submenus_1_2_1','用户管理','','/platform/tool/ST_201503180001.tool',1,1,'menu_root_submenus_1_2',1,2),('SMB_2018050716540061','SM_201507090001','menu_root_submenus_1_2_2','部门配置','','/platform/tree/sc_organization.treelist',1,1,'menu_root_submenus_1_2',2,2),('SMB_2018050716540062','SM_201507090001','menu_root_submenus_1_2_3','职务管理','','/platform/query/SQ_201501160001.query',1,1,'menu_root_submenus_1_2',3,2),('SMB_2018050716540063','SM_201507090001','menu_root_submenus_1_2_4','角色管理','','/platform/query/SQ_201501160002.query',1,1,'menu_root_submenus_1_2',4,2),('SMB_2018050716540064','SM_201507090001','menu_root_submenus_1_2_5','静态角色配置','','/platform/frame/SR_201502090001.frame',1,1,'menu_root_submenus_1_2',5,2),('SMB_2018050716540065','SM_201507090001','menu_root_submenus_1_4','工作流平台','','',1,1,'-1',4,1),('SMB_2018050716540066','SM_201507090001','menu_root_submenus_1_4_1','工作流配置','','/platform/tree/SE_201509240001.treelist?tool=ST_201506250001',1,1,'menu_root_submenus_1_4',1,2),('SMB_2018050716540067','SM_201507090001','menu_root_submenus_1_4_2','发起流程','','/platform/tree/SE_2017032217260001.treelist?tool=ST_2017032217320001',1,1,'menu_root_submenus_1_4',4,2),('SMB_2018050716540068','SM_201507090001','SMB_2017092914420001','流程管理','','/platform/tool/ST_2017092914450001.tool',1,1,'menu_root_submenus_1_4',2,2),('SMB_2018050716540069','SM_201507090001','SMB_2017101013580001','流程代理人','','/platform/tree/SE_2017101014010001.treelist',1,1,'menu_root_submenus_1_4',3,2),('SMB_2018050716540070','SM_201507090001','menu_root_submenus_1_1','系统管理','','',1,1,'-1',1,1),('SMB_2018050716540071','SM_201507090001','menu_root_submenus_1_1_1','平台管理','','/platform/tool/ST_201501070001.tool',1,1,'menu_root_submenus_1_1',1,2),('SMB_2018050716540072','SM_201507090001','menu_root_submenus_1_1_2','配置工具','','/platform/tree/sc_system.treelist',1,1,'menu_root_submenus_1_1',2,2),('SMB_2018050716540073','SM_201507090001','menu_root_submenus_1_1_4','数据监控','','/druid/index.html',1,1,'menu_root_submenus_1_1',4,2),('SMB_2018050716540074','SM_201507090001','menu_root_submenus_1_1_5','系统日志','','/platform/query/SQ_201507240001.query',1,1,'menu_root_submenus_1_1',5,2),('SMB_2018050716540075','SM_201507090001','menu_root_submenus_1_1_3','文件类型','','/platform/query/SQ_201509140001.query',1,1,'menu_root_submenus_1_1',3,2),('SMB_2018050716540076','SM_201507090001','menu_root_submenus_1_1_6','控件注册','','/platform/query/SQ_2016042916370001.query',1,1,'menu_root_submenus_1_1',6,2),('SMB_2018050716540077','SM_201507090001','menu_root_submenus_1_1_7','逻辑注册','','/platform/tree/SE_2016050614270001.treelist',1,1,'menu_root_submenus_1_1',7,2),('SMB_2018050716540078','SM_201507090001','menu_root_submenus_1_1_8','通用属性','','/platform/tree/SE_2016050514340001.treelist',1,1,'menu_root_submenus_1_1',8,2),('SMB_2018050716540079','SM_201507090001','menu_root_submenus_1_1_9','主键生成策略','','/platform/query/SQ_2017092114310001.query',1,1,'menu_root_submenus_1_1',9,2),('SMB_2018050716540080','SM_201507090001','menu_root_submenus_1_5','调度管理','','',1,1,'-1',5,1),('SMB_2018050716540081','SM_201507090001','menu_root_submenus_1_5_1','任务列表','','/platform/tree/SE_201507240001.treelist',1,1,'menu_root_submenus_1_5',1,2),('SMB_2018050716540082','SM_201507090001','SMB_2017092914240001','我的流程','','',1,1,'-1',1,1),('SMB_2018050716540083','SM_201507090001','SMB_2017092914250030','参与的流程','','/platform/tree/SE_2017032217140001.treelist?tool=ST_201510280002',1,1,'SMB_2017092914240001',1,2),('SMB_2018050716540084','SM_201507090001','SMB_2017092914260031','运行的流程','','/platform/tree/SE_2017032217150001.treelist?tool=act_hi_procinst_Running',1,1,'SMB_2017092914240001',2,2),('SMB_2018050716540085','SM_201507090001','SMB_2017092914270032','办结的流程','','/platform/tree/SE_2017032217150002.treelist?tool=act_hi_procinst_Completed',1,1,'SMB_2017092914240001',3,2);

/*Table structure for table `sc_organization` */

DROP TABLE IF EXISTS `sc_organization`;

CREATE TABLE `sc_organization` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `name` varchar(64) DEFAULT NULL COMMENT '部门名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父ID',
  `platform_id` varchar(32) DEFAULT NULL COMMENT '平台绑定ID',
  `org_index` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_organization` */

insert  into `sc_organization`(`id`,`description`,`name`,`parent_id`,`platform_id`,`org_index`) values ('1','不能删除。','xx集团',NULL,NULL,NULL);

/*Table structure for table `sc_post` */

DROP TABLE IF EXISTS `sc_post`;

CREATE TABLE `sc_post` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '岗位',
  `level` varchar(256) DEFAULT NULL,
  `ps_index` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_post` */

insert  into `sc_post`(`id`,`name`,`level`,`ps_index`) values ('SP_2017121115180001','测试员','1',1),('SP_2017122711260001','职员','2',2);

/*Table structure for table `sc_post_org` */

DROP TABLE IF EXISTS `sc_post_org`;

CREATE TABLE `sc_post_org` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `post_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `org_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_post_org` */

insert  into `sc_post_org`(`id`,`post_id`,`org_id`) values ('SOG_2017121217540001','SP_2017121115180001','132804'),('SOG_2017122711250001','SP_2017121115180001','132807'),('SOG_2017122711260001','SP_2017122711260001','132807'),('SOG_2017122711280001','SP_2017121115180001','132806'),('SOG_2018010317180001','SP_2017121115180001','124641'),('SOG_2018031414590001','SP_2017121115180001','132809');

/*Table structure for table `sc_role` */

DROP TABLE IF EXISTS `sc_role`;

CREATE TABLE `sc_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '角色',
  `type` int(11) DEFAULT NULL COMMENT '类型(0静态,1动态)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_role` */

insert  into `sc_role`(`id`,`name`,`type`) values ('1','超级管理员',1),('SR_201502260001','普通员工',1),('SR_2016112417080001','POS管理员',1);

/*Table structure for table `sc_role_dynamicpermission` */

DROP TABLE IF EXISTS `sc_role_dynamicpermission`;

CREATE TABLE `sc_role_dynamicpermission` (
  `id` varchar(32) DEFAULT NULL COMMENT '主键',
  `roleid` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `permission` varchar(32) DEFAULT NULL COMMENT '权限编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_role_dynamicpermission` */

/*Table structure for table `sc_role_permission` */

DROP TABLE IF EXISTS `sc_role_permission`;

CREATE TABLE `sc_role_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_role_permission` */

insert  into `sc_role_permission`(`id`,`role_id`,`permission`) values ('1','1','menu_root_shortcut1'),('10','1','menu_root_submenus_1_1_2'),('12','1','menu_root_submenus_1_2_1'),('13','1','menu_root_submenus_1_2_2'),('14','1','menu_root_submenus_1_2_3'),('19','1','menu_root_submenus_1_1_4'),('2','1','menu_root_submenus_1'),('20','1','menu_root_submenus_1_1_5'),('21','1','menu_root_submenus_1_1_6'),('22','1','menu_root_submenus_1_1_7'),('23','1','menu_root_submenus_1_1_8'),('24','1','menu_root_submenus_1_1_9'),('25','1','menu_root_submenus_1_1_10'),('28','1','menu_root_submenus_1_3_1'),('29','1','menu_root_submenus_1_3_2'),('3','1','menu_root_submenus_1_1'),('4','1','menu_root_submenus_1_3'),('SRP_201501190001','1','menu_root_submenus_1_1_1'),('SRP_201501190005','1','menu_root_submenus_1_2'),('SRP_201501190007','1','menu_root_submenus_1_2_4'),('SRP_201502040001','1','menu_root_submenus_1_1_3'),('SRP_201502120001','1','menu_root_submenus_1_2_5'),('SRP_201502270010','SR_201502260001','menu_root_submenus_2'),('SRP_201502270011','SR_201502260001','menu_root_submenus_2_1'),('SRP_201502270012','SR_201502260001','menu_root_submenus_2_1_1'),('SRP_201502270013','SR_201502260001','menu_root_submenus_2_1_2'),('SRP_201502270014','SR_201502260001','menu_root_submenus_2_2'),('SRP_201502280001','SP_201501160001','menu_root_submenus_1_1_1'),('SRP_201502280002','SP_201501160001','menu_root_submenus_1_1_3'),('SRP_201502280003','SP_201501160001','menu_root_submenus_1'),('SRP_201503090001','1','menu_root_submenus_2'),('SRP_201503090002','1','menu_root_submenus_2_1'),('SRP_201503090003','1','menu_root_submenus_2_3'),('SRP_201504120001','1','menu_course_shortcut1'),('SRP_201504120002','1','menu_course_submenus_1'),('SRP_201504120003','1','menu_course_submenus_1_1'),('SRP_201504120004','1','menu_course_submenus_1_1_1'),('SRP_201504120005','1','menu_course_submenus_1_1_2'),('SRP_201504120006','1','menu_course_submenus_1_1_3'),('SRP_201506250001','1','menu_root_submenus_1_4'),('SRP_201506250002','1','menu_root_submenus_1_4_1'),('SRP_201506260002','SR_201502260001','menu_root_submenus_1_1_10'),('SRP_201507100001','1','menu_root_submenus_1_4_3'),('SRP_201507100002','1','menu_root_submenus_1_4_2'),('SRP_201507100003','1','menu_root_submenus_1_4_4'),('SRP_201507100004','1','menu_root_submenus_1_4_5'),('SRP_201507150001','SR_201502260001','menu_root_submenus_1_4_3'),('SRP_201507150002','SR_201502260001','menu_root_submenus_1_4_4'),('SRP_201507150003','SR_201502260001','menu_root_submenus_1_4_5'),('SRP_201507220001','1','menu_root_submenus_1_5'),('SRP_201507220002','1','menu_root_submenus_1_5_1'),('SRP_201507220003','1','menu_root_submenus_1_5_2'),('SRP_201507220004','1','menu_root_submenus_1_5_3'),('SRP_201507220005','1','menu_root_submenus_1_5_4'),('SRP_201508200001','1','test_002'),('SRP_201604190004','1','OA_Manager'),('SRP_201604190005','1','OA_Manager_Basic'),('SRP_201604190006','1','OA_Manager_Basic_Company'),('SRP_201604190007','1','SP_201604190001'),('SRP_201604190008','1','menu_SP_201604190001_shortcut1'),('SRP_201604190009','SP_201501160001','menu_root_shortcut1'),('SRP_201604200001','SR_201604190001','OA_Manager'),('SRP_201604200002','SR_201604190001','OA_Manager_Basic'),('SRP_201604200003','SR_201604190001','OA_Manager_Basic_Company'),('SRP_201604200004','SR_201604190001','OA_Manager_Basic_Org'),('SRP_201604200005','SR_201604190001','OA_Manager_Basic_Role'),('SRP_201604200006','SR_201604190001','OA_Manager_Basic_Post'),('SRP_201604200007','SR_201604190001','OA_Manager_Basic_User'),('SRP_201604200008','SR_201604190001','OA_Manager_Basic_Properties'),('SRP_201604200009','SR_201604190001','OA_Manager_Permissions'),('SRP_201604200010','SR_201604190001','OA_Manager_Permissions_Post'),('SRP_201604200011','SR_201604190001','OA_Manager_Permissions_Role'),('SRP_201604200012','SR_201604190001','OA_Manager_Permissions_OrgPost'),('SRP_201604200013','SR_201604190001','OA_Manager_Person'),('SRP_201604200014','SR_201604190001','OA_Manager_Person_Self'),('SRP_201604200015','SR_201604190001','OA_Manager_Form'),('SRP_201604200016','SR_201604190001','OA_Manager_Form_List'),('SRP_201604200017','SR_201604190001','OA_Manager_WF'),('SRP_201604200018','SR_201604190001','OA_Manager_WF_Design'),('SRP_201604200019','SR_201604190001','OA_Manager_WF_Manager'),('SRP_201604200020','SR_201604190001','OA_Manager_WF_MyFlow'),('SRP_201604200021','SR_201604190001','OA_Manager_WF_SendWF'),('SRP_201604200022','SR_201604190001','OA_Manager_WF_Draft'),('SRP_201604200023','SR_201604190001','OA_Manager_WF_Run'),('SRP_201604200024','SR_201604190001','OA_Manager_WF_Complete'),('SRP_201604200025','SR_201604190001','OA_Manager_WF_Join'),('SRP_2016112114440001','1','SP_2016111813580001'),('SRP_2016112114440002','1','YGCT_001'),('SRP_2016112114440003','1','YGCT_002'),('SRP_2016112114440004','1','YGCT_003'),('SRP_2016112317480001','1','YGCT_004'),('SRP_2016112317480002','1','YGCT_005'),('SRP_2016112317480003','1','YGCT_006'),('SRP_2016112317480004','1','YGCT_007'),('SRP_2016112418510001','SR_2016112417080001','menu_root_shortcut1'),('SRP_2016112418510002','SR_2016112417080001','SP_2016111813580001'),('SRP_2016112517410001','SR_2016112417080001','menuinfo201611250001'),('SRP_2016112517410002','SR_2016112417080001','menuinfo201611250002'),('SRP_2016112517410003','SR_2016112417080001','menuinfo201611250003'),('SRP_2016112517410004','SR_2016112417080001','menuinfo201611250004'),('SRP_2016112517410005','SR_2016112417080001','menuinfo201611250005'),('SRP_2016112517410006','SR_2016112417080001','menuinfo201611250006'),('SRP_2016120114500001','SR_2016112417080001','menuinfo201611250007'),('SRP_2016120516530001','SR_2016112417080001','YGCT_YGYC'),('SRP_2016120516580001','1','SP_2016111813580001_menu'),('SRP_2016120516580002','1','menuinfo201611250001'),('SRP_2016120516580003','1','menuinfo201611250002'),('SRP_2016120516580004','1','menuinfo201611250003'),('SRP_2016120516580005','1','YGCT_YGYC'),('SRP_2016120516580006','1','menuinfo201611250004'),('SRP_2016120516580007','1','menuinfo201611250005'),('SRP_2016120516580008','1','menuinfo201611250006'),('SRP_2016120516580009','1','menuinfo201611250007'),('SRP_2017092914390001','SR_201502260001','SMB_2017092914240001'),('SRP_2017092914390002','SR_201502260001','SMB_2017092914250030'),('SRP_2017092914390003','SR_201502260001','SMB_2017092914260031'),('SRP_2017092914390004','SR_201502260001','SMB_2017092914270032'),('SRP_2017092914430001','1','SMB_2017092914420001'),('SRP_2017100517560001','SR_201502260001','menu_root_shortcut1'),('SRP_2017100518020001','SR_201502260001','menu_root_submenus_1'),('SRP_2017101014000001','1','SMB_2017101013580001'),('SRP_2017121123190001','1','SMB_2017121123170001'),('SRP_2017121123190002','1','SMB_2017121123170011'),('SRP_2017121123190003','1','SMB_2017121123180001'),('SRP_2017121311410001','SP_2017121115180001','SMB_2017121123170001'),('SRP_2017121311410002','SP_2017121115180001','SMB_2017121123170011'),('SRP_2017121311410003','SP_2017121115180001','SMB_2017121123180001'),('SRP_2017121311440001','SP_2017121115180001','SP_2016111813580001_menu'),('SRP_2018010317240001','SR_2016112417080001','SP_2016111813580001_menu'),('SRP_2018010317240002','SR_201502260001','SP_2016111813580001_menu'),('SRP_2018010513460001','SR_2016112417080001','SMB_2018010513390001'),('SRP_2018011014560001','SR_2016112417080001','SMB_2018011014540001'),('SRP_2018011014570001','1','SMB_2018011014540001'),('SRP_2018011014570002','1','SMB_2018010513390001'),('SRP_2018011114160001','1','SMB_2018011114100001'),('SRP_2018011114160002','1','SMB_2018011114110014'),('SRP_2018011116150001','1','SMB_2018011116070001'),('SRP_2018011116150002','1','SMB_2018011116090046'),('SRP_2018012918020001','1','SMB_2018012918010001'),('SRP_2018012918020002','1','SMB_2018012918010003'),('SRP_2018012918050001','1','SMB_2018012918030001'),('SRP_2018012918050002','1','SMB_2018012918040004'),('SRP_2018012918050003','1','SMB_2018012918040015'),('SRP_2018012918050004','1','SMB_2018012918040022'),('SRP_2018012918050005','1','SMB_2018012918040030'),('SRP_2018012918050006','1','SMB_2018012918040009'),('SRP_2018031214580001','SR_2016112417080001','SMB_2018011114100001'),('SRP_2018031214580002','SR_2016112417080001','SMB_2018011114110014'),('SRP_2018031610160001','SR_2016112417080001','SMB_2017121123170001'),('SRP_2018031610160002','SR_2016112417080001','SMB_2017121123170011'),('SRP_2018031610160003','SR_2016112417080001','SMB_2018031610090001'),('SRP_2018031610160004','SR_2016112417080001','SMB_2018031610100025'),('SRP_2018031916440001','SR_2016112417080001','SMB_2018031916330014'),('SRP_2018031916440002','SR_2016112417080001','SMB_2018031916350015'),('SRP_2018031916440003','SR_2016112417080001','SMB_2018031916380001'),('SRP_2018032010050001','1','SMB_2018031610090001'),('SRP_2018032010050002','1','SMB_2018031610100025'),('SRP_2018032010050003','1','SMB_2018031916330014'),('SRP_2018032010050004','1','SMB_2018031916350015'),('SRP_2018032010050005','1','SMB_2018031916380001'),('SRP_2018032317010001','SR_2016112417080001','SMB_2018032317000001'),('SRP_2018032317010002','1','SMB_2018032317000001'),('SRP_2018040314580001','SR_2016112417080001','SMB_2018040314440001'),('SRP_2018040314580002','SR_2016112417080001','SMB_2018040314450018'),('SRP_2018040315000001','1','SMB_2018040314440001'),('SRP_2018040315000002','1','SMB_2018040314450018'),('SRP_2018040410080001','SR_2016112417080001','SMB_2018040315370001'),('SRP_2018040410080002','SR_2016112417080001','SMB_2018040315370021'),('SRP_2018040410080003','SR_2016112417080001','SMB_2018040315380021'),('SRP_2018040410080004','1','SMB_2018040315370001'),('SRP_2018040410080005','1','SMB_2018040315370021'),('SRP_2018040410080006','1','SMB_2018040315380021'),('SRP_2018040811490001','SR_2016112417080001','SMB_2018040811440001'),('SRP_2018040811490002','SR_2016112417080001','SMB_2018040811490001'),('SRP_2018040811500001','1','SMB_2018040811440001'),('SRP_2018040811500002','1','SMB_2018040811490001'),('SRP_2018041013250001','SR_2016112417080001','SMB_2018041013240001'),('SRP_2018041017550001','1','SMB_2018041013240001'),('SRP_2018041017550002','1','SMB_2018041017550001'),('SRP_2018041017560001','SR_2016112417080001','SMB_2018041017550001');

/*Table structure for table `sc_systemtablerole` */

DROP TABLE IF EXISTS `sc_systemtablerole`;

CREATE TABLE `sc_systemtablerole` (
  `CK_Id` varchar(32) NOT NULL COMMENT '主键',
  `CK_Dbname` varchar(32) DEFAULT NULL COMMENT '数据库缩写',
  `CK_Tbname` varchar(32) DEFAULT NULL COMMENT '表名',
  `CK_Key` varchar(32) DEFAULT NULL COMMENT '主键名',
  `CK_Value` varchar(32) DEFAULT NULL COMMENT '主键Title',
  `CK_Year` varchar(4) DEFAULT NULL COMMENT '年',
  `CK_Month` varchar(2) DEFAULT NULL COMMENT '月',
  `CK_Day` varchar(2) DEFAULT NULL COMMENT '日',
  `CK_Hour` varchar(2) DEFAULT NULL COMMENT '时',
  `CK_Minute` varchar(2) DEFAULT NULL COMMENT '分',
  `CK_Second` varchar(2) DEFAULT NULL COMMENT '秒',
  `CK_TypeInfo` varchar(6) DEFAULT NULL COMMENT '类型111111(年月日时分秒)',
  `CK_Num` int(11) DEFAULT NULL COMMENT '当前序号',
  `CK_Number` bigint(11) DEFAULT NULL COMMENT '序号位数',
  PRIMARY KEY (`CK_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_systemtablerole` */

insert  into `sc_systemtablerole`(`CK_Id`,`CK_Dbname`,`CK_Tbname`,`CK_Key`,`CK_Value`,`CK_Year`,`CK_Month`,`CK_Day`,`CK_Hour`,`CK_Minute`,`CK_Second`,`CK_TypeInfo`,`CK_Num`,`CK_Number`) values ('ACT_CM001','wf','act_re_url','id','AR_','2018','03','05','15','48','','111110',4,2),('ACT_CM002','wf','act_re_model','id_','WF_','2017','12','02','12','56','','111110',4,1),('ACT_CM003','wf','act_busskey','id','BK_','2018','03','05','15','48','','111110',4,2),('ACT_CM004','wf','sc_commoncode','CC_ID','SC_','2017','10','30','11','29','','111110',4,1),('ACT_CM005','wf','sc_commoncodetype','CCT_ID','SCC_','2017','10','19','10','21','','111110',4,1),('ACT_CM006','wf','act_re_url','code','WFC_','2018','03','05','15','48','','111110',4,1),('CK_2017092115230001','pm','pm_sceneassess','id','SC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('CK_2017092811160001','wf','act_hi_suggestion','id','SG_','2018','03','05','16','04','','111110',4,1),('CK_2017120418110001','com','sc_datavisualization','id','dv_','2017','12','05','17','32','','111110',4,1),('CK_2017120816250001','wandapos','sc_commoncodetype','CCT_ID','SCC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('CK_2017121215460001','rest','sc_commoncode','CC_ID','SC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('CK_2017121215470001','rest','sc_commoncodetype','CCT_ID','SCC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('CK_2017121314250001','rest','fillreport','id','FR_','2018','03','22','17','11','','111110',4,1),('CK_2017121314260001','rest','fillreportdetailed','id','FD_','2018','03','22','17','11','','111110',4,54),('CK_2018021519360001','weixin','sc_commoncodetype','CCT_ID','SCC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('CK_2018021519370001','weixin','sc_commoncode','CC_ID','SC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('CK_2018032115320001','rest','transactionlog','id','TL_','2018','04','03','17','38','','111110',4,2),('PM_005','pm','sc_commoncode','CC_ID','SC_','2017','09','25','14','33','','111110',4,2),('PM_007','pm','sc_commoncodetype','CCT_ID','PSCC_','2017','09','25','14','28','','111110',4,1),('PM_008','rest','userlist','id','UL_','2017','02','06','09','16','','111110',4,1),('PM_009','pm','pm_lessonclass','id','LC_','2018','03','05','15','48','','111110',4,1),('QZ_CM_001','qz','qrtz_job_log','id','JL_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('QZ_CM_002','qz','qrtz_trigger_user_log','id','TL_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('QZ_CM_003','qz','sc_commoncode','CC_ID','SC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('QZ_CM_004','qz','sc_commoncodetype','CCT_ID','SCC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('REDIS_001','com','sc_redis','id','RE_','2018','06','26','21','36','03','111111',4,3),('Rest_001','rest','user_rest','id','RC_','2018','03','12','11','00','','111110',4,1),('Rest_002','rest','emplog','id','LG_','2018','03','29','16','04','','111110',4,1),('Rest_003','rest','errorlog','id','ELG_','2018','03','14','10','02','','111110',4,2),('SC_001','com','sc_form','id','SF_','2018','06','22','10','57','','111110',4,1),('SC_002','com','sc_query','id','SQ_','2018','04','08','10','19','','111110',4,3),('SC_003','com','sc_tool','id','ST_','2018','04','03','15','40','','111110',4,2),('SC_004','com','sc_frame','id','SR_','2016','05','06','11','25','','111110',4,1),('SC_005','com','sc_tree','id','SE_','2018','04','02','14','55','','111110',4,1),('SC_006','com','sc_platform','id','SP_','2018','01','29','17','39','','111110',4,1),('SC_007','com','sc_control','id','SC_','2017','10','12','14','00','','111110',4,1),('SC_008','com','sc_control_tld','id','SCT_','2017','12','04','16','43','','111110',4,1),('SC_009','com','sc_control_tld_attr','id','STA_','2018','03','28','15','37','','111110',4,1),('SC_010','com','sc_file','id','FL_','2018','03','05','16','02','','111110',4,2),('SC_011','com','sc_filepath','id','FP_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('SC_012','com','sc_filetype','id','FT_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('SC_013','com','sc_commoncode','CC_ID','SC_','2018','03','15','12','09','','111110',4,1),('SC_014','com','sc_commoncodetype','CCT_ID','SCC_','2017','12','03','15','42','','111110',4,1),('SC_015','com','sc_logic','id','LG_','2018','04','10','11','40','','111110',4,1),('SC_016','com','sc_logicmethod','id','LM_','2018','04','10','11','40','','111110',4,1),('SC_017','com','sc_logicparam','id','LP_','2018','04','10','11','40','','111110',4,2),('SC_018','com','sc_formcontrol','id','SFC_','2016','07','22','17','11','','111110',4,1),('SC_019','com','sc_workflow','id','SK_','2017','12','02','12','56','','111110',4,1),('SC_020','com','sc_fileinfo','id','FI_','2018','06','26','21','36','','111110',4,1),('SH_001','shiro','sc_user_role','id','SUR_','2018','04','10','12','19','','111110',4,1),('SH_002','shiro','sc_user','id','SU_','2018','04','10','12','19','','111110',4,1),('SH_003','shiro','sc_organization','id','SO_','2017','09','22','10','49','','111110',4,1),('SH_004','shiro','sc_post','id','SP_','2017','12','27','11','26','','111110',4,1),('SH_005','shiro','sc_role','id','SR_','2016','11','24','17','08','','111110',4,1),('SH_006','shiro','sc_role_permission','id','SRP_','2018','04','10','17','56','','111110',4,1),('SH_007','shiro','sc_user_org','id','SUO_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('SH_008','shiro','sc_user_post','id','SUP_','2018','03','14','15','00','','111110',4,1),('SH_009','shiro','sc_post_org','id','SOG_','2018','03','14','14','59','','111110',4,1),('SH_010','shiro','sc_menu','id','SM_','2018','01','29','17','39','','111110',4,1),('SH_011','shiro','sc_menu_shortcut','id','SMS_','2018','05','07','16','54','','111110',4,9),('SH_012','shiro','sc_menu_submenu','id','SMB_','2018','05','07','16','54','','111110',4,85),('SH_013','shiro','sc_tenant','id','ST_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('SH_014','shiro','sc_commoncode','CC_ID','SC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('SH_015','shiro','sc_commoncodetype','CCT_ID','SCC_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL),('SH_016','shiro','sc_systemtablerole','CK_ID','CK_','2018','03','21','15','32','','111110',4,1),('WandaPOS_001','wandapos','sc_commoncode','CC_ID','SC_','2017','12','09','23','07','','111110',4,1),('WDZFB_001','wfzfb','orderinfo','id','ZF_','2018','06','19','08','52','','111110',4,1),('WDZFB_002','wfzfb','alipay_order_traninfo','id','OT_',NULL,NULL,NULL,NULL,NULL,NULL,'111110',4,NULL);

/*Table structure for table `sc_user` */

DROP TABLE IF EXISTS `sc_user`;

CREATE TABLE `sc_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_time` datetime DEFAULT NULL,
  `password` varchar(2048) DEFAULT NULL,
  `salt` varchar(32) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `email` varchar(128) DEFAULT NULL,
  `realname` varchar(32) DEFAULT NULL COMMENT '实际名称',
  `phone` varchar(32) DEFAULT NULL,
  `platform_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_user` */

insert  into `sc_user`(`id`,`create_time`,`password`,`salt`,`status`,`username`,`email`,`realname`,`phone`,`platform_id`) values ('SU_201411170001','2012-08-03 14:58:38','954e6d641bbbf4bab9a41b4748c791ef9af7ea0e','da14681c19ef6ff9','enabled','admin','admin@163.com','管理员','','-1');

/*Table structure for table `sc_user_post` */

DROP TABLE IF EXISTS `sc_user_post`;

CREATE TABLE `sc_user_post` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `por_id` varchar(32) DEFAULT NULL COMMENT '部门岗位ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_user_post` */

insert  into `sc_user_post`(`id`,`user_id`,`por_id`) values ('SUP_2017121217550001','SU_2017121217530001','SOG_2017121217540001'),('SUP_2017122711260001','SU_2016112417080001','SOG_2017122711250001'),('SUP_2017122711270001','SU_2016112417080001','SOG_2017122711260001'),('SUP_2017122711290001','SU_2017121217530001','SOG_2017122711280001'),('SUP_2018010317180001','SU_2017121217530001','SOG_2018010317180001'),('SUP_2018031415000001','SU_2018031414590001','SOG_2018031414590001');

/*Table structure for table `sc_user_role` */

DROP TABLE IF EXISTS `sc_user_role`;

CREATE TABLE `sc_user_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc_user_role` */

insert  into `sc_user_role`(`id`,`role_id`,`user_id`) values ('SUR_2016112418280001','SR_2016112417080001','SU_2016112417080001'),('SUR_2017092212030001','1','SU_201411170001'),('SUR_2017121217530001','SR_201502260001','SU_2017121217530001'),('SUR_2018010317230001','SR_201502260001','SU_2016112417080001'),('SUR_2018031414590001','SR_201502260001','SU_2018031414590001'),('SUR_2018041010340001','SR_201502260001','SU_201411170001'),('SUR_2018041012010001','SR_201502260001','SU_2018041012010001'),('SUR_2018041012040001','SR_201502260001','SU_2018041012040001'),('SUR_2018041012070001','SR_201502260001','SU_2018041012070001'),('SUR_2018041012190001','SR_201502260001','SU_2018041012190001');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
