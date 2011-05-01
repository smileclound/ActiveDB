package br.com.activedb.core;

import java.util.ArrayList;
import java.util.List;

public class Record {
	private List<Value<?>> values;
	private String id;
	
	public Record(ArrayList<Value<?>> values){
		this.values = values;
	}
	
	public List<Value<?>> getValues(){
		return values;
	}
	
	public Value<?> get(int index){
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
