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
#include <arpa/inet.h>

// Turn on Debugmessages
#undef DEBUG

// Formula: MESG_TOKENLEN + MESG_MESSAGELEN = SOCKBUF
#define SOCKBUF 1500 // = MTU
#define MESG_TOKENLEN 16
#define MESG_MESSAGELEN 1484

/* Internal use */
struct MESSAGE{
	char token[MESG_TOKENLEN];
	char mesg[MESG_MESSAGELEN];
};

struct MESSAGE* packmessage(char *inputstr);

/* public Interfaces */
extern void initserver(char *_token, char *ip, int port);
extern void bindserver();
extern char* readserver();
extern void sendserver(char *mesg);

extern void initclient(char *_token, char *ip, int port);
extern char* readclient();
extern void sendclient(char *mesg);


#endif	/* LIBSAFEUDP_H */

