package co.th.ktc.nfe.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.constants.NFEBatchConstants;

@Component
public class FileUtils {
	
	private static Logger LOG = Logger.getLogger(FileUtils.class);

	/**
     * Buffer size for transferring process
     */
    public static final int BUFFER_SIZE = 4096;

    public static final String ENCODING_UTF8 = "UTF8";
    public static final String ENCODING_TIS620 = "TIS620";
	
	private StringBuilder data;
    
    public FileUtils() {
    	data = new StringBuilder();
    }
    
    public String writeFile(String fileName, 
	            			String dirPath) throws Exception {
		String batchFilePath = this.writeFile(fileName, 
											  dirPath, 
											  null);
		return batchFilePath;
	}
	
	/**
	* Method to create excel file to destination.
	* @param fileName report file name
	* @param dirPath  path for create report file
	* @param date     not null : system will append date value to end of report file name
	*                 null : system will skip step for append date value
	* @throws Exception
	*/
	public String writeFile(String fileName,
							String dirPath,
							String date) 
						throws CommonException {
		StringBuilder batchFileName = new StringBuilder();
		StringBuilder batchFileTrigName = new StringBuilder();
		String fileExtention = NFEBatchConstants.TXT_FILE_EXTENTION;
		
		if (date == null || date.isEmpty()) {
			batchFileName.append(fileName);
			batchFileName.append(fileExtention);
		} else {
			batchFileName.append(fileName);
//			batchFileName.append("_");
			batchFileName.append(date);
			
			batchFileTrigName.append(batchFileName);
			batchFileTrigName.append("_TRIG");
			
			batchFileName.append(fileExtention);
			batchFileTrigName.append(fileExtention);
		}
		
		String batchFilePath = new File(dirPath, 
				batchFileName.toString()).getAbsolutePath();
		
		LOG.info(MessageUtils.getMessage("MSTD0001AINF", 
									     new Object[] {batchFilePath}));
		
		try {
			
			String batchFileTrigPath = new File(dirPath, 
					batchFileTrigName.toString()).getAbsolutePath();
			 
			File file = new File(batchFilePath);
			
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, 
																 String.valueOf(data).getBytes(ENCODING_UTF8));
			

			File fileTrig = new File(batchFileTrigPath);
			
			org.apache.commons.io.FileUtils.writeStringToFile(fileTrig, "");
		} catch (UnsupportedEncodingException uee) {
        	LOG.fatal(uee.getMessage(), uee);
        	throw ErrorUtil.generateError("MSTD0038AERR", fileName); 
		} catch (IOException ioe) {
        	LOG.fatal(ioe.getMessage(), ioe);
        	throw ErrorUtil.generateError("MSTD0038AERR", fileName); 
		}
		
		return batchFilePath;
	}
	
	/**
	* Method to create excel file to destination.
	* @param fileName report file name
	* @param dirPath  path for create report file
	* @param date     not null : system will append date value to end of batch file name
	*                 null : system will skip step for append date value
	* @param type     not null : system will append type value to end of batch file name
	*                 null : system will skip step for append type value
	* @throws Exception
	*/
	public String writeFile(String fileName,
							String dirPath,
							String date,
							String type) 
						throws CommonException {
		StringBuilder batchFileName = new StringBuilder();
		StringBuilder batchFileTrigName = new StringBuilder();
		String fileExtention = NFEBatchConstants.TXT_FILE_EXTENTION;
		
		if (date == null || date.isEmpty()) {
			batchFileName.append(fileName);
			batchFileName.append(fileExtention);
		} else {
			batchFileName.append(fileName);
//			batchFileName.append("_");
			batchFileName.append(date);
			batchFileName.append("_");
			batchFileName.append(type);
			
			batchFileTrigName.append(batchFileName);
			batchFileTrigName.append("_TRIG");
			
			batchFileName.append(fileExtention);
			batchFileTrigName.append(fileExtention);
		}
		
		String batchFilePath = new File(dirPath, 
				batchFileName.toString()).getAbsolutePath();
		
		LOG.info(MessageUtils.getMessage("MSTD0001AINF", 
									      new Object[] {batchFilePath}));

		try {
			
			String batchFileTrigPath = new File(dirPath, 
					batchFileTrigName.toString()).getAbsolutePath();
			 
			File file = new File(batchFilePath);
			
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, 
																 String.valueOf(data).getBytes(ENCODING_UTF8));
			

			File fileTrig = new File(batchFileTrigPath);
			
			org.apache.commons.io.FileUtils.writeStringToFile(fileTrig, "");
		} catch (UnsupportedEncodingException uee) {
        	LOG.fatal(uee.getMessage(), uee);
        	throw ErrorUtil.generateError("MSTD0038AERR", fileName); 
		} catch (IOException ioe) {
        	LOG.fatal(ioe.getMessage(), ioe);
        	throw ErrorUtil.generateError("MSTD0038AERR", fileName); 
		}
		
		return batchFilePath;
	}
	
	public void setObject(Object obj) {
		if (obj == null) {
			data.append("");
		} else {
			data.append(obj);
		}
	}
	
	public void setObject(Object obj, String delimiter) {
		if (obj == null) {
			data.append("");
		} else {
			data.append(obj);
		}
		data.append(delimiter);
	}
	
	public void eol() {
		if (data.charAt(data.length() - 1) == '|'
				|| data.charAt(data.length() - 1) == ',') {
			data.deleteCharAt(data.length() - 1);
		}
		data.append("\r\n");
	}
	
}
