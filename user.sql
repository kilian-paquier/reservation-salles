create table user
(
	mail_user varchar(255) not null,
	nom_user varchar(255) null,
	prenom_user varchar(255) null,
	password varchar(255) null,
	constraint USER_mail_user_uindex
		unique (mail_user)
);

alter table user
	add primary key (mail_user);

