use items;

insert into time_series_suppliers(email, file_column_index_identifier_code, file_column_index_price_date, file_column_index_price_value)
values('danwys.dev@gmail.com', 0, 1, 2),
      ('example1@ex.ex', 0, 1, 2),
      ('example2@ex.ex', 0, 1, 2),
      ('example3@ex.ex', 0, 1, 2),
      ('example4@ex.ex', 0, 1, 2),
      ('example5@ex.ex', 0, 1, 2);

insert into item_details(identifier_code, name, pricing_currency_iso)
values
    ('ID0000000000', 'My Item 1', 'USD'),
    ('ID0000000001', 'My Item 1', 'USD'),
    ('ID0000000002', 'My Item 1', 'PLN'),
    ('ID0000000003', 'My Item 1', 'USD'),
    ('ID0000000004', 'My Item 1', 'EUR'),
    ('ID0000000005', 'My Item 1', 'PLN');