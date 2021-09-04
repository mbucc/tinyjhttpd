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
  private final String body;
  private final List<HTTPHeader> headers;

	public HandlerResponse(int status, String body) {
		this.status = status;
		this.body = body;
		headers = new Headers();
		headers.add("Content-Type", "text/html; charset=UTF-8");
	}
}
