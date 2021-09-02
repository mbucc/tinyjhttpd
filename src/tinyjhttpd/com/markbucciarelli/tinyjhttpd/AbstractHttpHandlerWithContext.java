package com.markbucciarelli.tinyjhttpd;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * The AbstractHttpHandlerWithContext catches exceptions and returns a 500.
 *
 * <p>
 *    The default behavior of com.sun.net.httpserver.HttpServer is to swallow
 *    any exceptions raised in an com.sun.net.httpserver.HttpHandler's handle
 *    method.  This base class implements a handle() that wraps the abstract
 *    safeHandle(), and returns a 500 if safeHandle raises any exception.
 * </p>
 *
 * <p>
 *    If the environmental variable DEBUG is defined, the stack trace will be
 *    included in the body of the 500 response.
 * </p>
 */
public abstract class AbstractHttpHandlerWithContext implements HttpHandlerWithContext {

	private static final String ERR_FMT = "<html>\n" +
		"<head><title>Internal Server Error</title></head>\n" +
		"<body>\n" +
		"<h1>Internal Server Error</h1>\n" +
		"<pre>%s</pre>\n" +
		"</body>\n" +
		"</html>\n";

	public abstract String getContext();
	public abstract HandlerResponse safeHandle(HttpExchange x) throws IOException;

	@Override
	public void handle(HttpExchange x)  {

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
			response = new HandlerResponse(500, String.format(ERR_FMT, stack));
		}

		try {
			Headers ys = x.getResponseHeaders();
			for (Map.Entry<String, List<String>> entry: response.headers.entrySet()) {
				for (String val: entry.getValue()) {
					ys.add(entry.getKey(), val);
				}
			}
			x.sendResponseHeaders(
				response.status,
				response.body.getBytes(StandardCharsets.UTF_8).length);
			OutputStream os = x.getResponseBody();
			os.write(response.body.getBytes(StandardCharsets.UTF_8));
			os.close();
		} catch (Exception e) {
			// If we get here, we failed to write the 500 response, so simply
			// output the stack trace to stderr and carry on.
			e.printStackTrace();
		}
	}

}
