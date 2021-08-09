build: Crypto.java Stocks.java
	javac Crypto.java Stocks.java Valley.java Ridge.java Trigigel.java

run-p1: Crypto.class
	java Crypto

run-p2: Stocks.class
	java Stocks

run-p3: Valley.class
	java Valley

run-p4: Ridge.class
	java Ridge

run-p5: Trigigel.class
	java Trigigel

clean: *.class
	rm *.class
