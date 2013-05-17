package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

public class ApplicationRecieveDao extends AbstractReportDao {

	public ApplicationRecieveDao() {
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
		
		sql.append("SELECT * ");
		sql.append("FROM ");
		sql.append("(SELECT TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') APP_DATETIME, ");
		sql.append("   		(CASE ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
		sql.append("        	THEN 'CC' ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE = 'F') ");
		sql.append("        	THEN 'PL' ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE = 'N') ");
		sql.append("        	THEN 'BD' ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE = 'R') ");
		sql.append("        	THEN 'RL' ");
		sql.append("        	ELSE ' ' ");
		sql.append("   		END) AS GROUPLOAN_TYPE, ");
		sql.append("   		T1.APP_VSOURCE, ");
		sql.append("   		T1.APP_NO, ");
		sql.append("   		T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS FULL_THAINAME, ");
		sql.append("   		T1.APP_CITIZENID AS CITIZENID, ");
		sql.append("   		'M' AS CREDIT_TYPE, ");
		sql.append("   		(SELECT PRODUCT_DESCRIPTION ");
		sql.append("    	 FROM NFE_MS_PRODUCT ");
		sql.append("    	 WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) ");
		sql.append("    	 || '-' || ");
		sql.append("   		(SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("    	 FROM NFE_MS_SUBPRODUCT ");
		sql.append("    	 WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) ");
		sql.append("    	AS PRODUCT_SUBPRODUCT, ");
		sql.append("   		T1.APP_CREATEBY AS USER_LOGIN, ");
		sql.append("   		T1.APP_CHKNCB, ");
		sql.append("   		T1.APP_BARCODE2, ");
		sql.append("   		T1.APP_SOURCECODE, ");
		sql.append("   		T1.APP_AGENT, ");
		sql.append("   		T1.APP_BRANCH, ");
		sql.append("   		(SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("    	 FROM NFE_MS_APPSTATUS ");
		sql.append("    	 WHERE APPSTATUS_CODE = T1.APP_STATUSCODE) AS QUEUES, ");
		sql.append("   		NVL((SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("           	 FROM NFE_MS_APPSTATUS ");
		sql.append("           	 WHERE APPSTATUS_CODE = T3.RESOLVE_STATUSCODE), '-') AS STATUS ");
		sql.append("FROM NFE_APPLICATION T1, ");
		sql.append("     NFE_APP_PRODUCT T2, ");
		sql.append(" 	 NFE_APP_RESOLVE T3 ");
		sql.append("WHERE T1.APP_NO                  <> ' ' ");
		sql.append("AND   T1.APP_STATUSCODE          <> '2C' ");
		sql.append("AND   ?                          = TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') ");
		sql.append("AND   T2.APPPRODUCT_ID(+)        > 0 ");
		sql.append("AND   T2.APPPRODUCT_APPNO(+)     = T1.APP_NO ");
		sql.append("AND   T3.RESOLVE_ID(+)           > 0 ");
		sql.append("AND   T3.RESOLVE_APPNO(+)        = T1.APP_NO ");
		sql.append("AND   T3.RESOLVE_CREDITTYPE(+)   = 'M' ");
		sql.append("AND EXISTS (SELECT 'X' ");
		sql.append("            FROM NFE_MS_GROUPPRODUCT G, ");
		sql.append(" 				 NFE_MS_PRODUCT P ");
		sql.append("            WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("			AND   P.PRODUCT_ID      = T2.APPPRODUCT_PRODUCTID ");
		sql.append("            AND  (G.GROUPPRODUCT_LOANTYPE IN ('C', 'B') ");
		sql.append("                  OR (G.GROUPPRODUCT_TYPE = 'B' ");
		sql.append("                      AND G.GROUPPRODUCT_LOANTYPE IN ('C', 'B'))) ");
		sql.append("            ) ");
		sql.append("UNION ALL ");
		sql.append("SELECT TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') APP_DATETIME, ");
		sql.append("   	   (CASE ");
		sql.append("        WHEN EXISTS (SELECT 'X' ");
		sql.append("                     FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     WHERE GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
		sql.append("                     AND   GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
		sql.append("        THEN 'CC' ");
		sql.append("        ELSE ' ' ");
		sql.append("   		END) AS GROUPLOAN_TYPE, ");
		sql.append("  	    S2.APP_VSOURCE, ");
		sql.append("   S1.APPSUP_APPNO AS APP_NO, ");
		sql.append("   S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAILNAME AS FULL_THAINAME, ");
		sql.append("   S1.APPSUP_CITIZENID AS CITIZENID, ");
		sql.append("   'S' AS CREDIT_TYPE, ");
		sql.append("   (SELECT PRODUCT_DESCRIPTION ");
		sql.append("    FROM NFE_MS_PRODUCT ");
		sql.append("    WHERE PRODUCT_ID = S1.APPSUP_PRODUCT) ");
		sql.append("    || '-' || ");
		sql.append("   (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("    FROM NFE_MS_SUBPRODUCT ");
		sql.append("    WHERE SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT) ");
		sql.append("    AS PRODUCT_SUBPRODUCT, ");
		sql.append("   S2.APP_CREATEBY AS USER_LOGIN, ");
		sql.append("   S2.APP_CHKNCB, ");
		sql.append("   S2.APP_BARCODE2, ");
		sql.append("   S2.APP_SOURCECODE, ");
		sql.append("   S2.APP_AGENT, ");
		sql.append("   S2.APP_BRANCH, ");
		sql.append("   (SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("    FROM NFE_MS_APPSTATUS ");
		sql.append("WHERE APPSTATUS_CODE = S2.APP_STATUSCODE) AS QUEUES, ");
		sql.append("   NVL((SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("     FROM NFE_MS_APPSTATUS ");
		sql.append(" WHERE APPSTATUS_CODE = S3.RESOLVE_STATUSCODE), '-') AS STATUS ");
		sql.append("FROM NFE_APP_SUPPLEMENTARY S1, ");
		sql.append(" 	 NFE_APPLICATION S2, ");
		sql.append(" 	 NFE_APP_RESOLVE S3 ");
		sql.append("WHERE S2.APP_NO                  <> ' ' ");
		sql.append("AND   S2.APP_STATUSCODE          <> '2C' ");
		sql.append("AND   ?                          = TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') ");
		sql.append("AND   S1.APPSUP_ID               > 0 ");
		sql.append("AND   S1.APPSUP_APPNO            = S2.APP_NO ");
		sql.append("AND   S3.RESOLVE_ID(+)           > 0 ");
		sql.append("AND   S3.RESOLVE_APPNO(+)        = S2.APP_NO ");
		sql.append("AND   S3.RESOLVE_CREDITTYPE(+)   = 'S' ");
		sql.append("AND EXISTS (SELECT 'X' ");
		sql.append("            FROM NFE_MS_GROUPPRODUCT G, ");
		sql.append(" 				 NFE_MS_PRODUCT P ");
		sql.append("            WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("			AND   P.PRODUCT_ID      = S1.APPSUP_PRODUCT ");
		sql.append("            AND  (G.GROUPPRODUCT_LOANTYPE IN ('C', 'B') ");
		sql.append("                  OR (G.GROUPPRODUCT_TYPE = 'B' ");
		sql.append("                      AND G.GROUPPRODUCT_LOANTYPE IN ('C', 'B'))) ");
		sql.append("            ) ");
		sql.append(") ");
		sql.append("ORDER BY APP_NO DESC, CREDIT_TYPE ASC ");
		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	public SqlRowSet queryNoCredit(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') APP_DATETIME, ");
		sql.append("   		(CASE ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
		sql.append("        	THEN 'CC' ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE = 'F') ");
		sql.append("        	THEN 'PL' ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE = 'N') ");
		sql.append("        	THEN 'BD' ");
		sql.append("        	WHEN EXISTS (SELECT 'X' ");
		sql.append("                     	 FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                     	 WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                     	 AND   GROUPPRODUCT_LOANTYPE = 'R') ");
		sql.append("        	THEN 'RL' ");
		sql.append("        	ELSE ' ' ");
		sql.append("   		END) AS GROUPLOAN_TYPE, ");
		sql.append("   		T1.APP_VSOURCE, ");
		sql.append("   		T1.APP_NO, ");
		sql.append("   		T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS FULL_THAINAME, ");
		sql.append("   		T1.APP_CITIZENID AS CITIZENID, ");
		sql.append("   		'M' AS CREDIT_TYPE, ");
		sql.append("   		(SELECT PRODUCT_DESCRIPTION ");
		sql.append("    	 FROM NFE_MS_PRODUCT ");
		sql.append("    	 WHERE PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) ");
		sql.append("    	 || '-' || ");
		sql.append("   		(SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("    	 FROM NFE_MS_SUBPRODUCT ");
		sql.append("    	 WHERE SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) ");
		sql.append("    	AS PRODUCT_SUBPRODUCT, ");
		sql.append("   		T1.APP_CREATEBY AS USER_LOGIN, ");
		sql.append("   		T1.APP_CHKNCB, ");
		sql.append("   		T1.APP_BARCODE2, ");
		sql.append("   		T1.APP_SOURCECODE, ");
		sql.append("   		T1.APP_AGENT, ");
		sql.append("   		T1.APP_BRANCH, ");
		sql.append("   		(SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("    	 FROM NFE_MS_APPSTATUS ");
		sql.append("    	 WHERE APPSTATUS_CODE = T1.APP_STATUSCODE) AS QUEUES, ");
		sql.append("   		NVL((SELECT APPSTATUS_CODE || ':' || APPSTATUS_DESCRIPTION ");
		sql.append("           	 FROM NFE_MS_APPSTATUS ");
		sql.append("           	 WHERE APPSTATUS_CODE = T3.RESOLVE_STATUSCODE), '-') AS STATUS ");
		sql.append("FROM NFE_APPLICATION T1, ");
		sql.append("     NFE_APP_PRODUCT T2, ");
		sql.append(" 	 NFE_APP_RESOLVE T3 ");
		sql.append("WHERE T1.APP_NO                  <> ' ' ");
		sql.append("AND   T1.APP_STATUSCODE          <> '2C' ");
		sql.append("AND   ?                          = TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') ");
		sql.append("AND   T2.APPPRODUCT_ID(+)        > 0 ");
		sql.append("AND   T2.APPPRODUCT_APPNO(+)     = T1.APP_NO ");
		sql.append("AND   T3.RESOLVE_ID(+)           > 0 ");
		sql.append("AND   T3.RESOLVE_APPNO(+)        = T1.APP_NO ");
		sql.append("AND EXISTS (SELECT 'X' ");
		sql.append("            FROM NFE_MS_GROUPPRODUCT G, ");
		sql.append(" 				 NFE_MS_PRODUCT P ");
		sql.append("            WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("			AND   P.PRODUCT_ID      = T2.APPPRODUCT_PRODUCTID ");
		sql.append("            AND  (G.GROUPPRODUCT_LOANTYPE = ? ");
		sql.append("                  OR (G.GROUPPRODUCT_TYPE = 'B' ");
		sql.append("                      AND G.GROUPPRODUCT_LOANTYPE = ?)) ");
		sql.append("            ) ");
		sql.append("ORDER BY APP_NO DESC, CREDIT_TYPE ASC ");
		
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
