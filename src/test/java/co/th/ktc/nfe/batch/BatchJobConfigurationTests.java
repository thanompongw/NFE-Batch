package co.th.ktc.nfe.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.th.ktc.nfe.common.BatchConfiguration;

@ContextConfiguration(locations={"/report-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchJobConfigurationTests {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Autowired
	private BatchConfiguration batchConfig;
	
	@Test
	public void testSimpleProperties() throws Exception {
		System.out.println(batchConfig.getPathOutput());
	}
	
	@Test
	public void testLaunchJob() throws Exception {
//		jobLauncher.run(job, new JobParameters());
	}
	
}
