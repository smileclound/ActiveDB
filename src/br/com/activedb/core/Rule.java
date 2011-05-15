package br.com.activedb.core;

import java.util.HashSet;
import java.util.Set;

public class Rule<T extends Comparable<T>>{
	private BooleanRule rule;
	private Value<T> value;
	private String fieldID;
	
	public Rule(BooleanRule rule, Value<T> value, String fieldID){
		this.rule = rule;
		this.value = value;
		this.fieldID = fieldID;
	}
	
	public boolean validateRule(Value<?> obj){	
		boolean retVal = false;
		@SuppressWarnings("unchecked")
		Value<T> value = (Value<T>)obj;

		switch (rule) {
		case EQUALS:
			retVal = this.value.equals(value);			
			break;
		case HIGHER:
			retVal = this.value.compareTo(value) > 0;
			break;
		case LOWER:
			retVal = this.value.compareTo(value) < 0;			
		break;
		case DIFF:
			retVal = !this.value.equals(value);
		break;	
		default:
			retVal = false;
		}
		
		return retVal;
	}
	
	public Set<String> getValidKeysFromIndexForValue(FieldIndex index){
		Set<String> result = null;
		
		switch (rule) {
		case EQUALS:
			result = index.getAllIdsForValueEqualsTo(value);			
			break;
		case HIGHER:
			result = index.getAllIdsForValueHigherThan(value, true);
			break;
		case LOWER:
			result = index.getAllIdsForValueLowerThan(value, true);		
		break;
		case DIFF:
			result = index.getAllIdsForValueDifferentThan(value);
		break;	
		default:
			result = new HashSet<String>(0);
		}
		
		return result;
	}
	
	public String getFieldID(){
		return fieldID;
	}
	
	public static enum BooleanRule{
		EQUALS, HIGHER, LOWER, DIFF;
	}

}
