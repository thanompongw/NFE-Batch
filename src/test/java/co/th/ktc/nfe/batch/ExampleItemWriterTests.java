package co.th.ktc.nfe.batch;

import co.th.ktc.nfe.batch.writer.Card51ItemWriter;

import junit.framework.TestCase;

public class ExampleItemWriterTests extends TestCase {

	private Card51ItemWriter writer = new Card51ItemWriter();
	
	public void testWrite() throws Exception {
		writer.write(null); // nothing bad happens
	}

}
