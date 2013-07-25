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
@Repository(value = "letterApproveRevolvingLoanLA01Dao")
public class LetterApproveRevolvingLoanLA01Dao extends AbstractBatchDao {

	/**
	 * 
	 */
	public LetterApproveRevolvingLoanLA01Dao() {
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
		
		sql.append("SELECT   T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAI_NAME, ");
		sql.append("         NVL((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT PIF_CURRENTADDR_LINE1 ");
		sql.append("                        FROM   NFE_APP_PIF ");
		sql.append("                        WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT APPOCCUPATION_WORKPLACE ");
		sql.append("                        FROM   NFE_APP_OCCUPATION  ");
		sql.append("                        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), '') AS ADDRESSLINE1, ");
		sql.append("         NVL((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT PIF_CURRENTADDR_LINE2 ");
		sql.append("                        FROM   NFE_APP_PIF ");
		sql.append("                        WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT APPOCCUPATION_ADDRLINE1 || ' ' || APPOCCUPATION_ADDRLINE2 ");
		sql.append("                        FROM  NFE_APP_OCCUPATION  ");
		sql.append("                        WHERE APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), '') AS ADDRESSLINE2, ");
		sql.append("         NVL((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1)  ");
		sql.append("                  THEN ((SELECT D.DISTRICT_TNAME ");
		sql.append("                         FROM   NFE_MS_DISTRICT D, ");
		sql.append("                                NFE_APP_PIF P ");
		sql.append("                         WHERE  D.DISTRICT_ID = P.PIF_CURRENTADDR_DISTRICT ");
		sql.append("                         AND    P.PIF_APPNO = T1.APP_NO) ");
		sql.append("                       || '  ' ");
		sql.append("                       || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                           FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                  NFE_APP_PIF P ");
		sql.append("                           WHERE  S.SUBPROVINCE_ID = P.PIF_CURRENTADDR_AMPHUR ");
		sql.append("                           AND    P.PIF_APPNO = T1.APP_NO)) ");
		sql.append("                  ELSE ((SELECT D.DISTRICT_TNAME ");
		sql.append("                         FROM   NFE_MS_DISTRICT D, ");
		sql.append("                                NFE_APP_OCCUPATION O ");
		sql.append("                         WHERE  DISTRICT_ID = O.APPOCCUPATION_DISTRICT ");
		sql.append("                         AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                       || '  ' ");
		sql.append("                       || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                           FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                  NFE_APP_OCCUPATION O ");
		sql.append("                           WHERE  S.SUBPROVINCE_ID = O.APPOCCUPATION_AMPHUR ");
		sql.append("                           AND    O.APPOCCUPATION_APPNO = T1.APP_NO)) ");
		sql.append("              END), '') AS ADDRESSLINE3, ");
		sql.append("         NVL((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT P.PROVINCE_TNAME ");
		sql.append("                        FROM   NFE_MS_PROVINCE P, ");
		sql.append("                               NFE_APP_PIF AP ");
		sql.append("                        WHERE  P.PROVINCE_ID = AP.PIF_CURRENTADDR_PROVINCE ");
		sql.append("                        AND    AP.PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT P.PROVINCE_TNAME ");
		sql.append("                        FROM   NFE_MS_PROVINCE P, ");
		sql.append("                               NFE_APP_OCCUPATION O ");
		sql.append("                        WHERE  P.PROVINCE_ID = O.APPOCCUPATION_PROVINCE ");
		sql.append("                        AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), '') AS ADDRESSLINE4, ");
		sql.append("         NVL((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT PIF_CURRENTADDR_ZIPCODE ");
		sql.append("                        FROM   NFE_APP_PIF ");
		sql.append("                        WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT APPOCCUPATION_ZIPCODE ");
		sql.append("                        FROM   NFE_APP_OCCUPATION  ");
		sql.append("                        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), '') AS ADDRESSLINE5, ");
		sql.append("         T1.APP_NO AS APPLICATION_NO, ");
		sql.append("         (SELECT TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY') FROM NFE_SETDATE) AS DATES, ");
		sql.append("         (SELECT PRODUCT_DESCRIPTION ");
		sql.append("          FROM     NFE_MS_PRODUCT ");
		sql.append("          WHERE  PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("          AND    PRODUCT_GROUPPRODUCTID > 0) AS PRODUCT_DESC, ");
		sql.append("         (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("          FROM   NFE_APP_STATUSTRACKING ");
		sql.append("          WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("          AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("          AND    STATUSTRACKING_STATUS = '8F') AS APPROVE_DATE, ");
		sql.append("         T4.APPROVE_CARDNO AS CARD_NUMBER, ");
		sql.append("         NVL(T4.APPROVE_CREDITLIMIT, '00.00') AS CREDIT_LIMIT, ");
		sql.append("         NVL(T4.APPROVE_CASHADVANCE, '00.00') AS CASH_ADVANCE, ");
		sql.append("         NVL(T4.APPROVE_PERCENTINTEREST, '00.00') AS NORMAL_INTEREST, ");
		sql.append("         NVL(T4.APPROVE_COMMINTEREST, '00.00') AS NORMAL_COMM_INTEREST, ");
		sql.append("         (CASE ");
		sql.append("              WHEN     T2.APPPRODUCT_PROMOTIONRATE = 0  ");
		sql.append("                   AND T2.APPPRODUCT_PROMOTIONTERMS > 0  ");
		sql.append("              THEN TO_CHAR(NVL(T2.APPPRODUCT_PROMOTIONRATE, 0)) ");
		sql.append("              ELSE '-' ");
		sql.append("          END) AS SPECIAL_INTEREST, ");
		sql.append("         (CASE ");
		sql.append("              WHEN     T2.APPPRODUCT_PROMOTIONRATE = 0  ");
		sql.append("                   AND T2.APPPRODUCT_PROMOTIONTERMS > 0  ");
		sql.append("              THEN TO_CHAR(NVL(T2.APPPRODUCT_PROMOTIONTERMS, 0)) ");
		sql.append("              ELSE '-' ");
		sql.append("          END) AS SPECIAL_TERMS, ");
		sql.append("         NVL((SELECT BK.BANK_NAME ");
		sql.append("              FROM   NFE_APP_TRANSFERDEBT TD, ");
		sql.append("                     NFE_MS_BANK BK ");
		sql.append("              WHERE  TD.TRANSFERDEBT_ID > 0  ");
		sql.append("              AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("              AND    BK.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("              ), '-') AS BANK, ");
		sql.append("         (SELECT NVL(TRANSFERDEBT_ACCNAME, '-') ");
		sql.append("          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("          WHERE  TRANSFERDEBT_ID > 0  ");
		sql.append("          AND    TRANSFERDEBT_APPNO = T1.APP_NO) AS ACCOUNT_NAME, ");
		sql.append("         (SELECT NVL(TRANSFERDEBT_ACCNO, '-') ");
		sql.append("          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("          WHERE  TRANSFERDEBT_ID > 0  ");
		sql.append("          AND    TRANSFERDEBT_APPNO = T1.APP_NO) AS ACCOUNT_NO ");
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
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    G.GROUPPRODUCT_LOANTYPE = 'R') ");
		sql.append("AND   EXISTS (SELECT 'X' ");
		sql.append("              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("              AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("              AND    STATUSTRACKING_STATUS = '8F' ");
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
