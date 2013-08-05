/******************************************************
 * Program History
 * 
 * Project Name	            :  NFE
 * Client Name				:  
 * Package Name             :  co.th.ktc.nfe.batch.exception
 * Program ID 	            :  CommonException.java
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

/**
 * @author Thanompong.W
 */
public class CommonException extends Exception {
	/** serialVersionUID property **/
    private static final long serialVersionUID = -393747328869623929L;
	protected ErrorList errorList = null;
	protected int errIndex;
	
	public CommonException() {
		super();
	}
	
	public CommonException(String msg) {
		super(msg);
		
	}
	
	public int getErrIndex() {
		return errIndex;
	}
	
	public void setErrIndex(int errIndex) {
		this.errIndex = errIndex;
	}
	
	public CommonException(ErrorList errorList) {
		this.errorList = errorList;
	}
	
	public ErrorList getErrorList() {
		return errorList;
	}
	
	public void setErrorList(ErrorList errorList) {
		this.errorList = errorList;
	}
	
}
