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
@Service(value = "penrollService")
public class PenrollBO implements ReportBO {
	
	private static Logger LOG = Logger.getLogger(PenrollBO.class);
	
	private static final String REPORT_FILE_NAME = "PENROLL";
	
	@Resource(name = "penrollDao")
	private AbstractReportDao dao;
	
	@Autowired
	private BatchConfiguration config;
	
	private CommonPOI poi;

	/**
	 * Default Constructor of BBCReport Class.
	 */
	public PenrollBO() {
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
				//MEMBER_ID.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("MEMBER_ID"));
				//TITLE_NAME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("TITLE_NAME"));
				//FIRST_NAME.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("FIRST_NAME"));
				//LAST_NAME.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("LAST_NAME"));
				//ADDRESS_INDICATOR.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("ADDRESS_INDICATOR"));
				//HOME_ADDRESS_LINE1.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("HOME_ADDRESS_LINE1"));
				//HOME_ADDRESS_LINE2.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_ADDRESS_LINE2"));
				//HOME_ADDRESS_LINE3.
				poi.setObject(curSheet, 
						  	  dataRows, 
						  	  dataColumnIndex++,
						  	  rowSet.getString("HOME_ADDRESS_LINE3"));
				//HOME_ADDRESS_LINE4.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_ADDRESS_LINE4"));
				//HOME_CITY_NAME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_CITY_NAME"));
				//HOME_PROVINCE_NAME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_PROVINCE_NAME"));
				//HOME_POSTAL_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_POSTAL_CODE"));
				//HOME_COUNTRY.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_COUNTRY"));
				//COMPANY_NAME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("COMPANY_NAME"));
				//BU_ADDRESS_LINE1.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_ADDRESS_LINE1"));
				//BU_ADDRESS_LINE2.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_ADDRESS_LINE2"));
				//BU_ADDRESS_LINE3.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_ADDRESS_LINE3"));
				//BU_ADDRESS_LINE4.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_ADDRESS_LINE4"));
				//BU_CITY_NAME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_CITY_NAME"));
				//BU_PROVINCE_NAME.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_PROVINCE_NAME"));
				//BU_COUNTRY.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_COUNTRY"));
				//BU_POSTAL_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_POSTAL_CODE"));
				//TELEPHONE_INDICATOR.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("TELEPHONE_INDICATOR"));
				//HOME_TEL_AREA_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_TEL_AREA_CODE"));
				//HOME_TEL_NO.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_TEL_NO"));
				//HOME_TEL_EXT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("HOME_TEL_EXT"));
				//BU_TEL_AREA_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_TEL_AREA_CODE"));
				//BU_TEL_NO.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_TEL_NO"));
				//BU_TEL_EXT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BU_TEL_EXT"));
				//MOBILE_AREA_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("MOBILE_AREA_CODE"));
				//MOBILE_NO.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("MOBILE_NO"));
				//MOBILE_NO_EXT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("MOBILE_NO_EXT"));
				//FAX_AREA_CODE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("FAX_AREA_CODE"));
				//FAX_NO.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("FAX_NO"));
				//FAX_EXT.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("FAX_EXT"));
				//EMAIL.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("EMAIL"));
				//BIRTH_DATE.
				poi.setObject(curSheet, 
						      dataRows, 
						      dataColumnIndex++,
						      rowSet.getString("BIRTH_DATE"));
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