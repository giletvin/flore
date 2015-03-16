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
import fr.flore_de_poche.helper.I18nHelper;
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

		/** The map name code. */
		private final Map<String, String> mapNameCode;
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
			this.mapNameCode = pMapNameCode;
		}

		/**
		 * Gets the map name code.
		 * 
		 * @return the map name code
		 */
		public Map<String, String> getMapNameCode() {
			return this.mapNameCode;
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

	/** The activity. */
	private final Activity activity;

	/** The beak forms list. */
	private List<String> beakFormsList;
	/** The beak forms maps. */
	private Map<String, Integer> beakFormsMaps;

	/** The categories list. */
	private List<String> scientificFamiliesList;

	/** The categories map. */
	private Map<String, Integer> scientificFamiliesMap;

	/** The colours list. */
	private List<String> coloursList;

	/** The colours map. */
	private Map<String, Integer> coloursMap;

	/** The countries list. */
	private List<String> inflorescencesList;

	private Map<String, Integer> inflorescencesMap;

	/** The current bird. */
	private Subject currentSubject;

	/** The data base open helper. */
	private final DatabaseOpenHelper dataBaseOpenHelper;

	/** The habitats list. */
	private List<String> habitatsList;

	/** The habitats map. */
	private Map<String, Integer> habitatsMap;

	/** The ornidroid dao. */
	private final IDAO ornidroidDAO;

	/** The remarkable signs list. */
	private List<String> remarkableSignsList;

	/** The remarkable signs map. */
	private Map<String, Integer> remarkableSignsMap;

	/** The sizes list. */
	private List<String> sizesList;

	/** The sizes map. */
	private Map<String, Integer> sizesMap;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getBeakFormId(java.lang.String
	 * )
	 */
	public Integer getBeakFormId(final String beakFormName) {
		return this.beakFormsMaps != null ? this.beakFormsMaps
				.get(beakFormName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getBeakForms()
	 */
	public List<String> getBeakForms() {
		if (this.beakFormsMaps == null) {
			// Find the names of the beak forms in the selected language
			final Cursor cursorQueryHabitats = this.ornidroidDAO.getBeakForms();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.beakFormsMaps = sfv.getMapNameId();
			this.beakFormsList = sfv.getFieldsValues();

		}
		return this.beakFormsList;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getCategoryId(java.lang.String
	 * )
	 */
	public Integer getScientificFamilyId(final String categoryName) {
		return this.scientificFamiliesMap != null ? this.scientificFamiliesMap
				.get(categoryName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getColourId(java.lang.String)
	 */
	public Integer getColourId(final String colourName) {
		return this.coloursMap != null ? this.coloursMap.get(colourName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getColours()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getCountryCode(java.lang.
	 * String)
	 */
	public Integer getInflorescenceId(final String inflorescenceName) {
		return this.inflorescencesMap != null ? this.inflorescencesMap
				.get(inflorescenceName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getCurrentBird()
	 */
	public Subject getCurrentSubject() {
		return this.currentSubject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getHabitatId(java.lang.String
	 * )
	 */
	public Integer getHabitatId(final String habitatName) {
		return this.habitatsMap != null ? this.habitatsMap.get(habitatName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getHabitats()
	 */
	public List<String> getHabitats() {
		if (this.habitatsMap == null) {
			final Cursor cursorQueryHabitats = this.ornidroidDAO.getHabitats();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.habitatsMap = sfv.getMapNameId();
			this.habitatsList = sfv.getFieldsValues();

		}
		return this.habitatsList;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getRemarkableSignId(java.
	 * lang. String)
	 */
	public Integer getRemarkableSignId(final String remarkableSignName) {
		return this.remarkableSignsMap != null ? this.remarkableSignsMap
				.get(remarkableSignName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getRemarkableSigns()
	 */
	public List<String> getRemarkableSigns() {
		if (this.remarkableSignsMap == null) {
			final Cursor cursorQueryHabitats = this.ornidroidDAO
					.getRemarkableSigns();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQueryHabitats, true);
			this.remarkableSignsMap = sfv.getMapNameId();
			this.remarkableSignsList = sfv.getFieldsValues();

		}
		return this.remarkableSignsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.service.IOrnidroidService#getSizeId(java.lang.String)
	 */
	public Integer getSizeId(final String sizeName) {
		return this.sizesMap != null ? this.sizesMap.get(sizeName) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getSizes()
	 */
	public List<String> getSizes() {
		if (this.sizesMap == null) {
			final Cursor cursorQuerySizes = this.ornidroidDAO.getSizes();
			final SelectFieldsValue sfv = loadSelectFieldsFromCursor(
					cursorQuerySizes, true);
			this.sizesMap = sfv.getMapNameId();
			this.sizesList = sfv.getFieldsValues();

		}
		return this.sizesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.service.IOrnidroidService#getWikipediaLink(fr.
	 * flore_de_poche. bo.Bird)
	 */
	public String getWikipediaLink(final Subject currentSubject) {
		final SupportedLanguage lang = I18nHelper.getLang();
		final StringBuffer sbuf = new StringBuffer();
		sbuf.append("<a href=\"http://");
		sbuf.append(lang.getCode());
		sbuf.append(".wikipedia.org/wiki/");
		sbuf.append(currentSubject.getScientificName().replaceAll(" ", "%20"));
		sbuf.append("\">");
		sbuf.append(Constants.getCONTEXT().getResources()
				.getString(R.string.wikipedia));
		sbuf.append(currentSubject.getTaxon());
		sbuf.append("</a>");
		return sbuf.toString();
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

			final SubjectFactoryImpl subjectFactory = new SubjectFactoryImpl();
			this.currentSubject = subjectFactory.createSubject(
					cursor.getInt(idIndex), cursor.getString(taxonIndex),
					cursor.getString(scientificNameIndex),

					cursor.getString(directoryNameIndex));
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
		mapNameId.put(this.activity.getString(R.string.search_all), 0);
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
