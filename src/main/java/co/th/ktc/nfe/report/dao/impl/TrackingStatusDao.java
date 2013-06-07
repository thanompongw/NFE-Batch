/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.report.dao.AbstractReportDao;


@Repository(value = "trackingStatusDao")
public class TrackingStatusDao extends AbstractReportDao {

	/**
	 * 
	 */
	public TrackingStatusDao() {
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
		
		sql.append("WITH W_CREDIT_INFO AS ( ");
		if (parameter.length > 4) {
			sql.append("SELECT * ");
			sql.append("FROM  ");
			sql.append("( ");
		}
		sql.append("SELECT TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS DATE_REC, ");
		sql.append("       (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
		sql.append("            THEN 'CC' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("            THEN 'PL' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND GROUPPRODUCT_LOANTYPE = 'N')  ");
		sql.append("            THEN 'BD' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("            THEN 'RL' ");
		sql.append("            ELSE ' ' ");
		sql.append("        END) AS GROUPLOAN_TYPE, ");
		sql.append("       T1.APP_VSOURCE, ");
		sql.append("       T1.APP_NO AS APPLICATION_ID, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAI_NAME, ");
		sql.append("       T1.APP_CITIZENID AS CITIZENID, ");
		sql.append("       'M' AS CREDIT_TYPE, ");
		sql.append("       T1.APP_CREATEBY AS USER_LOGIN_VAGENT, ");
		sql.append("       T1.APP_CHKNCB, ");
		sql.append("       T1.APP_BARCODE2, ");
		sql.append("       T1.APP_SOURCECODE, ");
		sql.append("       T1.APP_AGENT, ");
		sql.append("       T1.APP_BRANCH, ");
		sql.append("       (SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("        FROM    NFE_MS_APPSTATUS ");
		sql.append("        WHERE    APPSTATUS_CODE = T1.APP_STATUSCODE) AS QUEUE, ");
		sql.append("       NVL((SELECT REASON_CODE || ':' || REASON_DESCRIPTION ");
		sql.append("            FROM   NFE_MS_REASON ");
		sql.append("            WHERE  REASON_CODE = T3.RESOLVE_REASONCODE), ");
		sql.append("           '-') AS REASON, ");
//		sql.append("       (WITH MEMO AS (SELECT MEMO_DETAIL AS DETAIL, ");
//		sql.append("                             MEMO_APPNO AS APPNO ");
//		sql.append("                      FROM   NFE_APP_MEMO ");
//		sql.append("                      WHERE  MEMO_ID > 0 ");
//		sql.append("                      AND    MEMO_APPNO <> ' ' ");
//		sql.append("                      AND    MEMO_TYPE = 'M') ");
//		sql.append("        SELECT RTRIM(XMLAGG(XMLELEMENT(E, DETAIL || '/')  ");
//		sql.append("                            ORDER BY APPNO).EXTRACT('//text()'), '/') AS MEMO_DETAIL ");
//		sql.append("        FROM   MEMO ");
//		sql.append("        WHERE  APPNO = T1.APP_NO ");
//		sql.append("        GROUP BY APPNO) AS MEMO, ");
		sql.append("       ''  AS MEMO, ");
		sql.append("       NVL((SELECT (CASE  ");
		sql.append("                        WHEN MIN(APPPRODUCT_ID) = T2.APPPRODUCT_ID ");
		sql.append("                        THEN 'Y' ");
		sql.append("                        ELSE 'N' ");
		sql.append("                    END) ");
		sql.append("           FROM     NFE_APP_PRODUCT ");
		sql.append("           WHERE    APPPRODUCT_ID > 0  ");
		sql.append("           AND      APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("           GROUP BY APPPRODUCT_APPNO), 'Y') AS PAY_OA, ");
		sql.append("      T2.APPPRODUCT_ID ");
		sql.append("FROM  NFE_APPLICATION T1 ");
		sql.append("      LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("      LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0  ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND (T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("                OR T3.RESOLVE_APPPRODUCTID IS NULL) ");
		sql.append("WHERE T1.APP_NO <> ' ' ");
		sql.append("AND   T1.APP_STATUSCODE <> '2C' ");
		if (parameter != null && parameter.length > 4) {
			sql.append("AND   EXISTS (SELECT 'X' ");
			sql.append("              FROM    NFE_MS_GROUPPRODUCT ");
			sql.append("              WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
			sql.append("              AND     ((GROUPPRODUCT_TYPE = 'M' ");
			sql.append("                        AND GROUPPRODUCT_LOANTYPE IN (?, ?)) ");
			sql.append("                       OR (GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                           AND GROUPPRODUCT_LOANTYPE IN (?, ?)))) ");
		} else {
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_MS_GROUPPRODUCT ");
			sql.append("               WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
			sql.append("               AND     ((GROUPPRODUCT_TYPE = 'M' ");
			sql.append("                         AND GROUPPRODUCT_LOANTYPE = ?) ");
			sql.append("                        OR (GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                            AND GROUPPRODUCT_LOANTYPE = ?))) ");
		}
		sql.append("AND   (T1.APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                           AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");
		if (parameter != null && parameter.length > 4) {
			sql.append("UNION ALL ");
			sql.append("SELECT   TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') AS DATE_REC, ");
			sql.append("         'CC' AS GROUPLOAN_TYPE, ");
			sql.append("         S2.APP_VSOURCE, ");
			sql.append("         S1.APPSUP_APPNO AS APPLICATION_ID, ");
			sql.append("         S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAILNAME AS THAI_NAME, ");
			sql.append("         S1.APPSUP_CITIZENID AS CITIZENID, ");
			sql.append("         'S' AS CREDIT_TYPE, ");
			sql.append("         S2.APP_CREATEBY AS USER_LOGIN_VAGENT, ");
			sql.append("         S2.APP_CHKNCB, ");
			sql.append("         S2.APP_BARCODE2, ");
			sql.append("         S2.APP_SOURCECODE, ");
			sql.append("         S2.APP_AGENT, ");
			sql.append("         S2.APP_BRANCH, ");
			sql.append("         (SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
			sql.append("          FROM    NFE_MS_APPSTATUS ");
			sql.append("          WHERE    APPSTATUS_CODE = S2.APP_STATUSCODE) AS QUEUE, ");
			sql.append("         NVL((SELECT REASON_CODE || ':' || REASON_DESCRIPTION ");
			sql.append("              FROM   NFE_MS_REASON ");
			sql.append("              WHERE  REASON_CODE = S3.RESOLVE_REASONCODE), ");
			sql.append("             '-') AS REASON, ");
	//		sql.append("         (WITH MEMO AS (SELECT MEMO_DETAIL AS DETAIL, ");
	//		sql.append("                               MEMO_APPNO AS APPNO ");
	//		sql.append("                        FROM   NFE_APP_MEMO ");
	//		sql.append("                        WHERE  MEMO_ID > 0 ");
	//		sql.append("                        AND    MEMO_APPNO <> ' ' ");
	//		sql.append("                        AND    MEMO_TYPE = 'M') ");
	//		sql.append("          SELECT RTRIM(XMLAGG(XMLELEMENT(E, DETAIL || '/')  ");
	//		sql.append("                              ORDER BY APPNO).EXTRACT('//text()'), '/') AS MEMO_DETAIL ");
	//		sql.append("          FROM   MEMO ");
	//		sql.append("          WHERE  APPNO = S1.APPSUP_APPNO ");
	//		sql.append("          GROUP BY APPNO) AS MEMO, ");
			sql.append("          ''  AS MEMO, ");
			sql.append("          NVL((SELECT  (CASE  ");
			sql.append("                            WHEN MIN(APPSUP_ID) = S1.APPSUP_ID ");
			sql.append("                            THEN 'Y' ");
			sql.append("                            ELSE 'N' ");
			sql.append("                         END) ");
			sql.append("               FROM     NFE_APP_SUPPLEMENTARY ");
			sql.append("               WHERE    APPSUP_ID > 0  ");
			sql.append("               AND      APPSUP_APPNO = S1.APPSUP_APPNO ");
			sql.append("               GROUP BY APPSUP_APPNO), 'Y') AS PAY_OA, ");
			sql.append("         S1.APPSUP_ID AS APPPRODUCT_ID  ");
			sql.append("FROM  NFE_APP_SUPPLEMENTARY S1 ");
			sql.append("      LEFT JOIN NFE_APPLICATION S2 ");
			sql.append("            ON  S1.APPSUP_ID > 0  ");
			sql.append("            AND S1.APPSUP_APPNO = S2.APP_NO ");
			sql.append("      LEFT JOIN NFE_APP_RESOLVE S3 ");
			sql.append("           ON  S3.RESOLVE_ID > 0  ");
			sql.append("           AND S3.RESOLVE_APPNO = S1.APPSUP_APPNO ");
			sql.append("           AND (S3.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
			sql.append("                OR S3.RESOLVE_APPPRODUCTID IS NULL) ");
			sql.append("WHERE S1.APPSUP_APPNO <> ' ' ");
			sql.append("AND   S2.APP_STATUSCODE <> '2C' ");
			sql.append("AND   EXISTS (SELECT 'X' ");
			sql.append("              FROM    NFE_MS_GROUPPRODUCT ");
			sql.append("              WHERE   GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("              AND     GROUPPRODUCT_TYPE = 'S'  ");
			sql.append("              AND     GROUPPRODUCT_LOANTYPE = 'C') ");
			sql.append("AND   (S2.APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                           AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");
			sql.append(") ");
		}
		sql.append(") ");
		sql.append("SELECT *  ");
		sql.append("FROM (SELECT * FROM W_CREDIT_INFO  ");
		sql.append("      UNION ALL  ");
		sql.append("      SELECT NULL AS DATE_REC,  ");
		sql.append("             NULL AS GROUPLOAN_TYPE,  ");
		sql.append("             C.APP_VSOURCE,  ");
		sql.append("             NULL AS APPLICATION_ID,  ");
		sql.append("             NULL AS THAI_NAME,  ");
		sql.append("             NULL AS CITIZENID,  ");
		sql.append("             NULL AS CREDIT_TYPE,  ");
		sql.append("             NULL AS USER_LOGIN_VAGENT,  ");
		sql.append("             NULL AS APP_CHKNCB,  ");
		sql.append("             NULL AS APP_BARCODE2,  ");
		sql.append("             NULL AS APP_SOURCECODE,  ");
		sql.append("             NULL AS APP_AGENT,  ");
		sql.append("             NULL AS APP_BRANCH,  ");
		sql.append("             NULL AS QUEUE,  ");
		sql.append("             NULL AS REASON,  ");
		sql.append("             'TOTAL' AS MEMO,  ");
		sql.append("             TO_CHAR (SUM (CASE  ");
		sql.append("                               WHEN C.PAY_OA = 'Y'  ");
		sql.append("                               THEN 1  ");
		sql.append("                               ELSE 0  ");
		sql.append("                           END))  AS PAY_OA,  ");
		sql.append("             NULL AS APPPRODUCT_ID  ");
		sql.append("       FROM W_CREDIT_INFO C  ");
		sql.append("       GROUP BY C.APP_VSOURCE)  ");
		sql.append("ORDER BY APP_VSOURCE,  ");
		sql.append("         APPLICATION_ID,  ");
		sql.append("         CREDIT_TYPE,  ");
		sql.append("         APPPRODUCT_ID ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(),parameter);
		
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
