alter table form_payment add date_update datetime null;
update form_payment set date_update = utc_timestamp;
alter table form_payment modify date_update datetime not null;