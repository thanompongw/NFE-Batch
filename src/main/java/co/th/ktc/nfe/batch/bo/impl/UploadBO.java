/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.io.File;
import java.io.IOException;
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
import co.th.ktc.nfe.common.FTPFile;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "uploadService")
public class UploadBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(UploadBO.class);
	
	private static final String FUNCTION_ID = "SU001";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Resource(name = "declineLoanWeeklyDao")
	private AbstractBatchDao dao;
	
	@Autowired
	private FTPFile ftpFile;

	/**
	 *  
	 */
	public UploadBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			upload();
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
								 UploadBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
	}
	
	private void upload() throws CommonException {
		
		String dirPath = batchConfig.getPathOutput();
		
		File sourceDir = new File(dirPath);
		
		for (File file : sourceDir.listFiles()) {
			if (file.isDirectory()) {
				if (file.getName().equals("CSP")
						|| file.getName().equals("iNet")
						|| file.getName().equals("SMS")
						|| file.getName().equals("Montran")) {
					
					for (File subFile : file.listFiles()) {
						ftpFile.uploadFTP(subFile.getName(), 
										  subFile.getParent(), 
										  batchConfig.getRemotePath(file.getName()), 
								  		  batchConfig.getFtpHost(), 
								  		  batchConfig.getFtpUserName(), 
								  		  batchConfig.getFtpPassword(), 
								  		  Integer.parseInt(batchConfig.getFtpPort()));
					}
				}
			} else {

				ftpFile.uploadFTP(file.getName(), 
								  file.getParent(), 
								  batchConfig.getRemotePath(""), 
						  		  batchConfig.getFtpHost(), 
						  		  batchConfig.getFtpUserName(), 
						  		  batchConfig.getFtpPassword(), 
						  		  Integer.parseInt(batchConfig.getFtpPort()));
			}
		}
	}

}
