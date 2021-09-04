mlib/tinyjhttpd@1.jar: classes
	mkdir -p mlib
	jar --create \
		--file=mlib/tinyjhttpd@1.jar \
		--module-version 1 \
		--main-class com/markbucciarelli/tinyjhttpd/Server \
		-C ./mods/com.markbucciarelli.tinyjhttpd/ \
		.

# --module-source-path ./server/src
#		-m com.markbucciarelli.tinyjhttpd
classes:
	javac -d ./mods/com.markbucciarelli.tinyjhttpd $$(find server/src -name '*.java')

test: mlib/tinyjhttpd@1.jar mlib/helloworld@1.jar
	@./test/runtest.sh 1 "server starts successfully" "/"
	@./test/runtest.sh 2 "hello world handler works" "/hello"
	@rm -f test/*.out

testclasses: mlib/tinyjhttpd@1.jar
	javac \
		--module-path ./mlib \
		-d ./mods/com.example.helloworld \
		$$(find test/helloworld -name '*.java')

mlib/helloworld@1.jar: testclasses
	jar --create \
		--file=mlib/helloworld@1.jar \
		--module-version 1 \
		-C ./mods/com.example.helloworld/ \
	    .

test2: mlib/tinyjhttpd@1.jar mlib/helloworld@1.jar


fmt:
	npx prettier --write "**/*.java"

clean:
	rm -rf mlib
	rm -rf mods
	rm -f test/*.out

