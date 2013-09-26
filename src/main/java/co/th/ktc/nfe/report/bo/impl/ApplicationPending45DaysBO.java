/**
 * 
 */
package co.th.ktc.nfe.report.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;
import co.th.ktc.nfe.report.bo.ReportBO;
import co.th.ktc.nfe.report.dao.AbstractReportDao;
import co.th.ktc.nfe.report.domain.ApplicationPendingBean;
import co.th.ktc.nfe.report.domain.DateBean;
import co.th.ktc.nfe.report.domain.RemainBean;

/**
 * @author Deedy
 *
 */
@Service(value = "applicationPending45DaysService")
public class ApplicationPending45DaysBO implements ReportBO {
	
	private static final String FUNCTION_ID = "S3004";
	
	private static Logger LOG = Logger.getLogger(ApplicationPending45DaysBO.class);
	
	private static final String REPORT_FILE_NAME = "NewAppTrackingByDailyReport";
	
	@Resource(name = "applicationPending45DaysDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of ApplicationRecieveBO Class.
	 */
	public ApplicationPending45DaysBO() {
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
								 ApplicationPending45DaysBO.class);
			}
		}
		return processStatus;

	}

	public Workbook generateReport(Map<String, String> parameter) throws CommonException {

		Workbook workbook = poi.getWorkBook();

		List<ApplicationPendingBean> pendingBeans = 
				preparedDataList(parameter, 
								 NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE);
		

		this.generateReport(workbook,
							pendingBeans,
                			NFEBatchConstants.CREDIT_CARD_SHEET_NO,
                			NFEBatchConstants.CREDIT_CARD_SHEET_NAME,
                			parameter);
		
		pendingBeans = 
				preparedDataList(parameter, 
								 NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE);
		

		this.generateReport(workbook,
							pendingBeans,
                			NFEBatchConstants.FIXED_LOAN_SHEET_NO,
                			NFEBatchConstants.FIXED_LOAN_SHEET_NAME,
                			parameter);
		
		pendingBeans = 
				preparedDataList(parameter, 
								 NFEBatchConstants.REVOLVING_LOAN_GROUP_LOANTYPE);
		

		this.generateReport(workbook,
							pendingBeans,
                			NFEBatchConstants.REVOLVING_LOAN_SHEET_NO,
                			NFEBatchConstants.REVOLVING_LOAN_SHEET_NAME,
                			parameter);
		
		pendingBeans = 
				preparedDataList(parameter, 
								 NFEBatchConstants.BUNDLE_GROUP_LOANTYPE);
		

		this.generateReport(workbook,
							pendingBeans,
                			NFEBatchConstants.BUNDLE_SHEET_NO,
                			NFEBatchConstants.BUNDLE_SHEET_NAME,
                			parameter);


		return workbook;
	}
	
	private List<ApplicationPendingBean> preparedDataList(Map<String, String> parameter, 
														  String groupLoanType) throws CommonException {
		
		List<DateBean> businessDates = dao.getBusinessBy60Days(parameter.get("REPORT_DATE"));
		List<DateBean> remainDates = null;
		DateBean remainDate = null;
		Object[] sqlParemeters = null;
		SqlRowSet rowSet = null;
		Integer totalRecieveApp = null;
		Integer totalFinalApp = null;
		Integer remainApp = null;
		ApplicationPendingBean pendingBean = null;
		List<ApplicationPendingBean> pendingBeans = new ArrayList<ApplicationPendingBean>();
		String buDateFrom = null;
		String buDateTo = null;
		String remainDateFrom = null;
		String remainDateTo = null;
		List<RemainBean> remainApps = null;
		RemainBean remainBean = null;
		Double percentage = null;
		
		for (DateBean businessDate : businessDates) {
			totalRecieveApp = 0;
			pendingBean = new ApplicationPendingBean();
			buDateFrom = dateUtils.toString((Date) businessDate.getDateFrom(), 
											DateUtils.DEFAULT_DATE_FORMAT);
			
			buDateTo = dateUtils.toString((Date) businessDate.getDateTo(), 
					  					  DateUtils.DEFAULT_DATE_FORMAT);
			
			pendingBean.setBusinessDate(buDateTo);
			
			sqlParemeters = 
					new Object[] {NFEBatchConstants.CANCEL_BY_OA_STATUS_CODE,
								  buDateFrom + " 00:00:00",
								  buDateTo + " 23:59:59",
								  groupLoanType,
								  groupLoanType};
			rowSet = dao.query(sqlParemeters);
			
			if (rowSet.next()) {
				totalRecieveApp = rowSet.getInt(1);
			}
			
			pendingBean.setTotalRecieveApp(totalRecieveApp);
			
			remainDates = dao.getRemainDays(buDateTo);
			
			remainApps = new ArrayList<RemainBean>();
			remainApp = totalRecieveApp;
			
			for (int i = 0; i < remainDates.size(); i++) {
				percentage = 0.0D;
				totalFinalApp = 0;
				remainBean = new RemainBean();
				remainDate = remainDates.get(i);
				
				remainDateFrom = dateUtils.toString((Date) remainDate.getDateFrom(), 
					    							DateUtils.DEFAULT_DATE_FORMAT);
				
				remainDateTo = dateUtils.toString((Date) remainDate.getDateTo(), 
					    						  DateUtils.DEFAULT_DATE_FORMAT);
				if (i == 0) {
					remainDateFrom = buDateFrom;
					remainDateTo = buDateTo;
				} else if (i == 7) {
					i++;
					remainDate = remainDates.get(remainDates.size() - 1);
					remainDateTo = dateUtils.toString((Date) remainDate.getDateTo(), 
						  							  DateUtils.DEFAULT_DATE_FORMAT);
				}
				if (i == remainDates.size() - 1) {
					sqlParemeters = 
							new Object[] {NFEBatchConstants.FINAL_RESOLVE_STATUS_CODE,
										  remainDateTo + " 23:59:59",
										  buDateFrom + " 00:00:00",
										  buDateTo + " 23:59:59",
										  groupLoanType,
										  groupLoanType};
				} else {
					sqlParemeters = 
							new Object[] {NFEBatchConstants.FINAL_RESOLVE_STATUS_CODE,
										  remainDateFrom + " 00:00:00",
										  remainDateTo + " 23:59:59",
										  buDateFrom + " 00:00:00",
										  buDateTo + " 23:59:59",
										  groupLoanType,
										  groupLoanType};
				}
				
				rowSet = dao.query(sqlParemeters);
				
				if (rowSet.next()) {
					totalFinalApp = rowSet.getInt(1);
				}
				
				remainApp -= totalFinalApp;
				if (totalRecieveApp != null && totalRecieveApp.doubleValue() != 0.0D) {
					percentage = (double) (remainApp.doubleValue() / 
							               totalRecieveApp.doubleValue());
				}
				
				remainBean.setRemain(remainApp);
				remainBean.setPercentage(percentage == null ? 0 : percentage);
				
				remainApps.add(remainBean);
			}
			
			pendingBean.setRemains(remainApps);
			pendingBeans.add(pendingBean);
		}
		
		return pendingBeans;
	}

	private void generateReport(Workbook workbook,
								List<ApplicationPendingBean> pendingBeans,
							    int sheetNo,
							    String sheetName,
							    Map<String, String> parameter) throws CommonException {
		try {
			workbook.cloneSheet(sheetNo);
			workbook.setSheetName(sheetNo, sheetName);
			Sheet curSheet = workbook.getSheetAt(sheetNo);
			
			int templateSheetNo = workbook.getNumberOfSheets() - 1;
	        
			int lastRow = curSheet.getLastRowNum();
			int minColIdx = curSheet.getRow(lastRow).getFirstCellNum();
			int maxColIdx = curSheet.getRow(lastRow).getLastCellNum();
			
			int dataRows = lastRow - 1;
			int dataColumnIndex = minColIdx;
			
			List<RemainBean> remainBeans = null;
			RemainBean remain = null;
			Integer grandTotalReceiveApp = 0;
			
			List<Integer> remains1 = new ArrayList<Integer>();
			List<Integer> remains2 = new ArrayList<Integer>();
			List<Integer> remains3 = new ArrayList<Integer>();
			List<Integer> remains4 = new ArrayList<Integer>();
			List<Integer> remains5 = new ArrayList<Integer>();
			List<Integer> remains6 = new ArrayList<Integer>();
			List<Integer> remains7 = new ArrayList<Integer>();
			List<Integer> remains8 = new ArrayList<Integer>();
			List<Integer> remains10 = new ArrayList<Integer>();

			for (ApplicationPendingBean pendingBean : pendingBeans) {
				poi.copyRow(sheetNo,
						    templateSheetNo,
						    dataRows,
							lastRow - 1,
							minColIdx,
							maxColIdx - 1);
	            // Business Date
	            poi.setObject(curSheet,
	                          dataRows,
	                          dataColumnIndex++,
	                          pendingBean.getBusinessDate());
	            // App Receive
	            poi.setObject(curSheet,
	                          dataRows,
	                          dataColumnIndex++,
	                          pendingBean.getTotalRecieveApp());
	            
	            remainBeans = pendingBean.getRemains();
	            for (int i = 0; i < remainBeans.size(); i++) {
	            	remain = remainBeans.get(i);
	            	
	            	switch (i) {
					case 0:
						remains1.add(remain.getRemain());
						break;
					case 1:
						remains2.add(remain.getRemain());
						break;
					case 2:
						remains3.add(remain.getRemain());
						break;
					case 3:
						remains4.add(remain.getRemain());
						break;
					case 4:
						remains5.add(remain.getRemain());
						break;
					case 5:
						remains6.add(remain.getRemain());
						break;
					case 6:
						remains7.add(remain.getRemain());
						break;
					case 7:
						remains8.add(remain.getRemain());
						break;
					case 8:
						remains10.add(remain.getRemain());
						break;

					default:
						break;
					}
		            
		            // Remain App
		            poi.setObject(curSheet,
		                          dataRows,
		                          dataColumnIndex++,
		                          remain.getRemain());
		            // Remain App
		            poi.setObject(curSheet,
		                          dataRows,
		                          dataColumnIndex++,
		                          remain.getPercentage());
	            }
	            
	            // Remark
	            poi.setObject(curSheet,
	                          dataRows,
	                          dataColumnIndex++,
	                          pendingBean.getRemark());
	  	
	            dataRows++;
	            dataColumnIndex = 0;

				poi.copyRow(sheetNo,
						    templateSheetNo,
						    dataRows,
							lastRow,
							minColIdx,
							maxColIdx - 1);
				
				grandTotalReceiveApp += pendingBean.getTotalRecieveApp();
			}
			dataColumnIndex++;

	        // Grand Total Receive App
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      grandTotalReceiveApp);
	        
	        Map<String, BigDecimal> totalRemain1 = calGrandTotalRemain(pendingBeans, remains1);

	        // Total Remain App day 1
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain1.get("TOTAL"));

	        // Total Percentage Remain App day 1
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain1.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain2 = calGrandTotalRemain(pendingBeans, remains2);

	        // Total Remain App day 2
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain2.get("TOTAL"));

	        // Total Percentage Remain App day 2
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain2.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain3 = calGrandTotalRemain(pendingBeans, remains3);

	        // Total Remain App day 3
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain3.get("TOTAL"));

	        // Total Percentage Remain App day 3
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain3.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain4 = calGrandTotalRemain(pendingBeans, remains4);

	        // Total Remain App day 4
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain4.get("TOTAL"));

	        // Total Percentage Remain App day 4
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain4.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain5 = calGrandTotalRemain(pendingBeans, remains5);

	        // Total Remain App day 5
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain5.get("TOTAL"));

	        // Total Percentage Remain App day 5
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain5.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain6 = calGrandTotalRemain(pendingBeans, remains6);

	        // Total Remain App day 6
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain6.get("TOTAL"));

	        // Total Percentage Remain App day 6
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain6.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain7 = calGrandTotalRemain(pendingBeans, remains7);

	        // Total Remain App day 7
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain7.get("TOTAL"));

	        // Total Percentage Remain App day 7
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain7.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain8 = calGrandTotalRemain(pendingBeans, remains8);

	        // Total Remain App day 8
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain8.get("TOTAL"));

	        // Total Percentage Remain App day 8
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain8.get("PERCENTAGE"));
	        
	        Map<String, BigDecimal> totalRemain10 = calGrandTotalRemain(pendingBeans, remains10);

	        // Total Remain App day 10
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain10.get("TOTAL"));

	        // Total Percentage Remain App day 10
	        poi.setObject(curSheet,
	                      dataRows,
	                      dataColumnIndex++,
	                      totalRemain10.get("PERCENTAGE"));
			
			workbook.removeSheetAt(templateSheetNo);
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
		}
		
	}
	
	private Map<String, BigDecimal> calGrandTotalRemain(List<ApplicationPendingBean> pendingBeans, 
													 	List<Integer> sumTotalRemains) {
		
		Integer sumTotalReceiveApp = 0;
		Integer sumTotalRemain = 0;
		ApplicationPendingBean pendingBean = null;
		for (int i = 0; i < sumTotalRemains.size(); i++) {
			pendingBean = pendingBeans.get(i);
			sumTotalReceiveApp += pendingBean.getTotalRecieveApp();
			sumTotalRemain += sumTotalRemains.get(i);
		}
		
		Double totalPercentage = 0.0D;
		
		if (sumTotalReceiveApp != null && sumTotalReceiveApp.doubleValue() != 0.0D) {
			totalPercentage = (double) (sumTotalRemain.doubleValue() / sumTotalReceiveApp.doubleValue());
		}
		
		Map<String, BigDecimal> total = new HashMap<String, BigDecimal>();
		
		total.put("TOTAL", new BigDecimal(sumTotalRemain));
		total.put("PERCENTAGE", new BigDecimal(totalPercentage));
		
		return total;
	}

}
