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
import co.th.ktc.nfe.common.FTPFile;
import co.th.ktc.nfe.common.FileUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "disbursementService")
public class DisbursementBO extends BatchBO {
	
	private static Logger LOG = Logger.getLogger(DisbursementBO.class);
	
	private static final String FUNCTION_ID = "SB041";
	
	private static final String BATCH_FILE_NAME = "APSDISB01_D";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "disbursementBatchDao")
	private AbstractBatchDao dao;

	@Autowired
	private FileUtils file;

	@Autowired
	private FTPFile ftpFile;

	/**
	 *  
	 */
	public DisbursementBO() {
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
			
			if (dao.isDayoff(currentDate)) {
				ErrorUtil.generateError("MSTD0002AWRN", currentDate);
			}
			
			String mediaClearingDay = dao.getMediaCleringDay(currentDate);
			
			LOG.info("Media Clearing Date : " + mediaClearingDay);
			
			if (mediaClearingDay != null && !mediaClearingDay.isEmpty()) {

				String fromTimestamp =
						dateUtils.convertFormatDateTime(mediaClearingDay, 
								"yyMMdd", DateUtils.DEFAULT_DATE_FORMAT) + " 00:00:00";
				String toTimestamp = 
						dateUtils.convertFormatDateTime(mediaClearingDay, 
								"yyMMdd", DateUtils.DEFAULT_DATE_FORMAT) + " 23:59:59";
				
				LOG.info("Report DateTime From : " + fromTimestamp);
				LOG.info("Report DateTime To : " + toTimestamp);
				
				parameter.put("DATE_FROM", fromTimestamp);
				parameter.put("DATE_TO", toTimestamp);
				
				// generateReport
			    write(parameter);
				
				String dirPath = batchConfig.getPathOutputMontran();
				
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
								 DisbursementBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		
		try {
			SqlRowSet rowSet = dao.queryHeader(null);
			
			generateFileHeader(rowSet);

			rowSet = dao.queryDetail(new Object[] {parameter.get("DATE_FROM"),
	                                               parameter.get("DATE_TO")});
			generateFileDetail(rowSet);

			rowSet = dao.queryTrailer(new Object[] {parameter.get("DATE_FROM"),
	                                                parameter.get("DATE_TO")});
			generateFileTrailer(rowSet);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

	private void generateFileHeader(SqlRowSet rowSet) {
		
		if (rowSet.next()) {
			file.setObject(rowSet.getString("RECORD_TYPE"));
			file.setObject(rowSet.getString("S1"));
			file.setObject(rowSet.getString("H1"));
			file.setObject(rowSet.getString("H2"));
			file.setObject(rowSet.getString("H3"));
			file.setObject(rowSet.getString("S2"));
			file.setObject(rowSet.getString("H4"));
			file.setObject(rowSet.getString("H5"));
			file.setObject(rowSet.getString("H6"));
			file.setObject(rowSet.getString("H7"));
			file.setObject(rowSet.getString("S3"));
			file.setObject(rowSet.getString("S4"));
			file.eol();
		}
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("RECORD_TYPE"));
			file.setObject(rowSet.getString("CARD_NUMBER"));
			file.setObject(rowSet.getString("MD_SCSTC"));
			file.setObject(rowSet.getString("MD_AMT"));
			file.setObject(rowSet.getString("MD_DESC"));
			file.setObject(rowSet.getString("LODGEMENT_REFERENCE"));
			file.setObject(rowSet.getString("TRACE_BSB"));
			file.setObject(rowSet.getString("TRACE_ACCOUNT"));
			file.setObject(rowSet.getString("NAME_OF_REMITTER"));
			file.setObject(rowSet.getString("REASON_CODE"));
			file.setObject(rowSet.getString("PROCESSING_DATE"));
			file.eol();
		}
	}

	private void generateFileTrailer(SqlRowSet rowSet) {
		
		if (rowSet.next()) {
			file.setObject(rowSet.getString("RECORD_TYPE"));
			file.setObject(rowSet.getString("BSB_CODE"));
			file.setObject(rowSet.getString("S1"));
			file.setObject(rowSet.getString("TOTAL_AMOUNT"));
			file.setObject(rowSet.getString("TOTAL_CREDIT_AMOUNT"));
			file.setObject(rowSet.getString("TOTAL_AMOUNT"));
			file.setObject(rowSet.getString("S2"));
			file.setObject(rowSet.getString("TOTAL_RECORD"));
			file.setObject(rowSet.getString("S3"));
			file.eol();
		}
	}

}
