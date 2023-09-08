alter table order_tb add code varchar(36) not null after id;
update order_tb set code = uuid();
alter table order_tb add constraint uk_order_code unique (code);