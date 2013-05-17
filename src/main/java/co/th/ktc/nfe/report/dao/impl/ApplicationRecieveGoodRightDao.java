package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Service("applicationRecieveGoodRightDao")
public class ApplicationRecieveGoodRightDao extends AbstractReportDao {

	public ApplicationRecieveGoodRightDao() {
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
		
		sql.append("SELECT  TO_CHAR(APP_DATETIME, 'DD/MM/YYYY') AS APP_DATETIME, ");
		sql.append("        (CASE ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                             WHERE GROUPPRODUCT_ID = APP_GROUPPRODUCT ");
		sql.append("                             AND   GROUPPRODUCT_LOANTYPE IN ('C', 'B')) ");
		sql.append("                THEN 'CC'  ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                             WHERE GROUPPRODUCT_ID = APP_GROUPPRODUCT ");
		sql.append("                             AND   GROUPPRODUCT_LOANTYPE = 'F') ");
		sql.append("                THEN 'PL' ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                             WHERE GROUPPRODUCT_ID = APP_GROUPPRODUCT ");
		sql.append("                             AND   GROUPPRODUCT_LOANTYPE = 'N') ");
		sql.append("                THEN 'BD' ");
		sql.append("                WHEN EXISTS (SELECT 'X' ");
		sql.append("                             FROM NFE_MS_GROUPPRODUCT ");
		sql.append("                             WHERE GROUPPRODUCT_ID = APP_GROUPPRODUCT ");
		sql.append("                             AND   GROUPPRODUCT_LOANTYPE = 'R') ");
		sql.append("                THEN 'RL' ");
		sql.append("                ELSE ' ' ");
		sql.append("            END) AS GROUPLOAN_TYPE, ");
		sql.append("        (SELECT GROUPPRODUCT_TYPE ");
		sql.append("        FROM NFE_MS_GROUPPRODUCT ");
		sql.append("        WHERE GROUPPRODUCT_ID = APP_GROUPPRODUCT) AS GROUPPRODUCT_TYPE, ");
		sql.append("        APP_VSOURCE, ");
		sql.append("        APP_NO, ");
		sql.append("        APP_THAIFNAME, ");
		sql.append("        APP_THAILNAME, ");
		sql.append("        APP_THAIFNAME || ' ' || APP_THAILNAME AS FULL_THAINAME, ");
		sql.append("        APP_CITIZENID, ");
		sql.append("        APP_CHKNCB  ");
		sql.append("FROM NFE_APPLICATION ");
		sql.append("WHERE APP_NO <> ' '  ");
		sql.append("AND   (APP_DATETIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:mi:ss') ");
		sql.append("                    AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:mi:ss')) ");
		sql.append("ORDER BY APP_NO ");
		
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
