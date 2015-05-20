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

    void *handle;
    void (*testfunc)();
    char *error;
    
    handle = dlopen ("./libsafeudp.so", RTLD_LAZY);
    
    if (!handle) {
        fputs (dlerror(), stderr);
        exit(1);
    }

    testfunc = dlsym(handle, "test");
    if ((error = dlerror()) != NULL)  {
        fputs(error, stderr);
        exit(1);
    }

    (*testfunc)();
    dlclose(handle);
    
    return (EXIT_SUCCESS);
}

