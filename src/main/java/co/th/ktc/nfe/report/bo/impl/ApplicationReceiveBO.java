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
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;

/**
 * @author Deedy
 *
 */
@Service(value = "applicationReceiveService")
public class ApplicationReceiveBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(ApplicationReceiveBO.class);
	
	private static final String FUNCTION_ID = "S3002";
	
	private static final String REPORT_FILE_NAME = "ApplicationReceiveReport";
	
	private Integer[] printDateRowColumn = new Integer[] {0, 16};
	private Integer[] printTimeRowColumn = new Integer[] {1, 16};
	private Integer[] reportDateRowColumn = new Integer[] {1, 8};
	
	@Resource(name = "applicationReceiveDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of ApplicationRecieveBO Class.
	 */
	public ApplicationReceiveBO() {
	}
	
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		try {
			poi = new CommonPOI(REPORT_FILE_NAME, batchConfig.getPathTemplate());
			
			String currentDate = null;
			
			if (parameter == null || parameter.isEmpty()) {
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
			parameter.put("PRINT_DATE", dateUtils.getCurrentDateTime(DateUtils.DEFAULT_DATE_FORMAT));
			parameter.put("PRINT_TIME", dateUtils.getCurrentDateTime(DateUtils.DEFAULT_TIME_FORMAT));
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);
			
			// report
			Workbook report = generateReport(parameter);
			
			String fileName = REPORT_FILE_NAME;
			String dirPath = batchConfig.getPathOutput();
			
			currentDate = 
					dateUtils.convertFormatDateTime(currentDate, 
													DateUtils.DEFAULT_DATE_FORMAT, 
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
								 ApplicationReceiveBO.class);
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
				   			  NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE,
				   			  NFEBatchConstants.CREDIT_CARD_BL_GROUP_LOANTYPE,
				   			  NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE,
				   			  NFEBatchConstants.CREDIT_CARD_BL_GROUP_LOANTYPE,
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
		int templateSheetNo = 0;

		try {
			workbook.cloneSheet(sheetNo);
			workbook.setSheetName(sheetNo, sheetName);
			Sheet curSheet = workbook.getSheetAt(sheetNo);
			templateSheetNo = workbook.getNumberOfSheets() - 1;
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
			int dataColumnIndex = minColIdx;
			while (rowSet.next()) {
				poi.copyRow(sheetNo, 
						    templateSheetNo, 
						    dataRows, 
						    lastRow,
						    minColIdx, 
						    maxColIdx - 1);
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
				// APP_CREATEBY
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APP_CREATEBY"));
				// NCB_STATUS
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("NCB_STATUS"));
				// APP_BARCODE2
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APP_BARCODE2"));
				// APP_SOURCECODE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APP_SOURCECODE"));
				// APP_AGENT
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APP_AGENT"));
				// BRANCH
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APP_BRANCH"));
				// QUEUEE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("QUEUEE"));
				// APPSTATUS_DESCRIPTION
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APPSTATUS_DESCRIPTION"));

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
