package me.muheun.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class Debug extends OutputStream {

  public final static String PROPERTY_KEY = "framework.debug.out";
  private static final LimitedList<String> imsi = new LimitedList<String>(500);
  private final static Integer ZERO = new Integer(0);

  private static void out(Level level, CharSequence message) {
    if (level.getCode() < PrintLevel.getCode()) {
      return;
    }
    out(System.out, ZERO, level, message);
  }

  private static void err(Level level, CharSequence message) {
    if (level.getCode() < PrintLevel.getCode()) {
      return;
    }
    out(System.err, ZERO, level, message);
  }

  private static Level PrintLevel = Level.Json;

  public static void setPrintLevel(Level level) {
    PrintLevel = level;
  }

  private static void out(final PrintStream out, final int indent, final Level level, CharSequence message) {

    if ("off".equals(System.getProperty(PROPERTY_KEY))) {
      return;
    } else if ("system".equals(System.getProperty(PROPERTY_KEY))) {
      System.out.print(message);
      return;
    }

    StackTraceElement[] stackTraces = new Exception().getStackTrace(); //Thread.currentThread().getStackTrace() ;//  new Exception().getStackTrace();
    String thisName = Debug.class.getName();
    for (StackTraceElement st : stackTraces) {
      String className = st.getClassName();
      if (!thisName.equals(className) && (!className.contains("Debug") && (!className.startsWith("org.apache")))) {

        String callerClass = st.getClassName();
        String callerMethod = st.getMethodName();
        int line = st.getLineNumber();
        String currDay = CalendarUtils.getCurrentDate("HH:mm:ss.SSS");
        String outMessage = level.getPrintName() + " => " + message
            + " by " + callerClass + ":" + callerMethod + "(" + line + ")" + "[" + currDay + "]";
        synchronized (imsi) {
          imsi.add(outMessage);
        }
        out.println(outMessage);
        break;
      }
    }
  }

  private static void print(Level level, CharSequence message) {
    out(level, message);
  }

  private static void printErr(Level level, CharSequence message) {
    err(level, message);
  }

  public static void line() {
    line("");
  }

  public void write(int b) throws IOException {
    System.out.print(b);
  }

  public static void line(Object... objs) {
    linec('=', objs);
  }

  public static void linec(char c, Object... objs) {
    String s = StringUtil.repeat(String.valueOf(c), 20);
    print(Level.Debug, Arrays.deepToString(new Object[]{s, objs}));
  }

  public static void error(Object... objs) {
    error(Arrays.deepToString(objs));
  }

  private static void error(String message) {
    printErr(Level.Error, message);
  }

  public static void warn(Object... objs) {
    warn(Arrays.deepToString(objs));
  }

  private static void warn(CharSequence message) {
    printErr(Level.Warn, message);
  }

  public static void debug(Object... objs) {
    debug(Arrays.deepToString(objs));
  }

  private static void debug(CharSequence message) {
    print(Level.Debug, message);
  }

  public static void info(Object... objs) {
    info(Arrays.deepToString(objs));
  }

  private static void info(CharSequence message) {
    print(Level.Info, message);
  }

  public static void json(Object obj) {
    out(Level.Json, JSON.prettyString(obj) + " -> " + obj.getClass().getTypeName());
  }

  public static void println(Object... obj) {
    StringBuilder sb = new StringBuilder();
    for (Object object : obj) {
      sb.append(object).append(" ");
    }
    System.out.println(sb);
  }

  public static void trace(String message) {
    debug(message);
  }

  public enum Level {
    Json(0, "JSON"),
    Debug(1, "DEBUG"),
    Info(2, "INFO"),
    Warn(4, "WARN"),
    Error(8, "ERROR");

    private final int code;
    private final String printName;

    Level(int code, String printName) {
      this.code = code;
      this.printName = String.format("%5s", printName);
    }

    public int getCode() {
      return code;
    }

    public String getPrintName() {
      return printName;
    }
  }

}
