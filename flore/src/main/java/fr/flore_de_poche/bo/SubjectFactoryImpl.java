package fr.flore_de_poche.bo;

/**
 * The Class BirdFactoryImpl.
 */
public class SubjectFactoryImpl {

	/**
	 * Instantiates a new bird factory impl.
	 * 
	 */
	public SubjectFactoryImpl() {

	}

	public Subject createSubject(final Integer id, final String taxon,
			final String scientificName, final String directoryName,
			String docUrl) {
		final Subject subject = new Subject();
		subject.setId(id);
		subject.setTaxon(taxon);
		subject.setScientificName(scientificName);
		subject.setDirectoryName(directoryName);
		subject.setDocUrl(docUrl);
		return subject;
	}

	/**
	 * Creates the simple bird. Only the taxon and the directory name are set
	 * 
	 * @param id
	 *            the id
	 * @param taxon
	 *            the taxon
	 * @param birdDirectoryName
	 *            the bird directory name
	 * @param scientificName
	 *            the scientific name
	 * @return the bird
	 */
	public SimpleSubject createSimpleBird(final Integer id, final String taxon,
			final String birdDirectoryName, final String scientificName) {
		final SimpleSubject bird = new SimpleSubject();
		bird.setId(id);
		bird.setTaxon(taxon);
		bird.setScientificName(scientificName);
		bird.setDirectoryName(birdDirectoryName);
		return bird;
	}

}
