package co.th.ktc.nfe.batch.task;

import javax.annotation.Resource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import co.th.ktc.nfe.batch.bo.BatchBO;

public class MontranTask implements Tasklet {

	@Resource(name = "montranService")
	private BatchBO bo;

	/**
	 * 
	 */
	public MontranTask() {
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
		
//		Date paramDate = (Date) context.getAttribute("REPORT_DATE");
//		
//		Map<String, String> parameterMap = new HashMap<String, String>();
//		
//		parameterMap.put("REPORT_DATE", DateTimeUtils.toString(paramDate, 
//				                             				   DateTimeUtils.DEFAULT_DATE_FORMAT));
//		
//		Integer processStatus = bo.execute(parameterMap);
		Integer processStatus = bo.execute(null);

		if (processStatus == 0) {
			return RepeatStatus.FINISHED;
		}
		return RepeatStatus.FINISHED;
	}

}
