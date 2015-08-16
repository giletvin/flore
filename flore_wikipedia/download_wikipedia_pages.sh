#!/bin/sh


export DATABASE_NAME=../flore_database/flore.jpg
export WIKIPEDIA_ROOT_DIR=wikipedia
export WIKIPEDIA_ROOT_DIR_FR=$WIKIPEDIA_ROOT_DIR/fr/
export WIKIPEDIA_ROOT_DIR_EN=$WIKIPEDIA_ROOT_DIR/en/
#rm -rf $WIKIPEDIA_ROOT_DIR
rm *.tmp
mkdir -p $WIKIPEDIA_ROOT_DIR_FR
mkdir -p $WIKIPEDIA_ROOT_DIR_EN

#echo 'Ulex europaeus'> names.txt
#echo 'Trifolium subterraneum' >> names.txt
#echo 'select scientific_name from fleur;'|sqlite3 $DATABASE_NAME >names.txt

while read line
do
	#recherche du 2e nom latin au cas ou
	scientific_name_2=`echo "select taxon from taxonomy where lang='la' and taxon!='$line' and fleur_fk=(select fleur_fk from taxonomy where taxon='$line');"|sqlite3 $DATABASE_NAME`

	scientific_name=`echo $line | sed 's/\ /_/g'`
	#rm $scientific_name*
	echo "$scientific_name ---- $scientific_name_2"
	wget -q -k http://fr.wikipedia.org/wiki/$scientific_name
	retour_wget=$?
	if [ $retour_wget != 0 ]
	then
		echo "####Page Wikipedia FR inexistante pour $scientific_name"
		echo "essai avec le 2e nom scientifique $scientific_name_2"
		scientific_name=`echo $scientific_name_2 | sed 's/\ /_/g'`
		if [ ! -z $scientific_name ]
		then
			wget -q -k http://fr.wikipedia.org/wiki/$scientific_name
			retour_wget=$?
			if [ $retour_wget != 0 ]
			then
				echo "####Page Wikipedia FR inexistante pour le 2e nom scientifique $scientific_name"
				echo "<html>Pas de page Wikipedia disponible">$scientific_name
				echo "Menu de navigation">>$scientific_name
			fi
		else
			echo "Pas de 2e nom scientifique"
			scientific_name=`echo $line | sed 's/\ /_/g'`
			echo "<html>Pas de page Wikipedia disponible">$scientific_name
			echo "Menu de navigation">>$scientific_name
		fi
	fi
	if [ -f $scientific_name ]
	then
		echo "page wiki ok pour $scientific_name ... transformation"		
		php transform_wikipedia_file.php $scientific_name fr > $scientific_name.tmp
		echo ""> $scientific_name
		#extraire la ligne a partir de laquelle on peut tailler
		final_line=`grep -n 'Menu de navigation' $scientific_name.tmp | cut -d ":" -f1`
		i=0
		#recopier chaque ligne du fichier telecharge jusqu'à la final_line
		while read wikipedia_line
		do
			i=`expr $i + 1`
			if [ "$i" -lt "$final_line" ];then
				echo $wikipedia_line >> $scientific_name 
			fi
		done < $scientific_name.tmp
		rm $scientific_name.tmp
		#fermer proprement la balise html
		echo "</html>" >> $scientific_name
		final_name=`echo $line | sed 's/\ /_/g'`
		mv $scientific_name $WIKIPEDIA_ROOT_DIR_FR/$final_name
	fi

	#ENGLISH
	scientific_name=`echo $line | sed 's/\ /_/g'`
#	rm $scientific_name*
	echo $scientific_name
	wget -q -k http://en.wikipedia.org/wiki/$scientific_name
	retour_wget=$?
	if [ $retour_wget != 0 ]
	then 
		echo "####Page Wikipedia EN inexistante pour $scientific_name"
		scientific_name=`echo $scientific_name_2 | sed 's/\ /_/g'`
		echo "essai avec le 2e nom scientifique $scientific_name"
		if [ ! -z $scientific_name ]
		then
			wget -q -k http://en.wikipedia.org/wiki/$scientific_name
			retour_wget=$?
			if [ $retour_wget != 0 ]
			then
				echo "####Page Wikipedia EN inexistante pour le 2e nom scientifique $scientific_name"
				echo "<html>Wikipedia article not available">$scientific_name
				echo "Navigation menu">>$scientific_name
			fi
		else
			echo "Pas de 2e nom scientifique"
			scientific_name=`echo $line | sed 's/\ /_/g'`
			echo "<html>Wikipedia article not available">$scientific_name
			echo "Navigation menu">>$scientific_name
		fi
	fi
	if [ -f $scientific_name ]
	then
		php transform_wikipedia_file.php $scientific_name en > $scientific_name.tmp
		echo ""> $scientific_name
		#extraire la ligne a partir de laquelle on peut tailler
		final_line=`grep -n 'Navigation menu' $scientific_name.tmp | cut -d ":" -f1`
		i=0
		#recopier chaque ligne du fichier telecharge jusqu'à la final_line
		while read wikipedia_line
		do
			i=`expr $i + 1`
			if [ "$i" -lt "$final_line" ];then
				echo $wikipedia_line >> $scientific_name 
			fi
		done < $scientific_name.tmp
		rm $scientific_name.tmp
		#fermer proprement la balise html
		echo "</html>" >> $scientific_name 
		final_name=`echo $line | sed 's/\ /_/g'`
		mv $scientific_name $WIKIPEDIA_ROOT_DIR_EN/$final_name
	fi

	
done < names.txt

