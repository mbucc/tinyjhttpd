import com.markbucciarelli.tinyjhttpd.HTTPHandlerWithContext;

module com.markbucciarelli.tinyjhttpd {
  exports com.markbucciarelli.tinyjhttpd ;
  exports com.markbucciarelli.tinyjhttpd.types ;
  uses HTTPHandlerWithContext;
  requires jdk.httpserver;
}
