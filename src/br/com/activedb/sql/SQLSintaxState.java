package br.com.activedb.sql;

public class SQLSintaxState {
	private String stateValue;
	private String beginIdentifier;
	private String endIdentifier;
	private String separator;
	
	public SQLSintaxState(String stateValue, String beginIdentifier, String separator, String endIdentifier){
		this.stateValue = stateValue;
		this.beginIdentifier = beginIdentifier;
		this.separator = separator;
		this.endIdentifier = endIdentifier;
	}
	
	public String getStateValue(){
		return stateValue;
	}
	
	public String getBeginIdentifier(){
		return beginIdentifier;
	}
	
	public String separator(){
		return separator;
	}
	
	public String getEndIndentifier(){
		return endIdentifier;
	}
	
}
