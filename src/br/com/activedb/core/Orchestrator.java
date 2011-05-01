package br.com.activedb.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orchestrator {
	private Map<String, DataStore> stores;
	private ServerState state;
	
	public Orchestrator(){
		this.init();
	}
	
	public void init(){
		stores = new HashMap<String, DataStore>();
		state = new ServerState("ALPHA_SERVER");
	}
	
	public ServerState getServerState(){
		return state;
	}
	
	public DataStore getStoreForID(String storeID) throws Exception{
		DataStore store = stores.get(storeID);
		
		if(store == null){
			throw new Exception("Store " + storeID + " does not exist");
		}
		
		return store;
	}
	
	public DataStore createStore(String storeID, List<Field> metadata) throws Exception{
		DataStore store = stores.get(storeID);
		
		if(store != null){
			throw new Exception("Store cannot be created: Already exists");
		}
		
		store = new DataStore(metadata, storeID, state.getRecordIDGeneratorForDataStore(storeID));
		
		return store;
	}
}
