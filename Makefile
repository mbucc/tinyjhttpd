NAME=tinyjhttpd
SRC=./${NAME}
DST=./mods/${NAME}
LIB=./mlib


${LIB}/${NAME}@1.jar: all
	mkdir -p ${LIB}
	jar --create --file=${LIB}/${NAME}@1.jar --module-version 1 --main-class com/markbucciarelli/${NAME}/Server -C ${DST} .


.PHONY: all
all: ${DST}/module-info.class ${DST}/com/markbucciarelli/${NAME}/Server.class


${DST}/%.class: ${SRC}/%.java
	javac -d mods --module-path lib --module-source-path . $?


.PHONY:class
clean:
	rm -rf ${DST}
	rm -rf ${LIB}

