package fr.flore_de_poche.bo;

public class SimpleSubject {

	protected SimpleSubject() {
	}

	public String getTaxon() {
		return taxon;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	protected void setTaxon(String taxon) {
		this.taxon = taxon;
	}

	private Integer id;

	private String taxon;

	private String directoryName;

	private String scientificName;

	private String docUrl;

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String pDocUrl) {
		this.docUrl = pDocUrl;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	protected void setDirectoryName(String pDirectoryName) {
		this.directoryName = pDirectoryName;
	}

	@Override
	public String toString() {
		return taxon;
	}
}
