package br.com.activedb.core;

public class Value<T> implements Comparable<T>{
	private Comparable<T> value;
	private Class<?> type;
	
	public Value(Comparable<T> value, Class<?> type){
		this.value = value;
		this.type = type;
	}
	
	public Object getValue(){
		return value;
	}
	
	public Class<?> getType(){
		return type;
	}

	@Override
	public int compareTo(T o) {
		return this.value.compareTo(o);
	}
}
