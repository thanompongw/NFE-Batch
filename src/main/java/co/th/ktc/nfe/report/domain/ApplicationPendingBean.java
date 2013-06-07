package co.th.ktc.nfe.report.domain;

import java.io.Serializable;
import java.util.List;

public class ApplicationPendingBean implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5032131611354156864L;
	
	private String businessDate;
	private Integer totalRecieveApp;
	private List<RemainBean> remains;
	private Integer remark;

	public ApplicationPendingBean() {
	}

	/**
	 * @return the businessDate
	 */
	public String getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the totalRecieveApp
	 */
	public Integer getTotalRecieveApp() {
		return totalRecieveApp;
	}

	/**
	 * @param totalRecieveApp the totalRecieveApp to set
	 */
	public void setTotalRecieveApp(Integer totalRecieveApp) {
		this.totalRecieveApp = totalRecieveApp;
	}

	/**
	 * @return the remains
	 */
	public List<RemainBean> getRemains() {
		return remains;
	}

	/**
	 * @param remains the remains to set
	 */
	public void setRemains(List<RemainBean> remains) {
		this.remains = remains;
	}

	/**
	 * @return the remark
	 */
	public Integer getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(Integer remark) {
		this.remark = remark;
	}

}
