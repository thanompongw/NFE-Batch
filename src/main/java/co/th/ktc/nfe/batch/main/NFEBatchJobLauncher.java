/**
 * 
 */
package co.th.ktc.nfe.batch.main;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.th.ktc.nfe.common.CommonLogger;


/**
 * @author temp_dev2
 *
 */
public class NFEBatchJobLauncher {

	/**
	 * Constructor NFEBatchJobLauncher class.
	 */
	public NFEBatchJobLauncher() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] springConfig  = {"/nfe-batch-context.xml"};
	 
		ApplicationContext context = 
			new ClassPathXmlApplicationContext(springConfig);
	 
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("APSJOB001");
	 
		try {
			
			JobParametersBuilder builder = new JobParametersBuilder();
			
			builder.addDate("REPORT_DATE", new Date(System.currentTimeMillis()));
			
			JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
			
			System.out.println("Exit Status : " + execution.getStatus());
	 
		} catch (Exception e) {
			CommonLogger.logStackTrace(e);
		}
	 
		System.out.println("Done");

	}

}
