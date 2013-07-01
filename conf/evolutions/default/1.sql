# Users スキーマ

# --- !Ups

CREATE TABLE User (
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (username)
);

# --- !Downs

DROP TABLE User;