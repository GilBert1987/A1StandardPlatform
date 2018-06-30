USE commontable;

DELETE sc_database FROM sc_database;

INSERT INTO `sc_database` VALUES ('1', 'com', 'commontable', '通用库', 1, 1, 1);
INSERT INTO `sc_database` VALUES ('12', 'activemq', 'activemq', '消息数据库', 1, 1, 12);
INSERT INTO `sc_database` VALUES ('2', 'wf', 'activiti', '工作流', 1, 1, 2);
INSERT INTO `sc_database` VALUES ('3', 'shiro', 'shirotable', '权限库', 1, 1, 3);
INSERT INTO `sc_database` VALUES ('4', 'rest', 'restaurant', '餐厅库', 1, 0, 4);
INSERT INTO `sc_database` VALUES ('5', 'qz', 'quartz', '计划调度', 1, 1, 5);
INSERT INTO `sc_database` VALUES ('6', 'kylin', 'kylin', '报表库', 0, 0, 6);