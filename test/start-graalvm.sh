#! /bin/sh -e

HEAP=$1

./tinyjhttpd \
  -Xmx${HEAP} \
  -Xms${HEAP} &
