/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.1.9-MariaDB : Database - activiti
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`activiti` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `activiti`;

/*Table structure for table `act_evt_log` */

DROP TABLE IF EXISTS `act_evt_log`;

CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_evt_log` */

/*Table structure for table `act_ge_bytearray` */

DROP TABLE IF EXISTS `act_ge_bytearray`;

CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ge_bytearray` */

insert  into `act_ge_bytearray`(`ID_`,`REV_`,`NAME_`,`DEPLOYMENT_ID_`,`BYTES_`,`GENERATED_`) values 
('32952d9bd71d11e795b0a01d48d28732',1,'source',NULL,'{\"modelId\":\"\",\"bounds\":{\"lowerRight\":{\"x\":\"1485\",\"y\":\"250\"},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"WF_2017120212560001\",\"name\":\"流程案例\",\"documentation\":\"流程案例\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"http://www.flowable.org/processdef\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"width\":\"1485\",\"height\":\"250\"},\"childShapes\":[{\"resourceId\":\"ST_1\",\"properties\":{\"overrideid\":\"\",\"name\":\"开始\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formreference\":\"\",\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_5\"}],\"bounds\":{\"lowerRight\":{\"x\":14447,\"y\":2640},\"upperLeft\":{\"x\":144,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"ED_2\",\"properties\":{\"overrideid\":\"ED_2\",\"name\":\"结束\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":61747,\"y\":8040},\"upperLeft\":{\"x\":617,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"UT_4\",\"properties\":{\"overrideid\":\"UT_4\",\"name\":\"部门领导\",\"usertype\":\"1\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"部门领导\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"${UT_4_candidateUsersId}\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \"}],\"assignment\":{\"candidateUsers\":[{\"value\":\"${UT_4_candidateUsersId}\"}]}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_7\"},{\"resourceId\":\"SF_37\"}],\"bounds\":{\"lowerRight\":{\"x\":22382,\"y\":2640},\"upperLeft\":{\"x\":223,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"FK_6\",\"properties\":{\"overrideid\":\"FK_6\",\"name\":\"分支\",\"documentation\":\"\"},\"stencil\":{\"id\":\"ExclusiveGateway\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_9\"},{\"resourceId\":\"SF_15\"}],\"bounds\":{\"lowerRight\":{\"x\":33047,\"y\":2640},\"upperLeft\":{\"x\":330,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"UT_8\",\"properties\":{\"overrideid\":\"UT_8\",\"name\":\"个人签收\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"个人签收\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_8_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"SU_201411170002\",\"name\":\"测试用户\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_8_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_39\"},{\"resourceId\":\"SF_40\"}],\"bounds\":{\"lowerRight\":{\"x\":46782,\"y\":3640},\"upperLeft\":{\"x\":467,\"y\":36}},\"dockers\":[]},{\"resourceId\":\"UT_14\",\"properties\":{\"overrideid\":\"UT_14\",\"name\":\"集体会签\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"UT_14_assigneeUsersId\",\"multiinstance_condition\":\"%24%7BnrOfCompletedInstances%2FnrOfInstances%3D%3D1%7D\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"UT_14_assigneeUserId\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"集体会签\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_14_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_14_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_43\"}],\"bounds\":{\"lowerRight\":{\"x\":40982,\"y\":8040},\"upperLeft\":{\"x\":409,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"UT_20\",\"properties\":{\"overrideid\":\"UT_20\",\"name\":\"单人任务\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"单人任务\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_20_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_20_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_23\"},{\"resourceId\":\"SF_41\"}],\"bounds\":{\"lowerRight\":{\"x\":58582,\"y\":3640},\"upperLeft\":{\"x\":585,\"y\":36}},\"dockers\":[]},{\"resourceId\":\"UT_26\",\"properties\":{\"overrideid\":\"UT_26\",\"name\":\"回退节点\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"回退节点\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_26_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \"}],\"assignment\":{\"assignee\":\"${UT_26_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_27\"}],\"bounds\":{\"lowerRight\":{\"x\":15082,\"y\":9440},\"upperLeft\":{\"x\":150,\"y\":94}},\"dockers\":[]},{\"resourceId\":\"UT_42\",\"properties\":{\"overrideid\":\"UT_42\",\"name\":\"会议纪要\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"会议纪要\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_42_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"1\",\"name\":\"超级管理员\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_42_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_44\"},{\"resourceId\":\"SF_45\"}],\"bounds\":{\"lowerRight\":{\"x\":51782,\"y\":8040},\"upperLeft\":{\"x\":517,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"SF_5\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_5\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":186,\"y\":46},\"upperLeft\":{\"x\":144,\"y\":46}},\"dots\":[],\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_7\",\"properties\":{\"overrideid\":\"SF_7\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"FK_6\"}],\"bounds\":{\"lowerRight\":{\"x\":300,\"y\":46},\"upperLeft\":{\"x\":265,\"y\":46}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":21,\"y\":21}],\"target\":{\"resourceId\":\"FK_6\"}},{\"resourceId\":\"SF_37\",\"properties\":{\"overrideid\":\"SF_37\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":250,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":118,\"y\":-87}},\"dots\":[{\"x\":264,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_9\",\"properties\":{\"overrideid\":\"SF_9\",\"name\":\"单人签收\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120001%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"2\",\"textX\":483,\"textY\":22},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_8\"}],\"bounds\":{\"lowerRight\":{\"x\":354,\"y\":13},\"upperLeft\":{\"x\":199,\"y\":-12}},\"dots\":[{\"x\":353.5,\"y\":12.5},{\"x\":508,\"y\":12.5}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_8\"}},{\"resourceId\":\"SF_15\",\"properties\":{\"overrideid\":\"SF_15\",\"name\":\"集体会签\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120002%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":427,\"textY\":73},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_14\"}],\"bounds\":{\"lowerRight\":{\"x\":372,\"y\":46},\"upperLeft\":{\"x\":330,\"y\":-8}},\"dots\":[{\"x\":390,\"y\":46},{\"x\":390,\"y\":100}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_14\"}},{\"resourceId\":\"SF_39\",\"properties\":{\"overrideid\":\"SF_39\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_20\"}],\"bounds\":{\"lowerRight\":{\"x\":544,\"y\":56},\"upperLeft\":{\"x\":498,\"y\":56}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_20\"}},{\"resourceId\":\"SF_40\",\"properties\":{\"overrideid\":\"SF_40\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":205,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-126,\"y\":-87}},\"dots\":[{\"x\":508,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_43\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_43\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"executionlisteners\":\"\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_42\"}],\"bounds\":{\"lowerRight\":{\"x\":486,\"y\":100},\"upperLeft\":{\"x\":450,\"y\":100}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_42\"}},{\"resourceId\":\"SF_23\",\"properties\":{\"overrideid\":\"SF_23\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"1\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":659,\"y\":56},\"upperLeft\":{\"x\":637,\"y\":12}},\"dots\":[{\"x\":680.75,\"y\":56},{\"x\":680.75,\"y\":99}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_41\",\"properties\":{\"overrideid\":\"SF_41\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":612,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-244,\"y\":-87}},\"dots\":[{\"x\":626,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_27\",\"properties\":{\"overrideid\":\"SF_27\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":223,\"textY\":72},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":65},\"upperLeft\":{\"x\":118,\"y\":34}},\"dots\":[{\"x\":191,\"y\":80.5},{\"x\":264,\"y\":80.5}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_44\",\"properties\":{\"overrideid\":\"SF_44\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":594,\"y\":100},\"upperLeft\":{\"x\":566,\"y\":100}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_45\",\"properties\":{\"overrideid\":\"SF_45\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"4\",\"textX\":0,\"textY\":0},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-176,\"y\":-87}},\"dots\":[{\"x\":558,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"./stencilsets/bpmn2.0/bpmn2.0.json\"},\"ssextensions\":[],\"maxCount\":45,\"initTime\":\"1512190593167\"}',NULL),
('595ff35dc21d11e7b894a01d48d28732',2,'source',NULL,'{\"modelId\":\"595c49dcc21d11e7b894a01d48d28732\",\"bounds\":{\"lowerRight\":{\"x\":\"1485\",\"y\":\"1050\"},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"WF_2017110519340001\",\"name\":\"流程案例\",\"documentation\":\"流程案例\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"CC_035\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"width\":\"1485\",\"height\":\"1050\"},\"childShapes\":[{\"resourceId\":\"ST_1\",\"properties\":{\"overrideid\":\"\",\"name\":\"开始\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formreference\":\"\",\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_5\"}],\"bounds\":{\"lowerRight\":{\"x\":14447,\"y\":2640},\"upperLeft\":{\"x\":144,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"ED_2\",\"properties\":{\"overrideid\":\"ED_2\",\"name\":\"结束\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":61747,\"y\":8040},\"upperLeft\":{\"x\":617,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"UT_4\",\"properties\":{\"overrideid\":\"UT_4\",\"name\":\"部门领导\",\"usertype\":\"1\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"部门领导\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"${UT_4_candidateUsersId}\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \"}],\"assignment\":{\"candidateUsers\":[{\"value\":\"${UT_4_candidateUsersId}\"}]}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_7\"},{\"resourceId\":\"SF_37\"}],\"bounds\":{\"lowerRight\":{\"x\":22382,\"y\":2640},\"upperLeft\":{\"x\":223,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"FK_6\",\"properties\":{\"overrideid\":\"FK_6\",\"name\":\"分支\",\"documentation\":\"\"},\"stencil\":{\"id\":\"ExclusiveGateway\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_9\"},{\"resourceId\":\"SF_15\"}],\"bounds\":{\"lowerRight\":{\"x\":33047,\"y\":2640},\"upperLeft\":{\"x\":330,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"UT_8\",\"properties\":{\"overrideid\":\"UT_8\",\"name\":\"个人签收\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"个人签收\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_8_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"SU_201411170002\",\"name\":\"测试用户\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_8_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_39\"},{\"resourceId\":\"SF_40\"}],\"bounds\":{\"lowerRight\":{\"x\":46782,\"y\":3640},\"upperLeft\":{\"x\":467,\"y\":36}},\"dockers\":[]},{\"resourceId\":\"UT_14\",\"properties\":{\"overrideid\":\"UT_14\",\"name\":\"集体会签\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"UT_14_assigneeUsersId\",\"multiinstance_condition\":\"%24%7BnrOfCompletedInstances%2FnrOfInstances%3D%3D1%7D\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"UT_14_assigneeUserId\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"集体会签\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_14_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_14_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_43\"}],\"bounds\":{\"lowerRight\":{\"x\":40982,\"y\":8040},\"upperLeft\":{\"x\":409,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"UT_20\",\"properties\":{\"overrideid\":\"UT_20\",\"name\":\"单人任务\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"单人任务\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_20_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_20_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_23\"},{\"resourceId\":\"SF_41\"}],\"bounds\":{\"lowerRight\":{\"x\":58582,\"y\":3640},\"upperLeft\":{\"x\":585,\"y\":36}},\"dockers\":[]},{\"resourceId\":\"UT_26\",\"properties\":{\"overrideid\":\"UT_26\",\"name\":\"回退节点\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"回退节点\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_26_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \"}],\"assignment\":{\"assignee\":\"${UT_26_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_27\"}],\"bounds\":{\"lowerRight\":{\"x\":15082,\"y\":9440},\"upperLeft\":{\"x\":150,\"y\":94}},\"dockers\":[]},{\"resourceId\":\"UT_42\",\"properties\":{\"overrideid\":\"UT_42\",\"name\":\"会议纪要\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"会议纪要\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_42_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"1\",\"name\":\"超级管理员\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_42_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_44\"},{\"resourceId\":\"SF_45\"}],\"bounds\":{\"lowerRight\":{\"x\":51782,\"y\":8040},\"upperLeft\":{\"x\":517,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"SF_5\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_5\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":186,\"y\":46},\"upperLeft\":{\"x\":144,\"y\":46}},\"dots\":[],\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_7\",\"properties\":{\"overrideid\":\"SF_7\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"FK_6\"}],\"bounds\":{\"lowerRight\":{\"x\":300,\"y\":46},\"upperLeft\":{\"x\":265,\"y\":46}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":21,\"y\":21}],\"target\":{\"resourceId\":\"FK_6\"}},{\"resourceId\":\"SF_37\",\"properties\":{\"overrideid\":\"SF_37\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":250,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":118,\"y\":-87}},\"dots\":[{\"x\":264,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_9\",\"properties\":{\"overrideid\":\"SF_9\",\"name\":\"单人签收\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120001%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"2\",\"textX\":483,\"textY\":22},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_8\"}],\"bounds\":{\"lowerRight\":{\"x\":354,\"y\":13},\"upperLeft\":{\"x\":199,\"y\":-12}},\"dots\":[{\"x\":353.5,\"y\":12.5},{\"x\":508,\"y\":12.5}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_8\"}},{\"resourceId\":\"SF_15\",\"properties\":{\"overrideid\":\"SF_15\",\"name\":\"集体会签\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120002%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":427,\"textY\":73},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_14\"}],\"bounds\":{\"lowerRight\":{\"x\":372,\"y\":46},\"upperLeft\":{\"x\":330,\"y\":-8}},\"dots\":[{\"x\":390,\"y\":46},{\"x\":390,\"y\":100}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_14\"}},{\"resourceId\":\"SF_39\",\"properties\":{\"overrideid\":\"SF_39\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_20\"}],\"bounds\":{\"lowerRight\":{\"x\":544,\"y\":56},\"upperLeft\":{\"x\":498,\"y\":56}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_20\"}},{\"resourceId\":\"SF_40\",\"properties\":{\"overrideid\":\"SF_40\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":205,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-126,\"y\":-87}},\"dots\":[{\"x\":508,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_43\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_43\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"executionlisteners\":\"\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_42\"}],\"bounds\":{\"lowerRight\":{\"x\":486,\"y\":100},\"upperLeft\":{\"x\":450,\"y\":100}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_42\"}},{\"resourceId\":\"SF_23\",\"properties\":{\"overrideid\":\"SF_23\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"1\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":659,\"y\":56},\"upperLeft\":{\"x\":637,\"y\":12}},\"dots\":[{\"x\":680.75,\"y\":56},{\"x\":680.75,\"y\":99}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_41\",\"properties\":{\"overrideid\":\"SF_41\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":612,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-244,\"y\":-87}},\"dots\":[{\"x\":626,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_27\",\"properties\":{\"overrideid\":\"SF_27\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":223,\"textY\":72},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":65},\"upperLeft\":{\"x\":118,\"y\":34}},\"dots\":[{\"x\":191,\"y\":80.5},{\"x\":264,\"y\":80.5}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_44\",\"properties\":{\"overrideid\":\"SF_44\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":594,\"y\":100},\"upperLeft\":{\"x\":566,\"y\":100}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_45\",\"properties\":{\"overrideid\":\"SF_45\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"4\",\"textX\":0,\"textY\":0},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-176,\"y\":-87}},\"dots\":[{\"x\":558,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"./stencilsets/bpmn2.0/bpmn2.0.json\"},\"ssextensions\":[],\"maxCount\":45,\"initTime\":\"1512148438043\"}',NULL),
('8d2b332e204911e8aef8485ab673973d',1,'var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('8d2b5a40204911e8aef8485ab673973d',1,'hist.var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('8d2b8154204911e8aef8485ab673973d',1,'var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('8d2b8156204911e8aef8485ab673973d',1,'hist.var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('8d2bcf80204911e8aef8485ab673973d',1,'var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('8d2bf692204911e8aef8485ab673973d',1,'hist.var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('8d2c44bc204911e8aef8485ab673973d',1,'var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('8d2c44be204911e8aef8485ab673973d',1,'hist.var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('8d2cb9fa204911e8aef8485ab673973d',1,'var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('8d2cb9fc204911e8aef8485ab673973d',1,'hist.var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('WM_2017103009590001',1,'source',NULL,'{\"modelId\":\"\",\"bounds\":{\"lowerRight\":{\"x\":\"1485\",\"y\":\"250\"},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"WF_2017110519340001\",\"name\":\"流程案例\",\"documentation\":\"流程案例\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"width\":\"1485\",\"height\":\"1050\"},\"childShapes\":[{\"resourceId\":\"ST_1\",\"properties\":{\"overrideid\":\"\",\"name\":\"开始\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formreference\":\"\",\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_5\"}],\"bounds\":{\"lowerRight\":{\"x\":14447,\"y\":2640},\"upperLeft\":{\"x\":144,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"ED_2\",\"properties\":{\"overrideid\":\"ED_2\",\"name\":\"结束\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":61747,\"y\":8040},\"upperLeft\":{\"x\":617,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"UT_4\",\"properties\":{\"overrideid\":\"UT_4\",\"name\":\"部门领导\",\"usertype\":\"1\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"部门领导\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"${UT_4_candidateUsersId}\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \"}],\"assignment\":{\"candidateUsers\":[{\"value\":\"${UT_4_candidateUsersId}\"}]}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_7\"},{\"resourceId\":\"SF_37\"}],\"bounds\":{\"lowerRight\":{\"x\":22382,\"y\":2640},\"upperLeft\":{\"x\":223,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"FK_6\",\"properties\":{\"overrideid\":\"FK_6\",\"name\":\"分支\",\"documentation\":\"\"},\"stencil\":{\"id\":\"ExclusiveGateway\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_9\"},{\"resourceId\":\"SF_15\"}],\"bounds\":{\"lowerRight\":{\"x\":33047,\"y\":2640},\"upperLeft\":{\"x\":330,\"y\":26}},\"dockers\":[]},{\"resourceId\":\"UT_8\",\"properties\":{\"overrideid\":\"UT_8\",\"name\":\"个人签收\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"个人签收\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_8_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"SU_201411170002\",\"name\":\"测试用户\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_8_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_39\"},{\"resourceId\":\"SF_40\"}],\"bounds\":{\"lowerRight\":{\"x\":46782,\"y\":3640},\"upperLeft\":{\"x\":467,\"y\":36}},\"dockers\":[]},{\"resourceId\":\"UT_14\",\"properties\":{\"overrideid\":\"UT_14\",\"name\":\"集体会签\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"UT_14_assigneeUsersId\",\"multiinstance_condition\":\"%24%7BnrOfCompletedInstances%2FnrOfInstances%3D%3D1%7D\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"UT_14_assigneeUserId\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"集体会签\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_14_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_14_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_43\"}],\"bounds\":{\"lowerRight\":{\"x\":40982,\"y\":8040},\"upperLeft\":{\"x\":409,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"UT_20\",\"properties\":{\"overrideid\":\"UT_20\",\"name\":\"单人任务\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"单人任务\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_20_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_20_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_23\"},{\"resourceId\":\"SF_41\"}],\"bounds\":{\"lowerRight\":{\"x\":58582,\"y\":3640},\"upperLeft\":{\"x\":585,\"y\":36}},\"dockers\":[]},{\"resourceId\":\"UT_26\",\"properties\":{\"overrideid\":\"UT_26\",\"name\":\"回退节点\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"回退节点\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_26_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \"}],\"assignment\":{\"assignee\":\"${UT_26_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_27\"}],\"bounds\":{\"lowerRight\":{\"x\":15082,\"y\":9440},\"upperLeft\":{\"x\":150,\"y\":94}},\"dockers\":[]},{\"resourceId\":\"UT_42\",\"properties\":{\"overrideid\":\"UT_42\",\"name\":\"会议纪要\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"会议纪要\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_42_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"1\",\"name\":\"超级管理员\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_42_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_44\"},{\"resourceId\":\"SF_45\"}],\"bounds\":{\"lowerRight\":{\"x\":51782,\"y\":8040},\"upperLeft\":{\"x\":517,\"y\":80}},\"dockers\":[]},{\"resourceId\":\"SF_5\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_5\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":186,\"y\":46},\"upperLeft\":{\"x\":144,\"y\":46}},\"dots\":[],\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_7\",\"properties\":{\"overrideid\":\"SF_7\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"FK_6\"}],\"bounds\":{\"lowerRight\":{\"x\":300,\"y\":46},\"upperLeft\":{\"x\":265,\"y\":46}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":21,\"y\":21}],\"target\":{\"resourceId\":\"FK_6\"}},{\"resourceId\":\"SF_37\",\"properties\":{\"overrideid\":\"SF_37\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":250,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":118,\"y\":-87}},\"dots\":[{\"x\":264,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_9\",\"properties\":{\"overrideid\":\"SF_9\",\"name\":\"单人签收\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120001%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"2\",\"textX\":483,\"textY\":22},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_8\"}],\"bounds\":{\"lowerRight\":{\"x\":354,\"y\":13},\"upperLeft\":{\"x\":199,\"y\":-12}},\"dots\":[{\"x\":353.5,\"y\":12.5},{\"x\":508,\"y\":12.5}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_8\"}},{\"resourceId\":\"SF_15\",\"properties\":{\"overrideid\":\"SF_15\",\"name\":\"集体会签\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120002%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":427,\"textY\":73},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_14\"}],\"bounds\":{\"lowerRight\":{\"x\":372,\"y\":46},\"upperLeft\":{\"x\":330,\"y\":-8}},\"dots\":[{\"x\":390,\"y\":46},{\"x\":390,\"y\":100}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_14\"}},{\"resourceId\":\"SF_39\",\"properties\":{\"overrideid\":\"SF_39\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_20\"}],\"bounds\":{\"lowerRight\":{\"x\":544,\"y\":56},\"upperLeft\":{\"x\":498,\"y\":56}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_20\"}},{\"resourceId\":\"SF_40\",\"properties\":{\"overrideid\":\"SF_40\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":205,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-126,\"y\":-87}},\"dots\":[{\"x\":508,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_43\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_43\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"executionlisteners\":\"\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_42\"}],\"bounds\":{\"lowerRight\":{\"x\":486,\"y\":100},\"upperLeft\":{\"x\":450,\"y\":100}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_42\"}},{\"resourceId\":\"SF_23\",\"properties\":{\"overrideid\":\"SF_23\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"1\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":659,\"y\":56},\"upperLeft\":{\"x\":637,\"y\":12}},\"dots\":[{\"x\":680.75,\"y\":56},{\"x\":680.75,\"y\":99}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_41\",\"properties\":{\"overrideid\":\"SF_41\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":612,\"textY\":12},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-244,\"y\":-87}},\"dots\":[{\"x\":626,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_27\",\"properties\":{\"overrideid\":\"SF_27\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":223,\"textY\":72},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":65},\"upperLeft\":{\"x\":118,\"y\":34}},\"dots\":[{\"x\":191,\"y\":80.5},{\"x\":264,\"y\":80.5}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_44\",\"properties\":{\"overrideid\":\"SF_44\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":594,\"y\":100},\"upperLeft\":{\"x\":566,\"y\":100}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_45\",\"properties\":{\"overrideid\":\"SF_45\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"4\",\"textX\":0,\"textY\":0},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-176,\"y\":-87}},\"dots\":[{\"x\":558,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"./stencilsets/bpmn2.0/bpmn2.0.json\"},\"ssextensions\":[],\"maxCount\":45,\"initTime\":\"1509811740651\"}',NULL),
('a99a55a1c22211e7b894a01d48d28732',1,'bcd1734ec21e11e7b894a01d48d28732.json','a99a2e90c22211e7b894a01d48d28732','{\"modelId\":\"bcd1734ec21e11e7b894a01d48d28732\",\"bounds\":{\"lowerRight\":{\"x\":\"1485\",\"y\":\"250\"},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"WF_2017110519440001\",\"name\":\"流程案例\",\"documentation\":\"流程案例\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"CC_035\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"width\":\"1485\",\"height\":\"250\"},\"childShapes\":[{\"resourceId\":\"ST_1\",\"properties\":{\"overrideid\":\"\",\"name\":\"开始\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formreference\":\"\",\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_5\"}],\"bounds\":{\"lowerRight\":{\"x\":12847,\"y\":5940},\"upperLeft\":{\"x\":128,\"y\":59}},\"dockers\":[]},{\"resourceId\":\"ED_2\",\"properties\":{\"overrideid\":\"ED_2\",\"name\":\"结束\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":63747,\"y\":9340},\"upperLeft\":{\"x\":637,\"y\":93}},\"dockers\":[]},{\"resourceId\":\"UT_4\",\"properties\":{\"overrideid\":\"UT_4\",\"name\":\"部门领导\",\"usertype\":\"1\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"部门领导\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"${UT_4_candidateUsersId}\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \"}],\"assignment\":{\"candidateUsers\":[{\"value\":\"${UT_4_candidateUsersId}\"}]}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_7\"},{\"resourceId\":\"SF_37\"}],\"bounds\":{\"lowerRight\":{\"x\":21082,\"y\":5940},\"upperLeft\":{\"x\":210,\"y\":59}},\"dockers\":[]},{\"resourceId\":\"FK_6\",\"properties\":{\"overrideid\":\"FK_6\",\"name\":\"分支\",\"documentation\":\"\"},\"stencil\":{\"id\":\"ExclusiveGateway\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_9\"},{\"resourceId\":\"SF_15\"}],\"bounds\":{\"lowerRight\":{\"x\":32847,\"y\":5940},\"upperLeft\":{\"x\":328,\"y\":59}},\"dockers\":[]},{\"resourceId\":\"UT_8\",\"properties\":{\"overrideid\":\"UT_8\",\"name\":\"个人签收\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"个人签收\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_8_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"SU_201411170002\",\"name\":\"测试用户\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_8_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_39\"},{\"resourceId\":\"SF_40\"}],\"bounds\":{\"lowerRight\":{\"x\":40682,\"y\":2940},\"upperLeft\":{\"x\":406,\"y\":29}},\"dockers\":[]},{\"resourceId\":\"UT_14\",\"properties\":{\"overrideid\":\"UT_14\",\"name\":\"集体会签\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"UT_14_assigneeUsersId\",\"multiinstance_condition\":\"%24%7BnrOfCompletedInstances%2FnrOfInstances%3D%3D1%7D\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"UT_14_assigneeUserId\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"集体会签\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_14_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_14_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_43\"}],\"bounds\":{\"lowerRight\":{\"x\":40582,\"y\":9340},\"upperLeft\":{\"x\":405,\"y\":93}},\"dockers\":[]},{\"resourceId\":\"UT_20\",\"properties\":{\"overrideid\":\"UT_20\",\"name\":\"单人任务\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"单人任务\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_20_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_20_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_23\"},{\"resourceId\":\"SF_41\"}],\"bounds\":{\"lowerRight\":{\"x\":56582,\"y\":2940},\"upperLeft\":{\"x\":565,\"y\":29}},\"dockers\":[]},{\"resourceId\":\"UT_26\",\"properties\":{\"overrideid\":\"UT_26\",\"name\":\"回退节点\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"回退节点\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_26_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \"}],\"assignment\":{\"assignee\":\"${UT_26_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_27\"}],\"bounds\":{\"lowerRight\":{\"x\":15082,\"y\":11940},\"upperLeft\":{\"x\":150,\"y\":119}},\"dockers\":[]},{\"resourceId\":\"UT_42\",\"properties\":{\"overrideid\":\"UT_42\",\"name\":\"会议纪要\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"会议纪要\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_42_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"1\",\"name\":\"超级管理员\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_42_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_44\"},{\"resourceId\":\"SF_45\"}],\"bounds\":{\"lowerRight\":{\"x\":51382,\"y\":9340},\"upperLeft\":{\"x\":513,\"y\":93}},\"dockers\":[]},{\"resourceId\":\"SF_5\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_5\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":170,\"y\":79},\"upperLeft\":{\"x\":125,\"y\":79}},\"dots\":[],\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_7\",\"properties\":{\"overrideid\":\"SF_7\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"0\",\"textX\":316,\"textY\":75},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"FK_6\"}],\"bounds\":{\"lowerRight\":{\"x\":287,\"y\":79},\"upperLeft\":{\"x\":241,\"y\":79}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":21,\"y\":21}],\"target\":{\"resourceId\":\"FK_6\"}},{\"resourceId\":\"SF_37\",\"properties\":{\"overrideid\":\"SF_37\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":430,\"textY\":32},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":48},\"upperLeft\":{\"x\":131,\"y\":-24}},\"dots\":[{\"x\":251,\"y\":48},{\"x\":191,\"y\":48}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_9\",\"properties\":{\"overrideid\":\"SF_9\",\"name\":\"单人签收\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120001%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":367,\"textY\":54},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_8\"}],\"bounds\":{\"lowerRight\":{\"x\":370,\"y\":49},\"upperLeft\":{\"x\":329,\"y\":19}},\"dots\":[{\"x\":391,\"y\":79},{\"x\":391,\"y\":49}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_8\"}},{\"resourceId\":\"SF_15\",\"properties\":{\"overrideid\":\"SF_15\",\"name\":\"集体会签\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120002%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":366,\"textY\":114},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_14\"}],\"bounds\":{\"lowerRight\":{\"x\":370,\"y\":79},\"upperLeft\":{\"x\":330,\"y\":45}},\"dots\":[{\"x\":391,\"y\":79},{\"x\":391,\"y\":113}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_14\"}},{\"resourceId\":\"SF_39\",\"properties\":{\"overrideid\":\"SF_39\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\",\"textX\":523,\"textY\":45},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_20\"}],\"bounds\":{\"lowerRight\":{\"x\":483,\"y\":49},\"upperLeft\":{\"x\":396,\"y\":49}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_20\"}},{\"resourceId\":\"SF_40\",\"properties\":{\"overrideid\":\"SF_40\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":237,\"textY\":59},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":22},\"upperLeft\":{\"x\":-65,\"y\":-76}},\"dots\":[{\"x\":447,\"y\":22},{\"x\":191,\"y\":22}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_43\",\"properties\":{\"overrideid\":\"SF_43\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"0\",\"textX\":494,\"textY\":109},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_42\"}],\"bounds\":{\"lowerRight\":{\"x\":482,\"y\":113},\"upperLeft\":{\"x\":446,\"y\":113}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_42\"}},{\"resourceId\":\"SF_23\",\"properties\":{\"overrideid\":\"SF_23\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":654,\"textY\":45},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":642,\"y\":49},\"upperLeft\":{\"x\":575,\"y\":-15}},\"dots\":[{\"x\":709,\"y\":49},{\"x\":709,\"y\":113}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_41\",\"properties\":{\"overrideid\":\"SF_41\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":544,\"textY\":27},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":13},\"upperLeft\":{\"x\":-224,\"y\":-94}},\"dots\":[{\"x\":606,\"y\":13},{\"x\":191,\"y\":13}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_27\",\"properties\":{\"overrideid\":\"SF_27\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":234,\"textY\":107},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":98},\"upperLeft\":{\"x\":131,\"y\":75}},\"dots\":[{\"x\":191,\"y\":111},{\"x\":251,\"y\":111}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_44\",\"properties\":{\"overrideid\":\"SF_44\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\",\"textX\":610,\"textY\":109},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":590,\"y\":113},\"upperLeft\":{\"x\":538,\"y\":113}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_45\",\"properties\":{\"overrideid\":\"SF_45\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":593,\"textY\":25},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-172,\"y\":-112}},\"dots\":[{\"x\":554,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"./stencilsets/bpmn2.0/bpmn2.0.json\"},\"ssextensions\":[],\"maxCount\":45,\"initTime\":\"1509883964861\"}',0),
('a99a55a2c22211e7b894a01d48d28732',1,'bcd1734ec21e11e7b894a01d48d28732.bpmn20.xml','a99a2e90c22211e7b894a01d48d28732','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"CC_035\">\n  <process id=\"WF_2017110519440001\" name=\"流程案例\" isExecutable=\"true\">\n    <documentation>流程案例</documentation>\n    <startEvent id=\"ST_1\" name=\"开始\" activiti:formKey=\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\"></startEvent>\n    <endEvent id=\"ED_2\" name=\"结束\"></endEvent>\n    <userTask id=\"UT_4\" name=\"部门领导\" default=\"SF_7\" activiti:candidateUsers=\"${UT_4_candidateUsersId}\">\n      <documentation>部门领导</documentation>\n    </userTask>\n    <exclusiveGateway id=\"FK_6\" name=\"分支\"></exclusiveGateway>\n    <userTask id=\"UT_8\" name=\"个人签收\" default=\"SF_39\" activiti:assignee=\"${UT_8_assigneeUserId}\">\n      <documentation>个人签收</documentation>\n    </userTask>\n    <userTask id=\"UT_14\" name=\"集体会签\" activiti:assignee=\"${UT_14_assigneeUserId}\">\n      <documentation>集体会签</documentation>\n      <multiInstanceLoopCharacteristics isSequential=\"false\" activiti:collection=\"UT_14_assigneeUsersId\" activiti:elementVariable=\"UT_14_assigneeUserId\">\n        <completionCondition>${nrOfCompletedInstances/nrOfInstances==1}</completionCondition>\n      </multiInstanceLoopCharacteristics>\n    </userTask>\n    <userTask id=\"UT_20\" name=\"单人任务\" default=\"SF_23\" activiti:assignee=\"${UT_20_assigneeUserId}\">\n      <documentation>单人任务</documentation>\n    </userTask>\n    <userTask id=\"UT_26\" name=\"回退节点\" default=\"SF_27\" activiti:assignee=\"${UT_26_assigneeUserId}\">\n      <documentation>回退节点</documentation>\n    </userTask>\n    <userTask id=\"UT_42\" name=\"会议纪要\" default=\"SF_44\" activiti:assignee=\"${UT_42_assigneeUserId}\">\n      <documentation>会议纪要</documentation>\n    </userTask>\n    <sequenceFlow id=\"SF_5\" sourceRef=\"ST_1\" targetRef=\"UT_4\"></sequenceFlow>\n    <sequenceFlow id=\"SF_7\" name=\"提交\" sourceRef=\"UT_4\" targetRef=\"FK_6\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${\'1\'==_submitInfo}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_37\" name=\"退回\" sourceRef=\"UT_4\" targetRef=\"UT_26\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${\'0\'==_submitInfo}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_9\" name=\"单人签收\" sourceRef=\"FK_6\" targetRef=\"UT_8\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${teacherId==\"PT_201504120001\"}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_15\" name=\"集体会签\" sourceRef=\"FK_6\" targetRef=\"UT_14\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${teacherId==\"PT_201504120002\"}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_39\" name=\"提交\" sourceRef=\"UT_8\" targetRef=\"UT_20\"></sequenceFlow>\n    <sequenceFlow id=\"SF_40\" name=\"退回\" sourceRef=\"UT_8\" targetRef=\"UT_26\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${\'0\'==_submitInfo}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_43\" name=\"提交\" sourceRef=\"UT_14\" targetRef=\"UT_42\"></sequenceFlow>\n    <sequenceFlow id=\"SF_23\" name=\"提交\" sourceRef=\"UT_20\" targetRef=\"ED_2\"></sequenceFlow>\n    <sequenceFlow id=\"SF_41\" name=\"退回\" sourceRef=\"UT_20\" targetRef=\"UT_26\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${\'0\'==_submitInfo}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_27\" name=\"提交\" sourceRef=\"UT_26\" targetRef=\"UT_4\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${\'1\'==_submitInfo}]]></conditionExpression>\n    </sequenceFlow>\n    <sequenceFlow id=\"SF_44\" name=\"提交\" sourceRef=\"UT_42\" targetRef=\"ED_2\"></sequenceFlow>\n    <sequenceFlow id=\"SF_45\" name=\"退回\" sourceRef=\"UT_42\" targetRef=\"UT_26\">\n      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${\'0\'==_submitInfo}]]></conditionExpression>\n    </sequenceFlow>\n  </process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_WF_2017110519440001\">\n    <bpmndi:BPMNPlane bpmnElement=\"WF_2017110519440001\" id=\"BPMNPlane_WF_2017110519440001\">\n      <bpmndi:BPMNShape bpmnElement=\"ST_1\" id=\"BPMNShape_ST_1\">\n        <omgdc:Bounds height=\"5881.0\" width=\"12719.0\" x=\"128.0\" y=\"59.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"ED_2\" id=\"BPMNShape_ED_2\">\n        <omgdc:Bounds height=\"9247.0\" width=\"63110.0\" x=\"637.0\" y=\"93.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"UT_4\" id=\"BPMNShape_UT_4\">\n        <omgdc:Bounds height=\"5881.0\" width=\"20872.0\" x=\"210.0\" y=\"59.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"FK_6\" id=\"BPMNShape_FK_6\">\n        <omgdc:Bounds height=\"5881.0\" width=\"32519.0\" x=\"328.0\" y=\"59.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"UT_8\" id=\"BPMNShape_UT_8\">\n        <omgdc:Bounds height=\"2911.0\" width=\"40276.0\" x=\"406.0\" y=\"29.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"UT_14\" id=\"BPMNShape_UT_14\">\n        <omgdc:Bounds height=\"9247.0\" width=\"40177.0\" x=\"405.0\" y=\"93.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"UT_20\" id=\"BPMNShape_UT_20\">\n        <omgdc:Bounds height=\"2911.0\" width=\"56017.0\" x=\"565.0\" y=\"29.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"UT_26\" id=\"BPMNShape_UT_26\">\n        <omgdc:Bounds height=\"11821.0\" width=\"14932.0\" x=\"150.0\" y=\"119.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"UT_42\" id=\"BPMNShape_UT_42\">\n        <omgdc:Bounds height=\"9247.0\" width=\"50869.0\" x=\"513.0\" y=\"93.0\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_45\" id=\"BPMNEdge_SF_45\">\n        <omgdi:waypoint x=\"513.0\" y=\"136.58126721763074\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"200.0\" y=\"159.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_44\" id=\"BPMNEdge_SF_44\">\n        <omgdi:waypoint x=\"563.0\" y=\"133.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"637.5737520871754\" y=\"110.96684597424365\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_43\" id=\"BPMNEdge_SF_43\">\n        <omgdi:waypoint x=\"455.0\" y=\"133.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"513.0\" y=\"133.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_41\" id=\"BPMNEdge_SF_41\">\n        <omgdi:waypoint x=\"565.0\" y=\"79.84337349397583\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"384.44444444444446\" y=\"119.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_23\" id=\"BPMNEdge_SF_23\">\n        <omgdi:waypoint x=\"615.0\" y=\"69.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"641.371574929249\" y=\"96.83666242531844\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_40\" id=\"BPMNEdge_SF_40\">\n        <omgdi:waypoint x=\"406.0\" y=\"86.578125\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"313.77777777777777\" y=\"119.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_15\" id=\"BPMNEdge_SF_15\">\n        <omgdi:waypoint x=\"349.0\" y=\"80.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"405.0\" y=\"108.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_7\" id=\"BPMNEdge_SF_7\">\n        <omgdi:waypoint x=\"260.0\" y=\"99.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"349.0\" y=\"80.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_27\" id=\"BPMNEdge_SF_27\">\n        <omgdi:waypoint x=\"240.0\" y=\"119.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"210.0\" y=\"149.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_5\" id=\"BPMNEdge_SF_5\">\n        <omgdi:waypoint x=\"157.66886846726163\" y=\"77.13437360411574\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"210.0\" y=\"88.31623931623926\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_37\" id=\"BPMNEdge_SF_37\">\n        <omgdi:waypoint x=\"210.0\" y=\"149.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"240.0\" y=\"119.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_39\" id=\"BPMNEdge_SF_39\">\n        <omgdi:waypoint x=\"456.0\" y=\"69.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"565.0\" y=\"69.0\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"SF_9\" id=\"BPMNEdge_SF_9\">\n        <omgdi:waypoint x=\"349.0\" y=\"80.0\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"406.0\" y=\"74.14018691588808\"></omgdi:waypoint>\n      </bpmndi:BPMNEdge>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</definitions>',0),
('aca4589d0d7011e89b91a01d48d28732',1,'var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('aca4589f0d7011e89b91a01d48d28732',1,'hist.var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('aca4a6c30d7011e89b91a01d48d28732',1,'var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('aca4a6c50d7011e89b91a01d48d28732',1,'hist.var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('aca51bff0d7011e89b91a01d48d28732',1,'var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('aca51c010d7011e89b91a01d48d28732',1,'hist.var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('aca5913b0d7011e89b91a01d48d28732',1,'var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('aca5913d0d7011e89b91a01d48d28732',1,'hist.var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('aca5df690d7011e89b91a01d48d28732',1,'var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('aca5df6b0d7011e89b91a01d48d28732',1,'hist.var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0\0x',NULL),
('bcd3962fc21e11e7b894a01d48d28732',9,'source',NULL,'{\"modelId\":\"bcd1734ec21e11e7b894a01d48d28732\",\"bounds\":{\"lowerRight\":{\"x\":\"1485\",\"y\":\"250\"},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"WF_2017110519440001\",\"name\":\"流程案例\",\"documentation\":\"流程案例\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"http://www.flowable.org/processdef\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"width\":\"1485\",\"height\":\"250\"},\"childShapes\":[{\"resourceId\":\"ST_1\",\"properties\":{\"overrideid\":\"\",\"name\":\"开始\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formreference\":\"\",\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_5\"}],\"bounds\":{\"lowerRight\":{\"x\":12847,\"y\":5940},\"upperLeft\":{\"x\":128,\"y\":59}},\"dockers\":[]},{\"resourceId\":\"ED_2\",\"properties\":{\"overrideid\":\"ED_2\",\"name\":\"结束\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":63747,\"y\":9340},\"upperLeft\":{\"x\":637,\"y\":93}},\"dockers\":[]},{\"resourceId\":\"UT_4\",\"properties\":{\"overrideid\":\"UT_4\",\"name\":\"部门领导\",\"usertype\":\"1\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"部门领导\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"${UT_4_candidateUsersId}\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"shiro\\\",\\\"custSql\\\":\\\"SELECT \'SU_2016112417080001\' AS userid UNION SELECT \'SU_201411170001\' AS userid\\\"} \"}],\"assignment\":{\"candidateUsers\":[{\"value\":\"${UT_4_candidateUsersId}\"}]}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_7\"},{\"resourceId\":\"SF_37\"}],\"bounds\":{\"lowerRight\":{\"x\":21082,\"y\":5940},\"upperLeft\":{\"x\":210,\"y\":59}},\"dockers\":[]},{\"resourceId\":\"FK_6\",\"properties\":{\"overrideid\":\"FK_6\",\"name\":\"分支\",\"documentation\":\"\"},\"stencil\":{\"id\":\"ExclusiveGateway\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_9\"},{\"resourceId\":\"SF_15\"}],\"bounds\":{\"lowerRight\":{\"x\":32847,\"y\":5940},\"upperLeft\":{\"x\":328,\"y\":59}},\"dockers\":[]},{\"resourceId\":\"UT_8\",\"properties\":{\"overrideid\":\"UT_8\",\"name\":\"个人签收\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"1\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"个人签收\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_8_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"SU_201411170002\",\"name\":\"测试用户\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_8_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_39\"},{\"resourceId\":\"SF_40\"}],\"bounds\":{\"lowerRight\":{\"x\":40682,\"y\":2940},\"upperLeft\":{\"x\":406,\"y\":29}},\"dockers\":[]},{\"resourceId\":\"UT_14\",\"properties\":{\"overrideid\":\"UT_14\",\"name\":\"集体会签\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"UT_14_assigneeUsersId\",\"multiinstance_condition\":\"%24%7BnrOfCompletedInstances%2FnrOfInstances%3D%3D1%7D\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"UT_14_assigneeUserId\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"集体会签\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_14_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_14_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_43\"}],\"bounds\":{\"lowerRight\":{\"x\":40582,\"y\":9340},\"upperLeft\":{\"x\":405,\"y\":93}},\"dockers\":[]},{\"resourceId\":\"UT_20\",\"properties\":{\"overrideid\":\"UT_20\",\"name\":\"单人任务\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"Yes\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"单人任务\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_20_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"SR_201507050001\",\"name\":\"教师\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_20_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_23\"},{\"resourceId\":\"SF_41\"}],\"bounds\":{\"lowerRight\":{\"x\":56582,\"y\":2940},\"upperLeft\":{\"x\":565,\"y\":29}},\"dockers\":[]},{\"resourceId\":\"UT_26\",\"properties\":{\"overrideid\":\"UT_26\",\"name\":\"回退节点\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"回退节点\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_26_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \",\"name\":\"{\\\"sysid\\\":\\\"wf\\\",\\\"custSql\\\":\\\"SELECT \'${sessionScope.user.id}\' as userid\\\"} \"}],\"assignment\":{\"assignee\":\"${UT_26_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_27\"}],\"bounds\":{\"lowerRight\":{\"x\":15082,\"y\":11940},\"upperLeft\":{\"x\":150,\"y\":119}},\"dockers\":[]},{\"resourceId\":\"UT_42\",\"properties\":{\"overrideid\":\"UT_42\",\"name\":\"会议纪要\",\"usertype\":\"0\",\"usercheck\":\"1\",\"userselother\":\"0\",\"asynchronousdefinition\":\"false\",\"duedatedefinition\":\"\",\"exclusivedefinition\":\"Yes\",\"formkeydefinition\":\"%7B%22startcode%22%3A%22WF_01%22%2C%22endcode%22%3A%22WF_02%22%7D\",\"formproperties\":\"\",\"isforcompensation\":\"false\",\"looptype\":\"None\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_condition\":\"\",\"multiinstance_sequential\":\"No\",\"multiinstance_variable\":\"\",\"prioritydefinition\":\"\",\"tasklisteners\":\"\",\"documentation\":\"会议纪要\",\"usertaskassignment\":{\"totalCount\":9,\"items\":[{\"assignment_type\":\"assignee\",\"resourceassignmentexpr\":\"${UT_42_assigneeUserId}\",\"name\":\"\"},{\"assignment_type\":\"candidateUsers\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"candidateGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"singleUser\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"roleGroups\",\"resourceassignmentexpr\":\"1\",\"name\":\"超级管理员\"},{\"assignment_type\":\"postGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"deptPostGroups\",\"resourceassignmentexpr\":\"\",\"name\":\"\"},{\"assignment_type\":\"custSql\",\"resourceassignmentexpr\":\"\",\"name\":\"\"}],\"assignment\":{\"assignee\":\"${UT_42_assigneeUserId}\"}}},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"SF_44\"},{\"resourceId\":\"SF_45\"}],\"bounds\":{\"lowerRight\":{\"x\":51382,\"y\":9340},\"upperLeft\":{\"x\":513,\"y\":93}},\"dockers\":[]},{\"resourceId\":\"SF_5\",\"properties\":{\"name\":\"\",\"overrideid\":\"SF_5\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":170,\"y\":79},\"upperLeft\":{\"x\":125,\"y\":79}},\"dots\":[],\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_7\",\"properties\":{\"overrideid\":\"SF_7\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"0\",\"textX\":316,\"textY\":75},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"FK_6\"}],\"bounds\":{\"lowerRight\":{\"x\":287,\"y\":79},\"upperLeft\":{\"x\":241,\"y\":79}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":21,\"y\":21}],\"target\":{\"resourceId\":\"FK_6\"}},{\"resourceId\":\"SF_37\",\"properties\":{\"overrideid\":\"SF_37\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":430,\"textY\":32},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":48},\"upperLeft\":{\"x\":131,\"y\":-24}},\"dots\":[{\"x\":251,\"y\":48},{\"x\":191,\"y\":48}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_9\",\"properties\":{\"overrideid\":\"SF_9\",\"name\":\"单人签收\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120001%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":367,\"textY\":54},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_8\"}],\"bounds\":{\"lowerRight\":{\"x\":370,\"y\":49},\"upperLeft\":{\"x\":329,\"y\":19}},\"dots\":[{\"x\":391,\"y\":79},{\"x\":391,\"y\":49}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_8\"}},{\"resourceId\":\"SF_15\",\"properties\":{\"overrideid\":\"SF_15\",\"name\":\"集体会签\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7BteacherId%3D%3D%26quot%3BPT_201504120002%26quot%3B%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":366,\"textY\":114},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_14\"}],\"bounds\":{\"lowerRight\":{\"x\":370,\"y\":79},\"upperLeft\":{\"x\":330,\"y\":45}},\"dots\":[{\"x\":391,\"y\":79},{\"x\":391,\"y\":113}],\"dockers\":[{\"x\":21,\"y\":21},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_14\"}},{\"resourceId\":\"SF_39\",\"properties\":{\"overrideid\":\"SF_39\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\",\"textX\":523,\"textY\":45},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_20\"}],\"bounds\":{\"lowerRight\":{\"x\":483,\"y\":49},\"upperLeft\":{\"x\":396,\"y\":49}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_20\"}},{\"resourceId\":\"SF_40\",\"properties\":{\"overrideid\":\"SF_40\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":237,\"textY\":59},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":22},\"upperLeft\":{\"x\":-65,\"y\":-76}},\"dots\":[{\"x\":447,\"y\":22},{\"x\":191,\"y\":22}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_43\",\"properties\":{\"overrideid\":\"SF_43\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"0\",\"textX\":494,\"textY\":109},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_42\"}],\"bounds\":{\"lowerRight\":{\"x\":482,\"y\":113},\"upperLeft\":{\"x\":446,\"y\":113}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_42\"}},{\"resourceId\":\"SF_23\",\"properties\":{\"overrideid\":\"SF_23\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"None\",\"linetype\":\"1\",\"textX\":654,\"textY\":45},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":642,\"y\":49},\"upperLeft\":{\"x\":575,\"y\":-15}},\"dots\":[{\"x\":709,\"y\":49},{\"x\":709,\"y\":113}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_41\",\"properties\":{\"overrideid\":\"SF_41\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":544,\"textY\":27},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":13},\"upperLeft\":{\"x\":-224,\"y\":-94}},\"dots\":[{\"x\":606,\"y\":13},{\"x\":191,\"y\":13}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}},{\"resourceId\":\"SF_27\",\"properties\":{\"overrideid\":\"SF_27\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B1%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"2\",\"textX\":234,\"textY\":107},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_4\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":98},\"upperLeft\":{\"x\":131,\"y\":75}},\"dots\":[{\"x\":191,\"y\":111},{\"x\":251,\"y\":111}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_4\"}},{\"resourceId\":\"SF_44\",\"properties\":{\"overrideid\":\"SF_44\",\"name\":\"提交\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"defaultflow\":\"true\",\"conditionalflow\":\"\",\"linetype\":\"0\",\"textX\":610,\"textY\":109},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"ED_2\"}],\"bounds\":{\"lowerRight\":{\"x\":590,\"y\":113},\"upperLeft\":{\"x\":538,\"y\":113}},\"dots\":[],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"ED_2\"}},{\"resourceId\":\"SF_45\",\"properties\":{\"overrideid\":\"SF_45\",\"name\":\"退回\",\"documentation\":\"\",\"conditionsequenceflow\":\"%24%7B%26apos%3B0%26apos%3B%3D%3D_submitInfo%7D\",\"defaultflow\":\"None\",\"conditionalflow\":\"\",\"linetype\":\"5\",\"textX\":593,\"textY\":25},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"UT_26\"}],\"bounds\":{\"lowerRight\":{\"x\":191,\"y\":4},\"upperLeft\":{\"x\":-172,\"y\":-112}},\"dots\":[{\"x\":554,\"y\":4},{\"x\":191,\"y\":4}],\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"UT_26\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"./stencilsets/bpmn2.0/bpmn2.0.json\"},\"ssextensions\":[],\"maxCount\":45,\"initTime\":\"1520238853546\"}',NULL),
('c2a2df98c22211e7b894a01d48d28732',1,'var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('c2a2df9ac22211e7b894a01d48d28732',1,'hist.var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('c2a306aec22211e7b894a01d48d28732',1,'var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('c2a306b0c22211e7b894a01d48d28732',1,'hist.var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('c2a32dcac22211e7b894a01d48d28732',1,'var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('c2a354dcc22211e7b894a01d48d28732',1,'hist.var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('c2a37bf6c22211e7b894a01d48d28732',1,'var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('c2a37bf8c22211e7b894a01d48d28732',1,'hist.var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('c2a3ca24c22211e7b894a01d48d28732',1,'var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('c2a3ca26c22211e7b894a01d48d28732',1,'hist.var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('df060675c22211e7b894a01d48d28732',1,'var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('df060677c22211e7b894a01d48d28732',1,'hist.var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('df06067bc22211e7b894a01d48d28732',1,'var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('df06067dc22211e7b894a01d48d28732',1,'hist.var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('df062d97c22211e7b894a01d48d28732',1,'var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('df062d99c22211e7b894a01d48d28732',1,'hist.var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('df0654b3c22211e7b894a01d48d28732',1,'var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('df067bc5c22211e7b894a01d48d28732',1,'hist.var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('df06a2e1c22211e7b894a01d48d28732',1,'var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('df06a2e3c22211e7b894a01d48d28732',1,'hist.var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('faf89134c22211e7b894a01d48d28732',3,'hist.var-UT_26_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050004x',NULL),
('faf8b84ac22211e7b894a01d48d28732',1,'hist.var-UT_14_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL),
('faf8df66c22211e7b894a01d48d28732',1,'hist.var-UT_42_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170001x',NULL),
('faf90682c22211e7b894a01d48d28732',1,'hist.var-UT_8_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201411170002x',NULL),
('faf92da0c22211e7b894a01d48d28732',1,'hist.var-UT_20_assigneeUsersId',NULL,'��\0sr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0t\0SU_201507050003t\0SU_201507050004x',NULL);

/*Table structure for table `act_ge_property` */

DROP TABLE IF EXISTS `act_ge_property`;

CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ge_property` */

insert  into `act_ge_property`(`NAME_`,`VALUE_`,`REV_`) values 
('next.dbid','72501',30),
('schema.history','create(5.22.0.0)',1),
('schema.version','5.22.0.0',1);

/*Table structure for table `act_hi_actinst` */

DROP TABLE IF EXISTS `act_hi_actinst`;

CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_actinst` */

insert  into `act_hi_actinst`(`ID_`,`PROC_DEF_ID_`,`PROC_INST_ID_`,`EXECUTION_ID_`,`ACT_ID_`,`TASK_ID_`,`CALL_PROC_INST_ID_`,`ACT_NAME_`,`ACT_TYPE_`,`ASSIGNEE_`,`START_TIME_`,`END_TIME_`,`DURATION_`,`TENANT_ID_`) values 
('2329dfecc22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','FK_6',NULL,NULL,'分支','exclusiveGateway',NULL,'2017-11-05 20:16:22','2017-11-05 20:16:22',1,'SystemWeb'),
('232a06fdc22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','UT_8','232a06fec22311e7b894a01d48d28732',NULL,'个人签收','userTask','SU_2017121217530001','2017-11-05 20:16:22',NULL,NULL,'SystemWeb'),
('3859c391c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','FK_6',NULL,NULL,'分支','exclusiveGateway',NULL,'2017-11-05 20:16:58','2017-11-05 20:16:58',0,'SystemWeb'),
('3859c392c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','UT_8','3859c393c22311e7b894a01d48d28732',NULL,'个人签收','userTask','SU_2017121217530001','2017-11-05 20:16:58',NULL,NULL,'SystemWeb'),
('482a39d6c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','FK_6',NULL,NULL,'分支','exclusiveGateway',NULL,'2017-11-05 20:17:24','2017-11-05 20:17:24',0,'SystemWeb'),
('482aaf08c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482a39d7c22311e7b894a01d48d28732','UT_14','482b9870c22311e7b894a01d48d28732',NULL,'集体会签','userTask','SU_201507050003','2017-11-05 20:17:24','2017-11-05 20:20:17',173271,'SystemWeb'),
('482cd0f4c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482ad61dc22311e7b894a01d48d28732','UT_14','482cd0f5c22311e7b894a01d48d28732',NULL,'集体会签','userTask','SU_201507050004','2017-11-05 20:17:24','2017-11-05 20:21:36',252750,'SystemWeb'),
('8d2a96ec204911e8aef8485ab673973d','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','ST_1',NULL,NULL,'开始','startEvent',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10',87,'SystemWeb'),
('8d3804a2204911e8aef8485ab673973d','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','UT_4','8d3804a3204911e8aef8485ab673973d',NULL,'部门领导','userTask',NULL,'2018-03-05 15:48:10',NULL,NULL,'SystemWeb'),
('aca3954b0d7011e89b91a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ST_1',NULL,NULL,'开始','startEvent',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21',71,'SystemWeb'),
('acae92010d7011e89b91a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','UT_4','acae92020d7011e89b91a01d48d28732',NULL,'部门领导','userTask',NULL,'2018-02-09 16:10:21',NULL,NULL,'SystemWeb'),
('c2a26a66c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','ST_1',NULL,NULL,'开始','startEvent',NULL,'2017-11-05 20:13:40','2017-11-05 20:13:40',32,'SystemWeb'),
('c2a773acc22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','UT_4','c2a773adc22211e7b894a01d48d28732',NULL,'部门领导','userTask','SU_201411170001','2017-11-05 20:13:40','2017-11-05 20:16:22',162768,'SystemWeb'),
('de52c627c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','UT_42','de52c628c22311e7b894a01d48d28732',NULL,'会议纪要','userTask','SU_201411170001','2017-11-05 20:21:36','2017-11-05 20:22:24',48606,'SystemWeb'),
('df05df63c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','ST_1',NULL,NULL,'开始','startEvent',NULL,'2017-11-05 20:14:28','2017-11-05 20:14:28',8,'SystemWeb'),
('df071819c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','UT_4','df07181ac22211e7b894a01d48d28732',NULL,'部门领导','userTask','SU_201411170001','2017-11-05 20:14:28','2017-11-05 20:16:58',150314,'SystemWeb'),
('fad5f7e9c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','ED_2',NULL,NULL,'结束','endEvent',NULL,'2017-11-05 20:22:24','2017-11-05 20:22:24',0,'SystemWeb'),
('faf89130c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','ST_1',NULL,NULL,'开始','startEvent',NULL,'2017-11-05 20:15:15','2017-11-05 20:15:15',7,'SystemWeb'),
('faf9c9e6c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','UT_4','faf9c9e7c22211e7b894a01d48d28732',NULL,'部门领导','userTask','SU_201411170001','2017-11-05 20:15:15','2017-11-05 20:17:24',129845,'SystemWeb');

/*Table structure for table `act_hi_attachment` */

DROP TABLE IF EXISTS `act_hi_attachment`;

CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_attachment` */

/*Table structure for table `act_hi_comment` */

DROP TABLE IF EXISTS `act_hi_comment`;

CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_comment` */

/*Table structure for table `act_hi_detail` */

DROP TABLE IF EXISTS `act_hi_detail`;

CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_detail` */

/*Table structure for table `act_hi_identitylink` */

DROP TABLE IF EXISTS `act_hi_identitylink`;

CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_identitylink` */

insert  into `act_hi_identitylink`(`ID_`,`GROUP_ID_`,`TYPE_`,`USER_ID_`,`TASK_ID_`,`PROC_INST_ID_`) values 
('232a06ffc22311e7b894a01d48d28732',NULL,'participant','SU_201411170002',NULL,'c2a158f4c22211e7b894a01d48d28732'),
('3859eaa4c22311e7b894a01d48d28732',NULL,'participant','SU_201411170002',NULL,'df059141c22211e7b894a01d48d28732'),
('482be691c22311e7b894a01d48d28732',NULL,'participant','SU_201507050003',NULL,'faf81bfec22211e7b894a01d48d28732'),
('482d1f16c22311e7b894a01d48d28732',NULL,'participant','SU_201507050004',NULL,'faf81bfec22211e7b894a01d48d28732'),
('8d29faab204911e8aef8485ab673973d',NULL,'starter','SU_201411170001',NULL,'8d2343ea204911e8aef8485ab673973d'),
('8d3ebb64204911e8aef8485ab673973d',NULL,'candidate','SU_2016112417080001','8d3804a3204911e8aef8485ab673973d',NULL),
('8d3f0985204911e8aef8485ab673973d',NULL,'participant','SU_2016112417080001',NULL,'8d2343ea204911e8aef8485ab673973d'),
('8d3f0986204911e8aef8485ab673973d',NULL,'candidate','SU_201411170001','8d3804a3204911e8aef8485ab673973d',NULL),
('99c346b8f50611e7bba6a01d48d28732',NULL,'participant','SU_2017121217530001',NULL,'df059141c22211e7b894a01d48d28732'),
('9a22a4c9f50611e7bba6a01d48d28732',NULL,'participant','SU_2017121217530001',NULL,'c2a158f4c22211e7b894a01d48d28732'),
('aca283da0d7011e89b91a01d48d28732',NULL,'starter','SU_201411170001',NULL,'ac99d1490d7011e89b91a01d48d28732'),
('acb4d3930d7011e89b91a01d48d28732',NULL,'candidate','SU_2016112417080001','acae92020d7011e89b91a01d48d28732',NULL),
('acb4faa40d7011e89b91a01d48d28732',NULL,'participant','SU_2016112417080001',NULL,'ac99d1490d7011e89b91a01d48d28732'),
('acb4faa50d7011e89b91a01d48d28732',NULL,'candidate','SU_201411170001','acae92020d7011e89b91a01d48d28732',NULL),
('c2a1ce25c22211e7b894a01d48d28732',NULL,'starter','SU_201411170001',NULL,'c2a158f4c22211e7b894a01d48d28732'),
('c2a8d33ec22211e7b894a01d48d28732',NULL,'candidate','SU_2016112417080001','c2a773adc22211e7b894a01d48d28732',NULL),
('c2a8fa4fc22211e7b894a01d48d28732',NULL,'participant','SU_2016112417080001',NULL,'c2a158f4c22211e7b894a01d48d28732'),
('c2a8fa50c22211e7b894a01d48d28732',NULL,'candidate','SU_201411170001','c2a773adc22211e7b894a01d48d28732',NULL),
('df05df62c22211e7b894a01d48d28732',NULL,'starter','SU_201411170001',NULL,'df059141c22211e7b894a01d48d28732'),
('df07663bc22211e7b894a01d48d28732',NULL,'candidate','SU_2016112417080001','df07181ac22211e7b894a01d48d28732',NULL),
('df078d4cc22211e7b894a01d48d28732',NULL,'participant','SU_2016112417080001',NULL,'df059141c22211e7b894a01d48d28732'),
('df078d4dc22211e7b894a01d48d28732',NULL,'candidate','SU_201411170001','df07181ac22211e7b894a01d48d28732',NULL),
('faf86a1fc22211e7b894a01d48d28732',NULL,'starter','SU_201411170001',NULL,'faf81bfec22211e7b894a01d48d28732'),
('fafa1808c22211e7b894a01d48d28732',NULL,'candidate','SU_2016112417080001','faf9c9e7c22211e7b894a01d48d28732',NULL),
('fafa1809c22211e7b894a01d48d28732',NULL,'participant','SU_2016112417080001',NULL,'faf81bfec22211e7b894a01d48d28732'),
('fafa180ac22211e7b894a01d48d28732',NULL,'candidate','SU_201411170001','faf9c9e7c22211e7b894a01d48d28732',NULL);

/*Table structure for table `act_hi_procinst` */

DROP TABLE IF EXISTS `act_hi_procinst`;

CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_procinst` */

insert  into `act_hi_procinst`(`ID_`,`PROC_INST_ID_`,`BUSINESS_KEY_`,`PROC_DEF_ID_`,`START_TIME_`,`END_TIME_`,`DURATION_`,`START_USER_ID_`,`START_ACT_ID_`,`END_ACT_ID_`,`SUPER_PROCESS_INSTANCE_ID_`,`DELETE_REASON_`,`TENANT_ID_`,`NAME_`) values 
('8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','BK_2018030515480002','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','2018-03-05 15:48:10',NULL,NULL,'SU_201411170001','ST_1',NULL,NULL,NULL,'SystemWeb',NULL),
('ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','BK_2018020916100002','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','2018-02-09 16:10:21',NULL,NULL,'SU_201411170001','ST_1',NULL,NULL,NULL,'SystemWeb',NULL),
('c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','BK_2017110520130002','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','2017-11-05 20:13:40',NULL,NULL,'SU_201411170001','ST_1',NULL,NULL,NULL,'SystemWeb',NULL),
('df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','BK_2017110520140002','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','2017-11-05 20:14:28',NULL,NULL,'SU_201411170001','ST_1',NULL,NULL,NULL,'SystemWeb',NULL),
('faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','BK_2017110520150002','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','2017-11-05 20:15:15','2017-11-05 20:22:24',429608,'SU_201411170001','ST_1','ED_2',NULL,NULL,'SystemWeb',NULL);

/*Table structure for table `act_hi_suggestion` */

DROP TABLE IF EXISTS `act_hi_suggestion`;

CREATE TABLE `act_hi_suggestion` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `inid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `processkey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `businkey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `task_ID` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `suggest` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `userId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `userName` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `act_hi_suggestion` */

insert  into `act_hi_suggestion`(`id`,`inid`,`processkey`,`businkey`,`task_ID`,`suggest`,`userId`,`userName`,`createDate`) values 
('SG_2017110520130001','c2a158f4c22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520130002',NULL,'test_001','SU_201411170001','管理员','2017-11-05 20:13:56'),
('SG_2017110520140001','df059141c22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520140002',NULL,'test_001','SU_201411170001','管理员','2017-11-05 20:14:40'),
('SG_2017110520150001','faf81bfec22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520150002',NULL,'test_001','SU_201411170001','管理员','2017-11-05 20:15:36'),
('SG_2017110520160001','c2a158f4c22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520130002','c2a773adc22211e7b894a01d48d28732','test_002','SU_201411170001','管理员','2017-11-05 20:16:22'),
('SG_2017110520160002','df059141c22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520140002','df07181ac22211e7b894a01d48d28732','test_002','SU_201411170001','管理员','2017-11-05 20:16:58'),
('SG_2017110520170001','faf81bfec22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520150002','faf9c9e7c22211e7b894a01d48d28732','test_002','SU_201411170001','管理员','2017-11-05 20:17:25'),
('SG_2017110520200001','faf81bfec22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520150002','482b9870c22311e7b894a01d48d28732','test_003','SU_201507050003','T_001','2017-11-05 20:20:17'),
('SG_2017110520210001','faf81bfec22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520150002','482cd0f5c22311e7b894a01d48d28732','test_004','SU_201507050004','T_002','2017-11-05 20:21:36'),
('SG_2017110520220001','faf81bfec22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520150002','de52c628c22311e7b894a01d48d28732','test_005','SU_201411170001','管理员','2017-11-05 20:22:24'),
('SG_2018030516040001','8d2343ea204911e8aef8485ab673973d','WF_2017110519440001','BK_2018030515480002',NULL,'','SU_201411170001','管理员','2018-03-05 16:04:52');

/*Table structure for table `act_hi_taskinst` */

DROP TABLE IF EXISTS `act_hi_taskinst`;

CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `CLAIM_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_taskinst` */

insert  into `act_hi_taskinst`(`ID_`,`PROC_DEF_ID_`,`TASK_DEF_KEY_`,`PROC_INST_ID_`,`EXECUTION_ID_`,`NAME_`,`PARENT_TASK_ID_`,`DESCRIPTION_`,`OWNER_`,`ASSIGNEE_`,`START_TIME_`,`CLAIM_TIME_`,`END_TIME_`,`DURATION_`,`DELETE_REASON_`,`PRIORITY_`,`DUE_DATE_`,`FORM_KEY_`,`CATEGORY_`,`TENANT_ID_`) values 
('232a06fec22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_8','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','个人签收',NULL,'个人签收','SU_201411170002','SU_2017121217530001','2017-11-05 20:16:22',NULL,NULL,NULL,NULL,50,NULL,NULL,NULL,'SystemWeb'),
('3859c393c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_8','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','个人签收',NULL,'个人签收','SU_201411170002','SU_2017121217530001','2017-11-05 20:16:58',NULL,NULL,NULL,NULL,50,NULL,NULL,NULL,'SystemWeb'),
('482b9870c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_14','faf81bfec22211e7b894a01d48d28732','482aaf0cc22311e7b894a01d48d28732','集体会签',NULL,'集体会签',NULL,'SU_201507050003','2017-11-05 20:17:24',NULL,'2017-11-05 20:20:17',173264,'SC_2017101910220001',50,NULL,NULL,NULL,'SystemWeb'),
('482cd0f5c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_14','faf81bfec22211e7b894a01d48d28732','482ad61dc22311e7b894a01d48d28732','集体会签',NULL,'集体会签',NULL,'SU_201507050004','2017-11-05 20:17:24',NULL,'2017-11-05 20:21:36',252718,'SC_2017101910220001',50,NULL,NULL,NULL,'SystemWeb'),
('8d3804a3204911e8aef8485ab673973d','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_4','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','部门领导',NULL,'部门领导',NULL,NULL,'2018-03-05 15:48:10',NULL,NULL,NULL,NULL,50,NULL,NULL,NULL,'SystemWeb'),
('acae92020d7011e89b91a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_4','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','部门领导',NULL,'部门领导',NULL,NULL,'2018-02-09 16:10:21',NULL,NULL,NULL,NULL,50,NULL,NULL,NULL,'SystemWeb'),
('c2a773adc22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_4','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','部门领导',NULL,'部门领导',NULL,'SU_201411170001','2017-11-05 20:13:40','2017-11-05 20:15:53','2017-11-05 20:16:22',162760,'SC_2017101910220001',50,NULL,NULL,NULL,'SystemWeb'),
('de52c628c22311e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_42','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','会议纪要',NULL,'会议纪要',NULL,'SU_201411170001','2017-11-05 20:21:36',NULL,'2017-11-05 20:22:24',48602,'SC_2017101910220001',50,NULL,NULL,NULL,'SystemWeb'),
('df07181ac22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_4','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','部门领导',NULL,'部门领导',NULL,'SU_201411170001','2017-11-05 20:14:28','2017-11-05 20:15:55','2017-11-05 20:16:58',150308,'SC_2017101910220001',50,NULL,NULL,NULL,'SystemWeb'),
('faf9c9e7c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','UT_4','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','部门领导',NULL,'部门领导',NULL,'SU_201411170001','2017-11-05 20:15:15','2017-11-05 20:15:56','2017-11-05 20:17:24',129839,'SC_2017101910220001',50,NULL,NULL,NULL,'SystemWeb');

/*Table structure for table `act_hi_varinst` */

DROP TABLE IF EXISTS `act_hi_varinst`;

CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_hi_varinst` */

insert  into `act_hi_varinst`(`ID_`,`PROC_INST_ID_`,`EXECUTION_ID_`,`TASK_ID_`,`NAME_`,`VAR_TYPE_`,`REV_`,`BYTEARRAY_ID_`,`DOUBLE_`,`LONG_`,`TEXT_`,`TEXT2_`,`CREATE_TIME_`,`LAST_UPDATED_TIME_`) values 
('2322db0bc22311e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'wfSuggestion','string',0,NULL,NULL,NULL,'test_002',NULL,'2017-11-05 20:16:22','2017-11-05 20:16:22'),
('38530cd0c22311e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'wfSuggestion','string',0,NULL,NULL,NULL,'test_002',NULL,'2017-11-05 20:16:58','2017-11-05 20:16:58'),
('482271a5c22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'wfSuggestion','string',3,NULL,NULL,NULL,'test_005',NULL,'2017-11-05 20:17:24','2017-11-05 20:22:24'),
('482aaf09c22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482a39d7c22311e7b894a01d48d28732',NULL,'nrOfInstances','integer',1,NULL,NULL,2,'2',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('482aaf0ac22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482a39d7c22311e7b894a01d48d28732',NULL,'nrOfCompletedInstances','integer',2,NULL,NULL,2,'2',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('482aaf0bc22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482a39d7c22311e7b894a01d48d28732',NULL,'nrOfActiveInstances','integer',2,NULL,NULL,0,'0',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('482b243ec22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482aaf0cc22311e7b894a01d48d28732',NULL,'loopCounter','integer',1,NULL,NULL,0,'0',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('482b4b4fc22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482aaf0cc22311e7b894a01d48d28732',NULL,'UT_14_assigneeUserId','string',2,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('482ca9e2c22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482ad61dc22311e7b894a01d48d28732',NULL,'loopCounter','integer',1,NULL,NULL,1,'1',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('482cd0f3c22311e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','482ad61dc22311e7b894a01d48d28732',NULL,'UT_14_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:17:24','2017-11-05 20:21:36'),
('8d2a96ed204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnclose','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b332f204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_26_assigneeUsersId','serializable',0,'8d2b5a40204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b5a41204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'candidateGroupsId','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b5a42204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'formdata','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b5a43204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnBack','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b8155204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_14_assigneeUsersId','serializable',0,'8d2b8156204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b8157204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_42_assigneeUserId','string',0,NULL,NULL,NULL,'SU_201411170001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2b8158204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'urldata','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ba869204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'processkey','string',0,NULL,NULL,NULL,'WF_2017110519440001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ba86a204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'defkey','string',0,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ba86b204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnsubmit','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ba86c204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnimage','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ba86d204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'FI_btnKey','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bcf7e204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'id','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bcf7f204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'_idvalue','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bcf81204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_42_assigneeUsersId','serializable',0,'8d2bf692204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bf693204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'name','string',0,NULL,NULL,NULL,'test',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bf694204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'moneyInfo','string',0,NULL,NULL,NULL,'1',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bf695204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_4_candidateUsersId','string',0,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2bf696204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_26_assigneeUserId','string',0,NULL,NULL,NULL,'SU_201411170001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c1da7204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'candidateUsersId','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c1da8204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'querystring','string',0,NULL,NULL,NULL,'?fiid=${param.businesskey}',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c1da9204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_20_assigneeUserId','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c1daa204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'assigneeUserId','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c1dab204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnBackWF','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c44bd204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_8_assigneeUsersId','serializable',0,'8d2c44be204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c44bf204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_14_assigneeUserId','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c44c0204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'FI_btnId','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c6bd1204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnsubInitWF','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c6bd2204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'tkid','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c6bd3204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'code','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c6bd4204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'multiFile','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c92e5204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'businesskey','string',0,NULL,NULL,NULL,'BK_2018030515480001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c92e6204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'fileId','string',0,NULL,NULL,NULL,'FL_2018030515470001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c92e7204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'stufiledId','string',0,NULL,NULL,NULL,'FL_2018030515470002',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c92e8204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'_submitInfo','string',0,NULL,NULL,NULL,'1',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2c92e9204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_8_assigneeUserId','string',0,NULL,NULL,NULL,'SU_201411170002',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2cb9fb204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'UT_20_assigneeUsersId','serializable',0,'8d2cb9fc204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2cb9fd204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnsave','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2cb9fe204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'inid','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ce10f204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'FIID','string',0,NULL,NULL,NULL,'BK_2018030515480002',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ce110204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'teacherId','string',0,NULL,NULL,NULL,'PT_201504120001',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('8d2ce111204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'btnnext','string',0,NULL,NULL,NULL,'',NULL,'2018-03-05 15:48:10','2018-03-05 15:48:10'),
('aca3bc5c0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnclose','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4589e0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_26_assigneeUsersId','serializable',0,'aca4589f0d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca47fb00d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'candidateGroupsId','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca47fb10d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'formdata','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca47fb20d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnBack','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4a6c40d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_14_assigneeUsersId','serializable',0,'aca4a6c50d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4a6c60d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_42_assigneeUserId','string',0,NULL,NULL,NULL,'SU_201411170001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4a6c70d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'urldata','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4cdd80d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'processkey','string',0,NULL,NULL,NULL,'WF_2017110519440001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4cdd90d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'defkey','string',0,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4cdda0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnsubmit','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4cddb0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnimage','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4f4ec0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'FI_btnKey','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4f4ed0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'id','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca4f4ee0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'_idvalue','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca51c000d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_42_assigneeUsersId','serializable',0,'aca51c010d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca51c020d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'name','string',0,NULL,NULL,NULL,'test',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca51c030d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'moneyInfo','string',0,NULL,NULL,NULL,'1',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca543140d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_4_candidateUsersId','string',0,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca543150d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_26_assigneeUserId','string',0,NULL,NULL,NULL,'SU_201411170001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca543160d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'candidateUsersId','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca543170d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'querystring','string',0,NULL,NULL,NULL,'?fiid=${param.businesskey}',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca56a280d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_20_assigneeUserId','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca56a290d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'assigneeUserId','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca56a2a0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnBackWF','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5913c0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_8_assigneeUsersId','serializable',0,'aca5913d0d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5913e0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_14_assigneeUserId','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5913f0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'FI_btnId','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5b8500d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnsubInitWF','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5b8510d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'tkid','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5b8520d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'code','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5b8530d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'multiFile','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5b8540d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'businesskey','string',0,NULL,NULL,NULL,'BK_2018020916100001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5b8550d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'fileId','string',0,NULL,NULL,NULL,'FL_2018020916090001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5df660d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'stufiledId','string',0,NULL,NULL,NULL,'FL_2018020916090002',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5df670d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'_submitInfo','string',0,NULL,NULL,NULL,'1',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5df680d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_8_assigneeUserId','string',0,NULL,NULL,NULL,'SU_201411170002',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5df6a0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'UT_20_assigneeUsersId','serializable',0,'aca5df6b0d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5df6c0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnsave','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca5df6d0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'inid','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca6067e0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'FIID','string',0,NULL,NULL,NULL,'BK_2018020916100002',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca6067f0d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'teacherId','string',0,NULL,NULL,NULL,'PT_201504120001',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('aca606800d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'btnnext','string',0,NULL,NULL,NULL,'',NULL,'2018-02-09 16:10:21','2018-02-09 16:10:21'),
('c2a26a67c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnclose','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a2df99c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_26_assigneeUsersId','serializable',1,'c2a2df9ac22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a2df9bc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'candidateGroupsId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a2df9cc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'formdata','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a306adc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnBack','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a306afc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_14_assigneeUsersId','serializable',1,'c2a306b0c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a306b1c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_42_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201411170001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a306b2c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'urldata','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a306b3c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'processkey','string',1,NULL,NULL,NULL,'WF_2017110519440001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a32dc4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'defkey','string',1,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a32dc5c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnsubmit','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a32dc6c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnimage','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a32dc7c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'FI_btnKey','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a32dc8c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'id','string',1,NULL,NULL,NULL,'LC_2017110520130001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a32dc9c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'_idvalue','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354dbc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_42_assigneeUsersId','serializable',1,'c2a354dcc22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354ddc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'name','string',1,NULL,NULL,NULL,'20171105_001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354dec22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'moneyInfo','string',1,NULL,NULL,NULL,'1',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354dfc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_4_candidateUsersId','string',1,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354e0c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_26_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201411170001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354e1c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'candidateUsersId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a354e2c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'querystring','string',1,NULL,NULL,NULL,'?fiid=BK_2017110520130002&inid=c2a158f4c22211e7b894a01d48d28732',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a37bf3c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_20_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a37bf4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'assigneeUserId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a37bf5c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnBackWF','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a37bf7c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_8_assigneeUsersId','serializable',1,'c2a37bf8c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a37bf9c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_14_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a37bfac22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'FI_btnId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a30bc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnsubInitWF','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a30cc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'tkid','string',1,NULL,NULL,NULL,'c2a773adc22211e7b894a01d48d28732',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a30dc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'code','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a30ec22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'multiFile','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a30fc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'businesskey','string',1,NULL,NULL,NULL,'BK_2017110520130002',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a310c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'fileId','string',1,NULL,NULL,NULL,'FL_2017110520130001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3a311c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'stufiledId','string',1,NULL,NULL,NULL,'FL_2017110520130002',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3ca22c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'_submitInfo','string',1,NULL,NULL,NULL,'1',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3ca23c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_8_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201411170002',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3ca25c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'UT_20_assigneeUsersId','serializable',1,'c2a3ca26c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3f137c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnsave','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3f138c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'inid','string',1,NULL,NULL,NULL,'c2a158f4c22211e7b894a01d48d28732',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3f139c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'FIID','string',1,NULL,NULL,NULL,'BK_2017110520130002',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3f13ac22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'teacherId','string',1,NULL,NULL,NULL,'PT_201504120001',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('c2a3f13bc22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'btnnext','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:13:40','2017-11-05 20:16:22'),
('df05df64c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnclose','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df060676c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_26_assigneeUsersId','serializable',1,'df060677c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df060678c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'candidateGroupsId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df060679c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'formdata','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06067ac22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnBack','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06067cc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_14_assigneeUsersId','serializable',1,'df06067dc22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06067ec22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_42_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201411170001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06067fc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'urldata','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d90c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'processkey','string',1,NULL,NULL,NULL,'WF_2017110519440001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d91c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'defkey','string',1,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d92c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnsubmit','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d93c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnimage','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d94c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'FI_btnKey','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d95c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'id','string',1,NULL,NULL,NULL,'LC_2017110520140001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d96c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'_idvalue','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df062d98c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_42_assigneeUsersId','serializable',1,'df062d99c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654aac22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'name','string',1,NULL,NULL,NULL,'test20171105_002',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654abc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'moneyInfo','string',1,NULL,NULL,NULL,'1',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654acc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_4_candidateUsersId','string',1,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654adc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_26_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201411170001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654aec22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'candidateUsersId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654afc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'querystring','string',1,NULL,NULL,NULL,'?fiid=BK_2017110520140002&inid=df059141c22211e7b894a01d48d28732',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654b0c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_20_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654b1c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'assigneeUserId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df0654b2c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnBackWF','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bc4c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_8_assigneeUsersId','serializable',1,'df067bc5c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bc6c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_14_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bc7c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'FI_btnId','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bc8c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnsubInitWF','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bc9c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'tkid','string',1,NULL,NULL,NULL,'df07181ac22211e7b894a01d48d28732',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bcac22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'code','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bcbc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'multiFile','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bccc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'businesskey','string',1,NULL,NULL,NULL,'BK_2017110520140002',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bcdc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'fileId','string',1,NULL,NULL,NULL,'FL_2017110520140001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df067bcec22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'stufiledId','string',1,NULL,NULL,NULL,'FL_2017110520140002',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2dfc22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'_submitInfo','string',1,NULL,NULL,NULL,'1',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e0c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_8_assigneeUserId','string',1,NULL,NULL,NULL,'SU_201411170002',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e2c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'UT_20_assigneeUsersId','serializable',1,'df06a2e3c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e4c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnsave','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e5c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'inid','string',1,NULL,NULL,NULL,'df059141c22211e7b894a01d48d28732',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e6c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'FIID','string',1,NULL,NULL,NULL,'BK_2017110520140002',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e7c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'teacherId','string',1,NULL,NULL,NULL,'PT_201504120001',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('df06a2e8c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'btnnext','string',1,NULL,NULL,NULL,'',NULL,'2017-11-05 20:14:28','2017-11-05 20:16:58'),
('faf89131c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnclose','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf89133c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_26_assigneeUsersId','serializable',4,'faf89134c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf89135c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'candidateGroupsId','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf89136c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'formdata','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf89137c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnBack','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b849c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_14_assigneeUsersId','serializable',4,'faf8b84ac22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b84bc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_42_assigneeUserId','string',4,NULL,NULL,NULL,'SU_201411170001',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b84cc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'urldata','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b84dc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'processkey','string',4,NULL,NULL,NULL,'WF_2017110519440001',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b84ec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'defkey','string',4,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b84fc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnsubmit','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b850c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnimage','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8b851c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'FI_btnKey','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df62c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'id','string',4,NULL,NULL,NULL,'LC_2017110520150001',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df63c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'_idvalue','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df65c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_42_assigneeUsersId','serializable',4,'faf8df66c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df67c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'name','string',4,NULL,NULL,NULL,'test_20171105_003',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df68c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'moneyInfo','string',4,NULL,NULL,NULL,'1',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df69c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_4_candidateUsersId','string',4,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df6ac22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_26_assigneeUserId','string',4,NULL,NULL,NULL,'SU_201507050004',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df6bc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'candidateUsersId','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf8df6cc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'querystring','string',4,NULL,NULL,NULL,'?fiid=BK_2017110520150002&inid=faf81bfec22211e7b894a01d48d28732',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf9067dc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_20_assigneeUserId','string',4,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf9067ec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'assigneeUserId','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf9067fc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnBackWF','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf90681c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_8_assigneeUsersId','serializable',4,'faf90682c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf90683c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_14_assigneeUserId','string',2,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf90684c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'FI_btnId','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf90685c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnsubInitWF','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d96c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'tkid','string',4,NULL,NULL,NULL,'de52c628c22311e7b894a01d48d28732',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d97c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'code','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d98c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'multiFile','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d99c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'businesskey','string',4,NULL,NULL,NULL,'BK_2017110520150002',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d9ac22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'fileId','string',4,NULL,NULL,NULL,'FL_2017110520140005',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d9bc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'stufiledId','string',4,NULL,NULL,NULL,'FL_2017110520140006',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d9cc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'_submitInfo','string',4,NULL,NULL,NULL,'1',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d9dc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_8_assigneeUserId','string',4,NULL,NULL,NULL,'SU_201411170002',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf92d9fc22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'UT_20_assigneeUsersId','serializable',4,'faf92da0c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf954b1c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnsave','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf954b2c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'inid','string',4,NULL,NULL,NULL,'faf81bfec22211e7b894a01d48d28732',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf954b3c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'FIID','string',4,NULL,NULL,NULL,'BK_2017110520150002',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf954b4c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'teacherId','string',4,NULL,NULL,NULL,'PT_201504120002',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24'),
('faf954b5c22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732',NULL,'btnnext','string',4,NULL,NULL,NULL,'',NULL,'2017-11-05 20:15:15','2017-11-05 20:22:24');

/*Table structure for table `act_id_group` */

DROP TABLE IF EXISTS `act_id_group`;

CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_id_group` */

/*Table structure for table `act_id_info` */

DROP TABLE IF EXISTS `act_id_info`;

CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_id_info` */

/*Table structure for table `act_id_membership` */

DROP TABLE IF EXISTS `act_id_membership`;

CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_id_membership` */

/*Table structure for table `act_id_user` */

DROP TABLE IF EXISTS `act_id_user`;

CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_id_user` */

/*Table structure for table `act_procdef_info` */

DROP TABLE IF EXISTS `act_procdef_info`;

CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_procdef_info` */

/*Table structure for table `act_re_deployment` */

DROP TABLE IF EXISTS `act_re_deployment`;

CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_re_deployment` */

insert  into `act_re_deployment`(`ID_`,`NAME_`,`CATEGORY_`,`TENANT_ID_`,`DEPLOY_TIME_`) values 
('a99a2e90c22211e7b894a01d48d28732','流程案例','CC_035','SystemWeb','2017-11-05 20:12:58');

/*Table structure for table `act_re_model` */

DROP TABLE IF EXISTS `act_re_model`;

CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_re_model` */

insert  into `act_re_model`(`ID_`,`REV_`,`NAME_`,`KEY_`,`CATEGORY_`,`CREATE_TIME_`,`LAST_UPDATE_TIME_`,`VERSION_`,`META_INFO_`,`DEPLOYMENT_ID_`,`EDITOR_SOURCE_VALUE_ID_`,`EDITOR_SOURCE_EXTRA_VALUE_ID_`,`TENANT_ID_`) values 
('328c05dad71d11e795b0a01d48d28732',2,'流程案例','WF_2017120212560001','CC_035','2017-12-02 12:56:46','2017-12-02 12:56:46',1,'流程案例',NULL,'32952d9bd71d11e795b0a01d48d28732',NULL,'SystemWeb'),
('595c49dcc21d11e7b894a01d48d28732',3,'流程案例','WF_2017110519340001','CC_035','2017-11-05 19:34:56','2017-12-02 01:14:00',1,'流程案例',NULL,'595ff35dc21d11e7b894a01d48d28732',NULL,'SystemWeb'),
('WM_2017103009590001',1,'流程模板',NULL,NULL,'2017-10-16 18:34:33',NULL,1,NULL,NULL,'WM_2017103009590001',NULL,'SystemWeb'),
('bcd1734ec21e11e7b894a01d48d28732',10,'流程案例','WF_2017110519440001','CC_035','2017-11-05 19:44:53','2018-03-05 16:43:15',1,'流程案例','a99a2e90c22211e7b894a01d48d28732','bcd3962fc21e11e7b894a01d48d28732',NULL,'SystemWeb');

/*Table structure for table `act_re_procdef` */

DROP TABLE IF EXISTS `act_re_procdef`;

CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_re_procdef` */

insert  into `act_re_procdef`(`ID_`,`REV_`,`CATEGORY_`,`NAME_`,`KEY_`,`VERSION_`,`DEPLOYMENT_ID_`,`RESOURCE_NAME_`,`DGRM_RESOURCE_NAME_`,`DESCRIPTION_`,`HAS_START_FORM_KEY_`,`HAS_GRAPHICAL_NOTATION_`,`SUSPENSION_STATE_`,`TENANT_ID_`) values 
('WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',1,'CC_035','流程案例','WF_2017110519440001',1,'a99a2e90c22211e7b894a01d48d28732','bcd1734ec21e11e7b894a01d48d28732.bpmn20.xml',NULL,'流程案例',1,1,1,'SystemWeb');

/*Table structure for table `act_re_url` */

DROP TABLE IF EXISTS `act_re_url`;

CREATE TABLE `act_re_url` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `category` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wfname` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(2048) COLLATE utf8_unicode_ci DEFAULT NULL,
  `inid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `processkey` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `businkey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deploymentId` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `issubmit` int(11) DEFAULT NULL,
  `istempsubmit` int(11) DEFAULT NULL,
  `delReason` varchar(2048) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `act_re_url` */

insert  into `act_re_url`(`id`,`category`,`code`,`wfname`,`name`,`url`,`inid`,`processkey`,`businkey`,`deploymentId`,`issubmit`,`istempsubmit`,`delReason`,`user_id`,`user_time`) values 
('AR_2017110520130001','CC_035','WFC_2017110520130001','流程案例','[流程案例]20171105_001','/platform/workflow/WF_2017110519440001.wf?fiid=BK_2017110520130002&inid=c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520130002','a99a2e90c22211e7b894a01d48d28732',1,0,NULL,'SU_201411170001','2017-11-05 20:13:40'),
('AR_2017110520140001','CC_035','WFC_2017110520140001','流程案例','[流程案例]test20171105_002','/platform/workflow/WF_2017110519440001.wf?fiid=BK_2017110520140002&inid=df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520140002','a99a2e90c22211e7b894a01d48d28732',1,0,NULL,'SU_201411170001','2017-11-05 20:14:28'),
('AR_2017110520150001','CC_035','WFC_2017110520150001','流程案例','[流程案例]test_20171105_003','/platform/workflow/WF_2017110519440001.wf?fiid=BK_2017110520150002&inid=faf81bfec22211e7b894a01d48d28732','faf81bfec22211e7b894a01d48d28732','WF_2017110519440001','BK_2017110520150002','a99a2e90c22211e7b894a01d48d28732',1,0,NULL,'SU_201411170001','2017-11-05 20:15:15'),
('AR_2018020916100001','CC_035','WFC_2018020916100001','流程案例','[流程案例]test','/platform/workflow/WF_2017110519440001.wf?fiid=BK_2018020916100002&inid=ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','WF_2017110519440001','BK_2018020916100002','a99a2e90c22211e7b894a01d48d28732',0,0,NULL,'SU_201411170001','2018-02-09 16:10:21'),
('AR_2018030515480001','CC_035','WFC_2018030515480001','流程案例','[流程案例]test','/platform/workflow/WF_2017110519440001.wf?fiid=BK_2018030515480002&inid=8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','WF_2017110519440001','BK_2018030515480002','a99a2e90c22211e7b894a01d48d28732',1,0,NULL,'SU_201411170001','2018-03-05 15:48:09');

/*Table structure for table `act_ru_event_subscr` */

DROP TABLE IF EXISTS `act_ru_event_subscr`;

CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ru_event_subscr` */

/*Table structure for table `act_ru_execution` */

DROP TABLE IF EXISTS `act_ru_execution`;

CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ru_execution` */

insert  into `act_ru_execution`(`ID_`,`REV_`,`PROC_INST_ID_`,`BUSINESS_KEY_`,`PARENT_ID_`,`PROC_DEF_ID_`,`SUPER_EXEC_`,`ACT_ID_`,`IS_ACTIVE_`,`IS_CONCURRENT_`,`IS_SCOPE_`,`IS_EVENT_SCOPE_`,`SUSPENSION_STATE_`,`CACHED_ENT_STATE_`,`TENANT_ID_`,`NAME_`,`LOCK_TIME_`) values 
('8d2343ea204911e8aef8485ab673973d',1,'8d2343ea204911e8aef8485ab673973d','BK_2018030515480002',NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'UT_4',1,0,1,0,1,2,'SystemWeb',NULL,NULL),
('ac99d1490d7011e89b91a01d48d28732',1,'ac99d1490d7011e89b91a01d48d28732','BK_2018020916100002',NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'UT_4',1,0,1,0,1,2,'SystemWeb',NULL,NULL),
('c2a158f4c22211e7b894a01d48d28732',2,'c2a158f4c22211e7b894a01d48d28732','BK_2017110520130002',NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'UT_8',1,0,1,0,1,2,'SystemWeb',NULL,NULL),
('df059141c22211e7b894a01d48d28732',2,'df059141c22211e7b894a01d48d28732','BK_2017110520140002',NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL,'UT_8',1,0,1,0,1,2,'SystemWeb',NULL,NULL);

/*Table structure for table `act_ru_identitylink` */

DROP TABLE IF EXISTS `act_ru_identitylink`;

CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ru_identitylink` */

insert  into `act_ru_identitylink`(`ID_`,`REV_`,`GROUP_ID_`,`TYPE_`,`USER_ID_`,`TASK_ID_`,`PROC_INST_ID_`,`PROC_DEF_ID_`) values 
('232a06ffc22311e7b894a01d48d28732',1,NULL,'participant','SU_201411170002',NULL,'c2a158f4c22211e7b894a01d48d28732',NULL),
('3859eaa4c22311e7b894a01d48d28732',1,NULL,'participant','SU_201411170002',NULL,'df059141c22211e7b894a01d48d28732',NULL),
('8d29faab204911e8aef8485ab673973d',1,NULL,'starter','SU_201411170001',NULL,'8d2343ea204911e8aef8485ab673973d',NULL),
('8d3ebb64204911e8aef8485ab673973d',1,NULL,'candidate','SU_2016112417080001','8d3804a3204911e8aef8485ab673973d',NULL,NULL),
('8d3f0985204911e8aef8485ab673973d',1,NULL,'participant','SU_2016112417080001',NULL,'8d2343ea204911e8aef8485ab673973d',NULL),
('8d3f0986204911e8aef8485ab673973d',1,NULL,'candidate','SU_201411170001','8d3804a3204911e8aef8485ab673973d',NULL,NULL),
('99c346b8f50611e7bba6a01d48d28732',1,NULL,'participant','SU_2017121217530001',NULL,'df059141c22211e7b894a01d48d28732',NULL),
('9a22a4c9f50611e7bba6a01d48d28732',1,NULL,'participant','SU_2017121217530001',NULL,'c2a158f4c22211e7b894a01d48d28732',NULL),
('aca283da0d7011e89b91a01d48d28732',1,NULL,'starter','SU_201411170001',NULL,'ac99d1490d7011e89b91a01d48d28732',NULL),
('acb4d3930d7011e89b91a01d48d28732',1,NULL,'candidate','SU_2016112417080001','acae92020d7011e89b91a01d48d28732',NULL,NULL),
('acb4faa40d7011e89b91a01d48d28732',1,NULL,'participant','SU_2016112417080001',NULL,'ac99d1490d7011e89b91a01d48d28732',NULL),
('acb4faa50d7011e89b91a01d48d28732',1,NULL,'candidate','SU_201411170001','acae92020d7011e89b91a01d48d28732',NULL,NULL),
('c2a1ce25c22211e7b894a01d48d28732',1,NULL,'starter','SU_201411170001',NULL,'c2a158f4c22211e7b894a01d48d28732',NULL),
('c2a8fa4fc22211e7b894a01d48d28732',1,NULL,'participant','SU_2016112417080001',NULL,'c2a158f4c22211e7b894a01d48d28732',NULL),
('df05df62c22211e7b894a01d48d28732',1,NULL,'starter','SU_201411170001',NULL,'df059141c22211e7b894a01d48d28732',NULL),
('df078d4cc22211e7b894a01d48d28732',1,NULL,'participant','SU_2016112417080001',NULL,'df059141c22211e7b894a01d48d28732',NULL);

/*Table structure for table `act_ru_job` */

DROP TABLE IF EXISTS `act_ru_job`;

CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ru_job` */

/*Table structure for table `act_ru_task` */

DROP TABLE IF EXISTS `act_ru_task`;

CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ru_task` */

insert  into `act_ru_task`(`ID_`,`REV_`,`EXECUTION_ID_`,`PROC_INST_ID_`,`PROC_DEF_ID_`,`NAME_`,`PARENT_TASK_ID_`,`DESCRIPTION_`,`TASK_DEF_KEY_`,`OWNER_`,`ASSIGNEE_`,`DELEGATION_`,`PRIORITY_`,`CREATE_TIME_`,`DUE_DATE_`,`CATEGORY_`,`SUSPENSION_STATE_`,`TENANT_ID_`,`FORM_KEY_`) values 
('232a06fec22311e7b894a01d48d28732',2,'c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','个人签收',NULL,'个人签收','UT_8','SU_201411170002','SU_2017121217530001','PENDING',50,'2017-11-05 20:16:22',NULL,NULL,1,'SystemWeb',NULL),
('3859c393c22311e7b894a01d48d28732',2,'df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','个人签收',NULL,'个人签收','UT_8','SU_201411170002','SU_2017121217530001','PENDING',50,'2017-11-05 20:16:58',NULL,NULL,1,'SystemWeb',NULL),
('8d3804a3204911e8aef8485ab673973d',1,'8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','部门领导',NULL,'部门领导','UT_4',NULL,NULL,NULL,50,'2018-03-05 15:48:10',NULL,NULL,1,'SystemWeb',NULL),
('acae92020d7011e89b91a01d48d28732',1,'ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732','WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732','部门领导',NULL,'部门领导','UT_4',NULL,NULL,NULL,50,'2018-02-09 16:10:21',NULL,NULL,1,'SystemWeb',NULL);

/*Table structure for table `act_ru_variable` */

DROP TABLE IF EXISTS `act_ru_variable`;

CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `act_ru_variable` */

insert  into `act_ru_variable`(`ID_`,`REV_`,`TYPE_`,`NAME_`,`EXECUTION_ID_`,`PROC_INST_ID_`,`TASK_ID_`,`BYTEARRAY_ID_`,`DOUBLE_`,`LONG_`,`TEXT_`,`TEXT2_`) values 
('2322db0bc22311e7b894a01d48d28732',1,'string','wfSuggestion','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'test_002',NULL),
('38530cd0c22311e7b894a01d48d28732',1,'string','wfSuggestion','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'test_002',NULL),
('8d2a96ed204911e8aef8485ab673973d',1,'string','btnclose','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2b332f204911e8aef8485ab673973d',1,'serializable','UT_26_assigneeUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'8d2b332e204911e8aef8485ab673973d',NULL,NULL,NULL,NULL),
('8d2b5a41204911e8aef8485ab673973d',1,'string','candidateGroupsId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2b5a42204911e8aef8485ab673973d',1,'string','formdata','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2b5a43204911e8aef8485ab673973d',1,'string','btnBack','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2b8155204911e8aef8485ab673973d',1,'serializable','UT_14_assigneeUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'8d2b8154204911e8aef8485ab673973d',NULL,NULL,NULL,NULL),
('8d2b8157204911e8aef8485ab673973d',1,'string','UT_42_assigneeUserId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('8d2b8158204911e8aef8485ab673973d',1,'string','urldata','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2ba869204911e8aef8485ab673973d',1,'string','processkey','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'WF_2017110519440001',NULL),
('8d2ba86a204911e8aef8485ab673973d',1,'string','defkey','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL),
('8d2ba86b204911e8aef8485ab673973d',1,'string','btnsubmit','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2ba86c204911e8aef8485ab673973d',1,'string','btnimage','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2ba86d204911e8aef8485ab673973d',1,'string','FI_btnKey','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2bcf7e204911e8aef8485ab673973d',1,'string','id','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2bcf7f204911e8aef8485ab673973d',1,'string','_idvalue','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2bcf81204911e8aef8485ab673973d',1,'serializable','UT_42_assigneeUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'8d2bcf80204911e8aef8485ab673973d',NULL,NULL,NULL,NULL),
('8d2bf693204911e8aef8485ab673973d',1,'string','name','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'test',NULL),
('8d2bf694204911e8aef8485ab673973d',1,'string','moneyInfo','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'1',NULL),
('8d2bf695204911e8aef8485ab673973d',1,'string','UT_4_candidateUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL),
('8d2bf696204911e8aef8485ab673973d',1,'string','UT_26_assigneeUserId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('8d2c1da7204911e8aef8485ab673973d',1,'string','candidateUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c1da8204911e8aef8485ab673973d',1,'string','querystring','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'?fiid=${param.businesskey}',NULL),
('8d2c1da9204911e8aef8485ab673973d',1,'string','UT_20_assigneeUserId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c1daa204911e8aef8485ab673973d',1,'string','assigneeUserId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c1dab204911e8aef8485ab673973d',1,'string','btnBackWF','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c44bd204911e8aef8485ab673973d',1,'serializable','UT_8_assigneeUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'8d2c44bc204911e8aef8485ab673973d',NULL,NULL,NULL,NULL),
('8d2c44bf204911e8aef8485ab673973d',1,'string','UT_14_assigneeUserId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c44c0204911e8aef8485ab673973d',1,'string','FI_btnId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c6bd1204911e8aef8485ab673973d',1,'string','btnsubInitWF','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c6bd2204911e8aef8485ab673973d',1,'string','tkid','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c6bd3204911e8aef8485ab673973d',1,'string','code','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c6bd4204911e8aef8485ab673973d',1,'string','multiFile','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2c92e5204911e8aef8485ab673973d',1,'string','businesskey','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'BK_2018030515480001',NULL),
('8d2c92e6204911e8aef8485ab673973d',1,'string','fileId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'FL_2018030515470001',NULL),
('8d2c92e7204911e8aef8485ab673973d',1,'string','stufiledId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'FL_2018030515470002',NULL),
('8d2c92e8204911e8aef8485ab673973d',1,'string','_submitInfo','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'1',NULL),
('8d2c92e9204911e8aef8485ab673973d',1,'string','UT_8_assigneeUserId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'SU_201411170002',NULL),
('8d2cb9fb204911e8aef8485ab673973d',1,'serializable','UT_20_assigneeUsersId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,'8d2cb9fa204911e8aef8485ab673973d',NULL,NULL,NULL,NULL),
('8d2cb9fd204911e8aef8485ab673973d',1,'string','btnsave','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2cb9fe204911e8aef8485ab673973d',1,'string','inid','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('8d2ce10f204911e8aef8485ab673973d',1,'string','FIID','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'BK_2018030515480002',NULL),
('8d2ce110204911e8aef8485ab673973d',1,'string','teacherId','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'PT_201504120001',NULL),
('8d2ce111204911e8aef8485ab673973d',1,'string','btnnext','8d2343ea204911e8aef8485ab673973d','8d2343ea204911e8aef8485ab673973d',NULL,NULL,NULL,NULL,'',NULL),
('aca3bc5c0d7011e89b91a01d48d28732',1,'string','btnclose','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4589e0d7011e89b91a01d48d28732',1,'serializable','UT_26_assigneeUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'aca4589d0d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL),
('aca47fb00d7011e89b91a01d48d28732',1,'string','candidateGroupsId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca47fb10d7011e89b91a01d48d28732',1,'string','formdata','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca47fb20d7011e89b91a01d48d28732',1,'string','btnBack','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4a6c40d7011e89b91a01d48d28732',1,'serializable','UT_14_assigneeUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'aca4a6c30d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL),
('aca4a6c60d7011e89b91a01d48d28732',1,'string','UT_42_assigneeUserId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('aca4a6c70d7011e89b91a01d48d28732',1,'string','urldata','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4cdd80d7011e89b91a01d48d28732',1,'string','processkey','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'WF_2017110519440001',NULL),
('aca4cdd90d7011e89b91a01d48d28732',1,'string','defkey','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL),
('aca4cdda0d7011e89b91a01d48d28732',1,'string','btnsubmit','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4cddb0d7011e89b91a01d48d28732',1,'string','btnimage','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4f4ec0d7011e89b91a01d48d28732',1,'string','FI_btnKey','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4f4ed0d7011e89b91a01d48d28732',1,'string','id','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca4f4ee0d7011e89b91a01d48d28732',1,'string','_idvalue','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca51c000d7011e89b91a01d48d28732',1,'serializable','UT_42_assigneeUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'aca51bff0d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL),
('aca51c020d7011e89b91a01d48d28732',1,'string','name','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'test',NULL),
('aca51c030d7011e89b91a01d48d28732',1,'string','moneyInfo','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'1',NULL),
('aca543140d7011e89b91a01d48d28732',1,'string','UT_4_candidateUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL),
('aca543150d7011e89b91a01d48d28732',1,'string','UT_26_assigneeUserId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('aca543160d7011e89b91a01d48d28732',1,'string','candidateUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca543170d7011e89b91a01d48d28732',1,'string','querystring','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'?fiid=${param.businesskey}',NULL),
('aca56a280d7011e89b91a01d48d28732',1,'string','UT_20_assigneeUserId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca56a290d7011e89b91a01d48d28732',1,'string','assigneeUserId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca56a2a0d7011e89b91a01d48d28732',1,'string','btnBackWF','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5913c0d7011e89b91a01d48d28732',1,'serializable','UT_8_assigneeUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'aca5913b0d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL),
('aca5913e0d7011e89b91a01d48d28732',1,'string','UT_14_assigneeUserId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5913f0d7011e89b91a01d48d28732',1,'string','FI_btnId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5b8500d7011e89b91a01d48d28732',1,'string','btnsubInitWF','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5b8510d7011e89b91a01d48d28732',1,'string','tkid','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5b8520d7011e89b91a01d48d28732',1,'string','code','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5b8530d7011e89b91a01d48d28732',1,'string','multiFile','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5b8540d7011e89b91a01d48d28732',1,'string','businesskey','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'BK_2018020916100001',NULL),
('aca5b8550d7011e89b91a01d48d28732',1,'string','fileId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'FL_2018020916090001',NULL),
('aca5df660d7011e89b91a01d48d28732',1,'string','stufiledId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'FL_2018020916090002',NULL),
('aca5df670d7011e89b91a01d48d28732',1,'string','_submitInfo','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'1',NULL),
('aca5df680d7011e89b91a01d48d28732',1,'string','UT_8_assigneeUserId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170002',NULL),
('aca5df6a0d7011e89b91a01d48d28732',1,'serializable','UT_20_assigneeUsersId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,'aca5df690d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL),
('aca5df6c0d7011e89b91a01d48d28732',1,'string','btnsave','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca5df6d0d7011e89b91a01d48d28732',1,'string','inid','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('aca6067e0d7011e89b91a01d48d28732',1,'string','FIID','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'BK_2018020916100002',NULL),
('aca6067f0d7011e89b91a01d48d28732',1,'string','teacherId','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'PT_201504120001',NULL),
('aca606800d7011e89b91a01d48d28732',1,'string','btnnext','ac99d1490d7011e89b91a01d48d28732','ac99d1490d7011e89b91a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a26a67c22211e7b894a01d48d28732',1,'string','btnclose','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a2df99c22211e7b894a01d48d28732',1,'serializable','UT_26_assigneeUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'c2a2df98c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('c2a2df9bc22211e7b894a01d48d28732',1,'string','candidateGroupsId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a2df9cc22211e7b894a01d48d28732',1,'string','formdata','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a306adc22211e7b894a01d48d28732',1,'string','btnBack','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a306afc22211e7b894a01d48d28732',1,'serializable','UT_14_assigneeUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'c2a306aec22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('c2a306b1c22211e7b894a01d48d28732',1,'string','UT_42_assigneeUserId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('c2a306b2c22211e7b894a01d48d28732',1,'string','urldata','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a306b3c22211e7b894a01d48d28732',1,'string','processkey','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'WF_2017110519440001',NULL),
('c2a32dc4c22211e7b894a01d48d28732',1,'string','defkey','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL),
('c2a32dc5c22211e7b894a01d48d28732',1,'string','btnsubmit','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a32dc6c22211e7b894a01d48d28732',1,'string','btnimage','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a32dc7c22211e7b894a01d48d28732',1,'string','FI_btnKey','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a32dc8c22211e7b894a01d48d28732',2,'string','id','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'LC_2017110520130001',NULL),
('c2a32dc9c22211e7b894a01d48d28732',1,'string','_idvalue','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a354dbc22211e7b894a01d48d28732',1,'serializable','UT_42_assigneeUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'c2a32dcac22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('c2a354ddc22211e7b894a01d48d28732',1,'string','name','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'20171105_001',NULL),
('c2a354dec22211e7b894a01d48d28732',1,'string','moneyInfo','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'1',NULL),
('c2a354dfc22211e7b894a01d48d28732',1,'string','UT_4_candidateUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL),
('c2a354e0c22211e7b894a01d48d28732',1,'string','UT_26_assigneeUserId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('c2a354e1c22211e7b894a01d48d28732',1,'string','candidateUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a354e2c22211e7b894a01d48d28732',2,'string','querystring','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'?fiid=BK_2017110520130002&inid=c2a158f4c22211e7b894a01d48d28732',NULL),
('c2a37bf3c22211e7b894a01d48d28732',1,'string','UT_20_assigneeUserId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL),
('c2a37bf4c22211e7b894a01d48d28732',1,'string','assigneeUserId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a37bf5c22211e7b894a01d48d28732',1,'string','btnBackWF','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a37bf7c22211e7b894a01d48d28732',1,'serializable','UT_8_assigneeUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'c2a37bf6c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('c2a37bf9c22211e7b894a01d48d28732',1,'string','UT_14_assigneeUserId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL),
('c2a37bfac22211e7b894a01d48d28732',1,'string','FI_btnId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a3a30bc22211e7b894a01d48d28732',1,'string','btnsubInitWF','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a3a30cc22211e7b894a01d48d28732',2,'string','tkid','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'c2a773adc22211e7b894a01d48d28732',NULL),
('c2a3a30dc22211e7b894a01d48d28732',1,'string','code','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a3a30ec22211e7b894a01d48d28732',1,'string','multiFile','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a3a30fc22211e7b894a01d48d28732',2,'string','businesskey','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'BK_2017110520130002',NULL),
('c2a3a310c22211e7b894a01d48d28732',1,'string','fileId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'FL_2017110520130001',NULL),
('c2a3a311c22211e7b894a01d48d28732',1,'string','stufiledId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'FL_2017110520130002',NULL),
('c2a3ca22c22211e7b894a01d48d28732',1,'string','_submitInfo','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'1',NULL),
('c2a3ca23c22211e7b894a01d48d28732',1,'string','UT_8_assigneeUserId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170002',NULL),
('c2a3ca25c22211e7b894a01d48d28732',1,'serializable','UT_20_assigneeUsersId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,'c2a3ca24c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('c2a3f137c22211e7b894a01d48d28732',1,'string','btnsave','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('c2a3f138c22211e7b894a01d48d28732',2,'string','inid','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'c2a158f4c22211e7b894a01d48d28732',NULL),
('c2a3f139c22211e7b894a01d48d28732',1,'string','FIID','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'BK_2017110520130002',NULL),
('c2a3f13ac22211e7b894a01d48d28732',1,'string','teacherId','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'PT_201504120001',NULL),
('c2a3f13bc22211e7b894a01d48d28732',1,'string','btnnext','c2a158f4c22211e7b894a01d48d28732','c2a158f4c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df05df64c22211e7b894a01d48d28732',1,'string','btnclose','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df060676c22211e7b894a01d48d28732',1,'serializable','UT_26_assigneeUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'df060675c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('df060678c22211e7b894a01d48d28732',1,'string','candidateGroupsId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df060679c22211e7b894a01d48d28732',1,'string','formdata','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df06067ac22211e7b894a01d48d28732',1,'string','btnBack','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df06067cc22211e7b894a01d48d28732',1,'serializable','UT_14_assigneeUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'df06067bc22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('df06067ec22211e7b894a01d48d28732',1,'string','UT_42_assigneeUserId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('df06067fc22211e7b894a01d48d28732',1,'string','urldata','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df062d90c22211e7b894a01d48d28732',1,'string','processkey','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'WF_2017110519440001',NULL),
('df062d91c22211e7b894a01d48d28732',1,'string','defkey','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'WF_2017110519440001:1:aaba1d83c22211e7b894a01d48d28732',NULL),
('df062d92c22211e7b894a01d48d28732',1,'string','btnsubmit','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df062d93c22211e7b894a01d48d28732',1,'string','btnimage','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df062d94c22211e7b894a01d48d28732',1,'string','FI_btnKey','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df062d95c22211e7b894a01d48d28732',2,'string','id','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'LC_2017110520140001',NULL),
('df062d96c22211e7b894a01d48d28732',1,'string','_idvalue','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df062d98c22211e7b894a01d48d28732',1,'serializable','UT_42_assigneeUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'df062d97c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('df0654aac22211e7b894a01d48d28732',1,'string','name','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'test20171105_002',NULL),
('df0654abc22211e7b894a01d48d28732',1,'string','moneyInfo','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'1',NULL),
('df0654acc22211e7b894a01d48d28732',1,'string','UT_4_candidateUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_2016112417080001,SU_201411170001',NULL),
('df0654adc22211e7b894a01d48d28732',1,'string','UT_26_assigneeUserId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170001',NULL),
('df0654aec22211e7b894a01d48d28732',1,'string','candidateUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df0654afc22211e7b894a01d48d28732',2,'string','querystring','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'?fiid=BK_2017110520140002&inid=df059141c22211e7b894a01d48d28732',NULL),
('df0654b0c22211e7b894a01d48d28732',1,'string','UT_20_assigneeUserId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL),
('df0654b1c22211e7b894a01d48d28732',1,'string','assigneeUserId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df0654b2c22211e7b894a01d48d28732',1,'string','btnBackWF','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df067bc4c22211e7b894a01d48d28732',1,'serializable','UT_8_assigneeUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'df0654b3c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('df067bc6c22211e7b894a01d48d28732',1,'string','UT_14_assigneeUserId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201507050003,SU_201507050004',NULL),
('df067bc7c22211e7b894a01d48d28732',1,'string','FI_btnId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df067bc8c22211e7b894a01d48d28732',1,'string','btnsubInitWF','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df067bc9c22211e7b894a01d48d28732',2,'string','tkid','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'df07181ac22211e7b894a01d48d28732',NULL),
('df067bcac22211e7b894a01d48d28732',1,'string','code','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df067bcbc22211e7b894a01d48d28732',1,'string','multiFile','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df067bccc22211e7b894a01d48d28732',2,'string','businesskey','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'BK_2017110520140002',NULL),
('df067bcdc22211e7b894a01d48d28732',1,'string','fileId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'FL_2017110520140001',NULL),
('df067bcec22211e7b894a01d48d28732',1,'string','stufiledId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'FL_2017110520140002',NULL),
('df06a2dfc22211e7b894a01d48d28732',1,'string','_submitInfo','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'1',NULL),
('df06a2e0c22211e7b894a01d48d28732',1,'string','UT_8_assigneeUserId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'SU_201411170002',NULL),
('df06a2e2c22211e7b894a01d48d28732',1,'serializable','UT_20_assigneeUsersId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,'df06a2e1c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL),
('df06a2e4c22211e7b894a01d48d28732',1,'string','btnsave','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL),
('df06a2e5c22211e7b894a01d48d28732',2,'string','inid','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'df059141c22211e7b894a01d48d28732',NULL),
('df06a2e6c22211e7b894a01d48d28732',1,'string','FIID','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'BK_2017110520140002',NULL),
('df06a2e7c22211e7b894a01d48d28732',1,'string','teacherId','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'PT_201504120001',NULL),
('df06a2e8c22211e7b894a01d48d28732',1,'string','btnnext','df059141c22211e7b894a01d48d28732','df059141c22211e7b894a01d48d28732',NULL,NULL,NULL,NULL,'',NULL);

/*Table structure for table `sc_commoncode` */

DROP TABLE IF EXISTS `sc_commoncode`;

CREATE TABLE `sc_commoncode` (
  `CC_ID` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `CC_Name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CC_Index` int(11) DEFAULT NULL,
  `CC_ShortName` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CC_Remark` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CC_ParentID` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CCT_ID` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`CC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `sc_commoncode` */

insert  into `sc_commoncode`(`CC_ID`,`CC_Name`,`CC_Index`,`CC_ShortName`,`CC_Remark`,`CC_ParentID`,`CCT_ID`) values 
('CC_001','是',2,NULL,'1',NULL,'CCT_002'),
('CC_002','否',1,NULL,'0',NULL,'CCT_002'),
('CC_003','是',2,NULL,'0',NULL,'CCT_003'),
('CC_004','否',1,NULL,'0',NULL,'CCT_003'),
('CC_005','用户',1,'user','1',NULL,'CCT_001'),
('CC_006','组',2,'role','2',NULL,'CCT_001'),
('CC_007','动态SQL',3,'sql','3',NULL,'CCT_001'),
('CC_035','测试工作流',1,NULL,NULL,NULL,'CCT_008'),
('SC_2017101910220001','提交',1,'tj','','','SCC_2017101910210001'),
('SC_2017101910220002','回退',2,'huitu','','','SCC_2017101910210001'),
('SC_2017101910230001','取回',3,'quhui','','','SCC_2017101910210001'),
('SC_2017101910240001','流程管理跳转',4,'lcgl','','','SCC_2017101910210001'),
('SC_2017103011290001','管理员删除',5,'glysc','','','SCC_2017101910210001');

/*Table structure for table `sc_commoncodetype` */

DROP TABLE IF EXISTS `sc_commoncodetype`;

CREATE TABLE `sc_commoncodetype` (
  `CCT_ID` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `CCT_Code` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CCT_Name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CCT_Index` int(11) DEFAULT NULL,
  `CCT_Remark` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`CCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `sc_commoncodetype` */

insert  into `sc_commoncodetype`(`CCT_ID`,`CCT_Code`,`CCT_Name`,`CCT_Index`,`CCT_Remark`) values 
('CCT_001','user_type','类型',1,NULL),
('CCT_002','user_select','是否弹窗',2,NULL),
('CCT_003','user_multiple','是否多选',3,NULL),
('CCT_008','WF_type','工作流类别设定',8,NULL),
('SCC_2017101910210001','tjlx','提交类型',4,'');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
