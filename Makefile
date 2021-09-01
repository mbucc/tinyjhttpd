mlib/tinyjhttpd@1.jar: classes
	mkdir -p mlib
	jar --create \
		--file=mlib/tinyjhttpd@1.jar \
		--module-version 1 \
		--main-class com/markbucciarelli/tinyjhttpd/Server \
		-C ./mods/tinyjhttpd \
		.

classes: mods/tinyjhttpd/module-info.class \
		mods/tinyjhttpd/com/markbucciarelli/tinyjhttpd/Server.class

mods/tinyjhttpd/%.class: src/tinyjhttpd/%.java
	mkdir -p mods/tinyjhttpd
	javac -d mods --module-path lib --module-source-path ./src $?

test: mlib/tinyjhttpd@1.jar
	@./stop.sh
	@sleep 0.5
	@./start.sh 9876
	@sleep 0.5
	curl -D- -sS http://127.0.0.1:9876/ |grep -v '^Date: '> test.out
	diff -uw test.out test/expected.gold && echo PASS || echo FAIL

clean:
	rm -rf mlib
	rm -rf mods
	rm -f test.out

