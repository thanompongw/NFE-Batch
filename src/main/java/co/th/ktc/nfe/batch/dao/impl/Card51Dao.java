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
@Repository(value = "card51Dao")
public class Card51Dao extends AbstractBatchDao {

	/**
	 * 
	 */
	public Card51Dao() {
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
	public SqlRowSet queryDetail(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT   'A' AS CARD_STATUS, ");
		sql.append("         RPAD(T1.APP_CITIZENID, 16, ' ') AS CID, ");
		sql.append("         RPAD(T1.APP_CITIZENID, 25, ' ') AS CID1, ");
		sql.append("         RPAD(' ', 36, ' ')  AS S, ");
		sql.append("         RPAD(T4.APPROVE_CARDNO, 16, ' ') AS ACC_NO, ");
		sql.append("         RPAD((SELECT TO_CHAR(NFE_CURRENTDATE, 'DDMMYY')  ");
		sql.append("               FROM NFE_SETDATE), 8, ' ') AS OPEN_DATE, ");
		sql.append("         RPAD((CASE ");
		sql.append("                   WHEN T3.RESOLVE_CREDITTYPE = 'M'  ");
		sql.append("                   THEN T1.APP_ENGFNAME || ' ' || T1.APP_ENGLNAME ");
		sql.append("                   ELSE T5.APPSUP_ENGFNAME || ' ' || T5.APPSUP_ENGLNAME ");
		sql.append("               END), 90, ' ') AS ENG_NAME, ");
		sql.append("         RPAD((CASE ");
		sql.append("                   WHEN T3.RESOLVE_CREDITTYPE = 'M'  ");
		sql.append("                   THEN T1.APP_THAIFNAME || ' ' || T1.APP_THAILNAME ");
		sql.append("                   ELSE T5.APPSUP_THAIFNAME || ' ' || T5.APPSUP_THAILNAME ");
		sql.append("               END), 90, ' ') AS THAI_NAME, ");
		sql.append("        RPAD(' ', 17, ' ') AS S1, ");
		sql.append("        'A1' AS TYPED, ");
		sql.append("        RPAD((SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
		sql.append("              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("              AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("              AND    STATUSTRACKING_STATUS = '8F'), 8, ' ') AS ISSUE_DATE, ");
		sql.append("        RPAD(' ', 8, ' ') AS ACTV_DATE, ");
		sql.append("        RPAD(' ', 2, ' ') AS ACTV_STATUS, ");
		sql.append("        RPAD((SELECT TO_CHAR(STATUSTRACKING_ENDTIME, 'YYYYMMDD') ");
		sql.append("              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("              AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("              AND    STATUSTRACKING_STATUS = '8F'), 8, ' ') AS CURR_EXPIRY_DATE, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT PRODUCT_CODE ");
		sql.append("                               FROM   NFE_MS_PRODUCT ");
		sql.append("                               WHERE  PRODUCT_ID = T2.APPPRODUCT_PRODUCTID  ");
		sql.append("                               AND    PRODUCT_GROUPPRODUCTID > 0 ");
		sql.append("                               AND    PRODUCT_CODE = 'MSW')  ");
		sql.append("                  THEN (SELECT CONFIGURATION_VALUE ");
		sql.append("                        FROM   NFE_CONFIGURATION ");
		sql.append("                        WHERE  CONFIGURATION_ID = 1048) ");
		sql.append("                  ELSE (SELECT CONFIGURATION_VALUE ");
		sql.append("                        FROM   NFE_CONFIGURATION  ");
		sql.append("                        WHERE  CONFIGURATION_ID = 1056) ");
		sql.append("              END), 3, ' ') AS PRODUCT_CODE, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("                               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("                               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("                               AND    SUBPRODUCT_CODE IN ('W01', 'W02')) ");
		sql.append("                  THEN 'W01' ");
		sql.append("                  ELSE 'I01' ");
		sql.append("              END), 3, ' ') AS SUBPRODUCT_CODE, ");
		sql.append("        RPAD(T4.APPROVE_EMBOSSNAME1, 30, ' ') AS EMBOSSLINE1, ");
		sql.append("        RPAD(T4.APPROVE_EMBOSSNAME2, 30, ' ') AS EMBOSSLINE2, ");
		sql.append("        RPAD((CASE  ");
		sql.append("                  WHEN T3.RESOLVE_CREDITTYPE = 'M'  ");
		sql.append("                  THEN T1.APP_SEX  ");
		sql.append("                  ELSE T5.APPSUP_SEX  ");
		sql.append("              END), 1, ' ') AS GENDER, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN T3.RESOLVE_CREDITTYPE = 'M'  ");
		sql.append("                  THEN TO_CHAR(T1.APP_DOB, 'YYYYMMDD') ");
		sql.append("                  ELSE TO_CHAR(T5.APPSUP_DOB, 'YYYYMMDD') ");
		sql.append("              END), 8, ' ') AS DATE_OF_BIRTH, ");
		sql.append("        RPAD(' ', 1, ' ') AS CARD_STATUS1, ");
		sql.append("        RPAD(' ', 46, ' ') AS S4, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT PIF_CURRENTADDR_LINE1 ");
		sql.append("                        FROM   NFE_APP_PIF ");
		sql.append("                        WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT APPOCCUPATION_ADDRLINE1 ");
		sql.append("                        FROM   NFE_APP_OCCUPATION  ");
		sql.append("                        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), 50, ' ') AS ADDRESS1, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT PIF_CURRENTADDR_LINE2 ");
		sql.append("                        FROM   NFE_APP_PIF ");
		sql.append("                        WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT APPOCCUPATION_ADDRLINE2 ");
		sql.append("                        FROM   NFE_APP_OCCUPATION  ");
		sql.append("                        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), 50, ' ') AS ADDRESS2, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1)  ");
		sql.append("                  THEN ((SELECT D.DISTRICT_TNAME ");
		sql.append("                         FROM   NFE_MS_DISTRICT D, ");
		sql.append("                                NFE_APP_PIF P ");
		sql.append("                         WHERE  D.DISTRICT_ID = P.PIF_CURRENTADDR_DISTRICT ");
		sql.append("                         AND    P.PIF_APPNO = T1.APP_NO) ");
		sql.append("                      || '  ' ");
		sql.append("                      || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                          FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                 NFE_APP_PIF P ");
		sql.append("                          WHERE  S.SUBPROVINCE_ID = P.PIF_CURRENTADDR_AMPHUR ");
		sql.append("                          AND    P.PIF_APPNO = T1.APP_NO)) ");
		sql.append("                  ELSE ((SELECT D.DISTRICT_TNAME ");
		sql.append("                         FROM   NFE_MS_DISTRICT D, ");
		sql.append("                                NFE_APP_OCCUPATION O ");
		sql.append("                         WHERE  DISTRICT_ID = O.APPOCCUPATION_DISTRICT ");
		sql.append("                         AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("                       || '  ' ");
		sql.append("                       || (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("                           FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("                                  NFE_APP_PIF P ");
		sql.append("                           WHERE  S.SUBPROVINCE_ID = P.PIF_CENSUSADDR_AMPHUR ");
		sql.append("                           AND    P.PIF_APPNO = T1.APP_NO)) ");
		sql.append("              END), 50, ' ') AS ADDRESS3, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT P.PROVINCE_TNAME ");
		sql.append("                        FROM   NFE_MS_PROVINCE P, ");
		sql.append("                               NFE_APP_PIF AP ");
		sql.append("                        WHERE  P.PROVINCE_ID = AP.PIF_CURRENTADDR_PROVINCE ");
		sql.append("                        AND    AP.PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT P.PROVINCE_TNAME ");
		sql.append("                        FROM   NFE_MS_PROVINCE P, ");
		sql.append("                               NFE_APP_OCCUPATION O ");
		sql.append("                        WHERE  P.PROVINCE_ID = O.APPOCCUPATION_PROVINCE ");
		sql.append("                        AND    O.APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), 50, ' ') AS ADDRESS4, ");
		sql.append("        RPAD(' ', 50, ' ') AS S5, ");
		sql.append("        RPAD((CASE ");
		sql.append("                  WHEN EXISTS (SELECT 'X' ");
		sql.append("                               FROM  NFE_APP_OCCUPATION  ");
		sql.append("                               WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                               AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("                  THEN (SELECT PIF_CURRENTADDR_ZIPCODE ");
		sql.append("                        FROM   NFE_APP_PIF ");
		sql.append("                        WHERE  PIF_APPNO = T1.APP_NO) ");
		sql.append("                  ELSE (SELECT APPOCCUPATION_ZIPCODE ");
		sql.append("                        FROM   NFE_APP_OCCUPATION  ");
		sql.append("                        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) ");
		sql.append("              END), 5, ' ') AS ZIPCODE, ");
		sql.append("        RPAD(NVL((SELECT PIF_CENSUSADDR_LINE1 ");
		sql.append("                  FROM   NFE_APP_PIF ");
		sql.append("                  WHERE  PIF_APPNO = T1.APP_NO), ' '), 50, ' ') AS PRIMARY_ADDR1, ");
		sql.append("        RPAD(NVL((SELECT PIF_CENSUSADDR_LINE2 ");
		sql.append("                  FROM   NFE_APP_PIF ");
		sql.append("                  WHERE  PIF_APPNO = T1.APP_NO), ' '), 50, ' ') AS PRIMARY_ADDR2, ");
		sql.append("        RPAD(NVL((SELECT D.DISTRICT_TNAME || '  ' || S.SUBPROVINCE_TNAME ");
		sql.append("                  FROM   NFE_MS_DISTRICT D, ");
		sql.append("                         NFE_MS_SUBPROVINCE S, ");
		sql.append("                         NFE_APP_PIF P ");
		sql.append("                  WHERE  D.DISTRICT_SUBPROVINCEID = S.SUBPROVINCE_ID ");
		sql.append("                  AND    D.DISTRICT_ID = P.PIF_CENSUSADDR_DISTRICT ");
		sql.append("                  AND    S.SUBPROVINCE_ID = P.PIF_CENSUSADDR_AMPHUR ");
		sql.append("                  AND    P.PIF_APPNO = T1.APP_NO), ' '), 50, ' ') AS PRIMARY_ADDR3, ");
		sql.append("        RPAD(NVL((SELECT P.PROVINCE_TNAME ");
		sql.append("                  FROM   NFE_MS_PROVINCE P, ");
		sql.append("                         NFE_APP_PIF AP ");
		sql.append("                  WHERE  AP.PIF_CENSUSADDR_PROVINCE = P.PROVINCE_ID ");
		sql.append("                  AND    AP.PIF_APPNO = T1.APP_NO), ' '), 50, ' ') AS PROVINCE, ");
		sql.append("        RPAD(NVL((SELECT TO_CHAR(SUBSTR(PIF_CENSUSADDR_ZIPCODE, 1, 2)) ");
		sql.append("                  FROM   NFE_APP_PIF ");
		sql.append("                  WHERE  PIF_APPNO = T1.APP_NO), ' '), 3, ' ') AS PRIMARY_STATE, ");
		sql.append("        RPAD(NVL((SELECT PIF_CENSUSADDR_ZIPCODE ");
		sql.append("                  FROM   NFE_APP_PIF ");
		sql.append("                  WHERE  PIF_APPNO = T1.APP_NO), ' '), 5, ' ') AS PRIMARY_ZIP_CODE, ");
		sql.append("        RPAD(' ', 40, ' ') AS OFFICE_BUILD, ");
		sql.append("        RPAD(NVL((SELECT APPOCCUPATION_ADDRLINE1 ");
		sql.append("                  FROM   NFE_APP_OCCUPATION  ");
		sql.append("                  WHERE  APPOCCUPATION_APPNO = T1.APP_NO), ' '), 50, ' ') AS OFFICE_ADDR1, ");
		sql.append("        RPAD(NVL((SELECT NVL(APPOCCUPATION_ADDRLINE2, ' ') ");
		sql.append("                  FROM   NFE_APP_OCCUPATION  ");
		sql.append("                  WHERE  APPOCCUPATION_APPNO = T1.APP_NO), ' '), 50, ' ') AS OFFICE_ADDR2, ");
		sql.append("        RPAD(NVL((SELECT D.DISTRICT_TNAME || ' ' || S.SUBPROVINCE_TNAME ");
		sql.append("                  FROM   NFE_MS_DISTRICT D, ");
		sql.append("                         NFE_MS_SUBPROVINCE S, ");
		sql.append("                         NFE_APP_OCCUPATION O ");
		sql.append("                  WHERE  D.DISTRICT_SUBPROVINCEID = S.SUBPROVINCE_ID ");
		sql.append("                  AND    D.DISTRICT_ID = O.APPOCCUPATION_DISTRICT ");
		sql.append("                  AND    S.SUBPROVINCE_ID = O.APPOCCUPATION_AMPHUR ");
		sql.append("                  AND    O.APPOCCUPATION_APPNO = T1.APP_NO), ' '), 50, ' ') AS OFFICE_ADDR3, ");
		sql.append("        RPAD(NVL((SELECT NVL(P.PROVINCE_TNAME, ' ') ");
		sql.append("                  FROM   NFE_MS_PROVINCE P, ");
		sql.append("                         NFE_APP_OCCUPATION O ");
		sql.append("                  WHERE  O.APPOCCUPATION_PROVINCE = P.PROVINCE_ID ");
		sql.append("                  AND    O.APPOCCUPATION_APPNO = T1.APP_NO), ' '), 50, ' ') AS OFFICE_PROVINCE, ");
		sql.append("        RPAD(NVL((SELECT NVL(APPOCCUPATION_ZIPCODE, ' ') ");
		sql.append("                  FROM   NFE_APP_OCCUPATION  ");
		sql.append("                  WHERE  APPOCCUPATION_APPNO = T1.APP_NO), ' '), 5, ' ') AS OFFICE_ZIP_CODE, ");
		sql.append("        LPAD(NVL((SELECT PIF_MARRIAGE_NOCHILDREN ");
		sql.append("                  FROM   NFE_APP_PIF ");
		sql.append("                  WHERE  PIF_APPNO = T1.APP_NO), 0), 2, '0') AS NO_OF_CHILD, ");
		sql.append("        RPAD(NVL((SELECT PIF_MARRIAGE_FNAME || ' ' || PIF_MARRIAGE_LNAME ");
		sql.append("                  FROM   NFE_APP_PIF ");
		sql.append("                  WHERE  PIF_APPNO = T1.APP_NO), ' '), 160, ' ') AS SPOUSE_NAME, ");
		sql.append("        RPAD(T1.APP_NO, 20, ' ') AS APPLY_NO, ");
		sql.append("        RPAD(T1.APP_SOURCECODE, 6, ' ') AS SOURCE_CODE, ");
		sql.append("        RPAD(T1.APP_AGENT, 20, ' ') AS AGEN_CODE, ");
		sql.append("        RPAD(T1.APP_BRANCH, 3, ' ') AS BRANCH_CODE, ");
		sql.append("        RPAD(NVL((SELECT A.ADDRTYPE_TNAME ");
		sql.append("                  FROM   NFE_MS_ADDRTYPE A, ");
		sql.append("                         NFE_APP_PIF P ");
		sql.append("                  WHERE  A.ADDRTYPE_ID = P.PIF_ADDRTYPE_TYPE ");
		sql.append("                  AND    P.PIF_APPNO = T1.APP_NO), ' '), 100, ' ') AS HOME_TYPE, ");
		sql.append("        RPAD(' ', 100, ' ') AS HOME_STATUS, ");
		sql.append("        RPAD(' ', 25, ' ') AS PHONE, ");
		sql.append("        RPAD(' ', 25, ' ') AS FAX, ");
		sql.append("        RPAD((SELECT NVL(PIF_CURRENTADDR_MOBILENO, ' ') ");
		sql.append("              FROM   NFE_APP_PIF ");
		sql.append("              WHERE  PIF_APPNO = T1.APP_NO), 16, ' ')  AS MOBILE, ");
		sql.append("        RPAD((SELECT NVL(APPOCCUPATION_PHONENO1, ' ') ");
		sql.append("              FROM   NFE_APP_OCCUPATION  ");
		sql.append("              WHERE  APPOCCUPATION_APPNO = T1.APP_NO), 25, ' ') AS OFFICE_PHONE1, ");
		sql.append("        RPAD((SELECT NVL(APPOCCUPATION_PHONENO2, ' ') ");
		sql.append("              FROM   NFE_APP_OCCUPATION  ");
		sql.append("              WHERE  APPOCCUPATION_APPNO = T1.APP_NO), 25, ' ') AS OFFICE_PHONE2, ");
		sql.append("        RPAD(' ', 25, ' ') AS OFFICE_FAX, ");
		sql.append("        RPAD(' ', 50, ' ') AS WWW, ");
		sql.append("        RPAD((SELECT NVL(PIF_CURRENTADDR_EMAIL, ' ')  ");
		sql.append("              FROM   NFE_APP_PIF ");
		sql.append("              WHERE  PIF_APPNO = T1.APP_NO), 80, ' ') AS EMAIL, ");
		sql.append("        RPAD(' ', 100, ' ') AS OCCUPATION, ");
		sql.append("        RPAD(' ', 100, ' ') AS STATUS, ");
		sql.append("        RPAD(' ', 50, ' ') AS UNIVERSITY, ");
		sql.append("        RPAD(' ', 30, ' ') AS MAJOR, ");
		sql.append("        RPAD(' ', 200, ' ') AS U_ADRESS, ");
		sql.append("        RPAD((SELECT NVL(E.EDUDEGREE_TNAME, ' ') ");
		sql.append("              FROM   NFE_MS_EDUDEGREE E, ");
		sql.append("                     NFE_APP_PIF P ");
		sql.append("              WHERE  E.EDUDEGREE_ID = P.PIF_OTHERS_DEGREE ");
		sql.append("              AND    P.PIF_APPNO = T1.APP_NO), 100, ' ') AS DEGREES, ");
		sql.append("        RPAD((SELECT NVL(APPOCCUPATION_WORKPLACE, ' ') ");
		sql.append("              FROM   NFE_APP_OCCUPATION  ");
		sql.append("              WHERE  APPOCCUPATION_APPNO = T1.APP_NO), 120, ' ') AS COMPANY_NAME, ");
		sql.append("        RPAD(' ', 100, ' ') AS INDUSTRIAL_SECTOR, ");
		sql.append("        RPAD(NVL((SELECT P.POSITION_TNAME ");
		sql.append("                  FROM   NFE_MS_POSITION P, ");
		sql.append("                         NFE_APP_OCCUPATION O ");
		sql.append("                  WHERE  P.POSITION_ID = O.APPOCCUPATION_POSITION ");
		sql.append("                  AND    O.APPOCCUPATION_APPNO = T1.APP_NO), ' '), 100, ' ') AS POSITION, ");
		sql.append("        RPAD(' ', 8, ' ') AS WORK_START, ");
		sql.append("        RPAD(' ', 30, '0') AS ANNUAL_INC, ");
		sql.append("        (SELECT APPOCCUPATION_RECIEVEBILL ");
		sql.append("         FROM   NFE_APP_OCCUPATION  ");
		sql.append("         WHERE  APPOCCUPATION_APPNO = T1.APP_NO) AS SEND_MAIL_TO, ");
		sql.append("        RPAD(NVL((SELECT R.RECEIVECARDTYPE_TNAME ");
		sql.append("                  FROM   NFE_MS_RECEIVECARDTYPE R, ");
		sql.append("                         NFE_APP_OCCUPATION O ");
		sql.append("                  WHERE  O.APPOCCUPATION_RECIEVECARD = R.RECEIVECARDTYPE_ID ");
		sql.append("                  AND    O.APPOCCUPATION_APPNO = T1.APP_NO), ' '), 80, ' ') AS CARD_PICKUP, ");
		sql.append("        RPAD(' ', 80, ' ') AS CARD_PICKUP1, ");
		sql.append("        RPAD(' ', 5, ' ') AS REASON_CODE, ");
		sql.append("        RPAD(' ', 20, ' ') AS BOOTH_DR_SALE, ");
		sql.append("        RPAD(' ', 6, ' ') AS CO_BRAND_CODE, ");
		sql.append("        RPAD(' ', 32, ' ') AS S6, ");
		sql.append("        RPAD(' ', 8, ' ') AS OLD_EXPIRY_DATE, ");
		sql.append("        RPAD(T4.APPROVE_UPDATEBY, 10, ' ') AS DONE_BY, ");
		sql.append("        RPAD(' ', 14, ' ') AS ACTDATE_AND_ACTIME, ");
		sql.append("        RPAD(' ', 16, ' ') AS S7, ");
		sql.append("        RPAD(' ', 200, ' ') AS NOTE, ");
		sql.append("        RPAD(' ', 10, ' ') AS ACT_DATE, ");
		sql.append("        RPAD(' ', 10, ' ') AS ACT_TIME, ");
		sql.append("        RPAD(' ', 50, ' ') AS S8, ");
		sql.append("        RPAD((CASE WHEN T1.APP_CARD51 = 'Y' THEN 'P' ELSE 'N' END), 2, ' ') AS ISSUE_FLAG, ");
		sql.append("        RPAD(' ', 1, ' ') AS REPLACEMENT_FLAG ");
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
		sql.append("       LEFT JOIN NFE_APP_SUPPLEMENTARY T5 ");
		sql.append("           ON T5.APPSUP_ID = T2.APPPRODUCT_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T1.APP_CARD51 = 'Y' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_SLOYALTYCARD1 = 'Y') ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_APP_STATUSTRACKING ");
		sql.append("               WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("               AND    STATUSTRACKING_APPNO = T1.APP_NO ");
		sql.append("               AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("               AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                  AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");
		sql.append("ORDER BY T4.APPROVE_CARDNO ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryHeader(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryHeader(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT 'H0' || TO_CHAR(SYSDATE, 'YYYYMMDD') || LPAD(COUNT(1), 8, '0') || RPAD(' ', 3314, ' ') AS HEADER ");
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
		sql.append("       LEFT JOIN NFE_APP_SUPPLEMENTARY T5 ");
		sql.append("           ON T5.APPSUP_ID = T2.APPPRODUCT_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T1.APP_CARD51 = 'Y' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T2.APPPRODUCT_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T2.APPPRODUCT_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_SLOYALTYCARD1 = 'Y') ");
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
