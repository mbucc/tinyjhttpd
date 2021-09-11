#! /bin/sh -e
#
# September 7, 2021

HEAP=$1
[ "x$2" = "x" ] && GC=UseG1GC || GC=UseSerialGC

echo "gc = $GC"

java \
  -Xmx${HEAP}m \
  -Xms${HEAP}m \
  -XX:+${GC} \
  -XX:NativeMemoryTracking=detail \
  -p mlib:lib \
  -m com.markbucciarelli.tinyjhttpd &
