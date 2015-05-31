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
	public ArrayList<byte[]> lastPack = null;
	
	
	public static void main(String[] args) {
		String test = "TESTinggggg";
		byte[] is = test.getBytes(StandardCharsets.UTF_8);
	 	Packages pa = new Packages(0,10,is, test);
		
	 	Helping t = new Helping();
	 	
	 	byte[] tb = new byte[] {1,2,3,4,5,6,7,8};
	 	
	 	t.sendPackages(pa);

	 	
	 	
	 	//System.out.println(t.deserial(t.getBytesFromList(t.getListFromBytes(t.serial(pa)))).getName());

	 	
	}
	
	/**
	 * Only for testing
	 * @param p
	 */
	
	void sendPackages(Packages p){
		lastPack = new ArrayList<byte[]>();
		System.out.println("SendPack" + serial(p).length);
		
		lastPack.add(getByteFromInt(serial(p).length));
		lastPack.addAll(getListFromBytes(serial(p)));
		
		recievePackages(getBytesFromList(lastPack));
		
	}
	
	/**
	 * Only for testing
	 * @param bs
	 */
	void recievePackages(byte[] bs ){
		System.out.println(deserial(bs));
		
	}
	
	
	/**
	 * returns a ArrayList with byte Array the First ist the length of the Packages
	 * @param b
	 * @return ArrayList with byte array
	 */
	
	ArrayList<byte[]> getListFromBytes(byte[] b){
		ArrayList<byte[]> al = new ArrayList<byte[]>();
		
		int counter = 1; //0 = number of size
		
		int size = b.length;
				if (size % 4 == 0){
		
			for ( int i = 0 ; i< size; i=i+4 ){
				al.add(getByteFromInt(counter));
				counter++;
				byte[] by = {b[i],b[i+1],b[i+2],b[i+3]};
				al.add(by);
				
			}
		
		}else {
			//when the byte Array has a uneven length
			
			for ( int i = 0 ; i< size-size%4; i=i+4 ){
				al.add(getByteFromInt(counter));
				counter++;
				byte[] by = {b[i],b[i+1],b[i+2],b[i+3]};
				al.add(by);
				
			}
			
			int byteL = 0;
			byte[] retb = new byte[4];
					
			for ( int t = size-size%4 ; t < size; t++){
				al.add(getByteFromInt(counter));
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
	
	/**
	 * Gets an arrayList and calculate a byte Array but without error correction
	 * @param al
	 * @return byteArray
	 */
	byte[] getBytesFromList(ArrayList<byte[]> al){
		byte[] b = new byte[al.size()*4];
		
		int size = getIntFromByte(al.get(0));
		
		System.out.println("Size: " + size);
		
		
		int temp = 0;

			for ( int i = 2 ; i < al.size()-2 ; i=i+2){
				b[temp] = al.get(i)[0];
				b[temp+1] = al.get(i)[1];
				b[temp+2] = al.get(i)[2];
				b[temp+3] = al.get(i)[3];
				temp=temp+4;
			}	
			
		
		
		return b ;
	}
	
	/**
	 * Return a int from a 4byte Array
	 * @param rno
	 * @return 
	 */
	int getIntFromByte(byte[] rno){
		int i= (rno[0]<<24)&0xff000000|
			       (rno[1]<<16)&0x00ff0000|
			       (rno[2]<< 8)&0x0000ff00|
			       (rno[3]<< 0)&0x000000ff;
		
		
		return i;
	}
	
	/**
	 * Return a byte from an 4byte int
	 * @param b
	 * @return byte array 4 byte
	 */
	byte[] getByteFromInt(int b){
		
		return new byte[] {
	            (byte)(b >>> 24),
	            (byte)(b >>> 16),
	            (byte)(b >>> 8),
	            (byte)b};
		
	}
	/**
	 * return a byte Array from the Packages serializier
	 * @param p
	 * @return
	 */
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
	/**
	 * From a byte Array into a Packages Type
	 * @param b
	 * @return Packages
	 */
	 	 
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
