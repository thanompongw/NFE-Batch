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
public class FTPFileTests {
	
	@Resource(name = "ftpFile")
	FTPFile ftpFile;
	
	@Autowired
	private BatchConfiguration batchConfig;

	/**
	 * Test method for {@link co.th.ktc.nfe.common.FTPFile#transferTargetFileToServer(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testTransferTargetFileToServer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link co.th.ktc.nfe.common.FTPFile#transferTargetFileFromServer(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testTransferTargetFileFromServer() {
		
		ftpFile = new FTPFile();
		
		try {
			String fileNameString = 
					ftpFile.download("MEDIACLR_LNDISB_D130716_out.TXT", 
							         "/data02/PRD/KTBCORP/KTC/APS/", 
							         batchConfig.getPathTemp(),
							         batchConfig.getFtpHost(),
							         batchConfig.getFtpUserName(),
							         batchConfig.getFtpPassword(),
							         Integer.parseInt(batchConfig.getFtpPort()));
			System.out.println(fileNameString);
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
