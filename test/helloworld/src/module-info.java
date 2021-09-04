module com.example.helloworld {
  requires com.markbucciarelli.tinyjhttpd;
  requires jdk.httpserver;
  provides com.markbucciarelli.tinyjhttpd.HTTPHandlerWithContext
    with com.example.helloworld.HelloWorld;
}
