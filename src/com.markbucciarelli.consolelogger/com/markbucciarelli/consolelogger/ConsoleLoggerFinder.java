package com.markbucciarelli.consolelogger;

public class ConsoleLoggerFinder extends System.LoggerFinder {

  @Override
  public System.Logger getLogger(String name, Module module) {
    return new ConsoleLogger();
  }
}
