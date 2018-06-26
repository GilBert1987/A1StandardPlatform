/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.1.9-MariaDB : Database - activemq
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`activemq` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `activemq`;

/*Table structure for table `activemq_acks` */

DROP TABLE IF EXISTS `activemq_acks`;

CREATE TABLE `activemq_acks` (
  `CONTAINER` varchar(250) NOT NULL,
  `SUB_DEST` varchar(250) DEFAULT NULL,
  `CLIENT_ID` varchar(250) NOT NULL,
  `SUB_NAME` varchar(250) NOT NULL,
  `SELECTOR` varchar(250) DEFAULT NULL,
  `LAST_ACKED_ID` bigint(20) DEFAULT NULL,
  `PRIORITY` bigint(20) NOT NULL DEFAULT '5',
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`CONTAINER`,`CLIENT_ID`,`SUB_NAME`,`PRIORITY`),
  KEY `ACTIVEMQ_ACKS_XIDX` (`XID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activemq_acks` */

/*Table structure for table `activemq_computer` */

DROP TABLE IF EXISTS `activemq_computer`;

CREATE TABLE `activemq_computer` (
  `id` varchar(32) NOT NULL,
  `ac_name` varchar(512) DEFAULT NULL,
  `ac_link` varchar(1024) DEFAULT NULL,
  `ac_trInfo` int(11) DEFAULT NULL,
  `ac_remark` varchar(8000) DEFAULT NULL,
  `ac_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activemq_computer` */

insert  into `activemq_computer`(`id`,`ac_name`,`ac_link`,`ac_trInfo`,`ac_remark`,`ac_state`) values ('1','PC-201711261347','failover:(tcp://localhost:61616)?initialReconnectDelay=100',1,NULL,1);

/*Table structure for table `activemq_interface` */

DROP TABLE IF EXISTS `activemq_interface`;

CREATE TABLE `activemq_interface` (
  `id` varchar(32) NOT NULL,
  `ac_id` varchar(32) DEFAULT NULL,
  `al_destinationtype` varchar(32) DEFAULT NULL,
  `al_servertype` varchar(32) DEFAULT NULL,
  `al_listname` varchar(32) DEFAULT NULL,
  `al_name` varchar(512) DEFAULT NULL,
  `al_classinfo` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activemq_interface` */

insert  into `activemq_interface`(`id`,`ac_id`,`al_destinationtype`,`al_servertype`,`al_listname`,`al_name`,`al_classinfo`) values ('1','1','queue','dubbo','exportexcelNo1','Excelå¯¼å‡ºç¬¬ä¸€æ­?,'com.common.jms.customer.queue.ExcelReceiver1'),('2','1','queue','dubbo','exportexcelNo2','Excelå¯¼å‡ºç¬¬äºŒæ­?,'com.common.jms.customer.queue.ExcelReceiver2'),('3','1','queue','dubbo','exportexcelNo3','Excelå¯¼å‡ºç¬¬ä¸‰æ­?,'com.common.jms.customer.queue.ExcelReceiver3'),('4','1','queue','dubbo','exportexcelNo4','Excelå¯¼å‡ºç¬¬å››æ­?,'com.common.jms.customer.queue.ExcelReceiver4');

/*Table structure for table `activemq_lock` */

DROP TABLE IF EXISTS `activemq_lock`;

CREATE TABLE `activemq_lock` (
  `ID` bigint(20) NOT NULL,
  `TIME` bigint(20) DEFAULT NULL,
  `BROKER_NAME` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activemq_lock` */

insert  into `activemq_lock`(`ID`,`TIME`,`BROKER_NAME`) values (1,NULL,NULL);

/*Table structure for table `activemq_msgs` */

DROP TABLE IF EXISTS `activemq_msgs`;

CREATE TABLE `activemq_msgs` (
  `ID` bigint(20) NOT NULL,
  `CONTAINER` varchar(250) DEFAULT NULL,
  `MSGID_PROD` varchar(250) DEFAULT NULL,
  `MSGID_SEQ` bigint(20) DEFAULT NULL,
  `EXPIRATION` bigint(20) DEFAULT NULL,
  `MSG` longblob,
  `PRIORITY` bigint(20) DEFAULT NULL,
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ACTIVEMQ_MSGS_MIDX` (`MSGID_PROD`,`MSGID_SEQ`),
  KEY `ACTIVEMQ_MSGS_CIDX` (`CONTAINER`),
  KEY `ACTIVEMQ_MSGS_EIDX` (`EXPIRATION`),
  KEY `ACTIVEMQ_MSGS_PIDX` (`PRIORITY`),
  KEY `ACTIVEMQ_MSGS_XIDX` (`XID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activemq_msgs` */

insert  into `activemq_msgs`(`ID`,`CONTAINER`,`MSGID_PROD`,`MSGID_SEQ`,`EXPIRATION`,`MSG`,`PRIORITY`,`XID`) values (1,'queue://PC-201711261347_dubbo_queue:exportexcelNo1','ID:PC-201711261347-4986-1530020133354-5:1:1:1',1,0,'\0\05\0\0\0\0{\0)ID:PC-201711261347-4986-1530020133354-5:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0d\0*PC-201711261347_dubbo_queue:exportexcelNo1o\0\0\0\0\0\0\0x\0)ID:PC-201711261347-4986-1530020133354-5:1\0n\0{\0)ID:PC-201711261347-4986-1530020133354-5:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0W\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<Nðß\0\0\0ò\0\0î{\"queryid\":\"RE_201806262136030001\",\"titleid\":\"RE_201806262136030002\",\"key\":\"FI_2018062621360001\",\"code\":\"sys_form\",\"sysid\":\"com\",\"datatype\":\"sql\",\"sql\":\"select * from ( SELECT id,code,name FROM sc_form WHERE typeid=\'CC_007\') as t \",\"where\":\"\",\"orderBy\":\"id\",\"listParams\":[],\"extend\":[],\"group\":[],\"extendcol\":[{\"key\":\"id\",\"sqlinfo\":\"\"},{\"key\":\"code\",\"sqlinfo\":\"\"},{\"key\":\"name\",\"sqlinfo\":\"\"}],\"extendcountcol\":[{\"key\":\"id\",\"sqlinfo\":\"\"},{\"key\":\"code\",\"sqlinfo\":\"\"},{\"key\":\"name\",\"sqlinfo\":\"\"}]}\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<Nðã\0\0\0\0\0\0\0\0\0',0,NULL),(2,'queue://PC-201711261347_dubbo_queue:exportexcelNo2','ID:PC-201711261347-4880-1530020082734-5:1:1:1',1,0,'\0\08\0\0\0\0{\0)ID:PC-201711261347-4880-1530020082734-5:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0d\0*PC-201711261347_dubbo_queue:exportexcelNo2o\0\0\0\0\0\0\0x\0)ID:PC-201711261347-4880-1530020082734-5:1\0n\0{\0)ID:PC-201711261347-4880-1530020082734-5:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\\\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<Nñ´\0\0\0õ\0\0ñ{\"sql\":\"select * from ( SELECT id,code,name FROM sc_form WHERE typeid=\'CC_007\') as t \",\"where\":\"\",\"queryid\":\"RE_201806262136030001\",\"currkey\":\"RE_201806262136030003\",\"subList\":[{\"key\":\"RE_201806262136030003\"}],\"titleid\":\"RE_201806262136030002\",\"key\":\"FI_2018062621360001\",\"extend\":[],\"extendcol\":[{\"key\":\"id\",\"sqlinfo\":\"\"},{\"key\":\"code\",\"sqlinfo\":\"\"},{\"key\":\"name\",\"sqlinfo\":\"\"}],\"extendcountcol\":[{\"key\":\"id\",\"sqlinfo\":\"\"},{\"key\":\"code\",\"sqlinfo\":\"\"},{\"key\":\"name\",\"sqlinfo\":\"\"}],\"listParams\":[]}\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<Nñµ\0\0\0\0\0\0\0\0\0',0,NULL),(3,'queue://PC-201711261347_dubbo_queue:exportexcelNo3','ID:PC-201711261347-4880-1530020082734-7:1:1:1',1,0,'\0\0\0\0\0\0{\0)ID:PC-201711261347-4880-1530020082734-7:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0d\0*PC-201711261347_dubbo_queue:exportexcelNo3o\0\0\0\0\0\0\0x\0)ID:PC-201711261347-4880-1530020082734-7:1\0n\0{\0)ID:PC-201711261347-4880-1530020082734-7:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0a\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<N?\0\0\0Ó\0\0Ï{\"sql\":\"select * from ( SELECT id,code,name FROM sc_form WHERE typeid=\'CC_007\') as t \",\"where\":\"\",\"queryid\":\"RE_201806262136030001\",\"subList\":[{\"key\":\"RE_201806262136030003\"}],\"titleid\":\"RE_201806262136030002\",\"key\":\"FI_2018062621360001\",\"extend\":[],\"extendcol\":[{\"key\":\"id\",\"sqlinfo\":\"\"},{\"key\":\"code\",\"sqlinfo\":\"\"},{\"key\":\"name\",\"sqlinfo\":\"\"}],\"extendcountcol\":[{\"key\":\"id\",\"sqlinfo\":\"\"},{\"key\":\"code\",\"sqlinfo\":\"\"},{\"key\":\"name\",\"sqlinfo\":\"\"}],\"listParams\":[]}\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<N?\0\0\0\0\0\0\0\0\0',0,NULL),(4,'queue://PC-201711261347_dubbo_queue:exportexcelNo4','ID:PC-201711261347-4880-1530020082734-9:1:1:1',1,0,'\0\0?\0\0\0\0{\0)ID:PC-201711261347-4880-1530020082734-9:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0d\0*PC-201711261347_dubbo_queue:exportexcelNo4o\0\0\0\0\0\0\0x\0)ID:PC-201711261347-4880-1530020082734-9:1\0n\0{\0)ID:PC-201711261347-4880-1530020082734-9:1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0f\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<N?\0\0\0\0G\0\0\0C{\"key\":\"FI_2018062621360001\",\"filename\":\"FI_2018062621360001.xlsx\"}\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0d<N?\0\0\0\0\0\0\0\0\0',0,NULL);

/*Table structure for table `activemq_send` */

DROP TABLE IF EXISTS `activemq_send`;

CREATE TABLE `activemq_send` (
  `as_id` varchar(32) NOT NULL,
  `ac_id` varchar(32) DEFAULT NULL,
  `as_name` varchar(512) DEFAULT NULL,
  `as_destinationtype` varchar(32) DEFAULT NULL,
  `as_servertype` varchar(32) DEFAULT NULL,
  `as_listname` varchar(32) DEFAULT NULL,
  `as_remark` varchar(8000) DEFAULT NULL,
  PRIMARY KEY (`as_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activemq_send` */

insert  into `activemq_send`(`as_id`,`ac_id`,`as_name`,`as_destinationtype`,`as_servertype`,`as_listname`,`as_remark`) values ('1','1','Excelå¯¼å‡ºç¬¬ä¸€æ­?,'queue','dubbo','exportexcelNo1',''),('2','1','Excelå¯¼å‡ºç¬¬äºŒæ­?,'queue','dubbo','exportexcelNo2',''),('3','1','Excelå¯¼å‡ºç¬¬ä¸‰æ­?,'queue','dubbo','exportexcelNo3',''),('4','1','Excelå¯¼å‡ºç¬¬å››æ­?,'queue','dubbo','exportexcelNo4',''),('5','1','è‡ªåŠ¨å…³é—­è®¢å•','queue','dubbo','closeOrderFinishReceiver','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
