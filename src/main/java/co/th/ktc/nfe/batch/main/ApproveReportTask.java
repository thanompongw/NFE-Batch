package co.th.ktc.nfe.batch.main;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import co.th.ktc.nfe.report.bo.impl.ApproveBO;

public class ApproveReportTask implements Tasklet {

	@Autowired
	private ApproveBO bo;

	/**
	 * 
	 */
	public ApproveReportTask() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,ChunkContext chunkContext) 
			throws Exception {
		
		Integer processStatus = bo.execute(null);

		if (processStatus == 0) {
			return RepeatStatus.FINISHED;
		}
		return RepeatStatus.CONTINUABLE;
	}

}
