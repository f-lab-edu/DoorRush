INSERT INTO `USER` (`USER_SEQ`, `LOGIN_ID`, `PASSWORD`, `NAME`, `PHONE_NUMBER`, `EMAIL`)
VALUES ('1', 'test1', 'test1pw', '11', '01000000001', '1@naver.com'),
       ('4', 'test2', 'test2pw', '22', '01000000001', '2@naver.com'),
       ('6', 'test3', 'test3pw', '33', '01000000001', '3@naver.com'),
       ('8', 'test4', 'test4pw', '44', '01000000001', '4@naver.com'),
       ('23', 'test5', 'test5pw', '55', '01000000001', '5@naver.com'),
       ('25', 'test6', '$2a$10$n87tHz0k0YzazutVDnZg3.BcD5kvDiPM/osWDhlKcZCDCf2g7zCse', '66', '01000000001', '6@naver.com');


INSERT INTO `ADDRESS` (`ADDRESS_SEQ`, `POST`, `SPOT_Y`, `SPOT_X`, `ADDRESS_DETAIL`)
VALUES ('1', '13561', '127.10522081658463', '37.35951219616309', '301호'),
       ('2', '13561', '127.10522081658463', '37.35951219616309', '1208호'),
       ('3', '63309', '33.450701', '126.570667', '2039호'),
       ('4', '63309', '33.450701', '126.570667', '503호');

INSERT INTO `USER_ADDRESS_MAPPING` (`USER_ADDRESS_SEQ`, `USER_SEQ`, `ADDRESS_SEQ`, `DEFAULT_YN`)
VALUES ('1', '25', '1', 'Y'),
       ('2', '25', '2', 'N'),
       ('3', '23', '3', 'Y'),
       ('4', '23', '4', 'N');




