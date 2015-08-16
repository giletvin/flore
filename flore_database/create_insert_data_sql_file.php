<?php

/**
* suppression des caractères accentués d'une string
* @param $str string
* @return string sans les caractères accentués
*/
function removeDiacritics($str) {
	return str_replace(array (
		"à",
		"â",
		"ä",
		"å",
		"ã",
		"á",
		"Â",
		"Ä",
		"À",
		"Å",
		"Ã",
		"Á",
		"æ",
		"Æ",
		"ç",
		"Ç",
		"é",
		"è",
		"ê",
		"ë",
		"É",
		"Ê",
		"Ë",
		"È",
		"ï",
		"î",
		"ì",
		"í",
		"Ï",
		"Î",
		"Ì",
		"Í",
		"ñ",
		"Ñ",
		"ö",
		"ô",
		"ó",
		"ò",
		"õ",
		"Ó",
		"Ô",
		"Ö",
		"Ò",
		"Õ",
		"ù",
		"û",
		"ü",
		"ú",
		"Ü",
		"Û",
		"Ù",
		"Ú",
		"ý",
		"ÿ",
		"Ÿ"
	), array (
		"a",
		"a",
		"a",
		"a",
		"a",
		"a",
		"A",
		"A",
		"A",
		"A",
		"A",
		"A",
		"a",
		"A",
		"c",
		"C",
		"e",
		"e",
		"e",
		"e",
		"E",
		"E",
		"E",
		"E",
		"i",
		"i",
		"i",
		"i",
		"I",
		"I",
		"I",
		"I",
		"n",
		"N",
		"o",
		"o",
		"o",
		"o",
		"o",
		"O",
		"O",
		"O",
		"O",
		"O",
		"u",
		"u",
		"u",
		"u",
		"U",
		"U",
		"U",
		"U",
		"y",
		"y",
		"Y"
	), $str);
}



/*
* renvoie une string en minuscule avec la premier lettre en maj
*/
function normalizeString($string){
	return ucfirst(strtolower(trim($string)));
}




/*
* Collecte les données référentielles dans le tab array_data
* @param array_data : tableau des données référentielles
* @data : cellule du fichier csv qu'on analyse
* @return : un tableau contenant les indexes des données pour générer la requête SQL d'insert
*/
function collectData(&$array_data,$data){
	$indexes = array();
	if ($data!=''){
		$data_tab = explode(",", $data);

		foreach ($data_tab as $key => $value){
			$value=	normalizeString($value);
			if (!in_array($value,$array_data)){
				array_push($array_data,$value);
			}
			array_push($indexes,array_search($value,$array_data));
		}
		
	}
	return $indexes;
}

/*
* MAIN
*/


$array_scientific_family = array();
$array_inflorescence = array();
$array_couleur = array();
$array_particularite = array();
$array_aspect = array();
$array_nb_petales = array();
$array_type_feuille = array();
$array_disposition_feuille = array();
$array_pilosite_tige = array();
$array_pilosite_feuille = array();

$repertoire_index=0;
$doc_url_index=1;
$nom_fr_1_index=2;
$nom_fr_2_index=3;
$nom_fr_3_index=4;
$nom_scientifique_1_index=5;
$nom_scientifique_2_index=6;
$nom_en_index=7;
$famille_index=8;
$inflorescence_index=10;
$couleur_1_index=12;
$couleur_2_index=13;
$particularite_index=15;
$aspect_index=16;
$nb_petale_index=17;
$type_feuille_index=19;
$disposition_feuille_index=20;
$pilosite_tige_index=21;
$pilosite_feuille_index=22;

$idFleur=0;
$sql_queries = array();
if (($handle = fopen("Fleurs.csv", "r")) !== FALSE) {
    while (($data = fgetcsv($handle, 1000, "|")) !== FALSE) {
	if ($idFleur>0){
		//attention : si on ajoute une colonne dans le csv, changer ce test !
		if (count($data)==24&&$data[$nom_scientifique_1_index]!=''){
			$indexes_scientific_family= collectData($array_scientific_family,$data[$famille_index]);
			//Attention : inflorescence, plusieurs valeurs dans la colonne	
			$indexes_inflorescence=collectData($array_inflorescence,$data[$inflorescence_index]);
			$indexes_couleur_1=collectData($array_couleur,$data[$couleur_1_index]);
			$indexes_couleur_2=collectData($array_couleur,$data[$couleur_2_index]);
			$indexes_particularite=collectData($array_particularite,$data[$particularite_index]);
			//Attention : aspect, plusieurs valeurs dans la colonne	
			$indexes_aspect=collectData($array_aspect,$data[$aspect_index]);
			//Attention : nb petales, plusieurs valeurs dans la colonne	
			$indexes_nb_petale=collectData($array_nb_petales,$data[$nb_petale_index]);
			//Attention : type de feuille: plusieurs valeurs possibles dans une colonne
			$indexes_type_feuille=collectData($array_type_feuille,$data[$type_feuille_index]);
			//Attention : disposition de feuille: plusieurs valeurs possibles dans une colonne
			$indexes_disposition_feuille=collectData($array_disposition_feuille,$data[$disposition_feuille_index]);
			//Attention : pilosité de la tige: plusieurs valeurs possibles dans une colonne
			$indexes_pilosite_tige=collectData($array_pilosite_tige,$data[$pilosite_tige_index]);
			//Attention : pilosité de la feuille: plusieurs valeurs possibles dans une colonne
			$indexes_pilosite_feuille=collectData($array_pilosite_feuille,$data[$pilosite_feuille_index]);
			/*id integer,
			directory_name,
			scientific_family_fk*/
			array_push($sql_queries,"insert into fleur (id,directory_name,scientific_name,scientific_family_fk,doc_url) values (".$idFleur.",\"".trim($data[$repertoire_index])."\",\"".trim($data[$nom_scientifique_1_index])."\",".$indexes_scientific_family[0].",\"".trim($data[$doc_url_index])."\");");
			//langues
			array_push($sql_queries,"insert into taxonomy (lang, taxon, searched_taxon, fleur_fk, taxon_usuel) values (\"fr\",\"".trim($data[$nom_fr_1_index])."\",\"".strtolower(removeDiacritics(trim($data[$nom_fr_1_index])))."\",".$idFleur.",1);");
			if ($data[$nom_fr_2_index]!=''){
				array_push($sql_queries,"insert into taxonomy (lang, taxon, searched_taxon, fleur_fk, taxon_usuel) values (\"fr\",\"".trim($data[$nom_fr_2_index])."\",\"".strtolower(removeDiacritics(trim($data[$nom_fr_2_index])))."\",".$idFleur.",0);");
			}
			if ($data[$nom_fr_3_index]!=''){
				array_push($sql_queries,"insert into taxonomy (lang, taxon, searched_taxon, fleur_fk, taxon_usuel) values (\"fr\",\"".trim($data[$nom_fr_3_index])."\",\"".strtolower(removeDiacritics(trim($data[$nom_fr_3_index])))."\",".$idFleur.",0);");
			}
			array_push($sql_queries,"insert into taxonomy (lang, taxon, searched_taxon, fleur_fk, taxon_usuel) values (\"la\",\"".trim($data[$nom_scientifique_1_index])."\",\"".strtolower(removeDiacritics(trim($data[$nom_scientifique_1_index])))."\",".$idFleur.",1);");
			if ($data[$nom_scientifique_2_index]!=''){
				array_push($sql_queries,"insert into taxonomy (lang, taxon, searched_taxon, fleur_fk, taxon_usuel) values (\"la\",\"".trim($data[$nom_scientifique_2_index])."\",\"".strtolower(removeDiacritics(trim($data[$nom_scientifique_2_index])))."\",".$idFleur.",0);");
			}
			if ($data[$nom_en_index]!=''){
				array_push($sql_queries,"insert into taxonomy (lang, taxon, searched_taxon, fleur_fk, taxon_usuel) values (\"en\",\"".trim($data[$nom_en_index])."\",\"".strtolower(removeDiacritics(trim($data[$nom_en_index])))."\",".$idFleur.",1);");
			}
			//inflorescence
			if ($data[$inflorescence_index]!=''){
				foreach ($indexes_inflorescence as $key => $value){
					array_push($sql_queries,"insert into fleur_inflorescence (fleur_fk, inflorescence_fk) values (".$idFleur.",".$value.");");
				}
			}
			//couleur
			if ($data[$couleur_1_index]!=''){
				array_push($sql_queries,"insert into fleur_couleur (fleur_fk, couleur_fk) values (".$idFleur.",".$indexes_couleur_1[0].");");
			}
			if ($data[$couleur_2_index]!=''){
				array_push($sql_queries,"insert into fleur_couleur (fleur_fk, couleur_fk) values (".$idFleur.",".$indexes_couleur_2[0].");");
			}
			//particularite
			if ($data[$particularite_index]!=''){
				foreach ($indexes_particularite as $key => $value){
					array_push($sql_queries,"insert into fleur_particularite (fleur_fk, particularite_fk) values (".$idFleur.",".$value.");");
				}
			}
			//aspect
			if ($data[$aspect_index]!=''){
				foreach ($indexes_aspect as $key => $value){
					array_push($sql_queries,"insert into fleur_aspect (fleur_fk, aspect_fk) values (".$idFleur.",".$value.");");
				}
			}
			//nb_petale
			if ($data[$nb_petale_index]!=''){
				foreach ($indexes_nb_petale as $key => $value){
					array_push($sql_queries,"insert into fleur_nb_petale (fleur_fk, nb_petale_fk) values (".$idFleur.",".$value.");");
				}
			}
			//type_feuille
			if ($data[$type_feuille_index]!=''){
				foreach ($indexes_type_feuille as $key => $value){
					array_push($sql_queries,"insert into fleur_type_feuille (fleur_fk, type_feuille_fk) values (".$idFleur.",".$value.");");
				}
			}
			//disposition_feuille
			if ($data[$disposition_feuille_index]!=''){
				foreach ($indexes_disposition_feuille as $key => $value){
					array_push($sql_queries,"insert into fleur_disposition_feuille (fleur_fk, disposition_feuille_fk) values (".$idFleur.",".$value.");");
				}
			}
			//pilosite_tige
			if ($data[$pilosite_tige_index]!=''){
				foreach ($indexes_pilosite_tige as $key => $value){
					array_push($sql_queries,"insert into fleur_pilosite_tige (fleur_fk, pilosite_tige_fk) values (".$idFleur.",".$value.");");
				}
			}
			//pilosite_feuille
			if ($data[$pilosite_feuille_index]!=''){
				foreach ($indexes_pilosite_feuille as $key => $value){
					array_push($sql_queries,"insert into fleur_pilosite_feuille (fleur_fk, pilosite_feuille_fk) values (".$idFleur.",".$value.");");
				}
			}

		}
		else {
			//echo "erreur sur la ligne : ".$idFleur. " ". print_r($data);
		}
	}
	$idFleur++;
    }
    fclose($handle);
}

///////////////////////////////////
//ECRITURE DES DONNEES REF
$handlerTableReferentiel = fopen('generate_insert_data_table_referentiel.sql', 'w');

foreach ($array_scientific_family as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO  scientific_family(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}


foreach ($array_inflorescence as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO inflorescence(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}


foreach ($array_couleur as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO couleur(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}

foreach ($array_particularite as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO particularite(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}

foreach ($array_aspect as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO aspect(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}

foreach ($array_nb_petales as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO nb_petale(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}
foreach ($array_type_feuille as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO type_feuille(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}
foreach ($array_disposition_feuille as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO disposition_feuille(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}
foreach ($array_pilosite_tige as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO pilosite_tige(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}
foreach ($array_pilosite_feuille as $key => $value){
	//commandes
	$sql_insert_query = "INSERT INTO pilosite_feuille(id,name,lang) VALUES(".$key.",\"".$value."\",'fr');\n";
	fwrite($handlerTableReferentiel, $sql_insert_query);
}
fclose($handlerTableReferentiel);


///////////////////////////////////
//ECRITURE DES DONNEES FLEURS
//print_r($sql_queries);
$handlerTableFleurs = fopen('generate_insert_data_table_fleurs.sql', 'w');
foreach ($sql_queries as $key => $value){
	fwrite($handlerTableFleurs, $value."\n");
}


fclose($handlerTableFleurs);





?>
