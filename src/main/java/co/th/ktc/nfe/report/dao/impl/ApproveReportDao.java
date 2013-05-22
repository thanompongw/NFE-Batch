/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.report.dao.AbstractReportDao;


@Service(value="approveReportDao")
public class ApproveReportDao extends AbstractReportDao {

	/**
	 * 
	 */
	public ApproveReportDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public SqlRowSet query(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT A.* ");
		sql.append("             FROM (SELECT T1.APP_VSOURCE AS V_SOURCE, ");
		sql.append("                          (CASE ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                                        WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                                              AND GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
		sql.append("                              THEN ");
		sql.append("                                 'CC' ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                                        WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                                              AND GROUPPRODUCT_LOANTYPE = 'F') ");
		sql.append("                              THEN ");
		sql.append("                                 'PL' ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                                        WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                                              AND GROUPPRODUCT_LOANTYPE = 'N') ");
		sql.append("                              THEN ");
		sql.append("                                 'BD' ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                                        WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                                              AND GROUPPRODUCT_LOANTYPE = 'R') ");
		sql.append("                              THEN ");
		sql.append("                                 'RL' ");
		sql.append("                              ELSE ");
		sql.append("                                 ' ' ");
		sql.append("                           END) ");
		sql.append("                             AS GROUPLOAN_TYPE, ");
		sql.append("                          TO_CHAR (T5.STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("                             AS DATE_APPROVE, ");
		sql.append("                          T1.APP_NO AS APPLY_ID, ");
		sql.append("                          T3.APPROVE_CARDNO AS CARD_NO, ");
		sql.append("                          T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME AS CUSTOMER_NAME, ");
		sql.append("                          T3.APPROVE_CREDITLIMIT AS CREDIT_LINE, ");
		sql.append("                          T6.DTA_YEARLYINCOME AS INCOME, ");
		sql.append("                          T3.APPROVE_CREDITLIMIT / NULLIF (DTA_YEARLYINCOME / 12, 0) ");
		sql.append("                             AS FIVEX, ");
		sql.append("                          (CASE ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_CUSTOMERTYPE ");
		sql.append("                                        WHERE CUSTOMERTYPE_ID = T6.DTA_CUSTOMERTYPE ");
		sql.append("                                              AND CUSTOMERTYPE_CODE = 'I') ");
		sql.append("                              THEN ");
		sql.append("                                 (CASE ");
		sql.append("                                     WHEN ( (T6.DTA_YEARLYINCOME < 500000) ");
		sql.append("                                           AND (T3.APPROVE_CREDITLIMIT > ");
		sql.append("                                                   (T6.DTA_YEARLYINCOME * .10))) ");
		sql.append("                                     THEN ");
		sql.append("                                        'F' ");
		sql.append("                                     WHEN ( (T6.DTA_YEARLYINCOME > 500000) ");
		sql.append("                                           AND (T3.APPROVE_CREDITLIMIT > ");
		sql.append("                                                   (T6.DTA_YEARLYINCOME * .10))) ");
		sql.append("                                     THEN ");
		sql.append("                                        'F' ");
		sql.append("                                     WHEN ( (T6.DTA_YEARLYINCOME = 500000) ");
		sql.append("                                           AND (T3.APPROVE_CREDITLIMIT > ");
		sql.append("                                                   (T6.DTA_YEARLYINCOME * .10))) ");
		sql.append("                                     THEN ");
		sql.append("                                        'F' ");
		sql.append("                                     ELSE ");
		sql.append("                                        'T' ");
		sql.append("                                  END) ");
		sql.append("                              ELSE ");
		sql.append("                                 'X' ");
		sql.append("                           END) ");
		sql.append("                             AS TENPY, ");
		sql.append("                          'Prim' AS CREDIT_TYPE, ");
		sql.append("                          (SELECT CUSTOMERTYPE_CODE ");
		sql.append("                             FROM NFE_MS_CUSTOMERTYPE ");
		sql.append("                            WHERE CUSTOMERTYPE_ID = T6.DTA_CUSTOMERTYPE) ");
		sql.append("                             AS CUSTOMER_TYPE, ");
		sql.append("                          T1.APP_ANALYST, ");
		sql.append("                             (SELECT PRODUCT_DESCRIPTION ");
		sql.append("                                FROM NFE_MS_PRODUCT ");
		sql.append("                               WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) ");
		sql.append("                          || '-' ");
		sql.append("                          || (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("                                FROM NFE_MS_SUBPRODUCT ");
		sql.append("                               WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) ");
		sql.append("                             AS PRODUCT_SUBPRODUCT, ");
		sql.append("                          T1.APP_SOURCECODE AS SOURCE_CODE, ");
		sql.append("                          NVL(T3.APPROVE_EMBOSSNAME1,'') AS APPROVE_EMBOSSNAME1, ");
		sql.append("                          NVL(T3.APPROVE_EMBOSSNAME2,'') AS APPROVE_EMBOSSNAME2  ");
		sql.append("                     FROM NFE_APPLICATION T1 ");
		sql.append("                          LEFT JOIN NFE_APP_PRODUCT T2 ");
		sql.append("                             ON T2.APPPRODUCT_ID > 0 AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("                          JOIN NFE_APP_APPROVE T3 ");
		sql.append("                             ON     T3.APPROVE_APPNO = T1.APP_NO ");
		sql.append("                                AND T3.APPROVE_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                                AND T3.APPROVE_SUBPRODUCTID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("                                AND T3.APPROVE_CREDITTYPE = 'M' ");
		sql.append("                          LEFT JOIN NFE_APP_RESOLVE T4 ");
		sql.append("                             ON     T4.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("                                AND T4.RESOLVE_ID = T3.APPROVE_RESOLVEID ");
		sql.append("                                AND T4.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("                                AND T4.RESOLVE_STATUSCODE = '8A' ");
		sql.append("                          JOIN NFE_APP_STATUSTRACKING T5 ");
		sql.append("                             ON T5.STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("                                AND T5.STATUSTRACKING_STATUS = '8F' ");
		sql.append("                          JOIN NFE_APP_DATAANALYSIS T6 ");
		sql.append("                             ON T6.DTA_APPNO = T1.APP_NO ");
		sql.append("                    WHERE T1.APP_STATUSCODE = '8F' ");
		sql.append("                          AND EXISTS ");
		sql.append("                                 (SELECT 'X' ");
		sql.append("                                    FROM NFE_MS_GROUPPRODUCT G, NFE_MS_PRODUCT P ");
		sql.append("                                   WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                                         AND P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                                         AND (G.GROUPPRODUCT_LOANTYPE IN ('C', 'B') ");
		sql.append("                                              OR (G.GROUPPRODUCT_TYPE = 'B' ");
		sql.append("                                                  AND G.GROUPPRODUCT_LOANTYPE IN ");
		sql.append("                                                         ('C', 'B')))) ");
		sql.append("		  AND   (T5.STATUSTRACKING_ENDTIME  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss') ");
		sql.append("                   AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')) ");	
		sql.append("                   UNION ALL ");
		sql.append("                   SELECT S2.APP_VSOURCE AS V_SOURCE, ");
		sql.append("                          (CASE ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_GROUPPRODUCT G, NFE_MS_PRODUCT P ");
		sql.append("                                        WHERE G.GROUPPRODUCT_ID = ");
		sql.append("                                                 P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                                              AND P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
		sql.append("                                              AND G.GROUPPRODUCT_LOANTYPE = 'C') ");
		sql.append("                              THEN ");
		sql.append("                                 'CC' ");
		sql.append("                              ELSE ");
		sql.append("                                 ' ' ");
		sql.append("                           END) ");
		sql.append("                             AS GROUPLOAN_TYPE, ");
		sql.append("                          TO_CHAR (S5.STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("                             AS DATE_APPROVE, ");
		sql.append("                          S1.APPSUP_APPNO AS APPLY_ID, ");
		sql.append("                          S3.APPROVE_CARDNO AS CARD_NO, ");
		sql.append("                          S1.APPSUP_ENGFNAME || ' ' || S1.APPSUP_ENGLNAME ");
		sql.append("                             AS CUSTOMER_NAME, ");
		sql.append("                          S3.APPROVE_CREDITLIMIT AS CREDIT_LINE, ");
		sql.append("                          S1.APPSUP_YEARLYINCOME AS INCOME, ");
		sql.append("                          S3.APPROVE_CREDITLIMIT ");
		sql.append("                          / NULLIF (S1.APPSUP_YEARLYINCOME / 12, 0) ");
		sql.append("                             AS FIVEX, ");
		sql.append("                          (CASE ");
		sql.append("                              WHEN EXISTS ");
		sql.append("                                      (SELECT 'X' ");
		sql.append("                                         FROM NFE_MS_CUSTOMERTYPE ");
		sql.append("                                        WHERE CUSTOMERTYPE_ID = S1.APPSUP_CUSTOMERTYPE ");
		sql.append("                                              AND CUSTOMERTYPE_CODE = 'I') ");
		sql.append("                              THEN ");
		sql.append("                                 (CASE ");
		sql.append("                                     WHEN ( (S1.APPSUP_YEARLYINCOME < 500000) ");
		sql.append("                                           AND (S3.APPROVE_CREDITLIMIT > ");
		sql.append("                                                   (S1.APPSUP_YEARLYINCOME * .10))) ");
		sql.append("                                     THEN ");
		sql.append("                                        'F' ");
		sql.append("                                     WHEN ( (S1.APPSUP_YEARLYINCOME > 500000) ");
		sql.append("                                           AND (S3.APPROVE_CREDITLIMIT > ");
		sql.append("                                                   (S1.APPSUP_YEARLYINCOME * .10))) ");
		sql.append("                                     THEN ");
		sql.append("                                        'F' ");
		sql.append("                                     WHEN ( (S1.APPSUP_YEARLYINCOME = 500000) ");
		sql.append("                                           AND (S3.APPROVE_CREDITLIMIT > ");
		sql.append("                                                   (S1.APPSUP_YEARLYINCOME * .10))) ");
		sql.append("                                     THEN ");
		sql.append("                                        'F' ");
		sql.append("                                     ELSE ");
		sql.append("                                        'T' ");
		sql.append("                                  END) ");
		sql.append("                              ELSE ");
		sql.append("                                 'X' ");
		sql.append("                           END) ");
		sql.append("                             AS TENPY, ");
		sql.append("                          'Supp' AS CREDIT_TYPE, ");
		sql.append("                          (SELECT CUSTOMERTYPE_CODE ");
		sql.append("                             FROM NFE_MS_CUSTOMERTYPE ");
		sql.append("                            WHERE CUSTOMERTYPE_ID = S1.APPSUP_CUSTOMERTYPE) ");
		sql.append("                             AS CUSTOMER_TYPE, ");
		sql.append("                          S2.APP_ANALYST, ");
		sql.append("                             (SELECT PRODUCT_DESCRIPTION ");
		sql.append("                                FROM NFE_MS_PRODUCT ");
		sql.append("                               WHERE PRODUCT_ID = S1.APPSUP_PRODUCT)  ");
		sql.append("                          || '-' ");
		sql.append("                          || (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("                                FROM NFE_MS_SUBPRODUCT ");
		sql.append("                               WHERE SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT)  ");
		sql.append("                             AS PRODUCT_SUBPRODUCT, ");
		sql.append("                          S2.APP_SOURCECODE AS SOURCE_CODE, ");
		sql.append("                          NVL(S3.APPROVE_EMBOSSNAME1,'') AS APPROVE_EMBOSSNAME1, ");
		sql.append("                          NVL(S3.APPROVE_EMBOSSNAME2,'') AS APPROVE_EMBOSSNAME2  ");
		sql.append("                     FROM NFE_APP_SUPPLEMENTARY S1  ");
		sql.append("                          JOIN NFE_APPLICATION S2  ");
		sql.append("                             ON  S1.APPSUP_ID      > 0 ");
		sql.append("                             AND S1.APPSUP_APPNO   = S2.APP_NO ");
		sql.append("                             AND S2.APP_STATUSCODE = '8F' ");
		sql.append("                          JOIN NFE_APP_APPROVE S3 ");
		sql.append("                             ON     S3.APPROVE_APPNO = S1.APPSUP_APPNO ");
		sql.append("                                AND S3.APPROVE_PRODUCTID = S1.APPSUP_PRODUCT ");
		sql.append("                                AND S3.APPROVE_SUBPRODUCTID = S1.APPSUP_SUBPRODUCT ");
		sql.append("                                AND S3.APPROVE_CREDITTYPE = 'S' ");
		sql.append("                          JOIN NFE_APP_RESOLVE S4 ");
		sql.append("                             ON     S4.RESOLVE_APPNO = S1.APPSUP_APPNO ");
		sql.append("                                AND S4.RESOLVE_ID = S3.APPROVE_RESOLVEID ");
		sql.append("                                AND S4.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
		sql.append("                                AND S4.RESOLVE_STATUSCODE = '8A' ");
		sql.append("                          JOIN NFE_APP_STATUSTRACKING S5 ");
		sql.append("                             ON S5.STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
		sql.append("                                AND S5.STATUSTRACKING_STATUS = '8F' ");
		sql.append("                     WHERE EXISTS (SELECT 'X' ");
		sql.append("                                  FROM NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                   NFE_MS_PRODUCT P  ");
		sql.append("                                  WHERE G.GROUPPRODUCT_ID       = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                                  AND   P.PRODUCT_ID            = S1.APPSUP_PRODUCT ");
		sql.append("                                  AND   G.GROUPPRODUCT_LOANTYPE = 'C' ");
		sql.append("                                  ) ");
		sql.append("		  AND   (S5.STATUSTRACKING_ENDTIME  BETWEEN TO_TIMESTAMP (?, 'DD/MM/YYYY HH24:mi:ss') ");
		sql.append("                    AND TO_TIMESTAMP (?, 'DD/MM/YYYY HH24:mi:ss')) ");	
		sql.append("                  ) A ");
		sql.append("         ORDER BY A.V_SOURCE, ");
		sql.append("                  A.CARD_NO, ");
		sql.append("                  A.APP_ANALYST, ");
		sql.append("                  A.APPLY_ID ");
		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(),parameter);
		
		return sqlRowSet;
	}

	public SqlRowSet query(Object[] parameter,String sheetName) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
        sql.append("    T1.APP_VSOURCE AS V_SOURCE, ");
        sql.append("    (CASE  ");
        sql.append("        WHEN EXISTS (SELECT 'X' ");
        sql.append("                     FROM NFE_MS_GROUPPRODUCT ");
        sql.append("                     WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
        sql.append("                     AND   GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
        sql.append("        THEN 'CC'  ");
        sql.append("        WHEN EXISTS (SELECT 'X' ");
        sql.append("                     FROM NFE_MS_GROUPPRODUCT ");
        sql.append("                     WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
        sql.append("                     AND   GROUPPRODUCT_LOANTYPE = 'F')  ");
        sql.append("        THEN 'PL' ");
        sql.append("        WHEN EXISTS (SELECT 'X' ");
        sql.append("                     FROM NFE_MS_GROUPPRODUCT ");
        sql.append("                     WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
        sql.append("                     AND   GROUPPRODUCT_LOANTYPE = 'N')  ");
        sql.append("        THEN 'BD' ");
        sql.append("        WHEN EXISTS (SELECT 'X' ");
        sql.append("                     FROM NFE_MS_GROUPPRODUCT ");
        sql.append("                     WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
        sql.append("                     AND   GROUPPRODUCT_LOANTYPE = 'R')  ");
        sql.append("        THEN 'RL' ");
        sql.append("        ELSE ' '  ");
        sql.append("    END) AS GROUPLOAN_TYPE, ");
        sql.append("    TO_CHAR (T5.STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') AS DATE_APPROVE, ");
        sql.append("    T1.APP_NO AS APPLY_ID, ");
        sql.append("    T3.APPROVE_CARDNO AS CARD_NO, ");
        sql.append("    T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME AS CUSTOMER_NAME, ");
        sql.append("    T3.APPROVE_CREDITLIMIT AS CREDIT_LINE, ");
        if (sheetName != null && sheetName.equals("F")) {
             sql.append("    T3.APPROVE_CREDITLIMIT as MONEY_TRANSFER, ");
        }else if(sheetName != null && sheetName.equals("R")) {
             sql.append("    T3.APPROVE_CASHADVANCE as MONEY_TRANSFER, ");
        }
        sql.append("    T6.DTA_YEARLYINCOME AS INCOME, ");
        sql.append("    T3.APPROVE_CREDITLIMIT / NULLIF (DTA_YEARLYINCOME / 12, 0) AS FIVEX, ");
        
        if (sheetName != null && sheetName.equals("N")) {
            sql.append("    (CASE ");
            sql.append("     WHEN EXISTS (SELECT 'X' ");
            sql.append("                  FROM NFE_MS_CUSTOMERTYPE ");
            sql.append("                  WHERE CUSTOMERTYPE_ID   = T6.DTA_CUSTOMERTYPE ");
            sql.append("                  AND   CUSTOMERTYPE_CODE = 'I') ");
            sql.append("     THEN ");
            sql.append("        (CASE ");
            sql.append("            WHEN (    (T6.DTA_YEARLYINCOME < 500000) ");
            sql.append("                  AND (T3.APPROVE_CREDITLIMIT > (T6.DTA_YEARLYINCOME * .10))) ");
            sql.append("            THEN 'F' ");
            sql.append("            WHEN (    (T6.DTA_YEARLYINCOME > 500000) ");
            sql.append("                  AND (T3.APPROVE_CREDITLIMIT > (T6.DTA_YEARLYINCOME * .10))) ");
            sql.append("            THEN 'F' ");
            sql.append("            WHEN (    (T6.DTA_YEARLYINCOME = 500000) ");
            sql.append("                  AND (T3.APPROVE_CREDITLIMIT > (T6.DTA_YEARLYINCOME * .10))) ");
            sql.append("            THEN 'F' ");
            sql.append("            ELSE 'T' ");
            sql.append("         END) ");
            sql.append("     ELSE 'X' ");
            sql.append("    END) AS TENPY, ");
        }
        sql.append("    (T3.APPROVE_PERCENTINTEREST + T3.APPROVE_COMMINTEREST) AS INTERESTRATE, ");
        sql.append("    'Prim' AS CREDIT_TYPE, ");
        sql.append("    (SELECT CUSTOMERTYPE_CODE ");
        sql.append("     FROM NFE_MS_CUSTOMERTYPE ");
        sql.append("     WHERE CUSTOMERTYPE_ID = T6.DTA_CUSTOMERTYPE) AS CUSTOMER_TYPE, ");
        if (sheetName != null && sheetName.equals("F")) {
            sql.append("    NVL ((SELECT BANK_CODE \n");
            sql.append("           FROM NFE_MS_BANK \n");
            sql.append("           WHERE BANK_ID = T8.TRANSFERLOAN_BANK), \n");
            sql.append("          '-') AS BANK_CODE, \n");
            sql.append("    NVL (T8.TRANSFERLOAN_ACCNO, '-') AS ACCOUNT_NO, \n");

        }else if(sheetName != null && sheetName.equals("R")) {
            sql.append("    NVL ((SELECT BANK_CODE \n");
            sql.append("           FROM NFE_MS_BANK \n");
            sql.append("           WHERE BANK_ID = T7.TRANSFERDEBT_BANK), \n");
            sql.append("          '-') AS BANK_CODE, \n");
            sql.append("    NVL (T7.TRANSFERDEBT_ACCNO, '-') AS ACCOUNT_NO, \n");

        }
        sql.append("    T1.APP_ANALYST, ");
        sql.append("    (SELECT PRODUCT_DESCRIPTION ");
        sql.append("    FROM NFE_MS_PRODUCT ");
        sql.append("    WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID)  ");
        sql.append("    || '-' || ");
        sql.append("    (SELECT SUBPRODUCT_DESCRIPTION ");
        sql.append("    FROM NFE_MS_SUBPRODUCT ");
        sql.append("    WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) ");
        sql.append("    AS PRODUCT_SUBPRODUCT, ");
        sql.append("    T1.APP_SOURCECODE AS SOURCE_CODE ");

        if (sheetName != null && sheetName.equals("R")) {
            sql.append("    ,T3.APPROVE_EMBOSSNAME1 ");
        }
        if (sheetName != null && sheetName.equals("N")) {
            sql.append("    ,T3.APPROVE_EMBOSSNAME1 ");
            sql.append("    ,T3.APPROVE_EMBOSSNAME2 ");
        }
        sql.append("FROM NFE_APPLICATION T1 ");
        sql.append("LEFT JOIN NFE_APP_PRODUCT T2 ");
        sql.append("    ON  T2.APPPRODUCT_ID    > 0 ");
        sql.append("    AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
        sql.append("JOIN NFE_APP_APPROVE T3 ");
        sql.append("    ON T3.APPROVE_APPNO         = T1.APP_NO ");
        sql.append("    AND T3.APPROVE_PRODUCTID    = T2.APPPRODUCT_PRODUCTID ");
        sql.append("    AND T3.APPROVE_SUBPRODUCTID = T2.APPPRODUCT_SUBPRODUCTID ");
        sql.append("    AND T3.APPROVE_CREDITTYPE   = 'M' ");
        sql.append("JOIN NFE_APP_RESOLVE T4 ");
        sql.append("    ON  T4.RESOLVE_APPNO        = T1.APP_NO ");
        sql.append("    AND T4.RESOLVE_ID           = T3.APPROVE_RESOLVEID ");
        sql.append("    AND T4.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
        sql.append("    AND T4.RESOLVE_STATUSCODE   = '8A' ");
        sql.append("JOIN NFE_APP_STATUSTRACKING T5 ");
        sql.append("    ON  T5.STATUSTRACKING_APPNO  = T1.APP_NO ");
        sql.append("    AND T5.STATUSTRACKING_STATUS = '8F' ");
        sql.append("LEFT JOIN NFE_APP_DATAANALYSIS T6 ");
        sql.append("    ON T6.DTA_APPNO  = T1.APP_NO ");
        if(sheetName != null && sheetName.equals("R")) {
             sql.append(" JOIN  NFE_APP_TRANSFERDEBT T7  ");
             sql.append("    ON T1.APP_NO = T7.TRANSFERDEBT_APPNO ");
        }else if(sheetName != null && sheetName.equals("F")) {
             sql.append(" JOIN  NFE_APP_TRANSFERLOAN T8 ");
             sql.append("    ON T1.APP_NO = T8.TRANSFERLOAN_APPNO ");
        }
        sql.append("WHERE T1.APP_STATUSCODE = '8F' ");
        if(sheetName != null && (sheetName.equals("R") || sheetName.equals("F"))) {
            sql.append(" AND EXISTS (SELECT 'X'  ");
            sql.append("             FROM NFE_MS_GROUPPRODUCT G, ");
            sql.append("                  NFE_MS_PRODUCT P ");
            sql.append("             WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
            sql.append("             AND   P.PRODUCT_ID      = T2.APPPRODUCT_PRODUCTID  ");
            sql.append("             AND  (G.GROUPPRODUCT_LOANTYPE = ? ");
            sql.append("                   OR (G.GROUPPRODUCT_TYPE = 'B' ");
            sql.append("                   AND G.GROUPPRODUCT_LOANTYPE = ?)) ");
            sql.append("             ) ");
        } else {
            sql.append("AND EXISTS (SELECT 'X'  ");
            sql.append("            FROM NFE_MS_GROUPPRODUCT  ");
            sql.append("            WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
            sql.append("            AND   (GROUPPRODUCT_LOANTYPE = ? ");
            sql.append("            OR (GROUPPRODUCT_TYPE = 'B'  ");
            sql.append("            AND GROUPPRODUCT_LOANTYPE = ? )) ");
            sql.append("            )  ");
        }
		sql.append("		  AND   (T5.STATUSTRACKING_ENDTIME  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss') ");
		sql.append("                   AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')) ");	
        sql.append(" ORDER BY T1.APP_VSOURCE, ");
        sql.append("          T3.APPROVE_CARDNO, ");
        sql.append("          T1.APP_ANALYST, ");
        sql.append("          T1.APP_NO ");
		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

}
