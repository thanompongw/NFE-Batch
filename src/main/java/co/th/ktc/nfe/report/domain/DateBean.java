/**
 * 
 */
package co.th.ktc.nfe.report.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author temp_dev1
 *
 */
public class DateBean implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7118886678096533777L;
	
	private Date dateFrom;
	private Date dateTo;

	/**
	 * 
	 */
	public DateBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

}
