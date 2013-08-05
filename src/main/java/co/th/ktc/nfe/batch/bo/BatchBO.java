package co.th.ktc.nfe.batch.bo;

import java.util.Map;

import co.th.ktc.nfe.batch.exception.CommonException;

public interface BatchBO {
	
	public Integer execute(Map<String, String> parameter);
	
	public void write(Map<String, String> parameter) throws CommonException;

}
