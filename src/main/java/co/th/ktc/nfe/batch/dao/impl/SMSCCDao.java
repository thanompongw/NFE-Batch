/**
 * 
 */
package co.th.ktc.nfe.batch.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.batch.dao.AbstractBatchDao;

/**
 * @author temp_dev1
 *
 */
@Repository(value = "smsCCDao")
public class SMSCCDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public SMSCCDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#update(java.lang.Object[])
	 */
	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#delete(java.lang.Object[])
	 */
	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryDetail(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryDetail(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT RPAD(NVL(T4.PIF_CURRENTADDR_MOBILENO, ' '), 10, ' ') AS MOBILENO ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_PIF T4 ");
		sql.append("           ON T4.PIF_APPNO = T1.APP_NO ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.PIF_CURRENTADDR_MOBILENO IS NOT NULL ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    G.GROUPPRODUCT_LOANTYPE = 'C') ");
		sql.append("AND    EXISTS (SELECT 'X'  ");
		sql.append("               FROM NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND   SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND   SUBPRODUCT_CODE NOT IN ('I01', 'I03', 'I06', 'W01', 'W02')) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT RPAD(NVL(S1.APPSUP_CURRENTADDR_MOBILENO, ' '), 10, ' ') AS MOBILENO ");
		sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
		sql.append("       LEFT JOIN NFE_APPLICATION S2 ");
		sql.append("            ON S2.APP_NO = S1.APPSUP_APPNO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE S3 ");
		sql.append("           ON  S3.RESOLVE_ID > 0 ");
		sql.append("           AND S3.RESOLVE_APPNO = S1.APPSUP_APPNO ");
		sql.append("           AND S3.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
		sql.append("WHERE  S1.APPSUP_APPNO <> ' ' ");
		sql.append("AND    S3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    S1.APPSUP_CURRENTADDR_MOBILENO IS NOT NULL ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
		sql.append("               AND    G.GROUPPRODUCT_ID > 0 ");
		sql.append("               AND    G.GROUPPRODUCT_LOANTYPE = 'C') ");
		sql.append("AND    EXISTS (SELECT 'X'  ");
		sql.append("               FROM NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT ");
		sql.append("               AND   SUBPRODUCT_PRODUCTID = S1.APPSUP_PRODUCT ");
		sql.append("               AND   SUBPRODUCT_CODE NOT IN ('I01', 'I03', 'I06', 'W01', 'W02')) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryHeader(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryHeader(Object[] parameter) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#success(java.lang.Object[])
	 */
	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#fail(java.lang.Object[])
	 */
	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

}
