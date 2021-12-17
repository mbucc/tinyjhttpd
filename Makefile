jars: compile
	mkdir -p mlib
	jar --create \
		--file=mlib/com.markbucciarelli.tinyjhttpd@1.jar \
		--module-version 1 \
		--main-class com/markbucciarelli/tinyjhttpd/Server \
		-C ./classes/com.markbucciarelli.tinyjhttpd/ \
		.
	jar --create \
		--file=mlib/com.markbucciarelli.consolelogger@1.jar \
		--module-version 1 \
		-C ./classes/com.markbucciarelli.consolelogger/ \
		.

tinyjhttpd: compile
	native-image -cp "mlib/*" --install-exit-handlers com.markbucciarelli.tinyjhttpd.Server tinyjhttpd

testjars: compile
	mkdir -p mlib
	jar --create \
		--file=mlib/com.example.helloworld@1.jar \
		--module-version 1 \
		--main-class com/example/helloworld/Server \
		-C ./classes/com.example.helloworld/ \
		.
	jar --create \
		--file=mlib/net.freeutils.httpserver@1.jar \
		--module-version 1 \
		--main-class net/freeutils/httpserver/HTTPServer \
		-C ./classes/net.freeutils.httpserver/ \
		.

compile:
	javac -d ./classes --module-source-path src $$(find src -name '*.java')

testclasses: compile
	javac  \
		-cp "testlib/*:classes/com.markbucciarelli.tinyjhttpd" \
		-d ./classes/ \
		$$(find test/com.markbucciarelli.tinyjhttpd -name '*.java')

test: jars testjars testclasses
	@./test/runtest.sh 1 "server starts successfully" "/"
	@./test/runtest.sh 2 "hello world handler works" "/hello"
	@./test/memtest.sh 3 "server resident set size (RSS) test"
	@./test/memtest-jlhttp.sh 4 "jlhttp RSS test"

fmt:
	npx prettier --write "**/*.java"

clean:
	rm -rf mlib
	rm -rf classes
	rm -f test/*.out

