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
@Repository(value = "autoReceiveApplicationDao")
public class AutoReceiveApplicationDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public AutoReceiveApplicationDao() {
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
		
		sql.append("SELECT (SELECT T.PRODUCTTYPE_DESCRIPTION ");
		sql.append("        FROM NFE_MS_PRODUCTTYPE T, ");
		sql.append("             NFE_MS_PRODUCT P ");
		sql.append("        WHERE P.PRODUCT_PRODUCTTYPE = T.PRODUCTTYPE_ID ");
		sql.append("        AND   P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) AS PRODUCT_TYPE, ");
		sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS APP_CREATE, ");
		sql.append("       T1.APP_NO AS APPLY_NO, ");
		sql.append("       T2.APPPRODUCT_PRODUCTID AS PRODUCT_CODE, ");
		sql.append("       T2.APPPRODUCT_SUBPRODUCTID AS SUB_PRODUCT_CODE, ");
		sql.append("       (SELECT SUBPRODUCT_PLASTICCODE ");
		sql.append("        FROM NFE_MS_SUBPRODUCT ");
		sql.append("        WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS PLASTIC_CODE, ");
		sql.append("       T1.APP_SOURCECODE AS SOURCE_CODE, ");
		sql.append("       T1.APP_AGENT AS AGENT_CODE, ");
		sql.append("       T1.APP_BRANCH AS BRANCH_CODE, ");
		sql.append("       (SELECT SUBPRODUCT_SLOYALTYCARD1 ");
		sql.append("        FROM NFE_MS_SUBPRODUCT ");
		sql.append("        WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS ID_CARD, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAI_NAME, ");
		sql.append("       (SELECT APPSTATUS_DESCRIPTION ");
		sql.append("        FROM NFE_MS_APPSTATUS ");
		sql.append("        WHERE APPSTATUS_CODE = T3.RESOLVE_STATUSCODE) AS APP_STATUS, ");
		sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS APP_DATE, ");
		sql.append("       (SELECT REASON_DESCRIPTION ");
		sql.append("        FROM NFE_MS_REASON ");
		sql.append("        WHERE REASON_CODE = T3.RESOLVE_REASONCODE) AS REASON, ");
		sql.append("       (SELECT SUBPRODUCT_PLASTICCODE ");
		sql.append("        FROM NFE_MS_SUBPRODUCT ");
		sql.append("        WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS CC_TYPE, ");
		sql.append("       (SELECT DTA_MONTHLYINCOME ");
		sql.append("        FROM NFE_APP_DATAANALYSIS ");
		sql.append("        WHERE DTA_APPNO = T1.APP_NO) AS SALARY, ");
		sql.append("       (SELECT O.OCCUPATION_ENAME ");
		sql.append("        FROM NFE_MS_OCCUPATION O, ");
		sql.append("             NFE_APP_PIF AP ");
		sql.append("        WHERE O.OCCUPATION_ID = AP.PIF_OTHERS_OCCUPATION ");
		sql.append("        AND   AP.PIF_APPNO = T1.APP_NO) AS OCCUPATION, ");
		sql.append("        NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                THEN (SELECT PIF_CURRENTADDR_ZIPCODE ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT APPOCCUPATION_ZIPCODE ");
		sql.append("                      FROM   NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("             END), '') AS ZIPCODE, ");
		sql.append("        NVL(T1.APP_CREDITLINE, T2.APPPRODUCT_CREDITLIMIT) AS LINELIMIT_APP, ");
		sql.append("        T4.APPROVE_CASHADVANCE AS TRANSFER_AMT, ");
		sql.append("        '' AS CRITERIA ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T1.APP_STATUSCODE <> '2C' ");
		sql.append("AND   (T1.APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                           AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");

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
