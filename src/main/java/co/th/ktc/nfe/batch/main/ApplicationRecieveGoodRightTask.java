/**
 * 
 */
package co.th.ktc.nfe.batch.main;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import co.th.ktc.nfe.report.bo.impl.ApplicationRecieveGoodRightBO;

/**
 * @author Deedy
 *
 */
public class ApplicationRecieveGoodRightTask implements Tasklet {
	
	@Autowired
	private ApplicationRecieveGoodRightBO bo;

	/**
	 * 
	 */
	public ApplicationRecieveGoodRightTask() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	public RepeatStatus execute(StepContribution step, ChunkContext chunk)
			throws Exception {
		
		Integer processStatus = bo.execute(null);
		
		if (processStatus == 0) {
			return RepeatStatus.FINISHED;
		}
		return RepeatStatus.CONTINUABLE;
	}

}
