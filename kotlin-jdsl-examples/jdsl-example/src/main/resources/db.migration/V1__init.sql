create table member_role
(
    member_role_id bigint      not null auto_increment,
    created_at     datetime(6) not null,
    update_at      datetime(6) not null,
    member_id      bigint,
    role_id        bigint,
    primary key (member_role_id)
) engine = InnoDB;

create table members
(
    member_id  bigint      not null auto_increment,
    created_at datetime(6) not null,
    update_at  datetime(6) not null,
    name       varchar(255),
    primary key (member_id)
) engine = InnoDB;

create table roles
(
    role_id    bigint      not null auto_increment,
    created_at datetime(6) not null,
    update_at  datetime(6) not null,
    name       varchar(255),
    primary key (role_id)
) engine = InnoDB;