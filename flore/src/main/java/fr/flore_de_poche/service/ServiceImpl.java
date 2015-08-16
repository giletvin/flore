package fr.flore_de_poche.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.SearchManager;
import android.database.Cursor;
import android.provider.BaseColumns;
import fr.flore.R;
import fr.flore_de_poche.bo.MultiCriteriaSearchFormBean;
import fr.flore_de_poche.bo.SimpleSubject;
import fr.flore_de_poche.bo.Subject;
import fr.flore_de_poche.bo.SubjectFactoryImpl;
import fr.flore_de_poche.bo.Taxon;
import fr.flore_de_poche.data.DAOImpl;
import fr.flore_de_poche.data.DatabaseOpenHelper;
import fr.flore_de_poche.data.IDAO;
import fr.flore_de_poche.helper.ApplicationException;
import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.Constants;
import fr.flore_de_poche.helper.StringHelper;
import fr.flore_de_poche.helper.SupportedLanguage;
import fr.flore_de_poche.ui.picture.PictureHelper;

/**
 * The Class OrnidroidServiceImpl.
 */
public class ServiceImpl implements IService {

	/**
	 * The Class SelectFieldsValue : dto object to embed the map and the list of
	 * the select fields. The SQL queries handles the order by clause.
	 */
	private class SelectFieldsValue {

		/** The fields values. */
		private final List<String> fieldsValues;

		/** The map name id. */
		private final Map<String, Integer> mapNameId;

		/**
		 * Instantiates a new select fields value.
		 * 
		 * @param pMapNameId
		 *            the map name id
		 * @param pMapNameCode
		 *            the map name code
		 * @param pFieldValues
		 *            the field values
		 */
		public SelectFieldsValue(final Map<String, Integer> pMapNameId,
				final Map<String, String> pMapNameCode,
				final List<String> pFieldValues) {
			this.mapNameId = pMapNameId;
			this.fieldsValues = pFieldValues;
		}

		/**
		 * Gets the fields values.
		 * 
		 * @return the fields values
		 */
		protected List<String> getFieldsValues() {
			return this.fieldsValues;
		}

		/**
		 * Gets the map name id.
		 * 
		 * @return the map name id
		 */
		protected Map<String, Integer> getMapNameId() {
			return this.mapNameId;
		}
	}

	/** The service instance. */
	private static IService serviceInstance;

	/**
	 * Gets the single instance of OrnidroidServiceImpl.
	 * 
	 * @param pActivity
	 *            the activity
	 * @return single instance of OrnidroidServiceImpl
	 */
	protected static IService getInstance(final Activity pActivity) {
		if (null == serviceInstance) {
			serviceInstance = new ServiceImpl(pActivity);
		}
		return serviceInstance;
	}

	private final Activity activity;

	private List<String> leafTypesList;

	private Map<String, Integer> leafTypesMap;

	private List<String> scientificFamiliesList;

	private Map<String, Integer> scientificFamiliesMap;

	private List<String> coloursList;

	private Map<String, Integer> coloursMap;

	private List<String> inflorescencesList;

	private Map<String, Integer> inflorescencesMap;
	private List<String> particularitesList;

	private Map<String, Integer> particularitesMap;

	private Subject currentSubject;

	/** The data base open helper. */
	private final DatabaseOpenHelper dataBaseOpenHelper;

	private List<String> aspectsList;

	private Map<String, Integer> aspectsMap;

	/** The ornidroid dao. */
	private final IDAO ornidroidDAO;

	private List<String> leafDispositions;

	private Map<String, Integer> leafDispositionsMap;

	private List<String> nbPetalesList;
	private Map<String, Integer> nbPetalesMap;
	private List<String> pilositeTigeList;
	private Map<String, Integer> pilositeTigeMap;
	private List<String> pilositeFeuilleList;
	private Map<String, Integer> pilositeFeuilleMap;

	/** The query result. */
	private List<SimpleSubject> queryResult;

	/**
	 * Instantiates a new ornidroid service impl.
	 * 
	 * @param pActivity
	 *            the activity
	 */
	private ServiceImpl(final Activity pActivity) {
		this.activity = pActivity;
		this.dataBaseOpenHelper = new DatabaseOpenHelper(pActivity);
		this.ornidroidDAO = DAOImpl.getInstance(this.dataBaseOpenHelper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#createDbIfNecessary()
	 */
	public void createDbIfNecessary() throws ApplicationException {
		this.dataBaseOpenHelper.createDbIfNecessary();
	}

	public Integer getLeafTypeId(final String leafTypeName) {
		return this.leafTypesMap != null ? this.leafTypesMap.get(leafTypeName)
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getLeafTypes() {
		if (this.leafTypesMap == null) {
			// Find the names of the beak forms in the selected language
			final Cursor cursorQueryHabitats = this.ornidroidDAO.getLeafTypes();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.leafTypesMap = sfv.getMapNameId();
			this.leafTypesList = sfv.getFieldsValues();

		}
		return this.leafTypesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#
	 * getBirdMatchesFromMultiSearchCriteria
	 * (fr.flore_de_poche.bo.MultiCriteriaSearchFormBean)
	 */
	public void getMatchesFromMultiSearchCriteria(
			final MultiCriteriaSearchFormBean formBean) {
		queryResult = this.ornidroidDAO
				.getMatchesFromMultiSearchCriteria(formBean);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getCategories()
	 */
	public List<String> getScientificFamilies() {
		if (this.scientificFamiliesMap == null) {
			final Cursor cursorQueryHabitats = this.ornidroidDAO
					.getScientificFamilies();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.scientificFamiliesMap = sfv.getMapNameId();
			this.scientificFamiliesList = sfv.getFieldsValues();

		}
		return this.scientificFamiliesList;
	}

	public Integer getScientificFamilyId(final String categoryName) {
		return this.scientificFamiliesMap != null ? this.scientificFamiliesMap
				.get(categoryName) : BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getColourId(final String colourName) {
		return this.coloursMap != null ? this.coloursMap.get(colourName)
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getColours() {
		if (this.coloursMap == null) {
			final Cursor cursorQueryColours = this.ornidroidDAO.getColours();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryColours, true);
			this.coloursMap = sfv.getMapNameId();
			this.coloursList = sfv.getFieldsValues();

		}
		return this.coloursList;
	}

	public List<String> getInflorescences() {
		if (this.inflorescencesMap == null) {
			final Cursor cursorInflorescences = this.ornidroidDAO
					.getInflorescences();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorInflorescences, true);
			this.inflorescencesMap = sfv.getMapNameId();
			this.inflorescencesList = sfv.getFieldsValues();

		}
		return this.inflorescencesList;
	}

	public Integer getInflorescenceId(final String inflorescenceName) {
		return this.inflorescencesMap != null ? this.inflorescencesMap
				.get(inflorescenceName) : BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getParticularites() {
		if (this.particularitesMap == null) {
			final Cursor cursorInflorescences = this.ornidroidDAO
					.getParticularites();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorInflorescences, true);
			this.particularitesMap = sfv.getMapNameId();
			this.particularitesList = sfv.getFieldsValues();

		}
		return this.particularitesList;
	}

	public Integer getParticulariteId(final String particulariteName) {
		return this.particularitesMap != null ? this.particularitesMap
				.get(particulariteName) : BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Subject getCurrentSubject() {
		return this.currentSubject;
	}

	public Integer getAspectId(final String aspectName) {
		return this.aspectsMap != null ? this.aspectsMap.get(aspectName)
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getAspects() {
		if (this.aspectsMap == null) {
			final Cursor cursorQueryHabitats = this.ornidroidDAO.getAspects();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.aspectsMap = sfv.getMapNameId();
			this.aspectsList = sfv.getFieldsValues();

		}
		return this.aspectsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#
	 * getMultiSearchCriteriaCountResults
	 * (fr.flore_de_poche.bo.MultiCriteriaSearchFormBean)
	 */
	public int getMultiSearchCriteriaCountResults(
			final MultiCriteriaSearchFormBean formBean) {
		return this.ornidroidDAO.getMultiSearchCriteriaCountResults(formBean);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getNames(int)
	 */
	public List<Taxon> getNames(final int id) {
		final Cursor cursor = this.ornidroidDAO.getSubjectNames(id);
		final List<Taxon> result = new ArrayList<Taxon>();
		if (cursor != null) {
			final int nbResults = cursor.getCount();
			for (int i = 0; i < nbResults; i++) {
				cursor.moveToPosition(i);
				final int langIndex = cursor
						.getColumnIndexOrThrow(IDAO.LANG_COLUMN_NAME);
				final int taxonIndex = cursor.getColumnIndexOrThrow(IDAO.TAXON);
				final Taxon taxon = new Taxon(cursor.getString(langIndex),
						cursor.getString(taxonIndex));
				result.add(taxon);
			}
			cursor.close();
		}
		return result;
	}

	public Integer getLeafDispositionId(final String leafDispositionName) {
		return this.leafDispositionsMap != null ? this.leafDispositionsMap
				.get(leafDispositionName) : BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getLeafDispositions() {
		if (this.leafDispositionsMap == null) {
			final Cursor cursorQueryHabitats = this.ornidroidDAO
					.getLeafDispositions();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.leafDispositionsMap = sfv.getMapNameId();
			this.leafDispositions = sfv.getFieldsValues();

		}
		return this.leafDispositions;
	}

	public Integer getNbPetaleId(final String nbPetalesName) {
		return this.nbPetalesMap != null ? this.nbPetalesMap.get(nbPetalesName)
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getNbPetalesList() {
		if (this.nbPetalesMap == null) {
			final Cursor cursorQuerySizes = this.ornidroidDAO.getNbPetale();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQuerySizes, true);
			this.nbPetalesMap = sfv.getMapNameId();
			this.nbPetalesList = sfv.getFieldsValues();

		}
		return this.nbPetalesList;
	}

	public Integer getPilositeTigeId(final String pilositeTigeName) {
		return this.pilositeTigeMap != null ? this.pilositeTigeMap
				.get(pilositeTigeName) : BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getPilositeTigeList() {
		if (this.pilositeTigeMap == null) {
			final Cursor cursorQuerySizes = this.ornidroidDAO.getPilositeTige();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQuerySizes, true);
			this.pilositeTigeMap = sfv.getMapNameId();
			this.pilositeTigeList = sfv.getFieldsValues();

		}
		return this.pilositeTigeList;
	}

	public Integer getPilositeFeuilleId(final String pilositeFeuilleName) {
		return this.pilositeFeuilleMap != null ? this.pilositeFeuilleMap
				.get(pilositeFeuilleName) : BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public List<String> getPilositeFeuilleList() {
		if (this.pilositeFeuilleMap == null) {
			final Cursor cursorQuerySizes = this.ornidroidDAO
					.getPilositeFeuille();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQuerySizes, true);
			this.pilositeFeuilleMap = sfv.getMapNameId();
			this.pilositeFeuilleList = sfv.getFieldsValues();

		}
		return this.pilositeFeuilleList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getWikipediaLink(fr.
	 * flore_de_poche. bo.Bird)
	 */
	public String getWikipediaLink(final Subject currentSubject,
			final SupportedLanguage lang) {
		final StringBuffer sbuf = new StringBuffer();
		sbuf.append("<a href=\"http://");
		sbuf.append(lang.getCode());
		sbuf.append(".wikipedia.org/wiki/");
		sbuf.append(currentSubject.getScientificName().replaceAll(" ", "%20"));
		sbuf.append("\">");
		sbuf.append(Constants.getCONTEXT().getResources()
				.getString(R.string.wikipedia));
		sbuf.append(" (").append(lang.getCode()).append(") ");
		sbuf.append(currentSubject.getTaxon());
		sbuf.append("</a>");
		return sbuf.toString();
	}

	public String getDocUrlLink(final Subject currentSubject) {
		if (StringHelper.isNotBlank(currentSubject.getDocUrl())) {

			final StringBuffer sbuf = new StringBuffer();
			sbuf.append("<a href=\"");

			sbuf.append(currentSubject.getDocUrl());
			sbuf.append("\">");
			sbuf.append(Constants.getCONTEXT().getResources()
					.getString(R.string.doc_url));
			sbuf.append(currentSubject.getTaxon());
			sbuf.append("</a>");
			return sbuf.toString();
		} else {
			return BasicConstants.EMPTY_STRING;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#hasHistory()
	 */
	public boolean hasHistory() {
		return (queryResult != null && queryResult.size() > 0);
	}

	/**
	 * Load bird details.
	 * 
	 * @param subjectId
	 *            the bird id
	 */
	public void loadSubjectDetails(final Integer subjectId) {
		final Cursor cursor = this.ornidroidDAO
				.getSubject(subjectId.toString());

		loadDetailsFromCursor(cursor);

	}

	/**
	 * Load bird details from cursor and closes it
	 * 
	 * @param cursor
	 *            the cursor
	 */
	private void loadDetailsFromCursor(final Cursor cursor) {
		if (cursor != null) {
			cursor.moveToFirst();
			final int idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID);

			final int taxonIndex = cursor
					.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
			final int scientificNameIndex = cursor
					.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_2);

			final int directoryNameIndex = cursor
					.getColumnIndexOrThrow(IDAO.DIRECTORY_NAME_COLUMN);
			final int urlDocIndex = cursor
					.getColumnIndexOrThrow(IDAO.DOC_URL_COLUMN);

			final SubjectFactoryImpl subjectFactory = new SubjectFactoryImpl();
			this.currentSubject = subjectFactory.createSubject(
					cursor.getInt(idIndex), cursor.getString(taxonIndex),
					cursor.getString(scientificNameIndex),

					cursor.getString(directoryNameIndex),
					cursor.getString(urlDocIndex));
			// when a new subject arrives, clear the hashmap of stored bitmaps
			PictureHelper.resetLoadedBitmaps();
			cursor.close();
		}

	}

	/**
	 * Load select fields from cursor, used to populate the Spinners and closes
	 * the cursor
	 * 
	 * @param cursor
	 *            the cursor from the DAO, from a select query on the fields ID
	 *            and NAME
	 * @param intId
	 *            true if the id to map from the database is an integer. If
	 *            false, the id is a String (like in the country table)
	 * @return the map, NAME (String)-> ID (Integer)
	 */
	private SelectFieldsValue loadSelectFieldsFromCursor(final Cursor cursor,
			final boolean intId) {
		final Map<String, Integer> mapNameId = new HashMap<String, Integer>();
		final Map<String, String> mapNameCode = new HashMap<String, String>();
		// init the map and the list with "ALL" with id = 0
		mapNameId.put(this.activity.getString(R.string.search_all), -1);
		mapNameCode.put(this.activity.getString(R.string.search_all),
				BasicConstants.EMPTY_STRING);

		final List<String> fieldsValues = new ArrayList<String>();

		fieldsValues.add(this.activity.getString(R.string.search_all));
		if (cursor != null) {
			final int nbResults = cursor.getCount();
			for (int i = 0; i < nbResults; i++) {
				cursor.moveToPosition(i);
				final int idIndex = cursor.getColumnIndexOrThrow(IDAO.ID);
				final int nameIndex = cursor
						.getColumnIndexOrThrow(IDAO.NAME_COLUMN_NAME);

				if (intId) {
					mapNameId.put(cursor.getString(nameIndex),
							cursor.getInt(idIndex));
				} else {
					// in this case, if the id is a string (country code)
					mapNameCode.put(cursor.getString(nameIndex),
							cursor.getString(idIndex));
				}
				fieldsValues.add(cursor.getString(nameIndex));
			}
			cursor.close();
		}
		final SelectFieldsValue sfv = new SelectFieldsValue(mapNameId,
				mapNameCode, fieldsValues);
		return sfv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getMatchingBirds(java.lang
	 * .String)
	 */
	public List<SimpleSubject> getMatchingSubjects(String query) {
		queryResult = this.ornidroidDAO.getMatchingSubjects(query);
		return queryResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getQueryResult()
	 */
	public List<SimpleSubject> getQueryResult() {
		return queryResult == null ? new ArrayList<SimpleSubject>()
				: queryResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getReleaseNotes()
	 */
	public String getReleaseNotes() {
		return ornidroidDAO.getReleaseNotes();
	}
}
