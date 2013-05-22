/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

/**
 * @author Deedy
 *
 */
public class BBCReportDao extends AbstractReportDao {

	/**
	 * 
	 */
	public BBCReportDao() {
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
	@Override
	public SqlRowSet query(Object[] parameter,String sheetname) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#query(java.lang.Object[])
	 */
	@Override
	public SqlRowSet query(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT NVL(T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME, '') AS CUSTOMER_NAME, ");
		sql.append("       TO_CHAR(T1.APP_UPDATEDATE, 'DD/MM/YYYY')AS OPEN_DATE, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT, '00.00') AS AMOUNT, ");
		sql.append("       NVL(T4.APPROVE_TERM, '0') AS TERM, ");
		sql.append("       NVL(T1.APP_SOURCECODE, '') AS SOURCE_CODE, ");
		sql.append("       NVL(T1.APP_BRANCH, '') AS BRANCH_CODE, ");
		sql.append("       NVL(T1.APP_AGENT, '') AS AGENT_ID, ");
		sql.append("       NVL(T1.APP_NO, '') AS APPLICATION_NO, ");
		sql.append("       NVL(T4.APPROVE_COMMINTEREST, '00.00') AS COMMINTEREST, ");
		sql.append("       NVL((T4.APPROVE_COMMINTEREST * 0.03), '00.00') AS TAX, ");
		sql.append("       (SELECT PRODUCT_CODE ");
		sql.append("        FROM NFE_MS_PRODUCT ");
		sql.append("        WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) ");
		sql.append("        || '/' || ");
		sql.append("       (SELECT SUBPRODUCT_CODE ");
		sql.append("        FROM NFE_MS_SUBPRODUCT ");
		sql.append("        WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS PRODUCT_SUBPRODUCT ");
		sql.append("FROM NFE_APPLICATION T1, ");
		sql.append("     NFE_APP_PRODUCT T2, ");
		sql.append("     NFE_APP_RESOLVE T3, ");
		sql.append("     NFE_APP_APPROVE T4, ");
		sql.append("     NFE_APP_STATUSTRACKING T5 ");
		sql.append("WHERE T1.APP_NO                <> ' ' ");
		sql.append("AND   T2.APPPRODUCT_ID         > 0 ");
		sql.append("AND   T2.APPPRODUCT_APPNO      = T1.APP_NO ");
		sql.append("AND   T3.RESOLVE_ID            > 0 ");
		sql.append("AND   T3.RESOLVE_APPNO         = T1.APP_NO ");
		sql.append("AND   T3.RESOLVE_APPPRODUCTID  = T2.APPPRODUCT_ID ");
		sql.append("AND   T3.RESOLVE_STATUSCODE    = '8A' ");
		sql.append("AND   T4.APPROVE_RESOLVEID     = T3.RESOLVE_ID ");
		sql.append("AND   T4.APPROVE_APPNO         = T1.APP_NO ");
		sql.append("AND   T4.APPROVE_PRODUCTID     = T2.APPPRODUCT_PRODUCTID ");
		sql.append("AND   T4.APPROVE_SUBPRODUCTID  = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("AND   T5.STATUSTRACKING_ID     > 0 ");
		sql.append("AND   T5.STATUSTRACKING_APPNO  = T1.APP_NO ");
		sql.append("AND   T5.STATUSTRACKING_STATUS = '8F' ");
		sql.append("AND   ?                        = TO_CHAR(T5.STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("AND   EXISTS (SELECT 'X' ");
		sql.append("              FROM NFE_MS_GROUPPRODUCT G, ");
		sql.append("                   NFE_MS_PRODUCT P ");
		sql.append("              WHERE P.PRODUCT_ID             = T2.APPPRODUCT_PRODUCTID ");
		sql.append("              AND   P.PRODUCT_GROUPPRODUCTID = G.GROUPPRODUCT_ID ");
		sql.append("              AND   P.PRODUCT_CODE           = 'PVL' ");
		sql.append("              AND   G.GROUPPRODUCT_LOANTYPE  = 'F') ");
		sql.append("AND   EXISTS (SELECT 'X' ");
		sql.append("              FROM NFE_MS_SUBPRODUCT ");
		sql.append("              WHERE SUBPRODUCT_ID        = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("              AND   SUBPRODUCT_PRODUCTID > 0 ");
		sql.append("              AND   SUBPRODUCT_CODE      = 'ULN' ");
		sql.append("              AND   T1.APP_SOURCECODE    = 'BBC') ");
		
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
