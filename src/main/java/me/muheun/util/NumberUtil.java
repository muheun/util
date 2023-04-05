package me.muheun.util;


import org.apache.commons.lang3.math.NumberUtils;

public class NumberUtil extends NumberUtils {

  public final static int toIntWithMark(String s) {
    if (s.startsWith("+")) {
      return toIntWithMark(s.substring(1));
    } else {
      return toInt(s);
    }
  }

  public final static int toInt(Object obj) {
    return toInt(obj, 0);
  }

  public final static int toInt(Object obj, int dft) {
    String str = ObjectUtil.toString(obj, String.valueOf(dft));
    return toInt(str);
  }

  public final static boolean isModify(long l, int mod) {
    return l % mod == 0;
  }
}
