package br.com.activedb.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
	private Map<String, Field> metaData;
	private Map<String, Record> store;
	private String storeID;
	private RecordIdGenerator idGen;

	public DataStore(List<Field> fields, String storeID, RecordIdGenerator idGen){
		this.metaData = new LinkedHashMap<String, Field>();
		this.storeID = storeID;
		this.store = new HashMap<String, Record>();
		this.idGen = idGen;
		
		this.applyMetaData(fields);
	}
	
	public Collection<Field> getMetaData(){
		return metaData.values();
	}
	
	public String getStoreID(){
		return storeID;
	}
	
	public synchronized String storeRecord(Record record){
		record.setRecordID(idGen.generateId());
		store.put(record.getRecordID(), record);
		
		return record.getRecordID();
	}
	
	public List<String> storeRecord(ArrayList<Record> records){
		List<String> retVal = new ArrayList<String>();
		
		for(Record record : records){
			record.setRecordID(idGen.generateId());
			retVal.add(record.getRecordID());
			
			store.put(record.getRecordID(), record);
		}
		
		return retVal;
	}
	
	public Record lookupRecordByRecordID(String id){
		return store.get(id);
	}
	
	public synchronized Collection<Record> lookupRecords(List<Rule<Object>> rules){
		List<Record> list = new ArrayList<Record>();
		
		for(Record rec : store.values()){
	
			rulesvalidation:
			for(Rule<Object> rule : rules){
				Field field = metaData.get(rule.getFieldID());
				
				if(field == null){
			    	throw new RuntimeException("Unknown fieldID for DataStore. FieldID:" + rule.getFieldID() + " DataStore:" + this.storeID);
				}	
				
				int fieldIndex = field.getIndex();
				
				if(!rule.validateRule(rec.get(fieldIndex).getValue())){
					break rulesvalidation;
				}
				
				list.add(rec);
			}
		
		}
		
		return list;		
	}
	
	private void applyMetaData(List<Field> meta){
		int i = 0;
		for(Field f : meta){
			f.setIndex(i++);
			
			metaData.put(f.getName(), f);
		}
	}
	
	
}
