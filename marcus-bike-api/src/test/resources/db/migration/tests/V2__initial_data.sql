INSERT INTO app_user (username, email, password_hash, user_role) VALUES 
('admin', 'admin@test.com', '$2a$10$1m13lkQRaaXDsydETdfHQ.SupMIfe2wHSyGRQO2XrDrvacJonHA7C', 'ADMIN');

INSERT INTO product (product_name, brand, category, material, image_url) VALUES
('Trek Domane AL 2', 'Trek', 'Ruta', 'Aluminio', 'Trek Domane AL 2.png'),
('Specialized Roubaix', 'Specialized', 'Ruta', 'Carbono', 'Specialized Roubaix.png'),
('Cannondale Synapse Carbon', 'Cannondale', 'Ruta', 'Carbono', 'Cannondale Synapse Carbon.png'),
('Trek Marlin 7', 'Trek', 'Montaña', 'Aluminio', 'Trek Marlin 7.png'),
('Specialized Rockhopper', 'Specialized', 'Montaña', 'Aluminio', 'Specialized Rockhopper.png');


INSERT INTO product_part (part_option, stock, base_price, category) VALUES
('Full-suspension', 30, 130, 'frame_type'),
('Diamond', 20, 100, 'frame_type'),
('Step-through', 10, 80, 'frame_type'),
('Matte', 2, 35, 'frame_finish'),
('Shiny', 20, 15, 'frame_finish'),
('Road wheels', 23, 15, 'wheel_type'),
('Mountain wheels', 22, 15, 'wheel_type'),
('Fat bike wheels', 3, 15, 'wheel_type'),
('Red', 2, 15, 'rim_colour'),
('Black', 10, 15, 'rim_colour'),
('Blue', 0, 15, 'rim_colour'),
('Single-speed chain', 0, 15, 'chain_type'),
('8-speed chain', 22, 15, 'chain_type');
