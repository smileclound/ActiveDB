package br.com.activedb.core;

import java.util.List;

public class DataStore {

	private List<Field<? extends Object>> metadata;

	public DataStore(List<Field<? extends Object>> fields){
		this.metadata = fields;
	}
	
	public List<Field<? extends Object>> getMetaData(){
		return metadata;
	}
	
	public Field<? extends Object> getMetaData(int index){
		return metadata.get(index);
	}	
}
