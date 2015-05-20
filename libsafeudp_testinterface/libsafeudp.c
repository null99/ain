/* 
 * File:   libsafeudp.c
 * Author: null
 *
 * Created on May 20, 2015, 12:48 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include "libsafeudp.h"

/*
 * 
 */


void test(){
	printf("1. Dies ist ein einfacher Test.\n");
}

void test_with_argument(char *arg0){
    	printf("2. Dies ist ein Test mit Argument: (%s).\n", arg0);
}

char* test_with_ptr_result(){
	char *ret = (char*)malloc(32);
	ret = "mein einfacher Rückgabewert";
	printf("3. Dies ist ein Test mit einem Pointer Rückgabewert.\n");
	return &ret[0];
}

struct Mstruct* test_with_complex_result(){
        struct Mstruct *p;
	p = (struct Mstruct*)malloc(sizeof(p));
 	p->a = 'a'; p->b = (int)8;
        printf("4. Dies ist ein Test mit einem komplexen Rückgabewert.\n");
        return p;
}


