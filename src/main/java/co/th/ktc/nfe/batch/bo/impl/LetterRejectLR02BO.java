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
@Service(value = "letterRejectLR02Service")
public class LetterRejectLR02BO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(LetterRejectLR02BO.class);
	
	private static final String FUNCTION_ID = "SB011";
	
	private static final String BATCH_FILE_NAME = "LR02_";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "letterRejectLR02Dao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public LetterRejectLR02BO() {
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
								 LetterRejectLR02BO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		try {

			generateFileHeader();
			
			SqlRowSet rowSet = dao.queryDetail(new Object[] {parameter.get("DATE_FROM"),
	                                                         parameter.get("DATE_TO"),
	                                                         parameter.get("DATE_FROM"),
	                                                         parameter.get("DATE_TO"),
	                                                         parameter.get("DATE_FROM"),
	                                                         parameter.get("DATE_TO")});
			
			generateFileDetail(rowSet);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

	private void generateFileHeader() {
		
		file.setObject("FirstLastName", "|");
        file.setObject("AddressLine1", "|");
        file.setObject("AddressLine2", "|");
        file.setObject("AddressLine3", "|");
        file.setObject("AddressLine4", "|");
        file.setObject("AddressLine5", "|");
        file.setObject("Date", "|");
//        file.setObject("DateLetter", "|");
        file.setObject("ApplicationNo", "|");
        file.setObject("ProductName", "|");
        file.setObject("RejectCode", "|");
        file.setObject("ContactName");
        file.eol();
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("CUSTOMER_NAME"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE1"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE2"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE3"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE4"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE5"), "|");
			file.setObject(rowSet.getString("DATES"), "|");
//			file.setObject(rowSet.getString("DATE_LETER"), "|");
			file.setObject(rowSet.getString("APPLICATION_NO"), "|");
			file.setObject(rowSet.getString("PRODUCT_NAME"), "|");
			file.setObject(rowSet.getString("REJECT_DESC"), "|");
			file.setObject(rowSet.getString("CONTACT"));
			file.eol();
		}
	}

}
