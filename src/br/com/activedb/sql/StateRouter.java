package br.com.activedb.sql;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StateRouter {
	private Map<List<Character>, State> rules;
	
	public StateRouter(Map<List<Character>, State> rules){
		this.rules = rules;
	}
	
	public List<State> getStateForChar(Character ch){
		List<State> states = new LinkedList<State>();
		
		for(List<Character> key : rules.keySet()){
			if(key.contains(ch)){
				states.add(rules.get(key));
			}
		}
		
		return states;
	}
	
	
}
