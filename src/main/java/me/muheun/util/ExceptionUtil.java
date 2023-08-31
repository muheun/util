package me.muheun.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class ExceptionUtil extends ExceptionUtils {

	/**
	 * <p>
	 * Error 정보를 Console에 출력한다.
	 * </p>
	 *
	 * @param e (Exception 객체)
	 */
	public static void printConsole(Exception e, boolean full, String... messages) {
		StringBuilder sb = new StringBuilder("\n\n");
//        sb.append("+=================================================================================================================================+\n");
		if (!full) {
			sb.append(getShortStackTrace(e)).append("\n");
			for (String msg : messages) {
				sb.append("\t\t").append(msg).append("\n");
			}
		} else {
			for (String msg : getRootCauseStackTrace(e)) {
				sb.append(msg).append("\n");
			}
		}
		sb.append("\n");
//        sb.append("+=================================================================================================================================+\n");
		log.debug(sb.toString());
		sb = null;
	}

	public static void printConsole(Exception e, String... messages) {
		printConsole(e, false, messages);
	}

	public static void printConsole(Exception e) {
		printConsole(e, false);
	}

	public static void printConsole(Exception e, boolean full) {
		printConsole(e, full, new String[0]);
	}

	/**
	 * <p>
	 * Error 정보를 Console에 출력한다.
	 * </p>
	 *
	 * @param e       (Exception 객체)
	 * @param clazz   (에러가 발생한 Class 객체)
	 * @param message (추가할 Exception 메시지)
	 */
	public static void printConsole(Exception e, Class<?> clazz, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(
				"+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("[").append(DateUtil.getNowAll()).append("] [ERROR] ").append(clazz.getName()).append("\n");
		sb.append("(").append(e.toString()).append(") - ").append(StringUtil.clean(message)).append("\n");
		sb.append(
				"+---------------------------------------------------------------------------------------------------------------------------------+\n");
		log.debug(sb.toString());
		sb = new StringBuilder();
		sb.append(
				"\n+=================================================================================================================================+\n");
		sb.append(getShortStackTrace(e));
		sb.append(
				"\n+=================================================================================================================================+\n");
		log.debug(sb.toString());
		sb = null;
	}

	/**
	 * <p>
	 * 짧은 Exception StackTrace를 리턴한다.
	 * </p>
	 *
	 * @param e (Exception 객체)
	 * @return String (Exception StackTrace)
	 */
	public static String getShortStackTrace(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		String className = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		String fileName = ste[0].getFileName();
		int lineNumber = ste[0].getLineNumber();
		String exceptionClassName = StringUtil.clean(e.getClass()).replaceFirst("class ", "");
		//        sb.append(exceptionClassName).append("\n");
		return exceptionClassName + ": " + e.getMessage() + "\n"
				+ "\tat " + className + "." + methodName
				+ "(" + fileName + ":" + lineNumber + ")";
	}

	/**
	 * <p>
	 * Exception StackTrace를 리턴한다.
	 * </p>
	 *
	 * @param e (Exception 객체)
	 * @return String (Exception StackTrace)
	 */
	public static String getStackTrace(Exception e) {
		StringBuffer sb = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sb = sw.getBuffer();
			return sb.toString();
		} catch (Exception e2) {
			log.warn("Exception StackTrace를 가져올 수 없습니다.");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception ignored) {
				}
			}
			if (sw != null) {
				try {
					sw.close();
				} catch (Exception ignored) {
				}
			}
		}
		return "";
	}

	/**
	 * <p>
	 * Exception 메시지에 사용자 메시지를 추가하여 리턴한다.
	 * </p>
	 *
	 * @param e       (Exception 객체)
	 * @param message (추가할 Exception 메시지)
	 * @return String (Exception 메시지)
	 */
	public static String addMessage(Exception e, String message) {
		return e.toString() + "\n"
				+ "Stacktrace: " + getStackTrace(e)
				+ "Caused by: " + message + "\n";
	}

}
