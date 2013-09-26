/**
 * 
 */
package co.th.ktc.nfe.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.th.ktc.nfe.batch.exception.CommonException;


/**
 * @author temp_dev2
 *
 */
@Component(value = "ftpFile")
public class FTPFile {
	
	private static Logger LOG = Logger.getLogger(FTPFile.class);

	//Constants for default values.
	private static final int DEF_BUF_SIZE = 1024; //Default buffer size.

	private static final int DEF_TIMEOUT = 300000; //Default time out - millisecond.
	
	public static final int MODE_FTP = 1; //Mode SFTP

	public static final int MODE_SFTP = 2; //Mode SFTP
	
	private FTPClient ftpClient;
	
	private StandardFileSystemManager manager;
	
	@Autowired
	private BatchConfiguration batchConfig;

	/**
	 * Constructor of FTPFile class.
	 */
	public FTPFile() {
		//Create FTPClient instance.
		ftpClient = new FTPClient();

		//Set Default Timeout.
		ftpClient.setDefaultTimeout(DEF_TIMEOUT);
		
		manager = new StandardFileSystemManager();
		
	}
	
	private FileSystemOptions createDefaultOptions()
	        throws FileSystemException {
	    // Create SFTP options
	    FileSystemOptions opts = new FileSystemOptions();
	 
	    // SSH Key checking
	    SftpFileSystemConfigBuilder.getInstance()
	    						   .setStrictHostKeyChecking(opts, "no");
	 
	    // Root directory set to user home
	    SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
	 
	    // Timeout is count by Milliseconds
	    SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, DEF_TIMEOUT);
	 
	    return opts;
	}
	
	public static String createSFTPURL(String hostName,
            						   String userName, 
            						   String password, 
            						   String remoteFilePath) {
		StringBuilder sftpURL = new StringBuilder();
		sftpURL.append("sftp://");
		sftpURL.append(userName);
		sftpURL.append(":");
		sftpURL.append(password);
		sftpURL.append("@");
		sftpURL.append(hostName);
		sftpURL.append("/");
		sftpURL.append(remoteFilePath);
        // result: "sftp://username:password@hostname/filename
        return sftpURL.toString();
    }

	/**
	 * upload method is used to transfer the file specified
	 * to upload location.
	 * 
	 * @param : fileID - File ID.
	 * @param : tempUpLocation - Temporary Upload Location.
	 * @param : uploadLocation - Upload Location.
	 * @throws : Exception
	 */
	public void upload(String fileName,
					   String tempUpLocation, 
					   String upLocation,
					   String serverName,
					   String userName,
					   String password,
					   int port,
					   int mode)
			throws CommonException {

		if (mode == MODE_FTP) {
			uploadFTP(fileName, 
					  tempUpLocation, 
					  upLocation, 
					  serverName, 
					  userName, 
					  password, 
					  port);
		} else if (mode == MODE_SFTP) {
			
			uploadSFTP(fileName, 
					   tempUpLocation, 
					   upLocation, 
					   serverName, 
					   userName, 
					   password, 
					   port);
		}
	}

	/**
	 * upload method is used to transfer the file specified
	 * to upload location.
	 * 
	 * @param : fileName - File Name.
	 * @param : localFilePath - Local File Path.
	 * @param : remoteFilePath - Remote Server File Path.
	 * @param : serverName - Remote Server Name.
	 * @param : userName - Remote Server User Name.
	 * @param : password - Remote Server Password.
	 * @param : port - Server Port.
	 * @throws : Exception
	 */
	public void uploadFTP(String fileName,
					      String localFilePath, 
					      String remoteFilePath,
					      String serverName,
					      String userName,
					      String password,
					      int port)
			throws CommonException {

		//The declaration of variables.
		boolean isFileExist = false; //File Exist Flag.
		BufferedInputStream bis = null; //BufferedInputStream object.
		org.apache.commons.net.ftp.FTPFile[] ftpFiles = null; //FTP File List.
		int i = 0; //Counter.
		int max = 0; //Max Value.
		int replyCode = 0; //Reply Code.
		InputStream is = null; //InputStream object.
		long fileSize = 0; //File Size.
		String[] msgArgs = null; //String Array for message.

		try {
			//Connect to FTP server.
			ftpClient.connect(serverName, port);
			//Get Reply Code.
			replyCode = ftpClient.getReplyCode();

			//Check Reply Code.
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				// MSTD1035AERR: Failed to connect to FTP Server [{0}].
				throw ErrorUtil.generateError("MSTD1035AERR",
											  serverName);
			}

			//Log in FTP server.
			if (!ftpClient.login(userName, password)) {
				msgArgs = new String[3];
				msgArgs[0] = serverName;;
				msgArgs[1] = userName;
				msgArgs[2] = password;
				//MSTD1036AERR: Failed to log in FTP Server. FTP Server :
				// [{0}], User Name : [{1}], Password : [{2}].
				throw ErrorUtil.generateError("MSTD1036AERR", msgArgs, 0);
			}
			
			LOG.info("Local File Path : " + localFilePath);
			LOG.info("File Name : " + fileName);

			//Create InputStream object.
			is = new FileInputStream(localFilePath + "/" + fileName);
			//Get File Size.
			fileSize = is.available();
			//Create BufferedInputStream object.
			bis = new BufferedInputStream(is);

			//Set Local Passive Mode.
			ftpClient.enterLocalPassiveMode();
			//Set File Type as binary.
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			//Set Default buffer size
			ftpClient.setBufferSize(DEF_BUF_SIZE);
			
			LOG.info("Remote File Path : " + remoteFilePath);
			
			//Store File.
			ftpClient.storeFile(remoteFilePath  + "/" + fileName, bis);

			//Close BufferedInputStream object.
			bis.close();

			//Change Directory.
			ftpClient.changeWorkingDirectory(remoteFilePath);
			//Get File List in Upload Location.
			ftpFiles = ftpClient.listFiles();
			//Get length of alFtpFile.
			max = ftpFiles.length;

			//Check size of uploaded file.
			for (i = 0; i < max; i++) {
				//Compare File Name.
				if (fileName.equals(ftpFiles[i].getName())) {
					isFileExist = true;
					//Compare lFileSize with uploaded file size.
					if (fileSize == ftpFiles[i].getSize()) {
						break;
					} else {
						msgArgs = new String[2];
						msgArgs[0] = String.valueOf(ftpFiles[i].getSize());
						msgArgs[1] = String.valueOf(fileSize);
						//MSTD1037AERR: Failed to upload the file. Size of
						// uploaded file is not same with size of source file.
						// Size of uploaded file : [{0}]byte, Size of source
						// file : [{1}]byte.
						throw ErrorUtil.generateError("MSTD1037AERR", msgArgs, 0);
					}
				}
			}

			//Check existence of uploaded file.
			if (!isFileExist) {
				//MSTD1038AERR: Failed to upload the file. Uploaded File
				// doesn't exist in Upload Location.
				throw ErrorUtil.generateError("MSTD1038AERR", null);
			}

			//Delete the temporary file.
//			(new File(localFilePath + fileName)).delete();
		} catch (SocketTimeoutException se) {
			LOG.fatal(se.getMessage(), se);
			//MSTD1042AERR: FTP Timeout occurs. Current Timeout :
			// [{0}millisecond] Please check value of "ftp_timeout" in
			// batch.properties.
			throw ErrorUtil.generateError("MSTD1042AERR", String.valueOf(DEF_TIMEOUT));
		} catch (UnknownHostException ue) {
			LOG.fatal(ue.getMessage(), ue);
			//MSTD1035AERR: Failed to connect to FTP Server [{0}].
			throw ErrorUtil.generateError("MSTD1035AERR", serverName);
		} catch (FileNotFoundException fe) {
			LOG.fatal(fe.getMessage(), fe);
			throw ErrorUtil.generateError(batchConfig.getFileNotExists(), fileName);
		} catch (IOException ie) {
			LOG.fatal(ie.getMessage(), ie);
			ErrorUtil.handleSystemException(ie);
		} finally {
			try {
				//Logout from FTP server.
				ftpClient.logout();
			} catch (Exception ie) {
				//Do nothing.
			}

			if (ftpClient.isConnected()) {
				try {
					//Disconnect to FTP Server.
					ftpClient.disconnect();
				} catch (Exception ie) {
					//Do nothing.
				}
			}
		}
	}

	/**
	 * upload method is used to transfer the file specified
	 * to upload location.
	 * 
	 * @param : fileID - File ID.
	 * @param : tempUpLocation - Temporary Upload Location.
	 * @param : uploadLocation - Upload Location.
	 * @throws CommonException 
	 */
	public void uploadSFTP(String fileName,
					       String localFilePath, 
					       String remoteFilePath,
					       String serverName,
					       String userName,
					       String password,
					       int port) throws CommonException {
		 
	    File localFile = new File(localFilePath + fileName);
	    if (!localFile.exists()) {
			//MSTD1038AERR: Failed to upload the file. Uploaded File
			// doesn't exist in Upload Location.
			throw ErrorUtil.generateError("MSTD1038AERR", null);
	    }
	    
	    FileObject localFileObj = null;
	    FileObject remoteFileObj = null;
	 
	    try {
	        manager.init();
	 
	        // Create local file object
	        localFileObj = manager.resolveFile(localFile.getAbsolutePath());
	        
	        String url = createSFTPURL(serverName, 
	        					       userName, 
	        					       password, 
	        					       remoteFilePath);
	 
	        // Create remote file object
	        remoteFileObj = manager.resolveFile(url, createDefaultOptions());
	 
	        // Copy local file to sftp server
	        remoteFileObj.copyFrom(localFileObj, Selectors.SELECT_SELF);
	        
	    } catch (FileSystemException fse) {
			LOG.fatal(fse.getMessage(), fse);
			ErrorUtil.handleSystemException(fse);
		} finally {
			if (localFileObj != null) {
				try {
					localFileObj.close();
				} catch (FileSystemException e) {
					//Do nothing.
				}
			}
			if (remoteFileObj != null) {
				try {
					remoteFileObj.close();
				} catch (FileSystemException e) {
					//Do nothing.
				}
			}
	    	if (manager != null) {
		        manager.close();
	    	}
	    }
	}

	/**
	 * download method is used to transfer the specified
	 * file to download location.
	 * 
	 * @param : fileID - File ID.
	 * @param : upLocation - File Upload Location.
	 * @param : tempDownLocation - Temporary Download Location.
	 * @return : String - Temporary File Name.
	 * @throws : Exception
	 */
	public String download(String fileName,
						   String upLocation, 
						   String tempDownLocation,
						   String serverName,
						   String userName,
						   String password,
						   int port)
			throws CommonException {

		//The declaration of variables.
		String[] msgArgs = null; //String Array for message.
		OutputStream os = null; //OutputStream object.
		int replyCode = 0; //Reply Code.

		try {
			//Connect to FTP server.
			ftpClient.connect(serverName, port);
			//Get Reply Code.
			replyCode = ftpClient.getReplyCode();

			//Check Reply Code.
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				//MSTD1035AERR: Failed to connect to FTP Server [{0}].
				throw ErrorUtil.generateError("MSTD1035AERR", serverName);
			}

			//Log in FTP server.
			if (!ftpClient.login(userName, password)) {
				msgArgs = new String[3];
				msgArgs[0] = serverName;;
				msgArgs[1] = userName;
				msgArgs[2] = password;
				//MSTD1036AERR: Failed to log in FTP Server. FTP Server :
				// [{0}], User Name : [{1}], Password : [{2}].
				throw ErrorUtil.generateError("MSTD1036AERR", msgArgs, 0);
			}

			//Set Local Passive Mode.
			ftpClient.enterLocalPassiveMode();
			//Set File Type as binary.
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			//Set Default buffer size
			ftpClient.setBufferSize(DEF_BUF_SIZE);

			//Create FileOutputStream object.
			os = new FileOutputStream(tempDownLocation + fileName);

			isFileExist(upLocation, fileName);//added to check file existence in server

			String remoteFilePath = upLocation + fileName;
			
			//Store File.
			boolean isRetrieveSuccess = 
					ftpClient.retrieveFile(remoteFilePath, os);
			
			//Begin: add checking whether file is exist or no in server
			if (!isRetrieveSuccess) {
				//MSTD1060AERR: File ID [{0}] does not exist in server.
				throw ErrorUtil.generateError("MSTD1060AERR", fileName);
			}
			//End: add checking whether file is exist or no in server
			//Close FileOutputStream object.
			//fouStream.close();
		} catch (SocketTimeoutException se) {
			LOG.fatal(se.getMessage(), se);
			//MSTD1042AERR: FTP Timeout occurs. Current Timeout :
			// [{0}millisecond] Please check value of "ftp_timeout" in
			// Standard.properties.
			throw ErrorUtil.generateError("MSTD1042AERR", String.valueOf(DEF_TIMEOUT));
		} catch (UnknownHostException ue) {
			LOG.fatal(ue.getMessage(), ue);
			//MSTD1035AERR: Failed to connect to FTP Server [{0}].
			throw ErrorUtil.generateError("MSTD1035AERR", serverName);
		} catch (IOException ie) {
			LOG.fatal(ie.getMessage(), ie);
			ErrorUtil.handleSystemException(ie);
		} finally {
			try {
				//Begin: add Close FileOutputStream object by
				if (os != null) {
					os.close();
				}
				//End: add Close FileOutputStream object by
				//Logout from FTP server.
				ftpClient.logout();
			} catch (Exception ie) {
				//Do nothing.
			}

			if (ftpClient.isConnected()) {
				try {
					//Disconnect to FTP Server.
					ftpClient.disconnect();
				} catch (Exception ie) {
					//Do nothing.
				}
			}
		}
		//Return Temporary File Name.
		return fileName;
	}
	
	/**
	 * deleteTargetFile method is used to upload the file specified in FormFile
	 * object.
	 * 
	 * @param : fileID - File ID.
	 * @param : location - File Location.
	 * @return Boolean
	 * @throws : Exception
	 */
	public boolean delete(String fileName, 
			              String location,
						  String serverName,
						  String userName,
						  String password,
						  int port)
			throws CommonException {

		//The declaration of variables.
		boolean isSucceed = false; //File Exist Flag.
		int replyCode = 0; //Reply Code.
		String[] msgArgs = null; //String Array for message.

		try {
			//Connect to FTP server.
			ftpClient.connect(serverName, port);
			//Get Reply Code.
			replyCode = ftpClient.getReplyCode();

			//Check Reply Code.
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				//MSTD1035AERR: Failed to connect to FTP Server [{0}].
				throw ErrorUtil.generateError("MSTD1035AERR", serverName);
			}

			//Log in FTP server.
			if (!ftpClient.login(userName, password)) {
				msgArgs = new String[3];
				msgArgs[0] = serverName;;
				msgArgs[1] = userName;
				msgArgs[2] = password;
				//MSTD1036AERR: Failed to log in FTP Server. FTP Server :
				// [{0}], User Name : [{1}], Password : [{2}].
				throw ErrorUtil.generateError("MSTD1036AERR", msgArgs, 0);
			}

			//Set Local Passive Mode.
			ftpClient.enterLocalPassiveMode();
			//Set Default buffer size
			ftpClient.setBufferSize(DEF_BUF_SIZE);

			isFileExist(location, fileName);//added to check file existence in server

			isSucceed = ftpClient.deleteFile(location + fileName);
			if (!isSucceed) {
				//MSTD1040AERR: Failed to delete the file. File ID [{0}].
				throw ErrorUtil.generateError("MSTD1040AERR", fileName);
			}

		} catch (SocketTimeoutException se) {
			LOG.fatal(se.getMessage(), se);
			//MSTD1042AERR: FTP Timeout occurs. Current Timeout :
			// [{0}millisecond] Please check value of "ftp_timeout" in
			// batch.properties.
			throw ErrorUtil.generateError("MSTD1042AERR", String.valueOf(DEF_TIMEOUT));
		} catch (UnknownHostException ue) {
			LOG.fatal(ue.getMessage(), ue);
			//MSTD1035AERR: Failed to connect to FTP Server [{0}].
			throw ErrorUtil.generateError("MSTD1035AERR", serverName);
		} catch (FileNotFoundException fe) {
			LOG.fatal(fe.getMessage(), fe);
			throw ErrorUtil.generateError(batchConfig.getFileNotExists(), fileName);
		} catch (IOException ie) {
			LOG.fatal(ie.getMessage(), ie);
			ErrorUtil.handleSystemException(ie);
		} finally {
			try {
				//Logout from FTP server.
				ftpClient.logout();
			} catch (Exception ie) {
				//Do nothing.
			}

			if (ftpClient.isConnected()) {
				try {
					//Disconnect to FTP Server.
					ftpClient.disconnect();
				} catch (Exception ie) {
					//Do nothing.
				}
			}
		}
		return isSucceed;

	}

	/**
	 * to check whether the file id is exist in server or no in case of not exist,
	 * it will throw error
	 * 
	 * @param upLocation
	 * @param fileName
	 * @throws Exception
	 */
	private void isFileExist(String upLocation,
			                 String fileName) throws CommonException {
		org.apache.commons.net.ftp.FTPFile[] ftpFiles = null;
		boolean isFileExist = false;
		try {
			ftpClient.changeWorkingDirectory(upLocation);
			ftpFiles = ftpClient.listFiles();
			int max = ftpFiles.length;

			for (int i = 0; i < max; i++) {
				if (fileName.equals(ftpFiles[i].getName())) {
					isFileExist = true;
					break;
				}
			}
			if (!isFileExist) {
				//MSTD1060AERR: File ID [{0}] does not exist in server.
				throw ErrorUtil.generateError("MSTD1060AERR", fileName);
			}
		} catch (SocketTimeoutException se) {
			LOG.fatal(se.getMessage(), se);
			//MSTD1042AERR: FTP Timeout occurs. Current Timeout :
			// [{0}millisecond] Please check value of "ftp_timeout" in
			// batch.properties.
			throw ErrorUtil.generateError("MSTD1042AERR", String.valueOf(DEF_TIMEOUT));
		} catch (FileNotFoundException fe) {
			LOG.fatal(fe.getMessage(), fe);
			throw ErrorUtil.generateError(batchConfig.getFileNotExists(), fileName);
		} catch (IOException ie) {
			LOG.fatal(ie.getMessage(), ie);
			ErrorUtil.handleSystemException(ie);
		}
	}

}
