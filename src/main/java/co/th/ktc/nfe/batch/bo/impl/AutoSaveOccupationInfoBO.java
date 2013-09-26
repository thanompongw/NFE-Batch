/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "autoImportApplicationService")
public class AutoSaveOccupationInfoBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(AutoSaveOccupationInfoBO.class);
	
	private static final String REPORT_FILE_NAME = "Import File Application-auth_CB_Coll";
	
	private static final String FUNCTION_ID = "SB023";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "autoImportApplicationDao")
	private AbstractBatchDao dao;
	
	private CommonPOI poi;

	/**
	 *  
	 */
	public AutoSaveOccupationInfoBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			String currentDate = null;
		
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("BATCH_DATE");
			}
			
			LOG.info("Batch Date : " + currentDate);
			
			poi = new CommonPOI(REPORT_FILE_NAME, batchConfig.getPathTemp());
			
			write(parameter);
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
								 AutoSaveOccupationInfoBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		List<Object[]> dataList = read(parameter);
		
		for (Object[] objects : dataList) {
			
			dao.insert(objects);
		}
	}

	private List<Object[]> read(Map<String, String> parameter) throws CommonException {
		Workbook workbook = poi.getWorkBook();
		int sheetNo = 0;
		
		Sheet sheet = workbook.getSheetAt(sheetNo);
		
		int lastRow = sheet.getLastRowNum();
		int minColIx = sheet.getRow(lastRow).getFirstCellNum();
		int maxColIx = sheet.getRow(lastRow).getLastCellNum();
		
		int dataRows = 1;
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		
		Object[] values = new Object[maxColIx];
		
		for (int i = dataRows; i < lastRow; i++) {
			values = new Object[maxColIx];
			
			for (int j = minColIx; j <= maxColIx; j++) {
				Object value = poi.getObject(sheet, i, j);
				
				values[j] = value;
			}
			
			dataList.add(values);
		}
		
		return dataList;
	}

}
