DROP TABLE IF EXISTS common_code;
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
CREATE TABLE common_code
(
    id	        BIGINT	        NOT NULL AUTO_INCREMENT COMMENT '공통코드_ID',
    type        varchar(255)    NOT NULL                COMMENT '대학,학과,사진타입,작품카테고리',
    code        varchar(255)    NOT NULL                COMMENT '코드',
    name        varchar(255)    NOT NULL                COMMENT '이름',
    created_id  varchar(255)    NOT NULL                COMMENT '생성자',
    created_at  datetime        NOT NULL                COMMENT '생성일시',
    updated_id  varchar(255)	NULL                    COMMENT '수정자(null)',
    updated_at  datetime	    NULL                    COMMENT '수정일시(null)',
    PRIMARY KEY (id)
);