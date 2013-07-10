package co.th.ktc.nfe.common;

import java.io.File;

import org.springframework.stereotype.Component;

import co.th.ktc.nfe.constants.NFEBatchConstants;

@Component(value = "fileUtils")
public class FileUtils {
	
	StringBuilder data;
    
    /**
     * Buffer size for transferring process
     */
    public static final int BUFFER_SIZE = 4096;
    public static final String ENCODING = "UTF8";
    
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
	* @param date     notnull : system will append date value to end of report file name
	*                 null : system will skip step for append date value
	* @throws Exception
	*/
	public String writeFile(String fileName,
							String dirPath,
							String date) 
						throws Exception {
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
		
		String batchFileTrigPath = new File(dirPath, 
				batchFileTrigName.toString()).getAbsolutePath();
		 
		File file = new File(batchFilePath);
		
		org.apache.commons.io.FileUtils.writeByteArrayToFile(file, 
															 String.valueOf(data).getBytes(ENCODING));
		
		File fileTrig = new File(batchFileTrigPath);
		
		org.apache.commons.io.FileUtils.writeStringToFile(fileTrig, "");
		
		return batchFilePath;
	}
	
	public void setObject(Object obj) {
		if (obj == null) {
			data.append("");
		} else {
			data.append(obj);
		}
	}
}
