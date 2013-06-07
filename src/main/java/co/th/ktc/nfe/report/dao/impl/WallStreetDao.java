/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

/**
 * @author Deedy
 *
 */
@Repository(value = "wallStreetDao")
public class WallStreetDao extends AbstractReportDao {

	/**
	 * 
	 */
	public WallStreetDao() {
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
		
		sql.append("SELECT T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME  AS CUSTOMER_NAME, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS OPEN_DATE,  ");
		sql.append("        NVL(T4.APPROVE_CREDITLIMIT, 00.00) AS AMOUNT, ");
		sql.append("        NVL(T4.APPROVE_TERM, 0) AS TERM, ");
		sql.append("        T1.APP_SOURCECODE AS SOURCE_CODE, ");
		sql.append("        T1.APP_BRANCH AS BRANCH_CODE, ");
		sql.append("        T1.APP_AGENT AS AGENT_ID, ");
		sql.append("        T1.APP_NO AS APPLICATION_NO,  ");
		sql.append("        NVL(T4.APPROVE_COMMINTEREST, 00.00) AS COMMINTEREST,  ");
		sql.append("        NVL((T4.APPROVE_COMMINTEREST * 0.03), 00.00) AS TAX,  ");
		sql.append("        (SELECT P.PRODUCT_CODE || '/' || S.SUBPRODUCT_CODE ");
		sql.append("         FROM   NFE_MS_PRODUCT P, ");
		sql.append("                NFE_MS_SUBPRODUCT S ");
		sql.append("         WHERE  S.SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("         AND    S.SUBPRODUCT_PRODUCTID = P.PRODUCT_ID ");
		sql.append("         AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID)  AS PRODUCT_SUBPRODUCT ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T1.APP_SOURCECODE IN ('WSC', 'WSX') ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
		sql.append("AND    EXISTS (SELECT 'X'   ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT  ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CODE  = 'ULN') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		
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
