package co.th.ktc.nfe.batch.task;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import co.th.ktc.nfe.report.bo.ReportBO;

public class ApplicationReceiveTask implements Tasklet {

	@Autowired
	private ReportBO bo;

	/**
	 * 
	 */
	public ApplicationReceiveTask() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
	 */
	public RepeatStatus execute(StepContribution step, ChunkContext context) 
			throws Exception {
		
		Integer processStatus = bo.execute(null);

		if (processStatus == 0) {
			return RepeatStatus.FINISHED;
		}
		return RepeatStatus.CONTINUABLE;
	}

}
