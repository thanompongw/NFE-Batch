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
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.common.FileUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "smsCLSupplementaryCardService")
public class SMSCLSupplementaryCardBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(SMSCLSupplementaryCardBO.class);
	
	private static final String BATCH_FILE_NAME = "CLIP_SMS_";
	
	@Autowired
	private BatchConfiguration config;
	
	@Resource(name = "smsCLSupplementaryCardDao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public SMSCLSupplementaryCardBO() {
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
			
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);
			
			// generate Batch File
		    write(parameter);
			
			String dirPath = config.getPathOutputSMS();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"yyMMdd");
			String type = null;
			String statusCode = parameter.get("STATUS_CODE");
			
			if (statusCode.equals(NFEBatchConstants.APPROVE_STATUS_CODE)) {
				type = NFEBatchConstants.SMS_BATCH_TYPE_SUPPLEMENT_APPROVE;
			} else if (statusCode.equals(NFEBatchConstants.DECLINE_STATUS_CODE)) {
				type = NFEBatchConstants.SMS_BATCH_TYPE_SUPPLEMENT_REJECT;
			}
			
			file.writeFile(BATCH_FILE_NAME, 
					       dirPath, 
					       currentDate, 
					       type);
		} catch (Exception e) {
			processStatus = 1;
			e.printStackTrace();
			//TODO: throws error to main function
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) {

		SqlRowSet rowSet = dao.queryDetail(new Object[] {parameter.get("STATUS_CODE"),
														 parameter.get("DATE_FROM"),
                                                         parameter.get("DATE_TO")});
		
		generateFileDetail(rowSet);
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