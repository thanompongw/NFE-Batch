package co.th.ktc.nfe.report.bo.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;
import co.th.ktc.nfe.report.dao.impl.ApproveReportDao;

@Component(value="approveReportService")
public class ApproveReportBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(ApproveReportBO.class);
	
	private static final String REPORT_FILE_NAME = "ApproveReportByDailyReport";
	public static final String CREDIT_CARD_SHEET_NAME = "Credit Card";
    public static final String BUNDLE_SHEET_NAME = "Bundle";
    public static final String FIXED_LOAN_SHEET_NAME = "Fixed Loan";
    public static final String REVOLVING_LOAN_SHEET_NAME = "Revolving Loan";
	
	@Resource(name="approveReportDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration config;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of ApproveReportBO Class.
	 */
	public ApproveReportBO() {
	}

	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		Workbook report = null;
		try {
			
			poi = new CommonPOI(REPORT_FILE_NAME,config.getPathTemplate());
			String currentDate = null;
			
			if (parameter == null || parameter.containsKey("REPORT_DATE")) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
//				currentDate = "12/12/2013";
				
			} else {
				currentDate = parameter.get("REPORT_DATE");
			}
			
			String fromTimestamp = currentDate + " 00:00:00";
			String toTimestamp = currentDate + " 23:59:59";
			
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
			String dirPath = config.getPathOutput();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"dd_MM_yyyy");
			
			poi.writeFile(report, fileName, dirPath, currentDate);
		} catch (Exception e) {
			processStatus = 1;
			e.printStackTrace();
			//TODO: throws error to main function
		}
		return processStatus;
		
	}

	public Workbook generateReport(Map<String, String> parameter) {
		
		Workbook workbook = poi.getWorkBook();
		
		
		try {
			SqlRowSet rowSet = dao.query(new Object[] { parameter.get("DATE_FROM"),
														parameter.get("DATE_TO"),
														parameter.get("DATE_FROM"),
														parameter.get("DATE_TO")});
			
			this.generateReport(workbook,
	                rowSet,
	                0,
	                CREDIT_CARD_SHEET_NAME,
					parameter);
			//
		    rowSet = dao.query(new Object[] {   parameter.get("FIXED_LOAN_SHEET_NAME"),
		    									parameter.get("FIXED_LOAN_SHEET_NAME"),
	    										parameter.get("DATE_FROM"),
												parameter.get("DATE_TO")},parameter.get("FIXED_LOAN_SHEET_NAME"));
		    
			this.generateReport(workbook,
	                rowSet,
	                1,
	                FIXED_LOAN_SHEET_NAME,
					parameter);
			//
			rowSet = dao.query(new Object[] {   parameter.get("REVOLVING_LOAN_SHEET_NAME"),
												parameter.get("REVOLVING_LOAN_SHEET_NAME"),
												parameter.get("DATE_FROM"),
												parameter.get("DATE_TO")},parameter.get("REVOLVING_LOAN_SHEET_NAME"));
			
			this.generateReport(workbook,
	                rowSet,
	                2,
	                REVOLVING_LOAN_SHEET_NAME,
					parameter);
			//
			rowSet = dao.query(new Object[] { 	parameter.get("BUNDLE_SHEET_NAME"),
												parameter.get("BUNDLE_SHEET_NAME"),
												parameter.get("DATE_FROM"),
												parameter.get("DATE_TO")},parameter.get("BUNDLE_SHEET_NAME"));
			
			this.generateReport(workbook,
	                rowSet,
	                3,
	                BUNDLE_SHEET_NAME,
					parameter);
			//
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

	private void generateReport(Workbook workbook,
							    SqlRowSet rowSet,
							    int sheetNo,
							    String sheetName,
							    Map<String, String> parameter) throws Exception {
		
		workbook.cloneSheet(sheetNo);
		workbook.setSheetName(sheetNo, sheetName);
		Sheet curSheet = workbook.getSheetAt(sheetNo);
		
		int templateSheetNo = workbook.getNumberOfSheets() - 1;
		
		try {
			 //SHEET NO:0 = CREDIT_CARD_SHEET
	        if(sheetNo == 0 ){
	            // Print Date
	            poi.setObject(curSheet, 0, 17, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 17, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        }
	        //SHEET NO:1 = FIXED_LOAN_SHEET
	        else if (sheetNo == 1){
	             // Print Date
	            poi.setObject(curSheet, 0, 18, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 18, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        }
	        //SHEET NO:2 = REVOLVING_LOAN_SHEET_NAME
	        else if (sheetNo == 2){
	             // Print Date
	            poi.setObject(curSheet, 0, 19, parameter.get("REPORT_DATE"));
	            // Print Time
	            poi.setObject(curSheet, 1, 19, parameter.get("PRINT_TIME"));
	            // Date of Data
	            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        }
	        //SHEET NO:3 = BUNDLE_SHEET_NAME
	        else{
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

			while (rowSet.next()) {
				if (!rowSet.isLast()) {
					poi.copyRow(sheetNo,
							templateSheetNo,
							dataRows,
							lastRow,
							minColIx, 
							maxColIx - 1,
							true);
				}
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
		                        rowSet.getInt("CREDIT_LINE"));
		          if(sheetNo == 1 || sheetNo == 2){
		              // MONEY_TRANSFER.
		              poi.setObject(curSheet,
		                            dataRows,
		                            dataColumnIndex++,
		                            rowSet.getInt("MONEY_TRANSFER"));
		          }
		          // INCOME.
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getLong("INCOME"));
		          // FIVEX.
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getDouble("FIVEX"));
		          if(sheetNo == 0 || sheetNo == 3){
		              // TENPY.
		              poi.setObject(curSheet,
		                            dataRows,
		                            dataColumnIndex++,
		                            rowSet.getString("TENPY"));
		          }
		          else{
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
		          if(sheetNo == 1 || sheetNo == 2){
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
		          if(sheetNo == 0 || sheetNo == 2){
		              // APPROVE_EMBOSSNAME1.
		              poi.setObject(curSheet,
		                            dataRows,
		                            dataColumnIndex++,
		                            rowSet.getString("APPROVE_EMBOSSNAME1"));
		          }
		          if(sheetNo == 0 || sheetNo == 3){
		              // APPROVE_EMBOSSNAME2.
		              poi.setObject(curSheet,
		                            dataRows,
		                            dataColumnIndex++,
		                            rowSet.getString("APPROVE_EMBOSSNAME2"));
		          }		
		          dataRows++;
		          dataColumnIndex = 0;
				}
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
            throw sqlEx;
        }
		 workbook.removeSheetAt(workbook.getNumberOfSheets() - 1);
	}

}
