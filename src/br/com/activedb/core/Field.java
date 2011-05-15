package br.com.activedb.core;

import java.util.ArrayList;
import java.util.List;

public class Field{
	private Class<?> type;
	private final String name;
	private static final List<Class<?>> supportedTypes;
	private int index;
	private int hashCode;
	
	static{
		supportedTypes = new ArrayList<Class<?>>();
		supportedTypes.add(Integer.class);
		supportedTypes.add(Double.class);
		supportedTypes.add(String.class);
		supportedTypes.add(Character.class);
		supportedTypes.add(Float.class);
		supportedTypes.add(Short.class);
		supportedTypes.add(Byte.class);
		supportedTypes.add(Long.class);
	}

	public Field(Class<?> type, String fieldName){
		this.type = type;
		this.name = fieldName;
		this.validate();
		this.hashCode = 0;
	}

	public Class<?> getType(){
		return type;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("type:" + type.getName());
		
		return s.toString();
	}
	
	public String getName(){
		return name;
	}
	
	public void setIndex(int i){
		this.index = i;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public void validate() throws RuntimeException{
		if(!supportedTypes.contains(type)){
			throw new RuntimeException("Invalid type for field:" + type.getName());
		}
	}
	
	@Override
	public boolean equals(Object obj) {	
		boolean retval = false;
	
		if(obj instanceof Field && ((Field)obj).getName().equals(this.name)){
			retval =  true;
		}
		
		return retval;
	}
	
	@Override
	public int hashCode() {
		if(this.hashCode == 0){
			hashCode = hashCode * 37 + name.hashCode();
		}
		
		return hashCode;
	}
}
