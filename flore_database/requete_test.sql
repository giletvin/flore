explain query plan
   ...> select count(*) from fleur f
   ...> inner join fleur_couleur fc on fc.fleur_fk=f.id
   ...> inner join couleur c on c.id=fc.couleur_fk
   ...> inner join taxonomy t on t.fleur_fk=f.id
   ...> inner join fleur_aspect fa on fa.fleur_fk=f.id
   ...> inner join aspect a on a.id=fa.aspect_fk
   ...> inner join scientific_family sf on f.scientific_family_fk=sf.id
   ...> inner join fleur_inflorescence fi on fi.fleur_fk=f.id
   ...> inner join inflorescence i on i.id=fi.inflorescence_fk
   ...> inner join fleur_pilosite_tige ftp on ftp.fleur_fk=f.id
   ...> inner join pilosite_tige tp on tp.id=ftp.pilosite_tige_fk
   ...> inner join fleur_disposition_feuille fdf on fdf.fleur_fk=f.id
   ...> inner join disposition_feuille dp on fdf.disposition_feuille_fk=dp.id
   ...> inner join fleur_nb_petale fnp on fnp.fleur_fk=f.id
   ...> inner join nb_petale np on fnp.nb_petale_fk=np.id
   ...> where 
   ...> i.name='Groupe'
   ...> --and a.name='Pser'
   ...> and c.name='Jaune'
   ...> and tp.name='Glabre'
   ...> and t.lang='fr'
   ...> and dp.name='Opposées'
   ...> and np.name='5';
