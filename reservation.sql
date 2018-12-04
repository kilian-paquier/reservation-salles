create table reservation
(
	id_salle int not null,
	mail_user varchar(255) not null,
	date_debut datetime not null,
	date_fin datetime not null,
	primary key (id_salle, date_debut, date_fin)
);

create index ForeignUser
	on reservation (mail_user);

