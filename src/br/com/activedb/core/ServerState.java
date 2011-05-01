package br.com.activedb.core;

import java.util.HashMap;
import java.util.Map;

public class ServerState {
	private Map<String, RecordIdGenerator> idGenerators;
	private String serverName;
	
	public ServerState(String serverName){
		idGenerators = new HashMap<String, RecordIdGenerator>();
		this.serverName = serverName;
	}
	
	public String getServerName(){
		return serverName;
	}
	
	public RecordIdGenerator getRecordIDGeneratorForDataStore(String dataStoreID){
		RecordIdGenerator idGen = idGenerators.get(dataStoreID);
		
		if(idGen == null){
			idGen = new RecordIdGenerator(dataStoreID, serverName);
			
			idGenerators.put(dataStoreID, idGen);
		}
		
		return idGen;
	}
}
