package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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
		
		sql.append(" SELECT (CASE  ");
		sql.append("                    WHEN EXISTS  ");
		sql.append("                            (SELECT 'X'  ");
		sql.append("                               FROM NFE_MS_GROUPPRODUCT  ");
		sql.append("                              WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                                    AND GROUPPRODUCT_LOANTYPE IN ('C', 'B'))  ");
		sql.append("                    THEN  ");
		sql.append("                       'CC'  ");
		sql.append("                    WHEN EXISTS  ");
		sql.append("                            (SELECT 'X'  ");
		sql.append("                               FROM NFE_MS_GROUPPRODUCT  ");
		sql.append("                              WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                                    AND GROUPPRODUCT_LOANTYPE = 'F')  ");
		sql.append("                    THEN  ");
		sql.append("                       'PL'  ");
		sql.append("                    WHEN EXISTS  ");
		sql.append("                            (SELECT 'X'  ");
		sql.append("                               FROM NFE_MS_GROUPPRODUCT  ");
		sql.append("                              WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                                    AND GROUPPRODUCT_LOANTYPE = 'N')  ");
		sql.append("                    THEN  ");
		sql.append("                       'BD'  ");
		sql.append("                    WHEN EXISTS  ");
		sql.append("                            (SELECT 'X'  ");
		sql.append("                               FROM NFE_MS_GROUPPRODUCT  ");
		sql.append("                              WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
		sql.append("                                    AND GROUPPRODUCT_LOANTYPE = 'R')  ");
		sql.append("                    THEN  ");
		sql.append("                       'RL'  ");
		sql.append("                    ELSE  ");
		sql.append("                       ' '  ");
		sql.append("                 END)  ");
		sql.append("                   AS GROUPLOAN_TYPE,  ");
		sql.append("                T1.APP_VSOURCE AS V_SOURCE,  ");
		sql.append("                T1.APP_NO AS APPLICATION_NO,  ");
		sql.append("                T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS THAINAME,  ");
		sql.append("                T1.APP_SOURCECODE AS SOURCECODE,  ");
		sql.append("                T1.APP_AGENT AS AGENT,  ");
		sql.append("                T1.APP_BRANCH AS BRANCH,  ");
		sql.append("                TO_CHAR (T1.APP_DATETIME, 'dd/MM/yyyy') AS RECDATE,  ");
		sql.append("                TO_CHAR (T4.STATUSTRACKING_STARTTIME, 'dd/MM/yyyy') AS CANCELDATE,  ");
		sql.append("                T5.MEMO_DETAIL AS MEMO,  ");
		sql.append("                T1.APP_VAGENT AS VAGENT,  ");
		sql.append("                T4.STATUSTRACKING_USER AS DONE_BY  ");
		sql.append("                 ");
		sql.append("                FROM NFE_APPLICATION T1  ");
		sql.append("                  LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("                     ON T2.APPPRODUCT_ID > 0   ");
		sql.append("                     AND T2.APPPRODUCT_APPNO = T1.APP_NO  ");
		sql.append("                  LEFT JOIN NFE_APP_STATUSTRACKING T4  ");
		sql.append("                     ON T4.STATUSTRACKING_APPNO = T1.APP_NO  ");
		sql.append("                     AND T4.STATUSTRACKING_STATUS = '2C'  ");
		sql.append("                  LEFT JOIN NFE_APP_MEMO T5  ");
		sql.append("                     ON T5.MEMO_APPNO = T1.APP_NO ");
		if (true) {
			sql.append(" WHERE  (EXISTS  ");
			sql.append("                      (SELECT 'X'  ");
			sql.append("                        FROM NFE_MS_GROUPPRODUCT G, NFE_MS_PRODUCT P  ");
			sql.append("                        WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID  ");
			sql.append("                              AND P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID  ");
			sql.append("                              AND G.GROUPPRODUCT_LOANTYPE = ?  ");
			sql.append("                              AND G.GROUPPRODUCT_TYPE = 'B'))  ");
			sql.append("                OR (EXISTS  ");
			sql.append("                      (SELECT 'X'  ");
			sql.append("                        FROM NFE_MS_GROUPPRODUCT  ");
			sql.append("                        WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
			sql.append("                              AND GROUPPRODUCT_LOANTYPE = ?  ");
			sql.append("                              AND GROUPPRODUCT_TYPE = 'B'))  ");

		} else {
			sql.append(" WHERE  (EXISTS  ");
			sql.append("                   	  (SELECT 'X'  ");
			sql.append("                         FROM NFE_MS_GROUPPRODUCT G, NFE_MS_PRODUCT P  ");
			sql.append("                         WHERE G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID  ");
			sql.append("                              AND P.PRODUCT_ID = T2.APPPRODUCT_PRODUCTID  ");
			sql.append("                              AND (G.GROUPPRODUCT_LOANTYPE IN (?, ?)  ");
			sql.append("                                   OR (G.GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                                   AND G.GROUPPRODUCT_LOANTYPE IN (?, ?))))  ");
			sql.append("                OR EXISTS  ");
			sql.append("                      (SELECT 'X'  ");
			sql.append("                         FROM NFE_MS_GROUPPRODUCT  ");
			sql.append("                         WHERE GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT  ");
			sql.append("                              AND (GROUPPRODUCT_LOANTYPE IN (?, 'B')  ");
			sql.append("                                    OR GROUPPRODUCT_TYPE = 'B'  ");
			sql.append("                                    AND GROUPPRODUCT_LOANTYPE IN (?, 'B'))))  ");
		}
		sql.append("       and (T4.STATUSTRACKING_ENDTIME  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')  ");
		sql.append("       AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss'))  ");

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
