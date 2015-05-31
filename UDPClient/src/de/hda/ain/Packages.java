package de.hda.ain;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

public class Packages implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4975605626742935938L;
	private long packagesNr ;   //which packNumber is it
	private long bytePack;  // how big is the package 
	private byte[] byteStream; // for files etc. 
	private String packName; //for test etc. 
	
	public Packages(long packagesNr, long bytePack, byte[] byteStream, String packName){
		this.packagesNr = packagesNr;
		this.bytePack = bytePack;
		this.byteStream = byteStream; 
		this.packName = packName;
			
		
	}

	public long getPackagesNr() {
		return packagesNr;
	}

	public void setPackagesNr(long packagesNr) {
		this.packagesNr = packagesNr;
	}

	public long getBytePack() {
		return bytePack;
	}

	public void setBytePack(long bytePack) {
		this.bytePack = bytePack;
	}

	public byte[] getByteStream() {
		return byteStream;
	}

	public void setByteStream(byte[] byteStream) {
		this.byteStream = byteStream;
	}

	public String getName() {
		return packName;
	}

	public void setName(String name) {
		this.packName = name;
	}
	
	public String toString(){
		StringBuilder sb =new StringBuilder();
		sb.append("Name: " + packName);
		
		return sb.toString();
	}
	
}
