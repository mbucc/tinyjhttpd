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
 * <p>
 *   It also uses the default
 *   Executor, which controls thread use and scheduling.
 * </p>
 *
 * @see <a href="http://acme.com/software/thttpd/thttpd_man.html">thttpd</a>
 */
public class Server {

  private static final System.Logger LOGGER = System.getLogger("ConsoleLogger");

  public static void main(String[] args) throws IOException {
    int port = 8000;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    String hostname = null; //"localhost";
    int backlog = 0;
    if (args.length > 1) {
      backlog = Integer.parseInt(args[1]);
    }
    LOGGER.log(INFO, "creating HttpServer on port {0}", String.valueOf(port));
    InetSocketAddress address = hostname == null
      ? new InetSocketAddress(port)
      : new InetSocketAddress(hostname, port);
    HttpServer server = HttpServer.create(address, backlog);
    ServiceLoader
      .load(HTTPHandlerWithContext.class)
      .stream()
      .map(ServiceLoader.Provider::get)
      .forEach(o -> {
        // TODO: Why does getClass().getName() work?
        LOGGER.log(
          INFO,
          "registering route {0} to {1}",
          o.getContext(),
          o.getClass().getName()
        );
        server.createContext(o.getContext(), o);
      });
    server.setExecutor(Executors.newFixedThreadPool(10));
    server.start();
  }
}
