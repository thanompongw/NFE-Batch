/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.common.FileUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "card51Service")
public class Card51BO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(Card51BO.class);
	
	private static final String FUNCTION_ID = "SB002";
	
	private static final String BATCH_FILE_NAME = "CARD51_D";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "card51Dao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public Card51BO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			file = new FileUtils();
			String currentDate = null;
		
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("BATCH_DATE");
			}
			
			String fromTimestamp = currentDate + " 00:00:00";
			String toTimestamp = currentDate + " 23:59:59";
			
			LOG.info("Report DateTime From : " + fromTimestamp);
			LOG.info("Report DateTime To : " + toTimestamp);
			
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);
			
			// generateReport
		    write(parameter);
			
			String dirPath = batchConfig.getPathOutputCSP();
			
			currentDate = 
					dateUtils.convertFormatDateTime(currentDate, 
													DateUtils.DEFAULT_DATE_FORMAT, 
													"yyMMdd");
			file.writeFile(BATCH_FILE_NAME, dirPath, currentDate);
		} catch (CommonException ce) {
			processStatus = 1;
			for (BusinessError error : ce.getErrorList().getErrorList()) {
				CommonLogger.log(NFEBatchConstants.REPORT_APP_ID, 
								 NFEBatchConstants.SYSTEM_ID, 
								 FUNCTION_ID, 
								 error.getErrorkey(), 
								 String.valueOf(processStatus), 
								 error.getSubstitutionValues(), 
								 NFEBatchConstants.ERROR, 
								 Card51BO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {

		try {

			SqlRowSet rowSet = dao.queryHeader(new Object[] {parameter.get("DATE_FROM"),
											 	   			 parameter.get("DATE_TO")});
			
			generateFileHeader(rowSet);

			
			rowSet = dao.queryDetail(new Object[] {parameter.get("DATE_FROM"),
	                                               parameter.get("DATE_TO")});
			
			generateFileDetail(rowSet);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

	private void generateFileHeader(SqlRowSet rowSet) {
		
		if (rowSet.next()) {
			file.setObject(rowSet.getString(1));
			file.eol();
		}
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("CARD_STATUS"));
			file.setObject(rowSet.getString("CID"));
			file.setObject(rowSet.getString("CID1"));
			file.setObject(rowSet.getString("S"));
			file.setObject(rowSet.getString("ACC_NO"));
			file.setObject(rowSet.getString("OPEN_DATE"));
			file.setObject(rowSet.getString("ENG_NAME"));
			file.setObject(rowSet.getString("THAI_NAME"));
			file.setObject(rowSet.getString("S1"));
			file.setObject(rowSet.getString("TYPED"));
			file.setObject(rowSet.getString("ISSUE_DATE"));
			file.setObject(rowSet.getString("ACTV_DATE"));
			file.setObject(rowSet.getString("ACTV_STATUS"));
			file.setObject(rowSet.getString("CURR_EXPIRY_DATE"));
			file.setObject(rowSet.getString("PRODUCT_CODE"));
			file.setObject(rowSet.getString("SUBPRODUCT_CODE"));
			file.setObject(rowSet.getString("EMBOSSLINE1"));
			file.setObject(rowSet.getString("EMBOSSLINE2"));
			file.setObject(rowSet.getString("GENDER"));
			file.setObject(rowSet.getString("DATE_OF_BIRTH"));
			file.setObject(rowSet.getString("CARD_STATUS1"));
			file.setObject(rowSet.getString("S4"));
			file.setObject(rowSet.getString("ADDRESS1"));
			file.setObject(rowSet.getString("ADDRESS2"));
			file.setObject(rowSet.getString("ADDRESS3"));
			file.setObject(rowSet.getString("ADDRESS4"));
			file.setObject(rowSet.getString("S5"));
			file.setObject(rowSet.getString("ZIPCODE"));
			file.setObject(rowSet.getString("PRIMARY_ADDR1"));
			file.setObject(rowSet.getString("PRIMARY_ADDR2"));
			file.setObject(rowSet.getString("PRIMARY_ADDR3"));
			file.setObject(rowSet.getString("PROVINCE"));
			file.setObject(rowSet.getString("PRIMARY_STATE"));
			file.setObject(rowSet.getString("PRIMARY_ZIP_CODE"));
			file.setObject(rowSet.getString("OFFICE_BUILD"));
			file.setObject(rowSet.getString("OFFICE_ADDR1"));
			file.setObject(rowSet.getString("OFFICE_ADDR2"));
			file.setObject(rowSet.getString("OFFICE_ADDR3"));
			file.setObject(rowSet.getString("OFFICE_PROVINCE"));
			file.setObject(rowSet.getString("OFFICE_ZIP_CODE"));
			file.setObject(rowSet.getString("NO_OF_CHILD"));
			file.setObject(rowSet.getString("SPOUSE_NAME"));
			file.setObject(rowSet.getString("APPLY_NO"));
			file.setObject(rowSet.getString("SOURCE_CODE"));
			file.setObject(rowSet.getString("AGEN_CODE"));
			file.setObject(rowSet.getString("BRANCH_CODE"));
			file.setObject(rowSet.getString("HOME_TYPE"));
			file.setObject(rowSet.getString("HOME_STATUS"));
			file.setObject(rowSet.getString("PHONE"));
			file.setObject(rowSet.getString("FAX"));
			file.setObject(rowSet.getString("MOBILE"));
			file.setObject(rowSet.getString("OFFICE_PHONE1"));
			file.setObject(rowSet.getString("OFFICE_PHONE2"));
			file.setObject(rowSet.getString("OFFICE_FAX"));
			file.setObject(rowSet.getString("WWW"));
			file.setObject(rowSet.getString("EMAIL"));
			file.setObject(rowSet.getString("OCCUPATION"));
			file.setObject(rowSet.getString("STATUS"));
			file.setObject(rowSet.getString("UNIVERSITY"));
			file.setObject(rowSet.getString("MAJOR"));
			file.setObject(rowSet.getString("U_ADRESS"));
			file.setObject(rowSet.getString("DEGREES"));
			file.setObject(rowSet.getString("COMPANY_NAME"));
			file.setObject(rowSet.getString("INDUSTRIAL_SECTOR"));
			file.setObject(rowSet.getString("POSITION"));
			file.setObject(rowSet.getString("WORK_START"));
			file.setObject(rowSet.getString("ANNUAL_INC"));
			file.setObject(rowSet.getString("SEND_MAIL_TO"));
			file.setObject(rowSet.getString("CARD_PICKUP"));
			file.setObject(rowSet.getString("CARD_PICKUP1"));
			file.setObject(rowSet.getString("REASON_CODE"));
			file.setObject(rowSet.getString("BOOTH_DR_SALE"));
			file.setObject(rowSet.getString("CO_BRAND_CODE"));
			file.setObject(rowSet.getString("S6"));
			file.setObject(rowSet.getString("OLD_EXPIRY_DATE"));
			file.setObject(rowSet.getString("DONE_BY"));
			file.setObject(rowSet.getString("ACTDATE_AND_ACTIME"));
			file.setObject(rowSet.getString("S7"));
			file.setObject(rowSet.getString("NOTE"));
			file.setObject(rowSet.getString("ACT_DATE"));
			file.setObject(rowSet.getString("ACT_TIME"));
			file.setObject(rowSet.getString("S8"));
			file.setObject(rowSet.getString("ISSUE_FLAG"));
			file.setObject(rowSet.getString("REPLACEMENT_FLAG"));
			file.eol();
		}
	}

}
