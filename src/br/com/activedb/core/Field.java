package br.com.activedb.core;

import java.util.ArrayList;
import java.util.List;

public class Field <E extends Object> {
	private Class<E> type;
	private static final List<Class<?>> supportedTypes;
	
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

	public Field(Class<E> type){
		this.type = type;
		this.validate();
	}

	public Class<E> getType(){
		return type;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("type:" + type.getName());
		
		return s.toString();
	}
	
	public void validate() throws RuntimeException{
		if(!supportedTypes.contains(type)){
			throw new RuntimeException("Invalid type for field:" + type.getName());
		}
	}
}
