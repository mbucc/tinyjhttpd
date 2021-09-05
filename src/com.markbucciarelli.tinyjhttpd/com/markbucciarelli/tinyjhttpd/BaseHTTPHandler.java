package com.markbucciarelli.tinyjhttpd;

import com.markbucciarelli.tinyjhttpd.types.HTTPHeader;
import com.markbucciarelli.tinyjhttpd.types.HTTPStatus;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * The BaseHTTPHandler provides error handling for HTTP request handling, returning
 * an internal server error if the handling code raises an exception.
 *
 * <p>
 *    The default behavior of com.sun.net.httpserver.HttpServer is to swallow
 *    any exceptions raised in an com.sun.net.httpserver.HttpHandler's handle
 *    method.  If you extend this class, your safeHandle() implementation can
 *    raise an exception and this class will turn it into a 500.
 * </p>
 *
 * <p>
 *    If the environmental variable DEBUG is defined, the stack trace will be
 *    included in the body of the 500 response.
 * </p>
 */
public abstract class BaseHTTPHandler implements HTTPHandlerWithContext {

  private static final String ERR_FMT =
    "<html>\n" +
    "<head><title>Internal Server Error</title></head>\n" +
    "<body>\n" +
    "<h1>Internal Server Error</h1>\n" +
    "<pre>%s</pre>\n" +
    "</body>\n" +
    "</html>\n";

  public abstract HandlerResponse safeHandle(HttpExchange x);

  @Override
  public void handle(HttpExchange x) {
    HandlerResponse response;
    try {
      response = safeHandle(x);
    } catch (Exception e) {
      String stack = "";
      if (System.getenv().get("DEBUG") != null) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        stack = sw.toString();
      }
      response =
        new HandlerResponse(
          HTTPStatus.INTERNAL_SERVER,
          String.format(ERR_FMT, stack).getBytes(StandardCharsets.UTF_8)
        );
    }

    try {
      Headers ys = x.getResponseHeaders();
      for (HTTPHeader h : response.getHeaders()) {
        ys.add(h.getName().value, h.getValue());
      }
      x.sendResponseHeaders(
        response.getStatus().getCode(),
        response.getBody().length
      );
      OutputStream os = x.getResponseBody();
      os.write(response.getBody());
      os.close();
    } catch (Exception e) {
      // If we get here, we failed to write the 500 response, so simply
      // output the stack trace to stderr and carry on.
      e.printStackTrace();
    }
  }
}
