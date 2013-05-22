package co.th.ktc.nfe.report;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.th.ktc.nfe.report.bo.impl.ApproveReportBO;

@ContextConfiguration(locations={"/launch-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ApproveReportTests extends TestCase {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Autowired
	private ApproveReportBO bo;

	@Test
	public void testExecute() {
			
		int status = bo.execute(null);
		
		if (status == 1) {
			fail("Generate Report Error!!");
		} else {
			assertEquals(0, status);
		}
	}

}
