/* 
 * File:   libsafeudp.h
 * Author: null
 *
 * Created on May 20, 2015, 12:48 PM
 */

#ifndef LIBSAFEUDP_H
#define	LIBSAFEUDP_H

#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#define SOCKBUF 1024

extern void initserver(int port);
extern void bindserver();
extern char* readserver();
extern void sendserver(char* mesg);

extern void initclient(char* ip, int port);
extern char* readclient();
extern void sendclient(char* mesg);

#endif	/* LIBSAFEUDP_H */

