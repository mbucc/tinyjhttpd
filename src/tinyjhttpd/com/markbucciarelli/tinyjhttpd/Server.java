package com.markbucciarelli.tinyjhttpd;


import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("java:S106")  // Suppress warning about stderr.
public class Server implements HttpHandler {

   public void handle(HttpExchange t) throws IOException {
       InputStream is = t.getRequestBody();
       read(is);

        String text = "One, two, {{three}}. Three sir!\n";
        Template tmpl = Mustache.compiler().compile(text);
        Map<String, String> data = new HashMap<>();
        data.put("three", "five");
        String response = tmpl.execute(data);

       t.sendResponseHeaders(200, response.length());
       OutputStream os = t.getResponseBody();
       os.write(response.getBytes());
       os.close();
   }

    void read(InputStream x) throws IOException {
        long n = x.transferTo(System.out);
        System.out.printf("read %d bytes%n", n);
    }

   public static void main(String[] args) throws IOException {
        int port = 8000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        System.out.printf("starting port %d%n", port);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new Server());
        server.setExecutor(null);
        server.start();
   }
}
