create table restaurant_user_responsible (
    restaurant_id bigint not null,
    user_id bigint not null,
    
    primary key (restaurant_id, user_id)
) engine=InnoDB default charset=utf8;

alter table restaurant_user_responsible add constraint fk_restaurant_user_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant_user_responsible add constraint fk_restaurant_user_user_tb
foreign key (user_id) references user_tb (id);