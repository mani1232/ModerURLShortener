CREATE TABLE IF NOT EXISTS users
(
    id           serial PRIMARY KEY NOT NULL,
    display_name VARCHAR(255),
    username     VARCHAR(255) UNIQUE,
    password     VARCHAR(255),
    token        VARCHAR(255) UNIQUE,
    urls         integer ARRAY
);

CREATE TABLE IF NOT EXISTS urls
(
    id           serial PRIMARY KEY NOT NULL,
    title        VARCHAR(255),
    owner_id     VARCHAR(255),
    description  VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_at       TIMESTAMP NULL,
    enabled      BOOLEAN   DEFAULT TRUE,
    click_count  INT       DEFAULT 0,
    short_url    VARCHAR(10),
    full_url     VARCHAR(255)
);