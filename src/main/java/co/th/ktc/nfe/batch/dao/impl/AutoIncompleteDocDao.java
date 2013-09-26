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
@Repository(value = "autoIncompleteDocDao")
public class AutoIncompleteDocDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public AutoIncompleteDocDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO NFE_APP_STATUSTRACKING ( ");
		sql.append("STATUSTRACKING_APPNO, ");
		sql.append("STATUSTRACKING_STATUS, ");
		sql.append("STATUSTRACKING_USER, ");
		sql.append("STATUSTRACKING_STARTTIME, ");
		sql.append("STATUSTRACKING_ENDTIME, ");
		sql.append("STATUSTRACKING_DURATION) ");
		sql.append("VALUES (?, ");
		sql.append("        '8F', ");
		sql.append("        'SYSTEM', ");
		sql.append("        TO_TIMESTAMP(?, 'DD/MM/YYYY HH:MI:SS'), ");
		sql.append("        TO_TIMESTAMP(?, 'DD/MM/YYYY HH:MI:SS'), ");
		sql.append("        0) ");

		getJdbcTemplate().update(sql.toString(), new Object[] {parameter[0], 
															   parameter[1], 
															   parameter[1]});
		
		sql.setLength(0);
		
		sql.append("INSERT INTO NFE_APP_RESOLVE ");
		sql.append("(RESOLVE_APPNO , ");
		sql.append(" RESOLVE_APPPRODUCTID, ");
		sql.append(" RESOLVE_CREDITTYPE, ");
		sql.append(" RESOLVE_STATUSCODE, ");
		sql.append(" RESOLVE_REASONCODE, ");
		sql.append(" RESOLVE_GENTYPE, ");
		sql.append(" RESOLVE_PROCEEDBY, ");
		sql.append(" RESOLVE_PROCEEDDATE, ");
		sql.append(" RESOLVE_FINALBY, ");
		sql.append(" RESOLVE_FINALDATE ) ");
		sql.append("SELECT APPPRODUCT_APPNO AS APPNO, ");
		sql.append("       APPPRODUCT_PRODUCTID AS PRODUCTID, ");
		sql.append("       'M' AS CREADITTYPE, ");
		sql.append("       '8D' AS STATUSCODE, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION ");
		sql.append("        WHERE CONFIGURATION_NAME = 'DefaultReasonAutoReject') AS REASON_CODE, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION ");
		sql.append("        WHERE CONFIGURATION_NAME = 'GenerateTypeAutoReject') AS GEN_TYPE, ");
		sql.append("        'SYSTEM', ");
		sql.append("        TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("        'SYSTEM', ");
		sql.append("        TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("FROM NFE_APP_PRODUCT ");
		sql.append("WHERE APPPRODUCT_ID > 0 ");
		sql.append("AND   APPPRODUCT_APPNO = ? ");
		sql.append("UNION ALL ");
		sql.append("SELECT APPSUP_APPNO AS APPNO, ");
		sql.append("       APPSUP_PRODUCT AS PRODUCTID, ");
		sql.append("       'S' AS CREADITTYPE, ");
		sql.append("       '8D' AS STATUSCODE, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION ");
		sql.append("        WHERE CONFIGURATION_NAME = 'DefaultReasonAutoReject') AS REASON_CODE, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION ");
		sql.append("        WHERE CONFIGURATION_NAME = 'GenerateTypeAutoReject') AS GEN_TYPE, ");
		sql.append("        'SYSTEM', ");
		sql.append("        TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("        'SYSTEM', ");
		sql.append("        TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("FROM NFE_APP_SUPPLEMENTARY ");
		sql.append("WHERE APPSUP_ID > 0 ");
		sql.append("AND   APPSUP_APPNO = ? ");
		
		getJdbcTemplate().update(sql.toString(), new Object[] {parameter[1], 
															   parameter[1], 
															   parameter[0],
															   parameter[1], 
															   parameter[1], 
															   parameter[0]});

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#update(java.lang.Object[])
	 */
	@Override
	public void update(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE NFE_APPLICATION ");
		sql.append("SET APP_STATUSCODE = '8D', ");
		sql.append("APP_UPDATEDATE = TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'), ");
		sql.append("APP_UPDATEBY = 'SYSTEM' ");
		sql.append("WHERE APP_NO = ? ");
		
		getJdbcTemplate().update(sql.toString(), new Object[] {parameter[1],
															   parameter[0]});
		
		sql.setLength(0);
		
		sql.append("UPDATE NFE_APP_STATUSTRACKING ");
		sql.append("SET STATUSTRACKING_ENDTIME  = TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'),  ");
		sql.append("STATUSTRACKING_DURATION = (TO_DATE(?, 'DD/MM/YYYY') ");
		sql.append("- TO_DATE(TO_CHAR(STATUSTRACKING_STARTTIME, 'DD/MM/YYYY'), 'DD/MM/YYYY')) ");
		sql.append("WHERE STATUSTRACKING_APPNO = ? ");
		sql.append("AND STATUSTRACKING_STATUS = '2I' ");
		
		getJdbcTemplate().update(sql.toString(), new Object[] {parameter[1],
															   parameter[2],
															   parameter[0]});


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
		
		sql.append("SELECT STATUSTRACKING_APPNO AS APP_NO ");
		sql.append("FROM NFE_APP_STATUSTRACKING ");
		sql.append("WHERE STATUSTRACKING_APPNO <> ' ' ");
		sql.append("AND STATUSTRACKING_STATUS = '2I' ");
		sql.append("AND STATUSTRACKING_ENDTIME IS NULL ");
		sql.append("AND (TO_DATE(?, 'DD/MM/YYYY') ");
		sql.append("   - TO_DATE(TO_CHAR(STATUSTRACKING_STARTTIME, 'DD/MM/YYYY'), 'DD/MM/YYYY') > ");
		sql.append("		(SELECT CONFIGURATION_VALUE ");
		sql.append("         FROM NFE_CONFIGURATION ");
		sql.append("         WHERE CONFIGURATION_NAME = 'IncompleteDocTimeForAutoReject')) ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter[0]);
		
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
