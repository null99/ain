/* 
 * File:   loader.c
 * Author: null
 *
 * Created on May 20, 2015, 12:55 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

/*
 * 
 */
 int main(int argc, char** argv) {

 	printf("Starting client...\n");

    void *handle;

    void (*initclient)(char*, int);
    char* (*readclient)();
    void (*sendclient)();

    char *error;
    
    handle = dlopen ("./libsafeudp.so", RTLD_LAZY);
    
    if (!handle) {
        fputs (dlerror(), stderr);
        exit(1);
    }

    initclient = dlsym(handle, "initclient");
    readclient = dlsym(handle, "readclient");
    sendclient = dlsym(handle, "sendclient");

    if ((error = dlerror()) != NULL)  {
        fputs(error, stderr);
        exit(1);
    }


    (*initclient)("127.0.0.1", 32001);

    for(;;){

    	char sendmesg[1024];
    	printf("client: Gebe zu sendene Nachricht an: ");
    	scanf("%s", sendmesg);

    	// Sende Nachricht
        printf("client: sending msg to server: %s\n", sendmesg);
        (*sendclient)(&sendmesg);

        // Empfange Nachricht vom Server
        char *mesg = (*readclient)();
        printf("client: server says: %s\n", mesg);

        printf("-----------------------------\n");

    }


    dlclose(handle);
    
    return (EXIT_SUCCESS);
}