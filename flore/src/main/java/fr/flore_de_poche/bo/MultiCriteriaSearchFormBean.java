package fr.flore_de_poche.bo;

import fr.flore_de_poche.helper.BasicConstants;

/**
 * The Class MultiCriteriaSearchFormBean. This class handles the values selected
 * by the user in the multi criteria search screen.
 */
public class MultiCriteriaSearchFormBean {

	private Integer leafTypeId;

	private Integer scientificFamilyId;

	private Integer inflorescenceId;

	private Integer colourId;

	private Integer aspectId;
	private Integer particulariteId;

	public Integer getParticulariteId() {
		return this.particulariteId != null ? this.particulariteId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public void setParticulariteId(Integer pParticulariteId) {
		this.particulariteId = pParticulariteId;
	}

	private Integer leafDispositionId;

	private Integer nbPetaleId;

	private Integer pilositeFeuilleId;

	private Integer pilositeTigeId;

	public Integer getPilositeFeuilleId() {
		return this.pilositeFeuilleId != null ? this.pilositeFeuilleId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getPilositeTigeId() {
		return this.pilositeTigeId != null ? this.pilositeTigeId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getLeafTypeId() {
		return this.leafTypeId != null ? this.leafTypeId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getScientificFamilyId() {
		return this.scientificFamilyId != null ? this.scientificFamilyId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getInflorescenceId() {
		return this.inflorescenceId != null ? this.inflorescenceId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getColourId() {
		return this.colourId != null ? this.colourId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getAspectId() {
		return this.aspectId != null ? this.aspectId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public Integer getLeafDispositionId() {

		return this.leafDispositionId != null ? this.leafDispositionId
				: BasicConstants.DEFAULT_EMPTY_VALUE;

	}

	public Integer getNbPetaleId() {
		return this.nbPetaleId != null ? this.nbPetaleId
				: BasicConstants.DEFAULT_EMPTY_VALUE;
	}

	public void setLeafTypeId(final Integer pLeafTypeId) {
		this.leafTypeId = pLeafTypeId;

	}

	public void setScientificFamilyId(final Integer pScientificFamilyId) {
		this.scientificFamilyId = pScientificFamilyId;

	}

	public void setInflorescenceId(final Integer pInflorescenceId) {
		this.inflorescenceId = pInflorescenceId;
	}

	public void setColourId(final Integer pColourId) {
		this.colourId = pColourId;
	}

	public void setAspectId(final Integer pAspectId) {
		this.aspectId = pAspectId;

	}

	public void setLeafDispositionId(final Integer pLeafDispositionId) {
		this.leafDispositionId = pLeafDispositionId;
	}

	public void setNbPetaleId(final Integer pNbPetaleId) {
		this.nbPetaleId = pNbPetaleId;
	}

	public void setPilositeFeuilleId(final Integer pPilositeFeuilleId) {
		this.pilositeFeuilleId = pPilositeFeuilleId;
	}

	public void setPilositeTigeId(final Integer pPilositeTigeId) {
		this.pilositeTigeId = pPilositeTigeId;
	}

}
