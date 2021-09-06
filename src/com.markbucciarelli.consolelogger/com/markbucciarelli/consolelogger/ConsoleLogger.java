package com.markbucciarelli.consolelogger;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * A ConsoleLogger outputs INFO log messages to stdout and ERROR to stderr.
 *
 * <p>
 *   If the environmental variable DEBUG is defined, then DEBUG and TRACE
 *   log levels are also logged to stdout.  All other levels are ignored.
 *
 * <p>
 *   For an interesting take on logging, see Dave Cheney's 2015 blog post
 *   <a href="https://dave.cheney.net/2015/11/05/lets-talk-about-logging">Let's
 *   talk about logging</a>.
 */
public class ConsoleLogger implements System.Logger {

  @Override
  public String getName() {
    return "ConsoleLogger";
  }

  @Override
  public boolean isLoggable(Level level) {
    boolean y = level == Level.INFO || level == Level.ERROR;
    if (System.getenv("DEBUG") != null) {
      y &= (level == Level.DEBUG || level == Level.TRACE);
    }
    return y;
  }

  @Override
  public void log(
    Level level,
    ResourceBundle bundle,
    String msg,
    Throwable thrown
  ) {
    out(level).printf("%s %s - %s%n", now(), msg, thrown);
  }

  @Override
  public void log(
    Level level,
    ResourceBundle bundle,
    String format,
    Object... params
  ) {
    out(level).printf("%s %s%n", now(), MessageFormat.format(format, params));
  }

  @SuppressWarnings("java:S106")
  private PrintStream out(Level x) {
    if (x == Level.ERROR) {
      return System.err;
    }
    return System.out;
  }

  private String now() {
    return (new Date()).toString();
  }
}
