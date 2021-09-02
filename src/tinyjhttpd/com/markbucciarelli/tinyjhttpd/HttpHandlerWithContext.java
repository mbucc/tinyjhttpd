package com.markbucciarelli.tinyjhttpd;

import com.sun.net.httpserver.HttpHandler;

/**
 * An HttpHandlerWithContext returns the context root (for example,
 * "/my-account"), this handler listens to.
 */
public interface HttpHandlerWithContext extends HttpHandler {
	String getContext();
}
