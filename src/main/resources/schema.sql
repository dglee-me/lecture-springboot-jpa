create table IF NOT EXISTS member
(
    member_id bigint not null,
    city      varchar(255),
    name      varchar(255),
    street    varchar(255),
    zipcode   varchar(255),
    primary key (member_id)
);

create table IF NOT EXISTS item
(
    price          integer     not null,
    stock_quantity integer     not null,
    item_id        bigint      not null,
    dtype          varchar(31) not null,
    actor          varchar(255),
    artist         varchar(255),
    author         varchar(255),
    director       varchar(255),
    etc            varchar(255),
    isbn           varchar(255),
    name           varchar(255),
    primary key (item_id)
);