module com.markbucciarelli.consolelogger {
  provides java.lang.System.LoggerFinder
    with com.markbucciarelli.consolelogger.ConsoleLoggerFinder;
}
