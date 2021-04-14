DROP TABLE IF EXISTS test;

CREATE TABLE IF NOT EXISTS message
(
    id         BIGINT PRIMARY KEY,
    link       TEXT,
    message_id BIGINT,
    author_id  BIGINT
);