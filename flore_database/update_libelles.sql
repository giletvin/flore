-- familles - copier/coller les noms fr en anglais 
insert into scientific_family(id,name) select id,name from scientific_family;
update scientific_family set lang="en" where lang is null;
-- nb petales : idem
insert into nb_petale(id,name) select id,name from nb_petale;
update nb_petale set lang="en" where lang is null;

--inflorescence - insertion des noms anglais
insert into inflorescence(id) select id from inflorescence where id=0;
update aspect set name="Not single",lang="en" where name is null;
insert into inflorescence(id) select id from inflorescence where id=1;
update aspect set name="Single",lang="en" where name is null;



--type de feuilles
insert into type_feuille(id) select id from type_feuille where name="Composée";
update type_feuille set name="Compound",lang="en" where name is null;
insert into type_feuille(id) select id from type_feuille where name="Divisée";
update type_feuille set name="Dissected",lang="en" where name is null;
insert into type_feuille(id) select id from type_feuille where name="Simple dentée";
update type_feuille set name="Simple and toothed",lang="en" where name is null;
insert into type_feuille(id) select id from type_feuille where name="Simple entière";
update type_feuille set name="Simple and entire",lang="en" where name is null;
insert into type_feuille(id) select id from type_feuille where name="Autre";
update type_feuille set name="Other",lang="en" where name is null;

--1 PSER : pétales séparés et égaux
--2 PSI : pétales séparés et inégaux
--3 PSLI : pétales soudés, lobes inégaux
--4 PSLE : pétales soudés, lobes égaux
--5 CTL : composée de fleurs tubulées et ligulées
--6 CLOT : composée de fleurs tubulées ou ligulées
--7 Autre

update aspect set name="Pétales séparés et égaux",ordre=1 where name="Pser" and lang="fr";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=1;
update aspect set name="Petals free and equal",lang="en" where name is null and ordre=1;

update aspect set name="Pétales séparés et inégaux",ordre=2 where name="Psi" and lang="fr";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=2;
update aspect set name="Petals free and inequal",lang="en" where name is null and ordre=2;

update aspect set name="Pétales soudés, lobes égaux",ordre=4 where name="Psle" and lang="fr";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=4;
update aspect set name="Petals fused and regular",lang="en" where name is null and ordre=4;

update aspect set name="Pétales soudés, lobes inégaux",ordre=3 where name="Psli" and lang="fr";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=3;
update aspect set name="Petals fused and irregular",lang="en" where name is null and ordre=3;

update aspect set name="Composée de fleurs tubulées ou ligulées",ordre=6 where name="Clot" and lang="fr";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=6;
update aspect set name="Composite with tubular and ligulate florets",lang="en" where name is null and ordre=6;

update aspect set name="Composée de fleurs tubulées et ligulées",ordre=5 where name="Ctl" and lang="fr";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=5;
update aspect set name="Composite, all florets tubular or ligulate",lang="en" where name is null and ordre=5;

update fleur_aspect set aspect_fk=10 where aspect_fk=(select id from aspect where name="Autre");
update aspect set id=10,ordre=7 where name="Autre";
insert into aspect(id,ordre) select id,ordre from aspect where ordre=7;
update aspect set name="Other",lang="en" where name is null and ordre=7;



--particularites
--corolle tâchetée, striée, bicolore
--présence d'un éperon
--pétales nettement échancrés
--forme de cloche, grelot
--type "papillon"
--type orchidée

update particularite set name="Forme de cloche, grelot" where name="Cloche" and lang="fr";
insert into particularite(id) select id from particularite where name="Forme de cloche, grelot";
update particularite set name="Bell shape",lang="en" where name is null;

update particularite set name="Pétales nettement échancrés" where name="échancré" and lang="fr";
insert into particularite(id) select id from particularite where name="Pétales nettement échancrés";
update particularite set name="Deeply notched petals",lang="en" where name is null;

update particularite set name="Présence d'un éperon" where name="éperon" and lang="fr";
--'
insert into particularite(id) select id from particularite where name="Présence d'un éperon";
--'
update particularite set name="Spur",lang="en" where name is null;

update particularite set name="Type papillon" where name="Papillon" and lang="fr";
insert into particularite(id) select id from particularite where name="Type papillon";
update particularite set name="Papilionaceous (butterfly shape)",lang="en" where name is null;

update particularite set name="Corolle tâchetée, striée, bicolore" where name="Tachetée" and lang="fr";
insert into particularite(id) select id from particularite where name="Corolle tâchetée, striée, bicolore";
update particularite set name="Bicolor or spotted corolla",lang="en" where name is null;

update particularite set name="Type orchidée" where name="Orchidée" and lang="fr";
insert into particularite(id) select id from particularite where name="Type orchidée";
update particularite set name="Orchid like",lang="en" where name is null;

--disposition des feuilles
--Alternes
--Opposées
--Verticillées
--Toutes à la base
--Autre
update disposition_feuille set name="Toutes à la base" where name="à la base" and lang="fr";
insert into disposition_feuille(id) select id from disposition_feuille where name="Alternes";
update disposition_feuille set name="Alternate",lang="en" where name is null;
insert into disposition_feuille(id) select id from disposition_feuille where name="Opposées";
update disposition_feuille set name="Opposite",lang="en" where name is null;
insert into disposition_feuille(id) select id from disposition_feuille where name="Toutes à la base";
update disposition_feuille set name="All basal",lang="en" where name is null;
insert into disposition_feuille(id) select id from disposition_feuille where name="Verticillées";
update disposition_feuille set name="Whorled",lang="en" where name is null;
insert into disposition_feuille(id) select id from disposition_feuille where name="Autre";
update disposition_feuille set name="Other",lang="en" where name is null;



--Pilosité de la tige
--Poilue, duveteuse
--Glabre
--Présence d'épines
update pilosite_tige set name="Poilue, duveteuse" where name="Pubescente" and lang="fr";
update pilosite_tige set name="Présence d'épines" where name="épine" and lang="fr";
--'

insert into pilosite_tige(id) select id from pilosite_tige where name="Glabre";
update pilosite_tige set name="Hairless",lang="en" where name is null;
insert into pilosite_tige(id) select id from pilosite_tige where name="Poilue, duveteuse";
update pilosite_tige set name="Hairy",lang="en" where name is null;
insert into pilosite_tige(id) select id from pilosite_tige where name="Présence d'épines";--'
update pilosite_tige set name="Spiny",lang="en" where name is null;

--Pilosité des feuilles
--Glabre
--Poilue ou duveteuse sur une face
--Poilue ou duveteuse sur les deux faces
update pilosite_feuille set name="Poilue ou duveteuse sur les deux faces" where name="Pubescente2" and lang="fr";
update pilosite_feuille set name="Poilue ou duveteuse sur une face" where name="Pubescente1" and lang="fr";
insert into pilosite_feuille(id) select id from pilosite_feuille where name="Glabre";
update pilosite_feuille set name="Hairless",lang="en" where name is null;
insert into pilosite_feuille(id) select id from pilosite_feuille where name="Poilue ou duveteuse sur une face";
update pilosite_feuille set name="Hairy on one face",lang="en" where name is null;
insert into pilosite_feuille(id) select id from pilosite_feuille where name="Poilue ou duveteuse sur les deux faces";
update pilosite_feuille set name="Hairy on both faces",lang="en" where name is null;


--couleur
--rose, rouge, mauve, pourpre
--bleu, violet
--jaune, jaunâtre
--brun, marron
--vert, verdâtre
--blanc, blanchâtre
-- couleur - insertion des noms anglais
insert into couleur(id) select id from couleur where name="Blanc";
update couleur set name="White",lang="en" where name is null;
insert into couleur(id) select id from couleur where name="Rose";
update couleur set name="Pink",lang="en" where name is null;
insert into couleur(id) select id from couleur where name="Bleu";
update couleur set name="Blue",lang="en" where name is null;
insert into couleur(id) select id from couleur where name="Jaune";
update couleur set name="Yellow",lang="en" where name is null;
insert into couleur(id) select id from couleur where name="Marron";
update couleur set name="Brown",lang="en" where name is null;
insert into couleur(id) select id from couleur where name="Vert";
update couleur set name="Green",lang="en" where name is null;
update couleur set name="Blanc, blanchâtre" where name="Blanc" and lang="fr";
update couleur set name="Rose, rouge, mauve, pourpre" where name="Rose" and lang="fr";
update couleur set name="Bleu, violet" where name="Bleu" and lang="fr";
update couleur set name="Jaune, jaunâtre" where name="Jaune" and lang="fr";
update couleur set name="Brun, marron" where name="Marron" and lang="fr";
update couleur set name="Vert, verdâtre" where name="Vert" and lang="fr";

