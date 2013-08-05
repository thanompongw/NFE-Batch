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

/**
 * @author Deedy
 *
 */
@Service(value = "paymentChequePartnerService")
public class PaymentChequePartnerBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(PaymentChequePartnerBO.class);
	
	private static final String FUNCTION_ID = "S3016";
	
	private static final String REPORT_FILE_NAME = "PaymentChequePartnerReport";
	
	private Integer[] printDateRowColumn = new Integer[] {2, 6};
	private Integer[] printTimeRowColumn = new Integer[] {3, 6};
	private Integer[] reportDateRowColumn = new Integer[] {3, 3};
	private Integer[] pageNoRowColumn = new Integer[] {2, 8};
	
	@Resource(name = "paymentChequePartnerDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of PaymentChequePartnerBO Class.
	 */
	public PaymentChequePartnerBO() {
	}

	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		try {
			poi = new CommonPOI(REPORT_FILE_NAME, batchConfig.getPathTemplate());
			
			String currentDate = null;
			
			if (parameter == null) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");

			} else {
				currentDate = parameter.get("REPORT_DATE");
			}
			
			String fromTimestamp = currentDate + " 00:00:00";
			String toTimestamp = currentDate + " 23:59:59";
			
			LOG.info("Report DateTime From : " + fromTimestamp);
			LOG.info("Report DateTime To : " + toTimestamp);			

			parameter.put("REPORT_DATE", currentDate);
			parameter.put("PRINT_DATE", 
					DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_DATE_FORMAT));
			parameter.put("PRINT_TIME", 
					DateTimeUtils.getCurrentDateTime(DateTimeUtils.DEFAULT_TIME_FORMAT));
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);
			
			Workbook report = generateReport(parameter);
			
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
								 PaymentChequePartnerBO.class);
			}
		}
		return processStatus;
	}

	public Workbook generateReport(Map<String, String> parameter) throws CommonException {
		
		Workbook workbook = poi.getWorkBook();
		int detailSheetNo = 0;
		
		SqlRowSet rowSet = dao.query(new Object[] {parameter.get("DATE_FROM"),
												   parameter.get("DATE_TO")});
			
		String sheetName = 
				DateTimeUtils.convertFormatDateTime(parameter.get("REPORT_DATE"), 
													DateTimeUtils.DEFAULT_DATE_FORMAT, 
													"dd-MM-yyyy");

    	this.generateReport(workbook,
							rowSet, 
							detailSheetNo,
							sheetName, 
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
			// Page No
			poi.setObject(curSheet, 
						  pageNoRowColumn[0], 
						  pageNoRowColumn[1], 
						  1);
			
			int lastRow = curSheet.getLastRowNum();
			int minColIx = curSheet.getRow(lastRow).getFirstCellNum();
			int maxColIx = curSheet.getRow(lastRow).getLastCellNum();
			
			int dataRows = lastRow - 4;
			int rowCopy = lastRow - 4;
			int dataColumnIndex = 0;
			
			double sumTotalAmount = 0.0D;
			String cardType = null;
			
			Integer totalRecord = 0;
			
			while (rowSet.next()) {
                poi.copyRow(sheetNo,
                            templateSheetNo,
                            dataRows,
                            rowCopy,
                            minColIx,
                            maxColIx - 1);
                cardType = rowSet.getString("CARD_TYPE");
				//SEQ.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getRow());
				//ACCOUNT_NO.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("ACCOUNT_NO"));
				//CUSTOMER_NAME_THAI.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("CUSTOMER_NAME_THAI"));
				//CUSTOMER_NAME_ENGLISH.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("CUSTOMER_NAME_ENGLISH"));
				//BANK_NAME.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("BANK_NAME"));
				//BANK_ACCOUNT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BANK_ACCOUNT"));
				//TOTAL_AMOUNT.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getDouble("TOTAL_AMOUNT"));
				
				if (!rowSet.isLast()) {
					dataColumnIndex = 0;					
				}
				dataRows++;
				
				sumTotalAmount += rowSet.getDouble("TOTAL_AMOUNT");
				totalRecord++;
			}
			
			if (totalRecord != 0) {
				// Card Type
				poi.setObject(curSheet, 
							  4, 
							  1, 
							  cardType);
				//TOTAL_AMOUNT.
				poi.setObject(curSheet, 
						  	  dataRows + 1, 
						  	  dataColumnIndex - 1,
						  	  sumTotalAmount);
			}
			
			workbook.removeSheetAt(templateSheetNo);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
		}
	}

}
