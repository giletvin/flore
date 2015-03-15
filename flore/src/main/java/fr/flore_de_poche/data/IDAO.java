package fr.flore_de_poche.data;

import java.util.List;

import android.database.Cursor;
import fr.flore_de_poche.bo.MultiCriteriaSearchFormBean;
import fr.flore_de_poche.bo.SimpleSubject;

/**
 * The Interface IOrnidroidDAO.
 */
public interface IDAO {

	/**
	 * The Constant BEAK_FORM_TABLE.
	 * 
	 * @deprecated
	 */
	public static final String BEAK_FORM_TABLE = "beak_form";

	/**
	 * The Constant BIRD_COUNTRY_TABLE. * @deprecated
	 */
	public static final String BIRD_COUNTRY_TABLE = "bird_country";
	/** The Constant SUBJECT_TABLE. */
	public static final String SUBJECT_TABLE = "fleur";

	/**
	 * The Constant CATEGORY_COLUMN. * @deprecated
	 */
	public static final String CATEGORY_COLUMN = "category";

	/** The Constant COLOUR_TABLE. */
	public static final String COLOUR_TABLE = "colour";

	/**
	 * The Constant COUNTRY_TABLE.
	 * 
	 * @deprecated
	 */
	public static final String COUNTRY_TABLE = "country";
	/**
	 * The Constant DESCRIPTION_COLUMN.
	 * 
	 * @deprecated
	 */
	public static final String DESCRIPTION_COLUMN = "description";
	/** The Constant DIRECTORY_NAME_COLUMN. */
	public static final String DIRECTORY_NAME_COLUMN = "directory_name";
	/**
	 * The Constant DISTRIBUTION_COLUMN.
	 * 
	 * @deprecated
	 * */
	public static final String DISTRIBUTION_COLUMN = "distribution";
	/**
	 * The Constant HABITAT_1_NAME_COLUMN.
	 * 
	 * @deprecated
	 */
	public static final String HABITAT_1_NAME_COLUMN = "habitat1";
	/**
	 * The Constant HABITAT_2_NAME_COLUMN.
	 * 
	 * @deprecated
	 */
	public static final String HABITAT_2_NAME_COLUMN = "habitat2";
	/** The Constant ID. */
	public static final String ID = "id";
	/** The Constant LANG_COLUMN_NAME. */
	public static final String LANG_COLUMN_NAME = "lang";
	/** The Constant NAME_FIELD_NAME. */
	public static final String NAME_COLUMN_NAME = "name";

	/**
	 * The Constant OISEAUX_NET_COLUMN.
	 * 
	 * @deprecated
	 */
	public static final String OISEAUX_NET_COLUMN = "oiseaux_net_link";
	/**
	 * The Constant REMARKABLE_SIGN_TABLE.
	 * 
	 * @deprecated
	 */
	public static final String REMARKABLE_SIGN_TABLE = "remarkable_sign";
	/** The Constant SCIENTIFIC_FAMILY_NAME_COLUMN. */
	public static final String SCIENTIFIC_FAMILY_NAME_COLUMN = "scientific_family";
	/** The Constant SCIENTIFIC_FAMILY_TABLE. */
	public static final String SCIENTIFIC_FAMILY_TABLE = "scientific_family";

	/** The Constant SCIENTIFIC_NAME. */
	public static final String SCIENTIFIC_NAME = "scientific_name";

	/**
	 * The Constant SEARCHED_TAXON. The taxon where diacritics are removed. Ex
	 * taxon "Bécassine" is "Becassine" in this column
	 */
	public static final String SEARCHED_TAXON = "searched_taxon";

	/**
	 * The Constant SIZE_TABLE.
	 * 
	 * @deprecated
	 */
	public static final String SIZE_TABLE = "size_table";

	/**
	 * The Constant SIZE_VALUE_COLUMN.
	 * 
	 * @deprecated
	 */
	public static final String SIZE_VALUE_COLUMN = "size_value";

	/** The Constant TAXON. */
	public static final String TAXON = "taxon";

	/**
	 * Gets the beak forms.
	 * 
	 * @return the beak forms
	 * @deprecated
	 */
	Cursor getBeakForms();

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
	 * @deprecated
	 */
	Cursor getCategories();

	/**
	 * Gets the colours.
	 * 
	 * @return the colours
	 */
	Cursor getColours();

	/**
	 * Gets the countries.
	 * 
	 * @return the countries
	 * @deprecated
	 */
	Cursor getCountries();

	/**
	 * Gets the habitats.
	 * 
	 * @return the habitats
	 * @deprecated
	 */
	Cursor getHabitats();

	/**
	 * Gets the multi search criteria count results.
	 * 
	 * @param formBean
	 *            the form bean
	 * @return the multi search criteria count results
	 */
	int getMultiSearchCriteriaCountResults(MultiCriteriaSearchFormBean formBean);

	/**
	 * Gets the remarkable signs.
	 * 
	 * @return the remarkable signs
	 * @deprecated
	 */
	Cursor getRemarkableSigns();

	/**
	 * Gets the sizes.
	 * 
	 * @return the sizes
	 * @deprecated
	 */
	Cursor getSizes();

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

}