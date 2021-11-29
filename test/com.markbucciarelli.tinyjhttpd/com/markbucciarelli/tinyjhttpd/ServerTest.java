package com.markbucciarelli.tinyjhttpd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.markbucciarelli.tinyjhttpd.Server.CommandLineArguments;
import org.junit.jupiter.api.Test;

class ServerTest {

  @Test
  void testHost() {
    // setup
    String[] args = new String[] { "-h", "dev.example.com" };

    // execute
    CommandLineArguments ys = Server.scan(args);

    // verify
    assertEquals("dev.example.com", ys.host);
  }

  @Test
  void testPort() {
    // setup
    String[] args = new String[] { "-p", "7070" };

    // execute
    CommandLineArguments ys = Server.scan(args);

    // verify
    assertEquals(7070, ys.port);
  }

  @Test
  void testBacklog() {
    // setup
    String[] args = new String[] { "-b", "5" };

    // execute
    CommandLineArguments ys = Server.scan(args);

    // verify
    assertEquals(5, ys.backlog);
  }

  @Test
  void testThreads() {
    // setup
    String[] args = new String[] { "-t", "5" };

    // execute
    CommandLineArguments ys = Server.scan(args);

    // verify
    assertEquals(5, ys.threads);
  }

  @Test
  void testAll() {
    // setup
    String[] args = new String[] {
      "-t",
      "5",
      "-b",
      "6",
      "-p",
      "7070",
      "-h",
      "dev.example.com",
    };

    // execute
    CommandLineArguments ys = Server.scan(args);

    // verify
    assertEquals(5, ys.threads);
    assertEquals(6, ys.backlog);
    assertEquals(7070, ys.port);
    assertEquals("dev.example.com", ys.host);
  }
}
