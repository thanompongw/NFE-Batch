package co.th.ktc.nfe.batch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.th.ktc.nfe.batch.bo.BatchBO;

@ContextConfiguration(locations={"/nfe-batch-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SMSRLTests extends TestCase {
	
	@Resource(name = "smsRLService")
	private BatchBO bo;

	@Test
	public void testExecute() {
		
		Map<String, String> parameter = new HashMap<String, String>();
		
		parameter.put("BATCH_DATE", "10/06/2013");
			
		int status = bo.execute(parameter);
		
		if (status == 1) {
			fail("Generate Report Error!!");
		} else {
			assertEquals(0, status);
		}
	}

}
