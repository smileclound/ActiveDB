package br.com.activedb.sql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQLStateChain {
	private LinkedList<SQLSintaxState> chain;
	public static final List<SQLSintaxState> validStateChains;
	public static final String VALUES = "<VALUES>";
	public static final String FIELDS = "<FIELDS>";
	public static final String TABLE = "<TABLE>";
	
	
	static{
		validStateChains = new ArrayList<SQLSintaxState>();		
		validStateChains.add(new SQLSintaxState(SQLCommand.INSERT.toString(), null, null, " "));
		validStateChains.add(new SQLSintaxState("INTO", null, null, " "));
		validStateChains.add(new SQLSintaxState(TABLE, null, null, " "));
		validStateChains.add(new SQLSintaxState(FIELDS, "(", ",", ") "));
		validStateChains.add(new SQLSintaxState(VALUES, "(", ",", ") "));
		
	}
	
	public SQLStateChain(LinkedList<SQLSintaxState> chain){
		this.chain = chain;
	}
	
	public List<SQLSintaxState> getChain(){
		return chain;
	}
	
	public boolean isValid(SQLStateChain other){
		return false;
	}
}
