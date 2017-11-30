/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/11/8 16:17:44                           */
/*==============================================================*/


drop table if exists tl_client_func;

drop table if exists tl_client;

drop table if exists tl_microservice_func;

drop table if exists tl_microservice;

drop table if exists tl_secret;

/*==============================================================*/
/* Table: tl_client                                             */
/*==============================================================*/
create table tl_client
(
   client_id            varchar(50) not null comment '客户端ID',
   client_key1          varchar(255) not null comment '客户端登录key1',
   client_key2          varchar(255) comment '客户端登录key2；多个的目的是为了方便平滑的更新登录key',
   client_type          tinyint not null comment '客户端类型；99:超级管理员; 1:内部应用; 2:内部服务',
   client_desc          text comment '描述信息',
   client_status        	
                        tinyint not null comment '状态；1:有效; 0:无效',
   primary key (client_id)
);

alter table tl_client comment '客户端信息表';

/*==============================================================*/
/* Table: tl_microservice                                       */
/*==============================================================*/
create table tl_microservice
(
   service_id           int not null auto_increment comment '微服务ID',
   service_name         varchar(50) not null comment '微服务名',
   service_cname        varchar(100) not null comment '微服务中文名',
   service_desc         varchar(255) comment '微服务说明',
   service_status       tinyint not null comment '状态；1:有效; 0:无效',
   primary key (service_id)
);

alter table tl_microservice comment '微服务信息表';

/*==============================================================*/
/* Index: uq_microservice_name                                  */
/*==============================================================*/
create unique index uq_microservice_name on tl_microservice
(
   service_name
);

/*==============================================================*/
/* Table: tl_microservice_func                                  */
/*==============================================================*/
create table tl_microservice_func
(
   func_id              int not null auto_increment comment '功能ID',
   service_id           int not null comment '微服务ID',
   func_name            varchar(50) not null comment '功能名',
   func_cname           varchar(100) not null comment '功能中文名',
   func_desc            varchar(255) comment '功能描述',
   func_status          tinyint not null comment '状态；1:有效; 0:无效',
   primary key (func_id)
);

alter table tl_microservice_func comment '微服务功能表';

/*==============================================================*/
/* Index: uq_microservice_func1                                 */
/*==============================================================*/
create unique index uq_microservice_func1 on tl_microservice_func
(
   service_id,
   func_name
);

/*==============================================================*/
/* Table: tl_client_func                                        */
/*==============================================================*/
create table tl_client_func
(
   cf_id                int not null auto_increment comment '权限ID',
   client_id            varchar(50) not null comment '客户端ID',
   func_id              int not null comment '功能ID',
   primary key (cf_id)
);

alter table tl_client_func comment '客户端功能权限表';

/*==============================================================*/
/* Table: tl_secret                                             */
/*==============================================================*/
create table tl_secret
(
   secret_id            int not null auto_increment comment '私钥ID',
   secret               varchar(255) not null comment '私钥',
   status               tinyint not null comment '状态；1:有效; 0:无效',
   primary key (secret_id)
);

alter table tl_secret comment '私钥表';

alter table tl_client_func add constraint FK_Reference_1 foreign key (client_id)
      references tl_client (client_id) on delete restrict on update restrict;

alter table tl_client_func add constraint FK_Reference_2 foreign key (func_id)
      references tl_microservice_func (func_id) on delete restrict on update restrict;

alter table tl_microservice_func add constraint FK_Reference_3 foreign key (service_id)
      references tl_microservice (service_id) on delete restrict on update restrict;

