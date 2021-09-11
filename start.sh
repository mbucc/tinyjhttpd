#! /bin/sh -e
# Start one or more minimal servers.
# August 9, 2021

[ "x$1" = "x" ] && port=8080 || port=$1

# Equivalants:
#	  -p   --module-path
#	  -m   --module

java \
  -Xmx6m \
  -Xms6m \
  -XX:+UseSerialGC \
  -p mlib:lib \
  -m com.markbucciarelli.tinyjhttpd $port &
