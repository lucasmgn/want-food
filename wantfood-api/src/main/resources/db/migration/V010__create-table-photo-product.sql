create table photo_product(
	product_id bigint not null,
	name_file varchar(150) not null,
	description varchar(150),
	content_type varchar(80) not null,
	size_photo int not null,
	
	primary key (product_id),
	constraint fk_photo_product_product foreign key (product_id) references product (id)
	)engine=InnoDB;