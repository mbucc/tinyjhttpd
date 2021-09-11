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
		--main-class com/markbucciarelli/consolelogger/Server \
		-C ./classes/com.markbucciarelli.consolelogger/ \
		.

testjars: compile
	mkdir -p mlib
	jar --create \
		--file=mlib/com.example.helloworld@1.jar \
		--module-version 1 \
		--main-class com/example/helloworld/Server \
		-C ./classes/com.example.helloworld/ \
		.

compile:
	javac -d ./classes --module-source-path src $$(find src -name '*.java')

test: jars testjars
	@./test/runtest.sh 1 "server starts successfully" "/"
	@./test/runtest.sh 2 "hello world handler works" "/hello"
	@./test/memtest.sh 3 "server resident set size (RSS) test" "/hello"

fmt:
	npx prettier --write "**/*.java"

clean:
	rm -rf mlib
	rm -rf classes
	rm -f test/*.out

