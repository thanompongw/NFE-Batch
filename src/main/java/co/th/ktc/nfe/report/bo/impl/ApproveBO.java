package co.th.ktc.nfe.report.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Service(value = "approveReportService")
public class ApproveBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(ApproveBO.class);
	
	private static final String FUNCTION_ID = "S3003";
	
	private static final String REPORT_FILE_NAME = "ApproveByDailyReport";
	
	private List<Integer> totalRecordList = new ArrayList<Integer>();
	
	@Resource(name = "approveDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of ApproveBO Class.
	 */
	public ApproveBO() {
	}

	public Integer execute(Map<String, String> parameter) {
		
		Integer processStatus = 0;
		Workbook report = null;
		try {
			String currentDate = null;
			
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
				
			} else {
				currentDate = parameter.get("REPORT_DATE");
			}
			
			CommonLogger.logStart(FUNCTION_ID, 
					              "MSTD7000BINF", 
					              new Object[] {REPORT_FILE_NAME}, 
					              ApproveBO.class);
			
			poi = new CommonPOI(REPORT_FILE_NAME, batchConfig.getPathTemplate());
			
			String fromTimestamp = currentDate + " 00:00:00";
			String toTimestamp = currentDate + " 23:59:59";
			
			LOG.info("Report DateTime From : " + fromTimestamp);
			LOG.info("Report DateTime To : " + toTimestamp);
			
			parameter.put("REPORT_DATE", currentDate);
			parameter.put("PRINT_DATE", DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_DATE_FORMAT));
			parameter.put("PRINT_TIME", DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_TIME_FORMAT));
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);

			
			parameter.put("BUNDLE_SHEET_NAME", NFEBatchConstants.BUNDLE_SHEET_NAME);
			parameter.put("FIXED_LOAN_SHEET_NAME", NFEBatchConstants.FIXED_LOAN_SHEET_NAME);
			parameter.put("REVOLVING_LOAN_SHEET_NAME", NFEBatchConstants.REVOLVING_LOAN_SHEET_NAME);
			
			// generateReport
		    report = generateReport(parameter);
			
			String fileName = REPORT_FILE_NAME;
			String dirPath = batchConfig.getPathOutput();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"yyyyMMdd");
			
			poi.writeFile(report, fileName, dirPath, currentDate);
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
								 ApproveBO.class);
			}
		}
		return processStatus;
		
	}

	public Workbook generateReport(Map<String, String> parameter) throws CommonException {
		
		Workbook workbook = poi.getWorkBook();
		
		Object[] sqlParemeters = 
				new Object[] {NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE,
				   			  NFEBatchConstants.CREDIT_CARD_BL_GROUP_LOANTYPE,
				   			  NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE,
				   			  NFEBatchConstants.CREDIT_CARD_BL_GROUP_LOANTYPE,
				   			  parameter.get("DATE_FROM"),
				   			  parameter.get("DATE_TO"),
				   			  parameter.get("DATE_FROM"),
				   			  parameter.get("DATE_TO")};
		SqlRowSet rowSet = dao.query(sqlParemeters);

		this.generateReport(workbook,
                			rowSet,
                			NFEBatchConstants.CREDIT_CARD_SHEET_NO,
                			NFEBatchConstants.CREDIT_CARD_SHEET_NAME,
                			parameter);
		
	    rowSet = dao.query(new Object[] { NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE,
	    								  NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE,
    									  parameter.get("DATE_FROM"),
										  parameter.get("DATE_TO") });

		this.generateReport(workbook,
                			rowSet,
                			NFEBatchConstants.FIXED_LOAN_SHEET_NO,
                			NFEBatchConstants.FIXED_LOAN_SHEET_NAME,
                			parameter);
		
	    rowSet = dao.query(new Object[] { NFEBatchConstants.REVOLVING_LOAN_GROUP_LOANTYPE,
	    								  NFEBatchConstants.REVOLVING_LOAN_GROUP_LOANTYPE,
    									  parameter.get("DATE_FROM"),
										  parameter.get("DATE_TO") });

		this.generateReport(workbook,
                			rowSet,
                			NFEBatchConstants.REVOLVING_LOAN_SHEET_NO,
                			NFEBatchConstants.REVOLVING_LOAN_SHEET_NAME,
                			parameter);
		
	    rowSet = dao.query(new Object[] { NFEBatchConstants.BUNDLE_GROUP_LOANTYPE,
	    								  NFEBatchConstants.BUNDLE_GROUP_LOANTYPE,
    									  parameter.get("DATE_FROM"),
										  parameter.get("DATE_TO") });

		this.generateReport(workbook,
                			rowSet,
                			NFEBatchConstants.BUNDLE_SHEET_NO,
                			NFEBatchConstants.BUNDLE_SHEET_NAME,
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
			 //SHEET NO:0 = CREDIT_CARD_SHEET
	        if (sheetNo == 0 ) {
	            // Print Date
	            poi.setObject(curSheet, 0, 17, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 17, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        } else if (sheetNo == 1) {
	             // Print Date
	            poi.setObject(curSheet, 0, 18, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 18, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        } else if (sheetNo == 2) {
	             // Print Date
	            poi.setObject(curSheet, 0, 19, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 19, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        } else{
	             // Print Date
	            poi.setObject(curSheet, 0, 17, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 17, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        }
			
			int lastRow = curSheet.getLastRowNum();
			int minColIx = curSheet.getRow(lastRow).getFirstCellNum();
			int maxColIx = curSheet.getRow(lastRow).getLastCellNum();
			
			int dataRows = lastRow;
			int dataColumnIndex = minColIx;
			
			Integer totalRecord = 0;

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
						      rowSet.getString("DATE_APPROVE"));
				// V_SOURCE
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("V_SOURCE"));
				// APPLY_ID
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getInt("APPLY_ID"));
				// ACCOUNT_NO
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("CARD_NO"));
				// CUSTOMER_NAME
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("CUSTOMER_NAME"));
				// CREDIT_LINE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("CREDIT_LINE"));
				if (sheetNo == 1 || sheetNo == 2) {
					// MONEY_TRANSFER.
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getDouble("MONEY_TRANSFER"));
				}
				// INCOME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("INCOME"));
				// FIVEX.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("FIVEX"));
				if (sheetNo == 0 || sheetNo == 3) {
					// TENPY.
					poi.setObject(curSheet, 
								  dataRows, 
								  dataColumnIndex++,
								  rowSet.getString("TENPY"));
				} else {
					// INTERESTRATE.
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("INTERESTRATE"));
				}

				// CREDIT_TYPE.
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("CREDIT_TYPE"));
				// CUSTOMER_TYPE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("CUSTOMER_TYPE"));
				if (sheetNo == 1 || sheetNo == 2) {
					// BANKCODE.
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("BANK_CODE"));
					// TRANFER_ACCOUNT.
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("ACCOUNT_NO"));
				}
				// APP_ANALYST.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APP_ANALYST"));
				// PRODUCT_SUBPRODUCT.
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("PRODUCT_SUBPRODUCT"));
				// SOURCE_CODE.
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("SOURCE_CODE"));
				if (sheetNo == 2) {
					// APPROVE_EMBOSSNAME1.
					poi.setObject(curSheet, 
								  dataRows, 
								  dataColumnIndex++,
								  rowSet.getString("APPROVE_EMBOSSNAME1"));
				}
				if (sheetNo == 0 || sheetNo == 3) {
					// APPROVE_EMBOSSNAME1.
					poi.setObject(curSheet, 
								  dataRows, 
								  dataColumnIndex++,
								  rowSet.getString("APPROVE_EMBOSSNAME1"));
					// APPROVE_EMBOSSNAME2.
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("APPROVE_EMBOSSNAME2"));
				}
				totalRecord++;
				dataRows++;
				dataColumnIndex = 0;
			}
			
			totalRecordList.add(totalRecord);

			workbook.removeSheetAt(templateSheetNo);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

}
