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
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.common.FileUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "smsCLRevolvingLoanService")
public class SMSCLRevolvingLoanBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(SMSCLRevolvingLoanBO.class);
	
	private static final String FUNCTION_ID = "SB020";
	
	private static final String BATCH_FILE_APPROVE_NAME = "SMS_APPROVE_";
	private static final String BATCH_FILE_DECLINE_NAME = "SMS_DECLINE_";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Resource(name = "smsCLRevolvingLoanDao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public SMSCLRevolvingLoanBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			file = new FileUtils();
			String currentDate = null;
		
			if (parameter == null) {
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
			
			// generate Batch File
		    write(parameter);
			
			String dirPath = batchConfig.getPathOutputSMS();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"yyMMdd");
			String fileName = null;
			String statusCode = parameter.get("STATUS_CODE");
			
			if (statusCode.equals(NFEBatchConstants.APPROVE_STATUS_CODE)) {
				fileName = BATCH_FILE_APPROVE_NAME;
			} else if (statusCode.equals(NFEBatchConstants.DECLINE_STATUS_CODE)) {
				fileName = BATCH_FILE_DECLINE_NAME;
			}
			
			file.writeFile(fileName, 
					       dirPath, 
					       currentDate, 
					       NFEBatchConstants.SMS_BATCH_TYPE_REVOLVING);
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
								 SMSCLRevolvingLoanBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {

		try {
			SqlRowSet rowSet = dao.queryDetail(new Object[] {parameter.get("STATUS_CODE"),
															 parameter.get("DATE_FROM"),
	                                                         parameter.get("DATE_TO")});
			
			generateFileDetail(rowSet);			
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("MOBILE_NO"));
			file.setObject(rowSet.getString("CLCARD_NO"));
			file.setObject(rowSet.getString("CREDIT_LIMIT"));
			file.eol();
		}
	}

}
