/**
 * 
 */
package co.th.ktc.nfe.batch.domain;

import java.io.Serializable;

/**
 * @author temp_dev2
 *
 */
public class MotranDetailBean implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8693139496983435529L;
	
	private String fileType;
	private Integer recordType;
	private Integer setNo;
	private String receivingBankCode;
	private String receivingBankBranch;
	private String receivingBankAccount;
	private String receiverId;
	private String receiverName;
	private String sendingBankCodeDefault;
	private String sendingBankCode;
	private String sendingBankAccount;
	private String senderName;
	private String serviceType;
	private String effectiveDate;
	private String clearingHouseCode;
	private Integer transferAmount;
	private String receiverInformation;
	private String otherInformationI;
	private String otherInformationII;
	private String referenceNo;
	private String referenceRunningNo;	
	private String filler;

	/**
	 * Default Constructor of MotranDetailBean class.
	 */
	public MotranDetailBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the recordType
	 */
	public Integer getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType the recordType to set
	 */
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return the setNo
	 */
	public Integer getSetNo() {
		return setNo;
	}

	/**
	 * @param setNo the setNo to set
	 */
	public void setSetNo(Integer setNo) {
		this.setNo = setNo;
	}

	/**
	 * @return the receivingBankCode
	 */
	public String getReceivingBankCode() {
		return receivingBankCode;
	}

	/**
	 * @param receivingBankCode the receivingBankCode to set
	 */
	public void setReceivingBankCode(String receivingBankCode) {
		this.receivingBankCode = receivingBankCode;
	}

	/**
	 * @return the receivingBankBranch
	 */
	public String getReceivingBankBranch() {
		return receivingBankBranch;
	}

	/**
	 * @param receivingBankBranch the receivingBankBranch to set
	 */
	public void setReceivingBankBranch(String receivingBankBranch) {
		this.receivingBankBranch = receivingBankBranch;
	}

	/**
	 * @return the receivingBankAccount
	 */
	public String getReceivingBankAccount() {
		return receivingBankAccount;
	}

	/**
	 * @param receivingBankAccount the receivingBankAccount to set
	 */
	public void setReceivingBankAccount(String receivingBankAccount) {
		this.receivingBankAccount = receivingBankAccount;
	}

	/**
	 * @return the receiverId
	 */
	public String getReceiverId() {
		return receiverId;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * @return the sendingBankCodeDefault
	 */
	public String getSendingBankCodeDefault() {
		return sendingBankCodeDefault;
	}

	/**
	 * @param sendingBankCodeDefault the sendingBankCodeDefault to set
	 */
	public void setSendingBankCodeDefault(String sendingBankCodeDefault) {
		this.sendingBankCodeDefault = sendingBankCodeDefault;
	}

	/**
	 * @return the sendingBankCode
	 */
	public String getSendingBankCode() {
		return sendingBankCode;
	}

	/**
	 * @param sendingBankCode the sendingBankCode to set
	 */
	public void setSendingBankCode(String sendingBankCode) {
		this.sendingBankCode = sendingBankCode;
	}

	/**
	 * @return the sendingBankAccount
	 */
	public String getSendingBankAccount() {
		return sendingBankAccount;
	}

	/**
	 * @param sendingBankAccount the sendingBankAccount to set
	 */
	public void setSendingBankAccount(String sendingBankAccount) {
		this.sendingBankAccount = sendingBankAccount;
	}

	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the clearingHouseCode
	 */
	public String getClearingHouseCode() {
		return clearingHouseCode;
	}

	/**
	 * @param clearingHouseCode the clearingHouseCode to set
	 */
	public void setClearingHouseCode(String clearingHouseCode) {
		this.clearingHouseCode = clearingHouseCode;
	}

	/**
	 * @return the transferAmount
	 */
	public Integer getTransferAmount() {
		return transferAmount;
	}

	/**
	 * @param transferAmount the transferAmount to set
	 */
	public void setTransferAmount(Integer transferAmount) {
		this.transferAmount = transferAmount;
	}

	/**
	 * @return the receiverInformation
	 */
	public String getReceiverInformation() {
		return receiverInformation;
	}

	/**
	 * @param receiverInformation the receiverInformation to set
	 */
	public void setReceiverInformation(String receiverInformation) {
		this.receiverInformation = receiverInformation;
	}

	/**
	 * @return the otherInformationI
	 */
	public String getOtherInformationI() {
		return otherInformationI;
	}

	/**
	 * @param otherInformationI the otherInformationI to set
	 */
	public void setOtherInformationI(String otherInformationI) {
		this.otherInformationI = otherInformationI;
	}

	/**
	 * @return the otherInformationII
	 */
	public String getOtherInformationII() {
		return otherInformationII;
	}

	/**
	 * @param otherInformationII the otherInformationII to set
	 */
	public void setOtherInformationII(String otherInformationII) {
		this.otherInformationII = otherInformationII;
	}

	/**
	 * @return the referenceNo
	 */
	public String getReferenceNo() {
		return referenceNo;
	}

	/**
	 * @param referenceNo the referenceNo to set
	 */
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	/**
	 * @return the referenceRunningNo
	 */
	public String getReferenceRunningNo() {
		return referenceRunningNo;
	}

	/**
	 * @param referenceRunningNo the referenceRunningNo to set
	 */
	public void setReferenceRunningNo(String referenceRunningNo) {
		this.referenceRunningNo = referenceRunningNo;
	}

	/**
	 * @return the filler
	 */
	public String getFiller() {
		return filler;
	}

	/**
	 * @param filler the filler to set
	 */
	public void setFiller(String filler) {
		this.filler = filler;
	}

}
