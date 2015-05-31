package de.hda.ain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Main {
	private final static int portNumber = 6666;
	private final static int packetsize = 5;
	private final static Logger log = Logger.getLogger(Main.class.getName());
	private final static String host = "127.0.0.1";
	private static InetAddress address = null;
	
	public static void main(String[] args) {
		
	DatagramSocket socket = null;
	
	
	try {
		address = InetAddress.getByName(host);
		String test = "TEST";
		byte[] is = test.getBytes(StandardCharsets.UTF_8);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(baos);
		
		Packages pa = new Packages(0,10,is, "TEST");
		
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(pa);
		socket = new DatagramSocket();
		
		byte[] serializedMessage = bStream.toByteArray();
		
	      int number = serializedMessage.length;
//	      System.out.println("MessageNumber:" + number);
	      
	      
	      for ( int y = 0; y < number ; y+=4 ){
		      byte[] data = new byte[4];
	
		      // int -> byte[]
		      //System.out.println("Before" +data);
//		      for (int i = 0; i < 4; ++i) {
//		          int shift = i << 3; // i * 8
//		          data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
//		      }
//			
		     // System.out.println("after  "+ data);
		      
			DatagramPacket packet = new DatagramPacket(data,data.length, address, portNumber);
			
			socket.send(packet);
	      }
		
		
		oo.close();

		
		
		
//	      // get the byte array of the object
//	      byte[] buf= baos.toByteArray();
//
//	      int number = buf.length;;
//	      byte[] data = new byte[4];
//
//	      // int -> byte[]
//	      for (int i = 0; i < 4; ++i) {
//	          int shift = i << 3; // i * 8
//	          data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
//	      }
////		
//		socket = new DatagramSocket();
//		
//		DatagramPacket packet = new DatagramPacket(data, data.length, address, portNumber);
//		
//		socket.send(packet);
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		if (socket != null)
			socket.close();
	}
	
	
		

	}

}
