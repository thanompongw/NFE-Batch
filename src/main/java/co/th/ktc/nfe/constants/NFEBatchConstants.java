/**
 * 
 */
package co.th.ktc.nfe.constants;

/**
 * @author Deedy
 *
 */
public interface NFEBatchConstants {
	
	public static final String UNDEFINED_ERR = "undefined_err";
	public static final String ERR_CONCURRENT_LOCK = "concurrency_by_locked";
	public static final String DATA_USED = "data_used";
	public static final String CON_FAILED = "con_failed";
	
 	public static final String LOG_INFO = "I";
 	public static final String LOG_WARNING = "W";
 	public static final String LOG_ERROR = "E";
 	public static final String LOG_FATAL = "F";
 	public static final String LOG_DEBUG = "D";
 	
 	public static final int DEBUG = 1;
 	public static final int INFO = 2;
 	public static final int WARN = 3;
 	public static final int ERROR = 4;
 	public static final int FATAL = 5;
 	
 	public static final String PROCESSING = "P";
    public static final String SEND = "Y";
    public static final String SEND_FLAG = "SB";
    public static final String NOT_SEND = "N";
    public static final String START_PROCESS = "S";
    public static final String END_PROCESS = "E";
 	
 	public static final String REPORT_APP_ID = "APSR001";
 	public static final String BATCH_APP_ID = "APSB001";
 	public static final String SYSTEM_ID = "APS";
 	
 	public static final int DEST_FILE = 1;
 	public static final int DEST_DB = 2;
 	public static final int DEST_BOTH = 3;
	
	public static final String CREDIT_CARD_SHEET_NAME = "Credit Card";
	public static final String BUNDLE_SHEET_NAME = "Bundle";
	public static final String FIXED_LOAN_SHEET_NAME = "Personal Loan";
	public static final String REVOLVING_LOAN_SHEET_NAME = "Revolving Loan";
	public static final String TOTAL_SHEET_NAME = "Total";
	public static final String SUCCESS_SHEET_NAME = "Success";
	public static final String UNSUCCESS_SHEET_NAME = "Unsuccess";
	
	public static final int CREDIT_CARD_SHEET_NO = 0;
	public static final int FIXED_LOAN_SHEET_NO = 1;
	public static final int REVOLVING_LOAN_SHEET_NO = 2;
	public static final int BUNDLE_SHEET_NO = 3;
	public static final int TOTAL_SHEET_NO = 4;
	public static final int SUCCESS_SHEET_NO = 0;
	public static final int UNSUCCESS_SHEET_NO = 1;
	
	public static final String CREDIT_CARD_GROUP_LOANTYPE = "C";
	public static final String CREDIT_CARD_BL_GROUP_LOANTYPE = "B";
	public static final String FIXED_LOAN_GROUP_LOANTYPE = "F";
	public static final String REVOLVING_LOAN_GROUP_LOANTYPE = "R";
	public static final String BUNDLE_GROUP_LOANTYPE = "N";
	
	public static final String APPROVE_STATUS_CODE = "8A";
	public static final String CANCEL_BY_OA_STATUS_CODE = "2C";
	public static final String CANCEL_BY_CAU_STATUS_CODE = "8C";
	public static final String DECLINE_STATUS_CODE = "8D";
	public static final String REJECT_STATUS_CODE = "2R";
	public static final String FINAL_RESOLVE_STATUS_CODE = "8F";

    public static final String XLS_REPORT_EXTENTION = ".xls";
    public static final String XLSX_REPORT_EXTENTION = ".xlsx";
    public static final String TXT_FILE_EXTENTION = ".TXT";
    
    public static final String SMS_BATCH_TYPE_MASTER_APPROVE = "MA";
    public static final String SMS_BATCH_TYPE_MASTER_REJECT = "MR";
    public static final String SMS_BATCH_TYPE_SUPPLEMENT_APPROVE = "SA";
    public static final String SMS_BATCH_TYPE_SUPPLEMENT_REJECT = "SR";
    public static final String SMS_BATCH_TYPE_REVOLVING = "RL";
    
    public static final String SUCCESS_FLAG = "S";
    public static final String UNSUCCESS_FLAG = "U";
    
    public static final String FLAG_YES = "Y";
    public static final String FLAG_NO = "N";
    
    public static final String MALE_SEX = "M";
    public static final String FEMALE_SEX = "F";
    
    public static final String MAIN_CARDTYPE = "M";
    public static final String SUB_CARDTYPE = "S";
	
}
