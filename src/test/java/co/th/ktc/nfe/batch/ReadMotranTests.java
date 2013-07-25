package co.th.ktc.nfe.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CosNaming._BindingIteratorImplBase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.th.ktc.nfe.batch.bo.BatchBO;
import co.th.ktc.nfe.batch.domain.MotranHeaderBean;
import co.th.ktc.nfe.batch.domain.MotranDetailBean;

public class ReadMotranTests extends TestCase {

	@Test
	public void testExecute() {
		BeanReader in = null;
		InputStream is = null;
		try {
			// create a BeanIO StreamFactory
			StreamFactory factory = StreamFactory.newInstance();
			// load the mapping file from the working directory
//			factory.load("/resources/montran.xml");
			
			is = new FileInputStream("D:\\workspace-sts\\NFE-Batch\\src\\main\\resources\\montran.xml");
			
			factory.load(is);
			// create a BeanReader to read from "input.csv"
			in = factory.createReader("montran", new File(
					"D:\\NFE\\report\\TEMP\\MEDIACLR_LNDISB_D130716_out.TXT"));
			Object record = null;
			// read records from "input.csv"
			while ((record = in.read()) != null) {
				if (in.getRecordName().equals("header")) {
					MotranHeaderBean bean = (MotranHeaderBean) record;

					System.out.println("Record Type : " + bean.getRecordType());
					System.out.println("Effective Date : " + bean.getEffectiveDate());
				} else {
					MotranDetailBean bean = (MotranDetailBean) record;

					System.out.println("Record Type : " + bean.getRecordType());
					System.out.println("Effective Date : " + bean.getEffectiveDate());
					System.out.println("App No : " + bean.getAppNo());
					System.out.println("Product Code : " + bean.getProductCode());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
			
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		assertTrue("Success", true);
	}

}
