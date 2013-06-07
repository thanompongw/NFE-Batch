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
@Repository(value = "paymentChequePartnerDao")
public class PaymentChequePartnerDao extends AbstractReportDao {

	/**
	 * 
	 */
	public PaymentChequePartnerDao() {
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
		
		sql.append("SELECT (SELECT GROUPPRODUCT_NAME ");
		sql.append("        FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("        WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("        AND    GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("        AND    GROUPPRODUCT_TYPE = 'M') AS CARD_TYPE, ");
		sql.append("       ((SELECT PRODUCT_DESCRIPTION ");
		sql.append("         FROM   NFE_MS_PRODUCT ");
		sql.append("         WHERE  PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("         AND    PRODUCT_GROUPPRODUCTID > 0) ");
		sql.append("         || '/' ||  ");
		sql.append("         (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("          FROM   NFE_MS_SUBPRODUCT ");
		sql.append("          WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("          AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID)) AS CARD_TYPE, ");
		sql.append("       T4.APPROVE_CARDNO AS ACCOUNT_NO, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || APP_THAILNAME AS CUSTOMER_NAME_THAI, ");
		sql.append("       T1.APP_ENGFNAME || ' ' || APP_ENGLNAME AS CUSTOMER_NAME_ENGLISH, ");
		sql.append("      (SELECT B.BANK_NAME ");
		sql.append("       FROM NFE_MS_BANK B, ");
		sql.append("            NFE_APP_TRANSFERLOAN TL ");
		sql.append("       WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("       AND    TL.TRANSFERLOAN_ID > 0  ");
		sql.append("       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO) AS BANK_NAME, ");
		sql.append("      (SELECT TRANSFERLOAN_ACCNO ");
		sql.append("       FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("       WHERE  TRANSFERLOAN_ID > 0  ");
		sql.append("       AND    TRANSFERLOAN_APPNO = T1.APP_NO) AS BANK_ACCOUNT, ");
		sql.append("       T4.APPROVE_CREDITLIMIT AS TOTAL_AMOUNT ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON  T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CHKBALANCETRANSFER = 'S') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
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
