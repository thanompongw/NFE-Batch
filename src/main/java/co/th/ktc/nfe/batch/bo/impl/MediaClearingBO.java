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
import co.th.ktc.nfe.report.domain.DateBean;

/**
 * @author temp_dev1
 *
 */
@Service(value = "mediaClearingService")
public class MediaClearingBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(MediaClearingBO.class);
	
	private static final String FUNCTION_ID = "SB014";
	
	private static final String BATCH_FILE_NAME = "MEDIACLR_LNDISB_D";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "mediaClearingDao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public MediaClearingBO() {
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
			
			DateBean dateBean = dao.getBusinessDay(currentDate);
			
			if (dateBean != null) {

				String fromTimestamp = 
						dateUtils.toString(dateBean.getDateFrom(), 
										   DateUtils.DEFAULT_DATE_FORMAT)  + " 00:00:00";
				String toTimestamp = 
						dateUtils.toString(dateBean.getDateTo(), 
								   DateUtils.DEFAULT_DATE_FORMAT) + " 23:59:59";
				
				LOG.info("Report DateTime From : " + fromTimestamp);
				LOG.info("Report DateTime To : " + toTimestamp);
				
				parameter.put("DATE_FROM", fromTimestamp);
				parameter.put("DATE_TO", toTimestamp);
				
				// generateReport
			    write(parameter);
				
				String dirPath = batchConfig.getPathOutputINet();
				
				currentDate = 
						dateUtils.convertFormatDateTime(currentDate, 
														DateUtils.DEFAULT_DATE_FORMAT, 
														"yyMMdd");
				file.writeFile(BATCH_FILE_NAME, dirPath, currentDate);
			}
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
								 MediaClearingBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		try {
			Integer totalRecord = dao.size(new Object[] {parameter.get("DATE_FROM"),
											 	   	     parameter.get("DATE_TO")});
			
			Integer totalRound = new Integer((int) Math.ceil(totalRecord / 100.00));
			
			for (int i = 1; i <= totalRound; i++) {
				
				SqlRowSet rowSet = dao.queryHeader(new Object[] {i,
						                                         parameter.get("DATE_FROM"),
												 	   			 parameter.get("DATE_TO"),
												 	   			 i,
												 	   			 i});
				
				generateFileHeader(rowSet);

				
				rowSet = dao.queryDetail(new Object[] {i, 
						                               parameter.get("DATE_FROM"),
		                                               parameter.get("DATE_TO"),
		                                               i,
		                                               i});
				
				generateFileDetail(rowSet);
			}			
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

	private void generateFileHeader(SqlRowSet rowSet) {
		
		if (rowSet.next()) {
			file.setObject(rowSet.getString("FILE_TYPE"));
			file.setObject(rowSet.getString("RECORD_TYPE"));
			file.setObject(rowSet.getString("SEQ"));
			file.setObject(rowSet.getString("DEFAULT_BANK_CODE"));
			file.setObject(rowSet.getString("TOTAL_RECORD"));
			file.setObject(rowSet.getString("TOTAL_BALANCE_TRANSFER"));
			file.setObject(rowSet.getString("EFFECTIVE_DATE"));
			file.setObject(rowSet.getString("TYPE"));
			file.setObject(rowSet.getString("GROUPPRODUCT_TYPE"));
			file.setObject(rowSet.getString("ZERO"));
			file.eol();
		}
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("FILE_TYPE"));
			file.setObject(rowSet.getString("RECORD_TYPE"));
			file.setObject(rowSet.getString("BATCH_NUMBER"));
			file.setObject(rowSet.getString("RECEIVING_BANK_CODE"));
			file.setObject(rowSet.getString("RECEIVING_BANK_BRANCH"));
			file.setObject(rowSet.getString("RECEIVING_BANK_ACCOUNT"));
			file.setObject(rowSet.getString("SENDING_BANK_CODE_DEFAULT"));
			file.setObject(rowSet.getString("SENDING_BANK_CODE"));
			file.setObject(rowSet.getString("SENDING_ACCCOUNT_NO"));
			file.setObject(rowSet.getString("EFFECTIVE_DATE"));
			file.setObject(rowSet.getString("SERVICE_TYPE_CODE"));
			file.setObject(rowSet.getString("CLEARIGNG_HOUSE_CODE"));
			file.setObject(rowSet.getString("TRANSFER_AMOUNT"));
			file.setObject(rowSet.getString("RECEIVER_INFORMATION"));
			file.setObject(rowSet.getString("RECEIVER_ID"));
			file.setObject(rowSet.getString("RECEIVER_NAME"));
			file.setObject(rowSet.getString("SENDER_NAME"));
			file.setObject(rowSet.getString("OTHER_INFORMATION_I"));
			file.setObject(rowSet.getString("REFERENCE_NUMBER"));
			file.setObject(rowSet.getString("OTHER_INFORMATION_II"));
			file.setObject(rowSet.getString("REFERENCE_RUNNING_NUMBER"));
			file.setObject(rowSet.getString("ZERO"));
			file.eol();
		}
	}

}
