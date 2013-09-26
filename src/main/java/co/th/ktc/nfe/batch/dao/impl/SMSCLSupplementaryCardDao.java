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
@Repository(value = "smsCLSupplementaryCardDao")
public class SMSCLSupplementaryCardDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public SMSCLSupplementaryCardDao() {
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
		
		sql.append("SELECT A.CLAPPLICATION_MOBILEPHONE AS MOBILE_NO, ");
		sql.append("       SUBSTR(T.APPROVE_CARDNO, 13) AS CLCARD_NO, ");
		sql.append("       T.APPROVE_CREDITLIMIT AS CREDIT_LIMIT ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT T1.CLAPPLICATION_NO, ");
		sql.append("       T2.CLPRODUCT_PLASTICRELATION, ");
		sql.append("       T1.CLAPPLICATION_MOBILEPHONE ");
		sql.append("FROM NFE_CLAPPLICATION T1 ");
		sql.append("LEFT JOIN NFE_APP_CLPRODUCT T2 ");
		sql.append("    ON  T2.CLPRODUCT_ID > 0 ");
		sql.append("    AND T2.CLPRODUCT_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("    ON T3.RESOLVE_ID > 0  ");
		sql.append("    AND T3.RESOLVE_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("    AND T3.RESOLVE_APPPRODUCTID = T2.CLPRODUCT_ID ");
		sql.append("WHERE T1.CLAPPLICATION_MOBILEPHONE IS NOT NULL ");
		sql.append("AND T3.RESOLVE_STATUSCODE = ? ");
		sql.append("AND EXISTS(SELECT 'X' ");
		sql.append("           FROM NFE_SCS_MAPPINGCODE  ");
		sql.append("           WHERE MAPPINGCODE_SCSCODE = T2.CLPRODUCT_GROUPPRODUCT ");
		sql.append("           AND MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
		sql.append("           AND MAPPINGCODE_NFECODE = 'C' ) ");
		sql.append("AND EXISTS(SELECT 'X' ");
		sql.append("           FROM   NFE_APP_STATUSTRACKING ");
		sql.append("           WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("           AND    STATUSTRACKING_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("           AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("           AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                              AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("GROUP BY T1.CLAPPLICATION_NO, ");
		sql.append("         T2.CLPRODUCT_PLASTICRELATION, ");
		sql.append("         T1.CLAPPLICATION_MOBILEPHONE ");
		sql.append("HAVING COUNT(DISTINCT(CLPRODUCT_PLASTICRELATION)) = 1 ");
		sql.append(") A ");
		sql.append("JOIN NFE_APP_APPROVE T ");
		sql.append("    ON T.APPROVE_RESOLVEID > 0 ");
		sql.append("    AND T.APPROVE_APPNO = A.CLAPPLICATION_NO ");
		sql.append("WHERE A.CLPRODUCT_PLASTICRELATION = 'S' ");
		sql.append("AND   T.APPROVE_CREDITTYPE = 'S' ");

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
