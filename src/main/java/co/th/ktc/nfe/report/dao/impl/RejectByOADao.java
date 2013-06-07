package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Repository(value = "rejectByOADao")
public class RejectByOADao extends AbstractReportDao {

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
		
		sql.append("SELECT (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '2R') AS DATE_REJECT, ");
		sql.append("   	   T1.APP_VSOURCE AS V_SOURCE, ");
		sql.append("   	   (CASE ");
		sql.append("			WHEN EXISTS(SELECT 'X' ");
		sql.append("    					FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("    					WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
		sql.append("            THEN 'CC' ");
		sql.append("			WHEN EXISTS(SELECT 'X' ");
		sql.append("    					FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("    					WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("            THEN 'PL' ");
		sql.append("			WHEN EXISTS(SELECT 'X' ");
		sql.append("    					FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("    					WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'N')  ");
		sql.append("            THEN 'BD' ");
		sql.append("			WHEN EXISTS(SELECT 'X' ");
		sql.append("    					FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("    					WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("            THEN 'RL' ");
		sql.append("			ELSE ' ' ");
		sql.append("    	 END) AS GROUPLOAN_TYPE, ");
		sql.append("   		T1.APP_NO AS APPLICATION_NO, ");
		sql.append("   		T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME, ");
		sql.append("   		'Prim' AS CREDIT_TYPE, ");
		sql.append("   		(SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
		sql.append("         FROM   NFE_MS_REASON R, ");
		sql.append("                NFE_APP_RESOLVE AR ");
		sql.append("         WHERE  AR.RESOLVE_ID > 0 ");
		sql.append("         AND    AR.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("         AND    R.REASON_CODE = AR.RESOLVE_REASONCODE) AS REASON_DESCRIPTION, ");
		sql.append("   		(SELECT STATUSTRACKING_USER ");
		sql.append("         FROM   NFE_APP_STATUSTRACKING ");
		sql.append("         WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("         AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("         AND    STATUSTRACKING_STATUS = '2R') AS USER_ANALYST ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("   	   LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("            ON T2.APPPRODUCT_ID > 0  ");
		sql.append("            AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
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
		sql.append("               AND    STATUSTRACKING_STATUS = '2R' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		if (parameter.length > 4) {
			sql.append("UNION ALL ");
			sql.append("SELECT (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '2R') AS DATE_REJECT, ");
			sql.append("       S2.APP_VSOURCE AS V_SOURCE, ");
			sql.append("       'CC' AS GROUPLOAN_TYPE, ");
			sql.append("       S1.APPSUP_APPNO AS APPLICATION_NO, ");
			sql.append("       S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAILNAME AS CUSTOMER_NAME, ");
			sql.append("       'Supp' AS CREDIT_TYPE, ");
			sql.append("       (SELECT REASON_CODE || '-' || REASON_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_REASON R, ");
			sql.append("               NFE_APP_RESOLVE AR ");
			sql.append("        WHERE  AR.RESOLVE_ID > 0 ");
			sql.append("        AND    AR.RESOLVE_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND    R.REASON_CODE = AR.RESOLVE_REASONCODE) AS REASON_DESCRIPTION, ");
			sql.append("       (SELECT STATUSTRACKING_USER ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '2R') AS USER_ANALYST ");
			sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
			sql.append("       LEFT JOIN NFE_APPLICATION S2  ");
			sql.append("            ON  S1.APPSUP_ID > 0 ");
			sql.append("            AND S2.APP_NO = S1.APPSUP_APPNO ");
			sql.append("WHERE  S1.APPSUP_APPNO <> ' ' ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
			sql.append("                      NFE_MS_PRODUCT P ");
			sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
			sql.append("               AND    P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
			sql.append("               AND    G.GROUPPRODUCT_LOANTYPE = 'C') ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_APP_STATUSTRACKING ");
			sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("               AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("               AND    STATUSTRACKING_STATUS = '2R' ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')))");
			sql.append(") A ");
		}
		sql.append("ORDER BY V_SOURCE, ");
		sql.append("         APPLICATION_NO ");

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
