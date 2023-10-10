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

-- Create game table
CREATE TABLE game
(
    `game_id`       BIGINT          NOT NULL    AUTO_INCREMENT,
    `title`         VARCHAR(40)     NOT NULL,
    `description`   VARCHAR(300)    NOT NULL,
    `view_count`    BIGINT          NOT NULL    DEFAULT 0,
    `play_count`    BIGINT          NOT NULL    DEFAULT 0,
    `item_count`    BIGINT          NOT NULL    DEFAULT 0,
    `report_count`  BIGINT          NOT NULL    DEFAULT 0,
    `deleted`       TINYINT(1)      NOT NULL    DEFAULT 0,
    `member_id`     BIGINT          NOT NULL,
    `created_at`    TIMESTAMP       NOT NULL,
    `updated_at`    TIMESTAMP       NOT NULL,
    PRIMARY KEY (game_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

-- Create item table
CREATE TABLE item
(
    `item_id`     BIGINT           NOT NULL    AUTO_INCREMENT,
    `image_url`   VARCHAR(2083)    NOT NULL,
    `caption`     VARCHAR(50)      NOT NULL,
    `win_count`   BIGINT           NOT NULL    DEFAULT 0,
    `deleted`     TINYINT(1)       NOT NULL    DEFAULT 0,
    `game_id`     BIGINT           NOT NULL,
    `created_at`  TIMESTAMP        NOT NULL,
    `updated_at`  TIMESTAMP        NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY (game_id) REFERENCES game(game_id)
);