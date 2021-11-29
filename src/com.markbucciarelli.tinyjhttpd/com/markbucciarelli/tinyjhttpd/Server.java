package com.markbucciarelli.tinyjhttpd;

import static java.lang.System.Logger.Level.INFO;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceLoader;
import java.util.concurrent.Executors;

/**
 * A Server is the main class.
 * It first
 * registers all HttpHandlerWithContext implementations
 * found on the class path,
 * and then starts up an HTTP server.
 *
 * <p>
 *   The server listens on port 8000 by default.
 *   You can change this
 *   by passing a different port as the first
 *   command-line argument.
 * </p>
 *
 * <p>
 *   The maximum length of the queue of pending
 *   client connections is 50.  You can change this by passing
 *   a positive integer as the second command-line argument.
 * </p>
 *
 * @see <a href="http://acme.com/software/thttpd/thttpd_man.html">thttpd</a>
 */
public class Server {

  private static final System.Logger LOGGER = System.getLogger("ConsoleLogger");

  static class CommandLineArguments {

    int port;
    String host;
    int threads;
    int backlog;

    private CommandLineArguments() {
      this.port = 8000;
      this.host = "localhost";
      this.threads = 10;
      this.backlog = 0;
    }

    String portAsString() {
      return String.valueOf(port);
    }
  }

  static CommandLineArguments scan(String[] args) {
    CommandLineArguments y = new CommandLineArguments();
    int n = args.length;
    for (int i = 0; i < n - 1; i += 2) {
      switch (args[i]) {
        case "-h" -> y.host = args[i + 1];
        case "-p" -> y.port = Integer.parseInt(args[i + 1]);
        case "-b" -> y.backlog = Integer.parseInt(args[i + 1]);
        case "-t" -> y.threads = Integer.parseInt(args[i + 1]);
        default -> {
          System.err.printf("unknown argument '" + args[i] + "'");
          usage();
          System.exit(1);
        }
      }
    }
    return y;
  }

  private static void usage() {
    System.err.printf(
      "valid arguments: -h <host> -p <port> -b <backlog> -t <threads>"
    );
  }

  public static void main(String[] args) throws IOException {
    CommandLineArguments xs = scan(args);
    LOGGER.log(INFO, "creating HttpServer on port {0}", xs.portAsString());
    HttpServer server = HttpServer.create(
      new InetSocketAddress(xs.host, xs.port),
      xs.backlog
    );
    ServiceLoader
      .load(HTTPHandlerWithContext.class)
      .stream()
      .map(ServiceLoader.Provider::get)
      .forEach(o -> {
        LOGGER.log(
          INFO,
          "registering route {0} to {1}",
          o.getContext(),
          o.getClass().getName()
        );
        server.createContext(o.getContext(), o);
      });
    server.setExecutor(Executors.newFixedThreadPool(xs.threads));
    server.start();
  }
}
