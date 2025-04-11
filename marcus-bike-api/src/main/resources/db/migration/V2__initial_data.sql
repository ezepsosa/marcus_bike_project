INSERT INTO app_user (username, email, password_hash, user_role) VALUES
('marcus', 'marcus@admin.com', '$2a$10$1m13lkQRaaXDsydETdfHQ.SupMIfe2wHSyGRQO2XrDrvacJonHA7C', 'ADMIN'),
('john', 'johndoe@example.com', '$2a$10$1m13lkQRaaXDsydETdfHQ.SupMIfe2wHSyGRQO2XrDrvacJonHA7C', 'USER');

INSERT INTO product (product_name, brand, category, material, image_url) VALUES
('Trek Domane AL 2', 'Trek', 'Ruta', 'Aluminio', 'Trek Domane AL 2.png'),
('Specialized Roubaix', 'Specialized', 'Ruta', 'Carbono', 'Specialized Roubaix.png'),
('Cannondale Synapse Carbon', 'Cannondale', 'Ruta', 'Carbono', 'Cannondale Synapse Carbon.png'),
('Trek Marlin 7', 'Trek', 'Montaña', 'Aluminio', 'Trek Marlin 7.png'),
('Specialized Rockhopper', 'Specialized', 'Montaña', 'Aluminio', 'Specialized Rockhopper.png');


INSERT INTO product_part (part_option, stock, base_price, category) VALUES
('Full-suspension', 30, 130, 'FRAME_TYPE'),
('Diamond', 20, 100, 'FRAME_TYPE'),
('Step-through', 10, 80, 'FRAME_TYPE'),
('Matte', 2, 35, 'FRAME_FINISH'),
('Shiny', 20, 15, 'FRAME_FINISH'),
('Road wheels', 23, 15, 'WHEEL_TYPE'),
('Mountain wheels', 22, 15, 'WHEEL_TYPE'),
('Fat bike wheels', 3, 15, 'WHEEL_TYPE'),
('Red', 2, 15, 'RIM_COLOUR'),
('Black', 10, 15, 'RIM_COLOUR'),
('Blue', 0, 15, 'RIM_COLOUR'),
('Single-speed chain', 0, 15, 'CHAIN_TYPE'),
('8-speed chain', 22, 15, 'CHAIN_TYPE');

