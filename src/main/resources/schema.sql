CREATE TABLE users(
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(100),
    profile VARCHAR(255),
    name VARCHAR(50),
    nickname VARCHAR(50),
    address VARCHAR(255),
    phone VARCHAR(255),
    gender VARCHAR(10),
    role VARCHAR(20),
    PRIMARY KEY (id)
);
