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

	@Value("${batch.path.output.CSP}")
	private String pathOutputCSP;

	@Value("${batch.path.output.iNet}")
	private String pathOutputINet;

	@Value("${batch.path.output.SMS}")
	private String pathOutputSMS;

	@Value("${batch.path.temp}")
	private String pathTemp;

	@Value("${batch.FTP.host}")
	private String ftpHost;

	@Value("${batch.FTP.username}")
	private String ftpUserName;

	@Value("${batch.FTP.password}")
	private String ftpPassword;

	@Value("${batch.FTP.port}")
	private String ftpPort;

	@Value("${batch.FTP.CSP.host}")
	private String ftpCSPHost;

	@Value("${batch.FTP.CSP.username}")
	private String ftpCSPUserName;

	@Value("${batch.FTP.CSP.password}")
	private String ftpCSPPassword;

	@Value("${batch.FTP.CSP.port}")
	private String ftpCSPPort;
	
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
	 * @return the pathOutputCSP
	 */
	public String getPathOutputCSP() {
		return pathOutputCSP;
	}

	/**
	 * @return the pathOutputINet
	 */
	public String getPathOutputINet() {
		return pathOutputINet;
	}

	/**
	 * @return the pathOutputSMS
	 */
	public String getPathOutputSMS() {
		return pathOutputSMS;
	}

	/**
	 * @return the pathTemp
	 */
	@Bean
	public String getPathTemp() {
		return pathTemp;
	}

	/**
	 * @return the ftpHost
	 */
	public String getFtpHost() {
		return ftpHost;
	}

	/**
	 * @return the ftpUserName
	 */
	public String getFtpUserName() {
		return ftpUserName;
	}

	/**
	 * @return the ftpPassword
	 */
	public String getFtpPassword() {
		return ftpPassword;
	}

	/**
	 * @return the ftpPort
	 */
	public String getFtpPort() {
		return ftpPort;
	}

	/**
	 * @return the ftpCSPHost
	 */
	public String getFtpCSPHost() {
		return ftpCSPHost;
	}

	/**
	 * @return the ftpCSPUserName
	 */
	public String getFtpCSPUserName() {
		return ftpCSPUserName;
	}

	/**
	 * @return the ftpCSPPassword
	 */
	public String getFtpCSPPassword() {
		return ftpCSPPassword;
	}

	/**
	 * @return the ftpCSPPort
	 */
	public String getFtpCSPPort() {
		return ftpCSPPort;
	}

}
