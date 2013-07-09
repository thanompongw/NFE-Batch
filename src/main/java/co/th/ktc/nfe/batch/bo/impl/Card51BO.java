/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.DateTimeUtils;

/**
 * @author temp_dev1
 *
 */
public class Card51BO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(Card51BO.class);
	
	@Autowired
	private BatchConfiguration config;
	
	@Resource(name = "card51Dao")
	private AbstractBatchDao dao;

	/**
	 * 
	 */
	public Card51BO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		
		String currentDate = null;
	
		if (parameter == null) {
			parameter = new HashMap<String, String>();
			currentDate = dao.getSetDate("DD/MM/YYYY");
		} else {
			currentDate = parameter.get("REPORT_DATE");
		}
		
		String fromTimestamp = currentDate + " 00:00:00";
		String toTimestamp = currentDate + " 23:59:59";
		
		parameter.put("DATE_FROM", fromTimestamp);
		parameter.put("DATE_TO", toTimestamp);
		return null;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) {
		// TODO Auto-generated method stub

	}

}
