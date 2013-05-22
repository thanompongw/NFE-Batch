/**
 * 
 */
package co.th.ktc.nfe.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Deedy
 *
 */
public class DateTimeUtils {
	
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	
	public static final String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	private static SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	/**
	 * 
	 */
	public DateTimeUtils() {
	}
	
	public static Timestamp convertToDateTime(String dateTimeStr, String dateTimeFormat) {
		Timestamp dateTime = null;
		if (dateTimeFormat == null || dateTimeFormat.isEmpty()) {
			df.applyPattern(DEFAULT_DATETIME_FORMAT);
		} else {
			df.applyPattern(dateTimeFormat);
		}
		
		if (dateTimeStr == null || dateTimeStr.isEmpty()) {
			dateTime = null;
		} else {
			try {
				dateTime = new Timestamp(df.parse(dateTimeStr).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dateTime;
	}
	
	public static String getCurrentDateTime(String dateTimeFormat) {
		if (dateTimeFormat == null || dateTimeFormat.isEmpty()) {
			df.applyPattern(DEFAULT_DATETIME_FORMAT);
		} else {
			df.applyPattern(dateTimeFormat);
		}
		
		Date currentDate = new Date(System.currentTimeMillis());
		
		String dateTime = df.format(currentDate);
		
		return dateTime;
	}
	
	public static String convertFormatDateTime(String dateTimeStr,
											   String formatForm,
											   String formateTo) {
		if (formatForm == null || formatForm.isEmpty()) {
			df.applyPattern(DEFAULT_DATETIME_FORMAT);
		} else {
			df.applyPattern(formatForm);
		}
		
		String dateTimeTo = null;
		
		if (dateTimeStr == null || dateTimeStr.isEmpty()) {
			dateTimeTo = null;
		} else {
			try {
				Date dateTime = new Date(df.parse(dateTimeStr).getTime());
				
				if (formateTo == null || formateTo.isEmpty()) {
					df.applyPattern(DEFAULT_DATETIME_FORMAT);
				} else {
					df.applyPattern(formateTo);
				}
				
				dateTimeTo = df.format(dateTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dateTimeTo;
	}
	public static Date toDate(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.US);

        return sdf.parse(date);
    }

}
