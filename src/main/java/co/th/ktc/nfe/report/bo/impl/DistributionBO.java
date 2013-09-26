package co.th.ktc.nfe.report.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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

@Service(value = "distributionService")
public class DistributionBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(DistributionBO.class);
	
	private static final String FUNCTION_ID = "S3014";
	
	private static final String REPORT_CC17_FILE_NAME = "CC17";
	private static final String REPORT_PL17_FILE_NAME = "PL17";
	
	private Integer[] CC17PrintDateRowColumn = new Integer[] {0, 16};
	private Integer[] CC17PrintTimeRowColumn = new Integer[] {1, 16};
	private Integer[] CC17ReportDateRowColumn = new Integer[] {1, 9};
	
	private Integer[] PL17PrintDateRowColumn = new Integer[] {0, 13};
	private Integer[] PL17PrintTimeRowColumn = new Integer[] {1, 13};
	private Integer[] PL17ReportDateRowColumn = new Integer[] {1, 8};
	
	@Resource(name = "distributionDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of DistributionBO Class.
	 */
	public DistributionBO() {
	}

	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		Workbook report = null;
		List<String> fileTypes = new ArrayList<String>();
		try {
			fileTypes.add(REPORT_CC17_FILE_NAME);
			fileTypes.add(REPORT_PL17_FILE_NAME);
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
			
			for (String fileType : fileTypes) {
			
				poi = new CommonPOI(fileType, batchConfig.getPathTemplate());
				
				parameter.put("FILE_TYPE", fileType);
				
				// generateReport
			    report = generateReport(parameter);			
				
				String fileName = fileType;
				String dirPath = batchConfig.getPathOutput();
				
				String reportDate = 
						dateUtils.convertFormatDateTime(currentDate, 
														DateUtils.DEFAULT_DATE_FORMAT, 
														"yyyyMMdd");
				
				poi.writeFile(report, fileName, dirPath, reportDate);
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
								 DistributionBO.class);
			}
		}
		return processStatus;
		
	}

	public Workbook generateReport(Map<String, String> parameter) throws CommonException {
		
		Workbook workbook = poi.getWorkBook();
		
		List<String> statusCodes = new ArrayList<String>();
		statusCodes.add(NFEBatchConstants.APPROVE_STATUS_CODE);
		statusCodes.add(NFEBatchConstants.CANCEL_BY_CAU_STATUS_CODE);
		statusCodes.add(NFEBatchConstants.DECLINE_STATUS_CODE);
		statusCodes.add(NFEBatchConstants.REJECT_STATUS_CODE);
		
		if (parameter != null && !parameter.isEmpty()) {
			if (parameter.get("FILE_TYPE").equals(REPORT_CC17_FILE_NAME)) {
				
				workbook.cloneSheet(NFEBatchConstants.CREDIT_CARD_SHEET_NO);
				workbook.setSheetName(NFEBatchConstants.CREDIT_CARD_SHEET_NO, 
									  NFEBatchConstants.CREDIT_CARD_SHEET_NAME);
				Sheet curSheet = workbook.getSheetAt(NFEBatchConstants.CREDIT_CARD_SHEET_NO);
				int templateSheetNo = workbook.getNumberOfSheets() - 1;
				
				for (String statusCode : statusCodes) {
					String statusTrackingCode = null;
					if (statusCode.equals(NFEBatchConstants.REJECT_STATUS_CODE)) {
						statusTrackingCode = NFEBatchConstants.REJECT_STATUS_CODE;
					} else {
						statusTrackingCode = NFEBatchConstants.FINAL_RESOLVE_STATUS_CODE;
					}
					Object[] sqlParemeters = 
							new Object[] {statusCode,
										  statusTrackingCode,
				   			  			  parameter.get("DATE_FROM"),
				   			  			  parameter.get("DATE_TO"),
				   			  			  statusCode,
				   			  			  statusTrackingCode,
				   			  			  parameter.get("DATE_FROM"),
				   			  			  parameter.get("DATE_TO")};
					SqlRowSet rowSet = dao.query(sqlParemeters);
					
					parameter.put("STATUS_CODE", statusCode);

					this.generateReport(workbook,
										curSheet,
			                			rowSet,
			                			NFEBatchConstants.CREDIT_CARD_SHEET_NO,
			                			parameter);
				}
				
				workbook.removeSheetAt(templateSheetNo);
			} else {
				List<String> groupProductTypes = new ArrayList<String>();
				groupProductTypes.add(NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE);
				groupProductTypes.add(NFEBatchConstants.REVOLVING_LOAN_GROUP_LOANTYPE);
				
				for (int i = 0; i < groupProductTypes.size(); i++) {
					String groupProductType = groupProductTypes.get(i);
					
					String sheetName = null;
					if (groupProductType.equals(NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE)) {
						sheetName = NFEBatchConstants.FIXED_LOAN_SHEET_NAME;
					} else {
						sheetName = NFEBatchConstants.REVOLVING_LOAN_SHEET_NAME;
					}
					
					workbook.cloneSheet(i);
					workbook.setSheetName(i, sheetName);
					Sheet curSheet = workbook.getSheetAt(i);
					
					int templateSheetNo = workbook.getNumberOfSheets() - 1;
					
					for (String statusCode : statusCodes) {
						String statusTrackingCode = null;
						if (statusCode.equals(NFEBatchConstants.REJECT_STATUS_CODE)) {
							statusTrackingCode = NFEBatchConstants.REJECT_STATUS_CODE;
						} else {
							statusTrackingCode = NFEBatchConstants.FINAL_RESOLVE_STATUS_CODE;
						}
						Object[] sqlParemeters = 
								new Object[] {statusCode,
											  groupProductType,
											  groupProductType,
											  statusTrackingCode,
					   			  			  parameter.get("DATE_FROM"),
					   			  			  parameter.get("DATE_TO")};
						
						SqlRowSet rowSet = dao.query(sqlParemeters);
						
						parameter.put("STATUS_CODE", statusCode);

						this.generateReport(workbook,
											curSheet,
				                			rowSet,
				                			i,
				                			parameter);
					}
					
					workbook.removeSheetAt(templateSheetNo);
				}
			}
		}

		return workbook;
	}

	private void generateReport(Workbook workbook,
								Sheet curSheet,
							    SqlRowSet rowSet,
							    int sheetNo,
							    Map<String, String> parameter) throws CommonException {
		
		try {
			
			String fileType = parameter.get("FILE_TYPE");
			
			Integer[] printDateRowColumn = new Integer[2];
			Integer[] printTimeRowColumn = new Integer[2];
			Integer[] reportDateRowColumn = new Integer[2];
			
			if (fileType.equals(REPORT_CC17_FILE_NAME)) {
				printDateRowColumn = CC17PrintDateRowColumn;
				printTimeRowColumn = CC17PrintTimeRowColumn;
				reportDateRowColumn = CC17ReportDateRowColumn;
			} else {
				printDateRowColumn = PL17PrintDateRowColumn;
				printTimeRowColumn = PL17PrintTimeRowColumn;
				reportDateRowColumn = PL17ReportDateRowColumn;
			}

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

			
			int templateSheetNo = workbook.getNumberOfSheets() - 1;
			Sheet templateSheet = workbook.getSheetAt(templateSheetNo);
			int rowCopy = templateSheet.getLastRowNum() - 1;
			int lastRow = 0;
			
			String totalName = null;
			String statusCode = parameter.get("STATUS_CODE");
			if (statusCode.equals(NFEBatchConstants.APPROVE_STATUS_CODE)) {
				totalName = "Approve Total: ";
				lastRow = templateSheet.getLastRowNum() - 1;
			} else if (statusCode.equals(NFEBatchConstants.CANCEL_BY_CAU_STATUS_CODE)) {
				totalName = "Cancel Total: ";
				lastRow = curSheet.getLastRowNum() + 1;
			} else if (statusCode.equals(NFEBatchConstants.DECLINE_STATUS_CODE)) {
				totalName = "Decline Total: ";
				lastRow = curSheet.getLastRowNum() + 1;
			} else if (statusCode.equals(NFEBatchConstants.REJECT_STATUS_CODE)) {
				totalName = "Reject Total: ";
				lastRow = curSheet.getLastRowNum() + 1;
			} else {
				totalName = "Total: ";
				lastRow = curSheet.getLastRowNum() + 1;
			}
			
			int minColIx = templateSheet.getRow(rowCopy).getFirstCellNum();
			int maxColIx = templateSheet.getRow(rowCopy).getLastCellNum();
			
			int dataRows = lastRow;
			int dataColumnIndex = minColIx;

			int countTotal = 0;
			while (rowSet.next()) {
				poi.copyRow(sheetNo,
                            templateSheetNo,
                            dataRows,
                            rowCopy,
                            minColIx,
                            maxColIx - 1);
				// APPNO
				poi.setObject(curSheet, 
							  dataRows, 
							  dataColumnIndex++,
							  rowSet.getString("APPNO"));
				// THAINAME
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("THAINAME"));
				// STATUS
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("STATUS"));
				// RECEIVE_DATE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("RECEIVE_DATE"));
				
				if (fileType.equals(REPORT_CC17_FILE_NAME)) {
					// BS
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("BS"));
				}
				// SOURCE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("SOURCE"));
				// AGENT_CODE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("AGENT_CODE"));
				
				if (fileType.equals(REPORT_CC17_FILE_NAME)) {
					// TYPE
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("TYPE"));
					// SUBTYPE
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("SUBTYPE"));
					
				} else {
					// LOAN_TYPE
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("LOAN_TYPE"));
				}
				// LINELIMIT
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getDouble("LINELIMIT"));
				// UPDATE_DATE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("UPDATE_DATE"));
				// REASON
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("REASON"));
				// ACCT_NO
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("ACCT_NO"));
				// APPROVE_DATE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("APPROVE_DATE"));
				
				if (fileType.equals(REPORT_CC17_FILE_NAME)) {
					// EXISTING
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("EXISTING"));
				}
				// BRANCH_CODE
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BRANCH_CODE"));
				
				if (fileType.equals(REPORT_CC17_FILE_NAME)) {
					// CITIZENID
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getString("CITIZENID"));
				} else {
					// DRAWNDOWN
					poi.setObject(curSheet, 
							      dataRows, 
							      dataColumnIndex++,
							      rowSet.getDouble("DRAWNDOWN"));
				}
				dataRows++;
				dataColumnIndex = 0;
				countTotal++;
			}
			poi.copyRow(sheetNo,
                    	templateSheetNo,
                    	dataRows,
                    	rowCopy,
                    	minColIx,
                    	maxColIx - 1);
			
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.YELLOW.index2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setAlignment(CellStyle.ALIGN_LEFT);
			style.setBorderBottom(CellStyle.BORDER_THICK);
			style.setBorderLeft(CellStyle.BORDER_THICK);
			style.setBorderRight(CellStyle.BORDER_THICK);
			style.setBorderTop(CellStyle.BORDER_THICK);
			
			Font font = workbook.createFont();
			font.setFontName("Tahoma");
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 10);
			
			style.setFont(font);
			
			poi.setStyleRow(curSheet, 
							dataRows, 
							dataRows, 
							minColIx, 
							maxColIx - 1, 
							style);
			
			CellRangeAddress range = new CellRangeAddress(dataRows, 
														  dataRows, 
														  minColIx + 1,
														  maxColIx - 1);
			
			poi.mergeCell(curSheet, 
						  range, 
						  dataRows, 
						  dataRows,
						  minColIx + 1,
						  maxColIx - 1);
			
			// Caption "TOTAL"
			poi.setObject(curSheet, 
					      dataRows, 
					      dataColumnIndex++,
					      totalName);
			// Sum TOTAL
			poi.setObject(curSheet, 
					      dataRows, 
					      dataColumnIndex++,
					      countTotal);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
		}
	}

}
