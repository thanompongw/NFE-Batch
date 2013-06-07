package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Repository(value = "applicationReceiveDao")
public class ApplicationReceiveDao extends AbstractReportDao {

	public ApplicationReceiveDao() {
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
		
		if (parameter.length > 4) {
			sql.append("SELECT * ");
			sql.append("FROM ");
			sql.append("( ");
		}
		sql.append("SELECT TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS DATE_REC, ");
		sql.append("       (CASE ");
		sql.append("              WHEN EXISTS ");
		sql.append("                       (SELECT 'X' ");
		sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                        WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
		sql.append("              THEN 'CC' ");
		sql.append("              WHEN EXISTS ");
		sql.append("                       (SELECT 'X' ");
		sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                        WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("              THEN 'RL' ");
		sql.append("              WHEN EXISTS ");
		sql.append("                       (SELECT 'X' ");
		sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                        WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("              THEN 'PL' ");
		sql.append("              WHEN EXISTS ");
		sql.append("                       (SELECT 'X' ");
		sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                        WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'N')  ");
		sql.append("              THEN 'BD' ");
		sql.append("              ELSE ' ' ");
		sql.append("        END) AS GROUPPRODUCT_LOANTYPE, ");
		sql.append("       T1.APP_VSOURCE, ");
		sql.append("       T1.APP_NO AS APPLICATIONID, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME, ");
		sql.append("       T1.APP_CITIZENID AS CITIZENID, ");
		sql.append("       'M' AS GROUPPRODUCT_TYPE, ");
		sql.append("       (SELECT P.PRODUCT_DESCRIPTION || '-' || SP.SUBPRODUCT_DESCRIPTION ");
		sql.append("        FROM   NFE_MS_PRODUCT P,  ");
		sql.append("               NFE_MS_SUBPRODUCT SP ");
		sql.append("        WHERE  P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("        AND    P.PRODUCT_GROUPPRODUCTID > 0 ");
		sql.append("        AND    SP.SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("        AND    SP.SUBPRODUCT_PRODUCTID = P.PRODUCT_ID) AS PRODUCT_SUBPRODUCT, ");
		sql.append("       T1.APP_CREATEBY, ");
		sql.append("       T1.APP_CHKNCB AS NCB_STATUS, ");
		sql.append("       T1.APP_BARCODE2, ");
		sql.append("       T1.APP_SOURCECODE, ");
		sql.append("       T1.APP_AGENT, ");
		sql.append("       T1.APP_BRANCH, ");
		sql.append("       (SELECT APPSTATUS_DESCRIPTION ");
		sql.append("        FROM   NFE_MS_APPSTATUS ");
		sql.append("        WHERE  APPSTATUS_ID > 0 ");
		sql.append("        AND    APPSTATUS_CODE = T1.APP_STATUSCODE) AS QUEUEE, ");
		sql.append("       NVL((SELECT APPSTATUS_DESCRIPTION ");
		sql.append("            FROM   NFE_MS_APPSTATUS S, ");
		sql.append("                   NFE_APP_RESOLVE R ");
		sql.append("            WHERE  R.RESOLVE_ID > 0 ");
		sql.append("            AND    R.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("            AND    (R.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("                   OR R.RESOLVE_APPPRODUCTID IS NULL) ");
		sql.append("            AND    APPSTATUS_ID > 0 ");
		sql.append("            AND    APPSTATUS_CODE = R.RESOLVE_STATUSCODE), '-') AS APPSTATUS_DESCRIPTION ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T1.APP_STATUSCODE <> '2C' ");
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
		sql.append("AND (T1.APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                         AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");
		if (parameter.length > 4) {
			sql.append("UNION ALL ");
			sql.append("SELECT TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') AS DATE_REC, ");
			sql.append("       (CASE ");
			sql.append("              WHEN EXISTS ");
			sql.append("                       (SELECT 'X' ");
			sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
			sql.append("                        WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("                        AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
			sql.append("              THEN 'CC' ");
			sql.append("              WHEN EXISTS ");
			sql.append("                       (SELECT 'X' ");
			sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
			sql.append("                        WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
			sql.append("              THEN 'RL' ");
			sql.append("              WHEN EXISTS ");
			sql.append("                       (SELECT 'X' ");
			sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
			sql.append("                        WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
			sql.append("              THEN 'PL' ");
			sql.append("              WHEN EXISTS ");
			sql.append("                       (SELECT 'X' ");
			sql.append("                        FROM   NFE_MS_GROUPPRODUCT ");
			sql.append("                        WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("                        AND    GROUPPRODUCT_LOANTYPE = 'N')  ");
			sql.append("              THEN 'BD' ");
			sql.append("              ELSE ' ' ");
			sql.append("        END) AS GROUPPRODUCT_LOANTYPE, ");
			sql.append("       S2.APP_VSOURCE, ");
			sql.append("       S2.APP_NO AS APPLICATIONID, ");
			sql.append("       S2.APP_THAIFNAME || ' ' || S2.APP_THAILNAME AS THAINAME, ");
			sql.append("       S2.APP_CITIZENID AS CITIZENID, ");
			sql.append("       'S' AS GROUPPRODUCT_TYPE, ");
			sql.append("       (SELECT P.PRODUCT_DESCRIPTION || '-' || SP.SUBPRODUCT_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_PRODUCT P,  ");
			sql.append("               NFE_MS_SUBPRODUCT SP ");
			sql.append("        WHERE  P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
			sql.append("        AND    P.PRODUCT_GROUPPRODUCTID > 0 ");
			sql.append("        AND    SP.SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT ");
			sql.append("        AND    SP.SUBPRODUCT_PRODUCTID = P.PRODUCT_ID) AS PRODUCT_SUBPRODUCT, ");
			sql.append("       S2.APP_CREATEBY, ");
			sql.append("       S2.APP_CHKNCB AS NCB_STATUS, ");
			sql.append("       S2.APP_BARCODE2, ");
			sql.append("       S2.APP_SOURCECODE, ");
			sql.append("       S2.APP_AGENT, ");
			sql.append("       S2.APP_BRANCH, ");
			sql.append("       (SELECT APPSTATUS_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_APPSTATUS ");
			sql.append("        WHERE  APPSTATUS_ID > 0 ");
			sql.append("        AND    APPSTATUS_CODE = S2.APP_STATUSCODE) AS QUEUEE, ");
			sql.append("       NVL((SELECT APPSTATUS_DESCRIPTION ");
			sql.append("            FROM   NFE_MS_APPSTATUS S, ");
			sql.append("                   NFE_APP_RESOLVE R ");
			sql.append("            WHERE  R.RESOLVE_ID > 0 ");
			sql.append("            AND    R.RESOLVE_APPNO = S1.APPSUP_APPNO ");
			sql.append("            AND    (R.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
			sql.append("                    OR R.RESOLVE_APPPRODUCTID IS NULL) ");
			sql.append("            AND    APPSTATUS_ID > 0 ");
			sql.append("            AND    APPSTATUS_CODE = R.RESOLVE_STATUSCODE), '-') AS APPSTATUS_DESCRIPTION ");
			sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
			sql.append("       LEFT JOIN NFE_APPLICATION S2 ");
			sql.append("           ON  S1.APPSUP_ID > 0 ");
			sql.append("           AND S1.APPSUP_APPNO = S2.APP_NO ");
			sql.append("WHERE  S1.APPSUP_APPNO <> ' ' ");
			sql.append("AND    S2.APP_STATUSCODE <> '2C' ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
			sql.append("                      NFE_MS_PRODUCT P ");
			sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
			sql.append("               AND    P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
			sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN (?, ?) ");
			sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN (?, ?)))) ");
			sql.append("AND (S2.APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                         AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')) ");
			sql.append(")  ");
			sql.append("ORDER BY APPLICATIONID DESC, GROUPPRODUCT_TYPE ");
		} else {
			sql.append("ORDER BY T1.APP_NO DESC ");
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
