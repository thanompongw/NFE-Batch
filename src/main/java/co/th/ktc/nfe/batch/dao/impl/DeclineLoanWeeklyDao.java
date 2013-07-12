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
@Repository(value = "declineLoanWeeklyDao")
public class DeclineLoanWeeklyDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public DeclineLoanWeeklyDao() {
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
		
		sql.append("SELECT TO_CHAR(SYSDATE, 'DD/MM/YYYY') AS PROCESSING_DATE, ");
		sql.append("       (SELECT NVL(G.GROUPPRODUCT_NAME, '') ");
		sql.append("        FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("               NFE_MS_PRODUCT P ");
		sql.append("        WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("        AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) AS PRODUCT_TYPE, ");
		sql.append("       (SELECT NVL(PRODUCT_DESCRIPTION, '') ");
		sql.append("        FROM NFE_MS_PRODUCT ");
		sql.append("        WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("        AND   PRODUCT_GROUPPRODUCTID > 0) AS CARD_TYPE, ");
		sql.append("       (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("        FROM NFE_MS_SUBPRODUCT ");
		sql.append("        WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("        AND   SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID) AS SUBCARD_TYPE, ");
		sql.append("       (SELECT NVL(SUBPRODUCT_PLASTICCODE, '') ");
		sql.append("        FROM NFE_MS_SUBPRODUCT ");
		sql.append("        WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS PLASTIC_CODE, ");
		sql.append("       NVL(T1.APP_SOURCECODE, '') AS SOURCE_CODE, ");
		sql.append("       NVL(T1.APP_AGENT, '') AS AGENT_CODE, ");
		sql.append("       NVL(T1.APP_CARDNO, '') AS ID_CARD, ");
		sql.append("       NVL(T1.APP_CUSTOMERNO, '') AS CUSTOMER_ACCOUNT_NO, ");
		sql.append("       NVL(T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME, '') AS THAI_NAME, ");
		sql.append("       NVL(T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME, '') AS ENGLISH_NAME, ");
		sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS APPLY_DATE, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS APPROVE_DATE, ");
		sql.append("       TO_CHAR(T3.RESOLVE_FINALDATE, 'DD/MM/YYYY') AS GATEWAY_FILE_DATE, ");
		sql.append("       NVL(T3.RESOLVE_STATUSCODE, '') AS RESULTS, ");
		sql.append("       (SELECT APPSTATUS_DESCRIPTION ");
		sql.append("        FROM NFE_MS_APPSTATUS ");
		sql.append("        WHERE APPSTATUS_CODE = T3.RESOLVE_STATUSCODE) AS RESULT_DESCRIPTION, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                THEN (SELECT PIF_CURRENTADDR_LINE1 || ' ' || PIF_CURRENTADDR_LINE2 ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT APPOCCUPATION_ADDRLINE1 || ' ' || APPOCCUPATION_ADDRLINE2 ");
		sql.append("                      FROM  NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESS, ");
		sql.append("       NVL(T1.APP_MAINCARDNO, '') AS PARENT_ID_CARD, ");
		sql.append("       (SELECT NVL(APPOCCUPATION_POSITION, '') ");
		sql.append("        FROM NFE_APP_OCCUPATION ");
		sql.append("        WHERE APPOCCUPATION_APPNO = T1.APP_NO) AS OCCUPATION_CODE, ");
		sql.append("       NVL(T3.RESOLVE_GENTYPE, '') AS CRITERIA_CODE, ");
		sql.append("       NVL(T1.APP_VSOURCE, '') AS DATA_ENTRY_SOURCE_CODE, ");
		sql.append("       NVL(T1.APP_VAGENT, '') AS DATA_ENTRY_NAME, ");
		sql.append("       NVL(T2.APPPRODUCT_CREDITLIMIT, '00.00') AS APPROVE_AMOUNT, ");
		sql.append("       NVL(T2.APPPRODUCT_CASHADVANCE, '00.00') AS MONEY_TRANSFER, ");
		sql.append("       NVL(T1.APP_NO, '') AS APPLICATION_ID, ");
		sql.append("       NVL(T1.APP_BRANCH, '') AS BRANCH_CODE, ");
		sql.append("       (SELECT NVL(PRODUCT_BINCODE, '') ");
		sql.append("        FROM NFE_MS_PRODUCT ");
		sql.append("        WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("        AND   PRODUCT_GROUPPRODUCTID > 0) AS BLOCK_CODE, ");
		sql.append("       TO_CHAR(T1.APP_UPDATEDATE, 'DD/MM/YYYY') AS BLOCK_DATE, ");
		sql.append("       '00.00' AS TOTAL_NEWBALANCE ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8D' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    G.GROUPPRODUCT_LOANTYPE IN ('F', 'R')) ");
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
