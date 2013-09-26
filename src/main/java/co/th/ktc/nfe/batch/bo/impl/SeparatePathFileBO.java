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
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "separatePathFileService")
public class SeparatePathFileBO extends BatchBO {
	
	private static Logger LOG = Logger.getLogger(SeparatePathFileBO.class);
	
	private static final String FUNCTION_ID = "SS001";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Resource(name = "declineLoanWeeklyDao")
	private AbstractBatchDao dao;

	/**
	 *  
	 */
	public SeparatePathFileBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			move();
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
								 SeparatePathFileBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
	}
	
	private void move() throws CommonException {
		
		String dirPath = batchConfig.getPathOutput();
		
		File sourceDir = new File(dirPath);
		File destDir = null;
		StringBuilder destFileName = new StringBuilder();
		StringBuilder supDestFileName = new StringBuilder();
		String currentDate = dao.getSetDate("YYYYMMDD");
		try {
		
			for (File file : sourceDir.listFiles()) {

				destFileName.setLength(0);
				destFileName.append(dirPath);
				destFileName.append(currentDate);
				destDir = new File(destFileName.toString());
				
				if (file.isDirectory()) {
					if (file.getName().equals("CSP")
							|| file.getName().equals("iNet")
							|| file.getName().equals("SMS")
							|| file.getName().equals("Montran")) {
						
						for (File subFile : file.listFiles()) {
							
							supDestFileName.setLength(0);
							supDestFileName.append(destFileName.toString());
							supDestFileName.append("/");
							supDestFileName.append(file.getName());
							supDestFileName.append("/");
							
							destDir = new File(supDestFileName.toString());
							LOG.info("Move File : " + subFile.getName() + " Directory To " + destDir.getPath());
							try {
								org.apache.commons.io.FileUtils.moveFileToDirectory(subFile, destDir, true);
							} catch (IOException e) {
								org.apache.commons.io.FileUtils.forceDelete(destDir);
								org.apache.commons.io.FileUtils.moveFileToDirectory(subFile, destDir, true);
							}
						}
					}
				} else {
					try {
						LOG.info("Move File : " + file.getName() + " Directory To " + destDir.getPath());
						org.apache.commons.io.FileUtils.moveFileToDirectory(file, destDir, true);
					} catch (IOException e) {
						org.apache.commons.io.FileUtils.forceDelete(destDir);
						org.apache.commons.io.FileUtils.moveFileToDirectory(file, destDir, true);
					}
				}
			}
		} catch (IOException e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
		}
	}

}
