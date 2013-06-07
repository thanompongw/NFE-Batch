package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Repository(value = "staffInterestRateDao")
public class StaffInterestRateDao extends AbstractReportDao {

	public StaffInterestRateDao() {
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
		sql.append("SELECT T1.APP_CARDNO AS CARDACCOUNT_NO, ");
		sql.append("       T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME AS NAMES, ");
		sql.append("       T1.APP_CITIZENID AS CITIZEN_ID, ");
		sql.append("       (SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'DDMMYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS OPENDATE, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT, '00.00') AS CREDIT_LIMIT, ");
		sql.append("       NVL(T5.DTA_YEARLYINCOME, '00.00') AS ANNUAL_INCOME, ");
		sql.append("       (SELECT CUSTOMERTYPE_DESCRIPTION ");
		sql.append("        FROM   NFE_MS_CUSTOMERTYPE  ");
		sql.append("        WHERE  CUSTOMERTYPE_ID = T5.DTA_CUSTOMERTYPE) AS CUSTOMER_TYPE, ");
		sql.append("       TO_CHAR(T1.APP_UPDATEDATE, 'DDMMYY') AS LAST_MAINTAIN_DATE, ");
		sql.append("       T1.APP_ANALYST AS MAINTAIN_OPER_ID ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("       LEFT JOIN NFE_APP_DATAANALYSIS T5  ");
		sql.append("           ON T5.DTA_APPNO = T1.APP_NO ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("               WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("               AND    GROUPPRODUCT_LOANTYPE = 'C' ");
		sql.append("               AND    GROUPPRODUCT_TYPE = 'M') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_CUSTOMERTYPE  ");
		sql.append("               WHERE  CUSTOMERTYPE_ID = T5.DTA_CUSTOMERTYPE ");
		sql.append("               AND    ((CUSTOMERTYPE_CODE IN ('D', 'B', 'K')  ");
		sql.append("                        AND T5.DTA_STAFFRATEFLAG = 'N') ");
		sql.append("                       OR (CUSTOMERTYPE_CODE NOT IN ('D', 'B', 'K')  ");
		sql.append("                            AND T5.DTA_STAFFRATEFLAG = 'Y')) ");
		sql.append("              ) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		
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
