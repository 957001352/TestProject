-- CREATE DATABASE `dhlk_light_factory` CHARACTER SET utf8 COLLATE utf8_general_ci;
/*==============================================================*/
/* Table: 用户表                                                 */
/*==============================================================*/
drop table if exists dhlk_basic_user;
create table dhlk_basic_user
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(50)                    DEFAULT NULL COMMENT '姓名',
   login_name           varchar(50)                    DEFAULT NULL COMMENT '用户名',
   password             varchar(100)                   DEFAULT NULL COMMENT '密码',
   phone                varchar(50)                    DEFAULT NULL COMMENT '联系电话',
   email                varchar(50)                    DEFAULT NULL COMMENT '邮箱',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 1禁用',
   org_id               varchar(100)                   DEFAULT NULL COMMENT '所属机构集合 用逗号分割',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   is_admin             integer                        DEFAULT '1' COMMENT '是否超级用户 0超级管理员，2租户管理员，1普通用户',
   tenant_id            integer                        DEFAULT NULL COMMENT '租户',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

/*==============================================================*/
/* Table: 区域管理表                                                 */
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
/* Table: deng表                                                 */
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
/* Table: 租户管理                                       */
/*==============================================================*/
drop table if exists dhlk_basic_tenant;
create table  dhlk_basic_tenant (
  id              integer              NOT NULL AUTO_INCREMENT,
  code            varchar(100)         DEFAULT NULL COMMENT '租户编码',
  name            varchar(50)          DEFAULT NULL COMMENT '租户名称',
  telephone       varchar(20)          DEFAULT NULL COMMENT '企业联系电话',
  adress          varchar(100)         DEFAULT NULL COMMENT '联系地址',
  company_email   varchar(50)          DEFAULT NULL COMMENT '企业邮箱',
  linkman         varchar(20)          DEFAULT NULL COMMENT '联系人',
  phone           varchar(15)          DEFAULT NULL COMMENT '联系电话',
  email           varchar(50)          DEFAULT NULL COMMENT '邮箱',
  start_time      timestamp            DEFAULT NOW() COMMENT '有效期起',
  end_time        timestamp            DEFAULT NOW() COMMENT '有效期止',
  create_time     timestamp            DEFAULT NOW() COMMENT '创建时间',
  devices         integer              DEFAULT NULL COMMENT '最大设备数',
  tb_loginname    varchar(50)          DEFAULT NULL COMMENT 'tb登录账号',
  tb_password     varchar(50)          DEFAULT NULL COMMENT 'tb登录密码',
  tb_id           varchar(100)         DEFAULT NULL COMMENT '租户id',
  is_system       integer DEFAULT '1'  COMMENT '是否系统默认租户  0是 1否',
  file_id         varchar(100)         DEFAULT NULL COMMENT '附件id',
  status          integer DEFAULT '0'  COMMENT '状态 0正常 2 删除',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户管理';

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
/* Table: 灯能耗                             */
/*==============================================================*/
drop table if exists dhlk_light_led_power;
create table dhlk_light_led_power
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   led_sn               varchar(100)                   DEFAULT NULL COMMENT '灯sn',
   electric             decimal(12,4)                   DEFAULT NULL COMMENT '电流',
   voltage              decimal(12,4)                   DEFAULT NULL COMMENT '电压',
   power                decimal(12,4)                   DEFAULT NULL COMMENT '功率',
   create_time          timestamp                      DEFAULT NOW() COMMENT '统计时间',
   energy               decimal(12,4)                   DEFAULT NULL COMMENT '电能',
   brightness           integer                        DEFAULT NULL COMMENT '灯亮度',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯功率统计，10分钟统计一次';

/*==============================================================*/
/* Table: 每小时汇总后的灯能耗                             */
/*==============================================================*/
drop table if exists dhlk_light_led_power_summarizing;
create table dhlk_light_led_power_summarizing
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   led_sn               varchar(100)                   DEFAULT NULL COMMENT '灯sn',
   create_time          timestamp                      DEFAULT NOW() COMMENT '统计时间',
   energy               decimal(12,4)                  DEFAULT NULL COMMENT '电能',
   isSend               char(2)                        DEFAULT '0' COMMENT '是否往云端发送成功，0失败 1成功',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯能耗，一小时汇总一次';

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
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯操作记录';
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
/* Table: 本地人感数据  */
/*==============================================================*/
drop table if exists dhlk_light_local_people_feel_statistics;
create table dhlk_light_local_people_feel_statistics
(
   id                   integer                      NOT NULL AUTO_INCREMENT,
   led_sn 		        varchar(50)                  DEFAULT NULL COMMENT '照明设备',
   status               integer                      DEFAULT NULL COMMENT '0无人 1有人',
   create_time          timestamp                    NULL COMMENT '统计时间',
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='本地人感数据统计';


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
   primary key  (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业原照明功率维护';

/*==============================================================*/
/* Table: 本地等在线时长 */
/*==============================================================*/
drop table if exists dhlk_light_led_online;
create table dhlk_light_led_online
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   led_sn               varchar(100)                   DEFAULT NULL COMMENT '灯sn',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   online_time          integer                        DEFAULT NULL COMMENT '在线时长(分钟)',
   isSend               char(2)                        DEFAULT '0' COMMENT '是否往云端发送成功，0失败 1成功',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯在线统计，按小时统计灯在线多少分钟';