package co.th.ktc.nfe.report.bo;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

public interface ReportBO {
	
	public Workbook generateReport(Map<String, String> parameter);
	
	public Integer execute(Map<String, String> parameter);

}
