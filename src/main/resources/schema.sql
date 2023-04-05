create table if NOT EXISTS ACCOUNT (
                                       IBAN VARCHAR(255) NOT NULL primary key,
                                       ABSOLUTE_LIMIT FLOAT,
                                       ACTIVE BOOLEAN,
                                       BALANCE FLOAT,
                                       CURRENCY INTEGER,
                                       PINCODE INTEGER,
                                       TYPE INTEGER,
                                       USERID BIGINT NOT NULL
);

create table IF NOT EXISTS TRANSACTION (
                                           ID  INTEGER NOT NULL primary key,
                                           ACCOUNT_FROM  VARCHAR(255),
                                           ACCOUNT_TO VARCHAR(255),
                                           AMOUNT FLOAT ,
                                           DESCRIPTION VARCHAR(255),
                                           EXECUTION_DATE TIMESTAMP,
                                           USER_PERFORMING  BIGINT
);

create table IF NOT EXISTS "USER" (
                                      ID BIGINT NOT NULL  primary key,
                                      DAY_LIMIT FLOAT ,
                                      FIRSTNAME VARCHAR(255),
                                      LASTNAME VARCHAR(255) NOT NULL,
                                      PASSWORD VARCHAR(255),
                                      TRANSACTION_LIMIT FLOAT,
                                      USERNAME  VARCHAR(255)
);

create table IF NOT EXISTS USER_ROLES   (
                                            USER_ID BIGINT NOT NULL,
                                            ROLES   INTEGER
);


create table if NOT EXISTS ACCOUNT (
                                       IBAN VARCHAR(255) NOT NULL primary key,
                                       ABSOLUTE_LIMIT FLOAT,
                                       ACTIVE BOOLEAN,
                                       BALANCE FLOAT,
                                       CURRENCY INTEGER,
                                       PINCODE INTEGER,
                                       TYPE INTEGER,
                                       USERID BIGINT NOT NULL,
                                       Foreign key(USERID) references "USER"(Id)
);

create table IF NOT EXISTS TRANSACTION (
                                           ID  INTEGER NOT NULL primary key,
                                           ACCOUNT_FROM  VARCHAR(255),
                                           ACCOUNT_TO VARCHAR(255),
                                           AMOUNT FLOAT ,
                                           DESCRIPTION VARCHAR(255),
                                           EXECUTION_DATE TIMESTAMP,
                                           USER_PERFORMING  BIGINT,

                                           Foreign key(USER_PERFORMING) references "USER"(Id),

                                           Foreign key(ACCOUNT_FROM) references ACCOUNT(IBAN),

                                           Foreign key(ACCOUNT_TO) references ACCOUNT(IBAN)

);

create table IF NOT EXISTS "USER" (
                                      ID BIGINT NOT NULL  primary key,
                                      DAY_LIMIT FLOAT ,
                                      FIRSTNAME VARCHAR(255),
                                      LASTNAME VARCHAR(255) NOT NULL,
                                      PASSWORD VARCHAR(255),
                                      TRANSACTION_LIMIT FLOAT,
                                      USERNAME  VARCHAR(255)
);

create table IF NOT EXISTS USER_ROLES   (
                                            USER_ID BIGINT NOT NULL,
                                            ROLES   INTEGER,

                                            Foreign key(USER_ID) references "USER"(Id)

);