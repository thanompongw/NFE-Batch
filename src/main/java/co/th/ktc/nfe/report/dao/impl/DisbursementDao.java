/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;


@Repository(value="disbursementDao")
public class DisbursementDao extends AbstractReportDao {

	/**
	 * 
	 */
	public DisbursementDao() {
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
		
		sql.append("SELECT (CASE ");
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
		sql.append("            ELSE '' ");
		sql.append("        END) AS GROUPLOAN_TYPE, ");
		sql.append("       TO_CHAR(T4.APPROVE_UPDATEDATE, 'DD/MM/YYYY') AS APPROVE_DATE, ");
		sql.append("       T4.APPROVE_CARDNO AS ACCOUNT_NO, ");
		sql.append("       T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME AS ENGLISH_NAME, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT, '00.00') AS CREDIT_LINE, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT, T4.APPROVE_CASHADVANCE) AS MONEY_TRANSFER, ");
		sql.append("       (T4.APPROVE_PERCENTINTEREST + T4.APPROVE_COMMINTEREST) AS INTEREST_RATE, ");
		sql.append("       (CASE ");
		sql.append("             WHEN EXISTS (SELECT 'X' ");
		sql.append("                          FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                 NFE_MS_PRODUCT P ");
		sql.append("                          WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                          AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                          AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                  OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                      AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
		sql.append("             THEN (SELECT B.BANK_CODE  ");
		sql.append("                   FROM NFE_MS_BANK B, ");
		sql.append("                        NFE_APP_TRANSFERLOAN TL ");
		sql.append("                   WHERE TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                   AND   TL.TRANSFERLOAN_BANK = B.BANK_ID ");
		sql.append("                   AND   TL.TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("             WHEN EXISTS (SELECT 'X' ");
		sql.append("                          FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                 NFE_MS_PRODUCT P ");
		sql.append("                          WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                          AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                          AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                                  OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                      AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("             THEN (SELECT B.BANK_CODE  ");
		sql.append("                   FROM NFE_MS_BANK B, ");
		sql.append("                        NFE_APP_TRANSFERDEBT TD ");
		sql.append("                   WHERE TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                   AND   TD.TRANSFERDEBT_BANK = B.BANK_ID ");
		sql.append("                   AND   TD.TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("             ELSE '' ");
		sql.append("        END) AS BANK_CODE, ");
		sql.append("       (CASE ");
		sql.append("             WHEN EXISTS (SELECT 'X' ");
		sql.append("                          FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                 NFE_MS_PRODUCT P ");
		sql.append("                          WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                          AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                          AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                                  OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                      AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
		sql.append("             THEN (SELECT TL.TRANSFERLOAN_ACCNO ");
		sql.append("                   FROM  NFE_APP_TRANSFERLOAN TL ");
		sql.append("                   WHERE TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                   AND   TL.TRANSFERLOAN_APPNO = T1.APP_NO) ");
		sql.append("             WHEN EXISTS (SELECT 'X' ");
		sql.append("                          FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                                 NFE_MS_PRODUCT P ");
		sql.append("                          WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("                          AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                          AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                                  OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                                      AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("             THEN (SELECT TD.TRANSFERDEBT_ACCNO  ");
		sql.append("                   FROM  NFE_APP_TRANSFERDEBT TD ");
		sql.append("                   WHERE TD.TRANSFERDEBT_ID > 0 ");
		sql.append("                   AND   TD.TRANSFERDEBT_APPNO = T1.APP_NO) ");
		sql.append("             ELSE '' ");
		sql.append("        END) AS TRANSFER_ACCOUNT, ");
		sql.append("       T1.APP_ANALYST AS ANALYST, ");
		sql.append("       (SELECT PRODUCT_DESCRIPTION ");
		sql.append("        FROM   NFE_MS_PRODUCT ");
		sql.append("        WHERE  PRODUCT_ID = T2.APPPRODUCT_PRODUCTID) ");
		sql.append("       || '-' ");
		sql.append("       || (SELECT SUBPRODUCT_DESCRIPTION ");
		sql.append("           FROM   NFE_MS_SUBPRODUCT ");
		sql.append("           WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID) AS PRODUCT_SUBPRODUCT ");
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
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN ('F', 'R') ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN ('F', 'R')))) ");
		if ((parameter != null && parameter.length == 3)
				&& parameter[parameter.length - 1].equals(NFEBatchConstants.SUCCESS_FLAG)) {
			sql.append("AND    NOT EXISTS (SELECT 'X' ");
			sql.append("                   FROM   NFE_G_MONTRAN ");
			sql.append("                   WHERE  APP_NO = T1.APP_NO) ");
		} else {
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_G_MONTRAN ");
			sql.append("               WHERE  APP_NO = T1.APP_NO) ");			
		}
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("ORDER BY T1.APP_NO ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), new Object[] {parameter[0],
					                                                           parameter[1]});
		
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
