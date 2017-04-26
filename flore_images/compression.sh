#!/bin/bash
#set -x
#script de compression d'images
#ce script convertit à la volée l'encoding des fichiers de properties vers du ASCII avec la commande native2ascii
#pre requis : gimp 2.6 et le script-fu compress.scm dans le répertoire ~/.gimp-2.6/scripts
#sudo apt-get exiv2
#@param 1 : repertoire source
#@param 2 : repertoire destination : si ce répertoire existe, il est effacé pour être recréé en début de script

#compression batch gimp
#@param 1 : path de l'image
#@param 2 : qualite (0.5 par exemple)
function compression_gimp {
	gimp -i -b "(simple-compress \""$1"\" $2)" -b '(gimp-quit 0)'
	
}

EXPECTED_ARGS=2
E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Script de compression d'images"
  echo "Usage: `basename $0` {dir_src} {dir_dest}"
  exit $E_BADARGS
fi


export DIR_SRC=$1
export DIR_COMPRESSION=$2


rm -rf $DIR_COMPRESSION
mkdir $DIR_COMPRESSION

export QUALITY_GIMP=0.5
#3008 : largeur des photos du d70
export MAX_RESOLUTION=2000


for repertoire in `ls $DIR_SRC`
do
	echo $repertoire
	#copie du repertoire source
	cp -r $DIR_SRC/$repertoire $DIR_COMPRESSION
	for picturefile in `ls $DIR_COMPRESSION/$repertoire/*.jpg`
	do
		echo "### " "$picturefile"
		#3 compression du fichier si necessaire
		hauteur=`identify -verbose  $picturefile | grep "ImageLength" | tr -d " " | cut -d ":" -f 3 | tail -n 1`
		largeur=`identify -verbose  $picturefile | grep "ImageWidth" | tr -d " " | cut -d ":" -f 3 | tail -n 1`

		if [[ "$hauteur" -gt "$MAX_RESOLUTION"  ||  "$largeur" -gt "$MAX_RESOLUTION" ]]; then
			echo "compression et reduction de resolution!"
			mogrify -resize ${MAX_RESOLUTION}x${MAX_RESOLUTION} $picturefile
		else
			echo "compression uniquement!"
		fi
		#supprimer les exif du fichier 
		#exiftool -all= $picturefile
		#suppression du fichier _original généré par la commande exiftool
		#rm ${picturefile}_original

		compression_gimp $picturefile $QUALITY_GIMP
		if [[ -f ${picturefile}.properties ]] ; then
			echo "fichier de properties deja present"
		else
			cp template.image.FLORE.properties ${picturefile}.properties
		fi
		#conversion en ascii du fichier de properties associé
		native2ascii ${picturefile}.properties ${picturefile}.properties

	done


done

