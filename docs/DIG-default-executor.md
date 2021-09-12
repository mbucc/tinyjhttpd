September 12, 2021

## The Default Executor


ServerImpl start()
[creates a new](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L143)
DefaultExecutor.

The
[DefaultExecutor](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L157-L161)
runs every task immediately in the main server's thread:

```
    private static class DefaultExecutor implements Executor {
        public void execute (Runnable task) {
            task.run();
        }
    }
```

In short, the default server uses a single-thread to respond to all requests.


## How are the tasks given to the executor?

When a ServerImpl is instantiated, it
[opens a channel](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L98)
to a stream-based socket, 
[binds the channel's socket](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L99-L103)
to the hostname and port (setting the backlog parameter),
[configures the channel to be non-blocking](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L105)
(so the code does stop and wait for an incoming connection when you 
register the selector),
and [registers a selector of accept events.](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L106)

At this point we have a single thread that will service all incoming connections.

When the server starts, it
[starts a dispatcherThread](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L145-L147)
called "HTTP-Dispatcher",
which calls the run() method on the
[calls the run() method](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L348)
Dispatcher inner class of ServerImpl.

At a high-level, this method
1. loops while `!finished`, 
2. [blocks on the socket for one second listening](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L370) for new accept() events,
3. if one found, [creates and executes the exchange](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L446-L447) with the executor.

There is lots of channel and 
[HttpConnection](https://github.com/openjdk/jdk/blob/0c1b16b75a2361431cbf9f4112dcd6049e981a78/src/jdk.httpserver/share/classes/sun/net/httpserver/ServerImpl.java#L393)
state-handling logic in dispatcher run().

The following blog post was a good resource on how the socket channel/selector pattern
described above: 
https://www.waitingforcode.com/java-i-o/handling-multiple-io-one-thread-nio-selector/read