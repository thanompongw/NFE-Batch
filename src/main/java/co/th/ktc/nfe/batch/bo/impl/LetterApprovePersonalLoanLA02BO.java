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
@Service(value = "letterApprovePersonalLoanLA02Service")
public class LetterApprovePersonalLoanLA02BO extends BatchBO {
	
	private static Logger LOG = Logger.getLogger(LetterApprovePersonalLoanLA02BO.class);
	
	private static final String FUNCTION_ID = "SR006";
	
	private static final String BATCH_FILE_NAME = "LA02_";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "letterApprovePersonalLoanLA02Dao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public LetterApprovePersonalLoanLA02BO() {
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
								 LetterApprovePersonalLoanLA02BO.class);
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

			SqlRowSet rsHeader = dao.queryHeader(new Object[] {parameter.get("DATE_FROM"),
											 	   			   parameter.get("DATE_TO")});
			
			generateFileDetail(rsHeader);
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
        file.setObject("ApplicationNo", "|");
        file.setObject("Dates", "|");
        file.setObject("ProductDesc", "|");
        file.setObject("ApproveDate", "|");
        file.setObject("CardNumber", "|");
        file.setObject("CreditLimit", "|");
        file.setObject("NormalInterest", "|");
        file.setObject("NormalCommInterest", "|");
        file.setObject("NormalTerm", "|");
        file.setObject("SpecialInterest", "|");
        file.setObject("SpecialTerm", "|");
        file.setObject("FirstDue", "|");
        file.setObject("Bank", "|");
        file.setObject("AccountName", "|");
        file.setObject("AccountNo", "|");
        file.eol();
        file.setObject("TermNo", "|");
        file.setObject("Installment", "|");
        file.setObject("Instprincipal", "|");
        file.setObject("Instinterest", "|");
        file.setObject("instcomm", "|");
        file.setObject("Endingbalance");
        file.eol();
	}

	private void generateFileDetail(SqlRowSet rsHeader) {
		
		SqlRowSet rsDetail = null;
		
		while (rsHeader.next()) {
			file.setObject("H", "|");
			file.setObject(rsHeader.getString("THAI_NAME"), "|");
			file.setObject(rsHeader.getString("ADDRESSLINE1"), "|");
			file.setObject(rsHeader.getString("ADDRESSLINE2"), "|");
			file.setObject(rsHeader.getString("ADDRESSLINE3"), "|");
			file.setObject(rsHeader.getString("ADDRESSLINE4"), "|");
			file.setObject(rsHeader.getString("ADDRESSLINE5"), "|");
			file.setObject(rsHeader.getString("APPLICATION_NO"), "|");
			file.setObject(rsHeader.getString("DATES"), "|");
			file.setObject(rsHeader.getString("PRODUCT_DESC"), "|");
			file.setObject(rsHeader.getString("APPROVE_DATE"), "|");
			file.setObject(rsHeader.getString("CARD_NUMBER"), "|");
			file.setObject(rsHeader.getString("CREDIT_LIMIT"), "|");
			file.setObject(rsHeader.getString("NORMAL_INTEREST"), "|");
			file.setObject(rsHeader.getString("NORMAL_COMM_INTEREST"), "|");
			file.setObject(rsHeader.getString("NORMAL_TERMS"), "|");
			file.setObject(rsHeader.getString("SPECIAL_INTEREST"), "|");
			file.setObject(rsHeader.getString("SPECIAL_TERMS"), "|");
			file.setObject(rsHeader.getString("FIRST_DUE"), "|");
			file.setObject(rsHeader.getString("BANK"), "|");
			file.setObject(rsHeader.getString("ACCOUNT_NAME"), "|");
			file.setObject(rsHeader.getString("ACCOUNT_NO"));
			file.eol();
			

			rsDetail = dao.queryDetail(new Object[] {rsHeader.getString("APPLICATION_NO"),
													 rsHeader.getInt("PRODUCT_ID")});
			while (rsDetail.next()) {
				file.setObject("D", "|");
				file.setObject(rsDetail.getInt("TERM_NO"), "|");
				file.setObject(rsDetail.getDouble("INSTALLMENT"), "|");
				file.setObject(rsDetail.getDouble("INST_PRINCIPAL"), "|");
				file.setObject(rsDetail.getDouble("INST_INTEREST"), "|");
				file.setObject(rsDetail.getDouble("INST_COMM"), "|");
				file.setObject(rsDetail.getDouble("ENDING_BALANCE"));
				file.eol();
			}
		}
	}

}
