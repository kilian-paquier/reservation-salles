create table reservation
(
  id_salle   int          not null,
  mail_user  varchar(255) not null,
  date_debut datetime     not null,
  date_fin   datetime     not null,
  primary key (id_salle, date_debut, date_fin)
);

create index ForeignUser
  on reservation (mail_user);

create table salle
(
  id_salle  int auto_increment
    primary key,
  nom_salle varchar(255) null
);

create table user
(
  mail_user   varchar(255) not null,
  nom_user    varchar(255) null,
  prenom_user varchar(255) null,
  password    varchar(255) null,
  constraint USER_mail_user_uindex
    unique (mail_user)
);

alter table user
  add primary key (mail_user);


