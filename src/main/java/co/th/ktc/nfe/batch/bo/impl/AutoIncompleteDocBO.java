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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "autoIncompleteDocService")
public class AutoIncompleteDocBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(AutoIncompleteDocBO.class);
	
	private static final String FUNCTION_ID = "SB022";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "autoIncompleteDocDao")
	private AbstractBatchDao dao;

	/**
	 *  
	 */
	public AutoIncompleteDocBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			String currentDate = null;
		
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("BATCH_DATE");
			}
			
			LOG.info("Batch Date : " + currentDate);
			
			try {
				
				String timeStamp = 
						dateUtils.getCurrentDateTime(DateUtils.DEFAULT_TIME_FORMAT);
			
				SqlRowSet rowSet = dao.queryDetail(new Object[] {currentDate});
				String appNo = null;
				Object[] parameters = null;
				while (rowSet.next()) {
					parameters = new Object[3];
					appNo = rowSet.getString("APP_NO");
					
					parameters[0] = appNo;
					parameters[1] = currentDate + " " + timeStamp;
					parameters[2] = currentDate;
					
					dao.update(parameters);
					
					dao.insert(parameters);
				}
			} catch (Exception e) {
				CommonLogger.logStackTrace(e);
				ErrorUtil.handleSystemException(e);
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
								 AutoIncompleteDocBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		
	}

}
