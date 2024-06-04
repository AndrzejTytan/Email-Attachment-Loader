use
items;

insert into time_series_suppliers(email, file_column_index_identifier_code, file_column_index_price_date,
                                  file_column_index_price_value)
values ('danwys.dev@gmail.com', 0, 1, 2),
       ('example1@ex.ex', 0, 1, 2),
       ('example2@ex.ex', 0, 1, 2),
       ('example3@ex.ex', 0, 1, 2),
       ('example4@ex.ex', 0, 1, 2),
       ('example5@ex.ex', 0, 1, 2);

insert into item_details(identifier_code, name, pricing_currency_iso)
values ('ID0000000000', 'My Item 1', 'USD'),
       ('ID0000000001', 'My Item 1', 'USD'),
       ('ID0000000002', 'My Item 1', 'PLN'),
       ('ID0000000003', 'My Item 1', 'USD'),
       ('ID0000000004', 'My Item 1', 'EUR'),
       ('ID0000000005', 'My Item 1', 'PLN');

insert into processed_time_series_emails(time_series_supplier_id, email_receieved, email_processed)
values (1, '2024-06-01', '2024-06-01'),
       (1, '2024-06-02', '2024-06-02'),
       (1, '2024-06-03', '2024-06-03'),
       (1, '2024-06-04', '2024-06-04'),
       (1, '2024-06-05', '2024-06-05'),
       (1, '2024-06-06', '2024-06-06')
    insert
into item_time_series(item_detail_id, processed_time_series_email_id, price_value, price_date)
values
    (1, 1, 10.0, '2000-01-01'), (1, 1, 10.2, '2000-01-02'), (1, 1, 10.1, '2000-01-03'), (1, 1, 10.3, '2000-01-04'), (1, 1, 9.7, '2000-01-05'), (1, 1, 10.2, '2000-01-06'), (1, 1, 9.9, '2000-01-07')