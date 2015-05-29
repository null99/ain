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

    printf("Starting server...\n");

    void *handle;

    void (*initserver)(char*, char*, int);
    void (*bindserver)();
    char* (*readserver)();
    void (*sendserver)();

    char *error;
    
    handle = dlopen ("./libsafeudp.so", RTLD_LAZY);
    
    if (!handle) {
        fputs (dlerror(), stderr);
        exit(1);
    }

    initserver = dlsym(handle, "initserver");
    bindserver = dlsym(handle, "bindserver");
    readserver = dlsym(handle, "readserver");
    sendserver = dlsym(handle, "sendserver");

    if ((error = dlerror()) != NULL)  {
        fputs(error, stderr);
        exit(1);
    }


    (*initserver)("tokenpassword", "127.0.0.1", 32001);
    (*bindserver)();

    for(;;){

        // Empfange Nachricht
        char *mesg = (*readserver)();

        // Checke if token was correct
        if(mesg == NULL){
            printf("Token was wrong. Aborting.\n");
            exit(-1);
        }

        printf("server: client says: %s\n", mesg);

        // Sende Nachricht zurück
        printf("server: sending back msg to client\n");
        (*sendserver)(mesg);

    }


    dlclose(handle);
    
    return (EXIT_SUCCESS);
}

