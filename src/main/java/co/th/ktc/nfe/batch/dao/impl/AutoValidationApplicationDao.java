/**
 * 
 */
package co.th.ktc.nfe.batch.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.batch.dao.AbstractBatchDao;

/**
 * @author temp_dev1
 *
 */
@Repository(value = "autoValidationApplicationDao")
public class AutoValidationApplicationDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public AutoValidationApplicationDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(Object[] parameters) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO NFE_G_APPLICATION (BARCODE1, ");
		sql.append("                               EVIDENCE_TYPE, ");
		sql.append("                               CITIZEN_ID, ");
		sql.append("                               GROUP_PRODUCT, ");
		sql.append("                               ANALYST, ");
		sql.append("                               APPLICANT_TYPE, ");
		sql.append("                               PREFIX_NAME, ");
		sql.append("                               PREFIX_NAME_OTH, ");
		sql.append("                               SEX, ");
		sql.append("                               THAI_FNAME, ");
		sql.append("                               THAI_LNAME, ");
		sql.append("                               ENG_FNAME, ");
		sql.append("                               ENG_LNAME, ");
		sql.append("                               DOB, ");
		sql.append("                               RUSH_CARD, ");
		sql.append("                               NATIONALITY, ");
		sql.append("                               RELIGION, ");
		sql.append("                               CURRENT_ADDRESSLINE1, ");
		sql.append("                               CURRENT_ADDRESSLINE2, ");
		sql.append("                               CURRENT_ADDRESS_PROVINCE, ");
		sql.append("                               CURRENT_ADDRESS_AMPHUR, ");
		sql.append("                               CURRENT_ADDRESS_DISTRICT, ");
		sql.append("                               CURRENT_ADDRESS_ZIPCODE, ");
		sql.append("                               CURRENT_ADDRESS_PHONENO, ");
		sql.append("                               CURRENT_ADDRESS_PHONENO_EXT, ");
		sql.append("                               CURRENT_ADDRESS_MOBILENO, ");
		sql.append("                               CURRENT_ADDRESS_EMAIL, ");
		sql.append("                               CENSUS_ADDRESSLINE1, ");
		sql.append("                               CENSUS_ADDRESSLINE2, ");
		sql.append("                               CENSUS_ADDRESS_PROVINCE, ");
		sql.append("                               CENSUS_ADDRESS_AMPHUR, ");
		sql.append("                               CENSUS_ADDRESS_DISTRICT, ");
		sql.append("                               CENSUS_ADDRESS_ZIPCODE, ");
		sql.append("                               CENSUS_ADDRESS_PHONENO, ");
		sql.append("                               CENSUS_ADDRESS_PHONENOEXT, ");
		sql.append("                               CENSUS_ADDRESS_MOBILENO, ");
		sql.append("                               ADDRTYPE_YEARLIVE, ");
		sql.append("                               ADDRTYPE_TYPE, ");
		sql.append("                               ADDRTYPE_STATUS, ");
		sql.append("                               ADDRTYPE_INSTALLMENT, ");
		sql.append("                               OTHERS_DEGREE, ");
		sql.append("                               OTHERS_OCCUPATION, ");
		sql.append("                               OTHERS_OCCUPATION_OTH, ");
		sql.append("                               OTHERS_WORKPLACE, ");
		sql.append("                               MARRIAGE_STATUS, ");
		sql.append("                               REFERENCE_NAME, ");
		sql.append("                               REFERENCE_RELATIONSHIP, ");
		sql.append("                               REFERENCE_HOME_PHONENO, ");
		sql.append("                               REFERENCE_WORK_PHONENO, ");
		sql.append("                               REFERENCE_MOBILE_PHONENO, ");
		sql.append("                               CUSTOMER_TYPE, ");
		sql.append("                               STAFF_RATE_FLAG, ");
		sql.append("                               MONTHLY_INCOME, ");
		sql.append("                               SLCUSTOMERINFO_LOAN_INCOME, ");
		sql.append("                               SLCUSTOMERINFO_CARD_INCOME, ");
		sql.append("                               SLCUSTOMERINFO_PERMANENT_CREDI, ");
		sql.append("                               SLCUSTOMERINFO_BILLING_CYCLE, ");
		sql.append("                               SLCUSTOMERINFO_ESTATEMENT_FLAG, ");
		sql.append("                               PRODUCT_PRODUCT_ID, ");
		sql.append("                               PRODUCT_SUBPRODUCT_ID, ");
		sql.append("                               PRODUCT_EMBOSSNAME1, ");
		sql.append("                               PRODUCT_EMBOSSNAME2, ");
		sql.append("                               PRODUCT_PAYMENT_METHOD, ");
		sql.append("                               PRODUCT_CYCLE_DATE, ");
		sql.append("                               PRODUCT_CREDIT_LIMIT, ");
		sql.append("                               PRODUCT_PERCENT_INTEREST, ");
		sql.append("                               PRODUCT_COMMINTE_REST, ");
		sql.append("                               PRODUCT_PROMOTION_RATE, ");
		sql.append("                               PRODUCT_PROMOTION_TERMS, ");
		sql.append("                               PRODUCT_TERM, ");
		sql.append("                               PRODUCT_CASH_ADVANCE, ");
		sql.append("                               APPOCCUPATION_TYPE, ");
		sql.append("                               APPOCCUPATION_TYPE_OTH, ");
		sql.append("                               APPOCCUPATION_BUSINESS_TYPE, ");
		sql.append("                               APPOCCUPATION_BUSINESS_TYPE_OT, ");
		sql.append("                               APPOCCUPATION_ID, ");
		sql.append("                               APPOCCUPATION_OTH, ");
		sql.append("                               APPOCCUPATION_WORKPLACE, ");
		sql.append("                               APPOCCUPATION_JURISTIC_NO, ");
		sql.append("                               APPOCCUPATION_JURISTIC_DE, ");
		sql.append("                               APPOCCUPATION_JURISTIC_REGDATE, ");
		sql.append("                               APPOCCUPATION_ADDRLINE1, ");
		sql.append("                               APPOCCUPATION_ADDRLINE2, ");
		sql.append("                               APPOCCUPATION_PROVINCE, ");
		sql.append("                               APPOCCUPATION_AMPHUR, ");
		sql.append("                               APPOCCUPATION_DISTRICT, ");
		sql.append("                               APPOCCUPATION_ZIPCODE, ");
		sql.append("                               APPOCCUPATION_PHONENO1, ");
		sql.append("                               APPOCCUPATION_PHONENO1_EXT, ");
		sql.append("                               APPOCCUPATION_MOBILENO, ");
		sql.append("                               APPOCCUPATION_POSITION, ");
		sql.append("                               APPOCCUPATION_YEAR_OF_WORK, ");
		sql.append("                               APPOCCUPATION_RECIEVE_CARD, ");
		sql.append("                               APPOCCUPATION_RECIEVE_BRANCH, ");
		sql.append("                               APPOCCUPATION_RECIEVE_BILL, ");
		sql.append("                               APPOCCUPATION_COMMUNICATION, ");
		sql.append("                               APPOCCUPATION_SME_CUSTOMER_NO, ");
		sql.append("                               BANKACCOUNT_BANK, ");
		sql.append("                               BANKACCOUNT_BANK_BRANCH, ");
		sql.append("                               BANKACCOUNT_ACC_TYPE, ");
		sql.append("                               BANKACCOUNT_ACC_NAME, ");
		sql.append("                               BANKACCOUNT_ACC_NO, ");
		sql.append("                               BANKACCOUNT_OPEN_PERIOD, ");
		sql.append("                               BANKACCOUNT_TM_BALANCE, ");
		sql.append("                               BANKACCOUNT_LM_BALANCE, ");
		sql.append("                               LOANACCOUNT_ACCNO, ");
		sql.append("                               LOANACCOUNT_ACCTYPE, ");
		sql.append("                               LOANACCOUNT_BANK, ");
		sql.append("                               LOANACCOUNT_ISSUE_DATE, ");
		sql.append("                               LOANACCOUNT_EXPIRE_DATE, ");
		sql.append("                               LOANACCOUNT_CREDIT_LIMIT, ");
		sql.append("                               LOANACCOUNT_PAYMENT_METHOD, ");
		sql.append("                               LOANACCOUNT_INSTALLMENT, ");
		sql.append("                               TRANSFER_DEBT_ACC_NAME, ");
		sql.append("                               TRANSFER_DEBT_ACC_NO, ");
		sql.append("                               TRANSFER_DEBT_ACC_TYPE, ");
		sql.append("                               TRANSFER_DEBT_BANK, ");
		sql.append("                               TRANSFER_DEBT_LAST_AMOUNT, ");
		sql.append("                               TRANSFERLOAN_ACC_NAME, ");
		sql.append("                               TRANSFERLOAN_ACC_NO, ");
		sql.append("                               TRANSFERLOAN_ACC_TYPE, ");
		sql.append("                               TRANSFERLOAN_BANK, ");
		sql.append("                               PAYMENTMETHOD_VALUE, ");
		sql.append("                               PAYMENTMETHOD_ACC_NAME, ");
		sql.append("                               PAYMENTMETHOD_ACC_NO, ");
		sql.append("                               PAYMENTMETHOD_BANK) ");
		sql.append("VALUES (?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?, ");
		sql.append("        ?) ");
		
		int[] parameterTypes = new int[parameters.length];
		
		getJdbcTemplate().update(sql.toString(), parameters, parameterTypes);

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#update(java.lang.Object[])
	 */
	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#delete(java.lang.Object[])
	 */
	@Override
	public void delete(Object[] parameter) {
		String sql = "TRUNCATE TABLE NFE_G_APPLICATION";
		
		getJdbcTemplate().execute(sql);

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryDetail(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryDetail(Object[] parameter) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#queryHeader(java.lang.Object[])
	 */
	@Override
	public SqlRowSet queryHeader(Object[] parameter) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#success(java.lang.Object[])
	 */
	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#fail(java.lang.Object[])
	 */
	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public SqlRowSet queryTrailer(Object[] parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
