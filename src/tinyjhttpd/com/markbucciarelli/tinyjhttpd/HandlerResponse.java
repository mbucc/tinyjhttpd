package com.markbucciarelli.tinyjhttpd;


import com.sun.net.httpserver.Headers;


/**
 * A HandlerResponse contains the data required by AbstractHttpHandlerWithContext
 * to construct an HTTP response.
 *
 * <p>
 *    This class is not thread-safe, so a handler should create a new instance for
 *    each incoming request.
 * </p>
 *
 * <p>
 *    This class defaults the Content-Type to text/html; charset=UTF-8. To
 *    set a different content type, use the "set" method on the Headers
 *    instance in this class.
 * </p>
 */
@SuppressWarnings("java:S1104")  // Lazy.  Read last paragraph above.
public final class HandlerResponse {
	public int status;
	public String body;
	public Headers headers = new Headers();

	public HandlerResponse(int status, String body) {
		this.status = status;
		this.body = body;
		headers = new Headers();
		headers.add("Content-Type", "text/html; charset=UTF-8");
	}
}
