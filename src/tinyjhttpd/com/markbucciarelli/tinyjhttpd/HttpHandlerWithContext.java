package com.markbucciarelli.tinyjhttpd;

import com.sun.net.httpserver.HttpHandler;

/**
 * An HttpHandlerWithContext returns the context root (for example, "/myapp")
 * to register this handler on.
 */
public interface HttpHandlerWithContext extends HttpHandler {
	String getContext();
}
