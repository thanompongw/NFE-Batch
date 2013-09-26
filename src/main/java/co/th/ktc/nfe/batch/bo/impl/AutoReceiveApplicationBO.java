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
@Service(value = "autoReceiveApplicationService")
public class AutoReceiveApplicationBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(AutoReceiveApplicationBO.class);
	
	private static final String FUNCTION_ID = "SB001";
	
	private static final String BATCH_FILE_NAME = "AutoRcv_";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "autoReceiveApplicationDao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public AutoReceiveApplicationBO() {
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
			
			String dirPath = batchConfig.getPathOutput();
			
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
								 AutoReceiveApplicationBO.class);
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
		
		 file.setObject("Product Type", "|");
         file.setObject("AppCreate", "|");
         file.setObject("ApplyNo", "|");
         file.setObject("ProductCode", "|");
         file.setObject("SubProduct", "|");
         file.setObject("PlasticCode", "|");
         file.setObject("SourceCode", "|");
         file.setObject("AgentCode", "|");
         file.setObject("BranchCode", "|");
         file.setObject("IDCard", "|");
         file.setObject("ThaiName", "|");
         file.setObject("AppStatus", "|");
         file.setObject("AppDate", "|");
         file.setObject("ReasonApp", "|");
         file.setObject("CCTyp", "|");
         file.setObject("Salary", "|");
         file.setObject("Occupation", "|");
         file.setObject("Zipcode", "|");
         file.setObject("LinneLimitApp", "|");
         file.setObject("TransferAmt", "|");
         file.setObject("Criteria");
         file.eol();
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("PRODUCT_TYPE"), "|");
			file.setObject(rowSet.getString("APP_CREATE"), "|");
			file.setObject(rowSet.getString("APPLY_NO"), "|");
			file.setObject(rowSet.getString("PRODUCT_CODE"), "|");
			file.setObject(rowSet.getString("SUB_PRODUCT_CODE"), "|");
			file.setObject(rowSet.getString("PLASTIC_CODE"), "|");
			file.setObject(rowSet.getString("SOURCE_CODE"), "|");
			file.setObject(rowSet.getString("AGENT_CODE"), "|");
			file.setObject(rowSet.getString("BRANCH_CODE"), "|");
			file.setObject(rowSet.getString("ID_CARD"), "|");
			file.setObject(rowSet.getString("THAI_NAME"), "|");
			file.setObject(rowSet.getString("APP_STATUS"), "|");
			file.setObject(rowSet.getString("APP_DATE"), "|");
			file.setObject(rowSet.getString("REASON"), "|");
			file.setObject(rowSet.getString("CC_TYPE"), "|");
			file.setObject(rowSet.getString("SALARY"), "|");
			file.setObject(rowSet.getString("OCCUPATION"), "|");
			file.setObject(rowSet.getString("ZIPCODE"), "|");
			file.setObject(rowSet.getString("LINELIMIT_APP"), "|");
			file.setObject(rowSet.getString("TRANSFER_AMT"), "|");
			file.setObject(rowSet.getString("CRITERIA"));
			file.eol();
		}
	}

}
