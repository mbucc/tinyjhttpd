jars: classes
	mkdir -p mlib
	jar --create \
		--file=mlib/com.markbucciarelli.tinyjhttpd@1.jar \
		--module-version 1 \
		--main-class com/markbucciarelli/tinyjhttpd/Server \
		-C ./classes/com.markbucciarelli.tinyjhttpd/ \
		.

testjars: classes
	mkdir -p mlib
	jar --create \
		--file=mlib/com.example.helloworld@1.jar \
		--module-version 1 \
		--main-class com/example/helloworld/Server \
		-C ./classes/com.example.helloworld/ \
		.

classes:
	javac -d ./classes --module-source-path src $$(find src -name '*.java')

test: jars testjars
	@./test/runtest.sh 1 "server starts successfully" "/"
	@./test/runtest.sh 2 "hello world handler works" "/hello"
	@./test/runload.sh 3 "server resident set size (RSS) test" "/hello"
	@rm -f test/*.out

fmt:
	npx prettier --write "**/*.java"

clean:
	rm -rf mlib
	rm -rf classes
	rm -f test/*.out

