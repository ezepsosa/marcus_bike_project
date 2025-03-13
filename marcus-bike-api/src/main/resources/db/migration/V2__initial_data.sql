INSERT INTO app_user (username, email, password_hash, user_role) VALUES
('marcus', 'marcus@admin.com', 'password_hash_1', 'admin'),
('john', 'johndoe@example.com', 'password_hash_2', 'user');

INSERT INTO product (product_name) VALUES
('bike TN'),
('bike MB');

INSERT INTO product_part (part_option, is_available, base_price, category) VALUES
('Full-suspension', true, 130, 'frame_type'),
('Diamond', true, 100, 'frame_type'),
('Step-through', true, 80, 'frame_type'),
('Matte', true, 35, 'frame_finish'),
('Shiny', true, 15, 'frame_finish'),
('Road wheels', true, 15, 'wheel_type'),
('Mountain wheels', true, 15, 'wheel_type'),
('Fat bike wheels', true, 15, 'wheel_type'),
('Red', true, 15, 'rim_colour'),
('Black', true, 15, 'rim_colour'),
('Blue', true, 15, 'rim_colour'),
('Single-speed chain', true, 15, 'chain_type'),
('8-speed chain', true, 15, 'chain_type');

INSERT INTO product_product_part (product_id, product_part_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13);


INSERT INTO product_part_condition (part_id, dependant_part_id, price_adjustment, is_restriction) VALUES 
(7,2, 0, true),
(7,3, 0, true),
(8,10, 0, true),
(4,1, 15, false);

INSERT INTO app_order (app_user_id, final_price) VALUES
(1, 500),
(2, 450);

INSERT INTO order_line (app_order_id, product_id, quantity) VALUES
(1, 1, 1),
(1, 2, 2),
(2, 1, 1);

INSERT INTO order_line_product_part (order_line_id, product_part_id, final_price) VALUES
(1, 1, 130),
(1, 5, 15),
(1, 6, 15),
(2, 2, 100),
(2, 7, 15),
(3, 3, 80),
(3, 8, 15);
