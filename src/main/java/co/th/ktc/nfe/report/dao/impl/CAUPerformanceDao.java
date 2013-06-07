/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.report.dao.AbstractReportDao;


@Repository(value="cauPerformanceDao")
public class CAUPerformanceDao extends AbstractReportDao {

	/**
	 * 
	 */
	public CAUPerformanceDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public SqlRowSet query(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT  T2.STATUSTRACKING_USER AS ANALYST,  ");
		sql.append("        SUM(CASE   ");
		sql.append("                WHEN T2.STATUSTRACKING_STATUS IN ('4A', '5A')  ");
		sql.append("                THEN 1  ");
		sql.append("                ELSE 0  ");
		sql.append("            END) AS ASSSIGN_REASSIGN,  ");
		sql.append("        SUM(CASE   ");
		sql.append("                WHEN (T2.STATUSTRACKING_STATUS = '6P')  ");
		sql.append("                THEN 1  ");
		sql.append("                ELSE 0  ");
		sql.append("            END) AS PRE_RESOLVE,  ");
		sql.append("        SUM(CASE   ");
		sql.append("                 WHEN (T2.STATUSTRACKING_STATUS = '6W')  ");
		sql.append("                 THEN 1  ");
		sql.append("                 ELSE 0  ");
		sql.append("            END) AS WAITING_FOR_OVERRIDE,  ");
		sql.append("        SUM(CASE   ");
		sql.append("                 WHEN (T2.STATUSTRACKING_STATUS = '7O')  ");
		sql.append("                 THEN 1  ");
		sql.append("                 ELSE 0  ");
		sql.append("            END) AS OVERRIDE,  ");
		sql.append("        SUM(CASE   ");
		sql.append("                WHEN T2.STATUSTRACKING_STATUS = '8F'  ");
		sql.append("                THEN 1  ");
		sql.append("                ELSE 0  ");
		sql.append("            END) AS FINAL_RESOLVE,  ");
		sql.append("        SUM(CASE   ");
		sql.append("                WHEN (T2.STATUSTRACKING_STATUS = '9C')  ");
		sql.append("                THEN 1  ");
		sql.append("                ELSE 0  ");
		sql.append("            END) AS CANCEL_AFTER_APPROVED,  ");
		sql.append("        ((SUM(CASE   ");
		sql.append("                WHEN T2.STATUSTRACKING_STATUS IN ('4A', '5A')  ");
		sql.append("                THEN 1  ");
		sql.append("                ELSE 0  ");
		sql.append("          END)) -  ");
		sql.append("        (SUM (CASE   ");
		sql.append("                WHEN T2.STATUSTRACKING_STATUS = '8F'  ");
		sql.append("                THEN 1  ");
		sql.append("                ELSE 0  ");
		sql.append("         END))) AS REMAIN  ");
		sql.append("FROM NFE_APPLICATION T1 ");
		sql.append("     LEFT JOIN NFE_APP_STATUSTRACKING T2 ");
		sql.append("     	ON  T2.STATUSTRACKING_ID     > 0  ");
		sql.append("        AND T2.STATUSTRACKING_APPNO  = T1.APP_NO ");
		sql.append("WHERE T1.APP_NO <> ' ' ");
		sql.append("AND   T2.STATUSTRACKING_STATUS <> ' ' ");
		sql.append("AND   (T2.STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                     AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");

		if (parameter != null && parameter.length > 2) {
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_MS_GROUPPRODUCT ");
			sql.append("               WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
			if (parameter.length > 4) {
				sql.append("               AND    (GROUPPRODUCT_LOANTYPE IN (?, ?) ");
				sql.append("                       OR (GROUPPRODUCT_TYPE = 'B'  ");
				sql.append("                           AND GROUPPRODUCT_LOANTYPE IN (?, ?)))) ");
			} else {
				sql.append("               AND    (GROUPPRODUCT_LOANTYPE = ? ");
				sql.append("                       OR (GROUPPRODUCT_TYPE = 'B'  ");
				sql.append("                           AND GROUPPRODUCT_LOANTYPE = ?))) ");
			}
		}
		sql.append("GROUP BY T2.STATUSTRACKING_USER  ");
		sql.append("ORDER BY T2.STATUSTRACKING_USER  ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(),parameter);
		
		return sqlRowSet;
	}

	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

}
