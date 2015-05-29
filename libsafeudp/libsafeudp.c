/* 
 * File:   libsafeudp.c
 * Author: null
 *
 * Created on May 20, 2015, 12:48 PM
 */

#include "libsafeudp.h"

/*
 * Globals
 */
int sockfd,n;
struct sockaddr_in servaddr,cliaddr;
char mesg[SOCKBUF];
struct MESSAGE *oldmesg = NULL;
char token[MESG_TOKENLEN]; 

/*
 * Exported function to initialize the server.
 * 
 * params: Token; Bind to this IP; The portnumber to listen on.
 */
void initserver(char *_token, char *ip, int port){

	strncpy(token, _token, MESG_TOKENLEN);

#ifdef DEBUG
	printf("server: Server initialized with params: token=%s, ip=%s, port=%d\n", token, ip, port);
#endif

	sockfd=socket(AF_INET,SOCK_DGRAM,0);
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(ip); // htonl(INADDR_ANY);
	servaddr.sin_port=htons(port);

}

/*
 * Exported function to initialize the client.
 *
 * params: Token; The IP of the server; The portnumber of the server to connect to.
 */
void initclient (char *_token, char* ip, int port){

	strncpy(token, _token, MESG_TOKENLEN);

#ifdef DEBUG
	printf("client: Client initialized with params: token=%s, ip=%s, port=%d\n", token, ip, port);
#endif

	sockfd=socket(AF_INET,SOCK_DGRAM,0);
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(ip);
	servaddr.sin_port=htons(port);

}

/*
 * Exported function to start the server listening.
 */
void bindserver(){

	bind(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr));

}

/*
 * Exported function to receive a messages from the client to the server.
 *
 * return: Pointer to the beginning of the message. If token was incorrect, NULL will be returned.
 */
char* readserver(){

	socklen_t len;

	len = sizeof(cliaddr);
	struct MESSAGE _mesg;
	char _temp_mesg[SOCKBUF];
	n = recvfrom(sockfd,_temp_mesg,SOCKBUF,0,(struct sockaddr *)&cliaddr,&len);

	memcpy(&_mesg, &_temp_mesg, sizeof(_mesg));
	_mesg.mesg[MESG_MESSAGELEN]='\0';

	// Check for a valid token
	if(memcmp(_mesg.token, token, MESG_TOKENLEN) != 0)
		return NULL;

	strncpy(mesg, _mesg.mesg, MESG_MESSAGELEN);

#ifdef DEBUG
	printf("server: D: %s\n", _mesg.mesg);
#endif

	mesg[n] = 0;

	return((char*)&mesg);

}

/*
 * Exported function to receive a messages from the server to the client.
 *
 * return: Pointer to the beginning of the message. If token was incorrect, NULL will be returned.
 */
char* readclient(){

	struct MESSAGE _mesg;
	char _temp_mesg[SOCKBUF];

	n=recvfrom(sockfd,_temp_mesg,SOCKBUF,0,NULL,NULL);

	memcpy(&_mesg, &_temp_mesg, SOCKBUF);
	_mesg.mesg[MESG_MESSAGELEN]='\0';
	strncpy(mesg, _mesg.mesg, MESG_MESSAGELEN);

	// Check for a valid token
	if(memcmp(_mesg.token, token, MESG_TOKENLEN) != 0)
		return NULL;

#ifdef DEBUG
	printf("client: D: %s\n", _mesg.mesg);
#endif

	mesg[n]=0;

	return((char*)&mesg);

}

/*
 * Exported function to send a messages from the server to the client.
 *
 * params: Pointer to the beginning of the message.
 */
void sendserver(char *sendthis){

	struct MESSAGE *fragments = packmessage(sendthis);
	char line[SOCKBUF];

	for(int i=0; (fragments+i)->mesg[0]!=0; i++){
		memcpy(&line, (fragments+i), SOCKBUF);
		line[SOCKBUF]='\0';
		sendto(sockfd,line,SOCKBUF,0,(struct sockaddr *)&cliaddr,sizeof(cliaddr));
	}

}

/*
 * Exported function to send a messages from the client to the server.
 *
 * params: Pointer to the beginning of the message.
 */
void sendclient(char *sendthis){

#ifdef DEBUG
	printf("client: about to send %i in length\n", (int)strlen(sendthis));
#endif

	struct MESSAGE *fragments = packmessage(sendthis);
	char line[SOCKBUF];

#ifdef DEBUG
	printf("client: token[0]: %c, token[1]: %c ...\n", (fragments+0)->token[0], (fragments+0)->token[1]);
#endif

	for(int i=0; (fragments+i)->mesg[0]!=0; i++){
		memcpy(&line, (fragments+i), SOCKBUF);
		line[SOCKBUF]='\0';

#ifdef DEBUG
		printf("client: line: %s\n", line);	// Read through boundaris from token to mesg
#endif

		sendto(sockfd,line,SOCKBUF,0,(struct sockaddr *)&servaddr,sizeof(servaddr));

	}
	

}

/* packmessage() packs an arbitrary bytestring into an ready to submit MESSAGE struct array.
 * This MESSAGE encapsulates the given string with a authentification token.
 * Each message has a size of SOCKBUF, the token has the size of MESG_TOKENLEN and 
 * the acutual inputstring will be of size MESG_MESSAGELEN. If the inputstring is longer
 * than MESG_MESSAGELEN it will be packed into a second, third ... MESSAGE.
 * These MESSAGES will be returned as an array.
 * 
 * params: Pointer to the beginning of the string to send. The size can be arbitrary long.
 * return: Pointer to the according MESSAGE array.
 */
struct MESSAGE* packmessage(char *inputstr){

	// Free the last used MESSAGE to avoid memory leak
	if(oldmesg!=NULL)
		free(oldmesg);

	struct MESSAGE *cmesg = calloc(1, sizeof(struct MESSAGE)); // current message pointer

	char _token[MESG_MESSAGELEN];
	char _mesg[MESG_TOKENLEN];
	unsigned int _pos_in_current_mesg = 0;
	unsigned int _pos_index_mesg = -1;

	for(int i=0;i<strlen(inputstr);i++){
		
		if(i%MESG_MESSAGELEN==0){
			_pos_index_mesg++;

			cmesg = (struct MESSAGE*)realloc(cmesg, (_pos_index_mesg+1)*sizeof(struct MESSAGE));

#ifdef DEBUG
			printf("packmessage: allocated new message\n");
#endif

			// Set memory to 0x00
			memset((cmesg+_pos_index_mesg)->token, '\0', MESG_TOKENLEN);
			memset((cmesg+_pos_index_mesg)->mesg, '\0', MESG_MESSAGELEN);

			_pos_in_current_mesg=0;
			
		}

		strncpy((cmesg+_pos_index_mesg)->token, token, MESG_TOKENLEN);
		(cmesg+_pos_index_mesg)->mesg[_pos_in_current_mesg]=inputstr[i];

#ifdef DEBUG
		printf("packmessage: OUT: %c\n", inputstr[i]);
#endif

		_pos_in_current_mesg++;


	} // end for

	// Save pointer, so it can be freed on next run.
	oldmesg = cmesg;

	return(cmesg);

}