/* 
 * File:   libsafeudp.h
 * Author: null
 *
 * Created on May 20, 2015, 12:48 PM
 */

#ifndef LIBSAFEUDP_H
#define	LIBSAFEUDP_H

struct Mstruct{
	char a;
	int b;
};

extern void test();
extern void test_with_argument(char *arg0);
extern char* test_with_ptr_result();
extern struct Mstruct* test_with_complex_result();

#endif	/* LIBSAFEUDP_H */

