package me.muheun.util;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;

public abstract class StringUtil extends StringUtils {

	private StringUtil() {
	}

	public final static String T = "T";
	public final static String TRUE = "TRUE";

	// for jdk8 script engine
	public static boolean isBlank(String val) {
		return StringUtils.isBlank(val);
	}

	public static boolean isEmpty(String val) {
		return StringUtils.isEmpty(val);
	}

	public static boolean isEmpty(Object obj) {
		return isEmpty(toString(obj, EMPTY));
	}

	public static boolean isBlank(Object object) {
		return isBlank(toString(object, EMPTY));
	}

	public static boolean isAlphaNumericUnderBar(String str) {
		if (str == null) {
			return false;
		}
		return str.matches("[a-zA-Z0-9_]+");
	}

	public static String coalesce(String... strs) {
		for (String str : strs) {
			if (isNotBlank(str)) {
				return str;
			}
		}
		return null;
	}

	public static boolean isSmallAlphaNumericUnderBar(String str) {
		return str != null && str.matches("[a-z0-9_]+");
	}

	public static boolean isIncludeIgnoreCase(String[] array, String arg) {
		if ((array == null) || array.length == 0 || arg == null) {
			return false;
		}
		for (String str : array) {
			if (arg.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	public static String join(int[] ints) {
		return join(ints, ',');
	}

	public static String join(int[] ints, char c) {
		String[] s = new String[ints.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = toString(ints[i]);
		}
		return StringUtils.join(s, c);
	}

	public static String[] splitWorker(String str, String separator) {
		if (separator == null) {
			return split(str);
		} else if (separator.length() == 1) {
			return split(str, separator);
		} else {
			List<String> list = ListUtil.newList();
			int idx;
			while (true) {
				idx = str.indexOf(separator);
				if (idx == -1) {
					list.add(str);
					break;
				}
				String split = str.substring(0, idx);
				if (isNotEmpty(split)) {
					list.add(split);
				}
				str = str.substring(idx + separator.length());
			}
			return list.toArray(new String[0]);
		}
	}

	public static String escapeControlChar(String str) {
		char[] charArray = str.toCharArray();
		char[] target = new char[charArray.length];

		int k = 0;
		for (char ch : charArray) {
			// if (charArray[i] >= 32 || charArray[i] == '\n' || charArray[i] ==
			// '\r' || charArray[i] == '\t') {
			if (Character.isIdentifierIgnorable(ch)) {
				continue;
			}
			if (ch >= 32 || ch == '\n' || ch == '\r' || ch == '\t') {
				target[k++] = ch;
			}
		}
		char[] result = new char[k];
		System.arraycopy(target, 0, result, 0, k);

		return new String(result);
	}

	public static String escapeControlChar2(String str) {
		char[] charArray = str.toCharArray();
		StringBuilder buffer = new StringBuilder();
		for (char ch : charArray) {
			if (ch >= 32 || ch == '\n' || ch == '\r' || ch == '\t') {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static Vector<Object> listToVector(List<Object> list) {
		return new Vector<>(list);

	}

	public static Vector<Object> arrayToVector(Object[] obj) {
		return new Vector<>(Arrays.asList(obj));
	}

	public static boolean toBoolean(String str) {
		if (isBlank(str)) {
			return false;
		}
		return "T".equalsIgnoreCase(str) || "TRUE".equalsIgnoreCase(str);
	}

	public static int[] stringToArrayInt(String arrIntVal) {
		return stringToArrayInt(arrIntVal, ",");
	}

	public static int[] stringToArrayInt(String arrIntVal, String div) {
		String[] strArtIds = StringUtils.split(arrIntVal, div);
		int[] artIds = new int[strArtIds.length];
		for (int i = 0; i < strArtIds.length; i++) {
			artIds[i] = Integer.parseInt(strArtIds[i]);
		}
		return artIds;
	}

	private static final long byteSize = 1024;

	public static String toFileSize(long size) {
		NumberFormat nf = NumberFormat.getInstance();
		long result;
		if (size < byteSize) {
			result = (size != 0) ? 1 : 0;
		} else {
			long modSize = size % byteSize;
			if (modSize != 0) {
				result = (size / byteSize) + 1;

			} else {
				result = (size / byteSize);
			}
		}
		return nf.format(result) + " KB";
	}

	public static String filterHTML(String value) {
		if (value == null) {
			return (null);
		}
		char[] content = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuilder result = new StringBuilder(content.length + 50);
		for (char ch : content) {
			switch (ch) {
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
				case '&':
					result.append("&amp;");
					break;
				case '"':
					result.append("&quot;");
					break;
				case '\'':
					result.append("&#39;");
					break;
				case ' ':
					result.append("&nbsp;");
					break;
				default:
					result.append(ch);
			}
		}
		return (result.toString());
	}

	public static String toString(Object value) {
		return toString(value, EMPTY);
	}

	public static boolean isBetween(String value, String start, String end) {
		return startsWith(value, start) && endsWith(value, end);
	}

	public static String toString(Object value, String defaultString) {
		return (value == null) ? defaultString : value.toString();
	}

	public static String toHexString(byte[] b) throws Exception {
		StringBuilder result = new StringBuilder();
		for (byte value : b) {
			result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
}
