/**
 * 
 */
package co.th.ktc.nfe.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import co.th.ktc.nfe.batch.exception.CommonException;

/**
 * @author Deedy
 * 
 */
@Component
public class DateUtils {

	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	public static final String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	/**
	 * 
	 */
	public DateUtils() {
	}

	public Timestamp convertToDateTime(String dateTimeStr, String dateTimeFormat)
			throws CommonException {
		Timestamp dateTime = null;
		SimpleDateFormat df = new SimpleDateFormat();
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
				ErrorUtil.handleSystemException(e);
			}
		}

		return dateTime;
	}

	public String getCurrentDateTime(String dateTimeFormat) {
		SimpleDateFormat df = new SimpleDateFormat();
		if (dateTimeFormat == null || dateTimeFormat.isEmpty()) {
			df.applyPattern(DEFAULT_DATETIME_FORMAT);
		} else {
			df.applyPattern(dateTimeFormat);
		}

		Date currentDate = new Date(System.currentTimeMillis());

		String dateTime = df.format(currentDate);

		return dateTime;
	}

	public String convertFormatDateTime(String dateTimeStr, String formatFrom,
			String formateTo) throws CommonException {
		SimpleDateFormat df = new SimpleDateFormat();
		if (formatFrom == null || formatFrom.isEmpty()) {
			df.applyPattern(DEFAULT_DATETIME_FORMAT);
		} else {
			df.applyPattern(formatFrom);
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
				ErrorUtil.handleSystemException(e);
			}
		}

		return dateTimeTo;
	}

	public Date toDate(String date, String format) throws CommonException {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			ErrorUtil.handleSystemException(e);
		}
		return null;
	}

	public String toString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

		return sdf.format(date);
	}

	public Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public String addDays(Date date, int days, String format)
			throws ParseException {

		Date addedDate = addDays(date, days);
		return toString(addedDate, format);
	}

}
