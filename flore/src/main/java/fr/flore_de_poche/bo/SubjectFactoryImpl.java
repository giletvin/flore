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

	/**
	 * Creates the bird.
	 * 
	 * @param id
	 *            the id
	 * @param taxon
	 *            the taxon
	 * @param scientificName
	 *            the scientific name
	 * 
	 * @param directoryName
	 *            the directory name
	 * 
	 * @return the bird
	 */
	public Subject createSubject(final Integer id, final String taxon,
			final String scientificName, final String directoryName) {
		final Subject bird = new Subject();
		bird.setId(id);
		bird.setTaxon(taxon);
		bird.setScientificName(scientificName);
		bird.setDirectoryName(directoryName);

		return bird;
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
