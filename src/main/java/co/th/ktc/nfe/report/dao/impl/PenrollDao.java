/**
 * 
 */
package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

/**
 * @author temp_dev1
 *
 */
@Repository(value = "penrollDao")
public class PenrollDao extends AbstractReportDao {

	/**
	 * 
	 */
	public PenrollDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#update(java.lang.Object[])
	 */
	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#delete(java.lang.Object[])
	 */
	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#query(java.lang.Object[])
	 */
	@Override
	public SqlRowSet query(Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT T4.APPROVE_EMBOSSNAME2 AS MEMBER_ID, ");
		sql.append("       (SELECT PREFIXNAME_ENAME ");
		sql.append("        FROM   NFE_MS_PREFIXNAME  ");
		sql.append("        WHERE  PREFIXNAME_ID = T1.APP_PREFIXNAME) AS TITLE_NAME,  ");
		sql.append("       T1.APP_ENGFNAME AS FIRST_NAME, ");
		sql.append("       T1.APP_ENGLNAME AS LAST_NAME, ");
		sql.append("       (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM  NFE_APP_OCCUPATION  ");
		sql.append("                         WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                         AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("            THEN 'H' ");
		sql.append("            ELSE 'B' ");
		sql.append("        END) AS ADDRESS_INDICATOR, ");
		sql.append("       (SELECT PIF_CURRENTADDR_LINE1 ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS HOME_ADDRESS_LINE1, ");
		sql.append("       (SELECT PIF_CURRENTADDR_LINE2 ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS HOME_ADDRESS_LINE2, ");
		sql.append("       (SELECT D.DISTRICT_TNAME ");
		sql.append("        FROM   NFE_MS_DISTRICT D, ");
		sql.append("               NFE_APP_PIF P ");
		sql.append("        WHERE  D.DISTRICT_ID = P.PIF_CURRENTADDR_DISTRICT ");
		sql.append("        AND    P.PIF_APPNO = T1.APP_NO) AS HOME_ADDRESS_LINE3, ");
		sql.append("       ''  AS HOME_ADDRESS_LINE4, ");
		sql.append("       (SELECT S.SUBPROVINCE_TNAME ");
		sql.append("        FROM   NFE_MS_SUBPROVINCE S, ");
		sql.append("               NFE_APP_PIF P ");
		sql.append("        WHERE  S.SUBPROVINCE_ID = P.PIF_CENSUSADDR_AMPHUR ");
		sql.append("        AND    P.PIF_APPNO = T1.APP_NO) AS HOME_CITY_NAME, ");
		sql.append("       (SELECT P.PROVINCE_TNAME ");
		sql.append("        FROM   NFE_MS_PROVINCE P, ");
		sql.append("               NFE_APP_PIF AP ");
		sql.append("        WHERE  P.PROVINCE_ID = AP.PIF_CURRENTADDR_PROVINCE ");
		sql.append("        AND    AP.PIF_APPNO = T1.APP_NO) AS HOME_PROVINCE_NAME, ");
		sql.append("       'TH' AS HOME_COUNTRY, ");
		sql.append("       (SELECT PIF_CURRENTADDR_ZIPCODE ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS HOME_POSTAL_CODE, ");
		sql.append("       (SELECT PIF_OTHERS_WORKPLACE ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS COMPANY_NAME, ");
		sql.append("       (SELECT APPOCCUPATION_ADDRLINE1 ");
		sql.append("        FROM  NFE_APP_OCCUPATION  ");
		sql.append("        WHERE APPOCCUPATION_APPNO = T1.APP_NO) AS BU_ADDRESS_LINE1, ");
		sql.append("       (SELECT APPOCCUPATION_ADDRLINE2 ");
		sql.append("        FROM  NFE_APP_OCCUPATION  ");
		sql.append("        WHERE APPOCCUPATION_APPNO = T1.APP_NO) AS BU_ADDRESS_LINE2, ");
		sql.append("       (SELECT D.DISTRICT_TNAME ");
		sql.append("        FROM   NFE_MS_DISTRICT D, ");
		sql.append("               NFE_APP_OCCUPATION O ");
		sql.append("        WHERE  D.DISTRICT_ID = O.APPOCCUPATION_DISTRICT ");
		sql.append("        AND    O.APPOCCUPATION_APPNO = T1.APP_NO) AS BU_ADDRESS_LINE3, ");
		sql.append("       '' AS BU_ADDRESS_LINE4,  ");
		sql.append("       (SELECT S.SUBPROVINCE_TNAME  ");
		sql.append("        FROM   NFE_MS_SUBPROVINCE S,  ");
		sql.append("        NFE_APP_OCCUPATION O  ");
		sql.append("        WHERE  S.SUBPROVINCE_ID = O.APPOCCUPATION_AMPHUR  ");
	    sql.append("        AND    O.APPOCCUPATION_APPNO = T1.APP_NO) AS BU_CITY_NAME,  ");
		sql.append("       (SELECT P.PROVINCE_TNAME ");
		sql.append("        FROM   NFE_MS_PROVINCE P, ");
		sql.append("               NFE_APP_OCCUPATION O ");
		sql.append("        WHERE  P.PROVINCE_ID = O.APPOCCUPATION_PROVINCE ");
		sql.append("        AND    O.APPOCCUPATION_APPNO = T1.APP_NO) AS BU_PROVINCE_NAME, ");
		sql.append("       'TH' AS BU_COUNTRY, ");
		sql.append("       (SELECT APPOCCUPATION_ZIPCODE ");
		sql.append("        FROM   NFE_APP_OCCUPATION ");
		sql.append("        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) AS BU_POSTAL_CODE, ");
		sql.append("       (CASE ");
		sql.append("            WHEN EXISTS (SELECT 'X' ");
		sql.append("                         FROM  NFE_APP_OCCUPATION  ");
		sql.append("                         WHERE APPOCCUPATION_APPNO = T1.APP_NO ");
		sql.append("                         AND   APPOCCUPATION_RECIEVEBILL = 1) ");
		sql.append("            THEN 'H' ");
		sql.append("            ELSE 'B' ");
		sql.append("        END) AS TELEPHONE_INDICATOR, ");
		sql.append("       (SELECT P.PROVINCE_PHONECODE ");
		sql.append("        FROM   NFE_MS_PROVINCE P, ");
		sql.append("               NFE_APP_PIF AP ");
		sql.append("        WHERE  P.PROVINCE_ID = AP.PIF_CURRENTADDR_PROVINCE ");
		sql.append("        AND    AP.PIF_APPNO = T1.APP_NO) AS HOME_TEL_AREA_CODE, ");
		sql.append("       (SELECT PIF_CURRENTADDR_PHONENO ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS HOME_TEL_NO, ");
		sql.append("       (SELECT PIF_CURRENTADDR_PHONENOEXT ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS HOME_TEL_EXT, ");
		sql.append("       (SELECT P.PROVINCE_PHONECODE ");
		sql.append("        FROM   NFE_MS_PROVINCE P, ");
		sql.append("               NFE_APP_OCCUPATION O ");
		sql.append("        WHERE  P.PROVINCE_ID = O.APPOCCUPATION_PROVINCE ");
		sql.append("        AND    O.APPOCCUPATION_APPNO = T1.APP_NO) AS BU_TEL_AREA_CODE, ");
		sql.append("       (SELECT APPOCCUPATION_PHONENO1 ");
		sql.append("        FROM   NFE_APP_OCCUPATION ");
		sql.append("        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) AS BU_TEL_NO, ");
		sql.append("       (SELECT APPOCCUPATION_PHONENO1EXT ");
		sql.append("        FROM   NFE_APP_OCCUPATION ");
		sql.append("        WHERE  APPOCCUPATION_APPNO = T1.APP_NO) AS BU_TEL_EXT, ");
		sql.append("       '66' AS MOBILE_AREA_CODE, ");
		sql.append("       (SELECT PIF_CURRENTADDR_MOBILENO ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS MOBILE_NO, ");
		sql.append("       '' AS MOBILE_NO_EXT, ");
		sql.append("       '' AS FAX_AREA_CODE, ");
		sql.append("       '' AS FAX_NO, ");
		sql.append("       '' AS FAX_EXT, ");
		sql.append("       (SELECT PIF_CURRENTADDR_EMAIL ");
		sql.append("        FROM   NFE_APP_PIF ");
		sql.append("        WHERE  PIF_APPNO = T1.APP_NO) AS EMAIL, ");
		sql.append("       TO_CHAR(T1.APP_DOB, 'DDMMYYYY') AS BIRTH_DATE ");
		sql.append("FROM   NFE_APPLICATION T1 ");
		sql.append("       LEFT JOIN NFE_APP_PRODUCT T2  ");
		sql.append("           ON  T2.APPPRODUCT_ID > 0  ");
		sql.append("           AND T2.APPPRODUCT_APPNO = T1.APP_NO ");
		sql.append("       LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("           ON  T3.RESOLVE_ID > 0 ");
		sql.append("           AND T3.RESOLVE_APPNO = T1.APP_NO ");
		sql.append("           AND T3.RESOLVE_APPPRODUCTID = T2.APPPRODUCT_ID ");
		sql.append("       LEFT JOIN NFE_APP_APPROVE T4  ");
		sql.append("             ON  T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE  T1.APP_NO <> ' ' ");
		sql.append("AND    T3.RESOLVE_STATUSCODE = '8A' ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_GROUPPRODUCT ");
		sql.append("               WHERE  GROUPPRODUCT_ID = T1.APP_GROUPPRODUCT ");
		sql.append("               AND    (GROUPPRODUCT_LOANTYPE IN ('C', 'B') ");
		sql.append("                       OR (GROUPPRODUCT_TYPE = 'B'  ");
		sql.append("                           AND GROUPPRODUCT_LOANTYPE IN ('C', 'B')))) ");
		sql.append("AND    EXISTS (SELECT 'X' ");
		sql.append("               FROM   NFE_MS_SUBPRODUCT ");
		sql.append("               WHERE  SUBPRODUCT_ID = T4.APPROVE_SUBPRODUCTID ");
		sql.append("               AND    SUBPRODUCT_PRODUCTID = T4.APPROVE_PRODUCTID ");
		sql.append("               AND    SUBPRODUCT_CODE IN ('P32', 'T24')) ");
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
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#success(java.lang.Object[])
	 */
	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.report.dao.AbstractReportDao#fail(java.lang.Object[])
	 */
	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

}
