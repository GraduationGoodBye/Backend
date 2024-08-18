DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS common_code;
DROP TABLE IF EXISTS admin;
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

CREATE TABLE `admin` (
    id	        BIGINT	        NOT NULL	COMMENT '관리자_ID',
    account	    varchar(255)	NOT NULL	COMMENT '아이디',
    password	varchar(255)	NOT NULL	COMMENT '비밀번호',
    contact	    varchar(255)	NOT NULL	COMMENT '담당자',
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

CREATE TABLE artist
(
    id                      BIGINT          NOT NULL AUTO_INCREMENT COMMENT '작가_ID',
    member_id               BIGINT          NOT NULL                COMMENT '멤버_ID',
    common_university_id    BIGINT	        NOT NULL                COMMENT '공통코드_대학_ID',
    common_major_id         BIGINT          NOT NULL                COMMENT '공통코드_학과_ID',
    admin_id                BIGINT          NULL                    COMMENT '승인자',
    name                    varchar(255)    NULL                    COMMENT '작가이름(null)',
    created_id              varchar(255)    NOT NULL                COMMENT '생성자',
    created_at              datetime        NOT NULL                COMMENT '생성일시',
    updated_id              varchar(255)	NULL                    COMMENT '수정자(null)',
    updated_at              datetime        NULL                    COMMENT '수정일시(null)',
    certificate_url          varchar(255)	NOT NULL                COMMENT '증명서URL',
    approval_date           datetime        NULL	                COMMENT 'null 로 승인 여부 판별',
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    FOREIGN KEY (common_university_id) REFERENCES common_code(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    FOREIGN KEY (common_major_id) REFERENCES common_code(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);







INSERT INTO members (sns_type,sns_id,email,nickname,role)
VALUES ('GOOGLE','test','test@google.com','nickname','USER');


INSERT INTO common_code (type, code, name, created_id, created_at)
VALUES ('university','0','홍익대학교','system',now());

INSERT INTO common_code (type, code, name, created_id, created_at)
VALUES ('major','0','패션디자인과','system',now());