// Src: https://weblogs.java.net/blog/wvreeven/archive/2015/01/07/safely-loading-different-versions-native-library-jna-or-jni
// This exaple is for linux platform only
// Save the code in a file named jnatest.java
// Keep the jna.jar in the same directory
// Then compile it as
// $javac -cp jna.jar jnatest.java
//
// Then run it as below:
// $java -cp .:jna.jar jnatest

import com.sun.jna.Library;
import com.sun.jna.Native;


interface CLibrary extends Library {
    CLibrary INSTANCE = (CLibrary) Native.loadLibrary("safeudp", CLibrary.class);
    void initserver(String token, String ip, int port);
    void bindserver();
    String readserver();
    void sendserver(String mesg);
}

public class jnatest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
		CLibrary.INSTANCE.initserver("tokenpassword", "127.0.0.1", 32001);
	        CLibrary.INSTANCE.bindserver();
		for(;;){
			String answer = CLibrary.INSTANCE.readserver();
			if(answer == null){
				System.out.println("Token was wrong");
				System.exit(-1);
			}
			System.out.println("server: client says: " + answer);
			
			System.out.println("server: sending back msg to client");
			CLibrary.INSTANCE.sendserver(answer);
		}
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Exception" + e);
        }
    }
}
