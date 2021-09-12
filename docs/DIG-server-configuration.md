September 12, 2021

## jdk.httpserver Configuration

The
[ServerConfig class](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerConfig.java#L38)
defines the configuration variables.

You override the default values by setting an 
environmental variable.

| envvar | default value | description |
| ------ | ------------- | -----------------------|
| sun.net.httpserver.idleInterval | 30 minutes |  |
| sun.net.httpserver.clockTick | 10 seconds |  |
| sun.net.httpserver.maxIdleConnections | 200 | |
| sun.net.httpserver.drainAmount | 64 * 1024 | |
| sun.net.httpserver.maxReqHeaders | 200 | | 
| sun.net.httpserver.maxReqTime | -1 (forever) | |
| sun.net.httpserver.maxRspTime |  -1 (forever) | |
| sun.net.httpserver.timerMillis | 1000 ms | |
| sun.net.httpserver.debug | false | |
| sun.net.httpserver.nodelay | false | [Set TCP_NODELAY](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L386) on the channel socket. |