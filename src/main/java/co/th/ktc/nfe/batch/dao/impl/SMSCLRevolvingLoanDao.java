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
@Repository(value = "smsCLRevolvingLoanDao")
public class SMSCLRevolvingLoanDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public SMSCLRevolvingLoanDao() {
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
		
		sql.append("SELECT CLAPPLICATION_MOBILEPHONE AS MOBILE_NO,  ");
		sql.append("       SUBSTR(CLPRODUCT_CARDNUMBER, -4, 4) AS CLCARD_NO,  ");
		sql.append("       A.CREDIT_LIMIT ");
		sql.append("FROM NFE_CLAPPLICATION T ");
		sql.append("JOIN ( ");
		sql.append("SELECT T1.CLAPPLICATION_NO, ");
		sql.append("       T2.CLPRODUCT_CARDNUMBER, ");
		sql.append("       (SELECT MAX(APPROVE_CREDITLIMIT) ");
		sql.append("       FROM NFE_APP_APPROVE ");
		sql.append("       WHERE APPROVE_RESOLVEID > 0 ");
		sql.append("       AND   APPROVE_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("       GROUP BY APPROVE_APPNO) AS CREDIT_LIMIT ");
		sql.append("FROM NFE_CLAPPLICATION T1 ");
		sql.append("LEFT JOIN NFE_APP_CLPRODUCT T2 ");
		sql.append("  ON  T2.CLPRODUCT_ID > 0 ");
		sql.append("  AND T2.CLPRODUCT_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("WHERE T2.CLPRODUCT_PLASTICRELATION = 'M'  ");
		sql.append("AND T1.CLAPPLICATION_MOBILEPHONE IS NOT NULL ");
		sql.append("AND EXISTS(SELECT 'X' ");
		sql.append("           FROM NFE_APP_RESOLVE T3 ");
		sql.append("           WHERE T3.RESOLVE_ID > 0  ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.CLPRODUCT_ID ");
		sql.append("           AND T3.RESOLVE_STATUSCODE = ?) ");
		sql.append("AND EXISTS(SELECT 'X' ");
		sql.append("           FROM NFE_SCS_MAPPINGCODE  ");
		sql.append("           WHERE MAPPINGCODE_SCSCODE = T2.CLPRODUCT_GROUPPRODUCT ");
		sql.append("           AND MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE'   ");
		sql.append("           AND MAPPINGCODE_NFECODE = 'R' ) ");
		sql.append("AND EXISTS(SELECT 'X' ");
		sql.append("           FROM   NFE_APP_STATUSTRACKING ");
		sql.append("           WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("           AND    STATUSTRACKING_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("           AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("           AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                              AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("GROUP BY T1.CLAPPLICATION_NO, ");
		sql.append("         T2.CLPRODUCT_CARDNUMBER) A ");
		sql.append("    ON T.CLAPPLICATION_NO = A.CLAPPLICATION_NO ");


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

	@Override
	public SqlRowSet queryTrailer(Object[] parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
