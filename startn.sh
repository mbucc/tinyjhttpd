#! /bin/sh -e
# Start one or more minimal servers.
# August 9, 2021

[ "x$1" = "x" ] && n=1 || n=$1
i=0
while [ "$i" -lt "$n" ]
do
	# Equivalants:
	#	-p   --module-path
	#	-m   --module
	java -p mlib:lib -m tinyjhttpd $i &
	i=$(expr $i + 1)
done
