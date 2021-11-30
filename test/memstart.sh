#! /bin/sh -e
#
# September 7, 2021

HEAP=$1
GC=$2

java \
  -Xmx${HEAP} \
  -Xms${HEAP} \
  -XX:+${GC} \
  -XX:NativeMemoryTracking=detail \
  -p mlib:lib \
  -m com.markbucciarelli.tinyjhttpd &
