package fr.ornidroid.bo;

/**
 * The Class BirdFactoryImpl.
 */
public class BirdFactoryImpl {

	/**
	 * Instantiates a new bird factory impl.
	 * 
	 */
	public BirdFactoryImpl() {

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
	 * @param birdDirectoryName
	 *            the directory name
	 * @param description
	 *            the description
	 * @param distribution
	 *            the distribution
	 * 
	 * @param scientificFamily
	 *            the scientific family
	 * @param habitat
	 *            the habitat
	 * @param size
	 *            the size
	 * @param category
	 *            the category
	 * @param pOiseauxNetUrl
	 *            the oiseaux net url
	 * @return the bird
	 */
	public Subject createBird(final Integer id, final String taxon,
			final String scientificName, final String birdDirectoryName,
			final String description, final String distribution,
			final String scientificFamily, final String habitat,
			final String size, final String category,
			final String pOiseauxNetUrl) {
		final Subject bird = new Subject();
		bird.setId(id);
		bird.setTaxon(taxon);
		bird.setDescription(description);
		bird.setDistribution(distribution);
		bird.setScientificName(scientificName);
		bird.setScientificFamily(scientificFamily);
		bird.setBirdDirectoryName(birdDirectoryName);
		bird.setHabitat(habitat);
		bird.setSize(size);
		bird.setCategory(category);
		bird.setOiseauxNetUrl(pOiseauxNetUrl);
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
		bird.setBirdDirectoryName(birdDirectoryName);
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
	 * @return the bird
	 * @deprecated
	 */
	public SimpleSubject createSimpleBird(final Integer id, final String taxon,
			final String birdDirectoryName) {
		final SimpleSubject bird = new SimpleSubject();
		bird.setId(id);
		bird.setTaxon(taxon);

		bird.setBirdDirectoryName(birdDirectoryName);
		return bird;
	}

}
