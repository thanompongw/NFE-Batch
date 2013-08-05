package co.th.ktc.nfe.common;

/******************************************************
 * Project                       :   NFE
 * Package Name                  :   co.th.ktc.nfe.common
 * Class Name                    :   CommonLogger
 * Filename                      :   CommonLogger.java
 * Prepared By                   :   Thanompong.w
 * Preparation Date              :   05/08/2013
 * Version No                    :   1.0
 * Program Description           :   The class that used to encapsulate the another logger function <br>
 *
 *
 * Modification History	:
 * Version	   Date		   Person Name		Chng Req No		Remarks
 *
 ********************************************************/

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author thanompong.w <br>
 * <br>
 * 
 *         This class encapsulates the logging function. <br>
 *         Logging will be applied to both error and process <br>
 *         There are 2 destinations of the logging, they are <br>
 *         1. File <br>
 *         As it was defined, the log target is a text file. The location of the
 *         log file <br>
 *         must be defined first in the log4j.properties file, located in the
 *         WAS classpath. <br>
 */
@Component
public class CommonLogger {

	private static ResourceBundleMessageSource messageSource;

	@Autowired
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		CommonLogger.messageSource = messageSource;
	}

	private static Logger LOG;

	/**
	 * @param systemId
	 *            subsystem code, represents the module where the message comes
	 *            from
	 * @param functionId
	 *            function id
	 * @param messageCode
	 *            the message code represents a particular message as stated in
	 *            the resource bundle
	 * @param msgArgs the list of message argument
	 * @param level
	 *            the message type, can be one of the followings <br>
	 *            1. DEBUG : debug <br>
	 *            2. INFO : information <br>
	 *            3. WARN : warning <br>
	 *            4. ERROR : error <br>
	 *            5. FATAL : fatal <br>
	 * @see NFEBatchConstants
	 * @param intDestination
	 *            the destination where the log will be pointed to. The values
	 *            can be <be> 
	 *            1. DEST_TABLE, log to database <br>
	 *            2. DEST_FILE, log to file <br>
	 *            3. DEST_BOTH, log to both table and file <br>
	 *            The value of the destination is available in the
	 *            co.th.ktc.nfe.constants.NFEBatchConstants <br>
	 * @see NFEBatchConstants
	 * @param className the logger name assigned
	 * 
	 */

	public static void log(String systemId, 
						   String functionId,
						   String messageCode, 
						   String processStatus,
						   Object[] msgArgs, 
						   int level,
						   Class className) {

		CommonLogger.log("", 
						 systemId, 
						 functionId, 
						 messageCode, 
						 processStatus, 
						 msgArgs, 
						 level, 
						 className);

	}

	/**
	 * @param appId
	 *            application id
	 * @param systemId
	 *            subsystem code, represents the module where the message comes
	 *            from
	 * @param functionId
	 *            function id
	 * @param stUserId
	 *            the user who logs in
	 * @param messageCode
	 *            the message code represents a particular message as stated in
	 *            the resource bundle
	 * @param msgArgs
	 *            the list of message argument
	 * @param messageTy
	 *            the message type, can be one of the followings <br>
	 *            1. DEBUG : debug <br>
	 *            2. INFO : information <br>
	 *            3. WARN : warning <br>
	 *            4. ERROR : error <br>
	 *            5. FATAL : fatal <br>
	 * @see NFEBatchConstants
	 * @param intDestination
	 *            the destination where the log will be pointed to. The values
	 *            can be <be> 1. DEST_TABLE, log to database <br>
	 *            2. DEST_FILE, log to file <br>
	 *            3. DEST_BOTH, log to both table and file <br>
	 *            The value of the destination is available in the
	 *            th.co.toyota.standard.constant.NFEBatchConstants <br>
	 * @see NFEBatchConstants
	 * @param className
	 *            the logger name assigned
	 * 
	 */
	public static void log(String appId, 
						   String systemId,
						   String functionId,
						   String messageCode,
						   String processStatus, 
						   Object[] msgArgs, 
						   int level,
						   Class className) {

		LOG = Logger.getLogger(className);

		String message = messageSource.getMessage(messageCode, 
											      msgArgs,
												  null);

		// append module id, function id
		if ((functionId != null) && (!"".equals(functionId))) {
			message = "[" + functionId + "] " + message;
		}
		if ((systemId != null) && (!"".equals(systemId))) {
			message = "[" + systemId + "] " + message;
		}
		if ((appId != null) && (!"".equals(appId))) {
			message = "[" + appId + "] " + message;
		}

		if (level == NFEBatchConstants.DEBUG) {
			LOG.debug(message);
		} else if (level == NFEBatchConstants.INFO) {
			LOG.info(message);
		} else if (level == NFEBatchConstants.WARN) {
			LOG.warn(message);
		} else if (level == NFEBatchConstants.ERROR) {
			LOG.error(message);
		} else if (level == NFEBatchConstants.FATAL) {
			LOG.fatal(message);
		}
		LOG.removeAllAppenders();

	}
	
	/**
	 * Log stacktrace of provided Exception. The stacktrace will be written into
	 * log appender with level ERROR To log stacktrace with different level use
	 * logStackTrace(Level level, Throwable exception)
	 * 
	 * @param exception object which stacktrace to be logged
	 */
	public static void logStackTrace(Throwable exception) {
		LOG = Logger.getLogger(CommonLogger.class);
		LOG.error(null, exception);
		LOG.removeAllAppenders();
	}

	/**
	 * Log stacktrace of provided Exception. The stacktrace will be written into
	 * log appender with level ERROR To log stacktrace with different level use
	 * logStackTrace(int priority, Throwable exception)
	 * 
	 * @param priority, type of message to be logged. 
	 *        Refer to org.apache.log4j.Priority<br>
	 *            (WARN_INT, INFO_INT, DEBUG_INT, ERROR_INT, FATAL_INT)
	 * @param exception object which stacktrace to be logged
	 */
	public static void logStackTrace(int priority, Throwable exception) {
		LOG = Logger.getLogger(CommonLogger.class);

		switch (priority) {
		case NFEBatchConstants.DEBUG:
			priority = Priority.DEBUG_INT;
			break;
		case NFEBatchConstants.INFO:
			priority = Priority.INFO_INT;
			break;
		case NFEBatchConstants.WARN:
			priority = Priority.WARN_INT;
			break;
		case NFEBatchConstants.ERROR:
			priority = Priority.ERROR_INT;
			break;
		case NFEBatchConstants.FATAL:
			priority = Priority.FATAL_INT;
			break;
		}

		LOG.log(Level.toLevel(priority), null, exception);
		LOG.removeAllAppenders();
	}

	/**
	* Used for log info-process
	* @param msgCode
	* @param args, the args are messages.
	* @param prefixMessage
	*/
	public static void  logProcess(String functionId,
						   		   String messageCode,
						   		   Object[] args,
						   		   Class className) {

		CommonLogger.log(NFEBatchConstants.BATCH_APP_ID, 
				 		 NFEBatchConstants.SYSTEM_ID, 
						 functionId, 
						 messageCode, 
						 NFEBatchConstants.PROCESSING,
						 args, 
						 NFEBatchConstants.INFO,
						 className);
	}
	
	/**
	* Used for log info-start 
	* @param msgCode
	* @param args, the args are messages.
	* @param prefixMessage
	*/
	public static void  logStart(String functionId,
					     		 String messageCode,
					     		 Object[] args,
					     		 Class className) {

		CommonLogger.log(NFEBatchConstants.BATCH_APP_ID, 
				 		 NFEBatchConstants.SYSTEM_ID,
						 functionId, 
						 messageCode, 
						 NFEBatchConstants.START_PROCESS,
						 args, 
						 NFEBatchConstants.INFO,
						 className);
	}
	
	/**
	* Used for log info-end
	* @param msgCode
	* @param args, the args are messages.
	* @param prefixMessage
	*/
	public static void logEnd(String functionId,
		     		   		  String messageCode,
		     		   		  Object[] args,
		     		   		  Class className) {

		CommonLogger.log(NFEBatchConstants.BATCH_APP_ID, 
						 NFEBatchConstants.SYSTEM_ID, 
					 	 functionId, 
					 	 messageCode, 
					 	 NFEBatchConstants.END_PROCESS,
					 	 args, 
					 	 NFEBatchConstants.INFO,
					 	 className);
	}
}