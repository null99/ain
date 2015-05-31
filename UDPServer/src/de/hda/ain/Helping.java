package de.hda.ain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Helping {
	public int blockSize = 4; //size for fragmetierung from  
	
	
	public static void main(String[] args) {
		String test = "TESTinggggg";
		byte[] is = test.getBytes(StandardCharsets.UTF_8);
	 	Packages pa = new Packages(0,10,is, test);
		
	 	Helping t = new Helping();
	 	
	 	byte[] tb = new byte[] {1,2,3,4,5,6,7,8};
	 	
	 	
//	 	for (int i : t.getByteFromInt(155)){
//	 		System.out.println(i);
//	 	}
	 	
//	 	for (byte[] b :t.getListFromBytes(tb)) {
//	 		for (byte bit: b ){
//	 			System.out.println(bit);
//	 		}
//	 	}
	 	
	 //	System.out.println( t.getBytesFromList(t.getListFromBytes(tb)));
//	 	System.out.println("ListLeng bevor" + t.serial(pa).length);
//	 	System.out.println("After" + t.getListFromBytes(t.serial(pa)).size()*4);
	 	
	 	System.out.println(t.deserial(t.getBytesFromList(t.getListFromBytes(t.serial(pa)))).getName());
//	 	);
	 	
	 	//System.out.println(t.getIntFromByte(t.getByteFromInt(54455)));
	 	
	 	//System.out.println(t.deserial(t.serial(pa)).getName());
	 	
	}
	
	
	ArrayList<byte[]> getListFromBytes(byte[] b){
		ArrayList<byte[]> al = new ArrayList<byte[]>();
		
		int size = b.length;
				if (size % 4 == 0){
		
			for ( int i = 0 ; i< size; i=i+4 ){
				byte[] by = {b[i],b[i+1],b[i+2],b[i+3]};
				al.add(by);
				
			}
		
		}else {
			//when the 
			
			for ( int i = 0 ; i< size-size%4; i=i+4 ){
				byte[] by = {b[i],b[i+1],b[i+2],b[i+3]};
				al.add(by);
				
			}
			
			int byteL = 0;
			byte[] retb = new byte[4];
					
			for ( int t = size-size%4 ; t < size; t++){
				retb[byteL] = b[t];
				byteL++;
			}
			
			for (int l = byteL ;l<=3 ;l++){
				retb[l] =0;
			}
			
			al.add(retb);
			
			
		}
		
		return al;
	}
	
	
	byte[] getBytesFromList(ArrayList<byte[]> al){
		byte[] b = new byte[al.size()*4];
		
		
		int temp = 0;
//		if (al.size()%4 ==0){
			for ( int i = 0 ; i < al.size() ; i++){
				b[temp] = al.get(i)[0];
				b[temp+1] = al.get(i)[1];
				b[temp+2] = al.get(i)[2];
				b[temp+3] = al.get(i)[3];
				temp=temp+4;
			}	
			
//		}else {
//			
//			
//			
//		}
		
//		for (byte[] bu : al){
//			System.out.println(" b:  "+ bu.length);
//		}
		
//			for (byte bi :b){
//				System.out.println(bi);
//			}
//		
//		System.out.println( "Size : " +b.length);
		
		
		return b ;
	}
	
	
	int getIntFromByte(byte[] rno){
		int i= (rno[0]<<24)&0xff000000|
			       (rno[1]<<16)&0x00ff0000|
			       (rno[2]<< 8)&0x0000ff00|
			       (rno[3]<< 0)&0x000000ff;
		
		
		return i;
	}
	
	
	byte[] getByteFromInt(int b){
		
		return new byte[] {
	            (byte)(b >>> 24),
	            (byte)(b >>> 16),
	            (byte)(b >>> 8),
	            (byte)b};
		
	}
	
	 byte[] serial(Packages p){
		 
		 	
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			try {
			ObjectOutputStream oo = new ObjectOutputStream(bStream); 
			
				oo.writeObject(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bStream.toByteArray();
		 
		
		 
	}
	 
	Packages deserial(byte[] b){
		 ObjectInputStream iStream = null;
		 Packages messageClass = null;
		try {
			iStream = new ObjectInputStream(new ByteArrayInputStream(b));
			messageClass = (Packages) iStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		return messageClass;
	}

}
