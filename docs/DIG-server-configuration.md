September 12, 2021

## jdk.httpserver Configuration

The
[ServerConfig class](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerConfig.java#L38)
defines the configuration variables.

You override the default values by setting an 
environmental variable.

| envvar | default value | description |
| ------ | ------------- | -----------------------|
| sun.net.httpserver.idleInterval | 30 minutes | How long a connection stays idle (I think.) |
| sun.net.httpserver.clockTick | 10 seconds | How often the server [closes idle connections](https://github.com/openjdk/jdk/blob/51a5731d6dc4b6f6feac920a4b8b49c15fd6b34f/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L858-L879) whose time attribute is less than current time. |
| sun.net.httpserver.maxIdleConnections | 200 | Limit the number of open HTTP connections.  [Enforced when](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L307-L309) a connection receives a WriteFinishedEvent.  |
| sun.net.httpserver.drainAmount | 64 * 1024 | The maximum number of bytes to drain from an inputstream. |
| sun.net.httpserver.maxReqHeaders | 200 | The maximum number of request headers allowable. | 
| sun.net.httpserver.maxReqTime | -1 (forever) | The maximum time a request is allowed to take. |
| sun.net.httpserver.maxRspTime |  -1 (forever) | The maximum time a response is allowed to take. |
| sun.net.httpserver.timerMillis | 1000 ms | How often the server [closes a request or response](https://github.com/openjdk/jdk/blob/51a5731d6dc4b6f6feac920a4b8b49c15fd6b34f/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L858-L879) that has run too long. |
| sun.net.httpserver.debug | false | [Prints](https://github.com/openjdk/jdk/blob/51a5731d6dc4b6f6feac920a4b8b49c15fd6b34f/src/jdk.httpserver/share/classes/sun/net/httpserver/HttpConnection.java#L119-L159) some HTTP Connection stack traces that are otherwise ignored when closing a connection. |
| sun.net.httpserver.nodelay | false | By default, TCP uses Nagle's algorithm to reduce the number of packets sent over the network.  It does this by buffereing the outgoing data until it has a full packet's worth ("full" usually means the maximum segment size or MSS).  This can have a detrimental effect on latency, for example a gamer's mouse movements will all be small packets.  [Setting TCP_NODELAY](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L386) to true disables this algorithm. |
