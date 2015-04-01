package fr.flore_de_poche.service;

import java.util.List;

import fr.flore_de_poche.bo.MultiCriteriaSearchFormBean;
import fr.flore_de_poche.bo.SimpleSubject;
import fr.flore_de_poche.bo.Subject;
import fr.flore_de_poche.bo.Taxon;
import fr.flore_de_poche.helper.ApplicationException;
import fr.flore_de_poche.helper.SupportedLanguage;

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

	Integer getLeafTypeId(String string);

	List<String> getLeafTypes();

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

	List<String> getInflorescences();

	Integer getInflorescenceId(String inflorescenceName);

	List<String> getParticularites();

	Integer getParticulariteId(String particulariteName);

	/**
	 * Gets the current subject. If a previous call to show subject detail was
	 * already done, get the subject without querying the db
	 * 
	 * @return the current subject
	 */
	Subject getCurrentSubject();

	/**
	 * Gets the aspect id.
	 * 
	 * @param aspectName
	 *            the aspect
	 * @return the aspect id
	 * 
	 */
	Integer getAspectId(String aspectName);

	/**
	 * Gets the aspects.
	 * 
	 * @return the aspects
	 */
	List<String> getAspects();

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

	Integer getLeafDispositionId(String leafDispositionName);

	List<String> getLeafDispositions();

	Integer getNbPetaleId(String nbPetalesName);

	List<String> getNbPetalesList();

	Integer getPilositeTigeId(String pilositeTigeName);

	List<String> getPilositeTigeList();

	Integer getPilositeFeuilleId(String pilositeFeuilleName);

	List<String> getPilositeFeuilleList();

	/**
	 * Gets the wikipedia link of the subject, using the gui language en, fr or
	 * de .wikipedia.org
	 * 
	 * @param currentBird
	 *            the current bird
	 * @return the wikipedia link
	 * 
	 */
	String getWikipediaLink(Subject currentBird, SupportedLanguage lang);

	String getDocUrlLink(Subject currentBird);

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
