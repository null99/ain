all:
	javac -classpath jna.jar jnatest.java
	@echo Compiling fertig.
	@echo Starte Server...
	@echo Server gestartet und warte auf Client
	LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH java -classpath jna.jar:. jnatest
clean:
	rm CLibrary.class jnatest.class
