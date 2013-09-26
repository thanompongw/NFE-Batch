/**
 * 
 */
package co.th.ktc.nfe.report.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.th.ktc.nfe.common.DateUtils;
import co.th.ktc.nfe.report.domain.DateBean;
import co.th.ktc.nfe.report.domain.RemainBean;

/**
 * @author temp_dev1
 *
 */
@ContextConfiguration(locations={"/report-context.xml"})
 @RunWith(SpringJUnit4ClassRunner.class)
public class AbstractReportDao_Tests {
	
	@Resource(name = "applicationPending45DaysDao")
	private AbstractReportDao dao;

	/**
	 * Test method for {@link co.th.ktc.nfe.report.dao.AbstractReportDao#getBusinessBy60Days(java.lang.String)}.
	 */
	@Test
	public void testGetBusinessBy60Days() {
		
		List<DateBean> remainDates = dao.getBusinessBy60Days("03/07/2013");
	
		try {
		
			for (int i = 0; i < remainDates.size(); i++) {
				DateBean actualDate = remainDates.get(i);
				
				System.out.println(actualDate.getDateTo());
				
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.th.ktc.nfe.report.dao.AbstractReportDao#getRemainDays(java.lang.String)}.
	 */
	@Test
	public void testGetRemainDays() {
//		List<DateBean> remainDates = dao.getRemainDays("04/04/2013");
//		
//		try {
//		
//			for (int i = 0; i < remainDates.size(); i++) {
//				DateBean actualDate = remainDates.get(i);
//				
//				System.out.println(actualDate.getDateTo());
//				
//			}
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
	}

}
