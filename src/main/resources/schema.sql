create table if NOT EXISTS account (
                                       IBAN VARCHAR(255) NOT NULL primary key,
                                       ABSOLUTE_LIMIT FLOAT,
                                       ACTIVE BOOLEAN,
                                       BALANCE FLOAT,
                                       CURRENCY INTEGER,
                                       PINCODE INTEGER,
                                       TYPE INTEGER,
                                       USERID BIGINT NOT NULL
);

create table IF NOT EXISTS transaction (
                                           ID  uuid NOT NULL primary key,
                                           ACCOUNT_FROM  VARCHAR(255),
                                           ACCOUNT_TO VARCHAR(255),
                                           AMOUNT FLOAT ,
                                           DESCRIPTION VARCHAR(255),
                                           EXECUTION_DATE TIMESTAMP,
                                           USER_PERFORMING  BIGINT

);

create table IF NOT EXISTS "user" (
                                      ID BIGINT NOT NULL  primary key,
                                      DAY_LIMIT FLOAT ,
                                      FIRSTNAME VARCHAR(255),
                                      LASTNAME VARCHAR(255) NOT NULL,
                                      PASSWORD VARCHAR(255),
                                      TRANSACTION_LIMIT FLOAT,
                                      USERNAME  VARCHAR(255)
);

create table IF NOT EXISTS user_roles   (
                                            USER_ID BIGINT NOT NULL,
                                            ROLES   INTEGER



);

