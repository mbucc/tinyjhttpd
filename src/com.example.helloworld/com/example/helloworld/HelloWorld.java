package com.example.helloworld;

import com.markbucciarelli.tinyjhttpd.BaseHTTPHandler;
import com.markbucciarelli.tinyjhttpd.HandlerResponse;
import com.markbucciarelli.tinyjhttpd.types.HTTPHeader;
import com.markbucciarelli.tinyjhttpd.types.HTTPStatus;
import com.sun.net.httpserver.HttpExchange;
import java.util.Arrays;

public class HelloWorld extends BaseHTTPHandler {

  @Override
  public String getContext() {
    return "/hello";
  }

  @Override
  public HandlerResponse safeHandle(HttpExchange x) {
    return new HandlerResponse(
      HTTPStatus.OK,
      "Hello World",
      Arrays.asList(HTTPHeader.cacheDays(365), HTTPHeader.plaintext())
    );
  }
}
