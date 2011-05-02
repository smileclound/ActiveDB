package br.com.activedb.core;

public class Value<T extends Comparable<T>> implements Comparable<Value<T>>{
	private T value;
	private Class<?> type;
	
	public Value(T value, Class<?> type){
		this.value = value;
		this.type = type;
	}
	
	public T getValue(){
		return value;
	}
	
	public Class<?> getType(){
		return type;
	}

	@Override
	public int compareTo(Value<T> o) {
		return this.value.compareTo(o.value);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean retVal = false;
		
		if(obj instanceof Value && this.value.equals(((Value<?>)obj).getValue())){
			retVal = true;
		}
		
		return retVal;
	}
}
