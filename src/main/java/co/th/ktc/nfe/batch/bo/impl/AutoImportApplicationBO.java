/**
 * 
 */
package co.th.ktc.nfe.batch.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.dao.AbstractBatchDao;
import co.th.ktc.nfe.batch.domain.ApplicationBean;
import co.th.ktc.nfe.batch.exception.BusinessError;
import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.common.BatchConfiguration;
import co.th.ktc.nfe.common.CommonLogger;
import co.th.ktc.nfe.common.CommonPOI;
import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.common.ErrorUtil;
import co.th.ktc.nfe.constants.NFEBatchConstants;

/**
 * @author temp_dev1
 *
 */
@Service(value = "autoImportApplicationService")
public class AutoImportApplicationBO extends BatchBO {
	
	private static Logger LOG = Logger.getLogger(AutoImportApplicationBO.class);
	
	private static final String REPORT_FILE_NAME = "Import File Application-auth_CB_Coll";
	
	private static final String FUNCTION_ID = "SB023";
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Resource(name = "autoImportApplicationDao")
	private AbstractBatchDao dao;
	
	private CommonPOI poi;

	/**
	 *  
	 */
	public AutoImportApplicationBO() {
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#execute(java.util.Map)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Integer execute(Map<String, String> parameter) {
		Integer processStatus = 0;
		
		try {
			String currentDate = null;
		
			if (parameter == null || parameter.isEmpty()) {
				parameter = new HashMap<String, String>();
				currentDate = dao.getSetDate("DD/MM/YYYY");
			} else {
				currentDate = parameter.get("BATCH_DATE");
			}
			
			LOG.info("Batch Date : " + currentDate);
			
			poi = new CommonPOI(REPORT_FILE_NAME, batchConfig.getPathTemp());
			
			write(parameter);
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
								 AutoImportApplicationBO.class);
			}
		}
		
		return processStatus;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.bo.BatchBO#write(java.util.Map)
	 */
	public void write(Map<String, String> parameter) throws CommonException {
		List<ApplicationBean> dataList = read(parameter);
		
		for (ApplicationBean applicationBean : dataList) {
			
			dao.insert(null);
		}
	}

	private List<ApplicationBean> read(Map<String, String> parameter) throws CommonException {
		Workbook workbook = poi.getWorkBook();
		int sheetNo = 0;
		
		Sheet sheet = workbook.getSheetAt(sheetNo);
		
		int lastRow = sheet.getLastRowNum();
		int minColIx = sheet.getRow(lastRow).getFirstCellNum();
		
		int dataRows = 1;
		
		List<ApplicationBean> dataSuccessList = new ArrayList<ApplicationBean>();
		List<ApplicationBean> dataUnsuccessList = new ArrayList<ApplicationBean>();
		
		ApplicationBean applicationBean = null;
		
		int j = 0;
		
		for (int i = dataRows; i < lastRow; i++) {
			
			j = minColIx;
			applicationBean = new ApplicationBean();
			
			applicationBean.setBarcode1((String) poi.getObject(sheet, i, j++));
			applicationBean.setBarcode2((String) poi.getObject(sheet, i, j++));
			applicationBean.setEvidenceType((String) poi.getObject(sheet, i, j++));
			applicationBean.setCitizenId((String) poi.getObject(sheet, i, j++));
			applicationBean.setCardType((String) poi.getObject(sheet, i, j++));
			applicationBean.setIsApplyMainWithSup((String) poi.getObject(sheet, i, j++));
			applicationBean.setMainCardNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setGroupProduct((String) poi.getObject(sheet, i, j++));
			applicationBean.setAnalyst((String) poi.getObject(sheet, i, j++));
			applicationBean.setApplicantType((String) poi.getObject(sheet, i, j++));
			applicationBean.setPrefixName((String) poi.getObject(sheet, i, j++));
			applicationBean.setPrefixNameOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setSex((String) poi.getObject(sheet, i, j++));
			applicationBean.setThaiFname((String) poi.getObject(sheet, i, j++));
			applicationBean.setThaiLname((String) poi.getObject(sheet, i, j++));
			applicationBean.setEngFname((String) poi.getObject(sheet, i, j++));
			applicationBean.setEngLname((String) poi.getObject(sheet, i, j++));
			applicationBean.setDob((String) poi.getObject(sheet, i, j++));
			applicationBean.setIsChkNCB((String) poi.getObject(sheet, i, j++));
			applicationBean.setRushCard((String) poi.getObject(sheet, i, j++));
			applicationBean.setNationality((String) poi.getObject(sheet, i, j++));
			applicationBean.setReligion((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressline1((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressline2((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressProvince((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressAmphur((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressDistrict((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressZipcode((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressPhoneno((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressPhonenoExt((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressMobileno((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressEmail((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressline1((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressline2((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressProvince((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressAmphur((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressDistrict((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressZipcode((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressPhoneno((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressPhonenoext((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressMobileno((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrtypeYearlive((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrtypeType((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrtypeStatus((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrtypeInstallment((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersDegree((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersOccupation((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersOccupationOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersWorkplace((String) poi.getObject(sheet, i, j++));
			applicationBean.setMarriageStatus((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceName((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceRelationship((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceHomePhoneno((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceWorkPhoneno((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceMobilePhoneno((String) poi.getObject(sheet, i, j++));
			applicationBean.setCustomerType((String) poi.getObject(sheet, i, j++));
			applicationBean.setStaffRateFlag((String) poi.getObject(sheet, i, j++));
			applicationBean.setMonthlyIncome((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlcustomerinfoLoanIncome((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlcustomerinfoCardIncome((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlcustomerinfoPermanentCredi((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlcustomerinfoBillingCycle((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlcustomerinfoEstatementFlag((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductProductId((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductSubproductId((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductEmbossname1((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductEmbossname2((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPaymentMethod((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCycleDate((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCreditLimit((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPercentInterest((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductComminteRest((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPromotionRate((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPromotionTerms((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductTerm((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCashAdvance((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationType((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationTypeOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationBusinessType((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationBusinessTypeOt((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationId((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationWorkplace((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationJuristicNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationJuristicDe((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationJuristicRegdate((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationAddrline1((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationAddrline2((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationProvince((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationAmphur((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationDistrict((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationZipcode((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationPhoneno1((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationPhoneno1Ext((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationMobileno((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationPosition((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationYearOfWork((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationRecieveCard((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationRecieveBranch((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationRecieveBill((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationCommunication((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppoccupationSmeCustomerNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountBankBranch((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountAccType((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountOpenPeriod((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountTmBalance((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankaccountLmBalance((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountAccno((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountAcctype((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountIssueDate((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountExpireDate((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountCreditLimit((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountPaymentMethod((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanaccountInstallment((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtAccType((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtLastAmount((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferloanAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferloanAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferloanAccType((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferloanBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentmethodValue((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentmethodAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentmethodAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentmethodBank((String) poi.getObject(sheet, i, j++));
			
			try {
				validateMandatory(applicationBean);
				
				dataSuccessList.add(applicationBean);
				
			} catch (CommonException e) {
				dataUnsuccessList.add(applicationBean);
			}
		}
		
		return dataSuccessList;
	}
	
	private void validateMandatory(ApplicationBean applicationBean) throws CommonException {

		String barcode1 = applicationBean.getBarcode1();
		if (isEmpty(barcode1)) {
			ErrorUtil.generateError("MSTD0031AERR", "Barcode1");
		} else if (barcode1.length() > 19) {
			String[] args = new String[2];
			
			args[0] = "Barcode1";
			args[1] = "19";
			ErrorUtil.generateError("MSTD0051AERR", args, 0);				
		} else {
			String sourceCode = barcode1.substring(0, 3);
			
			if (!sourceCode.equals("MGM")) {
				barcode1 = sourceCode + barcode1.substring(3, 13);
			}
			
			applicationBean.setBarcode1(barcode1);
		}
		
		if (isEmpty(applicationBean.getBarcode2())) {
			ErrorUtil.generateError("MSTD0031AERR", "Barcode2");
		} else if (applicationBean.getBarcode2().length() != 10) {
			String[] args = new String[2];
			
			args[0] = "Barcode2";
			args[1] = "10";
			ErrorUtil.generateError("MSTD0054AERR", args, 0);				
		}
		
		if (isEmpty(applicationBean.getEvidenceType())) {
			ErrorUtil.generateError("MSTD0031AERR", "EvidenceType");
		} else {
			/*"Must be in 
			Select 'X'
			From nfe_ms_evidencetype 
			Where evidencetype_id = :EvidenceType
			and evidencetype_status = 'A'"*/
		}
		
		String citizenId = applicationBean.getCitizenId();
		
		if (isEmpty(citizenId)) {
			ErrorUtil.generateError("MSTD0031AERR", "CitizenId");
		} else {
			if (applicationBean.getEvidenceType().equals("1")) {
				if (citizenId.length() != 13) {
					String[] args = new String[2];
					
					args[0] = "CitizenId";
					args[1] = "13";
					ErrorUtil.generateError("MSTD0054AERR", args, 0);
				}
				int sum = 0;
		        for (int i = 0; i < 12; i++) {
		            sum += citizenId.charAt(i) * (citizenId.length() - i);
			        if ((11 - sum % 11) % 10 != citizenId.charAt(12)) {
			        	ErrorUtil.generateError("MSTD0043AERR", "CitizenId");
			        }
		        }
			}

		}
		
		String cardType = applicationBean.getCardType();
		
		if (isEmpty(cardType)) {
			ErrorUtil.generateError("MSTD0031AERR", "CardType");
		} else {
			if (!cardType.equals("M") && !cardType.equals("S")) {
				ErrorUtil.generateError("MSTD0043AERR", "CardType");
			}
		}
		
		String isApplyMainWithSup = applicationBean.getIsApplyMainWithSup();
		
		if (isEmpty(isApplyMainWithSup)) {
			ErrorUtil.generateError("MSTD0031AERR", "ISApplyMainWithSup");
		} else {
			if (cardType.equals("M")) {
				if (!isApplyMainWithSup.equals("Y") && !isApplyMainWithSup.equals("N")) {
					ErrorUtil.generateError("MSTD0043AERR", "IsApplyMainWithSup");
				}
			} else if (cardType.equals("S")) {

				if (isEmpty(applicationBean.getMainCardNo())) {
					ErrorUtil.generateError("MSTD0031AERR", "MainCardNo");
				}
				
				if (!isApplyMainWithSup.equals("N")) {
					ErrorUtil.generateError("MSTD0043AERR", "IsApplyMainWithSup");
				}
			}
		}
		
		if (isEmpty(applicationBean.getApplicantType())) {
			ErrorUtil.generateError("MSTD0031AERR", "ApplicantType");
		} else {
			/*"Must be in 
			Select applicanttype_id 
			From nfe_ms_applicanttype
			Where  application_status = 'A'"*/		
		}
		
		if (isEmpty(applicationBean.getPrefixName())) {
			ErrorUtil.generateError("MSTD0031AERR", "PrefixName");
		} else {
			/*"Must be in 
			Select prefixname_id
			From nfe_ms_prefixname
			Where prefixname_status = 'A'"*/		
		}
		
		String sex = applicationBean.getSex();
		
		if (isEmpty(sex)) {
			ErrorUtil.generateError("MSTD0031AERR", "Sex");
		} else {
			if (!sex.equals("M") && !sex.equals("F")) {
				ErrorUtil.generateError("MSTD0043AERR", "Sex");
			}
		}
		
		if (isEmpty(applicationBean.getThaiFname())) {
			ErrorUtil.generateError("MSTD0031AERR", "ThaiFName");
		}
		
		if (isEmpty(applicationBean.getThaiLname())) {
			ErrorUtil.generateError("MSTD0031AERR", "ThaiLName");
		}
		
		if (isEmpty(applicationBean.getEngFname())) {
			ErrorUtil.generateError("MSTD0031AERR", "EngFName");
		}
		
		if (isEmpty(applicationBean.getEngLname())) {
			ErrorUtil.generateError("MSTD0031AERR", "EngLName");
		}
		
		String dateOfBirth = applicationBean.getDob();
		if (isEmpty(dateOfBirth)) {
			ErrorUtil.generateError("MSTD0031AERR", "Dob");
		} else {
			if (!isValidDate(dateOfBirth)) {
				ErrorUtil.generateError("MSTD0043AERR", "DOB");
			}
		}
		String isChkNCB = applicationBean.getIsChkNCB();
		if (isEmpty(isChkNCB)) {
			ErrorUtil.generateError("MSTD0031AERR", "IsChkNCB");
		} else {
			if (!isChkNCB.equals("Y") && !isChkNCB.equals("N")) {
				ErrorUtil.generateError("MSTD0043AERR", "IsChkNCB");
			}
		}
	}

}
