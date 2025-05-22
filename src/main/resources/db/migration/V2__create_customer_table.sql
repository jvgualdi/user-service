CREATE TABLE IF NOT EXISTS customer(
    id BIGSERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    phone_number varchar(20) NOT NULL,
    street varchar(100) NOT NULL,
    city varchar(50) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    state varchar(50) NOT NULL,
    country varchar(50) NOT NULL,
    zip_code varchar(20) NOT NULL,
    number int NOT NULL DEFAULT 0,
    complement varchar(100)
);