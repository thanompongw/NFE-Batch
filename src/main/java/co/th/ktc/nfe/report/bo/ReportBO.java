package co.th.ktc.nfe.report.bo;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import co.th.ktc.nfe.batch.exception.CommonException;

public interface ReportBO {
	
	public Workbook generateReport(Map<String, String> parameter) throws CommonException;
	
	public Integer execute(Map<String, String> parameter);

}
