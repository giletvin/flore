package fr.flore_de_poche.service;

import java.util.List;

import fr.flore_de_poche.bo.MultiCriteriaSearchFormBean;
import fr.flore_de_poche.bo.SimpleSubject;
import fr.flore_de_poche.bo.Subject;
import fr.flore_de_poche.bo.Taxon;
import fr.flore_de_poche.helper.ApplicationException;

/**
 * The Interface IOrnidroidService.
 */
public interface IService {

	/**
	 * Creates the db if necessary.
	 * 
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	void createDbIfNecessary() throws ApplicationException;

	/**
	 * Gets the beak form id.
	 * 
	 * @param string
	 *            the string
	 * @return the beak form id
	 * @deprecated
	 */
	Integer getBeakFormId(String string);

	/**
	 * Gets the beak forms.
	 * 
	 * @return the beak forms
	 * 
	 * @deprecated
	 */
	List<String> getBeakForms();

	/**
	 * Gets the matching subjects and store the results in the history result
	 * 
	 * @param query
	 *            the query
	 * @return the matching subjects
	 */
	List<SimpleSubject> getMatchingSubjects(String query);

	/**
	 * Gets the matches from the multi criteria search and store the results in
	 * the history result.
	 * 
	 * @param formBean
	 *            the form bean
	 * @return the matches from multi search criteria
	 */
	void getMatchesFromMultiSearchCriteria(MultiCriteriaSearchFormBean formBean);

	/**
	 * Gets the categories to load the select menu of the scientific families.
	 * 
	 * @return the categories
	 * 
	 */
	List<String> getScientificFamilies();

	/**
	 * Gets the getScientificFamilyId.
	 * 
	 * @param pScientifFamilyName
	 *            the category name
	 * @return the scientific family id
	 * 
	 */
	Integer getScientificFamilyId(String pScientifFamilyName);

	/**
	 * Gets the colour id.
	 * 
	 * @param colourName
	 *            the colour name
	 * @return the feather colour id
	 */
	Integer getColourId(String colourName);

	/**
	 * Gets the colours.
	 * 
	 * @return the colours
	 */
	List<String> getColours();

	/**
	 * Gets the inflorescences.
	 * 
	 * @return the inflorescences
	 * 
	 */
	List<String> getInflorescences();

	/**
	 * Gets the inflorescence id.
	 * 
	 * @param inflorescenceName
	 *            the inflorescence name
	 * @return the inflorescence id
	 * 
	 */
	Integer getInflorescenceId(String inflorescenceName);

	/**
	 * Gets the current subject. If a previous call to show subject detail was
	 * already done, get the subject without querying the db
	 * 
	 * @return the current subject
	 */
	Subject getCurrentSubject();

	/**
	 * Gets the habitat id.
	 * 
	 * @param habitatName
	 *            the habitatName
	 * @return the habitat id
	 * @deprecated
	 */
	Integer getHabitatId(String habitatName);

	/**
	 * Gets the habitats.
	 * 
	 * @return the habitats
	 * @deprecated
	 */
	List<String> getHabitats();

	/**
	 * Gets the multi search criteria count results.
	 * 
	 * @param formBean
	 *            the form bean
	 * @return the multi search criteria count results
	 */
	int getMultiSearchCriteriaCountResults(MultiCriteriaSearchFormBean formBean);

	/**
	 * Gets the names of the bird in different languages.
	 * 
	 * @param id
	 *            the id
	 * @return List of taxon
	 */
	List<Taxon> getNames(int id);

	/**
	 * Gets the remarkable sign id.
	 * 
	 * @param remarkableSignName
	 *            the remarkable sign name
	 * @return the remarkable sign id
	 * @deprecated
	 */
	Integer getRemarkableSignId(String remarkableSignName);

	/**
	 * Gets the remarkable signs.
	 * 
	 * @return the remarkable signs
	 * @deprecated
	 */
	List<String> getRemarkableSigns();

	/**
	 * Gets the size id.
	 * 
	 * @param sizeName
	 *            the size name
	 * @return the size id
	 * @deprecated
	 */
	Integer getSizeId(String sizeName);

	/**
	 * Gets the sizes.
	 * 
	 * @return the sizes
	 * @deprecated
	 */
	List<String> getSizes();

	/**
	 * Gets the wikipedia link of the subject, using the gui language en, fr or
	 * de .wikipedia.org
	 * 
	 * @param currentBird
	 *            the current bird
	 * @return the wikipedia link
	 * 
	 */
	String getWikipediaLink(Subject currentBird);

	/**
	 * Checks for history.
	 * 
	 * @return true, if successful
	 */
	boolean hasHistory();

	/**
	 * Load subjectId details.
	 * 
	 * @param subjectId
	 *            the subjectId id
	 */
	void loadSubjectDetails(Integer subjectId);

	/**
	 * Gets the query result.
	 * 
	 * @return the query result
	 */
	List<SimpleSubject> getQueryResult();

	/**
	 * Gets the release notes.
	 * 
	 * @return the release notes
	 */
	String getReleaseNotes();

}
