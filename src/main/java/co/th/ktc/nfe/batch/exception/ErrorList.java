/******************************************************
 * Program History
 * 
 * Project Name	            :  NFE
 * Client Name				:  
 * Package Name             :  co.th.ktc.nfe.batch.exception
 * Program ID 	            :  ErrorList.java
 * Program Description	    :  
 * Environment	 	        :  
 * Author					:  thanompong.w
 * Version					:  1.0
 * Creation Date            :  02/08/2013
 *
 * Modification History	    :
 * Version	   Date		   Person Name		Chng Req No		Remarks
 *
 * Copyright(C) 2013-ITOS Consulting Co.,TLD. All Rights Reserved.                 
 ********************************************************/
package co.th.ktc.nfe.batch.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thanompong.W
 */
public class ErrorList implements Serializable {
	
	/** serialVersionUID property **/
	private static final long serialVersionUID = 8187504399984567786L;
	private List<BusinessError> errorList;
	
	/**
	 * Default Constructor of ErrorList.java
	 */
	public ErrorList() {
		this.errorList = new ArrayList<BusinessError>();
	}
	
	public ErrorList(ArrayList<BusinessError> arrErrList) {
		errorList = new ArrayList<BusinessError>(arrErrList);
	}
	
	public ArrayList<BusinessError> getErrorList() {
		return (ArrayList<BusinessError>) errorList;
	}
	
	public void addError(String errorKey, int type) {
		BusinessError bx = null;
		if (errorKey != null) {
			bx = new BusinessError(errorKey, type);
			errorList.add(bx);
		}
	}
	
	public void addError(String errorKey, String[] args) {
		BusinessError bx = null;
		if (errorKey != null) {
			bx = new BusinessError(errorKey, args);
			errorList.add(bx);
		}
	}
	
	public void addError(String errorKey) {
		BusinessError bx = null;
		if (errorKey != null) {
			bx = new BusinessError(errorKey, null);
			errorList.add(bx);
		}
	}
	
	public void addError(String errorKey, int type, String[] args) {
		BusinessError bx = null;
		if (errorKey != null) {
			bx = new BusinessError(errorKey, type, args);
			errorList.add(bx);
		}
	}
	
	public boolean isTransactionSuccess() {
		boolean isTxSuccess = true;
		if (errorList.size() == 0) {
			isTxSuccess = true;
		} else {
			BusinessError bx = null;
			for (int i = 0; i < errorList.size(); i++) {
				bx = (BusinessError) errorList.get(i);
				if (bx.getErrorType() == 3) {
					isTxSuccess = false;
					break;
				}
			}
			
		}
		return isTxSuccess;
	}
	
	public int getNumberOfErrors() {
		return errorList.size();
		
	}
	
	public CommonException createAppException() {
		CommonException cx = new CommonException(this);
		return cx;
	}
	
	public void throwExceptionIfErrors() throws CommonException {
		if (getNumberOfErrors() > 0) {
			CommonException cx = createAppException();
			throw cx;
		}
	}
	
}
