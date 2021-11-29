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

# setup
./stop.sh > /dev/null
sleep 0.5
./test/start-freeutils.sh > ./test/jhttpd.out
sleep 0.5
N=$(jps | grep httpserver | awk '{print $1}')
jcmd $N VM.native_memory baseline > test/memtest.out

# execute
printf "\n\nab\n-----------------------------\n" >> test/memtest.out
abn=10000  # It looks like freeutils does not support keep alive, so 50,000 will run out of client sockets.
echo "Submitting $abn requests to server (no keep alive) ... "
  ab -k -n $abn -c 25 http://127.0.0.1:8000/hello.txt >> test/memtest.out 2>&1

# verify
rss=$(ps x -orss= -p$N\
  |awk '$1 ~ /[0-9]m/ {printf "%.0f", $1;next} {printf "%.0f", $1/1024}')
jcmd $N VM.native_memory summary.diff > test/native-memory.out
printf "\n\nVM.native_memory\n-----------------------------\n" >> test/memtest.out
cat test/native-memory.out >> test/memtest.out
printf "\n\nSummary (VM.native_memory)\n-----------------------------\n" >> test/memtest.out
awk -f test/mem-stats.awk < ./test/native-memory.out test/native-memory.out > test/mem-stats.out
cat test/mem-stats.out >> test/memtest.out
native=$(grep Total test/mem-stats.out\
  |awk '{x=$1; sub("FB","",x); printf "%.0f", x/1024;}')

name="$name: ${min_rss}m < ${rss}m < ${max_rss}m? (VM.native=${native}m)"
printf "%-75s" "$name"
[ $rss -lt $max_rss ] && [ $rss -gt $min_rss ] && echo PASS || echo FAIL
./test/stop-freeutils.sh > /dev/null
