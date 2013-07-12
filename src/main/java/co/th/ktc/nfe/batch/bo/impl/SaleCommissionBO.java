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
@Service(value = "saleCommisionService")
public class SaleCommissionBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(SaleCommissionBO.class);
	
	private static final String BATCH_FILE_NAME = "SaleInc_";
	
	@Autowired
	private BatchConfiguration config;
	
	@Resource(name = "saleCommissionDao")
	private AbstractBatchDao dao;
	
	private FileUtils file;

	/**
	 *  
	 */
	public SaleCommissionBO() {
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
			
			String dirPath = config.getPathOutput();
			
			currentDate = 
					DateTimeUtils.convertFormatDateTime(currentDate, 
														DateTimeUtils.DEFAULT_DATE_FORMAT, 
														"ddMMyy");
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

		
		SqlRowSet rowSet = dao.queryHeader(new Object[] {parameter.get("DATE_FROM"),
										 	   			 parameter.get("DATE_TO")});
		
		generateFileHeader(rowSet);

		
		rowSet = dao.queryDetail(new Object[] {parameter.get("DATE_FROM"),
                                               parameter.get("DATE_TO")});
		
		generateFileDetail(rowSet);
	}

	private void generateFileHeader(SqlRowSet rowSet) {
		
		file.setObject("ProcessingDate", "|");
        file.setObject("ProductType", "|");
        file.setObject("CardType", "|");
        file.setObject("SubCardType", "|");
        file.setObject("PlasticCode", "|");
        file.setObject("SourceCode", "|");
        file.setObject("AgentCode", "|");
        file.setObject("IDCard", "|");
        file.setObject("CustomerAccountNo", "|");
        file.setObject("ThaiName", "|");
        file.setObject("EnglishName", "|");
        file.setObject("ApplInDate", "|");
        file.setObject("ApproveDate", "|");
        file.setObject("GatewayFileDate", "|");
        file.setObject("Results", "|");
        file.setObject("ResultDescription", "|");
        file.setObject("Address", "|");
        file.setObject("ParentIDCard", "|");
        file.setObject("OccupationCode", "|");
        file.setObject("CriteriaCode", "|");
        file.setObject("DataEntrySourceCode", "|");
        file.setObject("DataEntryName", "|");
        file.setObject("ApproveAmount", "|");
        file.setObject("MoneyTransfer", "|");
        file.setObject("Application_ID", "|");
        file.setObject("BranchCode", "|");
        file.setObject("BlockCode", "|");
        file.setObject("BlockDate", "|");
        file.setObject("TotNewBal");
        file.eol();
	}

	private void generateFileDetail(SqlRowSet rowSet) {
		
		while (rowSet.next()) {
			file.setObject(rowSet.getString("PROCESSING_DATE"), "|");
			file.setObject(rowSet.getString("PRODUCT_TYPE"), "|");
			file.setObject(rowSet.getString("CARD_TYPE"), "|");
			file.setObject(rowSet.getString("SUBCARD_TYPE"), "|");
			file.setObject(rowSet.getString("PLASTIC_CODE"), "|");
			file.setObject(rowSet.getString("SOURCE_CODE"), "|");
			file.setObject(rowSet.getString("AGENT_CODE"), "|");
			file.setObject(rowSet.getString("ID_CARD"), "|");
			file.setObject(rowSet.getString("CUSTOMER_ACCOUNT_NO"), "|");
			file.setObject(rowSet.getString("THAI_NAME"), "|");
			file.setObject(rowSet.getString("ENGLISH_NAME"), "|");
			file.setObject(rowSet.getString("APPLY_DATE"), "|");
			file.setObject(rowSet.getString("APPROVE_DATE"), "|");
			file.setObject(rowSet.getString("GATEWAY_FILE_DATE"), "|");
			file.setObject(rowSet.getString("RESULTS"), "|");
			file.setObject(rowSet.getString("RESULT_DESCRIPTION"), "|");
			file.setObject(rowSet.getString("OCCUPATION_CODE"), "|");
			file.setObject(rowSet.getString("CRITERIA_CODE"), "|");
			file.setObject(rowSet.getString("DATA_ENTRY_SOURCE_CODE"), "|");
			file.setObject(rowSet.getString("DATA_ENTRY_NAME"), "|");
			file.setObject(rowSet.getString("APPROVE_AMOUNT"), "|");
			file.setObject(rowSet.getString("MONEY_TRANSFER"), "|");
			file.setObject(rowSet.getString("APPLICATION_ID"), "|");
			file.setObject(rowSet.getString("BRANCH_CODE"), "|");
			file.setObject(rowSet.getString("BLOCK_CODE"), "|");
			file.setObject(rowSet.getString("BLOCK_DATE"), "|");
			file.setObject(rowSet.getString("TOTAL_NEWBALANCE"));
			file.eol();
		}
	}

}
