/******************************************************
 * Program History
 * 
 * Project Name	            :  CRMReport
 * Client Name				:  
 * Package Name             :  co.th.genth.crm.common.util
 * Program ID 	            :  ErrorUtil.java
 * Program Description	    :  
 * Environment	 	        :  
 * Author					:  thanompongw
 * Version					:  1.0
 * Creation Date            :  9 ��.�. 2555
 *
 * Modification History	    :
 * Version	   Date		   Person Name		Chng Req No		Remarks
 *
 * Copyright(C) 2011-Generali Life Insurance (Thailand) Co.,Ltd. All Rights Reserved.             
 ********************************************************/
package co.th.ktc.nfe.common;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.batch.exception.ErrorList;

/**
 * @author Thanompong.W
 */
@Component
public class ErrorUtil {
	
	private static BatchConfiguration batchConfig;

    @Autowired
    public void setBatchConfig(BatchConfiguration batchConfig){
    	ErrorUtil.batchConfig = batchConfig;
    }
	
	public static List<String> getErrors(CommonException e) {
		List<String> messages = null;
        ErrorList errorList = e.getErrorList();
        if (errorList != null) {
            List<BusinessError> beList = errorList.getErrorList();
            messages = new ArrayList<String>();
            String message = null;
            for (BusinessError be : beList) {
                String[] stSubsValues = be.getSubstitutionValues();
                if (stSubsValues != null) {
                    String formattedSubsValues[] = new String[stSubsValues.length];
                    for (int j = 0; j < formattedSubsValues.length; j++) {
                       formattedSubsValues[j] = removeSpChar(stSubsValues[j]);
                    }
                    message = MessageUtils.getMessage(be.getErrorkey(), formattedSubsValues);
                } else {
                    message = MessageUtils.getMessage(be.getErrorkey());
                }
                messages.add(message);
            }
        }
        return messages;
    }
	
	public static void generateError(CommonException e,
	                                 String errorCode,
	                                 String[] msgArgs,
	                                 int errorIdx) {
		ErrorList obErrList = e.getErrorList();
		
		if (obErrList == null) {
			obErrList = new ErrorList();
		}
		
		obErrList.addError(errorCode, msgArgs);
		e.setErrIndex(errorIdx);
		e.setErrorList(obErrList);
	}
	
	public static void generateError(CommonException e,
	                                 String errorCode,
	                                 String msgArgs,
	                                 int errorIdx) {
		ErrorList obErrList = e.getErrorList();
		
		if (obErrList == null) {
			obErrList = new ErrorList();
		}
		
		String[] stErrMsg = { msgArgs };
		obErrList.addError(errorCode, stErrMsg);
		e.setErrIndex(errorIdx);
		e.setErrorList(obErrList);
	}
	
	public static void generateError(CommonException e, String errorCode, String msgArgs) {
		ErrorList obErrList = e.getErrorList();
		
		if (obErrList == null) {
			obErrList = new ErrorList();
		}
		if (msgArgs != null) {
			String[] stErrMsg = { removeSpChar(msgArgs) };
			obErrList.addError(errorCode, stErrMsg);
		} else {
			obErrList.addError(errorCode);
		}
		e.setErrorList(obErrList);
	}
	
	public static void generateError(CommonException e, ErrorList errList) {
		ErrorList obErrList = e.getErrorList();
		
		if (obErrList == null) {
			obErrList = new ErrorList();
		}
		if (errList != null) {
			ArrayList<BusinessError> arrErrList = obErrList.getErrorList();
			arrErrList.addAll(errList.getErrorList());
			obErrList = new ErrorList(arrErrList);
		}
		e.setErrorList(obErrList);
	}
	
	public static CommonException generateError(String errorCode, String msgArgs) {
		CommonException e = new CommonException(errorCode);
		generateError(e, errorCode, msgArgs);
		return e;
	}
	
	public static CommonException generateError(String errorCode, 
												String msgArgs, 
												int errorIdx) {
		CommonException e = new CommonException(errorCode);
		generateError(e, errorCode, msgArgs, errorIdx);
		return e;
	}
	
	public static CommonException generateError(String errorCode, 
												String[] msgArgs, 
												int errorFlag) {
		CommonException e = new CommonException(errorCode);
		generateError(e, errorCode, msgArgs, errorFlag);
		return e;
	}
	
	public static CommonException generateError(ErrorList ErrorList) {
		return new CommonException(ErrorList);
	}
	
	public static String removeSpChar(String stSource) {
		return stSource == null ? "" : 
			(((stSource.replace((char) 0xd, (char) 0x0))
		        .replace((char) 0xa, (char) 0x0))
		        .replace('\'', (char) 0x0))
		        .replace('\"', (char) 0x0);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getMessages(Array arr) throws SQLException {
		List arrRes = new ArrayList();
		if (arr != null) {
			String[] msgs = (String[]) arr.getArray();
			for (int i = 0; i < msgs.length; i++)
				arrRes.add(msgs[i]);
		}
		return arrRes;
	}
	
	/**
	 * Use this function to handle specific Oracle Error only. For other
	 * database this method can not be used
	 * 
	 * @param e
	 * @throws CommonException
	 */
	public static void handleCommonException(SQLException e) throws CommonException {
		if (e.getErrorCode() == 54) {
			throw ErrorUtil.generateError(batchConfig.getConcurrencyLocked(),
			                              e.getMessage());
		} else if (e.getMessage().toLowerCase().indexOf("io exception") > -1) {
			throw ErrorUtil.generateError(batchConfig.getConnectionFailed(), null);
		} else {
			throw ErrorUtil.generateError(batchConfig.getUndefinedError(),
			                              e.getMessage());
		}
	}
	
	public static void handleSystemException(Exception e) throws CommonException {
		throw ErrorUtil.generateError(batchConfig.getUndefinedError(),
		                              e.getMessage());
	}
}
