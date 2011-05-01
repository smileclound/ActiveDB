package br.com.activedb.core;

public class RecordIdGenerator {
	private long gen;
	private String storeName;
	private String serverName;
	
	public RecordIdGenerator(String storeName, String serverName){
		this.gen = 0;
		this.storeName = storeName;
		this.serverName = serverName;
	}
	
	public RecordIdGenerator(RecordIdGenerator other){
		this.gen = other.gen;
		this.storeName = other.storeName;
		this.serverName = other.serverName;
	}
	
	public String generateId(){
		StringBuilder builder = new StringBuilder(); 
		
		++gen;
		
		builder.append(serverName + ":");
		builder.append(storeName + ":");
		builder.append(gen);
		
		return builder.toString();
	}

}
