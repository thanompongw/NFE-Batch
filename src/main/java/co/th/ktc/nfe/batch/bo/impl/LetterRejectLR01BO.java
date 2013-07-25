/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.common.FileUtils;

/**
 * @author temp_dev1
 *
 */
@Service(value = "letterRejectLR01Service")
public class LetterRejectLR01BO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(LetterRejectLR01BO.class);
	
	private static final String BATCH_FILE_NAME = "LR01_";
	
	@Autowired
	private BatchConfiguration config;
	
	@Resource(name = "letterRejectLR01Dao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public LetterRejectLR01BO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			file = new FileUtils();
			String currentDate = null;
		
			if (parameter == null) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("BATCH_DATE");
			}
			
			String fromTimestamp = currentDate + " 00:00:00";
			String toTimestamp = currentDate + " 23:59:59";
			
			parameter.put("DATE_FROM", fromTimestamp);
			parameter.put("DATE_TO", toTimestamp);
			
			// generateReport
		    write(parameter);
			
			String dirPath = config.getPathOutputCSP();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"yyMMdd");
			file.writeFile(BATCH_FILE_NAME, dirPath, currentDate);
		} catch (Exception e) {
			processStatus = 1;
			e.printStackTrace();
			//TODO: throws error to main function
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) {
		
		generateFileHeader();
		
		SqlRowSet rowSet = dao.queryDetail(new Object[] {parameter.get("DATE_FROM"),
                                                         parameter.get("DATE_TO"),
                                                         parameter.get("DATE_FROM"),
                                                         parameter.get("DATE_TO"),
                                                         parameter.get("DATE_FROM"),
                                                         parameter.get("DATE_TO")});
		
		generateFileDetail(rowSet);
	}

	private void generateFileHeader() {
		
		file.setObject("FirstLastName", "|");
        file.setObject("AddressLine1", "|");
        file.setObject("AddressLine2", "|");
        file.setObject("AddressLine3", "|");
        file.setObject("AddressLine4", "|");
        file.setObject("AddressLine5", "|");
        file.setObject("ApplicationNo", "|");
        file.setObject("Date", "|");
        file.setObject("DateLetter", "|");
        file.setObject("ApplicationNo", "|");
        file.setObject("ProductName", "|");
        file.setObject("RejectCode", "|");
        file.setObject("ContactName");
        file.eol();
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("CUSTOMER_NAME"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE1"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE2"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE3"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE4"), "|");
			file.setObject(rowSet.getString("ADDRESSLINE5"), "|");
			file.setObject(rowSet.getString("DATES"), "|");
			file.setObject(rowSet.getString("DATE_LETER"), "|");
			file.setObject(rowSet.getString("APPLICATION_NO"), "|");
			file.setObject(rowSet.getString("PRODUCT_NAME"), "|");
			file.setObject(rowSet.getString("REJECT_DESC"), "|");
			file.setObject(rowSet.getString("CONTACT"));
			file.eol();
		}
	}

}
