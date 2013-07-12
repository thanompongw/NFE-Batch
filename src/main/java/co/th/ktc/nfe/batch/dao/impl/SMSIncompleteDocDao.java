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
@Repository(value = "smsIncompleteDocDao")
public class SMSIncompleteDocDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public SMSIncompleteDocDao() {
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
		
		sql.append("SELECT T2.PIF_CURRENTADDR_MOBILENO AS MOBILENO ");
		sql.append("FROM NFE_APPLICATION T1 ");
		sql.append("JOIN NFE_APP_PIF T2 ");
		sql.append("        ON T2.PIF_APPNO = T1.APP_NO ");
		sql.append("WHERE T1.APP_STATUSCODE = '2I' ");
		sql.append("AND   T2.PIF_CURRENTADDR_MOBILENO IS NOT NULL ");
		sql.append("AND   EXISTS (SELECT 'X' ");
		sql.append("              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("              AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("              AND    STATUSTRACKING_STATUS = '2I' ");
		sql.append("              AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                 AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT S1.APPSUP_CURRENTADDR_MOBILENO AS MOBILENO ");
		sql.append("FROM NFE_APP_SUPPLEMENTARY S1 ");
		sql.append("JOIN NFE_APPLICATION S2 ");
		sql.append("        ON S2.APP_NO = S1.APPSUP_APPNO ");
		sql.append("WHERE S2.APP_STATUSCODE = '2I' ");
		sql.append("AND   S1.APPSUP_CURRENTADDR_MOBILENO IS NOT NULL ");
		sql.append("AND   EXISTS (SELECT 'X'  ");
		sql.append("              FROM   NFE_MS_GROUPPRODUCT  ");
		sql.append("              WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
		sql.append("              AND    GROUPPRODUCT_TYPE = 'S') ");
		sql.append("AND   EXISTS (SELECT 'X' ");
		sql.append("              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("              AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
		sql.append("              AND    STATUSTRACKING_STATUS = '2I' ");
		sql.append("              AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                 AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");

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
