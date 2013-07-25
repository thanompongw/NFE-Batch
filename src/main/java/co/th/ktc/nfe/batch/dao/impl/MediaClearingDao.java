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
@Repository(value = "mediaClearingDao")
public class MediaClearingDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public MediaClearingDao() {
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
		sql.append("SELECT * ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT ROWNUM AS RNUM, A.* ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT 10 AS FILE_TYPE, ");
		sql.append("       2 AS RECORD_TYPE, ");
		sql.append("       LPAD(?, 6, '0') AS BATCH_NUMBER, ");
		sql.append("       RPAD((CASE ");
		sql.append("                 WHEN EXISTS (SELECT 'X' ");
		sql.append("                              FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                     NFE_MS_PRODUCT P ");
		sql.append("                              WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                              AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                              AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                      OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                          AND G.GROUPPRODUCT_LOANTYPE = 'F')))  ");
		sql.append("                 THEN (SELECT B.BANK_CODE ");
		sql.append("                       FROM      NFE_MS_BANK B, ");
		sql.append("                             NFE_APP_TRANSFERLOAN TL ");
		sql.append("                       WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                       AND    TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                 ELSE (SELECT B.BANK_CODE ");
		sql.append("                       FROM   NFE_MS_BANK B, ");
		sql.append("                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                    END), 3, '0') AS RECEIVING_BANK_CODE, ");
		sql.append("       RPAD((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                    NFE_MS_PRODUCT P ");
		sql.append("                             WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                             AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                             AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                     OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                         AND G.GROUPPRODUCT_LOANTYPE = 'F')))  ");
		sql.append("                THEN (CASE  ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                                       WHERE  TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                                       AND    B.BANK_CODE = '033') ");
		sql.append("                          THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 1, 3)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                                       WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                                       AND    TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '067') ");
		sql.append("                          THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 1, 4)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                                       WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                                       AND    TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '030') ");
		sql.append("                          THEN (CASE  ");
		sql.append("                                    WHEN (SELECT LENGTH(TRANSFERLOAN_ACCNO) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                          WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                          AND    TRANSFERLOAN_APPNO = T1.APP_NO) = 15 ");
		sql.append("                                    THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 1, 4)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                          WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                          AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                                    ELSE (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 1, 3)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                          WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                          AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                                END) ");
		sql.append("                          ELSE (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 1, 3)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                      END) ");
		sql.append("                ELSE (CASE  ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '033') ");
		sql.append("                          THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERDEBT_ACCNO, 1, 3)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                WHERE  TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '067') ");
		sql.append("                          THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERDEBT_ACCNO, 1, 4)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                WHERE  TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '030') ");
		sql.append("                          THEN (CASE  ");
		sql.append("                                    WHEN (SELECT LENGTH(TRANSFERDEBT_ACCNO) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                          WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                          AND    TRANSFERDEBT_APPNO = T1.APP_NO) = 15 ");
		sql.append("                                    THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERDEBT_ACCNO, 1, 4)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                          WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                          AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                                    ELSE (SELECT TO_CHAR('999' || SUBSTR(TRANSFERDEBT_ACCNO, 1, 1)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                          WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                          AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                                END) ");
		sql.append("                          ELSE (SELECT TO_CHAR('0' || SUBSTR(TRANSFERDEBT_ACCNO, 1, 3)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                     END) ");
		sql.append("            END), 4, '0') AS RECEIVING_BANK_BRANCH, ");
		sql.append("       RPAD((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                    NFE_MS_PRODUCT P ");
		sql.append("                             WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                             AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                             AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                     OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                         AND G.GROUPPRODUCT_LOANTYPE = 'F')))  ");
		sql.append("                THEN (CASE  ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                                       WHERE  TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                                       AND    B.BANK_CODE = '033') ");
		sql.append("                          THEN (SELECT TO_CHAR('00' || SUBSTR(TRANSFERLOAN_ACCNO, 4, 12)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                                       WHERE  TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                                       AND    B.BANK_CODE = '067') ");
		sql.append("                          THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 5, 14)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                                       WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                                       AND    TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                                       AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '030') ");
		sql.append("                          THEN (CASE  ");
		sql.append("                                    WHEN (SELECT LENGTH(TRANSFERLOAN_ACCNO) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                          WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                          AND    TRANSFERLOAN_APPNO = T1.APP_NO) = 15 ");
		sql.append("                                    THEN (SELECT TO_CHAR(SUBSTR(TRANSFERLOAN_ACCNO, 5, 15)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                          WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                          AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                                    ELSE (SELECT TO_CHAR(SUBSTR(TRANSFERLOAN_ACCNO, 2, 12)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                          WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                          AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                                END) ");
		sql.append("                          ELSE (SELECT TO_CHAR('0' || SUBSTR(TRANSFERLOAN_ACCNO, 1, 10)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                                WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                                AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("                      END) ");
		sql.append("                ELSE (CASE  ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '033') ");
		sql.append("                          THEN (SELECT TO_CHAR('00' || SUBSTR(TRANSFERDEBT_ACCNO, 4, 12)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                WHERE  TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '067') ");
		sql.append("                          THEN (SELECT TO_CHAR('0' || SUBSTR(TRANSFERDEBT_ACCNO, 5, 14)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                WHERE  TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                          WHEN EXISTS (SELECT 'X' ");
		sql.append("                                       FROM   NFE_MS_BANK B, ");
		sql.append("                                              NFE_APP_TRANSFERDEBT TD ");
		sql.append("                                       WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                                       AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                                       AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO ");
		sql.append("                                       AND    B.BANK_CODE = '030') ");
		sql.append("                          THEN (CASE  ");
		sql.append("                                    WHEN (SELECT LENGTH(TRANSFERDEBT_ACCNO) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                          WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                          AND    TRANSFERDEBT_APPNO = T1.APP_NO) = 15 ");
		sql.append("                                    THEN (SELECT TO_CHAR(SUBSTR(TRANSFERDEBT_ACCNO, 5, 15)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                          WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                          AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                                    ELSE (SELECT TO_CHAR(SUBSTR(TRANSFERDEBT_ACCNO, 2, 12)) ");
		sql.append("                                          FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                          WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                          AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                                END) ");
		sql.append("                          ELSE (SELECT TO_CHAR('0' || SUBSTR(TRANSFERDEBT_ACCNO, 1, 10)) ");
		sql.append("                                FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                                WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                                AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("                     END) ");
		sql.append("            END), 11, '0') AS RECEIVING_BANK_ACCOUNT, ");
		sql.append("       '006' AS SENDING_BANK_CODE_DEFAULT, ");
		sql.append("       '0000' AS SENDING_BANK_CODE, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'Configure_Media_KTB') AS SENDING_ACCCOUNT_NO, ");
		sql.append("       (SELECT TO_CHAR((CASE  ");
		sql.append("                            WHEN TO_CHAR((SELECT TO_TIMESTAMP(TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY')  ");
		sql.append("                                                              || '23:59:59', 'DD/MM/YYYY HH24:MI:SS') - 10/24 ");
		sql.append("                                          FROM NFE_SETDATE), 'D') = 6 ");
		sql.append("                            THEN B_DATE + 1 ");
		sql.append("                            ELSE B_DATE ");
		sql.append("                        END), 'DDMMYYYY') ");
		sql.append("        FROM  ");
		sql.append("        ( ");
		sql.append("        SELECT B_DATE AS B_DATE ");
		sql.append("        FROM   (SELECT ((SELECT TO_TIMESTAMP(TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY')  ");
		sql.append("                                                     || '23:59:59', 'DD/MM/YYYY HH24:MI:SS') - 10/24 ");
		sql.append("                         FROM NFE_SETDATE) + (LEVEL - 1)) + 2 AS B_DATE  ");
		sql.append("                FROM   DUAL  ");
		sql.append("                CONNECT BY LEVEL <= 10)  ");
		sql.append("        WHERE  TO_CHAR(B_DATE, 'D') NOT IN (7, 1)  ");
		sql.append("        AND    NOT EXISTS(SELECT 'X'  ");
		sql.append("                          FROM   NFE_HOLIDAY  ");
		sql.append("                          WHERE  HOLIDAY_DATE = CAST(B_DATE AS DATE)) ");
		sql.append("        ORDER BY ROWNUM ASC ");
		sql.append("        ) ");
		sql.append("        WHERE ROWNUM = 1) AS EFFECTIVE_DATE, ");
		sql.append("       '07' AS SERVICE_TYPE_CODE, ");
		sql.append("       '00' AS CLEARIGNG_HOUSE_CODE, ");
		sql.append("       LPAD(((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                      NFE_MS_PRODUCT P ");
		sql.append("                               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                               AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                           AND G.GROUPPRODUCT_LOANTYPE = 'F')))  ");
		sql.append("                   THEN NVL(T4.APPROVE_CREDITLIMIT, '00.00') ");
		sql.append("                   ELSE NVL(T4.APPROVE_CASHADVANCE, '00.00') ");
		sql.append("              END) * 100), 12, '0') AS TRANSFER_AMOUNT, ");
		sql.append("       'PERSONAL' AS RECEIVER_INFORMATION, ");
		sql.append("       RPAD(' ', 10, ' ') AS RECEIVER_ID, ");
		sql.append("       RPAD((T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME), 42, ' ') AS RECEIVER_NAME, ");
		sql.append("       RPAD((SELECT UPPER(CONFIGURATION_VALUE) ");
		sql.append("             FROM   NFE_CONFIGURATION ");
		sql.append("             WHERE  CONFIGURATION_NAME = 'CompanyName'), 60, ' ') AS SENDER_NAME, ");
		sql.append("       RPAD((T1.APP_NO  ");
		sql.append("             || '' ||  ");
		sql.append("             (SELECT PRODUCT_CODE ");
		sql.append("              FROM   NFE_MS_PRODUCT ");
		sql.append("              WHERE  PRODUCT_ID = T2.APPPRODUCT_PRODUCTID)), 58, ' ') AS OTHER_INFORMATION_I, ");
		sql.append("       LPAD(T4.APPROVE_CARDNO, 20, '0') AS REFERENCE_NUMBER, ");
		sql.append("       RPAD(' ', 22, ' ') AS OTHER_INFORMATION_II, ");
		sql.append("       RPAD(' ', 6, ' ') AS REFERENCE_RUNNING_NUMBER, ");
		sql.append("       RPAD('0', 23, '0') AS ZERO ");
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
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN ('F', 'R') ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN ('F', 'R')))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CHKBALANCETRANSFER = 'M') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append(") A ");
		sql.append("WHERE ROWNUM < ((? * 100) + 1 ) ");
		sql.append(") ");
		sql.append("WHERE RNUM >= (((? - 1) * 100) + 1) ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryHeader(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryHeader(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT 10 AS FILE_TYPE, ");
		sql.append("       1 AS RECORD_TYPE, ");
		sql.append("       LPAD(?, 6, '0') AS SEQ, ");
		sql.append("       '006' AS DEFAULT_BANK_CODE, ");
		sql.append("       LPAD(COUNT(1), 3, '0') AS TOTAL_RECORD, ");
		sql.append("       LPAD((SUM(BALANCE_TRANSFER) * 100), 15, '0') AS TOTAL_BALANCE_TRANSFER, ");
		sql.append("       TO_CHAR(SYSDATE + 2, 'DDMMYYYY') AS EFFECTIVE_DATE, ");
		sql.append("       'C' AS TYPE, ");
		sql.append("       'PERSONAL' AS GROUPPRODUCT_TYPE, ");
		sql.append("       LPAD('0', 271, '0') AS ZERO ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT ROWNUM AS RNUM, A.* ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                NFE_MS_PRODUCT P ");
		sql.append("                         WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                         AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                         AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                 OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                     AND G.GROUPPRODUCT_LOANTYPE = 'F')))  ");
		sql.append("             THEN NVL(T4.APPROVE_CREDITLIMIT, '00.00') ");
		sql.append("             ELSE NVL(T4.APPROVE_CASHADVANCE, '00.00') ");
		sql.append("        END) AS BALANCE_TRANSFER ");
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
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN ('F', 'R') ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN ('F', 'R')))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CHKBALANCETRANSFER = 'M') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append(") A ");
		sql.append("WHERE ROWNUM < ((? * 100) + 1 ) ");
		sql.append(") ");
		sql.append("WHERE RNUM >= (((? - 1) * 100) + 1) ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}
		
	public Integer size(Object[] parameter) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(1) AS TOTAL_RECORD ");
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
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN ('F', 'R') ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN ('F', 'R')))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CHKBALANCETRANSFER = 'M') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		
		return getJdbcTemplate().queryForInt(sql.toString(), parameter);
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
