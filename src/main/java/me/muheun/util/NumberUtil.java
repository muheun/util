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
        return toInt(StringUtil.toString(obj), dft);
    }

    public final static long toLong(Object obj) {
        return toLong(obj, 0L);
    }

    public final static long toLong(Object obj, long dft) {
        return toLong(StringUtil.toString(obj), dft);
    }

    public final static boolean isModify(long l, int mod) {
        return l % mod == 0;
    }
}
