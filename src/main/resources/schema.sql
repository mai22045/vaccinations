create table center
(
    code    varchar(512) not null,
    address varchar(512) not null
);

create table citizen
(
    afm     int          not null,
    amka    int          not null,
    name    varchar(512) not null,
    surname varchar(512) not null,
    email   varchar(512) not null
);

create table doctor
(
    amka    int          not null,
    name    varchar(512) not null,
    surname varchar(512) null
);

create table timeslot
(
    start timestamp not null,
    end   timestamp null
);

