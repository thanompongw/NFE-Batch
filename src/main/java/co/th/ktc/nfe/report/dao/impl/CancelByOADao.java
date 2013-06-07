package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Repository(value = "cancelByOADao")
public class CancelByOADao extends AbstractReportDao {

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
			sql.append("FROM ");
			sql.append("( ");
		}
		
		sql.append("SELECT (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
		sql.append("            THEN 'CC' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("            THEN 'PL' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'N')  ");
		sql.append("            THEN 'BD' ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("                         WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("            THEN 'RL' ");
		sql.append("            ELSE '' ");
		sql.append("        END) AS GROUPLOAN_TYPE, ");
		sql.append("       T1.APP_VSOURCE AS V_SOURCE, ");
		sql.append("       T1.APP_NO AS APPLICATION_NO, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME, ");
		sql.append("       T1.APP_SOURCECODE AS SOURCECODE, ");
		sql.append("       T1.APP_AGENT AS AGENT, ");
		sql.append("       T1.APP_BRANCH AS BRANCH, ");
		sql.append("       TO_CHAR(T1.APP_DATETIME, 'DD/MM/YYYY') AS RECDATE, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '2C') AS CANCELDATE, ");
//		For Oracle over version 11g
//		sql.append("       (SELECT LISTAGG(MEMO_DETAIL, '/') WITHIN GROUP (ORDER BY MEMO_ID) ");
//		sql.append("        FROM   NFE_APP_MEMO ");
//		sql.append("        WHERE  MEMO_ID > 0 ");
//		sql.append("        AND    MEMO_APPNO = T1.APP_NO ");
//		sql.append("        AND    MEMO_TYPE  = 'M' ");
//		sql.append("        GROUP BY MEMO_APPNO) AS MEMO, ");
		sql.append("       (WITH MEMO AS  ");
		sql.append("            (  ");
		sql.append("                SELECT MEMO_DETAIL AS DETAIL, ");
		sql.append("                       MEMO_APPNO AS APPNO  ");
		sql.append("                FROM   NFE_APP_MEMO ");
		sql.append("                WHERE  MEMO_TYPE  = 'M' ");
		sql.append("            )  ");
		sql.append("            SELECT RTRIM(XMLAGG (XMLELEMENT(e, DETAIL||'/') ORDER BY APPNO).EXTRACT('//text()'), '/') AS MEMO_DETAIL ");
		sql.append("            FROM  MEMO   ");
		sql.append("            WHERE APPNO = T1.APP_NO ");
		sql.append("            GROUP BY APPNO  ");
		sql.append("       ) AS MEMO, ");
		sql.append("       T1.APP_VAGENT AS VAGENT, ");
		sql.append("       (SELECT STATUSTRACKING_USER ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '2C') AS DONE_BY ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("            ON T2.APPPRODUCT_ID > 0  ");
		sql.append("            AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		if (parameter != null && parameter.length > 0) {
			if (parameter[0].equals(NFEBatchConstants.BUNDLE_GROUP_LOANTYPE)) {
				sql.append("WHERE  EXISTS (SELECT 'X' ");
				sql.append("               FROM    NFE_MS_GROUPPRODUCT ");
				sql.append("               WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
				sql.append("               AND     (GROUPPRODUCT_LOANTYPE = ? ");
				sql.append("                        OR (GROUPPRODUCT_TYPE = 'B'  ");
				sql.append("                            AND GROUPPRODUCT_LOANTYPE = ?))) ");
			} else {
				sql.append("WHERE  (EXISTS (SELECT 'X' ");
				sql.append("                FROM    NFE_MS_GROUPPRODUCT G, ");
				sql.append("                        NFE_MS_PRODUCT P ");
				sql.append("                WHERE   G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
				sql.append("                AND     P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID ");
				if (parameter.length > 6) {
					sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE IN (?, ?) ");
					sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
					sql.append("                           AND G.GROUPPRODUCT_LOANTYPE IN (?, ?)))) ");
				} else {
					sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = ? ");
					sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
					sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = ?))) ");
				}
				sql.append("        OR EXISTS (SELECT 'X' ");
				sql.append("                   FROM    NFE_MS_GROUPPRODUCT ");
				sql.append("                   WHERE   GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
				if (parameter.length > 6) {
					sql.append("                   AND     (GROUPPRODUCT_LOANTYPE IN (?, ?) ");
					sql.append("                            OR (GROUPPRODUCT_TYPE = 'B'  ");
					sql.append("                                AND GROUPPRODUCT_LOANTYPE IN (?, ?))))) ");
				} else {
					sql.append("                   AND     (GROUPPRODUCT_LOANTYPE = ?");
					sql.append("                            OR (GROUPPRODUCT_TYPE = 'B'  ");
					sql.append("                                AND GROUPPRODUCT_LOANTYPE = ?)))) ");
				}
			}
		}
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '2C' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		if (parameter.length > 6) {
			sql.append("UNION ALL ");
			sql.append("SELECT (CASE  ");
			sql.append("            WHEN EXISTS (SELECT 'X'  ");
			sql.append("                         FROM   NFE_MS_GROUPPRODUCT  ");
			sql.append("                         WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT  ");
			sql.append("                         AND    GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
			sql.append("            THEN 'CC'  ");
			sql.append("            WHEN EXISTS (SELECT 'X'  ");
			sql.append("                         FROM   NFE_MS_GROUPPRODUCT  ");
			sql.append("                         WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT  ");
			sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'F')   ");
			sql.append("            THEN 'PL'  ");
			sql.append("            WHEN EXISTS (SELECT 'X'  ");
			sql.append("                         FROM   NFE_MS_GROUPPRODUCT  ");
			sql.append("                         WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT  ");
			sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'N')   ");
			sql.append("            THEN 'BD'  ");
			sql.append("            WHEN EXISTS (SELECT 'X'  ");
			sql.append("                         FROM   NFE_MS_GROUPPRODUCT  ");
			sql.append("                         WHERE  GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT  ");
			sql.append("                         AND    GROUPPRODUCT_LOANTYPE = 'R')   ");
			sql.append("            THEN 'RL'  ");
			sql.append("            ELSE ''  ");
			sql.append("        END) AS GROUPLOAN_TYPE, ");
			sql.append("       S2.APP_VSOURCE AS V_SOURCE, ");
			sql.append("       S1.APPSUP_APPNO AS APPLICATION_NO, ");
			sql.append("       S1.APPSUP_THAIFNAME || ' ' || S1.APPSUP_THAIlNAME AS THAINAME, ");
			sql.append("       S2.APP_SOURCECODE AS SOURCECODE, ");
			sql.append("       S2.APP_AGENT AS AGENT, ");
			sql.append("       S2.APP_BRANCH AS BRANCH, ");
			sql.append("       TO_CHAR(S2.APP_DATETIME, 'DD/MM/YYYY') AS RECDATE,  ");
			sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DD/MM/YYYY')  ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING  ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0  ");
			sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO  ");
			sql.append("        AND    STATUSTRACKING_STATUS = '2C') AS CANCELDATE,  ");
			sql.append("       (WITH MEMO AS   ");
			sql.append("            (   ");
			sql.append("                SELECT MEMO_DETAIL AS DETAIL,  ");
			sql.append("                       MEMO_APPNO AS APPNO   ");
			sql.append("                FROM   NFE_APP_MEMO  ");
			sql.append("                WHERE  MEMO_TYPE  = 'M'  ");
			sql.append("            )   ");
			sql.append("            SELECT RTRIM(XMLAGG (XMLELEMENT(e, DETAIL||'/') ORDER BY APPNO).EXTRACT('//text()'), '/') AS MEMO_DETAIL  ");
			sql.append("            FROM  MEMO    ");
			sql.append("            WHERE APPNO = S1.APPSUP_APPNO  ");
			sql.append("            GROUP BY APPNO   ");
			sql.append("       ) AS MEMO,  ");
			sql.append("       S2.APP_VAGENT AS VAGENT,  ");
			sql.append("       (SELECT STATUSTRACKING_USER  ");
			sql.append("        FROM   NFE_APP_STATUSTRACKING  ");
			sql.append("        WHERE  STATUSTRACKING_ID > 0  ");
			sql.append("        AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("        AND    STATUSTRACKING_STATUS = '2C') AS DONE_BY  ");
			sql.append("FROM   NFE_APP_SUPPLEMENTARY S1 ");
			sql.append("       LEFT JOIN NFE_APPLICATION S2   ");
			sql.append("            ON  S1.APPSUP_ID > 0 ");
			sql.append("            AND S2.APP_NO = S1.APPSUP_APPNO ");
			sql.append("WHERE  S1.APPSUP_APPNO <> ' ' ");
			sql.append("AND    (EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
			sql.append("                      NFE_MS_PRODUCT P ");
			sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
			sql.append("               AND    P.PRODUCT_ID = S1.APPSUP_PRODUCT ");
			sql.append("               AND    G.GROUPPRODUCT_LOANTYPE = 'C') ");
			sql.append("        OR EXISTS (SELECT 'X' ");
			sql.append("                   FROM    NFE_MS_GROUPPRODUCT ");
			sql.append("                   WHERE   GROUPPRODUCT_ID = S2.APP_GROUPPRODUCT ");
			sql.append("                   AND     GROUPPRODUCT_LOANTYPE = 'C')) ");
			sql.append("AND    EXISTS (SELECT 'X' ");
			sql.append("               FROM   NFE_APP_STATUSTRACKING ");
			sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
			sql.append("               AND    STATUSTRACKING_APPNO = S1.APPSUP_APPNO ");
			sql.append("               AND    STATUSTRACKING_STATUS = '2C' ");
			sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
			sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS')))");
			sql.append(") A ");
			sql.append("ORDER BY A.V_SOURCE, ");
			sql.append("         A.APPLICATION_NO ");
		} else {
			sql.append("ORDER BY V_SOURCE, ");
			sql.append("         APPLICATION_NO ");
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
