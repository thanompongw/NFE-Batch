package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Repository(value = "declineByCAUDao")
public class DeclineByCAUDao extends AbstractReportDao {

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
		if (parameter.length > 4) {
			sql.append("SELECT A.* ");
			sql.append("FROM ");
			sql.append("( ");
		}
		sql.append("SELECT (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM    NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
		sql.append("            THEN 'CC' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM    NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("            THEN 'PL' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("            THEN 'RL' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM    NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'N')  ");
		sql.append("            THEN 'BD' ");
		sql.append("            ELSE ' ' ");
		sql.append("        END) AS GROUPLOAN_TYPE, ");
		sql.append("       T1.APP_VSOURCE, ");
		sql.append("       T1.APP_NO, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME, ");
		sql.append("       'Prim' AS CARD_TYPE, ");
		sql.append("       T1.APP_SOURCECODE AS SOURCE_CODE, ");
		sql.append("       T1.APP_AGENT AS AGENT_CODE, ");
		sql.append("       T1.APP_BRANCH AS BRANCH_CODE, ");
		sql.append("       (SELECT P.PRODUCT_DESCRIPTION || '-' || SP.SUBPRODUCT_DESCRIPTION ");
		sql.append("        FROM NFE_MS_PRODUCT P, NFE_MS_SUBPRODUCT SP ");
		sql.append("        WHERE  P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("        AND P.PRODUCT_GROUPPRODUCTID > 0 ");
		sql.append("        AND SP.SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("        AND SP.SUBPRODUCT_PRODUCTID = P.PRODUCT_ID) AS PRODUCT_SUBPRODUCT, ");
		sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS REC_DATE, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND STATUSTRACKING_STATUS = '8F') AS CANCEL_DATE, ");
		sql.append("       (SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
		sql.append("        FROM NFE_MS_REASON ");
		sql.append("        WHERE  REASON_CODE = T3.RESOLVE_REASONCODE) AS REASON, ");
		sql.append("       (WITH MEMO AS (SELECT TO_CHAR(MEMO_DETAIL) AS DETAIL, ");
		sql.append("                             MEMO_APPNO AS APPNO ");
		sql.append("                      FROM   NFE_APP_MEMO ");
		sql.append("                      WHERE  MEMO_ID > 0  ");
		sql.append("                      AND    MEMO_APPNO <> ' '  ");
		sql.append("                      AND    MEMO_TYPE = 'M') ");
		sql.append("        SELECT   RTRIM(XMLAGG(XMLELEMENT(E, DETAIL || '/') ORDER BY APPNO).EXTRACT('//text()'), '/') AS MEMO_DETAIL ");
		sql.append("        FROM  MEMO ");
		sql.append("        WHERE    APPNO = T1.APP_NO ");
		sql.append("        GROUP BY APPNO) AS MEMO_DETAIL, ");
		sql.append("       T1.APP_ANALYST ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2 ");
		sql.append("            ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("            AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("            ON  T3.RESOLVE_ID > 0 ");
		sql.append("            AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("            AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("WHERE  T1.APP_NO <> ' '  ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8C' ");
		if (parameter != null && parameter.length > 0) {
			if (parameter[0].equals(NFEBatchConstants.BUNDLE_GROUP_LOANTYPE)) {
				sql.append("AND    EXISTS (SELECT 'X' ");
				sql.append("               FROM   NFE_MS_GROUPPRODUCT ");
				sql.append("               WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
				sql.append("               AND    (GROUPPRODUCT_LOANTYPE = ? ");
				sql.append("                       OR (GROUPPRODUCT_TYPE = 'B'  ");
				sql.append("                           AND GROUPPRODUCT_LOANTYPE = ?))) ");
			} else {
				sql.append("AND    EXISTS (SELECT 'X' ");
				sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
				sql.append("                      NFE_MS_PRODUCT P ");
				sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
				sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
				if (parameter.length > 4) {
					sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN (?, ?) ");
					sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
					sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN (?, ?)))) ");
				} else {
					sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = ? ");
					sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
					sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = ?))) ");
				}
			}
		}
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		if (parameter.length > 4) {
			sql.append("UNION ALL ");
			sql.append("SELECT 'CC' AS GROUPLOAN_TYPE, ");
			sql.append("       S2.APP_VSOURCE, ");
			sql.append("       S2.APP_NO, ");
			sql.append("       S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAILNAME AS THAINAME, ");
			sql.append("       'Supp' AS CARD_TYPE, ");
			sql.append("       S2.APP_SOURCECODE AS SOURCE_CODE, ");
			sql.append("       S2.APP_AGENT AS AGENT_CODE, ");
			sql.append("       S2.APP_BRANCH AS BRANCH_CODE, ");
			sql.append("       (SELECT P.PRODUCT_DESCRIPTION || '-' || SP.SUBPRODUCT_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_PRODUCT P,  ");
			sql.append("               NFE_MS_SUBPRODUCT SP ");
			sql.append("        WHERE  P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
			sql.append("        AND   P.PRODUCT_GROUPPRODUCTID > 0 ");
			sql.append("        AND   SP.SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT ");
			sql.append("        AND   SP.SUBPRODUCT_PRODUCTID = P.PRODUCT_ID) AS PRODUCT_SUBPRODUCT, ");
			sql.append("       TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') AS REC_DATE, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
			sql.append("        FROM NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND STATUSTRACKING_STATUS = '8F') AS CANCEL_DATE, ");
			sql.append("       (SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
			sql.append("        FROM NFE_MS_REASON ");
			sql.append("        WHERE  REASON_CODE = S3.RESOLVE_REASONCODE) AS REASON, ");
			sql.append("       (WITH MEMO AS (SELECT TO_CHAR(MEMO_DETAIL) AS DETAIL, ");
			sql.append("                             MEMO_APPNO AS APPNO ");
			sql.append("                      FROM   NFE_APP_MEMO ");
			sql.append("                      WHERE  MEMO_ID > 0  ");
			sql.append("                      AND    MEMO_APPNO <> ' '  ");
			sql.append("                      AND    MEMO_TYPE = 'M') ");
			sql.append("        SELECT   RTRIM(XMLAGG(XMLELEMENT(E, DETAIL || '/') ORDER BY APPNO).EXTRACT('//text()'), '/') AS MEMO_DETAIL ");
			sql.append("        FROM       MEMO ");
			sql.append("        WHERE    APPNO = S1.APPSUP_APPNO ");
			sql.append("        GROUP BY APPNO) AS MEMO_DETAIL, ");
			sql.append("       S2.APP_ANALYST ");
			sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
			sql.append("       LEFT JOIN  NFE_APPLICATION S2 ");
			sql.append("            ON  S1.APPSUP_ID > 0 ");
			sql.append("            AND S2.APP_NO = S1.APPSUP_APPNO ");
			sql.append("       LEFT JOIN NFE_APP_RESOLVE S3 ");
			sql.append("            ON  S3.RESOLVE_ID > 0 ");
			sql.append("            AND S3.RESOLVE_APPNO = S1.APPSUP_APPNO ");
			sql.append("            AND S3.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
			sql.append("WHERE  S1.APPSUP_APPNO <> ' '  ");
			sql.append("AND    S3.RESOLVE_STATUSCODE = '8C' ");
			sql.append("AND    EXISTS(SELECT 'X' ");
			sql.append("              FROM   NFE_MS_GROUPPRODUCT G,  ");
			sql.append("                     NFE_MS_PRODUCT P ");
			sql.append("              WHERE  P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
			sql.append("              AND    P.PRODUCT_GROUPPRODUCTID = G.GROUPPRODUCT_ID ");
			sql.append("              AND    (G.GROUPPRODUCT_LOANTYPE IN ('C', 'B') ");
			sql.append("                      OR (G.GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                          AND G.GROUPPRODUCT_LOANTYPE IN ('C', 'B')))) ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_APP_STATUSTRACKING ");
			sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("               AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
			sql.append(") A ");
		}
		sql.append("ORDER BY APP_ANALYST,  ");
		sql.append("         APP_NO ");

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
