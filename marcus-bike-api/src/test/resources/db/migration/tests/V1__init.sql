CREATE TABLE app_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    user_role VARCHAR(10) NOT NULL CHECK (user_role IN ('USER', 'ADMIN'))
);

create table product_part(
    id SERIAL PRIMARY KEY,
    part_option VARCHAR(100) NOT NULL,
    stock DECIMAL NOT NULL DEFAULT 0,
    base_price DECIMAL(10,2) NOT NULL,
    category VARCHAR(20) NOT NULL CHECK (category IN ('FRAME_TYPE', 'FRAME_FINISH', 'WHEEL_TYPE', 'RIM_COLOUR', 'CHAIN_TYPE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    material VARCHAR(100) NOT NULL,
    image_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);