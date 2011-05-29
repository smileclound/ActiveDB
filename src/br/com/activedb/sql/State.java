package br.com.activedb.sql;

import java.util.List;

public class State {
	private Boolean isFinal;
	private Boolean isInitial;
	private List<StateRouter> stateRouters;	

	public State(Boolean isFinal, Boolean isInitial,
			List<StateRouter> stateRouters) {
		super();
		this.isFinal = isFinal;
		this.isInitial = isInitial;
		this.stateRouters = stateRouters;
	}
	
	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public Boolean getIsInitial() {
		return isInitial;
	}

	public void setIsInitial(Boolean isInitial) {
		this.isInitial = isInitial;
	}

	public List<StateRouter> getStateRouters() {
		return stateRouters;
	}

	public void setStateRouters(List<StateRouter> stateRouters) {
		this.stateRouters = stateRouters;
	}
	
	public void handleInput(StringSentence sequence){
		if(!isInitial && sequence.getCurrentPosition() == -1){
			throw new IllegalStateException("Not initial state. Fail.");
		}
		
		if(sequence.isValid()){
			return;
		}
		
		for(StateRouter router : stateRouters){
			List<State> states = router.getStateForChar(sequence.getNext());
			
			if(states == null){
				if(sequence.isLast() && isFinal){
					System.out.println("Sequence is valid!");
					sequence.setValid(true);
					return;
				}else{
					throw new IllegalStateException("Invalid Sentence");
				}
			}else{
				for(State state : states){
					state.handleInput(sequence);
				}
			}						
		}
	}
		
}
