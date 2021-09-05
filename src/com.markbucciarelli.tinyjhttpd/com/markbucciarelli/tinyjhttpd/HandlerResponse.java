package com.markbucciarelli.tinyjhttpd;

import com.markbucciarelli.tinyjhttpd.types.HTTPHeader;
import com.markbucciarelli.tinyjhttpd.types.HTTPStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A HandlerResponse contains the data required by BaseHTTPHandler
 * to construct an HTTP response.
 */

public final class HandlerResponse {

  private final HTTPStatus status;
  private final byte[] body;
  private final List<HTTPHeader> headers;

  /**
   * Defaults headers to HTML content type and cache to one day.
   */
  public HandlerResponse(HTTPStatus status, byte[] body) {
    this(
      status,
      body,
      Arrays.asList(HTTPHeader.cacheDays(1), HTTPHeader.html())
    );
  }

  public HandlerResponse(
    HTTPStatus status,
    byte[] body,
    List<HTTPHeader> headers
  ) {
    Objects.requireNonNull(status, "HTTP status code cannot be null");
    this.status = status;
    this.body = body == null ? new byte[] {} : body;
    this.headers =
      headers == null ? Collections.emptyList() : new ArrayList<>(headers);
  }

  public HTTPStatus getStatus() {
    return status;
  }

  public byte[] getBody() {
    return body;
  }

  public List<HTTPHeader> getHeaders() {
    return new ArrayList<>(headers);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HandlerResponse that = (HandlerResponse) o;
    return (
      status == that.status &&
      Arrays.equals(body, that.body) &&
      headers.equals(that.headers)
    );
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(status, headers);
    result = 31 * result + Arrays.hashCode(body);
    return result;
  }

  @Override
  public String toString() {
    return (
      "HandlerResponse{" +
      "status=" +
      status +
      ", body=" +
      Arrays.toString(body) +
      ", headers=" +
      headers +
      '}'
    );
  }
}
