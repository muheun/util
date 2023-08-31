package me.muheun.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public abstract class DateUtil extends DateUtils {

	private DateUtil() {
	}

	/**
	 * <p>현재의 날짜와 시간을 기본형식 'yyyy-MM-dd HH:mm:ss'로 리턴한다.</p>
	 * <p/>
	 * <pre>
	 * DateUtil.getNowAll() = "2009-12-20 20:15:57"
	 * </pre>
	 *
	 * @return String (현재 날짜와 시간)
	 */
	public static String getNowAll() {
		return toDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * <p>Date 객체를 원하는 날짜형식 문자로 변경후 리턴한다.</p>
	 *
	 * @param date   (Date 객체)
	 * @param format (변경할 포멧)
	 * @return String (변경후 날짜)
	 */
	public static String toDateFormat(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (StringUtil.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
