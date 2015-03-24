package fr.flore_de_poche.ui.multicriteriasearch;

import fr.flore.R;

/**
 * The Class SpinnerIconSelector. This is a helper that allows to display icons
 * in the spinners.
 */
public class SpinnerIconSelector {

	/**
	 * Gets the icon resource id from inflorescenceid. This is very dependant to
	 * the sql data. Maps the inflorescence id to an icon.
	 * 
	 * @param inflorescenceId
	 *            the inflorescenceId
	 * @return the icon resource name from inflorescenceId
	 */
	public static int getIconResourceIdFromInflorescenceId(
			final int inflorescenceId) {

		// TODO : là pour les icones d'inflorescences
		int resourceId = 0;

		switch (inflorescenceId) {
		case 0:
			resourceId = R.drawable.ic_inflorescence_groupe;
			break;
		case 1:
			resourceId = R.drawable.inflorescence_solitaire;
			break;
		default:
			resourceId = R.drawable.tous;
			break;
		}
		return resourceId;
	}

	/**
	 * INSERT INTO aspect(id,name,lang) VALUES(0,"Pser",'fr'); INSERT INTO
	 * aspect(id,name,lang) VALUES(1,"Ctl",'fr'); INSERT INTO
	 * aspect(id,name,lang) VALUES(2,"Psi",'fr'); INSERT INTO
	 * aspect(id,name,lang) VALUES(3,"Psli",'fr'); INSERT INTO
	 * aspect(id,name,lang) VALUES(4,"Clot",'fr'); INSERT INTO
	 * aspect(id,name,lang) VALUES(5,"Autre",'fr'); INSERT INTO
	 * aspect(id,name,lang) VALUES(6,"Psle",'fr');
	 * 
	 * @param pAspectId
	 * @return
	 */
	public static int getIconResourceIdFromAspectId(final int pAspectId) {

		int resourceId = 0;

		switch (pAspectId) {
		case 0:
			resourceId = R.drawable.aspect_pser;
			break;
		case 1:
			resourceId = R.drawable.aspect_ctl;
			break;
		case 2:
			resourceId = R.drawable.aspect_psi;
			break;
		case 3:
			resourceId = R.drawable.aspect_psli;
			break;
		case 4:
			resourceId = R.drawable.aspect_clot;
			break;
		case 5:
			resourceId = R.drawable.tous;
			break;
		case 6:
			resourceId = R.drawable.aspect_psle;
			break;

		default:
			resourceId = R.drawable.tous;
			break;
		}
		return resourceId;
	}

	/**
	 * INSERT INTO pilosite_feuille(id,name,lang) VALUES(0,"Glabre",'fr');
	 * INSERT INTO pilosite_feuille(id,name,lang) VALUES(1,"Pubescente2",'fr');
	 * INSERT INTO pilosite_feuille(id,name,lang) VALUES(2,"Pubescente1",'fr');
	 * 
	 * @param pPilositeFeuilleId
	 * @return
	 */
	public static int getIconResourceIdFromPilositeFeuilleId(
			final int pPilositeFeuilleId) {

		int resourceId = 0;

		switch (pPilositeFeuilleId) {
		case 0:
			resourceId = R.drawable.pilosite_feuille_glabre;
			break;
		case 1:
			resourceId = R.drawable.pilosite_feuille_pubescent;
			break;

		case 2:
			resourceId = R.drawable.pilosite_feuille_pubescent;
			break;
		default:
			resourceId = R.drawable.tous;
			break;
		}
		return resourceId;
	}

	/**
	 * INSERT INTO type_feuille(id,name,lang) VALUES(0,"Composée",'fr'); INSERT
	 * INTO type_feuille(id,name,lang) VALUES(1,"Divisée",'fr'); INSERT INTO
	 * type_feuille(id,name,lang) VALUES(2,"Simple dentée",'fr'); INSERT INTO
	 * type_feuille(id,name,lang) VALUES(3,"Simple entière",'fr'); INSERT INTO
	 * type_feuille(id,name,lang) VALUES(4,"Autre",'fr');
	 * 
	 * @param id
	 * @return
	 */
	public static int getIconResourceIdFromLeafTypeId(final int id) {

		int resourceId = 0;

		switch (id) {
		case 0:
			resourceId = R.drawable.type_feuille_composee;
			break;
		case 1:
			resourceId = R.drawable.type_feuille_divisee;
			break;
		case 2:
			resourceId = R.drawable.type_feuille_dentee;
			break;
		case 3:
			resourceId = R.drawable.type_feuille_entiere;
			break;

		default:
			resourceId = R.drawable.tous;
			break;
		}
		return resourceId;
	}
}
