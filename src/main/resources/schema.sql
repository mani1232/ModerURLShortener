CREATE TABLE IF NOT EXISTS users
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    display_name VARCHAR(255),
    username     VARCHAR(255),
    password     VARCHAR(255),
    token        VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS urls
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    title        VARCHAR(255),
    description  VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_at       TIMESTAMP             NULL,
    enabled      BOOLEAN   DEFAULT TRUE,
    click_count  BIGINT    DEFAULT 0,
    short_url    VARCHAR(10),
    full_url     VARCHAR(255)
);

CREATE TABLE users_urls
(
    user_id BIGINT NOT NULL REFERENCES users(id),
    url_id  BIGINT NOT NULL REFERENCES urls(id),
    PRIMARY KEY (user_id, url_id)
)