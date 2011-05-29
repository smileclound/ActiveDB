package br.com.activedb.sql;

public class StringSentence {
	private String sentence;
	private int currentPosition;
	private boolean isValid;

	public StringSentence(String sentence) {	
		this.sentence = sentence;
		currentPosition = -1;
	}
	
	public Character getNext(){
		return sentence.charAt(++currentPosition);
	}		
	
	public boolean isLast(){
		return sentence.length() == currentPosition;
	}
	
	public int getCurrentPosition(){
		return currentPosition;
	}
	
	public boolean isValid(){
		return isValid;
	}
	
	public void setValid(boolean valid){
		this.isValid = valid;
	}

}
