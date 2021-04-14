DROP TABLE IF EXISTS test;

CREATE TABLE message
(
    id         BIGINT PRIMARY KEY,
    link       TEXT,
    message_id BIGINT,
    author_id  BIGINT
);