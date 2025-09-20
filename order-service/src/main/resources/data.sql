-- Insert into Orders (10 records)
INSERT INTO orders (order_status, total_price) VALUES
('PENDING', 1200.50),
('CONFIRMED', 850.00),
('CONFIRMED', 4500.75),
('CANCELLED', 999.99),
('PENDING', 1750.25),
('CONFIRMED', 620.40),
('CONFIRMED', 3100.10),
('PENDING', 2222.22),
('CANCELLED', 1440.00),
('CONFIRMED', 5050.55);


INSERT INTO order_item (product_id, quantity, order_id) VALUES
(1, 2, 1),
(2, 1, 1),
(3, 3, 2),
(4, 1, 2),
(5, 5, 3),
(6, 2, 4),
(7, 4, 5),
(8, 1, 5),
(9, 2, 6),
(10, 3, 7);
