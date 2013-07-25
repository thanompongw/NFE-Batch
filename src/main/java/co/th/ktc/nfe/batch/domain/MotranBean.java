package co.th.ktc.nfe.batch.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author temp_dev2
 *
 */
public class MotranBean implements Serializable {


	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3864900001936556983L;
	
	private String fileType;
	private Integer recordType;
	private Integer rowNo;
	private String bankCode;
	private Integer totalRecord;
	private Integer totalBanlanceTransfer;
	private String effectiveDate;
	private String type;
	private String productType;
	private String filler;
	
	private List<MotranDetailBean> motranDetailBeans;

	/**
	 * Default Constructor of MotranBean class.
	 */
	public MotranBean() {
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
	 * @return the rowNo
	 */
	public Integer getRowNo() {
		return rowNo;
	}

	/**
	 * @param rowNo the rowNo to set
	 */
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the totalRecord
	 */
	public Integer getTotalRecord() {
		return totalRecord;
	}

	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	/**
	 * @return the totalBanlanceTransfer
	 */
	public Integer getTotalBanlanceTransfer() {
		return totalBanlanceTransfer;
	}

	/**
	 * @param totalBanlanceTransfer the totalBanlanceTransfer to set
	 */
	public void setTotalBanlanceTransfer(Integer totalBanlanceTransfer) {
		this.totalBanlanceTransfer = totalBanlanceTransfer;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
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

	/**
	 * @return the motranDetailBeans
	 */
	public List<MotranDetailBean> getMotranDetailBeans() {
		return motranDetailBeans;
	}

	/**
	 * @param motranDetailBeans the motranDetailBeans to set
	 */
	public void setMotranDetailBeans(List<MotranDetailBean> motranDetailBeans) {
		this.motranDetailBeans = motranDetailBeans;
	}

}
