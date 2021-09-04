package com.markbucciarelli.tinyjhttpd.types;

import java.util.Objects;

/**
 * A Header is an HTTP header; for example, "Content-Type: text/html; charset=UTF-8";
 */
public final class HTTPHeader {

  public static final String UTF8_CHARSET = "; charset= UTF-8";
  private final HTTPHeaderName name;
  private final String value;

  public HTTPHeader(HTTPHeaderName name, String value) {
    Objects.requireNonNull(name, "header name cannot be empty");
    Objects.requireNonNull(value, "header value cannot be empty");
    this.name = name;
    this.value = value;
  }

  public String toString() {
    return String.format("%s: %s", name.value, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HTTPHeader header = (HTTPHeader) o;
    return name == header.name && value.equals(header.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

  public HTTPHeaderName getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  //----------------------------------------------------------------------------
  //
  //        S T A T I C    C O N V E N I E N C E    M E T H O D S
  //
  //----------------------------------------------------------------------------

  /**
   * Prohibit client browsers from storing unencrypted values on disk.
   *
   * <p>
   *   Note that by default, browsers cache HTTPS resources to disk in an
   *   unencrypted form.  If you don't want this to happen, you should set
   *   the cache header to "no-store" (which is what this method does).
   * <p>
   *   See https://www.ise.io/casestudies/industry-wide-misunderstandings-of-https/index.html
   */
  public static HTTPHeader noCache() {
    return new HTTPHeader(HTTPHeaderName.CACHE_CONTROL, "no-store");
  }

  /**
   * Cache the resource for the given number of seconds.
   */
  public static HTTPHeader cache(long seconds) {
    if (seconds < 1) {
      throw new IllegalArgumentException(
        "cache duration must be a positive integer"
      );
    }
    return new HTTPHeader(
      HTTPHeaderName.CACHE_CONTROL,
      String.format("max-age=%d", seconds)
    );
  }

  public static HTTPHeader html() {
    return new HTTPHeader(
      HTTPHeaderName.CONTENT_TYPE,
      MediaType.HTML.getValue() + UTF8_CHARSET
    );
  }

  public static HTTPHeader plain() {
    return new HTTPHeader(
      HTTPHeaderName.CONTENT_TYPE,
      MediaType.TXT.getValue() + UTF8_CHARSET
    );
  }
}
