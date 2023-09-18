package me.muheun.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class ExceptionUtil extends ExceptionUtils {

	public static void printStackTrace(final Throwable throwable) {
		printStackTrace(throwable, 15);
	}

	public static void printStackTrace(final Throwable throwable, final int limit) {
		if (throwable == null) {
			return;
		}
		StringBuilder sb = new StringBuilder("\n\n");
		final String[] trace = getRootCauseStackTrace(throwable, limit);
		for (final String element : trace) {
			sb.append(element).append("\n");
		}
		sb.append("\n");
		log.debug(sb.toString());
	}

	private static String[] getRootCauseStackTrace(final Throwable throwable, int limit) {
		if (throwable == null) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		final Throwable[] throwables = getThrowables(throwable);
		final int count = throwables.length;
		final List<String> frames = new ArrayList<>();
		List<String> nextTrace = getStackFrameList(throwables[count - 1], limit);
		for (int i = count; --i >= 0; ) {
			final List<String> trace = nextTrace;
			if (i != 0) {
				nextTrace = getStackFrameList(throwables[i - 1], limit);
				removeCommonFrames(trace, nextTrace);
			}
			if (i == count - 1) {
				frames.add(throwables[i].toString());
			} else {
				frames.add(" [wrapped] " + throwables[i].toString());
			}
			frames.addAll(trace);
		}
		return frames.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
	}

	private static List<String> getStackFrameList(final Throwable t, int limit) {
		final String stackTrace = getStackTrace(t);
		final String linebreak = System.lineSeparator();
		final StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
		final List<String> list = new ArrayList<>();
		int line = 1;
		while (frames.hasMoreTokens()) {
			final String token = frames.nextToken();
			// Determine if the line starts with <whitespace>at
			final int at = token.indexOf("at");
			if (at != -1 && token.substring(0, at).trim().isEmpty()) {
				if (limit > 0 && limit == line) {
					break;
				}
				list.add(token);
				line++;
			}
		}
		return list;
	}

}
