package fr.flore_de_poche.data;

import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import fr.flore_de_poche.bo.MultiCriteriaSearchFormBean;
import fr.flore_de_poche.bo.SimpleSubject;
import fr.flore_de_poche.bo.SubjectFactoryImpl;
import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.Constants;
import fr.flore_de_poche.helper.I18nHelper;
import fr.flore_de_poche.helper.StringHelper;
import fr.flore_de_poche.helper.SupportedLanguage;

/**
 * Contains sql queries to search for birds in the database.
 */
public class DAOImpl implements IDAO {
	/** The subject factory. */
	private final SubjectFactoryImpl subjectFactory;

	/**
	 * The Class SqlDynamicFragments. Contains the sql code snippets where and
	 * from
	 */
	private class SqlDynamicFragments {

		/** The from clause. */
		private final String fromClause;

		/** The where clause. */
		private final String whereClause;

		/**
		 * Instantiates a new sql dynamic fragments.
		 * 
		 * @param pWhereClause
		 *            the where clause
		 * @param pFromClause
		 *            the from clause
		 */
		public SqlDynamicFragments(final String pWhereClause,
				final String pFromClause) {
			this.whereClause = pWhereClause;
			this.fromClause = pFromClause;
		}

		/**
		 * Gets the from clause.
		 * 
		 * @return the from clause
		 */
		public String getFromClause() {
			return this.fromClause;
		}

		/**
		 * Gets the where clause.
		 * 
		 * @return the where clause
		 */
		public String getWhereClause() {
			return this.whereClause;
		}

	}

	/** The Constant AS. */
	private static final String AS = " as ";

	/** The Constant COUNT_STAR. */
	private static final String COUNT_STAR = " count(*) ";

	/** The Constant FROM. */
	private static final String FROM = " from ";

	/** The Constant FTS_VIRTUAL_TABLE_TAXONOMY. */
	private static final String FTS_VIRTUAL_TABLE_TAXONOMY = "taxonomy";

	private static final String ASPECT_TABLE_NAME = "aspect";

	/** The Constant INNER_JOIN. */
	private static final String INNER_JOIN = " inner join ";

	/** The Constant LEFT_OUTER_JOIN. */
	private static final String LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";

	/** The Constant ORDER_BY. */
	private static final String ORDER_BY = " order by ";

	/** The Constant SELECT. */
	private static final String SELECT = "select ";

	/** The Constant AND. */
	private static final String AND = " and ";

	/** The singleton. */
	private static IDAO singleton;

	/** The Constant WHERE. */
	private static final String WHERE = " where ";

	/**
	 * Gets the single instance of OrnidroidDAOImpl.
	 * 
	 * @return single instance of OrnidroidDAOImpl
	 */
	public static IDAO getInstance() {

		return singleton;
	}

	/**
	 * Gets the single instance of OrnidroidDAOImpl. If it doesn't exist, create
	 * it.
	 * 
	 * @param dataBaseOpenHelper
	 *            the data base open helper
	 * @return single instance of OrnidroidDAOImpl
	 */
	public static IDAO getInstance(final DatabaseOpenHelper dataBaseOpenHelper) {
		if (null == singleton) {
			singleton = new DAOImpl(dataBaseOpenHelper);
		}
		return singleton;
	}

	/** The data base open helper. */
	private final DatabaseOpenHelper dataBaseOpenHelper;

	/**
	 * Constructor.
	 * 
	 * @param pDataBaseOpenHelper
	 *            the data base open helper
	 */
	private DAOImpl(final DatabaseOpenHelper pDataBaseOpenHelper) {
		this.dataBaseOpenHelper = pDataBaseOpenHelper;

		this.subjectFactory = new SubjectFactoryImpl();
	}

	public Cursor getLeafTypes() {
		return getCursorFromListTable(LEAF_TYPE_TABLE, ID, I18nHelper.getLang());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getBird(java.lang.String)
	 */
	public Cursor getSubject(final String rowId) {
		final String whereClause = WHERE + SUBJECT_TABLE + ".id = ?";
		final String[] selectionArgs = new String[] { rowId };

		return query(new SqlDynamicFragments(whereClause,
				Constants.EMPTY_STRING), selectionArgs, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.data.IOrnidroidDAO#getBirdMatchesFromMultiSearchCriteria
	 * (fr.flore_de_poche.bo.MultiCriteriaSearchFormBean)
	 */
	public List<SimpleSubject> getMatchesFromMultiSearchCriteria(
			final MultiCriteriaSearchFormBean formBean) {

		final Cursor cursor = query(getSqlDynamicFragments(formBean, true),
				null, false);
		return getSubjectListFromCursor(cursor);

	}

	/**
	 * Gets the subject list from cursor and close the cursor once the subjects
	 * are instanciated
	 * 
	 * @param cursor
	 *            the cursor
	 * @return the subject list from cursor
	 */
	private List<SimpleSubject> getSubjectListFromCursor(final Cursor cursor) {
		List<SimpleSubject> results = new ArrayList<SimpleSubject>();
		if (null == cursor) {
			return results;
		}

		final int nbRouws = cursor.getCount();
		for (int i = 0; i < nbRouws; i++) {
			cursor.moveToPosition(i);
			final int idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID);

			final String taxon = cursor.getString(cursor
					.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
			final String directoryName = cursor.getString(cursor
					.getColumnIndex(IDAO.DIRECTORY_NAME_COLUMN));
			final String scientificName = cursor.getString(cursor
					.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2));

			final SimpleSubject bird = this.subjectFactory.createSimpleBird(
					cursor.getInt(idIndex), taxon, directoryName,
					scientificName);
			results.add(bird);

		}
		cursor.close();
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getBirdNames(java.lang.Integer)
	 */
	public Cursor getSubjectNames(final Integer id) {
		Cursor cursor = null;
		try {
			final SQLiteDatabase db = this.dataBaseOpenHelper
					.getReadableDatabase();
			final StringBuilder query = new StringBuilder();
			query.append(SELECT);
			query.append(LANG_COLUMN_NAME);
			query.append(Constants.COMMA_STRING);
			query.append(TAXON);
			query.append(" from taxonomy where fleur_fk=");
			query.append(id);
			query.append(ORDER_BY);
			query.append(LANG_COLUMN_NAME);
			// Log.d(Constants.LOG_TAG, "Perform SQL query " +
			// query.toString());
			final String[] selectionArgs = null;
			cursor = db.rawQuery(query.toString(), selectionArgs);
			if (cursor == null) {
				return null;
			} else if (!cursor.moveToFirst()) {
				cursor.close();
				return null;
			}
		} catch (final SQLException e) {
			// Log.e(Constants.LOG_TAG, "Exception sql " + e);
		} finally {
			this.dataBaseOpenHelper.close();
		}
		return cursor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getCategories()
	 */
	public Cursor getScientificFamilies() {
		return getCursorFromListTable(SCIENTIFIC_FAMILY_TABLE,
				NAME_COLUMN_NAME, I18nHelper.getLang());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getColours()
	 */
	public Cursor getColours() {
		return getCursorFromListTable(COLOUR_TABLE, NAME_COLUMN_NAME,
				I18nHelper.getLang());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getCountries()
	 */
	public Cursor getInflorescences() {
		return getCursorFromListTable(INFLORESCENCE_TABLE, NAME_COLUMN_NAME,
				I18nHelper.getLang());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getHabitats()
	 */
	public Cursor getAspects() {
		return getCursorFromListTable(ASPECT_TABLE_NAME, ORDRE_COLUMN_NAME,
				I18nHelper.getLang());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.data.IOrnidroidDAO#getMultiSearchCriteriaCountResults
	 * (fr .ornidroid.bo.MultiCriteriaSearchFormBean)
	 */
	public int getMultiSearchCriteriaCountResults(
			final MultiCriteriaSearchFormBean formBean) {
		final StringBuilder countQuery = new StringBuilder();
		final SqlDynamicFragments sqlFragments = getSqlDynamicFragments(
				formBean, false);
		countQuery.append(SELECT).append(COUNT_STAR).append(FROM)
				.append(SUBJECT_TABLE).append(sqlFragments.getFromClause())
				.append(sqlFragments.getWhereClause());
		final SQLiteDatabase db = this.dataBaseOpenHelper.getReadableDatabase();
		final int countResults = (int) DatabaseUtils.longForQuery(db,
				countQuery.toString(), null);
		db.close();
		return countResults;
	}

	public Cursor getLeafDispositions() {
		return getCursorFromListTable(LEAF_DISPOSITION_TABLE, ID,
				I18nHelper.getLang());
	}

	public Cursor getNbPetale() {
		return getCursorFromListTable(NB_PETALE_TABLE, NAME_COLUMN_NAME,
				I18nHelper.getLang());
	}

	public Cursor getPilositeTige() {
		return getCursorFromListTable(PILOSITE_TIGE_TABLE, ID,
				I18nHelper.getLang());
	}

	public Cursor getPilositeFeuille() {
		return getCursorFromListTable(PILOSITE_FEUILLE_TABLE, ID,
				I18nHelper.getLang());
	}

	/**
	 * Gets the cursor from list table with ID AND NAME column NAMEs (habitat,
	 * category).
	 * 
	 * @param tableName
	 *            the table name (habitat, category, ..)
	 * @param orderBy
	 *            the column used in the order by
	 * @param lang
	 *            the lang
	 * @return the cursor from list table
	 */
	private Cursor getCursorFromListTable(final String tableName,
			final String orderBy, final SupportedLanguage lang) {
		Cursor cursor = null;
		try {
			final SQLiteDatabase db = this.dataBaseOpenHelper
					.getReadableDatabase();
			final StringBuilder query = new StringBuilder();
			query.append(SELECT);
			query.append(ID);
			query.append(Constants.COMMA_STRING);
			query.append(NAME_COLUMN_NAME);
			query.append(FROM);
			query.append(tableName);
			query.append(WHERE).append("lang=\"");
			query.append(lang.getCode());
			query.append("\"");
			query.append(ORDER_BY);
			query.append(orderBy);
			final String[] selectionArgs = null;
			cursor = db.rawQuery(query.toString(), selectionArgs);
			if (cursor == null) {
				return null;
			} else if (!cursor.moveToFirst()) {
				// no results
				cursor.close();
				if (lang.equals(SupportedLanguage.FRENCH)) {
					return null;
				} else {
					// if not found in the locale of the user, try the same
					// search in FRENCH
					return getCursorFromListTable(tableName, orderBy,
							SupportedLanguage.FRENCH);
				}
			}
		} catch (final SQLException e) {
			Log.e(Constants.LOG_TAG, "Exception sql " + e);
		} finally {
			this.dataBaseOpenHelper.close();
		}
		return cursor;
	}

	/**
	 * Gets the where sql clauses according to the fields completed by the user
	 * in the Form bean.
	 * 
	 * @param formBean
	 *            the form bean
	 * @param resultQuery
	 *            context of the query : results (true) or count(*) query
	 *            (false)
	 * @return the where sql clauses
	 */
	private SqlDynamicFragments getSqlDynamicFragments(
			final MultiCriteriaSearchFormBean formBean,
			final boolean resultQuery) {
		final StringBuffer whereClauses = new StringBuffer();
		final StringBuffer fromClauses = new StringBuffer();
		whereClauses.append(WHERE).append("1=1");

		if (resultQuery) {
			// elimine les doublons avec des noms différents ds les resultats de
			// recherche mcs
			whereClauses.append(AND).append("taxonomy.taxon_usuel=1");
		}

		if (formBean.getScientificFamilyId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			whereClauses.append(
					" AND " + SUBJECT_TABLE + ".scientific_family_fk = ")
					.append(formBean.getScientificFamilyId());

		}
		if (formBean.getLeafDispositionId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_LEAF_DISPOSITION_TABLE)
						.append(" fl where fl.fleur_fk=fleur.id and fl.disposition_feuille_fk=")
						.append(formBean.getLeafDispositionId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_LEAF_DISPOSITION_TABLE)
						.append(" on " + FLEUR_LEAF_DISPOSITION_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_LEAF_DISPOSITION_TABLE
								+ ".disposition_feuille_fk=").append(
						formBean.getLeafDispositionId());
			}
		}
		if (formBean.getAspectId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_ASPECT_TABLE)
						.append(" fa where fa.fleur_fk=fleur.id and fa.aspect_fk=")
						.append(formBean.getAspectId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_ASPECT_TABLE)
						.append(" on " + FLEUR_ASPECT_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_ASPECT_TABLE + ".aspect_fk=").append(
						formBean.getAspectId());
			}

		}
		if (formBean.getParticulariteId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_PARTICULARITE_TABLE)
						.append(" fp where fp.fleur_fk=fleur.id and fp.particularite_fk=")
						.append(formBean.getParticulariteId()).append(")");
			} else {

				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_PARTICULARITE_TABLE)
						.append(" on " + FLEUR_PARTICULARITE_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_PARTICULARITE_TABLE
								+ ".particularite_fk=").append(
						formBean.getParticulariteId());
			}
		}
		if (formBean.getColourId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_COLOUR_TABLE)
						.append(" fc where fc.fleur_fk=fleur.id and fc.couleur_fk=")
						.append(formBean.getColourId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_COLOUR_TABLE)
						.append(" on " + FLEUR_COLOUR_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_COLOUR_TABLE + ".couleur_fk=").append(
						formBean.getColourId());
			}
		}

		if (formBean.getLeafTypeId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_LEAF_TYPE_TABLE)
						.append(" f where f.fleur_fk=fleur.id and f.type_feuille_fk=")
						.append(formBean.getLeafTypeId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_LEAF_TYPE_TABLE)
						.append(" on " + FLEUR_LEAF_TYPE_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_LEAF_TYPE_TABLE + ".type_feuille_fk=")
						.append(formBean.getLeafTypeId());
			}
		}
		if (formBean.getNbPetaleId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_NB_PETALE_TABLE)
						.append(" f where f.fleur_fk=fleur.id and f.nb_petale_fk=")
						.append(formBean.getNbPetaleId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_NB_PETALE_TABLE)
						.append(" on " + FLEUR_NB_PETALE_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_NB_PETALE_TABLE + ".nb_petale_fk=")
						.append(formBean.getNbPetaleId());
			}
		}
		if (formBean.getPilositeFeuilleId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_PILOSITE_FEUILLE)
						.append(" f where f.fleur_fk=fleur.id and f.pilosite_feuille_fk=")
						.append(formBean.getPilositeFeuilleId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_PILOSITE_FEUILLE)
						.append(" on " + FLEUR_PILOSITE_FEUILLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_PILOSITE_FEUILLE
								+ ".pilosite_feuille_fk=").append(
						formBean.getPilositeFeuilleId());
			}
		}
		if (formBean.getPilositeTigeId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_PILOSITE_TIGE)
						.append(" f where f.fleur_fk=fleur.id and f.pilosite_tige_fk=")
						.append(formBean.getPilositeTigeId()).append(")");
			} else {
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_PILOSITE_TIGE)
						.append(" on " + FLEUR_PILOSITE_TIGE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_PILOSITE_TIGE + ".pilosite_tige_fk=")
						.append(formBean.getPilositeTigeId());
			}
		}
		if (formBean.getInflorescenceId() != BasicConstants.DEFAULT_EMPTY_VALUE) {
			if (resultQuery) {
				// result query faster with this sql code with a subquery
				// and exists
				whereClauses
						.append(" and exists (select 1 from ")
						.append(FLEUR_INFLORESCENCE_TABLE)
						.append(" fi where fi.fleur_fk=fleur.id and fi.inflorescence_fk=")
						.append(formBean.getInflorescenceId()).append(")");
			} else {
				// count query faster with this sql code
				fromClauses
						.append(INNER_JOIN)
						.append(FLEUR_INFLORESCENCE_TABLE)
						.append(" on " + FLEUR_INFLORESCENCE_TABLE
								+ ".fleur_fk=fleur.id");
				whereClauses.append(
						" AND " + FLEUR_INFLORESCENCE_TABLE
								+ ".inflorescence_fk=").append(
						formBean.getInflorescenceId());

			}
		}

		return new SqlDynamicFragments(whereClauses.toString(),
				fromClauses.toString());
	}

	/**
	 * Performs a database query.
	 * 
	 * @param sqlDynamicFragments
	 *            the sql dynamic fragments
	 * @param selectionArgs
	 *            Selection arguments for "?" components in the selection
	 * @param fullBirdInfo
	 *            if true, all info about the birds are requested and will be
	 *            stored in the cursor
	 * @return A Cursor over all rows matching the query
	 */
	private Cursor query(final SqlDynamicFragments sqlDynamicFragments,
			final String[] selectionArgs, final boolean fullBirdInfo) {
		Cursor cursor = null;
		try {
			final SQLiteDatabase db = this.dataBaseOpenHelper
					.getReadableDatabase();
			final StringBuilder query = new StringBuilder();
			query.append("select fleur.id as ");
			query.append(BaseColumns._ID);
			query.append(",scientific_name as ");
			query.append(SearchManager.SUGGEST_COLUMN_TEXT_2);
			query.append(",taxon as ");
			query.append(SearchManager.SUGGEST_COLUMN_TEXT_1);
			query.append(", fleur.id as ");
			query.append(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
			query.append(Constants.COMMA_STRING);
			query.append(DIRECTORY_NAME_COLUMN);
			query.append(Constants.COMMA_STRING);
			query.append(DOC_URL_COLUMN);
			query.append(FROM);
			query.append(FTS_VIRTUAL_TABLE_TAXONOMY);
			query.append(",");
			query.append(SUBJECT_TABLE);
			query.append(sqlDynamicFragments.getFromClause());
			if (fullBirdInfo) {
				// join on scientific family table
				query.append(LEFT_OUTER_JOIN);
				query.append(SCIENTIFIC_FAMILY_TABLE);
				query.append(" on ");
				query.append(SUBJECT_TABLE);
				query.append(".scientific_family_fk=");
				query.append(SCIENTIFIC_FAMILY_TABLE);
				query.append(".id and ");
				query.append(SCIENTIFIC_FAMILY_TABLE);
				query.append(".lang=\"");
				query.append(I18nHelper.getLang().getCode());
				query.append("\"");
			}
			query.append(sqlDynamicFragments.getWhereClause());
			query.append(" and fleur.id=taxonomy.fleur_fk");
			query.append(handleSetOfLanguagesinSqlQuery(Constants
					.getOrnidroidSearchLanguages()));

			query.append(" order by searched_taxon");
			// Log.d(Constants.LOG_TAG, "Perform SQL query " +
			// query.toString());
			cursor = db.rawQuery(query.toString(), selectionArgs);
			if (cursor == null) {
				return null;
			} else if (!cursor.moveToFirst()) {
				cursor.close();
				return null;
			}
		} catch (final SQLException e) {

			// Log.e(Constants.LOG_TAG, "Exception sql " + e);
		} finally {
			this.dataBaseOpenHelper.close();
		}
		return cursor;

	}

	/**
	 * Handle set of languagesin sql query.
	 * 
	 * @param ornidroidSearchLanguages
	 *            the ornidroid search languages
	 * @return the string
	 */
	private String handleSetOfLanguagesinSqlQuery(
			List<String> ornidroidSearchLanguages) {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(" and taxonomy.lang in (\"\"");
		for (String lang : ornidroidSearchLanguages) {
			sbuf.append(",\"");
			sbuf.append(lang);
			sbuf.append("\"");
		}
		sbuf.append(")");
		return sbuf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getGeographicDistribution(int)
	 */
	public Cursor getGeographicDistribution(int id) {
		Cursor cursor = null;
		try {
			final SQLiteDatabase db = this.dataBaseOpenHelper
					.getReadableDatabase();
			final StringBuilder query = new StringBuilder();
			String countryNameColumn = NAME_COLUMN_NAME
					+ Constants.UNDERSCORE_STRING
					+ I18nHelper.getLang().getCode();
			query.append(SELECT);
			query.append(countryNameColumn);
			query.append(AS);
			query.append(NAME_COLUMN_NAME);
			query.append(FROM);
			query.append(INFLORESCENCE_TABLE);
			query.append(INNER_JOIN);
			query.append(FLEUR_INFLORESCENCE_TABLE);
			query.append(" on country_code=code");
			query.append(WHERE);
			query.append("bird_fk=");
			query.append(id);
			query.append(ORDER_BY);
			query.append(NAME_COLUMN_NAME);

			final String[] selectionArgs = null;
			cursor = db.rawQuery(query.toString(), selectionArgs);
			if (cursor == null) {
				return null;
			} else if (!cursor.moveToFirst()) {
				cursor.close();
				return null;
			}
		} catch (final SQLException e) {
			// Log.e(Constants.LOG_TAG, "Exception sql " + e);
		} finally {
			this.dataBaseOpenHelper.close();
		}
		return cursor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.flore_de_poche.data.IOrnidroidDAO#getMatchingBirds(java.lang.String)
	 */
	public List<SimpleSubject> getMatchingSubjects(String pQuery) {
		List<SimpleSubject> results = new ArrayList<SimpleSubject>();
		final StringBuffer whereClause = new StringBuffer().append(WHERE)
				.append(SEARCHED_TAXON).append(" MATCH ?");
		final String[] selectionArgs = new String[] { prepareSearchedString(pQuery) };
		final Cursor cursor = query(
				new SqlDynamicFragments(whereClause.toString(),
						Constants.EMPTY_STRING), selectionArgs, false);
		if (StringHelper.isBlank(pQuery) || null == cursor) {
			return results;
		}

		return getSubjectListFromCursor(cursor);

	}

	/**
	 * Prepare searched string to be used in the 'match' sqlite clause.
	 * <ul>
	 * <li>adds "*" at the end of the string
	 * <li>remove diacritics
	 * <li>escape the '-' character
	 * </ul>
	 * 
	 * @param searchedString
	 *            the searched string
	 * @return the prepared string
	 */
	private String prepareSearchedString(String searchedString) {
		return StringHelper.replace(StringHelper.stripAccents(searchedString),
				BasicConstants.DASH_STRING, "'-'", -1)
				+ BasicConstants.STAR_STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.data.IOrnidroidDAO#getReleaseNotes()
	 */
	public String getReleaseNotes() {
		Cursor cursor = null;
		String releaseNotes = null;
		try {
			final SQLiteDatabase db = this.dataBaseOpenHelper
					.getReadableDatabase();

			String commentsColumn = "comments_"
					+ I18nHelper.getLang().getCode();
			final StringBuilder query = new StringBuilder();
			query.append(SELECT);
			query.append(commentsColumn);
			query.append(FROM);
			query.append("release_notes");
			query.append(WHERE);
			query.append("version_code=");
			query.append(Constants.getVersionCode());
			query.append(AND);
			query.append("read=0");

			final String[] selectionArgs = null;
			cursor = db.rawQuery(query.toString(), selectionArgs);

			if (cursor.moveToFirst()) {
				int commentsColumnIndex = cursor.getColumnIndex(commentsColumn);
				releaseNotes = cursor.getString(commentsColumnIndex);
			}
		} catch (final SQLException e) {
			// Log.e(Constants.LOG_TAG, "Exception sql " + e);
		} finally {
			this.dataBaseOpenHelper.close();
			if (cursor != null) {
				cursor.close();
			}
		}
		if (StringHelper.isNotBlank(releaseNotes)) {
			updateReadReleaseNoteFlag(Constants.getVersionCode());

		}
		return releaseNotes;
	}

	/**
	 * Update read release note flag.
	 * 
	 * @param versionCode
	 *            the version code
	 */
	private void updateReadReleaseNoteFlag(int versionCode) {
		try {
			final SQLiteDatabase db = this.dataBaseOpenHelper
					.getWritableDatabase();

			String strSQL = "UPDATE release_notes SET read = 1 WHERE version_code="
					+ Constants.getVersionCode();
			db.execSQL(strSQL);
			// db.setTransactionSuccessful();
			// db.endTransaction();
		} catch (final SQLException e) {

		} finally {
			this.dataBaseOpenHelper.close();
		}

	}

	@Override
	public Cursor getParticularites() {
		return getCursorFromListTable(PARTICULARITE_TABLE, NAME_COLUMN_NAME,
				I18nHelper.getLang());
	}
}
