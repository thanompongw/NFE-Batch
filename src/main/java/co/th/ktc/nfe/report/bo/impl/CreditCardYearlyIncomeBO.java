/**
 * 
 */
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

import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

/**
 * @author Deedy
 *
 */
@Service(value = "creditCardYearlyIncomeService")
public class CreditCardYearlyIncomeBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(CreditCardYearlyIncomeBO.class);
	
	private static final String REPORT_FILE_NAME = "INCOME180000";
	
	private Integer[] printDateRowColumn = new Integer[] {0, 9};
	private Integer[] printTimeRowColumn = new Integer[] {1, 9};
	private Integer[] reportDateRowColumn = new Integer[] {1, 5};
	
	@Resource(name = "creditCardYearlyIncomeDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration config;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of CreditCardYearlyIncomeBO Class.
	 */
	public CreditCardYearlyIncomeBO() {
	}

	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		try {
			poi = new CommonPOI(REPORT_FILE_NAME, config.getPathTemplate());
			
			String currentDate = null;
			
			if (parameter == null) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
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
			
			Workbook report = generateReport(parameter);
			
			String fileName = REPORT_FILE_NAME;
			String dirPath = config.getPathOutput();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"yyyyMMdd");
			
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
		int detailSheetNo = 0;
		
		SqlRowSet rowSet = dao.query(new Object[] { parameter.get("DATE_FROM"),
													parameter.get("DATE_TO")});
		
		if (rowSet != null && rowSet.isBeforeFirst()) {
			
			String sheetName = 
					DateTimeUtils.convertFormatDateTime(parameter.get("REPORT_DATE"), 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"dd-MM-yyyy");

        	this.generateReport(workbook,
								rowSet, 
								detailSheetNo,
								sheetName, 
								parameter);
        } else {
        	//TODO: throws error to main function
        }
		
		return workbook;
	}

	private void generateReport(Workbook workbook,
							    SqlRowSet rowSet,
							    int sheetNo,
							    String sheetName, 
							    Map<String, String> parameter) {
		
		try {
			
			workbook.cloneSheet(sheetNo);
			workbook.setSheetName(sheetNo, sheetName);
			Sheet curSheet = workbook.getSheetAt(sheetNo);
			
			int templateSheetNo = workbook.getNumberOfSheets() - 1;
			
			//HEADER REPORT
			// Print Date:
			poi.setObject(curSheet, 
						  printDateRowColumn[0], 
						  printDateRowColumn[1], 
						  parameter.get("PRINT_DATE"));
			// Print Time:
			poi.setObject(curSheet, 
						  printTimeRowColumn[0], 
						  printTimeRowColumn[1], 
						  parameter.get("PRINT_TIME"));
			// Report Date:
			poi.setObject(curSheet, 
						  reportDateRowColumn[0], 
						  reportDateRowColumn[1], 
						  parameter.get("REPORT_DATE"));
			
			int lastRow = curSheet.getLastRowNum();
			int minColIdx = curSheet.getRow(lastRow).getFirstCellNum();
			int maxColIdx = curSheet.getRow(lastRow).getLastCellNum();
			
			int dataRows = lastRow;
			int dataColumnIndex = 0;
			
			 while (rowSet.next()) {
                poi.copyRow(sheetNo,
                            templateSheetNo,
                            dataRows,
                            lastRow,
                            minColIdx,
                            maxColIdx - 1);
				//No.
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getRow());
				//CARDHOLDERNO.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("CARDHOLDER_NO"));
				//NAMES
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("NAMES"));
				//CITIZENID
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("CITIZEN_ID"));
				//OPENDATE
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("OPENDATE"));
				//CREDIT_LIMIT
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getDouble("CREDIT_LIMIT"));
				//ANNUAL_INCOME
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getDouble("ANNUAL_INCOME"));
				//CUSTOMER_TYPE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("CUSTOMER_TYPE"));
				//LAST_MAINTAIN_DATE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("LAST_MAINTAIN_DATE"));
				//MAINTAIN_OPER_ID
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("MAINTAIN_OPER_ID"));
				dataColumnIndex = 0;
				dataRows++;
			}
			workbook.removeSheetAt(templateSheetNo);
		} catch (Exception e) {
			e.printStackTrace();
			//TODO: throws error to main function
		}
	}

}
