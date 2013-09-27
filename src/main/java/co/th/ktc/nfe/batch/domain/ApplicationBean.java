/**
 * 
 */
package co.th.ktc.nfe.batch.domain;

import java.io.Serializable;

/**
 * @author temp_dev2
 *
 */
public class ApplicationBean implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7751622514680275928L;
	
	private String barcode1;
	private String barcode2;
	private String evidenceType;
	private String citizenId;
	private String cardType;
	private String isApplyMainWithSup;
	private String mainCardNo;
	private String groupProduct;
	private String analyst;
	private String applicantType;
	private String prefixName;
	private String prefixNameOth;
	private String sex;
	private String thaiFName;
	private String thaiLName;
	private String engFName;
	private String engLName;
	private String dob;
	private String isChkNCB;
	private String rushCard;
	private String nationality;
	private String religion;
	private String currentAddressLine1;
	private String currentAddressLine2;
	private String currentAddressProvince;
	private String currentAddressAmphur;
	private String currentAddressDistrict;
	private String currentAddressZipcode;
	private String currentAddressPhoneNo;
	private String currentAddressPhoneNoExt;
	private String currentAddressMobileNo;
	private String currentAddressEmail;
	private String censusAddressLine1;
	private String censusAddressLine2;
	private String censusAddressProvince;
	private String censusAddressAmphur;
	private String censusAddressDistrict;
	private String censusAddressZipcode;
	private String censusAddressPhoneNo;
	private String censusAddressPhoneNoext;
	private String censusAddressMobileNo;
	private String addrTypeYearLive;
	private String addrTypeType;
	private String addrTypeStatus;
	private String addrTypeInstallment;
	private String othersDegree;
	private String othersOccupation;
	private String othersOccupationOth;
	private String othersWorkplace;
	private String marriageStatus;
	private String referenceName;
	private String referenceRelationship;
	private String referenceHomePhoneNo;
	private String referenceWorkPhoneNo;
	private String referenceMobilePhoneNo;
	private String customerType;
	private String staffRateFlag;
	private String monthlyIncome;
	private String otherIncome;
	private String sourceOtherIncome;
	private String slCustomerInfoLoanIncome;
	private String slCustomerInfoCardIncome;
	private String slCustomerInfoPermanentCredit;
	private String slCustomerInfoBillingCycle;
	private String slCustomerInfoEstatementFlag;
	private String productProductId;
	private String productSubproductId;
	private String productEmbossname1;
	private String productEmbossname2;
	private String productPaymentMethod;
	private String productCycleDate;
	private String productCreditLimit;
	private String productPercentInterest;
	private String productCommInterest;
	private String productPromotionRate;
	private String productPromotionTerms;
	private String productTerm;
	private String productCashAdvance;
	private String appOccupationType;
	private String appOccupationTypeOth;
	private String appOccupationBusinessType;
	private String appOccupationBusinessTypeOth;
	private String appOccupationId;
	private String appOccupationOth;
	private String appOccupationWorkplace;
	private String appOccupationJuristicNo;
	private String appOccupationJuristicDe;
	private String appOccupationJuristicRegdate;
	private String appOccupationAddrline1;
	private String appOccupationAddrline2;
	private String appOccupationProvince;
	private String appOccupationAmphur;
	private String appOccupationDistrict;
	private String appOccupationZipcode;
	private String appOccupationPhoneNo1;
	private String appOccupationPhoneNo1Ext;
	private String appOccupationMobileNo;
	private String appOccupationPosition;
	private String appOccupationYearOfWork;
	private String appOccupationRecieveCard;
	private String appOccupationRecieveBranch;
	private String appOccupationRecieveBill;
	private String appOccupationCommunication;
	private String appOccupationSMECustomerNo;
	private String bankAccountBank;
	private String bankAccountBankBranch;
	private String bankAccountAccType;
	private String bankAccountAccName;
	private String bankAccountAccNo;
	private String bankAccountOpenPeriod;
	private String bankAccountTMBalance;
	private String bankAccountLMBalance;
	private String loanAccountAccno;
	private String loanAccountAcctype;
	private String loanAccountBank;
	private String loanAccountIssueDate;
	private String loanAccountExpireDate;
	private String loanAccountCreditLimit;
	private String loanAccountPaymentMethod;
	private String loanAccountInstallment;
	private String transferDebtAccName;
	private String transferDebtAccNo;
	private String transferDebtAccType;
	private String transferDebtBank;
	private String transferDebtLastAmount;
	private String transferLoanAccName;
	private String transferLoanAccNo;
	private String transferLoanAccType;
	private String transferLoanBank;
	private String paymentMethodValue;
	private String paymentMethodAccName;
	private String paymentMethodAccNo;
	private String paymentMethodBank;

	/**
	 * 
	 */
	public ApplicationBean() {
	}

	/**
	 * @return the barcode1
	 */
	public String getBarcode1() {
		return barcode1;
	}

	/**
	 * @param barcode1 the barcode1 to set
	 */
	public void setBarcode1(String barcode1) {
		this.barcode1 = barcode1;
	}

	/**
	 * @return the barcode2
	 */
	public String getBarcode2() {
		return barcode2;
	}

	/**
	 * @param barcode2 the barcode2 to set
	 */
	public void setBarcode2(String barcode2) {
		this.barcode2 = barcode2;
	}

	/**
	 * @return the evidenceType
	 */
	public String getEvidenceType() {
		return evidenceType;
	}

	/**
	 * @param evidenceType the evidenceType to set
	 */
	public void setEvidenceType(String evidenceType) {
		this.evidenceType = evidenceType;
	}

	/**
	 * @return the citizenId
	 */
	public String getCitizenId() {
		return citizenId;
	}

	/**
	 * @param citizenId the citizenId to set
	 */
	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the isApplyMainWithSup
	 */
	public String getIsApplyMainWithSup() {
		return isApplyMainWithSup;
	}

	/**
	 * @param isApplyMainWithSup the isApplyMainWithSup to set
	 */
	public void setIsApplyMainWithSup(String isApplyMainWithSup) {
		this.isApplyMainWithSup = isApplyMainWithSup;
	}

	/**
	 * @return the mainCardNo
	 */
	public String getMainCardNo() {
		return mainCardNo;
	}

	/**
	 * @param mainCardNo the mainCardNo to set
	 */
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}

	/**
	 * @return the groupProduct
	 */
	public String getGroupProduct() {
		return groupProduct;
	}

	/**
	 * @param groupProduct the groupProduct to set
	 */
	public void setGroupProduct(String groupProduct) {
		this.groupProduct = groupProduct;
	}

	/**
	 * @return the analyst
	 */
	public String getAnalyst() {
		return analyst;
	}

	/**
	 * @param analyst the analyst to set
	 */
	public void setAnalyst(String analyst) {
		this.analyst = analyst;
	}

	/**
	 * @return the applicantType
	 */
	public String getApplicantType() {
		return applicantType;
	}

	/**
	 * @param applicantType the applicantType to set
	 */
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	/**
	 * @return the prefixName
	 */
	public String getPrefixName() {
		return prefixName;
	}

	/**
	 * @param prefixName the prefixName to set
	 */
	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	/**
	 * @return the prefixNameOth
	 */
	public String getPrefixNameOth() {
		return prefixNameOth;
	}

	/**
	 * @param prefixNameOth the prefixNameOth to set
	 */
	public void setPrefixNameOth(String prefixNameOth) {
		this.prefixNameOth = prefixNameOth;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the thaiFName
	 */
	public String getThaiFName() {
		return thaiFName;
	}

	/**
	 * @param thaiFName the thaiFName to set
	 */
	public void setThaiFName(String thaiFName) {
		this.thaiFName = thaiFName;
	}

	/**
	 * @return the thaiLName
	 */
	public String getThaiLName() {
		return thaiLName;
	}

	/**
	 * @param thaiLName the thaiLName to set
	 */
	public void setThaiLName(String thaiLName) {
		this.thaiLName = thaiLName;
	}

	/**
	 * @return the engFName
	 */
	public String getEngFName() {
		return engFName;
	}

	/**
	 * @param engFName the engFName to set
	 */
	public void setEngFName(String engFName) {
		this.engFName = engFName;
	}

	/**
	 * @return the engLName
	 */
	public String getEngLName() {
		return engLName;
	}

	/**
	 * @param engLName the engLName to set
	 */
	public void setEngLName(String engLName) {
		this.engLName = engLName;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the isChkNCB
	 */
	public String getIsChkNCB() {
		return isChkNCB;
	}

	/**
	 * @param isChkNCB the isChkNCB to set
	 */
	public void setIsChkNCB(String isChkNCB) {
		this.isChkNCB = isChkNCB;
	}

	/**
	 * @return the rushCard
	 */
	public String getRushCard() {
		return rushCard;
	}

	/**
	 * @param rushCard the rushCard to set
	 */
	public void setRushCard(String rushCard) {
		this.rushCard = rushCard;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}

	/**
	 * @return the currentAddressLine1
	 */
	public String getCurrentAddressLine1() {
		return currentAddressLine1;
	}

	/**
	 * @param currentAddressLine1 the currentAddressLine1 to set
	 */
	public void setCurrentAddressLine1(String currentAddressLine1) {
		this.currentAddressLine1 = currentAddressLine1;
	}

	/**
	 * @return the currentAddressLine2
	 */
	public String getCurrentAddressLine2() {
		return currentAddressLine2;
	}

	/**
	 * @param currentAddressLine2 the currentAddressLine2 to set
	 */
	public void setCurrentAddressLine2(String currentAddressLine2) {
		this.currentAddressLine2 = currentAddressLine2;
	}

	/**
	 * @return the currentAddressProvince
	 */
	public String getCurrentAddressProvince() {
		return currentAddressProvince;
	}

	/**
	 * @param currentAddressProvince the currentAddressProvince to set
	 */
	public void setCurrentAddressProvince(String currentAddressProvince) {
		this.currentAddressProvince = currentAddressProvince;
	}

	/**
	 * @return the currentAddressAmphur
	 */
	public String getCurrentAddressAmphur() {
		return currentAddressAmphur;
	}

	/**
	 * @param currentAddressAmphur the currentAddressAmphur to set
	 */
	public void setCurrentAddressAmphur(String currentAddressAmphur) {
		this.currentAddressAmphur = currentAddressAmphur;
	}

	/**
	 * @return the currentAddressDistrict
	 */
	public String getCurrentAddressDistrict() {
		return currentAddressDistrict;
	}

	/**
	 * @param currentAddressDistrict the currentAddressDistrict to set
	 */
	public void setCurrentAddressDistrict(String currentAddressDistrict) {
		this.currentAddressDistrict = currentAddressDistrict;
	}

	/**
	 * @return the currentAddressZipcode
	 */
	public String getCurrentAddressZipcode() {
		return currentAddressZipcode;
	}

	/**
	 * @param currentAddressZipcode the currentAddressZipcode to set
	 */
	public void setCurrentAddressZipcode(String currentAddressZipcode) {
		this.currentAddressZipcode = currentAddressZipcode;
	}

	/**
	 * @return the currentAddressPhoneNo
	 */
	public String getCurrentAddressPhoneNo() {
		return currentAddressPhoneNo;
	}

	/**
	 * @param currentAddressPhoneNo the currentAddressPhoneNo to set
	 */
	public void setCurrentAddressPhoneNo(String currentAddressPhoneNo) {
		this.currentAddressPhoneNo = currentAddressPhoneNo;
	}

	/**
	 * @return the currentAddressPhoneNoExt
	 */
	public String getCurrentAddressPhoneNoExt() {
		return currentAddressPhoneNoExt;
	}

	/**
	 * @param currentAddressPhoneNoExt the currentAddressPhoneNoExt to set
	 */
	public void setCurrentAddressPhoneNoExt(String currentAddressPhoneNoExt) {
		this.currentAddressPhoneNoExt = currentAddressPhoneNoExt;
	}

	/**
	 * @return the currentAddressMobileNo
	 */
	public String getCurrentAddressMobileNo() {
		return currentAddressMobileNo;
	}

	/**
	 * @param currentAddressMobileNo the currentAddressMobileNo to set
	 */
	public void setCurrentAddressMobileNo(String currentAddressMobileNo) {
		this.currentAddressMobileNo = currentAddressMobileNo;
	}

	/**
	 * @return the currentAddressEmail
	 */
	public String getCurrentAddressEmail() {
		return currentAddressEmail;
	}

	/**
	 * @param currentAddressEmail the currentAddressEmail to set
	 */
	public void setCurrentAddressEmail(String currentAddressEmail) {
		this.currentAddressEmail = currentAddressEmail;
	}

	/**
	 * @return the censusAddressLine1
	 */
	public String getCensusAddressLine1() {
		return censusAddressLine1;
	}

	/**
	 * @param censusAddressLine1 the censusAddressLine1 to set
	 */
	public void setCensusAddressLine1(String censusAddressLine1) {
		this.censusAddressLine1 = censusAddressLine1;
	}

	/**
	 * @return the censusAddressLine2
	 */
	public String getCensusAddressLine2() {
		return censusAddressLine2;
	}

	/**
	 * @param censusAddressLine2 the censusAddressLine2 to set
	 */
	public void setCensusAddressLine2(String censusAddressLine2) {
		this.censusAddressLine2 = censusAddressLine2;
	}

	/**
	 * @return the censusAddressProvince
	 */
	public String getCensusAddressProvince() {
		return censusAddressProvince;
	}

	/**
	 * @param censusAddressProvince the censusAddressProvince to set
	 */
	public void setCensusAddressProvince(String censusAddressProvince) {
		this.censusAddressProvince = censusAddressProvince;
	}

	/**
	 * @return the censusAddressAmphur
	 */
	public String getCensusAddressAmphur() {
		return censusAddressAmphur;
	}

	/**
	 * @param censusAddressAmphur the censusAddressAmphur to set
	 */
	public void setCensusAddressAmphur(String censusAddressAmphur) {
		this.censusAddressAmphur = censusAddressAmphur;
	}

	/**
	 * @return the censusAddressDistrict
	 */
	public String getCensusAddressDistrict() {
		return censusAddressDistrict;
	}

	/**
	 * @param censusAddressDistrict the censusAddressDistrict to set
	 */
	public void setCensusAddressDistrict(String censusAddressDistrict) {
		this.censusAddressDistrict = censusAddressDistrict;
	}

	/**
	 * @return the censusAddressZipcode
	 */
	public String getCensusAddressZipcode() {
		return censusAddressZipcode;
	}

	/**
	 * @param censusAddressZipcode the censusAddressZipcode to set
	 */
	public void setCensusAddressZipcode(String censusAddressZipcode) {
		this.censusAddressZipcode = censusAddressZipcode;
	}

	/**
	 * @return the censusAddressPhoneNo
	 */
	public String getCensusAddressPhoneNo() {
		return censusAddressPhoneNo;
	}

	/**
	 * @param censusAddressPhoneNo the censusAddressPhoneNo to set
	 */
	public void setCensusAddressPhoneNo(String censusAddressPhoneNo) {
		this.censusAddressPhoneNo = censusAddressPhoneNo;
	}

	/**
	 * @return the censusAddressPhoneNoext
	 */
	public String getCensusAddressPhoneNoext() {
		return censusAddressPhoneNoext;
	}

	/**
	 * @param censusAddressPhoneNoext the censusAddressPhoneNoext to set
	 */
	public void setCensusAddressPhoneNoext(String censusAddressPhoneNoext) {
		this.censusAddressPhoneNoext = censusAddressPhoneNoext;
	}

	/**
	 * @return the censusAddressMobileNo
	 */
	public String getCensusAddressMobileNo() {
		return censusAddressMobileNo;
	}

	/**
	 * @param censusAddressMobileNo the censusAddressMobileNo to set
	 */
	public void setCensusAddressMobileNo(String censusAddressMobileNo) {
		this.censusAddressMobileNo = censusAddressMobileNo;
	}

	/**
	 * @return the addrTypeYearLive
	 */
	public String getAddrTypeYearLive() {
		return addrTypeYearLive;
	}

	/**
	 * @param addrTypeYearLive the addrTypeYearLive to set
	 */
	public void setAddrTypeYearLive(String addrTypeYearLive) {
		this.addrTypeYearLive = addrTypeYearLive;
	}

	/**
	 * @return the addrTypeType
	 */
	public String getAddrTypeType() {
		return addrTypeType;
	}

	/**
	 * @param addrTypeType the addrTypeType to set
	 */
	public void setAddrTypeType(String addrTypeType) {
		this.addrTypeType = addrTypeType;
	}

	/**
	 * @return the addrTypeStatus
	 */
	public String getAddrTypeStatus() {
		return addrTypeStatus;
	}

	/**
	 * @param addrTypeStatus the addrTypeStatus to set
	 */
	public void setAddrTypeStatus(String addrTypeStatus) {
		this.addrTypeStatus = addrTypeStatus;
	}

	/**
	 * @return the addrTypeInstallment
	 */
	public String getAddrTypeInstallment() {
		return addrTypeInstallment;
	}

	/**
	 * @param addrTypeInstallment the addrTypeInstallment to set
	 */
	public void setAddrTypeInstallment(String addrTypeInstallment) {
		this.addrTypeInstallment = addrTypeInstallment;
	}

	/**
	 * @return the othersDegree
	 */
	public String getOthersDegree() {
		return othersDegree;
	}

	/**
	 * @param othersDegree the othersDegree to set
	 */
	public void setOthersDegree(String othersDegree) {
		this.othersDegree = othersDegree;
	}

	/**
	 * @return the othersOccupation
	 */
	public String getOthersOccupation() {
		return othersOccupation;
	}

	/**
	 * @param othersOccupation the othersOccupation to set
	 */
	public void setOthersOccupation(String othersOccupation) {
		this.othersOccupation = othersOccupation;
	}

	/**
	 * @return the othersOccupationOth
	 */
	public String getOthersOccupationOth() {
		return othersOccupationOth;
	}

	/**
	 * @param othersOccupationOth the othersOccupationOth to set
	 */
	public void setOthersOccupationOth(String othersOccupationOth) {
		this.othersOccupationOth = othersOccupationOth;
	}

	/**
	 * @return the othersWorkplace
	 */
	public String getOthersWorkplace() {
		return othersWorkplace;
	}

	/**
	 * @param othersWorkplace the othersWorkplace to set
	 */
	public void setOthersWorkplace(String othersWorkplace) {
		this.othersWorkplace = othersWorkplace;
	}

	/**
	 * @return the marriageStatus
	 */
	public String getMarriageStatus() {
		return marriageStatus;
	}

	/**
	 * @param marriageStatus the marriageStatus to set
	 */
	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	/**
	 * @return the referenceName
	 */
	public String getReferenceName() {
		return referenceName;
	}

	/**
	 * @param referenceName the referenceName to set
	 */
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	/**
	 * @return the referenceRelationship
	 */
	public String getReferenceRelationship() {
		return referenceRelationship;
	}

	/**
	 * @param referenceRelationship the referenceRelationship to set
	 */
	public void setReferenceRelationship(String referenceRelationship) {
		this.referenceRelationship = referenceRelationship;
	}

	/**
	 * @return the referenceHomePhoneNo
	 */
	public String getReferenceHomePhoneNo() {
		return referenceHomePhoneNo;
	}

	/**
	 * @param referenceHomePhoneNo the referenceHomePhoneNo to set
	 */
	public void setReferenceHomePhoneNo(String referenceHomePhoneNo) {
		this.referenceHomePhoneNo = referenceHomePhoneNo;
	}

	/**
	 * @return the referenceWorkPhoneNo
	 */
	public String getReferenceWorkPhoneNo() {
		return referenceWorkPhoneNo;
	}

	/**
	 * @param referenceWorkPhoneNo the referenceWorkPhoneNo to set
	 */
	public void setReferenceWorkPhoneNo(String referenceWorkPhoneNo) {
		this.referenceWorkPhoneNo = referenceWorkPhoneNo;
	}

	/**
	 * @return the referenceMobilePhoneNo
	 */
	public String getReferenceMobilePhoneNo() {
		return referenceMobilePhoneNo;
	}

	/**
	 * @param referenceMobilePhoneNo the referenceMobilePhoneNo to set
	 */
	public void setReferenceMobilePhoneNo(String referenceMobilePhoneNo) {
		this.referenceMobilePhoneNo = referenceMobilePhoneNo;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the staffRateFlag
	 */
	public String getStaffRateFlag() {
		return staffRateFlag;
	}

	/**
	 * @param staffRateFlag the staffRateFlag to set
	 */
	public void setStaffRateFlag(String staffRateFlag) {
		this.staffRateFlag = staffRateFlag;
	}

	/**
	 * @return the monthlyIncome
	 */
	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	/**
	 * @param monthlyIncome the monthlyIncome to set
	 */
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	/**
	 * @return the otherIncome
	 */
	public String getOtherIncome() {
		return otherIncome;
	}

	/**
	 * @param otherIncome the otherIncome to set
	 */
	public void setOtherIncome(String otherIncome) {
		this.otherIncome = otherIncome;
	}

	/**
	 * @return the sourceOtherIncome
	 */
	public String getSourceOtherIncome() {
		return sourceOtherIncome;
	}

	/**
	 * @param sourceOtherIncome the sourceOtherIncome to set
	 */
	public void setSourceOtherIncome(String sourceOtherIncome) {
		this.sourceOtherIncome = sourceOtherIncome;
	}

	/**
	 * @return the slCustomerInfoLoanIncome
	 */
	public String getSlCustomerInfoLoanIncome() {
		return slCustomerInfoLoanIncome;
	}

	/**
	 * @param slCustomerInfoLoanIncome the slCustomerInfoLoanIncome to set
	 */
	public void setSlCustomerInfoLoanIncome(String slCustomerInfoLoanIncome) {
		this.slCustomerInfoLoanIncome = slCustomerInfoLoanIncome;
	}

	/**
	 * @return the slCustomerInfoCardIncome
	 */
	public String getSlCustomerInfoCardIncome() {
		return slCustomerInfoCardIncome;
	}

	/**
	 * @param slCustomerInfoCardIncome the slCustomerInfoCardIncome to set
	 */
	public void setSlCustomerInfoCardIncome(String slCustomerInfoCardIncome) {
		this.slCustomerInfoCardIncome = slCustomerInfoCardIncome;
	}

	/**
	 * @return the slCustomerInfoPermanentCredit
	 */
	public String getSlCustomerInfoPermanentCredit() {
		return slCustomerInfoPermanentCredit;
	}

	/**
	 * @param slCustomerInfoPermanentCredit the slCustomerInfoPermanentCredit to set
	 */
	public void setSlCustomerInfoPermanentCredit(
			String slCustomerInfoPermanentCredit) {
		this.slCustomerInfoPermanentCredit = slCustomerInfoPermanentCredit;
	}

	/**
	 * @return the slCustomerInfoBillingCycle
	 */
	public String getSlCustomerInfoBillingCycle() {
		return slCustomerInfoBillingCycle;
	}

	/**
	 * @param slCustomerInfoBillingCycle the slCustomerInfoBillingCycle to set
	 */
	public void setSlCustomerInfoBillingCycle(String slCustomerInfoBillingCycle) {
		this.slCustomerInfoBillingCycle = slCustomerInfoBillingCycle;
	}

	/**
	 * @return the slCustomerInfoEstatementFlag
	 */
	public String getSlCustomerInfoEstatementFlag() {
		return slCustomerInfoEstatementFlag;
	}

	/**
	 * @param slCustomerInfoEstatementFlag the slCustomerInfoEstatementFlag to set
	 */
	public void setSlCustomerInfoEstatementFlag(String slCustomerInfoEstatementFlag) {
		this.slCustomerInfoEstatementFlag = slCustomerInfoEstatementFlag;
	}

	/**
	 * @return the productProductId
	 */
	public String getProductProductId() {
		return productProductId;
	}

	/**
	 * @param productProductId the productProductId to set
	 */
	public void setProductProductId(String productProductId) {
		this.productProductId = productProductId;
	}

	/**
	 * @return the productSubproductId
	 */
	public String getProductSubproductId() {
		return productSubproductId;
	}

	/**
	 * @param productSubproductId the productSubproductId to set
	 */
	public void setProductSubproductId(String productSubproductId) {
		this.productSubproductId = productSubproductId;
	}

	/**
	 * @return the productEmbossname1
	 */
	public String getProductEmbossname1() {
		return productEmbossname1;
	}

	/**
	 * @param productEmbossname1 the productEmbossname1 to set
	 */
	public void setProductEmbossname1(String productEmbossname1) {
		this.productEmbossname1 = productEmbossname1;
	}

	/**
	 * @return the productEmbossname2
	 */
	public String getProductEmbossname2() {
		return productEmbossname2;
	}

	/**
	 * @param productEmbossname2 the productEmbossname2 to set
	 */
	public void setProductEmbossname2(String productEmbossname2) {
		this.productEmbossname2 = productEmbossname2;
	}

	/**
	 * @return the productPaymentMethod
	 */
	public String getProductPaymentMethod() {
		return productPaymentMethod;
	}

	/**
	 * @param productPaymentMethod the productPaymentMethod to set
	 */
	public void setProductPaymentMethod(String productPaymentMethod) {
		this.productPaymentMethod = productPaymentMethod;
	}

	/**
	 * @return the productCycleDate
	 */
	public String getProductCycleDate() {
		return productCycleDate;
	}

	/**
	 * @param productCycleDate the productCycleDate to set
	 */
	public void setProductCycleDate(String productCycleDate) {
		this.productCycleDate = productCycleDate;
	}

	/**
	 * @return the productCreditLimit
	 */
	public String getProductCreditLimit() {
		return productCreditLimit;
	}

	/**
	 * @param productCreditLimit the productCreditLimit to set
	 */
	public void setProductCreditLimit(String productCreditLimit) {
		this.productCreditLimit = productCreditLimit;
	}

	/**
	 * @return the productPercentInterest
	 */
	public String getProductPercentInterest() {
		return productPercentInterest;
	}

	/**
	 * @param productPercentInterest the productPercentInterest to set
	 */
	public void setProductPercentInterest(String productPercentInterest) {
		this.productPercentInterest = productPercentInterest;
	}

	/**
	 * @return the productCommInterest
	 */
	public String getProductCommInterest() {
		return productCommInterest;
	}

	/**
	 * @param productCommInterest the productCommInterest to set
	 */
	public void setProductCommInterest(String productCommInterest) {
		this.productCommInterest = productCommInterest;
	}

	/**
	 * @return the productPromotionRate
	 */
	public String getProductPromotionRate() {
		return productPromotionRate;
	}

	/**
	 * @param productPromotionRate the productPromotionRate to set
	 */
	public void setProductPromotionRate(String productPromotionRate) {
		this.productPromotionRate = productPromotionRate;
	}

	/**
	 * @return the productPromotionTerms
	 */
	public String getProductPromotionTerms() {
		return productPromotionTerms;
	}

	/**
	 * @param productPromotionTerms the productPromotionTerms to set
	 */
	public void setProductPromotionTerms(String productPromotionTerms) {
		this.productPromotionTerms = productPromotionTerms;
	}

	/**
	 * @return the productTerm
	 */
	public String getProductTerm() {
		return productTerm;
	}

	/**
	 * @param productTerm the productTerm to set
	 */
	public void setProductTerm(String productTerm) {
		this.productTerm = productTerm;
	}

	/**
	 * @return the productCashAdvance
	 */
	public String getProductCashAdvance() {
		return productCashAdvance;
	}

	/**
	 * @param productCashAdvance the productCashAdvance to set
	 */
	public void setProductCashAdvance(String productCashAdvance) {
		this.productCashAdvance = productCashAdvance;
	}

	/**
	 * @return the appOccupationType
	 */
	public String getAppOccupationType() {
		return appOccupationType;
	}

	/**
	 * @param appOccupationType the appOccupationType to set
	 */
	public void setAppOccupationType(String appOccupationType) {
		this.appOccupationType = appOccupationType;
	}

	/**
	 * @return the appOccupationTypeOth
	 */
	public String getAppOccupationTypeOth() {
		return appOccupationTypeOth;
	}

	/**
	 * @param appOccupationTypeOth the appOccupationTypeOth to set
	 */
	public void setAppOccupationTypeOth(String appOccupationTypeOth) {
		this.appOccupationTypeOth = appOccupationTypeOth;
	}

	/**
	 * @return the appOccupationBusinessType
	 */
	public String getAppOccupationBusinessType() {
		return appOccupationBusinessType;
	}

	/**
	 * @param appOccupationBusinessType the appOccupationBusinessType to set
	 */
	public void setAppOccupationBusinessType(String appOccupationBusinessType) {
		this.appOccupationBusinessType = appOccupationBusinessType;
	}

	/**
	 * @return the appOccupationBusinessTypeOth
	 */
	public String getAppOccupationBusinessTypeOth() {
		return appOccupationBusinessTypeOth;
	}

	/**
	 * @param appOccupationBusinessTypeOth the appOccupationBusinessTypeOth to set
	 */
	public void setAppOccupationBusinessTypeOth(String appOccupationBusinessTypeOth) {
		this.appOccupationBusinessTypeOth = appOccupationBusinessTypeOth;
	}

	/**
	 * @return the appOccupationId
	 */
	public String getAppOccupationId() {
		return appOccupationId;
	}

	/**
	 * @param appOccupationId the appOccupationId to set
	 */
	public void setAppOccupationId(String appOccupationId) {
		this.appOccupationId = appOccupationId;
	}

	/**
	 * @return the appOccupationOth
	 */
	public String getAppOccupationOth() {
		return appOccupationOth;
	}

	/**
	 * @param appOccupationOth the appOccupationOth to set
	 */
	public void setAppOccupationOth(String appOccupationOth) {
		this.appOccupationOth = appOccupationOth;
	}

	/**
	 * @return the appOccupationWorkplace
	 */
	public String getAppOccupationWorkplace() {
		return appOccupationWorkplace;
	}

	/**
	 * @param appOccupationWorkplace the appOccupationWorkplace to set
	 */
	public void setAppOccupationWorkplace(String appOccupationWorkplace) {
		this.appOccupationWorkplace = appOccupationWorkplace;
	}

	/**
	 * @return the appOccupationJuristicNo
	 */
	public String getAppOccupationJuristicNo() {
		return appOccupationJuristicNo;
	}

	/**
	 * @param appOccupationJuristicNo the appOccupationJuristicNo to set
	 */
	public void setAppOccupationJuristicNo(String appOccupationJuristicNo) {
		this.appOccupationJuristicNo = appOccupationJuristicNo;
	}

	/**
	 * @return the appOccupationJuristicDe
	 */
	public String getAppOccupationJuristicDe() {
		return appOccupationJuristicDe;
	}

	/**
	 * @param appOccupationJuristicDe the appOccupationJuristicDe to set
	 */
	public void setAppOccupationJuristicDe(String appOccupationJuristicDe) {
		this.appOccupationJuristicDe = appOccupationJuristicDe;
	}

	/**
	 * @return the appOccupationJuristicRegdate
	 */
	public String getAppOccupationJuristicRegdate() {
		return appOccupationJuristicRegdate;
	}

	/**
	 * @param appOccupationJuristicRegdate the appOccupationJuristicRegdate to set
	 */
	public void setAppOccupationJuristicRegdate(String appOccupationJuristicRegdate) {
		this.appOccupationJuristicRegdate = appOccupationJuristicRegdate;
	}

	/**
	 * @return the appOccupationAddrline1
	 */
	public String getAppOccupationAddrline1() {
		return appOccupationAddrline1;
	}

	/**
	 * @param appOccupationAddrline1 the appOccupationAddrline1 to set
	 */
	public void setAppOccupationAddrline1(String appOccupationAddrline1) {
		this.appOccupationAddrline1 = appOccupationAddrline1;
	}

	/**
	 * @return the appOccupationAddrline2
	 */
	public String getAppOccupationAddrline2() {
		return appOccupationAddrline2;
	}

	/**
	 * @param appOccupationAddrline2 the appOccupationAddrline2 to set
	 */
	public void setAppOccupationAddrline2(String appOccupationAddrline2) {
		this.appOccupationAddrline2 = appOccupationAddrline2;
	}

	/**
	 * @return the appOccupationProvince
	 */
	public String getAppOccupationProvince() {
		return appOccupationProvince;
	}

	/**
	 * @param appOccupationProvince the appOccupationProvince to set
	 */
	public void setAppOccupationProvince(String appOccupationProvince) {
		this.appOccupationProvince = appOccupationProvince;
	}

	/**
	 * @return the appOccupationAmphur
	 */
	public String getAppOccupationAmphur() {
		return appOccupationAmphur;
	}

	/**
	 * @param appOccupationAmphur the appOccupationAmphur to set
	 */
	public void setAppOccupationAmphur(String appOccupationAmphur) {
		this.appOccupationAmphur = appOccupationAmphur;
	}

	/**
	 * @return the appOccupationDistrict
	 */
	public String getAppOccupationDistrict() {
		return appOccupationDistrict;
	}

	/**
	 * @param appOccupationDistrict the appOccupationDistrict to set
	 */
	public void setAppOccupationDistrict(String appOccupationDistrict) {
		this.appOccupationDistrict = appOccupationDistrict;
	}

	/**
	 * @return the appOccupationZipcode
	 */
	public String getAppOccupationZipcode() {
		return appOccupationZipcode;
	}

	/**
	 * @param appOccupationZipcode the appOccupationZipcode to set
	 */
	public void setAppOccupationZipcode(String appOccupationZipcode) {
		this.appOccupationZipcode = appOccupationZipcode;
	}

	/**
	 * @return the appOccupationPhoneNo1
	 */
	public String getAppOccupationPhoneNo1() {
		return appOccupationPhoneNo1;
	}

	/**
	 * @param appOccupationPhoneNo1 the appOccupationPhoneNo1 to set
	 */
	public void setAppOccupationPhoneNo1(String appOccupationPhoneNo1) {
		this.appOccupationPhoneNo1 = appOccupationPhoneNo1;
	}

	/**
	 * @return the appOccupationPhoneNo1Ext
	 */
	public String getAppOccupationPhoneNo1Ext() {
		return appOccupationPhoneNo1Ext;
	}

	/**
	 * @param appOccupationPhoneNo1Ext the appOccupationPhoneNo1Ext to set
	 */
	public void setAppOccupationPhoneNo1Ext(String appOccupationPhoneNo1Ext) {
		this.appOccupationPhoneNo1Ext = appOccupationPhoneNo1Ext;
	}

	/**
	 * @return the appOccupationMobileNo
	 */
	public String getAppOccupationMobileNo() {
		return appOccupationMobileNo;
	}

	/**
	 * @param appOccupationMobileNo the appOccupationMobileNo to set
	 */
	public void setAppOccupationMobileNo(String appOccupationMobileNo) {
		this.appOccupationMobileNo = appOccupationMobileNo;
	}

	/**
	 * @return the appOccupationPosition
	 */
	public String getAppOccupationPosition() {
		return appOccupationPosition;
	}

	/**
	 * @param appOccupationPosition the appOccupationPosition to set
	 */
	public void setAppOccupationPosition(String appOccupationPosition) {
		this.appOccupationPosition = appOccupationPosition;
	}

	/**
	 * @return the appOccupationYearOfWork
	 */
	public String getAppOccupationYearOfWork() {
		return appOccupationYearOfWork;
	}

	/**
	 * @param appOccupationYearOfWork the appOccupationYearOfWork to set
	 */
	public void setAppOccupationYearOfWork(String appOccupationYearOfWork) {
		this.appOccupationYearOfWork = appOccupationYearOfWork;
	}

	/**
	 * @return the appOccupationRecieveCard
	 */
	public String getAppOccupationRecieveCard() {
		return appOccupationRecieveCard;
	}

	/**
	 * @param appOccupationRecieveCard the appOccupationRecieveCard to set
	 */
	public void setAppOccupationRecieveCard(String appOccupationRecieveCard) {
		this.appOccupationRecieveCard = appOccupationRecieveCard;
	}

	/**
	 * @return the appOccupationRecieveBranch
	 */
	public String getAppOccupationRecieveBranch() {
		return appOccupationRecieveBranch;
	}

	/**
	 * @param appOccupationRecieveBranch the appOccupationRecieveBranch to set
	 */
	public void setAppOccupationRecieveBranch(String appOccupationRecieveBranch) {
		this.appOccupationRecieveBranch = appOccupationRecieveBranch;
	}

	/**
	 * @return the appOccupationRecieveBill
	 */
	public String getAppOccupationRecieveBill() {
		return appOccupationRecieveBill;
	}

	/**
	 * @param appOccupationRecieveBill the appOccupationRecieveBill to set
	 */
	public void setAppOccupationRecieveBill(String appOccupationRecieveBill) {
		this.appOccupationRecieveBill = appOccupationRecieveBill;
	}

	/**
	 * @return the appOccupationCommunication
	 */
	public String getAppOccupationCommunication() {
		return appOccupationCommunication;
	}

	/**
	 * @param appOccupationCommunication the appOccupationCommunication to set
	 */
	public void setAppOccupationCommunication(String appOccupationCommunication) {
		this.appOccupationCommunication = appOccupationCommunication;
	}

	/**
	 * @return the appOccupationSMECustomerNo
	 */
	public String getAppOccupationSMECustomerNo() {
		return appOccupationSMECustomerNo;
	}

	/**
	 * @param appOccupationSMECustomerNo the appOccupationSMECustomerNo to set
	 */
	public void setAppOccupationSMECustomerNo(String appOccupationSMECustomerNo) {
		this.appOccupationSMECustomerNo = appOccupationSMECustomerNo;
	}

	/**
	 * @return the bankAccountBank
	 */
	public String getBankAccountBank() {
		return bankAccountBank;
	}

	/**
	 * @param bankAccountBank the bankAccountBank to set
	 */
	public void setBankAccountBank(String bankAccountBank) {
		this.bankAccountBank = bankAccountBank;
	}

	/**
	 * @return the bankAccountBankBranch
	 */
	public String getBankAccountBankBranch() {
		return bankAccountBankBranch;
	}

	/**
	 * @param bankAccountBankBranch the bankAccountBankBranch to set
	 */
	public void setBankAccountBankBranch(String bankAccountBankBranch) {
		this.bankAccountBankBranch = bankAccountBankBranch;
	}

	/**
	 * @return the bankAccountAccType
	 */
	public String getBankAccountAccType() {
		return bankAccountAccType;
	}

	/**
	 * @param bankAccountAccType the bankAccountAccType to set
	 */
	public void setBankAccountAccType(String bankAccountAccType) {
		this.bankAccountAccType = bankAccountAccType;
	}

	/**
	 * @return the bankAccountAccName
	 */
	public String getBankAccountAccName() {
		return bankAccountAccName;
	}

	/**
	 * @param bankAccountAccName the bankAccountAccName to set
	 */
	public void setBankAccountAccName(String bankAccountAccName) {
		this.bankAccountAccName = bankAccountAccName;
	}

	/**
	 * @return the bankAccountAccNo
	 */
	public String getBankAccountAccNo() {
		return bankAccountAccNo;
	}

	/**
	 * @param bankAccountAccNo the bankAccountAccNo to set
	 */
	public void setBankAccountAccNo(String bankAccountAccNo) {
		this.bankAccountAccNo = bankAccountAccNo;
	}

	/**
	 * @return the bankAccountOpenPeriod
	 */
	public String getBankAccountOpenPeriod() {
		return bankAccountOpenPeriod;
	}

	/**
	 * @param bankAccountOpenPeriod the bankAccountOpenPeriod to set
	 */
	public void setBankAccountOpenPeriod(String bankAccountOpenPeriod) {
		this.bankAccountOpenPeriod = bankAccountOpenPeriod;
	}

	/**
	 * @return the bankAccountTMBalance
	 */
	public String getBankAccountTMBalance() {
		return bankAccountTMBalance;
	}

	/**
	 * @param bankAccountTMBalance the bankAccountTMBalance to set
	 */
	public void setBankAccountTMBalance(String bankAccountTMBalance) {
		this.bankAccountTMBalance = bankAccountTMBalance;
	}

	/**
	 * @return the bankAccountLMBalance
	 */
	public String getBankAccountLMBalance() {
		return bankAccountLMBalance;
	}

	/**
	 * @param bankAccountLMBalance the bankAccountLMBalance to set
	 */
	public void setBankAccountLMBalance(String bankAccountLMBalance) {
		this.bankAccountLMBalance = bankAccountLMBalance;
	}

	/**
	 * @return the loanAccountAccno
	 */
	public String getLoanAccountAccno() {
		return loanAccountAccno;
	}

	/**
	 * @param loanAccountAccno the loanAccountAccno to set
	 */
	public void setLoanAccountAccno(String loanAccountAccno) {
		this.loanAccountAccno = loanAccountAccno;
	}

	/**
	 * @return the loanAccountAcctype
	 */
	public String getLoanAccountAcctype() {
		return loanAccountAcctype;
	}

	/**
	 * @param loanAccountAcctype the loanAccountAcctype to set
	 */
	public void setLoanAccountAcctype(String loanAccountAcctype) {
		this.loanAccountAcctype = loanAccountAcctype;
	}

	/**
	 * @return the loanAccountBank
	 */
	public String getLoanAccountBank() {
		return loanAccountBank;
	}

	/**
	 * @param loanAccountBank the loanAccountBank to set
	 */
	public void setLoanAccountBank(String loanAccountBank) {
		this.loanAccountBank = loanAccountBank;
	}

	/**
	 * @return the loanAccountIssueDate
	 */
	public String getLoanAccountIssueDate() {
		return loanAccountIssueDate;
	}

	/**
	 * @param loanAccountIssueDate the loanAccountIssueDate to set
	 */
	public void setLoanAccountIssueDate(String loanAccountIssueDate) {
		this.loanAccountIssueDate = loanAccountIssueDate;
	}

	/**
	 * @return the loanAccountExpireDate
	 */
	public String getLoanAccountExpireDate() {
		return loanAccountExpireDate;
	}

	/**
	 * @param loanAccountExpireDate the loanAccountExpireDate to set
	 */
	public void setLoanAccountExpireDate(String loanAccountExpireDate) {
		this.loanAccountExpireDate = loanAccountExpireDate;
	}

	/**
	 * @return the loanAccountCreditLimit
	 */
	public String getLoanAccountCreditLimit() {
		return loanAccountCreditLimit;
	}

	/**
	 * @param loanAccountCreditLimit the loanAccountCreditLimit to set
	 */
	public void setLoanAccountCreditLimit(String loanAccountCreditLimit) {
		this.loanAccountCreditLimit = loanAccountCreditLimit;
	}

	/**
	 * @return the loanAccountPaymentMethod
	 */
	public String getLoanAccountPaymentMethod() {
		return loanAccountPaymentMethod;
	}

	/**
	 * @param loanAccountPaymentMethod the loanAccountPaymentMethod to set
	 */
	public void setLoanAccountPaymentMethod(String loanAccountPaymentMethod) {
		this.loanAccountPaymentMethod = loanAccountPaymentMethod;
	}

	/**
	 * @return the loanAccountInstallment
	 */
	public String getLoanAccountInstallment() {
		return loanAccountInstallment;
	}

	/**
	 * @param loanAccountInstallment the loanAccountInstallment to set
	 */
	public void setLoanAccountInstallment(String loanAccountInstallment) {
		this.loanAccountInstallment = loanAccountInstallment;
	}

	/**
	 * @return the transferDebtAccName
	 */
	public String getTransferDebtAccName() {
		return transferDebtAccName;
	}

	/**
	 * @param transferDebtAccName the transferDebtAccName to set
	 */
	public void setTransferDebtAccName(String transferDebtAccName) {
		this.transferDebtAccName = transferDebtAccName;
	}

	/**
	 * @return the transferDebtAccNo
	 */
	public String getTransferDebtAccNo() {
		return transferDebtAccNo;
	}

	/**
	 * @param transferDebtAccNo the transferDebtAccNo to set
	 */
	public void setTransferDebtAccNo(String transferDebtAccNo) {
		this.transferDebtAccNo = transferDebtAccNo;
	}

	/**
	 * @return the transferDebtAccType
	 */
	public String getTransferDebtAccType() {
		return transferDebtAccType;
	}

	/**
	 * @param transferDebtAccType the transferDebtAccType to set
	 */
	public void setTransferDebtAccType(String transferDebtAccType) {
		this.transferDebtAccType = transferDebtAccType;
	}

	/**
	 * @return the transferDebtBank
	 */
	public String getTransferDebtBank() {
		return transferDebtBank;
	}

	/**
	 * @param transferDebtBank the transferDebtBank to set
	 */
	public void setTransferDebtBank(String transferDebtBank) {
		this.transferDebtBank = transferDebtBank;
	}

	/**
	 * @return the transferDebtLastAmount
	 */
	public String getTransferDebtLastAmount() {
		return transferDebtLastAmount;
	}

	/**
	 * @param transferDebtLastAmount the transferDebtLastAmount to set
	 */
	public void setTransferDebtLastAmount(String transferDebtLastAmount) {
		this.transferDebtLastAmount = transferDebtLastAmount;
	}

	/**
	 * @return the transferLoanAccName
	 */
	public String getTransferLoanAccName() {
		return transferLoanAccName;
	}

	/**
	 * @param transferLoanAccName the transferLoanAccName to set
	 */
	public void setTransferLoanAccName(String transferLoanAccName) {
		this.transferLoanAccName = transferLoanAccName;
	}

	/**
	 * @return the transferLoanAccNo
	 */
	public String getTransferLoanAccNo() {
		return transferLoanAccNo;
	}

	/**
	 * @param transferLoanAccNo the transferLoanAccNo to set
	 */
	public void setTransferLoanAccNo(String transferLoanAccNo) {
		this.transferLoanAccNo = transferLoanAccNo;
	}

	/**
	 * @return the transferLoanAccType
	 */
	public String getTransferLoanAccType() {
		return transferLoanAccType;
	}

	/**
	 * @param transferLoanAccType the transferLoanAccType to set
	 */
	public void setTransferLoanAccType(String transferLoanAccType) {
		this.transferLoanAccType = transferLoanAccType;
	}

	/**
	 * @return the transferLoanBank
	 */
	public String getTransferLoanBank() {
		return transferLoanBank;
	}

	/**
	 * @param transferLoanBank the transferLoanBank to set
	 */
	public void setTransferLoanBank(String transferLoanBank) {
		this.transferLoanBank = transferLoanBank;
	}

	/**
	 * @return the paymentMethodValue
	 */
	public String getPaymentMethodValue() {
		return paymentMethodValue;
	}

	/**
	 * @param paymentMethodValue the paymentMethodValue to set
	 */
	public void setPaymentMethodValue(String paymentMethodValue) {
		this.paymentMethodValue = paymentMethodValue;
	}

	/**
	 * @return the paymentMethodAccName
	 */
	public String getPaymentMethodAccName() {
		return paymentMethodAccName;
	}

	/**
	 * @param paymentMethodAccName the paymentMethodAccName to set
	 */
	public void setPaymentMethodAccName(String paymentMethodAccName) {
		this.paymentMethodAccName = paymentMethodAccName;
	}

	/**
	 * @return the paymentMethodAccNo
	 */
	public String getPaymentMethodAccNo() {
		return paymentMethodAccNo;
	}

	/**
	 * @param paymentMethodAccNo the paymentMethodAccNo to set
	 */
	public void setPaymentMethodAccNo(String paymentMethodAccNo) {
		this.paymentMethodAccNo = paymentMethodAccNo;
	}

	/**
	 * @return the paymentMethodBank
	 */
	public String getPaymentMethodBank() {
		return paymentMethodBank;
	}

	/**
	 * @param paymentMethodBank the paymentMethodBank to set
	 */
	public void setPaymentMethodBank(String paymentMethodBank) {
		this.paymentMethodBank = paymentMethodBank;
	}

}
