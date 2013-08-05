/**
 * 
 */
package co.th.ktc.nfe.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import co.th.ktc.nfe.report.domain.DateBean;

/**
 * @author Deedy
 *
 */
public abstract class AbstractBatchDao extends JdbcDaoSupport {

	/**
	 * Default AbstractReportDao Class.
	 */
	public AbstractBatchDao() {
	}
	
	public abstract void insert(Object[] parameter);
	
	public abstract void update(Object[] parameter);
	
	public abstract void delete(Object[] parameter);
	
	public abstract SqlRowSet queryDetail(Object[] parameter);
	
	public abstract SqlRowSet queryHeader(Object[] parameter);
	
	public abstract SqlRowSet queryTrailer(Object[] parameter);
	
	public abstract void success(Object[] parameter);
	
	public abstract void fail(Object[] parameter);
	
	public Integer size(Object[] parameter) {
		return 0;
	}
	
	public String getConfigRemotePath(Object[] parameter) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CONFIGURATION_VALUE || '/' ");
		sql.append("FROM NFE_CONFIGURATION ");
		sql.append("WHERE CONFIGURATION_NAME = 'Fixed_MEDIACLR_Path' ");
		
		String remoteServerPath = 
				getJdbcTemplate().queryForObject(sql.toString(), 
										 		 String.class);
		
		return remoteServerPath;
	}
	
	public String getSetDate(String format) {
		
		String sql = "SELECT TO_CHAR(NFE_CURRENTDATE, ?) FROM NFE_SETDATE";
		
		String dateStr = 
				getJdbcTemplate().queryForObject(sql, 
										 		 new String[] { format }, 
										 		 String.class);
		
		return dateStr;
	}
	
	private RowMapper<DateBean> rowMapper = new RowMapper<DateBean>() {

		public DateBean mapRow(ResultSet rs, int i) throws SQLException {
			
			DateBean dateBean = new DateBean();
			dateBean.setDateFrom(rs.getDate("B_DATE_FROM"));
			dateBean.setDateTo(rs.getDate("B_DATE_TO"));
			
			return dateBean;
		}
	};
	
	public Boolean isDayoff(String date) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT 1 ");
		sql.append("FROM  DUAL ");
		sql.append("WHERE EXISTS(SELECT 'X' ");
		sql.append("             FROM   NFE_HOLIDAY ");
		sql.append("             WHERE  HOLIDAY_DATE <> TO_DATE(?, 'DD/MM/YYYY')) ");
		sql.append("AND   TO_CHAR(TO_DATE(?, 'DD/MM/YYYY'), 'D') NOT IN (7, 1) ");
		
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql.toString(),
														new Object[] {date, date});
		if (rowSet.next()) {
			return true;
		}
		
		return false;
	}
	
	public DateBean getBusinessDay(String date) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CAST((CASE ");
		sql.append("            WHEN DIFF >= 0 ");
		sql.append("            THEN B_DATE - DIFF ");
		sql.append("            WHEN DIFF < 0 ");
		sql.append("            THEN B_DATE + DIFF ");
		sql.append("            ELSE B_DATE ");
		sql.append("        END) AS DATE) AS B_DATE_FROM, ");
		sql.append("       CAST((CASE ");
		sql.append("            WHEN DIFF >= 0 OR DIFF < 0 ");
		sql.append("            THEN B_DATE ");
		sql.append("            ELSE B_DATE + DIFF ");
		sql.append("        END) AS DATE) AS B_DATE_TO ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT B_DATE, ");
		sql.append("       B_DATE - CAST(LAG(CAST(B_DATE AS DATE), 1, CAST(B_DATE AS DATE))  ");
		sql.append("                     OVER (ORDER BY B_DATE) AS DATE) - 1 AS DIFF ");
		sql.append("FROM   (SELECT TO_DATE(?, 'DD/MM/YYYY') - LEVEL + 1 AS B_DATE ");
		sql.append("        FROM   DUAL ");
		sql.append("        CONNECT BY LEVEL <= 10) ");
		sql.append("WHERE  TO_CHAR(B_DATE, 'D') NOT IN (7, 1) ");
		sql.append("AND    NOT EXISTS(SELECT 'X' ");
		sql.append("                  FROM   NFE_HOLIDAY ");
		sql.append("                  WHERE  HOLIDAY_DATE = CAST(B_DATE AS DATE)) ");
		sql.append("AND    B_DATE >= (SELECT MIN(APP_DATETIME) ");
		sql.append("                  FROM   NFE_APPLICATION ");
		sql.append("                  WHERE  APP_NO <> ' ') ");
		sql.append("ORDER BY ROWNUM ASC ");
		sql.append(") ");
		sql.append("WHERE ROWNUM = 1 ");
		
		DateBean businessDates = null;
		
		try {
			
			if (isDayoff(date)) {
				businessDates = getJdbcTemplate().queryForObject(sql.toString(), 
						                                         rowMapper, 
						                                         new Object[] {date});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return businessDates;

	}
	
	public String getMediaCleringDay(String effectiveDate) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT TO_CHAR(CAST((CASE ");
		sql.append("                         WHEN TO_CHAR((B_DATE - DIFF - 1), 'D') = 6 ");
		sql.append("                         THEN B_DATE - DIFF - 1 ");
		sql.append("                         ELSE B_DATE - DIFF ");
		sql.append("                     END) AS DATE), 'YYMMDD') AS MEDIA_DATE ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT B_DATE, ");
		sql.append("       B_DATE - (CAST(LAG(CAST(B_DATE AS DATE), 1, CAST(B_DATE AS DATE))  ");
		sql.append("                      OVER (ORDER BY B_DATE) AS DATE)) AS DIFF, ");
		sql.append("       ROWNUM ");
		sql.append("FROM   (SELECT (TO_DATE(?, 'DD/MM/YYYY') - LEVEL) AS B_DATE ");
		sql.append("        FROM   DUAL ");
		sql.append("        CONNECT BY LEVEL <= 10) ");
		sql.append("WHERE  TO_CHAR(B_DATE, 'D') NOT IN (7, 1) ");
		sql.append("AND    NOT EXISTS(SELECT 'X' ");
		sql.append("                  FROM   NFE_HOLIDAY ");
		sql.append("                  WHERE  HOLIDAY_DATE = CAST(B_DATE AS DATE)) ");
		sql.append("ORDER BY ROWNUM ASC ");
		sql.append(") ");
		sql.append("WHERE ROWNUM <= 1 ");
		
		String mediaCleringDate = null;
		
		try {
			
			if (isDayoff(effectiveDate)) {
				mediaCleringDate = getJdbcTemplate().queryForObject(sql.toString(), 
						                                            new Object[] {effectiveDate},
						                                            String.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mediaCleringDate;

	}
}
