package de.hda.ain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainServer {
	private final static int portNumber = 6666;
	private final static int packetsize = 5;
	private final static Logger log = Logger.getLogger(MainServer.class.getName());
	
	public static void main(String[] args) {
		log.info("Starting UDP-Server");
		
		
		try {
			
			DatagramSocket socket = new DatagramSocket(portNumber);
			log.info("Server is running on ip: " + socket.getLocalSocketAddress() + " port " + portNumber );
			
			
			 byte[] buffer = null;
			//while(true){
				byte[] data = new byte[4];
			      DatagramPacket packet = new DatagramPacket(data, data.length );
			      socket.receive(packet);
			     
			      int len = 0;
			      // byte[] -> int
			      for (int i = 0; i < 4; ++i) {
			          len |= (data[3-i] & 0xff) << (i << 3);
			      }
			      
			      // now we know the length of the payload
			      buffer = new byte[len];
			      System.out.println("after" + buffer);
			      packet = new DatagramPacket(buffer, buffer.length );
			      socket.receive(packet);
//				  if(buffer != null)
//					  break;
			      ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));
			      Packages messageClass = (Packages) iStream.readObject();
			      
			      if (messageClass != null ){
			    	  System.out.println(messageClass.getName());
			      }else {
			    	  System.out.println("nix");
			      }
			      
			      iStream.close();
//			      
//			      // now we know the length of the payload
//			      buffer = new byte[len];
//			      packet = new DatagramPacket(buffer, buffer.length );
//			      socket.receive(packet);
//				  if(buffer != null)
//					  break;
					  
			//}
//			  ByteArrayInputStream baos = new ByteArrayInputStream(buffer);
//		      ObjectInputStream oos = new ObjectInputStream(baos);
//		      Packages pa = (Packages)oos.readObject();
//		      pa.getName();
			
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failing ", e);
			e.printStackTrace();
		} 
		
		


	}

}
