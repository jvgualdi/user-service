CREATE TABLE IF NOT EXISTS employee(
    id bigserial PRIMARY KEY,
    full_name varchar(50) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);
