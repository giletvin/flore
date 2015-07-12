package fr.flore_de_poche.data;

import java.util.List;

import android.database.Cursor;
import fr.flore_de_poche.bo.MultiCriteriaSearchFormBean;
import fr.flore_de_poche.bo.SimpleSubject;

/**
 * The Interface IOrnidroidDAO.
 */
public interface IDAO {

	public static final String LEAF_TYPE_TABLE = "type_feuille";

	public static final String FLEUR_INFLORESCENCE_TABLE = "fleur_inflorescence";
	public static final String FLEUR_ASPECT_TABLE = "fleur_aspect";
	public static final String FLEUR_NB_PETALE_TABLE = "fleur_nb_petale";
	public static final String FLEUR_LEAF_TYPE_TABLE = "fleur_type_feuille";
	public static final String FLEUR_LEAF_DISPOSITION_TABLE = "fleur_disposition_feuille";
	public static final String FLEUR_COLOUR_TABLE = "fleur_couleur";
	public static final String FLEUR_PILOSITE_FEUILLE = "fleur_pilosite_feuille";
	public static final String FLEUR_PILOSITE_TIGE = "fleur_pilosite_tige";
	public static final String FLEUR_PARTICULARITE_TABLE = "fleur_particularite";

	public static final String SUBJECT_TABLE = "fleur";

	/**
	 * The Constant CATEGORY_COLUMN. * @deprecated
	 */
	public static final String CATEGORY_COLUMN = "category";

	/** The Constant COLOUR_TABLE. */
	public static final String COLOUR_TABLE = "couleur";

	/**
	 * The Constant INFLORESCENCE_TABLE.
	 * 
	 * 
	 */
	public static final String INFLORESCENCE_TABLE = "inflorescence";
	/**
	 * The Constant DESCRIPTION_COLUMN.
	 * 
	 * @deprecated
	 */
	public static final String DESCRIPTION_COLUMN = "description";
	/** The Constant DIRECTORY_NAME_COLUMN. */
	public static final String DIRECTORY_NAME_COLUMN = "directory_name";

	/** The Constant ID. */
	public static final String ID = "id";
	/** The Constant LANG_COLUMN_NAME. */
	public static final String LANG_COLUMN_NAME = "lang";
	/** The Constant NAME_FIELD_NAME. */
	public static final String NAME_COLUMN_NAME = "name";
	public static final String ORDRE_COLUMN_NAME = "ordre";

	public static final String LEAF_DISPOSITION_TABLE = "disposition_feuille";

	public static final String SCIENTIFIC_FAMILY_NAME_COLUMN = "scientific_family";

	public static final String SCIENTIFIC_FAMILY_TABLE = "scientific_family";

	public static final String SCIENTIFIC_NAME = "scientific_name";

	/**
	 * The Constant SEARCHED_TAXON. The taxon where diacritics are removed. Ex
	 * taxon "Bécassine" is "Becassine" in this column
	 */
	public static final String SEARCHED_TAXON = "searched_taxon";

	public static final String NB_PETALE_TABLE = "nb_petale";
	public static final String PILOSITE_TIGE_TABLE = "pilosite_tige";
	public static final String PILOSITE_FEUILLE_TABLE = "pilosite_feuille";
	public static final String PARTICULARITE_TABLE = "particularite";

	/** The Constant TAXON. */
	public static final String TAXON = "taxon";

	public static final String DOC_URL_COLUMN = "doc_url";

	Cursor getLeafTypes();

	/**
	 * Returns a Cursor positioned at the subject specified by rowId.
	 * 
	 * @param rowId
	 *            id of subject to retrieve
	 * @return Cursor positioned to matching bird, or null if not found.
	 */
	Cursor getSubject(String rowId);

	/**
	 * Gets the matches from multi search criteria and stores the results in the
	 * history stack.
	 * 
	 * @param formBean
	 *            the form bean
	 * @return the bird matches from multi search criteria
	 */
	List<SimpleSubject> getMatchesFromMultiSearchCriteria(
			MultiCriteriaSearchFormBean formBean);

	/**
	 * Gets the subject names in different languages.
	 * 
	 * @param id
	 *            the id of the subject
	 * @return cursor on the results of the query. Can be null if something goes
	 *         wrong
	 */
	Cursor getSubjectNames(Integer id);

	/**
	 * Gets the categories.
	 * 
	 * @return the categories
	 * 
	 */
	Cursor getScientificFamilies();

	/**
	 * Gets the colours.
	 * 
	 * @return the colours
	 */
	Cursor getColours();

	Cursor getInflorescences();

	Cursor getAspects();

	/**
	 * Gets the multi search criteria count results.
	 * 
	 * @param formBean
	 *            the form bean
	 * @return the multi search criteria count results
	 */
	int getMultiSearchCriteriaCountResults(MultiCriteriaSearchFormBean formBean);

	Cursor getLeafDispositions();

	Cursor getNbPetale();

	Cursor getPilositeTige();

	Cursor getPilositeFeuille();

	/**
	 * gets the countries where the bird can be seen
	 * 
	 * @param id
	 *            the id of the bird
	 * @return cursor on the results of the query. Can be null if something goes
	 *         wrong
	 * @deprecated
	 */
	Cursor getGeographicDistribution(int id);

	/**
	 * Gets the matching birds.
	 * 
	 * @param query
	 *            the query
	 * @return the matching birds
	 */
	List<SimpleSubject> getMatchingSubjects(String query);

	/**
	 * Gets the release notes.
	 * 
	 * @return the release notes
	 */
	String getReleaseNotes();

	Cursor getParticularites();

}