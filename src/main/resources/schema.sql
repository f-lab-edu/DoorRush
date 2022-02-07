DROP TABLE IF EXISTS ORDER_MENU CASCADE;
DROP TABLE IF EXISTS `ORDER` CASCADE;
DROP TABLE IF EXISTS MENU CASCADE;
DROP TABLE IF EXISTS USER_ADDRESS_MAPPING CASCADE;
DROP TABLE IF EXISTS RESTAURANT CASCADE;
DROP TABLE IF EXISTS ADDRESS CASCADE;
DROP TABLE IF EXISTS OWNER CASCADE;
DROP TABLE IF EXISTS USER CASCADE;

CREATE TABLE if not exists USER
(
    USER_SEQ     BIGINT                  NOT NULL AUTO_INCREMENT,
    LOGIN_ID     VARCHAR(20) UNIQUE      NOT NULL,
    PASSWORD     VARCHAR(60)             NOT NULL,
    NAME         VARCHAR(60)             NOT NULL,
    PHONE_NUMBER VARCHAR(14)             NOT NULL,
    EMAIL        VARCHAR(60),
    INPUT_DATE   TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE  TIMESTAMP,
    PRIMARY KEY (`USER_SEQ`)
    );

CREATE TABLE if not EXISTS OWNER
(
    OWNER_SEQ   BIGINT                  NOT NULL AUTO_INCREMENT,
    LOGIN_ID    VARCHAR(20) UNIQUE      NOT NULL,
    PASSWORD    VARCHAR(60)             NOT NULL,
    INPUT_DATE  TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE TIMESTAMP,
    PRIMARY KEY (`OWNER_SEQ`)
    );


CREATE TABLE if not EXISTS OWNER
(
    OWNER_SEQ   BIGINT                  NOT NULL AUTO_INCREMENT,
    LOGIN_ID    VARCHAR(20) UNIQUE      NOT NULL,
    PASSWORD    VARCHAR(60)             NOT NULL,
    INPUT_DATE  TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE TIMESTAMP,
    PRIMARY KEY (`OWNER_SEQ`)
);


CREATE TABLE if not exists ADDRESS
(
    ADDRESS_SEQ    BIGINT                  NOT NULL AUTO_INCREMENT,
    POST           VARCHAR(5)              NOT NULL,
    SPOT_Y         DECIMAL(13, 10)         NOT NULL,
    SPOT_X         DECIMAL(13, 10)         NOT NULL,
    ADDRESS_DETAIL VARCHAR(150)            NOT NULL,
    INPUT_DATE     TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE    TIMESTAMP,
    PRIMARY KEY (`ADDRESS_SEQ`)
    );

CREATE TABLE if not exists USER_ADDRESS_MAPPING
(
    USER_ADDRESS_SEQ BIGINT     NOT NULL AUTO_INCREMENT,
    USER_SEQ         BIGINT     NOT NULL,
    ADDRESS_SEQ      BIGINT     NOT NULL,
    DEFAULT_YN       VARCHAR(1) NOT NULL,
    PRIMARY KEY (`USER_ADDRESS_SEQ`),
    FOREIGN KEY (USER_SEQ) REFERENCES USER (USER_SEQ) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ADDRESS_SEQ) REFERENCES ADDRESS (ADDRESS_SEQ) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE if not exists RESTAURANT
(
    RESTAURANT_SEQ       BIGINT                  NOT NULL AUTO_INCREMENT,
    OWNER_SEQ            BIGINT                  NOT NULL,
    CATEGORY             VARCHAR(50)             NOT NULL,
    OPEN_YN              VARCHAR(1),
    RESTAURANT_NAME      VARCHAR(50)             NOT NULL,
    INTRODUCTION         VARCHAR(500),
    MINIMUM_ORDER_AMOUNT BIGINT                  NOT NULL,
    ADDRESS_SEQ          BIGINT                  NOT NULL,
    INPUT_DATE           TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE          TIMESTAMP,
    PRIMARY KEY (`RESTAURANT_SEQ`),
    FOREIGN KEY (OWNER_SEQ) REFERENCES OWNER (OWNER_SEQ) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ADDRESS_SEQ) REFERENCES ADDRESS (ADDRESS_SEQ) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS `ORDER`
(
    ORDER_SEQ       BIGINT                  NOT NULL AUTO_INCREMENT,
    USER_SEQ        BIGINT                  NOT NULL,
    ADDRESS         VARCHAR(100)            NOT NULL,
    RESTAURANT_SEQ  BIGINT                  NOT NULL,
    RESTAURANT_NAME VARCHAR(50)             NOT NULL,
    ORDER_STATUS    VARCHAR(15)             NOT NULL,
    AMOUNT          INT                     NOT NULL,
    ORDER_TIME      TIMESTAMP,
    INPUT_DATE      TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE     TIMESTAMP,
    PRIMARY KEY (`ORDER_SEQ`),
    FOREIGN KEY (USER_SEQ) REFERENCES USER (USER_SEQ) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (RESTAURANT_SEQ) REFERENCES RESTAURANT (RESTAURANT_SEQ) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS MENU
(
    MENU_SEQ       BIGINT                  NOT NULL AUTO_INCREMENT,
    RESTAURANT_SEQ BIGINT                  NOT NULL,
    NAME           VARCHAR(50)             NOT NULL,
    PRICE          INT                     NOT NULL,
    INPUT_DATE     TIMESTAMP DEFAULT NOW() NOT NULL,
    UPDATE_DATE    TIMESTAMP,
    PRIMARY KEY (`MENU_SEQ`),
    FOREIGN KEY (RESTAURANT_SEQ) REFERENCES RESTAURANT (RESTAURANT_SEQ) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS ORDER_MENU
(
    MENU_SEQ  BIGINT NOT NULL,
    ORDER_SEQ BIGINT NOT NULL,
    COUNT     INT,
    FOREIGN KEY (ORDER_SEQ) REFERENCES `ORDER` (ORDER_SEQ) ON UPDATE CASCADE,
    FOREIGN KEY (MENU_SEQ) REFERENCES MENU (MENU_SEQ) ON UPDATE CASCADE
    );