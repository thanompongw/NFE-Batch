/**
 * 
 */
package co.th.ktc.nfe.report.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * @author Deedy
 *
 */
public abstract class AbstractReportDao extends JdbcDaoSupport {

	/**
	 * Default AbstractReportDao Class.
	 */
	public AbstractReportDao() {
	}
	
	public abstract void insert(Object[] parameter);
	
	public abstract void update(Object[] parameter);
	
	public abstract void delete(Object[] parameter);
	
	public abstract SqlRowSet query(Object[] parameter);
	
	public abstract void success(Object[] parameter);
	
	public abstract void fail(Object[] parameter);
	
	public String getSetDate(String format) {
		
		String sql = "SELECT TO_CHAR(NFE_CURRENTDATE, ?) FROM NFE_SETDATE";
		
		String dateStr = 
				getJdbcTemplate().queryForObject(sql, 
										 		 new String[] { format }, 
										 		 String.class);
		
		return dateStr;
	}

}
