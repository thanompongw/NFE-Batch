/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

/**
 * @author Deedy
 *
 */
@Repository(value = "applicationPending45DaysDao")
public class ApplicationPending45DaysDao extends AbstractReportDao {

	/**
	 * 
	 */
	public ApplicationPending45DaysDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#update(java.lang.Object[])
	 */
	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#delete(java.lang.Object[])
	 */
	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#query(java.lang.Object[])
	 */
	@Override
	public SqlRowSet query(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
        if (parameter != null && parameter.length > 0) {
    		if (parameter[0].equals(NFEBatchConstants.FINAL_RESOLVE_STATUS_CODE)) {
    			sql.append("SELECT SUM(CASE  ");
    			sql.append("               WHEN (SELECT COUNT(1) ");
    			sql.append("                             FROM   NFE_APP_STATUSTRACKING T2 ");
    			sql.append("                             WHERE  T2.STATUSTRACKING_ID > 0 ");
    			sql.append("                             AND    T2.STATUSTRACKING_APPNO = T1.APP_NO ");
    			sql.append("                             AND    T2.STATUSTRACKING_STATUS IN (?, '2R') ");
    			if (parameter.length == 7) {
        			sql.append("                             AND    (T2.STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
        			sql.append("                                                                   AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");
    			} else {
        			sql.append("                             AND    T2.STATUSTRACKING_ENDTIME > TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
    			}
    			sql.append("                     GROUP BY T2.STATUSTRACKING_ENDTIME) = 1 ");
    			sql.append("               THEN 1 ");
    			sql.append("               ELSE 0 ");
    			sql.append("           END) AS FINAL_APP ");
    			sql.append("FROM   NFE_APPLICATION T1 ");
    			sql.append("WHERE  T1.APP_STATUSCODE <> '2C' ");
    			sql.append("AND    (T1.APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
    			sql.append("                            AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");
    		} else {
        		sql.append("SELECT COUNT (1) AS TOTAL_APP ");
                sql.append("FROM   NFE_APPLICATION T1 ");
                sql.append("WHERE  T1.APP_STATUSCODE <> ? ");
                sql.append("AND    (T1.APP_DATETIME BETWEEN TO_TIMESTAMP (?, 'DD/MM/YYYY HH24:MI:SS') ");
                sql.append("                            AND TO_TIMESTAMP (?, 'DD/MM/YYYY HH24:MI:SS')) ");
    		}
            
    		if (parameter[parameter.length - 1].equals(NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE)) {
    			sql.append("AND   EXISTS (SELECT 'X' ");
                sql.append("              FROM    NFE_MS_GROUPPRODUCT ");
                sql.append("              WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
                sql.append("              AND     (GROUPPRODUCT_LOANTYPE IN (?, 'B') ");
                sql.append("                      OR (GROUPPRODUCT_TYPE = 'B'  ");
                sql.append("                          AND GROUPPRODUCT_LOANTYPE IN (?, 'B')))) ");
    		} else {
    			sql.append("AND   EXISTS (SELECT 'X' ");
                sql.append("              FROM    NFE_MS_GROUPPRODUCT ");
                sql.append("              WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
                sql.append("              AND     (GROUPPRODUCT_LOANTYPE = ? ");
                sql.append("                       OR (GROUPPRODUCT_TYPE = 'B' ");
                sql.append("                           AND GROUPPRODUCT_LOANTYPE = ?))) ");
    		}
		}
		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#success(java.lang.Object[])
	 */
	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#fail(java.lang.Object[])
	 */
	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

}
