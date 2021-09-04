#! /bin/sh -e
#
# Note:
#     Java 11 and up
#       The Xmx value is 25% of the available memory with a maximum of 25 GB.
#       However, where there is 2 GB or less of physical memory, the value set
#       is 50% of available memory with a minimum value of 16 MB and a maximum
#       value of 512 MB.
#
#     Java 8
#       The Xmx value is half the available memory with a minimum of 16 MB
#       and a maximum of 512 MB.


n="$1"
name="$2"
route="$3"
limit_in_mb=45

# setup
./stop.sh > /dev/null
sleep 0.5
./start.sh 9876 > /dev/null
sleep 0.5

# execute
ab -n 100 -c 5 http://127.0.0.1:9876/ > /dev/null

# verify
rss=$(ps x -orss,args\
  |grep 'java.*tinyjhttpd' \
  |grep -v grep\
  |awk '{printf "%.0f", $1/1024}')
name="$name: $rss < $limit_in_mb?"
printf "%-65s" "$name"
[ $rss -lt $limit_in_mb ] && echo PASS || echo FAIL
./stop.sh > /dev/null
