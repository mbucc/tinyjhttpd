#! /bin/sh -e

[ "x$1" = "x" ] && n=1 || n=$1
i=0
while [ "$i" -lt "$n" ]
do
	port=$(expr $i + 8000)
	echo "starting ab in background, piping log to myapp$i.log"
	ab -n 1000 -c 5 -p post-body.txt http://jdevgarden:$port/myapp$i > myapp$i.log 2>&1  &
	i=$(expr $i + 1)
done
