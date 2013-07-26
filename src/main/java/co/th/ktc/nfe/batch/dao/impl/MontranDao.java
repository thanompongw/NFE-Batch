/**
 * 
 */
package co.th.ktc.nfe.batch.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import co.th.ktc.nfe.batch.dao.AbstractBatchDao;

/**
 * @author temp_dev1
 *
 */
@Repository(value = "montranDao")
public class MontranDao extends AbstractBatchDao {

	/**
	 * 
	 */
	public MontranDao() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see co.th.ktc.nfe.batch.dao.AbstractBatchDao#insert(java.lang.Object[])
	 */
	@Override
	public void insert(final Object[] parameter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO NFE_G_MONTRAN (MONTRAN_ID,  ");
		sql.append("                           APP_NO,  ");
		sql.append("                           FILE_TYPE,  ");
		sql.append("                           RECORD_TYPE,  ");
		sql.append("                           SET_NO,  ");
		sql.append("                           RECEIVING_BANK_CODE,  ");
		sql.append("                           RECEIVING_BANK_BRANCH,  ");
		sql.append("                           RECEIVING_BANK_ACCOUNT,  ");
		sql.append("                           SENDING_BANK_CODE_DEFAULT,  ");
		sql.append("                           SENDING_BANK_CODE,  ");
		sql.append("                           SENDING_BANK_ACCOUNT,  ");
		sql.append("                           EFFECTIVE_DATE,  ");
		sql.append("                           SERVICE_TYPE,  ");
		sql.append("                           CLERING_HOUSEHOLD,  ");
		sql.append("                           TRANSFER_AMOUNT,  ");
		sql.append("                           RECEIVER_INFO,  ");
		sql.append("                           RECEIVER_ID,  ");
		sql.append("                           REVEIVER_NAME,  ");
		sql.append("                           SENDER_NAME,  ");
		sql.append("                           PRODUCT_CODE,  ");
		sql.append("                           REFERENCE_NO,  ");
		sql.append("                           OTHER_INFO_II,  ");
		sql.append("                           REFER_RUNNING_NO)  ");
		sql.append("VALUES (SEQ_NFE_G_MONTRAN.NEXTVAL, ");
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
		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			
			int parameterIndex = 1;
			int objIndex = 0;
					
			public void setValues(PreparedStatement pstm) throws SQLException {
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setInt(parameterIndex++, (Integer) parameter[objIndex++]);
				pstm.setInt(parameterIndex++, (Integer) parameter[objIndex++]);
				pstm.setInt(parameterIndex++, (Integer) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setInt(parameterIndex++, (Integer) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
				pstm.setString(parameterIndex++, (String) parameter[objIndex++]);
			}
		});
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
		StringBuilder sql = new StringBuilder();
		
		sql.append("TRUNCATE TABLE NFE_G_MONTRAN");
		
		getJdbcTemplate().execute(sql.toString());

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
		
	public Integer size(Object[] parameter) {
		
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

}
