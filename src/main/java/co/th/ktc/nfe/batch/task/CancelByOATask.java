package co.th.ktc.nfe.batch.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import co.th.ktc.nfe.common.DateTimeUtils;
import co.th.ktc.nfe.report.bo.ReportBO;

public class CancelByOATask implements Tasklet {

	@Resource(name = "cancelByOAService")
	private ReportBO bo;

	/**
	 * 
	 */
	public CancelByOATask() {
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
		
		Date paramDate = (Date) context.getAttribute("REPORT_DATE");
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		
		parameterMap.put("REPORT_DATE", DateTimeUtils.toString(paramDate, 
				                             				   DateTimeUtils.DEFAULT_DATE_FORMAT));
		
		Integer processStatus = bo.execute(parameterMap);

		if (processStatus == 0) {
			return RepeatStatus.FINISHED;
		}
		return RepeatStatus.CONTINUABLE;
	}

}
