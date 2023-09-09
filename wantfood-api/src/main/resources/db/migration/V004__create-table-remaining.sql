create table form_payment (
	id bigint not null auto_increment,
	description varchar(60) not null,
	primary key (id)
 ) engine=InnoDB;
 
create table group_tb (
	id bigint not null auto_increment,
	name varchar(60) not null,
	primary key (id)
 ) engine=InnoDB;

create table group_permission (
	group_id bigint not null,
	permission_id bigint not null,
    primary key (group_id, permission_id)
 ) engine=InnoDB;
 
create table permission (
	id bigint not null auto_increment,
	description varchar(80) not null,
	name varchar(100) not null,
	primary key (id)
 ) engine=InnoDB;
 
create table product (
	id bigint not null auto_increment,
	active tinyint(1) not null,
	description text not null,
	name varchar(80) not null,
	price decimal(19,2) not null,
	restaurant_id bigint not null,
	primary key (id)
 ) engine=InnoDB;
 
create table restaurant (
	id bigint not null auto_increment,
	date_update datetime not null,
	date_register datetime not null,
	address_neighborhood varchar(60),
	address_cep varchar(9),
	address_complement varchar(60),
	address_logradouro varchar(100),
	address_number varchar(20),
	name varchar(80) not null,
	rate_shipping decimal(19,2) not null,
	kitchen_id bigint not null,
	address_city_id bigint,
	primary key (id)
 ) engine=InnoDB;
 
create table restaurant_form_payment (
	restaurant_id bigint not null,
	form_payment_id bigint not null,
    primary key(restaurant_id, form_payment_id)
 ) engine=InnoDB;
 
create table user_tb (
	id bigint not null auto_increment,
	data_register datetime not null,
	email varchar(225) not null,
	name varchar(80) not null,
	password varchar(255) not null,
	primary key (id)
 ) engine=InnoDB;
 
create table user_group (
	user_id bigint not null,
    group_id bigint not null,
    primary key (user_id, group_id)
) engine=InnoDB;

alter table city add constraint FKkworrwk40xj58kevvh3evi500 foreign key (state_id) references state (id);

alter table group_permission add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permission_id) references permission (id);

alter table group_permission add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (group_id) references group_tb (id);

alter table product add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint FK76grk4roudh659skcgbnanthi foreign key (kitchen_id) references kitchen (id);

alter table restaurant add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (address_city_id) references city (id);

alter table restaurant_form_payment add constraint FK7aln770m80358y4olr03hyhh2 foreign key (form_payment_id) references form_payment (id);

alter table restaurant_form_payment add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurant_id) references restaurant (id);

alter table user_group add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (group_id) references group_tb (id);

alter table user_group add constraint FKdofo9es0esuiahyw2q467crxw foreign key (user_id) references user_tb (id);

