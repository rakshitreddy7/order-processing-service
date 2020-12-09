INSERT INTO order_details (order_id, created_date, status, sub_total, total, customer_id, shipping_address, shipping_zip_code, shipping_method) VALUES
  ('efd09dca-5668-4add-bf7a-2b6ca83cd09c', '2020-12-13T05:47:08.644' , 'ACTIVE', 50.0, 75.0, 123, 'Taylor St' ,'60657', 'SHIP_TO_HOME'),
  ('de0d595a-81e5-458a-8459-700e31f001e2', '2020-12-12T05:47:08.644' , 'ACTIVE', 70.0, 95.0, 123, 'Taylor St' ,'07032', 'SHIP_TO_HOME' ),
  ('59be5b72-484d-4101-8619-bf25b1373fe4', '2020-12-11T05:47:08.644' , 'SHIPPED', 85.0, 100.0, 123, 'Taylor St' , '60657', 'SHIP_TO_HOME'),
  ('926421bb-24aa-402a-aff0-f23a10bc1d10', '2020-12-12T05:47:08.644' , 'PROCESSING', 100.0, 120.0, 456, 'Lincoln St' , '07032', 'SHIP_TO_HOME'),
  ('7d5848a1-9bc4-48a9-84a4-01c7c528bdcf', '2020-12-12T05:47:08.644' ,' ACTIVE', 90.0, 110.0, 789, 'Lakeshore Dr' , '07032', 'SHIP_TO_HOME');

INSERT INTO item_quantity (order_id, item_id, quantity) VALUES
  ('efd09dca-5668-4add-bf7a-2b6ca83cd09c', 1, 2 ),
  ('efd09dca-5668-4add-bf7a-2b6ca83cd09c', 2, 1 ),
  ('de0d595a-81e5-458a-8459-700e31f001e2', 1, 1 ),
  ('59be5b72-484d-4101-8619-bf25b1373fe4', 1, 1 ),
  ('926421bb-24aa-402a-aff0-f23a10bc1d10', 2, 2),
  ('7d5848a1-9bc4-48a9-84a4-01c7c528bdcf', 3, 1);

INSERT INTO item_details (id, name) VALUES
  (1, 'flowers' ),
  (2,'vegetables'),
  (3, 'iphone 11');

