-- creation de la base 
-- creation de la table taxonomy en jointure avec la table fleur


create table fleur(
	id integer,
	directory_name,
	scientific_name,
	scientific_family_fk,
	doc_url,
	PRIMARY KEY(id)
);

CREATE VIRTUAL TABLE taxonomy USING fts3(lang, taxon, searched_taxon, fleur_fk,taxon_usuel);

create table scientific_family(
		id,
		name,
		lang,
		PRIMARY KEY(id)
);


create table inflorescence(
	id integer,
	name,
	lang,
	PRIMARY KEY(id)
);


create table fleur_inflorescence(
	fleur_fk,
	inflorescence_fk
);

create table couleur(
		id integer,
		name,
		lang,
	PRIMARY KEY(id)
);


create table fleur_couleur(
	fleur_fk,
	couleur_fk
);

create table aspect(
		id integer,
		name,
		lang,
		ordre,
	PRIMARY KEY(id)
);

create table fleur_aspect(
	fleur_fk,
	aspect_fk
);

create table nb_petale(
		id integer,
		name,
		lang,
	PRIMARY KEY(id)
);

create table fleur_nb_petale(
	fleur_fk,
	nb_petale_fk
);

create table type_feuille(
		id integer,
		name,
		lang,
	PRIMARY KEY(id)
);
create table fleur_type_feuille(
	fleur_fk,
	type_feuille_fk
);
create table disposition_feuille(
		id integer,
		name,
		lang,
	PRIMARY KEY(id)
);

create table fleur_disposition_feuille(
	fleur_fk,
	disposition_feuille_fk
);
create table pilosite_tige(
		id integer,
		name,
		lang,
	PRIMARY KEY(id)
);
create table fleur_pilosite_tige(
	fleur_fk,
	pilosite_tige_fk
);
create table pilosite_feuille(
		id integer,
		name,
		lang,
	PRIMARY KEY(id)
);
create table fleur_pilosite_feuille(
	fleur_fk,
	pilosite_feuille_fk
);


create table particularite(
	id integer,
	name,
	lang,
	PRIMARY KEY(id)
);


create table fleur_particularite(
	fleur_fk,
	particularite_fk
);


create table application_info(id integer,key,value,date,comments);
insert into application_info(id,key,value,date,comments) VALUES(1,"flore_version","1.0.0","","");
