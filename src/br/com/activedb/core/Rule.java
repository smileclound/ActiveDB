package br.com.activedb.core;

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
	
	public String getFieldID(){
		return fieldID;
	}
	
	public static enum BooleanRule{
		EQUALS, HIGHER, LOWER, DIFF;
	}

}
