package co.th.ktc.nfe.report.bo.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Service(value = "disbursementReportService")
public class DisbursementBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(DisbursementBO.class);
	
	private static final String FUNCTION_ID = "S3013";
	
	private static final String REPORT_FILE_NAME = "DisbursementReport";
	
	@Resource(name = "disbursementDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of DisbursementBO Class.
	 */
	public DisbursementBO() {
	}

	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		Workbook report = null;
		try {
			
			poi = new CommonPOI(REPORT_FILE_NAME, batchConfig.getPathTemplate());
			String currentDate = null;
			
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
				
			} else {
				currentDate = parameter.get("REPORT_DATE");
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
				
				parameter.put("REPORT_DATE", currentDate);
				parameter.put("PRINT_DATE", 
						dateUtils.getCurrentDateTime(DateUtils.DEFAULT_DATE_FORMAT));
				parameter.put("PRINT_TIME", 
						dateUtils.getCurrentDateTime(DateUtils.DEFAULT_TIME_FORMAT));
				parameter.put("DATE_FROM", fromTimestamp);
				parameter.put("DATE_TO", toTimestamp);
			
				// generateReport
			    report = generateReport(parameter);
				
				String fileName = REPORT_FILE_NAME;
				String dirPath = batchConfig.getPathOutput();
				
				currentDate = 
						dateUtils.convertFormatDateTime(currentDate, 
														DateUtils.DEFAULT_DATE_FORMAT, 
														"yyyyMMdd");
				
				poi.writeFile(report, fileName, dirPath, currentDate);
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

	public Workbook generateReport(Map<String, String> parameter) throws CommonException {
		
		Workbook workbook = poi.getWorkBook();
		
		Object[] sqlParemeters = 
				new Object[] {parameter.get("DATE_FROM"),
				   			  parameter.get("DATE_TO"),
				   			  NFEBatchConstants.SUCCESS_FLAG};
		SqlRowSet rowSet = dao.query(sqlParemeters);

		this.generateReport(workbook,
                			rowSet,
                			NFEBatchConstants.SUCCESS_SHEET_NO,
                			NFEBatchConstants.SUCCESS_SHEET_NAME,
                			parameter);
		sqlParemeters = 
				new Object[] {parameter.get("DATE_FROM"),
				   			  parameter.get("DATE_TO"),
				   			  NFEBatchConstants.UNSUCCESS_FLAG};
		
	    rowSet = dao.query(sqlParemeters);

		this.generateReport(workbook,
                			rowSet,
                			NFEBatchConstants.UNSUCCESS_SHEET_NO,
                			NFEBatchConstants.UNSUCCESS_SHEET_NAME,
                			parameter);

		return workbook;
	}

	private void generateReport(Workbook workbook,
							    SqlRowSet rowSet,
							    int sheetNo,
							    String sheetName,
							    Map<String, String> parameter) throws CommonException {
		
		try {
			
			workbook.cloneSheet(sheetNo);
			workbook.setSheetName(sheetNo, sheetName);
			Sheet curSheet = workbook.getSheetAt(sheetNo);
			
			int templateSheetNo = workbook.getNumberOfSheets() - 1;
			
            // Print Date
            poi.setObject(curSheet, 0, 11, parameter.get("REPORT_DATE"));
            // Print Time
            poi.setObject(curSheet, 1, 11, parameter.get("PRINT_TIME"));
            // Date of Data
            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));

			int lastRow = curSheet.getLastRowNum();
			int minColIx = curSheet.getRow(lastRow).getFirstCellNum();
			int maxColIx = curSheet.getRow(lastRow).getLastCellNum();
			
			int dataRows = lastRow;
			int dataColumnIndex = minColIx;

			while (rowSet.next()) {
				poi.copyRow(sheetNo,
							templateSheetNo,
							dataRows,
							lastRow,
							minColIx, 
							maxColIx - 1);
				// Seq.
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getRow());
				// GROUPLOAN_TYPE
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("GROUPLOAN_TYPE"));
				// DATE_APPROVE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APPROVE_DATE"));
				// ACCOUNT_NO
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("ACCOUNT_NO"));
				// ENGLISH_NAME
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("ENGLISH_NAME"));
				// CREDIT_LINE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("CREDIT_LINE"));
				// MONEY_TRANSFER
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("MONEY_TRANSFER"));
				// INTEREST_RATE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("INTEREST_RATE"));
				// BANK_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BANK_CODE"));
				// TRANSFER_ACCOUNT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("TRANSFER_ACCOUNT"));
				// ANALYST.
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("ANALYST"));
				// PRODUCT_SUBPRODUCT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("PRODUCT_SUBPRODUCT"));
				dataRows++;
				dataColumnIndex = 0;
			}

			workbook.removeSheetAt(templateSheetNo);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
		}
	}

}
