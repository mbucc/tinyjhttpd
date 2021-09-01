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
 *    The default HttpHandler just swallows the exception.  This implementation
 *    prints the stack trace to the resulting web page if the OS environmental
 *    variable DEBUG is defined.
 * </p>
 *
 * <p>
 *    By default, this set's the response content type to text/html.
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
			e.printStackTrace();
		}
	}

}
