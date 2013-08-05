/**
 * 
 */
package co.th.ktc.nfe.batch.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.batch.dao.AbstractBatchDao;

/**
 * @author temp_dev1
 *
 */
@Repository(value = "disbursementBatchDao")
public class DisbursementDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public DisbursementDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#update(java.lang.Object[])
	 */
	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#delete(java.lang.Object[])
	 */
	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryDetail(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryDetail(Object[] parameters) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT 1 AS RECORD_TYPE, ");
		sql.append("       A.* ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       T4.APPROVE_CARDNO AS CARD_NUMBER, ");
		sql.append("       (CASE  ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                         WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_CHKBALANCETRANSFER = 'S') ");
		sql.append("            THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  FROM   NFE_CONFIGURATION ");
		sql.append("                  WHERE  CONFIGURATION_NAME = 'PL_MD_Motor_SCSTC') ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                         WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_CHKBALANCETRANSFER = 'C') ");
		sql.append("            THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  FROM   NFE_CONFIGURATION ");
		sql.append("                  WHERE  CONFIGURATION_NAME = 'PLCH_TransactionCode') ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                         WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_CHKBALANCETRANSFER = 'M') ");
		sql.append("            THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  FROM   NFE_CONFIGURATION ");
		sql.append("                  WHERE  CONFIGURATION_NAME = 'PL_MD_SCSTC') ");
		sql.append("            ELSE ' ' ");
		sql.append("        END) AS MD_SCSTC, ");
		sql.append("       LPAD(T4.APPROVE_CREDITLIMIT * 100, 10, '0') AS MD_AMT, ");
		sql.append("       RPAD((CASE  ");
		sql.append("            	 WHEN EXISTS (SELECT 'X' ");
		sql.append("                              FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                              WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                              AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                              AND    SUBPRODUCT_CHKBALANCETRANSFER = 'S') ");
		sql.append("                 THEN (SELECT B.BANK_NAME ");
		sql.append("                       FROM   NFE_MS_BANK B, ");
		sql.append("                              NFE_APP_TRANSFERLOAN TL ");
		sql.append("                       WHERE  B.BANK_ID = TL.TRANSFERLOAN_BANK ");
		sql.append("                       AND    TL.TRANSFERLOAN_ID > 0 ");
		sql.append("                       AND    TL.TRANSFERLOAN_APPNO = T3.RESOLVE_APPNO) ");
		sql.append("                 WHEN EXISTS (SELECT 'X' ");
		sql.append("                 	         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                     	     WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         	 AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         	 AND    SUBPRODUCT_CHKBALANCETRANSFER = 'C') ");
		sql.append("            	 THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  	   FROM   NFE_CONFIGURATION ");
		sql.append("                       WHERE  CONFIGURATION_NAME = 'PLCH_Titleaccount') ");
		sql.append("                 WHEN EXISTS (SELECT 'X' ");
		sql.append("                 	          FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                      	      WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                              AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                              AND    SUBPRODUCT_CHKBALANCETRANSFER = 'M') ");
		sql.append("            	 THEN (SELECT TRANSFERLOAN_ACCNO ");
		sql.append("                       FROM   NFE_APP_TRANSFERLOAN ");
		sql.append("                       WHERE  TRANSFERLOAN_ID > 0 ");
		sql.append("                       AND    TRANSFERLOAN_APPNO = T3.RESOLVE_APPNO) ");
		sql.append("            	 ELSE ' ' ");
		sql.append("        	  END), 32, ' ') AS MD_DESC, ");
		sql.append("       RPAD(' ', 18, ' ') AS LODGEMENT_REFERENCE, ");
		sql.append("       RPAD(' ', 6, ' ') AS TRACE_BSB, ");
		sql.append("       RPAD(' ', 9, ' ') AS TRACE_ACCOUNT, ");
		sql.append("       RPAD(' ', 16, ' ') AS NAME_OF_REMITTER, ");
		sql.append("       (CASE  ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                         WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_CHKBALANCETRANSFER = 'S') ");
		sql.append("            THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  FROM   NFE_CONFIGURATION ");
		sql.append("                  WHERE  CONFIGURATION_NAME = 'ReasonCode_Motor') ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                         WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_CHKBALANCETRANSFER = 'C') ");
		sql.append("            THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  FROM   NFE_CONFIGURATION ");
		sql.append("                  WHERE  CONFIGURATION_NAME = 'PLCH_Reasoncode') ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                         WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("                         AND    SUBPRODUCT_CHKBALANCETRANSFER = 'M') ");
		sql.append("            THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                  FROM   NFE_CONFIGURATION ");
		sql.append("                  WHERE  CONFIGURATION_NAME = 'PL_Reason') ");
		sql.append("            ELSE ' ' ");
		sql.append("        END) AS REASON_CODE, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'YYYYMMDD') FROM NFE_SETDATE) AS PROCESSING_DATE ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CHKBALANCETRANSFER IN ('S', 'C', 'M')) ");
		sql.append("UNION ALL ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       T4.APPROVE_CARDNO AS CARD_NUMBER, ");
		sql.append("       RPAD((SELECT CONFIGURATION_VALUE ");
		sql.append("             FROM   NFE_CONFIGURATION ");
		sql.append("             WHERE  CONFIGURATION_NAME = 'RL_MD_SCSTC') ");
		sql.append("            , 2, ' ') AS MD_SCSTC, ");
		sql.append("       LPAD(T4.APPROVE_CASHADVANCE * 100, 10, '0') AS MD_AMT, ");
		sql.append("       RPAD((SELECT CONFIGURATION_VALUE ");
		sql.append("             FROM   NFE_CONFIGURATION ");
		sql.append("             WHERE  CONFIGURATION_NAME = 'RL_MD_Desc'), 32, ' ') AS MD_DESC, ");
		sql.append("       RPAD(' ', 18, ' ') AS LODGEMENT_REFERENCE, ");
		sql.append("       RPAD(' ', 6, ' ') AS TRACE_BSB, ");
		sql.append("       RPAD(' ', 9, ' ') AS TRACE_ACCOUNT, ");
		sql.append("       RPAD(' ', 16, ' ') AS NAME_OF_REMITTER, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'ReasonCode1st') AS REASON_CODE, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'YYYYMMDD') FROM NFE_SETDATE) AS PROCESSING_DATE ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       T4.APPROVE_CARDNO AS CARD_NUMBER, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'CBF_SCS_TC') AS MD_SCSTC, ");
		sql.append("       LPAD(((SELECT TO_NUMBER(CONFIGURATION_VALUE) ");
		sql.append("              FROM   NFE_CONFIGURATION ");
		sql.append("              WHERE  CONFIGURATION_NAME = 'CBF_Amt') * 100) ");
		sql.append("       , 10, '0') AS MD_AMT, ");
		sql.append("       RPAD((SELECT CONFIGURATION_VALUE ");
		sql.append("             FROM   NFE_CONFIGURATION ");
		sql.append("             WHERE  CONFIGURATION_NAME = 'CBF_Desc'), 32, ' ') AS MD_DESC, ");
		sql.append("       RPAD(' ', 18, ' ') AS LODGEMENT_REFERENCE, ");
		sql.append("       RPAD(' ', 6, ' ') AS TRACE_BSB, ");
		sql.append("       RPAD(' ', 9, ' ') AS TRACE_ACCOUNT, ");
		sql.append("       RPAD(' ', 16, ' ') AS NAME_OF_REMITTER, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'ReasonCode2nd') AS REASON_CODE, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'YYYYMMDD') FROM NFE_SETDATE) AS PROCESSING_DATE ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       T4.APPROVE_CARDNO AS CARD_NUMBER, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'DTS_SCSTC') AS MD_SCSTC, ");
		sql.append("       LPAD((NVL(T4.APPROVE_CREDITLIMIT * (0.0005), 00.00) * 100), 10, '0') AS MD_AMT, ");
		sql.append("       RPAD((SELECT CONFIGURATION_VALUE ");
		sql.append("             FROM   NFE_CONFIGURATION ");
		sql.append("             WHERE  CONFIGURATION_NAME = 'DTS_Desc'), 32, ' ') AS MD_DESC, ");
		sql.append("       RPAD(' ', 18, ' ') AS LODGEMENT_REFERENCE, ");
		sql.append("       RPAD(' ', 6, ' ') AS TRACE_BSB, ");
		sql.append("       RPAD(' ', 9, ' ') AS TRACE_ACCOUNT, ");
		sql.append("       RPAD(' ', 16, ' ') AS NAME_OF_REMITTER, ");
		sql.append("       (SELECT CONFIGURATION_VALUE ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'ReasonCode3rd') AS REASON_CODE, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'YYYYMMDD') FROM NFE_SETDATE) AS PROCESSING_DATE ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append(") A ");
		sql.append("WHERE  NOT EXISTS(SELECT 'X' ");
		sql.append("                  FROM   NFE_G_MONTRAN ");
		sql.append("                  WHERE  MONTRAN_ID > 0 ");
		sql.append("                  AND    APP_NO = A.APP_NO) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = A.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("ORDER BY CARD_NUMBER, MD_DESC ");


		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameters);
		
		return sqlRowSet;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryHeader(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryHeader(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT 0 AS RECORD_TYPE, ");
		sql.append("       RPAD(' ', 17, ' ') AS S1, ");
		sql.append("       '01' AS H1, ");
		sql.append("       'KTC' AS H2, ");
		sql.append("       'APS' AS H3, ");
		sql.append("       RPAD(' ', 4, ' ') AS S2, ");
		sql.append("       RPAD('KTC', 26, ' ') AS H4, ");
		sql.append("       '000000' AS H5, ");
		sql.append("       RPAD('APS', 12, ' ') AS H6, ");
		sql.append("       TO_CHAR(NFE_CURRENTDATE, 'DDMMYY') AS H7, ");
		sql.append("       RPAD(' ', 15, ' ') AS S3, ");
		sql.append("       RPAD(' ', 25, ' ') AS S4 ");
		sql.append("FROM NFE_SETDATE ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString());
		
		return sqlRowSet;
	}

	@Override
	public SqlRowSet queryTrailer(Object[] parameters) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT 7 AS RECORD_TYPE, ");
		sql.append("       '999999' AS BSB_CODE, ");
		sql.append("       RPAD(' ', 12, ' ') AS S1, ");
		sql.append("       SUM(AMOUNT) * 100 AS TOTAL_AMOUNT, ");
		sql.append("       '0000000000' AS TOTAL_CREDIT_AMOUNT, ");
		sql.append("       RPAD(' ', 24, ' ') AS S2, ");
		sql.append("       LPAD(COUNT(1), 6, '0') AS TOTAL_RECORD, ");
		sql.append("       RPAD(' ', 41, ' ') AS S3 ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       NVL(T4.APPROVE_CASHADVANCE, 00.00) AS AMOUNT ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       (SELECT TO_NUMBER(CONFIGURATION_VALUE) ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'CBF_Amt') AS AMOUNT ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT * (0.0005), 00.00) AS AMOUNT ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'R' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'R'))) ");
		sql.append("UNION ALL ");
		sql.append("SELECT T4.APPROVE_APPNO AS APP_NO, ");
		sql.append("       NVL(T4.APPROVE_CREDITLIMIT, 00.00) AS AMOUNT ");
		sql.append("FROM   NFE_APP_RESOLVE T3 ");
		sql.append("       INNER JOIN NFE_APP_APPROVE T4  ");
		sql.append("           ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T3.RESOLVE_ID > 0 ");
		sql.append("AND    T3.RESOLVE_APPNO <> ' ' ");
		sql.append("AND    T3.RESOLVE_APPPRODUCTID > 0 ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    T4.APPROVE_APPNO <> ' ' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT G,  ");
		sql.append("                      NFE_MS_PRODUCT P ");
		sql.append("               WHERE  G.GROUPPRODUCT_ID = P.PRODUCT_GROUPPRODUCTID ");
		sql.append("               AND    P.PRODUCT_ID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    (G.GROUPPRODUCT_LOANTYPE = 'F' ");
		sql.append("                       OR (G.GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND G.GROUPPRODUCT_LOANTYPE = 'F'))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CHKBALANCETRANSFER IN ('S', 'C', 'M')) ");
		sql.append(") A ");
		sql.append("WHERE  NOT EXISTS(SELECT 'X' ");
		sql.append("                  FROM   NFE_G_MONTRAN ");
		sql.append("                  WHERE  MONTRAN_ID > 0 ");
		sql.append("                  AND    APP_NO = A.APP_NO) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = A.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameters);
		
		return sqlRowSet;
	}
		
	public Integer size(Object[] parameter) {
		
		StringBuilder sql = new StringBuilder();
		
		return getJdbcTemplate().queryForInt(sql.toString(), parameter);
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#success(java.lang.Object[])
	 */
	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#fail(java.lang.Object[])
	 */
	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

}
