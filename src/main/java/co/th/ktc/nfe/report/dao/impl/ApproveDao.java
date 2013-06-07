/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;


@Repository(value="approveDao")
public class ApproveDao extends AbstractReportDao {

	/**
	 * 
	 */
	public ApproveDao() {
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
		
		if (parameter.length > 4) {
			sql.append("SELECT A.* ");
			sql.append("FROM ");
			sql.append("( ");
		}
		sql.append("SELECT T1.APP_VSOURCE AS V_SOURCE, ");
		sql.append("       (CASE ");
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
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS DATE_APPROVE, ");
		sql.append("       T1.APP_NO AS APPLY_ID, ");
		sql.append("       T4.APPROVE_CARDNO AS CARD_NO, ");
		sql.append("       T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME AS CUSTOMER_NAME, ");
		sql.append("       T4.APPROVE_CREDITLIMIT AS CREDIT_LINE, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT, T4.APPROVE_CASHADVANCE) AS MONEY_TRANSFER, ");
		sql.append("       T5.DTA_YEARLYINCOME AS INCOME, ");
		sql.append("       T4.APPROVE_CREDITLIMIT / NULLIF(T5.DTA_YEARLYINCOME / 12, 0) AS FIVEX, ");
		sql.append("       (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM     NFE_MS_CUSTOMERTYPE ");
		sql.append("                         WHERE  CUSTOMERTYPE_ID = T5.DTA_CUSTOMERTYPE  ");
		sql.append("                         AND    CUSTOMERTYPE_CODE = 'I')  ");
		sql.append("            THEN (CASE ");
		sql.append("                     WHEN ((T5.DTA_YEARLYINCOME < 500000) ");
		sql.append("                            AND (T4.APPROVE_CREDITLIMIT > (T5.DTA_YEARLYINCOME * .10)))  ");
		sql.append("                     THEN 'F' ");
		sql.append("                     WHEN ((T5.DTA_YEARLYINCOME > 500000) ");
		sql.append("                            AND (T4.APPROVE_CREDITLIMIT > (T5.DTA_YEARLYINCOME * .10)))  ");
		sql.append("                     THEN 'F' ");
		sql.append("                     WHEN ((T5.DTA_YEARLYINCOME = 500000) ");
		sql.append("                            AND (T4.APPROVE_CREDITLIMIT > (T5.DTA_YEARLYINCOME * .10)))  ");
		sql.append("                     THEN 'F' ");
		sql.append("                     ELSE 'T' ");
		sql.append("                  END) ");
		sql.append("            ELSE 'X' ");
		sql.append("        END) AS TENPY, ");
		sql.append("       (T4.APPROVE_PERCENTINTEREST + T4.APPROVE_COMMINTEREST) AS INTERESTRATE, ");
		sql.append("       'Prim' AS CREDIT_TYPE, ");
		sql.append("       (SELECT CUSTOMERTYPE_CODE ");
		sql.append("        FROM   NFE_MS_CUSTOMERTYPE ");
		sql.append("        WHERE  CUSTOMERTYPE_ID = T5.DTA_CUSTOMERTYPE) AS CUSTOMER_TYPE, ");
		sql.append("       T1.APP_ANALYST, ");
		sql.append("       (SELECT PRODUCT_DESCRIPTION ");
		sql.append("        FROM   NFE_MS_PRODUCT ");
		sql.append("        WHERE  PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) ");
		sql.append("       || '-' ");
		sql.append("       || (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("           FROM   NFE_MS_SUBPRODUCT ");
		sql.append("           WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS PRODUCT_SUBPRODUCT, ");
		sql.append("       T1.APP_SOURCECODE AS SOURCE_CODE, ");
		sql.append("       NVL(T4.APPROVE_EMBOSSNAME1, '') AS APPROVE_EMBOSSNAME1, ");
		sql.append("       NVL(T4.APPROVE_EMBOSSNAME2, '') AS APPROVE_EMBOSSNAME2, ");
		sql.append("       (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM    NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("            THEN (SELECT B.BANK_CODE  ");
		sql.append("                  FROM   NFE_MS_BANK B, ");
		sql.append("                         NFE_APP_TRANSFERLOAN TL  ");
		sql.append("                  WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                  AND    TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                  AND    TL.TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("            THEN (SELECT B.BANK_CODE  ");
		sql.append("                  FROM   NFE_MS_BANK B, ");
		sql.append("                         NFE_APP_TRANSFERDEBT TD ");
		sql.append("                  WHERE  B.BANK_ID = TD.TRANSFERDEBT_BANK ");
		sql.append("                  AND    TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                  AND    TD.TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("            ELSE ' ' ");
		sql.append("        END) AS BANK_CODE, ");
		sql.append("       (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM    NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("            THEN (SELECT TRANSFERLOAN_ACCNO ");
		sql.append("                  FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                  WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                  AND    TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("            THEN (SELECT TRANSFERDEBT_ACCNO ");
		sql.append("                  FROM   NFE_APP_TRANSFERDEBT ");
		sql.append("                  WHERE  TRANSFERDEBT_ID > 0 ");
		sql.append("                  AND    TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("            ELSE ' ' ");
		sql.append("        END) AS ACCOUNT_NO ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("       JOIN NFE_APP_DATAANALYSIS T5  ");
		sql.append("           ON T5.DTA_APPNO = T1.APP_NO ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_CREDITTYPE = 'M' ");
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
			sql.append("SELECT S2.APP_VSOURCE AS V_SOURCE, ");
			sql.append("       'CC' AS GROUPLOAN_TYPE, ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS DATE_APPROVE, ");
			sql.append("       S1.APPSUP_APPNO AS APPLY_ID, ");
			sql.append("       S4.APPROVE_CARDNO AS CARD_NO, ");
			sql.append("       S1.APPSUP_ENGFNAME || ' ' || S1.APPSUP_ENGLNAME AS CUSTOMER_NAME, ");
			sql.append("       S4.APPROVE_CREDITLIMIT AS CREDIT_LINE, ");
			sql.append("       0.00 AS MONEY_TRANSFER, ");
			sql.append("       S1.APPSUP_YEARLYINCOME AS INCOME, ");
			sql.append("       S4.APPROVE_CREDITLIMIT / NULLIF(S1.APPSUP_YEARLYINCOME / 12, 0) AS FIVEX, ");
			sql.append("       (CASE ");
			sql.append("            WHEN EXISTS (SELECT 'X' ");
			sql.append("                         FROM     NFE_MS_CUSTOMERTYPE ");
			sql.append("                         WHERE  CUSTOMERTYPE_ID = S1.APPSUP_CUSTOMERTYPE ");
			sql.append("                         AND    CUSTOMERTYPE_CODE = 'I')  ");
			sql.append("            THEN (CASE ");
			sql.append("                     WHEN ((S1.APPSUP_YEARLYINCOME < 500000) ");
			sql.append("                            AND (S4.APPROVE_CREDITLIMIT > (S1.APPSUP_YEARLYINCOME * .10)))  ");
			sql.append("                     THEN 'F' ");
			sql.append("                     WHEN ((S1.APPSUP_YEARLYINCOME > 500000) ");
			sql.append("                            AND (S4.APPROVE_CREDITLIMIT > (S1.APPSUP_YEARLYINCOME * .10)))  ");
			sql.append("                     THEN 'F' ");
			sql.append("                     WHEN ((S1.APPSUP_YEARLYINCOME = 500000) ");
			sql.append("                            AND (S4.APPROVE_CREDITLIMIT > (S1.APPSUP_YEARLYINCOME * .10)))  ");
			sql.append("                     THEN 'F' ");
			sql.append("                     ELSE 'T' ");
			sql.append("                  END) ");
			sql.append("            ELSE 'X' ");
			sql.append("        END) AS TENPY, ");
			sql.append("       (S4.APPROVE_PERCENTINTEREST + S4.APPROVE_COMMINTEREST) AS INTERESTRATE, ");
			sql.append("       'Supp' AS CREDIT_TYPE, ");
			sql.append("       (SELECT CUSTOMERTYPE_CODE ");
			sql.append("        FROM   NFE_MS_CUSTOMERTYPE ");
			sql.append("        WHERE  CUSTOMERTYPE_ID = S1.APPSUP_CUSTOMERTYPE) AS CUSTOMER_TYPE, ");
			sql.append("       S2.APP_ANALYST, ");
			sql.append("       (SELECT PRODUCT_DESCRIPTION ");
			sql.append("        FROM   NFE_MS_PRODUCT ");
			sql.append("        WHERE  PRODUCT_ID = S1.APPSUP_PRODUCT) ");
			sql.append("       || '-' ");
			sql.append("       || (SELECT SUBPRODUCT_DESCRIPTION ");
			sql.append("           FROM   NFE_MS_SUBPRODUCT ");
			sql.append("           WHERE  SUBPRODUCT_ID = S1.APPSUP_SUBPRODUCT) AS PRODUCT_SUBPRODUCT, ");
			sql.append("       S2.APP_SOURCECODE AS SOURCE_CODE, ");
			sql.append("       NVL(S4.APPROVE_EMBOSSNAME1, '') AS APPROVE_EMBOSSNAME1, ");
			sql.append("       NVL(S4.APPROVE_EMBOSSNAME2, '') AS APPROVE_EMBOSSNAME2, ");
			sql.append("       '' AS BANK_CODE, ");
			sql.append("       '' AS ACCOUNT_NO ");
			sql.append("FROM   NFE_APP_SUPPLEMENTARY S1  ");
			sql.append("       LEFT JOIN NFE_APPLICATION S2 ");
			sql.append("           ON  S1.APPSUP_ID > 0 ");
			sql.append("           AND S1.APPSUP_APPNO = S2.APP_NO ");
			sql.append("       LEFT JOIN NFE_APP_RESOLVE S3 ");
			sql.append("           ON  S3.RESOLVE_ID > 0 ");
			sql.append("           AND S3.RESOLVE_APPNO = S1.APPSUP_APPNO ");
			sql.append("           AND S3.RESOLVE_APPPRODUCTID = S1.APPSUP_ID ");
			sql.append("       LEFT JOIN NFE_APP_APPROVE S4  ");
			sql.append("           ON S4.APPROVE_RESOLVEID = S3.RESOLVE_ID ");
			sql.append("WHERE  S1.APPSUP_APPNO <> ' ' ");
			sql.append("AND    S3.RESOLVE_STATUSCODE = '8A' ");
			sql.append("AND    S4.APPROVE_CREDITTYPE = 'S' ");
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
			sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')))");
			sql.append(") A ");
			sql.append("ORDER BY A.V_SOURCE, ");
			sql.append("         A.CARD_NO, ");
			sql.append("         A.APP_ANALYST, ");
			sql.append("         A.APPLY_ID ");
		} else {
			sql.append("ORDER BY T1.APP_VSOURCE, ");
			sql.append("         T4.APPROVE_CARDNO, ");
			sql.append("         T1.APP_ANALYST, ");
			sql.append("         T1.APP_NO");
		}

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
