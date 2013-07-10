package co.th.ktc.nfe.report;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.th.ktc.nfe.report.bo.impl.PaymentChequePartnerBO;

@ContextConfiguration(locations={"/report-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentChequePartnerTests extends TestCase {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Autowired
	private PaymentChequePartnerBO bo;

	@Test
	public void testExecute() {
		
		Map<String, String> parameter = new HashMap<String, String>();
		
		parameter.put("REPORT_DATE", "01/04/2013");
			
		int status = bo.execute(parameter);
		
		if (status == 1) {
			fail("Generate Report Error!!");
		} else {
			assertEquals(0, status);
		}
	}

}
