drop table if exists events cascade;
drop table if exists location cascade;
drop table if exists participationRequests cascade;
drop table if exists categories cascade;
drop table if exists users cascade;
drop table if exists compilations cascade;
drop table if exists events_compilations;

create table if not exists locations
(
    id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat float not null,
    lon float not null
);

create table if not exists users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email varchar(30) not null,
    name  varchar(30),
    UNIQUE (email)
);

create table if not exists categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(69) not null,
    UNIQUE (name)
);

create table if not exists events
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation        varchar(2000)               NOT NULL,
    category          bigint,
    description       varchar(7000)               NOT NULL,
    eventDate         TIMESTAMP WITHOUT TIME ZONE not null,
    createdOn         TIMESTAMP WITHOUT TIME ZONE,
    initiator         bigint,
    location          bigint,
    paid              boolean,
    participantLimit  int,
    publishedOn       TIMESTAMP WITHOUT TIME ZONE,
    requestModeration boolean,
    state             varchar(18),
    title             varchar(69),
    views             bigint,
    CONSTRAINT fk_location FOREIGN KEY (location) REFERENCES locations (id),
    CONSTRAINT fk_category FOREIGN KEY (category) REFERENCES categories (id),
    CONSTRAINT fk_users FOREIGN KEY (initiator) REFERENCES users (id)
);

create table if not exists participationRequests
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created     timestamp with time zone not null,
    eventId     bigint                   not null,
    requesterId bigint                   not null,
    status      varchar                  not null,
    CONSTRAINT fk_request_for_event FOREIGN KEY (eventId) REFERENCES events (id),
    CONSTRAINT fk_request_for_user FOREIGN KEY (requesterId) REFERENCES users (id)
);

create table if not exists compilations
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    eventId bigint  not null,
    pinned  boolean not null,
    title   varchar(69),
    CONSTRAINT fk_compilation_for_event FOREIGN KEY (eventId) REFERENCES events (id)
);

