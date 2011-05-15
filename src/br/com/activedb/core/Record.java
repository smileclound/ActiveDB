package br.com.activedb.core;

import java.util.Map;

public class Record {
	private Map<Field, Value<?>> values;
	private String id;
	
	public Record(Map<Field, Value<?>> values){
		this.values = values;
	}
	
	public Map<Field, Value<?>> getValues(){
		return values;
	}
	
	public Value<?> get(Field index){
		return values.get(index);
	}
	
	public String getRecordID() {
		return id;
	}
	
	public void setRecordID(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean retVal = false;
		
		if(obj instanceof Record && ((Record)obj).getRecordID().equals(this.id)){
			retVal = true;
		}
		
		return retVal;
	}
}
