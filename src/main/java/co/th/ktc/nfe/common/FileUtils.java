package co.th.ktc.nfe.common;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import co.th.ktc.nfe.constants.NFEBatchConstants;

@Component(value = "fileUtils")
public class FileUtils {
	
	StringBuilder data;
    
    /**
     * Buffer size for transferring process
     */
    public static final int BUFFER_SIZE = 4096;

    public static final String ENCODING_UTF8 = "UTF8";
    public static final String ENCODING_TIS620 = "TIS620";
    
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
															 String.valueOf(data).getBytes(ENCODING_UTF8));
		
		File fileTrig = new File(batchFileTrigPath);
		
		org.apache.commons.io.FileUtils.writeStringToFile(fileTrig, "");
		
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
	public List<Map<String, Object>> readFile(String fileName,
						   					  String dirPath,
						   					  String date,
						   					  Map<String, Integer> fixLenghtMap) 
						   							  throws Exception {
		StringBuilder batchFileName = new StringBuilder();
		String fileExtention = NFEBatchConstants.TXT_FILE_EXTENTION;
		
		if (date == null || date.isEmpty()) {
			batchFileName.append(fileName);
			batchFileName.append(fileExtention);
		} else {
			batchFileName.append(fileName);
//			batchFileName.append("_");
			batchFileName.append(date);
			
			batchFileName.append(fileExtention);
		}
		
		String batchFilePath = new File(dirPath, 
				batchFileName.toString()).getAbsolutePath();
		 
		File file = new File(batchFilePath);
		
		if (!file.exists()) {
			
		}
		
		return null;
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
			batchFileName.append("_");
			batchFileName.append(type);
			
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
															 String.valueOf(data).getBytes(ENCODING_UTF8));
		
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
	
	public String findFileByEffectiveDate(String fileName, 
			                              String dirPath, 
			                              String date,
			                              String effectiveDate) {
		
		StringBuilder batchFileName = new StringBuilder();
		String fileExtention = NFEBatchConstants.TXT_FILE_EXTENTION;
		String outFile = "out";
		
		if (date == null || date.isEmpty()) {
			batchFileName.append(fileName);
			batchFileName.append(fileExtention);
		} else {
			batchFileName.append(fileName);
			batchFileName.append(date);
			batchFileName.append("_");
			batchFileName.append(outFile);
			batchFileName.append(fileExtention);
		}
		
		String batchFileOutPath = new File(dirPath, 
				batchFileName.toString()).getAbsolutePath();
		
		Pattern pattern = Pattern.compile("\\" + effectiveDate + "\\b", 
				                          Pattern.CASE_INSENSITIVE);
		
		Scanner scanner = new Scanner(batchFileOutPath);
		if (scanner.hasNextLine()) {
		    String nextLine = scanner.nextLine();
		    Matcher matcher = pattern.matcher(nextLine);
		    
		    if (matcher.find()) {
		    	return batchFileName.toString();
		    }
		}
		
		return null;
	}
	
}
