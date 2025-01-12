CREATE SCHEMA security;

create table security.feature
(
    id               varchar(255) not null,
    active           boolean default true,
    description      varchar(255),
    name             varchar(255),
    id_system_module varchar(255),
    primary key (id)
);

create table security.profile
(
    id          varchar(255) not null,
    active      boolean default true,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);

create table security.role
(
    id          varchar(255) not null,
    active      boolean default true,
    description varchar(255),
    name        varchar(255),
    id_feature  varchar(255),
    primary key (id)
);

create table security.roles_profile
(
    id_profile varchar(255) not null,
    id_role    varchar(255) not null,
    primary key (id_profile, id_role)
);

create table security.system
(
    id          varchar(255) not null,
    active      boolean default true,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);

create table security.system_module
(
    id          varchar(255) not null,
    active      boolean default true,
    description varchar(255),
    name        varchar(255),
    id_system   varchar(255),
    primary key (id)
);

create table security.user_profile
(
    id_user    int8         not null,
    id_profile varchar(255) not null,
    primary key (id_user, id_profile)
);

create table security.user_system
(
    id                   bigserial    not null,
    document_number      varchar(255),
    document_type        varchar(100),
    email                varchar(255),
    last_access          timestamp,
    login                varchar(255),
    name                 varchar(255) not null,
    password             varchar(255) not null,
    qty_invalid_attempts int4,
    status               varchar(100) not null,
    uuid                 uuid,
    primary key (id)
);

create index user_index_status on security.user_system (status);
create index user_index_doc_type on security.user_system (document_type);

alter table security.feature
    add constraint FK2avs0t4ujifn6olnuow5t9fdn
        foreign key (id_system_module)
            references security.system_module;

alter table security.role
    add constraint FKeund6rnm60un45wi6ba0cvacc
        foreign key (id_feature)
            references security.feature;

alter table security.roles_profile
    add constraint FK56qbos2s4oknbkevqjsa56lr0
        foreign key (id_role)
            references security.role;

alter table security.roles_profile
    add constraint FKc0w2w2eux8lbajydghtqsmyny
        foreign key (id_profile)
            references security.profile;

alter table security.system_module
    add constraint FK6aueaexykvybh6xhr0hma6l7r
        foreign key (id_system)
            references security.system;

alter table security.user_profile
    add constraint FK78bw1kv69go0c2tr0jv08dcjx
        foreign key (id_profile)
            references security.profile;

alter table security.user_profile
    add constraint FKcspgan7a5397ba6me049ukbhj
        foreign key (id_user)
            references security.user_system;

create table customer
(
    id            serial not null,
    active        boolean default true,
    register_date timestamp,
    updated_date  timestamp,
    document      varchar(20),
    email         varchar(255),
    name          varchar(255),
    uuid          uuid,
    primary key (id)
);

alter table customer
    add constraint UK_phlle50dp6ivt0paa1d5gkvk2 unique (document);
