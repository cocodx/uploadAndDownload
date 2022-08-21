create database if not exists `db_updown` default character set utf8mb4 collate utf8mb4_general_ci;
use db_updown;
SET FOREIGN_KEY_CHECKS=0;

drop table if exists `t_user`;
create table `t_user`(
    id bigint primary key auto_increment ,
    user_name varchar(100),
    password varchar(100)
)ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

drop table if exists `t_files`;
create table `t_files`(
    id bigint primary key auto_increment,
    old_file_name varchar(100),
    new_file_name varchar(100),
    ext varchar(100),
    path varchar(300),
    size varchar(200),
    type varchar(120),
    is_img varchar(8),
    down_count int(8),
    upload_time datetime
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;