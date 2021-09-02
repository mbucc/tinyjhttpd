package com.markbucciarelli.tinyjhttpd;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceLoader;


/**
 * A Server contains a main entry point registers all HttpHandlerWithContext
 * implementations on the class path and listens on the HTTP protocol.
 *
 * <p>
 *   The server takes a single optional command-line argument that defines the
 *   port to listen on.  The default port is 8000.
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
@SuppressWarnings("java:S106")  // Suppress warning about using stdout.
public class Server  {

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
    ServiceLoader.load(HttpHandlerWithContext.class)
        .stream()
        .map(ServiceLoader.Provider::get)
        .forEach(o -> {
          // Note: I wanted to print the classname here, but getSimpleName() is
          // implemented with reflection.  Since a handler may be in a module
          // that does not allow reflection, we can't do that.
          // TODO: Investigate Java 9's LoggerFinder.
          System.out.printf("registering route for %s%n", o.getContext());
          server.createContext(o.getContext(), o);
        });
    // TODO: Use ServiceLoader and allow a client to specify the Executor.
    server.setExecutor(null);
    server.start();
  }
}
