--orders table
INSERT INTO orders (order_status, total_price) VALUES
('PENDING', 1200.00),
('CONFIRMED', 875.50),
('SHIPPED', 1523.75),
('DELIVERED', 999.99),
('CANCELLED', 450.00);

--order_item table :
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(101, 2, 1),
(102, 1, 1),
(103, 5, 1),
(104, 3, 2),
(105, 2, 2),
(106, 1, 2),
(107, 4, 3),
(108, 2, 3),
(109, 3, 3),
(110, 5, 3),
(101, 1, 4),
(102, 2, 4),
(103, 3, 4),
(104, 4, 4),
(105, 5, 5),
(106, 3, 5),
(107, 2, 5),
(108, 1, 5),
(109, 2, 5),
(110, 3, 5);


