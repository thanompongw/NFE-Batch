/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.batch.domain.MontranDetailBean;
import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.common.FTPFile;
import co.th.ktc.nfe.common.FileUtils;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "montranService")
@Transactional(readOnly = true)
public class MontranBO implements BatchBO {
	
	private static Logger LOG = Logger.getLogger(MontranBO.class);
	
	private static final String FUNCTION_ID = "SB040";
	
	private static final String BATCH_FILE_NAME = "MEDIACLR_LNDISB_D";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "montranDao")
	private AbstractBatchDao dao;
	
	@Autowired
	private FileUtils file;
	
	@Autowired
	private FTPFile ftpFile;

	/**
	 *  
	 */
	public MontranBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			file = new FileUtils();
			String currentDate = null;
		
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("BATCH_DATE");
			}
			
			if (dao.isDayoff(currentDate)) {
				ErrorUtil.generateError("MSTD0002AWRN", currentDate);
			}
			
			String mediaClearingDate = dao.getMediaCleringDay(currentDate);
			
			LOG.info("Media Clearing Date : " + mediaClearingDate);
			
			if (mediaClearingDate != null && !mediaClearingDate.isEmpty()) {
				download(mediaClearingDate);
				
				String effectiveDate = 
						dateUtils.convertFormatDateTime(currentDate, 
														DateUtils.DEFAULT_DATE_FORMAT, 
														"ddMMyy");
				
				LOG.info("Effective Date : " + effectiveDate);
				List<MontranDetailBean> motranDetailBeans = 
						read(mediaClearingDate, effectiveDate);
				
				dao.delete(null);
				
				insertMotran(motranDetailBeans);
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
								 MontranBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		
	}
	
	private List<MontranDetailBean> read(String mediaClearingDate, 
			                             String effectiveDate) throws CommonException {
		BeanReader in = null;
		List<MontranDetailBean> motranDetailBeans = new ArrayList<MontranDetailBean>();
		try {
			// create a BeanIO StreamFactory
			StreamFactory factory = StreamFactory.newInstance();
			// load the mapping file from the working directory			
			factory.loadResource("montran.xml");
			
			StringBuilder fileName = new StringBuilder();
			fileName.append(BATCH_FILE_NAME);
			fileName.append(mediaClearingDate);
			fileName.append("_");
			fileName.append("out");
			fileName.append(NFEBatchConstants.TXT_FILE_EXTENTION);
			
			String localTempPath = batchConfig.getPathTemp();
			
			File file = new File(localTempPath + fileName.toString());
			InputStream is = new ByteArrayInputStream(
					org.apache.commons.io.FileUtils.readFileToByteArray(file));
			Reader reader = new InputStreamReader(is, Charset.forName(FileUtils.ENCODING_TIS620));
			// create a BeanReader to read from "MEDIACLR_LNDISB_Dxxxxxx_out.TXT"
			in = factory.createReader("montran", reader);
			Object record = null;
			// read records from "input.csv"
			while ((record = in.read()) != null) {
				if (!in.getRecordName().equals("header")) {
					MontranDetailBean bean = (MontranDetailBean) record;

					if (bean.getEffectiveDate().equals(effectiveDate)) {
						break;
					} else {
						motranDetailBeans.add(bean);
					}
				}
			}
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        } finally {
			if (in != null) {
				in.close();
			}
		}
		return motranDetailBeans;
	}
	
	private void download(String mediaClearingDate) throws CommonException {
		
		StringBuilder fileName = new StringBuilder();
		fileName.append(BATCH_FILE_NAME);
		fileName.append(mediaClearingDate);
		fileName.append("_");
		fileName.append("out");
		fileName.append(NFEBatchConstants.TXT_FILE_EXTENTION);
		
		String remoteServerPath = dao.getConfigRemotePath(null);
		String localTempPath = batchConfig.getPathTemp();
		String hostName = batchConfig.getFtpHost();
		String userName = batchConfig.getFtpUserName();
		String password = batchConfig.getFtpPassword();
		Integer port = Integer.valueOf(batchConfig.getFtpPort());
		
		ftpFile.download(fileName.toString(), 
						 remoteServerPath, 
						 localTempPath, 
						 hostName, 
						 userName, 
						 password, 
						 port);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private void insertMotran(List<MontranDetailBean> montranDetailBeans) throws CommonException {
		try {

			Object[] parameter = null;
			
			for (MontranDetailBean montranDetailBean : montranDetailBeans) {
				parameter = new Object[] {montranDetailBean};
				dao.insert(parameter);
			}
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
			ErrorUtil.handleSystemException(e);
        }
	}

}
