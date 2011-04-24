package br.com.activedb.sql;

import br.com.activedb.core.common.EnumDisplayable;

public enum SQLCommand implements EnumDisplayable{
	INSERT("Insert"),
	UPDATE("Update"),
	DELETE("Delete"),
	CREATE("Create");

	private SQLCommand(String display){
		this.displayValue = display;
	}
	
	private String displayValue;
	
	@Override
	public String getDisplayValue() {
		return displayValue;
	}
}

