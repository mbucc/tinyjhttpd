package com.example.helloworld;

import com.markbucciarelli.tinyjhttpd.BaseHTTPHandler;
import com.markbucciarelli.tinyjhttpd.HandlerResponse;
import com.markbucciarelli.tinyjhttpd.types.HTTPHeader;
import com.markbucciarelli.tinyjhttpd.types.HTTPStatus;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HelloWorld extends BaseHTTPHandler {

  @Override
  public String getContext() {
    return "/hello";
  }

  @Override
  public HandlerResponse safeHandle(HttpExchange x) {
    readRequest(x);
    return new HandlerResponse(
      HTTPStatus.OK,
      "Hello World".getBytes(StandardCharsets.UTF_8),
      Arrays.asList(HTTPHeader.cacheDays(365), HTTPHeader.plaintext())
    );
  }

  private void readRequest(HttpExchange x) {
    try {
      x.getRequestBody().transferTo(OutputStream.nullOutputStream());
    } catch (IOException e) {
      throw new IllegalStateException("can't read request", e);
    }
  }
}
