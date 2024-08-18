DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS members;

CREATE TABLE members
(
    id       BIGINT AUTO_INCREMENT,
    sns_type VARCHAR(20),
    sns_id   VARCHAR(255),
    email    VARCHAR(100),
    profile  VARCHAR(255),
    nickname VARCHAR(50),
    address  VARCHAR(255),
    gender   VARCHAR(10),
    age      TINYINT,
    phone    VARCHAR(255),
    role     VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE tokens
(
    id            BIGINT AUTO_INCREMENT,
    user_id       VARCHAR(100) UNIQUE,
    refresh_token VARCHAR(255),
    PRIMARY KEY (id)
);