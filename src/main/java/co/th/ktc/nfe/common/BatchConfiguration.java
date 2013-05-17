package co.th.ktc.nfe.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

	@Value("${batch.path.templates}")
	private String pathTemplate;

	@Value("${batch.path.output}")
	private String pathOutput;

	@Value("${batch.path.temp}")
	private String pathTemp;
	
	/**
	 * Default Constructor of BatchConfiguration Class.
	 */
	public BatchConfiguration() {
		
	}

	/**
	 * @return the pathTemplate
	 */
	@Bean
	public String getPathTemplate() {
		return pathTemplate;
	}

	/**
	 * @return the pathOutput
	 */
	@Bean
	public String getPathOutput() {
		return pathOutput;
	}

	/**
	 * @return the pathTemp
	 */
	@Bean
	public String getPathTemp() {
		return pathTemp;
	}

}
