all:
	gcc -o libsafeudp.so libsafeudp.c -shared -fPIC -std=c99 -g -O0
	gcc server.c -o server ./libsafeudp.so -ldl -g -O0
	gcc client.c -o client ./libsafeudp.so -ldl -g -O0
	@echo Compiling fertig.
	@echo Starte Server mit: ./server
	@echo Starte Client mit: ./client

clean:
	rm *.so server client

production:
	gcc -o libsafeudp.so libsafeudp.c -shared -fPIC -std=c99
	gcc server.c -o server ./libsafeudp.so -ldl
	gcc client.c -o client ./libsafeudp.so -ldl
	@echo Compiling fertig.
	@echo Starte Server mit: ./server
	@echo Starte Client mit: ./client