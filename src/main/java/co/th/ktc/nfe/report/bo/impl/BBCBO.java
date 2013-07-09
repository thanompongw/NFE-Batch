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
@Service(value = "BBCReportService")
public class BBCBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(BBCBO.class);
	
	private static final String REPORT_FILE_NAME = "BBCReport";
	
	private Integer[] printDateRowColumn = new Integer[] {0, 10};
	private Integer[] printTimeRowColumn = new Integer[] {1, 10};
	private Integer[] reportDateRowColumn = new Integer[] {1, 4};
	
	@Resource(name = "bbcDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration config;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of BBCReport Class.
	 */
	public BBCBO() {
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
			parameter.put("PRINT_DATE", 
					DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_DATE_FORMAT));
			parameter.put("PRINT_TIME", 
					DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_TIME_FORMAT));
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
		
		SqlRowSet rowSet = dao.query(new Object[] {parameter.get("DATE_FROM"),
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
			int minColIx = curSheet.getRow(lastRow).getFirstCellNum();
			int maxColIx = curSheet.getRow(lastRow).getLastCellNum();
			
			int dataRows = lastRow;
			int dataColumnIndex = 0;
			
			 while (rowSet.next()) {
                poi.copyRow(sheetNo,
                            templateSheetNo,
                            dataRows,
                            lastRow,
                            minColIx,
                            maxColIx - 1);
				//CUSTOMER_NAME.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("CUSTOMER_NAME"));
				//OPEN_DATE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("OPEN_DATE"));
				//AMOUNT.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getDouble("AMOUNT"));
				//TERM.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getInt("TERM"));
				//SOURCE_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("SOURCE_CODE"));
				//BRANCH_CODE.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("BRANCH_CODE"));
				//AGENT_ID.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("AGENT_ID"));
				//APPLICATION_NO.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("APPLICATION_NO"));
				//COMMINTEREST.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("COMMINTEREST"));
				//TAX.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("TAX"));
				//PRODUCT_SUBPRODUCT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("PRODUCT_SUBPRODUCT"));
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