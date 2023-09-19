create database only_one_pick;
use only_one_pick;

-- Create version table
CREATE TABLE version
(
    `version_id`  INT            NOT NULL    AUTO_INCREMENT,
    `minimum`     VARCHAR(20)    NOT NULL,
    `latest`      VARCHAR(20)    NOT NULL,
    `created_at`  TIMESTAMP      NOT NULL,
    `updated_at`  TIMESTAMP      NOT NULL,
    PRIMARY KEY (version_id)
);

-- Create member table
CREATE TABLE member
(
    `member_id`   BIGINT          NOT NULL    AUTO_INCREMENT,
    `email`       VARCHAR(320)    NOT NULL,
    `password`    VARCHAR(255)    NOT NULL,
    `authority`   VARCHAR(10)     NOT NULL    DEFAULT 'ROLE_USER',
    `deleted`     TINYINT(1)      NOT NULL    DEFAULT 0,
    `created_at`  TIMESTAMP       NOT NULL,
    `updated_at`  TIMESTAMP       NOT NULL,
    PRIMARY KEY (member_id)
);