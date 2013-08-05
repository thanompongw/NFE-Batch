package co.th.ktc.nfe.batch.main;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import co.th.ktc.nfe.common.CommonLogger;

@ContextConfiguration(locations={"/report-context.xml"})
public class NFEBatchJobLauncher {
	
	private static JobLauncher jobLauncher;

	private static Job job;

	/**
	 * @param jobLauncher the jobLauncher to set
	 */
	@Autowired
	public void setJobLauncher(JobLauncher jobLauncher) {
		NFEBatchJobLauncher.jobLauncher = jobLauncher;
	}

	/**
	 * @param job the job to set
	 */
	@Autowired
	public void setJob(Job job) {
		NFEBatchJobLauncher.job = job;
	}

	public static void main(String[] args) {
		
		try {
			
			JobParametersBuilder builder = new JobParametersBuilder();
			
			builder.addDate("REPORT_DATE", new Date(System.currentTimeMillis()));
			
			jobLauncher.run(job, builder.toJobParameters());
		} catch (JobExecutionAlreadyRunningException e) {
			CommonLogger.logStackTrace(e);
		} catch (JobRestartException e) {
			CommonLogger.logStackTrace(e);
		} catch (JobInstanceAlreadyCompleteException e) {
			CommonLogger.logStackTrace(e);
		} catch (JobParametersInvalidException e) {
			CommonLogger.logStackTrace(e);
		}
		
		System.exit(0);
	}

}
