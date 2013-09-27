package co.th.ktc.nfe.batch.bo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.th.ktc.nfe.batch.exception.CommonException;

public abstract class BatchBO {
	
	public abstract Integer execute(Map<String, String> parameter);
	
	public abstract void write(Map<String, String> parameter) throws CommonException;
	
	public Boolean isEmpty(Object obj) {
		
		if (obj == null) {
			return true;
		}
		
		if (obj instanceof String) {
			if (((String) obj).isEmpty()) {
				return true;
			}
		} else if (obj instanceof Double) {
			if (((Double) obj) > 0.0D) {
				return true;
			}
		} else if (obj instanceof Integer) {
			if (((Integer) obj) > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean isValidDate(String date) {
		
		if (date == null) {
			return false;
		}
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			df.setLenient(false);
			df.parse(date);
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}
	
	private Pattern pattern;
	private Matcher matcher;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	/**
	 * Validate email with regular expression
	 * 
	 * @param email hex for validation
	 * @return true valid email, false invalid email
	 */
	public Boolean isValidEmail(final String email) {
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
	
	public Boolean isNumeric(String value) {
		return value.matches("-?\\d+(\\.\\d+)?");
	}

}
