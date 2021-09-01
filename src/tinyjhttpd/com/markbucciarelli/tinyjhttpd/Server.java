package com.markbucciarelli.tinyjhttpd;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceLoader;


@SuppressWarnings("java:S106")  // Suppress warning about stderr.
public class Server  {

  public static void main(String[] args) throws IOException {
    int port = 8000;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    System.out.printf("starting port %d%n", port);
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    ServiceLoader.load(HttpHandlerWithContext.class)
        .stream()
        .map(ServiceLoader.Provider::get)
        .forEach(o -> server.createContext(o.getContext(), o));
    server.setExecutor(null);
    server.start();
  }
}
