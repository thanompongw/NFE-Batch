/**
 * 
 */
package co.th.ktc.nfe.report.bo.impl;

import java.sql.SQLException;
import java.text.ParseException;
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

import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateTimeUtils;
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
	
	private static Logger LOG = Logger.getLogger(ApplicationPending45DaysBO.class);
	
	private static final String REPORT_FILE_NAME = "NewAppTrackingByDailyReport";
	
	
	@Resource(name = "applicationPending45DaysDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration config;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of ApplicationRecieveBO Class.
	 */
	public ApplicationPending45DaysBO() {
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
		
		List<ApplicationPendingBean> pendingBeans = 
				preparedDataList(parameter, 
								 NFEBatchConstants.CREDIT_CARD_GROUP_LOANTYPE);

		try {
			

			this.generateReport(workbook,
								pendingBeans,
	                			NFEBatchConstants.CREDIT_CARD_SHEET_NO,
	                			NFEBatchConstants.CREDIT_CARD_SHEET_NAME,
	                			parameter);
//			
//		    rowSet = dao.query(new Object[] { NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE,
//		    								  NFEBatchConstants.FIXED_LOAN_GROUP_LOANTYPE,
//	    									  parameter.get("DATE_FROM"),
//											  parameter.get("DATE_TO") });
//
//			this.generateReport(workbook,
//	                			rowSet,
//	                			NFEBatchConstants.FIXED_LOAN_SHEET_NO,
//	                			NFEBatchConstants.FIXED_LOAN_SHEET_NAME,
//	                			parameter);
//			
//		    rowSet = dao.query(new Object[] { NFEBatchConstants.REVOLVING_LOAN_GROUP_LOANTYPE,
//		    								  NFEBatchConstants.REVOLVING_LOAN_GROUP_LOANTYPE,
//	    									  parameter.get("DATE_FROM"),
//											  parameter.get("DATE_TO") });
//
//			this.generateReport(workbook,
//	                			rowSet,
//	                			NFEBatchConstants.REVOLVING_LOAN_SHEET_NO,
//	                			NFEBatchConstants.REVOLVING_LOAN_SHEET_NAME,
//	                			parameter);
//			
//		    rowSet = dao.query(new Object[] { NFEBatchConstants.BUNDLE_GROUP_LOANTYPE,
//		    								  NFEBatchConstants.BUNDLE_GROUP_LOANTYPE,
//	    									  parameter.get("DATE_FROM"),
//											  parameter.get("DATE_TO") });
//
//			this.generateReport(workbook,
//	                			rowSet,
//	                			NFEBatchConstants.BUNDLE_SHEET_NO,
//	                			NFEBatchConstants.BUNDLE_SHEET_NAME,
//	                			parameter);	
//			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
	
	private List<ApplicationPendingBean> preparedDataList(Map<String, String> parameter, 
														  String groupLoanType) {
		
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
		
		try {
			for (DateBean businessDate : businessDates) {
				pendingBean = new ApplicationPendingBean();
				buDateFrom = DateTimeUtils.toString((Date) businessDate.getDateFrom(), 
												    DateTimeUtils.DEFAULT_DATE_FORMAT);
				
				buDateTo = DateTimeUtils.toString((Date) businessDate.getDateTo(), 
						  						  DateTimeUtils.DEFAULT_DATE_FORMAT);
				
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
				
				System.out.println("");

				System.out.print("Receive Date : " + buDateTo + "  ");
				System.out.print("Receive App : " + totalRecieveApp + "  ");
				
				for (int i = 0; i < remainDates.size(); i++) {
					remainBean = new RemainBean();
					remainDate = remainDates.get(i);
					
					remainDateFrom = DateTimeUtils.toString((Date) remainDate.getDateFrom(), 
						    								DateTimeUtils.DEFAULT_DATE_FORMAT);
					
					remainDateTo = DateTimeUtils.toString((Date) remainDate.getDateTo(), 
						    							  DateTimeUtils.DEFAULT_DATE_FORMAT);
					if (i == 0) {
						remainDateFrom = buDateFrom;
						remainDateTo = buDateTo;
					} else if (i == 7) {
						i++;
						remainDate = remainDates.get(remainDates.size() - 1);
						remainDateTo = DateTimeUtils.toString((Date) remainDate.getDateTo(), 
  							  								  DateTimeUtils.DEFAULT_DATE_FORMAT);
					}
					if (i == remainDates.size() - 1) {
						sqlParemeters = 
								new Object[] {NFEBatchConstants.FINAL_RESOLVE_STATUS_CODE,
											  remainDateFrom + " 23:59:59",
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
					
					System.out.print("Final Date_" + (i + 1) + " : " + remainDateTo + "  ");
					
					rowSet = dao.query(sqlParemeters);
					
					if (rowSet.next()) {
						totalFinalApp = rowSet.getInt(1);
					}
					System.out.print("Total Final App_" + (i + 1) + " : " + totalFinalApp + "  ");
					
					remainApp -= totalFinalApp;
					
					System.out.print("Remain App_" + (i + 1) + " : " + remainApp + "  ");
					
					percentage = (double) (remainApp.doubleValue() / totalRecieveApp.doubleValue());
					
					System.out.print("Remain App Per_" + (i + 1) + " : " + percentage + "  ");
					
					remainBean.setRemain(remainApp);
					remainBean.setPercentage(percentage);
					
					remainApps.add(remainBean);
				}
				
				pendingBean.setRemains(remainApps);
				
				pendingBeans.add(pendingBean);
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pendingBeans;
	}

	private void generateReport(Workbook workbook,
								List<ApplicationPendingBean> pendingBeans,
							    int sheetNo,
							    String sheetName,
							    Map<String, String> parameter) throws Exception  {
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
	            
	            for (RemainBean remain : pendingBean.getRemains()) {
		            
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
			}
			
			workbook.removeSheetAt(templateSheetNo);
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
            throw sqlEx;
        }
	}

}
