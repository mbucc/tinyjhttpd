#! /bin/sh -e
#
# September 7, 2021

HEAP=$1
GC=$2
STACK=144k  # The Java virtual machine requires a thread stack of at least 144k.

java \
  -Xmx${HEAP} \
  -Xms${HEAP} \
  -Xss${STACK} \
  -XX:+${GC} \
  -XX:NativeMemoryTracking=detail \
  -p mlib:lib \
  -m com.markbucciarelli.tinyjhttpd &
