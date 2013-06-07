package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Repository(value="distributionDao")
public class DistributionDao extends AbstractReportDao {

	public DistributionDao() {
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
		
		if (parameter.length > 6) {
		
			sql.append("SELECT A.* ");
			sql.append("FROM  ");
			sql.append("( ");
			sql.append("SELECT T1.APP_NO AS APPNO, ");
			sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME, ");
			sql.append("       (CASE  ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '8A' ");
			sql.append("            THEN 'A' ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '8C' ");
			sql.append("            THEN 'C' ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '8D' ");
			sql.append("            THEN 'D' ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '2R' ");
			sql.append("            THEN 'R' ");
			sql.append("        END) AS STATUS, ");
			sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS RECEIVE_DATE, ");
			sql.append("       T3.RESOLVE_CREDITTYPE AS BS, ");
			sql.append("       T1.APP_SOURCECODE AS SOURCE, ");
			sql.append("       T1.APP_AGENT AS AGENT_CODE, ");
			sql.append("   	   (SELECT SUBPRODUCT_CODE ");
			sql.append("        FROM   NFE_MS_SUBPRODUCT ");
			sql.append("        WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
			sql.append("        AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID) AS TYPE, ");
			sql.append("       (SELECT SUBPRODUCT_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_SUBPRODUCT ");
			sql.append("        WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
			sql.append("        AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID) AS SUBTYPE, ");
			sql.append("       (CASE WHEN T3.RESOLVE_STATUSCODE = '8A'  ");
			sql.append("             THEN T4.APPROVE_CREDITLIMIT  ");
			sql.append("             ELSE T2.APPPRODUCT_CREDITLIMIT  ");
			sql.append("        END) AS LINELIMIT, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID = (SELECT MAX(STATUSTRACKING_ID) ");
			sql.append("                                    FROM   NFE_APP_STATUSTRACKING ");
			sql.append("                                    WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("                                    AND    STATUSTRACKING_APPNO = T1.APP_NO)) AS UPDATE_DATE, ");
			sql.append("       (SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_REASON ");
			sql.append("        WHERE  REASON_CODE = T3.RESOLVE_REASONCODE) AS REASON, ");
			sql.append("       (CASE WHEN T3.RESOLVE_STATUSCODE = '8A'  ");
			sql.append("             THEN T4.APPROVE_CARDNO  ");
			sql.append("             ELSE T1.APP_CARDNO  ");
			sql.append("        END) AS ACCT_NO, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS APPROVE_DATE, ");
			sql.append("       'N' AS EXISTING, ");
			sql.append("       T1.APP_BRANCH AS BRANCH_CODE, ");
			sql.append("       T1.APP_CITIZENID AS CITIZENID ");
			sql.append("FROM   NFE_APPLICATION T1 ");
			sql.append("   	   LEFT JOIN NFE_APP_PRODUCT T2  ");
			sql.append("           ON T2.APPPRODUCT_ID > 0  ");
			sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
			sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
			sql.append("       	   ON T3.RESOLVE_ID > 0 ");
			sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
			sql.append("           AND (T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
			sql.append("                OR T3.RESOLVE_APPPRODUCTID IS NULL) ");
			sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
			sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
			sql.append("WHERE  T1.APP_NO <> ' ' ");
			sql.append("AND    T3.RESOLVE_STATUSCODE = ? ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM    NFE_MS_GROUPPRODUCT ");
			sql.append("               WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
			sql.append("               AND     ((GROUPPRODUCT_TYPE = 'M' ");
			sql.append("                         AND GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
			sql.append("                        OR (GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                            AND GROUPPRODUCT_LOANTYPE IN ('C', 'B')))) ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_APP_STATUSTRACKING ");
			sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
			sql.append("               AND    STATUSTRACKING_STATUS = ? ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
			sql.append("UNION ALL ");
			sql.append("SELECT S1.APPSUP_APPNO AS APPNO, ");
			sql.append("       S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAILNAME AS THAINAME, ");
			sql.append("       (CASE  ");
			sql.append("            WHEN S3.RESOLVE_STATUSCODE = '8A' ");
			sql.append("            THEN 'A' ");
			sql.append("            WHEN S3.RESOLVE_STATUSCODE = '8C' ");
			sql.append("            THEN 'C' ");
			sql.append("            WHEN S3.RESOLVE_STATUSCODE = '8D' ");
			sql.append("            THEN 'D' ");
			sql.append("            WHEN S3.RESOLVE_STATUSCODE = '2R' ");
			sql.append("            THEN 'R' ");
			sql.append("        END) AS STATUS, ");
			sql.append("       TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') AS RECEIVE_DATE, ");
			sql.append("       S3.RESOLVE_CREDITTYPE AS BS, ");
			sql.append("       S2.APP_SOURCECODE AS SOURCE, ");
			sql.append("       S2.APP_AGENT AS AGENT_CODE, ");
			sql.append("       (SELECT SUBPRODUCT_CODE ");
			sql.append("        FROM   NFE_MS_SUBPRODUCT ");
			sql.append("        WHERE  SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT ");
			sql.append("        AND    SUBPRODUCT_PRODUCTID = S1.APPSUP_PRODUCT) AS TYPE, ");
			sql.append("       (SELECT SUBPRODUCT_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_SUBPRODUCT ");
			sql.append("        WHERE  SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT ");
			sql.append("        AND    SUBPRODUCT_PRODUCTID = S1.APPSUP_PRODUCT) AS SUBTYPE, ");
			sql.append("       (CASE WHEN S3.RESOLVE_STATUSCODE = '8A'  ");
			sql.append("             THEN S4.APPROVE_CREDITLIMIT  ");
			sql.append("             ELSE S1.APPSUP_CREDITLIMIT ");
			sql.append("        END) AS LINELIMIT, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID = (SELECT MAX(STATUSTRACKING_ID) ");
			sql.append("                                    FROM   NFE_APP_STATUSTRACKING ");
			sql.append("                                    WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("                                    AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO)) AS UPDATE_DATE, ");
			sql.append("       (SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_REASON ");
			sql.append("        WHERE  REASON_CODE = S3.RESOLVE_REASONCODE) AS REASON, ");
			sql.append("       (CASE WHEN S3.RESOLVE_STATUSCODE = '8A'  ");
			sql.append("             THEN S4.APPROVE_CARDNO  ");
			sql.append("             ELSE S2.APP_CARDNO  ");
			sql.append("        END) AS ACCT_NO, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS APPROVE_DATE, ");
			sql.append("       'N' AS EXISTING, ");
			sql.append("       S2.APP_BRANCH AS BRANCH_CODE, ");
			sql.append("       S1.APPSUP_CITIZENID AS CITIZENID ");
			sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
			sql.append("       LEFT JOIN NFE_APPLICATION S2 ");
			sql.append("           ON  S1.APPSUP_ID > 0 ");
			sql.append("           AND S2.APP_NO = S1.APPSUP_APPNO ");
			sql.append("       LEFT JOIN NFE_APP_RESOLVE S3 ");
			sql.append("           ON S3.RESOLVE_ID > 0 ");
			sql.append("           AND S3.RESOLVE_APPNO = S1.APPSUP_APPNO ");
			sql.append("           AND (S3.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
			sql.append("                OR S3.RESOLVE_APPPRODUCTID IS NULL) ");
			sql.append("       LEFT JOIN NFE_APP_APPROVE S4  ");
			sql.append("           ON S4.APPROVE_RESOLVEID = S3.RESOLVE_ID ");
			sql.append("WHERE  S1.APPSUP_APPNO <> ' ' ");
			sql.append("AND    S3.RESOLVE_STATUSCODE = ? ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM    NFE_MS_GROUPPRODUCT ");
			sql.append("               WHERE   GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("               AND     GROUPPRODUCT_TYPE = 'S'  ");
			sql.append("               AND     GROUPPRODUCT_LOANTYPE = 'C') ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_APP_STATUSTRACKING ");
			sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("               AND    STATUSTRACKING_APPNO = S2.APP_NO ");
			sql.append("               AND    STATUSTRACKING_STATUS = ? ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
			sql.append(") A ");
		} else {
			sql.append("SELECT T1.APP_NO AS APPNO, ");
			sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME, ");
			sql.append("       (CASE  ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '8A' ");
			sql.append("            THEN 'A' ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '8C' ");
			sql.append("            THEN 'C' ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '8D' ");
			sql.append("            THEN 'D' ");
			sql.append("            WHEN T3.RESOLVE_STATUSCODE = '2R' ");
			sql.append("            THEN 'R' ");
			sql.append("        END) AS STATUS, ");
			sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS RECEIVE_DATE, ");
			sql.append("       T1.APP_SOURCECODE AS SOURCE, ");
			sql.append("       T1.APP_AGENT AS AGENT_CODE, ");
			sql.append("       (SELECT SUBPRODUCT_CODE || '-' || SUBPRODUCT_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_SUBPRODUCT ");
			sql.append("        WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
			sql.append("        AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID) AS LOAN_TYPE, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID = (SELECT MAX(STATUSTRACKING_ID) ");
			sql.append("                                    FROM   NFE_APP_STATUSTRACKING ");
			sql.append("                                    WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("                                    AND    STATUSTRACKING_APPNO = T1.APP_NO)) AS UPDATE_DATE, ");
			sql.append("       (CASE WHEN T3.RESOLVE_STATUSCODE = '8A'  ");
			sql.append("             THEN T4.APPROVE_CARDNO  ");
			sql.append("             ELSE T1.APP_CARDNO  ");
			sql.append("        END) AS ACCT_NO, ");
			sql.append("       (SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_REASON ");
			sql.append("        WHERE  REASON_CODE = T3.RESOLVE_REASONCODE) AS REASON, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS APPROVE_DATE, ");
			sql.append("       (CASE WHEN T3.RESOLVE_STATUSCODE = '8A'  ");
			sql.append("             THEN T4.APPROVE_CREDITLIMIT  ");
			sql.append("             ELSE T2.APPPRODUCT_CREDITLIMIT  ");
			sql.append("        END) AS LINELIMIT, ");
			sql.append("       T1.APP_CITIZENID AS CITIZENID, ");
			sql.append("       T1.APP_BRANCH AS BRANCH_CODE, ");
			sql.append("       (CASE WHEN T3.RESOLVE_STATUSCODE = '8A' ");
			sql.append("             THEN (CASE ");
			sql.append("                        WHEN EXISTS (SELECT 'X' ");
			sql.append("                                     FROM   NFE_MS_GROUPPRODUCT G,  ");
			sql.append("                                            NFE_MS_PRODUCT P ");
			sql.append("                                     WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
			sql.append("                                     AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
			sql.append("                                     AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
			sql.append("                                             OR (G.GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                                                 AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
			sql.append("                        THEN T4.APPROVE_CREDITLIMIT ");
			sql.append("                        ELSE T4.APPROVE_CASHADVANCE ");
			sql.append("                   END) ");
			sql.append("             ELSE (CASE ");
			sql.append("                        WHEN EXISTS (SELECT 'X' ");
			sql.append("                                     FROM   NFE_MS_GROUPPRODUCT G,  ");
			sql.append("                                            NFE_MS_PRODUCT P ");
			sql.append("                                     WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
			sql.append("                                     AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
			sql.append("                                     AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
			sql.append("                                             OR (G.GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                                                 AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
			sql.append("                        THEN T2.APPPRODUCT_CREDITLIMIT ");
			sql.append("                        ELSE T2.APPPRODUCT_CASHADVANCE ");
			sql.append("                   END)  ");
			sql.append("        END) AS DRAWNDOWN ");
			sql.append("FROM   NFE_APPLICATION T1 ");
			sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
			sql.append("           ON T2.APPPRODUCT_ID > 0  ");
			sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
			sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
			sql.append("           ON T3.RESOLVE_ID > 0 ");
			sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
			sql.append("           AND (T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
			sql.append("                OR T3.RESOLVE_APPPRODUCTID IS NULL) ");
			sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
			sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
			sql.append("WHERE  T1.APP_NO <> ' ' ");
			sql.append("AND    T3.RESOLVE_STATUSCODE = ? ");
			sql.append("AND   EXISTS (SELECT 'X' ");
			sql.append("              FROM    NFE_MS_GROUPPRODUCT ");
			sql.append("              WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
			sql.append("              AND     ((GROUPPRODUCT_TYPE = 'M' ");
			sql.append("                        AND GROUPPRODUCT_LOANTYPE = ?) ");
			sql.append("                       OR (GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                           AND GROUPPRODUCT_LOANTYPE = ?))) ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_APP_STATUSTRACKING ");
			sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
			sql.append("               AND    STATUSTRACKING_STATUS = ? ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		}
		
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
