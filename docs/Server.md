September 2, 2021

## The TCP socket backlog


The default server is 
[HttpServerImpl](https://github.com/openjdk/jdk/blob/master/src/jdk.httpserver/share/classes/sun/net/httpserver/DefaultHttpServerProvider.java#L35).

HttpServerImpl 
[calls bind](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/HttpServerImpl.java#L54)
on ServerImpl.

ServerImpl
[calls bind](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L134)
on ServerSocket.

If backlog is less than zero, ServerSocket
[sets it to 50](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/java.base/share/classes/java/net/ServerSocket.java#L381-L382).

ServerSocket passes backlog as argument to 
[the listen method](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/java.base/share/classes/java/net/ServerSocket.java#L389)
of the socket implementation that is found.

I can't see where a SocketFactory is set on the ServerSocket
(the setter 
[is deprecated]()
as of Java 17),
so I think ServerSocket
[uses the platform default](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/java.base/share/classes/java/net/ServerSocket.java#L303)
socket implementation.

SocketImpl 
[returns a](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/java.base/share/classes/java/net/SocketImpl.java#L50-L53)
NioSocketImpl.

If backlog is less than one, NioSocketImpl
[sets it to 50](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/java.base/share/classes/sun/nio/ch/NioSocketImpl.java#L655)
and calls Net.

Net
[calls the native listen](),
passing the file descriptor and the backlog.

The linux 
[listen](https://man7.org/linux/man-pages/man2/listen.2.html)
defines the backlog as "maximum length to which the
queue of pending connections for sockfd may grow."
