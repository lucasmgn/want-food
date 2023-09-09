set foreign_key_checks = 0;

delete from city;
delete from kitchen;
delete from state;
delete from form_payment;
delete from group_tb;
delete from group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_form_payment;
delete from user_tb;
delete from user_group;
delete from restaurant_user_responsible;
delete from order_tb;
delete from item_order;
delete from photo_product;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table form_payment auto_increment = 1;
alter table group_tb auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user_tb auto_increment = 1;
alter table order_tb auto_increment = 1;
alter table item_order auto_increment = 1;

insert into kitchen (id, name) values (1, 'Tailandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into kitchen (id, name) values (3, 'Argentina');
insert into kitchen (id, name) values (4, 'Brasileira');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, rate_shipping, kitchen_id, date_register, date_update, active, open, address_city_id, address_cep, address_logradouro, address_number, address_neighborhood) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurant (id, name, rate_shipping, kitchen_id, date_register, date_update, active, open) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, rate_shipping, kitchen_id, date_register, date_update, active, open) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, rate_shipping, kitchen_id, date_register, date_update, active, open) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, rate_shipping, kitchen_id, date_register, date_update, active, open) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, rate_shipping, kitchen_id, date_register, date_update, active, open) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into form_payment (id, description, date_update) values (1, 'Cartão de crédito', utc_timestamp);
insert into form_payment (id, description, date_update) values (2, 'Cartão de débito', utc_timestamp);
insert into form_payment (id, description, date_update) values (3, 'Dinheiro', utc_timestamp);

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar kitchens');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar kitchens');

insert into restaurant_form_payment (restaurant_id, form_payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1);
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into group_tb (id, name) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');
insert into group_permission (group_id, permission_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into user_tb (id, name, email, password, data_register) values
(1, 'Takeo Hide', 'franciscotakeohidefarias@gmail.com', '123', utc_timestamp),
(2, 'Rian Ramos', 'rianramosnacimento2020@outlook.com', '123', utc_timestamp),
(3, 'Lucas Magno', 'lucasmagno172@gmail.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com.br', '123', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp),
(6, 'Débora Mendonça', 'email.teste.aw+debora@gmail.com', '123', utc_timestamp),
(7, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);

insert into user_group (user_id, group_id) values (4, 1), (5, 2), (6,4), (2, 2), (3,3), (4,4);

insert into restaurant_user_responsible (restaurant_id, user_id) values (1, 5), (3, 5);

insert into order_tb (id, code, restaurant_id, user_client_id, form_payment_id, address_city_id, address_cep,
    address_logradouro, address_number, address_complement, address_neighborhood,
    status, date_creation, amount, rate_shipping, subtotal)
values (1, '9c612bbb-6d28-42f2-9442-f26c3d70311b', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_order (id, order_id, product_id, amount, unit_price, price_total, observation)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_order (id, order_id, product_id, amount, unit_price, price_total, observation)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

insert into order_tb (id, code, restaurant_id, user_client_id, form_payment_id, address_city_id, address_cep,
        address_logradouro, address_number, address_complement, address_neighborhood,
        status, date_creation, amount, rate_shipping, subtotal)
values (2, '7022aead-4849-43d0-98aa-c6475e2783f5', 4, 2, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_order (id, order_id, product_id, amount, unit_price, price_total, observation)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into order_tb (id, code, restaurant_id, user_client_id, form_payment_id, address_city_id, address_cep,
                    address_logradouro, address_number, address_complement, address_neighborhood,
	                status, date_creation, date_confirmation, delivery_date, amount, rate_shipping, subtotal)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 3, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'CRIADO', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into item_order (id, order_id, product_id, amount, unit_price, price_total, observation)
values (4, 3, 2, 1, 110, 110, null);

insert into order_tb (id, code, restaurant_id, user_client_id, form_payment_id, address_city_id, address_cep,
                    address_logradouro, address_number, address_complement, address_neighborhood,
	                status, date_creation, date_confirmation, delivery_date, amount, rate_shipping, subtotal)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'ENTREGUE', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into item_order (id, order_id, product_id, amount, unit_price, price_total, observation)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into order_tb (id, code, restaurant_id, user_client_id, form_payment_id, address_city_id, address_cep,
                    address_logradouro, address_number, address_complement, address_neighborhood,
	                status, date_creation, date_confirmation, delivery_date, amount, rate_shipping, subtotal)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2019-11-02 21:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2);

insert into item_order (id, order_id, product_id, amount, unit_price, price_total, observation)
values (6, 5, 3, 1, 87.2, 87.2, null);
