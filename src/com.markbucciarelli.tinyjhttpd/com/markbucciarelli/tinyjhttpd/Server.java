package com.markbucciarelli.tinyjhttpd;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceLoader;

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
 */
@SuppressWarnings("java:S106") // Suppress warning about using stdout.
public class Server {

  public static void main(String[] args) throws IOException {
    int port = 8000;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    int backlog = 0;
    if (args.length > 1) {
      backlog = Integer.parseInt(args[1]);
    }
    System.out.printf("starting server port %d%n", port);
    HttpServer server = HttpServer.create(new InetSocketAddress(port), backlog);
    ServiceLoader
      .load(HTTPHandlerWithContext.class)
      .stream()
      .map(ServiceLoader.Provider::get)
      .forEach(o -> {
        // TODO: Why does getClass().getName() work?
        // It thought that uses reflection and the com.example.helloworld
        // module-info does not open up that class, it just provides it.
        // TODO: Investigate Java 9's LoggerFinder.
        System.out.printf(
          "registering route %s to %s%n",
          o.getContext(),
          o.getClass().getName()
        );
        server.createContext(o.getContext(), o);
      });
    // TODO: Use ServiceLoader to allow a client to specify a different Executor.
    server.setExecutor(null);
    server.start();
  }
}
