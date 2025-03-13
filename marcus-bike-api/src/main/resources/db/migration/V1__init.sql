CREATE TYPE user_role AS ENUM ('user', 'admin');
CREATE TYPE product_part_category AS ENUM ('frame_type', 'frame_finish', 'wheel_type', 'rim_colour', 'chain_type');


create table app_user (
	id SERIAL PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_role user_role);

create table app_order (
	id SERIAL PRIMARY KEY,
    app_user_id INTEGER NOT NULL,
    final_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_order_user FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
    );
   
create table product(
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

create table order_line(
    id SERIAL PRIMARY KEY,
    app_order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_line_order FOREIGN KEY (app_order_id) REFERENCES app_order(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_line_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
    );

create table product_part(
    id SERIAL PRIMARY KEY,
    part_option VARCHAR(100) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    base_price DECIMAL(10,2) NOT NULL,
    category product_part_category NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE product_product_part (
    product_id INTEGER NOT NULL,
    product_part_id INTEGER NOT NULL,
    PRIMARY KEY (product_id, product_part_id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (product_part_id) REFERENCES product_part(id) ON DELETE CASCADE
);


create table product_part_condition(
    part_id INTEGER NOT NULL,
    dependant_part_id INTEGER NOT NULL,
    price_adjustment DECIMAL(10,2) DEFAULT 0,
    is_restriction BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (part_id, dependant_part_id),
    FOREIGN KEY (part_id) REFERENCES product_part(id) ON DELETE CASCADE,
    FOREIGN KEY (dependant_part_id) REFERENCES product_part(id) ON DELETE CASCADE,
    CONSTRAINT check_incompatibilities CHECK(part_id<>dependant_part_id)
);

CREATE TABLE order_line_product_part(
    order_line_id INTEGER NOT NULL,
    product_part_id INTEGER NOT NULL,
    final_price DECIMAL (10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (order_line_id, product_part_id),
    FOREIGN KEY (order_line_id) REFERENCES order_line(id) ON DELETE CASCADE,
    FOREIGN KEY (product_part_id) REFERENCES product_part(id) ON DELETE CASCADE
);