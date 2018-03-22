set foreign_key_checks=0;
# 管理员部门
drop table if exists tbl_department;
create table tbl_department (
  id    integer unsigned not null auto_increment,
  name   varchar(36) default null, 
  remark   varchar(128) default null,
  primary key (id)
) engine=innodb default charset=utf8;

# 管理员账号
drop table if exists tbl_manager;
create table tbl_manager (
  id   integer unsigned not null auto_increment,
  username      varchar(32) not null,
  password      char(40) not null,
  department_id integer unsigned  default 0,
  permission      varchar(1024) default null,
  available       tinyint(1) default 0,
  primary key(id)
) engine=innodb default charset=utf8;

create unique index unique_account on tbl_manager (username);
delete from tbl_manager;
insert into tbl_manager (username,password,permission) values ('webmaster','16754d4ce97f684583549c16441ed18743c08c64',',z101,z1,z2,z3,z4,z5,z6');

# token
drop table if exists tbl_token;
create table tbl_token (
  id   integer unsigned not null auto_increment,
  token_id   varchar(36) default null, 
  user_id  integer unsigned default 0,
  device_id   varchar(32) default null, -- device unique id
  device_name   varchar(128) default null, -- device model
  os            varchar(10) default null, -- android or ios
  app_instance_id varchar(32) default null,
  app_version     varchar(16) default null,
  remote_addr   varchar(15) default null,
  x_forward_addr  varchar(15) default null,
  login_time    datetime default null,
  primary key (id)
) engine=innodb default charset=utf8;

# 
drop table if exists tbl_user;
create table tbl_user (
    id            integer unsigned not null auto_increment,
    username     varchar(32) default null,
    password     varchar(40) default null,
    
    email         varchar(64) default null,
    mobile        varchar(24) default null,
    
    nickname      varchar(16) default null,
    avatar_url    varchar(256) default null,
    gender        tinyint unsigned default 0,
    
    -- type          tinyint unsigned default 0, -- 0: 普通会员  1：代理商  2：合作商
    remote_addr     varchar(15) default null,
    x_forward_addr  varchar(15) default null,
    add_time        datetime default null,
    primary key(id)
) engine=innodb auto_increment=10001 default charset=utf8;


# 企业账号
drop table if exists tbl_company;
create table tbl_company (
    id            integer unsigned not null auto_increment,

    email         varchar(64) default null,
    mobile        varchar(24) default null,
    
    password     varchar(40) default null,
    
    name          varchar(16) default null,
    logo_url      varchar(256) default null,
    address       varchar(128) default null,
    
    -- type          tinyint unsigned default 0, -- 0: 普通会员  1：代理商  2：合作商
    remote_addr     varchar(15) default null,
    x_forward_addr  varchar(15) default null,
    is_verified     tinyint unsigned default 0,
    add_time        datetime default null,
    primary key(id)
) engine=innodb auto_increment=10001 default charset=utf8;


# 网关设备
drop table if exists tbl_gateway;
create table tbl_gateway (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    password      varchar(20) default null,
    version       varchar(5) default null, 
    snid          varchar(8) default null,
    device_count  integer unsigned default 0,
    group_count   integer unsigned default 0,
    job_count     integer unsigned default 0,
    scene_count   integer unsigned default 0,
    task_count    integer unsigned default 0,
    update_time   datetime default null,
    add_time      datetime default null,
    data        varchar(1024),
    primary key(id)
) engine=innodb default charset=utf8;

truncate table tbl_gateway;
insert into tbl_gateway (id, gateway_id, password) values (1, 10000,'1234');
insert into tbl_gateway (id, gateway_id, password) values (2, 36381,'qld9');

#   
drop table if exists tbl_report;
create table tbl_report (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    fd            integer default 0,
    sn            integer default 0, -- api 接口编号
    data          varchar(4096) default null,
    add_time      datetime default null,
    primary key(id)
) engine=innodb default charset=utf8;

set foreign_key_checks=1;

# 设备
drop table if exists tbl_device;
create table tbl_device (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    name          varchar(32) default null,
    short_addr    char(4) default null,
    end_point     char(2) default null,
    profile_id    char(4) default null,
    device_id     char(4) default null,
    switch_status   tinyint unsigned default 0,
    online        tinyint unsigned default 0,
    ieee          char(16) default null,
    sn            varchar(32) default null,
    zone_type     char(4) default null,
    battery_power char(4) default null,
    update_time   datetime default null,
    add_time      datetime default null,
    data          varchar(2048),
    primary key(id)
) engine=innodb default charset=utf8;

# 企业绑定网关
drop table if exists tbl_bind_gateway;
create table tbl_bind_gateway (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    company_id    integer unsigned default 0,
    add_time      datetime default null,
    primary key(id)
) engine=innodb default charset=utf8;

-- 以下数据表需要完善 at 2017-12-28

drop table if exists tbl_group;
create table tbl_group (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    name          varchar(32) default null,
    
    update_time   datetime default null,
    add_time      datetime default null,
    data          varchar(2048),
    primary key(id)
) engine=innodb default charset=utf8;

drop table if exists tbl_scene;
create table tbl_scene (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    name          varchar(32) default null,
    
    update_time   datetime default null,
    add_time      datetime default null,
    data          varchar(2048),
    primary key(id)
) engine=innodb default charset=utf8;

drop table if exists tbl_job;
create table tbl_job (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    name          varchar(32) default null,
    
    update_time   datetime default null,
    add_time      datetime default null,
    data          varchar(2048),
    primary key(id)
) engine=innodb default charset=utf8;

drop table if exists tbl_task;
create table tbl_task (
    id            integer unsigned not null auto_increment,
    gateway_id    integer unsigned default 0,
    name          varchar(32) default null,
    
    update_time   datetime default null,
    add_time      datetime default null,
    data          varchar(2048),
    primary key(id)
) engine=innodb default charset=utf8;