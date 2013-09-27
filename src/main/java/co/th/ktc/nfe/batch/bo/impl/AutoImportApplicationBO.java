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
			applicationBean.setThaiFName((String) poi.getObject(sheet, i, j++));
			applicationBean.setThaiLName((String) poi.getObject(sheet, i, j++));
			applicationBean.setEngFName((String) poi.getObject(sheet, i, j++));
			applicationBean.setEngLName((String) poi.getObject(sheet, i, j++));
			applicationBean.setDob((String) poi.getObject(sheet, i, j++));
			applicationBean.setIsChkNCB((String) poi.getObject(sheet, i, j++));
			applicationBean.setRushCard((String) poi.getObject(sheet, i, j++));
			applicationBean.setNationality((String) poi.getObject(sheet, i, j++));
			applicationBean.setReligion((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressLine1((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressLine2((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressProvince((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressAmphur((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressDistrict((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressZipcode((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressPhoneNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressPhoneNoExt((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressMobileNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setCurrentAddressEmail((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressLine1((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressLine2((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressProvince((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressAmphur((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressDistrict((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressZipcode((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressPhoneNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressPhoneNoext((String) poi.getObject(sheet, i, j++));
			applicationBean.setCensusAddressMobileNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrTypeYearLive((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrTypeType((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrTypeStatus((String) poi.getObject(sheet, i, j++));
			applicationBean.setAddrTypeInstallment((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersDegree((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersOccupation((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersOccupationOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setOthersWorkplace((String) poi.getObject(sheet, i, j++));
			applicationBean.setMarriageStatus((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceName((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceRelationship((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceHomePhoneNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceWorkPhoneNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setReferenceMobilePhoneNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setCustomerType((String) poi.getObject(sheet, i, j++));
			applicationBean.setStaffRateFlag((String) poi.getObject(sheet, i, j++));
			applicationBean.setMonthlyIncome((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlCustomerInfoLoanIncome((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlCustomerInfoCardIncome((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlCustomerInfoPermanentCredit((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlCustomerInfoBillingCycle((String) poi.getObject(sheet, i, j++));
			applicationBean.setSlCustomerInfoEstatementFlag((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductProductId((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductSubproductId((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductEmbossname1((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductEmbossname2((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPaymentMethod((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCycleDate((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCreditLimit((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPercentInterest((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCommInterest((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPromotionRate((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductPromotionTerms((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductTerm((String) poi.getObject(sheet, i, j++));
			applicationBean.setProductCashAdvance((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationType((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationTypeOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationBusinessType((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationBusinessTypeOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationId((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationOth((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationWorkplace((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationJuristicNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationJuristicDe((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationJuristicRegdate((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationAddrline1((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationAddrline2((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationProvince((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationAmphur((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationDistrict((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationZipcode((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationPhoneNo1((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationPhoneNo1Ext((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationMobileNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationPosition((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationYearOfWork((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationRecieveCard((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationRecieveBranch((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationRecieveBill((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationCommunication((String) poi.getObject(sheet, i, j++));
			applicationBean.setAppOccupationSMECustomerNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountBankBranch((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountAccType((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountOpenPeriod((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountTMBalance((String) poi.getObject(sheet, i, j++));
			applicationBean.setBankAccountLMBalance((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountAccno((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountAcctype((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountIssueDate((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountExpireDate((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountCreditLimit((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountPaymentMethod((String) poi.getObject(sheet, i, j++));
			applicationBean.setLoanAccountInstallment((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtAccType((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferDebtLastAmount((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferLoanAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferLoanAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferLoanAccType((String) poi.getObject(sheet, i, j++));
			applicationBean.setTransferLoanBank((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentMethodValue((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentMethodAccName((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentMethodAccNo((String) poi.getObject(sheet, i, j++));
			applicationBean.setPaymentMethodBank((String) poi.getObject(sheet, i, j++));
			
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
			if (!cardType.equals(NFEBatchConstants.MAIN_CARDTYPE) 
					&& !cardType.equals(NFEBatchConstants.SUB_CARDTYPE)) {
				ErrorUtil.generateError("MSTD0043AERR", "CardType");
			}
		}
		
		String isApplyMainWithSup = applicationBean.getIsApplyMainWithSup();
		
		if (isEmpty(isApplyMainWithSup)) {
			ErrorUtil.generateError("MSTD0031AERR", "ISApplyMainWithSup");
		} else {
			if (cardType.equals(NFEBatchConstants.MAIN_CARDTYPE)) {
				if (!isApplyMainWithSup.equals(NFEBatchConstants.FLAG_YES) 
						&& !isApplyMainWithSup.equals(NFEBatchConstants.FLAG_NO)) {
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
			if (!sex.equals(NFEBatchConstants.MALE_SEX) 
					&& !sex.equals(NFEBatchConstants.FEMALE_SEX)) {
				ErrorUtil.generateError("MSTD0043AERR", "Sex");
			}
		}
		
		if (isEmpty(applicationBean.getThaiFName())) {
			ErrorUtil.generateError("MSTD0031AERR", "ThaiFName");
		}
		
		if (isEmpty(applicationBean.getThaiLName())) {
			ErrorUtil.generateError("MSTD0031AERR", "ThaiLName");
		}
		
		if (isEmpty(applicationBean.getEngFName())) {
			ErrorUtil.generateError("MSTD0031AERR", "EngFName");
		}
		
		if (isEmpty(applicationBean.getEngLName())) {
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
			if (!isChkNCB.equals(NFEBatchConstants.FLAG_YES) 
					&& !isChkNCB.equals(NFEBatchConstants.FLAG_NO)) {
				ErrorUtil.generateError("MSTD0043AERR", "IsChkNCB");
			}
		}
		
		if (cardType.equals(NFEBatchConstants.MAIN_CARDTYPE)) {

			if (isEmpty(applicationBean.getNationality())) {
				ErrorUtil.generateError("MSTD0031AERR", "Nationality");
			} else {
				/*"Must be in 
					Select nationality_id 
					From nfe_ms_nationality
					Where nationality_status = 'A'"
					*/		
			}
			
			if (!isEmpty(applicationBean.getReligion())) {
				/*"Must be in
					Select religion_id
					From nfe_ms_religion
					Where religion_status ='A'"
					*/		
			}
			
			if (isEmpty(applicationBean.getCurrentAddressLine1())) {
				ErrorUtil.generateError("MSTD0031AERR", "Nationality");
			} else if (applicationBean.getCurrentAddressLine1().length() > 40) {
				String[] args = new String[2];
				
				args[0] = "CurrentAddressline1";
				args[1] = "19";
				ErrorUtil.generateError("MSTD0051AERR", args, 0);
			}
			
			if (isEmpty(applicationBean.getCurrentAddressDistrict())) {
				ErrorUtil.generateError("MSTD0031AERR", "CurrentAddressDistrict");
			} else {

				/*"
				 "Must be in 
					Select district_id
					 From nfe_ms_district
					 Where 
					district_subprovinceid = Column(CURRENT ADDRESSAMPHUR) and 
					district_status = 'A'"
					*/	
			}
			
			if (isEmpty(applicationBean.getCurrentAddressAmphur())) {
				ErrorUtil.generateError("MSTD0031AERR", "CurrentAddressAmphur");
			} else {

				/*"
				 "Must be in 
				select subprovince_id
				From nfe_ms_subprovince
				Where 
				subprovince_provinceid = Column(CURRENT ADDRESSPROVINCE) and
				subprovince_status = 'A'"
					*/	
			}
			
			if (isEmpty(applicationBean.getCurrentAddressProvince())) {
				ErrorUtil.generateError("MSTD0031AERR", "CurrentAddressProvince");
			} else {

				/*"
				 "Must be in 
				Select province_id 
				From nfe_ms_province 
				Where province_status = 'A'"
					*/	
			}
			
			if (isEmpty(applicationBean.getCurrentAddressZipcode())) {
				ErrorUtil.generateError("MSTD0031AERR", "CurrentAddressZipcode");
			} else if (applicationBean.getCurrentAddressZipcode().length() != 5) {
				String[] args = new String[2];
				
				args[0] = "CurrentAddressZipcode";
				args[1] = "5";
				ErrorUtil.generateError("MSTD0054AERR", args, 0);
			}
			
			String currentAddressPhoneno = applicationBean.getCurrentAddressPhoneNo();
			
			if (isEmpty(currentAddressPhoneno)) {
				ErrorUtil.generateError("MSTD0031AERR", "CurrentAddressPhoneno");
			} else {
				// 2. Length must equal NfeConfigValue.PhoneLength
				if (currentAddressPhoneno.length() != 8) {
					String[] args = new String[2];
					
					args[0] = "CurrentAddressPhoneno";
					args[1] = "5";
					ErrorUtil.generateError("MSTD0054AERR", args, 0);
				}
				
				/*1. first 2 characters of Column(CURRENT ADDRESSPHONENO) must = 
				Select province_phonecode 
				From nfe_ms_province 
				Where 
				province_id = Column(CURRENT ADDRESSPROVINCE)*/
				
			}
			
			if (!isEmpty(applicationBean.getCurrentAddressMobileNo())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getCurrentAddressEmail())
					&& !isValidEmail(applicationBean.getCurrentAddressEmail())) {
				ErrorUtil.generateError("MSTD0043AERR", "CurrentAddressEmail");
			}
			
			if (!isEmpty(applicationBean.getCensusAddressDistrict())) {
				/*"
				 "Must be in 
					Select district_id
					 From nfe_ms_district
					 Where 
					district_subprovinceid = Column(CURRENT ADDRESSAMPHUR) and 
					district_status = 'A'"
					*/	
			}
			
			if (!isEmpty(applicationBean.getCensusAddressAmphur())) {
				/*"
				 "Must be in 
					Select district_id
					 From nfe_ms_district
					 Where 
					district_subprovinceid = Column(CURRENT ADDRESSAMPHUR) and 
					district_status = 'A'"
					*/	
			}
			
			if (!isEmpty(applicationBean.getCensusAddressProvince())) {
				/*"
				 "Must be in 
					Select district_id
					 From nfe_ms_district
					 Where 
					district_subprovinceid = Column(CURRENT ADDRESSAMPHUR) and 
					district_status = 'A'"
					*/	
			}
			
			if (!isEmpty(applicationBean.getCensusAddressZipcode())
					&& applicationBean.getCurrentAddressZipcode().length() != 5) {
				String[] args = new String[2];
				
				args[0] = "CensusAddressZipcode";
				args[1] = "5";
				ErrorUtil.generateError("MSTD0054AERR", args, 0);
			}
			
			String censusAddressPhoneno = applicationBean.getCensusAddressPhoneNo();
			
			if (!isEmpty(censusAddressPhoneno)) {
				// 2. Length must equal NfeConfigValue.PhoneLength
				if (censusAddressPhoneno.length() != 8) {
					String[] args = new String[2];
					
					args[0] = "CensusAddressPhoneno";
					args[1] = "5";
					ErrorUtil.generateError("MSTD0054AERR", args, 0);
				}
				
				/*1. first 2 characters of Column(CURRENT ADDRESSPHONENO) must = 
				Select province_phonecode 
				From nfe_ms_province 
				Where 
				province_id = Column(CURRENT ADDRESSPROVINCE)*/
				
			}
			
			if (!isEmpty(applicationBean.getCensusAddressMobileNo())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			String addrTypeYearLive = applicationBean.getAddrTypeYearLive();
			
			if (!isEmpty(applicationBean.getAddrTypeYearLive())) {
				
				if (!isNumeric(addrTypeYearLive)) {
					ErrorUtil.generateError("MSTD0043AERR", "AddrtypeYearlive");
				} else {
					Integer month = Integer.valueOf(
							addrTypeYearLive.substring(addrTypeYearLive.indexOf("."), 
													   addrTypeYearLive.length()));
					if (month > 12) {
						ErrorUtil.generateError("MSTD0043AERR", "AddrtypeYearlive");
					}
				}
			}
			
			if (!isEmpty(applicationBean.getAddrTypeType())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getAddrTypeStatus())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getAddrTypeStatus())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getAddrTypeInstallment())) {
				try {
					Integer.parseInt(applicationBean.getAddrTypeInstallment());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "AddrtypeInstallment");
				}
			}
			
			if (!isEmpty(applicationBean.getOthersDegree())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getOthersOccupation())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (isEmpty(applicationBean.getOthersWorkplace())) {
				ErrorUtil.generateError("MSTD0031AERR", "OthersWorkplace");
			}
			
			if (!isEmpty(applicationBean.getMarriageStatus())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getReferenceRelationship())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getReferenceHomePhoneNo())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getReferenceMobilePhoneNo())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getReferenceWorkPhoneNo())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getReferenceRelationship())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			if (!isEmpty(applicationBean.getCustomerType())) {
				/*
				 * "Length must equal NfeConfigValue.MobileLength
					first 2 characters must exist in Configuration.MobilePrefix"
				 */		
			}
			
			String staffRateFlag = applicationBean.getStaffRateFlag();
			
			if (!isEmpty(staffRateFlag)) {
				if (!staffRateFlag.equals(NFEBatchConstants.FLAG_YES) 
						&& !staffRateFlag.equals(NFEBatchConstants.FLAG_NO)) {
					ErrorUtil.generateError("MSTD0043AERR", "StaffRateFlag");
				}
			}
			
			if (isEmpty(applicationBean.getMonthlyIncome())) {
				ErrorUtil.generateError("MSTD0031AERR", "MonthlyIncome");
			} else {
				try {
					Integer.parseInt(applicationBean.getMonthlyIncome());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "MonthlyIncome");
				}
			}
			
			if (!isEmpty(applicationBean.getOtherIncome())) {
				try {
					Integer.parseInt(applicationBean.getOtherIncome());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "OtherIncome");
				}
			}
			
			if (!isEmpty(applicationBean.getSourceOtherIncome())) {
				
			}
			
			if (isEmpty(applicationBean.getProductProductId())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductProductId");
			}
			
			if (isEmpty(applicationBean.getProductSubproductId())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductSubproductId");
			} else {
				
			}
			
			if (isEmpty(applicationBean.getProductEmbossname1())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductEmbossname1");
			} else {
				
			}
			
			if (isEmpty(applicationBean.getProductEmbossname2())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductEmbossname2");
			} else {
				
			}
			
			if (isEmpty(applicationBean.getProductPaymentMethod())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductPaymentMethod");
			} else {
				
			}
			
			if (isEmpty(applicationBean.getProductCycleDate())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductCycleDate");
			} else {
				
			}
			
			if (isEmpty(applicationBean.getProductCreditLimit())) {
				ErrorUtil.generateError("MSTD0031AERR", "ProductCreditLimit");
			} else {
				try {
					Integer.parseInt(applicationBean.getProductCreditLimit());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "ProductCreditLimit");
				}
			}
			
			if (!isEmpty(applicationBean.getProductPercentInterest())) {
				try {
					Double.parseDouble(applicationBean.getProductPercentInterest());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "ProductPercentInterest");
				}
			}
			
			if (!isEmpty(applicationBean.getProductCommInterest())) {
				try {
					Double.parseDouble(applicationBean.getProductCommInterest());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "ProductCommInterest");
				}
			}
			
			if (!isEmpty(applicationBean.getProductPromotionRate())) {
				try {
					Double.parseDouble(applicationBean.getProductPromotionRate());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "ProductPromotionRate");
				}
			}
			
			if (!isEmpty(applicationBean.getProductPromotionTerms())) {
				try {
					Integer.parseInt(applicationBean.getProductPromotionTerms());
				} catch (NumberFormatException e) {
					ErrorUtil.generateError("MSTD0043AERR", "ProductPromotionTerms");
				}
			}
			
		}
	}

}
