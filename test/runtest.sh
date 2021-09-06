#! /bin/sh -e
# September 4, 2021
# Test that output from server is as expected.

n="$1"
name="$2"
route="$3"

fn=test$n
logfile=test/$fn.out

# rmdates removes datetime strings that change with every test run.
rmdates() {
  sed -e 's/^.\{23\} 2[0-9]\{3\}/<Date.toString()>/' -e 's/^Date:.*/Date:/'
}

# rmports removes local port strings that change with every test run.
rmports() {
  sed 's/\(remote=[^:]*\):[1-9][0-9][0-9]*/\1:<localport>/'
}


./stop.sh > /dev/null
sleep 0.5
[ "x$n" = "x1" ] && export DEBUG=1 || unset DEBUG
./start.sh 9876 >test/server.out 2>&1
sleep 0.5

curl -D- -sS http://127.0.0.1:9876${route} >test/curl.out 2>&1

./stop.sh > /dev/null
sleep 0.5

cat test/server.out test/curl.out | rmdates | rmports > $logfile

printf "%-65s" "$name"
diff -uw test/$fn.gold $logfile >&2 && echo PASS || echo FAIL

