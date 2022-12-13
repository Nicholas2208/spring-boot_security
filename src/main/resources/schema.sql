create table if not exists roles (id bigint not null auto_increment, name varchar(10), primary key (id)) engine=InnoDB;

create table if not exists user_role (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;

create table if not exists users (id bigint not null auto_increment, email varchar(50), first_name varchar(100), last_name varchar(100), password varchar(100), primary key (id)) engine=InnoDB;

alter table users add constraint UK_585 unique (email);
