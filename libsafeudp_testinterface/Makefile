all:
	gcc -o libsafeudp.so libsafeudp.c -shared -fPIC
	gcc loader.c -o loader -ldl
	@echo Compiling fertig. Starte...
	./loader

