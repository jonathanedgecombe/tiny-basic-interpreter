package com.jonathanedgecombe.interpreter;

public class StringValue extends Value {
	private String value;
	
	public StringValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public int getValue() {
		//Should never be called.
		return 0;
	}

}
