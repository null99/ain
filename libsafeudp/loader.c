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
    void (*testfunc1)();
    void (*testfunc2)(char*);
    char* (*testfunc3)();
#include "libsafeudp.h"
    struct Mstruct* (*testfunc4)();

    char *error;
    
    handle = dlopen ("./libsafeudp.so", RTLD_LAZY);
    
    if (!handle) {
        fputs (dlerror(), stderr);
        exit(1);
    }

    testfunc1 = dlsym(handle, "test");
    testfunc2 = dlsym(handle, "test_with_argument");
    testfunc3 = dlsym(handle, "test_with_ptr_result");
    testfunc4 = dlsym(handle, "test_with_complex_result");

    if ((error = dlerror()) != NULL)  {
        fputs(error, stderr);
        exit(1);
    }


    (*testfunc1)();
    (*testfunc2)("Inhalt_zur_Uebergabe");
    char *result1 = (*testfunc3)();
    printf("Ret von test_with_ptr_result(): %s\n", result1);
    struct Mstruct* result2 = (*testfunc4)();
    printf("Ret von test_with_complex_result(): a=%c b=%d\n", result2->a, result2->b);


    dlclose(handle);
    
    return (EXIT_SUCCESS);
}

