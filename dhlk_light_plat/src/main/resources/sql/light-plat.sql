/*==============================================================*/
/* Table: 照明一体机                                        */
/*==============================================================*/
drop table if exists dhlk_light_computer;
create table dhlk_light_computer
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(100)                   DEFAULT NULL COMMENT '名称',
   sn                   varchar(50)                    DEFAULT NULL COMMENT 'sn码',
   ip                   varchar(20)                    DEFAULT NULL COMMENT 'ip地址',
   create_time          timestamp                      DEFAULT NOW() COMMENT '添加时间',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户对象',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='照明一体机';
/*==============================================================*/
/* Table: 开关                                        */
/*==============================================================*/
drop table if exists dhlk_light_switch;
create table dhlk_light_switch
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(100)                   DEFAULT NULL COMMENT '名称',
   sn                   varchar(50)                    DEFAULT NULL COMMENT 'sn码',
   ip                   varchar(20)                    DEFAULT NULL COMMENT 'ip地址',
   area_id              varchar(100)                   DEFAULT NULL COMMENT '区域',
   computer_id          integer                        DEFAULT NULL COMMENT '一体机',
   create_time          timestamp                      DEFAULT NOW() COMMENT '添加时间',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户对象',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开关';
/*==============================================================*/
/* Table: 开关和灯关系绑定                                        */
/*==============================================================*/
drop table if exists dhlk_light_led_switch;
create table dhlk_light_led_switch
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   switch_id            integer                        DEFAULT NULL COMMENT '开关',
   led_id               integer                        DEFAULT NULL COMMENT '灯',
   group_id	            integer				           DEFAULT NULL COMMENT '开关组id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开关和灯关系绑定';
/*==============================================================*/
/* Table: 灯                                        */
/*==============================================================*/
drop table if exists dhlk_light_led;
create table dhlk_light_led
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   sn                   varchar(50)                    DEFAULT NULL COMMENT 'sn码',
   ip                   varchar(20)                    DEFAULT NULL COMMENT 'ip地址',
   xaxis                varchar(50)                    DEFAULT NULL COMMENT 'x坐标',
   yaxis                varchar(50)                    DEFAULT NULL COMMENT 'y坐标',
   brightness           integer                        DEFAULT NULL COMMENT '亮度',
   ind_bright           integer                        DEFAULT NULL COMMENT '人感感应亮度',
   unind_bright         integer                        DEFAULT NULL COMMENT '人感未感应亮度',
   ind_time             integer                        DEFAULT NULL COMMENT '人感感应时间',
   ind_status           integer                        DEFAULT 0 COMMENT '人感感应开启状态0-未开启1-开启',
   create_time          timestamp                      DEFAULT NOW() COMMENT '添加时间',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户对象',
   area_id              varchar(100)                   DEFAULT NULL COMMENT '区域对象',
   alias                varchar(50)                    DEFAULT NULL COMMENT '别名',
   status               integer                        DEFAULT 0 COMMENT '灯状态 0未删除 1已删除',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯';
/*==============================================================*/
/* Table: 故障代码                                        */
/*==============================================================*/
drop table if exists dhlk_light_fault_code;
create table dhlk_light_fault_code
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(100)                   DEFAULT NULL COMMENT '名称',
   code                 varchar(100)                   DEFAULT NULL COMMENT '代码',
   content              TEXT                           DEFAULT NULL COMMENT '故障说明',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='故障代码';
/*==============================================================*/
/* Table: 灯在线统计                                 */
/*==============================================================*/
drop table if exists dhlk_light_led_online;
create table dhlk_light_led_online
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   led_sn               varchar(100)                   DEFAULT NULL COMMENT '灯sn',
   online_hour          timestamp                      DEFAULT NOW() COMMENT '在线时间(年月日时)',
   online_time          integer                        DEFAULT NULL COMMENT '在线时长(分钟)',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户对象',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯在线统计，按小时统计灯在线多少分钟';
/*==============================================================*/
/* Table: 灯能耗统计                                 */
/*==============================================================*/
drop table if exists dhlk_light_led_power;
create table dhlk_light_led_power
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   led_sn               varchar(100)                   DEFAULT NULL COMMENT '灯sn',
   electric             decimal(8,4)                   DEFAULT NULL COMMENT '电流',
   voltage              decimal(8,4)                   DEFAULT NULL COMMENT '电压',
   power                decimal(8,4)                   DEFAULT NULL COMMENT '功率',
   create_time          timestamp                      DEFAULT NOW() COMMENT '统计时间',
   energy               decimal(12,4)                   DEFAULT NULL COMMENT '电能',
   brightness           integer                        DEFAULT NULL COMMENT '灯亮度',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户对象',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯功率统计，10分钟统计一次';

/*==============================================================*/
/* Table: 区域管理                                 */
/*==============================================================*/
drop table if exists dhlk_light_area;
create table dhlk_light_area
(
   id                   varchar(100)                  NOT NULL,
   area            		varchar(50)                   DEFAULT NULL COMMENT '区域名称',
   tenant_id      		integer                       DEFAULT NULL COMMENT '租户Id',
   image_path           varchar(200)                  DEFAULT NULL COMMENT '图纸地址',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域管理';

/*==============================================================*/
/* Table: 施工信息                                 */
/*==============================================================*/
drop table if exists dhlk_light_construction;
create table dhlk_light_construction
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   sign_date            timestamp                      DEFAULT NOW()COMMENT '签约日期',
   business_people      varchar(20)                    DEFAULT NULL COMMENT '商务人员',
   pack_count           integer                        DEFAULT NULL COMMENT '购入Pack 数量',
   start_date           timestamp                      DEFAULT NOW() COMMENT '安装开始日期',
   end_date             timestamp                      DEFAULT NOW() COMMENT '安装结束日期',
   impl_people          varchar(20)                    DEFAULT NULL COMMENT '实施人员',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户对象id',
   create_time          timestamp                      DEFAULT NOW() COMMENT '添加时间',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='施工信息';

/*==============================================================*/
/* Table: 意见反馈                                 */
/*==============================================================*/
drop table if exists dhlk_light_question;
create table dhlk_light_question
(
   id                   varchar(100)                  NOT NULL,
   sn            		varchar(50)                   DEFAULT NULL COMMENT '问题编号',
   title                varchar(200)                  DEFAULT NULL COMMENT '问题概述',
   content              varchar(1000)                 DEFAULT NULL COMMENT '问题描述',
   link_man             varchar(20)                   DEFAULT NULL COMMENT '联系人',
   phone                varchar(15)                   DEFAULT NULL COMMENT '联系电话',
   create_user          integer                       DEFAULT NULL COMMENT '添加人',
   create_time          timestamp					  DEFAULT NOW() COMMENT '添加时间',
   status      		    integer                       DEFAULT '0' COMMENT '状态',
   tenant_id            integer                       DEFAULT NULL COMMENT '租户对象Id',
   file_id            	varchar(100)                  DEFAULT NULL COMMENT '附件Id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈';

/*==============================================================*/
/* Table: 问题解决方案                                 */
/*==============================================================*/
drop table if exists dhlk_light_question_answer;
create table dhlk_light_question_answer
(
   id                   varchar(100)                  NOT NULL,
   answer               TEXT                          DEFAULT NULL COMMENT '解决方案',
   deal_user            integer                       DEFAULT NULL COMMENT '处理人',
   deal_time            timestamp                     DEFAULT NOW() COMMENT '处理时间',
   question_id          varchar(100)                  DEFAULT NULL COMMENT '意见反馈Id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题解决方案';
/*==============================================================*/
/* Table: 灯操作记录                               */
/*==============================================================*/
drop table if exists dhlk_light_led_record;
create table dhlk_light_led_record
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   operator             varchar(20)                    DEFAULT NULL COMMENT '操作人',
   operate_ip           varchar(20)                    DEFAULT NULL COMMENT '操作人IP',
   operate_time         timestamp                      DEFAULT NOW() COMMENT '操作时间',
   command_info         text                           DEFAULT NULL COMMENT '操作命令',
   led_sn               varchar(100)                   DEFAULT NULL COMMENT '灯sn',
   back_result          text                           DEFAULT NULL COMMENT '返回结果',
   operate_result       varchar(100)                   DEFAULT NULL COMMENT '操作结果',
   time_key             varchar(50)                    DEFAULT NULL COMMENT '时间戳',
   back_time            timestamp                      NULL COMMENT '返回时间',
   source               varchar(10)                    DEFAULT NULL COMMENT '数据来源云端cloud，本地local',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯操作记录';
/*==============================================================*/
/* Table: 定时任务                             */
/*==============================================================*/
DROP TABLE IF EXISTS `dhlk_light_task_scheduler`;
CREATE TABLE `dhlk_light_task_scheduler` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `note` varchar(200) DEFAULT NULL COMMENT '任务描述',
  `status` int(11) DEFAULT NULL COMMENT '任务状态 0开启 1停止(默认)',
  `area_id` varchar(100) DEFAULT NULL COMMENT '区域id',
  `switch_id` int(11) DEFAULT NULL COMMENT '开关id',
  `schedule_cycle` varchar(15) DEFAULT NULL COMMENT '定时周期',
   `tenant_id`   integer    DEFAULT NULL COMMENT '租户对象Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*==============================================================*/
/* Table: 定时任务详情                             */
/*==============================================================*/
DROP TABLE IF EXISTS `dhlk_light_task_scheduler_detail`;
CREATE TABLE `dhlk_light_task_scheduler_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `schedule_id` int(11) DEFAULT NULL COMMENT '定时任务id',
  `time` varchar(20) DEFAULT NULL COMMENT '时间',
  `expression` varchar(100) DEFAULT NULL COMMENT '定时格式',
  `on_off` int(11) DEFAULT NULL COMMENT '操作',
  `brightness` int(11) DEFAULT NULL COMMENT '亮度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*==============================================================*/
/* Table: 定时任务日志                             */
/*==============================================================*/
DROP TABLE IF EXISTS `dhlk_light_task_scheduler_log`;
CREATE TABLE `dhlk_light_task_scheduler_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` varchar(50) DEFAULT NULL COMMENT '执行时间',
  `result` varchar(100) DEFAULT NULL COMMENT '执行结果',
  `schedule_id` int(11) DEFAULT NULL COMMENT '定时任务id',
  `scheduler_detail_id` int(11) DEFAULT NULL COMMENT '定时详情id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: 故障信息上报                               */
/*==============================================================*/
drop table if exists dhlk_light_led_fault;
create table dhlk_light_led_fault
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   led_sn               varchar(50)                    DEFAULT NULL COMMENT '照明设备',
   fault_code           varchar(100)                   DEFAULT NULL COMMENT '故障代码',
   type                 integer                        DEFAULT 1 COMMENT '设备类型 1-灯/2-人感/3-光感/4-开关',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='故障信息上报';

/*==============================================================*/
/* Table: 企业原照明功率维护  */
/*==============================================================*/
drop table if exists dhlk_light_original_power;
create table dhlk_light_original_power
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   led_count            integer                      DEFAULT NULL COMMENT '灯数量',
   led_power            float                        DEFAULT NULL COMMENT '灯功率',
   led_opentime         float                        DEFAULT NULL COMMENT '开灯时长小时',
   tenant_id            integer                      DEFAULT NULL COMMENT '租户Id',
   pre_brightness       integer                      DEFAULT NULL COMMENT '预设亮度',
   sys_runtime          varchar(50)                  DEFAULT NULL COMMENT '系统初始化时间戳',
   icon_size           varchar(50)                  DEFAULT NULL COMMENT '图标大小',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业原照明功率维护';

/*==============================================================*/
/* Table: 人感  */
/*==============================================================*/
drop table if exists dhlk_light_people_feel;
create table dhlk_light_people_feel
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   on_off		        integer                      DEFAULT 0 COMMENT '人感开关',
   trig_delay_tm        float                        DEFAULT NULL COMMENT '触发延迟时间',
   n_ramp_tm            float                        DEFAULT NULL COMMENT '不触发渐变时间',
   minvalue             float                        DEFAULT NULL COMMENT '人感目标最低亮度',
   maxval               float                        DEFAULT NULL COMMENT '人感目标最高亮度',
   tenant_id            integer                      DEFAULT NULL COMMENT '租户Id',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人感';

/*==============================================================*/
/* Table: 云人感数据  */
/*==============================================================*/
drop table if exists dhlk_light_cloud_people_feel_statistics;
create table dhlk_light_cloud_people_feel_statistics
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   led_sn 		        varchar(50)                  DEFAULT NULL COMMENT '照明设备',
   number               integer                      DEFAULT NULL COMMENT '次数',
   create_time          timestamp                    NULL COMMENT '统计时间',
   tenant_id            integer                      DEFAULT NULL COMMENT '租户Id',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云人感数据统计';

/*==============================================================*/
/* Table: wifi 设置  */
/*==============================================================*/
drop table if exists dhlk_light_wifi;
create table dhlk_light_wifi
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   ip 			        varchar(20)					 DEFAULT NULL COMMENT 'ip地址',
   password             varchar(20)					 DEFAULT NULL COMMENT 'wifi密码',
   ssid        			varchar(60)					 DEFAULT NULL COMMENT 'ssid',
   tenant_id            integer                      DEFAULT NULL COMMENT '租户Id',
   wf_dev				integer    					 DEFAULT 0 COMMENT 'Wifi 模块选择',
   wf_mode              integer						 DEFAULT 0 COMMENT 'wf_mode',
   type              	varchar(10)					 DEFAULT NULL COMMENT '类型,0灯,1开关',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='WiFi 配置';


drop table if exists dhlk_light_switch_group;
create table dhlk_light_switch_group
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   group_id 			integer					     DEFAULT NULL COMMENT '组Id',
   status               integer						 DEFAULT 0 COMMENT '状态默认0',
   switch_id            integer						 DEFAULT NULL COMMENT '开关Id',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开关组设置';

/*==============================================================*/
/* Table: 光感  */
/*==============================================================*/
drop table if exists dhlk_light_intensity;
create table dhlk_light_intensity
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   on_off     			integer						 DEFAULT 0 COMMENT '光感开关',
   illumi_top 	        float						 DEFAULT NULL COMMENT '照度上限值',
   illumi_top_min       float						 DEFAULT NULL COMMENT '照度上限值对应亮度最小值',
   illumi_flr		    float 						 DEFAULT NULL COMMENT '照度下限值',
   illumi_flr_max 		float					     DEFAULT NULL COMMENT '照度下限值对应亮度最大值',
   tenant_id            integer                      DEFAULT NULL COMMENT '租户Id',
   control_param        integer                      DEFAULT NULL COMMENT '控制参数',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='光感';


/*==============================================================*/
/* Table: 验收报告  */
/*==============================================================*/
drop table if exists dhlk_light_inspection_report;
create table dhlk_light_inspection_report
(
   id                   integer                       NOT NULL AUTO_INCREMENT,
   off            	    varchar(10)                   DEFAULT NULL COMMENT '关',
   dimming              varchar(10)                   DEFAULT NULL COMMENT '调光',
   timed_control        varchar(10)                   DEFAULT NULL COMMENT '定时控制',
   create_time          timestamp					  DEFAULT NOW() COMMENT '创建时间',
   tenant_id            integer                       DEFAULT NULL COMMENT '租户对象Id',
   sn                   varchar(50)                   DEFAULT NULL COMMENT 'sn码',
   ons            	    varchar(10)                   DEFAULT NULL COMMENT '开',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验收报告';

/*==============================================================*/
/* Table: 灯版本信息  */
/*==============================================================*/
drop table if exists dhlk_light_version_info;
create table dhlk_light_version_info
(
   id                     integer                        NOT NULL AUTO_INCREMENT,
   name                   varchar(50)                    DEFAULT NULL COMMENT '名称',
   version                varchar(10)                    DEFAULT NULL COMMENT '版本',
   address                varchar(100)                   DEFAULT NULL COMMENT '地址',
   create_time            timestamp     				 DEFAULT NOW() COMMENT '创建时间',
   creator            	  varchar(10)    				 DEFAULT NULL COMMENT '创建人',
   update_time            timestamp     				 DEFAULT NOW() COMMENT '更新时间',
   update_person          varchar(10)    				 DEFAULT NULL COMMENT '更新人',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯版本信息';

/*==============================================================*/
/* Table: 监控日志  */
/*==============================================================*/
drop table if exists dhlk_light_monitoring_log;
create table dhlk_light_monitoring_log
(
   id                     integer                        NOT NULL AUTO_INCREMENT,
   sn                     varchar(50)                    DEFAULT NULL COMMENT 'SN',
   create_time            timestamp                      DEFAULT NOW() COMMENT '创建时间',
   status                 integer                        DEFAULT NULL COMMENT '设备状态',
   tenant_id              integer     				     DEFAULT NULL COMMENT '租户id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控日志';

/*==============================================================*/
/* Table: 参数状态显示信息  */
/*==============================================================*/
drop table if exists dhlk_light_led_param;
create table dhlk_light_led_param
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   sn                   varchar(50)                    DEFAULT NULL COMMENT 'SN码',
   light_on_off         integer                        DEFAULT NULL COMMENT '光感开关',
   illumi_top           integer                        DEFAULT NULL COMMENT '照度上限值',
   illumi_top_min       integer                        DEFAULT NULL COMMENT '上限值最低亮度',
   illumi_flr           integer                        DEFAULT NULL COMMENT '照度下限值',
   illumi_flr_max       integer                        DEFAULT NULL COMMENT '下限值最高亮度',
   people_on_off         integer                       DEFAULT NULL COMMENT '人感开关',
   trig_delay_tm        integer                        DEFAULT NULL COMMENT '延迟时间',
   n_ramp_tm            integer                        DEFAULT NULL COMMENT '渐变时间',
   minval               integer                        DEFAULT NULL COMMENT '最低照度',
   maxval               integer                        DEFAULT NULL COMMENT '最高照度',
   ssid                 varchar(100)                   DEFAULT NULL COMMENT 'SSID',
   ip                   varchar(50)                    DEFAULT NULL COMMENT '网络IP',
   password             varchar(50)                    DEFAULT NULL COMMENT '密码',
   wf_dev               integer                        DEFAULT NULL COMMENT 'WIFI模块',
   wf_mode              integer                        DEFAULT NULL COMMENT '射频范围',
   version              varchar(50)                    DEFAULT NULL COMMENT '固件版本',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数状态显示信息';

/*==============================================================*/
/* Table: 云端同步到本地失败的数据  */
/*==============================================================*/
drop table if exists dhlk_light_sync_data;
create table dhlk_light_sync_data
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   table_name           varchar(50)                    DEFAULT NULL COMMENT '表名',
   operate              varchar(50)                    DEFAULT NULL COMMENT '执行方法',
   ts                   varchar(50)                    DEFAULT NULL COMMENT '创建时间',
   data_id              varchar(50)                    DEFAULT NULL COMMENT '数据id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云端同步到本地失败的数据';

/*==============================================================*/
/* sql : 添加权限管理  */
/*==============================================================*/
INSERT INTO `dhlk_basic_menu` VALUES (78, 'wisdomfloodlight', '智慧照明', '/wisdomfloodlight', 0, 0, 'wisdomfloodlight', 0);
INSERT INTO `dhlk_basic_menu` VALUES (79, 'area/findList', '施工区域', 'area/findList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (80, 'area/insert', '添加施工区域', 'area/save', 0, 79, 'area:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (81, 'area/delete', '删除施工区域', 'area/delete', 0, 79, 'area:delete', 1);

INSERT INTO `dhlk_basic_menu` VALUES (82, 'computer/findList', '照明一体机', 'computer/findList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (83, 'computer/insert', '添加', 'computer/save', 0, 82, 'computer:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (84, 'computer/update', '编辑', 'computer/update', 0, 82, 'computer:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (85, 'computer/delete', '删除', 'computer/delete', 0, 82, 'computer:delete', 1);
INSERT INTO `dhlk_basic_menu` VALUES (86, 'computer/agent', '一体机代理', 'computer/agent', 0, 82, 'dhlk:view', 1);

INSERT INTO `dhlk_basic_menu` VALUES (87, 'switch/findList', '灯控分组', 'switch/findList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (88, 'switch/insert', '添加', 'switch/save', 0, 87, 'switch:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (89, 'switch/update', '编辑', 'switch/update', 0, 87, 'switch:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (90, 'switch/delete', '删除', 'switch/delete', 0, 87, 'switch:delete', 1);

INSERT INTO `dhlk_basic_menu` VALUES (91, 'led/findAreasByLed', '照明设置', 'led/findAreasByLed', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (92, 'led/saveSwitchBoundLed', '绑定', 'led/saveSwitchBoundLed', 0, 91, 'led:saveSwitchBoundLed', 1);
INSERT INTO `dhlk_basic_menu` VALUES (93, 'led/openOrCloseLed', '开/关灯', 'led/openOrCloseLed', 0, 91, 'led:openOrCloseLed', 1);
INSERT INTO `dhlk_basic_menu` VALUES (94, 'led/setLedBrightness', '预设亮度', 'led/setLedBrightness', 0, 91, 'led:setLedBrightness', 1);

INSERT INTO `dhlk_basic_menu` VALUES (95, 'scheduler/findTaskSchedulerList', '定时控制', 'scheduler/findTaskSchedulerList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (96, 'scheduler/insert', '添加任务', 'scheduler/insert', 0, 95, 'scheduler:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (97, 'scheduler/update', '编辑', 'scheduler/update', 0, 95, 'scheduler:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (98, 'scheduler/delete', '删除', 'scheduler/delete', 0, 95, 'scheduler:delete', 1);
INSERT INTO `dhlk_basic_menu` VALUES (99, 'scheduler/startTask', '开启定时任务', 'scheduler/startTask', 0, 95, 'scheduler:startTask', 1);
INSERT INTO `dhlk_basic_menu` VALUES (100, 'scheduler/stopTask', '停止定时任务', 'scheduler/stopTask', 0, 95, 'scheduler:stopTask', 1);
INSERT INTO `dhlk_basic_menu` VALUES (101, 'scheduler/selectTaskSchedulerLogList', '执行结果', 'scheduler/selectTaskSchedulerLogList', 0, 95, 'dhlk:view', 1);

INSERT INTO `dhlk_basic_menu` VALUES (102, 'inspectionReport/findLampList', '验收报告', 'inspectionReport/findLampList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (103, 'inspectionReport/executeInspection', '执行验收测试', 'inspectionReport/executeInspection', 0, 102, 'inspectionReport:executeInspection', 1);

INSERT INTO `dhlk_basic_menu` VALUES (104, 'ledPowerStatistics/findList', '能源统计', 'ledPowerStatistics/findList', 0, 78, 'dhlk:view', 0);

INSERT INTO `dhlk_basic_menu` VALUES (105, 'question/findList', '在线售后', 'question/findList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (106, 'question/insert', '新增问题', 'question/insert', 0, 105, 'question:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (107, 'question/findQuestionDetail', '详情', 'question/findQuestionDetail', 0, 105, 'question:findQuestionDetail', 1);
INSERT INTO `dhlk_basic_menu` VALUES (108, 'question/delete', '删除', 'question/delete', 0, 105, 'question:delete', 1);

INSERT INTO `dhlk_basic_menu` VALUES (109, 'faultmessage', '故障信息', 'faultmessage', 0, 78, 'dhlk:view', 0);

INSERT INTO `dhlk_basic_menu` VALUES (110, 'originalPower/findList', '系统配置', 'originalPower/findList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (111, 'originalPower/insert', '保存', 'originalPower/save', 0, 110, 'originalPower:save', 1);

INSERT INTO `dhlk_basic_menu` VALUES (112, 'construction/findList', '施工信息', 'construction/findList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (113, 'construction/insert', '添加施工信息', 'construction/insert', 0, 112, 'construction:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (114, 'construction/update', '编辑', 'construction/update', 0, 112, 'construction:save', 1);
INSERT INTO `dhlk_basic_menu` VALUES (115, 'construction/delete', '删除', 'construction/delete', 0, 112, 'construction:delete', 1);

INSERT INTO `dhlk_basic_menu` VALUES (116, 'statistics', '统计汇总', 'statistics', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (117, 'companiesList', '企业列表', 'companiesList', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (118, 'faultCode', '故障代码', 'faultCode', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (119, 'firmwareVersion', '固件版本', 'firmwareVersion', 0, 78, 'dhlk:view', 0);


INSERT INTO `dhlk_basic_menu` VALUES (120, 'led/presetBrightness', '亮度设置', 'led/presetBrightness', 0, 91, 'led:presetBrightness', 1);
INSERT INTO `dhlk_basic_menu` VALUES (121, 'led/firmwarePpdate', '固件升级', 'led/firmwarePpdate', 0, 91, 'led:firmwarePpdate', 1);
INSERT INTO `dhlk_basic_menu` VALUES (122, 'led/setLight', '光感设置', 'led/setLight', 0, 91, 'led:setLight', 1);
INSERT INTO `dhlk_basic_menu` VALUES (123, 'led/setPeople', '人感控制', 'led/setPeople', 0, 91, 'led:setPeople', 1);
INSERT INTO `dhlk_basic_menu` VALUES (124, 'led/setwifi', '网络配置', 'led/setwifi', 0, 91, 'led:setwifi', 1);

INSERT INTO `dhlk_basic_menu` VALUES (125, 'peopleFeel/findList', '人感统计', 'peopleFeel/findList', 0, 78, 'dhlk:view', 0);

INSERT INTO `dhlk_basic_menu` VALUES (126, 'graphic/statistics', '指标总览', 'graphic/statistics', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (127, 'led/log', '日志', 'led/log', 0, 78, 'dhlk:view', 0);
INSERT INTO `dhlk_basic_menu` VALUES (128, 'area/edit', '修改施工区域', 'area/edit', 0, 79, 'area:edit', 1);
INSERT INTO `dhlk_basic_menu` VALUES (129, 'led/wifiBand', '射频范围', 'led/wifiBand', 0, 91, 'led:wifiBand', 1);



INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (53, 78, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (54, 79, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (55, 80, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (56, 81, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (57, 82, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (58, 83, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (59, 84, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (60, 85, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (61, 86, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (62, 87, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (63, 88, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (64, 89, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (65, 90, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (66, 91, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (67, 92, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (68, 93, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (69, 94, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (70, 95, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (71, 96, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (72, 97, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (73, 98, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (74, 99, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (75, 100, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (76, 101, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (77, 102, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (78, 103, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (79, 104, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (80, 105, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (81, 106, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (82, 107, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (83, 108, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (84, 109, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (85, 110, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (86, 111, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (87, 112, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (88, 113, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (89, 114, 1);

INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (90, 115, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (91, 116, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (92, 117, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (93, 118, 1);

INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (94, 119, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (95, 120, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (96, 121, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (97, 122, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (98, 123, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (99, 124, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (100, 125, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (101, 126, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (102, 127, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (103, 128, 1);
INSERT INTO `dhlk_basic_permissions`(`id`, `menu_id`, `role_id`) VALUES (104, 129, 1);
-- 故障代码
insert into dhlk_light_fault_code(code,name,content) values('10001','LocalNetwork_Exception','本地网络异常');
insert into dhlk_light_fault_code(code,name,content) values('10002','PublicNetwork_Exception','公网网络异常');
insert into dhlk_light_fault_code(code,name,content) values('10003','LocalMQTT_NO_Start','本地MQTT没有启动');
insert into dhlk_light_fault_code(code,name,content) values('10004','Publish_News_ToLocalMQTT_Exception','下发消息到本地的MQTT异常');
insert into dhlk_light_fault_code(code,name,content) values('10005','Subscription_News_FromLocalMQTT_Exception','从本地的MQTT订阅消息异常');
insert into dhlk_light_fault_code(code,name,content) values('10006','Light_Non-Existent','灯不存在');
insert into dhlk_light_fault_code(code,name,content) values('10007','Switch_Non_Existent','智能开关不存在');
insert into dhlk_light_fault_code(code,name,content) values('10008','LocalServer_Non_Existent','本地化一体机异常或者不存在');
insert into dhlk_light_fault_code(code,name,content) values('10009','CloudMQTT_NO_Start','云端MQTT未启动');
insert into dhlk_light_fault_code(code,name,content) values('10010','Subscription_News_FromCloudMQTT_Exception','从云端MQTT订阅消息异常');
insert into dhlk_light_fault_code(code,name,content) values('10011','Publish_News_ToCloudMQTT_Exception','推送消息到云端MQTT异常');
insert into dhlk_light_fault_code(code,name,content) values('10012','LocalConfig_Exception','本地化配置异常');
insert into dhlk_light_fault_code(code,name,content) values('10013','PullConfig_ToLocal_Exception','云化配置到本地异常');

