DROP TABLE IF EXISTS USER_ADDRESS_MAPPING CASCADE;
DROP TABLE IF EXISTS USER CASCADE;
CREATE TABLE if not exists USER
(
    USER_SEQ     bigint NOT NULL AUTO_INCREMENT,
    LOGIN_ID     VARCHAR(20) UNIQUE NOT NULL,
    PASSWORD     VARCHAR(60)        NOT NULL,
    NAME         VARCHAR(60)        NOT NULL,
    PHONE_NUMBER VARCHAR(14)        NOT NULL,
    EMAIL        VARCHAR(60),
    PRIMARY KEY (`USER_SEQ`)
);

DROP TABLE IF EXISTS ADDRESS CASCADE;
CREATE TABLE if not exists ADDRESS
(
    ADDRESS_SEQ    BIGINT          NOT NULL AUTO_INCREMENT,
    POST           VARCHAR(5)      NOT NULL,
    SPOT_Y         DECIMAL(13,10)  NOT NULL,
    SPOT_X         DECIMAL(13,10)  NOT NULL,
    ADDRESS_DETAIL VARCHAR(150)    NOT NULL,
    PRIMARY KEY (`ADDRESS_SEQ`)
);

DROP TABLE IF EXISTS USER_ADDRESS_MAPPING CASCADE;
CREATE TABLE if not exists USER_ADDRESS_MAPPING
(
    USER_ADDRESS_SEQ    BIGINT          NOT NULL AUTO_INCREMENT,
    USER_SEQ            BIGINT          NOT NULL,
    ADDRESS_SEQ         BIGINT          NOT NULL,
    DEFAULT_YN          VARCHAR(2)      NOT NULL,
    PRIMARY KEY (`USER_ADDRESS_SEQ`),
    CONSTRAINT FK_MAPPING_USER_SEQ FOREIGN KEY (USER_SEQ) REFERENCES USER (USER_SEQ) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_MAPPING_ADDRESS_SEQ FOREIGN KEY (ADDRESS_SEQ) REFERENCES ADDRESS (ADDRESS_SEQ) ON DELETE CASCADE ON UPDATE CASCADE
);
