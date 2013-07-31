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
@Repository(value = "letterRejectLR04Dao")
public class LetterRejectLR04Dao extends AbstractBatchDao {

	/**
	 * 
	 */
	public LetterRejectLR04Dao() {
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
		
		sql.append("SELECT NVL(T1.CLAPPLICATION_TNAME || ' ' || T1.CLAPPLICATION_TSURNAME, ' ') AS THAI_NAME, ");
		sql.append("       (SELECT NVL(SLADDRESS_ADDRESSLINE1, ' ') ");
		sql.append("        FROM NFE_APP_SLADDRESS ");
		sql.append("        WHERE SLADDRESS_ID > 0 ");
		sql.append("        AND   SLADDRESS_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("        AND   SLADDRESS_ADDRESSTYPE = T2.CLPRODUCT_STMMAILINGFLAG) AS ADDRESSLINE1, ");
		sql.append("       (SELECT NVL(SLADDRESS_ADDRESSLINE2, ' ') ");
		sql.append("        FROM NFE_APP_SLADDRESS ");
		sql.append("        WHERE SLADDRESS_ID > 0 ");
		sql.append("        AND   SLADDRESS_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("        AND   SLADDRESS_ADDRESSTYPE = T2.CLPRODUCT_STMMAILINGFLAG) AS ADDRESSLINE2, ");
		sql.append("       (SELECT NVL(SLADDRESS_ADDRESSLINE3 || ' ' || SLADDRESS_ADDRESSLINE4,' ') ");
		sql.append("        FROM NFE_APP_SLADDRESS ");
		sql.append("        WHERE SLADDRESS_ID > 0 ");
		sql.append("        AND   SLADDRESS_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("        AND   SLADDRESS_ADDRESSTYPE = T2.CLPRODUCT_STMMAILINGFLAG) AS ADDRESSLINE3, ");
		sql.append("       (SELECT NVL(SLADDRESS_CITY, ' ') ");
		sql.append("        FROM NFE_APP_SLADDRESS ");
		sql.append("        WHERE SLADDRESS_ID > 0 ");
		sql.append("        AND   SLADDRESS_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("        AND   SLADDRESS_ADDRESSTYPE = T2.CLPRODUCT_STMMAILINGFLAG) AS ADDRESSLINE4, ");
		sql.append("       (SELECT NVL(SLADDRESS_ZIPCODE, ' ') ");
		sql.append("        FROM NFE_APP_SLADDRESS ");
		sql.append("        WHERE SLADDRESS_ID > 0 ");
		sql.append("        AND   SLADDRESS_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("        AND   SLADDRESS_ADDRESSTYPE = T2.CLPRODUCT_STMMAILINGFLAG) AS ADDRESSLINE5, ");
		sql.append("       (SELECT TO_CHAR (STATUSTRACKING_ENDTIME, 'DD/MM/YYYY') ");
		sql.append("        FROM   NFE_APP_STATUSTRACKING ");
		sql.append("        WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("        AND    STATUSTRACKING_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("        AND    STATUSTRACKING_STATUS = '8F') AS DATES, ");
		sql.append("       (SELECT TO_CHAR(NFE_CURRENTDATE, 'DD/MM/YYYY') FROM NFE_SETDATE) AS DATE_LETER, ");
		sql.append("       T1.CLAPPLICATION_NO AS APPLICATION_NO, ");
		// for oracle upper 11g
//		sql.append("        (SELECT LISTAGG((CASE ");
//		sql.append("                            WHEN EXISTS (SELECT 'X' ");
//		sql.append("                                         FROM NFE_SCS_MAPPINGCODE  ");
//		sql.append("                                         WHERE MAPPINGCODE_SCSCODE = CLPRODUCT_GROUPPRODUCT ");
//		sql.append("                                         AND   MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
//		sql.append("                                         AND   MAPPINGCODE_NFECODE = 'F')  ");
//		sql.append("                            THEN 'สินเชื่อบุคคล KTC CASH' ");
//		sql.append("                            WHEN EXISTS (SELECT 'X' ");
//		sql.append("                                         FROM NFE_SCS_MAPPINGCODE  ");
//		sql.append("                                         WHERE MAPPINGCODE_SCSCODE = CLPRODUCT_GROUPPRODUCT ");
//		sql.append("                                         AND   MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
//		sql.append("                                         AND   MAPPINGCODE_NFECODE = 'R') ");
//		sql.append("                            THEN 'สินเชื่อพร้อมใช้ KTC CASH REVOLVE' ");
//		sql.append("                            WHEN EXISTS (SELECT 'X' ");
//		sql.append("                                         FROM NFE_SCS_MAPPINGCODE  ");
//		sql.append("                                         WHERE MAPPINGCODE_SCSCODE = CLPRODUCT_GROUPPRODUCT ");
//		sql.append("                                         AND   MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
//		sql.append("                                         AND   MAPPINGCODE_NFECODE = 'C')  ");
//		sql.append("                            THEN 'บัตรเครดิต KTC' ");
//		sql.append("                            ELSE ' ' ");
//		sql.append("                        END), '/') WITHIN GROUP (ORDER BY CLPRODUCT_GROUPPRODUCT)  ");
//		sql.append("        FROM   NFE_APP_CLPRODUCT ");
//		sql.append("        WHERE  CLPRODUCT_ID > 0  ");
//		sql.append("        AND    CLPRODUCT_APPNO = T1.CLAPPLICATION_NO ");
//		sql.append("        GROUP BY CLPRODUCT_APPNO, CLPRODUCT_GROUPPRODUCT) AS PRODUCT_NAME, ");
		// for oracle under 11g
		sql.append("       (WITH APP_CLPRODUCT AS  ");
		sql.append("            (  ");
		sql.append("                SELECT (CASE ");
		sql.append("                            WHEN EXISTS (SELECT 'X' ");
		sql.append("                                         FROM NFE_SCS_MAPPINGCODE  ");
		sql.append("                                         WHERE MAPPINGCODE_SCSCODE = CLPRODUCT_GROUPPRODUCT ");
		sql.append("                                         AND   MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
		sql.append("                                         AND   MAPPINGCODE_NFECODE = 'F')  ");
		sql.append("                            THEN 'สินเชื่อบุคคล KTC CASH' ");
		sql.append("                            WHEN EXISTS (SELECT 'X' ");
		sql.append("                                         FROM NFE_SCS_MAPPINGCODE  ");
		sql.append("                                         WHERE MAPPINGCODE_SCSCODE = CLPRODUCT_GROUPPRODUCT ");
		sql.append("                                         AND   MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
		sql.append("                                         AND   MAPPINGCODE_NFECODE = 'R') ");
		sql.append("                            THEN 'สินเชื่อพร้อมใช้ KTC CASH REVOLVE' ");
		sql.append("                            WHEN EXISTS (SELECT 'X' ");
		sql.append("                                         FROM NFE_SCS_MAPPINGCODE  ");
		sql.append("                                         WHERE MAPPINGCODE_SCSCODE = CLPRODUCT_GROUPPRODUCT ");
		sql.append("                                         AND   MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE' ");
		sql.append("                                         AND   MAPPINGCODE_NFECODE = 'C')  ");
		sql.append("                            THEN 'บัตรเครดิต KTC' ");
		sql.append("                            ELSE ' ' ");
		sql.append("                        END) AS PRODUCT_NAME, ");
		sql.append("                       CLPRODUCT_APPNO AS APPNO,  ");
		sql.append("                       CLPRODUCT_GROUPPRODUCT AS PRODUCT_ID ");
		sql.append("                FROM   NFE_APP_CLPRODUCT ");
		sql.append("                WHERE  CLPRODUCT_ID > 0  ");
		sql.append("                AND    CLPRODUCT_APPNO <> ' ' ");
		sql.append("                GROUP BY CLPRODUCT_APPNO, CLPRODUCT_GROUPPRODUCT ");
		sql.append("            )  ");
		sql.append("            SELECT RTRIM(XMLAGG (XMLELEMENT(e, PRODUCT_NAME||'/') ORDER BY PRODUCT_ID).EXTRACT('//text()'), '/') AS PRODUCT_NAME   ");
		sql.append("            FROM  APP_CLPRODUCT   ");
		sql.append("            WHERE APPNO = T1.CLAPPLICATION_NO ");
		sql.append("            GROUP BY APPNO  ");
		sql.append("       ) AS PRODUCT_NAME, ");
		sql.append("       (SELECT REASON_LETTERDESCRIPTION ");
		sql.append("        FROM   NFE_MS_REASON ");
		sql.append("        WHERE  REASON_CODE = T3.RESOLVE_REASONCODE) AS REJECT_DESC, ");
		sql.append("       (SELECT DISTINCT (CONFIGURATION_VALUE) ");
		sql.append("        FROM   NFE_CONFIGURATION ");
		sql.append("        WHERE  CONFIGURATION_NAME = 'Letter01_ContactAndPhone') AS CONTACT ");
		sql.append("FROM  NFE_CLAPPLICATION T1 ");
		sql.append("      LEFT JOIN NFE_APP_CLPRODUCT T2 ");
		sql.append("          ON  T2.CLPRODUCT_ID > 0  ");
		sql.append("          AND T2.CLPRODUCT_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("      LEFT JOIN NFE_APP_RESOLVE T3 ");
		sql.append("          ON  T3.RESOLVE_ID > 0  ");
		sql.append("          AND T3.RESOLVE_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("          AND T3.RESOLVE_APPPRODUCTID = T2.CLPRODUCT_ID ");
		sql.append("      LEFT JOIN NFE_APP_APPROVE T4 ");
		sql.append("          ON T4.APPROVE_RESOLVEID = T3.RESOLVE_ID ");
		sql.append("WHERE T1.CLAPPLICATION_NO <> ' ' ");
		sql.append("AND   T3.RESOLVE_STATUSCODE = '8D' ");
		sql.append("AND   EXISTS(SELECT 'X' ");
		sql.append("             FROM NFE_SCS_MAPPINGCODE  ");
		sql.append("             WHERE MAPPINGCODE_SCSCODE = T2.CLPRODUCT_GROUPPRODUCT ");
		sql.append("             AND MAPPINGCODE_GROUPNAME = 'NFE_MS_GROUPPRODUCTLOANTYPE') ");
		sql.append("AND   EXISTS (SELECT 'X' ");
		sql.append("              FROM   NFE_APP_STATUSTRACKING ");
		sql.append("              WHERE  STATUSTRACKING_ID > 0 ");
		sql.append("              AND    STATUSTRACKING_APPNO = T1.CLAPPLICATION_NO ");
		sql.append("              AND    STATUSTRACKING_STATUS = '8F' ");
		sql.append("              AND    (STATUSTRACKING_ENDTIME BETWEEN TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS') ");
		sql.append("                                                 AND TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI:SS'))) ");

		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryHeader(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryHeader(Object[] parameter) {
		
		return null;
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

	@Override
	public SqlRowSet queryTrailer(Object[] parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
