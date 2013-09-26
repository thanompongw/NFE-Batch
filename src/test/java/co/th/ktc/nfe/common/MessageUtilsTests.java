/**
 * 
 */
package co.th.ktc.nfe.common;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author temp_dev2
 *
 */
@ContextConfiguration(locations={"/nfe-batch-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageUtilsTests {

	/**
	 * Test method for {@link co.th.ktc.nfe.common.FTPFile#transferTargetFileFromServer(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetMessage() {
		
		try {
			System.out.println(MessageUtils.getMessage("MSTD0005AERR"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.th.ktc.nfe.common.FTPFile#deleteTargetFile(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteTargetFile() {
		fail("Not yet implemented");
	}

}
