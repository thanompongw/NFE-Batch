/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "setDateService")
public class SetDateBO extends BatchBO {
	
	private static Logger LOG = Logger.getLogger(SetDateBO.class);
	
	private static final String FUNCTION_ID = "SB023";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Resource(name = "setDateDao")
	private AbstractBatchDao dao;

	/**
	 *  
	 */
	public SetDateBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			try {
				dao.update(null);				
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
								 SetDateBO.class);
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
