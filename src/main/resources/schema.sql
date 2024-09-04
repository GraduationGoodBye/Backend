DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS common_code;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS members;

CREATE TABLE members
(
    id         BIGINT AUTO_INCREMENT NOT NULL COMMENT '멤버_ID',
    sns_type   VARCHAR(100) NOT NULL COMMENT 'GOOGLE, KAKAO , NAVER',
    sns_id     VARCHAR(255) NOT NULL COMMENT 'SNS 고유ID',
    email      VARCHAR(255) NOT NULL COMMENT 'SNS 로그인시 받아오는 이메일 값',
    profile    VARCHAR(255) NULL COMMENT 'storage URL',
    nickname   VARCHAR(255) NOT NULL COMMENT '닉네임(null시 랜덤값 생성)',
    address    VARCHAR(255) NULL COMMENT '주소(null)',
    gender     VARCHAR(1) NULL COMMENT '남/여',
    age        TINYINT NULL COMMENT '나이',
    phone      VARCHAR(13) NULL COMMENT '전화번호(13자제한)',
    created_at DATETIME     NOT NULL COMMENT '생성일시',
    created_id VARCHAR(255) NOT NULL COMMENT '생성자',
    updated_id VARCHAR(255) NULL COMMENT '수정자(null)',
    updated_at DATETIME NULL COMMENT '수정일시(null)',
    deleted_at DATETIME NULL COMMENT '탈퇴일시(null)',
    role       VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE tokens
(
    id            BIGINT AUTO_INCREMENT,
    member_id     VARCHAR(100) UNIQUE,
    refresh_token VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE `admin`
(
    id       BIGINT       NOT NULL COMMENT '관리자_ID',
    account  VARCHAR(255) NOT NULL COMMENT '아이디',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    contact  VARCHAR(255) NOT NULL COMMENT '담당자',
    PRIMARY KEY (id)
);

CREATE TABLE common_code
(
    id         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '공통코드_ID',
    type       VARCHAR(255) NOT NULL COMMENT '대학,학과,사진타입,작품카테고리',
    code       VARCHAR(255) NOT NULL COMMENT '코드',
    name       VARCHAR(255) NOT NULL COMMENT '이름',
    created_id VARCHAR(255) NOT NULL COMMENT '생성자',
    created_at DATETIME     NOT NULL COMMENT '생성일시',
    updated_id VARCHAR(255) NULL COMMENT '수정자(null)',
    updated_at DATETIME NULL COMMENT '수정일시(null)',
    PRIMARY KEY (id)
);

CREATE TABLE artist
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '작가_ID',
    member_id            BIGINT       NOT NULL COMMENT '멤버_ID',
    common_university_id BIGINT       NOT NULL COMMENT '공통코드_대학_ID',
    common_major_id      BIGINT       NOT NULL COMMENT '공통코드_학과_ID',
    admin_id             BIGINT NULL COMMENT '승인자',
    name                 VARCHAR(255) NULL COMMENT '작가이름(null)',
    created_id           VARCHAR(255) NOT NULL COMMENT '생성자',
    created_at           DATETIME     NOT NULL COMMENT '생성일시',
    updated_id           VARCHAR(255) NULL COMMENT '수정자(null)',
    updated_at           DATETIME NULL COMMENT '수정일시(null)',
    certificate_url      VARCHAR(255) NOT NULL COMMENT '증명서URL',
    approval_date        DATETIME NULL COMMENT 'null 로 승인 여부 판별',
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    FOREIGN KEY (common_university_id) REFERENCES common_code (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    FOREIGN KEY (common_major_id) REFERENCES common_code (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);



INSERT INTO members (sns_type, sns_id, email, nickname, role, created_id, created_at)
VALUES ('GOOGLE', 'test', 'test@google.com', 'nickname', 'MEMBER', 'default', now());

INSERT INTO members (sns_type, sns_id, email, nickname, role, created_id, created_at)
VALUES ('GOOGLE', 'admin', 'admin@google.com', 'admin', 'ADMIN', 'default', now());


INSERT INTO common_code (type, code, name, created_id, created_at)
VALUES ('university', '0', '홍익대학교', 'system', now());

INSERT INTO common_code (type, code, name, created_id, created_at)
VALUES ('major', '0', '패션디자인과', 'system', now());