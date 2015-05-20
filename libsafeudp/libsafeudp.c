/* 
 * File:   libsafeudp.c
 * Author: null
 *
 * Created on May 20, 2015, 12:48 PM
 */

#include "libsafeudp.h"

/*
 * 
 */
int sockfd,n;
struct sockaddr_in servaddr,cliaddr;
char mesg[SOCKBUF];

void initserver(int port){

	sockfd=socket(AF_INET,SOCK_DGRAM,0);
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=htonl(INADDR_ANY);
	servaddr.sin_port=htons(port);

}

void initclient (char* ip, int port){

   sockfd=socket(AF_INET,SOCK_DGRAM,0);
   bzero(&servaddr,sizeof(servaddr));
   servaddr.sin_family = AF_INET;
   servaddr.sin_addr.s_addr=inet_addr(ip);
   servaddr.sin_port=htons(port);
}

void bindserver(){

	bind(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr));

}

char* readserver(){

	socklen_t len;

	len = sizeof(cliaddr);
	n = recvfrom(sockfd,mesg,SOCKBUF,0,(struct sockaddr *)&cliaddr,&len);
	mesg[n] = 0;

	return((char*)&mesg);

}

char* readclient(){

	n=recvfrom(sockfd,mesg,SOCKBUF,0,NULL,NULL);
    mesg[n]=0;

    return((char*)&mesg);

}

void sendserver(char *sendthis){

	sendto(sockfd,sendthis,strlen((char*)sendthis),0,(struct sockaddr *)&cliaddr,sizeof(cliaddr));

}

void sendclient(char *sendthis){

	sendto(sockfd,sendthis,strlen((char*)sendthis),0,(struct sockaddr *)&servaddr,sizeof(servaddr));

}