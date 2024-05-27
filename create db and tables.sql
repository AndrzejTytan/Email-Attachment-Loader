create database if not exists items character set utf8mb4 collate utf8mb4_unicode_ci;

use items;

create table item_details
(
    id                   int unsigned not null primary key auto_increment,
    identifier_code      varchar(12)  not null unique,
    name                 varchar(255) not null,
    pricing_currency_iso char(3)      not null
);

create table time_series_suppliers
(
    id                                int unsigned     not null primary key auto_increment,
    email                             varchar(255)     not null unique,
    file_column_index_identifier_code tinyint unsigned not null,
    file_column_index_price_date      tinyint unsigned not null,
    file_column_index_price_value     tinyint unsigned not null
);

create table processed_time_series_emails
(
    id                      int unsigned not null primary key auto_increment,
    time_series_supplier_id int unsigned not null,
    email_receieved         datetime     not null,
    email_processed         datetime     not null,
    foreign key (time_series_supplier_id) references time_series_suppliers (id)
);

create table item_time_series
(
    id                             int unsigned   not null primary key auto_increment,
    item_detail_id                 int unsigned not null,
    processed_time_series_email_id int unsigned not null,
    price_value                    decimal(10, 2) not null,
    price_date                     date           not null,
    foreign key (item_detail_id) references item_details (id),
    foreign key (processed_time_series_email_id) references processed_time_series_emails (id)
);



