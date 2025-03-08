INSERT INTO app_user (username, email, password_hash, rol) VALUES
('marcus', 'marcus@admin.com', 'password_hash_1', 'admin'),
('john', 'johndoe@example.com', 'password_hash_2', 'user');

INSERT INTO product (product_name) VALUES
('bike TN'),
('bike MB');

INSERT INTO product_part (part_option, is_available, base_price, category) VALUES
('Full-suspension', true, 130, 'frame_type'),
('diamond', true, 100, 'frame_type'),
('Step-through', true, 80, 'frame_type'),
('Matte', true, 35, 'frame_finish'),
('Shiny', true, 15, 'frame_finish'),
('Road wheels', true, 15, 'wheel_type'),
('Mountain wheels', true, 15, 'wheel_type'),
('Fat bike wheels', true, 15, 'wheel_type'),
('Red', true, 15, 'rim_color'),
('Black', true, 15, 'rim_color'),
('Blue', true, 15, 'rim_color'),
('Single-speed chain', true, 15, 'chain_type'),
('8-speed chain', true, 15, 'chain_type');

INSERT INTO product_part_conditions (part_id, dependant_part_id, price_adjustment, is_restriction) VALUES 
(7,2, 0, true),
(7,3, 0, true),
(8,10, 0, true),
(4,1, 15, false);

