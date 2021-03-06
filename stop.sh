#! /bin/sh -e

ps x -o pid,args \
  | grep 'java .* -m com.markbucciarelli.tinyjhttpd' \
  | grep -v grep \
  | awk '{print $1}' \
  | while read pid; do echo killing $pid; kill $pid; done
