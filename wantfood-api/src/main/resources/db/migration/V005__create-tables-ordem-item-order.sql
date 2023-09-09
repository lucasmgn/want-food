create table order_tb (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    rate_shipping decimal(10,2) not null,
    amount decimal(10,2) not null,

    restaurant_id bigint not null,
    user_client_id bigint not null,
    form_payment_id bigint not null,
    
    address_city_id bigint(20) not null,
    address_cep varchar(9) not null,
    address_logradouro varchar(100) not null,
    address_number varchar(20) not null,
    address_complement varchar(60) null,
    address_neighborhood varchar(60) not null,
    
    status varchar(10) not null,
    date_creation datetime not null,
    date_confirmation datetime null,
    date_cancellation datetime null,
    delivery_date datetime null,

    primary key (id),

    constraint fk_order_address_city foreign key (address_city_id) references city (id),
    constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_user_client foreign key (user_client_id) references user_tb (id),
    constraint fk_order_payment_form foreign key (form_payment_id) references form_payment (id)
) engine=InnoDB;

create table item_order (
    id bigint not null auto_increment,
    amount smallint(6) not null,
    unit_price decimal(10,2) not null,
    price_total decimal(10,2) not null,
    observation varchar(255) null,
    order_id bigint not null,
    product_id bigint not null,
    
    primary key (id),
    unique key uk_item_order_product (order_id, product_id),

    constraint fk_item_order_order foreign key (order_id) references order_tb (id),
    constraint fk_item_order_product foreign key (product_id) references product (id)
) engine=InnoDB;