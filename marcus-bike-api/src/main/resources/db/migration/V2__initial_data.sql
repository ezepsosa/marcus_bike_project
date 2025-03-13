INSERT INTO app_user (username, email, password_hash, user_role) VALUES
('marcus', 'marcus@admin.com', 'password_hash_1', 'admin'),
('john', 'johndoe@example.com', 'password_hash_2', 'user');

INSERT INTO product (product_name) VALUES
('bike TN'),
('bike MB');

INSERT INTO product_part (product_id, part_option, is_available, base_price, category) VALUES
(1, 'Full-suspension', true, 130, 'frame_type'),
(1, 'Diamond', true, 100, 'frame_type'),
(1, 'Step-through', true, 80, 'frame_type'),
(1, 'Matte', true, 35, 'frame_finish'),
(1, 'Shiny', true, 15, 'frame_finish'),
(1, 'Road wheels', true, 15, 'wheel_type'),
(1, 'Mountain wheels', true, 15, 'wheel_type'),
(1, 'Fat bike wheels', true, 15, 'wheel_type'),
(1, 'Red', true, 15, 'rim_colour'),
(1, 'Black', true, 15, 'rim_colour'),
(1, 'Blue', true, 15, 'rim_colour'),
(1, 'Single-speed chain', true, 15, 'chain_type'),
(1, '8-speed chain', true, 15, 'chain_type'),

(2, 'Full-suspension', true, 140, 'frame_type'),
(2, 'Diamond', true, 110, 'frame_type'),
(2, 'Step-through', true, 85, 'frame_type'),
(2, 'Matte', true, 40, 'frame_finish'),
(2, 'Shiny', true, 20, 'frame_finish'),
(2, 'Road wheels', true, 20, 'wheel_type'),
(2, 'Mountain wheels', true, 20, 'wheel_type'),
(2, 'Fat bike wheels', true, 20, 'wheel_type'),
(2, 'Red', true, 20, 'rim_colour'),
(2, 'Black', true, 20, 'rim_colour'),
(2, 'Blue', true, 20, 'rim_colour'),
(2, 'Single-speed chain', true, 20, 'chain_type'),
(2, '8-speed chain', true, 20, 'chain_type');

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
