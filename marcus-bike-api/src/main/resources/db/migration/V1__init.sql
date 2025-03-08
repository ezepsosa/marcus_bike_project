CREATE TYPE user_rol AS ENUM ('user', 'admin');
CREATE TYPE product_part_category AS ENUM ('frame_type', 'frame_finish', 'wheel_type', 'rim_colour', 'chain_type');


create table app_user (
	id SERIAL PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol rol);

create table app_order (
	id SERIAL PRIMARY KEY,
    app_user_id INTEGER NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2),
    CONSTRAINT fk_app_order_user FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
    );
   
create table product(
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL
    );

create table order_line(
    id SERIAL PRIMARY KEY,
    app_order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order_line_order FOREIGN KEY (app_order_id) REFERENCES app_order(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_line_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
    );

create table product_part(
    id SERIAL PRIMARY KEY,
    part_option VARCHAR(100) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    base_price DECIMAL(10,2) NOT NULL,
    category category NOT NULL
    );

create table product_part_conditions(
    part_id INTEGER NOT NULL,
    dependant_part_id INTEGER NOT NULL,
    price_adjustment DECIMAL(10,2) DEFAULT 0,
    is_restriction BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (part_id, dependant_part_id),
    FOREIGN KEY (part_id) REFERENCES product_part(id) ON DELETE CASCADE,
    FOREIGN KEY (dependant_part_id) REFERENCES product_part(id) ON DELETE CASCADE,
    CONSTRAINT check_incompatibilities CHECK(part_id<>dependant_part_id)
);
