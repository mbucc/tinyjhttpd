#! /bin/sh -e

java \
  -Xmx6m \
  -Xms6m \
  -XX:+UseSerialGC \
  -XX:NativeMemoryTracking=detail \
  -p mlib:lib \
  -m net.freeutils.httpserver $(pwd)/test 8000 &
