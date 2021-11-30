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
max_rss=120

#GC=UseG1GC
GC=UseSerialGC
HEAP=3m
STACK=144k  # The minimum thread stack the JVM will start with is 144k.
LOGF=test/memtest_${GC}_${HEAP}.out

echo "$(date)" > $LOGF
echo "Output of $0" >> $LOGF
echo "" >> $LOGF
java -version >> $LOGF 2>&1
echo "$GC garbage collector" >> $LOGF
echo "$HEAP heap"  >> $LOGF
echo "$STACK thread stack"  >> $LOGF


# setup
./stop.sh > /dev/null
sleep 0.5
./test/memstart.sh $HEAP $GC $STACK > ./test/memtest-start.out 2>&1
sleep 0.5
N=$(jps | grep tinyjhttpd | awk '{print $1}')
printf "\n\nRecord memory usage before applying load\n-----------------------------\n" >> $LOGF
jcmd $N VM.native_memory baseline >> $LOGF

# execute
printf "\n\nab\n-----------------------------\n" >> $LOGF
abn=50000
abn=50000
echo "Submitting $abn requests to tinyjhttpd ... "
ab -s 5 -k -n $abn -c 25 http://127.0.0.1:8000/hello >> $LOGF 2>&1

# Report on memory usage.
rss=$(ps x -orss= -p$N\
  |awk '$1 ~ /[0-9]m/ {printf "%.0f", $1;next} {printf "%.0f", $1/1024}')

jcmd $N VM.native_memory summary.diff > test/native-memory.out
printf "\n\nMemory usage after applying load\n-----------------------------\n" >> $LOGF
cat test/native-memory.out >> $LOGF
printf "\n\nChange in memory usage\n-----------------------------\n" >> $LOGF
awk -f test/mem-stats.awk < ./test/native-memory.out test/native-memory.out > test/mem-stats.out
cat test/mem-stats.out >> $LOGF
native=$(grep Total test/mem-stats.out\
  |awk '{x=$1; sub("FB","",x); printf "%.0f", x/1024;}')
printf "\n\nMemory Usage Summary\n-----------------------------\n" >> $LOGF
printf "${rss}MB $(uname) $(uname -r) resident set size\n"  >> $LOGF
printf "${native}MB Total Java native memory\n"  >> $LOGF


# Output test success or failure.
name="$name: ${min_rss}m < ${rss}m < ${max_rss}m?"
printf "%-75s" "$name"
[ $rss -lt $max_rss ] && [ $rss -gt $min_rss ] && echo PASS || echo FAIL
./stop.sh > /dev/null
