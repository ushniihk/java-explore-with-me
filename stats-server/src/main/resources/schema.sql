drop table if exists HITS cascade;


create table if not exists HITS
(
    ID        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    APP       varchar(255),
    URI       varchar(255),
    IP        varchar(255),
    TIMESTAMP timestamp with time zone
);

