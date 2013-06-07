
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
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;


@Service(value = "cancelByOAService")
public class CancelByOABO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(CancelByOABO.class);
	
	private static final String REPORT_FILE_NAME = "CancelByOADreport";
	public static final String CREDIT_CARD_SHEET_NAME = "Credit Card";
    public static final String BUNDLE_SHEET_NAME = "Bundle";
    public static final String FIXED_LOAN_SHEET_NAME = "Fixed Loan";
    public static final String REVOLVING_LOAN_SHEET_NAME = "Revolving Loan";
	
	
	@Resource(name="cancelByOADao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration config;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of CancelByOADBO Class.
	 */
	public CancelByOABO() {
	}

	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		try {
			poi = new CommonPOI(REPORT_FILE_NAME, config.getPathTemplate());
			
			String currentDate = null;
			
			if (parameter == null || parameter.containsKey("REPORT_DATE")) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("REPORT_DATE");
			}
			
			String fromTimestamp = currentDate + " 00:00:00";
			String toTimestamp = currentDate + " 23:59:59";
			

			parameter.put("REPORT_DATE", currentDate);
			parameter.put("PRINT_DATE",DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_DATE_FORMAT));
			parameter.put("PRINT_TIME",DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_TIME_FORMAT));
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);
			
			// report
			Workbook report = generateReport(parameter);
			
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
			SqlRowSet rowSet = dao.query(new Object[] { parameter.get("CREDIT_CARD_SHEET_NAME"),
														parameter.get("CREDIT_CARD_SHEET_NAME"),
														parameter.get("CREDIT_CARD_SHEET_NAME"),
														parameter.get("CREDIT_CARD_SHEET_NAME"),
														parameter.get("DATE_FROM"),
														parameter.get("DATE_TO") });

			this.generateReport(workbook,
	                rowSet,
	                0,
	                CREDIT_CARD_SHEET_NAME,
					parameter);
			
		    rowSet = dao.query(new Object[] {   parameter.get("FIXED_LOAN_SHEET_NAME"),
		    									parameter.get("FIXED_LOAN_SHEET_NAME"),
												parameter.get("FIXED_LOAN_SHEET_NAME"),
		    									parameter.get("FIXED_LOAN_SHEET_NAME"),
	    										parameter.get("DATE_FROM"),
												parameter.get("DATE_TO") });
		    
			this.generateReport(workbook,
	                rowSet,
	                1,
	                FIXED_LOAN_SHEET_NAME,
					parameter);
			
			rowSet = dao.query(new Object[] {   parameter.get("REVOLVING_LOAN_SHEET_NAME"),
												parameter.get("REVOLVING_LOAN_SHEET_NAME"),
												parameter.get("REVOLVING_LOAN_SHEET_NAME"),
												parameter.get("REVOLVING_LOAN_SHEET_NAME"),
												parameter.get("DATE_FROM"),
												parameter.get("DATE_TO") });
			
			this.generateReport(workbook,
	                rowSet,
	                2,
	                REVOLVING_LOAN_SHEET_NAME,
					parameter);
			
			rowSet = dao.query(new Object[] { 	parameter.get("BUNDLE_SHEET_NAME"),
												parameter.get("BUNDLE_SHEET_NAME"),
												parameter.get("BUNDLE_SHEET_NAME"),
												parameter.get("BUNDLE_SHEET_NAME"),
												parameter.get("DATE_FROM"),
												parameter.get("DATE_TO") });
			
			this.generateReport(workbook,
	                rowSet,
	                3,
	                BUNDLE_SHEET_NAME,
					parameter);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void generateReport(Workbook workbook,
							    SqlRowSet rowSet,
							    int sheetNo,
							    String sheetName,
							    Map<String, String> parameter) throws Exception  {
		try {
		workbook.cloneSheet(sheetNo);
		workbook.setSheetName(sheetNo, sheetName);
		Sheet curSheet = workbook.getSheetAt(sheetNo);
		
		int templateSheetNo = workbook.getNumberOfSheets() - 1;
		
		 //SHEET 
            // Print Date
            poi.setObject(curSheet, 0, 16, parameter.get("REPORT_DATE"));
            // Print Time
            poi.setObject(curSheet, 1, 16, parameter.get("PRINT_TIME"));
            // Date of Data
            poi.setObject(curSheet, 1, 5, parameter.get("PRINT_DATE"));
	        
			
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
		          // DATE_REC
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("DATE_REC"));
		          // GROUPPRODUCT_LOANTYPE
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("GROUPPRODUCT_LOANTYPE"));
		          // APP_VSOURCE
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("APP_VSOURCE"));
		          // APPLICATION_ID
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getInt("APPLICATIONID"));
		          // ThaiName
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("THAINAME"));
		          // CitizenID
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("CITIZENID"));
		          // GROUPPRODUCT_TYPE
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("GROUPPRODUCT_TYPE"));
		          // PRODUCT_SUBPRODUCT
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("PRODUCT_SUBPRODUCT"));
		          // CREATEBY
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("CREATEBY"));
		          // APPBARCODE
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("APPBARCODE"));
		          // SOURCECODE
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("SOURCECODE"));
		          // AGENT
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("AGENT"));
		          // BRANCH
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("BRANCH"));
		          // QUEUEE
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("QUEUEE"));
		          // appstatus_description
		          poi.setObject(curSheet,
		                        dataRows,
		                        dataColumnIndex++,
		                        rowSet.getString("APPSTATUS_DESCRIPTION"));
		  	
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
