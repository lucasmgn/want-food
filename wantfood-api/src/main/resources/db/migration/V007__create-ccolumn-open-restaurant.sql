alter table restaurant add open tinyint(1) not null;
update restaurant set open = false;