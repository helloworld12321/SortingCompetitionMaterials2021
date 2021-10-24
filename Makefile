.PHONY: all clean

all:
	mkdir -p bin \
	  && javac src/Group11.java -sourcepath src -d bin

clean:
	rm -rf bin/*
