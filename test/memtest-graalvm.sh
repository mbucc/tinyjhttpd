#! /bin/sh -e
# December 17, 2021

n="$1"
name="$2"
min_rss=20
max_rss=120

# Community Graalvm only provides serial GC.
GC=UseSerialGC
HEAP=50m
LOGF=test/memtest-graalvm_${GC}_${HEAP}.out

echo "$(date)" > $LOGF
echo "Output of $0" >> $LOGF
echo "" >> $LOGF
java -version >> $LOGF 2>&1
echo "$GC garbage collector" >> $LOGF
echo "$HEAP heap"  >> $LOGF


# setup
./test/stop-graalvm.sh > /dev/null
sleep 0.5
./test/start-graalvm.sh $HEAP $STACK > ./test/start-graalvm.out 2>&1
sleep 0.5
N=$(ps | grep tinyjhttpd | grep -v grep | awk '{print $1}')

# execute
printf "\n\nab\n-----------------------------\n" >> $LOGF
abn=20000
echo "Submitting $abn requests to tinyjhttpd ... "
ab -s 5 -k -n $abn -c 25 http://127.0.0.1:8000/hello >> $LOGF 2>&1

# Report on memory usage.
rss=$(ps x -orss= -p$N\
  |awk '$1 ~ /[0-9]m/ {printf "%.0f", $1;next} {printf "%.0f", $1/1024}')

printf "\n\nMemory Usage Summary\n-----------------------------\n" >> $LOGF
printf "${rss}MB $(uname) $(uname -r) resident set size\n"  >> $LOGF

# Output test success or failure.
name="$name: ${min_rss}m < ${rss}m < ${max_rss}m?"
printf "%-75s" "$name"
[ $rss -lt $max_rss ] && [ $rss -gt $min_rss ] && echo PASS || echo FAIL
./stop.sh > /dev/null
