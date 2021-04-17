drop database if exists ltmusic;
create database if not exists ltmusic;


create table if not exists music(id int not null primary key auto_increment,
                          title varchar(50),
                          singer varchar(50),
                          upload_time varchar(50),
                          url varchar(1024),
                          uid int not null comment '个人用户ID',
                          create_time timestamp,
                          update_time timestamp);

create table if not exists mv(id int not null primary key auto_increment,
                          title varchar(50),
                          singer varchar(50),
                          upload_time varchar(50),
                          url varchar(1024),
                          uid int not null comment '个人用户ID');
create table if not exists admin(
id int not null primary key auto_increment,
username varchar(50),
password varchar(50),
age int,
sex varchar(10),
email varchar (100)
);

create table if not exists user(
id int not null primary key auto_increment,
username varchar(50),
password varchar(50),
age int,
sex varchar(10),
email varchar (100)
);

drop table if exists love;
create table if not exists love(
id int not null primary key auto_increment,
type varchar(20) not null,
user_id int not null,
music_id int,
mv_id int
);