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
#
# For native memory description, see
# https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/tooldescr007.html
#

n="$1"
name="$2"
min_rss=20
max_rss=100

GC=UseSerialGC
HEAP=6m

LOGF=test/memtest-jlhttp_${GC}_${HEAP}.out
echo "Run on $(date)" > $LOGF

# start up
./stop.sh > /dev/null
sleep 0.5
./test/start-jlhttp.sh $HEAP $GC > ./test/memtest-jlhttp-start.out
sleep 0.5
N=$(jps | grep httpserver | awk '{print $1}')
printf "\n\nRecord memory usage before applying load\n-----------------------------\n" >> $LOGF
jcmd $N VM.native_memory baseline >> $LOGF

# load server
printf "\n\nab\n-----------------------------\n" >> $LOGF
abn=10000
echo "Submitting $abn requests to jlhttp (no keep alive) ... "
ab -k -n $abn -c 25 http://127.0.0.1:8000/hello.txt >> $LOGF 2>&1

# collect memory stats
rss=$(ps x -orss= -p$N\
  |awk '$1 ~ /[0-9]m/ {printf "%.0f", $1;next} {printf "%.0f", $1/1024}')
jcmd $N VM.native_memory summary.diff > test/native-memory.out
printf "\n\nMemory usage after applying load\n-----------------------------\n" >> $LOGF
cat test/native-memory.out >> $LOGF
printf "\n\nChange in memory usage\n-----------------------------\n" >> $LOGF
awk -f test/mem-stats.awk < ./test/native-memory.out test/native-memory.out > test/mem-stats.out
cat test/mem-stats.out >> $LOGF
native=$(grep Total test/mem-stats.out\
  |tr -d 'KB+'\
  |awk '{printf "%.0f", ($1+$2)/1024;}')

name="$name: ${min_rss}m < ${rss}m < ${max_rss}m? (VM.native=${native}m)"
printf "%-75s" "$name"
[ $rss -lt $max_rss ] && [ $rss -gt $min_rss ] && echo PASS || echo FAIL
./test/stop-jlhttp.sh > /dev/null
