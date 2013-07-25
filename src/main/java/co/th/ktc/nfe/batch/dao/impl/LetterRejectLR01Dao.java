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
@Repository(value = "letterRejectLR01Dao")
public class LetterRejectLR01Dao extends AbstractBatchDao {

	/**
	 * 
	 */
	public LetterRejectLR01Dao() {
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
		
		sql.append("SELECT T1.APP_NO AS APPLICATION_NO, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS CUSTOMER_NAME, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 2) ");
		sql.append("                THEN (SELECT APPOCCUPATION_WORKPLACE ");
		sql.append("                      FROM   NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT PIF_CURRENTADDR_LINE1 ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESSLINE1, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 2) ");
		sql.append("                THEN (SELECT APPOCCUPATION_ADDRLINE1 || ' ' || APPOCCUPATION_ADDRLINE2 ");
		sql.append("                      FROM  NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT PIF_CURRENTADDR_LINE2 ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESSLINE2, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 2) ");
		sql.append("                THEN ((SELECT D.DISTRICT_TNAME ");
		sql.append("                       FROM   NFE_MS_DISTRICT D, ");
		sql.append("                              NFE_APP_OCCUPATION O ");
		sql.append("                       WHERE  DISTRICT_ID = O.APPOCCUPATION_DISTRICT ");
		sql.append("                       AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                      || '  ' ");
		sql.append("                      || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                          FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                 NFE_APP_OCCUPATION O ");
		sql.append("                          WHERE  S.SUBPROVINCE_ID = O.APPOCCUPATION_AMPHUR ");
		sql.append("                          AND    O.APPOCCUPATION_APPNO = T1.APP_NO)) ");
		sql.append("                  ELSE ((SELECT D.DISTRICT_TNAME ");
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
		sql.append("            END), '') AS ADDRESSLINE3, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 2) ");
		sql.append("                THEN (SELECT P.PROVINCE_TNAME ");
		sql.append("                      FROM   NFE_MS_PROVINCE P, ");
		sql.append("                             NFE_APP_OCCUPATION O ");
		sql.append("                      WHERE  P.PROVINCE_ID = O.APPOCCUPATION_PROVINCE ");
		sql.append("                      AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT P.PROVINCE_TNAME ");
		sql.append("                      FROM   NFE_MS_PROVINCE P, ");
		sql.append("                             NFE_APP_PIF AP ");
		sql.append("                      WHERE  P.PROVINCE_ID = AP.PIF_CURRENTADDR_PROVINCE ");
		sql.append("                      AND    AP.PIF_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESSLINE4, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 2) ");
		sql.append("                THEN (SELECT APPOCCUPATION_ZIPCODE ");
		sql.append("                      FROM   NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT PIF_CURRENTADDR_ZIPCODE ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESSLINE5, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY') FROM NFE_SETDATE) AS DATE_LETER, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    (CASE  ");
		sql.append("                    WHEN T2.RESOLVE_STATUSCODE = '8D' ");
		sql.append("                         AND STATUSTRACKING_STATUS = '8F' ");
		sql.append("                    THEN 1 ");
		sql.append("                    WHEN T2.RESOLVE_STATUSCODE = '2R' ");
		sql.append("                         AND STATUSTRACKING_STATUS = '2R' ");
		sql.append("                    THEN 1 ");
		sql.append("                    ELSE 0 ");
		sql.append("                END) = 1) AS DATES, ");
		// For Oracle upper 10g
		sql.append("        (SELECT LISTAGG((CASE ");
		sql.append("                            WHEN EXISTS (SELECT 'X' ");
		sql.append("                                         FROM    NFE_MS_GROUPPRODUCT G, ");
		sql.append("                                                 NFE_MS_PRODUCT P ");
		sql.append("                                         WHERE   P.PRODUCT_ID = APPPRODUCT_PRODUCTID ");
		sql.append("                                         AND     G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                                         AND     G.GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("                            THEN 'สินเชื่อบุคคล KTC CASH' ");
		sql.append("                            WHEN EXISTS (SELECT 'X' ");
		sql.append("                                         FROM    NFE_MS_GROUPPRODUCT G, ");
		sql.append("                                                 NFE_MS_PRODUCT P ");
		sql.append("                                         WHERE   P.PRODUCT_ID = APPPRODUCT_PRODUCTID ");
		sql.append("                                         AND     G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                                         AND     G.GROUPPRODUCT_LOANTYPE = 'R') ");
		sql.append("                            THEN 'สินเชื่อพร้อมใช้ KTC CASH REVOLVE' ");
		sql.append("                            WHEN EXISTS (SELECT 'X' ");
		sql.append("                                         FROM    NFE_MS_GROUPPRODUCT G, ");
		sql.append("                                                 NFE_MS_PRODUCT P ");
		sql.append("                                         WHERE   P.PRODUCT_ID = APPPRODUCT_PRODUCTID ");
		sql.append("                                         AND     G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                                         AND     G.GROUPPRODUCT_LOANTYPE = 'C')  ");
		sql.append("                            THEN 'บัตรเครดิต KTC' ");
		sql.append("                            ELSE ' ' ");
		sql.append("                        END), '/') WITHIN GROUP (ORDER BY APPPRODUCT_ID)  ");
		sql.append("        FROM   NFE_APP_PRODUCT ");
		sql.append("        WHERE  APPPRODUCT_ID > 0     ");
		sql.append("        AND    APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("        GROUP BY APPPRODUCT_APPNO) AS PRODUCT_NAME, ");
		// For Oracle under 10g
//		sql.append("       (WITH APP_PRODUCT AS  ");
//		sql.append("            (  ");
//		sql.append("                SELECT (CASE ");
//		sql.append("                            WHEN EXISTS (SELECT 'X' ");
//		sql.append("                                         FROM    NFE_MS_GROUPPRODUCT G, ");
//		sql.append("                                                 NFE_MS_PRODUCT P ");
//		sql.append("                                         WHERE   P.PRODUCT_ID = APPPRODUCT_PRODUCTID ");
//		sql.append("                                         AND     G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
//		sql.append("                                         AND     G.GROUPPRODUCT_LOANTYPE = 'F')  ");
//		sql.append("                            THEN 'สินเชื่อบุคคล KTC CASH' ");
//		sql.append("                            WHEN EXISTS (SELECT 'X' ");
//		sql.append("                                         FROM    NFE_MS_GROUPPRODUCT G, ");
//		sql.append("                                                 NFE_MS_PRODUCT P ");
//		sql.append("                                         WHERE   P.PRODUCT_ID = APPPRODUCT_PRODUCTID ");
//		sql.append("                                         AND     G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
//		sql.append("                                         AND     G.GROUPPRODUCT_LOANTYPE = 'R') ");
//		sql.append("                            THEN 'สินเชื่อพร้อมใช้ KTC CASH REVOLVE' ");
//		sql.append("                            WHEN EXISTS (SELECT 'X' ");
//		sql.append("                                         FROM    NFE_MS_GROUPPRODUCT G, ");
//		sql.append("                                                 NFE_MS_PRODUCT P ");
//		sql.append("                                         WHERE   P.PRODUCT_ID = APPPRODUCT_PRODUCTID ");
//		sql.append("                                         AND     G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
//		sql.append("                                         AND     G.GROUPPRODUCT_LOANTYPE = 'C')  ");
//		sql.append("                            THEN 'บัตรเครดิต KTC' ");
//		sql.append("                            ELSE ' ' ");
//		sql.append("                        END) AS PRODUCT_NAME, ");
//		sql.append("                       APPPRODUCT_APPNO AS APPNO,  ");
//		sql.append("                       APPPRODUCT_ID ");
//		sql.append("                FROM   NFE_APP_PRODUCT ");
//		sql.append("                WHERE  APPPRODUCT_ID > 0  ");
//		sql.append("                AND    APPPRODUCT_APPNO <> ' ' ");
//		sql.append("            )  ");
//		sql.append("            SELECT RTRIM(XMLAGG (XMLELEMENT(e, PRODUCT_NAME||'/') ORDER BY APPPRODUCT_ID).EXTRACT('//text()'), '/') AS PRODUCT_NAME   ");
//		sql.append("            FROM  APP_PRODUCT   ");
//		sql.append("            WHERE APPNO = T1.APP_NO ");
//		sql.append("            GROUP BY APPNO  ");
//		sql.append("       ) AS PRODUCT_NAME, ");
		sql.append("       (SELECT REASON_LETTERDESCRIPTION ");
		sql.append("        FROM   NFE_MS_REASON ");
		sql.append("        WHERE  REASON_CODE = T2.RESOLVE_REASONCODE) AS REJECT_DESC, ");
		sql.append("       (SELECT DISTINCT (CONFIGURATION_VALUE) ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'Letter01_ContactAndPhone') AS CONTACT ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T2 ");
		sql.append("           ON  T2.RESOLVE_ID > 0 ");
		sql.append("           AND T2.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T2.RESOLVE_STATUSCODE IN ('8D', '2R') ");
		sql.append("AND    T2.RESOLVE_CREDITTYPE = 'M' ");
		sql.append("AND    (CASE  ");
		sql.append("            WHEN T2.RESOLVE_STATUSCODE = '8D' ");
		sql.append("                 AND T2.RESOLVE_REASONCODE <> 'D99' ");
		sql.append("            THEN 1 ");
		sql.append("            WHEN T2.RESOLVE_STATUSCODE = '2R' ");
		sql.append("                 AND T2.RESOLVE_APPPRODUCTID IS NULL ");
		sql.append("            THEN 1 ");
		sql.append("            ELSE 0 ");
		sql.append("        END) = 1 ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_REASON ");
		sql.append("               WHERE  REASON_CODE = T2.RESOLVE_REASONCODE  ");
		sql.append("               AND    REASON_CHKNCB = 'N') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("                FROM   NFE_APP_STATUSTRACKING ");
		sql.append("                WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("                AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("                AND    STATUSTRACKING_STATUS IN ('8F', '2R') ");
		sql.append("                AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                   AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT S1.APPSUP_APPNO AS APPLICATION_NO,  ");
		sql.append("       S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAILNAME AS CUSTOMER_NAME,  ");
		sql.append("       NVL (S1.APPSUP_CURRENTADDR_LINE1, '') AS ADDRESSLINE1,  ");
		sql.append("       NVL (S1.APPSUP_CURRENTADDR_LINE2, '') AS ADDRESSLINE2,  ");
		sql.append("       NVL ((SELECT DISTRICT_TNAME  ");
		sql.append("              FROM NFE_MS_DISTRICT  ");
		sql.append("              WHERE DISTRICT_ID = S1.APPSUP_CURRENTADDR_DISTRICT) ");
		sql.append("           || ' '  ");
		sql.append("           || (SELECT SUBPROVINCE_TNAME  ");
		sql.append("               FROM NFE_MS_SUBPROVINCE  ");
		sql.append("               WHERE SUBPROVINCE_ID = S1.APPSUP_CURRENTADDR_SUBPROVINCE),  ");
		sql.append("           '') AS ADDRESSLINE3,  ");
		sql.append("       NVL ((SELECT PROVINCE_TNAME  ");
		sql.append("             FROM NFE_MS_PROVINCE  ");
		sql.append("             WHERE PROVINCE_ID = S1.APPSUP_CURRENTADDR_PROVINCE),  ");
		sql.append("             '') AS ADDRESS_LINE4,  ");
		sql.append("       NVL (S1.APPSUP_CURRENTADDR_ZIPCODE, '') AS ADDRESSLINE5,  ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY') FROM NFE_SETDATE) AS DATELETER, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
		sql.append("        AND    (CASE  ");
		sql.append("                    WHEN S2.RESOLVE_STATUSCODE = '8D' ");
		sql.append("                         AND STATUSTRACKING_STATUS = '8F' ");
		sql.append("                    THEN 1 ");
		sql.append("                    WHEN S2.RESOLVE_STATUSCODE = '2R' ");
		sql.append("                         AND STATUSTRACKING_STATUS = '2R' ");
		sql.append("                    THEN 1 ");
		sql.append("                    ELSE 0 ");
		sql.append("                END) = 1) AS DATES, ");
		sql.append("        (CASE  ");
		sql.append("            WHEN EXISTS  (SELECT 'X'  ");
		sql.append("                          FROM   NFE_MS_GROUPPRODUCT G, ");
		sql.append("                                 NFE_MS_PRODUCT P ");
		sql.append("                          WHERE  P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
		sql.append("                          AND    G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                          AND    G.GROUPPRODUCT_LOANTYPE = 'C')  ");
		sql.append("            THEN 'บัตรเครดิต KTC'  ");
		sql.append("            ELSE ' '  ");
		sql.append("         END) AS PRODUCT_NAME,  ");
		sql.append("       (SELECT REASON_LETTERDESCRIPTION ");
		sql.append("        FROM   NFE_MS_REASON ");
		sql.append("        WHERE  REASON_CODE = S2.RESOLVE_REASONCODE) AS REJECT_DESC, ");
		sql.append("       (SELECT DISTINCT (CONFIGURATION_VALUE)  ");
		sql.append("        FROM NFE_CONFIGURATION  ");
		sql.append("        WHERE CONFIGURATION_NAME = 'Letter01_ContactAndPhone') AS CONTACT ");
		sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE S2 ");
		sql.append("           ON  S2.RESOLVE_ID > 0 ");
		sql.append("           AND S2.RESOLVE_APPNO = S1.APPSUP_APPNO ");
		sql.append("           AND S2.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
		sql.append("WHERE  S1.APPSUP_APPNO NOT IN (SELECT DISTINCT(TMP1.APP_NO) ");
		sql.append("                               FROM   NFE_APPLICATION TMP1 ");
		sql.append("                                      LEFT JOIN NFE_APP_RESOLVE TMP2 ");
		sql.append("                                           ON  TMP2.RESOLVE_ID > 0 ");
		sql.append("                                           AND TMP2.RESOLVE_APPNO = TMP1.APP_NO ");
		sql.append("                                WHERE  TMP2.RESOLVE_STATUSCODE IN ('8D', '2R') ");
		sql.append("                                AND    (CASE  ");
		sql.append("                                            WHEN TMP2.RESOLVE_STATUSCODE = '8D' ");
		sql.append("                                                 AND TMP2.RESOLVE_REASONCODE <> 'D99' ");
		sql.append("                                            THEN 1 ");
		sql.append("                                            WHEN TMP2.RESOLVE_STATUSCODE = '2R' ");
		sql.append("                                                 AND TMP2.RESOLVE_APPPRODUCTID IS NULL ");
		sql.append("                                            THEN 1 ");
		sql.append("                                            ELSE 0 ");
		sql.append("                                        END) = 1 ");
		sql.append("                                AND    EXISTS (SELECT 'X' ");
		sql.append("                                               FROM   NFE_MS_REASON ");
		sql.append("                                               WHERE  REASON_CODE = TMP2.RESOLVE_REASONCODE  ");
		sql.append("                                               AND    REASON_CHKNCB = 'N') ");
		sql.append("                                AND    EXISTS (SELECT 'X' ");
		sql.append("                                                FROM   NFE_APP_STATUSTRACKING ");
		sql.append("                                                WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("                                                AND    STATUSTRACKING_APPNO = TMP1.APP_NO ");
		sql.append("                                                AND    STATUSTRACKING_STATUS IN ('8F', '2R') ");
		sql.append("                                                AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                                                   AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')))) ");
		sql.append("AND    S2.RESOLVE_STATUSCODE IN ('8D', '2R') ");
		sql.append("AND    S2.RESOLVE_CREDITTYPE = 'S' ");
		sql.append("AND    (CASE  ");
		sql.append("            WHEN S2.RESOLVE_STATUSCODE = '8D' ");
		sql.append("                 AND S2.RESOLVE_REASONCODE <> 'D99' ");
		sql.append("            THEN 1 ");
		sql.append("            WHEN S2.RESOLVE_STATUSCODE = '2R' ");
		sql.append("            THEN 1 ");
		sql.append("            ELSE 0 ");
		sql.append("        END) = 1 ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_REASON ");
		sql.append("               WHERE  REASON_CODE = S2.RESOLVE_REASONCODE  ");
		sql.append("               AND    REASON_CHKNCB = 'N') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
		sql.append("               AND    STATUSTRACKING_STATUS IN ('8F', '2R') ");
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
