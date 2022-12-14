drop table if exists events cascade;
drop table if exists locations cascade;
drop table if exists participation_requests cascade;
drop table if exists compilations cascade;
drop table if exists users cascade;
drop table if exists events_compilation cascade;
drop table if exists categories cascade;
drop table if exists comments cascade;

create table if not exists locations
(
    id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat float,
    lon float
);

create table if not exists users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email varchar not null,
    name  varchar,
    UNIQUE (email)
);

create table if not exists categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar not null,
    UNIQUE (name)
);

create table if not exists events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation         varchar(2000) NOT NULL,
    category           bigint,
    description        varchar(7000) NOT NULL,
    EVENT_DATE         TIMESTAMP WITHOUT TIME ZONE,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    initiator          bigint,
    location           bigint,
    paid               boolean,
    participant_limit  bigint,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation boolean,
    state              varchar,
    title              varchar(120),
    VIEWS              bigint,
    CONSTRAINT fk_location FOREIGN KEY (location) REFERENCES locations (id),
    CONSTRAINT fk_category FOREIGN KEY (category) REFERENCES categories (id),
    CONSTRAINT fk_users FOREIGN KEY (initiator) REFERENCES users (id)
);

create table if not exists participation_requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created      timestamp with time zone not null,
    EVENT_ID     bigint                   not null,
    REQUESTER_ID bigint                   not null,
    status       varchar                  not null,
    CONSTRAINT fk_request_for_event FOREIGN KEY (EVENT_ID) REFERENCES events (id),
    CONSTRAINT fk_request_for_user FOREIGN KEY (REQUESTER_ID) REFERENCES users (id)
);

create table if not exists events_compilation
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    EVENT_ID       bigint not null,
    compilation_id bigint not null
);

create table if not exists compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned boolean,
    title  varchar
);

create table if not exists comments
(
    ID       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    MESSAGE  varchar(300),
    EVENT_ID bigint                   not null,
    USER_ID  bigint                   not null,
    CREATED  timestamp with time zone not null,
    CONSTRAINT fk_comments_for_event FOREIGN KEY (EVENT_ID) REFERENCES events (id),
    CONSTRAINT fk_comments_for_user FOREIGN KEY (USER_ID) REFERENCES users (id)
);


