/**
 * 
 */
package co.th.ktc.nfe.report.domain;

import java.io.Serializable;

/**
 * @author temp_dev1
 *
 */
public class RemainBean implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8927762031494759890L;
	
	private Integer remain;
	private Double percentage;

	/**
	 * 
	 */
	public RemainBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the remain
	 */
	public Integer getRemain() {
		return remain;
	}

	/**
	 * @param remain the remain to set
	 */
	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	/**
	 * @return the percentage
	 */
	public Double getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

}
