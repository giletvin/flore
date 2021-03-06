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
		lang
);


create table inflorescence(
	id integer,
	name,
	lang
);


create table fleur_inflorescence(
	fleur_fk,
	inflorescence_fk
);

create table couleur(
		id integer,
		name,
		lang
);


create table fleur_couleur(
	fleur_fk,
	couleur_fk
);

create table aspect(
		id integer,
		name,
		lang,
		ordre
);

create table fleur_aspect(
	fleur_fk,
	aspect_fk
);

create table nb_petale(
		id integer,
		name,
		lang
);

create table fleur_nb_petale(
	fleur_fk,
	nb_petale_fk
);

create table type_feuille(
		id integer,
		name,
		lang
);
create table fleur_type_feuille(
	fleur_fk,
	type_feuille_fk
);
create table disposition_feuille(
		id integer,
		name,
		lang
);

create table fleur_disposition_feuille(
	fleur_fk,
	disposition_feuille_fk
);
create table pilosite_tige(
		id integer,
		name,
		lang
);
create table fleur_pilosite_tige(
	fleur_fk,
	pilosite_tige_fk
);
create table pilosite_feuille(
		id integer,
		name,
		lang
);
create table fleur_pilosite_feuille(
	fleur_fk,
	pilosite_feuille_fk
);


create table particularite(
	id integer,
	name,
	lang
);


create table fleur_particularite(
	fleur_fk,
	particularite_fk
);


create table application_info(id integer,key,value,date,comments);
insert into application_info(id,key,value,date,comments) VALUES(1,"flore_version","1.0.0","","");


create table release_notes(id integer,version_code integer,date,comments_de,comments_en,comments_fr,read integer);

insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(8,8,"09/11/2017","","","",0);
update release_notes set comments_fr=" * Version 1.0.7 (09/11/2017)
 * quelques ajouts dans la base de données
" where version_code=8;
update release_notes set comments_en=" * Version 1.0.7 (11/09/2017)
 * corrections in the database
" where version_code=8;
update release_notes set comments_de=" * Version 1.0.7 (11/09/2017)
 * corrections in the database
" where version_code=8;


insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(7,7,"23/04/2017","","","",0);
update release_notes set comments_fr=" * Version 1.0.6 (23/04/2017)
 * quelques ajouts dans la base de données
" where version_code=7;
update release_notes set comments_en=" * Version 1.0.6 (04/23/2017)
 * corrections in the database
" where version_code=7;
update release_notes set comments_de=" * Version 1.0.6 (04/23/2017)
 * corrections in the database
" where version_code=7;


insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(6,6,"06/10/2016","","","",0);
update release_notes set comments_fr=" * Version 1.0.5 (06/10/2016)
 * quelques ajouts dans la base de données
" where version_code=6;
update release_notes set comments_en=" * Version 1.0.5 (10/06/2016)
 * corrections in the database
" where version_code=6;
update release_notes set comments_de=" * Version 1.0.5 (10/06/2016)
 * corrections in the database
" where version_code=6;

-- release 1.0.3 version 4
insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(4,4,"08/05/2016","","","",0);
update release_notes set comments_fr=" * Version 1.0.3 (08/05/2016)
 * quelques corrections dans la base de données
" where version_code=4;
update release_notes set comments_en=" * Version 1.0.3 (05/08/2016)
 * corrections in the database
" where version_code=4;
update release_notes set comments_de=" * Version 1.0.3 (05/08/2016)
 * corrections in the database
" where version_code=4;

-- release 1.0.2 version 3
insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(3,3,"05/05/2016","","","",0);
update release_notes set comments_fr=" * Version 1.0.2 (05/05/2016)
 * quelques ajouts dans la base de données
" where version_code=3;
update release_notes set comments_en=" * Version 1.0.2 (05/05/2016)
 * new entries in the database
" where version_code=3;
update release_notes set comments_de=" * Version 1.0.2 (05/05/2016)
 * new entries in the database
" where version_code=3;




-- release 1.0.1 version 2
insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(2,2,"20/12/2015","","","",0);
update release_notes set comments_fr=" * Version 1.0.1 (21/12/2015)
 * quelques ajouts dans la base de données
" where version_code=2;
update release_notes set comments_en=" * Version 1.0.1 (12/21/2015)
 * new entries in the database
" where version_code=2;
update release_notes set comments_de=" * Version 1.0.1 (12/21/2015)
 * new entries in the database
" where version_code=2;

-- release 1.0.0 version 1
insert into release_notes(id,version_code,date,comments_en,comments_de,comments_fr,read) VALUES(1,1,"27/07/2015","","","",0);
update release_notes set comments_fr=" * Version 1.0.0 (27/07/2015)
Première version !
" where version_code=1;
update release_notes set comments_en=" * Version 1.0.0 (07/27/2015)
First release!
" where version_code=1;
