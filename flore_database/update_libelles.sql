--PSER : pétales séparés et égaux
--PSI : pétales séparés et inégaux
--PSLE : pétales soudés, lobes égaux
--PSLI : pétales soudés, lobes inégaux
--CLOT : composée de fleurs tubulées ou ligulées
--CTL : composée de fleurs tubulées et ligulées


update aspect set name="Pétales séparés et égaux" where name="Pser" and lang="fr";
update aspect set name="Pétales séparés et inégaux" where name="Psi" and lang="fr";
update aspect set name="Pétales soudés, lobes égaux" where name="Psle" and lang="fr";
update aspect set name="Pétales soudés, lobes inégaux" where name="Psli" and lang="fr";
update aspect set name="Composée de fleurs tubulées ou ligulées" where name="Clot" and lang="fr";
update aspect set name="Composée de fleurs tubulées et ligulées" where name="Ctl" and lang="fr";
update fleur_aspect set aspect_fk=10 where aspect_fk=(select id from aspect where name="Autre");
update aspect set id=10 where name="Autre";


--corolle tâchetée, striée, bicolore
--présence d'un éperon
--pétales nettement échancrés
--forme de cloche, grelot
--type "papillon"
--type orchidée

update particularite set name="Forme de cloche, grelot" where name="Cloche" and lang="fr";
update particularite set name="Pétales nettement échancrés" where name="échancré" and lang="fr";
update particularite set name="Présence d'un éperon" where name="éperon" and lang="fr";
--'
update particularite set name="Type papillon" where name="Papillon" and lang="fr";
update particularite set name="Corolle tâchetée, striée, bicolore" where name="Tachetée" and lang="fr";
update particularite set name="Type orchidée" where name="Orchidée" and lang="fr";


--disposition des feuilles
--Alternes
--Opposées
--Verticillées
--Toutes à la base
--Autre
update disposition_feuille set name="Toutes à la base" where name="à la base" and lang="fr";


--Pilosité de la tige
--Poilue, duveteuse
--Glabre
--Présence d'épines
update pilosite_tige set name="Poilue, duveteuse" where name="Pubescente" and lang="fr";
update pilosite_tige set name="Présence d'épines" where name="épine" and lang="fr";
--'

--Pilosité des feuilles
--Glabre
--Poilue ou duveteuse sur une face
--Poilue ou duveteuse sur les deux faces
update pilosite_feuille set name="Poilue ou duveteuse sur les deux faces" where name="Pubescente2" and lang="fr";
update pilosite_feuille set name="Poilue ou duveteuse sur une face" where name="Pubescente1" and lang="fr";


--couleur
--rose, rouge, mauve, pourpre
--bleu, violet
--jaune, jaunâtre
--brun, marron
--vert, verdâtre
--blanc, blanchâtre
update couleur set name="Blanc, blanchâtre" where name="Blanc" and lang="fr";
update couleur set name="Rose, rouge, mauve, pourpre" where name="Rose" and lang="fr";
update couleur set name="Bleu, violet" where name="Bleu" and lang="fr";
update couleur set name="Jaune, jaunâtre" where name="Jaune" and lang="fr";
update couleur set name="Brun, marron" where name="Marron" and lang="fr";
update couleur set name="Vert, verdâtre" where name="Vert" and lang="fr";

