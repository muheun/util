package me.muheun.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

public class HtmlUtil {

    /**
     * <p>
     * HTML 태그를 모두 제거후 리턴한다.
     * </p>
     *
     * @param object (변경할 문자형 객체)
     * @return String (변경후 문자)
     */
    public static String deleteHtml(Object object) {
      if (StringUtil.isBlank(object)) {
        return "";
      }
        String str = String.valueOf(object);
        str = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        str = str.replaceAll("\r\n", "");
        str = str.replaceAll("\r", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("\t", "");
        return str;
    }

    /**
     * <p>
     * 개행문자를 &lt;br&gt; 태그로 변경후 리턴한다.
     * </p>
     *
     * @param object (변경할 문자형 객체)
     * @return String (변경후 문자)
     */
    public static String encodeLine(Object object) {
      if (StringUtil.isBlank(object)) {
        return "";
      }
        String str = String.valueOf(object);
        str = str.replaceAll("\r\n", "<br />");
        str = str.replaceAll("\r", "<br />");
        str = str.replaceAll("\n", "<br />");
        return str;
    }

    /**
     * <p>
     * &lt;br&gt; 태그를 개행문자로 변경후 리턴한다.
     * </p>
     *
     * @param object (변경할 문자형 객체)
     * @return String (변경후 문자)
     */
    public static String decodeLine(Object object) {
      if (StringUtil.isBlank(object)) {
        return "";
      }
        String str = String.valueOf(object);
        str = str.replaceAll("<br>", "\r\n");
        str = str.replaceAll("<br />", "\r\n");
        return str;
    }

    /**
     * <p>
     * HTML 태그를 인코딩후 리턴한다.
     * </p>
     *
     * @param object (변경할 문자형 객체)
     * @return String (변경후 문자)
     */
    public static String encodeHtml(Object object) {
        return encodeHtml(object, false);
    }

    /**
     * <p>
     * HTML 태그를 인코딩하고 개행문자 처리후 리턴한다.
     * </p>
     *
     * @param object       (변경할 문자형 객체)
     * @param isEncodeLine (개행문자를 &lt;br&gt;로 변경여부)
     * @return String (변경후 문자)
     */
    public static String encodeHtml(Object object, boolean isEncodeLine) {
      if (StringUtil.isBlank(object)) {
        return "";
      }
        String str = String.valueOf(object);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(str.charAt(i));
            }
        }
        return isEncodeLine ? encodeLine(sb.toString()) : sb.toString();
    }

    /**
     * <p>
     * HTML 태그를 디코딩후 리턴한다.
     * </p>
     *
     * @param object (변경할 문자형 객체)
     * @return String (변경후 문자)
     */
    public static String decodeHtml(Object object) {
        return decodeHtml(object, false);
    }

    /**
     * <p>
     * HTML 태그를 디코딩하고 개행문자 처리후 리턴한다.
     * </p>
     *
     * @param object       (변경할 문자형 객체)
     * @param isDecodeLine (&lt;br&gt; 을 개행문자로 변경여부)
     * @return String (변경후 문자)
     */
    public static String decodeHtml(Object object, boolean isDecodeLine) {
      if (StringUtil.isBlank(object)) {
        return "";
      }
        String str = String.valueOf(object);
        str = str.replaceAll("&gt;", ">");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&#39;", "\'");
        str = str.replaceAll("&amp;", "&");
        return isDecodeLine ? decodeLine(str) : str;
    }

}
