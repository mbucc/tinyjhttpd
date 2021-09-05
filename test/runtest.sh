#! /bin/sh -e
# September 4, 2021
# Test that output from server is as expected.

n="$1"
name="$2"
route="$3"

fn=test$n

./stop.sh > /dev/null
sleep 0.5
./start.sh 9876 > test/$fn.out
sleep 0.5
curl -D- -sS http://127.0.0.1:9876${route} \
  |sed 's/^Date:.*/Date:/'>> test/$fn.out
printf "%-65s" "$name"
diff -uw test/$fn.gold test/$fn.out >&2 && echo PASS || echo FAIL
./stop.sh > /dev/null
rm -f test.out
