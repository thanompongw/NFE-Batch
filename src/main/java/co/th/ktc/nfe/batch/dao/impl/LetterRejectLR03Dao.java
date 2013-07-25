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
@Repository(value = "letterRejectLR03Dao")
public class LetterRejectLR03Dao extends AbstractBatchDao {

	/**
	 * 
	 */
	public LetterRejectLR03Dao() {
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
		
		sql.append("SELECT (SELECT (CASE  ");
		sql.append("                    WHEN DOCUMENT_ID = 11  ");
		sql.append("                    THEN '' ");
		sql.append("                    ELSE DOCUMENT_NAME ");
		sql.append("                END) ");
		sql.append("                FROM NFE_MS_DOCUMENT ");
		sql.append("                WHERE DOCUMENT_ID = T1.APPDOCUMENT_DOCID) AS DOCUMENT_NAME, ");
		sql.append("       (CASE ");
		sql.append("            WHEN T1.APPDOCUMENT_STATUS = 'Y' THEN 'N' ");
		sql.append("            WHEN T1.APPDOCUMENT_STATUS = 'N' THEN 'Y' ");
		sql.append("            ELSE '-' ");
		sql.append("        END) AS FLAG_MAINCARD, ");
		sql.append("       NVL((SELECT (CASE ");
		sql.append("                        WHEN SUPDOC_STATUS = 'Y' THEN 'N' ");
		sql.append("                        WHEN SUPDOC_STATUS = 'N' THEN 'Y' ");
		sql.append("                        ELSE '-' ");
		sql.append("                    END) ");
		sql.append("            FROM NFE_APP_SUPDOCREMARK ");
		sql.append("                 LEFT JOIN NFE_APP_SUPDOCUMENT ");
		sql.append("                     ON SUPDOCREMARK_APPNO = SUPDOC_APPNO ");
		sql.append("            WHERE SUPDOCREMARK_ALLSTATUS = 'N'  ");
		sql.append("            AND   SUPDOCREMARK_APPNO = T1.APPDOCUMENT_APPNO ");
		sql.append("            AND   SUPDOC_DOCID > 0), '-') AS FLAG_SUPPLECARD, ");
		sql.append("      (CASE ");
		sql.append("            WHEN EXISTS(SELECT 'x' ");
		sql.append("                        FROM NFE_APP_PRODUCT, ");
		sql.append("                             NFE_MS_PRODUCT, ");
		sql.append("                             NFE_MS_GROUPPRODUCT  ");
		sql.append("                        WHERE PRODUCT_ID = APPPRODUCT_PRODUCTID ");
		sql.append("                        AND   PRODUCT_GROUPPRODUCTID = GROUPPRODUCT_ID ");
		sql.append("                        AND   GROUPPRODUCT_LOANTYPE = 'C'  ");
		sql.append("                        AND   APPPRODUCT_ID > 0  ");
		sql.append("                        AND   APPPRODUCT_APPNO = T1.APPDOCUMENT_APPNO) ");
		sql.append("                  OR T2.APPDOCREMARK_ALLSTATUS IS NULL ");
		sql.append("            THEN '-' ");
		sql.append("            ELSE  (CASE ");
		sql.append("                       WHEN T1.APPDOCUMENT_STATUS = 'Y' THEN 'N' ");
		sql.append("                       WHEN T1.APPDOCUMENT_STATUS = 'N' THEN 'Y' ");
		sql.append("                       ELSE '-' ");
		sql.append("                   END) ");
		sql.append("        END) AS FLAG_LOAN ");
		sql.append("FROM  NFE_APP_DOCUMENT T1 ");
		sql.append("      LEFT JOIN NFE_APP_DOCREMARK T2 ");
		sql.append("           ON T2.APPDOCREMARK_APPNO = T1.APPDOCUMENT_APPNO ");
		sql.append("WHERE T1.APPDOCUMENT_APPNO = ? ");
		sql.append("AND   T1.APPDOCUMENT_DOCID > 0 ");
		sql.append("AND   T1.APPDOCUMENT_STATUS IS NOT NULL ");
		sql.append("ORDER BY T1.APPDOCUMENT_DOCID ");

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
		
		sql.append("SELECT T1.APP_NO AS APPLICATION_NO, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS CUSTOMER_NAME, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                THEN (SELECT PIF_CURRENTADDR_LINE1 ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT APPOCCUPATION_WORKPLACE ");
		sql.append("                      FROM   NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("             END), '') AS ADDRESSLINE1, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                THEN (SELECT PIF_CURRENTADDR_LINE2 ");
		sql.append("                      FROM   NFE_APP_PIF ");
		sql.append("                      WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT APPOCCUPATION_ADDRLINE1 || ' ' || APPOCCUPATION_ADDRLINE2 ");
		sql.append("                      FROM  NFE_APP_OCCUPATION  ");
		sql.append("                      WHERE APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESSLINE2, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 1)  ");
		sql.append("                THEN (  (SELECT D.DISTRICT_TNAME ");
		sql.append("                         FROM   NFE_MS_DISTRICT D, ");
		sql.append("                                NFE_APP_PIF P ");
		sql.append("                         WHERE  D.DISTRICT_ID = P.PIF_CURRENTADDR_DISTRICT ");
		sql.append("                         AND    P.PIF_APPNO = T1.APP_NO) ");
		sql.append("                     || '  ' ");
		sql.append("                     || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                         FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                NFE_APP_PIF P ");
		sql.append("                         WHERE  S.SUBPROVINCE_ID = P.PIF_CURRENTADDR_AMPHUR ");
		sql.append("                         AND    P.PIF_APPNO = T1.APP_NO)) ");
		sql.append("                ELSE ((SELECT D.DISTRICT_TNAME ");
		sql.append("                       FROM   NFE_MS_DISTRICT D, ");
		sql.append("                              NFE_APP_OCCUPATION O ");
		sql.append("                       WHERE  DISTRICT_ID = O.APPOCCUPATION_DISTRICT ");
		sql.append("                       AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                     || '  ' ");
		sql.append("                     || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                         FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                NFE_APP_OCCUPATION O ");
		sql.append("                         WHERE  S.SUBPROVINCE_ID = O.APPOCCUPATION_AMPHUR ");
		sql.append("                         AND    O.APPOCCUPATION_APPNO = T1.APP_NO)) ");
		sql.append("            END), '') AS ADDRESSLINE3, ");
		sql.append("       NVL((CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM  NFE_APP_OCCUPATION  ");
		sql.append("                             WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                             AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                THEN (SELECT P.PROVINCE_TNAME ");
		sql.append("                      FROM   NFE_MS_PROVINCE P, ");
		sql.append("                             NFE_APP_PIF AP ");
		sql.append("                      WHERE  P.PROVINCE_ID = AP.PIF_CURRENTADDR_PROVINCE ");
		sql.append("                      AND    AP.PIF_APPNO = T1.APP_NO) ");
		sql.append("                ELSE (SELECT P.PROVINCE_TNAME ");
		sql.append("                      FROM   NFE_MS_PROVINCE P, ");
		sql.append("                             NFE_APP_OCCUPATION O ");
		sql.append("                      WHERE  P.PROVINCE_ID = O.APPOCCUPATION_PROVINCE ");
		sql.append("                      AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("            END), '') AS ADDRESSLINE4, ");
		sql.append("       NVL((CASE ");
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
		sql.append("            END), '') AS ADDRESSLINE5, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY') FROM NFE_SETDATE) AS DATES, ");
		//for oracle upper 10g
//		sql.append("       (SELECT LISTAGG((CASE ");
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
//		sql.append("                        END), '/') WITHIN GROUP (ORDER BY APPPRODUCT_ID)  ");
//		sql.append("        FROM   NFE_APP_PRODUCT ");
//		sql.append("        WHERE  APPPRODUCT_ID > 0     ");
//		sql.append("        AND    APPPRODUCT_APPNO = T1.APP_NO ");
//		sql.append("        GROUP BY APPPRODUCT_APPNO) AS PRODUCT_NAME, ");
		
		//for oracle under 10g
		sql.append("       (WITH APP_PRODUCT AS  ");
		sql.append("            (  ");
		sql.append("                SELECT (CASE ");
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
		sql.append("                        END) AS PRODUCT_NAME, ");
		sql.append("                       APPPRODUCT_APPNO AS APPNO,  ");
		sql.append("                       APPPRODUCT_ID ");
		sql.append("                FROM   NFE_APP_PRODUCT ");
		sql.append("                WHERE  APPPRODUCT_ID > 0  ");
		sql.append("                AND    APPPRODUCT_APPNO <> ' ' ");
		sql.append("            )  ");
		sql.append("            SELECT RTRIM(XMLAGG (XMLELEMENT(e, PRODUCT_NAME||'/') ORDER BY APPPRODUCT_ID).EXTRACT('//text()'), '/') AS PRODUCT_NAME   ");
		sql.append("            FROM  APP_PRODUCT   ");
		sql.append("            WHERE APPNO = T1.APP_NO ");
		sql.append("            GROUP BY APPNO  ");
		sql.append("       ) AS PRODUCT_NAME, ");
		sql.append("       (SELECT CON.CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION CON ");
		sql.append("        WHERE CONFIGURATION_NAME = 'Letter03_ContactNumber') AS PHONE_NO, ");
		sql.append("       (SELECT CON.CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION CON ");
		sql.append("        WHERE CONFIGURATION_NAME = 'Letter03_Responseday') AS RESPONSE_DAYS ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T1.APP_STATUSCODE = '2I' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '2I' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
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
		sql.append("       (CASE  ");
		sql.append("            WHEN EXISTS  (SELECT 'X'  ");
		sql.append("                          FROM   NFE_MS_GROUPPRODUCT G, ");
		sql.append("                                 NFE_MS_PRODUCT P ");
		sql.append("                          WHERE  P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
		sql.append("                          AND    G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                          AND    G.GROUPPRODUCT_LOANTYPE = 'C')  ");
		sql.append("            THEN 'บัตรเครดิต KTC'  ");
		sql.append("            ELSE ' '  ");
		sql.append("        END) AS PRODUCT_NAME,  ");
		sql.append("       (SELECT CON.CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION CON ");
		sql.append("        WHERE CONFIGURATION_NAME = 'Letter03_ContactNumber') AS PHONE_NO, ");
		sql.append("       (SELECT CON.CONFIGURATION_VALUE ");
		sql.append("        FROM NFE_CONFIGURATION CON ");
		sql.append("        WHERE CONFIGURATION_NAME = 'Letter03_Responseday') AS RESPONSE_DAYS ");
		sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
		sql.append("WHERE  S1.APPSUP_APPNO NOT IN (SELECT DISTINCT(TMP1.APP_NO) ");
		sql.append("                               FROM   NFE_APPLICATION TMP1 ");
		sql.append("                               WHERE  TMP1.APP_NO <> ' ' ");
		sql.append("                               AND    TMP1.APP_STATUSCODE = '2I' ");
		sql.append("                               AND    EXISTS (SELECT 'X' ");
		sql.append("                                              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("                                              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("                                              AND    STATUSTRACKING_APPNO = TMP1.APP_NO ");
		sql.append("                                              AND    STATUSTRACKING_STATUS = '2I' ");
		sql.append("                                              AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                                                 AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '2I' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
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
