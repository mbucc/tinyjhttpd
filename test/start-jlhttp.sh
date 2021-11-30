#! /bin/sh -e

HEAP=$1
GC=$2

java \
  -Xmx${HEAP} \
  -Xms${HEAP} \
  -XX:+${GC} \
  -XX:NativeMemoryTracking=detail \
  -p mlib:lib \
  -m net.freeutils.httpserver $(pwd)/test 8000 &
